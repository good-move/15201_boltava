package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.message.IMessageHandler;
import ru.nsu.ccfit.boltava.model.message.types.*;

public interface IClientMessageHandler extends IMessageHandler {

    void handle(ErrorResponse msg);
    void handle(SuccessResponse msg);
    void handle(LoginSuccess msg);
    void handle(GetUserListSuccess msg);
    void handle(NewTextMessage msg);
    void handle(UpdateUserList msg);

}
