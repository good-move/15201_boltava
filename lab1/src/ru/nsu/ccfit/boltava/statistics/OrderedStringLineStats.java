package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;

import java.nio.file.Path;
import java.util.*;

public class OrderedStringLineStats extends StatisticsSerializer<Path, String, LineStatistics.FilterStats, String[]> {

    private LineStatistics mLineStats;

    public OrderedStringLineStats(LineStatistics lineStats) {
        if (lineStats == null) throw new NullPointerException(
                "Serializer cannot be constructed from null object"
        );
        mLineStats = lineStats;
    }

    @Override
    public String[] serialize() throws FilterSerializerFactory.FilterSerializationException {
        long totalLinesCount = mLineStats.getLinesCount();
        String header = "Total - " + totalLinesCount + " lines in " +
                        mLineStats.getSummary() + " files";

        if (totalLinesCount == 0) {
            return new String[]{header};
        }

        Map<IFilter, String> stringKeys = new HashMap<>();
        Map<IFilter, LineStatistics.FilterStats> stats = mLineStats.getRawDetailedData();
        IFilter[] mapKeys = stats.keySet().toArray(new IFilter[]{});
        ArrayList<String> result = new ArrayList<>();

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

        int maxFilterStringLength = 10;

        for (IFilter filter : mapKeys) {
            String str = FilterSerializerFactory.create(filter.getPrefix()).serialize(filter);
            stringKeys.put(filter, str);
            maxFilterStringLength = str.length() > maxFilterStringLength ? str.length() : maxFilterStringLength;
        }

        result.add(header);
        result.add(repeatString("-", maxFilterStringLength));

        for (IFilter filter: mapKeys) {
            LineStatistics.FilterStats statsEntry = stats.get(filter);
            if (statsEntry.mLinesCount == 0) break;
            String strKey = stringKeys.get(filter);
            String entry = alignFilterString(strKey, maxFilterStringLength - strKey.length());
            result.add(entry + " - " + statsEntry.mLinesCount + " lines in " + statsEntry.mFilesCount + " files");
        }

        return result.toArray(new String[result.size()]);
    }

    private String alignFilterString(String filter, int length) {
        return  filter.trim() + repeatString(" ", length);
    }

    private String repeatString(String repeat, int times) {
        return new String(new char[times]).replace("\0", repeat);
    }

}
