package ru.nsu.ccfit.boltava.storage;

import ru.nsu.ccfit.boltava.car.Car;

public class CarStorage extends Storage<Car> {

    CarStorage(long size) {
        super(size);
    }
}
