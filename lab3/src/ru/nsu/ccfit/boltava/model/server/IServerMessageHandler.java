package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.message.*;
import ru.nsu.ccfit.boltava.model.message.IMessageHandler;
import ru.nsu.ccfit.boltava.model.message.types.GetUserList;
import ru.nsu.ccfit.boltava.model.message.types.Login;
import ru.nsu.ccfit.boltava.model.message.types.Logout;
import ru.nsu.ccfit.boltava.model.message.types.SendTextMessage;

public interface IServerMessageHandler extends IMessageHandler {

    void handle(Login msg);
    void handle(Logout msg);
    void handle(SendTextMessage msg);
    void handle(GetUserList msg);

}
