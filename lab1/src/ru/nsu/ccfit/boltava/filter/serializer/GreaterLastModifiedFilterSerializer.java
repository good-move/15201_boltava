package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.GreaterLastModifiedFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.LAST_MODIFIED_FILTER;

public class GreaterLastModifiedFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = LAST_MODIFIED_FILTER;

    @Override
    public GreaterLastModifiedFilter serialize(String filterString) {
        filterString = filterString.trim();
        if (!Pattern.matches(filterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        Long timestamp = Long.parseLong(filterString.substring(1).trim());

        return new GreaterLastModifiedFilter(timestamp);
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        return filter.toString();
    }

}
