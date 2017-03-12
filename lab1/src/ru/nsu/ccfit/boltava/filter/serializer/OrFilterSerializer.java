package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.composite.OrFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.OR_FILTER;

public class OrFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = OR_FILTER;

    @Override
    public OrFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException("Null pointer argument passed");
        if (!Pattern.matches(filterPattern, filterString.trim())) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new OrFilter();
    }
}
