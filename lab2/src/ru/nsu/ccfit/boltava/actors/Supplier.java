package ru.nsu.ccfit.boltava.actors;

import ru.nsu.ccfit.boltava.car.Component;
import ru.nsu.ccfit.boltava.storage.StorageManager;

import java.lang.reflect.InvocationTargetException;

public class Supplier<ItemType extends Component> extends SimpleRepeatable implements Runnable {

    private final Class<ItemType> mItemClass;
    private final StorageManager<ItemType> mStorageManager;
    private final String mItemSerial;

    public Supplier(StorageManager<ItemType> storageManager, Class<ItemType> itemClass, String itemSerial) {
        mStorageManager = storageManager;
        mItemClass = itemClass;
        mItemSerial = itemSerial;
    }

    public Supplier(StorageManager<ItemType> storageManager, Class<ItemType> itemClass, String itemSerial, int interval) {
        mStorageManager = storageManager;
        mItemClass = itemClass;
        mItemSerial = itemSerial;
        setInterval(interval);
    }

    @Override
    public void run() {
        try {
            while(true) {
                ItemType item = mItemClass.getConstructor(String.class).newInstance(mItemSerial);
                mStorageManager.getStorage(mItemSerial).put(item);
                synchronized (this) {
                    wait(getInterval());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
