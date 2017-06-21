package ru.nsu.ccfit.boltava.model.message.notification;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Notification;

public class UserLeftChat extends Notification {

    private String username;

    public UserLeftChat(String username) {
        this.username = username;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

    public String getUsername() {
        return username;
    }

}
