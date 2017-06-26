package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

public class ClientXMLStream extends AbstractXMLStream<Request, ServerMessage> implements IClientSocketMessageStream {

    public ClientXMLStream(Socket socket, IMessageSerializer<String> serializer) throws IOException {
        super(socket, serializer);
    }
}
