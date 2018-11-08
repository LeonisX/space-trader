/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron
 * Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to
 * http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/

package spacetrader.game;

import spacetrader.controls.Graphics;
import spacetrader.controls.Image;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.Functions;
import spacetrader.util.Hashtable;

public class ShipSpec extends STSerializableObject {

    private ShipType type = ShipType.CUSTOM;
    private Size size = Size.TINY;
    private int cargoBays = 0;
    private int weaponSlots = 0;
    private int shieldSlots = 0;
    private int gadgetSlots = 0;
    private int crewQuarters = 0;
    private int fuelTanks = 0;
    private int fuelCost = 0;
    private int hullStrength = 0;
    private int repairCost = 0;
    private int price = 0;
    private int occurrence = 0;
    private Activity police = Activity.NA;
    private Activity pirates = Activity.NA;
    private Activity traders = Activity.NA;
    private TechLevel minTech = TechLevel.UNAVAILABLE;
    private boolean hullUpgraded = false;
    private int imageIndex = Consts.ShipImgUseDefault;

    ShipSpec() {
    }

    ShipSpec(ShipType type, Size size, int cargoBays, int weaponSlots, int shieldSlots, int gadgetSlots,
             int crewQuarters, int fuelTanks, int fuelCost, int hullStrength, int repairCost, int price, int occurrence,
             Activity police, Activity pirates, Activity traders, TechLevel minTechLevel) {
        this.type = type;
        this.size = size;
        this.cargoBays = cargoBays;
        this.weaponSlots = weaponSlots;
        this.shieldSlots = shieldSlots;
        this.gadgetSlots = gadgetSlots;
        this.crewQuarters = crewQuarters;
        this.fuelTanks = fuelTanks;
        this.fuelCost = fuelCost;
        this.hullStrength = hullStrength;
        this.repairCost = repairCost;
        this.price = price;
        this.occurrence = occurrence;
        this.police = police;
        this.pirates = pirates;
        this.traders = traders;
        minTech = minTechLevel;
    }

    ShipSpec(Hashtable hash) {
        super();
        type = ShipType.fromInt(getValueFromHash(hash, "_type", type, Integer.class));
        size = Size.fromInt(getValueFromHash(hash, "_size", size, Integer.class));
        cargoBays = getValueFromHash(hash, "_cargoBays", cargoBays);
        weaponSlots = getValueFromHash(hash, "_weaponSlots", weaponSlots);
        shieldSlots = getValueFromHash(hash, "_shieldSlots", shieldSlots);
        gadgetSlots = getValueFromHash(hash, "_gadgetSlots", gadgetSlots);
        crewQuarters = getValueFromHash(hash, "_crewQuarters", crewQuarters);
        fuelTanks = getValueFromHash(hash, "_fuelTanks", fuelTanks);
        fuelCost = getValueFromHash(hash, "_fuelCost", fuelCost);
        hullStrength = getValueFromHash(hash, "_hullStrength", hullStrength);
        repairCost = getValueFromHash(hash, "_repairCost", repairCost);
        price = getValueFromHash(hash, "_price", price);
        occurrence = getValueFromHash(hash, "_occurrence", occurrence);
        police = Activity.fromInt(getValueFromHash(hash, "_police", police, Integer.class));
        pirates = Activity.fromInt(getValueFromHash(hash, "_pirates", pirates, Integer.class));
        traders = Activity.fromInt(getValueFromHash(hash, "_traders", traders, Integer.class));
        minTech = TechLevel.fromInt(getValueFromHash(hash, "_minTech", minTech, Integer.class));
        hullUpgraded = getValueFromHash(hash, "_hullUpgraded", hullUpgraded);
        imageIndex = getValueFromHash(hash, "_imageIndex", Consts.ShipImgUseDefault);

        // Get the images if the ship uses the custom images.
        if (getImageIndex() == ShipType.CUSTOM.castToInt()) {
            GuiEngine.getImageProvider().setCustomShipImages(getValueFromHash(hash, "_images", GuiEngine.getImageProvider()
                    .getCustomShipImages()));
        }

        // Get the name if the ship is a custom design.
        if (getType() == ShipType.CUSTOM) {
            Strings.ShipNames[ShipType.CUSTOM.castToInt()] = getValueFromHash(hash, "_name",
                    Strings.ShipNames[ShipType.CUSTOM.castToInt()]);

            Consts.ShipSpecs[ShipType.CUSTOM.castToInt()] = new ShipSpec(type, size, cargoBays, weaponSlots,
                    shieldSlots, gadgetSlots, crewQuarters, fuelTanks, fuelCost, hullStrength, repairCost,
                    price, occurrence, police, pirates, traders, minTech);
            updateCustomImageOffsetConstants();
        }
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.put("_type", type.castToInt());
        hash.put("_size", size.castToInt());
        hash.put("_cargoBays", cargoBays);
        hash.put("_weaponSlots", weaponSlots);
        hash.put("_shieldSlots", shieldSlots);
        hash.add("_gadgetSlots", gadgetSlots);
        hash.add("_crewQuarters", crewQuarters);
        hash.add("_fuelTanks", fuelTanks);
        hash.add("_fuelCost", fuelCost);
        hash.add("_hullStrength", hullStrength);
        hash.add("_repairCost", repairCost);
        hash.add("_price", price);
        hash.add("_occurrence", occurrence);
        hash.add("_police", police.castToInt());
        hash.add("_pirates", pirates.castToInt());
        hash.add("_traders", traders.castToInt());
        hash.add("_minTech", minTech.castToInt());
        hash.add("_hullUpgraded", hullUpgraded);

        // Only save image index if it's not the default.
        if (imageIndex != Consts.ShipImgUseDefault) {
            hash.add("_imageIndex", imageIndex);
        }

        // Save the name if the ship is a custom design.
        if (getType() == ShipType.CUSTOM) {
            hash.add("_name", getName());
        }

        // Save the images if the ship uses the custom images.
        if (getImageIndex() == ShipType.CUSTOM.castToInt()) {
            hash.add("_images", GuiEngine.getImageProvider().getCustomShipImages());
        }

        return hash;
    }

