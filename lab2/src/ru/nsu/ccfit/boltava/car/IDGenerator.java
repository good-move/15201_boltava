package ru.nsu.ccfit.boltava.car;

import java.util.concurrent.atomic.AtomicLong;

class IDGenerator {

    private AtomicLong mCurrentId = new AtomicLong(0);
    private final String mTarget;

    IDGenerator(String host) {
        mTarget = host;
    }

    long getId() {
        if (mCurrentId.get() - 1 == Long.MAX_VALUE) {
            throw new RuntimeException("Ran out of ids for " + mTarget);
        }

        return mCurrentId.incrementAndGet();
    }

}
