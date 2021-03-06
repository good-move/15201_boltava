package ru.nsu.ccfit.boltava.model.message.event;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Event;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.view.IChatMessageRenderer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement (name = "userlogin_event")
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getUserJoinedChat"
)
public class UserJoinedChatEvent extends Event {

    @XmlElement(name = "name")
    private String username;

    public UserJoinedChatEvent(String username) {
        this.username = username;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

    public String getUsername() {
        return username;
    }

    public void render(IChatMessageRenderer renderer) {
        renderer.render(this);
    }

}
