package ru.nsu.ccfit.boltava.model.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.message.message_content.ChatMessage;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream.MessageStreamType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

public class Server {

    private static final Logger logger = LogManager.getLogger("ConsoleLogger");

    private HashSet<ChatMember> chatMembers = new HashSet<>();
    private HashSet<String> reservedUserNames = new HashSet<>();
    private LinkedBlockingQueue<ServerMessage> mMsgQueue = new LinkedBlockingQueue<>();

    private Server server;
    private Thread xmlListener;
    private Thread objectListener;
    private Thread messageSender;

    private final String USERNAME_PATTERN_STRING = "(\\w+){5,}";
    private final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN_STRING);
    private final int CHAT_HISTORY_SNEAK_PEEK_SIZE = 10;

    private ArrayList<ChatMessage> chatHistory = new ArrayList<>();

    private final Object lock = new Object();

    private Server() throws IOException {
        try (FileInputStream is = new FileInputStream("server.properties")) {
            Properties props = new Properties();
            props.load(is);

            objectListener = new Thread(new ClientsListener(Integer.parseInt(props.getProperty("object_port")),
                    MessageStreamType.OBJ), "OBJ Listener");
            xmlListener = new Thread(new ClientsListener(Integer.parseInt(props.getProperty("xml_port")),
                    MessageStreamType.XML), "XML Listener");

            messageSender = new Thread(new Broadcaster(), "Broadcaster");
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            logger.error("[Error] Invalid port number format: " + e.getMessage());
        }
    }

    private void start() {
        server = this;
        xmlListener.start();
        objectListener.start();
        messageSender.start();
    }

    private void stop() {
        xmlListener.interrupt();
        objectListener.interrupt();
        messageSender.interrupt();
        // send SHUT_SOWN message to members?
    }

    void enqueueMessage(ServerMessage msg) throws InterruptedException {
        mMsgQueue.put(msg);
        logger.info("Put a message in queue: " + msg.getClass().getSimpleName());
    }

    boolean isUsernameTaken(String username) {
        return reservedUserNames.contains(username);
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
            reservedUserNames.remove(member.getUser().getUsername());
            chatMembers.remove(member);
        }
    }

    private void broadcastMessage(ServerMessage msg) throws InterruptedException {
        for (ChatMember m : chatMembers) {
            if (m.getSessionId() != null &&
                !m.getSessionId().equals(msg.getSessionId())) {
                m.sendMessage(msg);
            }
        }
    }

    ArrayList<ChatMessage> getChatHistorySneakPeek() {
        ArrayList<ChatMessage> result = new ArrayList<>();
        synchronized (lock) {
            int historySize = chatHistory.size();
            int min = CHAT_HISTORY_SNEAK_PEEK_SIZE < historySize ? CHAT_HISTORY_SNEAK_PEEK_SIZE : historySize;
            for (int i = 0; i < min; i++) {
                result.add(chatHistory.get(historySize - i - 1));
            }
            lock.notifyAll();
        }

        return result;
    }

    List<String> getOnlineUsersList() {
        return new ArrayList<>(reservedUserNames);
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
                    logger.info("Client connected: " + socket.getInetAddress());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private class Broadcaster implements Runnable {

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    ServerMessage msg = mMsgQueue.take();
                    logger.info("Will process msg");
                    broadcastMessage(msg);
                    logger.info("Did process msg");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
