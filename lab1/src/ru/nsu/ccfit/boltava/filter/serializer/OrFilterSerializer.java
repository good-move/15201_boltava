package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.composite.OrFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.OR_FILTER_PATTERN;

public class OrFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = OR_FILTER_PATTERN;

    @Override
    public OrFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();
        if (!Pattern.matches(filterString.trim(), filterPattern)) {
            throw new IllegalArgumentException("Wrong filter format");
        }

        return new OrFilter();
    }
}
