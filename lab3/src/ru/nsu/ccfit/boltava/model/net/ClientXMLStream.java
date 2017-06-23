package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.client.Client;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.net.Socket;
import java.nio.charset.Charset;

public class ClientXMLStream implements IClientSocketMessageStream {

    private final IMessageSerializer<String> serializer;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ClientXMLStream(Socket socket, IMessageSerializer<String> serializer) throws JAXBException, IOException {
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        this.serializer = serializer;
    }

    @Override
    public void write(Request msg) throws IOException, JAXBException {
        String result = serializer.serialize(msg);
        out.write(result.getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public ServerMessage read() throws IOException, ClassNotFoundException {
        int messageSize = in.readInt();

        if (messageSize <= 0) {
            throw new IllegalArgumentException("Message size must be a positive integer");
        }

        byte[] bytes = new byte[messageSize];
        int bytesRead = in.read(bytes);

        if (bytesRead != messageSize) {
            throw new RuntimeException("Failed to read whole xml message");
        }

        String xmlString = new String(bytes, Charset.forName("UTF-8"));

        return (ServerMessage) serializer.deserialize(xmlString);
    }

}
