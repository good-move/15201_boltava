package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.message.Message;

public interface IMessageSerializer<T> {

    public Message serialize(T obj);
    public T serialize(Message msg);

}
