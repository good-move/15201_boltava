package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.composite.OrFilter;

import static org.junit.Assert.assertEquals;

public class OrFilterSerializerTest {

    private String[] validBodies;
    private String[] invalidBodies;

    @Before
    public void setUp() throws Exception {
        validBodies = new String[] {
                "|(.txt)",
                "  |(.txt .txt)  ",
                "  |  ( . txt   .  java   )  ",
                "  |(  &   ( .txt . java  ) <  1 )",
                " |   (  .       cpp   )  "
        };

        invalidBodies = new String[] {
                "&(sequence)",
                "  |  ( o d d )  ",
                "a/b.java",
                "a/b/c/d/e/f/g.py",
                "java",
                " |     (  <   5555 >  9 .)",
                " |     (  <   5555 | (>  9 . )  )"
        };

    }

    @Test
    public void expectThrowIllegalArgument() {

        for (String wrongFormat : invalidBodies) {
            try {
                new OrFilterSerializer().serialize(wrongFormat);
            } catch (IllegalArgumentException e) {
                assertEquals("Wrong filter format: " + wrongFormat, e.getMessage());
            }
        }

    }

    @Test
    public void checkCreationOnValidPatterns() {
        OrFilterSerializer s = new OrFilterSerializer();

        for (String filterBody : validBodies) {
            assertEquals(OrFilter.class, s.serialize(filterBody).getClass());
        }
    }

}