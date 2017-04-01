package ru.nsu.ccfit.boltava.filter.leaf;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

public class ModifiedEarlierFilter implements IFilter {

    public static final String prefix = "<";
    private final FileTime mTimeStamp;

    public ModifiedEarlierFilter(Long timestamp) {
        if (timestamp == null) throw new IllegalArgumentException(
                this.getClass().getName() +  ": Null pointer timestamp passed"
        );

        mTimeStamp = FileTime.fromMillis(timestamp * 1000);
    }

    @Override
    public boolean check(Path filePath) throws IOException {
        if (filePath == null) throw new IllegalArgumentException(
                this.getClass().getName() +  ": null path"
        );

        return Files.getLastModifiedTime(filePath).compareTo(mTimeStamp) < 0;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModifiedEarlierFilter that = (ModifiedEarlierFilter) o;

        return mTimeStamp != null ? mTimeStamp.equals(that.mTimeStamp) : that.mTimeStamp == null;
    }


    @Override
    public String getPrefix() { return prefix; }

    @Override
    public String toString() { return prefix + (mTimeStamp.toMillis()/1000); }

    @Override
    public int hashCode() {
        return prefix.hashCode() * mTimeStamp.hashCode();
    }
}
