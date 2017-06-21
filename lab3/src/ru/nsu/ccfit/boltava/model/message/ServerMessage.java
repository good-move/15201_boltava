package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;

public abstract class ServerMessage extends Message {

    public ServerMessage() {}

    public abstract void handle(IClientMessageHandler handler);

}
