package spacetrader.game;

import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static spacetrader.game.quest.enums.EventName.*;

public class Ship extends ShipSpec implements Serializable {

    static final long serialVersionUID = 150L;

    private int fuel;
    private int hull;
    private int[] cargo = new int[10];
    private Weapon[] weapons;
    private Shield[] shields;
    private Gadget[] gadgets;
    private CrewMember[] crewMembers;
    private boolean pod = false;

    private int opponentType;
    private boolean initialized;

    // The following does not need to be saved. It's more of a temp variable.
    private boolean[] tradeableItems;

    private boolean escapePod;

    private Ship() {
        // need for tests
    }

    public Ship(ShipType type) {
        super();
        setValues(type);
    }

    public Ship(ShipSpec shipSpec) {
        super();
        setValues(shipSpec);
    }

    public Ship(OpponentType oppType) {
        this(oppType.castToInt());
    }

    public Ship(int oppType) {
        this.opponentType = oppType;
        Game.getCurrentGame().getQuestSystem().fireEvent(ON_ENCOUNTER_GENERATE_OPPONENT, this);

        if (!initialized) {
            int tries = (oppType == OpponentType.MANTIS.castToInt()) ? Game.getCurrentGame().getDifficultyId() + 1
                    : Math.max(1, Game.getCurrentGame().getCommander().getWorth() / 150000
                            + Game.getCurrentGame().getDifficultyId() - Difficulty.NORMAL.castToInt());

            generateOpponentShip(oppType);
            generateOpponentAddCrew();
            generateOpponentAddGadgets(tries);
            generateOpponentAddShields(tries);
            generateOpponentAddWeapons(tries);

            if (oppType != OpponentType.MANTIS.castToInt()) {
                generateOpponentSetHullStrength();
            }

            if (oppType != OpponentType.POLICE.castToInt()) {
                generateOpponentAddCargo(oppType == OpponentType.PIRATE.castToInt());
            }
        }
    }

    public void addEquipment(Equipment item) {
        Equipment[] equip = getEquipmentsByType(item.getEquipmentType());

        int slot = -1;
        for (int i = 0; i < equip.length && slot == -1; i++)
            if (equip[i] == null) {
                slot = i;
            }

        if (slot >= 0) {
            equip[slot] = item.clone();
        }
    }

    public int getBaseWorth(boolean forInsurance) {
        // Trade-in value is three-fourths the original price subtract repair costs subtract costs to fill tank with fuel
        BooleanContainer reduceThePrice = new BooleanContainer(false);

        Game.getCurrentGame().getQuestSystem().fireEvent(ON_GET_BASE_WORTH, reduceThePrice);

        int shipPrice = getPrice() * (reduceThePrice.getValue() && !forInsurance ? 1 : 3) / 4;
        int price = shipPrice - (getHullStrength() - getHull()) * getRepairCost()
                - (getFuelTanks() - getFuel()) * getFuelCost();

        // Add 3/4 of the price of each item of equipment
        for (Weapon weapon : weapons) {
            if (weapon != null) {
                price += weapon.getSellPrice();
            }
        }
        for (Shield shield : shields) {
            if (shield != null) {
                price += shield.getSellPrice();
            }
        }
        for (Gadget gadget : gadgets) {
            if (gadget != null) {
                price += gadget.getSellPrice();
            }
        }

        return price;
    }

    int getBounty() {
        int price = getPrice();

        for (int i = 0; i < getWeapons().length; i++) {
            if (getWeapons()[i] != null) {
                price += getWeapons()[i].getPrice();
            }
        }

        for (int i = 0; i < getShields().length; i++) {
            if (getShields()[i] != null) {
                price += getShields()[i].getPrice();
            }
        }

        // Gadgets aren't counted in the price, because they are already taken
        // into account in the skill adjustment of the price.

        price = price * (2 * getPilot() + getEngineer() + 3 * getFighter()) / 60;

        // Divide by 200 to get the bounty, then round down to the nearest 25.
        int bounty = price / 200 / 25 * 25;

        return Math.max(25, Math.min(2500, bounty));
    }

