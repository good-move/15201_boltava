package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.message.notification.NewChatMessageNotification;
import ru.nsu.ccfit.boltava.model.message.notification.UserJoinedChat;
import ru.nsu.ccfit.boltava.model.message.notification.UserLeftChat;
import ru.nsu.ccfit.boltava.model.message.response.*;

public interface IClientMessageHandler {

    void handle(ErrorResponse msg);
    void handle(SuccessResponse msg);
    void handle(LoginSuccess msg);
    void handle(GetUserListSuccess msg);
    void handle(NewChatMessageNotification msg);
    void handle(UserJoinedChat msg);
    void handle(UserLeftChat msg);
    void handle(LoginError msg);
}
