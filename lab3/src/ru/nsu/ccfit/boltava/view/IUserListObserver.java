package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.chat.User;

import java.util.List;

public interface IUserListObserver {

    void onUserListSet(List<User> users);
    void onUserJoined(String username);
    void onUserLeft(String username);


}
