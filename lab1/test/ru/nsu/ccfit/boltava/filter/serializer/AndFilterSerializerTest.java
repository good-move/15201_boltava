package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.composite.AndFilter;

import static org.junit.Assert.assertEquals;

public class AndFilterSerializerTest {

    private String[] validBodies;
    private String[] invalidBodies;

    @Before
    public void setUp() throws Exception {
        validBodies = new String[] {
                "&(.txt)",
                "  &(.txt .txt)  ",
                "  &  ( . txt   .  java   )  ",
                "  &(  &   ( .txt . java  ) <  1 )",
                " &   (  .       cpp   )  "
        };

        invalidBodies = new String[] {
                "|(sequence)",
                "  &  ( o d d )  ",
                "a/b.java",
                "a/b/c/d/e/f/g.py",
                "java",
                " &     (  <   5555 >  9 .)",
                " &     (  <   5555 | (>  9 . )  )"
        };

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void expectThrowIllegalArgument() {

        for (String wrongFormat : invalidBodies) {
            try {
                new AndFilterSerializer().serialize(wrongFormat);
            } catch (IllegalArgumentException e) {
                assertEquals("Wrong filter format: " + wrongFormat.trim(), e.getMessage());
            } catch (FilterSerializerFactory.FilterSerializationException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void checkCreationOnValidPatterns() {
        AndFilterSerializer s = new AndFilterSerializer();

        for (String filterBody : validBodies) {
            try {
                assertEquals(AndFilter.class, s.serialize(filterBody).getClass());
            } catch (FilterSerializerFactory.FilterSerializationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

}