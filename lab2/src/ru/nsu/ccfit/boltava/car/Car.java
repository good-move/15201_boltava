package ru.nsu.ccfit.boltava.car;

import ru.nsu.ccfit.boltava.IDGenerator;

public class Car {

    private static final IDGenerator mIDGenerator = new IDGenerator(Car.class.getName());
    private final long mId;
    private final String mSerial;
    private final Engine mEngine;
    private final Body mBody;
    private final Accessory mAccessory;

    public Car(String serial, Engine engine, Body body, Accessory accessory) {
        mSerial = serial;
        mEngine = engine;
        mBody = body;
        mAccessory = accessory;
        mId = mIDGenerator.getId();
    }


    public long getId() {
        return mId;
    }

    public String getSerial() {
        return mSerial;
    }

}
