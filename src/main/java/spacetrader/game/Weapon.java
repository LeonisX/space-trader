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

package spacetrader.game;

import spacetrader.game.enums.TechLevel;
import spacetrader.game.enums.WeaponType;
import spacetrader.game.enums.EquipmentSubType;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;

public class Weapon extends Equipment {
    // #region Member Declarations

    private WeaponType _type;
    private int _power;
    private boolean _disabling;

    // #endregion

    // #region Methods

    public Weapon(WeaponType type, int power, boolean disabling, int price,
                  TechLevel minTechLevel, int chance) {
        super(spacetrader.game.enums.EquipmentType.WEAPON, price, minTechLevel, chance);
        _type = type;
        _power = power;
        _disabling = disabling;
    }

    public Weapon(Hashtable hash) {
        super(hash);
        _type = WeaponType.fromInt(getValueFromHash(hash, "_type", Integer.class));
        _power = getValueFromHash(hash, "_power", Integer.class);
        _disabling = getValueFromHash(hash, "_disabling", false);
    }

    public @Override
    Equipment Clone() {
        return new Weapon(_type, _power, _disabling, _price, _minTech, _chance);
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_type", _type.castToInt());
        hash.add("_power", _power);
        hash.add("_disabling", _disabling);

        return hash;
    }

    public @Override
    boolean TypeEquals(Object type) {
        boolean equal = false;

        try {
            if (Type() == (WeaponType) type)
                equal = true;
        } catch (Exception e) {
            Log.write("Ignored exeption " + e);
        }

        return equal;
    }

    // #endregion

    // #region Properties

    public boolean Disabling() {
        return _disabling;

    }

    public @Override
    String getName() {
        return Strings.WeaponNames[_type.castToInt()];
    }

    public int getPower() {
        return _power;
    }

    public @Override
    EquipmentSubType getSubType() {
        return Type();
    }


    public WeaponType Type() {
        return _type;
    }

    // #endregion
}
