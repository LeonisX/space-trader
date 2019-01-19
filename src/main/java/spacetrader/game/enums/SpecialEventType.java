package spacetrader.game.enums;

public enum SpecialEventType implements SpaceTraderEnum {

    NA, // = -1,
    Artifact, // = 0,
    ArtifactDelivery, // = 1,
    CargoForSale, // = 2,
    ASSIGNED;

    public static SpecialEventType fromInt(int i) {
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}
