package spacetrader.game;

import spacetrader.game.enums.ReputationType;

public class Reputation {

    private final ReputationType type;
    private final int minScore;

    Reputation(ReputationType type, int minScore) {
        this.type = type;
        this.minScore = minScore;
    }

    public static Reputation getReputationFromScore() {
        int i;
        for (i = 0; i < Consts.Reputations.length
                && Game.getCurrentGame().getCommander().getReputationScore() >= Consts.Reputations[i].getMinScore(); i++) {
        }
        return Consts.Reputations[Math.max(0, i - 1)];
    }

    private int getMinScore() {
        return minScore;
    }


    public String getName() {
        return type.getName();
    }


    public ReputationType getType() {
        return type;
    }

}
