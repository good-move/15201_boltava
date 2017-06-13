package ru.nsu.ccfit.boltava.model.chat;

import ru.nsu.ccfit.boltava.model.net.Connection;

import java.util.HashSet;

public class Conference extends ChatRoom {

    public Conference(HashSet<Connection> connections) {
        super(connections);
    }

    // change connection to connection id ???
    public void addMember(Connection connection) {
        mConnections.add(connection);
    }

    public void removeMember(Connection connection) {

    }

}
