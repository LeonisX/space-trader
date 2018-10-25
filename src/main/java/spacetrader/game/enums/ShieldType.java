package spacetrader.game.enums;

public enum ShieldType implements SpaceTraderEnum, EquipmentSubType {

    ENERGY,
    REFLECTIVE,
    LIGHTNING;

    public static ShieldType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
