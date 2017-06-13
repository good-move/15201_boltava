package ru.nsu.ccfit.boltava.model.chat;

import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.server.ServerMediator;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream.MessageStreamType;

import java.io.IOException;
import java.net.Socket;

public class ChatMember {

    private User mUser;
    private ServerMediator mConnection;

    public ChatMember(Socket socket,
                      IServerMessageHandler handler,
                      MessageStreamType type) throws IOException {
        mConnection = new ServerMediator(socket, handler, type);
        mConnection.listen();
    }

    public void sendMessage(ServerMessage msg) throws InterruptedException {
        mConnection.sendMessage(msg);
    }

}
