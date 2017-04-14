package ru.nsu.ccfit.boltava.filter.serializer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedLaterFilter;
import ru.nsu.ccfit.boltava.filter.leaf.ModifiedEarlierFilter;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class LastModifiedFilterSerializerTest {

    private String[] validBodiesGreater;
    private String[] validBodiesLess;
    private String[] validTxtPaths;
    private String[] timestampFiles;


    @Before
    public void setUp() throws Exception {
        timestampFiles = new String[] {
                "/beforeTimeStamp1.txt",
                "/beforeTimeStamp2.txt",
                "/afterTimeStamp1.txt",
                "/afterTimeStamp1.txt"
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
        for (int i = 0; i < timestampFiles.length; ++i) {
            timestampFiles[i] = helpersPath + timestampFiles[i];
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
                "<>564",
                " .   676"
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
        ModifiedLaterFilterSerializer s = new ModifiedLaterFilterSerializer();

        for (String filterBody : validBodiesGreater) {
            assertEquals(ModifiedLaterFilter.class, s.serialize(filterBody).getClass());
        }
    }

    @Test
    public void checkLessCreationOnValidPatterns() {
        ModifiedEarlierFilterSerializer s = new ModifiedEarlierFilterSerializer();

        for (String filterBody : validBodiesLess) {
            assertEquals(ModifiedEarlierFilter.class, s.serialize(filterBody).getClass());
        }
    }


    @Test
    public void checkNowTimestamp() {
        ModifiedEarlierFilterSerializer s = new ModifiedEarlierFilterSerializer();
        long nowTimeStamp = new Date().getTime() / 1000;
        String filterBody = "   <       " + nowTimeStamp + "     ";

        IFilter filter = s.serialize(filterBody);

        assertEquals(ModifiedEarlierFilter.class, filter.getClass());

        try {
            for (String stringPath : timestampFiles) {
                assertEquals(true, filter.check(Paths.get(stringPath)));
            }
        } catch (IllegalAccessException | IOException e) {
                e.printStackTrace();
            }
    }

}