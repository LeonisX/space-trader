package spacetrader.game;

import spacetrader.controls.Image;
import spacetrader.game.enums.EquipmentSubType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.TechLevel;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.Hashtable;

public abstract class Equipment extends STSerializableObject implements Cloneable {

    private EquipmentType equipType;
    private int price;
    private TechLevel minTech;
    private int chance;

    public Equipment(EquipmentType equipType, int price, TechLevel minTechLevel, int chance) {
        this.equipType = equipType;
        this.price = price;
        this.minTech = minTechLevel;
        this.chance = chance;
    }

    public Equipment(Hashtable hash) {
        super();
        equipType = EquipmentType.fromInt(getValueFromHash(hash, "_equipType", Integer.class));
        price = getValueFromHash(hash, "_price", Integer.class);
        minTech = TechLevel.fromInt(getValueFromHash(hash, "_minTech", Integer.class));
        chance = getValueFromHash(hash, "_chance", Integer.class);
    }

    //TODO was Clone. This implements Object.clone. Need to test
    public abstract Equipment clone();

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.put("_equipType", equipType.castToInt());
        hash.put("_price", price);
        hash.put("_minTech", minTech.castToInt());
        hash.put("_chance", chance);

        return hash;
    }

    @Override
    public String toString() {
        return getName();
    }

    public abstract boolean isTypeEquals(Object type);

    private int getBaseImageIndex() {
        switch (getEquipmentType()) {
            case GADGET:
                return Strings.WeaponNames.length + Strings.ShieldNames.length;
            case SHIELD:
                return Strings.WeaponNames.length;
            case WEAPON:
                return 0;
        }
        return 0;
    }

    int getChance() {
        return chance;
    }

    public EquipmentType getEquipmentType() {
        return equipType;
    }

    final public Image getImage() {
        return GuiEngine.getImageProvider().getEquipmentImages().getImages()[getBaseImageIndex() + getSubType().castToInt()];
    }

    public TechLevel getMinimumTechLevel() {
        return minTech;
    }

    public String getName() {
        return "Name not defined";
    }

    public int getPrice() {
        Commander cmdr = Game.getCurrentGame().getCommander();

        if (cmdr != null && cmdr.getCurrentSystem().getTechLevel().castToInt() >= getMinimumTechLevel().castToInt()) {
            return (price * (100 - cmdr.getShip().getTrader())) / 100;
        } else {
            return 0;
        }
    }

    public int getSellPrice() {
        return price * 3 / 4;
    }

    public EquipmentSubType getSubType() {
        return null;
    }

    int getTransferPrice() {
        // The cost to transfer is 10% of the item worth. This is changed
        // from actually PAYING the buyer about 8% to transfer items. - JAF
        return getSellPrice() * 110 / 90;
    }
}
