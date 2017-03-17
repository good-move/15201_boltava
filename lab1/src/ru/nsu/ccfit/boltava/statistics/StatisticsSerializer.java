package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;

public abstract class StatisticsSerializer
        <GlobalData, DetailedDataKey, DetailedDataValue, ReturnData> {

    private Statistics<GlobalData, DetailedDataKey, DetailedDataValue> mStats;

    public abstract ReturnData serialize() throws FilterSerializerFactory.FilterSerializationException;

}
