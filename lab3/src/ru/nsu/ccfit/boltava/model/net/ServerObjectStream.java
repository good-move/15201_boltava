package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerObjectStream implements IServerSocketMessageStream {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ServerObjectStream(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public Request read() throws IOException, ClassNotFoundException {
        System.out.println("Reading object");
        return (Request) in.readObject();
    }

    @Override
    public void write(ServerMessage msg) throws IOException {
        System.out.println("Writing object");

        out.writeObject(msg);
    }

}
