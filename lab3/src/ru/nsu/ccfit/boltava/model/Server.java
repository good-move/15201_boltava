package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.model.chat.ChatMember;
import ru.nsu.ccfit.boltava.model.message.IMessageHandler;
import ru.nsu.ccfit.boltava.model.message.ISocketMessageStream.MessageStreamType;
import ru.nsu.ccfit.boltava.model.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private HashSet<ChatMember> mChatMembers = new HashSet<>();
    private LinkedBlockingQueue<Message> msgQueue = new LinkedBlockingQueue<>();
    private IMessageHandler mMsgHandler;
    private Thread mXMLListener;
    private Thread mObjectListener;

//    TODO
//    create object socket listener
//    create xml socket listener
//
    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.createClientListeners();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("[Error] Invalid port number format: " + e.getMessage());
        }


    }

    private void createClientListeners() throws IOException, NumberFormatException {
        try (FileInputStream is = new FileInputStream("server.properties")) {
            Server server = new Server();
            Properties props = new Properties();
            props.load(is);

            mObjectListener = new Thread(new ClientsListener(Integer.parseInt(props.getProperty("object_port")),
                    MessageStreamType.OBJ));
            mXMLListener = new Thread(new ClientsListener(Integer.parseInt(props.getProperty("xml_port")),
                    MessageStreamType.XML));
        }
    }

    private void start() {
        mXMLListener.start();
        mObjectListener.start();
    }

    private void stop() {
        mXMLListener.interrupt();
        mObjectListener.interrupt();
        // send SHUT_SOWN message to members?
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
                    mChatMembers.add(new ChatMember(socket, mMsgHandler, streamType));

                    System.out.println("Client connected: " + socket.getInetAddress());
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

}
