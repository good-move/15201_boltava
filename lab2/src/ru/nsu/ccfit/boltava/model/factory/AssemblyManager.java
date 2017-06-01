package ru.nsu.ccfit.boltava.model.factory;

import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.car.CarDescription;

import java.util.HashMap;
import java.util.List;

public class AssemblyManager implements ICarPurchasedListener {

    private final Assembly mAssembly;
    private HashMap<String, CarDescription> mCarDescriptions = new HashMap<>();

    public AssemblyManager(Assembly assembly, HashMap<String, CarDescription> carDescriptions) {
        mAssembly = assembly;
        mCarDescriptions = carDescriptions;
        setInitialOrders();
    }

    public void setInitialOrders() {
        mCarDescriptions.keySet().forEach(this::assignTask);
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

    public class UnknownCarException extends Exception {

        public UnknownCarException(String msg) {
            super(msg);
        }

    }

}
