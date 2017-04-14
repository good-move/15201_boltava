package ru.nsu.ccfit.boltava.filter.leaf;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Path;

public class FileExtensionFilter implements IFilter {

    public static final String prefix = ".";
    private final String mExtension;
    private static Pattern mPattern = Pattern.compile(".*/*.*?(\\..*)");

    public FileExtensionFilter(String extension) {
        if (extension == null) throw new IllegalArgumentException("Null pointer passed");
        extension = extension.trim();
        mExtension = extension.startsWith(".") ? extension.trim() : "." + extension;
    }

    @Override
    public boolean check(Path path) {
        if (path == null) throw new IllegalArgumentException("Null pointer passed");

        String filename = path.getFileName().toString();
        int dotIndex = filename.lastIndexOf(".");

        return dotIndex != -1 && filename.substring(dotIndex).equals(mExtension);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FileExtensionFilter that = (FileExtensionFilter) o;

        return mExtension != null ? mExtension.equals(that.mExtension) : that.mExtension == null;
    }

    @Override
    public int hashCode() {
        return mExtension != null ?  prefix.hashCode() * mExtension.hashCode() : 0;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    @Override
    public String toString() {
        return mExtension;
    }
}
