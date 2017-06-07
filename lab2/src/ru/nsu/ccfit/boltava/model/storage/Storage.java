package ru.nsu.ccfit.boltava.model.storage;

import ru.nsu.ccfit.boltava.model.BlockingQueue;

import java.util.ArrayList;

public class Storage<ItemType> {

    private BlockingQueue<ItemType> mStorage;
    private ArrayList<IOnItemPutListener> mOnItemPutListeners= new ArrayList<>();

    public Storage(long size) {
        mStorage = new BlockingQueue<ItemType>(size);
    }

    public void put(ItemType item) throws InterruptedException {
        mStorage.enqueue(item);
        mOnItemPutListeners.forEach(IOnItemPutListener::onItemPut);
    }

    public ItemType get() throws InterruptedException {
        return mStorage.dequeue();
    }

    public void addOnItemPutListener(IOnItemPutListener listener) {
        mOnItemPutListeners.add(listener);
    }

}
