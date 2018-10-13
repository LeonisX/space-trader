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
// using System.Collections;
package spacetrader;

import java.util.ArrayList;

import spacetrader.enums.ShipType;
import spacetrader.enums.ShipyardId;
import spacetrader.enums.ShipyardSkill;
import spacetrader.enums.Size;

// / <summary>
// / Represents a shipyard orbiting a solar system in the universe.
// / In a shipyard, the player can design his own ship and have it finalructed, for a fee.
// / </summary>
public class Shipyard
{
	// #region Constants

	public static final int[] COST_FUEL = new int[] { 1, 1, 1, 3, 5, 10 };
	public static final int[] COST_HULL = new int[] { 1, 5, 10, 15, 20, 40 };
	public static final int[] BASE_FUEL = new int[] { 15, 14, 13, 12, 11, 10 };
	public static final int[] BASE_HULL = new int[] { 10, 25, 50, 100, 150, 200 };
	public static final int[] DESIGN_FEE = new int[] { 2000, 5000, 10000, 20000, 40000, 100000 };
	public static final int[] MAX_UNITS = new int[] { 50, 100, 150, 200, 250, 999 };
	public static final int[] PER_UNIT_FUEL = new int[] { 3, 2, 1, 1, 1, 1 };
	public static final int[] PER_UNIT_HULL = new int[] { 35, 30, 25, 20, 15, 10 };
	public static final int[] PRICE_PER_UNIT = new int[] { 75, 250, 500, 750, 1000, 1200 };
	public static final int[] UNITS_CREW = new int[] { 20, 20, 20, 20, 20, 20 };
	public static final int[] UNITS_FUEL = new int[] { 1, 1, 1, 5, 10, 15 };
	public static final int[] UNITS_GADGET = new int[] { 5, 5, 5, 5, 5, 5 };
	public static final int[] UNITS_HULL = new int[] { 1, 2, 3, 4, 5, 6 };
	public static final int[] UNITS_SHIELD = new int[] { 10, 10, 10, 8, 8, 8 };
	public static final int[] UNITS_WEAPON = new int[] { 15, 15, 15, 10, 10, 10 };

	// Fee and Price Per Unit 10% less for the specialty size, and 10% more for
	// sizes more than 1 away
	// from the specialty size.
	public static final int ADJUST_SIZE_DEFAULT = 100;
	public static final int ADJUST_SIZE_SPECIALTY = 90;
	public static final int ADJUST_SIZE_WEAKNESS = 110;

	// One of the costs will be adjusted based on the shipyard's skill.
	public static final int ADJUST_SKILL_CREW = 2;
	public static final int ADJUST_SKILL_FUEL = 2;
	public static final int ADJUST_SKILL_HULL = 5;
	public static final int ADJUST_SKILL_SHIELD = 2;
	public static final int ADJUST_SKILL_WEAPON = 2;

	// There is a crowding penalty for coming too close to the maximum. A modest
	// penalty is imposed at
	// one level, and a more severe penalty at a higher level.
	public static final int PENALTY_FIRST_PCT = 80;
	public static final int PENALTY_FIRST_FEE = 25;
	public static final int PENALTY_SECOND_PCT = 90;
	public static final int PENALTY_SECOND_FEE = 75;

	// #endregion

	// #region Member Variables

	private ShipyardId _id;
	private Size _specialtySize;
	private ShipyardSkill _skill;

	// Internal Variables
	private int modCrew = 0;
	private int modFuel = 0;
	private int modHull = 0;
	private int modShield = 0;
	private int modWeapon = 0;

	// #endregion

	// #region Methods

	public Shipyard(ShipyardId id, Size specialtySize, ShipyardSkill skill)
	{
		_id = id;
		_specialtySize = specialtySize;
		_skill = skill;

		switch (Skill())
		{
		case CrewQuarters:
			modCrew = ADJUST_SKILL_CREW;
			break;
		case FuelBase:
			modFuel = ADJUST_SKILL_FUEL;
			break;
		case HullPerUnit:
			modHull = ADJUST_SKILL_HULL;
			break;
		case ShieldSlotUnits:
			modShield = ADJUST_SKILL_SHIELD;
			break;
		case WeaponSlotUnits:
			modWeapon = ADJUST_SKILL_WEAPON;
			break;
		}
	}

	// Calculate the ship's price (worth here, not the price paid), the fuel
	// cost, and the repair cost.
	public void CalculateDependantVariables()
	{
		ShipSpec().setPrice(BasePrice() + PenaltyCost());
		ShipSpec().setFuelCost(CostFuel());
		ShipSpec().setRepairCost(CostHull());
	}

	public int AdjustedDesignFee()
	{
		return DESIGN_FEE[ShipSpec().getSize().CastToInt()] * CostAdjustment() / ADJUST_SIZE_DEFAULT;
	}

	public int AdjustedPenaltyCost()
	{
		return PenaltyCost() * CostAdjustment() / ADJUST_SIZE_DEFAULT;
	}

