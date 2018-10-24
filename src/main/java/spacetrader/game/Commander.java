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
 //using System;
 //using System.Collections;
 //using System.Windows.Forms;
 *****************************************************************************/

package spacetrader.game;

import spacetrader.controls.DialogResult;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Hashtable;

public class Commander extends CrewMember {
    // //#region Member Declarations

    private int _cash = 1000;
    private int _debt = 0;
    private int _killsPirate = 0;
    private int _killsPolice = 0;
    private int _killsTrader = 0;
    private int _policeRecordScore = 0;
    private int _reputationScore = 0;
    private int _days = 0;
    private boolean _insurance = false;
    private int _noclaim = 0;
    private Ship _ship = new Ship(ShipType.GNAT);
    private int[] _priceCargo = new int[10]; // Total price paid for trade goods

    // //#endregion

    // //#region Methods

    public Commander(CrewMember baseCrewMember)// : base(baseCrewMember)
    {
        super(baseCrewMember);
        // Start off with a crew of only the commander and a Pulse Laser.
        getShip().getCrew()[0] = this;
        getShip().addEquipment(Consts.Weapons[WeaponType.PulseLaser.castToInt()]);
    }

    public Commander(Hashtable hash)// : base(hash)
    {
        super(hash);
        _cash = getValueFromHash(hash, "_cash", _cash);
        _debt = getValueFromHash(hash, "_debt", _debt);
        _killsPirate = getValueFromHash(hash, "_killsPirate", _killsPirate);
        _killsPolice = getValueFromHash(hash, "_killsPolice", _killsPolice);
        _killsTrader = getValueFromHash(hash, "_killsTrader", _killsTrader);
        _policeRecordScore = getValueFromHash(hash, "_policeRecordScore", _policeRecordScore);
        _reputationScore = getValueFromHash(hash, "_reputationScore", _reputationScore);
        _days = getValueFromHash(hash, "_days", _days);
        _insurance = getValueFromHash(hash, "_insurance", _insurance);
        _noclaim = getValueFromHash(hash, "_noclaim", _noclaim);
        _ship = new Ship(getValueFromHash(hash, "_ship"/*,_ship*/, Hashtable.class));
        _priceCargo = getValueFromHash(hash, "_priceCargo", _priceCargo, int[].class);

        Game.getCurrentGame().getMercenaries()[CrewMemberId.COMMANDER.castToInt()] = this;
        Strings.CrewMemberNames[CrewMemberId.COMMANDER.castToInt()] = getValueFromHash(hash, "_name",
                Strings.CrewMemberNames[CrewMemberId.COMMANDER.castToInt()]);
    }

    public void PayInterest() {
        if (getDebt() > 0) {
            int interest = Math.max(1, (int) (getDebt() * Consts.IntRate));
            if (getCash() > interest)
                setCash(getCash() - interest);
            else {
                setDebt(getDebt() + (interest - getCash()));
                setCash(0);
            }
        }
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_cash", _cash);
        hash.add("_debt", _debt);
        hash.add("_killsPirate", _killsPirate);
        hash.add("_killsPolice", _killsPolice);
        hash.add("_killsTrader", _killsTrader);
        hash.add("_policeRecordScore", _policeRecordScore);
        hash.add("_reputationScore", _reputationScore);
        hash.add("_days", _days);
        hash.add("_insurance", _insurance);
        hash.add("_noclaim", _noclaim);
        hash.add("_ship", _ship.serialize());
        hash.add("_priceCargo", _priceCargo);
        hash.add("_name", getName());

        return hash;
    }

    public boolean isTradeShip(ShipSpec specToBuy, int netPrice) {
        return isTradeShip(specToBuy, netPrice, specToBuy.getName());
    }

