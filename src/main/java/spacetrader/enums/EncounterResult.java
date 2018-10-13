package spacetrader.enums;

public enum EncounterResult implements SpaceTraderEnum
{
	Continue, Normal, Killed, EscapePod, Arrested;
	
	public int CastToInt()
	{
		return ordinal();
	}
}
