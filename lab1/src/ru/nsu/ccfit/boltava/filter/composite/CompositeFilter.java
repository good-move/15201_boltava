package ru.nsu.ccfit.boltava.filter.composite;

import org.jetbrains.annotations.Contract;
import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class CompositeFilter implements IFilter {

    List<IFilter> mChildFilters;

    CompositeFilter() {
        mChildFilters = new ArrayList<IFilter>();
    }

    public abstract boolean check(Path fileName) throws IllegalAccessException;

    @Override
    public void add(IFilter filter) { this.mChildFilters.add(filter); };

}
