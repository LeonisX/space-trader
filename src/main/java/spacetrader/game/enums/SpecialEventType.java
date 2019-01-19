package spacetrader.game.enums;

public enum SpecialEventType implements SpaceTraderEnum {

    //TODO think, how to occupy system gently. May be boolean field.
    //TODO think, may be don't need to clean this field after quest fail
    NA, // = -1,
    ASSIGNED;

    public static SpecialEventType fromInt(int i) {
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}
