package ru.nsu.ccfit.boltava.model.message.request;

import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getUserListRequest"
)
public class GetUserList extends Request {

    public GetUserList(String sessionId) {
        super(sessionId);
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }

}
