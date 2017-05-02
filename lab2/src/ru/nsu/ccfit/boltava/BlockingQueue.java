package ru.nsu.ccfit.boltava;

import java.util.ArrayList;

public class BlockingQueue<ItemType> {

    private final long mSize;
    private ArrayList<ItemType> mQueue = new ArrayList<>();
    private final Object lock = new Object();

    public BlockingQueue(long size) {
        mSize = size;
    }

    public void enqueue(ItemType element) throws InterruptedException {
        synchronized (lock) {
            while (mQueue.size() == mSize) {
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

}
