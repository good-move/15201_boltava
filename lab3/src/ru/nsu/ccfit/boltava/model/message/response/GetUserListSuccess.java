package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement (name = "success")
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getUserListSuccess"
)
public class GetUserListSuccess extends Response {

    @XmlElementWrapper(name = "userlist")
    @XmlElement(name = "user")
    private List<User> onlineUsers;

    public GetUserListSuccess(List<User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public List<User> getOnlineUsers() {
        return onlineUsers;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
