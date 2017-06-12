package ru.nsu.ccfit.boltava.model.chat;

public class User {

    private int mID;
    private String mUsername;

    public User(String username) {
        mUsername = username;
    }

    public int getID() {
        return mID;
    }

    public String getUsername() {
        return mUsername;
    }

}
