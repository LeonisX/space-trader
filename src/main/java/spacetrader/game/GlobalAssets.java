package spacetrader.game;

import spacetrader.stub.PropertiesLoader;
import spacetrader.stub.StringsMap;
import spacetrader.stub.ValuesMap;

public class GlobalAssets {

    private static final String STRINGS_TEMPLATE = "strings/%s.properties";
    private static final String DIMENSIONS_TEMPLATE = "dimensions/%s.properties";

    private static StringsMap strings = new StringsMap();
    private static ValuesMap dimensions = new ValuesMap();

    public static void loadStrings(String fileName) {
        strings = PropertiesLoader.getStringsMap(String.format(STRINGS_TEMPLATE, fileName));
    }

    public static void loadDimensions(String fileName) {
        dimensions = PropertiesLoader.getValuesMap(String.format(DIMENSIONS_TEMPLATE, fileName));
    }

    public static StringsMap getStrings() {
        return strings;
    }

    public static ValuesMap getDimensions() {
        return dimensions;
    }
}
