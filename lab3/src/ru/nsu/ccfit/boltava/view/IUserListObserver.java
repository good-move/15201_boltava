package ru.nsu.ccfit.boltava.view;

import java.util.List;

public interface IUserListObserver {

    void onUserListSet(List<String> usernames);
    void onUserJoined(String username);
    void onUserLeft(String username);

}
