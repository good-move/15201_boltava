package ru.nsu.ccfit.boltava.resources;

public abstract class FilterPatterns {

    public static final String FILE_EXTENSION_FILTER_PATTERN = "^\\.\\S*";

    public static final String LAST_MODIFIED_FILTER_PATTERN = "^(<|>)[0-9]{1,}$";

    public static final String OR_FILTER_PATTERN = "^|\\(.+\\)$";

    public static final String AND_FILTER_PATTERN = "^$\\(.+\\)$";

    public static final String NOT_FILTER_PATTERN = "^!\\(.+\\)$";

    public static final String COMPOSITE_FILTER_PATTERN = "^\\s*\\(.+\\)\\s*$";

    public static final String LEAF_FILTER_PATTERN = "^\\s*\\S+\\s*$";

    public static final String PARSER_PATTERN = "(?<primitive>(?<prefix>[ \\t]*([^()\\s])[ \\t]*)(\\3)+\\s*)|" +
            "(?<composite>(\\k<prefix>)\\(+((\\k<primitive>)+|(\\k<composite>)+)+\\)+[ \\t]*)";

}
