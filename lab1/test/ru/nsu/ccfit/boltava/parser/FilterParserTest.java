package ru.nsu.ccfit.boltava.parser;

import org.junit.Test;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.AndFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import static org.junit.Assert.*;

public class FilterParserTest {

    @Test
    public void expectEqualFileExtensionFilter() {
        FilterParser filterParser = new FilterParser();
        IFilter filter;


        filter = filterParser.parse(".java");
        assertEquals(FileExtensionFilter.class, filter.getClass());

        filter = filterParser.parse("  .java   ");
        assertEquals(FileExtensionFilter.class, filter.getClass());

        filter = filterParser.parse("  .   java   ");
        assertEquals(FileExtensionFilter.class, filter.getClass());

        filter = filterParser.parse("&(.txt .java)");
        assertEquals(AndFilter.class, filter.getClass());

        filter = filterParser.parse("&(|(.java .txt)  |( <30 >50 &(.css   .   php) .ttt !(.java)))");
        assertEquals(AndFilter.class, filter.getClass());
    }

}