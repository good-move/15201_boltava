package ru.nsu.ccfit.boltava.assembly;

import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.storage.Storage;

public class AssemblyManager {

    private final Storage<Car> mCarStorage;
    private final Assembly mAssembly;

    AssemblyManager(Storage<Car> carStorage, Assembly assembly) {
        mCarStorage = carStorage;
        mAssembly = assembly;
//        carStorage.subscribe(Listener);
    }

}
