package ru.nsu.ccfit.boltava;

public interface IObservable {

    void subscribe(ISubscriber subscriber);
    void unsubscribe(ISubscriber subscriber);
    void updateSubscribers();

}
