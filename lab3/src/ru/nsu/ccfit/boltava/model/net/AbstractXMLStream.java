package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Message;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

public abstract class AbstractXMLStream<InMessage extends Message, OutMessage extends Message> implements ISocketMessageStream<InMessage, OutMessage> {

    private final IMessageSerializer<String> serializer;
    private final DataInputStream in;
    private final DataOutputStream out;

    public AbstractXMLStream(Socket socket, IMessageSerializer<String> serializer) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.flush();
        in = new DataInputStream(socket.getInputStream());
        this.serializer = serializer;
    }

    @Override
    public void write(InMessage msg) throws StreamWriteException, IMessageSerializer.MessageSerializationException {
        try {
            String result = serializer.serialize(msg);
            byte[] bytes = result.getBytes(Charset.forName("UTF-8"));
            out.writeInt(bytes.length);
            out.write(bytes);
            out.flush();
        } catch (IOException e) {
            throw new StreamWriteException(e.getMessage());
        }
    }

    @Override
    public OutMessage read() throws StreamReadException, IMessageSerializer.MessageSerializationException {
        try {
            int messageSize = in.readInt();

            if (messageSize <= 0) {
                throw new StreamReadException("Message size must be a positive integer");
            }

            byte[] bytes = new byte[messageSize];
            in.readFully(bytes);

            String xmlString = new String(bytes, Charset.forName("UTF-8"));

            return (OutMessage) serializer.deserialize(xmlString);

        } catch (IOException e) {
            throw new StreamReadException(e.getMessage());
        }
    }

}
