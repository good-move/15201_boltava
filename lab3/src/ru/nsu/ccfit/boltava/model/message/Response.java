package ru.nsu.ccfit.boltava.model.message;


import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;

public abstract class Response extends ServerMessage {

    public Response() {}

    public abstract void handle(IClientMessageHandler handler);

}
