package ru.nsu.ccfit.boltava.filter.leaf;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class LessLastModifiedFilter implements IFilter {

    private static final String prefix = "<";
    private final FileTime mTimeStamp;

    public LessLastModifiedFilter(Long timestamp) {
        mTimeStamp = FileTime.fromMillis(timestamp * 1000);
    }

    @Override
    public boolean check(Path filePath) throws IOException {
            return Files.getLastModifiedTime(filePath).compareTo(mTimeStamp) < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LessLastModifiedFilter that = (LessLastModifiedFilter) o;

        return mTimeStamp != null ? mTimeStamp.equals(that.mTimeStamp) : that.mTimeStamp == null;
    }

    @Override
    public int hashCode() {
        return mTimeStamp != null ? mTimeStamp.hashCode() : 0;
    }

    @Override
    public String getPrefix() { return prefix; }

    @Override
    public String toString() { return prefix + (mTimeStamp.toMillis()/1000); }
}
