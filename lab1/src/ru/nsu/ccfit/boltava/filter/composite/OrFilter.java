package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;

public class OrFilter extends CompositeFilter {

    @Override
    public boolean check(Path fileName) {
        for (IFilter filter : mChildFilters) {
            if (filter.check(fileName)) return true;
        }

        return false;
    }

}
