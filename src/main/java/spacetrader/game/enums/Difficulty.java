package spacetrader.game.enums;

public enum Difficulty implements SpaceTraderEnum {

    BEGINNER(1), EASY(1), NORMAL(0), HARD(-1), IMPOSSIBLE(-1);

    private final int skillAdjust;

    Difficulty(int skillAdjust) {
        this.skillAdjust = skillAdjust;
    }

    public static Difficulty fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }

    public int adjustSkill(int skill) {
        return skill + skillAdjust;
    }
}
