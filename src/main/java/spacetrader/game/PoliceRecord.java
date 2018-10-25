/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/

package spacetrader.game;

import spacetrader.game.enums.PoliceRecordType;

public class PoliceRecord {

    private final PoliceRecordType type;
    private final int minScore;

    PoliceRecord(PoliceRecordType type, int minScore) {
        this.type = type;
        this.minScore = minScore;
    }

    public static PoliceRecord getPoliceRecordFromScore(int PoliceRecordScore) {
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
