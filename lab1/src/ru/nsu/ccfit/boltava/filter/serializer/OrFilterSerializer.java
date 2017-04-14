package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.parser.FilterParser;
import ru.nsu.ccfit.boltava.filter.composite.OrFilter;

import java.util.regex.Pattern;

public class OrFilterSerializer implements IFilterSerializer {

    private static final String mFilterPattern = "^\\|[ \\t]*\\(.+\\)$";

    @Override
    public OrFilter serialize(String filterString) throws FilterSerializerFactory.FilterSerializationException {
        if (filterString == null) throw new IllegalArgumentException(
                this.getClass().getName() + "null string passed"
        );

        if (!Pattern.matches(mFilterPattern, filterString.trim())) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new OrFilter(FilterParser.getChildren(filterString));
    }

    public String serialize(IFilter filter) throws FilterSerializerFactory.FilterSerializationException {
        OrFilter orFilter = OrFilter.class.cast(filter);
        String result =  filter.getPrefix() + "(";

        for (IFilter child : orFilter.getChildFilters()) {
            result += (" " + FilterSerializerFactory.create(child.getPrefix()).serialize(child));
        }

        return result + " )";
    }
}
