package spacetrader.game.enums;

public enum ShipyardId implements SpaceTraderEnum {

    NA, // = -1,
    CORELLIAN, // = 0,
    INCOM, // = 1,
    KUAT, // = 2,
    SIENAR, // = 3,
    SOROSUUB;// = 4

    public static ShipyardId fromInt(int i) {
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }

}
