package ru.nsu.ccfit.boltava.statistics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Statistics<GlobalData, DetailedDataKey, DetailedDataValue> {

    private Set<GlobalData> mGlobalData;
    private Map<DetailedDataKey, DetailedDataValue> mDetailedData;

    Statistics() {
        mGlobalData = new HashSet<>();
        mDetailedData = new HashMap<>();
    }

    boolean register(GlobalData object) {
        try {
            return mGlobalData.add(object);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return false;
    }

    DetailedDataValue get(DetailedDataKey key) {
        return mDetailedData.get(key);
    }

    public Set<GlobalData> getRawGlobalData() {
        return mGlobalData;
    }

    public Map<DetailedDataKey, DetailedDataValue> getRawDetailedData() {
        return mDetailedData;
    }

    public boolean update(DetailedDataKey key, DetailedDataValue value) {
        mDetailedData.put(key, value);
        return true;
    }

    public DetailedDataValue remove(DetailedDataKey key) {
        return mDetailedData.remove(key);
    }

}
