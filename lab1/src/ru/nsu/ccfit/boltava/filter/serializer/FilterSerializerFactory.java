package ru.nsu.ccfit.boltava.filter.serializer;

import java.util.HashMap;
import java.util.UnknownFormatConversionException;

public class FilterSerializerFactory {


    private static final HashMap<String, String> mSerializers;

    static {
        mSerializers = new HashMap<>();
    }

    public static IFilterSerializer create(String prefix) {

        switch (prefix) {
            case ".": return new FileExtensionFilterSerializer();
            case "<": return new LessLastModifiedFilterSerializer();
            case ">": return new GreaterLastModifiedFilterSerializer();
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
