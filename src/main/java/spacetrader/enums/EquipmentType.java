package spacetrader.enums;

public enum EquipmentType implements SpaceTraderEnum// : int
{
	Weapon, Shield, Gadget;
	public int CastToInt()
	{
		return ordinal();
	}

	public static EquipmentType FromInt(int i)
	{
		return values()[i];
	}
}