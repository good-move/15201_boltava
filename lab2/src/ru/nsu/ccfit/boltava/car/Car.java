package ru.nsu.ccfit.boltava.car;

public class Car {

    private static final IDGenerator mIDGenerator = new IDGenerator(Car.class.getName());
    private final long mId;
    private final Engine mEngine;
    private final Body mBody;
    private final Accessory mAccessory;

    public Car(Engine engine, Body body, Accessory accessory) {
        mEngine = engine;
        mBody = body;
        mAccessory = accessory;
        mId = mIDGenerator.getId();
    }


    public long getId() {
        return mId;
    }

}
