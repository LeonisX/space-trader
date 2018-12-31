package spacetrader.game.enums;

public enum SpecialEventType implements SpaceTraderEnum {

    NA, // = -1,
    Artifact, // = 0,
    ArtifactDelivery, // = 1,
    CargoForSale, // = 2,
    Dragonfly, // = 3,
    DragonflyBaratas, // = 4,
    DragonflyDestroyed, // = 5,
    DragonflyMelina, // = 6,
    DragonflyRegulas, // = 7,
    DragonflyShield, // = 8,
    EraseRecord, // = 9,
    Experiment, // = 10,
    ExperimentFailed, // = 11,
    ExperimentStopped, // = 12,
    Gemulon, // = 13,
    GemulonFuel, // = 14,
    GemulonInvaded, // = 15,
    GemulonRescued, // = 16,
    Japori, // = 17,
    JaporiDelivery, // = 18,
    Moon, // = 22,
    MoonRetirement, // = 23,
    Scarab, // = 27,
    ScarabDestroyed, // = 28,
    ScarabUpgradeHull, // = 29,
    Skill, // = 30,
    SpaceMonster, // = 31,
    SpaceMonsterKilled, // = 32,
    ASSIGNED;

    public static SpecialEventType fromInt(int i) {
        return values()[i + 1];
    }

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}
