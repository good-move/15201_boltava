package ru.nsu.ccfit.boltava.statistics;

public abstract class StatisticsSerializer
        <GlobalData, DetailedDataKey, DetailedDataValue, ReturnData> {

    private Statistics<GlobalData, DetailedDataKey, DetailedDataValue> mStats;

    public abstract ReturnData serialize();

}
