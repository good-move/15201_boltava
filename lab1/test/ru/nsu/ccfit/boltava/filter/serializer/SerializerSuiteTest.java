package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.nsu.ccfit.boltava.filter.FileExtensionFilterTest;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;
import ru.nsu.ccfit.boltava.filter.parser.FilterParserTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AndFilterSerializerTest.class,
        FileExtensionFilterTest.class,
        LastModifiedFilterSerializerTest.class,
        NotFilterSerializerTest.class,
        OrFilterSerializerTest.class
})
public class SerializerSuiteTest {}
