package ru.nsu.ccfit.boltava.model.car;

import org.jetbrains.annotations.NotNull;

public class CarDescription {

    private final String mCarSerial;
    private final String mEngineSerial;
    private final String mBodySerial;
    private final String mAccessorySerial;


    public CarDescription(@NotNull String carSerial,
                          @NotNull String engineSerial,
                          @NotNull String bodySerial,
                          @NotNull String accessorySerial) {
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
