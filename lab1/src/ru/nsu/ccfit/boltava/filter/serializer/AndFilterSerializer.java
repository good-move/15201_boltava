package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.composite.AndFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.AND_FILTER_PATTERN;

public class AndFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = AND_FILTER_PATTERN;

    @Override
    public AndFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();
        if (!Pattern.matches(filterString.trim(), filterPattern)) {
            throw new IllegalArgumentException("Wrong filter format");
        }

        return new AndFilter();
    }
}
