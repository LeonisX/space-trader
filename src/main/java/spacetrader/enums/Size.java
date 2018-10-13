package spacetrader.enums;

// TODO rename "ShipSize". Is used as WorldSize too?
public enum Size implements SpaceTraderEnum// : int
{
	Tiny, // = 0,
	Small, // = 1,
	Medium, // = 2,
	Large, // = 3,
	Huge, // = 4,
	Gargantuan // = 5
	;

	public int CastToInt()
	{
		return ordinal();
	}

	public static Size FromInt(int i)
	{return values()[i];
	}
}