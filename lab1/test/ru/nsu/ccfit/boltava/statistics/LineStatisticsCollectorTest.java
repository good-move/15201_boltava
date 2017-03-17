package ru.nsu.ccfit.boltava.statistics;

import org.junit.Test;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.parser.ConfigReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class LineStatisticsCollectorTest {

    @Test
    public void collectStats() throws Exception {

        System.out.println("\nExample run\n");

        try {
            ArrayList<IFilter> filters;
            filters = ConfigReader.getFiltersFromConfig("./test/sample_config.txt");
            System.out.print(new OrderedStringLineStats(
                    new LineStatisticsCollector(filters).collectStats("./")
            ).serialize());

        } catch (IOException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void countLines() throws Exception {

        String mTestDataPath = "test resources/root";
        String mTestProfile = "test resources/test_profile.csv";
        String mTestConfigsPath = "test resources/configs";


        BufferedReader br = new BufferedReader(new FileReader(mTestProfile));
        ArrayList<IFilter> filters;
        String filterString;

        while ((filterString = br.readLine()) != null) {
            String[] row = Arrays
                                .stream(filterString.split(","))
                                .map(String::trim)
                                .toArray(String[]::new);

            String configFilePath = mTestConfigsPath +"/"+row[0];
            long expectedLinesMatched = Long.parseLong(row[1]);
            long expectedFilesMatched = Long.parseLong(row[2]);

            System.out.print("Testing " + row[0]);

            filters = ConfigReader.getFiltersFromConfig(configFilePath);
            LineStatistics stats = new LineStatisticsCollector(filters).collectStats(mTestDataPath);

            assertEquals(expectedLinesMatched, stats.getLinesCount());
            assertEquals(expectedFilesMatched, stats.getRawGlobalData().size());

            System.out.println(" --- PASSED");
        }

    }

}