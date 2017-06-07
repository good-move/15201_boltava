package ru.nsu.ccfit.boltava.model.factory;

import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.car.CarDescription;
import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

import java.util.HashMap;
import java.util.List;

public class AssemblyManager {

    private final Assembly mAssembly;
    private HashMap<String, CarDescription> mCarDescriptions = new HashMap<>();

    public AssemblyManager(Assembly assembly, HashMap<String, CarDescription> carDescriptions) {
        mAssembly = assembly;
        mCarDescriptions = carDescriptions;
    }

    public void orderCar(String carSerial) throws IllegalArgumentException {
        assignTask(carSerial);
    }

    public void addTaskQueueSizeListener(IOnValueChangedListener listener) {
        mAssembly.addTaskQueueSizeListener(listener);
    }

    private void assignTask(String carSerial)  {
        System.out.println(this.getClass().getSimpleName() + ": creating car");
        CarDescription carDescription  = mCarDescriptions.get(carSerial);
        if (carDescription == null) {
            String template = "%s: Car with serial %s is not available for production";
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
