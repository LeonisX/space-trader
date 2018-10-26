package spacetrader.game.enums;

public enum WeaponType implements SpaceTraderEnum, EquipmentSubType {

    PULSE_LASER, // = 0,
    BEAM_LASER, // = 1,
    MILITARY_LASER, // = 2,
    MORGANS_LASER, // = 3,
    PHOTON_DISRUPTOR, // = 4,
    QUANTUM_DISRUPTOR; // = 5

    public static WeaponType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
