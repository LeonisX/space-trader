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

import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.GameEndType;
import spacetrader.util.Hashtable;

public class HighScoreRecord extends STSerializableObject implements Comparable<HighScoreRecord> {

    private String name;
    private int score;
    private GameEndType type;
    private int days;
    private int worth;
    private Difficulty difficulty;

    HighScoreRecord(String name, int score, GameEndType type, int days, int worth, Difficulty difficulty) {
        this.name = name;
        this.score = score;
        this.type = type;
        this.days = days;
        this.worth = worth;
        this.difficulty = difficulty;
    }

    HighScoreRecord(Hashtable hash) {
        super();
        name = getValueFromHash(hash, "_name", String.class);
        score = getValueFromHash(hash, "_score", Integer.class);
        type = getValueFromHash(hash, "_type", GameEndType.class);
        days = getValueFromHash(hash, "_days", Integer.class);
        worth = getValueFromHash(hash, "_worth", Integer.class);
        difficulty = getValueFromHash(hash, "_difficulty", Difficulty.class);
    }

    public int compareTo(HighScoreRecord value) {
        if (value == null) {
            return 1;
        } else if (value.getScore() < getScore()) {
            return 1;
        } else if (value.getScore() > getScore()) {
            return -1;
        } else if (value.getWorth() < getWorth()) {
            return 1;
        } else if (value.getWorth() > getWorth()) {
            return -1;
        } else {
            return Integer.compare(getDays(), value.getDays());
        }
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_name", name);
        hash.add("_score", score);
        hash.add("_type", type.castToInt());
        hash.add("_days", days);
        hash.add("_worth", worth);
        hash.add("_difficulty", difficulty.castToInt());

        return hash;
    }

    public int getDays() {
        return days;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public GameEndType getType() {
        return type;
    }

    public int getWorth() {
        return worth;
    }
}
