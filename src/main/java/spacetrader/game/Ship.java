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

import spacetrader.game.enums.*;
import spacetrader.stub.ArrayList;
import spacetrader.util.Hashtable;
import spacetrader.util.Util;

public class Ship extends ShipSpec {
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
    private boolean EscapePod;

    public Ship(ShipType type) {
        SetValues(type);
    }

    public Ship(OpponentType oppType) {
        if (oppType == OpponentType.FamousCaptain) {
            SetValues(Consts.ShipSpecs[Consts.MaxShip].Type());

            for (int i = 0; i < Shields().length; i++)
                addEquipment(Consts.Shields[ShieldType.Reflective.castToInt()]);

            for (int i = 0; i < Weapons().length; i++)
                addEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);

            addEquipment(Consts.Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()]);
            addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

            Crew()[0] = Game.getCurrentGame().Mercenaries()[CrewMemberId.FamousCaptain.castToInt()];
        } else if (oppType == OpponentType.Bottle) {
            SetValues(ShipType.Bottle);
        } else {
            int tries = oppType == OpponentType.Mantis ? Game.getCurrentGame().Difficulty().castToInt() + 1 : Math
                    .max(1, Game.getCurrentGame().getCommander().Worth() / 150000
                            + Game.getCurrentGame().Difficulty().castToInt() - Difficulty.Normal.castToInt());

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

    public Ship(Hashtable hash) {
        super(hash);
        _fuel = GetValueFromHash(hash, "_fuel", Integer.class);
        _hull = GetValueFromHash(hash, "_hull", Integer.class);
        _tribbles = GetValueFromHash(hash, "_tribbles", _tribbles);
        _cargo = GetValueFromHash(hash, "_cargo", _cargo, int[].class);
        _weapons = (Weapon[]) ArrayListToArray(GetValueFromHash(hash, "_weapons", ArrayList.class), "Weapon");
        _shields = (Shield[]) ArrayListToArray(GetValueFromHash(hash, "_shields", ArrayList.class), "Shield");
        _gadgets = (Gadget[]) ArrayListToArray(GetValueFromHash(hash, "_gadgets", ArrayList.class), "Gadget");
        _pod = GetValueFromHash(hash, "_pod", _pod);

        int[] crewIds = GetValueFromHash(hash, "_crewIds", (new int[0]), int[].class);
        _crew = new CrewMember[crewIds.length];
        for (int index = 0; index < _crew.length; index++) {
            CrewMemberId id = CrewMemberId.fromInt(crewIds[index]);
            _crew[index] = (id == CrewMemberId.NA ? null : Game.getCurrentGame().Mercenaries()[id.castToInt()]);
        }
    }

    public void addEquipment(Equipment item) {
        Equipment[] equip = getEquipmentByType(item.getEquipmentType());

        int slot = -1;
        for (int i = 0; i < equip.length && slot == -1; i++)
            if (equip[i] == null)
                slot = i;

        if (slot >= 0)
            equip[slot] = item.Clone();
    }

    public int BaseWorth(boolean forInsurance) {
        int i;
        int price =
                // Trade-in value is three-fourths the original price
                getPrice() * (getTribbles() > 0 && !forInsurance ? 1 : 3) / 4
                        // subtract repair costs
                        - (getHullStrength() - getHull()) * getRepairCost()
                        // subtract costs to fill tank with fuel
                        - (getFuelTanks() - getFuel()) * getFuelCost();
        // Add 3/4 of the price of each item of equipment
        for (i = 0; i < _weapons.length; i++)
            if (_weapons[i] != null)
                price += _weapons[i].getSellPrice();
        for (i = 0; i < _shields.length; i++)
            if (_shields[i] != null)
                price += _shields[i].getSellPrice();
        for (i = 0; i < _gadgets.length; i++)
            if (_gadgets[i] != null)
                price += _gadgets[i].getSellPrice();

        return price;
    }

    public int Bounty() {
        int price = getPrice();

        for (int i = 0; i < Weapons().length; i++)
            if (Weapons()[i] != null)
                price += Weapons()[i].getPrice();

        for (int i = 0; i < Shields().length; i++)
            if (Shields()[i] != null)
                price += Shields()[i].getPrice();

        // Gadgets aren't counted in the price, because they are already taken
        // into account in
        // the skill adjustment of the price.

        price = price * (2 * Pilot() + Engineer() + 3 * Fighter()) / 60;

        // Divide by 200 to get the bounty, then round down to the nearest 25.
        int bounty = price / 200 / 25 * 25;

        return Math.max(25, Math.min(2500, bounty));
    }

    public Equipment[] getEquipmentByType(EquipmentType type) {
        Equipment[] equip = null;
        switch (type) {
            case WEAPON:
                equip = Weapons();
                break;
            case SHIELD:
                equip = Shields();
                break;
            case GADGET:
                equip = Gadgets();
                break;
        }
        return equip;
    }

    public void Fire(CrewMemberId crewId) {
        int skill = Trader();
        boolean found = false;
        CrewMember merc = null;
        for (int i = 0; i < Crew().length; i++) {
            if (Crew()[i] != null && Crew()[i].Id() == crewId) {
                found = true;
                merc = Crew()[i];
            }

            if (found)
                Crew()[i] = (i < Crew().length - 1) ? Crew()[i + 1] : null;
        }

        if (Trader() != skill)
            Game.getCurrentGame().RecalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());

        if (merc != null && !Util.ArrayContains(Consts.SpecialCrewMemberIds, (merc.Id()))) {
            StarSystem[] universe = Game.getCurrentGame().getUniverse();

            // The leaving Mercenary travels to a nearby random system.
            merc.setCurrentSystemId(StarSystemId.NA);
            while (merc.getCurrentSystemId() == StarSystemId.NA) {
                StarSystem system = universe[Functions.getRandom(universe.length)];
                if (Functions.distance(system, Game.getCurrentGame().getCommander().getCurrentSystem()) < Consts.MaxRange)
                    merc.setCurrentSystemId(system.Id());
            }
        }
    }

    private void GenerateOpponentAddCargo(boolean pirate) {
        if (getCargoBays() > 0) {
            Difficulty diff = Game.getCurrentGame().Difficulty();
            int baysToFill = getCargoBays();

            if (diff.castToInt() >= Difficulty.Normal.castToInt())
                baysToFill = Math.min(15, 3 + Functions.getRandom(baysToFill - 5));

            if (pirate) {
                if (diff.castToInt() < Difficulty.Normal.castToInt())
                    baysToFill = baysToFill * 4 / 5;
                else
                    baysToFill = Math.max(1, baysToFill / diff.castToInt());
            }

            for (int bays, i = 0; i < baysToFill; i += bays) {
                int item = Functions.getRandom(Consts.TradeItems.length);
                bays = Math.min(baysToFill - i, 1 + Functions.getRandom(10 - item));
                getCargo()[item] += bays;
            }
        }
    }

    private void GenerateOpponentAddCrew() {
        CrewMember[] mercs = Game.getCurrentGame().Mercenaries();
        Difficulty diff = Game.getCurrentGame().Difficulty();

        Crew()[0] = mercs[CrewMemberId.Opponent.castToInt()];
        Crew()[0].Pilot(1 + Functions.getRandom(Consts.MaxSkill));
        Crew()[0].Fighter(1 + Functions.getRandom(Consts.MaxSkill));
        Crew()[0].Trader(1 + Functions.getRandom(Consts.MaxSkill));

        if (Game.getCurrentGame().getWarpSystem().Id() == StarSystemId.Kravat && WildOnBoard()
                && Functions.getRandom(10) < diff.castToInt() + 1)
            Crew()[0].Engineer(Consts.MaxSkill);
        else
            Crew()[0].Engineer(1 + Functions.getRandom(Consts.MaxSkill));

        int numCrew = 0;
        if (diff == Difficulty.Impossible)
            numCrew = getCrewQuarters();
        else {
            numCrew = 1 + Functions.getRandom(getCrewQuarters());
            if (diff == Difficulty.Hard && numCrew < getCrewQuarters())
                numCrew++;
        }

        for (int i = 1; i < numCrew; i++) {
            // Keep getting a new random mercenary until we have a non-special
            // one.
            while (Crew()[i] == null || Util.ArrayContains(Consts.SpecialCrewMemberIds, Crew()[i].Id()))
                Crew()[i] = mercs[Functions.getRandom(mercs.length)];
        }
    }

    private void GenerateOpponentAddGadgets(int tries) {
        if (getGadgetSlots() > 0) {
            int numGadgets = 0;

            if (Game.getCurrentGame().Difficulty() == Difficulty.Impossible)
                numGadgets = getGadgetSlots();
            else {
                numGadgets = Functions.getRandom(getGadgetSlots() + 1);
                if (numGadgets < getGadgetSlots() && (tries > 4 || (tries > 2 && Functions.getRandom(2) > 0)))
                    numGadgets++;
            }

            for (int i = 0; i < numGadgets; i++) {
                int bestGadgetType = 0;
                for (int j = 0; j < tries; j++) {
                    int x = Functions.getRandom(100);
                    int sum = Consts.Gadgets[0].Chance();
                    int gadgetType = 0;
                    while (sum < x && gadgetType <= Consts.Gadgets.length - 1) {
                        gadgetType++;
                        sum += Consts.Gadgets[gadgetType].Chance();
                    }
                    if (!hasGadget(Consts.Gadgets[gadgetType].getType()) && gadgetType > bestGadgetType)
                        bestGadgetType = gadgetType;
                }

                addEquipment(Consts.Gadgets[bestGadgetType]);
            }
        }
    }

    public int getTribbles() {
        return _tribbles;
    }

    public void setTribbles(int tribbles) {
        _tribbles = tribbles;
    }

    public int getHull() {
        return _hull;
    }

    public void setHull(int hull) {
        _hull = hull;
    }

    public int getFuel() {
        return _fuel;
    }

    public void setFuel(int fuel) {
        _fuel = fuel;
    }

    public boolean getEscapePod() {
        return EscapePod;
    }

    public void setEscapePod(boolean escapePod) {
        EscapePod = escapePod;
    }

    private void GenerateOpponentAddShields(int tries) {
        if (getShieldSlots() > 0) {
            int numShields = 0;

            if (Game.getCurrentGame().Difficulty() == Difficulty.Impossible)
                numShields = getShieldSlots();
            else {
                numShields = Functions.getRandom(getShieldSlots() + 1);
                if (numShields < getShieldSlots() && (tries > 3 || (tries > 1 && Functions.getRandom(2) > 0)))
                    numShields++;
            }

            for (int i = 0; i < numShields; i++) {
                int bestShieldType = 0;
                for (int j = 0; j < tries; j++) {
                    int x = Functions.getRandom(100);
                    int sum = Consts.Shields[0].Chance();
                    int shieldType = 0;
                    while (sum < x && shieldType <= Consts.Shields.length - 1) {
                        shieldType++;
                        sum += Consts.Shields[shieldType].Chance();
                    }
                    if (!HasShield(Consts.Shields[shieldType].Type()) && shieldType > bestShieldType)
                        bestShieldType = shieldType;
                }

                addEquipment(Consts.Shields[bestShieldType]);

                Shields()[i].setCharge(0);
                for (int j = 0; j < 5; j++) {
                    int charge = 1 + Functions.getRandom(Shields()[i].getPower());
                    if (charge > Shields()[i].getCharge())
                        Shields()[i].setCharge(charge);
                }
            }
        }
    }

    private void GenerateOpponentAddWeapons(int tries) {
        if (getWeaponSlots() > 0) {
            int numWeapons = 0;

            if (Game.getCurrentGame().Difficulty() == Difficulty.Impossible)
                numWeapons = getWeaponSlots();
            else if (getWeaponSlots() == 1)
                numWeapons = 1;
            else {
                numWeapons = 1 + Functions.getRandom(getWeaponSlots());
                if (numWeapons < getWeaponSlots() && (tries > 4 || (tries > 3 && Functions.getRandom(2) > 0)))
                    numWeapons++;
            }

            for (int i = 0; i < numWeapons; i++) {
                int bestWeaponType = 0;
                for (int j = 0; j < tries; j++) {
                    int x = Functions.getRandom(100);
                    int sum = Consts.Weapons[0].Chance();
                    int weaponType = 0;
                    while (sum < x && weaponType <= Consts.Weapons.length - 1) {
                        weaponType++;
                        sum += Consts.Weapons[weaponType].Chance();
                    }
                    if (!HasWeapon(WeaponType.fromInt(weaponType), true) && weaponType > bestWeaponType)
                        bestWeaponType = weaponType;
                }

                addEquipment(Consts.Weapons[bestWeaponType]);
            }
        }
    }

    private void GenerateOpponentSetHullStrength() {
        // If there are shields, the hull will probably be stronger
        if (ShieldStrength() == 0 || Functions.getRandom(5) == 0) {
            setHull(0);

            for (int i = 0; i < 5; i++) {
                int hull = 1 + Functions.getRandom(getHullStrength());
                if (hull > getHull())
                    setHull(hull);
            }
        }
    }

    private void GenerateOpponentShip(OpponentType oppType) {
        Commander cmdr = Game.getCurrentGame().getCommander();
        PoliticalSystem polSys = Game.getCurrentGame().getWarpSystem().politicalSystem();

        if (oppType == OpponentType.Mantis)
            SetValues(ShipType.Mantis);
        else {
            ShipType oppShipType;
            int tries = 1;

            switch (oppType) {
                case Pirate:
                    // Pirates become better when you get richer
                    tries = 1 + cmdr.Worth() / 100000;
                    tries = Math.max(1, tries + Game.getCurrentGame().Difficulty().castToInt()
                            - Difficulty.Normal.castToInt());
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
                    tries = Math.max(1, tries + Game.getCurrentGame().Difficulty().castToInt()
                            - Difficulty.Normal.castToInt());
                    break;
            }

            if (oppType == OpponentType.Trader)
                oppShipType = ShipType.Flea;
            else
                oppShipType = ShipType.Gnat;

            int total = 0;
            for (int i = 0; i < Consts.MaxShip; i++) {
                ShipSpec spec = Consts.ShipSpecs[i];
                if (polSys.ShipTypeLikely(spec.Type(), oppType))
                    total += spec.Occurrence();
            }

            for (int i = 0; i < tries; i++) {
                int x = Functions.getRandom(total);
                int sum = -1;
                int j = -1;

                do {
                    j++;
                    if (polSys.ShipTypeLikely(Consts.ShipSpecs[j].Type(), oppType)) {
                        if (sum > 0)
                            sum += Consts.ShipSpecs[j].Occurrence();
                        else
                            sum = Consts.ShipSpecs[j].Occurrence();
                    }
                } while (sum < x && j < Consts.MaxShip);

                if (j > oppShipType.castToInt())
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
    public int GetRandomTradeableItem() {
        int index = Functions.getRandom(TradeableItems().length);

        while (!TradeableItems()[index])
            index = (index + 1) % TradeableItems().length;

        return index;
    }

    public boolean HasCrew(CrewMemberId id) {
        boolean found = false;
        for (int i = 0; i < Crew().length && !found; i++) {
            if (Crew()[i] != null && Crew()[i].Id() == id)
                found = true;
        }
        return found;
    }

    public boolean HasEquipment(Equipment item) {
        boolean found = false;
        switch (item.getEquipmentType()) {
            case WEAPON:
                found = HasWeapon(((Weapon) item).Type(), true);
                break;
            case SHIELD:
                found = HasShield(((Shield) item).Type());
                break;
            case GADGET:
                found = hasGadget(((Gadget) item).getType());
                break;
        }
        return found;
    }

    public boolean hasGadget(GadgetType gadgetType) {
        boolean found = false;
        for (int i = 0; i < Gadgets().length && !found; i++) {
            if (Gadgets()[i] != null && Gadgets()[i].getType() == gadgetType)
                found = true;
        }
        return found;
    }

    public boolean HasShield(ShieldType shieldType) {
        boolean found = false;
        for (int i = 0; i < Shields().length && !found; i++) {
            if (Shields()[i] != null && Shields()[i].Type() == shieldType)
                found = true;
        }
        return found;
    }

    // *************************************************************************
    // Determines if a given ship is carrying items that can be bought or sold
    // in the current system.
    // *************************************************************************
    public boolean HasTradeableItems() {
        boolean found = false;
        boolean criminal = Game.getCurrentGame().getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious;
        _tradeableItems = new boolean[10];

        for (int i = 0; i < getCargo().length; i++) {
            // Trade only if trader is selling and the item has a buy price on
            // the
            // local system, or trader is buying, and there is a sell price on
            // the
            // local system.
            // Criminals can only buy or sell illegal goods, Noncriminals cannot
            // buy
            // or sell such items.
            // Simplified this - JAF
            if (getCargo()[i] > 0
                    && !(criminal ^ Consts.TradeItems[i].Illegal())
                    && ((!CommandersShip() && Game.getCurrentGame().getPriceCargoBuy()[i] > 0) || (CommandersShip() && Game
                    .getCurrentGame().getPriceCargoSell()[i] > 0))) {
                found = true;
                TradeableItems()[i] = true;
            }
        }

        return found;
    }

    public boolean HasWeapon(WeaponType weaponType, boolean exactCompare) {
        boolean found = false;
        for (int i = 0; i < Weapons().length && !found; i++) {
            if (Weapons()[i] != null
                    && (Weapons()[i].Type() == weaponType || !exactCompare
                    && Weapons()[i].Type().castToInt() > weaponType.castToInt()))
                found = true;
        }
        return found;
    }

    public void Hire(CrewMember merc) {
        int skill = Trader();

        int slot = -1;
        for (int i = 0; i < Crew().length && slot == -1; i++)
            if (Crew()[i] == null)
                slot = i;

        if (slot >= 0)
            Crew()[slot] = merc;

        if (Trader() != skill)
            Game.getCurrentGame().RecalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());
    }

    public String IllegalSpecialCargoActions() {
        ArrayList<String> actions = new ArrayList<String>();

        if (ReactorOnBoard())
            actions.add(Strings.EncounterPoliceSurrenderReactor);
        else if (WildOnBoard())
            actions.add(Strings.EncounterPoliceSurrenderWild);

        if (SculptureOnBoard())
            actions.add(Strings.EncounterPoliceSurrenderSculpt);

        return actions.size() == 0 ? "" : Functions.stringVars(Strings.EncounterPoliceSurrenderAction, Functions
                .FormatList(Functions.ArrayListtoStringArray(actions)));
    }

    public String IllegalSpecialCargoDescription(String wrapper, boolean includePassengers, boolean includeTradeItems) {
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
            allItems = Functions.stringVars(wrapper, allItems);

        return allItems;
    }

    public void PerformRepairs() {
        // A disabled ship cannot be repaired.
        if (CommandersShip() || !Game.getCurrentGame().getOpponentDisabled()) {
            // Engineer may do some repairs
            int repairs = Functions.getRandom(Engineer());
            if (repairs > 0) {
                int used = Math.min(repairs, getHullStrength() - getHull());
                setHull(getHull() + used);
                repairs -= used;
            }

            // Shields are easier to repair
            if (repairs > 0) {
                repairs *= 2;

                for (int i = 0; i < Shields().length && repairs > 0; i++) {
                    if (Shields()[i] != null) {
                        int used = Math.min(repairs, Shields()[i].getPower() - Shields()[i].getCharge());
                        Shields()[i].setCharge(Shields()[i].getCharge() + used);
                        repairs -= used;
                    }
                }
            }
        }
    }

    public void removeEquipment(EquipmentType type, int slot) {
        Equipment[] equip = getEquipmentByType(type);

        int last = equip.length - 1;
        for (int i = slot; i < last; i++)
            equip[i] = equip[i + 1];
        equip[last] = null;
    }

    public void removeEquipment(EquipmentType type, Object subType) {
        boolean found = false;
        Equipment[] equip = getEquipmentByType(type);

        for (int i = 0; i < equip.length && !found; i++) {
            if (equip[i] != null && equip[i].TypeEquals(subType)) {
                removeEquipment(type, i);
                found = true;
            }
        }
    }

    public void RemoveIllegalGoods() {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].Illegal()) {
                getCargo()[i] = 0;
                Game.getCurrentGame().getCommander().getPriceCargo()[i] = 0;
            }
        }
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        // We don't want the actual CrewMember Objects - we just want the ids.
        int[] crewIds = new int[_crew.length];
        for (int i = 0; i < crewIds.length; i++)
            crewIds[i] = (_crew[i] == null ? CrewMemberId.NA : _crew[i].Id()).castToInt();

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
    void SetValues(ShipType type) {
        super.SetValues(type);

        _weapons = new Weapon[getWeaponSlots()];
        _shields = new Shield[getShieldSlots()];
        _gadgets = new Gadget[getGadgetSlots()];
        _crew = new CrewMember[getCrewQuarters()];
        _fuel = getFuelTanks();
        _hull = getHullStrength();
    }

    public int WeaponStrength() {
        return WeaponStrength(WeaponType.PulseLaser, WeaponType.QuantumDisruptor);
    }

    public int WeaponStrength(WeaponType min, WeaponType max) {
        int total = 0;

        for (int i = 0; i < Weapons().length; i++)
            if (Weapons()[i] != null && Weapons()[i].Type().castToInt() >= min.castToInt()
                    && Weapons()[i].Type().castToInt() <= max.castToInt())
                total += Weapons()[i].getPower();

        return total;
    }

    // #endregion

    // #region Properties

    public int Worth(boolean forInsurance) {
        int price = BaseWorth(forInsurance);
        for (int i = 0; i < _cargo.length; i++)
            price += Game.getCurrentGame().getCommander().getPriceCargo()[i];

        return price;
    }

    public boolean AnyIllegalCargo() {
        int illegalCargo = 0;
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].Illegal())
                illegalCargo += getCargo()[i];
        }

        return illegalCargo > 0;
    }

    public boolean ArtifactOnBoard() {
        return CommandersShip() && Game.getCurrentGame().getQuestStatusArtifact() == SpecialEvent.STATUS_ARTIFACT_ON_BOARD;
    }

    public int[] getCargo() {
        return _cargo;
    }

    // Changed the semantics of Filled versus total Cargo Bays. Bays used for
    // transporting special items are now included in the total bays and in the
    // filled bays. JAF
    public @Override
    int getCargoBays() {
        int bays = super.getCargoBays();

        for (int i = 0; i < Gadgets().length; i++)
            if (Gadgets()[i] != null
                    && (Gadgets()[i].getType() == GadgetType.EXTRA_CARGO_BAYS || Gadgets()[i].getType() == GadgetType.HIDDEN_CARGO_BAYS))
                bays += 5;

        return super.getCargoBays() + ExtraCargoBays() + HiddenCargoBays();
    }

    public boolean Cloaked() {
        int oppEng = CommandersShip() ? Game.getCurrentGame().getOpponent().Engineer() : Game.getCurrentGame().getCommander()
                .getShip().Engineer();
        return hasGadget(GadgetType.CLOAKING_DEVICE) && Engineer() > oppEng;
    }

    public boolean CommandersShip() {
        return this == Game.getCurrentGame().getCommander().getShip();
    }

    public CrewMember[] Crew() {
        return _crew;
    }

    public int CrewCount() {
        int total = 0;
        for (int i = 0; i < Crew().length; i++)
            if (Crew()[i] != null)
                total++;
        return total;
    }

    public boolean DetectableIllegalCargo() {
        int illegalCargo = 0;
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].Illegal())
                illegalCargo += getCargo()[i];
        }

        return (illegalCargo - HiddenCargoBays()) > 0;
    }

    public boolean DetectableIllegalCargoOrPassengers() {
        return DetectableIllegalCargo() || isIllegalSpecialCargo();
    }

    public boolean Disableable() {
        return !CommandersShip() && Type() != ShipType.Bottle && Type() != ShipType.Mantis
                && Type() != ShipType.SpaceMonster;
    }

    public int Engineer() {
        return Skills()[SkillType.Engineer.castToInt()];
    }

    public int ExtraCargoBays() {
        int bays = 0;

        for (int i = 0; i < Gadgets().length; i++)
            if (Gadgets()[i] != null && Gadgets()[i].getType() == GadgetType.EXTRA_CARGO_BAYS)
                bays += 5;

        return bays;
    }

    public int Fighter() {
        return Skills()[SkillType.Fighter.castToInt()];
    }

    // Changed the semantics of Filled versus total Cargo Bays. Bays used for
    // transporting special items are now included in the total bays and in the
    // filled bays. JAF

    public int getFilledCargoBays() {
        int filled = FilledNormalCargoBays();

        if (CommandersShip() && Game.getCurrentGame().getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT)
            filled += 10;

        if (ReactorOnBoard())
            filled += 5 + 10 - (Game.getCurrentGame().getQuestStatusReactor() - 1) / 2;

        return filled;
    }

    public int FilledNormalCargoBays() {
        int filled = 0;

        for (int i = 0; i < _cargo.length; i++)
            filled += _cargo[i];

        return filled;
    }

    public int getFreeCargoBays() {
        return getCargoBays() - getFilledCargoBays();
    }

    public int FreeCrewQuarters() {
        int count = 0;
        for (int i = 0; i < Crew().length; i++) {
            if (Crew()[i] == null)
                count++;
        }
        return count;
    }

    public int getFreeSlots() {
        return getFreeGadgetSlots() + getFreeShieldSlots() + getFreeWeaponSlots();
    }

    public int getFreeGadgetSlots() {
        int count = 0;
        for (int i = 0; i < Gadgets().length; i++) {
            if (Gadgets()[i] == null)
                count++;
        }
        return count;

    }

    public int getFreeShieldSlots() {
        int count = 0;
        for (int i = 0; i < Shields().length; i++) {
            if (Shields()[i] == null)
                count++;
        }
        return count;
    }

    public int getFreeWeaponSlots() {
        int count = 0;
        for (int i = 0; i < Weapons().length; i++) {
            if (Weapons()[i] == null)
                count++;
        }
        return count;
    }

    public @Override
    int getFuelTanks() {
        return super.getFuelTanks() + (hasGadget(GadgetType.FUEL_COMPACTOR) ? Consts.FuelCompactorTanks : 0);
    }

    public Gadget[] Gadgets() {
        return _gadgets;
    }

    public boolean HagglingComputerOnBoard() {
        return CommandersShip() && Game.getCurrentGame().getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_DONE;
    }

    public int HiddenCargoBays() {
        int bays = 0;

        for (int i = 0; i < Gadgets().length; i++)
            if (Gadgets()[i] != null && Gadgets()[i].getType() == GadgetType.HIDDEN_CARGO_BAYS)
                bays += 5;

        return bays;
    }

    public String getHullText() {
        return Functions.stringVars(Strings.EncounterHullStrength, Functions.formatNumber((int) Math.floor((double) 100
                * getHull() / getHullStrength())));
    }

    public boolean isIllegalSpecialCargo() {
        return WildOnBoard() || ReactorOnBoard() || SculptureOnBoard();
    }

    public boolean JarekOnBoard() {
        return HasCrew(CrewMemberId.Jarek);
    }

    public int Pilot() {
        return Skills()[SkillType.Pilot.castToInt()];
    }

    public boolean PrincessOnBoard() {
        return HasCrew(CrewMemberId.Princess);
    }

    public boolean ReactorOnBoard() {
        int status = Game.getCurrentGame().getQuestStatusReactor();
        return CommandersShip() && status > SpecialEvent.STATUS_REACTOR_NOT_STARTED
                && status < SpecialEvent.STATUS_REACTOR_DELIVERED;
    }

    public boolean SculptureOnBoard() {
        return CommandersShip()
                && Game.getCurrentGame().getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_IN_TRANSIT;
    }

    public int ShieldCharge() {
        int total = 0;

        for (int i = 0; i < Shields().length; i++)
            if (Shields()[i] != null)
                total += Shields()[i].getCharge();

        return total;
    }

    public Shield[] Shields() {
        return _shields;
    }

    public int ShieldStrength() {
        int total = 0;

        for (int i = 0; i < Shields().length; i++)
            if (Shields()[i] != null)
                total += Shields()[i].getPower();

        return total;
    }

    public String getShieldText() {
        return (Shields().length > 0 && Shields()[0] != null) ? Functions.stringVars(Strings.EncounterShieldStrength,
                Functions.formatNumber((int) Math.floor((double) 100 * ShieldCharge() / ShieldStrength())))
                : Strings.EncounterShieldNone;
    }

    public int[] Skills() {
        int[] skills = new int[4];

        // Get the best skill value among the crew for each skill.
        for (int skill = 0; skill < skills.length; skill++) {
            int max = 1;

            for (int crew = 0; crew < Crew().length; crew++) {
                if (Crew()[crew] != null && Crew()[crew].Skills()[skill] > max)
                    max = Crew()[crew].Skills()[skill];
            }

            skills[skill] = Math.max(1, Functions.AdjustSkillForDifficulty(max));
        }

        // Adjust skills based on any gadgets on board.
        for (int i = 0; i < Gadgets().length; i++) {
            if (Gadgets()[i] != null && Gadgets()[i].SkillBonus() != SkillType.NA)
                skills[Gadgets()[i].SkillBonus().castToInt()] += Consts.SkillBonus;
        }

        return skills;
    }

    // Crew members that are not hired/fired - Commander, Jarek, Princess, and
    // Wild - JAF
    public CrewMember[] SpecialCrew() {
        ArrayList<CrewMember> list = new ArrayList<CrewMember>();
        for (int i = 0; i < Crew().length; i++) {
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
    public ArrayList<Integer> StealableCargo() {
        // Put all of the cargo items in a list and sort it. Reverse it so the
        // most expensive items are first.
        ArrayList<Integer> tradeItems = new ArrayList<Integer>();
        for (int tradeItem = 0; tradeItem < getCargo().length; tradeItem++) {
            for (int count = 0; count < getCargo()[tradeItem]; count++)
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

    public boolean[] TradeableItems() {
        return _tradeableItems;
    }

    public int Trader() {
        return Skills()[SkillType.Trader.castToInt()] + (HagglingComputerOnBoard() ? 1 : 0);
    }

    public Weapon[] Weapons() {
        return _weapons;
    }

    public boolean WildOnBoard() {
        return HasCrew(CrewMemberId.Wild);
    }

    // #endregion
}
