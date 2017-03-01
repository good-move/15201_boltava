package ru.nsu.ccfit.boltava;

import java.nio.file.Path;
import java.util.ArrayList;

public abstract class CompositeFilter implements IFilter {

    protected ArrayList<IFilter> mChildFilters;

    public abstract boolean check(Path fileName);

    void add(IFilter filter) { this.mChildFilters.add(filter); };

    void remove() {};



    public static class AndFilter extends CompositeFilter {

        @Override
        public boolean check(Path fileName) {
            for (IFilter filter : mChildFilters) {
                if (!filter.check(fileName)) return false;
            }

            return true;
        }

    }

    public static class OrFilter extends CompositeFilter {

        @Override
        public boolean check(Path fileName) {
            for (IFilter filter : mChildFilters) {
                if (filter.check(fileName)) return true;
            }

            return false;
        }

    }

    public static class NotFilter extends CompositeFilter {

        @Override
        public void add(IFilter filter) {
            if (mChildFilters.size() == 0) {
                super.add(filter);
            }
        }

        @Override
        public boolean check(Path fileName) {
            return mChildFilters.size() > 0 &&
                   !mChildFilters.get(0).check(fileName);
        }

    }

}
