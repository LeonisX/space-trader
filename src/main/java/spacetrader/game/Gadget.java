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

import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.GadgetType;
import spacetrader.game.enums.SkillType;
import spacetrader.game.enums.TechLevel;
import spacetrader.game.enums.EquipmentSubType;
import spacetrader.util.Hashtable;
import spacetrader.util.Log;

public class Gadget extends Equipment {

    private GadgetType type;
    private SkillType skillBonus;

    public Gadget(GadgetType type, SkillType skillBonus, int price, TechLevel minTechLevel, int chance) {
        super(EquipmentType.GADGET, price, minTechLevel, chance);
        this.type = type;
        this.skillBonus = skillBonus;
    }

    public Gadget(Hashtable hash) {
        super(hash);
        type = GadgetType.fromInt(getValueFromHash(hash, "_type", Integer.class));
        skillBonus = (getValueFromHash(hash, "_skillBonus", SkillType.NA, SkillType.class));
    }

    @Override
    public Equipment clone() {
        return new Gadget(type, skillBonus, getPrice(), getMinimumTechLevel(), getChance());
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_type", type.castToInt());
        hash.add("_skillBonus", skillBonus.castToInt());

        return hash;
    }

    @Override
    public boolean isTypeEquals(Object type) {
        boolean equal = false;

        try {
            if (getType() == type) {
                equal = true;
            }
        } catch (Exception e) {
            Log.write("Ignored Exception " + e);
        }

        return equal;
    }

    @Override
    public String getName() {
        return Strings.GadgetNames[type.castToInt()];
    }

    @Override
    public EquipmentSubType getSubType() {
        return getType();
    }

    public GadgetType getType() {
        return type;
    }

    public SkillType getSkillBonus() {
        return skillBonus;
    }

}
