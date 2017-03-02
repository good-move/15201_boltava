package ru.nsu.ccfit.boltava.filter.composite;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.util.ArrayList;

public abstract class CompositeFilter implements IFilter {

    protected ArrayList<IFilter> mChildFilters;

    public abstract boolean check(Path fileName);

    void add(IFilter filter) { this.mChildFilters.add(filter); };

    void remove() {};

}
