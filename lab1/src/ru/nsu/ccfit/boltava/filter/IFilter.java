package ru.nsu.ccfit.boltava.filter;

import java.io.IOException;
import java.nio.file.Path;

public interface IFilter {
    boolean check(Path fileName) throws IllegalAccessException, IOException;
    String getPrefix();
}
