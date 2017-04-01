package ru.nsu.ccfit.boltava.statistics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Statistics<SummaryType, DetailedDataKey, DetailedDataValue> {

    private SummaryType mSummary;
    private Map<DetailedDataKey, DetailedDataValue> mData;

    Statistics(SummaryType summary) {
        mData = new HashMap<>();
        mSummary = summary;
    }

    public abstract void register(SummaryType object);

    DetailedDataValue get(DetailedDataKey key) {
        if (key == null) throw new IllegalArgumentException(
                this.getClass().getName() + ": Null keys are not allowed"
        );

        return mData.get(key);
    }

    public SummaryType getSummary() {
        return mSummary;
    }

    public Map<DetailedDataKey, DetailedDataValue> getRawDetailedData() {
        return mData;
    }

    public boolean update(DetailedDataKey key, DetailedDataValue value) {
        if (key == null || value == null) throw new IllegalArgumentException(
                this.getClass().getName() + ": Null keys and values are not allowed"
        );

        mData.put(key, value);
        return true;
    }

    public DetailedDataValue remove(DetailedDataKey key) {
        if (key == null) throw new IllegalArgumentException(
                this.getClass().getName() + ": Null keys are not allowed"
        );

        return mData.remove(key);
    }

}
