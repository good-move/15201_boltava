package ru.nsu.ccfit.boltava.model.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.factory.AssemblyManager;
import ru.nsu.ccfit.boltava.view.IOnValueChangedForKeyListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CarStorageManager extends StorageManager<Car> {

    private HashSet<IOnValueChangedForKeyListener<String, Integer>> mCarItemSalesListeners = new HashSet<>();
    private HashMap<String, Integer> mCarSalesStats = new HashMap<>();
    private AssemblyManager mAssemblyManager;

    private static final String CAR_SALES_LOGGER = "CarSalesLogger";
    private static final Logger carSalesLogger = LogManager.getLogger(CAR_SALES_LOGGER);

    public CarStorageManager(List<String> carModels, int maxStorageSize) {
        super(carModels, maxStorageSize);
        carModels.forEach(model -> mCarSalesStats.put(model,0));
    }

    public synchronized void attachAssemblyManager(AssemblyManager assemblyManager) {
        if (assemblyManager == null)
            throw new IllegalArgumentException("Assembly Manager can't be null");

        if (mAssemblyManager != null)
            throw new IllegalStateException("Assembly Manager is already attached");

        mAssemblyManager = assemblyManager;
    }

    public void orderCar(String carSerial) throws IllegalArgumentException, InterruptedException {
        mAssemblyManager.orderCar(carSerial);
    }

    public void checkOut(Car car) throws InterruptedException {
        carSalesLogger.trace(String.format("Bought a car! ID: %d, serial: %s", car.getId(), car.getSerial()));

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
