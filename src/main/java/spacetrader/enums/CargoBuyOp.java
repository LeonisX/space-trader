package spacetrader.enums;
public enum CargoBuyOp implements SpaceTraderEnum
	{
		BuySystem,
		BuyTrader,
		Plunder;

		public int CastToInt()
		{
return ordinal();
		}
	};