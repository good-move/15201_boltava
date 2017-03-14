package ru.nsu.ccfit.boltava.filter.serializer;

import java.util.UnknownFormatConversionException;

public class FilterSerializerFactory {

    public IFilterSerializer get(String prefix) {

        switch (prefix) {
            case ".": return new FileExtensionFilterSerializer();
            case "<":
            case ">": return new LastModifiedFilterSerializer();
            case "&": return new AndFilterSerializer();
            case "|": return new OrFilterSerializer();
            case "!": return new NotFilterSerializer();
            default:
                throw new UnknownFormatConversionException(
                        "Unknown filter prefix: " + prefix
                );
        }
    }

}
