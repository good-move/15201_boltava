package ru.nsu.ccfit.boltava.model.message.notification;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Notification;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getNewTextMessage"
)
public class NewTextMessageNotification extends Notification {

    private String sender;
    private String message;

    public NewTextMessageNotification(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    @XmlElement(name = "name")
    public String getSender() {
        return sender;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
