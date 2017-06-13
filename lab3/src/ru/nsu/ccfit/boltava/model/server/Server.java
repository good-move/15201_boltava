package ru.nsu.ccfit.boltava.model.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream.MessageStreamType;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;

public class Server {

    private static final Logger logger = LogManager.getLogger("ConsoleLogger");

    private HashMap<Long, ChatMember> mChatMembers = new HashMap<>();
    private HashSet<String> mReservesUserNames = new HashSet<>();
    private LinkedBlockingQueue<ServerMessage> mMsgQueue = new LinkedBlockingQueue<>();

    private Server server;
    private Thread xmlListener;
    private Thread objectListener;
    private Thread messageSender;

    private final String USERNAME_PATTERN_STRING = "(\\w+){5,}";
    private final Pattern usernamePattern = Pattern.compile(USERNAME_PATTERN_STRING);

//    private List<TextMess>
//    Add message history


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

    public void enqueueMessage(ServerMessage msg) throws InterruptedException {
        mMsgQueue.put(msg);
        logger.info("Put a message in queue: " + msg.getClass().getSimpleName());
    }

    public boolean isUsernameTaken(String username) {
        return mReservesUserNames.contains(username);
    }

    public boolean isUsernameFormatValid(String username) {
        return usernamePattern.matcher(username).matches();
    }

    public void registerUsername(String username) {
        mReservesUserNames.add(username);
    }

    private void broadcastMessage(MessageBox msgBox) throws InterruptedException {
        for (ChatMember m : mChatMembers.values()) {
            if (m.getID() != msgBox.senderID)
                m.sendMessage(msgBox.message);
        }
    }

    private void broadcastMessage(ServerMessage msg) throws InterruptedException {
        for (ChatMember m : mChatMembers.values()) {
            if (!m.getSessionId().equals(msg.getSessionId())) {
                m.sendMessage(msg);
            }
        }
    }

    public List<String> getOnlineUsersList() {
        return new ArrayList<>(mReservesUserNames);
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
                    mChatMembers.put(member.getID(), member);
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

    static class MessageBox {
        long senderID;
        long receiverID;
        ServerMessage message;
        BroadcastCoverage coverage;

        MessageBox() {}

        MessageBox(long senderID,
                   long receiverID,
                   ServerMessage message,
                   BroadcastCoverage coverage) {
            this.senderID = senderID;
            this.receiverID = receiverID;
            this.message = message;
            this.coverage = coverage;
        }

        public enum BroadcastCoverage {
            Broadcast,
            Multicast,
            Unicast
        }

    }

}
