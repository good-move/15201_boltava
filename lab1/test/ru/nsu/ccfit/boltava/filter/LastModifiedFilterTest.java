package ru.nsu.ccfit.boltava.filter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedEarlierFilter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.Assert.*;

public class LastModifiedFilterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String[] beforeFiles;

    @Before
    public void setUp() throws Exception {

        beforeFiles = new String[] {
                "/beforeTimeStamp1.txt",
                "/beforeTimeStamp2.txt"
        };

        final String helpersPath = "test/ru/nsu/ccfit/boltava/filter/LastModifiedFilterHelpers";
        for (int i = 0; i < beforeFiles.length; ++i) {
            beforeFiles[i] = helpersPath + beforeFiles[i];
        }

    }

    @Test
    public void checkBeforeTimeStamp() {

        final String validTimeStamp = "1488739799";

        ModifiedEarlierFilter filter = new ModifiedEarlierFilter(new Date().getTime()/1000);

        for (String stringPath : beforeFiles) {
            try {
                assertEquals(true, filter.check(Paths.get(stringPath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}