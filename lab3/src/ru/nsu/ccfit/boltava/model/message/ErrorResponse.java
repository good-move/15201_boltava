package ru.nsu.ccfit.boltava.model.message;

public class ErrorResponse extends Response {

    private final int mErrorCode;
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

    @Override
    public void handle(IMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
