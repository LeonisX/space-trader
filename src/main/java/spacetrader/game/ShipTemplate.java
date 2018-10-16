/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
package spacetrader.game;

import spacetrader.controls.Image;
import spacetrader.game.enums.ShipType;
import spacetrader.game.enums.Size;
import spacetrader.guifacade.GuiEngine;
import spacetrader.util.Hashtable;

public class ShipTemplate extends STSerializableObject implements Comparable<ShipTemplate> {
    // #region Member Variables

    private String _name = null;
    private Size _size = spacetrader.game.enums.Size.Tiny;
    private int _imageIndex = ShipType.Custom.castToInt();
    private int _cargoBays = 0;
    private int _weaponSlots = 0;
    private int _shieldSlots = 0;
    private int _gadgetSlots = 0;
    private int _crewQuarters = 0;
    private int _fuelTanks = 0;
    private int _hullStrength = 0;
    private Image[] _images = null;

    // #endregion

    // #region Methods

    public ShipTemplate(Size size, String name) {
        _name = name;
        _size = size;
        _images = GuiEngine.imageProvider.getCustomShipImages();
    }

    public ShipTemplate(ShipSpec spec, String name) {
        _name = name;
        _size = spec.getSize();
        _imageIndex = spec.ImageIndex();
        _cargoBays = spec.getCargoBays();
        _weaponSlots = spec.getWeaponSlots();
        _shieldSlots = spec.getShieldSlots();
        _gadgetSlots = spec.getGadgetSlots();
        _crewQuarters = spec.getCrewQuarters();
        _fuelTanks = spec.getFuelTanks();
        _hullStrength = spec.getHullStrength();

        if (ImageIndex() == Consts.ShipImgUseDefault)
            _images = GuiEngine.imageProvider.getCustomShipImages();
    }

    public ShipTemplate(Hashtable hash) {
        _name = GetValueFromHash(hash, "_name", _name, String.class);
        _size = (GetValueFromHash(hash, "_size", _size));
        _imageIndex = GetValueFromHash(hash, "_imageIndex", _imageIndex);
        _cargoBays = GetValueFromHash(hash, "_cargoBays", _cargoBays);
        _weaponSlots = GetValueFromHash(hash, "_weaponSlots", _weaponSlots);
        _shieldSlots = GetValueFromHash(hash, "_shieldSlots", _shieldSlots);
        _gadgetSlots = GetValueFromHash(hash, "_gadgetSlots", _gadgetSlots);
        _crewQuarters = GetValueFromHash(hash, "_crewQuarters", _crewQuarters);
        _fuelTanks = GetValueFromHash(hash, "_fuelTanks", _fuelTanks);
        _hullStrength = GetValueFromHash(hash, "_hullStrength", _hullStrength);
        _images = GetValueFromHash(hash, "_images", _images);
    }

    public int compareTo(ShipTemplate other) {
        if (other == null)
            return 1;
        else
            return Name().compareTo((other).Name());
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_name", _name);
        hash.add("_size", _size.castToInt());
        hash.add("_imageIndex", _imageIndex);
        hash.add("_cargoBays", _cargoBays);
        hash.add("_weaponSlots", _weaponSlots);
        hash.add("_shieldSlots", _shieldSlots);
        hash.add("_gadgetSlots", _gadgetSlots);
        hash.add("_crewQuarters", _crewQuarters);
        hash.add("_fuelTanks", _fuelTanks);
        hash.add("_hullStrength", _hullStrength);

        if (_images != null)
            hash.add("_images", _images);

        return hash;
    }

    public @Override
    String toString() {
        return Name();
    }

    // #endregion

    // #region Properties

    public int CargoBays() {
        return _cargoBays;
    }

    public void CargoBays(int value) {
        _cargoBays = value;
    }

    public int CrewQuarters() {
        return _crewQuarters;
    }

    public void CrewQuarters(int value) {
        _crewQuarters = value;
    }

    public int FuelTanks() {
        return _fuelTanks;
    }

    public void FuelTanks(int value) {
        _fuelTanks = value;
    }

    public int GadgetSlots() {
        return _gadgetSlots;
    }

    public void GadgetSlots(int value) {
        _gadgetSlots = value;
    }

    public int HullStrength() {
        return _hullStrength;
    }

    public void HullStrength(int value) {
        _hullStrength = value;
    }

    public int ImageIndex() {
        return _imageIndex;
    }

    public void ImageIndex(int value) {
        _imageIndex = value;
    }

    public Image[] Images() {
        return _images;
    }

    public void Images(Image[] value) {
        _images = value;
    }

    public String Name() {
        return _name;
    }

    public void Name(String value) {
        _name = value;
    }

    public int ShieldSlots() {
        return _shieldSlots;
    }

    public void ShieldSlots(int value) {
        _shieldSlots = value;
    }

    public Size Size() {
        return _size;
    }

    public void Size(Size value) {
        _size = value;
    }

    public int WeaponSlots() {
        return _weaponSlots;
    }

    public void WeaponSlots(int value) {
        _weaponSlots = value;
    }

    // #endregion
}
