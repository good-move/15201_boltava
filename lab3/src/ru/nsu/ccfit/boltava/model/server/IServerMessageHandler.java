package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.message.request.GetUserListRequest;
import ru.nsu.ccfit.boltava.model.message.request.LoginRequest;
import ru.nsu.ccfit.boltava.model.message.request.LogoutRequest;
import ru.nsu.ccfit.boltava.model.message.request.PostTextMessageRequest;

public interface IServerMessageHandler {

    void handle(LoginRequest msg) throws InterruptedException;
    void handle(LogoutRequest msg) throws InterruptedException;
    void handle(PostTextMessageRequest msg) throws InterruptedException;
    void handle(GetUserListRequest msg) throws InterruptedException;

}
