package spacetrader.enums;

public enum CrewMemberId implements SpaceTraderEnum
{
	NA, // = -1,
	Commander, // = 0,
	Alyssa, // = 1,
	Armatur, // = 2,
	Bentos, // = 3,
	C2U2, // = 4,
	ChiTi, // = 5,
	Crystal, // = 6,
	Dane, // = 7,
	Deirdre, // = 8,
	Doc, // = 9,
	Draco, // = 10,
	Iranda, // = 11,
	Jeremiah, // = 12,
	Jujubal, // = 13,
	Krydon, // = 14,
	Luis, // = 15,
	Mercedez, // = 16,
	Milete, // = 17,
	MuriL, // = 18,
	Mystyc, // = 19,
	Nandi, // = 20,
	Orestes, // = 21,
	Pancho, // = 22,
	PS37, // = 23,
	Quarck, // = 24,
	Sosumi, // = 25,
	Uma, // = 26,
	Wesley, // = 27,
	Wonton, // = 28,
	Yorvick, // = 29,
	Zeethibal, // = 30,
	Opponent, // = 31,
	Wild, // = 32,
	Jarek, // = 33,
	FamousCaptain, // = 34,
	Dragonfly, // = 35,
	Scarab, // = 36,
	SpaceMonster, // = 37,
	Aragorn, // = 38,
	Brady, // = 39,
	EightOfNine, // = 40,
	Fangorn, // = 41,
	Gagarin, // = 42,
	Hoshi, // = 43,
	Jackson, // = 44,
	Kaylee, // = 45,
	Marcus, // = 46,
	ONeill, // = 47,
	Ripley, // = 48,
	Stilgar, // = 49,
	Taggart, // = 50,
	Vansen, // = 51,
	Xizor, // = 52,
	Princess, // = 53,
	Scorpion;// = 54
	public int CastToInt()
	{
		return ordinal() - 1;
	}

	public static CrewMemberId FromInt(int i)
	{
		return values()[i+1];
	}
}