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
 *****************************************************************************/

package spacetrader.game;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Hashtable;

public class Commander extends CrewMember {

    private int cash = 1000;
    private int debt = 0;
    private int killsPirate = 0;
    private int killsPolice = 0;
    private int killsTrader = 0;
    private int policeRecordScore = 0;
    private int reputationScore = 0;
    private int days = 0;
    private boolean insurance = false;
    private int noClaim = 0;
    private Ship ship = new Ship(ShipType.GNAT);
    private int[] priceCargo = new int[10]; // Total price paid for trade goods

    public Commander(CrewMember baseCrewMember) {
        super(baseCrewMember);
        // Start off with a crew of only the commander and a Pulse Laser.
        getShip().getCrew()[0] = this;
        getShip().addEquipment(Consts.Weapons[WeaponType.PULSE_LASER.castToInt()]);
    }

    public Commander(Hashtable hash) {
        super(hash);
        cash = getValueFromHash(hash, "_cash", cash);
        debt = getValueFromHash(hash, "_debt", debt);
        killsPirate = getValueFromHash(hash, "_killsPirate", killsPirate);
        killsPolice = getValueFromHash(hash, "_killsPolice", killsPolice);
        killsTrader = getValueFromHash(hash, "_killsTrader", killsTrader);
        policeRecordScore = getValueFromHash(hash, "_policeRecordScore", policeRecordScore);
        reputationScore = getValueFromHash(hash, "_reputationScore", reputationScore);
        days = getValueFromHash(hash, "_days", days);
        insurance = getValueFromHash(hash, "_insurance", insurance);
        noClaim = getValueFromHash(hash, "_noclaim", noClaim);
        ship = new Ship(getValueFromHash(hash, "_ship", Hashtable.class));
        priceCargo = getValueFromHash(hash, "_priceCargo", priceCargo, int[].class);

        Game.getCurrentGame().getMercenaries()[CrewMemberId.COMMANDER.castToInt()] = this;
        Strings.CrewMemberNames[CrewMemberId.COMMANDER.castToInt()] = getValueFromHash(hash, "_name",
                Strings.CrewMemberNames[CrewMemberId.COMMANDER.castToInt()]);
    }

    void payInterest() {
        if (getDebt() > 0) {
            int interest = Math.max(1, (int) (getDebt() * Consts.IntRate));
            if (getCash() > interest) {
                setCash(getCash() - interest);
            } else {
                setDebt(getDebt() + (interest - getCash()));
                setCash(0);
            }
        }
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_cash", cash);
        hash.add("_debt", debt);
        hash.add("_killsPirate", killsPirate);
        hash.add("_killsPolice", killsPolice);
        hash.add("_killsTrader", killsTrader);
        hash.add("_policeRecordScore", policeRecordScore);
        hash.add("_reputationScore", reputationScore);
        hash.add("_days", days);
        hash.add("_insurance", insurance);
        hash.add("_noclaim", noClaim);
        hash.add("_ship", ship.serialize());
        hash.add("_priceCargo", priceCargo);
        hash.add("_name", getName());

        return hash;
    }

    public boolean isTradeShip(ShipSpec specToBuy, int netPrice) {
        return isTradeShip(specToBuy, netPrice, specToBuy.getName());
    }

