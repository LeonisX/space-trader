package spacetrader.game.enums;

public enum ShipyardId implements SpaceTraderEnum// : int
{
    NA, // = -1,
    Corellian, // = 0,
    Incom, // = 1,
    Kuat, // = 2,
    Sienar, // = 3,
    Sorosuub;// = 4

    public static ShipyardId fromInt(int i) {
        return values()[i + 1];
    }

    public int castToInt() {
        return ordinal() - 1;
    }
}