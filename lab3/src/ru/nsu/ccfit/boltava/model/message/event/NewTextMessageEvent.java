package ru.nsu.ccfit.boltava.model.message.event;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Event;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement (name = "message_event")
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getNewTextMessage"
)
public class NewTextMessageEvent extends Event {

    @XmlElement(name = "name")
    private String sender;

    @XmlElement(name = "message")
    private String message;

    public NewTextMessageEvent(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
