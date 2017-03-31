package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedEarlierFilter;

import java.util.regex.Pattern;

public class ModifiedEarlierFilterSerializer implements IFilterSerializer {

    private static final String mFilterPattern = "^<[ \\t]*\\d+$";;

    @Override
    public ModifiedEarlierFilter serialize(String filterString) {
        filterString = filterString.trim();
        if (!Pattern.matches(mFilterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        Long timestamp = Long.parseLong(filterString.substring(1).trim());

        return new ModifiedEarlierFilter(timestamp);
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        return ModifiedEarlierFilter.class.cast(filter).toString();
    }

}
