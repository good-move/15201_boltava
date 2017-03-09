package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class AndFilterSerializerTest {

    private String[] validBodies;
    private String[] invalidBodies;

    @Before
    public void setUp() throws Exception {
        validBodies = new String[] {
                "&(sequence)",
                "  &(sequence)  ",
                "  &  ( e v e n   chars)  ",
                "  &  ( f1 f2    f  3 )  ",
                "    .h ",
                "   .       cpp     "
        };

        invalidBodies = new String[] {
                "|(sequence)",
                "  &  ( o d d )  ",

                "a/b.java",
                "a/b/c/d/e/f/g.py"
        };

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void expectThrowOnNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Null pointer argument passed");
        new FileExtensionFilterSerializer().getFilter(null);
    }

    @Test
    public void expectThrowIllegalArgument() {

        String[] wrongFormats = new String[] {
                ".java file",
                ",java",
                ".",
                "",
                "java",
                "ja va"
        };

        for (String wrongFormat : wrongFormats) {
            try {
                new FileExtensionFilterSerializer().getFilter(wrongFormat);
            } catch (IllegalArgumentException e) {
                assertEquals("Wrong filter format: " + wrongFormat, e.getMessage());
            }
        }

    }

    @Test
    public void checkCreationOnValidPatterns() {
        FileExtensionFilterSerializer s = new FileExtensionFilterSerializer();

        for (String filterBody : validBodies) {
            assertEquals(FileExtensionFilter.class, s.getFilter(filterBody).getClass());
        }
    }

    @Test
    public void shouldFilterWorkCorrectly() {
        FileExtensionFilterSerializer s = new FileExtensionFilterSerializer();
        String filterBody = "   .       txt     ";

        IFilter filter = s.getFilter(filterBody);

        assertEquals(FileExtensionFilter.class, filter.getClass());

        for (String stringPath : invalidBodies) {
            assertEquals(false, filter.check(Paths.get(stringPath)));
        }
    }

}