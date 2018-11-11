package spacetrader.game;

import spacetrader.game.enums.EquipmentSubType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.TechLevel;
import spacetrader.game.enums.WeaponType;
import spacetrader.util.Log;

import java.io.Serializable;

public class Weapon extends Equipment implements Serializable {

    static final long serialVersionUID = 11L;

    private WeaponType type;
    private int power;
    private boolean disabling;

    public Weapon() {
    }

    public Weapon(WeaponType type, int power, boolean disabling, int price, TechLevel minTechLevel, int chance) {
        super(EquipmentType.WEAPON, price, minTechLevel, chance);
        this.type = type;
        this.power = power;
        this.disabling = disabling;
    }

    public @Override
    Equipment clone() {
        return new Weapon(type, power, disabling, getPrice(), getMinimumTechLevel(), getChance());
    }

    @Override
    public boolean isTypeEquals(Object targetType) {
        boolean equal = false;

        try {
            if (targetType == type) {
                equal = true;
            }
        } catch (Exception e) {
            Log.write("Ignored exception " + e);
        }

        return equal;
    }

    public boolean isDisabling() {
        return disabling;
    }

    @Override
    public String getName() {
        return Strings.WeaponNames[type.castToInt()];
    }

    public int getPower() {
        return power;
    }

    @Override
    public EquipmentSubType getSubType() {
        return type;
    }

    public WeaponType getType() {
        return type;
    }
}
