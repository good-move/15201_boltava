package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class CompositeFilter implements IFilter {

    protected List<IFilter> mChildFilters;

    public CompositeFilter() {
        mChildFilters = new ArrayList<IFilter>();
    }

    public abstract boolean check(Path fileName);

    void add(IFilter filter) { this.mChildFilters.add(filter); };

    void remove() {};

}
