package ru.nsu.ccfit.boltava.factory;

import ru.nsu.ccfit.boltava.ISubscriber;
import ru.nsu.ccfit.boltava.car.CarDescription;
import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.storage.StorageManager;

import java.util.HashMap;

public class AssemblyManager implements ISubscriber {

    private final Assembly mAssembly;
    private HashMap<String, CarDescription> mCarDescriptions = new HashMap<>();

    AssemblyManager(StorageManager<Car> carStorageManager, Assembly assembly) {
        mAssembly = assembly;
        carStorageManager.subscribe(this);
    }

    public void addCarDescription(CarDescription carDescription) {
        mCarDescriptions.put(carDescription.getCarSerial(), carDescription);
    }

    @Override
    public void update() {}


    public void update(String carSerial) {
        try {
            mAssembly.createCar(mCarDescriptions.get(carSerial));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
