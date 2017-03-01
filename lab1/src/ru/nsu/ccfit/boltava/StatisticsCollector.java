package ru.nsu.ccfit.boltava;

import java.nio.file.attribute.BasicFileAttributes;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.file.*;

import static java.nio.file.FileVisitResult.*;

public class StatisticsCollector {

    private ArrayList<IFilter> mFilters;
//    private Statistics mStats;

    StatisticsCollector(ArrayList<IFilter> filters) {
        mFilters = filters;
    }

    public void collectStats(String rootPath) {
        try {
            DirIterator iterator = new DirIterator();
            Files.walkFileTree(Paths.get(rootPath), iterator);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class DirIterator extends SimpleFileVisitor<Path> {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            for (IFilter filter : mFilters){
                if (filter.check(file)) {
//                    mStats.register(file);
//                    int linesCount = countLines(file);
//                    mStats.add(filter, linesCount);
                }
            }

            return CONTINUE;
        }
    }

}
