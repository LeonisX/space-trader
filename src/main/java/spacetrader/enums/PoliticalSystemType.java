package spacetrader.enums;

import spacetrader.Strings;

public enum PoliticalSystemType implements SpaceTraderEnum//: int
{
	Anarchy, //= 0,
	Capitalist, //= 1,
	Communist, //= 2,
	Confederacy, //= 3,
	Corporate, //= 4,
	Cybernetic, //= 5,
	Democracy, //= 6,
	Dictatorship, //= 7,
	Fascist, //= 8,
	Feudal, //= 9,
	Military, //= 10,
	Monarchy, //= 11,
	Pacifist, //= 12,
	Socialist, //= 13,
	Satori, //= 14,
	Technocracy, //= 15,
	Theocracy;//= 16

	public int CastToInt()
	{
		return ordinal();
	}

	public static PoliticalSystemType FromInt(int i)
	{
		return values()[i];
	}

	public String getName()
	{
		return Strings.PoliticalSystemNames[ordinal()];
	}
}
