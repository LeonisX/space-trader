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

import spacetrader.enums.*;
import spacetrader.stub.ArrayList;
import spacetrader.util.Hashtable;
import spacetrader.util.Util;

public class Ship extends ShipSpec
{
	// #region Member Declarations

	private int _fuel;
	private int _hull;
	private int _tribbles = 0;
	private int[] _cargo = new int[10];
	private Weapon[] _weapons;
	private Shield[] _shields;
	private Gadget[] _gadgets;
	private CrewMember[] _crew;
	private boolean _pod = false;

	// The following does not need to be saved. It's more of a temp variable.
	private boolean[] _tradeableItems;

	// #endregion

	// #region Methods

	public Ship(ShipType type)
	{
		SetValues(type);
	}

	public Ship(OpponentType oppType)
	{
		if (oppType == OpponentType.FamousCaptain)
		{
			SetValues(Consts.ShipSpecs[Consts.MaxShip].Type());

			for (int i = 0; i < Shields().length; i++)
				AddEquipment(Consts.Shields[ShieldType.Reflective.CastToInt()]);

			for (int i = 0; i < Weapons().length; i++)
				AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.CastToInt()]);

			AddEquipment(Consts.Gadgets[GadgetType.NavigatingSystem.CastToInt()]);
			AddEquipment(Consts.Gadgets[GadgetType.TargetingSystem.CastToInt()]);

