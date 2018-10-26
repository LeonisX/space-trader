package spacetrader.game.enums;

public enum ShipyardSkill implements SpaceTraderEnum {

    CREW_QUARTERS, // = 0, // Crew Quarters take up 2 fewer units
    FUEL_BASE, // = 1, // Fuel Base is 2 greater
    HULL_PER_UNIT, // = 2, // Number of Hull Points per unit is 5 greater
    SHIELD_SLOT_UNITS, // = 3, // Shield Slots take up 2 fewer units
    WEAPON_SLOT_UNITS;// = 4 // Weapon Slots take up 2 fewer units

    @Override
    public int castToInt() {
        return ordinal();
    }
}
