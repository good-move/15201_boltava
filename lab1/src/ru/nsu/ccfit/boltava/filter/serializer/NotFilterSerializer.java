package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.composite.AndFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.NOT_FILTER_PATTERN;

public class NotFilterSerializer implements IFilterSerializer {

    @Override
    public AndFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();
        if (!Pattern.matches(filterString.trim(), NOT_FILTER_PATTERN)) {
            throw new IllegalArgumentException("Wrong filter format");
        }

        return new AndFilter();
    }
}
