package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Message;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.server.Server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientObjectStream implements IClientSocketMessageStream {

    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientObjectStream(Socket socket) throws IOException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public ServerMessage read() throws IOException, ClassNotFoundException {
        return (ServerMessage) in.readObject();
    }

    @Override
    public void write(Request msg) throws IOException {
        out.writeObject(msg);
    }

}
