package spacetrader.enums;
	public enum CargoSellOp implements SpaceTraderEnum
	{
		SellSystem,
		SellTrader,
		Dump,
		Jettison;

		public int CastToInt()
		{
return ordinal();
		}
	};