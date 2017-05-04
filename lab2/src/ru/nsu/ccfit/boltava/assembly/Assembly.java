package ru.nsu.ccfit.boltava.assembly;

import ru.nsu.ccfit.boltava.assembly.threadpool.ITask;
import ru.nsu.ccfit.boltava.assembly.threadpool.ThreadPool;
import ru.nsu.ccfit.boltava.car.Accessory;
import ru.nsu.ccfit.boltava.car.Body;
import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.car.Engine;
import ru.nsu.ccfit.boltava.storage.Storage;

public class Assembly {

    private final Storage<Engine> mEngineStorage;
    private final Storage<Body> mBodyStorage;
    private final Storage<Accessory> mAccessoryStorage;
    private final Storage<Car> mCarStorage;
    private final ThreadPool mWorkers;

    public Assembly(Storage<Engine> eStorage,
                    Storage<Body> bStorage,
                    Storage<Accessory> aStorage,
                    Storage<Car> cStorage,
                    int queueSize,
                    int workersCount) {
        mEngineStorage = eStorage;
        mBodyStorage = bStorage;
        mAccessoryStorage = aStorage;
        mCarStorage = cStorage;
        mWorkers = new ThreadPool(queueSize, workersCount);
    }

    public void createCar() throws InterruptedException {
        mWorkers.feed(new AssembleCarTask());
    }

    class AssembleCarTask implements ITask {

        @Override
        public void execute() {
            try {
                Engine engine = mEngineStorage.get();
                Body body = mBodyStorage.get();
                Accessory accessory = mAccessoryStorage.get();
                mCarStorage.put(new Car(engine, body, accessory));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
