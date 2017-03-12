package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.*;
import java.io.*;
import java.util.List;

import static java.nio.file.FileVisitResult.*;

public class LineStatisticsCollector {

    private final List<IFilter> mFilters;
    private LineStatistics mStats;

    public LineStatisticsCollector(List<IFilter> filters) {
        mFilters = filters;
        mStats = new LineStatistics();
    }

    public void collectStats(String rootPath) throws IOException {
        Files.walkFileTree(Paths.get(rootPath), new StatisticsCollector());
    }

    public int countLines(Path filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath.toFile());
        LineNumberReader lnReader = new LineNumberReader(fileReader);
        lnReader.skip(Long.MAX_VALUE);

        return lnReader.getLineNumber() + 1;
    }

    public LineStatistics getStats() {
        return mStats;
    }

    private class StatisticsCollector extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            try {
                int linesCount = countLines(file);
                final long filesCount = 1;
                LineStatistics.FilterStats filterStats = new LineStatistics.FilterStats(linesCount, filesCount);

                for (IFilter filter : mFilters) {
                    if (filter.check(file)) {
                        mStats.register(file, linesCount);
                        mStats.update(filter.toString(), filterStats);
                    }
                }

                return  CONTINUE;
            } catch (IOException | IllegalAccessException e) {
                System.err.println(e.getMessage());
                return FileVisitResult.TERMINATE;
            }
        }

    }

}
