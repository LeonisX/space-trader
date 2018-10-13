package spacetrader.enums;

public enum SpecialEventType implements SpaceTraderEnum// : int
{
	NA, // = -1,
	Artifact, // = 0,
	ArtifactDelivery, // = 1,
	CargoForSale, // = 2,
	Dragonfly, // = 3,
	DragonflyBaratas, // = 4,
	DragonflyDestroyed, // = 5,
	DragonflyMelina, // = 6,
	DragonflyRegulas, // = 7,
	DragonflyShield, // = 8,
	EraseRecord, // = 9,
	Experiment, // = 10,
	ExperimentFailed, // = 11,
	ExperimentStopped, // = 12,
	Gemulon, // = 13,
	GemulonFuel, // = 14,
	GemulonInvaded, // = 15,
	GemulonRescued, // = 16,
	Japori, // = 17,
	JaporiDelivery, // = 18,
	Jarek, // = 19,
	JarekGetsOut, // = 20,
	Lottery, // = 21,
	Moon, // = 22,
	MoonRetirement, // = 23,
	Reactor, // = 24,
	ReactorDelivered, // = 25,
	ReactorLaser, // = 26,
	Scarab, // = 27,
	ScarabDestroyed, // = 28,
	ScarabUpgradeHull, // = 29,
	Skill, // = 30,
	SpaceMonster, // = 31,
	SpaceMonsterKilled, // = 32,
	Tribble, // = 33,
	TribbleBuyer, // = 34,
	Wild, // = 35,
	WildGetsOut, // = 36,
	Sculpture, // = 37,
	SculptureDelivered, // = 38,
	SculptureHiddenBays, // = 39,
	Princess, // = 40,
	PrincessCentauri, // = 41,
	PrincessInthara, // = 42,
	PrincessQonos, // = 43,
	PrincessQuantum, // = 44,
	PrincessReturned, // = 45
	;
	public int CastToInt()
	{
		return ordinal() - 1;
	}

	public static SpecialEventType FromInt(int i)
	{
		return values()[i+1];
	}
}