package spacetrader.game;

import spacetrader.game.enums.*;
import spacetrader.util.Log;

import java.io.Serializable;
import java.util.Objects;

public class Gadget extends Equipment implements Serializable {

    static final long serialVersionUID = 13L;

    private GadgetType type;
    private SkillType skillBonus;

    private Gadget() {
        //  need for tests
    }

    public Gadget(EquipmentType equipType, int price, TechLevel minTechLevel, int chance, GadgetType type, SkillType skillBonus) {
        super(equipType, price, minTechLevel, chance);
        this.type = type;
        this.skillBonus = skillBonus;
    }

    public Gadget(GadgetType type, SkillType skillBonus, int price, TechLevel minTechLevel, int chance) {
        super(EquipmentType.GADGET, price, minTechLevel, chance);
        this.type = type;
        this.skillBonus = skillBonus;
    }

    @Override
    public Equipment clone() {
        return new Gadget(type, skillBonus, getPrice(), getMinimumTechLevel(), getChance());
    }

    @Override
    public boolean isTypeEquals(Object type) {
        boolean equal = false;

        try {
            if (getType() == type) {
                equal = true;
            }
        } catch (Exception e) {
            Log.write("Ignored Exception " + e);
        }

        return equal;
    }

    @Override
    public String getName() {
        return Strings.GadgetNames[type.castToInt()];
    }

    @Override
    public EquipmentSubType getSubType() {
        return getType();
    }

    public GadgetType getType() {
        return type;
    }

    public SkillType getSkillBonus() {
        return skillBonus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gadget)) return false;
        if (!super.equals(o)) return false;
        Gadget gadget = (Gadget) o;
        return type == gadget.type &&
                skillBonus == gadget.skillBonus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, skillBonus);
    }
}
