package spacetrader.game.enums;

public enum ShipType implements SpaceTraderEnum {

    FLEA,           // 0
    GNAT,           // 1
    FIREFLY,        // 2
    MOSQUITO,       // 3
    BUMBLEBEE,      // 4
    BEETLE,         // 5
    HORNET,         // 6
    GRASSHOPPER,    // 7
    TERMITE,        // 8
    WASP,           // 9
    MANTIS,         // 10
    CUSTOM,         // 11
    QUEST;          // 12

    public static ShipType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
