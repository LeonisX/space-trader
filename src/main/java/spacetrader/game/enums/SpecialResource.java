package spacetrader.game.enums;

public enum SpecialResource implements SpaceTraderEnum {

    NA, // = -1,
    NOTHING, // = 0,
    MINERAL_RICH, // = 1,
    MINERAL_POOR, // = 2,
    DESERT, // = 3,
    SWEET_OCEANS, // = 4,
    RICH_SOIL, // = 5,
    POOR_SOIL, // = 6,
    RICH_FAUNA, // = 7,
    LIFELESS, // = 8,
    WEIRD_MUSHROOMS, // = 9,
    SPECIAL_HERBS, // = 10,
    ARTISTIC, // = 11,
    WARLIKE; // = 12

    public static SpecialResource fromInt(int i) {
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}
