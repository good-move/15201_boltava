package ru.nsu.ccfit.boltava;

import ru.nsu.ccfit.boltava.model.message.Message;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.types.GetUserList;
import ru.nsu.ccfit.boltava.model.message.types.Login;
import ru.nsu.ccfit.boltava.model.message.types.SendTextMessage;
import ru.nsu.ccfit.boltava.model.server.ServerMessageHandler;

public class Main {

    // TODO
    // add error checking in serializers and message streams
    // change server connection to accept Requests only (make Mediator Generic)

    public static void handle(Message m) {
        System.out.println("Message");
    }

    public static void handle(Request m) {
        System.out.println("Request");
    }

    public static void handle(Login m) {
        System.out.println("Login");
    }

    public static void main(String[] args) {
	// write your code here


//        Message m = new SendTextMessage("", "");
//        m = (Message) m;
//        ServerMessageHandler h = new ServerMessageHandler();
//        m.handle(h);

    }
}
