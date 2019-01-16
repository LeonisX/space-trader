package spacetrader.game.enums;

public enum GameEndType implements SpaceTraderEnum {

    NA, // = -1,
    KILLED, // = 0,
    RETIRED, // = 1,
    QUEST; //BOUGHT_MOON, BOUGHT_MOON_GIRL; // = 3 //TODO need????

    public static GameEndType fromInt(int i) {
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}
