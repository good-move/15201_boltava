package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.FILE_EXTENSION_FILTER;

import java.util.regex.Pattern;

public class FileExtensionFilterSerializer implements IFilterSerializer {

    @Override
    public FileExtensionFilter serialize(String filterString) {
        filterString = filterString.trim();

        if (!Pattern.matches(FILE_EXTENSION_FILTER, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        return new FileExtensionFilter(filterString.substring(1).trim());
    }

    @Override
    public String serialize(IFilter filter) throws IllegalArgumentException {
        return filter.toString();
    }

}
