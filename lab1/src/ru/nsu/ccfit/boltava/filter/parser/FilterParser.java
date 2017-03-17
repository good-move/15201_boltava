package ru.nsu.ccfit.boltava.filter.parser;


import com.florianingerl.util.regex.Matcher;
import com.florianingerl.util.regex.Pattern;
import ru.nsu.ccfit.boltava.filter.IFilter;
import ru.nsu.ccfit.boltava.filter.serializer.FilterSerializerFactory;


import java.util.ArrayList;


import static ru.nsu.ccfit.boltava.resources.FilterPatterns.*;

public class FilterParser {

    private static final int mMinFilterStringLength = 2;

    private static final String mLeafFilterString = LEAF_FILTER;
    private static final com.florianingerl.util.regex.Pattern mParserPattern = Pattern.compile(PARSER);


    public static IFilter parse(String filterString) throws IllegalArgumentException {

        filterString = filterString.trim();

        if (filterString.length() < mMinFilterStringLength) {
            throw new IllegalArgumentException(
                    "Expected filter with min length of " +
                            mMinFilterStringLength + ". Got: " + filterString
            );
        }

        Matcher matcher = mParserPattern.matcher(filterString);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Wrong filter format: " + filterString
            );
        }

        String prefix = filterString.substring(0,1);

        return FilterSerializerFactory.create(prefix).serialize(filterString);
    }

    public static ArrayList<IFilter> getChildren(String filterString) throws IllegalArgumentException {

        Matcher matcher = mParserPattern.matcher(filterString);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Wrong filter format: " + filterString
            );
        }

        ArrayList<IFilter> children = new ArrayList<>();

        String filterBody = filterString.trim().substring(1);
        matcher = mParserPattern.matcher(filterBody);

        while(matcher.find()) {
            children.add(parse(matcher.group()));
        }

        return children;
    }

}
