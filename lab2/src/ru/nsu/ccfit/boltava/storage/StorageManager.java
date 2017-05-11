package ru.nsu.ccfit.boltava.storage;

import ru.nsu.ccfit.boltava.IObservable;
import ru.nsu.ccfit.boltava.ISubscriber;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class StorageManager<ItemType> implements IObservable {

    private HashMap<String, Storage<ItemType>> mItemStorageMap = new HashMap<>();
    private HashSet<ISubscriber> mSubscribers = new HashSet<>();

    public StorageManager(List<String> itemSerials, int maxStorageSize) {
        itemSerials.forEach(serial -> mItemStorageMap.put(serial, new Storage<>(maxStorageSize)));
    }

    public Storage<ItemType> getStorage(String componentSerial) throws InterruptedException {
        return mItemStorageMap.get(componentSerial);
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
