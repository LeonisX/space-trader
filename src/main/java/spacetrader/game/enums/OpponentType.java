package spacetrader.game.enums;

public enum OpponentType implements SpaceTraderEnum {

    MANTIS,
    PIRATE,
    POLICE,
    TRADER;

    public static OpponentType fromInt(int value) {
        return OpponentType.values()[value];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
