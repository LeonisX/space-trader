package spacetrader.enums;
	public enum TradeItemType implements SpaceTraderEnum//: int
	{
		NA			,//						= -1,
		Water		,//						= 0,
		Furs		,//						= 1,
		Food		,//						= 2,
		Ore			,//						= 3,
		Games		,//						= 4,
		Firearms	,//					= 5,
		Medicine	,//					= 6,
		Machines	,//					= 7,
		Narcotics	,//					= 8,
		Robots		;//					= 9
		
		public int CastToInt(){ return ordinal()-1;}
		
	};