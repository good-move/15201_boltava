package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;

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
    public ServerMessage read() throws StreamReadException {
        try {
            return (ServerMessage) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StreamReadException(e.getMessage());
        }
    }

    @Override
    public void write(Request msg) throws StreamWriteException {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            throw new StreamWriteException(e.getMessage());
        }
    }

}
