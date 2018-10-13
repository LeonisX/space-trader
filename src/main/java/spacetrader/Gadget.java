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

import spacetrader.enums.GadgetType;
import spacetrader.enums.SkillType;
import spacetrader.enums.TechLevel;
import spacetrader.util.EquipmentSubType;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;

public class Gadget extends Equipment
{
	// #region Member Declarations

	private GadgetType _type;
	private SkillType _skillBonus;

	// #endregion

	// #region Methods

	public Gadget(GadgetType type, SkillType skillBonus, int price, TechLevel minTechLevel, int chance)
	{
		super(spacetrader.enums.EquipmentType.Gadget, price, minTechLevel, chance);
		_type = type;
		_skillBonus = skillBonus;
	}

	public Gadget(Hashtable hash)
	{
		super(hash);
		_type = GadgetType.FromInt(GetValueFromHash(hash, "_type", Integer.class));
		_skillBonus = ( GetValueFromHash(hash, "_skillBonus", SkillType.NA,SkillType.class));
	}

	public @Override
	Equipment Clone()
	{
		return new Gadget(_type, _skillBonus, _price, _minTech, _chance);
	}

	public @Override
	Hashtable Serialize()
	{
		Hashtable hash = super.Serialize();

		hash.add("_type", _type.CastToInt());
		hash.add("_skillBonus", _skillBonus.CastToInt());

		return hash;
	}

	public @Override
	boolean TypeEquals(Object type)
	{
		boolean equal = false;

		try
		{
			if (Type() == (GadgetType) type)
				equal = true;
		} catch (Exception e)
		{
			Log.write("Ignored Exception " + e);
		}

		return equal;
	}

	// #endregion

	// #region Properties
	@Override
	public String Name()
	{
		return Strings.GadgetNames[_type.CastToInt()];
	}

	@Override
	public EquipmentSubType SubType()
	{
		return Type();
	}

	public GadgetType Type()
	{
		return _type;
	}

	public SkillType SkillBonus()
	{
		return _skillBonus;
	}

	// #endregion
}
