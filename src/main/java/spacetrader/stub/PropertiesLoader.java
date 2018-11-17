package spacetrader.stub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesLoader {

    private final static Pattern LEFT_TRIM_PATTERN = Pattern.compile("^\\s+");
    private final static Pattern RIGHT_TRIM_PATTERN = Pattern.compile("\\s+$");

    public static ObjectsBundle getValuesBundle(String name) {
        return getValuesBundle(name, true);
    }

    /**
     * Loads key=value String->Object pairs as ObjectsBundle.
     *
     * # these comments will be removed
     * // these comments, if they are at the string start will be removed
     *
     * Single line comments after key-value pair will be deleted only if trimSingleLineComments is true
     *
     * Example:
     * Source: version=2.11 // the version of library
     * Result: "version"->"2.11"
     *
     * @param name                   name of file with strings
     * @param trimSingleLineComments trim single line comments at the end of strings
     * @return Map<String, Object> as ObjectsBundle
     */
    private static ObjectsBundle getValuesBundle(String name, boolean trimSingleLineComments) {
        ObjectsBundle objectsBundle = new ObjectsBundle();
        getResourceAsFilteredStream(name).forEach(s -> {
            String[] pair = s.split("=", 2);
            if (trimSingleLineComments && pair[1].contains("//")) {
                pair[1] = trimTrailComment(pair[1]);
            }
            objectsBundle.put(pair[0], pair[1]);
        });
        return objectsBundle;
    }

    public static StringsBundle getStringsBundle(String name) {
        return getStringsBundle(name, true);
    }

    /**
     * Loads key=value String pairs as StringBundle.
     *
     * # these comments will be removed
     * // these comments, if they are at the string start will be removed
     *
     * Single line comments after key-value pair will be deleted only if trimSingleLineComments is true
     *
     * Example:
     * Source: version=2.11 // the version of library
     * Result: "version"->"2.11"
     *
     * @param name                   name of file with strings
     * @param trimSingleLineComments trim single line comments at the end of strings
     * @return Map<String, String> as StringBundle
     */
    private static StringsBundle getStringsBundle(String name, boolean trimSingleLineComments) {
        StringsBundle result = new StringsBundle();
        getResourceAsFilteredStream(name).forEach(s -> {
            String[] pair = s.split("=", 2);
            if (trimSingleLineComments && pair[1].contains(" //")) {
                pair[1] = trimTrailComment(pair[1]);
            }
            result.put(pair[0], pair[1]);
        });
        return result;
    }

    private static String trimTrailComment(String string) {
        return RIGHT_TRIM_PATTERN.matcher(string.split(" //", 2)[0]).replaceAll("");
    }

    public static List<String> getResourceAsFilteredList(String name) {
        return getResourceAsFilteredStream(name).collect(Collectors.toList());
    }

    public static List<String> getResourceAsUnfilteredList(String name) {
        return getResourceAsStream(name).collect(Collectors.toList());
    }

    private static Stream<String> getResourceAsFilteredStream(String name) {
        return getResourceAsStream(name).filter(rawString -> {
            String s = LEFT_TRIM_PATTERN.matcher(rawString).replaceAll("");
            return !s.isEmpty() && !s.startsWith("#") && !s.startsWith("//") && s.contains("=");
        });
    }

    private static Stream<String> getResourceAsStream(String name) {
            List<String> lines = new ArrayList<>();
            InputStream is = PropertiesLoader.class.getClassLoader().getResourceAsStream(name);
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            try {
                String line;
                while ((line = r.readLine()) != null) {
                    lines.add(line);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return lines.stream();
    }
}
