package ru.nsu.ccfit.boltava.car;

public class Car {

    public String id;
    private final Engine mEngine;
    private final Body mBody;
    private final Accessory[] mAccessories;

    public Car(Engine engine, Body body, Accessory[] accessories) {
        mEngine = engine;
        mBody = body;
        mAccessories = accessories;
    }

}
