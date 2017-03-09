package ru.nsu.ccfit.boltava.parser;


import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;


import java.util.ArrayList;
import java.util.List;
import com.florianingerl.util.regex.Pattern;
import com.florianingerl.util.regex.Matcher;


import static ru.nsu.ccfit.boltava.resources.FilterPatterns.*;

public class FilterParser {

    private static final int minFilterStringLength = 2;
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

        Matcher matcher = Pattern.compile(PARSER).matcher(filterString);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid filter format: " + filterString
            );
        }

        String prefix = filterString.substring(0,1);
        IFilter filter = serializerFactory.get(prefix).getFilter(filterString);

        try {
            String[] children = getChildren(filterString);
            for (String child : children) {
                filter.add(this.parse(child));
            }
        } catch (IllegalAccessException e) {
            return filter;
        }

        return filter;

    }

    private String[] getChildren(String filterString) {

//        (?<primitive>(?<prefix>[ \t]*([^()\s])[ \t]*)(?3)+\s*)|(?<composite>(?&prefix)\(+?((?&primitive)+|(?&composite)+)+\)+?[ \t]*) <-- get filters from valid string
//        (\s*?([^\(\)\s])(?2)+\s*?) <-- match simple expressions
//        (\s*?([^\(\)\s])(?2)+\s*?)|(\s*?([^\(\)\s])\s*\((?2)+\s*(?2)+?\)) <-- match full expression
//        ^((?>\s*\()+)[^\(\)]+((?>\)\s*)+)$ <-- composite
//        (\s*([^\(\)\s])\s*(?2)+\s*)|(\s*(?2)\s*\(+(?2)+\s*(?2)+\s*\)+)

        if (filterString.matches(LEAF_FILTER)) {
            return new String[]{};
        }

        String filterBody = filterString.substring(1).trim();

        Matcher matcher = parserPattern.matcher(filterBody);
        List<String> allMatches = new ArrayList<>();
        while(matcher.find()) {
            allMatches.add(matcher.group().trim());
        }

        return allMatches.toArray(new String[]{});

    }

    private boolean isValidFormat(String filterString) {

        if (filterString.matches(LEAF_FILTER)) {
            return true;
        }


        return false;
    }

    private static final String compositeFilterPattern = COMPOSITE_FILTER;
    private static final String leafFilterPattern = LEAF_FILTER;
    private static final Pattern parserPattern = Pattern.compile(PARSER);
//    private static final Pattern parserPattern = Pattern.compile("[ \\t]*([^()\\s])[ \\t]*[^()\\s]+\\s*");
}
