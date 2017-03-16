package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.AndFilter;
import ru.nsu.ccfit.boltava.filter.parser.FilterParser;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.AND_FILTER;

public class AndFilterSerializer implements IFilterSerializer {

    private static final String mFilterPattern = AND_FILTER;

    @Override
    public AndFilter serialize(String filterString) {
        filterString = filterString.trim();
        if (!Pattern.matches(mFilterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new AndFilter(FilterParser.getChildren(filterString));
    }

    public String serialize(IFilter filter) {
        AndFilter f = AndFilter.class.cast(filter);
        String result =  filter.getPrefix() + "(";

        for (IFilter child : f.getChildFilters()) {
            result += (" " + FilterSerializerFactory.create(child.getPrefix()).serialize(filter));
        }

        return result + ")";
    }


}
