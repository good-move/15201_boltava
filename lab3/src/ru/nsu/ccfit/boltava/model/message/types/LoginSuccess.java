package ru.nsu.ccfit.boltava.model.message.types;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;

public class LoginSuccess extends Response {

    public LoginSuccess(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
