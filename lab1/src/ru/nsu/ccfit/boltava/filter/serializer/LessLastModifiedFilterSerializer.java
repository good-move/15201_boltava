package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.LessLastModifiedFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.LAST_MODIFIED_FILTER;

public class LessLastModifiedFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = LAST_MODIFIED_FILTER;

    @Override
    public LessLastModifiedFilter serialize(String filterString) {
        filterString = filterString.trim();
        if (!Pattern.matches(filterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        Long timestamp = Long.parseLong(filterString.substring(1).trim());

        return new LessLastModifiedFilter(timestamp);
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        return filter.toString();
    }

}
