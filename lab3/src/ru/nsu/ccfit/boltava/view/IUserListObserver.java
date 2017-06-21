package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.chat.User;

public interface IUserListObserver {

    void onUserJoined(String username);
    void onUserLeft(String username);

}
