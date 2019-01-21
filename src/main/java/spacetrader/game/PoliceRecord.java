package spacetrader.game;

import spacetrader.game.enums.PoliceRecordType;

public class PoliceRecord {

    private final PoliceRecordType type;
    private final int minScore;

    PoliceRecord(PoliceRecordType type, int minScore) {
        this.type = type;
        this.minScore = minScore;
    }

    public static PoliceRecord getPoliceRecordFromScore() {
        int i;
        for (i = 0; i < Consts.PoliceRecords.length
                && Game.getCurrentGame().getCommander().getPoliceRecordScore() >= Consts.PoliceRecords[i].getMinScore(); i++) {
        }
        return Consts.PoliceRecords[Math.max(0, i - 1)];
    }

    private int getMinScore() {
        return minScore;
    }

    public String getName() {
        return type.getName();
    }

    public PoliceRecordType getType() {
        return type;
    }

}
