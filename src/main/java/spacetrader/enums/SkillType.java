package spacetrader.enums;

public enum SkillType implements SpaceTraderEnum// : int
{
	NA, // = -1,
	Pilot, // = 0,
	Fighter, // = 1,
	Trader, // = 2,
	Engineer;// = 3
	public int CastToInt()
	{
		return ordinal() - 1;
	}

	public static SkillType FromInt(int i)
	{
		return values()[i + 1];
	}
}
