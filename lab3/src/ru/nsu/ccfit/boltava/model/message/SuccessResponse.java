package ru.nsu.ccfit.boltava.model.message;

public class SuccessResponse extends Response {

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
