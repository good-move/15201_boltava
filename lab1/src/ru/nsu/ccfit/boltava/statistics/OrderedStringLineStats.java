package ru.nsu.ccfit.boltava.statistics;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class OrderedStringLineStats extends StatisticsSerializer<Path, String, LineStatistics.FilterStats, String> {

    private LineStatistics mLineStats;

    OrderedStringLineStats(LineStatistics lineStats) {
        mLineStats = lineStats;
    }

    @Override
    public String serialize() {
        String result = "";
        long totalLinesCount = mLineStats.getLinesCount();

        result += "Total - " + totalLinesCount +
        " lines in " + mLineStats.getRawGlobalData().size() + " files";

        if (totalLinesCount == 0) {
            return result;
        }

        result += "\n---------------\n";

        Map<String, LineStatistics.FilterStats> stats = mLineStats.getRawDetailedData();
        String[] mapKeys = stats.keySet().toArray(new String[]{});

        // sort `stats` in descending order by the number of lines counted
        Arrays.sort(mapKeys, (s1, s2) -> {
            if (stats.get(s1).mLinesCount > stats.get(s2).mLinesCount) {
                return -1;
            } else if (stats.get(s1).mLinesCount < stats.get(s2).mLinesCount) {
                return 1;
            } else {
                return 0;
            }
        });

        for (String key: mapKeys) {
            LineStatistics.FilterStats statsEntry = stats.get(key);
//            if (statsEntry.mLinesCount == 0) break;
            result += (key + " - ");
            result += (statsEntry.mLinesCount + " lines in " + statsEntry.mFilesCount + " files\n");
        }

        return result;
    }

}
