package ru.nsu.ccfit.boltava;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileExtensionFilter implements IFilter {

    private final String mExtension;

    public FileExtensionFilter(String extension) {
        mExtension = extension;
    }

    public boolean check(Path fileName) {
        return Pattern.matches("("+mExtension+")$", fileName.toString());
    }

}
