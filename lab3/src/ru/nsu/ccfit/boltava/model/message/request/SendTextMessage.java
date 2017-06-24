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
public class SendTextMessage extends Request {

    private String message;

    public SendTextMessage(String user, String message) {
        super(user);
        this.message = message;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }
}
