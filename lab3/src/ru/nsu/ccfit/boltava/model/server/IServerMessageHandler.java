package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.message.request.GetUserList;
import ru.nsu.ccfit.boltava.model.message.request.Login;
import ru.nsu.ccfit.boltava.model.message.request.Logout;
import ru.nsu.ccfit.boltava.model.message.request.SendChatMessage;

public interface IServerMessageHandler {

    void handle(Login msg) throws InterruptedException;
    void handle(Logout msg) throws InterruptedException;
    void handle(SendChatMessage msg) throws InterruptedException;
    void handle(GetUserList msg) throws InterruptedException;

}
