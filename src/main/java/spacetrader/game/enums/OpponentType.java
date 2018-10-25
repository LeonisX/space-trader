package spacetrader.game.enums;

public enum OpponentType implements SpaceTraderEnum {

    BOTTLE,
    FAMOUS_CAPTAIN,
    MANTIS,
    PIRATE,
    POLICE,
    TRADER;

    @Override
    public int castToInt() {
        return ordinal();
    }
}
