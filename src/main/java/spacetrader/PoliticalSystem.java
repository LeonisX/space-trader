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
package spacetrader;

import spacetrader.enums.*;

public class PoliticalSystem
{
	// #region Member Declarations

	private final PoliticalSystemType _type;
	private final int _reactionIllegal; // Reaction level of illegal goods 0 = total
	// acceptance (determines how police reacts
	// if they find you carry them)
	private final Activity _activityPolice; // Activity level of police force 0 = no
	// police (determines occurrence rate)
	private final Activity _activityPirates; // Activity level of pirates 0 = no
	// pirates
	private final Activity _activityTraders; // Activity level of traders 0 = no
	// traders
	private final TechLevel _minTech; // Mininum tech level needed
	private final TechLevel _maxTech; // Maximum tech level where this is found
	private final int _bribeLevel; // Indicates how easily someone can be bribed 0 =
	// unbribeable/high bribe costs
	private final boolean _drugsOk; // Drugs can be traded (if not, people aren't
	// interested or the governemnt is too strict)
	private final boolean _firearmsOk; // Firearms can be traded (if not, people
	// aren't interested or the governemnt is
	// too strict)
	private final TradeItemType _wanted; // Tradeitem requested in particular in this

	// type of government

	// #endregion

	// #region Methods

	public PoliticalSystem(PoliticalSystemType type, int reactionIllegal, Activity activityPolice,
			Activity activityPirates, Activity activityTraders, TechLevel minTechLevel, TechLevel maxTechLevel,
			int bribeLevel, boolean drugsOk, boolean firearmsOk, TradeItemType wanted)
	{
		_type = type;
		_reactionIllegal = reactionIllegal;
		_activityPolice = activityPolice;
		_activityPirates = activityPirates;
		_activityTraders = activityTraders;
		_minTech = minTechLevel;
		_maxTech = maxTechLevel;
		_bribeLevel = bribeLevel;
		_drugsOk = drugsOk;
		_firearmsOk = firearmsOk;
		_wanted = wanted;
	}

	public boolean ShipTypeLikely(ShipType shipType, OpponentType oppType)
	{
		boolean likely = false;
		int diffMod = Math
				.max(0, Game.CurrentGame().Difficulty().CastToInt() - Difficulty.Normal.CastToInt());

		switch (oppType)
		{
		case Pirate:
			likely = ActivityPirates().CastToInt() + diffMod >= Consts.ShipSpecs[shipType.CastToInt()]
					.Pirates().CastToInt();
			break;
		case Police:
			likely = ActivityPolice().CastToInt() + diffMod >= Consts.ShipSpecs[shipType.CastToInt()]
					.Police().CastToInt();
			break;
		case Trader:
			likely = ActivityTraders().CastToInt() + diffMod >= Consts.ShipSpecs[shipType.CastToInt()]
					.Traders().CastToInt();
			break;
		}

		return likely;
	}

	// #endregion

	// #region Properties

	public Activity ActivityPirates()
	{
		return _activityPirates;
	}

	public Activity ActivityPolice()
	{
		return _activityPolice;
	}

	public Activity ActivityTraders()
	{
		return _activityTraders;
	}

	public int BribeLevel()
	{
		return _bribeLevel;
	}

	public boolean DrugsOk()
	{
		return _drugsOk;
	}

	public boolean FirearmsOk()
	{
		return _firearmsOk;
	}

	public TechLevel MaximumTechLevel()
	{
		return _maxTech;
	}

	public TechLevel MinimumTechLevel()
	{
		return _minTech;
	}

	public String Name()
	{
		return _type.getName();
	}

	public int ReactionIllegal()
	{
		return _reactionIllegal;
	}

	public PoliticalSystemType Type()
	{
		return _type;
	}

	public TradeItemType Wanted()
	{
		return _wanted;
	}

	// #endregion
}
