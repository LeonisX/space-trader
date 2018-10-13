package spacetrader.enums;

public enum SystemPressure implements SpaceTraderEnum//: int
{
    None,//= 0,
    War,//= 1,
    Plague,//= 2,
    Drought,//= 3,
    Boredom,//= 4,
    Cold,//= 5,
    CropFailure,//= 6,
    Employment;//= 7

    public static SystemPressure fromInt(int i) {
        return values()[i];
    }

    public int castToInt() {
        return ordinal();
    }
};