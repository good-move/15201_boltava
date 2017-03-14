package ru.nsu.ccfit.boltava.filter.leaf;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.io.IOException;
import java.nio.file.Files;

public class LastModifiedFilter implements IFilter {

    private final FileTime mTimeStamp;
    private final Comparator mComparator;

    public LastModifiedFilter(Comparator configuration, String timestamp) throws NumberFormatException {
        if (configuration == null || timestamp == null) {
            throw new IllegalArgumentException();
        }

        mComparator = configuration;
        mTimeStamp = FileTime.fromMillis(Long.parseLong(timestamp) * 1000);
    }

    @Override
    public boolean check(Path filePath) {
        try {
            FileTime time = Files.getLastModifiedTime(filePath);
            int result = time.compareTo(mTimeStamp);

            return mComparator == Comparator.BEFORE ? result < 0 : result > 0;
        } catch (IOException e) {
            System.err.print(e.getMessage());

            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastModifiedFilter that = (LastModifiedFilter) o;

        if (mTimeStamp != null ? !mTimeStamp.equals(that.mTimeStamp) : that.mTimeStamp != null)
            return false;
        return mComparator == that.mComparator;
    }

    @Override
    public int hashCode() {
        int result = mTimeStamp != null ? mTimeStamp.hashCode() : 0;
        result = 31 * result + (mComparator != null ? mComparator.hashCode() : 0);
        return result;
    }

    public enum Comparator { BEFORE, AFTER };

}
