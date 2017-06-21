package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.message.notification.UserLeftChat;
import ru.nsu.ccfit.boltava.model.net.IServerSocketMessageStream;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream.MessageStreamType;
import ru.nsu.ccfit.boltava.model.net.ServerMessageStreamFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class ChatMember {

    private static AtomicLong ID_GENERATOR = new AtomicLong(0);

    private long id = ID_GENERATOR.incrementAndGet();
    private User user;
    private String sessionId;

    private Socket socket;
    private Server server;
    private ServerMessageHandler messageHandler;
    private IServerSocketMessageStream stream;

    private Thread senderThread;
    private Thread receiverThread;
    private LinkedBlockingQueue<ServerMessage> messageQueue = new LinkedBlockingQueue<>();
    private boolean isClosed;

    public ChatMember(Socket socket,
                      Server server,
                      MessageStreamType type) throws IOException {
        this.socket = socket;
        this.server = server;
        messageHandler = new ServerMessageHandler(server, this);
        stream = ServerMessageStreamFactory.get(type, socket);

        senderThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    stream.write(messageQueue.take());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.err.println("Interrupted");
            } finally {
                close();
            }
        }, "Sender Thread");


        receiverThread = new Thread(() -> {
            try {
                while (!Thread.interrupted()) {
                    Request msg = stream.read();
                    msg.handle(messageHandler);
                }
            } catch (IOException | ClassNotFoundException e) {

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }, "Receiver Thread");

        senderThread.start();
        receiverThread.start();

    }

    public void sendMessage(ServerMessage msg) throws InterruptedException {
        messageQueue.put(msg);
    }

    public long getID() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUser(User user) {
        this.user = user;
    }

    void close() {
        try {
            if (!isClosed) {
                isClosed = true;
                socket.close();
                server.broadcastMessageFrom(new UserLeftChat(getUser().getUsername()), this);
                server.removeChatMember(this);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChatMember that = (ChatMember) o;

        return id == that.id && (sessionId != null ? sessionId.equals(that.sessionId) : that.sessionId == null);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
        return result;
    }
}
