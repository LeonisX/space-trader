package spacetrader.game.enums;

public enum EncounterResult implements SpaceTraderEnum {

    CONTINUE, NORMAL, KILLED, ESCAPE_POD, ARRESTED;

    @Override
    public int castToInt() {
        return ordinal();
    }
}
