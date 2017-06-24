package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getUserListSuccess"
)
public class GetUserListSuccess extends Response {

    private List<String> onlineUsers;

    public GetUserListSuccess(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    @XmlElementWrapper(name = "userlist")
    @XmlElement(name = "user")
    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
