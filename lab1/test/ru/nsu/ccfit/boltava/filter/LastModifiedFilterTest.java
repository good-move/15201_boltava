package ru.nsu.ccfit.boltava.filter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.leaf.LastModifiedFilter;

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
    public void checkNullConfig() {
        thrown.expect(IllegalArgumentException.class);
        LastModifiedFilter filter = new LastModifiedFilter(null, "123");
    }

    @Test
    public void checkNullTimestamp() {
        thrown.expect(IllegalArgumentException.class);
        LastModifiedFilter filter = new LastModifiedFilter(LastModifiedFilter.Comparator.BEFORE, null);
    }

    @Test
    public void checkEmptyTimestamp() {
        thrown.expect(NumberFormatException.class);
        LastModifiedFilter filter = new LastModifiedFilter(LastModifiedFilter.Comparator.BEFORE, "");
    }

    @Test
    public void checkInvalidTimestamp() {
        thrown.expect(NumberFormatException.class);
        LastModifiedFilter filter = new LastModifiedFilter(LastModifiedFilter.Comparator.BEFORE, "sdf");
    }

    @Test
    public void checkBeforeTimeStamp() {

        final String validTimeStamp = "1488739799";

        LastModifiedFilter filter = new LastModifiedFilter(
                LastModifiedFilter.Comparator.BEFORE,
                validTimeStamp
                );

        for (String stringPath : beforeFiles) {
            assertEquals(true, filter.check(Paths.get(stringPath)));
        }

    }

    @Test
    public void checkAfterTimeStamp() {

        final String validTimeStamp = "1480000000";

        LastModifiedFilter filter = new LastModifiedFilter(
                LastModifiedFilter.Comparator.AFTER,
                validTimeStamp
        );

        for (String stringPath : afterFiles) {
            assertEquals(true, filter.check(Paths.get(stringPath)));
        }

    }

}