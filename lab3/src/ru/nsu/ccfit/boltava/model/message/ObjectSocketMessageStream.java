package ru.nsu.ccfit.boltava.model.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocketMessageStream implements ISocketMessageStream {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ObjectSocketMessageStream(Socket socket) throws IOException {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public Message read() throws IOException, ClassNotFoundException {
        return (Message) in.readObject();
    }

    @Override
    public void write(Message msg) throws IOException {
        out.writeObject(msg);
    }

}
