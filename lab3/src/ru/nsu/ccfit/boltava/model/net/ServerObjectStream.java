package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;

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
    public Request read() throws StreamReadException, IMessageSerializer.MessageSerializationException {
        try {
            return (Request) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new StreamReadException(e.getMessage(), e);
        }
    }

    @Override
    public void write(ServerMessage msg) throws StreamWriteException, IMessageSerializer.MessageSerializationException {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            throw new StreamWriteException(e.getMessage(), e);
        }
    }

}
