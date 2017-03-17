package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.NotFilter;

import static org.junit.Assert.assertEquals;

public class NotFilterSerializerTest {

    private String[] validBodies;
    private String[] invalidBodies;

    @Before
    public void setUp() throws Exception {
        validBodies = new String[] {
                "!(.txt)",
                "  !(.txt)  ",
                " !   (  .       cpp   )  "
        };

        invalidBodies = new String[] {
                "!(.txt .txt)",
                "  |  ( o d d )  ",
                "a/b.java",
                "a/b/c/d/e/f/g.py",
                "java",
                "!(.txt .java)",
                " |     (  <   5555 >  9 .)",
                " !     (  <   5555 | (>  9 . )  )"
        };

    }

    @Test
    public void expectThrowIllegalArgument() {

        for (String wrongFormat : invalidBodies) {
            try {
                new NotFilterSerializer().serialize(wrongFormat);
            } catch (IllegalArgumentException e) {
                assertEquals("Wrong filter format: " + wrongFormat, e.getMessage());
            }
        }

    }

    @Test
    public void checkCreationOnValidPatterns() {
        NotFilterSerializer s = new NotFilterSerializer();

        for (String filterBody : validBodies) {
            assertEquals(NotFilter.class, s.serialize(filterBody).getClass());
        }
    }

}