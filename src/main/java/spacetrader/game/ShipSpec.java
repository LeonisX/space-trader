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
//using System;
//using System.Collections;
//using System.Drawing;
package spacetrader.game;

import spacetrader.controls.Graphics;
import spacetrader.controls.Image;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.Hashtable;

public class ShipSpec extends STSerializableObject {
    private ShipType _type = ShipType.CUSTOM;
    private Size _size = spacetrader.game.enums.Size.Tiny;
    private int _cargoBays = 0;
    private int _weaponSlots = 0;
    private int _shieldSlots = 0;
    private int _gadgetSlots = 0;
    private int _crewQuarters = 0;
    private int _fuelTanks = 0;
    private int _fuelCost = 0;
    private int _hullStrength = 0;
    private int _repairCost = 0;
    private int _price = 0;
    private int _occurrence = 0;
    private Activity _police = Activity.NA;
    private Activity _pirates = Activity.NA;
    private Activity _traders = Activity.NA;
    private TechLevel _minTech = TechLevel.UNAVAILABLE;
    private boolean _hullUpgraded = false;
    private int _imageIndex = Consts.ShipImgUseDefault;

    public ShipSpec() {
    }

    public ShipSpec(ShipType type, Size size, int cargoBays, int weaponSlots, int shieldSlots, int gadgetSlots,
                    int crewQuarters, int fuelTanks, int fuelCost, int hullStrength, int repairCost, int price, int occurrence,
                    Activity police, Activity pirates, Activity traders, TechLevel minTechLevel) {
        _type = type;
        _size = size;
        _cargoBays = cargoBays;
        _weaponSlots = weaponSlots;
        _shieldSlots = shieldSlots;
        _gadgetSlots = gadgetSlots;
        _crewQuarters = crewQuarters;
        _fuelTanks = fuelTanks;
        _fuelCost = fuelCost;
        _hullStrength = hullStrength;
        _repairCost = repairCost;
        _price = price;
        _occurrence = occurrence;
        _police = police;
        _pirates = pirates;
        _traders = traders;
        _minTech = minTechLevel;
    }

    public ShipSpec(Hashtable hash) {
        super();
        _type = ShipType.fromInt(getValueFromHash(hash, "_type", _type, Integer.class));
        _size = Size.fromInt(getValueFromHash(hash, "_size", _size, Integer.class));
        _cargoBays = getValueFromHash(hash, "_cargoBays", _cargoBays);
        _weaponSlots = getValueFromHash(hash, "_weaponSlots", _weaponSlots);
        _shieldSlots = getValueFromHash(hash, "_shieldSlots", _shieldSlots);
        _gadgetSlots = getValueFromHash(hash, "_gadgetSlots", _gadgetSlots);
        _crewQuarters = getValueFromHash(hash, "_crewQuarters", _crewQuarters);
        _fuelTanks = getValueFromHash(hash, "_fuelTanks", _fuelTanks);
        _fuelCost = getValueFromHash(hash, "_fuelCost", _fuelCost);
        _hullStrength = getValueFromHash(hash, "_hullStrength", _hullStrength);
        _repairCost = getValueFromHash(hash, "_repairCost", _repairCost);
        _price = getValueFromHash(hash, "_price", _price);
        _occurrence = getValueFromHash(hash, "_occurrence", _occurrence);
        _police = Activity.fromInt(getValueFromHash(hash, "_police", _police, Integer.class));
        _pirates = Activity.fromInt(getValueFromHash(hash, "_pirates", _pirates, Integer.class));
        _traders = Activity.fromInt(getValueFromHash(hash, "_traders", _traders, Integer.class));
        _minTech = TechLevel.fromInt(getValueFromHash(hash, "_minTech", _minTech, Integer.class));
        _hullUpgraded = getValueFromHash(hash, "_hullUpgraded", _hullUpgraded);
        _imageIndex = getValueFromHash(hash, "_imageIndex", Consts.ShipImgUseDefault);

        // Get the images if the ship uses the custom images.
        if (getImageIndex() == ShipType.CUSTOM.castToInt())
            GuiEngine.getImageProvider().setCustomShipImages(getValueFromHash(hash, "_images", GuiEngine.getImageProvider()
                    .getCustomShipImages()));

        // Get the name if the ship is a custom design.
        if (getType() == ShipType.CUSTOM) {
            Strings.ShipNames[ShipType.CUSTOM.castToInt()] = getValueFromHash(hash, "_name",
                    Strings.ShipNames[ShipType.CUSTOM.castToInt()]);

            Consts.ShipSpecs[ShipType.CUSTOM.castToInt()] = new ShipSpec(_type, _size, _cargoBays, _weaponSlots,
                    _shieldSlots, _gadgetSlots, _crewQuarters, _fuelTanks, _fuelCost, _hullStrength, _repairCost,
                    _price, _occurrence, _police, _pirates, _traders, _minTech);
            updateCustomImageOffsetConstants();
        }
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.put("_type", _type.castToInt());
        hash.put("_size", _size.castToInt());
        hash.put("_cargoBays", _cargoBays);
        hash.put("_weaponSlots", _weaponSlots);
        hash.put("_shieldSlots", _shieldSlots);
        hash.add("_gadgetSlots", _gadgetSlots);
        hash.add("_crewQuarters", _crewQuarters);
        hash.add("_fuelTanks", _fuelTanks);
        hash.add("_fuelCost", _fuelCost);
        hash.add("_hullStrength", _hullStrength);
        hash.add("_repairCost", _repairCost);
        hash.add("_price", _price);
        hash.add("_occurrence", _occurrence);
        hash.add("_police", _police.castToInt());
        hash.add("_pirates", _pirates.castToInt());
        hash.add("_traders", _traders.castToInt());
        hash.add("_minTech", _minTech.castToInt());
        hash.add("_hullUpgraded", _hullUpgraded);

        // Only save image index if it's not the default.
        if (_imageIndex != Consts.ShipImgUseDefault)
            hash.add("_imageIndex", _imageIndex);

        // Save the name if the ship is a custom design.
        if (getType() == ShipType.CUSTOM)
            hash.add("_name", getName());

        // Save the images if the ship uses the custom images.
        if (getImageIndex() == ShipType.CUSTOM.castToInt())
            hash.add("_images", GuiEngine.getImageProvider().getCustomShipImages());

        return hash;
    }

