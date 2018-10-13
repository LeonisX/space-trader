package spacetrader.enums;

public enum Activity implements SpaceTraderEnum
{
	Absent, // = 0,
	Minimal, // = 1,
	Few, // = 2,
	Some, // = 3,
	Moderate, // = 4,
	Many, // = 5,
	Abundant, // = 6,
	Swarms, // = 7,
	NA;// = 100

	
	//TODO go over all NAs, see if can use null, or atleast normalize.
	// TODO go over all CastToInt, see if needed.
	public int CastToInt()
	{
		return this == NA ? 100 : ordinal();
	}
	
	public static Activity FromInt(int i)
	{
		if (i==100) return NA;
		return values()[i];
	}
};