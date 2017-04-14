package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.statistics.LineStatistics.FilterStats;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.*;
import java.io.*;
import java.util.Set;

import static java.nio.file.FileVisitResult.*;

public class LineStatisticsCollector {

    private final Set<IFilter> mFilters;
    private LineStatistics mStats = new LineStatistics();

    public LineStatisticsCollector(Set<IFilter> filters) {
        if (filters == null) throw new NullPointerException(
                this.getClass().getName() + "Null pointer list passed"
        );
        mFilters = filters;
    }

    public LineStatistics collectStats(String rootPath) {
        try {
            Files.walkFileTree(Paths.get(rootPath), new StatisticsCollector());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return mStats;
    }

    public long countLines(Path filePath) throws IOException {
        if (filePath == null) throw new NullPointerException(
                this.getClass().getName() + ": Null pointer file path"
        );

        LineNumberReader lnreader = new LineNumberReader(new FileReader(filePath.toFile()));
        lnreader.skip(Long.MAX_VALUE);
        long linesCount = lnreader.getLineNumber() + 1;
        lnreader.close();

        return linesCount;
    }

    public LineStatistics getStats() {
        return mStats;
    }

    private class StatisticsCollector extends SimpleFileVisitor<Path> {

        @Override
        public FileVisitResult visitFileFailed(Path path, IOException e) throws IOException {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            try {
                boolean passed = false;
                long linesCount = 0;

                final long filesCount = 1;
                FilterStats filterStats = new FilterStats();

                for (IFilter filter : mFilters) {
                    if (filter.check(file)) {
                        if (!passed) {
                            passed = true;
                            linesCount = countLines(file);
                            mStats.register(linesCount);
                            filterStats.mFilesCount = filesCount;
                            filterStats.mLinesCount = linesCount;
                        }
                        mStats.update(filter, filterStats);
                    }
                }

            } catch (IOException | IllegalAccessException e) {
                System.err.println(e.getMessage());
            }

            return CONTINUE;
        }

    }

}
