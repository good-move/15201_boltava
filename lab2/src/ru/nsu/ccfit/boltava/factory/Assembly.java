package ru.nsu.ccfit.boltava.factory;

import ru.nsu.ccfit.boltava.car.CarDescription;
import ru.nsu.ccfit.boltava.car.Accessory;
import ru.nsu.ccfit.boltava.car.Body;
import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.car.Engine;
import ru.nsu.ccfit.boltava.storage.StorageManager;

public class Assembly {

    private final StorageManager<Engine> mEngineStorageManager;
    private final StorageManager<Body> mBodyStorageManager;
    private final StorageManager<Accessory> mAccessoryStorageManager;
    private final StorageManager<Car> mCarStorageManager;
    private final AssemblyLines mAssemblyLines;

    public Assembly(StorageManager<Engine> eStorageManager,
                    StorageManager<Body> bStorageManager,
                    StorageManager<Accessory> aStorageManager,
                    StorageManager<Car> cStorageManager,
                    int taskQueueSize,
                    int workersCount) {
        mEngineStorageManager = eStorageManager;
        mBodyStorageManager = bStorageManager;
        mAccessoryStorageManager = aStorageManager;
        mCarStorageManager = cStorageManager;
        mAssemblyLines = new AssemblyLines(taskQueueSize, workersCount);
    }

    public void createCar(CarDescription carDescription) throws InterruptedException {
        mAssemblyLines.addTask(new AssembleCarTask(carDescription));
    }

    public void shutDown() {
        mAssemblyLines.shutDown();
    }

    private class AssembleCarTask implements ITask {

        String mCarSerial;
        String mEngineSerial;
        String mBodySerial;
        String mAccessorySerial;

        AssembleCarTask(CarDescription carDescription) {
            mCarSerial = carDescription.getCarSerial();
            mEngineSerial = carDescription.getEngineSerial();
            mBodySerial = carDescription.getBodySerial();
            mAccessorySerial = carDescription.getAccessorySerial();
        }

        @Override
        public void execute() {
            try {
                Engine engine = mEngineStorageManager.getStorage(mEngineSerial).get();
                Body body = mBodyStorageManager.getStorage(mBodySerial).get();
                Accessory accessory = mAccessoryStorageManager.getStorage(mAccessorySerial).get();
                mCarStorageManager.getStorage(mCarSerial).put(new Car(mCarSerial, engine, body, accessory));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
