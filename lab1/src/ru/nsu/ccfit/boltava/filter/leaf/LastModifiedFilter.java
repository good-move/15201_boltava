package ru.nsu.ccfit.boltava.filter.leaf;

import org.jetbrains.annotations.Contract;
import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.io.IOException;
import java.nio.file.Files;

public class LastModifiedFilter implements IFilter {

    private final FileTime mTimeStamp;
    private final Comparator mComparator;

    @Contract("null, _ -> fail; !null, null -> fail")
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

    public enum Comparator { BEFORE, AFTER };

}
