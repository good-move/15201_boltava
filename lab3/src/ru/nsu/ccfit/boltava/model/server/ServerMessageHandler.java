package ru.nsu.ccfit.boltava.model.server;

import ru.nsu.ccfit.boltava.model.message.*;
import ru.nsu.ccfit.boltava.model.message.types.GetUserList;
import ru.nsu.ccfit.boltava.model.message.types.Login;
import ru.nsu.ccfit.boltava.model.message.types.Logout;
import ru.nsu.ccfit.boltava.model.message.types.SendTextMessage;

public class ServerMessageHandler implements IServerMessageHandler {

    public ServerMessageHandler() {};

    public ServerMessageHandler(Server server) {};

    @Override
    public void handle(Login msg) {
        System.out.println("Login request from " + msg.getUsername());
    }

    @Override
    public void handle(Logout msg) {
        System.out.println("Logout request from " + msg.getUsername());
    }

    @Override
    public void handle(SendTextMessage msg) {
        System.out.println("New message is sent");
    }

    @Override
    public void handle(GetUserList msg) {
        System.out.println("User list queried");
    }

}
