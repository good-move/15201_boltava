package ru.nsu.ccfit.boltava.statistics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class Statistics<GlobalData, PartialDataKey, PartialDataValue> {

    private HashSet<GlobalData> mGlobalData;
    private HashMap<PartialDataKey, PartialDataValue> mPartialData;

    Statistics() {
        mGlobalData = new HashSet<>();
        mPartialData = new HashMap<>();
    }

    boolean register(GlobalData object) {
        try {
            return mGlobalData.add(object);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

    PartialDataValue get(PartialDataKey key) {
        return mPartialData.get(key);
    }

    public boolean update(PartialDataKey key, PartialDataValue value) {
        mPartialData.put(key, value);
        return true;
    }

    public PartialDataValue remove(PartialDataKey key) {
        return mPartialData.remove(key);
    }

}
