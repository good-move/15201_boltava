package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import java.util.regex.Pattern;

public class FileExtensionFilterSerializer implements IFilterSerializer {

    private static final String mFilterPattern = "^\\.[ \\t]*[^\\s()]+";

    @Override
    public FileExtensionFilter serialize(String filterString) {
        filterString = filterString.trim();

        if (!Pattern.matches(mFilterPattern, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new FileExtensionFilter(filterString.substring(1).trim());
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        return FileExtensionFilter.class.cast(filter).toString();
    }

}
