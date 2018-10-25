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
        setValues(type);
    }

    public Ship(OpponentType oppType) {
        if (oppType == OpponentType.FAMOUS_CAPTAIN) {
            setValues(Consts.ShipSpecs[Consts.MaxShip].getType());

            for (int i = 0; i < getShields().length; i++)
                addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);

            for (int i = 0; i < getWeapons().length; i++)
                addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);

            addEquipment(Consts.Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()]);
            addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

            getCrew()[0] = Game.getCurrentGame().getMercenaries()[CrewMemberId.FAMOUS_CAPTAIN.castToInt()];
        } else if (oppType == OpponentType.BOTTLE) {
            setValues(ShipType.BOTTLE);
        } else {
            int tries = oppType == OpponentType.MANTIS ? Game.getCurrentGame().getDifficulty().castToInt() + 1 : Math
                    .max(1, Game.getCurrentGame().getCommander().getWorth() / 150000
                            + Game.getCurrentGame().getDifficulty().castToInt() - Difficulty.NORMAL.castToInt());

            GenerateOpponentShip(oppType);
            GenerateOpponentAddCrew();
            GenerateOpponentAddGadgets(tries);
            GenerateOpponentAddShields(tries);
            GenerateOpponentAddWeapons(tries);

            if (oppType != OpponentType.MANTIS)
                GenerateOpponentSetHullStrength();

            if (oppType != OpponentType.POLICE)
                GenerateOpponentAddCargo(oppType == OpponentType.PIRATE);
        }
    }

    public Ship(Hashtable hash) {
        super(hash);
        _fuel = getValueFromHash(hash, "_fuel", Integer.class);
        _hull = getValueFromHash(hash, "_hull", Integer.class);
        _tribbles = getValueFromHash(hash, "_tribbles", _tribbles);
        _cargo = getValueFromHash(hash, "_cargo", _cargo, int[].class);
        _weapons = (Weapon[]) arrayListToArray(getValueFromHash(hash, "_weapons", ArrayList.class), "Weapon");
        _shields = (Shield[]) arrayListToArray(getValueFromHash(hash, "_shields", ArrayList.class), "Shield");
        _gadgets = (Gadget[]) arrayListToArray(getValueFromHash(hash, "_gadgets", ArrayList.class), "Gadget");
        _pod = getValueFromHash(hash, "_pod", _pod);

        int[] crewIds = getValueFromHash(hash, "_crewIds", (new int[0]), int[].class);
        _crew = new CrewMember[crewIds.length];
        for (int index = 0; index < _crew.length; index++) {
            CrewMemberId id = CrewMemberId.fromInt(crewIds[index]);
            _crew[index] = (id == CrewMemberId.NA ? null : Game.getCurrentGame().getMercenaries()[id.castToInt()]);
        }
    }

    public void addEquipment(Equipment item) {
        Equipment[] equip = getEquipmentsByType(item.getEquipmentType());

        int slot = -1;
        for (int i = 0; i < equip.length && slot == -1; i++)
            if (equip[i] == null)
                slot = i;

        if (slot >= 0)
            equip[slot] = item.clone();
    }

    public int getBaseWorth(boolean forInsurance) {
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

    public int getBounty() {
        int price = getPrice();

        for (int i = 0; i < getWeapons().length; i++)
            if (getWeapons()[i] != null)
                price += getWeapons()[i].getPrice();

        for (int i = 0; i < getShields().length; i++)
            if (getShields()[i] != null)
                price += getShields()[i].getPrice();

        // Gadgets aren't counted in the price, because they are already taken
        // into account in
        // the skill adjustment of the price.

        price = price * (2 * getPilot() + getEngineer() + 3 * getFighter()) / 60;

        // Divide by 200 to get the bounty, then round down to the nearest 25.
        int bounty = price / 200 / 25 * 25;

        return Math.max(25, Math.min(2500, bounty));
    }

    public Equipment[] getEquipmentsByType(EquipmentType type) {
        Equipment[] equip = null;
        switch (type) {
            case WEAPON:
                equip = getWeapons();
                break;
            case SHIELD:
                equip = getShields();
                break;
            case GADGET:
                equip = getGadgets();
                break;
        }
        return equip;
    }

    public void fire(CrewMemberId crewId) {
        int skill = getTrader();
        boolean found = false;
        CrewMember merc = null;
        for (int i = 0; i < getCrew().length; i++) {
            if (getCrew()[i] != null && getCrew()[i].getId() == crewId) {
                found = true;
                merc = getCrew()[i];
            }

            if (found)
                getCrew()[i] = (i < getCrew().length - 1) ? getCrew()[i + 1] : null;
        }

        if (getTrader() != skill)
            Game.getCurrentGame().recalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());

        if (merc != null && !Util.arrayContains(Consts.SpecialCrewMemberIds, (merc.getId()))) {
            StarSystem[] universe = Game.getCurrentGame().getUniverse();

            // The leaving Mercenary travels to a nearby random system.
            merc.setCurrentSystemId(StarSystemId.NA);
            while (merc.getCurrentSystemId() == StarSystemId.NA) {
                StarSystem system = universe[Functions.getRandom(universe.length)];
                if (Functions.distance(system, Game.getCurrentGame().getCommander().getCurrentSystem()) < Consts.MaxRange)
                    merc.setCurrentSystemId(system.getId());
            }
        }
    }

    private void GenerateOpponentAddCargo(boolean pirate) {
        if (getCargoBays() > 0) {
            Difficulty diff = Game.getCurrentGame().getDifficulty();
            int baysToFill = getCargoBays();

            if (diff.castToInt() >= Difficulty.NORMAL.castToInt())
                baysToFill = Math.min(15, 3 + Functions.getRandom(baysToFill - 5));

            if (pirate) {
                if (diff.castToInt() < Difficulty.NORMAL.castToInt())
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
        CrewMember[] mercs = Game.getCurrentGame().getMercenaries();
        Difficulty diff = Game.getCurrentGame().getDifficulty();

        getCrew()[0] = mercs[CrewMemberId.OPPONENT.castToInt()];
        getCrew()[0].setPilot(1 + Functions.getRandom(Consts.MaxSkill));
        getCrew()[0].setFighter(1 + Functions.getRandom(Consts.MaxSkill));
        getCrew()[0].setTrader(1 + Functions.getRandom(Consts.MaxSkill));

        if (Game.getCurrentGame().getWarpSystem().getId() == StarSystemId.Kravat && isWildOnBoard()
                && Functions.getRandom(10) < diff.castToInt() + 1)
            getCrew()[0].setEngineer(Consts.MaxSkill);
        else
            getCrew()[0].setEngineer(1 + Functions.getRandom(Consts.MaxSkill));

        int numCrew = 0;
        if (diff == Difficulty.IMPOSSIBLE)
            numCrew = getCrewQuarters();
        else {
            numCrew = 1 + Functions.getRandom(getCrewQuarters());
            if (diff == Difficulty.HARD && numCrew < getCrewQuarters())
                numCrew++;
        }

        for (int i = 1; i < numCrew; i++) {
            // Keep getting a new random mercenary until we have a non-special
            // one.
            while (getCrew()[i] == null || Util.arrayContains(Consts.SpecialCrewMemberIds, getCrew()[i].getId()))
                getCrew()[i] = mercs[Functions.getRandom(mercs.length)];
        }
    }

    private void GenerateOpponentAddGadgets(int tries) {
        if (getGadgetSlots() > 0) {
            int numGadgets = 0;

            if (Game.getCurrentGame().getDifficulty() == Difficulty.IMPOSSIBLE)
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
                    int sum = Consts.Gadgets[0].getChance();
                    int gadgetType = 0;
                    while (sum < x && gadgetType <= Consts.Gadgets.length - 1) {
                        gadgetType++;
                        sum += Consts.Gadgets[gadgetType].getChance();
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

            if (Game.getCurrentGame().getDifficulty() == Difficulty.IMPOSSIBLE)
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
                    int sum = Consts.Shields[0].getChance();
                    int shieldType = 0;
                    while (sum < x && shieldType <= Consts.Shields.length - 1) {
                        shieldType++;
                        sum += Consts.Shields[shieldType].getChance();
                    }
                    if (!hasShield(Consts.Shields[shieldType].getType()) && shieldType > bestShieldType)
                        bestShieldType = shieldType;
                }

                addEquipment(Consts.Shields[bestShieldType]);

                getShields()[i].setCharge(0);
                for (int j = 0; j < 5; j++) {
                    int charge = 1 + Functions.getRandom(getShields()[i].getPower());
                    if (charge > getShields()[i].getCharge())
                        getShields()[i].setCharge(charge);
                }
            }
        }
    }

    private void GenerateOpponentAddWeapons(int tries) {
        if (getWeaponSlots() > 0) {
            int numWeapons = 0;

            if (Game.getCurrentGame().getDifficulty() == Difficulty.IMPOSSIBLE)
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
                    int sum = Consts.Weapons[0].getChance();
                    int weaponType = 0;
                    while (sum < x && weaponType <= Consts.Weapons.length - 1) {
                        weaponType++;
                        sum += Consts.Weapons[weaponType].getChance();
                    }
                    if (!hasWeapon(WeaponType.fromInt(weaponType), true) && weaponType > bestWeaponType)
                        bestWeaponType = weaponType;
                }

                addEquipment(Consts.Weapons[bestWeaponType]);
            }
        }
    }

    private void GenerateOpponentSetHullStrength() {
        // If there are shields, the hull will probably be stronger
        if (getShieldStrength() == 0 || Functions.getRandom(5) == 0) {
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
        PoliticalSystem polSys = Game.getCurrentGame().getWarpSystem().getPoliticalSystem();

        if (oppType == OpponentType.MANTIS)
            setValues(ShipType.MANTIS);
        else {
            ShipType oppShipType;
            int tries = 1;

            switch (oppType) {
                case PIRATE:
                    // Pirates become better when you get richer
                    tries = 1 + cmdr.getWorth() / 100000;
                    tries = Math.max(1, tries + Game.getCurrentGame().getDifficulty().castToInt()
                            - Difficulty.NORMAL.castToInt());
                    break;
                case POLICE:
                    // The police will try to hunt you down with better ships if you
                    // are
                    // a villain, and they will try even harder when you are
                    // considered to
                    // be a psychopath (or are transporting Jonathan Wild)
                    if (cmdr.getPoliceRecordScore() < Consts.PoliceRecordScorePsychopath || cmdr.getShip().isWildOnBoard())
                        tries = 5;
                    else if (cmdr.getPoliceRecordScore() < Consts.PoliceRecordScoreVillain)
                        tries = 3;
                    else
                        tries = 1;
                    tries = Math.max(1, tries + Game.getCurrentGame().getDifficulty().castToInt()
                            - Difficulty.NORMAL.castToInt());
                    break;
            }

            if (oppType == OpponentType.TRADER)
                oppShipType = ShipType.FLEA;
            else
                oppShipType = ShipType.GNAT;

            int total = 0;
            for (int i = 0; i < Consts.MaxShip; i++) {
                ShipSpec spec = Consts.ShipSpecs[i];
                if (polSys.ShipTypeLikely(spec.getType(), oppType))
                    total += spec.Occurrence();
            }

            for (int i = 0; i < tries; i++) {
                int x = Functions.getRandom(total);
                int sum = -1;
                int j = -1;

                do {
                    j++;
                    if (polSys.ShipTypeLikely(Consts.ShipSpecs[j].getType(), oppType)) {
                        if (sum > 0)
                            sum += Consts.ShipSpecs[j].Occurrence();
                        else
                            sum = Consts.ShipSpecs[j].Occurrence();
                    }
                } while (sum < x && j < Consts.MaxShip);

                if (j > oppShipType.castToInt())
                    oppShipType = Consts.ShipSpecs[j].getType();
            }

            setValues(oppShipType);
        }
    }

    // *************************************************************************
    // Returns the index of a trade good that is on a given ship that can be
    // bought/sold in the current system.
    // JAF - Made this MUCH simpler by storing an array of booleaneans
    // indicating
    // the tradeable goods when HasTradeableItem is called.
    // *************************************************************************
    public int getRandomTradeableItem() {
        int index = Functions.getRandom(getTradeableItems().length);

        while (!getTradeableItems()[index])
            index = (index + 1) % getTradeableItems().length;

        return index;
    }

    public boolean hasCrew(CrewMemberId id) {
        boolean found = false;
        for (int i = 0; i < getCrew().length && !found; i++) {
            if (getCrew()[i] != null && getCrew()[i].getId() == id)
                found = true;
        }
        return found;
    }

    public boolean hasEquipment(Equipment item) {
        boolean found = false;
        switch (item.getEquipmentType()) {
            case WEAPON:
                found = hasWeapon(((Weapon) item).getType(), true);
                break;
            case SHIELD:
                found = hasShield(((Shield) item).getType());
                break;
            case GADGET:
                found = hasGadget(((Gadget) item).getType());
                break;
        }
        return found;
    }

    public boolean hasGadget(GadgetType gadgetType) {
        boolean found = false;
        for (int i = 0; i < getGadgets().length && !found; i++) {
            if (getGadgets()[i] != null && getGadgets()[i].getType() == gadgetType)
                found = true;
        }
        return found;
    }

    public boolean hasShield(ShieldType shieldType) {
        boolean found = false;
        for (int i = 0; i < getShields().length && !found; i++) {
            if (getShields()[i] != null && getShields()[i].getType() == shieldType)
                found = true;
        }
        return found;
    }

    // *************************************************************************
    // Determines if a given ship is carrying items that can be bought or sold
    // in the current system.
    // *************************************************************************
    public boolean hasTradeableItems() {
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
                    && !(criminal ^ Consts.TradeItems[i].isIllegal())
                    && ((!isCommandersShip() && Game.getCurrentGame().getPriceCargoBuy()[i] > 0) || (isCommandersShip() && Game
                    .getCurrentGame().getPriceCargoSell()[i] > 0))) {
                found = true;
                getTradeableItems()[i] = true;
            }
        }

        return found;
    }

    public boolean hasWeapon(WeaponType weaponType, boolean exactCompare) {
        boolean found = false;
        for (int i = 0; i < getWeapons().length && !found; i++) {
            if (getWeapons()[i] != null
                    && (getWeapons()[i].getType() == weaponType || !exactCompare
                    && getWeapons()[i].getType().castToInt() > weaponType.castToInt()))
                found = true;
        }
        return found;
    }

    public void hire(CrewMember merc) {
        int skill = getTrader();

        int slot = -1;
        for (int i = 0; i < getCrew().length && slot == -1; i++)
            if (getCrew()[i] == null)
                slot = i;

        if (slot >= 0)
            getCrew()[slot] = merc;

        if (getTrader() != skill)
            Game.getCurrentGame().recalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());
    }

    public String IllegalSpecialCargoActions() {
        ArrayList<String> actions = new ArrayList<String>();

        if (isReactorOnBoard())
            actions.add(Strings.EncounterPoliceSurrenderReactor);
        else if (isWildOnBoard())
            actions.add(Strings.EncounterPoliceSurrenderWild);

        if (isSculptureOnBoard())
            actions.add(Strings.EncounterPoliceSurrenderSculpt);

        return actions.size() == 0 ? "" : Functions.stringVars(Strings.EncounterPoliceSurrenderAction, Functions.formatList(actions));
    }

    public String getIllegalSpecialCargoDescription(String wrapper, boolean includePassengers, boolean includeTradeItems) {
        ArrayList<String> items = new ArrayList<String>();

        if (includePassengers && isWildOnBoard())
            items.add(Strings.EncounterPoliceSubmitWild);

        if (isReactorOnBoard())
            items.add(Strings.EncounterPoliceSubmitReactor);

        if (isSculptureOnBoard())
            items.add(Strings.EncounterPoliceSubmitSculpture);

        if (includeTradeItems && isDetectableIllegalCargo())
            items.add(Strings.EncounterPoliceSubmitGoods);

        String allItems = Functions.formatList(items);

        if (allItems.length() > 0 && wrapper.length() > 0)
            allItems = Functions.stringVars(wrapper, allItems);

        return allItems;
    }

    public void PerformRepairs() {
        // A disabled ship cannot be repaired.
        if (isCommandersShip() || !Game.getCurrentGame().getOpponentDisabled()) {
            // Engineer may do some repairs
            int repairs = Functions.getRandom(getEngineer());
            if (repairs > 0) {
                int used = Math.min(repairs, getHullStrength() - getHull());
                setHull(getHull() + used);
                repairs -= used;
            }

            // Shields are easier to repair
            if (repairs > 0) {
                repairs *= 2;

                for (int i = 0; i < getShields().length && repairs > 0; i++) {
                    if (getShields()[i] != null) {
                        int used = Math.min(repairs, getShields()[i].getPower() - getShields()[i].getCharge());
                        getShields()[i].setCharge(getShields()[i].getCharge() + used);
                        repairs -= used;
                    }
                }
            }
        }
    }

    public void removeEquipment(EquipmentType type, int slot) {
        Equipment[] equip = getEquipmentsByType(type);

        int last = equip.length - 1;
        for (int i = slot; i < last; i++)
            equip[i] = equip[i + 1];
        equip[last] = null;
    }

    public void removeEquipment(EquipmentType type, Object subType) {
        boolean found = false;
        Equipment[] equip = getEquipmentsByType(type);

        for (int i = 0; i < equip.length && !found; i++) {
            if (equip[i] != null && equip[i].isTypeEquals(subType)) {
                removeEquipment(type, i);
                found = true;
            }
        }
    }

    public void removeIllegalGoods() {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].isIllegal()) {
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
            crewIds[i] = (_crew[i] == null ? CrewMemberId.NA : _crew[i].getId()).castToInt();

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
    void setValues(ShipType type) {
        super.setValues(type);

        _weapons = new Weapon[getWeaponSlots()];
        _shields = new Shield[getShieldSlots()];
        _gadgets = new Gadget[getGadgetSlots()];
        _crew = new CrewMember[getCrewQuarters()];
        _fuel = getFuelTanks();
        _hull = getHullStrength();
    }

    public int getWeaponStrength() {
        return getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.QUANTUM_DISRUPTOR);
    }

    public int getWeaponStrength(WeaponType min, WeaponType max) {
        int total = 0;

        for (int i = 0; i < getWeapons().length; i++)
            if (getWeapons()[i] != null && getWeapons()[i].getType().castToInt() >= min.castToInt()
                    && getWeapons()[i].getType().castToInt() <= max.castToInt())
                total += getWeapons()[i].getPower();

        return total;
    }

    // #endregion

    // #region Properties

    public int getWorth(boolean forInsurance) {
        int price = getBaseWorth(forInsurance);
        for (int i = 0; i < _cargo.length; i++)
            price += Game.getCurrentGame().getCommander().getPriceCargo()[i];

        return price;
    }

    public boolean isAnyIllegalCargo() {
        int illegalCargo = 0;
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].isIllegal())
                illegalCargo += getCargo()[i];
        }

        return illegalCargo > 0;
    }

    public boolean isArtifactOnBoard() {
        return isCommandersShip() && Game.getCurrentGame().getQuestStatusArtifact() == SpecialEvent.STATUS_ARTIFACT_ON_BOARD;
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

        for (int i = 0; i < getGadgets().length; i++)
            if (getGadgets()[i] != null
                    && (getGadgets()[i].getType() == GadgetType.EXTRA_CARGO_BAYS || getGadgets()[i].getType() == GadgetType.HIDDEN_CARGO_BAYS))
                bays += 5;

        return super.getCargoBays() + ExtraCargoBays() + getHiddenCargoBays();
    }

    public boolean isCloaked() {
        int oppEng = isCommandersShip() ? Game.getCurrentGame().getOpponent().getEngineer() : Game.getCurrentGame().getCommander()
                .getShip().getEngineer();
        return hasGadget(GadgetType.CLOAKING_DEVICE) && getEngineer() > oppEng;
    }

    public boolean isCommandersShip() {
        return this == Game.getCurrentGame().getCommander().getShip();
    }

    public CrewMember[] getCrew() {
        return _crew;
    }

    public int getCrewCount() {
        int total = 0;
        for (int i = 0; i < getCrew().length; i++)
            if (getCrew()[i] != null)
                total++;
        return total;
    }

    public boolean isDetectableIllegalCargo() {
        int illegalCargo = 0;
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].isIllegal())
                illegalCargo += getCargo()[i];
        }

        return (illegalCargo - getHiddenCargoBays()) > 0;
    }

    public boolean isDetectableIllegalCargoOrPassengers() {
        return isDetectableIllegalCargo() || isIllegalSpecialCargo();
    }

    public boolean isDisableable() {
        return !isCommandersShip() && getType() != ShipType.BOTTLE && getType() != ShipType.MANTIS
                && getType() != ShipType.SPACE_MONSTER;
    }

    public int getEngineer() {
        return getSkills()[SkillType.ENGINEER.castToInt()];
    }

    public int ExtraCargoBays() {
        int bays = 0;

        for (int i = 0; i < getGadgets().length; i++)
            if (getGadgets()[i] != null && getGadgets()[i].getType() == GadgetType.EXTRA_CARGO_BAYS)
                bays += 5;

        return bays;
    }

    public int getFighter() {
        return getSkills()[SkillType.FIGHTER.castToInt()];
    }

    // Changed the semantics of Filled versus total Cargo Bays. Bays used for
    // transporting special items are now included in the total bays and in the
    // filled bays. JAF

    public int getFilledCargoBays() {
        int filled = FilledNormalCargoBays();

        if (isCommandersShip() && Game.getCurrentGame().getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT)
            filled += 10;

        if (isReactorOnBoard())
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

    public int getFreeCrewQuartersCount() {
        int count = 0;
        for (int i = 0; i < getCrew().length; i++) {
            if (getCrew()[i] == null)
                count++;
        }
        return count;
    }

    public int getFreeSlots() {
        return getFreeGadgetSlots() + getFreeShieldSlots() + getFreeWeaponSlots();
    }

    public int getFreeGadgetSlots() {
        int count = 0;
        for (int i = 0; i < getGadgets().length; i++) {
            if (getGadgets()[i] == null)
                count++;
        }
        return count;

    }

    public int getFreeShieldSlots() {
        int count = 0;
        for (int i = 0; i < getShields().length; i++) {
            if (getShields()[i] == null)
                count++;
        }
        return count;
    }

    public int getFreeWeaponSlots() {
        int count = 0;
        for (int i = 0; i < getWeapons().length; i++) {
            if (getWeapons()[i] == null)
                count++;
        }
        return count;
    }

    @Override
    public int getFuelTanks() {
        return super.getFuelTanks() + (hasGadget(GadgetType.FUEL_COMPACTOR) ? Consts.FuelCompactorTanks : 0);
    }

    public Gadget[] getGadgets() {
        return _gadgets;
    }

    public boolean isHagglingComputerOnBoard() {
        return isCommandersShip() && Game.getCurrentGame().getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_DONE;
    }

    public int getHiddenCargoBays() {
        int bays = 0;

        for (int i = 0; i < getGadgets().length; i++)
            if (getGadgets()[i] != null && getGadgets()[i].getType() == GadgetType.HIDDEN_CARGO_BAYS)
                bays += 5;

        return bays;
    }

    public String getHullText() {
        return Functions.stringVars(Strings.EncounterHullStrength, Functions.formatNumber((int) Math.floor((double) 100
                * getHull() / getHullStrength())));
    }

    public boolean isIllegalSpecialCargo() {
        return isWildOnBoard() || isReactorOnBoard() || isSculptureOnBoard();
    }

    public boolean isJarekOnBoard() {
        return hasCrew(CrewMemberId.JAREK);
    }

    public int getPilot() {
        return getSkills()[SkillType.PILOT.castToInt()];
    }

    public boolean isPrincessOnBoard() {
        return hasCrew(CrewMemberId.PRINCESS);
    }

    public boolean isReactorOnBoard() {
        int status = Game.getCurrentGame().getQuestStatusReactor();
        return isCommandersShip() && status > SpecialEvent.STATUS_REACTOR_NOT_STARTED
                && status < SpecialEvent.STATUS_REACTOR_DELIVERED;
    }

    public boolean isSculptureOnBoard() {
        return isCommandersShip()
                && Game.getCurrentGame().getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_IN_TRANSIT;
    }

    public int getShieldCharge() {
        int total = 0;

        for (int i = 0; i < getShields().length; i++)
            if (getShields()[i] != null)
                total += getShields()[i].getCharge();

        return total;
    }

    public Shield[] getShields() {
        return _shields;
    }

    public int getShieldStrength() {
        int total = 0;

        for (int i = 0; i < getShields().length; i++)
            if (getShields()[i] != null)
                total += getShields()[i].getPower();

        return total;
    }

    public String getShieldText() {
        return (getShields().length > 0 && getShields()[0] != null) ? Functions.stringVars(Strings.EncounterShieldStrength,
                Functions.formatNumber((int) Math.floor((double) 100 * getShieldCharge() / getShieldStrength())))
                : Strings.EncounterShieldNone;
    }

    public int[] getSkills() {
        int[] skills = new int[4];

        // Get the best skill value among the crew for each skill.
        for (int skill = 0; skill < skills.length; skill++) {
            int max = 1;

            for (int crew = 0; crew < getCrew().length; crew++) {
                if (getCrew()[crew] != null && getCrew()[crew].getSkills()[skill] > max)
                    max = getCrew()[crew].getSkills()[skill];
            }

            skills[skill] = Math.max(1, Game.getCurrentGame().getDifficulty().adjustSkill(max));
        }

        // Adjust skills based on any gadgets on board.
        for (int i = 0; i < getGadgets().length; i++) {
            if (getGadgets()[i] != null && getGadgets()[i].getSkillBonus() != SkillType.NA)
                skills[getGadgets()[i].getSkillBonus().castToInt()] += Consts.SkillBonus;
        }

        return skills;
    }

    // Crew members that are not hired/fired - Commander, Jarek, Princess, and
    // Wild - JAF
    public CrewMember[] getSpecialCrew() {
        ArrayList<CrewMember> list = new ArrayList<CrewMember>();
        for (int i = 0; i < getCrew().length; i++) {
            if (getCrew()[i] != null && Util.arrayContains(Consts.SpecialCrewMemberIds, getCrew()[i].getId()))
                list.add(getCrew()[i]);
        }

        CrewMember[] crew = new CrewMember[list.size()];
        for (int i = 0; i < crew.length; i++)
            crew[i] = list.get(i);

        return crew;
    }

    // Sort all cargo based on value and put some of it in hidden bays, if they
    // are present.
    public ArrayList<Integer> getStealableCargo() {
        // Put all of the cargo items in a list and sort it. Reverse it so the
        // most expensive items are first.
        ArrayList<Integer> tradeItems = new ArrayList<Integer>();
        for (int tradeItem = 0; tradeItem < getCargo().length; tradeItem++) {
            for (int count = 0; count < getCargo()[tradeItem]; count++)
                tradeItems.add(tradeItem);
        }
        tradeItems.sort();
        tradeItems.reverse();

        int hidden = getHiddenCargoBays();
        if (isPrincessOnBoard())
            hidden--;
        if (isSculptureOnBoard())
            hidden--;

        if (hidden > 0)
            tradeItems.removeRange(0, hidden);

        return tradeItems;
    }

    private boolean[] getTradeableItems() {
        return _tradeableItems;
    }

    public int getTrader() {
        return getSkills()[SkillType.TRADER.castToInt()] + (isHagglingComputerOnBoard() ? 1 : 0);
    }

    public Weapon[] getWeapons() {
        return _weapons;
    }

    public boolean isWildOnBoard() {
        return hasCrew(CrewMemberId.WILD);
    }

}
