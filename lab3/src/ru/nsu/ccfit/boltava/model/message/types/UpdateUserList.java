package ru.nsu.ccfit.boltava.model.message.types;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Notification;

public class UpdateUserList extends Notification {

    private User user;
    private Action action;

    public UpdateUserList(User user, Action action) {
        this.user = user;
        this.action = action;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

    public enum Action {
        Add,
        Remove
    }

}
