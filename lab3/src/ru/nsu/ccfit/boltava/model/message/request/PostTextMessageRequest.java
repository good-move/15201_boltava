package ru.nsu.ccfit.boltava.model.message.request;

import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "message_command")
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getSendTextMessageRequest"
)
public class PostTextMessageRequest extends Request {

    @XmlElement(name = "message")
    private String message;

    public PostTextMessageRequest(String sessionId, String message) {
        super(sessionId);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }
}
