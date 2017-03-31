package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public final class NotFilter extends CompositeFilter {

    public static final String prefix = "!";

    public NotFilter(IFilter child) {
        super(new ArrayList<>(Collections.singletonList(child)));
    }

    @Override
    public boolean check(Path fileName) throws IllegalAccessException, IOException {
        if (fileName == null) throw new IllegalArgumentException("Null pointer passed as filename");

        return !mChildFilters.get(0).check(fileName);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

}
