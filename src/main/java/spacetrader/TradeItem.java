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
package spacetrader;

import spacetrader.enums.SpecialResource;
import spacetrader.enums.SystemPressure;
import spacetrader.enums.TechLevel;
import spacetrader.enums.TradeItemType;

public class TradeItem implements Comparable<TradeItem> // : IComparable
{
	// #region Member Declarations

	private final TradeItemType _type;
	private final TechLevel _techProduction; // Tech level needed for production
	private final TechLevel _techUsage; // Tech level needed to use
	private final TechLevel _techTopProduction; // Tech level which produces this item
	// the most
	private final int _piceLowTech; // Medium price at lowest tech level
	private final int _priceInc; // Price increase per tech level
	private final int _priceVariance; // Max percentage above or below calculated
	// price
	private final SystemPressure _pressurePriceHike; // Price increases considerably
	// when this event occurs
	private final SpecialResource _resourceLowPrice; // When this resource is
	// available, this trade item is
	// cheap
	private final SpecialResource _resourceHighPrice; // When this resource is
	// available, this trade item is
	// expensive
	private final int _minTradePrice; // Minimum price to buy/sell in orbit
	private final int _maxTradePrice; // Minimum price to buy/sell in orbit
	private final int _roundOff; // Roundoff price for trade in orbit

	// #endregion

	// #region Methods

	public TradeItem(TradeItemType type, TechLevel techProduction,
			TechLevel techUsage, TechLevel techTopProduction, int piceLowTech,
			int priceInc, int priceVariance, SystemPressure pressurePriceHike,
			SpecialResource resourceLowPrice,
			SpecialResource resourceHighPrice, int minTradePrice,
			int maxTradePrice, int roundOff)
	{
		_type = type;
		_techProduction = techProduction;
		_techUsage = techUsage;
		_techTopProduction = techTopProduction;
		_piceLowTech = piceLowTech;
		_priceInc = priceInc;
		_priceVariance = priceVariance;
		_pressurePriceHike = pressurePriceHike;
		_resourceLowPrice = resourceLowPrice;
		_resourceHighPrice = resourceHighPrice;
		_minTradePrice = minTradePrice;
		_maxTradePrice = maxTradePrice;
		_roundOff = roundOff;
	}

	public int compareTo(TradeItem o)
	{
		return CompareTo(o);
	}

	public int CompareTo(Object value)
	{
		int compared = 0;

		if (value == null)
			compared = 1;
		else
		{
			compared = ((Integer) PriceLowTech()).compareTo(((TradeItem) value)
					.PriceLowTech());
			if (compared == 0)
				compared = -((Integer) PriceInc())
						.compareTo(((TradeItem) value).PriceInc());
		}

		return compared;
	}

	public int StandardPrice(StarSystem target)
	{
		int price = 0;

		if (target.ItemUsed(this))
		{
			// Determine base price on techlevel of system
			price = PriceLowTech() + target.TechLevel().CastToInt() * PriceInc();

			// If a good is highly requested, increase the price
			if (target.PoliticalSystem().Wanted() == Type())
				price = price * 4 / 3;

			// High trader activity decreases prices
			price = price
					* (100 - 2 * target.PoliticalSystem().ActivityTraders().CastToInt())
					/ 100;

			// Large system = high production decreases prices
			price = price * (100 - target.Size().CastToInt()) / 100;

			// Special resources price adaptation
			if (target.SpecialResource() == ResourceLowPrice())
				price = price * 3 / 4;
			else if (target.SpecialResource() == ResourceHighPrice())
				price = price * 4 / 3;
		}

		return price;
	}

	// #endregion

	// #region Properties

	public boolean Illegal()
	{
		return Type() == TradeItemType.Firearms
				|| Type() == TradeItemType.Narcotics;

	}

	public int MaxTradePrice()
	{
		return _maxTradePrice;

	}

	public int MinTradePrice()
	{
		return _minTradePrice;
	}

	
public String Name()
	{
		return Strings.TradeItemNames[_type.CastToInt()];

	}

	


	public SystemPressure PressurePriceHike()
	{
		return _pressurePriceHike;

	}

	public int PriceInc()
	{
		return _priceInc;
	}

	public int PriceLowTech()
	{
		return _piceLowTech;

	}

	


	


	


	


	


	


	


	


	public int PriceVariance()
	{
		return _priceVariance;

	}

	


	public SpecialResource ResourceHighPrice()
	{
		return _resourceHighPrice;

	}

	


	public SpecialResource ResourceLowPrice()
	{
		return _resourceLowPrice;
	}

	public int RoundOff()
	{
		return _roundOff;

	}

	


	public TechLevel TechProduction()
	{
		return _techProduction;
	}

	


	public TechLevel TechTopProduction()
	{
		return _techTopProduction;

	}

	

	public TechLevel TechUsage()
	{
		return _techUsage;
	}

	


	public TradeItemType Type()
	{
		return _type;
	}
}
