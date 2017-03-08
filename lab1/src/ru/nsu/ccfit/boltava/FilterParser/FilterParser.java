package ru.nsu.ccfit.boltava.FilterParser;

import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.nsu.ccfit.boltava.resources.FilterPatterns.*;

public class FilterParser {

    private FilterSerializerFactory serializerFactory;

    public FilterParser() {
        serializerFactory = new FilterSerializerFactory();
    }

    public IFilter parse(String filterString) throws IllegalArgumentException {

        if (filterString == null) throw new IllegalArgumentException();
        filterString = filterString.trim();
        if (filterString.length() < minFilterStringLength) {
            throw new IllegalArgumentException(
                    "Expected filter with min length of " +
                    minFilterStringLength
            );
        }

        String prefix = filterString.substring(0,1);
        String filterBody = filterString.substring(1).trim();

        if (!(  filterBody.matches(compositeFilterPattern) ||
                filterBody.matches(leafFilterPattern))) {
            throw new IllegalArgumentException(
                    "Invalid filter format: " + filterString
            );
        }

        IFilter filter = serializerFactory.get(prefix).getFilter(filterString);

        try {
            String[] children = getChildren(filterBody);
            for (String child : children) {
                filter.add(this.parse(child));
            }
        } catch (IllegalAccessException e) {
            return filter;
        }

        return filter;
    }

    private String[] getChildren(String filterBody) {

//        (?'primitive'(?'prefix'[ \t]*([^\(\)\s])[ \t]*)(?3)+\s*)|(?'composite'(?&prefix)\(+((?&primitive)+|(?&composite)+)+\)+[ \t]*) <-- get filters from valid string
//        (\s*?([^\(\)\s])(?2)+\s*?) <-- match simple expressions
//        (\s*?([^\(\)\s])(?2)+\s*?)|(\s*?([^\(\)\s])\s*\((?2)+\s*(?2)+?\)) <-- match full expression
//        ^((?>\s*\()+)[^\(\)]+((?>\)\s*)+)$ <-- composite
//        (\s*([^\(\)\s])\s*(?2)+\s*)|(\s*(?2)\s*\(+(?2)+\s*(?2)+\s*\)+)


        Matcher matcher = parserPattern.matcher(filterBody);
        List<String> allMatches = new ArrayList<String>();
        while(matcher.find()) {
            allMatches.add(matcher.group().trim());
        }

        return allMatches.toArray(new String[]{});

    }

    private static final int minFilterStringLength = 2;
    private static final String compositeFilterPattern = COMPOSITE_FILTER_PATTERN;
    private static final String leafFilterPattern = LEAF_FILTER_PATTERN;
    private static final Pattern parserPattern = Pattern.compile(PARSER_PATTERN);
}
