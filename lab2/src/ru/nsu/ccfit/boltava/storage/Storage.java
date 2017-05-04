package ru.nsu.ccfit.boltava.storage;

import ru.nsu.ccfit.boltava.BlockingQueue;
import ru.nsu.ccfit.boltava.IObservable;
import ru.nsu.ccfit.boltava.ISubscriber;

import java.util.HashSet;

public class Storage<ItemType> {

    private BlockingQueue<ItemType> mStorage;

    public Storage(long size) {
        mStorage = new BlockingQueue<ItemType>(size);
    }

    public void put(ItemType item) throws InterruptedException {
        mStorage.enqueue(item);
    }

    public ItemType get() throws InterruptedException {
        return mStorage.dequeue();
    }

    public long getItemsCount() {
        return mStorage.getSize();
    }

}
