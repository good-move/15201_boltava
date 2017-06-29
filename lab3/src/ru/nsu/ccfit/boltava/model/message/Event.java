package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;

public abstract class Event extends ServerMessage {

    public Event() {}

    public abstract void handle(IClientMessageHandler handler);

}