    protected void setValues(ShipType type) {
        int typeInt = type.castToInt();

        this.type = type;
        size = Consts.ShipSpecs[typeInt].size;
        cargoBays = Consts.ShipSpecs[typeInt].cargoBays;
        weaponSlots = Consts.ShipSpecs[typeInt].weaponSlots;
        shieldSlots = Consts.ShipSpecs[typeInt].shieldSlots;
        gadgetSlots = Consts.ShipSpecs[typeInt].gadgetSlots;
        crewQuarters = Consts.ShipSpecs[typeInt].crewQuarters;
        fuelTanks = Consts.ShipSpecs[typeInt].fuelTanks;
        fuelCost = Consts.ShipSpecs[typeInt].fuelCost;
        hullStrength = Consts.ShipSpecs[typeInt].hullStrength;
        repairCost = Consts.ShipSpecs[typeInt].repairCost;
        price = Consts.ShipSpecs[typeInt].price;
        occurrence = Consts.ShipSpecs[typeInt].occurrence;
        police = Consts.ShipSpecs[typeInt].police;
        pirates = Consts.ShipSpecs[typeInt].pirates;
        traders = Consts.ShipSpecs[typeInt].traders;
        minTech = Consts.ShipSpecs[typeInt].minTech;
        hullUpgraded = Consts.ShipSpecs[typeInt].hullUpgraded;
        imageIndex = Consts.ShipSpecs[typeInt].imageIndex;
    }

    int getSlotsCount(EquipmentType type) {
        switch (type) {
            case WEAPON: return getWeaponSlots();
            case SHIELD: return getShieldSlots();
            case GADGET: return getGadgetSlots();
            default: return 0;
        }
    }

    public void updateCustomImageOffsetConstants() {
        Image image = GuiEngine.getImageProvider().getCustomShipImages()[0];
        int custIndex = ShipType.CUSTOM.castToInt();

        // Find the first column of pixels that has a non-white pixel for the X
        // value, and the last column for the width.
        int x = Graphics.getColumnOfFirstNonWhitePixel(image, 1);
        int width = Graphics.getColumnOfFirstNonWhitePixel(image, -1) - x + 1;
        Consts.ShipImageOffsets[custIndex].setX(Math.max(2, x));
        Consts.ShipImageOffsets[custIndex].setWidth(Math.min(62 - Consts.ShipImageOffsets[custIndex].getX(), width));
    }

    public int getCargoBays() {
        return cargoBays;
    }

    public void setCargoBays(int cargoBays) {
        this.cargoBays = cargoBays;
    }

    public int getFuelTanks() {
        return fuelTanks;
    }

    public void setFuelTanks(int fuelTanks) {
        this.fuelTanks = fuelTanks;
    }

    public int getWeaponSlots() {
        return weaponSlots;
    }

    public void setWeaponSlots(int weaponSlots) {
        this.weaponSlots = weaponSlots;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public int getShieldSlots() {
        return shieldSlots;
    }

    public void setShieldSlots(int shieldSlots) {
        this.shieldSlots = shieldSlots;
    }

    public int getRepairCost() {
        return repairCost;
    }

    void setRepairCost(int repairCost) {
        this.repairCost = repairCost;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean getHullUpgraded() {
        return hullUpgraded;
    }

    public void setHullUpgraded(boolean hullUpgraded) {
        this.hullUpgraded = hullUpgraded;
    }

    public int getGadgetSlots() {
        return gadgetSlots;
    }

    public void setGadgetSlots(int gadgetSlots) {
        this.gadgetSlots = gadgetSlots;
    }

    public int getFuelCost() {
        return fuelCost;
    }

    void setFuelCost(int fuelCost) {
        this.fuelCost = fuelCost;
    }

    public int getCrewQuarters() {
        return crewQuarters;
    }

    public void setCrewQuarters(int crewQuarters) {
        this.crewQuarters = crewQuarters;
    }

    public int getHullStrength() {
        return hullStrength + (getHullUpgraded() ? Consts.HullUpgrade : 0);
    }

    public void setHullStrength(int hullStrength) {
        this.hullStrength = hullStrength;
    }

    public Image getImage() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetNormal];
    }

    public Image getImageDamaged() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetDamage];
    }

    public Image getImageDamagedWithShields() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetShieldDamage];
    }

    public int getImageIndex() {
        return (imageIndex == Consts.ShipImgUseDefault ? getType().castToInt() : imageIndex);
    }

    public void setImageIndex(int value) {
        this.imageIndex = (value == getType().castToInt() ? Consts.ShipImgUseDefault : value);
    }

    public Image getImageWithShields() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetShield];
    }

    public TechLevel getMinimumTechLevel() {
        return minTech;
    }

    public String getName() {
        return Functions.singular(Strings.ShipNames[getType().castToInt()]);
    }

    int getOccurrence() {
        return occurrence;
    }

    public Activity getPolice() {
        return police;
    }

    public Activity getPirates() {
        return pirates;
    }

    Activity getTraders() {
        return traders;
    }

    public ShipType getType() {
        return type;
    }
}
