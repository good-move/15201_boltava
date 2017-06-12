package ru.nsu.ccfit.boltava.model.chat;

import ru.nsu.ccfit.boltava.model.Connection;

import java.util.HashSet;

public class Dialog extends ChatRoom {

    public Dialog(HashSet<Connection> chatConnections) {
        super(chatConnections);
    }

}
