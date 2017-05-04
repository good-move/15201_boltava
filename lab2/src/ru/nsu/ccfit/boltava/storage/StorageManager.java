package ru.nsu.ccfit.boltava.storage;

import ru.nsu.ccfit.boltava.IObservable;
import ru.nsu.ccfit.boltava.ISubscriber;

import java.util.HashMap;
import java.util.HashSet;

public class StorageManager<ItemType> implements IObservable {

    private HashMap<String, Storage<ItemType>> mItemStorageMap = new HashMap<>();
    private HashSet<ISubscriber> mSubscribers = new HashSet<>();

    public StorageManager(String[] itemModels, int maxStorageSize) {
        for (String model : itemModels) {
            mItemStorageMap.put(model, new Storage<>(maxStorageSize));
        }
    }

    public ItemType get(String engineSerial) throws InterruptedException {
        return mItemStorageMap.get(engineSerial).get();
    }

    public void put(ItemType item, String serial) throws InterruptedException {
        mItemStorageMap.get(serial).put(item);
    }

    @Override
    public void subscribe(ISubscriber subscriber) {
        mSubscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(ISubscriber subscriber) {
        mSubscribers.remove(subscriber);
    }

    @Override
    public void updateSubscribers() {
        for (ISubscriber subscriber : mSubscribers) {
            subscriber.update();
        }
    }

}
