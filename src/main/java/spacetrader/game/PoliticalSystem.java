package spacetrader.game;

import spacetrader.game.enums.*;

public class PoliticalSystem {

    private final PoliticalSystemType type;
    // Reaction level of illegal goods 0 = total acceptance (determines how police reacts if they find you carry them)
    private final int reactionIllegal;
    // Activity level of police force 0 = no police (determines occurrence rate)
    private final Activity activityPolice;
    // Activity level of pirates 0 = no pirates
    private final Activity activityPirates;
    // Activity level of traders 0 = no traders
    private final Activity activityTraders;
    // Minimum tech level needed
    private final TechLevel minTech;
    // Maximum tech level where this is found
    private final TechLevel maxTech;
    // Indicates how easily someone can be bribed 0 = unbribeable/high bribe costs
    private final int bribeLevel;
    // Drugs can be traded (if not, people aren't interested or the government is too strict)
    private final boolean drugsOk;
    // Firearms can be traded (if not, people aren't interested or the government is too strict)
    private final boolean firearmsOk;
    // Tradeitem requested in particular in this
    private final TradeItemType wanted;

    PoliticalSystem(PoliticalSystemType type, int reactionIllegal, Activity activityPolice,
                    Activity activityPirates, Activity activityTraders, TechLevel minTechLevel, TechLevel maxTechLevel,
                    int bribeLevel, boolean drugsOk, boolean firearmsOk, TradeItemType wanted) {
        this.type = type;
        this.reactionIllegal = reactionIllegal;
        this.activityPolice = activityPolice;
        this.activityPirates = activityPirates;
        this.activityTraders = activityTraders;
        this.minTech = minTechLevel;
        this.maxTech = maxTechLevel;
        this.bribeLevel = bribeLevel;
        this.drugsOk = drugsOk;
        this.firearmsOk = firearmsOk;
        this.wanted = wanted;
    }

    boolean ShipTypeLikely(ShipType shipType, int oppType) {
        boolean likely = false;
        int diffMod = Math.max(0, Game.getCurrentGame().getDifficultyId() - Difficulty.NORMAL.castToInt());

        if (oppType < 1000) {
            switch (OpponentType.fromInt(oppType)) {
                case PIRATE:
                    likely = getActivityPirates().castToInt() + diffMod >= Consts.ShipSpecs[shipType.castToInt()]
                            .getPirates().castToInt();
                    break;
                case POLICE:
                    likely = getActivityPolice().castToInt() + diffMod >= Consts.ShipSpecs[shipType.castToInt()]
                            .getPolice().castToInt();
                    break;
                case TRADER:
                    likely = getActivityTraders().castToInt() + diffMod >= Consts.ShipSpecs[shipType.castToInt()]
                            .getTraders().castToInt();
                    break;
            }
        }

        return likely;
    }

    public PoliticalSystemType getType() {
        return type;
    }

    public int getReactionIllegal() {
        return reactionIllegal;
    }

    public Activity getActivityPolice() {
        return activityPolice;
    }

    public Activity getActivityPirates() {
        return activityPirates;
    }

    public Activity getActivityTraders() {
        return activityTraders;
    }

    TechLevel getMinimumTechLevel() {
        return minTech;
    }

    TechLevel getMaximumTechLevel() {
        return maxTech;
    }

    public int getBribeLevel() {
        return bribeLevel;
    }

    boolean isDrugsOk() {
        return drugsOk;
    }

    boolean isFirearmsOk() {
        return firearmsOk;
    }

    TradeItemType getWanted() {
        return wanted;
    }

    public String getName() {
        return type.getName();
    }
}
