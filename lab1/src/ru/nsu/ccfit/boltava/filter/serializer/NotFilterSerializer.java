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

        return new NotFilter(FilterParser.getChildren(filterString).get(0));
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        NotFilter notFilter = NotFilter.class.cast(filter);
        String result =  filter.getPrefix() + "(";
        IFilter child = notFilter.getChildFilters().get(0);
        result += FilterSerializerFactory.create(child.getPrefix()).serialize(child);
        return result + ")";
    }

}
