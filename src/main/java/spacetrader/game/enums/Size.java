package spacetrader.game.enums;

// TODO rename "ShipSize". Is used as WorldSize too?
public enum Size implements SpaceTraderEnum {

    TINY, // = 0,
    SMALL, // = 1,
    MEDIUM, // = 2,
    LARGE, // = 3,
    HUGE, // = 4,
    GARGANTUAN; // = 5

    public static Size fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
