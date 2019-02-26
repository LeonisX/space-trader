package spacetrader.game;


import spacetrader.game.enums.EquipmentSubType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.ShieldType;
import spacetrader.game.enums.TechLevel;
import spacetrader.util.Log;

import java.io.Serializable;
import java.util.Objects;

public class Shield extends Equipment implements Serializable {

    static final long serialVersionUID = 12L;

    private ShieldType type;
    private int power;
    private int charge;

    private Shield() {
        //  need for tests
    }

    public Shield(EquipmentType equipType, int price, TechLevel minTechLevel, int chance) {
        super(equipType, price, minTechLevel, chance);
    }

    public Shield(ShieldType type, int power, int price, TechLevel minTechLevel, int chance) {
        super(EquipmentType.SHIELD, price, minTechLevel, chance);
        this.type = type;
        this.power = power;
        this.charge = power;
    }

    public Equipment clone() {
        Shield shield = new Shield(type, power, getPrice(), getMinimumTechLevel(), getChance());
        shield.setCharge(charge);
        return shield;
    }

    public boolean isTypeEquals(Object type) {
        try {
            return (getType() == type);
        } catch (Exception e) {
            Log.write("Ignored exception: " + e);
            return false;
        }
    }

    public String getName() {
        return Strings.ShieldNames[type.castToInt()];
    }

    public int getPower() {
        return power;
    }

    public ShieldType getType() {
        return type;
    }

    public EquipmentSubType getSubType() {
        return getType();
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Shield)) return false;
        if (!super.equals(object)) return false;
        Shield shield = (Shield) object;
        return power == shield.power &&
                charge == shield.charge &&
                type == shield.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, power, charge);
    }
}
