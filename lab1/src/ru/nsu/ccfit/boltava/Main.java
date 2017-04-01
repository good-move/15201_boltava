package ru.nsu.ccfit.boltava;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.parser.ConfigReader;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;
import ru.nsu.ccfit.boltava.statistics.LineStatistics;
import ru.nsu.ccfit.boltava.statistics.LineStatisticsCollector;
import ru.nsu.ccfit.boltava.statistics.OrderedStringLineStats;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Expected 2 arguments: config file path and root path");
            return;
        }

        try {
            Set<IFilter> filters;
            filters = ConfigReader.getFiltersFromConfig(args[0].trim());
            LineStatistics stats = new LineStatisticsCollector(filters).collectStats(args[1].trim());
            for (String line : new OrderedStringLineStats(stats).serialize()) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}