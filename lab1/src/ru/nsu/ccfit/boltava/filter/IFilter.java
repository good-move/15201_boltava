package ru.nsu.ccfit.boltava.filter;

import java.nio.file.Path;

public interface IFilter {
    boolean check(Path fileName);
}
