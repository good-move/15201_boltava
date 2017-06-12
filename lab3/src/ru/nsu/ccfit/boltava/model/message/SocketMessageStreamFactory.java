package ru.nsu.ccfit.boltava.model.message;

import java.io.IOException;
import java.net.Socket;

public class SocketMessageStreamFactory {

    public static ISocketMessageStream get(ISocketMessageStream.MessageStreamType type, Socket socket) throws IOException {
        switch (type) {
            case Object: return new ObjectSocketMessageStream(socket);
            case XML:return new XMLSocketMessageStream(socket);
            default: throw new RuntimeException("");
        }
    }


}
