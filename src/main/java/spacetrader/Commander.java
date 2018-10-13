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
//using System;
//using System.Collections;
//using System.Windows.Forms;
 *****************************************************************************/

package spacetrader;

import jwinforms.DialogResult;
import spacetrader.enums.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Hashtable;

public class Commander extends CrewMember
{
	// //#region Member Declarations

	private int _cash = 1000;
	private int _debt = 0;
	private int _killsPirate = 0;
	private int _killsPolice = 0;
	private int _killsTrader = 0;
	private int _policeRecordScore = 0;
	private int _reputationScore = 0;
	private int _days = 0;
	private boolean _insurance = false;
	private int _noclaim = 0;
	private Ship _ship = new Ship(ShipType.Gnat);
	private int[] _priceCargo = new int[10]; // Total price paid for trade goods

	// //#endregion

	// //#region Methods

	public Commander(CrewMember baseCrewMember)// : base(baseCrewMember)
	{
		super(baseCrewMember);
		// Start off with a crew of only the commander and a Pulse Laser.
		getShip().Crew()[0] = this;
		getShip().AddEquipment(Consts.Weapons[WeaponType.PulseLaser.CastToInt()]);
	}

	public Commander(Hashtable hash)// : base(hash)
	{
		super(hash);
		_cash = GetValueFromHash(hash, "_cash", _cash);
		_debt = GetValueFromHash(hash, "_debt", _debt);
		_killsPirate = GetValueFromHash(hash, "_killsPirate", _killsPirate);
		_killsPolice = GetValueFromHash(hash, "_killsPolice", _killsPolice);
		_killsTrader = GetValueFromHash(hash, "_killsTrader", _killsTrader);
		_policeRecordScore = GetValueFromHash(hash, "_policeRecordScore", _policeRecordScore);
		_reputationScore = GetValueFromHash(hash, "_reputationScore", _reputationScore);
		_days = GetValueFromHash(hash, "_days", _days);
		_insurance = GetValueFromHash(hash, "_insurance", _insurance);
		_noclaim = GetValueFromHash(hash, "_noclaim", _noclaim);
		_ship = new Ship(GetValueFromHash(hash, "_ship"/*,_ship*/, Hashtable.class));
		_priceCargo = GetValueFromHash(hash, "_priceCargo", _priceCargo, int[].class);

		Game.CurrentGame().Mercenaries()[CrewMemberId.Commander.CastToInt()] = this;
		Strings.CrewMemberNames[CrewMemberId.Commander.CastToInt()] = GetValueFromHash(hash, "_name",
				Strings.CrewMemberNames[CrewMemberId.Commander.CastToInt()]);
	}

	public void PayInterest()
	{
		if (getDebt() > 0)
		{
			int interest = Math.max(1, (int)(getDebt() * Consts.IntRate));
			if (getCash() > interest)
				setCash(getCash() - interest);
			else
			{
				setDebt(getDebt() + (interest - getCash()));
				setCash(0);
			}
		}
	}

	@Override
	public Hashtable Serialize()
	{
		Hashtable hash = super.Serialize();

		hash.add("_cash", _cash);
		hash.add("_debt", _debt);
		hash.add("_killsPirate", _killsPirate);
		hash.add("_killsPolice", _killsPolice);
		hash.add("_killsTrader", _killsTrader);
		hash.add("_policeRecordScore", _policeRecordScore);
		hash.add("_reputationScore", _reputationScore);
		hash.add("_days", _days);
		hash.add("_insurance", _insurance);
		hash.add("_noclaim", _noclaim);
		hash.add("_ship", _ship.Serialize());
		hash.add("_priceCargo", _priceCargo);
		hash.add("_name", Name());

		return hash;
	}

	public boolean TradeShip(ShipSpec specToBuy, int netPrice)
	{
		return TradeShip(specToBuy, netPrice, specToBuy.Name());
	}

