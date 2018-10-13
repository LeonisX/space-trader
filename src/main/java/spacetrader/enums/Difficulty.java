package spacetrader.enums;

public enum Difficulty implements SpaceTraderEnum// : int
{
    Beginner(1), Easy(1), Normal(0), Hard(-1), Impossible(-1);

    private final int skillAdjust;

    private Difficulty(int skillAdjust) {
        this.skillAdjust = skillAdjust;
    }

    public static Difficulty fromInt(int i) {
        return values()[i];
    }

    public int castToInt() {
        return ordinal();
    }

    public int adjustSkill(int skill) {
        return skill + skillAdjust;
    }
}
