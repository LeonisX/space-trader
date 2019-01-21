package spacetrader.game;

import spacetrader.game.enums.ShipType;
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.enums.ShipyardSkill;
import spacetrader.game.enums.Size;

import java.util.ArrayList;

public class Shipyard {

    public static final int[] COST_FUEL = new int[]{1, 1, 1, 3, 5, 10};
    public static final int[] COST_HULL = new int[]{1, 5, 10, 15, 20, 40};
    public static final int[] BASE_FUEL = new int[]{15, 14, 13, 12, 11, 10};
    public static final int[] BASE_HULL = new int[]{10, 25, 50, 100, 150, 200};
    public static final int[] DESIGN_FEE = new int[]{2000, 5000, 10000, 20000, 40000, 100000};
    public static final int[] MAX_UNITS = new int[]{50, 100, 150, 200, 250, 999};
    public static final int[] PER_UNIT_FUEL = new int[]{3, 2, 1, 1, 1, 1};
    public static final int[] PER_UNIT_HULL = new int[]{35, 30, 25, 20, 15, 10};
    public static final int[] PRICE_PER_UNIT = new int[]{75, 250, 500, 750, 1000, 1200};
    public static final int[] UNITS_CREW = new int[]{20, 20, 20, 20, 20, 20};
    public static final int[] UNITS_FUEL = new int[]{1, 1, 1, 5, 10, 15};
    public static final int[] UNITS_GADGET = new int[]{5, 5, 5, 5, 5, 5};
    public static final int[] UNITS_HULL = new int[]{1, 2, 3, 4, 5, 6};
    public static final int[] UNITS_SHIELD = new int[]{10, 10, 10, 8, 8, 8};
    public static final int[] UNITS_WEAPON = new int[]{15, 15, 15, 10, 10, 10};

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

    private ShipyardId id;
    private Size specialtySize;
    private ShipyardSkill skill;

    // Internal Variables
    private int modCrew = 0;
    private int modFuel = 0;
    private int modHull = 0;
    private int modShield = 0;
    private int modWeapon = 0;

    public Shipyard(ShipyardId id, Size specialtySize, ShipyardSkill skill) {
        this.id = id;
        this.specialtySize = specialtySize;
        this.skill = skill;

        switch (getSkill()) {
            case CREW_QUARTERS:
                modCrew = ADJUST_SKILL_CREW;
                break;
            case FUEL_BASE:
                modFuel = ADJUST_SKILL_FUEL;
                break;
            case HULL_PER_UNIT:
                modHull = ADJUST_SKILL_HULL;
                break;
            case SHIELD_SLOT_UNITS:
                modShield = ADJUST_SKILL_SHIELD;
                break;
            case WEAPON_SLOT_UNITS:
                modWeapon = ADJUST_SKILL_WEAPON;
                break;
        }
    }

    // Calculate the ship's price (worth here, not the price paid), the fuel
    // cost, and the repair cost.
    public void calculateDependantVariables() {
        getShipSpec().setPrice(getBasePrice() + getPenaltyCost());
        getShipSpec().setFuelCost(getCostFuel());
        getShipSpec().setRepairCost(getCostHull());
    }

    public int getAdjustedDesignFee() {
        return DESIGN_FEE[getShipSpec().getSize().castToInt()] * getCostAdjustment() / ADJUST_SIZE_DEFAULT;
    }

    public int getAdjustedPenaltyCost() {
        return getPenaltyCost() * getCostAdjustment() / ADJUST_SIZE_DEFAULT;
    }

    public int getAdjustedPrice() {
        return getBasePrice() * getCostAdjustment() / ADJUST_SIZE_DEFAULT;
    }

    public ArrayList<Size> getAvailableSizes() {
        ArrayList<Size> list = new ArrayList<>(6);

        int begin = Math.max(Size.TINY.castToInt(), getSpecialtySize().castToInt() - 2);
        int end = Math.min(Size.GARGANTUAN.castToInt(), getSpecialtySize().castToInt() + 2);
        for (int index = begin; index <= end; index++)
            list.add(Size.values()[index]);

        return list;
    }

