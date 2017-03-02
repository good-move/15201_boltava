package ru.nsu.ccfit.boltava.filter;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.io.IOException;
import java.nio.file.Files;

public class LastModifiedFilter implements IFilter {

    private final FileTime mTimeStamp;
    private final Comparator mComparator;

    public LastModifiedFilter(Comparator configuration, String timestamp) {
        mComparator = configuration;
        mTimeStamp = FileTime.fromMillis(Long.parseLong(timestamp) * 1000);
    }

    public boolean check(Path filePath) {
        try {
            FileTime time = Files.getLastModifiedTime(filePath);
            int result = time.compareTo(mTimeStamp);

            return mComparator == Comparator.LESS ? result < 0 : result > 0;
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
    }

    public enum Comparator { LESS, GREATER };

}
