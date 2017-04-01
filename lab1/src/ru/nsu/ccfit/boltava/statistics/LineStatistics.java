package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LineStatistics extends Statistics<Path, IFilter, LineStatistics.FilterStats> {

    private long mTotalLinesCount = 0;

    private Map<IFilter, LineStatistics.FilterStats> mDetailedData = new HashMap<>();


    @Override
    public boolean update(IFilter filterId, FilterStats filterData) {

        if (filterId == null || filterData == null) throw new IllegalArgumentException(
                this.getClass().getName() + ": Null pointer passed"
        );

        FilterStats currentData = super.get(filterId);

        if (currentData == null) {
            currentData = filterData;
        } else {
            currentData.mFilesCount += filterData.mFilesCount;
            currentData.mLinesCount += filterData.mLinesCount;
        }

        super.update(filterId, currentData);

        return true;
    }

    public boolean register(Path path, long linesCount) {
        if (super.register(path)) {
            mTotalLinesCount += linesCount;
            return true;
        }

        return false;
    }


    public long getLinesCount() {
        return mTotalLinesCount;
    }

    static class FilterStats {

        long mFilesCount = 0;
        long mLinesCount = 0;

        FilterStats() {}

        FilterStats(long linesCount, long filesCount) {
            mLinesCount = linesCount;
            mFilesCount = filesCount;
        }

    }

}
