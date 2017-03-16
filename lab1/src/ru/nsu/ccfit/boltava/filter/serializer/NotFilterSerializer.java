package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.NotFilter;
import ru.nsu.ccfit.boltava.filter.parser.FilterParser;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.NOT_FILTER;

public class NotFilterSerializer implements IFilterSerializer {

    @Override
    public NotFilter serialize(String filterString) {
        if (!Pattern.matches(NOT_FILTER, filterString.trim())) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new NotFilter(FilterParser.getChildren(filterString));
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        NotFilter f = NotFilter.class.cast(filter);
        String result =  filter.getPrefix() + "(";
        result += FilterSerializerFactory.create(f.getChildFilters().get(0).getPrefix()).serialize(filter);
        return result + ")";
    }

}
