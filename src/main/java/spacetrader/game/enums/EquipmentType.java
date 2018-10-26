package spacetrader.game.enums;

public enum EquipmentType implements SpaceTraderEnum {

    WEAPON, SHIELD, GADGET;

    public static EquipmentType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