    public int getBaseFuel() {
        return BASE_FUEL[getShipSpec().getSize().castToInt()] + modFuel;
    }

    public int getBaseHull() {
        return BASE_HULL[getShipSpec().getSize().castToInt()];
    }

    public int getBasePrice() {
        return getUnitsUsed() * getPricePerUnit();
    }

    public int getCostAdjustment() {
        int adjustment;

        switch (Math.abs(getSpecialtySize().castToInt() - getShipSpec().getSize().castToInt())) {
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

    public int getCostFuel() {
        return COST_FUEL[getShipSpec().getSize().castToInt()];
    }

    public int getCostHull() {
        return COST_HULL[getShipSpec().getSize().castToInt()];
    }

    public String getEngineer() {
        return Strings.ShipyardEngineers[getId().castToInt()];
    }

    public ShipyardId getId() {
        return id;
    }

    public int getMaxUnits() {
        return MAX_UNITS[getShipSpec().getSize().castToInt()];
    }

    public String getName() {
        return Strings.ShipyardNames[getId().castToInt()];
    }

    public int getPenaltyCost() {
        int penalty = 0;

        if (getPercentOfMaxUnits() >= PENALTY_SECOND_PCT)
            penalty = PENALTY_SECOND_FEE;
        else if (getPercentOfMaxUnits() >= PENALTY_FIRST_PCT)
            penalty = PENALTY_FIRST_FEE;

        return getBasePrice() * penalty / 100;
    }

    public int getPercentOfMaxUnits() {
        return getUnitsUsed() * 100 / getMaxUnits();
    }

    public int getPerUnitFuel() {
        return PER_UNIT_FUEL[getShipSpec().getSize().castToInt()];
    }

    public int getPerUnitHull() {
        return PER_UNIT_HULL[getShipSpec().getSize().castToInt()] + modHull;
    }

    public int getPricePerUnit() {
        return PRICE_PER_UNIT[getShipSpec().getSize().castToInt()];
    }

    public ShipSpec getShipSpec() {
        return Consts.ShipSpecs[ShipType.CUSTOM.castToInt()];
    }

    public ShipyardSkill getSkill() {
        return skill;
    }

    public Size getSpecialtySize() {
        return specialtySize;
    }

    public int getTotalCost() {
        return getAdjustedPrice() + getAdjustedPenaltyCost() + getAdjustedDesignFee() - getTradeIn();
    }

    public int getTradeIn() {
        return Game.getCurrentGame().getShip().getWorth(false);
    }

    public int getUnitsCrew() {
        return UNITS_CREW[getShipSpec().getSize().castToInt()] - modCrew;
    }

    public int getUnitsFuel() {
        return UNITS_FUEL[getShipSpec().getSize().castToInt()];
    }

    public int getUnitsGadgets() {
        return UNITS_GADGET[getShipSpec().getSize().castToInt()];
    }

    public int getUnitsHull() {
        return UNITS_HULL[getShipSpec().getSize().castToInt()];
    }

    public int getUnitsShields() {
        return UNITS_SHIELD[getShipSpec().getSize().castToInt()] - modShield;
    }

    public int getUnitsWeapons() {
        return UNITS_WEAPON[getShipSpec().getSize().castToInt()] - modWeapon;
    }

    public int getUnitsUsed() {
        int cargoBays = getShipSpec().getCargoBays();
        int crew = getShipSpec().getCrewQuarters() * getUnitsCrew();
        int fuel = (int) Math.ceil((double) (getShipSpec().getFuelTanks() - getBaseFuel()) / getPerUnitFuel() * getUnitsFuel());
        int gadgets = getShipSpec().getGadgetSlots() * getUnitsGadgets();
        int hull = (getShipSpec().getHullStrength() - getBaseHull()) / getPerUnitHull() * getUnitsHull();
        int shield = getShipSpec().getShieldSlots() * getUnitsShields();
        int weapons = getShipSpec().getWeaponSlots() * getUnitsWeapons();

        return cargoBays + crew + fuel + gadgets + hull + shield + weapons;
    }
}
