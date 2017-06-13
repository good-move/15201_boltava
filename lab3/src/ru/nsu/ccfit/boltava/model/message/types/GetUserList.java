package ru.nsu.ccfit.boltava.model.message.types;

import ru.nsu.ccfit.boltava.model.server.ChatMember;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

public class GetUserList extends Request {

    public GetUserList(String username) {
        super(username);
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }

}
