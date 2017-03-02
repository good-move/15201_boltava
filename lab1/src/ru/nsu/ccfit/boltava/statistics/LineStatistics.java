package ru.nsu.ccfit.boltava.statistics;

import ru.nsu.ccfit.boltava.filter.IFilter;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public class LineStatistics extends Statistics<Path, String, Integer> {

    @Override
    public boolean update(String filterID, Integer linesCount) {
        Integer currentValue = super.get(filterID);
        Integer newValue = currentValue == null ?
                            linesCount : currentValue + linesCount;
        System.out.println(newValue);
        return true;
    }

    public String getStats() {
        return "You will see statistics here";
    }

    // class Pair to keep <int, int>: Nfiles and Nlines

}
