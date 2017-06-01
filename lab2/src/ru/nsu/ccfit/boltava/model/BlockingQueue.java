package ru.nsu.ccfit.boltava.model;

import java.util.ArrayList;

public class BlockingQueue<ItemType> {

    private final long mMaxSize;
    private ArrayList<ItemType> mQueue = new ArrayList<>();
    private final Object lock = new Object();

    public BlockingQueue(long maxSize) {
        mMaxSize = maxSize;
    }

    public void enqueue(ItemType element) throws InterruptedException {
        synchronized (lock) {
            while (mQueue.size() == mMaxSize) {
                lock.wait();
            }
            mQueue.add(element);
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
            lock.notifyAll();
            return item;
        }
    }

    public long getSize() {
        return mQueue.size();
    }

}
