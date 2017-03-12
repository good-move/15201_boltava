package ru.nsu.ccfit.boltava.resources;

public abstract class FilterPatterns {

    private static final String spaces = "[ \\t]*";

    public static final String FILE_EXTENSION_FILTER = "^\\." + spaces + "[^\\s()]+";

    public static final String LAST_MODIFIED_FILTER = "^(<|>)" + spaces + "\\d+$";

    public static final String OR_FILTER = "^\\|[ \\t]*\\(.+\\)$";

    public static final String AND_FILTER = "^&[ \\t]*\\(.+\\)$";

    public static final String NOT_FILTER = "^!" + spaces + "\\(.+\\)";

    public static final String LEAF_FILTER = "([ \\t]*([^()\\s])[ \\t]*[^()\\s]+\\s*)";

    public static final String PARSER = "(?<primitive>(?<prefix>[ \\t]*([^()\\s])[ \\t]*)(?3)+\\s*)|" +
            "(?<composite>(?prefix)\\(+?((?primitive)+|(?composite)+)+\\)+?[ \\t]*)";

}
