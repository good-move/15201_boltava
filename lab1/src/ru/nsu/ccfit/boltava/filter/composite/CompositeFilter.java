package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class CompositeFilter implements IFilter {

    List<IFilter> mChildFilters;
    public static String prefix;

    CompositeFilter() {
        mChildFilters = new ArrayList<IFilter>();
    }

    public abstract boolean check(Path fileName) throws IllegalAccessException;

    @Override
    public void add(IFilter filter) { this.mChildFilters.add(filter); };

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