    public boolean isTradeShip(ShipSpec specToBuy, int netPrice, String newShipName) {

        if (netPrice > 0 && getDebt() > 0) {
            GuiFacade.alert(AlertType.DebtNoBuy);
        } else if (netPrice > getCashToSpend()) {
            GuiFacade.alert(AlertType.ShipBuyIF);
        } else if (specToBuy.getCrewQuarters() < getShip().getSpecialCrew().length) {
            String passengers = getShip().getSpecialCrew()[1].getName();
            if (getShip().getSpecialCrew().length > 2) {
                passengers += " " + Strings.CommanderAnd + " " + getShip().getSpecialCrew()[2].getName();
            }

            GuiFacade.alert(AlertType.ShipBuyPassengerQuarters, passengers);
        } else if (specToBuy.getCrewQuarters() < getShip().getCrewCount()) {
            GuiFacade.alert(AlertType.ShipBuyCrewQuarters);
        } else if (getShip().isReactorOnBoard()) {
            GuiFacade.alert(AlertType.ShipBuyReactor);
        } else {
            Equipment[] special = new Equipment[]{Consts.Weapons[WeaponType.MORGANS_LASER.castToInt()],
                    Consts.Weapons[WeaponType.QUANTUM_DISRUPTOR.castToInt()],
                    Consts.Shields[ShieldType.LIGHTNING.castToInt()],
                    Consts.Gadgets[GadgetType.FUEL_COMPACTOR.castToInt()],
                    Consts.Gadgets[GadgetType.HIDDEN_CARGO_BAYS.castToInt()]};
            boolean[] add = new boolean[special.length];
            boolean addPod = false;
            int extraCost = 0;

            for (int i = 0; i < special.length; i++) {
                if (getShip().hasEquipment(special[i])) {
                    if (specToBuy.getSlotsCount(special[i].getEquipmentType()) == 0) {
                        GuiFacade.alert(AlertType.ShipBuyNoSlots, newShipName, special[i].getName(),
                                Strings.EquipmentTypes[special[i].getEquipmentType().castToInt()]);
                    } else {
                        extraCost += special[i].getTransferPrice();
                        add[i] = true;
                    }
                }
            }

            if (getShip().getEscapePod()) {
                addPod = true;
                extraCost += Consts.PodTransferCost;
            }

            if (netPrice + extraCost > getCashToSpend()) {
                GuiFacade.alert(AlertType.ShipBuyIFTransfer);
            }

            extraCost = 0;

            for (int i = 0; i < special.length; i++) {
                if (add[i]) {
                    if (netPrice + extraCost + special[i].getTransferPrice() > getCashToSpend()) {
                        GuiFacade.alert(AlertType.ShipBuyNoTransfer, special[i].getName());
                    } else if (GuiFacade.alert(AlertType.ShipBuyTransfer, special[i].getName(), special[i].getName()
                            .toLowerCase(), Functions.formatNumber(special[i].getTransferPrice())) == DialogResult.YES) {
                        extraCost += special[i].getTransferPrice();
                    } else {
                        add[i] = false;
                    }
                }
            }

            if (addPod) {
                if (netPrice + extraCost + Consts.PodTransferCost > getCashToSpend()) {
                    GuiFacade.alert(AlertType.ShipBuyNoTransfer, Strings.ShipInfoEscapePod);
                } else if (GuiFacade.alert(AlertType.ShipBuyTransfer, Strings.ShipInfoEscapePod,
                        Strings.ShipInfoEscapePod.toLowerCase(), Functions.formatNumber(Consts.PodTransferCost)) == DialogResult.YES) {
                    extraCost += Consts.PodTransferCost;
                } else {
                    addPod = false;
                }
            }

            if (GuiFacade.alert(AlertType.ShipBuyConfirm, getShip().getName(), newShipName,
                    (add[0] || add[1] || add[2] || addPod ? Strings.ShipBuyTransfer : "")) == DialogResult.YES) {
                CrewMember[] oldCrew = getShip().getCrew();

                setShip(new Ship(specToBuy.getType()));
                setCash(getCash() - (netPrice + extraCost));

                for (int i = 0; i < Math.min(oldCrew.length, getShip().getCrew().length); i++) {
                    getShip().getCrew()[i] = oldCrew[i];
                }

                for (int i = 0; i < special.length; i++) {
                    if (add[i]) {
                        getShip().addEquipment(special[i]);
                    }
                }

                if (addPod) {
                    getShip().setEscapePod(true);
                } else if (getInsurance()) {
                    setInsurance(false);
                    setNoClaim(0);
                }

                return true;
            }
        }

        return false;
    }

    public int getCashToSpend() {
        return cash - (Game.getCurrentGame().getOptions().isReserveMoney() ? getCurrentCosts() : 0);
    }

    public int getCurrentCosts() {
        return Game.getCurrentGame().getCurrentCosts();
    }

    public int getNoClaim() {
        return noClaim;
    }

    public void setNoClaim(int value) {
        noClaim = Math.max(0, Math.min(Consts.MaxNoClaim, value));
    }

    public int[] getPriceCargo() {
        return priceCargo;
    }

    public int getWorth() {
        return getShip().getPrice() + cash - debt
                + (Game.getCurrentGame().getQuestStatusMoon() > 0 ? SpecialEvent.MOON_COST : 0);
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public int getReputationScore() {
        return reputationScore;
    }

    public void setReputationScore(int reputationScore) {
        this.reputationScore = reputationScore;
    }

    public int getPoliceRecordScore() {
        return policeRecordScore;
    }

    public void setPoliceRecordScore(int policeRecordScore) {
        this.policeRecordScore = policeRecordScore;
    }

    public int getKillsTrader() {
        return killsTrader;
    }

    public void setKillsTrader(int killsTrader) {
        this.killsTrader = killsTrader;
    }

    public int getKillsPolice() {
        return killsPolice;
    }

    public void setKillsPolice(int killsPolice) {
        this.killsPolice = killsPolice;
    }

    public int getKillsPirate() {
        return killsPirate;
    }

    public void setKillsPirate(int killsPirate) {
        this.killsPirate = killsPirate;
    }

    public boolean getInsurance() {
        return insurance;
    }

    public void setInsurance(boolean insurance) {
        this.insurance = insurance;
    }

    public int getDebt() {
        return debt;
    }

    public void setDebt(int debt) {
        this.debt = debt;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
