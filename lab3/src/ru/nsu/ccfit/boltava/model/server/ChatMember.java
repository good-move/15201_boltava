package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream.MessageStreamType;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicLong;

public class ChatMember {

    private static AtomicLong ID_GENERATOR = new AtomicLong(0);

    private long id = ID_GENERATOR.incrementAndGet();
    private User user;
    private String sessionId;
    private ServerMediator connection;
    private ServerMessageHandler messageHandler;

    public ChatMember(Socket socket,
                      Server server,
                      MessageStreamType type) throws IOException {
        messageHandler = new ServerMessageHandler(server, this);
        connection = new ServerMediator(socket, messageHandler, type, this);
        connection.listen();
    }

    public void sendMessage(ServerMessage msg) throws InterruptedException {
        connection.sendMessage(msg);
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

}
