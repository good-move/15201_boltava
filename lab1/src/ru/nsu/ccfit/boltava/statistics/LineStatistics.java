package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class LineStatistics extends Statistics<Long, IFilter, LineStatistics.FilterStats> {

    private long mTotalLinesCount = 0;
    private long mTotalFilesCount = 0;

    public LineStatistics() {
        super(0L);
    }

    @Override
    public boolean update(IFilter filterId, FilterStats filterData) {

        if (filterId == null || filterData == null) throw new IllegalArgumentException(
                this.getClass().getName() + ": Null pointer passed"
        );

        FilterStats currentData = super.get(filterId);

        if (currentData == null) {
            currentData = new FilterStats(filterData);
        } else {
            currentData.mFilesCount += filterData.mFilesCount;
            currentData.mLinesCount += filterData.mLinesCount;
        }

        super.update(filterId, currentData);

        return true;
    }

    @Override
    public Long getSummary() {
        return mTotalFilesCount;
    }

    @Override
    public void register(Long linesCount) {
        if (linesCount == null) throw new IllegalArgumentException(
                this.getClass().getName() + ": Cannot register null objects"
        );

        mTotalLinesCount += linesCount;
        mTotalFilesCount += 1;
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

        FilterStats(FilterStats stats) {
            mLinesCount = stats.mLinesCount;
            mFilesCount = stats.mFilesCount;
        }

    }

}
