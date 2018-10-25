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
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.TechLevel;
import spacetrader.guifacade.GuiEngine;
import spacetrader.game.enums.EquipmentSubType;
import spacetrader.util.Hashtable;

public abstract class Equipment extends STSerializableObject implements Cloneable {

    protected EquipmentType _equipType;
    protected int _price;
    protected TechLevel _minTech;
    protected int _chance;

    public Equipment(EquipmentType type, int price, TechLevel minTechLevel, int chance) {
        _equipType = type;
        _price = price;
        _minTech = minTechLevel;
        _chance = chance;
    }

    public Equipment(Hashtable hash)// : base(hash)
    {
        super();
        _equipType = EquipmentType.fromInt(getValueFromHash(hash, "_equipType", Integer.class));
        _price = getValueFromHash(hash, "_price", Integer.class);
        _minTech = TechLevel.fromInt(getValueFromHash(hash, "_minTech", Integer.class));
        _chance = getValueFromHash(hash, "_chance", Integer.class);
    }

    public abstract Equipment Clone();

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.put("_equipType", _equipType.castToInt());
        hash.put("_price", _price);
        hash.put("_minTech", _minTech.castToInt());
        hash.put("_chance", _chance);

        return hash;
    }

    @Override
    public String toString() {
        return getName();
    }

    public abstract boolean TypeEquals(Object type);

    private int BaseImageIndex() {
        int baseImageIndex = 0;

        switch (getEquipmentType()) {
            case GADGET:
                baseImageIndex = Strings.WeaponNames.length + Strings.ShieldNames.length;
                break;
            case SHIELD:
                baseImageIndex = Strings.WeaponNames.length;
                break;
            case WEAPON:
                // baseImageIndex should be 0
                break;
        }

        return baseImageIndex;
    }

    int Chance() {
        return _chance;
    }

    public EquipmentType getEquipmentType() {
        return _equipType;
    }

    final public Image getImage() {
        return GuiEngine.getImageProvider().getEquipmentImages().getImages()[BaseImageIndex()
                + getSubType().castToInt()];
    }

    private TechLevel MinimumTechLevel() {
        return _minTech;
    }

    public String getName() {
        return "Name not defined";
    }

    public int getPrice() {
        Commander cmdr = Game.getCurrentGame().getCommander();
        int price = 0;

        if (cmdr != null && cmdr.getCurrentSystem().getTechLevel().castToInt() >= MinimumTechLevel().castToInt())
            price = (_price * (100 - cmdr.getShip().getTrader())) / 100;

        return price;
    }

    public int getSellPrice() {
        return _price * 3 / 4;
    }

    public EquipmentSubType getSubType() {
        return null;
    }

    int getTransferPrice() {
        // The cost to transfer is 10% of the item worth. This is changed
        // from actually PAYING the buyer about 8% to transfer items. - JAF
        return getSellPrice() * 110 / 90;
    }
}