	public boolean TradeShip(ShipSpec specToBuy, int netPrice, String newShipName)
	{
		boolean traded = false;

		if (netPrice > 0 && getDebt() > 0)
			GuiFacade.alert(AlertType.DebtNoBuy);
		else if (netPrice > CashToSpend())
			GuiFacade.alert(AlertType.ShipBuyIF);
		else if (specToBuy.getCrewQuarters() < getShip().SpecialCrew().length)
		{
			String passengers = getShip().SpecialCrew()[1].Name();
			if (getShip().SpecialCrew().length > 2)
				passengers += " and " + getShip().SpecialCrew()[2].Name();

			GuiFacade.alert(AlertType.ShipBuyPassengerQuarters, passengers);
		} else if (specToBuy.getCrewQuarters() < getShip().CrewCount())
			GuiFacade.alert(AlertType.ShipBuyCrewQuarters);
		else if (getShip().ReactorOnBoard())
			GuiFacade.alert(AlertType.ShipBuyReactor);
		else
		{
			Equipment[] special = new Equipment[] { Consts.Weapons[WeaponType.MorgansLaser.CastToInt()],
					Consts.Weapons[WeaponType.QuantumDistruptor.CastToInt()],
					Consts.Shields[ShieldType.Lightning.CastToInt()],
					Consts.Gadgets[GadgetType.FuelCompactor.CastToInt()],
					Consts.Gadgets[GadgetType.HiddenCargoBays.CastToInt()] };
			boolean[] add = new boolean[special.length];
			boolean addPod = false;
			int extraCost = 0;

			for (int i = 0; i < special.length; i++)
			{
				if (getShip().HasEquipment(special[i]))
				{
					if (specToBuy.Slots(special[i].EquipmentType()) == 0)
						GuiFacade.alert(AlertType.ShipBuyNoSlots, newShipName, special[i].Name(), Strings.EquipmentTypes[special[i].EquipmentType().CastToInt()]);
					else
					{
						extraCost += special[i].TransferPrice();
						add[i] = true;
					}
				}
			}

			if (getShip().getEscapePod())
			{
				addPod = true;
				extraCost += Consts.PodTransferCost;
			}

			if (netPrice + extraCost > CashToSpend())
				GuiFacade.alert(AlertType.ShipBuyIFTransfer);

			extraCost = 0;

			for (int i = 0; i < special.length; i++)
			{
				if (add[i])
				{
					if (netPrice + extraCost + special[i].TransferPrice() > CashToSpend())
						GuiFacade.alert(AlertType.ShipBuyNoTransfer, special[i].Name());
					else if (GuiFacade.alert(AlertType.ShipBuyTransfer, special[i].Name(), special[i].Name()
					.toLowerCase(), Functions.FormatNumber(special[i].TransferPrice())) == DialogResult.Yes)
						extraCost += special[i].TransferPrice();
					else
						add[i] = false;
				}
			}

			if (addPod)
			{
				if (netPrice + extraCost + Consts.PodTransferCost > CashToSpend())
					GuiFacade.alert(AlertType.ShipBuyNoTransfer, Strings.ShipInfoEscapePod);
				else if (GuiFacade.alert(AlertType.ShipBuyTransfer, Strings.ShipInfoEscapePod, Strings.ShipInfoEscapePod.toLowerCase(), Functions.FormatNumber(Consts.PodTransferCost)) == DialogResult.Yes)
					extraCost += Consts.PodTransferCost;
				else
					addPod = false;
			}

			if (GuiFacade.alert(AlertType.ShipBuyConfirm, getShip().Name(), newShipName, (add[0] || add[1]
			|| add[2] || addPod ? Strings.ShipBuyTransfer : "")) == DialogResult.Yes)
			{
				CrewMember[] oldCrew = getShip().Crew();

				setShip(new Ship(specToBuy.Type()));
				setCash(getCash() - (netPrice + extraCost));

				for (int i = 0; i < Math.min(oldCrew.length, getShip().Crew().length); i++)
					getShip().Crew()[i] = oldCrew[i];

				for (int i = 0; i < special.length; i++)
				{
					if (add[i])
						getShip().AddEquipment(special[i]);
				}

				if (addPod)
					getShip().setEscapePod(true);
				else if (getInsurance())
				{
					setInsurance(false);
					NoClaim(0);
				}

				traded = true;
			}
		}

		return traded;
	}

	// //#endregion

	// //#region Properties


	public int CashToSpend()
	{
		return _cash - (Game.CurrentGame().Options().getReserveMoney() ? CurrentCosts() : 0);
	}

	public int CurrentCosts()
	{
		return Game.CurrentGame().CurrentCosts();
	}

	public int NoClaim()
	{
		return _noclaim;
	}

	public void NoClaim(int value)
	{
		_noclaim = Math.max(0, Math.min(Consts.MaxNoClaim, value));
	}

	public int[] PriceCargo()
	{
		return _priceCargo;
	}

	public int Worth()
	{
		return getShip().getPrice() + _cash - _debt
				+ (Game.CurrentGame().getQuestStatusMoon() > 0 ? SpecialEvent.MoonCost : 0);
	}

	public void setShip(Ship ship)
	{
		_ship = ship;
	}

	public Ship getShip()
	{
		return _ship;
	}

	public void setReputationScore(int reputationScore)
	{
		_reputationScore = reputationScore;
	}

	public int getReputationScore()
	{
		return _reputationScore;
	}

	public void setPoliceRecordScore(int policeRecordScore)
	{
		_policeRecordScore = policeRecordScore;
	}

	/**
	 * @return
	 */
	public int getPoliceRecordScore()
	{
		return _policeRecordScore;
	}

	public void setKillsTrader(int killsTrader)
	{
		_killsTrader = killsTrader;
	}

	/**
	 * @return
	 */
	public int getKillsTrader()
	{
		return _killsTrader;
	}

	public void setKillsPolice(int killsPolice)
	{
		_killsPolice = killsPolice;
	}

	/**
	 * @return
	 */
	public int getKillsPolice()
	{
		return _killsPolice;
	}

	public void setKillsPirate(int killsPirate)
	{
		_killsPirate = killsPirate;
	}

	/**
	 * @return
	 */
	public int getKillsPirate()
	{
		return _killsPirate;
	}

	public void setInsurance(boolean insurance)
	{
		_insurance = insurance;
	}

	/**
	 * @return
	 */
	public boolean getInsurance()
	{
		return _insurance;
	}

	public void setDebt(int debt)
	{
		_debt = debt;
	}

	/**
	 * @return
	 */
	public int getDebt()
	{
		return _debt;
	}

	public void setDays(int days)
	{
		_days = days;
	}

	/**
	 * @return
	 */
	public int getDays()
	{
		return _days;
	}

	public void setCash(int cash)
	{
		_cash = cash;
	}

	/**
	 * @return
	 */
	public int getCash()
	{
		return _cash;
	}

	// //#endregion
}