    protected void setValues(ShipType type) {
        int typeInt = type.castToInt();

        _type = type;
        _size = Consts.ShipSpecs[typeInt]._size;
        _cargoBays = Consts.ShipSpecs[typeInt]._cargoBays;
        _weaponSlots = Consts.ShipSpecs[typeInt]._weaponSlots;
        _shieldSlots = Consts.ShipSpecs[typeInt]._shieldSlots;
        _gadgetSlots = Consts.ShipSpecs[typeInt]._gadgetSlots;
        _crewQuarters = Consts.ShipSpecs[typeInt]._crewQuarters;
        _fuelTanks = Consts.ShipSpecs[typeInt]._fuelTanks;
        _fuelCost = Consts.ShipSpecs[typeInt]._fuelCost;
        _hullStrength = Consts.ShipSpecs[typeInt]._hullStrength;
        _repairCost = Consts.ShipSpecs[typeInt]._repairCost;
        _price = Consts.ShipSpecs[typeInt]._price;
        _occurrence = Consts.ShipSpecs[typeInt]._occurrence;
        _police = Consts.ShipSpecs[typeInt]._police;
        _pirates = Consts.ShipSpecs[typeInt]._pirates;
        _traders = Consts.ShipSpecs[typeInt]._traders;
        _minTech = Consts.ShipSpecs[typeInt]._minTech;
        _hullUpgraded = Consts.ShipSpecs[typeInt]._hullUpgraded;
        _imageIndex = Consts.ShipSpecs[typeInt]._imageIndex;
    }

    public int getSlotsCount(EquipmentType type) {
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
        return _cargoBays;
    }

    public void setCargoBays(int value) {
        _cargoBays = value;
    }

    public int getFuelTanks() {
        return _fuelTanks;
    }

    public void setFuelTanks(int value) {
        _fuelTanks = value;
    }

    public int getWeaponSlots() {
        return _weaponSlots;
    }

    public void setWeaponSlots(int weaponSlots) {
        _weaponSlots = weaponSlots;
    }

    public Size getSize() {
        return _size;
    }

    public void setSize(Size size) {
        _size = size;
    }

    public int getShieldSlots() {
        return _shieldSlots;
    }

    public void setShieldSlots(int shieldSlots) {
        _shieldSlots = shieldSlots;
    }

    public int getRepairCost() {
        return _repairCost;
    }

    public void setRepairCost(int repairCost) {
        _repairCost = repairCost;
    }

    public int getPrice() {
        return _price;
    }

    public void setPrice(int price) {
        _price = price;
    }

    public boolean getHullUpgraded() {
        return _hullUpgraded;
    }

    public void setHullUpgraded(boolean hullUpgraded) {
        _hullUpgraded = hullUpgraded;
    }

    public int getGadgetSlots() {
        return _gadgetSlots;
    }

    public void setGadgetSlots(int gadgetSlots) {
        _gadgetSlots = gadgetSlots;
    }

    public int getFuelCost() {
        return _fuelCost;
    }

    public void setFuelCost(int fuelCost) {
        _fuelCost = fuelCost;
    }

    public int getCrewQuarters() {
        return _crewQuarters;
    }

    public void setCrewQuarters(int crewQuarters) {
        _crewQuarters = crewQuarters;
    }

    public int getHullStrength() {
        return _hullStrength + (getHullUpgraded() ? Consts.HullUpgrade : 0);
    }

    public void setHullStrength(int value) {
        _hullStrength = value;
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
        return (_imageIndex == Consts.ShipImgUseDefault ? getType().castToInt() : _imageIndex);
    }

    public void setImageIndex(int value) {
        _imageIndex = (value == getType().castToInt() ? Consts.ShipImgUseDefault : value);
    }

    public Image getImageWithShields() {
        return GuiEngine.getImageProvider().getShipImages().getImages()[getImageIndex() * Consts.ImagesPerShip
                + Consts.ShipImgOffsetShield];
    }

    public TechLevel getMinimumTechLevel() {
        return _minTech;
    }

    public String getName() {
        return Strings.ShipNames[getType().castToInt()];
    }

    public int Occurrence() {
        return _occurrence;
    }

    public Activity Police() {
        return _police;
    }

    public Activity Pirates() {
        return _pirates;
    }

    public Activity Traders() {
        return _traders;
    }

    public ShipType getType() {
        return _type;
    }

}
