package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.FILE_EXTENSION_FILTER_PATTERN;

import java.util.regex.Pattern;

public class FileExtensionFilterSerializer implements IFilterSerializer {

    @Override
    public FileExtensionFilter getFilter(String filterString) {
        if (filterString == null) throw new IllegalArgumentException();

        filterString = filterString.trim();
        if (!Pattern.matches(filterString, FILE_EXTENSION_FILTER_PATTERN)) {
            throw new IllegalArgumentException("Wrong filter format: " + filterString);
        }

        if (!filterString.startsWith(".")) {
            throw new IllegalArgumentException("Expected filter to start with '.' symbol");
        }

        return new FileExtensionFilter(filterString);
    }

}
