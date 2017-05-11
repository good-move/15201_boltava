package ru.nsu.ccfit.boltava.actors;

import ru.nsu.ccfit.boltava.IDGenerator;
import ru.nsu.ccfit.boltava.car.Car;
import ru.nsu.ccfit.boltava.storage.CarStorageManager;
import ru.nsu.ccfit.boltava.storage.Storage;

public class Dealer extends SimpleRepeatable {

    private static final IDGenerator mIDGenerator = new IDGenerator("Dealer");

    private final CarStorageManager mCarStorageManager;
    private final String mCarSerial;
    private final long mID = mIDGenerator.getId();

    private Thread mThread = new Thread(new DealerRunnable());

    public Dealer(CarStorageManager carStorageManager, String carSerial) {
        this(carStorageManager, carSerial, 3000);
    }

    public Dealer(CarStorageManager carStorageManager, String carSerial, int interval) {
        mCarStorageManager = carStorageManager;
        mCarSerial = carSerial;
        setInterval(interval);
        mThread.setName(String.format("%s. ID: %d, Car serial: %s", this.getClass().getSimpleName(), mID, mCarSerial));
    }

    public Thread getThread() {
        return mThread;
    }


    public class DealerRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Storage<Car> carStorage = mCarStorageManager.getStorage(mCarSerial);
                    if (carStorage == null) {
                        System.err.println(String.format("Storage with cars %s is unavailable", mCarSerial));
                        break;
                    }
                    Car car = carStorage.get();
                    mCarStorageManager.checkOut(car);
                    System.out.println(String.format("Got a new car! ID: %d, serial: %s", car.getId(), car.getSerial()));
                    synchronized (this) {
                        wait(getInterval());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
