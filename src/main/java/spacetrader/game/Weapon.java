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

import spacetrader.game.enums.EquipmentSubType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.TechLevel;
import spacetrader.game.enums.WeaponType;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;

public class Weapon extends Equipment {

    private WeaponType type;
    private int power;
    private boolean disabling;

    public Weapon(WeaponType type, int power, boolean disabling, int price, TechLevel minTechLevel, int chance) {
        super(EquipmentType.WEAPON, price, minTechLevel, chance);
        this.type = type;
        this.power = power;
        this.disabling = disabling;
    }

    public Weapon(Hashtable hash) {
        super(hash);
        type = WeaponType.fromInt(getValueFromHash(hash, "_type", Integer.class));
        power = getValueFromHash(hash, "_power", Integer.class);
        disabling = getValueFromHash(hash, "_disabling", false);
    }

    public @Override
    Equipment clone() {
        return new Weapon(type, power, disabling, getPrice(), getMinimumTechLevel(), getChance());
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_type", type.castToInt());
        hash.add("_power", power);
        hash.add("_disabling", disabling);

        return hash;
    }

    @Override
    public boolean isTypeEquals(Object targetType) {
        boolean equal = false;

        try {
            if (targetType == type) {
                equal = true;
            }
        } catch (Exception e) {
            Log.write("Ignored exception " + e);
        }

        return equal;
    }

    public boolean isDisabling() {
        return disabling;
    }

    @Override
    public String getName() {
        return Strings.WeaponNames[type.castToInt()];
    }

    public int getPower() {
        return power;
    }

    @Override
    public EquipmentSubType getSubType() {
        return type;
    }

    public WeaponType getType() {
        return type;
    }
}
