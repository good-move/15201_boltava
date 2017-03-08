package ru.nsu.ccfit.boltava.filter;

import org.junit.Before;
import org.junit.Test;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class FileExtensionFilterTest {

    private String[] validTxtPaths;
    private String[] invalidTxtPaths;

    @Before
    public void setUp() throws Exception {
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

    @Test
    public void checkTxtWithoutDot() throws Exception {
        FileExtensionFilter filter = new FileExtensionFilter("txt");

        for (String stringPath : validTxtPaths) {
            assertEquals(true, filter.check(Paths.get(stringPath)));
        }

        for (String stringPath : invalidTxtPaths) {
            assertEquals(false, filter.check(Paths.get(stringPath)));
        }

    }

    @Test
    public void checkTxtWithDot() {
        FileExtensionFilter filter = new FileExtensionFilter(".txt");

        for (String stringPath : validTxtPaths) {
            assertEquals(true, filter.check(Paths.get(stringPath)));
        }

        for (String stringPath : invalidTxtPaths) {
            assertEquals(false, filter.check(Paths.get(stringPath)));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNull() {
        FileExtensionFilter filter = new FileExtensionFilter(null);
        assertEquals(false, filter.check(Paths.get("any")));
    }
}