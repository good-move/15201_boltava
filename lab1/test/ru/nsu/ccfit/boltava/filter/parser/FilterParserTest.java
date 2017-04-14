package ru.nsu.ccfit.boltava.filter.parser;

import org.junit.Test;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.AndFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;

import static org.junit.Assert.*;

public class FilterParserTest {

    @Test
    public void expectEqualFileExtensionFilter() {
        IFilter filter;

        try {
            filter = FilterParser.parse(".java");
            assertEquals(FileExtensionFilter.class, filter.getClass());

            filter = FilterParser.parse("  .java   ");
            assertEquals(FileExtensionFilter.class, filter.getClass());

            filter = FilterParser.parse("  .   java   ");
            assertEquals(FileExtensionFilter.class, filter.getClass());

            filter = FilterParser.parse("&(.txt .java)");
            assertEquals(AndFilter.class, filter.getClass());

            filter = FilterParser.parse("&(|(.java .txt)  |( <30 >50 &(.css   .   php) .ttt !(.java)))");
            assertEquals(AndFilter.class, filter.getClass());

            filter = FilterParser.parse("&  (  |  (  . java   . txt  ! (.zip) )  |  ( < 30 >  50 & ( . css   .   php) .ttt ! (.java)))");
            assertEquals(AndFilter.class, filter.getClass());
        } catch (FilterSerializerFactory.FilterSerializationException e){
            e.printStackTrace();
        }
    }

}