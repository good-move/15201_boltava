package ru.nsu.ccfit.boltava.model.chat;

import ru.nsu.ccfit.boltava.model.Connection;
import ru.nsu.ccfit.boltava.model.message.IMessageHandler;
import ru.nsu.ccfit.boltava.model.message.ISocketMessageStream.MessageStreamType;
import ru.nsu.ccfit.boltava.model.message.Message;

import java.io.IOException;
import java.net.Socket;

public class ChatMember {

    private User mUser;
    private Connection mConnection;
    private Status mStatus;

    public ChatMember(Socket socket,
                      IMessageHandler handler,
                      MessageStreamType type) throws IOException {
        mConnection = new Connection(socket, handler, type);
        mConnection.listen();
        mStatus = Status.Pending;
    }

    public void setStatus(ChatMember.Status status) {
        mStatus = status;
    }

    public void sendMessage(Message msg) throws InterruptedException {
        mConnection.sendMessage(msg);
    }

    public enum Status {
        LoggedIn, // a member who's connected, got an id and a username, and can operate in chat
        Pending, // a member who's connected, but has no username and cannot operate in chat
        Disconnected // no connection with client is sustained and member can be deleted from server
    }

}
