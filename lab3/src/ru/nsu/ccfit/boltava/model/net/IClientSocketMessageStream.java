package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;

import java.io.IOException;

public interface IClientSocketMessageStream extends ISocketMessageStream<ServerMessage, Request> {

    ServerMessage read() throws IOException, ClassNotFoundException;
    void write(Request msg) throws IOException;

}
