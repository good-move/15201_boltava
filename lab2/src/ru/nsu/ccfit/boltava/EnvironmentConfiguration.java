package ru.nsu.ccfit.boltava;

import ru.nsu.ccfit.boltava.car.CarDescription;

import java.util.HashMap;

public class EnvironmentConfiguration {

    private HashMap<String, CarDescription> mCarDescriptions;
    private FactoryInfo mFactoryInfo;
    private String[] mCarSerials;
    private String[] mSuppliedEngineSerials;
    private String[] mSuppliedBodySerials;
    private String[] mSuppliedAccessorySerials;

    public HashMap<String, CarDescription> getCarDescriptions() {
        return mCarDescriptions;
    }

    public void setCarDescriptions(HashMap<String, CarDescription> carDescriptions) {
        this.mCarDescriptions = carDescriptions;
    }

    public FactoryInfo getFactoryInfo() {
        return mFactoryInfo;
    }

    public void setFactoryInfo(FactoryInfo factoryInfo) {
        this.mFactoryInfo = factoryInfo;
    }

    public String[] getCarSerials() {
        return mCarSerials;
    }

    public void setCarSerials(String[] carSerials) {
        this.mCarSerials = carSerials;
    }

    public String[] getSuppliedEngineSerials() {
        return mSuppliedEngineSerials;
    }

    public void setSuppliedEngineSerials(String[] suppliedEngineSerials) {
        this.mSuppliedEngineSerials = suppliedEngineSerials;
    }

    public String[] getSuppliedBodySerials() {
        return mSuppliedBodySerials;
    }

    public void setSuppliedBodySerials(String[] suppliedBodySerials) {
        this.mSuppliedBodySerials = suppliedBodySerials;
    }

    public String[] getSuppliedAccessorySerials() {
        return mSuppliedAccessorySerials;
    }

    public void setSuppliedAccessorySerials(String[] suppliedAccessorySerials) {
        this.mSuppliedAccessorySerials = suppliedAccessorySerials;
    }

    public static class FactoryInfo {

        int mWorkersCount;
        int mCarStorageSize;
        int mEngineStorageSize;
        int mBodyStorageSize;
        int mAccessoryStorageSize;


        public FactoryInfo(int workersCount,
                           int carStorageSize,
                           int engineStorageSize,
                           int bodyStorageSize,
                           int accessoryStorageSize) {
            mWorkersCount = workersCount;
            mCarStorageSize = carStorageSize;
            mEngineStorageSize = engineStorageSize;
            mBodyStorageSize = bodyStorageSize;
            mAccessoryStorageSize = accessoryStorageSize;
        }


        public int getWorkersCount() {
            return mWorkersCount;
        }

        public int getCarStorageSize() {
            return mCarStorageSize;
        }

        public int getEngineStorageSize() {
            return mEngineStorageSize;
        }

        public int getBodyStorageSize() {
            return mBodyStorageSize;
        }

        public int getAccessoryStorageSize() {
            return mAccessoryStorageSize;
        }

    }

}