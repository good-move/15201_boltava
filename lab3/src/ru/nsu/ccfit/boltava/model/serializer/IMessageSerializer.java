package ru.nsu.ccfit.boltava.model.serializer;

import ru.nsu.ccfit.boltava.model.message.Message;

public interface IMessageSerializer<T> {

    T serialize(Message message) throws MessageSerializationException;
    Message deserialize(T object) throws MessageSerializationException;

    class MessageSerializationException extends Exception {

        MessageSerializationException() {};

        MessageSerializationException(String msg) { super(msg); };

    }

}
