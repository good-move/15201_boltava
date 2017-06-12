package ru.nsu.ccfit.boltava.model.message;

public abstract class Request extends Message {

    private final String mSender;

    Request(String username) {
        mSender = username;
    }

    public String getUsername() {
        return mSender;
    }

}
