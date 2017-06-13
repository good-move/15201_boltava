package ru.nsu.ccfit.boltava.model.car;

public abstract class Component {

    private final String mSerial;
    private final long mId;

    Component(String serial, long id) {
        mSerial = serial;
        mId = id;
    }

    public String getSerial() {
        return mSerial;
    }

    public long getId() {
        return mId;
    }

}
