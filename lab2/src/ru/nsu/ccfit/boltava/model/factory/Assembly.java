package ru.nsu.ccfit.boltava.model.factory;

import ru.nsu.ccfit.boltava.model.car.CarDescription;
import ru.nsu.ccfit.boltava.model.car.Accessory;
import ru.nsu.ccfit.boltava.model.car.Body;
import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.car.Engine;
import ru.nsu.ccfit.boltava.model.storage.StorageManager;
import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

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

    public void addTaskQueueSizeListener(IOnValueChangedListener listener) {
        mAssemblyLines.addTaskQueueSizeListener(listener);
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
        public void execute() throws InterruptedException {
            Engine engine = mEngineStorageManager.getStorage(mEngineSerial).get();
            Body body = mBodyStorageManager.getStorage(mBodySerial).get();
            Accessory accessory = mAccessoryStorageManager.getStorage(mAccessorySerial).get();
            mCarStorageManager.getStorage(mCarSerial).put(new Car(mCarSerial, engine, body, accessory));
        }

    }

}
