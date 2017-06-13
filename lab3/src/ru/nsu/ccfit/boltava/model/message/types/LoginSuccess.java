package ru.nsu.ccfit.boltava.model.message.types;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;

public class LoginSuccess extends Response {

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
