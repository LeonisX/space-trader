package spacetrader.game.enums;

import spacetrader.game.Strings;

public enum PoliceRecordType implements SpaceTraderEnum {

    PSYCHOPATH, // = 0,
    VILLAIN, // = 1,
    CRIMINAL, // = 2,
    CROOK, // = 3,
    DUBIOUS, // = 4,
    CLEAN, // = 5,
    LAWFUL, // = 6,
    TRUSTED, // = 7,
    LIKED, // = 8,
    HERO; // = 9

    @Override
    public int castToInt() {
        return ordinal();
    }

    public String getName() {
        return Strings.PoliceRecordNames[ordinal()];
    }
}
