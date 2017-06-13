package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.IMessageHandler;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream.MessageStreamType;
import ru.nsu.ccfit.boltava.model.message.Message;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection<IHandler extends IMessageHandler> {

    private int mID;
    private Thread mSenderThread;
    private Thread mReceiverThread;
    private LinkedBlockingQueue<Message> mSendMsgQueue = new LinkedBlockingQueue<>();
    private LinkedBlockingQueue<Message> mReceiveMsgQueue = new LinkedBlockingQueue<>();
    private final Socket mSocket;
    private IHandler mMsgHandler;
    private ISocketMessageStream mStream;
    private boolean isListening = false;

    public Connection(Socket socket, IHandler handler, MessageStreamType type) throws IOException {
        mSocket = socket;
        mMsgHandler = handler;
        mStream = SocketMessageStreamFactory.get(type, mSocket);
    }

    public Connection(ConnectionConfig connectionConfig, IHandler handler) throws IOException {
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
                    msg = msg.getClass().cast(msg);
                    msg.handle(mMsgHandler);
                }
            } catch (IOException | ClassNotFoundException e) {

            } finally {
                disconnect();
            }
        }, "Receiver Thread");

    }

}
