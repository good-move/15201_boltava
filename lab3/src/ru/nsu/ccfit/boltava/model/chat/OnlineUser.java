package ru.nsu.ccfit.boltava.model.chat;

public class OnlineUser {

    private int mID;
    private String mUsername;

    public OnlineUser(String username) {
        mUsername = username;
    }

    public int getID() {
        return mID;
    }

    public String getUsername() {
        return mUsername;
    }

}
