package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.composite.NotFilter;
import ru.nsu.ccfit.boltava.filter.composite.OrFilter;

import static org.junit.Assert.assertEquals;

public class NotFilterSerializerTest {

    private String[] validBodies;
    private String[] invalidBodies;

    @Before
    public void setUp() throws Exception {
        validBodies = new String[] {
                "!(sequence)",
                "  !(sequence)  ",
                "  !  ( e v e n)  ",
                " !  ( f1   )  ",
                "  ! (  .h )",
                " !   (  .       cpp   )  "
        };

        invalidBodies = new String[] {
                "!(sequence)",
                "  |  ( o d d )  ",
                "a/b.java",
                "a/b/c/d/e/f/g.py",
                "java",
                " |     (  <   5555 >  9 .)",
                " !     (  <   5555 | (>  9 . )  )"
        };

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void expectThrowOnNull() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Null pointer argument passed");
        new NotFilterSerializer().getFilter(null);
    }

    @Test
    public void expectThrowIllegalArgument() {

        for (String wrongFormat : invalidBodies) {
            try {
                new NotFilterSerializer().getFilter(wrongFormat);
            } catch (IllegalArgumentException e) {
                assertEquals("Wrong filter format: " + wrongFormat, e.getMessage());
            }
        }

    }

    @Test
    public void checkCreationOnValidPatterns() {
        NotFilterSerializer s = new NotFilterSerializer();

        for (String filterBody : validBodies) {
            assertEquals(NotFilter.class, s.getFilter(filterBody).getClass());
        }
    }

    @Test
    public void checkAddExtra() throws IllegalAccessException {
        NotFilterSerializer s = new NotFilterSerializer();
        IFilter filter = s.getFilter("!(.java)");
        try {
            filter.add(new FileExtensionFilterSerializer().getFilter(".java"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        thrown.expect(IllegalStateException.class);
        filter.add(new FileExtensionFilterSerializer().getFilter(".java"));

    }

}