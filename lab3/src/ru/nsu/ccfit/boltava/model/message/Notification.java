package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;

public abstract class Notification extends ServerMessage {

    public Notification(String sessionId) {
        super(sessionId);
    }

    public abstract void handle(IClientMessageHandler handler);

}
