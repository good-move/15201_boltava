package ru.nsu.ccfit.boltava.model.message;

import java.io.Serializable;

public abstract class Message implements Serializable {

    protected String sessionId;

    public void setSessionId(String id) {
        sessionId = id;
    }

    public String getSessionId() {
        return sessionId;
    }
}
