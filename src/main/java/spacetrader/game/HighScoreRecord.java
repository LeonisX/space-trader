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
//using System;
//using System.Collections;

package spacetrader.game;

import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.GameEndType;
import spacetrader.util.Hashtable;

// TODO implements Comparable
public class HighScoreRecord extends STSerializableObject implements Comparable<HighScoreRecord> {
    //#region Member Declarations

    private String _name;
    private int _score;
    private GameEndType _type;
    private int _days;
    private int _worth;
    private Difficulty _difficulty;

    //#endregion

    //#region Methods

    HighScoreRecord(String name, int score, GameEndType type, int days, int worth, Difficulty difficulty) {
        _name = name;
        _score = score;
        _type = type;
        _days = days;
        _worth = worth;
        _difficulty = difficulty;
    }

    HighScoreRecord(Hashtable hash) {
        super();
        _name = getValueFromHash(hash, "_name", String.class);
        _score = getValueFromHash(hash, "_score", Integer.class);
        _type = getValueFromHash(hash, "_type", GameEndType.class);
        _days = getValueFromHash(hash, "_days", Integer.class);
        _worth = getValueFromHash(hash, "_worth", Integer.class);
        _difficulty = getValueFromHash(hash, "_difficulty", Difficulty.class);
    }

    int CompareTo(HighScoreRecord value) {
        return compareTo(value);
    }

    public int compareTo(HighScoreRecord value) {
        int compared;

        if (value == null)
            compared = 1;
        else if (value.Score() < Score())
            compared = 1;
        else if (value.Score() > Score())
            compared = -1;
        else if (value.Worth() < Worth())
            compared = 1;
        else if (value.Worth() > Worth())
            compared = -1;
        else compared = Integer.compare(Days(), value.Days());

        return compared;
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_name", _name);
        hash.add("_score", _score);
        hash.add("_type", _type.castToInt());
        hash.add("_days", _days);
        hash.add("_worth", _worth);
        hash.add("_difficulty", _difficulty.castToInt());

        return hash;
    }

    //#endregion

		/*  #region Operators

		public static boolean operator > (HighScoreRecord a, HighScoreRecord b)
		{
			return a.CompareTo(b) > 0;
		}

		public static boolean operator < (HighScoreRecord a, HighScoreRecord b)
		{
			return a.CompareTo(b) < 0;
		}

		//#endregion */

    //#region Properties


    public int Days() {
        return _days;
    }


    public Difficulty Difficulty() {
        return _difficulty;
    }


    public String Name() {
        return _name;
    }


    public int Score() {
        return _score;
    }


    public GameEndType Type() {
        return _type;
    }


    public int Worth() {
        return _worth;
    }


    //#endregion
}
