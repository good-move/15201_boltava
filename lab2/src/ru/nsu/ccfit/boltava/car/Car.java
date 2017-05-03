package ru.nsu.ccfit.boltava.car;

public class Car {

    private static final IDGenerator mIDGenerator = new IDGenerator();
    private final long mId;
    private final Engine mEngine;
    private final Body mBody;
    private final Accessory mAccessory;

    public Car(Engine engine, Body body, Accessory accessory) throws IDGenerator.IDGenerationException {
        mEngine = engine;
        mBody = body;
        mAccessory = accessory;
        mId = mIDGenerator.getId();
    }

}
