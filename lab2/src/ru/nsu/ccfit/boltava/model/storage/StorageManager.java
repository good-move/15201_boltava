package ru.nsu.ccfit.boltava.model.storage;

import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StorageManager<ItemType> implements IStorageLoadListener, IOnItemPutListener {

    private HashMap<String, Storage<ItemType>> mItemStorageMap = new HashMap<>();
    private ArrayList<IStorageLoadListener> mStorageLoadListeners = new ArrayList<>();
    private ArrayList<IOnValueChangedListener<Integer>> mOnValueChangedListeners = new ArrayList<>();

    private int mItemsSupplied = 0;

    public StorageManager(List<String> itemSerials, int maxStorageSize) {
        itemSerials.forEach(serial -> mItemStorageMap.put(serial, new Storage<>(maxStorageSize)));
        mItemStorageMap.values().forEach(storage -> storage.addOnItemPutListener(this));
    }

    public Storage<ItemType> getStorage(String componentSerial) throws InterruptedException {
        return mItemStorageMap.get(componentSerial);
    }

    public void addStorageLoadObserver(IStorageLoadListener listener) {
        mStorageLoadListeners.add(listener);
    }

    public void removeStorageLoadObserver(IStorageLoadListener listener) {
        mStorageLoadListeners.remove(listener);
    }

    public void addOnValueChangedListener(IOnValueChangedListener<Integer> listener) {
        mOnValueChangedListeners.add(listener);
    }

    public void removeOnValueChangedListener(IOnValueChangedListener<Integer> listener) {
        mOnValueChangedListeners.remove(listener);
    }

    private void incrementItemsSupplied() {
        mItemsSupplied++;
    }

    @Override
    public void onStorageLoadChanged(Storage<? extends Component> storage) {
//        storage.getItemsCount();
//        mStorageLoadListeners.forEach(listener -> listener.onStorageLoadChanged(itemsCount));
    }

    @Override
    public synchronized void onItemPut() {
        incrementItemsSupplied();
        mOnValueChangedListeners.forEach(listener -> listener.onValueChanged(mItemsSupplied));
    }

}
