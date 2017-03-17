package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedLaterFilter;

import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.MODIFIED_LATER_FILTER;

public class ModifiedLaterFilterSerializer implements IFilterSerializer {

    private static final String filterPattern = MODIFIED_LATER_FILTER;

    @Override
    public ModifiedLaterFilter serialize(String filterString) {
        filterString = filterString.trim();
        if (!Pattern.matches(filterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        Long timestamp = Long.parseLong(filterString.substring(1).trim());

        return new ModifiedLaterFilter(timestamp);
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        return ModifiedLaterFilter.class.cast(filter).toString();
    }

}
