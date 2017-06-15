package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;

import java.util.List;

public class GetUserListSuccess extends Response {

    private List<String> onlineUsers;

    public GetUserListSuccess(String sessionId, List<String> onlineUsers) {
        super(sessionId);
        this.onlineUsers = onlineUsers;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
