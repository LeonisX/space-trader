package spacetrader.game;

import spacetrader.controls.Image;
import spacetrader.game.enums.EquipmentSubType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.TechLevel;
import spacetrader.guifacade.GuiEngine;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class Equipment implements Serializable, Cloneable {

    static final long serialVersionUID = 13L;

    private UUID barcode;
    private EquipmentType equipType;
    private int price;
    private TechLevel minTech;
    private int chance;

    public Equipment() {
        barcode = UUID.randomUUID();
    }

    public Equipment(EquipmentType equipType, int price, TechLevel minTechLevel, int chance) {
        this();
        this.equipType = equipType;
        this.price = price;
        this.minTech = minTechLevel;
        this.chance = chance;
    }

    public abstract Equipment clone();

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
        return price;
    }

    public int getBuyPrice() {
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

    public EquipmentType getEquipType() {
        return equipType;
    }

    public void setEquipType(EquipmentType equipType) {
        this.equipType = equipType;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public TechLevel getMinTech() {
        return minTech;
    }

    public void setMinTech(TechLevel minTech) {
        this.minTech = minTech;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public UUID getBarcode() {
        return barcode;
    }

    public void setBarcode(UUID barcode) {
        this.barcode = barcode;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Equipment)) return false;
        Equipment equipment = (Equipment) object;
        return price == equipment.price &&
                chance == equipment.chance &&
                Objects.equals(barcode, equipment.barcode) &&
                equipType == equipment.equipType &&
                minTech == equipment.minTech;
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, equipType, price, minTech, chance);
    }
}
