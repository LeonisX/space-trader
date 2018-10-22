package spacetrader.game.enums;

public enum SkillType implements SpaceTraderEnum {

    NA, // = -1,
    PILOT, // = 0,
    FIGHTER, // = 1,
    TRADER, // = 2,
    ENGINEER;// = 3

    public static SkillType fromInt(int i) {
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}
