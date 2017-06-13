package ru.nsu.ccfit.boltava.model.message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    public void handle(IMessageHandler handler) {
        handler.handle(this);
    }

}
