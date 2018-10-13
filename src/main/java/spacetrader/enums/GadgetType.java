package spacetrader.enums;

import spacetrader.util.EquipmentSubType;

public enum GadgetType implements EquipmentSubType, SpaceTraderEnum
{
	ExtraCargoBays, // = 0,
	AutoRepairSystem, // = 1,
	NavigatingSystem, // = 2,
	TargetingSystem, // = 3,
	CloakingDevice, // = 4,
	FuelCompactor, // = 5,
	HiddenCargoBays;// = 6

	public int CastToInt()
	{
		return ordinal();
	}

	public static GadgetType FromInt(int i)
	{
return values()[i];
	}
}