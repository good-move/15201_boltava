package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.message.event.NewTextMessageEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;
import ru.nsu.ccfit.boltava.model.message.response.*;

public interface IClientMessageHandler {

    void handle(LoginSuccess msg);
    void handle(LoginError msg);
    void handle(GetUserListSuccess msg);
    void handle(GetUserListError msg);
    void handle(PostTextMessageSuccess msg);
    void handle(PostTextMessageError msg);
    void handle(LogoutSuccess msg);
    void handle(LogoutError msg);

    void handle(NewTextMessageEvent msg);
    void handle(UserJoinedChatEvent msg);
    void handle(UserLeftChatEvent msg);
}
