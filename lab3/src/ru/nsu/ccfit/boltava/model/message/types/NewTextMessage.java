package ru.nsu.ccfit.boltava.model.message.types;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Notification;

public class NewTextMessage extends Notification {

    private String mSender;
    private String mMessageText;

    public NewTextMessage(String msg, String sender) {
        mMessageText = msg;
        mSender = sender;
    }

    public String getSender() {
        return mSender;
    }

    public String getMessageText() {
        return mMessageText;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
