package ru.nsu.ccfit.boltava.model.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream.MessageStreamType;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

public class Server {

    private static final Logger WORKFLOW_LOGGER = LogManager.getLogger(Server.class);
    private static final Logger CONSOLE_LOGGER = LogManager.getLogger("ConsoleLogger");

    private HashSet<ChatMember> chatMembers = new HashSet<>();
    private HashSet<String> reservedUserNames = new HashSet<>();

    private Server server;
    private Thread xmlListener;
    private Thread objectListener;

    private final String USERNAME_PATTERN_STRING = "\\w+";
    private final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN_STRING);
    private final int CHAT_HISTORY_SNEAK_PEEK_SIZE = 10;

    private ArrayList<TextMessage> chatHistory = new ArrayList<TextMessage>();

    private final Object lock = new Object();

    private Server() throws IOException {
        try (FileInputStream is = new FileInputStream("server.properties")) {
            Properties props = new Properties();
            props.load(is);

            objectListener = new Thread(new ClientsListener(Integer.parseInt(props.getProperty("object_port")),
                    MessageStreamType.OBJ), "OBJ Listener");
            xmlListener = new Thread(new ClientsListener(Integer.parseInt(props.getProperty("xml_port")),
                    MessageStreamType.XML), "XML Listener");
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            CONSOLE_LOGGER.error("Failed to start up server" + e.getMessage());
        } catch (NumberFormatException e) {
            CONSOLE_LOGGER.error("[Error] Invalid port number format: " + e.getMessage());
        }
    }

    private void start() {
        WORKFLOW_LOGGER.info("\nStarting server\n");
        server = this;
        xmlListener.start();
        objectListener.start();
    }

    private void stop() {
        xmlListener.interrupt();
        objectListener.interrupt();
        WORKFLOW_LOGGER.info("\nShutting down server\n");
    }

    boolean isUsernameTaken(String username) {
        boolean isFree;
        synchronized (lock) {
            isFree = reservedUserNames.contains(username);
        }
        return isFree;
    }

    boolean isUsernameFormatValid(String username) {
        return usernamePattern.matcher(username).matches();
    }

    void registerUsername(String username) {
        synchronized (lock) {
            reservedUserNames.add(username);
        }
    }

    void removeChatMember(ChatMember member) {
        synchronized (lock) {
            if (member.getUser() != null) {
                WORKFLOW_LOGGER.info("Freeing username: " + member.getUser().getUsername());
                reservedUserNames.remove(member.getUser().getUsername());
            }
            chatMembers.remove(member);
        }
    }

    void broadcastMessageFrom(ServerMessage msg, ChatMember member) throws InterruptedException {
        WORKFLOW_LOGGER.info("Broadcasting: " + msg.getClass().getSimpleName());

        synchronized (lock) {
            for (ChatMember m : chatMembers) {
                if (member != null && !m.equals(member)) {
                    m.sendMessage(msg);
                }
            }
        }
    }

    ArrayList<TextMessage> getChatHistorySneakPeek() {
        ArrayList<TextMessage> result = new ArrayList<>();
        synchronized (lock) {
            int historySize = chatHistory.size();
            int min = CHAT_HISTORY_SNEAK_PEEK_SIZE < historySize ? CHAT_HISTORY_SNEAK_PEEK_SIZE : historySize;
            for (int i = 0; i < min; i++) {
                result.add(chatHistory.get(i));
            }
        }

        return result;
    }

    void rollMessageHistory(TextMessage message) {
        synchronized (lock) {
            chatHistory.add(message);
            if (chatHistory.size() > CHAT_HISTORY_SNEAK_PEEK_SIZE) {
                chatHistory.remove(0);
            }
        }
    }

    List<User> getOnlineUsersList() {
        List<User> userList = new ArrayList<>();
        synchronized (lock) {
            chatMembers.forEach(m -> {
                User user = m.getUser();
                if (user != null) userList.add(user);
            });
        }
        return userList;
    }

    private class ClientsListener implements Runnable {

        private ServerSocket serverSocket;
        private MessageStreamType streamType;

        ClientsListener(int port, MessageStreamType type) throws IOException {
            serverSocket = new ServerSocket(port);
            streamType = type;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    Socket socket = serverSocket.accept();
                    ChatMember member = new ChatMember(socket, server, streamType);
                    chatMembers.add(member);
                    CONSOLE_LOGGER.info("Client connected: " + socket.getInetAddress());
                }
            } catch (IOException | JAXBException e) {
                WORKFLOW_LOGGER.error(e.getMessage());
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    WORKFLOW_LOGGER.error(e.getMessage());
                }
            }
        }

    }

}
