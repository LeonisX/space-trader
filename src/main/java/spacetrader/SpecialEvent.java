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

import spacetrader.enums.SpecialEventType;

public class SpecialEvent
{
	// #region Constants
	public final static int MoonCost = 500000;

	public final static int StatusArtifactNotStarted = 0;
	public final static int StatusArtifactOnBoard = 1;
	public final static int StatusArtifactDone = 2;

	public final static int StatusDragonflyNotStarted = 0;
	public final static int StatusDragonflyFlyBaratas = 1;
	public final static int StatusDragonflyFlyMelina = 2;
	public final static int StatusDragonflyFlyRegulas = 3;
	public final static int StatusDragonflyFlyZalkon = 4;
	public final static int StatusDragonflyDestroyed = 5;
	public final static int StatusDragonflyDone = 6;

	public final static int StatusExperimentNotStarted = 0;
	public final static int StatusExperimentStarted = 1;
	public final static int StatusExperimentDate = 11;
	public final static int StatusExperimentPerformed = 12;
	public final static int StatusExperimentCancelled = 13;

	public final static int StatusGemulonNotStarted = 0;
	public final static int StatusGemulonStarted = 1;
	public final static int StatusGemulonDate = 7;
	public final static int StatusGemulonTooLate = 8;
	public final static int StatusGemulonFuel = 9;
	public final static int StatusGemulonDone = 10;

	public final static int StatusJaporiNotStarted = 0;
	public final static int StatusJaporiInTransit = 1;
	public final static int StatusJaporiDone = 2;

	public final static int StatusJarekNotStarted = 0;
	public final static int StatusJarekStarted = 1;
	public final static int StatusJarekImpatient = 11;
	public final static int StatusJarekDone = 12;

	public final static int StatusMoonNotStarted = 0;
	public final static int StatusMoonBought = 1;
	public final static int StatusMoonDone = 2;

	public final static int StatusPrincessNotStarted = 0;
	public final static int StatusPrincessFlyCentauri = 1;
	public final static int StatusPrincessFlyInthara = 2;
	public final static int StatusPrincessFlyQonos = 3;
	public final static int StatusPrincessRescued = 4;
	public final static int StatusPrincessImpatient = 14;
	public final static int StatusPrincessReturned = 15;
	public final static int StatusPrincessDone = 16;

	public final static int StatusReactorNotStarted = 0;
	public final static int StatusReactorFuelOk = 1;
	public final static int StatusReactorDate = 20;
	public final static int StatusReactorDelivered = 21;
	public final static int StatusReactorDone = 22;

	public final static int StatusScarabNotStarted = 0;
	public final static int StatusScarabHunting = 1;
	public final static int StatusScarabDestroyed = 2;
	public final static int StatusScarabDone = 3;

	public final static int StatusSculptureNotStarted = 0;
	public final static int StatusSculptureInTransit = 1;
	public final static int StatusSculptureDelivered = 2;
	public final static int StatusSculptureDone = 3;

	public final static int StatusSpaceMonsterNotStarted = 0;
	public final static int StatusSpaceMonsterAtAcamar = 1;
	public final static int StatusSpaceMonsterDestroyed = 2;
	public final static int StatusSpaceMonsterDone = 3;

	public final static int StatusWildNotStarted = 0;
	public final static int StatusWildStarted = 1;
	public final static int StatusWildImpatient = 11;
	public final static int StatusWildDone = 12;

	private SpecialEventType _type;
	private int _price;
	private int _occurrence;
	private boolean _messageOnly;

	public SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
	{
		_type = type;
		_price = price;
		_occurrence = occurrence;
		_messageOnly = messageOnly;
	}

	public StarSystem Location()
	{
		StarSystem location = null;
		StarSystem[] universe = Game.CurrentGame().Universe();

		for (int i = 0; i < universe.length && location == null; i++)
			if (universe[i].SpecialEventType() == Type())
				location = universe[i];

		return location;
	}

	public boolean MessageOnly()
	{
		return _messageOnly;
	}

	public int Occurrence()
	{
		return _occurrence;
	}

	public int Price()
	{
		return _price;
	}

	public String String()
	{
		return Strings.SpecialEventStrings[_type.CastToInt()];
	}

	public String Title()
	{
		return Strings.SpecialEventTitles[_type.CastToInt()];
	}

	public SpecialEventType Type()
	{
		return _type;
	}
}
