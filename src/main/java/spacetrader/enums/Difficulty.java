package spacetrader.enums;

public enum Difficulty implements SpaceTraderEnum// : int
{
	Beginner(1), Easy(1), Normal(0), Hard(-1), Impossible(-1);

	public int CastToInt()
	{
		return ordinal();
	}

	private Difficulty(int skillAdjust)
	{
		this.skillAdjust = skillAdjust;
	}

	private final int skillAdjust;

	public int adjustSkill(int skill)
	{
		return skill + skillAdjust;
	}

	public static Difficulty FromInt(int i)
	{
		return values()[i];
	}
}
