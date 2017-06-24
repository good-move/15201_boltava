package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getLoginError"
)
public class LoginError extends ErrorResponse {

    public LoginError(String msg, int code) {
        super(msg, code);
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

    @Override
    public String toString() {
        return String.format("[Error] Code: %d. Msg: %s", mErrorCode, mErrorMessage);
    }

}
