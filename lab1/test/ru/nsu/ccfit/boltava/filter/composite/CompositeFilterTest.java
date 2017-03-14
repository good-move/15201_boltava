package ru.nsu.ccfit.boltava.filter.composite;

import org.junit.Test;
import ru.nsu.ccfit.boltava.filter.IFilter;

import static org.junit.Assert.*;

public class CompositeFilterTest {
    @Test
    public void shouldDifferentClassesNotEqual() throws Exception {

        IFilter andFilter = new AndFilter();
        IFilter orFilter = new OrFilter();
        IFilter notFilter = new NotFilter();

        assertTrue(!andFilter.equals(orFilter));
        assertTrue(!andFilter.equals(notFilter));

        assertTrue(!orFilter.equals(andFilter));
        assertTrue(!orFilter.equals(notFilter));

        assertTrue(!notFilter.equals(andFilter));
        assertTrue(!notFilter.equals(orFilter));

    }

    @Test
    public void emptyAndEquals() throws Exception {
        IFilter and1 = new AndFilter();
        IFilter and2 = new AndFilter();
        IFilter and3 = new AndFilter();

        assertEquals(and1, and2);
        assertEquals(and1, and3);
    }

    @Test
    public void emptyOrEquals() throws Exception {
        IFilter or1 = new OrFilter();
        IFilter or2 = new OrFilter();
        IFilter or3 = new OrFilter();

        assertEquals(or1, or2);
        assertEquals(or1, or3);
    }

    @Test
    public void emptyNotEquals() throws Exception {
        IFilter not1 = new OrFilter();
        IFilter not2 = new OrFilter();
        IFilter not3 = new OrFilter();

        assertEquals(not1, not2);
        assertEquals(not1, not3);
    }

}