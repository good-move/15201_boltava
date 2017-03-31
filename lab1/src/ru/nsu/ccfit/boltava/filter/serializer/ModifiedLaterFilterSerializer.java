package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedLaterFilter;

import java.util.regex.Pattern;

public class ModifiedLaterFilterSerializer implements IFilterSerializer {

    private static final String mFilterPattern = "^>[ \\t]*\\d+$";

    @Override
    public ModifiedLaterFilter serialize(String filterString) {
        if (filterString == null) throw new IllegalArgumentException(
                this.getClass().getName() + "null string passed"
        );

        filterString = filterString.trim();
        if (!Pattern.matches(mFilterPattern, filterString)) {
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
