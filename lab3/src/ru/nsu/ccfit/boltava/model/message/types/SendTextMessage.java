package ru.nsu.ccfit.boltava.model.message.types;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

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
    public void handle(IServerMessageHandler messageHandler) {
        messageHandler.handle(this);
    }
}
