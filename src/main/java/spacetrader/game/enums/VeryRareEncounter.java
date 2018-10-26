package spacetrader.game.enums;

public enum VeryRareEncounter implements SpaceTraderEnum {

    MARIE_CELESTE, // = 0,
    CAPTAIN_AHAB, // = 1,
    CAPTAIN_CONRAD, // = 2,
    CAPTAIN_HUIE, // = 3,
    BOTTLE_OLD, // = 4,
    BOTTLE_GOOD; // = 5

    @Override
    public int castToInt() {
        return ordinal();
    }
}
