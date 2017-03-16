package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.CompositeFilter;

import java.util.ArrayList;

public interface IFilterSerializer {

    abstract IFilter serialize(String filterString) throws IllegalArgumentException;
    abstract String serialize(IFilter filter) throws IllegalArgumentException;

}
