package spacetrader.game.enums;

public enum SystemPressure implements SpaceTraderEnum {

    NONE,//= 0,
    WAR,//= 1,
    PLAGUE,//= 2,
    DROUGHT,//= 3,
    BOREDOM,//= 4,
    COLD,//= 5,
    CROP_FAILURE,//= 6,
    EMPLOYMENT;//= 7

    public static SystemPressure fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
