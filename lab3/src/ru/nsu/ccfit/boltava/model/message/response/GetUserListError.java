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

@XmlRootElement (name = "error")
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getUserListError"
)
public class GetUserListError extends ErrorResponse {

    public GetUserListError(String msg, int code) {
        super(msg, code);
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
