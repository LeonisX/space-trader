package spacetrader.enums;

public enum NewsEvent implements SpaceTraderEnum// : int
{
	ArtifactDelivery, // = 0,
	CaughtLittering, // = 1,
	Dragonfly, // = 2,
	DragonflyBaratas, // = 3,
	DragonflyDestroyed, // = 4,
	DragonflyMelina, // = 5,
	DragonflyRegulas, // = 6,
	DragonflyZalkon, // = 7,
	ExperimentFailed, // = 8,
	ExperimentPerformed, // = 9,
	ExperimentStopped, // = 10,
	ExperimentArrival, // = 11,
	Gemulon, // = 12,
	GemulonInvaded, // = 13,
	GemulonRescued, // = 14,
	CaptAhabAttacked, // = 15,
	CaptAhabDestroyed, // = 16,
	CaptConradAttacked, // = 17,
	CaptConradDestroyed, // = 18,
	CaptHuieAttacked, // = 19,
	CaptHuieDestroyed, // = 20,
	Japori, // = 21,
	JaporiDelivery, // = 22,
	JarekGetsOut, // = 23,
	Scarab, // = 24,
	ScarabDestroyed, // = 25,
	ScarabHarass, // = 26,
	SpaceMonster, // = 27,
	SpaceMonsterKilled, // = 28,
	WildArrested, // = 29,
	WildGetsOut, // = 30,
	SculptureStolen, // = 31,
	SculptureTracked, // = 32,
	Princess, // = 33,
	PrincessCentauri, // = 34,
	PrincessInthara, // = 35,
	PrincessQonos, // = 36,
	PrincessRescued, // = 37,
	PrincessReturned;// = 38

	public int CastToInt()
	{
		return ordinal();
	}

	public static NewsEvent FromInt(int i)
	{
return values()[i];
	}
}