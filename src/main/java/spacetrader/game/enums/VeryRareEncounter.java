package spacetrader.game.enums;

public enum VeryRareEncounter {

    MARIE_CELESTE, // = 0,
    BOTTLE_OLD, // = 4,
    BOTTLE_GOOD; // = 5

    public Integer castToInt() {
        return ordinal();
    }
}
