package ru.nsu.ccfit.boltava.parser;


import com.florianingerl.util.regex.Matcher;
import com.florianingerl.util.regex.Pattern;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;


import java.util.ArrayList;


import static ru.nsu.ccfit.boltava.resources.FilterPatterns.*;

class FilterParser {

    private static final int minFilterStringLength = 2;
    private static final String mLeafFilterString = LEAF_FILTER;
    private static final com.florianingerl.util.regex.Pattern mParserPattern = Pattern.compile(PARSER);
    private FilterSerializerFactory serializerFactory;

    FilterParser() {
        serializerFactory = new FilterSerializerFactory();
    }

    IFilter parse(String filterString) throws IllegalArgumentException {

        if (filterString == null) throw new IllegalArgumentException();

        filterString = filterString.trim();

        if (filterString.length() < minFilterStringLength) {
            throw new IllegalArgumentException(
                    "Expected filter with min length of " +
                    minFilterStringLength
            );
        }

        Matcher matcher = mParserPattern.matcher(filterString);

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

        if (filterString.matches(mLeafFilterString)) {
            return new String[]{};
        }

        String filterBody = filterString.substring(1).trim();
        Matcher matcher = mParserPattern.matcher(filterBody);
        ArrayList<String> allMatches = new ArrayList<>();

        while(matcher.find()) {
            allMatches.add(matcher.group().trim());
        }

        return allMatches.toArray(new String[]{});
    }

}
