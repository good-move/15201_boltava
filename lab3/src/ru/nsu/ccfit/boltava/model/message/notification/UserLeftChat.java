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
        factoryMethod = "getUserLeftChat"
)
public class UserLeftChat extends Notification {

    private String username;

    public UserLeftChat(String username) {
        this.username = username;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

    @XmlElement(name = "name")
    public String getUsername() {
        return username;
    }

}
