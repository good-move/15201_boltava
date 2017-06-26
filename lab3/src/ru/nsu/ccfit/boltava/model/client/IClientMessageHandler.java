package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.message.event.NewTextMessageEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;
import ru.nsu.ccfit.boltava.model.message.response.*;

public interface IClientMessageHandler {

    void handle(ErrorResponse msg);
    void handle(SuccessResponse msg);
    void handle(LoginSuccess msg);
    void handle(GetUserListSuccess msg);
    void handle(NewTextMessageEvent msg);
    void handle(UserJoinedChatEvent msg);
    void handle(UserLeftChatEvent msg);
    void handle(LoginError msg);
}
