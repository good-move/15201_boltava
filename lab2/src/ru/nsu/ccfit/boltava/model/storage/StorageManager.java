package ru.nsu.ccfit.boltava.model.storage;

import ru.nsu.ccfit.boltava.model.car.Component;
import ru.nsu.ccfit.boltava.view.IOnValueChangedForKeyListener;
import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StorageManager<ItemType extends Component> implements IOnItemPutListener, Storage.IOnSizeChangedListener {

    private HashMap<String, Storage<ItemType>> mItemStorageMap = new HashMap<>();
    private HashMap<Storage<? extends Component>, String> mStorageItemMap = new HashMap<>();
    private ArrayList<IOnValueChangedForKeyListener<String, Integer>> mStorageLoadObservers = new ArrayList<>();
    private ArrayList<IOnValueChangedListener<Integer>> mOnItemPutListeners = new ArrayList<>();
    private int mItemsSupplied = 0;

    public StorageManager(List<String> itemSerials, int maxStorageSize) {
        itemSerials.forEach(serial -> mItemStorageMap.put(serial, new Storage<>(maxStorageSize)));
        mItemStorageMap.values().forEach(storage -> {
            storage.addOnItemPutListener(this);
            storage.addOnSizeChangedListener(this);
        });
    }

    public Storage<ItemType> getStorage(String componentSerial) throws InterruptedException, NoSuchStorageException {
        Storage<ItemType> storage = mItemStorageMap.get(componentSerial);
        if (storage == null) {
            throw new NoSuchStorageException(String.format("No storage for component with serial %s", componentSerial));
        }

        return storage;
    }

    public void addStorageLoadObserver(IOnValueChangedForKeyListener<String, Integer> listener) {
        mStorageLoadObservers.add(listener);
    }

    public void removeStorageLoadObserver(IOnValueChangedForKeyListener<String, Integer> listener) {
        mStorageLoadObservers.remove(listener);
    }

    public void addOnItemPutListener(IOnValueChangedListener<Integer> listener) {
        mOnItemPutListeners.add(listener);
    }

    public void removeOnItemPutListener(IOnValueChangedListener<Integer> listener) {
        mOnItemPutListeners.remove(listener);
    }

    private synchronized void incrementItemsSupplied() {
        mItemsSupplied++;
    }

    @Override
    public synchronized void onItemPut() {
        incrementItemsSupplied();
        mOnItemPutListeners.forEach(listener -> listener.onValueChanged(mItemsSupplied));
    }

    @Override
    public void onSizeChanged(Storage<? extends Component> storage) {
        String serial;
        if (mStorageItemMap.containsKey(storage)) {
            serial = mStorageItemMap.get(storage);
        } else {
            serial = mItemStorageMap
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().equals(storage))
                    .findFirst()
                    .get()
                    .getKey();

            mStorageItemMap.put(storage, serial);
        }

        mStorageLoadObservers.forEach(observer -> observer.onValueChangedForKey(serial, storage.getSize()));
    }

    public static class NoSuchStorageException extends Exception {

        public NoSuchStorageException(){}

        public NoSuchStorageException(String msg) {
            super(msg);
        }

    }

}
