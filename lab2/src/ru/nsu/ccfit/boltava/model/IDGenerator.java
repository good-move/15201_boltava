package ru.nsu.ccfit.boltava.model;

import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {

    private AtomicLong mCurrentId = new AtomicLong(0);
    private final String mTarget;

    public IDGenerator(String host) {
        mTarget = host;
    }

    public long getId() {
        if (mCurrentId.get() - 1 == Long.MAX_VALUE) {
            throw new RuntimeException("Ran out of ids for " + mTarget);
        }

        return mCurrentId.incrementAndGet();
    }

}
