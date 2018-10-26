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

import spacetrader.game.enums.SpecialResource;
import spacetrader.game.enums.SystemPressure;
import spacetrader.game.enums.TechLevel;
import spacetrader.game.enums.TradeItemType;

public class TradeItem implements Comparable<TradeItem> {

    private final TradeItemType type;
    private final TechLevel techProduction; // Tech level needed for production
    private final TechLevel techUsage; // Tech level needed to use
    private final TechLevel techTopProduction; // Tech level which produces this item the most
    private final int priceLowTech; // Medium price at lowest tech level
    private final int priceInc; // Price increase per tech level
    private final int priceVariance; // Max percentage above or below calculated/ price
    private final SystemPressure pressurePriceHike; // Price increases considerably when this event occurs
    private final SpecialResource resourceLowPrice; // When this resource is available, this trade item is cheap
    private final SpecialResource resourceHighPrice; // When this resource is available, this trade item is expensive
    private final int minTradePrice; // Minimum price to buy/sell in orbit
    private final int maxTradePrice; // Minimum price to buy/sell in orbit
    private final int roundOff; // Roundoff price for trade in orbit

    TradeItem(TradeItemType type, TechLevel techProduction, TechLevel techUsage, TechLevel techTopProduction,
            int priceLowTech, int priceInc, int priceVariance, SystemPressure pressurePriceHike,
            SpecialResource resourceLowPrice, SpecialResource resourceHighPrice, int minTradePrice, int maxTradePrice,
            int roundOff) {
        this.type = type;
        this.techProduction = techProduction;
        this.techUsage = techUsage;
        this.techTopProduction = techTopProduction;
        this.priceLowTech = priceLowTech;
        this.priceInc = priceInc;
        this.priceVariance = priceVariance;
        this.pressurePriceHike = pressurePriceHike;
        this.resourceLowPrice = resourceLowPrice;
        this.resourceHighPrice = resourceHighPrice;
        this.minTradePrice = minTradePrice;
        this.maxTradePrice = maxTradePrice;
        this.roundOff = roundOff;
    }

    public int compareTo(TradeItem value) {
        int compared = 1;

        if (value != null) {
            compared = Integer.compare(priceLowTech, value.getPriceLowTech());
            if (compared == 0) {
                compared = -Integer.compare(priceInc, value.getPriceInc());
            }
        }

        return compared;
    }

    public int standardPrice(StarSystem target) {
        int price = 0;

        if (target.itemUsed(this)) {
            // Determine base price on techlevel of system
            price = priceLowTech + target.getTechLevel().castToInt() * priceInc;

            // If a good is highly requested, increase the price
            if (target.getPoliticalSystem().getWanted() == type) {
                price = price * 4 / 3;
            }

            // High trader activity decreases prices
            price = price * (100 - 2 * target.getPoliticalSystem().getActivityTraders().castToInt()) / 100;

            // Large system = high production decreases prices
            price = price * (100 - target.getSize().castToInt()) / 100;

            // Special resources price adaptation
            if (target.getSpecialResource() == resourceLowPrice) {
                price = price * 3 / 4;
            } else if (target.getSpecialResource() == resourceHighPrice) {
                price = price * 4 / 3;
            }
        }

        return price;
    }

    public boolean isIllegal() {
        return type == TradeItemType.FIREARMS || type == TradeItemType.NARCOTICS;
    }

    int getMaxTradePrice() {
        return maxTradePrice;

    }

    int getMinTradePrice() {
        return minTradePrice;
    }

    public String getName() {
        return Strings.TradeItemNames[type.castToInt()];

    }

    SystemPressure getPressurePriceHike() {
        return pressurePriceHike;

    }

     private int getPriceInc() {
        return priceInc;
    }

     private int getPriceLowTech() {
        return priceLowTech;

    }

     int getPriceVariance() {
        return priceVariance;

    }

     SpecialResource getResourceHighPrice() {
        return resourceHighPrice;

    }

     SpecialResource getResourceLowPrice() {
        return resourceLowPrice;
    }

     int getRoundOff() {
        return roundOff;
    }

     TechLevel getTechProduction() {
        return techProduction;
    }

     TechLevel getTechTopProduction() {
        return techTopProduction;

    }

     TechLevel getTechUsage() {
        return techUsage;
    }

    public TradeItemType getType() {
        return type;
    }
}
