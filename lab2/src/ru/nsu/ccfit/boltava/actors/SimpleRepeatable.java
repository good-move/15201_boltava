package ru.nsu.ccfit.boltava.actors;

public class SimpleRepeatable implements IRepeatable {

    private int mInterval = 1000;

    public synchronized void setInterval(int interval) throws IllegalArgumentException {
        if (interval < 0) throw new IllegalArgumentException("Interval should be a positive number");
        mInterval = interval;
    }

    public synchronized int getInterval() {
        return mInterval;
    }

}
