package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class CompositeFilter implements IFilter {

    List<IFilter> mChildFilters;

    CompositeFilter(ArrayList<IFilter> children) {
        if (children.size() == 0) {
            throw new IllegalArgumentException("Composite filter must not be empty");
        }
        mChildFilters = children;
    }

    public abstract boolean check(Path fileName) throws IllegalAccessException, IOException;

    public List<IFilter> getChildFilters() {
        return mChildFilters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompositeFilter that = (CompositeFilter) o;

        return mChildFilters != null ? mChildFilters.equals(that.mChildFilters) : that.mChildFilters == null;
    }

    @Override
    public int hashCode() {
        return mChildFilters != null ? mChildFilters.hashCode() : 0;
    }

}
