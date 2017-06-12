package ru.nsu.ccfit.boltava.model.message;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectSocketMessageStream implements ISocketMessageStream {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ObjectSocketMessageStream(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public Message read() throws IOException, ClassNotFoundException, EOFException {
        System.out.println("Reading object");
        return (Message) in.readObject();
    }

    @Override
    public void write(Message msg) throws IOException {
        System.out.println("Writing object");

        out.writeObject(msg);
    }

}
