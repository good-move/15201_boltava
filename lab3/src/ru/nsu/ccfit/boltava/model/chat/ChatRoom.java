package ru.nsu.ccfit.boltava.model.chat;

import ru.nsu.ccfit.boltava.model.Connection;

import java.util.HashSet;

public abstract class ChatRoom {

    private int mID;
    protected HashSet<Connection> mConnections;
    protected ChatRoomType mType;
    // add blocking message queue?

    public ChatRoom(HashSet<Connection> chatConnections) {
        mConnections = chatConnections;
    }

    public enum ChatRoomType {
        Dialog,
        Conference
    }

}
