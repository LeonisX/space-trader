package spacetrader.enums;

public enum VeryRareEncounter implements SpaceTraderEnum// : int
{
	MarieCeleste, // = 0,
	CaptainAhab, // = 1,
	CaptainConrad, // = 2,
	CaptainHuie, // = 3,
	BottleOld, // = 4,
	BottleGood, // = 5
		;
	
	public int CastToInt()
	{
		return ordinal();
	}
}