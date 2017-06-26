package ru.nsu.ccfit.boltava.model.message.request;

import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement (name = "list_command")
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getUserListRequest"
)
public class GetUserListRequest extends Request {

    public GetUserListRequest(String sessionId) {
        super(sessionId);
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }

}
