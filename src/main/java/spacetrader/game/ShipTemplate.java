package spacetrader.game;

import spacetrader.controls.Image;
import spacetrader.game.enums.ShipType;
import spacetrader.game.enums.Size;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.Hashtable;

public class ShipTemplate extends STSerializableObject implements Comparable<ShipTemplate> {

    private String name = null;
    private Size size = spacetrader.game.enums.Size.TINY;
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

    public ShipTemplate(Hashtable hash) {
        name = getValueFromHash(hash, "_name", name, String.class);
        size = (getValueFromHash(hash, "_size", size));
        imageIndex = getValueFromHash(hash, "_imageIndex", imageIndex);
        cargoBays = getValueFromHash(hash, "_cargoBays", cargoBays);
        weaponSlots = getValueFromHash(hash, "_weaponSlots", weaponSlots);
        shieldSlots = getValueFromHash(hash, "_shieldSlots", shieldSlots);
        gadgetSlots = getValueFromHash(hash, "_gadgetSlots", gadgetSlots);
        crewQuarters = getValueFromHash(hash, "_crewQuarters", crewQuarters);
        fuelTanks = getValueFromHash(hash, "_fuelTanks", fuelTanks);
        hullStrength = getValueFromHash(hash, "_hullStrength", hullStrength);
        images = getValueFromHash(hash, "_images", images);
    }

    public int compareTo(ShipTemplate other) {
        if (other == null) {
            return 1;
        } else {
            return getName().compareTo((other).getName());
        }
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_name", name);
        hash.add("_size", size.castToInt());
        hash.add("_imageIndex", imageIndex);
        hash.add("_cargoBays", cargoBays);
        hash.add("_weaponSlots", weaponSlots);
        hash.add("_shieldSlots", shieldSlots);
        hash.add("_gadgetSlots", gadgetSlots);
        hash.add("_crewQuarters", crewQuarters);
        hash.add("_fuelTanks", fuelTanks);
        hash.add("_hullStrength", hullStrength);

        if (images != null) {
            hash.add("_images", images);
        }

        return hash;
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
