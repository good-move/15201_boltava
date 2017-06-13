package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.message.*;
import ru.nsu.ccfit.boltava.model.message.types.*;

public class ClientMessageHandler implements IClientMessageHandler {

    @Override
    public void handle(ErrorResponse msg) {
        System.err.println(msg.toString());
    }

    @Override
    public void handle(SuccessResponse msg) {

    }

    @Override
    public void handle(LoginSuccess msg) {

    }

    @Override
    public void handle(GetUserListSuccess msg) {

    }

    @Override
    public void handle(NewTextMessage msg) {
        System.out.println("Got a new message:" + msg.getMessageText());
    }

    @Override
    public void handle(UpdateUserList msg) {

    }

    @Override
    public void handle(Message msg) {
        System.err.println("Super generic is called");
    }

}
