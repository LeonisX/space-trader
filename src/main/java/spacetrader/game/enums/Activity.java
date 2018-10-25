package spacetrader.game.enums;

public enum Activity implements SpaceTraderEnum {

    ABSENT, // = 0,
    MINIMAL, // = 1,
    FEW, // = 2,
    SOME, // = 3,
    MODERATE, // = 4,
    MANY, // = 5,
    ABUNDANT, // = 6,
    SWARMS, // = 7,
    NA;// = 100

    public static Activity fromInt(int i) {
        if (i == 100) {
            return NA;
        } else {
            return values()[i];
        }
    }

    //TODO go over all NAs, see if can use null, or at least normalize.
    // TODO go over all castToInt, see if needed.
    @Override
    public int castToInt() {
        return this == NA ? 100 : ordinal();
    }
}
