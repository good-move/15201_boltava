package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.Message;

import java.io.IOException;
import java.util.Arrays;

public interface ISocketMessageStream {

    Message read() throws IOException, ClassNotFoundException;
    void write(Message msg) throws IOException;

    enum MessageStreamType {
        XML,
        OBJ
    }

}
