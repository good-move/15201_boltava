package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.message.*;
import ru.nsu.ccfit.boltava.model.message.types.GetUserList;
import ru.nsu.ccfit.boltava.model.message.types.Login;
import ru.nsu.ccfit.boltava.model.message.types.Logout;
import ru.nsu.ccfit.boltava.model.message.types.SendTextMessage;

public class ServerMessageHandler implements IServerMessageHandler {

    @Override
    public void handle(Request msg) {
        System.err.println("Unknown request from " + msg.getUsername());
    }

    @Override
    public void handle(Login msg) {
        System.out.println("Login request from " + msg.getUsername());
    }

    @Override
    public void handle(Logout msg) {

    }

    @Override
    public void handle(SendTextMessage msg) {

    }

    @Override
    public void handle(GetUserList msg) {

    }

    @Override
    public void handle(Message msg) {
        System.err.println("Super generic is called");
    }

}
