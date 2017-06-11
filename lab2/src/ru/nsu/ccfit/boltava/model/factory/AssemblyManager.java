package ru.nsu.ccfit.boltava.model.factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.car.CarDescription;
import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

import java.util.HashMap;

public class AssemblyManager {

    private final Assembly mAssembly;
    private HashMap<String, CarDescription> mCarDescriptions = new HashMap<>();
    private static Logger logger = LogManager.getLogger(AssemblyManager.class.getName());


    public AssemblyManager(Assembly assembly, HashMap<String, CarDescription> carDescriptions) {
        mAssembly = assembly;
        mCarDescriptions = carDescriptions;
    }

    public void orderCar(String carSerial) throws IllegalArgumentException, InterruptedException {
        assignTask(carSerial);
    }

    public void addTaskQueueSizeListener(IOnValueChangedListener<Integer> listener) {
        mAssembly.addTaskQueueSizeListener(listener);
    }

    private void assignTask(String carSerial) throws InterruptedException {
        CarDescription carDescription  = mCarDescriptions.get(carSerial);
        if (carDescription == null) {
            String template = "%s: Car with serial %s is not available for production";
            logger.warn(String.format(template, this.getClass().getSimpleName(), carSerial));
        }
        else {
            logger.info(this.getClass().getSimpleName() + ": creating car");
            mAssembly.createCar(carDescription);
        }
    }

}
