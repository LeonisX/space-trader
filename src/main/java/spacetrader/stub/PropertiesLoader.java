package spacetrader.stub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesLoader {

    public static ValuesMap getValuesMap(String name) {
        ValuesMap valuesMap = new ValuesMap();
        getResourceAsFilteredStream(name).forEach(s -> {
            String[] pair = s.split("=", 2);
            valuesMap.put(pair[0], pair[1]);
        });
        return valuesMap;
    }

    public static StringsMap getStringsMap(String name) {
        StringsMap result = new StringsMap();
        getResourceAsFilteredStream(name).forEach(s -> {
            String[] pair = s.split("=", 2);
            result.put(pair[0], pair[1]);
        });
        return result;
    }

    public static List<String> getResourceAsFilteredList(String name) {
        return getResourceAsFilteredStream(name).collect(Collectors.toList());
    }

    public static List<String> getResourceAsUnfilteredList(String name) {
        return getResourceAsStream(name).collect(Collectors.toList());
    }

    private static Stream<String> getResourceAsFilteredStream(String name) {
        return getResourceAsStream(name).filter(s -> !s.isEmpty() && !s.startsWith("#") && s.contains("="));
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
