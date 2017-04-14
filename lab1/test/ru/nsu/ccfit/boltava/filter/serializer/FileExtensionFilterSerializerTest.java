package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class FileExtensionFilterSerializerTest {

    private String[] validBodies;
    private String[] validTxtPaths;
    private String[] invalidTxtPaths;

    @Before
    public void setUp() throws Exception {
        validBodies = new String[] {
                ".zip",
                "  .java",
                ".css  ",
                "    .h ",
                "   .       cpp     "
        };

        validTxtPaths = new String[] {
                "dir/sample.txt",
                "dir/sample2.txt",
                "a/b.txt",
                "a/b/c/d/e/f/g.txt"
        };

        invalidTxtPaths = new String[] {
                "dir/sample.css",
                "dir/sample2.js",
                "a/b.java",
                "a/b/c/d/e/f/g.py"
        };

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
                new FileExtensionFilterSerializer().serialize(wrongFormat);
            } catch (IllegalArgumentException e) {
                assertEquals("Wrong filter format: " + wrongFormat, e.getMessage());
            }
        }

    }

    @Test
    public void checkCreationOnValidPatterns() {
        FileExtensionFilterSerializer s = new FileExtensionFilterSerializer();

        for (String filterBody : validBodies) {
            assertEquals(FileExtensionFilter.class, s.serialize(filterBody).getClass());
        }
    }

    @Test
    public void shouldFilterWorkCorrectly() {
        FileExtensionFilterSerializer s = new FileExtensionFilterSerializer();
        String filterBody = "   .       txt     ";

        IFilter filter = s.serialize(filterBody);

        assertEquals(FileExtensionFilter.class, filter.getClass());

        try {
            for (String stringPath : validTxtPaths) {
                assertEquals(true, filter.check(Paths.get(stringPath)));
            }

            for (String stringPath : invalidTxtPaths) {
                assertEquals(false, filter.check(Paths.get(stringPath)));
            }
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}