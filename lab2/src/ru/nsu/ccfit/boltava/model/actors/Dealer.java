package ru.nsu.ccfit.boltava.model.actors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.IDGenerator;
import ru.nsu.ccfit.boltava.model.car.Car;
import ru.nsu.ccfit.boltava.model.storage.CarStorageManager;
import ru.nsu.ccfit.boltava.model.storage.Storage;
import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;
public class Dealer extends SimpleRepeatable implements IOnValueChangedListener<Integer> {

    private static final IDGenerator mIDGenerator = new IDGenerator("Dealer");
    private static final Logger logger = LogManager.getLogger(Dealer.class.getName());

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

    @Override
    public void onValueChanged(Integer newInterval) {
        setInterval(newInterval);
    }


    public class DealerRunnable implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    Storage<Car> carStorage = mCarStorageManager.getStorage(mCarSerial);
                    if (carStorage == null) {
                        logger.warn(String.format("Storage with cars %s doesn't exist", mCarSerial));
                        break;
                    }
                    if (carStorage.isEmpty()) {
                        mCarStorageManager.orderCar(mCarSerial);
                    }
                    Car car = carStorage.get();
                    mCarStorageManager.checkOut(car);
                    synchronized (this) {
                        wait(getInterval());
                    }
                }
            } catch (InterruptedException e) {
                logger.info("Interrupted dealer of cars with serial " + mCarSerial);
            }
        }

    }
}
