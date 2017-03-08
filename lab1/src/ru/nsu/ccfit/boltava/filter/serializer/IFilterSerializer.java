package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;

public interface IFilterSerializer {

    IFilter getFilter(String filterString) throws IllegalArgumentException;
}
