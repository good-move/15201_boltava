package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

import java.util.ArrayList;

public class BlockingQueue<ItemType> {

    private final int mMaxSize;
    private ArrayList<ItemType> mQueue = new ArrayList<>();
    private final Object lock = new Object();
    private ArrayList<IOnValueChangedListener<Integer>> mOnValueChangedListeners = new ArrayList<>();

    public BlockingQueue(int maxSize) {
        mMaxSize = maxSize;
    }

    public void enqueue(ItemType element) throws InterruptedException {
        synchronized (lock) {
            while (mQueue.size() == mMaxSize) {
                lock.wait();
            }
            mQueue.add(element);
            queueSizeChanged();
            lock.notifyAll();
        }
    };

    public ItemType dequeue() throws InterruptedException {
        synchronized (lock) {
            while (mQueue.isEmpty()) {
                lock.wait();
            }
            ItemType item = mQueue.get(0);
            mQueue.remove(0);
            queueSizeChanged();
            lock.notifyAll();
            return item;
        }
    }

    public int getSize() {
        return mQueue.size();
    }

    public void addOnValueChangedListener(IOnValueChangedListener<Integer> listener) {
        mOnValueChangedListeners.add(listener);
    }

    private void queueSizeChanged() {
        synchronized (lock) {
            mOnValueChangedListeners.forEach(observer -> observer.onValueChanged(getSize()));
        }
    }

}
