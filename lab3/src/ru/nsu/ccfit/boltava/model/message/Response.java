package ru.nsu.ccfit.boltava.model.message;


import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;

public abstract class Response extends Message {

    public abstract void handle(IClientMessageHandler handler);

}
