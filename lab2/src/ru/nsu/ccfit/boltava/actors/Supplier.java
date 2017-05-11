package ru.nsu.ccfit.boltava.actors;

import ru.nsu.ccfit.boltava.IDGenerator;
import ru.nsu.ccfit.boltava.car.Component;
import ru.nsu.ccfit.boltava.storage.StorageManager;

import java.lang.reflect.InvocationTargetException;

public class Supplier<ItemType extends Component> extends SimpleRepeatable {

    private static final IDGenerator mIDGenerator = new IDGenerator("Suppliers");
    private final Class<ItemType> mItemClass;
    private final StorageManager<ItemType> mStorageManager;
    private final String mItemSerial;
    private final long mID = mIDGenerator.getId();

    private Thread mThread;

    public Supplier(StorageManager<ItemType> storageManager,
                    Class<ItemType> itemClass,
                    String itemSerial) {
        this(storageManager, itemClass, itemSerial, 3000);
    }

    public Supplier(StorageManager<ItemType> storageManager,
                    Class<ItemType> itemClass,
                    String itemSerial,
                    int interval) {
        mStorageManager = storageManager;
        mItemClass = itemClass;
        mItemSerial = itemSerial;
        setInterval(interval);
        mThread = new Thread(new SupplierRunnable());
        mThread.setName(String.format("Supplier. ID: %d, Component type: %s, Component serial: %s",
                mID, mItemClass, mItemSerial));
    }

    public Thread getThread() {
        return mThread;
    }


    public class SupplierRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    ItemType item = mItemClass.getConstructor(String.class).newInstance(mItemSerial);
                    System.out.println(this.getClass().getSimpleName() + ". Item ID: " + item.getId());
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

}
