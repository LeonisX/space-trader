package spacetrader.game.enums;

//TODO remove, create 2 separate news
public enum NewsEvent implements SpaceTraderEnum {

    CaughtLittering, // = 1,
    ExperimentArrival; // = 11,

    public static NewsEvent fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
