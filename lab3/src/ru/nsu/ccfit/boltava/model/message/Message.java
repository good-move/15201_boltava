package ru.nsu.ccfit.boltava.model.message;

public abstract class Message {

    public abstract void handle(IMessageHandler messageHandler);

    public enum Status {
        Success,
        Error
    }

}
