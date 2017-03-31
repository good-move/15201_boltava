package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public final class OrFilter extends CompositeFilter {

    public static final String prefix = "|";

    public OrFilter(ArrayList<IFilter> children) { super(children); }

    @Override
    public boolean check(Path fileName) throws IllegalAccessException, IOException {
        if (fileName == null) throw new IllegalArgumentException("Null pointer passed as filename");

        for (IFilter filter : mChildFilters) {
            if (filter.check(fileName)) return true;
        }

        return false;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

}
