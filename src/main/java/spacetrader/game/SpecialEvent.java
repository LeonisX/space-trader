package spacetrader.game;

import spacetrader.game.enums.SpecialEventType;

public class SpecialEvent {

    public final static int MOON_COST = 500000;

    public final static int STATUS_ARTIFACT_NOT_STARTED = 0;
    public final static int STATUS_ARTIFACT_ON_BOARD = 1;
    public final static int STATUS_ARTIFACT_DONE = 2;

    public final static int STATUS_DRAGONFLY_NOT_STARTED = 0;
    public final static int STATUS_DRAGONFLY_FLY_BARATAS = 1;
    public final static int STATUS_DRAGONFLY_FLY_MELINA = 2;
    public final static int STATUS_DRAGONFLY_FLY_REGULAS = 3;
    public final static int STATUS_DRAGONFLY_FLY_ZALKON = 4;
    public final static int STATUS_DRAGONFLY_DESTROYED = 5;
    public final static int STATUS_DRAGONFLY_DONE = 6;

    public final static int STATUS_EXPERIMENT_NOT_STARTED = 0;
    public final static int STATUS_EXPERIMENT_STARTED = 1;
    public final static int STATUS_EXPERIMENT_DATE = 11;
    public final static int STATUS_EXPERIMENT_PERFORMED = 12;
    public final static int STATUS_EXPERIMENT_CANCELLED = 13;

    public final static int STATUS_GEMULON_NOT_STARTED = 0;
    public final static int STATUS_GEMULON_STARTED = 1;
    public final static int STATUS_GEMULON_DATE = 7;
    public final static int STATUS_GEMULON_TOO_LATE = 8;
    public final static int STATUS_GEMULON_FUEL = 9;
    public final static int STATUS_GEMULON_DONE = 10;

    public final static int STATUS_JAPORI_NOT_STARTED = 0;
    public final static int STATUS_JAPORI_IN_TRANSIT = 1;
    public final static int STATUS_JAPORI_DONE = 2;

    public final static int STATUS_MOON_NOT_STARTED = 0;
    public final static int STATUS_MOON_BOUGHT = 1;
    public final static int STATUS_MOON_DONE = 2;

    public final static int STATUS_SCARAB_NOT_STARTED = 0;
    public final static int STATUS_SCARAB_HUNTING = 1;
    public final static int STATUS_SCARAB_DESTROYED = 2;
    public final static int STATUS_SCARAB_DONE = 3;

    private SpecialEventType type;
    private int price;
    private int occurrence;
    private boolean messageOnly;

    public SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly) {
        this.type = type;
        this.price = price;
        this.occurrence = occurrence;
        this.messageOnly = messageOnly;
    }

    public StarSystem getLocation() {
        StarSystem location = null;
        StarSystem[] universe = Game.getCurrentGame().getUniverse();

        for (int i = 0; i < universe.length && location == null; i++) {
            if (universe[i].getSpecialEventType() == getType()) {
                location = universe[i];
            }
        }

        return location;
    }

    public boolean isMessageOnly() {
        return messageOnly;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public int getPrice() {
        return price;
    }

    public String getString() {
        return Strings.QuestPhaseMessages[type.castToInt()];
    }

    public String getTitle() {
        return Strings.QuestPhaseTitles[type.castToInt()];
    }

    public SpecialEventType getType() {
        return type;
    }
}
