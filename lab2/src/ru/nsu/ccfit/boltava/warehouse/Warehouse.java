package ru.nsu.ccfit.boltava.warehouse;

import ru.nsu.ccfit.boltava.BlockingQueue;

public class Warehouse <ItemType> {

    private BlockingQueue<ItemType> mWarehouse;

    public Warehouse(long size) {
        mWarehouse = new BlockingQueue<ItemType>(size);
    }

    public void put(ItemType item) throws InterruptedException {
        mWarehouse.enqueue(item);
    }

    public ItemType get() throws InterruptedException {
        return mWarehouse.dequeue();
    }

}
