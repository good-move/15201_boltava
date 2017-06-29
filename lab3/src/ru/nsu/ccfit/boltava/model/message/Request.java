package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

import javax.xml.bind.annotation.XmlElement;

public abstract class Request extends Message {

    protected String sessionId;

    public Request(String sessionId) {
        this.sessionId = sessionId;
    }

    @XmlElement (name = "session")
    public void setSessionId(String id) {
        sessionId = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public abstract void handle(IServerMessageHandler handler) throws InterruptedException;

}
