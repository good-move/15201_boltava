package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.GreaterLastModifiedFilter;
import ru.nsu.ccfit.boltava.filter.leaf.LessLastModifiedFilter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LastModifiedFilterSerializerTest {

    private String[] validBodiesGreater;
    private String[] validBodiesLess;
    private String[] validTxtPaths;
    private String[] beforeFiles;


    @Before
    public void setUp() throws Exception {
        beforeFiles = new String[] {
                "/beforeTimeStamp1.txt",
                "/beforeTimeStamp2.txt"
        };

        validBodiesGreater = new String[] {
                ">123",
                "   >      123",
                "  >   12321335345345345   ",
                "    >   123213   "
        };

        validBodiesLess = new String[] {
                "<123",
                "<      123",
                "  <   12321335345345345   ",
        };


        String helpersPath = "test/ru/nsu/ccfit/boltava/filter/LastModifiedFilterHelpers";
        for (int i = 0; i < beforeFiles.length; ++i) {
            beforeFiles[i] = helpersPath + beforeFiles[i];
        }

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
                new FileExtensionFilterSerializer().serialize(wrongFormat);
            } catch (IllegalArgumentException e) {
                assertEquals("Wrong filter format: " + wrongFormat, e.getMessage());
            }
        }

    }

    @Test
    public void checkGreaterCreationOnValidPatterns() {
        GreaterLastModifiedFilterSerializer s = new GreaterLastModifiedFilterSerializer();

        for (String filterBody : validBodiesGreater) {
            assertEquals(GreaterLastModifiedFilter.class, s.serialize(filterBody).getClass());
        }
    }

    @Test
    public void checkLessCreationOnValidPatterns() {
        LessLastModifiedFilterSerializer s = new LessLastModifiedFilterSerializer();

        for (String filterBody : validBodiesLess) {
            assertEquals(LessLastModifiedFilter.class, s.serialize(filterBody).getClass());
        }
    }

    @Test
    public void shouldFilterWorkCorrectly() {
        GreaterLastModifiedFilterSerializer s = new GreaterLastModifiedFilterSerializer();
        String filterBody = "   <       " + 1480000000 + "     ";

        IFilter filter = s.serialize(filterBody);

        assertEquals(GreaterLastModifiedFilter.class, filter.getClass());

        try {
            for (String stringPath : beforeFiles) {
                assertEquals(true, filter.check(Paths.get(stringPath)));
            }
        } catch (IllegalAccessException | IOException e) {
                e.printStackTrace();
        }

    }

    @Test
    public void checkNowTimestamp() {
        LessLastModifiedFilterSerializer s = new LessLastModifiedFilterSerializer();
        long nowTimeStamp = new Date().getTime() / 1000;
        String filterBody = "   <       " + nowTimeStamp + "     ";

        IFilter filter = s.serialize(filterBody);

        assertEquals(LessLastModifiedFilter.class, filter.getClass());

        try {
            for (String stringPath : beforeFiles) {
                assertEquals(true, filter.check(Paths.get(stringPath)));
            }
        } catch (IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
    }

}