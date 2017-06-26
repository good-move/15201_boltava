package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "success")
@XmlType (
        factoryClass = MessageFactory.class,
        factoryMethod = "getLoginSuccess"
)
public class LoginSuccess extends SuccessResponse {

    @XmlElement (name = "session")
    private String sessionId;

    public LoginSuccess(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
