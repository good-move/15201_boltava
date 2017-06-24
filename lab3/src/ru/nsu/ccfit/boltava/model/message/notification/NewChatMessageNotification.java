package ru.nsu.ccfit.boltava.model.message.notification;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Notification;
import ru.nsu.ccfit.boltava.model.message.message_content.ChatMessage;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getNewTextMessage"
)
public class NewChatMessageNotification extends Notification {

    private String sender;
    private ChatMessage content;

    public NewChatMessageNotification(ChatMessage content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    @XmlElement(name = "name")
    public String getSender() {
        return sender;
    }

    public ChatMessage getContent() {
        return content;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
