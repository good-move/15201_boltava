package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.FILE_EXTENSION_FILTER;

import java.util.regex.Pattern;

public class FileExtensionFilterSerializer implements IFilterSerializer {

    @Override
    public FileExtensionFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException("Null pointer argument passed");

        filterString = filterString.trim();

        if (!Pattern.matches(FILE_EXTENSION_FILTER, filterString)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        filterString = filterString.substring(1).trim();

        return new FileExtensionFilter(filterString);
    }

}
