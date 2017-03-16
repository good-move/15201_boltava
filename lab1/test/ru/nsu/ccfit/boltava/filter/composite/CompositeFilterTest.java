package ru.nsu.ccfit.boltava.filter.composite;

import org.junit.Before;
import org.junit.Test;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FileExtensionFilterSerializer;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CompositeFilterTest {

    ArrayList<IFilter> child = new ArrayList<IFilter>();

    @Before
    public void prepare() {
        child.add(new FileExtensionFilterSerializer().serialize(".txt"));
    }


    @Test
    public void shouldDifferentClassesNotEqual() throws Exception {

        IFilter andFilter = new AndFilter(child);
        IFilter orFilter = new OrFilter(child);
        IFilter notFilter = new NotFilter(child.get(0));

        assertTrue(!andFilter.equals(orFilter));
        assertTrue(!andFilter.equals(notFilter));

        assertTrue(!orFilter.equals(andFilter));
        assertTrue(!orFilter.equals(notFilter));

        assertTrue(!notFilter.equals(andFilter));
        assertTrue(!notFilter.equals(orFilter));

    }

    @Test
    public void emptyAndEquals() throws Exception {
        IFilter and1 = new AndFilter(child);
        IFilter and2 = new AndFilter(child);
        IFilter and3 = new AndFilter(child);

        assertEquals(and1, and2);
        assertEquals(and1, and3);
    }

    @Test
    public void emptyOrEquals() throws Exception {
        IFilter or1 = new OrFilter(child);
        IFilter or2 = new OrFilter(child);
        IFilter or3 = new OrFilter(child);

        assertEquals(or1, or2);
        assertEquals(or1, or3);
    }

    @Test
    public void emptyNotEquals() throws Exception {
        IFilter not1 = new NotFilter(child.get(0));
        IFilter not2 = new NotFilter(child.get(0));
        IFilter not3 = new NotFilter(child.get(0));

        assertEquals(not1, not2);
        assertEquals(not1, not3);
    }

}