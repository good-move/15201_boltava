package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getErrorResponse"
)
public class ErrorResponse extends Response {

    protected final int mErrorCode;
    protected final String mErrorMessage;

    public ErrorResponse(String msg, int code) {
        mErrorCode = code;
        mErrorMessage = msg;
    }

    @XmlElement (name = "code")
    public int getErrorCode() {
        return mErrorCode;
    }

    @XmlElement (name = "message")
    public String getErrorMessage() {
        return mErrorMessage;
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
