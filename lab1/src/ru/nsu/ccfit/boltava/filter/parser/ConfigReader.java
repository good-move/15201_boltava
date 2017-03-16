package ru.nsu.ccfit.boltava.filter.parser;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigReader {

    private static final FilterParser mParser = new FilterParser();

    public static ArrayList<IFilter> getFiltersFromConfig(String configFile)
            throws IOException, IllegalArgumentException, IllegalStateException, IllegalAccessException
    {
        ArrayList<IFilter> filters = new ArrayList<IFilter>();

        BufferedReader br = new BufferedReader(new FileReader(configFile));
        String filterString;

        while ((filterString = br.readLine()) != null) {
            filters.add(mParser.parse(filterString));
        }

        return filters;
    }

}
