package spacetrader.game;

import spacetrader.controls.Image;
import spacetrader.game.enums.ShipType;
import spacetrader.game.enums.Size;
import spacetrader.guifacade.GuiEngine;

import java.io.Serializable;

public class ShipTemplate  implements Serializable, Comparable<ShipTemplate> {

    private String name;
    private Size size;
    private int imageIndex = ShipType.CUSTOM.castToInt();
    private int cargoBays = 0;
    private int weaponSlots = 0;
    private int shieldSlots = 0;
    private int gadgetSlots = 0;
    private int crewQuarters = 0;
    private int fuelTanks = 0;
    private int hullStrength = 0;
    private Image[] images = null;

    public ShipTemplate(Size size, String name) {
        this.name = name;
        this.size = size;
        images = GuiEngine.getImageProvider().getCustomShipImages();
    }

    public ShipTemplate(ShipSpec spec, String name) {
        this.name = name;
        size = spec.getSize();
        imageIndex = spec.getImageIndex();
        cargoBays = spec.getCargoBays();
        weaponSlots = spec.getWeaponSlots();
        shieldSlots = spec.getShieldSlots();
        gadgetSlots = spec.getGadgetSlots();
        crewQuarters = spec.getCrewQuarters();
        fuelTanks = spec.getFuelTanks();
        hullStrength = spec.getHullStrength();

        if (getImageIndex() == Consts.ShipImgUseDefault) {
            images = GuiEngine.getImageProvider().getCustomShipImages();
        }
    }

    public int compareTo(ShipTemplate other) {
        if (other == null) {
            return 1;
        } else {
            return getName().compareTo((other).getName());
        }
    }

    public @Override
    String toString() {
        return getName();
    }

    public int getCargoBays() {
        return cargoBays;
    }

    public void setCargoBays(int value) {
        cargoBays = value;
    }

    public int getCrewQuarters() {
        return crewQuarters;
    }

    public void setCrewQuarters(int value) {
        crewQuarters = value;
    }

    public int getFuelTanks() {
        return fuelTanks;
    }

    public void setFuelTanks(int value) {
        fuelTanks = value;
    }

    public int getGadgetSlots() {
        return gadgetSlots;
    }

    public void setGadgetSlots(int value) {
        gadgetSlots = value;
    }

    public int getHullStrength() {
        return hullStrength;
    }

    public void setHullStrength(int value) {
        hullStrength = value;
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int value) {
        imageIndex = value;
    }

    public Image[] getImages() {
        return images;
    }

    public void setImages(Image[] value) {
        images = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public int getShieldSlots() {
        return shieldSlots;
    }

    public void setShieldSlots(int value) {
        shieldSlots = value;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size value) {
        size = value;
    }

    public int getWeaponSlots() {
        return weaponSlots;
    }

    public void setWeaponSlots(int value) {
        weaponSlots = value;
    }

}
