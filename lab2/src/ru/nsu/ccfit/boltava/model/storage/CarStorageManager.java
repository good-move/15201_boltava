package ru.nsu.ccfit.boltava.model.storage;

import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.factory.AssemblyManager;
import ru.nsu.ccfit.boltava.model.factory.ICarPurchasedListener;

import java.util.HashSet;
import java.util.List;

public class CarStorageManager extends StorageManager<Car> {

    private HashSet<ICarPurchasedListener> mCarPurchasedListeners = new HashSet<>();

    public CarStorageManager(List<String> carModels, int maxStorageSize) {
        super(carModels, maxStorageSize);
    }

    public void checkOut(Car car) {
        mCarPurchasedListeners.forEach(listener -> listener.onCarPurchased(car));
    }

    public void addCarPurchasedListener(ICarPurchasedListener listener) {
        mCarPurchasedListeners.add(listener);
    }

    public void removeCarPurchasedListener(ICarPurchasedListener listener) {
        mCarPurchasedListeners.remove(listener);
    }

}