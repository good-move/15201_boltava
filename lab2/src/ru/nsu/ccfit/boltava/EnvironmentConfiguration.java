package ru.nsu.ccfit.boltava;

import ru.nsu.ccfit.boltava.car.CarDescription;

import java.util.HashMap;

public class EnvironmentConfiguration {

    private HashMap<String, CarDescription> carDescriptions;
    private FactoryInfo factoryInfo;
    private HashMap<String, Integer> orderedCarSerials;
    private HashMap<String, Integer> engineSuppliersInfo;
    private HashMap<String, Integer> bodySuppliersInfo;
    private HashMap<String, Integer> accessorySuppliersInfo;

    public HashMap<String, CarDescription> getCarDescriptions() {
        return carDescriptions;
    }

    public void setCarDescriptions(HashMap<String, CarDescription> carDescriptions) {
        this.carDescriptions = carDescriptions;
    }

    public FactoryInfo getFactoryInfo() {
        return factoryInfo;
    }

    public void setFactoryInfo(FactoryInfo factoryInfo) {
        this.factoryInfo = factoryInfo;
    }

    public HashMap<String, Integer> getOrderedCarSerials() {
        return orderedCarSerials;
    }

    public void setOrderedCarSerials(HashMap<String, Integer> orderedCarSerials) {
        this.orderedCarSerials = orderedCarSerials;
    }

    public HashMap<String, Integer> getEngineSuppliersInfo() {
        return engineSuppliersInfo;
    }

    public void setEngineSuppliersInfo(HashMap<String, Integer> engineSuppliersInfo) {
        this.engineSuppliersInfo = engineSuppliersInfo;
    }

    public HashMap<String, Integer> getBodySuppliersInfo() {
        return bodySuppliersInfo;
    }

    public void setBodySuppliersInfo(HashMap<String, Integer> bodySuppliersInfo) {
        this.bodySuppliersInfo = bodySuppliersInfo;
    }

    public HashMap<String, Integer> getAccessorySuppliersInfo() {
        return accessorySuppliersInfo;
    }

    public void setAccessorySuppliersInfo(HashMap<String, Integer> accessorySuppliersInfo) {
        this.accessorySuppliersInfo = accessorySuppliersInfo;
    }

    public static class FactoryInfo {

        int mWorkersCount;
        int mCarStorageSize;
        int mEngineStorageSize;
        int mBodyStorageSize;
        int mAccessoryStorageSize;


        public FactoryInfo() {}

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

        public void setWorkersCount(int mWorkersCount) {
            this.mWorkersCount = mWorkersCount;
        }

        public void setCarStorageSize(int mCarStorageSize) {
            this.mCarStorageSize = mCarStorageSize;
        }

        public void setEngineStorageSize(int mEngineStorageSize) {
            this.mEngineStorageSize = mEngineStorageSize;
        }

        public void setBodyStorageSize(int mBodyStorageSize) {
            this.mBodyStorageSize = mBodyStorageSize;
        }

        public void setAccessoryStorageSize(int mAccessoryStorageSize) {
            this.mAccessoryStorageSize = mAccessoryStorageSize;
        }
    }

}