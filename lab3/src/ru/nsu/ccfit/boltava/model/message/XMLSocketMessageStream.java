package ru.nsu.ccfit.boltava.model.message;

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