	public int AdjustedPrice()
	{
		return BasePrice() * CostAdjustment() / ADJUST_SIZE_DEFAULT;
	}

	public ArrayList<Size> AvailableSizes()
	{
		ArrayList<Size> list = new ArrayList<Size>(6);

		int begin = Math.max(Size.Tiny.CastToInt(), SpecialtySize().CastToInt() - 2);
		int end = Math.min(Size.Gargantuan.CastToInt(), SpecialtySize().CastToInt() + 2);
		for (int index = begin; index <= end; index++)
			list.add(Size.values()[index]);

		return list;
	}

	public int BaseFuel()
	{
		return BASE_FUEL[ShipSpec().getSize().CastToInt()] + modFuel;
	}

	public int BaseHull()
	{
		return BASE_HULL[ShipSpec().getSize().CastToInt()];
	}

	public int BasePrice()
	{
		return UnitsUsed() * PricePerUnit();
	}

	public int CostAdjustment()
	{
		int adjustment;

		switch (Math.abs(SpecialtySize().CastToInt() - ShipSpec().getSize().CastToInt()))
		{
		case 0:
			adjustment = ADJUST_SIZE_SPECIALTY;
			break;
		case 1:
			adjustment = ADJUST_SIZE_DEFAULT;
			break;
		default:
			adjustment = ADJUST_SIZE_WEAKNESS;
			break;
		}

		return adjustment;
	}

	public int CostFuel()
	{
		return COST_FUEL[ShipSpec().getSize().CastToInt()];
	}

	public int CostHull()
	{
		return COST_HULL[ShipSpec().getSize().CastToInt()];
	}

	public String Engineer()
	{
		return Strings.ShipyardEngineers[Id().CastToInt()];
	}

	public ShipyardId Id()
	{
		return _id;
	}

	public int MaxUnits()
	{
		return MAX_UNITS[ShipSpec().getSize().CastToInt()];
	}

	public String Name()
	{
		return Strings.ShipyardNames[Id().CastToInt()];
	}

	public int PenaltyCost()
	{
		int penalty = 0;

		if (PercentOfMaxUnits() >= PENALTY_SECOND_PCT)
			penalty = PENALTY_SECOND_FEE;
		else if (PercentOfMaxUnits() >= PENALTY_FIRST_PCT)
			penalty = PENALTY_FIRST_FEE;

		return BasePrice() * penalty / 100;
	}

	public int PercentOfMaxUnits()
	{
		return UnitsUsed() * 100 / MaxUnits();
	}

	public int PerUnitFuel()
	{
		return PER_UNIT_FUEL[ShipSpec().getSize().CastToInt()];
	}

	public int PerUnitHull()
	{
		return PER_UNIT_HULL[ShipSpec().getSize().CastToInt()] + modHull;
	}

	public int PricePerUnit()
	{
		return PRICE_PER_UNIT[ShipSpec().getSize().CastToInt()];
	}
	
	public ShipSpec ShipSpec()
	{
		return Consts.ShipSpecs[ShipType.Custom.CastToInt()];
	}

	public ShipyardSkill Skill()
	{
		return _skill;
	}

	public Size SpecialtySize()
	{
		return _specialtySize;
	}

	public int TotalCost()
	{
		return AdjustedPrice() + AdjustedPenaltyCost() + AdjustedDesignFee() - TradeIn();
	}

	public int TradeIn()
	{
		return Game.CurrentGame().Commander().getShip().Worth(false);
	}

	
	public int UnitsCrew()
	{
		return UNITS_CREW[ShipSpec().getSize().CastToInt()] - modCrew;
	}

	public int UnitsFuel()
	{
		return UNITS_FUEL[ShipSpec().getSize().CastToInt()];
	}

	public int UnitsGadgets()
	{
		return UNITS_GADGET[ShipSpec().getSize().CastToInt()];
	}

	public int UnitsHull()
	{
		return UNITS_HULL[ShipSpec().getSize().CastToInt()];
	}

	public int UnitsShields()
	{
		return UNITS_SHIELD[ShipSpec().getSize().CastToInt()] - modShield;
	}

	public int UnitsWeapons()
	{
		return UNITS_WEAPON[ShipSpec().getSize().CastToInt()] - modWeapon;
	}

	public int UnitsUsed()
	{
		int cargoBays = ShipSpec().CargoBays();
		int crew = ShipSpec().getCrewQuarters() * UnitsCrew();
		int fuel = (int)Math.ceil((double)(ShipSpec().FuelTanks() - BaseFuel()) / PerUnitFuel() * UnitsFuel());
		int gadgets = ShipSpec().getGadgetSlots() * UnitsGadgets();
		int hull = (ShipSpec().HullStrength() - BaseHull()) / PerUnitHull() * UnitsHull();
		int shield = ShipSpec().getShieldSlots() * UnitsShields();
		int weapons = ShipSpec().getWeaponSlots() * UnitsWeapons();

		return cargoBays + crew + fuel + gadgets + hull + shield + weapons;
	}
}
