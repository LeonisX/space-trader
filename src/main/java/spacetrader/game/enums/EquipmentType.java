package spacetrader.game.enums;

public enum EquipmentType implements SpaceTraderEnum// : int
{
    Weapon, Shield, Gadget;

    public static EquipmentType fromInt(int i) {
        return values()[i];
    }

    public int castToInt() {
        return ordinal();
    }
}