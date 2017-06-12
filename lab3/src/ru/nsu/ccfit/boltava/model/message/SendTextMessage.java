package ru.nsu.ccfit.boltava.model.message;

public class SendTextMessage extends Request {

    private String mMessageText;

    public SendTextMessage(String username, String messageText) {
        super(username);
        mMessageText = messageText;
    }

    public String getMessageText() {
        return mMessageText;
    }

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }
}
