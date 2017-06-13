package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Message;

import java.io.IOException;
import java.util.Arrays;

public interface ISocketMessageStream<InMessage extends Message, OutMessage extends Message> {

    InMessage read() throws IOException, ClassNotFoundException;
    void write(OutMessage msg) throws IOException;

    enum MessageStreamType {
        XML,
        OBJ
    }

}
