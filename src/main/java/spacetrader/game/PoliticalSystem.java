/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron
 * Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/

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

    boolean ShipTypeLikely(ShipType shipType, OpponentType oppType) {
        boolean likely = false;
        int diffMod = Math
                .max(0, Game.getCurrentGame().getDifficulty().castToInt() - Difficulty.NORMAL.castToInt());

        switch (oppType) {
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

        return likely;
    }

    public PoliticalSystemType getType() {
        return type;
    }

    //TODO ???
    public int getReactionIllegal() {
        return reactionIllegal;
    }

    public Activity getActivityPolice() {
        return activityPolice;
    }

    public Activity getActivityPirates() {
        return activityPirates;
    }

    Activity getActivityTraders() {
        return activityTraders;
    }

    TechLevel getMinimumTechLevel() {
        return minTech;
    }

    TechLevel getMaximumTechLevel() {
        return maxTech;
    }

    int getBribeLevel() {
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
