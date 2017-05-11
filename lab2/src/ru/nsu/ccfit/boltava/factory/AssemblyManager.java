package ru.nsu.ccfit.boltava.factory;

import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.car.CarDescription;
import ru.nsu.ccfit.boltava.storage.CarStorageManager;

import java.util.HashMap;
import java.util.List;

public class AssemblyManager implements ICarPurchasedListener {

    private final Assembly mAssembly;
    private HashMap<String, CarDescription> mCarDescriptions = new HashMap<>();

    public AssemblyManager(Assembly assembly, HashMap<String, CarDescription> carDescriptions) {
        mAssembly = assembly;
        mCarDescriptions = carDescriptions;
    }

    public void setInitialOrders(List<String> carSerials) {
        carSerials.forEach(this::assignTask);
    }

    public void onCarPurchased(Car car) {
        assignTask(car.getSerial());
    }

    private void assignTask(String carSerial)  {
        System.out.println(this.getClass().getSimpleName() + ": creating car");
        CarDescription carDescription  = mCarDescriptions.get(carSerial);
        if (carDescription == null) {
            String template = "%s: don't know how to create car with serial %s";
            System.out.println(String.format(template, this.getClass().getSimpleName(), carSerial));
        }
        else {
            try {
                mAssembly.createCar(carDescription);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
