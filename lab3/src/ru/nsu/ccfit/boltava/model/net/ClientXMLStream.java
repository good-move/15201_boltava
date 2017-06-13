package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Message;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;

import java.io.IOException;
import java.net.Socket;

public class ClientXMLStream implements IClientSocketMessageStream {



    public ClientXMLStream(Socket socket) {
    }

    @Override
    public void write(Request msg) throws IOException {

    }

    @Override
    public ServerMessage read() throws IOException, ClassNotFoundException {
        return null;
    }

}
