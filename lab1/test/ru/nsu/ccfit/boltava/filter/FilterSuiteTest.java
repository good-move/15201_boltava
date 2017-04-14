package ru.nsu.ccfit.boltava.filter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.nsu.ccfit.boltava.filter.composite.CompositeSuiteTest;
import ru.nsu.ccfit.boltava.filter.parser.ParserSuiteTest;
import ru.nsu.ccfit.boltava.filter.serializer.SerializerSuiteTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        FileExtensionFilterTest.class,
        LastModifiedFilterTest.class,
        ParserSuiteTest.class,
        CompositeSuiteTest.class,
        SerializerSuiteTest.class
})
public class FilterSuiteTest {
}
