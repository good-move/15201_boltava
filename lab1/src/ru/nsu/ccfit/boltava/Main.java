package ru.nsu.ccfit.boltava;

import ru.nsu.ccfit.boltava.filter.FileExtensionFilter;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.LastModifiedFilter;
import ru.nsu.ccfit.boltava.statistics.LineStatistics;
import ru.nsu.ccfit.boltava.statistics.LineStatisticsCollector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        LineStatistics ls = new LineStatistics();
        ls.printFormattedStats(System.out);
    }
}