package spacetrader.game.enums;

public enum EncounterType implements SpaceTraderEnum {

    PIRATE_ATTACK, // = 11,
    PIRATE_IGNORE, // = 12,
    PIRATE_FLEE, // = 13,
    PIRATE_SURRENDER, // = 14,
    PIRATE_DISABLED, // = 15,
    POLICE_ATTACK, // = 16,
    POLICE_IGNORE, // = 17,
    POLICE_FLEE, // = 18,
    POLICE_SURRENDER, // = 19,
    POLICE_DISABLED, // = 20,
    POLICE_INSPECT, // = 21,
    TRADER_ATTACK, // = 28,
    TRADER_IGNORE, // = 29,
    TRADER_FLEE, // = 30,
    TRADER_SURRENDER, // = 31,
    TRADER_DISABLED, // = 32,
    TRADER_BUY, // = 33,
    TRADER_SELL; // = 34

    public static EncounterType fromInt(int index) {
        return EncounterType.values()[index];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
