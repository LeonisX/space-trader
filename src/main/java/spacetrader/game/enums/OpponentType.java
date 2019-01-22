package spacetrader.game.enums;

public enum OpponentType implements SpaceTraderEnum {

    BOTTLE,
    //TODO remove if don't need
    //FAMOUS_CAPTAIN,
    MANTIS,
    PIRATE,
    POLICE,
    TRADER;

    @Override
    public int castToInt() {
        return ordinal();
    }
}
