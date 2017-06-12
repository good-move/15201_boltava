package ru.nsu.ccfit.boltava.model.message;

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
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
