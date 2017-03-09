package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.FileExtensionFilter;
import ru.nsu.ccfit.boltava.filter.leaf.LastModifiedFilter;

import java.nio.file.Paths;
import java.security.Timestamp;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LastModifiedFilterSerializerTest {

    private String[] validBodies;
    private String[] validTxtPaths;

    private String[] afterFiles;
    private String helpersPath = "test/ru/nsu/ccfit/boltava/filter/LastModifiedFilterHelpers";


    @Before
    public void setUp() throws Exception {
        validBodies = new String[] {
                "<123",
                ">123",
                "<      123",
                ">  123",
                "  <   12321335345345345   ",
                "    >   123213   "
        };

        afterFiles = new String[] {
                "/afterTimeStamp1.txt",
                "/afterTimeStamp2.txt"
        };

        for (int i = 0; i < afterFiles.length; ++i) {
            afterFiles[i] = helpersPath + afterFiles[i];
        }

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
                "+123",
                "434",
                "<",
                ">",
                "< abc",
                "> abc",
                ">  123letters456",
                "<123letters456",
                "<let123",
                ">let123",
                "<123 456",
                "<>564"
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
        LastModifiedFilterSerializer s = new LastModifiedFilterSerializer();

        for (String filterBody : validBodies) {
            assertEquals(LastModifiedFilter.class, s.getFilter(filterBody).getClass());
        }
    }

    @Test
    public void shouldFilterWorkCorrectly() {
        LastModifiedFilterSerializer s = new LastModifiedFilterSerializer();
        String filterBody = "   >       " + 1480000000 + "     ";

        IFilter filter = s.getFilter(filterBody);

        assertEquals(LastModifiedFilter.class, filter.getClass());

        for (String stringPath : afterFiles) {
            assertEquals(true, filter.check(Paths.get(stringPath)));
        }

    }

    @Test
    public void checkNowTimestamp() {
        LastModifiedFilterSerializer s = new LastModifiedFilterSerializer();
        long nowTimeStamp = new Date().getTime() / 1000;
        String filterBody = "   <       " + nowTimeStamp + "     ";

        IFilter filter = s.getFilter(filterBody);

        assertEquals(LastModifiedFilter.class, filter.getClass());

        for (String stringPath : afterFiles) {
            assertEquals(true, filter.check(Paths.get(stringPath)));
        }

    }

}