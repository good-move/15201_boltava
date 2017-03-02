package ru.nsu.ccfit.boltava.filter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileExtensionFilter implements IFilter {

    private final String mExtension;

    public FileExtensionFilter(String extension) {
        mExtension = extension;
    }

    public boolean check(Path path) {
        Matcher matcher =  Pattern
                            .compile(".*/*.*?(\\..*)")
                            .matcher(path.toString());

        return matcher.matches() && matcher.group(1).equals(mExtension);
    }

}
