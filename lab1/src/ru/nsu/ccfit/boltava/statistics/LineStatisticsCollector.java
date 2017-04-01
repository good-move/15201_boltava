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

    public LineStatistics collectStats(String rootPath) throws IOException {
        Files.walkFileTree(Paths.get(rootPath), new StatisticsCollector());
        return mStats;
    }

    public long countLines(Path filePath) throws IOException {
        if (filePath == null) throw new NullPointerException(
                this.getClass().getName() + ": Null pointer file path"
        );

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
                long linesCount = countLines(file);
                final long filesCount = 1;

                for (IFilter filter : mFilters) {
                    if (filter.check(file)) {
                        mStats.register(file, linesCount);
                        mStats.update(filter, new FilterStats(linesCount, filesCount));
                    }
                }

                return  CONTINUE;
            } catch (IOException | IllegalAccessException e) {
                System.out.println(e.getMessage());
            }

            return FileVisitResult.TERMINATE;
        }

    }

}
