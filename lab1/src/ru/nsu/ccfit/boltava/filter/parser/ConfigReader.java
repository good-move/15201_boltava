package ru.nsu.ccfit.boltava.filter.parser;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigReader {

    public static ArrayList<IFilter> getFiltersFromConfig(String configFile)
            throws  IllegalArgumentException,
                    IllegalAccessException,
                    FilterSerializerFactory.FilterSerializationException,
                    IOException
    {
        ArrayList<IFilter> filters = new ArrayList<>();

        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(configFile));
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found: " + e.getMessage());
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("Null config file");
        }

        String filterString;

        try {
            while ((filterString = br.readLine()) != null) {
                if (filterString.length() > 0) {
                    filters.add(FilterParser.parse(filterString));
                }
            }
        } catch (IOException e) {
            throw new IOException("Error while reading config file: " + e.getMessage());
        }

        return filters;
    }

}
