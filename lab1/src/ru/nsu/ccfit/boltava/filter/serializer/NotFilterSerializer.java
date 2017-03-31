package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.NotFilter;
import ru.nsu.ccfit.boltava.filter.parser.FilterParser;

import java.util.regex.Pattern;

public class NotFilterSerializer implements IFilterSerializer {

    private static final String mFilterPattern = "^![ \\t]*\\(.+\\)";

    @Override
    public NotFilter serialize(String filterString) throws FilterSerializerFactory.FilterSerializationException {
        if (filterString == null) throw new IllegalArgumentException(
                this.getClass().getName() + "null string passed"
        );

        if (!Pattern.matches(mFilterPattern, filterString.trim())) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new NotFilter(FilterParser.getChildren(filterString).get(0));
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException, FilterSerializerFactory.FilterSerializationException {
        NotFilter notFilter = NotFilter.class.cast(filter);
        String result =  filter.getPrefix() + "(";
        IFilter child = notFilter.getChildFilters().get(0);
        result += FilterSerializerFactory.create(child.getPrefix()).serialize(child);
        return result + ")";
    }

}
