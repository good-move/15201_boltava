package ru.nsu.ccfit.boltava.storage;

import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.factory.ICarPurchasedListener;

import java.util.ArrayList;

public class CarStorageManager extends StorageManager<Car> {

    private ArrayList<ICarPurchasedListener> mCarPurchasedListeners = new ArrayList<>();

    public CarStorageManager(String[] carModels, int maxStorageSize) {
        super(carModels, maxStorageSize);
    }

    public void checkOut(Car car) {
        mCarPurchasedListeners.forEach(listener -> listener.onCarPurchased(car));
    }

    public void subscribe(ICarPurchasedListener listener) {
        mCarPurchasedListeners.add(listener);
    }


}
