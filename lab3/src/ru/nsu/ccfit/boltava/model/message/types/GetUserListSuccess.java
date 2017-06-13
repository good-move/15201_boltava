package ru.nsu.ccfit.boltava.model.message.types;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;

import java.util.List;

public class GetUserListSuccess extends Response {

    private List<String> onlineUsers;

    public GetUserListSuccess(List<String> onlineUsers) {
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
