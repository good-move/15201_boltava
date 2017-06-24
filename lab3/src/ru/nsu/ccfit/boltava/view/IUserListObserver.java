package ru.nsu.ccfit.boltava.view;

public interface IUserListObserver {

    void onUserJoined(String username);
    void onUserLeft(String username);

}
