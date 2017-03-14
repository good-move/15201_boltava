package ru.nsu.ccfit.boltava.statistics;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Map;

public class LineStatistics extends Statistics<Path, String, LineStatistics.FilterStats> {

    private long mTotalLinesCount = 0;

    @Override
    public boolean update(String filterId, FilterStats filterData) {

        if (filterId == null || filterData == null) throw new IllegalArgumentException();

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

    public void printFormattedStats(PrintStream ps) {

        ps.println("Total - " + mTotalLinesCount
                        + " lines in " + getRawGlobalData().size() + " files");

        if (mTotalLinesCount > 0) {
            ps.println("---------------");
        }

        for (Map.Entry<String, FilterStats> entry : getRawDetailedData().entrySet()) {
            ps.print(entry.getKey() + " - ");
            FilterStats filterStats = entry.getValue();
            ps.print(filterStats.mLinesCount + " lines in " + filterStats.mFilesCount + " files\n");
        }

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
