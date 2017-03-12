package ru.nsu.ccfit.boltava.filter;

import ru.nsu.ccfit.boltava.filter.composite.CompositeFilter;

import java.nio.file.Path;

public interface IFilter {
    boolean check(Path fileName) throws IllegalAccessException;
    default void add(IFilter filter) throws IllegalAccessException {
        throw new IllegalAccessException("Cannot add elements to primitive structures");
    };
}
