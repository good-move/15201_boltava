package ru.nsu.ccfit.boltava.model.car;

public class CarDescription {

    private final String mCarSerial;
    private final String mEngineSerial;
    private final String mBodySerial;
    private final String mAccessorySerial;

    public CarDescription(String carSerial,
                          String engineSerial,
                          String bodySerial,
                          String accessorySerial) {
        mCarSerial = carSerial;
        mEngineSerial = engineSerial;
        mBodySerial = bodySerial;
        mAccessorySerial = accessorySerial;
    }

    public String getCarSerial() {
        return mCarSerial;
    }

    public String getEngineSerial() {
        return mEngineSerial;
    }

    public String getBodySerial() {
        return mBodySerial;
    }

    public String getAccessorySerial() {
        return mAccessorySerial;
    }

}
