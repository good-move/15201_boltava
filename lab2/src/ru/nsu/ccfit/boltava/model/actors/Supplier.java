package ru.nsu.ccfit.boltava.model.actors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.IDGenerator;
import ru.nsu.ccfit.boltava.model.car.Component;
import ru.nsu.ccfit.boltava.model.storage.StorageManager;
import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

import java.lang.reflect.InvocationTargetException;

public class Supplier<ItemType extends Component> extends SimpleRepeatable implements IOnValueChangedListener<Integer> {

    private static final IDGenerator mIDGenerator = new IDGenerator("Suppliers");
    private static Logger logger = LogManager.getLogger(Supplier.class.getName());

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
                mID, mItemClass.getSimpleName(), mItemSerial));
    }

    public Thread getThread() {
        return mThread;
    }

    public String getItemSerial() {
        return mItemSerial;
    }

    @Override
    public void onValueChanged(Integer newInterval) {
        setInterval(newInterval);
    }

    public class SupplierRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    ItemType item = mItemClass.getConstructor(String.class).newInstance(mItemSerial);
                    mStorageManager.getStorage(mItemSerial).put(item);
                    waitInterval();
                }
            } catch (InterruptedException e) {
            } catch (IllegalAccessException |
                    InstantiationException |
                    NoSuchMethodException |
                    InvocationTargetException e) {
                System.err.println(e.getMessage());
            } catch (StorageManager.NoSuchStorageException e) {
                System.err.println(e.getMessage());
                logger.error(String.format("Storage %s<%s> doesn't exist", mItemClass.getSimpleName(), getItemSerial()));
            } finally {
                logger.info("Supplier of " + mItemClass.getSimpleName() + " with serial " + mItemSerial + " finished work");
            }
        }

    }

}
