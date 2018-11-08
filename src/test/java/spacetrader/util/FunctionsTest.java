package spacetrader.util;

import org.junit.Test;
import spacetrader.game.GlobalAssets;
import spacetrader.game.enums.Language;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FunctionsTest {

    @Test
    public void stringVars() {
        String toParse = "^1 ^^1 ^2 ^^2 ^^^3";
        List<String> vars = Arrays.asList("a | a", "b ");

        assertEquals("a a b ^b ^^^3", Functions.stringVars(toParse, vars));
    }

    @Test
    public void englishPlurals() {
        GlobalAssets.setLanguage(Language.ENGLISH);
        String unit = "book | books";

        assertEquals("books", Functions.plural(0, unit));
        assertEquals("book", Functions.plural(1, unit));
        assertEquals("books", Functions.plural(2, unit));
        assertEquals("books", Functions.plural(1001, unit));
    }

    @Test
    public void russianPlurals() {
        GlobalAssets.setLanguage(Language.RUSSIAN);
        String unit = "книга | книги | книг";

        assertEquals("книг", Functions.plural(0, unit));
        assertEquals("книга", Functions.plural(1, unit));
        assertEquals("книги", Functions.plural(2, unit));
        assertEquals("книги", Functions.plural(3, unit));
        assertEquals("книги", Functions.plural(4, unit));
        assertEquals("книг", Functions.plural(5, unit));
        assertEquals("книг", Functions.plural(11, unit));
        assertEquals("книг", Functions.plural(500000, unit));
        assertEquals("книга", Functions.plural(1001, unit));
        assertEquals("книги", Functions.plural(103, unit));
    }
}