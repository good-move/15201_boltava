package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.leaf.LastModifiedFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.LAST_MODIFIED_FILTER_PATTERN;

public class LastModifiedFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = LAST_MODIFIED_FILTER_PATTERN;

    @Override
    public LastModifiedFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();

        filterString = filterString.trim();
        if (!Pattern.matches(filterString, filterPattern)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        LastModifiedFilter.Comparator comparator = filterString.startsWith("<") ?
                LastModifiedFilter.Comparator.BEFORE : LastModifiedFilter.Comparator.AFTER;

        String timestamp = filterString.substring(1);

        return new LastModifiedFilter(comparator, timestamp);
    }

}
