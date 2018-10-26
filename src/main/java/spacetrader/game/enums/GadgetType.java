package spacetrader.game.enums;

public enum GadgetType implements EquipmentSubType, SpaceTraderEnum {

    EXTRA_CARGO_BAYS, // = 0,
    AUTO_REPAIR_SYSTEM, // = 1,
    NAVIGATING_SYSTEM, // = 2,
    TARGETING_SYSTEM, // = 3,
    CLOAKING_DEVICE, // = 4,
    FUEL_COMPACTOR, // = 5,
    HIDDEN_CARGO_BAYS;// = 6

    public static GadgetType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
