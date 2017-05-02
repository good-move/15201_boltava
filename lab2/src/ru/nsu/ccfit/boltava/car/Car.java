package ru.nsu.ccfit.boltava.car;

public class Car {

    public String id;
    private final Engine mEngine;
    private final Body mBody;
    private final Accessory mAccessory;

    public Car(Engine engine, Body body, Accessory accessory) {
        mEngine = engine;
        mBody = body;
        mAccessory = accessory;
    }

}
