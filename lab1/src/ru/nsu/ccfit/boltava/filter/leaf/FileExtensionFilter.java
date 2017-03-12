package ru.nsu.ccfit.boltava.filter.leaf;

import org.jetbrains.annotations.Contract;
import ru.nsu.ccfit.boltava.filter.IFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Path;

public class FileExtensionFilter implements IFilter {

    private final String mExtension;
    private static Pattern mPattern = Pattern.compile(".*/*.*?(\\..*)");

    @Contract("null -> fail")
    public FileExtensionFilter(String extension) {
        if (extension == null) throw new IllegalArgumentException("Null pointer passed ");
        extension = extension.trim();
        mExtension = extension.startsWith(".") ? extension.trim() : "." + extension;
    }

    @Override
    public boolean check(Path path) {
        Matcher matcher = mPattern.matcher(path.toString());
        return matcher.matches() && matcher.group(1).equals(mExtension);
    }

}
