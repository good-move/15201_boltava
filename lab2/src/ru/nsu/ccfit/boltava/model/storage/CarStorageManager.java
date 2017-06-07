package ru.nsu.ccfit.boltava.model.storage;

import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.factory.AssemblyManager;
import ru.nsu.ccfit.boltava.model.factory.ICarPurchasedListener;

import java.beans.IntrospectionException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CarStorageManager extends StorageManager<Car> {

    private HashSet<ICarPurchasedListener> mCarPurchasedListeners = new HashSet<>();
    private HashMap<String, Integer> mCarSales = new HashMap<>();

    public CarStorageManager(List<String> carModels, int maxStorageSize) {
        super(carModels, maxStorageSize);
        carModels.forEach(model -> mCarSales.put(model,0));
    }

    public void checkOut(Car car) {
        mCarPurchasedListeners.forEach(listener -> listener.onCarPurchased(car));

        Integer sales = mCarSales.get(car.getSerial());
        sales++;
        mCarSales.put(car.getSerial(), sales);
        mCarPurchasedListeners.forEach(listener -> listener.onCarPurchased(car, mCarSales.get(car.getSerial())));
    }

    public void addCarPurchasedListener(ICarPurchasedListener listener) {
        mCarPurchasedListeners.add(listener);
    }

    public void removeCarPurchasedListener(ICarPurchasedListener listener) {
        mCarPurchasedListeners.remove(listener);
    }

}
