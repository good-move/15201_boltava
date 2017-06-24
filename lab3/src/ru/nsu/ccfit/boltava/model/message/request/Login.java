package ru.nsu.ccfit.boltava.model.message.request;

import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getLoginRequest"
)
public class Login extends Request {

    private final String username;
    private final String clientType;

    public Login(String username, String clientType) {
        super("");
        this.username = username;
        this.clientType = clientType;
    }

    @XmlElement(name = "name")
    public String getUsername() {
        return username;
    }

    @XmlElement(name = "type")
    public String getClientType() {
        return clientType;
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }

}
