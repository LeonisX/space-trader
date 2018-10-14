package spacetrader.game.enums;

public enum SkillType implements SpaceTraderEnum// : int
{
    NA, // = -1,
    Pilot, // = 0,
    Fighter, // = 1,
    Trader, // = 2,
    Engineer;// = 3

    public static SkillType fromInt(int i) {
        return values()[i + 1];
    }

    public int castToInt() {
        return ordinal() - 1;
    }
}
