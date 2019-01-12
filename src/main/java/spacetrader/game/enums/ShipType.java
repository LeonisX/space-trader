package spacetrader.game.enums;

public enum ShipType implements SpaceTraderEnum {

    FLEA,        // 0
    GNAT,        // 1
    FIREFLY,
    MOSQUITO,
    BUMBLEBEE,
    BEETLE,        // 5
    HORNET,
    GRASSHOPPER,//7
    TERMITE,
    WASP,        // 9
    DRAGONFLY,    // 11
    MANTIS,        // 12
    SCARAB,        // 13
    BOTTLE,        // 14
    CUSTOM,        // 15
    QUEST;

    public static ShipType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
