package spacetrader.game.enums;

public enum NewsEvent implements SpaceTraderEnum {

    ArtifactDelivery, // = 0,
    CaughtLittering, // = 1,
    Dragonfly, // = 2,
    DragonflyBaratas, // = 3,
    DragonflyDestroyed, // = 4,
    DragonflyMelina, // = 5,
    DragonflyRegulas, // = 6,
    DragonflyZalkon, // = 7,
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
