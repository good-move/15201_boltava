package ru.nsu.ccfit.boltava.model.message;

public class Login extends Request {

    public Login(String username) {
        super(username);
    }

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
