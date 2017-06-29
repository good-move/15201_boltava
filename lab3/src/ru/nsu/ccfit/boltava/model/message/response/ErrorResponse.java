package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;

import javax.xml.bind.annotation.XmlElement;


public abstract class ErrorResponse extends Response {

    @XmlElement (name = "code")
    private final int mErrorCode;
    @XmlElement (name = "message")
    private final String mErrorMessage;

    public ErrorResponse(String msg, int code) {
        mErrorCode = code;
        mErrorMessage = msg;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public abstract void handle(IClientMessageHandler messageHandler);

    @Override
    public String toString() {
        return String.format("[Error] Code: %d. Msg: %s", mErrorCode, mErrorMessage);
    }

}
