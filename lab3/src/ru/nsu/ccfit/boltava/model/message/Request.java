package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

public abstract class Request extends Message {

    private final String mSender;

    public Request(String username) {
        mSender = username;
    }

    public String getUsername() {
        return mSender;
    }

    public abstract void handle(IServerMessageHandler handler);

}
