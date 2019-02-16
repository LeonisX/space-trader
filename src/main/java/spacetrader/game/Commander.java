package spacetrader.game;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

import static spacetrader.game.quest.enums.EventName.IS_TRADE_SHIP;
import static spacetrader.game.quest.enums.EventName.ON_GET_WORTH;

public class Commander extends CrewMember implements Serializable {

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

    private Commander() {
        // Need for tests
    }

    public Commander(CrewMember baseCrewMember) {
        super(baseCrewMember);
        // Start off with a crew of only the commander and a Pulse Laser.
        setMercenary(false);
        getShip().getCrew()[0] = this;
        getShip().addEquipment(Consts.Weapons[WeaponType.PULSE_LASER.castToInt()]);
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

    public boolean isTradeShip(ShipSpec specToBuy, int netPrice) {
        return isTradeShip(specToBuy, netPrice, specToBuy.getName());
    }

    public boolean isTradeShip(ShipSpec specToBuy, int netPrice, String newShipName) {
        BooleanContainer canTrade = new BooleanContainer(true);

        Game.getCurrentGame().getQuestSystem().fireEvent(IS_TRADE_SHIP, canTrade);

        if (!canTrade.getValue()) {
            return false;
        }

        if (netPrice > 0 && getDebt() > 0) {
            GuiFacade.alert(AlertType.DebtNoBuy);
        } else if (netPrice > getCashToSpend()) {
            GuiFacade.alert(AlertType.ShipBuyIF);
        } else if (specToBuy.getCrewQuarters() < getShip().getSpecialCrew().size()) {
            String passengers = getShip().getSpecialCrew().get(1).getName();
            if (getShip().getSpecialCrew().size() > 2) {
                passengers += " " + Strings.CommanderAnd + " " + getShip().getSpecialCrew().get(2).getName();
            }

            GuiFacade.alert(AlertType.ShipBuyPassengerQuarters, passengers);
        } else if (specToBuy.getCrewQuarters() < getShip().getCrewCount()) {
            GuiFacade.alert(AlertType.ShipBuyCrewQuarters);
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
                        //TODO one message for all special items
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
                            .toLowerCase(), Functions.plural(special[i].getTransferPrice(), Strings.MoneyUnit)) == DialogResult.YES) {
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
                        Strings.ShipInfoEscapePod.toLowerCase(), Functions.plural(Consts.PodTransferCost, Strings.MoneyUnit)) == DialogResult.YES) {
                    extraCost += Consts.PodTransferCost;
                } else {
                    addPod = false;
                }
            }

            if (GuiFacade.alert(AlertType.ShipBuyConfirm, getShip().getName(), newShipName,
                    (add[0] || add[1] || add[2] || addPod ? Strings.ShipBuyTransfer : "")) == DialogResult.YES) {
                CrewMember[] oldCrew = getShip().getCrew();

                setShip(new Ship(specToBuy));
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

    public void initializeGigaGaia() {
        CrewMember[] crewMembers = ship.getCrew();
        ship = new Ship(new ShipSpec(ShipType.CUSTOM, Size.HUGE, 64, 5, 5, 5, 5, 20, 5, 300, 20, 500000, 0,
                Activity.NA, Activity.NA, Activity.NA, TechLevel.UNAVAILABLE));
        ship.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        ship.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        ship.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        ship.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        ship.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        ship.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        ship.addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        ship.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);
        ship.addEquipment(Consts.Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()]);
        ship.addEquipment(Consts.Gadgets[GadgetType.FUEL_COMPACTOR.castToInt()]);
        for (int i = 0; i < crewMembers.length; i++) {
            ship.getCrew()[i] = crewMembers[i];
        }
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
        IntContainer worth = new IntContainer(getShip().getPrice() + cash - debt);
        Game.getCurrentGame().getQuestSystem().fireEvent(ON_GET_WORTH, worth);
        return worth.getValue();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commander)) return false;
        if (!super.equals(o)) return false;
        Commander commander = (Commander) o;
        return cash == commander.cash &&
                debt == commander.debt &&
                killsPirate == commander.killsPirate &&
                killsPolice == commander.killsPolice &&
                killsTrader == commander.killsTrader &&
                policeRecordScore == commander.policeRecordScore &&
                reputationScore == commander.reputationScore &&
                days == commander.days &&
                insurance == commander.insurance &&
                noClaim == commander.noClaim &&
                Objects.equals(ship, commander.ship) &&
                Arrays.equals(priceCargo, commander.priceCargo);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), cash, debt, killsPirate, killsPolice, killsTrader, policeRecordScore, reputationScore, days, insurance, noClaim/*, ship*/);
        result = 31 * result + Arrays.hashCode(priceCargo);
        return result;
    }

    public void spendCash(int cashToSpend) {
        cash -= cashToSpend;
    }
}
