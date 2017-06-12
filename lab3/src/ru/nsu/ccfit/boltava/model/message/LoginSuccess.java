package ru.nsu.ccfit.boltava.model.message;

public class LoginSuccess extends Response {

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
