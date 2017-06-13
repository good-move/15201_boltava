package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Message;

import java.io.IOException;
import java.net.Socket;

public class XMLSocketMessageStream implements ISocketMessageStream {



    public XMLSocketMessageStream(Socket socket) {
    }

    @Override
    public void write(Message msg) throws IOException {

    }

    @Override
    public Message read() throws IOException, ClassNotFoundException {
        return null;
    }

}
