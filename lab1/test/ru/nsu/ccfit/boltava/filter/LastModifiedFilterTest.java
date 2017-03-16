package ru.nsu.ccfit.boltava.filter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.leaf.GreaterLastModifiedFilter;
import ru.nsu.ccfit.boltava.filter.leaf.LessLastModifiedFilter;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class LastModifiedFilterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String[] beforeFiles;
    private String[] afterFiles;

    @Before
    public void setUp() throws Exception {
        afterFiles = new String[] {
                 "/afterTimeStamp1.txt",
                 "/afterTimeStamp2.txt"
        };

        beforeFiles = new String[] {
                "/beforeTimeStamp1.txt",
                "/beforeTimeStamp2.txt"
        };

        final String helpersPath = "test/ru/nsu/ccfit/boltava/filter/LastModifiedFilterHelpers";
        for (int i = 0; i < afterFiles.length; ++i) {
            afterFiles[i] = helpersPath + afterFiles[i];
            beforeFiles[i] = helpersPath + beforeFiles[i];
        }

    }


    @Test
    public void checkBeforeTimeStamp() {

        final String validTimeStamp = "1488739799";

        LessLastModifiedFilter filter = new LessLastModifiedFilter(1488739799L);

        for (String stringPath : beforeFiles) {
            try {
                assertEquals(true, filter.check(Paths.get(stringPath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void checkAfterTimeStamp() {

        final String validTimeStamp = "1480000000";

        GreaterLastModifiedFilter filter = new GreaterLastModifiedFilter(1480000000L);

        for (String stringPath : afterFiles) {
            try {
                assertEquals(true, filter.check(Paths.get(stringPath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}