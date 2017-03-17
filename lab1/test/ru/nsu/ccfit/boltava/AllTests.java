package ru.nsu.ccfit.boltava;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.nsu.ccfit.boltava.filter.FilterSuiteTest;
import ru.nsu.ccfit.boltava.statistics.LineStatisticsCollectorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ LineStatisticsCollectorTest.class, FilterSuiteTest.class })
public final class AllTests {
}
