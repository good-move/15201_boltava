package ru.nsu.ccfit.boltava.FilterParser;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigReader {

    public static ArrayList<IFilter> getFiltersFromConfig(String configFile) {

        /*ArrayList<IFilter> filters = new ArrayList<IFilter>();

        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String filterRepresentation;
            FilterSerializerFactory factory = new FilterSerializerFactory();

            while ((filterRepresentation = br.readLine()) != null) {
                String filterPrefix = filterRepresentation.substring(0,1);
                factory.register(filterPrefix);
                IFilter filter = factory.get(filterPrefix).getFilter(filterRepresentation);
                filters.add(filter);
            }

            return filters;
        } catch (IOException e) {
        }*/

        return null;

    }

}
