package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;

import java.io.IOException;
import java.net.Socket;

public class ServerXMLStream implements IServerSocketMessageStream {

    public ServerXMLStream(Socket socket) {
    }

    @Override
    public void write(ServerMessage msg) throws IOException {

    }

    @Override
    public Request read() throws IOException, ClassNotFoundException {
        return null;
    }

}
