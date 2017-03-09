package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.composite.AndFilter;
import ru.nsu.ccfit.boltava.filter.composite.NotFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.NOT_FILTER;

public class NotFilterSerializer implements IFilterSerializer {

    @Override
    public NotFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();
        if (!Pattern.matches(NOT_FILTER, filterString.trim())) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new NotFilter();
    }
}
