package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.IFilter;

public interface IFilterSerializer {

    abstract IFilter serialize(String filterString) throws IllegalArgumentException, FilterSerializerFactory.FilterSerializationException;
    abstract String serialize(IFilter filter) throws IllegalArgumentException, FilterSerializerFactory.FilterSerializationException;

}
