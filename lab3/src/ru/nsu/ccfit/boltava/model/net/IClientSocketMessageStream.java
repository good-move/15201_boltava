package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;

public interface IClientSocketMessageStream extends ISocketMessageStream<Request, ServerMessage> {

    ServerMessage read() throws StreamReadException, IMessageSerializer.MessageSerializationException;
    void write(Request msg) throws StreamWriteException, IMessageSerializer.MessageSerializationException;

}
