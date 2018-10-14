package spacetrader.game.enums;

import spacetrader.game.Strings;

public enum ReputationType implements SpaceTraderEnum// : int
{
    Harmless, // = 0,
    MostlyHarmless, // = 1,
    Poor, // = 2,
    Average, // = 3,
    AboveAverage, // = 4,
    Competent, // = 5,
    Dangerous, // = 6,
    Deadly, // = 7,
    Elite;// = 8

    public int castToInt() {
        return ordinal();
    }

    public String getName() {
        return Strings.ReputationNames[ordinal()];
    }
}