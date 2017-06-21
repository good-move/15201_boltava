package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;

import java.io.IOException;

public interface IServerSocketMessageStream extends ISocketMessageStream<Request, ServerMessage> {

    Request read() throws IOException, ClassNotFoundException;
    void write(ServerMessage msg) throws IOException;

}
