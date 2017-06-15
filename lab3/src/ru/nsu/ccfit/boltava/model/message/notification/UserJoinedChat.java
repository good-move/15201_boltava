package ru.nsu.ccfit.boltava.model.message.notification;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Notification;

public class UserJoinedChat extends Notification {

    private User user;

    public UserJoinedChat(String sessionId, User user) {
        super(sessionId);
        this.user = user;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

    public User getUser() {
        return user;
    }

}
