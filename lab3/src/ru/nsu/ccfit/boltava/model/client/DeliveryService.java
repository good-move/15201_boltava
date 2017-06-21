package ru.nsu.ccfit.boltava.model.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.net.ClientMessageStreamFactory;
import ru.nsu.ccfit.boltava.model.net.IClientSocketMessageStream;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class DeliveryService {

    private final static Logger logger = LogManager.getLogger("ConsoleLogger");

    private boolean isStarted = false;
    private boolean isStopped = false;
    private IClientSocketMessageStream mStream;
    private Thread mSenderThread;
    private Thread mReceiverThread;
    private Socket mSocket;
    private IClientMessageHandler mMsgHandler;
    private LinkedBlockingQueue<Request> mSendMsgQueue = new LinkedBlockingQueue<>();

    public DeliveryService(ConnectionConfig connectionConfig, IClientMessageHandler handler) throws IOException {
        mSocket = new Socket(connectionConfig.getHost(), connectionConfig.getPort());
        mStream = ClientMessageStreamFactory.get(connectionConfig.getStreamType(), mSocket);
        mMsgHandler = handler;

        mSenderThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    mStream.write(mSendMsgQueue.take());
                }
            } catch (InterruptedException e) {
            } catch (IOException e) {
            } finally {
                logger.info(Thread.currentThread().getName() + " interrupted");
                shutDown();
            }
        }, "Sender Thread");

        mReceiverThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    ServerMessage msg = mStream.read();
                    msg = msg.getClass().cast(msg);
                    msg.handle(mMsgHandler);
                }
            } catch (IOException | ClassNotFoundException e) {
            } finally {
                logger.info(Thread.currentThread().getName() + " interrupted");
                shutDown();
            }
        }, "Receiver Thread");

    }

    public void sendMessage(Request msg) throws InterruptedException {
        mSendMsgQueue.put(msg);
    }

    public void start() {
        if (isStarted) throw new IllegalStateException("Delivery Service cannot be started more than once");
        mSenderThread.start();
        mReceiverThread.start();
        isStarted = true;
    }

    public void stop() {
        try {
            shutDown();
            mSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shutDown() {
        if (!isStopped) {
            isStopped = true;
            mReceiverThread.interrupt();
            mSenderThread.interrupt();
            // notify observers
        }
    }

}
