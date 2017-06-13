package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

public abstract class Request extends Message {

    private String id;
    private final String username;

    public Request(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public abstract void handle(IServerMessageHandler handler) throws InterruptedException;

}
