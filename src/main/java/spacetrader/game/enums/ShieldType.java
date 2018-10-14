package spacetrader.game.enums;

public enum ShieldType implements SpaceTraderEnum, EquipmentSubType {
    Energy, Reflective, Lightning;

    public static ShieldType fromInt(int i) {
        return values()[i];
    }

    public int castToInt() {
        return ordinal();
    }
}