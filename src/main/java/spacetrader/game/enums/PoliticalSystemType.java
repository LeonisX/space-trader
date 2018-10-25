package spacetrader.game.enums;

import spacetrader.game.Strings;

public enum PoliticalSystemType implements SpaceTraderEnum {

    ANARCHY, //= 0,
    CAPITALIST, //= 1,
    COMMUNIST, //= 2,
    CONFEDERACY, //= 3,
    CORPORATE, //= 4,
    CYBERNETIC, //= 5,
    DEMOCRACY, //= 6,
    DICTATORSHIP, //= 7,
    FASCIST, //= 8,
    FEUDAL, //= 9,
    MILITARY, //= 10,
    MONARCHY, //= 11,
    PACIFIST, //= 12,
    SOCIALIST, //= 13,
    SATORI, //= 14,
    TECHNOCRACY, //= 15,
    THEOCRACY;//= 16

    public static PoliticalSystemType fromInt(int i) {
        return values()[i];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }

    public String getName() {
        return Strings.PoliticalSystemNames[ordinal()];
    }
}
