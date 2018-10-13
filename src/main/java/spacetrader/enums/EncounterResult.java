package spacetrader.enums;

public enum EncounterResult implements SpaceTraderEnum {
    Continue, Normal, Killed, EscapePod, Arrested;

    public int castToInt() {
        return ordinal();
    }
}
