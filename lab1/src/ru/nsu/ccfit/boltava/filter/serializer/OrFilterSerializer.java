package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.parser.FilterParser;
import ru.nsu.ccfit.boltava.filter.composite.OrFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.OR_FILTER;

public class OrFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = OR_FILTER;

    @Override
    public OrFilter serialize(String filterString) {
        if (!Pattern.matches(filterPattern, filterString.trim())) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new OrFilter(FilterParser.getChildren(filterString));
    }

    public String serialize(IFilter filter) {
        OrFilter f = OrFilter.class.cast(filter);
        String result =  filter.getPrefix() + "(";

        for (IFilter child : f.getChildFilters()) {
            result += (" " + FilterSerializerFactory.create(child.getPrefix()).serialize(filter));
        }

        return result + " )";
    }
}
