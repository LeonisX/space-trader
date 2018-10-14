package spacetrader.game.enums;

public enum GameEndType implements SpaceTraderEnum {

    NA, // = -1,
    Killed, // = 0,
    Retired, // = 1,
    BoughtMoon, // = 2,
    BoughtMoonGirl; // = 3

    public static GameEndType fromInt(int i) {
        return values()[i + 1];
    }

    public int castToInt() {
        return ordinal() - 1;
    }
}
