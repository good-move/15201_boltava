package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.leaf.LastModifiedFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.LAST_MODIFIED_FILTER;

public class LastModifiedFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = LAST_MODIFIED_FILTER;

    @Override
    public LastModifiedFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();

        filterString = filterString.trim();
        if (!Pattern.matches(filterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        LastModifiedFilter.Comparator comparator = filterString.startsWith("<") ?
                LastModifiedFilter.Comparator.BEFORE : LastModifiedFilter.Comparator.AFTER;

        String timestamp = filterString.substring(1).trim();

        return new LastModifiedFilter(comparator, timestamp);
    }

}
