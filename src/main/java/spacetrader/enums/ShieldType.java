package spacetrader.enums;

import spacetrader.util.EquipmentSubType;

public enum ShieldType implements SpaceTraderEnum , EquipmentSubType
{
	Energy, Reflective, Lightning;

	public int CastToInt()
	{
		return ordinal();
	}

	public static ShieldType FromInt(int i)
	{
		return values()[i];
	}
}