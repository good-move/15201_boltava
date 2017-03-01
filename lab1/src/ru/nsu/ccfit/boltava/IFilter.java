package ru.nsu.ccfit.boltava;

import java.nio.file.Path;

public interface IFilter {
    boolean check(Path fileName);
}
