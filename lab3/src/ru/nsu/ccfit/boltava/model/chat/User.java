package ru.nsu.ccfit.boltava.model.chat;

import java.io.Serializable;

public class User implements Serializable {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return mID == user.mID && mUsername.equals(user.mUsername);
    }

    @Override
    public int hashCode() {
        int result = mID;
        result = 31 * result + mUsername.hashCode();
        return result;
    }
}
