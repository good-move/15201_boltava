package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class NotFilter extends CompositeFilter {

    private static final String prefix = "!";

    public NotFilter(IFilter child) {
        super(new ArrayList<IFilter>(Collections.singletonList(child)));
    }

    @Override
    public boolean check(Path fileName) throws IllegalAccessException, IOException {
        return !mChildFilters.get(0).check(fileName);
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

}
