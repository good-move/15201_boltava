package ru.nsu.ccfit.boltava.actors;

import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.storage.StorageManager;

public class Dealer extends SimpleRepeatable implements Runnable {

    private final StorageManager<Car> mCarStorageManager;
    private final String mCarSerial;

    public Dealer(StorageManager<Car> storageManager, String carSerial) {
        mCarStorageManager = storageManager;
        mCarSerial = carSerial;
    }

    public Dealer(StorageManager<Car> storageManager, String carSerial, int interval) {
        mCarStorageManager = storageManager;
        mCarSerial = carSerial;
        setInterval(interval);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Car car = mCarStorageManager.getStorage(mCarSerial).get();
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
