package ru.nsu.ccfit.boltava.model.message;

public class Logout extends Request {

    public Logout(String username) {
        super(username);
    }

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
