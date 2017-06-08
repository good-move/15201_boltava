package ru.nsu.ccfit.boltava.model.storage;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.factory.AssemblyManager;
import ru.nsu.ccfit.boltava.view.IOnValueChangedForKeyListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CarStorageManager extends StorageManager<Car> {

    private static Logger rootLogger = LogManager.getLogger(CarStorageManager.class.getName());
    private static Logger carSalesLogger = LogManager.getLogger("CarSalesLogger");

    private HashSet<IOnValueChangedForKeyListener<String, Integer>> mCarItemSalesListeners = new HashSet<>();
    private HashMap<String, Integer> mCarSalesStats = new HashMap<>();
    private AssemblyManager mAssemblyManager;

    public CarStorageManager(List<String> carModels, int maxStorageSize) {
        super(carModels, maxStorageSize);
        carModels.forEach(model -> mCarSalesStats.put(model,0));
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
        carSalesLogger.trace(String.format("Sold a car! ID: %d, serial: %s", car.getId(), car.getSerial()));

        Integer carsSold = mCarSalesStats.get(car.getSerial());
        carsSold++;
        mCarSalesStats.put(car.getSerial(), carsSold);
        mCarItemSalesListeners.forEach(listener -> listener.onValueChangedForKey(car.getSerial(), mCarSalesStats.get(car.getSerial())));
        // blocking call: should be invoked last
        mAssemblyManager.orderCar(car.getSerial());
    }

    public void addCarItemSalesListener(IOnValueChangedForKeyListener<String, Integer> listener) {
        mCarItemSalesListeners.add(listener);
    }

    public void removeCarItemSalesListener(IOnValueChangedForKeyListener<String, Integer> listener) {
        mCarItemSalesListeners.remove(listener);
    }

}
