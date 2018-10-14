package spacetrader.game.enums;

public enum TechLevel implements SpaceTraderEnum// : int
{
    PreAgricultural, // = 0,
    Agricultural, // = 1,
    Medieval, // = 2,
    Renaissance, // = 3,
    EarlyIndustrial, // = 4,
    Industrial, // = 5,
    PostIndustrial, // = 6,
    HiTech, // = 7,
    Unavailable;// = 8

    public static TechLevel fromInt(int i) {
        return values()[i];
    }

    public int castToInt() {
        return ordinal();
    }
}