package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;

public interface IServerSocketMessageStream extends ISocketMessageStream<ServerMessage, Request> {

    Request read() throws StreamReadException, IMessageSerializer.MessageSerializationException;
    void write(ServerMessage msg) throws StreamWriteException, IMessageSerializer.MessageSerializationException;

}
