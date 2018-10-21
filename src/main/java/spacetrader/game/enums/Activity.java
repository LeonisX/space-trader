package spacetrader.game.enums;

public enum Activity implements SpaceTraderEnum {

    Absent, // = 0,
    Minimal, // = 1,
    Few, // = 2,
    Some, // = 3,
    Moderate, // = 4,
    Many, // = 5,
    Abundant, // = 6,
    Swarms, // = 7,
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
