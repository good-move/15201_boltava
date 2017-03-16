package ru.nsu.ccfit.boltava.statistics;

import com.sun.org.apache.xml.internal.serialize.SerializerFactory;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;
import ru.nsu.ccfit.boltava.filter.serializer.Serializer;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

public class OrderedStringLineStats extends StatisticsSerializer<Path, String, LineStatistics.FilterStats, String> {

    private LineStatistics mLineStats;

    public OrderedStringLineStats(LineStatistics lineStats) {
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

        Map<IFilter, LineStatistics.FilterStats> stats = mLineStats.getRawDetailedData();
        IFilter[] mapKeys = stats.keySet().toArray(new IFilter[]{});

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

        for (IFilter filter: mapKeys) {
            LineStatistics.FilterStats statsEntry = stats.get(filter);
//            if (statsEntry.mLinesCount == 0) break;
            result += (FilterSerializerFactory.create(filter.getPrefix()).serialize(filter) + " - ");
            result += (statsEntry.mLinesCount + " lines in " + statsEntry.mFilesCount + " files\n");
        }

        return result;
    }

}
