package ru.nsu.ccfit.boltava.model.actors;

public class SimpleRepeatable implements IRepeatable {

    private int mInterval = 3000;
    private final Object lock = new Object();

    public void setInterval(int interval) throws IllegalArgumentException {
        synchronized (lock) {
            if (interval < 0)
                throw new IllegalArgumentException("Interval should be a positive number");
            mInterval = interval;
            lock.notifyAll();
        }
    }

    public synchronized int getInterval() {
        return mInterval;
    }

    public void waitInterval() throws InterruptedException {
        synchronized (lock) {
            if (getInterval() > 0) {
                lock.wait(mInterval);
            }
        }
    }

}
