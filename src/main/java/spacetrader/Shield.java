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
package spacetrader;


import spacetrader.enums.ShieldType;
import spacetrader.enums.TechLevel;
import spacetrader.util.EquipmentSubType;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;

public class Shield extends Equipment {
    private ShieldType _type;
    private int _power;
    private int _charge;
    private int Charge;

    public Shield(ShieldType type, int power, int price,
                  TechLevel minTechLevel, int chance) {
        super(spacetrader.enums.EquipmentType.Shield, price, minTechLevel, chance);
        _type = type;
        _power = power;

        _charge = _power;
    }

    public Shield(Hashtable hash) {
        super(hash);
        _type = ShieldType.fromInt(GetValueFromHash(hash, "_type", Integer.class));
        _power = GetValueFromHash(hash, "_power", Integer.class);
        _charge = GetValueFromHash(hash, "_charge", Integer.class);
    }

    public Equipment Clone() {
        Shield shield = new Shield(_type, _power, _price, _minTech, _chance);
        shield.setCharge(Charge);
        return shield;
    }

    public Hashtable Serialize() {
        Hashtable hash = super.Serialize();

        hash.put("_type", _type);
        hash.put("_power", _power);
        hash.put("_charge", _charge);

        return hash;
    }

    public boolean TypeEquals(Object type) {
        try {
            return (Type() == (ShieldType) type);
        } catch (Exception e) {
            Log.write("Ignored exception: " + e);
            return false;
        }
    }

    public String Name() {
        return Strings.ShieldNames[_type.castToInt()];
    }

    public int Power() {
        return _power;
    }


    public ShieldType Type() {
        return _type;
    }

    public EquipmentSubType SubType() {
        return Type();
    }

    public int getCharge() {
        return Charge;
    }

    public void setCharge(int charge) {
        Charge = charge;
    }


}
