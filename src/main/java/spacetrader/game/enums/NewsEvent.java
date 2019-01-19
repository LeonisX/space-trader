package spacetrader.game.enums;

public enum NewsEvent implements SpaceTraderEnum {

    ArtifactDelivery, // = 0,
    CaughtLittering, // = 1,
    ExperimentArrival, // = 11,
    CaptAhabAttacked, // = 15,
    CaptAhabDestroyed, // = 16,
    CaptConradAttacked, // = 17,
    CaptConradDestroyed, // = 18,
    CaptHuieAttacked, // = 19,
    CaptHuieDestroyed; // = 20,

    public static NewsEvent fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
