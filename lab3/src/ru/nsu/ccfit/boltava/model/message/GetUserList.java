package ru.nsu.ccfit.boltava.model.message;

public class GetUserList extends Request {

    public GetUserList(String username) {
        super(username);
    }

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
