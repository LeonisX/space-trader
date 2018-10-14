package spacetrader.game.enums;

public enum WeaponType implements SpaceTraderEnum, EquipmentSubType {

    PulseLaser, // = 0,
    BeamLaser, // = 1,
    MilitaryLaser, // = 2,
    MorgansLaser, // = 3,
    PhotonDisruptor, // = 4,
    QuantumDisruptor; // = 5

    public static WeaponType fromInt(int i) {
        return values()[i];
    }

    public int castToInt() {
        return ordinal();
    }
}