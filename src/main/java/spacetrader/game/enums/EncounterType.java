package spacetrader.game.enums;

public enum EncounterType implements SpaceTraderEnum {

    BOTTLE_GOOD, // = 0,
    BOTTLE_OLD, // = 1,
    CAPTAIN_AHAB, // = 2,
    CAPTAIN_CONRAD, // = 3,
    CAPTAIN_HUIE, // = 4,
    DRAGONFLY_ATTACK, // = 5,
    DRAGONFLY_IGNORE, // = 6,
    FAMOUS_CAPTAIN_ATTACK, // = 7,
    FAMOUS_CAPT_DISABLED, // = 8,
    MARIE_CELESTE, // = 9,
    MARIE_CELESTE_POLICE, // = 10,
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
    SCARAB_ATTACK, // = 22,
    SCARAB_IGNORE, // = 23,
    TRADER_ATTACK, // = 28,
    TRADER_IGNORE, // = 29,
    TRADER_FLEE, // = 30,
    TRADER_SURRENDER, // = 31,
    TRADER_DISABLED, // = 32,
    TRADER_BUY, // = 33,
    TRADER_SELL, // = 34
    QUEST_ATTACK,
    QUEST_IGNORE;

    public EncounterType getPreviousEncounter() {
        return EncounterType.values()[this.castToInt() - 1];
    }

    @Override
    public int castToInt() {
        return ordinal();
    }
}
