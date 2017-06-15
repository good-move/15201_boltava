package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;

public class LoginError extends ErrorResponse {

    public LoginError(String sessionId, String msg, int code) {
        super(sessionId, msg, code);
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
