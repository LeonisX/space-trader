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

package spacetrader;
import spacetrader.util.*;
import spacetrader.enums.*;
	public class HighScoreRecord extends STSerializableObject implements Comparable<HighScoreRecord>// TODO implements Comparable
	{
		//#region Member Declarations

		private String			_name;
		private int					_score;
		private GameEndType	_type;
		private int					_days;
		private int					_worth;
		private Difficulty	_difficulty;

		//#endregion

		//#region Methods

		public HighScoreRecord(String name, int score, GameEndType type, int days, int worth, Difficulty difficulty)
		{
			_name				= name;
			_score			= score;
			_type				= type;
			_days				= days;
			_worth			= worth;
			_difficulty	= difficulty;
		}

		public HighScoreRecord(Hashtable hash)
		{
			super(hash);
			_name				= GetValueFromHash(hash, "_name", String.class);
			_score			= GetValueFromHash(hash, "_score", Integer.class);
			_type				= GetValueFromHash(hash, "_type", GameEndType.class);
			_days				= GetValueFromHash(hash, "_days", Integer.class);
			_worth			= GetValueFromHash(hash, "_worth", Integer.class);
			_difficulty	= GetValueFromHash(hash, "_difficulty", Difficulty.class);
		}

		public int CompareTo(HighScoreRecord value){return compareTo(value);}
		public int compareTo(HighScoreRecord value)
		{
			int							compared;
			HighScoreRecord	highScore	= value;

			if (value == null)
				compared	= 1;
			else if (highScore.Score() < Score())
				compared	= 1;
			else if (highScore.Score() > Score())
				compared	= -1;
			else if (highScore.Worth() < Worth())
				compared	= 1;
			else if (highScore.Worth() > Worth())
				compared	= -1;
			else if (highScore.Days() < Days())
				compared	= 1;
			else if (highScore.Days() > Days())
				compared	= -1;
			else
				compared	= 0;

			return compared;
		}

		public @Override Hashtable Serialize()
		{
			Hashtable	hash	= super.Serialize();

			hash.add("_name",				_name);
			hash.add("_score",			_score);
			hash.add("_type",				_type.CastToInt());
			hash.add("_days",				_days);
			hash.add("_worth",			_worth);
			hash.add("_difficulty",	_difficulty.CastToInt());

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

		

  public int Days(){
				return _days;
			}


		

  public Difficulty Difficulty(){
				return _difficulty;
			}


		

  public String Name(){
				return _name;
			}


		

  public int Score(){
				return _score;
			}


		

  public GameEndType Type(){
				return _type;
			}


		

  public int Worth(){
				return _worth;
			}




















		//#endregion
	}
