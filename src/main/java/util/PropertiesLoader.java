package util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PropertiesLoader {

    //TODO optimize
    public static Map<String, String> getStringsMap(String name) {
        Map<String, String> result = new HashMap<>();
        getResourceAsFilteredStream(name).forEach(s -> {
            String[] pair = s.split("=", 2);
            result.put(pair[0], pair[1]);
        });
        return new LinkedHashMap<>(result);
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
        try {
            URL url = PropertiesLoader.class.getClassLoader().getResource(name);
            return Files.lines(Paths.get(url.toURI()), Charset.forName("utf-8"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
