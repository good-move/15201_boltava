package ru.nsu.ccfit.boltava.factory;

import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.car.CarDescription;
import ru.nsu.ccfit.boltava.storage.CarStorageManager;

import java.util.HashMap;

public class AssemblyManager implements ICarPurchasedListener {

    private final Assembly mAssembly;
    private HashMap<String, CarDescription> mCarDescriptions = new HashMap<>();

    AssemblyManager(CarStorageManager carStorageManager, Assembly assembly) {
        mAssembly = assembly;
        carStorageManager.subscribe(this);
    }

    public void addCarDescription(CarDescription carDescription) {
        mCarDescriptions.put(carDescription.getCarSerial(), carDescription);
    }

    public void onCarPurchased(Car car) {
        try {
            mAssembly.createCar(mCarDescriptions.get(car.getSerial()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
