package ru.nsu.ccfit.boltava.actors;

import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.storage.CarStorageManager;

public class Dealer extends SimpleRepeatable implements Runnable {

    private final CarStorageManager mCarStorageManager;
    private final String mCarSerial;

    public Dealer(CarStorageManager carStorageManager, String carSerial) {
        mCarStorageManager = carStorageManager;
        mCarSerial = carSerial;
    }

    public Dealer(CarStorageManager carStorageManager, String carSerial, int interval) {
        mCarStorageManager = carStorageManager;
        mCarSerial = carSerial;
        setInterval(interval);
    }

    @Override
    public void run() {
        try {
            while(true) {
                Car car = mCarStorageManager.getStorage(mCarSerial).get();
                mCarStorageManager.checkOut(car);
                System.out.println("Got a new car! ID: " + car.getId() + "serial: " + car.getSerial());
                synchronized (this) {
                    wait(getInterval());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
