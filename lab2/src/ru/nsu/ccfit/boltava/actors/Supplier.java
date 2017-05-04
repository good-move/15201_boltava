package ru.nsu.ccfit.boltava.actors;

import ru.nsu.ccfit.boltava.car.Component;
import ru.nsu.ccfit.boltava.storage.Storage;

public class Supplier<ItemType extends Component> extends SimpleRepeatable implements Runnable {

    private final Class<ItemType> mItemClass;
    private final Storage<ItemType> mStorage;

    public Supplier(Storage<ItemType> storage, Class<ItemType> itemClass) {
        mStorage = storage;
        mItemClass = itemClass;
    }

    public Supplier(Storage<ItemType> storage, Class<ItemType> itemClass, int interval) {
        mStorage = storage;
        mItemClass = itemClass;
        setInterval(interval);
    }

    @Override
    public void run() {
        try {
            while(true) {
                ItemType item = mItemClass.newInstance();
                mStorage.put(item);
                synchronized (this) {
                    wait(getInterval());
                }
            }
        } catch (InterruptedException e) {

        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
    }

}
