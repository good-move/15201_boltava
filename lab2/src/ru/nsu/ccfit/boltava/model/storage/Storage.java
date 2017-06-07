package ru.nsu.ccfit.boltava.model.storage;

import ru.nsu.ccfit.boltava.model.BlockingQueue;
import ru.nsu.ccfit.boltava.model.car.Component;

import java.util.ArrayList;

public class Storage<ItemType extends Component> {

    private BlockingQueue<ItemType> mStorage;
    private ArrayList<IOnItemPutListener> mOnItemPutListeners= new ArrayList<>();
    private ArrayList<IOnSizeChangedListener> mOnSizeChangedListeners = new ArrayList<>();

    public Storage(int size) {
        mStorage = new BlockingQueue<ItemType>(size);
    }

    public void put(ItemType item) throws InterruptedException {
        mStorage.enqueue(item);
        mOnItemPutListeners.forEach(IOnItemPutListener::onItemPut);
        mOnSizeChangedListeners.forEach(listener -> listener.onSizeChanged(this));
    }

    public ItemType get() throws InterruptedException {
        return mStorage.dequeue();
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public int getSize() {
        return mStorage.getSize();
    }

    public void addOnItemPutListener(IOnItemPutListener listener) {
        mOnItemPutListeners.add(listener);
    }

    public void addOnSizeChangedListener(IOnSizeChangedListener listener) {
        mOnSizeChangedListeners.add(listener);
    }

    public interface IOnSizeChangedListener {
        void onSizeChanged(Storage<? extends Component> storage);
    }
}
