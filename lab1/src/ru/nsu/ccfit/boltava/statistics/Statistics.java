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
        if (object == null) throw new IllegalArgumentException();

        return mGlobalData.add(object);
    }

    DetailedDataValue get(DetailedDataKey key) {
        if (key == null) throw new IllegalArgumentException();

        return mDetailedData.get(key);
    }

    public Set<GlobalData> getRawGlobalData() {
        return mGlobalData;
    }

    public Map<DetailedDataKey, DetailedDataValue> getRawDetailedData() {
        return mDetailedData;
    }

    public boolean update(DetailedDataKey key, DetailedDataValue value) {
        if (key == null || value == null) throw new IllegalArgumentException();

        mDetailedData.put(key, value);
        return true;
    }

    public DetailedDataValue remove(DetailedDataKey key) {
        if (key == null) throw new IllegalArgumentException();

        return mDetailedData.remove(key);
    }

}
