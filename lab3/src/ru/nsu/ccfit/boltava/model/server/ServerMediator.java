package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.net.IServerSocketMessageStream;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream;
import ru.nsu.ccfit.boltava.model.net.ServerMessageStreamFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class ServerMediator {

    private static AtomicLong ID_GENERATOR = new AtomicLong(0);

    private long mID = ID_GENERATOR.incrementAndGet();
    private User mUser;
    private boolean isListening = false;
    private Socket mSocket;
    private ChatMember mChatMember;

    private Thread mSenderThread;
    private Thread mReceiverThread;
    private IServerSocketMessageStream mStream;
    private IServerMessageHandler mMsgHandler;
    private LinkedBlockingQueue<ServerMessage> mSendMsgQueue = new LinkedBlockingQueue<>();


    public ServerMediator(Socket socket,
                          IServerMessageHandler handler,
                          ISocketMessageStream.MessageStreamType type,
                          ChatMember member) throws IOException {
        mSocket = socket;
        mMsgHandler = handler;
        mStream = ServerMessageStreamFactory.get(type, mSocket);
        mChatMember = member;

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
                    Request msg = mStream.read();
                    msg = msg.getClass().cast(msg);
                    msg.handle(mMsgHandler);
                }
            } catch (IOException | ClassNotFoundException e) {

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                disconnect();
            }
        }, "Receiver Thread");

    }

    public void sendMessage(ServerMessage msg) throws InterruptedException {
        mSendMsgQueue.put(msg);
    }

    public void disconnect() {
        try {
            mReceiverThread.interrupt();
            mSenderThread.interrupt();
            mSocket.close();
            // on connection closed listener
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            mChatMember.disconnect();
        }
    }

    public void listen() {
        if (isListening) throw new IllegalStateException("Mediator is already established");
        mSenderThread.start();
        mReceiverThread.start();
        isListening = true;
    }

}
