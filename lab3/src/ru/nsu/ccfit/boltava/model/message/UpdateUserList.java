package ru.nsu.ccfit.boltava.model.message;

public class UpdateUserList extends Notification {

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
