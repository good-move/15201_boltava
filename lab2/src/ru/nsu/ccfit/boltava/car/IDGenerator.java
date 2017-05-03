package ru.nsu.ccfit.boltava.car;

import java.util.concurrent.atomic.AtomicLong;

public class IDGenerator {

    private AtomicLong mCurrentId = new AtomicLong(0);

    long getId() throws IDGenerationException {
        if (mCurrentId.get() - 1 == Long.MAX_VALUE) {
            throw new IDGenerationException("Ran out of ids");
        }

        return mCurrentId.incrementAndGet();
    }

    public class IDGenerationException extends Exception {

        IDGenerationException(String msg) {
            super(msg);
        }
    }

}
