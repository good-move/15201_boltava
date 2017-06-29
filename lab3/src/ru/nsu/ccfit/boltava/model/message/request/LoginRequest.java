package ru.nsu.ccfit.boltava.model.message.request;

import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "login_command")
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getLoginRequest"
)
public class LoginRequest extends Request {

    @XmlElement(name = "name")
    private final String username;
    @XmlElement(name = "type")
    private final String clientType;

    public LoginRequest(String username, String clientType) {
        super("");
        this.username = username;
        this.clientType = clientType;
    }

    public String getUsername() {
        return username;
    }

    public String getClientType() {
        return clientType;
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }

}
