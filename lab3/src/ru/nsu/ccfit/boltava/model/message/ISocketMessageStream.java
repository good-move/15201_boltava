package ru.nsu.ccfit.boltava.model.message;

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
