package ru.nsu.ccfit.boltava.filter.parser;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigReader {

    public static ArrayList<IFilter> getFiltersFromConfig(String configFile)
            throws IOException, IllegalArgumentException, IllegalStateException, IllegalAccessException, FilterSerializerFactory.FilterSerializationException {
        ArrayList<IFilter> filters = new ArrayList<IFilter>();

        BufferedReader br = new BufferedReader(new FileReader(configFile));
        String filterString;

        while ((filterString = br.readLine()) != null) {
            if (filterString.length() > 0) {
                filters.add(FilterParser.parse(filterString));
            }
        }

        return filters;
    }

}
