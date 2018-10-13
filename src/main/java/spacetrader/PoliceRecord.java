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
// using System;
package spacetrader;

import spacetrader.enums.PoliceRecordType;

public class PoliceRecord
{
	// #region Member Declarations

	private final PoliceRecordType _type;
	private final int _minScore;

	// #endregion

	// #region Methods

	public PoliceRecord(PoliceRecordType type, int minScore)
	{
		_type = type;
		_minScore = minScore;
	}

	public static PoliceRecord GetPoliceRecordFromScore(int PoliceRecordScore)
	{
		int i;
		for (i = 0; i < Consts.PoliceRecords.length
				&& Game.CurrentGame().Commander().getPoliceRecordScore() >= Consts.PoliceRecords[i].MinScore(); i++)
			;
		return Consts.PoliceRecords[Math.max(0, i - 1)];
	}

	// #endregion

	// #region Properties



	public int MinScore()
	{
		return _minScore;
	}



	public String Name()
	{
		return _type.getName();
	}



	public PoliceRecordType Type()
	{
		return _type;
	}

	// #endregion
}
