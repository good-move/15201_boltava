package ru.nsu.ccfit.boltava.model.message;

public class GetUserListSuccess extends Request {

    public GetUserListSuccess(String username) {
        super(username);
    }

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
