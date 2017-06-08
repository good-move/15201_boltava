package ru.nsu.ccfit.boltava.model.storage;

import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.factory.AssemblyManager;
import ru.nsu.ccfit.boltava.view.IOnValueChangedForKeyListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CarStorageManager extends StorageManager<Car> {

    private HashSet<IOnValueChangedForKeyListener<String, Integer>> mCarItemSalesListeners = new HashSet<>();
    private HashMap<String, Integer> mCarSales = new HashMap<>();
    private AssemblyManager mAssemblyManager;

    public CarStorageManager(List<String> carModels, int maxStorageSize) {
        super(carModels, maxStorageSize);
        carModels.forEach(model -> mCarSales.put(model,0));
    }

    public void attachAssemblyManager(AssemblyManager assemblyManager) {
        if (assemblyManager == null)
            throw new IllegalArgumentException("Assembly Manager can't be null");

        if (mAssemblyManager != null)
            throw new IllegalStateException("Assembly Manager is already attached");

        mAssemblyManager = assemblyManager;
    }

    public void detachAssemblyManager() {
        mAssemblyManager = null;
    }

    public void orderCar(String carSerial) throws IllegalArgumentException {
        mAssemblyManager.orderCar(carSerial);
    }

    public void checkOut(Car car) {
        mAssemblyManager.orderCar(car.getSerial());

        Integer sales = mCarSales.get(car.getSerial());
        sales++;
        mCarSales.put(car.getSerial(), sales);
        mCarItemSalesListeners.forEach(listener -> listener.onValueChangedForKey(car.getSerial(), mCarSales.get(car.getSerial())));
    }

    public void addCarItemSalesListener(IOnValueChangedForKeyListener<String, Integer> listener) {
        mCarItemSalesListeners.add(listener);
    }

    public void removeCarItemSalesListener(IOnValueChangedForKeyListener<String, Integer> listener) {
        mCarItemSalesListeners.remove(listener);
    }

}
