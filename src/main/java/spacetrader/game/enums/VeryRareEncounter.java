package spacetrader.game.enums;

public enum VeryRareEncounter implements SpaceTraderEnum {

    MARIE_CELESTE, // = 0,
    BOTTLE_OLD, // = 4,
    BOTTLE_GOOD; // = 5

    @Override
    public int castToInt() {
        return ordinal();
    }
}
