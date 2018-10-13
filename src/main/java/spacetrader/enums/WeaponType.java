package spacetrader.enums;

import spacetrader.util.EquipmentSubType;

public enum WeaponType implements SpaceTraderEnum , EquipmentSubType
{
	PulseLaser, // = 0,
	BeamLaser, // = 1,
	MilitaryLaser, // = 2,
	MorgansLaser, // = 3,
	PhotonDisruptor, // = 4,
	QuantumDistruptor; // = 5

	public int CastToInt()
	{
		return ordinal();
	}

	public static WeaponType FromInt(int i)
	{
		return values()[i];
	}
}