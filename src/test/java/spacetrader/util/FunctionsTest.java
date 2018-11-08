package spacetrader.util;

import org.junit.Test;

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
}