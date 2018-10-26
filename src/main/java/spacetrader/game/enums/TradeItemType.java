package spacetrader.game.enums;

public enum TradeItemType implements SpaceTraderEnum {

    NA,//						= -1,
    WATER,//					= 0,
    FURS,//						= 1,
    FOOD,//						= 2,
    ORE,//						= 3,
    Games,//					= 4,
    FIREARMS,//					= 5,
    MEDICINE,//					= 6,
    MACHINES,//					= 7,
    NARCOTICS,//				= 8,
    ROBOTS;//					= 9

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}