    public Equipment[] getEquipmentsByType(EquipmentType type) {
        switch (type) {
            case WEAPON:
                return getWeapons();
            case SHIELD:
                return getShields();
            case GADGET:
                return getGadgets();
            default:
                return null;
        }
    }

    public void fire(int crewId) {
        int skill = getTrader();
        boolean found = false;
        CrewMember merc = null;
        for (int i = 0; i < getCrew().length; i++) {
            if (getCrew()[i] != null && getCrew()[i].getId() == crewId) {
                found = true;
                merc = getCrew()[i];
            }

            if (found) {
                getCrew()[i] = (i < getCrew().length - 1) ? getCrew()[i + 1] : null;
            }
        }

        if (getTrader() != skill) {
            Game.getCurrentGame().recalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());
        }

        if (merc != null && merc.isMercenary()) {
            StarSystem[] universe = Game.getCurrentGame().getUniverse();

            // The leaving Mercenary travels to a nearby random system.
            merc.setCurrentSystemId(StarSystemId.NA);
            while (merc.getCurrentSystemId() == StarSystemId.NA) {
                StarSystem system = universe[Functions.getRandom(universe.length)];
                if (Functions.distance(system, Game.getCurrentGame().getCommander().getCurrentSystem()) < Consts.MaxRange) {
                    merc.setCurrentSystemId(system.getId());
                }
            }
        }
    }

    private void generateOpponentAddCargo(boolean pirate) {
        if (getCargoBays() > 0) {
            Difficulty diff = Game.getCurrentGame().getDifficulty();
            int baysToFill = getCargoBays();

            if (diff.castToInt() >= Difficulty.NORMAL.castToInt()) {
                baysToFill = Math.min(15, 3 + Functions.getRandom(baysToFill - 5));
            }

            if (pirate) {
                if (diff.castToInt() < Difficulty.NORMAL.castToInt()) {
                    baysToFill = baysToFill * 4 / 5;
                } else {
                    baysToFill = Math.max(1, baysToFill / diff.castToInt());
                }
            }

            for (int bays, i = 0; i < baysToFill; i += bays) {
                int item = Functions.getRandom(Consts.TradeItems.length);
                bays = Math.min(baysToFill - i, 1 + Functions.getRandom(10 - item));
                getCargo()[item] += bays;
            }
        }
    }

    private void generateOpponentAddCrew() {
        Map<Integer, CrewMember> mercs = Game.getCurrentGame().getMercenaries();
        Difficulty diff = Game.getCurrentGame().getDifficulty();

        getCrew()[0] = mercs.get(CrewMemberId.OPPONENT.castToInt());
        getCrew()[0].setPilot(1 + Functions.getRandom(Consts.MaxSkill));
        getCrew()[0].setFighter(1 + Functions.getRandom(Consts.MaxSkill));
        getCrew()[0].setTrader(1 + Functions.getRandom(Consts.MaxSkill));
        getCrew()[0].setEngineer(1 + Functions.getRandom(Consts.MaxSkill));

        Game.getCurrentGame().getQuestSystem().fireEvent(ON_BEFORE_ENCOUNTER_GENERATE_OPPONENT, getCrew()[0]);

        int numCrew;
        if (diff == Difficulty.IMPOSSIBLE) {
            numCrew = getCrewQuarters();
        } else {
            numCrew = 1 + Functions.getRandom(getCrewQuarters());
            if (diff == Difficulty.HARD && numCrew < getCrewQuarters()) {
                numCrew++;
            }
        }

        for (int i = 1; i < numCrew; i++) {
            // Keep getting a new random mercenary until we have a non-special one.
            while (getCrew()[i] == null || !getCrew()[i].isMercenary()) {
                getCrew()[i] = mercs.get(Functions.getRandom(mercs.size()));
            }
        }
    }

    private void generateOpponentAddGadgets(int tries) {
        if (getGadgetSlots() > 0) {
            int numGadgets;

            if (Game.getCurrentGame().getDifficulty() == Difficulty.IMPOSSIBLE) {
                numGadgets = getGadgetSlots();
            } else {
                numGadgets = Functions.getRandom(getGadgetSlots() + 1);
                if (numGadgets < getGadgetSlots() && (tries > 4 || (tries > 2 && Functions.getRandom(2) > 0))) {
                    numGadgets++;
                }
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
                    if (!hasGadget(Consts.Gadgets[gadgetType].getType()) && gadgetType > bestGadgetType) {
                        bestGadgetType = gadgetType;
                    }
                }

                addEquipment(Consts.Gadgets[bestGadgetType]);
            }
        }
    }

    public int getHull() {
        return hull;
    }

    public void setHull(int hull) {
        this.hull = hull;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public boolean getEscapePod() {
        return escapePod;
    }

    public void setEscapePod(boolean escapePod) {
        this.escapePod = escapePod;
    }

    private void generateOpponentAddShields(int tries) {
        if (getShieldSlots() > 0) {
            int numShields;

            if (Game.getCurrentGame().getDifficulty() == Difficulty.IMPOSSIBLE) {
                numShields = getShieldSlots();
            } else {
                numShields = Functions.getRandom(getShieldSlots() + 1);
                if (numShields < getShieldSlots() && (tries > 3 || (tries > 1 && Functions.getRandom(2) > 0))) {
                    numShields++;
                }
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
                    if (!hasShield(Consts.Shields[shieldType].getType()) && shieldType > bestShieldType) {
                        bestShieldType = shieldType;
                    }
                }

                addEquipment(Consts.Shields[bestShieldType]);

                getShields()[i].setCharge(0);
                for (int j = 0; j < 5; j++) {
                    int charge = 1 + Functions.getRandom(getShields()[i].getPower());
                    if (charge > getShields()[i].getCharge()) {
                        getShields()[i].setCharge(charge);
                    }
                }
            }
        }
    }

    private void generateOpponentAddWeapons(int tries) {
        if (getWeaponSlots() > 0) {
            int numWeapons;

            if (Game.getCurrentGame().getDifficulty() == Difficulty.IMPOSSIBLE) {
                numWeapons = getWeaponSlots();
            } else if (getWeaponSlots() == 1) {
                numWeapons = 1;
            } else {
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
                    if (!hasWeapon(WeaponType.fromInt(weaponType), true) && weaponType > bestWeaponType) {
                        bestWeaponType = weaponType;
                    }
                }

                addEquipment(Consts.Weapons[bestWeaponType]);
            }
        }
    }

    private void generateOpponentSetHullStrength() {
        // If there are shields, the hull will probably be stronger
        if (getShieldStrength() == 0 || Functions.getRandom(5) == 0) {
            setHull(0);

            for (int i = 0; i < 5; i++) {
                int hull = 1 + Functions.getRandom(getHullStrength());
                if (hull > getHull()) {
                    setHull(hull);
                }
            }
        }
    }

    private void generateOpponentShip(int oppType) {
        Commander cmdr = Game.getCurrentGame().getCommander();
        PoliticalSystem polSys = Game.getCurrentGame().getWarpSystem().getPoliticalSystem();

        if (oppType == OpponentType.MANTIS.castToInt()) {
            setValues(ShipType.MANTIS);
        } else {
            ShipType oppShipType;
            IntContainer tries = new IntContainer(1);

            if (oppType < 1000) {
                switch (OpponentType.fromInt(oppType)) {
                    case PIRATE:
                        // Pirates become better when you get richer
                        tries.setValue(1 + cmdr.getWorth() / 100000);
                        tries.setValue(Math.max(1, tries.getValue() + Game.getCurrentGame().getDifficultyId() - Difficulty.NORMAL.castToInt()));
                        break;
                    case POLICE:
                        // The police will try to hunt you down with better ships if you are a villain, and they will
                        // try even harder when you are considered to be a psychopath (or are transporting Jonathan Wild)
                        if (cmdr.getPoliceRecordScore() < Consts.PoliceRecordScorePsychopath) {
                            tries.setValue(5);
                        } else if (cmdr.getPoliceRecordScore() < Consts.PoliceRecordScoreVillain) {
                            tries.setValue(3);
                        } else {
                            tries.setValue(1);
                        }

                        Game.getCurrentGame().getQuestSystem().fireEvent(ON_GENERATE_OPPONENT_SHIP_POLICE_TRIES, tries);

                        tries.setValue(Math.max(1, tries.getValue() + Game.getCurrentGame().getDifficultyId() - Difficulty.NORMAL.castToInt()));
                        break;
                }
            }

            if (oppType == OpponentType.TRADER.castToInt()) {
                oppShipType = ShipType.FLEA;
            } else {
                oppShipType = ShipType.GNAT;
            }

            int total = 0;
            for (int i = 0; i < Consts.MaxShip; i++) {
                ShipSpec spec = Consts.ShipSpecs[i];
                if (polSys.ShipTypeLikely(spec.getType(), oppType)) {
                    total += spec.getOccurrence();
                }
            }

            for (int i = 0; i < tries.getValue(); i++) {
                int x = Functions.getRandom(total);
                int sum = -1;
                int j = -1;

                do {
                    j++;
                    if (polSys.ShipTypeLikely(Consts.ShipSpecs[j].getType(), oppType)) {
                        if (sum > 0) {
                            sum += Consts.ShipSpecs[j].getOccurrence();
                        } else {
                            sum = Consts.ShipSpecs[j].getOccurrence();
                        }
                    }
                } while (sum < x && j < Consts.MaxShip);

                if (j > oppShipType.castToInt()) {
                    oppShipType = Consts.ShipSpecs[j].getType();
                }
            }

            setValues(oppShipType);
        }
    }

    // *************************************************************************
    // Returns the index of a trade good that is on a given ship that can be
    // bought/sold in the current system.
    // JAF - Made this MUCH simpler by storing an array of booleaneans indicating
    // the tradeable goods when HasTradeableItem is called.
    // *************************************************************************
    int getRandomTradeableItem() {
        int index = Functions.getRandom(getTradeableItems().length);

        while (!getTradeableItems()[index]) {
            index = (index + 1) % getTradeableItems().length;
        }

        return index;
    }

    public boolean hasCrew(int id) {
        return Arrays.stream(getCrew()).filter(Objects::nonNull).anyMatch(c -> c.getId() == id);
    }

    boolean hasEquipment(Equipment item) {
        switch (item.getEquipmentType()) {
            case WEAPON:
                return hasWeapon(((Weapon) item).getType(), true);
            case SHIELD:
                return hasShield(((Shield) item).getType());
            case GADGET:
                return hasGadget(((Gadget) item).getType());
        }
        return false;
    }

    public boolean hasGadget(GadgetType gadgetType) {
        boolean found = false;
        for (int i = 0; i < getGadgets().length && !found; i++) {
            if (getGadgets()[i] != null && getGadgets()[i].getType() == gadgetType) {
                found = true;
            }
        }
        return found;
    }

    public boolean hasShield(ShieldType shieldType) {
        boolean found = false;
        for (int i = 0; i < getShields().length && !found; i++) {
            if (getShields()[i] != null && getShields()[i].getType() == shieldType) {
                found = true;
            }
        }
        return found;
    }

    // *************************************************************************
    // Determines if a given ship is carrying items that can be bought or sold
    // in the current system.
    // *************************************************************************
    boolean hasTradeableItems() {
        boolean found = false;
        boolean criminal = Game.getCurrentGame().getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious;
        tradeableItems = new boolean[10];

        for (int i = 0; i < getCargo().length; i++) {
            // Trade only if trader is selling and the item has a buy price on the
            // local system, or trader is buying, and there is a sell price on the
            // local system.
            // Criminals can only buy or sell illegal goods, Noncriminals cannot buy
            // or sell such items.
            // Simplified this - JAF
            if (getCargo()[i] > 0
                    && criminal == Consts.TradeItems[i].isIllegal()
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
        for (int i = 0; i < getCrew().length && slot == -1; i++) {
            if (getCrew()[i] == null) {
                slot = i;
            }
        }

        if (slot >= 0) {
            getCrew()[slot] = merc;
        }

        if (getTrader() != skill) {
            Game.getCurrentGame().recalculateBuyPrices(Game.getCurrentGame().getCommander().getCurrentSystem());
        }
    }

    String getIllegalSpecialCargoActions() {
        ArrayList<String> actions = new ArrayList<>();

        Game.getCurrentGame().getQuestSystem().fireEvent(ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS, actions);

        return actions.isEmpty() ? "" : Functions.stringVars(Strings.EncounterPoliceSurrenderAction, Functions.formatList(actions));
    }

    String getIllegalSpecialCargoDescription(String wrapper, boolean includePassengers, boolean includeTradeItems) {
        ArrayList<String> items = new ArrayList<>();

        if (includePassengers) {
            Game.getCurrentGame().getQuestSystem().fireEvent(ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION, items);
        }

        if (includeTradeItems && isDetectableIllegalCargo()) {
            items.add(Strings.EncounterPoliceSubmitGoods);
        }

        String allItems = Functions.formatList(items);

        if (allItems.length() > 0 && wrapper.length() > 0)
            allItems = Functions.stringVars(wrapper, allItems);

        return allItems;
    }

    void performRepairs() {
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
        for (int i = slot; i < last; i++) {
            equip[i] = equip[i + 1];
        }
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

    void removeIllegalGoods() {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].isIllegal()) {
                getCargo()[i] = 0;
                Game.getCurrentGame().getCommander().getPriceCargo()[i] = 0;
            }
        }
    }

    public void setValues(ShipType type) {
        setValues(Consts.ShipSpecs[type.castToInt()]);
    }

    @Override
    public void setValues(ShipSpec shipSpec) {
        super.setValues(shipSpec);
        initializeShip();
    }

    private void initializeShip() {
        weapons = new Weapon[getWeaponSlots()];
        shields = new Shield[getShieldSlots()];
        gadgets = new Gadget[getGadgetSlots()];
        crewMembers = new CrewMember[getCrewQuarters()];
        fuel = getFuelTanks();
        hull = getHullStrength();
    }

    int getWeaponStrength() {
        return getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.QUANTUM_DISRUPTOR);
    }

    public int getWeaponStrength(WeaponType min, WeaponType max) {
        int total = 0;

        for (int i = 0; i < getWeapons().length; i++) {
            if (getWeapons()[i] != null && getWeapons()[i].getType().castToInt() >= min.castToInt()
                    && getWeapons()[i].getType().castToInt() <= max.castToInt()) {
                total += getWeapons()[i].getPower();
            }
        }

        return total;
    }

    public int getWorth(boolean forInsurance) {
        int price = getBaseWorth(forInsurance);
        for (int i = 0; i < cargo.length; i++) {
            price += Game.getCurrentGame().getCommander().getPriceCargo()[i];
        }

        return price;
    }

    boolean isAnyIllegalCargo() {
        int illegalCargo = 0;
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].isIllegal()) {
                illegalCargo += getCargo()[i];
            }
        }

        return illegalCargo > 0;
    }

    public int[] getCargo() {
        return cargo;
    }

    // Changed the semantics of Filled versus total Cargo Bays. Bays used for
    // transporting special items are now included in the total bays and in the filled bays. JAF
    public @Override
    int getCargoBays() {
        int bays = super.getCargoBays();

        for (int i = 0; i < getGadgets().length; i++)
            if (getGadgets()[i] != null
                    && (getGadgets()[i].getType() == GadgetType.EXTRA_CARGO_BAYS || getGadgets()[i].getType() == GadgetType.HIDDEN_CARGO_BAYS)) {
                bays += 5;
            }

        return bays + getExtraCargoBays() + getHiddenCargoBays();
    }

    public boolean isCloaked() {
        int oppEng = isCommandersShip() ? Game.getCurrentGame().getOpponent().getEngineer() : Game.getCurrentGame().getCommander()
                .getShip().getEngineer();
        return hasGadget(GadgetType.CLOAKING_DEVICE) && getEngineer() > oppEng;
    }

    boolean isCommandersShip() {
        return this == Game.getCurrentGame().getShip();
    }

    public CrewMember[] getCrew() {
        return crewMembers;
    }

    int getCrewCount() {
        int total = 0;
        for (int i = 0; i < getCrew().length; i++) {
            if (getCrew()[i] != null) {
                total++;
            }
        }
        return total;
    }

    boolean isDetectableIllegalCargo() {
        int illegalCargo = 0;
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (Consts.TradeItems[i].isIllegal()) {
                illegalCargo += getCargo()[i];
            }
        }

        return (illegalCargo - getHiddenCargoBays()) > 0;
    }

    boolean isDetectableIllegalCargoOrPassengers() {
        return isDetectableIllegalCargo() || isIllegalSpecialCargo();
    }

    boolean isDisableable() {
        BooleanContainer notDisableable = new BooleanContainer(true);
        Game.getCurrentGame().getQuestSystem().fireEvent(ENCOUNTER_IS_DISABLEABLE, notDisableable);

        return !isCommandersShip() && getType() != ShipType.MANTIS && notDisableable.getValue();
    }

    public int getEngineer() {
        return getSkills()[SkillType.ENGINEER.castToInt()];
    }

    private int getExtraCargoBays() {
        int bays = 0;

        for (int i = 0; i < getGadgets().length; i++) {
            if (getGadgets()[i] != null && getGadgets()[i].getType() == GadgetType.EXTRA_CARGO_BAYS) {
                bays += 5;
            }
        }

        return bays;
    }

    public int getFighter() {
        return getSkills()[SkillType.FIGHTER.castToInt()];
    }

    // Changed the semantics of Filled versus total Cargo Bays. Bays used for
    // transporting special items are now included in the total bays and in the
    // filled bays. JAF

    public int getFilledCargoBays() {
        IntContainer filled = new IntContainer(getFilledNormalCargoBays());
        Game.getCurrentGame().getQuestSystem().fireEvent(ON_GET_FILLED_CARGO_BAYS, filled);
        return filled.getValue();
    }

    private int getFilledNormalCargoBays() {
        return IntStream.of(cargo).sum();
    }

    public int getFreeCargoBays() {
        return getCargoBays() - getFilledCargoBays();
    }

    public int getFreeCrewQuartersCount() {
        return (int) Arrays.stream(getCrew()).filter(Objects::isNull).count();
    }

    public int getFreeSlots() {
        return getFreeGadgetSlots() + getFreeShieldSlots() + getFreeWeaponSlots();
    }

    public int getFreeGadgetSlots() {
        return (int) Arrays.stream(getGadgets()).filter(Objects::isNull).count();
    }

    public int getFreeShieldSlots() {
        return (int) Arrays.stream(getShields()).filter(Objects::isNull).count();
    }

    public int getFreeWeaponSlots() {
        return (int) Arrays.stream(getWeapons()).filter(Objects::isNull).count();
    }

    @Override
    public int getFuelTanks() {
        return super.getFuelTanks() + (hasGadget(GadgetType.FUEL_COMPACTOR) ? Consts.FuelCompactorTanks : 0);
    }

    public Gadget[] getGadgets() {
        return gadgets;
    }

    private int getHiddenCargoBays() {
        int bays = 0;

        for (int i = 0; i < getGadgets().length; i++) {
            if (getGadgets()[i] != null && getGadgets()[i].getType() == GadgetType.HIDDEN_CARGO_BAYS) {
                bays += 5;
            }
        }

        return bays;
    }

    public String getHullText() {
        return Functions.stringVars(Strings.EncounterHullStrength, Functions.formatNumber((int) Math.floor((double) 100
                * getHull() / getHullStrength())));
    }

    public boolean isIllegalSpecialCargo() {
        BooleanContainer isIllegalCargo = new BooleanContainer(false);
        Game.getCurrentGame().getQuestSystem().fireEvent(IS_ILLEGAL_SPECIAL_CARGO, isIllegalCargo);
        return isIllegalCargo.getValue();
    }

    public int getPilot() {
        return getSkills()[SkillType.PILOT.castToInt()];
    }

    public int getShieldCharge() {
        int total = 0;

        for (int i = 0; i < getShields().length; i++) {
            if (getShields()[i] != null) {
                total += getShields()[i].getCharge();
            }
        }

        return total;
    }

    public Shield[] getShields() {
        return shields;
    }

    public int getShieldStrength() {
        int total = 0;

        for (int i = 0; i < getShields().length; i++) {
            if (getShields()[i] != null) {
                total += getShields()[i].getPower();
            }
        }

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
                if (getCrew()[crew] != null && getCrew()[crew].getSkills()[skill] > max) {
                    max = getCrew()[crew].getSkills()[skill];
                }
            }

            skills[skill] = Math.max(1, Game.getCurrentGame().getDifficulty().adjustSkill(max));
        }

        // Adjust skills based on any gadgets on board.
        for (int i = 0; i < getGadgets().length; i++) {
            if (getGadgets()[i] != null && getGadgets()[i].getSkillBonus() != SkillType.NA) {
                skills[getGadgets()[i].getSkillBonus().castToInt()] += Consts.SkillBonus;
            }
        }

        return skills;
    }

    // Crew members that are not hired/fired - Commander, Jarek, Princess, and Wild - JAF
    List<CrewMember> getSpecialCrew() {
        return Arrays.stream(getCrew()).filter(c -> c != null && !c.isMercenary()).collect(Collectors.toList());
    }

    // Sort all cargo based on value and put some of it in hidden bays, if they are present.
    ArrayList<Integer> getStealableCargo() {
        // Put all of the cargo items in a list and sort it. Reverse it so the most expensive items are first.
        ArrayList<Integer> tradeItems = new ArrayList<>();
        for (int tradeItem = 0; tradeItem < getCargo().length; tradeItem++) {
            for (int count = 0; count < getCargo()[tradeItem]; count++) {
                tradeItems.add(tradeItem);
            }
        }
        tradeItems.sort();
        tradeItems.reverse();

        IntContainer intContainer = new IntContainer(getHiddenCargoBays());

        Game.getCurrentGame().getQuestSystem().fireEvent(ENCOUNTER_GET_STEALABLE_CARGO, intContainer);

        int hidden = intContainer.getValue();

        if (hidden > 0) {
            hidden = Math.min(hidden, tradeItems.size());
            tradeItems.removeRange(0, hidden);
        }

        return tradeItems;
    }

    private boolean[] getTradeableItems() {
        return tradeableItems;
    }

    public int getTrader() {
        return Game.getCurrentGame().getQuestSystem().affectSkills(getSkills())[SkillType.TRADER.castToInt()];
    }

    public Weapon[] getWeapons() {
        return weapons;
    }

    // For test purposes
    public void setWeapons(Weapon[] weapons) {
        this.weapons = weapons;
    }

    public void setShields(Shield[] shields) {
        this.shields = shields;
    }

    public void setGadgets(Gadget[] gadgets) {
        this.gadgets = gadgets;
    }

    //For test purposes

    public void setCrew(CrewMember[] crewMembers) {
        this.crewMembers = crewMembers;
    }

    public void setCargo(int[] cargo) {
        this.cargo = cargo;
    }

    public boolean isPod() {
        return pod;
    }

    public void setPod(boolean pod) {
        this.pod = pod;
    }

    public void setTradeableItems(boolean[] tradeableItems) {
        this.tradeableItems = tradeableItems;
    }

    public boolean isEscapePod() {
        return escapePod;
    }

    public int getOpponentType() {
        return opponentType;
    }

    public void setOpponentType(int opponentType) {
        this.opponentType = opponentType;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ship)) return false;
        if (!super.equals(o)) return false;
        Ship ship = (Ship) o;
        return fuel == ship.fuel &&
                hull == ship.hull &&
                pod == ship.pod &&
                escapePod == ship.escapePod &&
                Arrays.equals(cargo, ship.cargo) &&
                Arrays.equals(weapons, ship.weapons) &&
                Arrays.equals(shields, ship.shields) &&
                Arrays.equals(gadgets, ship.gadgets) &&
                Arrays.equals(crewMembers, ship.crewMembers) &&
                Arrays.equals(tradeableItems, ship.tradeableItems);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(super.hashCode(), fuel, hull, pod, escapePod);
        result = 31 * result + Arrays.hashCode(cargo);
        result = 31 * result + Arrays.hashCode(weapons);
        result = 31 * result + Arrays.hashCode(shields);
        result = 31 * result + Arrays.hashCode(gadgets);
        result = 31 * result + Arrays.hashCode(crewMembers);
        result = 31 * result + Arrays.hashCode(tradeableItems);
        return result;
    }
}
