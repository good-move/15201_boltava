package ru.nsu.ccfit.boltava.model.net;

import java.io.IOException;
import java.net.Socket;

public class ClientMessageStreamFactory {

    public static IClientSocketMessageStream get(ISocketMessageStream.MessageStreamType type, Socket socket) throws IOException {
        switch (type) {
            case OBJ: return new ClientObjectStream(socket);
            case XML:return new ClientXMLStream(socket);
            default: throw new RuntimeException("Unknown stream type");
        }
    }


}
