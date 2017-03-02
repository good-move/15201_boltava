package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;

public class NotFilter extends CompositeFilter {

    @Override
    public void add(IFilter filter) {
        if (mChildFilters.size() == 0) {
            super.add(filter);
        }
        // throw exception!
    }

    @Override
    public boolean check(Path fileName) {
        return mChildFilters.size() > 0 &&
                !mChildFilters.get(0).check(fileName);
    }

}
