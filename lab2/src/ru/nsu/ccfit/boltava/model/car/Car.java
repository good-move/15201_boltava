package ru.nsu.ccfit.boltava.model.car;

import ru.nsu.ccfit.boltava.model.IDGenerator;

public class Car {

    private static final IDGenerator mIDGenerator = new IDGenerator(Car.class.getName());
    private final long mId;
    private final String mSerial;
//    private final String mName;
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

//    public String toString() {
//        return String.format("%s (%s)", mName, mSerial);
//    }

}
