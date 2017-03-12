package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;

public class NotFilter extends CompositeFilter {

    @Override
    public void add(IFilter filter) {
        if (mChildFilters.size() == 0) {
            super.add(filter);
            return;
        }

        throw new IllegalStateException("Not filter cannot contain more than one child filter");
    }

    @Override
    public boolean check(Path fileName) throws IllegalAccessException {
        if (mChildFilters.size() == 0) {
            throw new IllegalAccessError("Filter is not set up");
        }

        return !mChildFilters.get(0).check(fileName);
    }

}
