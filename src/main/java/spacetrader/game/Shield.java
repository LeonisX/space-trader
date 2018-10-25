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


import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.ShieldType;
import spacetrader.game.enums.TechLevel;
import spacetrader.game.enums.EquipmentSubType;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;

public class Shield extends Equipment {
    private ShieldType _type;
    private int _power;
    private int _charge;
    private int Charge;

    public Shield(ShieldType type, int power, int price, TechLevel minTechLevel, int chance) {
        super(EquipmentType.SHIELD, price, minTechLevel, chance);
        _type = type;
        _power = power;

        _charge = _power;
    }

    public Shield(Hashtable hash) {
        super(hash);
        _type = ShieldType.fromInt(getValueFromHash(hash, "_type", Integer.class));
        _power = getValueFromHash(hash, "_power", Integer.class);
        _charge = getValueFromHash(hash, "_charge", Integer.class);
    }

    public Equipment clone() {
        Shield shield = new Shield(_type, _power, getPrice(), getMinimumTechLevel(), getChance());
        shield.setCharge(Charge);
        return shield;
    }

    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.put("_type", _type);
        hash.put("_power", _power);
        hash.put("_charge", _charge);

        return hash;
    }

    public boolean isTypeEquals(Object type) {
        try {
            return (getType() == (ShieldType) type);
        } catch (Exception e) {
            Log.write("Ignored exception: " + e);
            return false;
        }
    }

    public String getName() {
        return Strings.ShieldNames[_type.castToInt()];
    }

    public int getPower() {
        return _power;
    }


    public ShieldType getType() {
        return _type;
    }

    public EquipmentSubType getSubType() {
        return getType();
    }

    public int getCharge() {
        return Charge;
    }

    public void setCharge(int charge) {
        Charge = charge;
    }


}
