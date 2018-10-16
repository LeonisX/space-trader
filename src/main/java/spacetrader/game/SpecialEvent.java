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

import spacetrader.game.enums.SpecialEventType;

public class SpecialEvent {

    public final static int MOON_COST = 500000;

    public final static int STATUS_ARTIFACT_NOT_STARTED = 0;
    public final static int STATUS_ARTIFACT_ON_BOARD = 1;
    public final static int STATUS_ARTIFACT_DONE = 2;

    public final static int STATUS_DRAGONFLY_NOT_STARTED = 0;
    public final static int STATUS_DRAGONFLY_FLY_BARATAS = 1;
    public final static int STATUS_DRAGONFLY_FLY_MELINA = 2;
    public final static int STATUS_DRAGONFLY_FLY_REGULAS = 3;
    public final static int STATUS_DRAGONFLY_FLY_ZALKON = 4;
    public final static int STATUS_DRAGONFLY_DESTROYED = 5;
    public final static int STATUS_DRAGONFLY_DONE = 6;

    public final static int STATUS_EXPERIMENT_NOT_STARTED = 0;
    public final static int STATUS_EXPERIMENT_STARTED = 1;
    public final static int STATUS_EXPERIMENT_DATE = 11;
    public final static int STATUS_EXPERIMENT_PERFORMED = 12;
    public final static int STATUS_EXPERIMENT_CANCELLED = 13;

    public final static int STATUS_GEMULON_NOT_STARTED = 0;
    public final static int STATUS_GEMULON_STARTED = 1;
    public final static int STATUS_GEMULON_DATE = 7;
    public final static int STATUS_GEMULON_TOO_LATE = 8;
    public final static int STATUS_GEMULON_FUEL = 9;
    public final static int STATUS_GEMULON_DONE = 10;

    public final static int STATUS_JAPORI_NOT_STARTED = 0;
    public final static int STATUS_JAPORI_IN_TRANSIT = 1;
    public final static int STATUS_JAPORI_DONE = 2;

    public final static int STATUS_JAREK_NOT_STARTED = 0;
    public final static int STATUS_JAREK_STARTED = 1;
    public final static int STATUS_JAREK_IMPATIENT = 11;
    public final static int STATUS_JAREK_DONE = 12;

    public final static int STATUS_MOON_NOT_STARTED = 0;
    public final static int STATUS_MOON_BOUGHT = 1;
    public final static int STATUS_MOON_DONE = 2;

    public final static int STATUS_PRINCESS_NOT_STARTED = 0;
    public final static int STATUS_PRINCESS_FLY_CENTAURI = 1;
    public final static int STATUS_PRINCESS_FLY_INTHARA = 2;
    public final static int STATUS_PRINCESS_FLY_QONOS = 3;
    public final static int STATUS_PRINCESS_RESCUED = 4;
    public final static int STATUS_PRINCESS_IMPATIENT = 14;
    public final static int STATUS_PRINCESS_RETURNED = 15;
    public final static int STATUS_PRINCESS_DONE = 16;

    public final static int STATUS_REACTOR_NOT_STARTED = 0;
    public final static int STATUS_REACTOR_FUEL_OK = 1;
    public final static int STATUS_REACTOR_DATE = 20;
    public final static int STATUS_REACTOR_DELIVERED = 21;
    public final static int STATUS_REACTOR_DONE = 22;

    public final static int STATUS_SCARAB_NOT_STARTED = 0;
    public final static int STATUS_SCARAB_HUNTING = 1;
    public final static int STATUS_SCARAB_DESTROYED = 2;
    public final static int STATUS_SCARAB_DONE = 3;

    public final static int STATUS_SCULPTURE_NOT_STARTED = 0;
    public final static int STATUS_SCULPTURE_IN_TRANSIT = 1;
    public final static int STATUS_SCULPTURE_DELIVERED = 2;
    public final static int STATUS_SCULPTURE_DONE = 3;

    //TODO unused???
    public final static int STATUS_SPACE_MONSTER_NOT_STARTED = 0;
    public final static int STATUS_SPACE_MONSTER_AT_ACAMAR = 1;
    public final static int STATUS_SPACE_MONSTER_DESTROYED = 2;
    public final static int STATUS_SPACE_MONSTER_DONE = 3;

    public final static int STATUS_WILD_NOT_STARTED = 0;
    public final static int STATUS_WILD_STARTED = 1;
    public final static int STATUS_WILD_IMPATIENT = 11;
    public final static int STATUS_WILD_DONE = 12;

    private SpecialEventType type;
    private int price;
    private int occurrence;
    private boolean messageOnly;

    public SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly) {
        this.type = type;
        this.price = price;
        this.occurrence = occurrence;
        this.messageOnly = messageOnly;
    }

    public StarSystem getLocation() {
        StarSystem location = null;
        StarSystem[] universe = Game.getCurrentGame().getUniverse();

        for (int i = 0; i < universe.length && location == null; i++)
            if (universe[i].getSpecialEventType() == getType())
                location = universe[i];

        return location;
    }

    public boolean isMessageOnly() {
        return messageOnly;
    }

    public int getOccurrence() {
        return occurrence;
    }

    public int getPrice() {
        return price;
    }

    public String getString() {
        return Strings.SpecialEventStrings[type.castToInt()];
    }

    public String getTitle() {
        return Strings.SpecialEventTitles[type.castToInt()];
    }

    public SpecialEventType getType() {
        return type;
    }
}
