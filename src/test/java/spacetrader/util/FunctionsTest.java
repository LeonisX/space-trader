package spacetrader.util;

import org.junit.Test;
import spacetrader.game.GlobalAssets;
import spacetrader.game.Strings;
import spacetrader.game.enums.Language;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FunctionsTest {

    @Test
    public void stringVars() {
        Strings.pluralMap.put("a2", "aa");
        String toParse = "^1 ^^1 ^2 ^^2 ^^^3";
        List<String> vars = Arrays.asList("a", "b ");

        assertEquals("a aa b  b  ^^^3", Functions.stringVars(toParse, vars));
    }

    @Test
    public void englishPlurals() {
        GlobalAssets.setLanguage(Language.ENGLISH);
        Strings.pluralMap.put("book2", "books");
        String unit = "book";

        assertEquals("0 books", Functions.plural(0, unit));
        assertEquals("1 book", Functions.plural(1, unit));
        assertEquals("2 books", Functions.plural(2, unit));
        assertEquals("1,001 books", Functions.plural(1001, unit));
    }

    @Test
    public void russianPlurals() {
        GlobalAssets.setLanguage(Language.RUSSIAN);
        Strings.pluralMap.put("книга2", "книги");
        Strings.pluralMap.put("книга3", "книг");
        String unit = "книга";

        assertEquals("0 книг", Functions.plural(0, unit));
        assertEquals("1 книга", Functions.plural(1, unit));
        assertEquals("2 книги", Functions.plural(2, unit));
        assertEquals("3 книги", Functions.plural(3, unit));
        assertEquals("4 книги", Functions.plural(4, unit));
        assertEquals("5 книг", Functions.plural(5, unit));
        assertEquals("11 книг", Functions.plural(11, unit));
        assertEquals("500,000 книг", Functions.plural(500000, unit));
        assertEquals("1,001 книга", Functions.plural(1001, unit));
        assertEquals("103 книги", Functions.plural(103, unit));
    }

    @Test
    public void splitText() {
        List<String> result = Functions.splitString("asd | ddd\\|ddd | c");

        assertEquals(3, result.size());
        assertEquals("asd", result.get(0));
        assertEquals("ddd|ddd", result.get(1));
        assertEquals("c", result.get(2));
    }

    @Test
    public void versionToLong() {
        assertEquals(new Long(0L), Functions.versionToLong(""));
        assertEquals(new Long(1L), Functions.versionToLong("1"));
        assertEquals(new Long(10011L), Functions.versionToLong("2.11"));
        assertEquals(new Long(2L * 5000 * 5000 * 5000 + 4 * 5000 * 5000 + 6 * 5000 + 8), Functions.versionToLong("2.4.6.8-PR"));
    }

    @Test
    public void versionToLongCompare() {
        assertEquals(Functions.versionToLong("1.9.9").compareTo(Functions.versionToLong("1.9.10")), -1);
        assertEquals(Functions.versionToLong("1.9.9").compareTo(Functions.versionToLong("1.9 .09")), 0);
        assertEquals(Functions.versionToLong("1.9.9").compareTo(Functions.versionToLong("1.10.8")), -1);
    }
}