package spacetrader.enums;

public enum EncounterType implements SpaceTraderEnum// : int
{
	BottleGood, // = 0,
	BottleOld, // = 1,
	CaptainAhab, // = 2,
	CaptainConrad, // = 3,
	CaptainHuie, // = 4,
	DragonflyAttack, // = 5,
	DragonflyIgnore, // = 6,
	FamousCaptainAttack, // = 7,
	FamousCaptDisabled, // = 8,
	MarieCeleste, // = 9,
	MarieCelestePolice, // = 10,
	PirateAttack, // = 11,
	PirateIgnore, // = 12,
	PirateFlee, // = 13,
	PirateSurrender, // = 14,
	PirateDisabled, // = 15,
	PoliceAttack, // = 16,
	PoliceIgnore, // = 17,
	PoliceFlee, // = 18,
	PoliceSurrender, // = 19,
	PoliceDisabled, // = 20,
	PoliceInspect, // = 21,
	ScarabAttack, // = 22,
	ScarabIgnore, // = 23,
	ScorpionAttack, // = 24,
	ScorpionIgnore, // = 25,
	SpaceMonsterAttack, // = 26,
	SpaceMonsterIgnore, // = 27,
	TraderAttack, // = 28,
	TraderIgnore, // = 29,
	TraderFlee, // = 30,
	TraderSurrender, // = 31,
	TraderDisabled, // = 32,
	TraderBuy, // = 33,
	TraderSell; // = 34
	public int CastToInt()
	{
		return ordinal();
	}

	public static EncounterType FromInt(int i)
	{
		return values()[i];
	}
}