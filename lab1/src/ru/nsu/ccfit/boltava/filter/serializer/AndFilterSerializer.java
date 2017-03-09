package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.composite.AndFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.AND_FILTER;

public class AndFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = AND_FILTER;

    @Override
    public AndFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();
        if (!Pattern.matches(filterPattern, filterString.trim())) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new AndFilter();
    }
}