			Crew()[0] = Game.CurrentGame().Mercenaries()[CrewMemberId.FamousCaptain.CastToInt()];
		} else if (oppType == OpponentType.Bottle)
		{
			SetValues(ShipType.Bottle);
		} else
		{
			int tries = oppType == OpponentType.Mantis ? Game.CurrentGame().Difficulty().CastToInt() + 1 : Math
					.max(1, Game.CurrentGame().Commander().Worth() / 150000
							+ Game.CurrentGame().Difficulty().CastToInt() - Difficulty.Normal.CastToInt());

			GenerateOpponentShip(oppType);
			GenerateOpponentAddCrew();
			GenerateOpponentAddGadgets(tries);
			GenerateOpponentAddShields(tries);
			GenerateOpponentAddWeapons(tries);

			if (oppType != OpponentType.Mantis)
				GenerateOpponentSetHullStrength();

			if (oppType != OpponentType.Police)
				GenerateOpponentAddCargo(oppType == OpponentType.Pirate);
		}
	}

	public Ship(Hashtable hash)
	{
		super(hash);
		_fuel = GetValueFromHash(hash, "_fuel", Integer.class);
		_hull = GetValueFromHash(hash, "_hull", Integer.class);
		_tribbles = GetValueFromHash(hash, "_tribbles", _tribbles);
		_cargo = GetValueFromHash(hash, "_cargo", _cargo, int[].class);
		_weapons = (Weapon[])ArrayListToArray(GetValueFromHash(hash, "_weapons", ArrayList.class), "Weapon");
		_shields = (Shield[])ArrayListToArray(GetValueFromHash(hash, "_shields", ArrayList.class), "Shield");
		_gadgets = (Gadget[])ArrayListToArray(GetValueFromHash(hash, "_gadgets", ArrayList.class), "Gadget");
		_pod = GetValueFromHash(hash, "_pod", _pod);

		int[] crewIds = GetValueFromHash(hash, "_crewIds", (new int[0]), int[].class);
		_crew = new CrewMember[crewIds.length];
		for (int index = 0; index < _crew.length; index++)
		{
			CrewMemberId id = CrewMemberId.FromInt(crewIds[index]);
			_crew[index] = (id == CrewMemberId.NA ? null : Game.CurrentGame().Mercenaries()[id.CastToInt()]);
		}
	}

	public void AddEquipment(Equipment item)
	{
		Equipment[] equip = EquipmentByType(item.EquipmentType());

		int slot = -1;
		for (int i = 0; i < equip.length && slot == -1; i++)
			if (equip[i] == null)
				slot = i;

		if (slot >= 0)
			equip[slot] = item.Clone();
	}

	public int BaseWorth(boolean forInsurance)
	{
		int i;
		int price =
		// Trade-in value is three-fourths the original price
		getPrice() * (getTribbles() > 0 && !forInsurance ? 1 : 3) / 4
		// subtract repair costs
				- (HullStrength() - getHull()) * getRepairCost()
				// subtract costs to fill tank with fuel
				- (FuelTanks() - getFuel()) * getFuelCost();
		// Add 3/4 of the price of each item of equipment
		for (i = 0; i < _weapons.length; i++)
			if (_weapons[i] != null)
				price += _weapons[i].SellPrice();
		for (i = 0; i < _shields.length; i++)
			if (_shields[i] != null)
				price += _shields[i].SellPrice();
		for (i = 0; i < _gadgets.length; i++)
			if (_gadgets[i] != null)
				price += _gadgets[i].SellPrice();

		return price;
	}

	public int Bounty()
	{
		int price = getPrice();

		for (int i = 0; i < Weapons().length; i++)
			if (Weapons()[i] != null)
				price += Weapons()[i].Price();

		for (int i = 0; i < Shields().length; i++)
			if (Shields()[i] != null)
				price += Shields()[i].Price();

		// Gadgets aren't counted in the price, because they are already taken
		// into account in
		// the skill adjustment of the price.

		price = price * (2 * Pilot() + Engineer() + 3 * Fighter()) / 60;

		// Divide by 200 to get the bounty, then round down to the nearest 25.
		int bounty = price / 200 / 25 * 25;

		return Math.max(25, Math.min(2500, bounty));
	}

	public Equipment[] EquipmentByType(EquipmentType type)
	{
		Equipment[] equip = null;
		switch (type)
		{
		case Weapon:
			equip = Weapons();
			break;
		case Shield:
			equip = Shields();
			break;
		case Gadget:
			equip = Gadgets();
			break;
		}
		return equip;
	}

	public void Fire(CrewMemberId crewId)
	{
		int skill = Trader();
		boolean found = false;
		CrewMember merc = null;
		for (int i = 0; i < Crew().length; i++)
		{
			if (Crew()[i] != null && Crew()[i].Id() == crewId)
			{
				found = true;
				merc = Crew()[i];
			}

			if (found)
				Crew()[i] = (i < Crew().length - 1) ? Crew()[i + 1] : null;
		}

		if (Trader() != skill)
			Game.CurrentGame().RecalculateBuyPrices(Game.CurrentGame().Commander().getCurrentSystem());

		if (merc != null && !Util.ArrayContains(Consts.SpecialCrewMemberIds, (merc.Id())))
		{
			StarSystem[] universe = Game.CurrentGame().Universe();

			// The leaving Mercenary travels to a nearby random system.
			merc.setCurrentSystemId(StarSystemId.NA);
			while (merc.getCurrentSystemId() == StarSystemId.NA)
			{
				StarSystem system = universe[Functions.GetRandom(universe.length)];
				if (Functions.Distance(system, Game.CurrentGame().Commander().getCurrentSystem()) < Consts.MaxRange)
					merc.setCurrentSystemId(system.Id());
			}
		}
	}

	private void GenerateOpponentAddCargo(boolean pirate)
	{
		if (CargoBays() > 0)
		{
			Difficulty diff = Game.CurrentGame().Difficulty();
			int baysToFill = CargoBays();

			if (diff.CastToInt() >= Difficulty.Normal.CastToInt())
				baysToFill = Math.min(15, 3 + Functions.GetRandom(baysToFill - 5));

			if (pirate)
			{
				if (diff.CastToInt() < Difficulty.Normal.CastToInt())
					baysToFill = baysToFill * 4 / 5;
				else
					baysToFill = Math.max(1, baysToFill / diff.CastToInt());
			}

			for (int bays, i = 0; i < baysToFill; i += bays)
			{
				int item = Functions.GetRandom(Consts.TradeItems.length);
				bays = Math.min(baysToFill - i, 1 + Functions.GetRandom(10 - item));
				Cargo()[item] += bays;
			}
		}
	}

	private void GenerateOpponentAddCrew()
	{
		CrewMember[] mercs = Game.CurrentGame().Mercenaries();
		Difficulty diff = Game.CurrentGame().Difficulty();

		Crew()[0] = mercs[CrewMemberId.Opponent.CastToInt()];
		Crew()[0].Pilot(1 + Functions.GetRandom(Consts.MaxSkill));
		Crew()[0].Fighter(1 + Functions.GetRandom(Consts.MaxSkill));
		Crew()[0].Trader(1 + Functions.GetRandom(Consts.MaxSkill));

		if (Game.CurrentGame().WarpSystem().Id() == StarSystemId.Kravat && WildOnBoard()
				&& Functions.GetRandom(10) < diff.CastToInt() + 1)
			Crew()[0].Engineer(Consts.MaxSkill);
		else
			Crew()[0].Engineer(1 + Functions.GetRandom(Consts.MaxSkill));

		int numCrew = 0;
		if (diff == Difficulty.Impossible)
			numCrew = getCrewQuarters();
		else
		{
			numCrew = 1 + Functions.GetRandom(getCrewQuarters());
			if (diff == Difficulty.Hard && numCrew < getCrewQuarters())
				numCrew++;
		}

		for (int i = 1; i < numCrew; i++)
		{
			// Keep getting a new random mercenary until we have a non-special
			// one.
			while (Crew()[i] == null || Util.ArrayContains(Consts.SpecialCrewMemberIds, Crew()[i].Id()))
				Crew()[i] = mercs[Functions.GetRandom(mercs.length)];
		}
	}

	private void GenerateOpponentAddGadgets(int tries)
	{
		if (getGadgetSlots() > 0)
		{
			int numGadgets = 0;

			if (Game.CurrentGame().Difficulty() == Difficulty.Impossible)
				numGadgets = getGadgetSlots();
			else
			{
				numGadgets = Functions.GetRandom(getGadgetSlots() + 1);
				if (numGadgets < getGadgetSlots() && (tries > 4 || (tries > 2 && Functions.GetRandom(2) > 0)))
					numGadgets++;
			}

			for (int i = 0; i < numGadgets; i++)
			{
				int bestGadgetType = 0;
				for (int j = 0; j < tries; j++)
				{
					int x = Functions.GetRandom(100);
					int sum = Consts.Gadgets[0].Chance();
					int gadgetType = 0;
					while (sum < x && gadgetType <= Consts.Gadgets.length - 1)
					{
						gadgetType++;
						sum += Consts.Gadgets[gadgetType].Chance();
					}
					if (!HasGadget(Consts.Gadgets[gadgetType].Type()) && gadgetType > bestGadgetType)
						bestGadgetType = gadgetType;
				}

				AddEquipment(Consts.Gadgets[bestGadgetType]);
			}
		}
	}

	public void setTribbles(int tribbles)
	{
		_tribbles = tribbles;
	}

	public int getTribbles()
	{
		return _tribbles;
	}

	public void setHull(int hull)
	{
		_hull = hull;
	}

	public int getHull()
	{
		return _hull;
	}

	public void setFuel(int fuel)
	{
		_fuel = fuel;
	}

	public int getFuel()
	{
		return _fuel;
	}

	public void setEscapePod(boolean escapePod)
	{
		EscapePod = escapePod;
	}

	public boolean getEscapePod()
	{
		return EscapePod;
	}

	private void GenerateOpponentAddShields(int tries)
	{
		if (getShieldSlots() > 0)
		{
			int numShields = 0;

			if (Game.CurrentGame().Difficulty() == Difficulty.Impossible)
				numShields = getShieldSlots();
			else
			{
				numShields = Functions.GetRandom(getShieldSlots() + 1);
				if (numShields < getShieldSlots() && (tries > 3 || (tries > 1 && Functions.GetRandom(2) > 0)))
					numShields++;
			}

			for (int i = 0; i < numShields; i++)
			{
				int bestShieldType = 0;
				for (int j = 0; j < tries; j++)
				{
					int x = Functions.GetRandom(100);
					int sum = Consts.Shields[0].Chance();
					int shieldType = 0;
					while (sum < x && shieldType <= Consts.Shields.length - 1)
					{
						shieldType++;
						sum += Consts.Shields[shieldType].Chance();
					}
					if (!HasShield(Consts.Shields[shieldType].Type()) && shieldType > bestShieldType)
						bestShieldType = shieldType;
				}

				AddEquipment(Consts.Shields[bestShieldType]);

				Shields()[i].setCharge(0);
				for (int j = 0; j < 5; j++)
				{
					int charge = 1 + Functions.GetRandom(Shields()[i].Power());
					if (charge > Shields()[i].getCharge())
						Shields()[i].setCharge(charge);
				}
			}
		}
	}

	private void GenerateOpponentAddWeapons(int tries)
	{
		if (getWeaponSlots() > 0)
		{
			int numWeapons = 0;

			if (Game.CurrentGame().Difficulty() == Difficulty.Impossible)
				numWeapons = getWeaponSlots();
			else if (getWeaponSlots() == 1)
				numWeapons = 1;
			else
			{
				numWeapons = 1 + Functions.GetRandom(getWeaponSlots());
				if (numWeapons < getWeaponSlots() && (tries > 4 || (tries > 3 && Functions.GetRandom(2) > 0)))
					numWeapons++;
			}

			for (int i = 0; i < numWeapons; i++)
			{
				int bestWeaponType = 0;
				for (int j = 0; j < tries; j++)
				{
					int x = Functions.GetRandom(100);
					int sum = Consts.Weapons[0].Chance();
					int weaponType = 0;
					while (sum < x && weaponType <= Consts.Weapons.length - 1)
					{
						weaponType++;
						sum += Consts.Weapons[weaponType].Chance();
					}
					if (!HasWeapon(WeaponType.FromInt(weaponType), true) && weaponType > bestWeaponType)
						bestWeaponType = weaponType;
				}

				AddEquipment(Consts.Weapons[bestWeaponType]);
			}
		}
	}

	private void GenerateOpponentSetHullStrength()
	{
		// If there are shields, the hull will probably be stronger
		if (ShieldStrength() == 0 || Functions.GetRandom(5) == 0)
		{
			setHull(0);

			for (int i = 0; i < 5; i++)
			{
				int hull = 1 + Functions.GetRandom(HullStrength());
				if (hull > getHull())
					setHull(hull);
			}
		}
	}

	private void GenerateOpponentShip(OpponentType oppType)
	{
		Commander cmdr = Game.CurrentGame().Commander();
		PoliticalSystem polSys = Game.CurrentGame().WarpSystem().PoliticalSystem();

		if (oppType == OpponentType.Mantis)
			SetValues(ShipType.Mantis);
		else
		{
			ShipType oppShipType;
			int tries = 1;

			switch (oppType)
			{
			case Pirate:
				// Pirates become better when you get richer
				tries = 1 + cmdr.Worth() / 100000;
				tries = Math.max(1, tries + Game.CurrentGame().Difficulty().CastToInt()
						- Difficulty.Normal.CastToInt());
				break;
			case Police:
				// The police will try to hunt you down with better ships if you
				// are
				// a villain, and they will try even harder when you are
				// considered to
				// be a psychopath (or are transporting Jonathan Wild)
				if (cmdr.getPoliceRecordScore() < Consts.PoliceRecordScorePsychopath || cmdr.getShip().WildOnBoard())
					tries = 5;
				else if (cmdr.getPoliceRecordScore() < Consts.PoliceRecordScoreVillain)
					tries = 3;
				else
					tries = 1;
				tries = Math.max(1, tries + Game.CurrentGame().Difficulty().CastToInt()
						- Difficulty.Normal.CastToInt());
				break;
			}

			if (oppType == OpponentType.Trader)
				oppShipType = ShipType.Flea;
			else
				oppShipType = ShipType.Gnat;

			int total = 0;
			for (int i = 0; i < Consts.MaxShip; i++)
			{
				ShipSpec spec = Consts.ShipSpecs[i];
				if (polSys.ShipTypeLikely(spec.Type(), oppType))
					total += spec.Occurrence();
			}

			for (int i = 0; i < tries; i++)
			{
				int x = Functions.GetRandom(total);
				int sum = -1;
				int j = -1;

				do
				{
					j++;
					if (polSys.ShipTypeLikely(Consts.ShipSpecs[j].Type(), oppType))
					{
						if (sum > 0)
							sum += Consts.ShipSpecs[j].Occurrence();
						else
							sum = Consts.ShipSpecs[j].Occurrence();
					}
				} while (sum < x && j < Consts.MaxShip);

				if (j > oppShipType.CastToInt())
					oppShipType = Consts.ShipSpecs[j].Type();
			}

			SetValues(oppShipType);
		}
	}

	// *************************************************************************
	// Returns the index of a trade good that is on a given ship that can be
	// bought/sold in the current system.
	// JAF - Made this MUCH simpler by storing an array of booleaneans
	// indicating
	// the tradeable goods when HasTradeableItem is called.
	// *************************************************************************
	public int GetRandomTradeableItem()
	{
		int index = Functions.GetRandom(TradeableItems().length);

		while (!TradeableItems()[index])
			index = (index + 1) % TradeableItems().length;

		return index;
	}

	public boolean HasCrew(CrewMemberId id)
	{
		boolean found = false;
		for (int i = 0; i < Crew().length && !found; i++)
		{
			if (Crew()[i] != null && Crew()[i].Id() == id)
				found = true;
		}
		return found;
	}

	public boolean HasEquipment(Equipment item)
	{
		boolean found = false;
		switch (item.EquipmentType())
		{
		case Weapon:
			found = HasWeapon(((Weapon)item).Type(), true);
			break;
		case Shield:
			found = HasShield(((Shield)item).Type());
			break;
		case Gadget:
			found = HasGadget(((Gadget)item).Type());
			break;
		}
		return found;
	}

	public boolean HasGadget(GadgetType gadgetType)
	{
		boolean found = false;
		for (int i = 0; i < Gadgets().length && !found; i++)
		{
			if (Gadgets()[i] != null && Gadgets()[i].Type() == gadgetType)
				found = true;
		}
		return found;
	}

	public boolean HasShield(ShieldType shieldType)
	{
		boolean found = false;
		for (int i = 0; i < Shields().length && !found; i++)
		{
			if (Shields()[i] != null && Shields()[i].Type() == shieldType)
				found = true;
		}
		return found;
	}

	// *************************************************************************
	// Determines if a given ship is carrying items that can be bought or sold
	// in the current system.
	// *************************************************************************
	public boolean HasTradeableItems()
	{
		boolean found = false;
		boolean criminal = Game.CurrentGame().Commander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious;
		_tradeableItems = new boolean[10];

		for (int i = 0; i < Cargo().length; i++)
		{
			// Trade only if trader is selling and the item has a buy price on
			// the
			// local system, or trader is buying, and there is a sell price on
			// the
			// local system.
			// Criminals can only buy or sell illegal goods, Noncriminals cannot
			// buy
			// or sell such items.
			// Simplified this - JAF
			if (Cargo()[i] > 0
					&& !(criminal ^ Consts.TradeItems[i].Illegal())
					&& ((!CommandersShip() && Game.CurrentGame().PriceCargoBuy()[i] > 0) || (CommandersShip() && Game
							.CurrentGame().PriceCargoSell()[i] > 0)))
			{
				found = true;
				TradeableItems()[i] = true;
			}
		}

		return found;
	}

	public boolean HasWeapon(WeaponType weaponType, boolean exactCompare)
	{
		boolean found = false;
		for (int i = 0; i < Weapons().length && !found; i++)
		{
			if (Weapons()[i] != null
					&& (Weapons()[i].Type() == weaponType || !exactCompare
							&& Weapons()[i].Type().CastToInt() > weaponType.CastToInt()))
				found = true;
		}
		return found;
	}

	public void Hire(CrewMember merc)
	{
		int skill = Trader();

		int slot = -1;
		for (int i = 0; i < Crew().length && slot == -1; i++)
			if (Crew()[i] == null)
				slot = i;

		if (slot >= 0)
			Crew()[slot] = merc;

		if (Trader() != skill)
			Game.CurrentGame().RecalculateBuyPrices(Game.CurrentGame().Commander().getCurrentSystem());
	}

	public String IllegalSpecialCargoActions()
	{ 
		ArrayList<String> actions = new ArrayList<String>();

		if (ReactorOnBoard())
			actions.add(Strings.EncounterPoliceSurrenderReactor);
		else if (WildOnBoard())
			actions.add(Strings.EncounterPoliceSurrenderWild);

		if (SculptureOnBoard())
			actions.add(Strings.EncounterPoliceSurrenderSculpt);

		return actions.size() == 0 ? "" : Functions.StringVars(Strings.EncounterPoliceSurrenderAction, Functions
				.FormatList(Functions.ArrayListtoStringArray(actions)));
	}

	public String IllegalSpecialCargoDescription(String wrapper, boolean includePassengers, boolean includeTradeItems)
	{
		ArrayList<String> items = new ArrayList<String>();

		if (includePassengers && WildOnBoard())
			items.add(Strings.EncounterPoliceSubmitWild);

		if (ReactorOnBoard())
			items.add(Strings.EncounterPoliceSubmitReactor);

		if (SculptureOnBoard())
			items.add(Strings.EncounterPoliceSubmitSculpture);

		if (includeTradeItems && DetectableIllegalCargo())
			items.add(Strings.EncounterPoliceSubmitGoods);

		String allItems = Functions.FormatList(Functions.ArrayListtoStringArray(items));

		if (allItems.length() > 0 && wrapper.length() > 0)
			allItems = Functions.StringVars(wrapper, allItems);

		return allItems;
	}

	public void PerformRepairs()
	{
		// A disabled ship cannot be repaired.
		if (CommandersShip() || !Game.CurrentGame().getOpponentDisabled())
		{
			// Engineer may do some repairs
			int repairs = Functions.GetRandom(Engineer());
			if (repairs > 0)
			{
				int used = Math.min(repairs, HullStrength() - getHull());
				setHull(getHull() + used);
				repairs -= used;
			}

			// Shields are easier to repair
			if (repairs > 0)
			{
				repairs *= 2;

				for (int i = 0; i < Shields().length && repairs > 0; i++)
				{
					if (Shields()[i] != null)
					{
						int used = Math.min(repairs, Shields()[i].Power() - Shields()[i].getCharge());
						Shields()[i].setCharge(Shields()[i].getCharge() + used);
						repairs -= used;
					}
				}
			}
		}
	}

	public void RemoveEquipment(EquipmentType type, int slot)
	{
		Equipment[] equip = EquipmentByType(type);

		int last = equip.length - 1;
		for (int i = slot; i < last; i++)
			equip[i] = equip[i + 1];
		equip[last] = null;
	}

	public void RemoveEquipment(EquipmentType type, Object subType)
	{
		boolean found = false;
		Equipment[] equip = EquipmentByType(type);

		for (int i = 0; i < equip.length && !found; i++)
		{
			if (equip[i] != null && equip[i].TypeEquals(subType))
			{
				RemoveEquipment(type, i);
				found = true;
			}
		}
	}

	public void RemoveIllegalGoods()
	{
		for (int i = 0; i < Consts.TradeItems.length; i++)
		{
			if (Consts.TradeItems[i].Illegal())
			{
				Cargo()[i] = 0;
				Game.CurrentGame().Commander().PriceCargo()[i] = 0;
			}
		}
	}

	public @Override
	Hashtable Serialize()
	{
		Hashtable hash = super.Serialize();

		// We don't want the actual CrewMember Objects - we just want the ids.
		int[] crewIds = new int[_crew.length];
		for (int i = 0; i < crewIds.length; i++)
			crewIds[i] = (_crew[i] == null ? CrewMemberId.NA : _crew[i].Id()).CastToInt();

		hash.add("_fuel", _fuel);
		hash.add("_hull", _hull);
		hash.add("_tribbles", _tribbles);
		hash.add("_cargo", _cargo);
		hash.add("_weapons", ArrayToArrayList(_weapons));
		hash.add("_shields", ArrayToArrayList(_shields));
		hash.add("_gadgets", ArrayToArrayList(_gadgets));
		hash.add("_crewIds", crewIds);
		hash.add("_pod", _pod);

		return hash;
	}

	protected @Override
	void SetValues(ShipType type)
	{
		super.SetValues(type);

		_weapons = new Weapon[getWeaponSlots()];
		_shields = new Shield[getShieldSlots()];
		_gadgets = new Gadget[getGadgetSlots()];
		_crew = new CrewMember[getCrewQuarters()];
		_fuel = FuelTanks();
		_hull = HullStrength();
	}

	public int WeaponStrength()
	{
		return WeaponStrength(WeaponType.PulseLaser, WeaponType.QuantumDistruptor);
	}

	public int WeaponStrength(WeaponType min, WeaponType max)
	{
		int total = 0;

		for (int i = 0; i < Weapons().length; i++)
			if (Weapons()[i] != null && Weapons()[i].Type().CastToInt() >= min.CastToInt()
					&& Weapons()[i].Type().CastToInt() <= max.CastToInt())
				total += Weapons()[i].Power();

		return total;
	}

	public int Worth(boolean forInsurance)
	{
		int price = BaseWorth(forInsurance);
		for (int i = 0; i < _cargo.length; i++)
			price += Game.CurrentGame().Commander().PriceCargo()[i];

		return price;
	}

	// #endregion

	// #region Properties

	public boolean AnyIllegalCargo()
	{
		int illegalCargo = 0;
		for (int i = 0; i < Consts.TradeItems.length; i++)
		{
			if (Consts.TradeItems[i].Illegal())
				illegalCargo += Cargo()[i];
		}

		return illegalCargo > 0;
	}

	public boolean ArtifactOnBoard()
	{
		return CommandersShip() && Game.CurrentGame().getQuestStatusArtifact() == SpecialEvent.StatusArtifactOnBoard;
	}

	public int[] Cargo()
	{
		return _cargo;
	}

	// Changed the semantics of Filled versus total Cargo Bays. Bays used for
	// transporting special items are now included in the total bays and in the
	// filled bays. JAF
	public @Override
	int CargoBays()
	{
		int bays = super.CargoBays();

		for (int i = 0; i < Gadgets().length; i++)
			if (Gadgets()[i] != null
					&& (Gadgets()[i].Type() == GadgetType.ExtraCargoBays || Gadgets()[i].Type() == GadgetType.HiddenCargoBays))
				bays += 5;

		return super.CargoBays() + ExtraCargoBays() + HiddenCargoBays();
	}

	public boolean Cloaked()
	{
		int oppEng = CommandersShip() ? Game.CurrentGame().getOpponent().Engineer() : Game.CurrentGame().Commander()
				.getShip().Engineer();
		return HasGadget(GadgetType.CloakingDevice) && Engineer() > oppEng;
	}

	public boolean CommandersShip()
	{
		return this == Game.CurrentGame().Commander().getShip();
	}

	public CrewMember[] Crew()
	{
		return _crew;
	}

	public int CrewCount()
	{
		int total = 0;
		for (int i = 0; i < Crew().length; i++)
			if (Crew()[i] != null)
				total++;
		return total;
	}

	public boolean DetectableIllegalCargo()
	{
		int illegalCargo = 0;
		for (int i = 0; i < Consts.TradeItems.length; i++)
		{
			if (Consts.TradeItems[i].Illegal())
				illegalCargo += Cargo()[i];
		}

		return (illegalCargo - HiddenCargoBays()) > 0;
	}

	public boolean DetectableIllegalCargoOrPassengers()
	{
		return DetectableIllegalCargo() || IllegalSpecialCargo();
	}

	public boolean Disableable()
	{
		return !CommandersShip() && Type() != ShipType.Bottle && Type() != ShipType.Mantis
				&& Type() != ShipType.SpaceMonster;
	}

	public int Engineer()
	{
		return Skills()[SkillType.Engineer.CastToInt()];
	}

	private boolean EscapePod;

	public int ExtraCargoBays()
	{
		int bays = 0;

		for (int i = 0; i < Gadgets().length; i++)
			if (Gadgets()[i] != null && Gadgets()[i].Type() == GadgetType.ExtraCargoBays)
				bays += 5;

		return bays;
	}

	public int Fighter()
	{
		return Skills()[SkillType.Fighter.CastToInt()];
	}

	// Changed the semantics of Filled versus total Cargo Bays. Bays used for
	// transporting special items are now included in the total bays and in the
	// filled bays. JAF

	public int FilledCargoBays()
	{
		int filled = FilledNormalCargoBays();

		if (CommandersShip() && Game.CurrentGame().getQuestStatusJapori() == SpecialEvent.StatusJaporiInTransit)
			filled += 10;

		if (ReactorOnBoard())
			filled += 5 + 10 - (Game.CurrentGame().getQuestStatusReactor() - 1) / 2;

		return filled;
	}

	public int FilledNormalCargoBays()
	{
		int filled = 0;

		for (int i = 0; i < _cargo.length; i++)
			filled += _cargo[i];

		return filled;
	}

	public int FreeCargoBays()
	{
		return CargoBays() - FilledCargoBays();
	}

	public int FreeCrewQuarters()
	{
		int count = 0;
		for (int i = 0; i < Crew().length; i++)
		{
			if (Crew()[i] == null)
				count++;
		}
		return count;
	}

	public int FreeSlots()
	{
		return FreeSlotsGadget() + FreeSlotsShield() + FreeSlotsWeapon();
	}

	public int FreeSlotsGadget()
	{
		int count = 0;
		for (int i = 0; i < Gadgets().length; i++)
		{
			if (Gadgets()[i] == null)
				count++;
		}
		return count;

	}

	public int FreeSlotsShield()
	{
		int count = 0;
		for (int i = 0; i < Shields().length; i++)
		{
			if (Shields()[i] == null)
				count++;
		}
		return count;
	}

	public int FreeSlotsWeapon()
	{
		int count = 0;
		for (int i = 0; i < Weapons().length; i++)
		{
			if (Weapons()[i] == null)
				count++;
		}
		return count;
	}

	public @Override
	int FuelTanks()
	{
		return super.FuelTanks() + (HasGadget(GadgetType.FuelCompactor) ? Consts.FuelCompactorTanks : 0);
	}

	public Gadget[] Gadgets()
	{
		return _gadgets;
	}

	public boolean HagglingComputerOnBoard()
	{
		return CommandersShip() && Game.CurrentGame().getQuestStatusJarek() == SpecialEvent.StatusJarekDone;
	}

	public int HiddenCargoBays()
	{
		int bays = 0;

		for (int i = 0; i < Gadgets().length; i++)
			if (Gadgets()[i] != null && Gadgets()[i].Type() == GadgetType.HiddenCargoBays)
				bays += 5;

		return bays;
	}

	public String HullText()
	{
		return Functions.StringVars(Strings.EncounterHullStrength, Functions.FormatNumber((int)Math.floor((double)100
				* getHull() / HullStrength())));
	}

	public boolean IllegalSpecialCargo()
	{
		return WildOnBoard() || ReactorOnBoard() || SculptureOnBoard();
	}

	public boolean JarekOnBoard()
	{
		return HasCrew(CrewMemberId.Jarek);
	}

	public int Pilot()
	{
		return Skills()[SkillType.Pilot.CastToInt()];
	}

	public boolean PrincessOnBoard()
	{
		return HasCrew(CrewMemberId.Princess);
	}

	public boolean ReactorOnBoard()
	{
		int status = Game.CurrentGame().getQuestStatusReactor();
		return CommandersShip() && status > SpecialEvent.StatusReactorNotStarted
				&& status < SpecialEvent.StatusReactorDelivered;
	}

	public boolean SculptureOnBoard()
	{
		return CommandersShip()
				&& Game.CurrentGame().getQuestStatusSculpture() == SpecialEvent.StatusSculptureInTransit;
	}

	public int ShieldCharge()
	{
		int total = 0;

		for (int i = 0; i < Shields().length; i++)
			if (Shields()[i] != null)
				total += Shields()[i].getCharge();

		return total;
	}

	public Shield[] Shields()
	{
		return _shields;
	}

	public int ShieldStrength()
	{
		int total = 0;

		for (int i = 0; i < Shields().length; i++)
			if (Shields()[i] != null)
				total += Shields()[i].Power();

		return total;
	}

	public String ShieldText()
	{
		return (Shields().length > 0 && Shields()[0] != null) ? Functions.StringVars(Strings.EncounterShieldStrength,
				Functions.FormatNumber((int)Math.floor((double)100 * ShieldCharge() / ShieldStrength())))
				: Strings.EncounterShieldNone;
	}

	public int[] Skills()
	{
		int[] skills = new int[4];

		// Get the best skill value among the crew for each skill.
		for (int skill = 0; skill < skills.length; skill++)
		{
			int max = 1;

			for (int crew = 0; crew < Crew().length; crew++)
			{
				if (Crew()[crew] != null && Crew()[crew].Skills()[skill] > max)
					max = Crew()[crew].Skills()[skill];
			}

			skills[skill] = Math.max(1, Functions.AdjustSkillForDifficulty(max));
		}

		// Adjust skills based on any gadgets on board.
		for (int i = 0; i < Gadgets().length; i++)
		{
			if (Gadgets()[i] != null && Gadgets()[i].SkillBonus() != SkillType.NA)
				skills[Gadgets()[i].SkillBonus().CastToInt()] += Consts.SkillBonus;
		}

		return skills;
	}

	// Crew members that are not hired/fired - Commander, Jarek, Princess, and
	// Wild - JAF
	public CrewMember[] SpecialCrew()
	{
		ArrayList<CrewMember> list = new ArrayList<CrewMember>();
		for (int i = 0; i < Crew().length; i++)
		{
			if (Crew()[i] != null && Util.ArrayContains(Consts.SpecialCrewMemberIds, Crew()[i].Id()))
				list.add(Crew()[i]);
		}

		CrewMember[] crew = new CrewMember[list.size()];
		for (int i = 0; i < crew.length; i++)
			crew[i] = list.get(i);

		return crew;
	}

	// Sort all cargo based on value and put some of it in hidden bays, if they
	// are present.
	public ArrayList<Integer> StealableCargo()
	{
		// Put all of the cargo items in a list and sort it. Reverse it so the
		// most expensive items are first.
		ArrayList<Integer> tradeItems = new ArrayList<Integer>();
		for (int tradeItem = 0; tradeItem < Cargo().length; tradeItem++)
		{
			for (int count = 0; count < Cargo()[tradeItem]; count++)
				tradeItems.add(tradeItem);
		}
		 tradeItems.Sort();
		 tradeItems.Reverse();

		int hidden = HiddenCargoBays();
		if (PrincessOnBoard())
			hidden--;
		if (SculptureOnBoard())
			hidden--;

		if (hidden > 0)
			tradeItems.RemoveRange(0, hidden);

		return tradeItems;
	}

	public boolean[] TradeableItems()
	{
		return _tradeableItems;
	}

	public int Trader()
	{
		return Skills()[SkillType.Trader.CastToInt()] + (HagglingComputerOnBoard() ? 1 : 0);
	}

	public Weapon[] Weapons()
	{
		return _weapons;
	}

	public boolean WildOnBoard()
	{
		return HasCrew(CrewMemberId.Wild);
	}

	// #endregion
}