    public boolean isTradeShip(ShipSpec specToBuy, int netPrice, String newShipName) {
        boolean traded = false;

        if (netPrice > 0 && getDebt() > 0)
            GuiFacade.alert(AlertType.DebtNoBuy);
        else if (netPrice > getCashToSpend())
            GuiFacade.alert(AlertType.ShipBuyIF);
        else if (specToBuy.getCrewQuarters() < getShip().SpecialCrew().length) {
            String passengers = getShip().SpecialCrew()[1].getName();
            if (getShip().SpecialCrew().length > 2)
                passengers += " and " + getShip().SpecialCrew()[2].getName();

            GuiFacade.alert(AlertType.ShipBuyPassengerQuarters, passengers);
        } else if (specToBuy.getCrewQuarters() < getShip().getCrewCount())
            GuiFacade.alert(AlertType.ShipBuyCrewQuarters);
        else if (getShip().isReactorOnBoard())
            GuiFacade.alert(AlertType.ShipBuyReactor);
        else {
            Equipment[] special = new Equipment[]{Consts.Weapons[WeaponType.MorgansLaser.castToInt()],
                    Consts.Weapons[WeaponType.QuantumDisruptor.castToInt()],
                    Consts.Shields[ShieldType.Lightning.castToInt()],
                    Consts.Gadgets[GadgetType.FUEL_COMPACTOR.castToInt()],
                    Consts.Gadgets[GadgetType.HIDDEN_CARGO_BAYS.castToInt()]};
            boolean[] add = new boolean[special.length];
            boolean addPod = false;
            int extraCost = 0;

            for (int i = 0; i < special.length; i++) {
                if (getShip().HasEquipment(special[i])) {
                    if (specToBuy.Slots(special[i].getEquipmentType()) == 0)
                        GuiFacade.alert(AlertType.ShipBuyNoSlots, newShipName, special[i].getName(), Strings.EquipmentTypes[special[i].getEquipmentType().castToInt()]);
                    else {
                        extraCost += special[i].TransferPrice();
                        add[i] = true;
                    }
                }
            }

            if (getShip().getEscapePod()) {
                addPod = true;
                extraCost += Consts.PodTransferCost;
            }

            if (netPrice + extraCost > getCashToSpend())
                GuiFacade.alert(AlertType.ShipBuyIFTransfer);

            extraCost = 0;

            for (int i = 0; i < special.length; i++) {
                if (add[i]) {
                    if (netPrice + extraCost + special[i].TransferPrice() > getCashToSpend())
                        GuiFacade.alert(AlertType.ShipBuyNoTransfer, special[i].getName());
                    else if (GuiFacade.alert(AlertType.ShipBuyTransfer, special[i].getName(), special[i].getName()
                            .toLowerCase(), Functions.formatNumber(special[i].TransferPrice())) == DialogResult.YES)
                        extraCost += special[i].TransferPrice();
                    else
                        add[i] = false;
                }
            }

            if (addPod) {
                if (netPrice + extraCost + Consts.PodTransferCost > getCashToSpend())
                    GuiFacade.alert(AlertType.ShipBuyNoTransfer, Strings.ShipInfoEscapePod);
                else if (GuiFacade.alert(AlertType.ShipBuyTransfer, Strings.ShipInfoEscapePod, Strings.ShipInfoEscapePod.toLowerCase(), Functions.formatNumber(Consts.PodTransferCost)) == DialogResult.YES)
                    extraCost += Consts.PodTransferCost;
                else
                    addPod = false;
            }

            if (GuiFacade.alert(AlertType.ShipBuyConfirm, getShip().getName(), newShipName, (add[0] || add[1]
                    || add[2] || addPod ? Strings.ShipBuyTransfer : "")) == DialogResult.YES) {
                CrewMember[] oldCrew = getShip().getCrew();

                setShip(new Ship(specToBuy.getType()));
                setCash(getCash() - (netPrice + extraCost));

                for (int i = 0; i < Math.min(oldCrew.length, getShip().getCrew().length); i++)
                    getShip().getCrew()[i] = oldCrew[i];

                for (int i = 0; i < special.length; i++) {
                    if (add[i])
                        getShip().addEquipment(special[i]);
                }

                if (addPod)
                    getShip().setEscapePod(true);
                else if (getInsurance()) {
                    setInsurance(false);
                    setNoClaim(0);
                }

                traded = true;
            }
        }

        return traded;
    }

    // //#endregion

    // //#region Properties


    public int getCashToSpend() {
        return _cash - (Game.getCurrentGame().getOptions().getReserveMoney() ? getCurrentCosts() : 0);
    }

    public int getCurrentCosts() {
        return Game.getCurrentGame().getCurrentCosts();
    }

    public int getNoClaim() {
        return _noclaim;
    }

    public void setNoClaim(int value) {
        _noclaim = Math.max(0, Math.min(Consts.MaxNoClaim, value));
    }

    public int[] getPriceCargo() {
        return _priceCargo;
    }

    public int getWorth() {
        return getShip().getPrice() + _cash - _debt
                + (Game.getCurrentGame().getQuestStatusMoon() > 0 ? SpecialEvent.MOON_COST : 0);
    }

    public Ship getShip() {
        return _ship;
    }

    public void setShip(Ship ship) {
        _ship = ship;
    }

    public int getReputationScore() {
        return _reputationScore;
    }

    public void setReputationScore(int reputationScore) {
        _reputationScore = reputationScore;
    }

    /**
     * @return
     */
    public int getPoliceRecordScore() {
        return _policeRecordScore;
    }

    public void setPoliceRecordScore(int policeRecordScore) {
        _policeRecordScore = policeRecordScore;
    }

    /**
     * @return
     */
    public int getKillsTrader() {
        return _killsTrader;
    }

    public void setKillsTrader(int killsTrader) {
        _killsTrader = killsTrader;
    }

    /**
     * @return
     */
    public int getKillsPolice() {
        return _killsPolice;
    }

    public void setKillsPolice(int killsPolice) {
        _killsPolice = killsPolice;
    }

    /**
     * @return
     */
    public int getKillsPirate() {
        return _killsPirate;
    }

    public void setKillsPirate(int killsPirate) {
        _killsPirate = killsPirate;
    }

    /**
     * @return
     */
    public boolean getInsurance() {
        return _insurance;
    }

    public void setInsurance(boolean insurance) {
        _insurance = insurance;
    }

    /**
     * @return
     */
    public int getDebt() {
        return _debt;
    }

    public void setDebt(int debt) {
        _debt = debt;
    }

    /**
     * @return
     */
    public int getDays() {
        return _days;
    }

    public void setDays(int days) {
        _days = days;
    }

    /**
     * @return
     */
    public int getCash() {
        return _cash;
    }

    public void setCash(int cash) {
        _cash = cash;
    }

    // //#endregion
}
