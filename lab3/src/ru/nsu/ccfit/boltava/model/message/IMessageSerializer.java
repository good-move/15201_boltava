package ru.nsu.ccfit.boltava.model.message;

public interface IMessageSerializer<T> {

    public Message serialize(T obj);
    public T serialize(Message msg);

}
