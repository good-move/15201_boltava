package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;

public class ErrorResponse extends Response {

    protected final int mErrorCode;
    protected final String mErrorMessage;

    public ErrorResponse(String sessionId, String msg, int code) {
        super(sessionId);
        mErrorCode = code;
        mErrorMessage = msg;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

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
