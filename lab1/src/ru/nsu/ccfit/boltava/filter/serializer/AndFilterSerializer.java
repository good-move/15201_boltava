package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.AndFilter;
import ru.nsu.ccfit.boltava.filter.parser.FilterParser;

import java.util.regex.Pattern;

public class AndFilterSerializer implements IFilterSerializer {

    private static final String mFilterPattern = "^&[ \\t]*\\(.+\\)$";

    @Override
    public AndFilter serialize(String filterString) throws FilterSerializerFactory.FilterSerializationException {
        filterString = filterString.trim();
        if (!Pattern.matches(mFilterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new AndFilter(FilterParser.getChildren(filterString));
    }

    public String serialize(IFilter filter) throws FilterSerializerFactory.FilterSerializationException {
        AndFilter andFilter = AndFilter.class.cast(filter);
        String result =  filter.getPrefix() + "(";

        for (IFilter child : andFilter.getChildFilters()) {
            result += (" " + FilterSerializerFactory.create(child.getPrefix()).serialize(child));
        }

        return result + " )";
    }


}
