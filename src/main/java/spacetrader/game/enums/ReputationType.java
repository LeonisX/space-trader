package spacetrader.game.enums;

import spacetrader.game.Strings;

public enum ReputationType implements SpaceTraderEnum {

    HARMLESS, // = 0,
    MOSTLY_HARMLESS, // = 1,
    POOR, // = 2,
    AVERAGE, // = 3,
    ABOVE_AVERAGE, // = 4,
    COMPETENT, // = 5,
    DANGEROUS, // = 6,
    DEADLY, // = 7,
    ELITE;// = 8

    @Override
    public int castToInt() {
        return ordinal();
    }

    public String getName() {
        return Strings.ReputationNames[ordinal()];
    }
}
