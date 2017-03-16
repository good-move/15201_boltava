package ru.nsu.ccfit.boltava.statistics;

import org.junit.Test;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.parser.ConfigReader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class LineStatisticsCollectorTest {
    @Test
    public void collectStats() throws Exception {

        ArrayList<IFilter> filters;
        try {

            filters = ConfigReader.getFiltersFromConfig("config.txt");

            LineStatistics stats = new LineStatisticsCollector(filters).collectStats("./");
            System.out.print(new OrderedStringLineStats(
                    new LineStatisticsCollector(filters).collectStats("./")
            ).serialize());

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void countLines() throws Exception {

    }

}