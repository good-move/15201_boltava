package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.model.message.IMessageHandler;
import ru.nsu.ccfit.boltava.model.message.ISocketMessageStream;
import ru.nsu.ccfit.boltava.model.message.Message;
import ru.nsu.ccfit.boltava.model.message.SocketMessageStreamFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection {

    private int mID;
    private Thread mSenderThread;
    private Thread mReceiverThread;
    private LinkedBlockingQueue<Message> mSendMsgQueue = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Message> mReceiveMsgQueue = new LinkedBlockingQueue<>();
    private final Socket mSocket;
    private IMessageHandler mMsgHandler;
    private ISocketMessageStream mStream;
    private boolean isListening = false;

    public Connection(Socket socket, IMessageHandler handler, ISocketMessageStream.MessageStreamType type) throws IOException {
        mSocket = socket;
        mMsgHandler = handler;
        mStream = SocketMessageStreamFactory.get(type, mSocket);
    }

    public Connection(ConnectionConfig connectionConfig, IMessageHandler handler) throws IOException {
        mSocket = new Socket(connectionConfig.getHost(), connectionConfig.getPort());
        mStream = SocketMessageStreamFactory.get(connectionConfig.getStreamType(), mSocket);
        mMsgHandler = handler;
    }

    public void sendMessage(Message msg) throws InterruptedException {
        mSendMsgQueue.put(msg);
    }

    public void disconnect() {
        try {
            mSocket.close();
            mReceiverThread.interrupt();
            mSenderThread.interrupt();
            // on connection closed listener
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        if (isListening) throw new IllegalStateException("Connection is already established");
        createStreams();
        mSenderThread.start();
        mReceiverThread.start();
        isListening = true;
    }

    private void createStreams() {
        mSenderThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    mStream.write(mSendMsgQueue.take());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            } finally {
                disconnect();
            }
        }, "Sender Thread");

        mReceiverThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    Message msg = mStream.read();
                    msg.handle(mMsgHandler);
                }
            } catch (IOException | ClassNotFoundException e) {

            } finally {
                disconnect();
            }
        }, "Receiver Thread");

    }

}
