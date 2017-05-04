package ru.nsu.ccfit.boltava.actors;

import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.storage.Storage;

public class Dealer extends SimpleRepeatable implements Runnable {

    private final Storage<Car> mCarStorage;

    public Dealer(Storage<Car> storage) {
        mCarStorage = storage;
    }

    public Dealer(Storage<Car> storage, int interval) {
        mCarStorage = storage;
        setInterval(interval);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Car car = mCarStorage.get();
                System.out.println("Got a new car! ID: " + car.getId());
                synchronized (this) {
                    wait(getInterval());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
