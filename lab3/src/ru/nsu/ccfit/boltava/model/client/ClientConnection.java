package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.message.*;
import ru.nsu.ccfit.boltava.model.net.*;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientConnection {

    private boolean isListening = false;
    private IClientSocketMessageStream mStream;
    private Thread mSenderThread;
    private Thread mReceiverThread;
    private Socket mSocket;
    private IClientMessageHandler mMsgHandler;
    private LinkedBlockingQueue<Request> mSendMsgQueue = new LinkedBlockingQueue<>();

    public ClientConnection(ConnectionConfig connectionConfig, IClientMessageHandler handler) throws IOException {
        mSocket = new Socket(connectionConfig.getHost(), connectionConfig.getPort());
        mStream = ClientMessageStreamFactory.get(connectionConfig.getStreamType(), mSocket);
        mMsgHandler = handler;

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
                    ServerMessage msg = mStream.read();
                    msg = msg.getClass().cast(msg);
                    msg.handle(mMsgHandler);
                }
            } catch (IOException | ClassNotFoundException e) {

            } finally {
                disconnect();
            }
        }, "Receiver Thread");

    }

    public void sendMessage(Request msg) throws InterruptedException {
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
        if (isListening) throw new IllegalStateException("Mediator is already established");
        mSenderThread.start();
        mReceiverThread.start();
        isListening = true;
    }

}
