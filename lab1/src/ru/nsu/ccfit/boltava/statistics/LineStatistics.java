package ru.nsu.ccfit.boltava.statistics;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Map;

public class LineStatistics extends Statistics<Path, String, LineStatistics.Pair> {

    private long mTotalLinesCount = 0;

    @Override
    public boolean update(String filterId, Pair filterData) {

        Pair currentData = super.get(filterId);

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

        for (Map.Entry<String, Pair> entry : getRawDetailedData().entrySet()) {
            ps.print(entry.getKey() + " - ");
            Pair pair = entry.getValue();
            ps.print(pair.mLinesCount + " lines in " + pair.mFilesCount + " files\n");
        }

    }

    static class Pair {

        long mFilesCount = 0;
        long mLinesCount = 0;

        Pair() {}

        Pair(long linesCount, long filesCount) {
            mLinesCount = linesCount;
            mFilesCount = filesCount;
        }

    }

}
