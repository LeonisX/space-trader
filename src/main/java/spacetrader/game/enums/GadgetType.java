package spacetrader.game.enums;

public enum GadgetType implements EquipmentSubType, SpaceTraderEnum {

    ExtraCargoBays, // = 0,
    AutoRepairSystem, // = 1,
    NavigatingSystem, // = 2,
    TargetingSystem, // = 3,
    CloakingDevice, // = 4,
    FuelCompactor, // = 5,
    HiddenCargoBays;// = 6

    public static GadgetType fromInt(int i) {
        return values()[i];
    }

    public int castToInt() {
        return ordinal();
    }
}