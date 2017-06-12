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
    private Socket mSocket;
    private IMessageHandler mMsgHandler;
    private ISocketMessageStream mStream;

    public Connection(ConnectionConfig connectionConfig) throws IOException {
        mSocket = new Socket(connectionConfig.getHost(), connectionConfig.getPort());
        mMsgHandler = connectionConfig.getMessageHandler();

        mStream = SocketMessageStreamFactory.get(connectionConfig.getStreamType(), mSocket);

        mSenderThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    mStream.write(mSendMsgQueue.take());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
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
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }, "Receiver Thread");

    }

    public void sendMessage(Message msg) throws InterruptedException {
        mSendMsgQueue.put(msg);
    }

    public void disconnect() {
        try {
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() throws InterruptedException {
        mSenderThread.start();
        mReceiverThread.start();
        mSenderThread.join();
        mReceiverThread.join();
        disconnect();
    }

}
