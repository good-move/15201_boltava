package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.nio.file.*;
import java.io.*;

import static java.nio.file.FileVisitResult.*;

public class LineStatisticsCollector {

    private ArrayList<IFilter> mFilters;
    private LineStatistics mStats;

    public LineStatisticsCollector(ArrayList<IFilter> filters) {
        mFilters = filters;
        mStats = new LineStatistics();
    }

    public void collectStats(String rootPath) {
        try {
            Files.walkFileTree(Paths.get(rootPath), new StatisticsCollector());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int countLines(Path filePath) {
        try {
            FileReader fileReader = new FileReader(filePath.toFile());
            LineNumberReader lnReader = new LineNumberReader(fileReader);
            lnReader.skip(Long.MAX_VALUE);

            return lnReader.getLineNumber() + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public LineStatistics getStats() {
        return mStats;
    }

    private class StatisticsCollector extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            for (IFilter filter : mFilters){
                if (filter.check(file)) {
                    int linesCount = countLines(file);
                    LineStatistics.Pair pair = new LineStatistics.Pair(linesCount, 1);
                    mStats.register(file);
                    mStats.update(filter.toString(), pair);
                }
            }

            return CONTINUE;
        }

    }

}
