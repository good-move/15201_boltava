package ru.nsu.ccfit.boltava.model.net;

import java.io.IOException;
import java.net.Socket;

public class ServerMessageStreamFactory {

    public static IServerSocketMessageStream get(ISocketMessageStream.MessageStreamType type, Socket socket) throws IOException {
        switch (type) {
            case OBJ: return new ServerObjectStream(socket);
            case XML:return new ServerXMLStream(socket);
            default: throw new RuntimeException("Unknown stream type");
        }
    }


}
