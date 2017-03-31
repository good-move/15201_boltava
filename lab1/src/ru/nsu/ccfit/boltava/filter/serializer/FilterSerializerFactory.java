package ru.nsu.ccfit.boltava.filter.serializer;

import ru.nsu.ccfit.boltava.filter.composite.AndFilter;
import ru.nsu.ccfit.boltava.filter.composite.NotFilter;
import ru.nsu.ccfit.boltava.filter.composite.OrFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedLaterFilter;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedEarlierFilter;

import java.util.HashMap;

public class FilterSerializerFactory {

    private static final HashMap<String, Class<? extends IFilterSerializer>> mSerializers;

    static {
        mSerializers = new HashMap<>();
        mSerializers.put(FileExtensionFilter.prefix, FileExtensionFilterSerializer.class);
        mSerializers.put(ModifiedEarlierFilter.prefix, ModifiedEarlierFilterSerializer.class);
        mSerializers.put(ModifiedLaterFilter.prefix, ModifiedLaterFilterSerializer.class);
        mSerializers.put(AndFilter.prefix, AndFilterSerializer.class);
        mSerializers.put(OrFilter.prefix, OrFilterSerializer.class);
        mSerializers.put(NotFilter.prefix, NotFilterSerializer.class);
    }

    public static IFilterSerializer create(String prefix) throws FilterSerializationException {
        if (prefix == null) throw new IllegalArgumentException(
                "null prefix passed to factory"
        );

        try {
            return mSerializers.get(prefix).newInstance();
        } catch (InstantiationException e) {
            throw new FilterSerializationException("Couldn't instantiate serializer for prefix " + prefix);
        } catch (IllegalAccessException | NullPointerException e) {
            throw new FilterSerializationException("Unknown filter prefix: " + prefix);
        }

    }

    public static class FilterSerializationException extends Exception {

        public FilterSerializationException(){}

        FilterSerializationException(String message){
            super(message);
        }
    }

}
