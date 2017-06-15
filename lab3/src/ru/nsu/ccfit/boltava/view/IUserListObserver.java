package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.chat.User;

public interface IUserListObserver {

    void onUserJoined(User user);
    void onUserLeft(User user);

}
