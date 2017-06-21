package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

public abstract class Request extends Message {

    private String id;
    private final String sender;

    public Request(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getId() {
        return id;
    }

    public abstract void handle(IServerMessageHandler handler) throws InterruptedException;

}
