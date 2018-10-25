package spacetrader.game.enums;

public enum TechLevel implements SpaceTraderEnum {

    PRE_AGRICULTURAL, // = 0,
    AGRICULTURAL, // = 1,
    MEDIEVAL, // = 2,
    RENAISSANCE, // = 3,
    EARLY_INDUSTRIAL, // = 4,
    INDUSTRIAL, // = 5,
    POST_INDUSTRIAL, // = 6,
    HI_TECH, // = 7,
    UNAVAILABLE;// = 8

    public static TechLevel fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
