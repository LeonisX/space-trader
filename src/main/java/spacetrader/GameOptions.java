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
 * If you'd like a copy of the GNU General Public License, go to http:www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/
package spacetrader;

import spacetrader.util.Hashtable;

public class GameOptions extends STSerializableObject
{
//	 #region Member Declarations

	/**
	 * Automatically ignores pirates when it is safe to do so
	 */
	private boolean _alwaysIgnorePirates = false;
	/**
	 * Automatically ignores police when it is safe to do so
	 */
	private boolean _alwaysIgnorePolice = false;
	/**
	 * Automatically ignores Trade in Orbit when it is safe to do so
	 */
	private boolean _alwaysIgnoreTradeInOrbit = false;
	/**
	 * Automatically ignores traders when it is safe to do so
	 */
	private boolean _alwaysIgnoreTraders = true;
	/**
	 * Automatically get a full tank when arriving in a new system
	 */
	private boolean _autoFuel = false;
	/**
	 * Automatically get a full hull repair when arriving in a new system
	 */
	private boolean _autoRepair = false;
	/**
	 * Continuous attack/flee mode
	 */
	private boolean _continuousAttack = false;
	/**
	 * Continue attack on fleeing ship
	 */
	private boolean _continuousAttackFleeing = false;
	/**
	 * Disable opponents when possible (when you have disabling weapons and the opponent is a pirate, trader, or mantis)
	 */
	private boolean _disableOpponents = false;
	/**
	 * by default, ask each time someone buys a newspaper
	 */
	private boolean _newsAutoPay = false;
	/**
	 * by default, don't show newspaper
	 */
	private boolean _newsAutoShow = false;
	/**
	 * remind you every five days about outstanding loan balances
	 */
	private boolean _remindLoans = true;
	/**
	 * Keep enough money for insurance and mercenaries
	 */
	private boolean _reserveMoney = false;
	/**
	 * display range when tracking a system on Short Range Chart
	 */
	private boolean _showTrackedRange = true;
	/**
	 * Automatically stop tracking a system when you get to it?
	 */
	private boolean _trackAutoOff = true;
	/**
	 * Number of cargo bays to leave empty when buying goods
	 */
	private int _leaveEmpty = 0;

//	 #endregion

//	 #region Methods

	public GameOptions(boolean loadFromDefaults)
	{
		if (loadFromDefaults)
			LoadFromDefaults(false);
	}

	public GameOptions(Hashtable hash)
	{
		super(hash);
		_alwaysIgnorePirates = GetValueFromHash(hash, "_alwaysIgnorePirates", _alwaysIgnorePirates);
		_alwaysIgnorePolice = GetValueFromHash(hash, "_alwaysIgnorePolice", _alwaysIgnorePolice);
		_alwaysIgnoreTradeInOrbit = GetValueFromHash(hash, "_alwaysIgnoreTradeInOrbit",
				_alwaysIgnoreTradeInOrbit);
		_alwaysIgnoreTraders = GetValueFromHash(hash, "_alwaysIgnoreTraders", _alwaysIgnoreTraders);
		_autoFuel = GetValueFromHash(hash, "_autoFuel", _autoFuel);
		_autoRepair = GetValueFromHash(hash, "_autoRepair", _autoRepair);
		_continuousAttack = GetValueFromHash(hash, "_continuousAttack", _continuousAttack);
		_continuousAttackFleeing = GetValueFromHash(hash, "_continuousAttackFleeing", _continuousAttackFleeing);
		_disableOpponents = GetValueFromHash(hash, "_disableOpponents", _disableOpponents);
		_newsAutoPay = GetValueFromHash(hash, "_newsAutoPay", _newsAutoPay);
		_newsAutoShow = GetValueFromHash(hash, "_newsAutoShow", _newsAutoShow);
		_remindLoans = GetValueFromHash(hash, "_remindLoans", _remindLoans);
		_reserveMoney = GetValueFromHash(hash, "_reserveMoney", _reserveMoney);
		_showTrackedRange = GetValueFromHash(hash, "_showTrackedRange", _showTrackedRange);
		_trackAutoOff = GetValueFromHash(hash, "_trackAutoOff", _trackAutoOff);
		_leaveEmpty = GetValueFromHash(hash, "_leaveEmpty", _leaveEmpty);
	}

	public void CopyValues(GameOptions source)
	{
		setAlwaysIgnorePirates(source.getAlwaysIgnorePirates());
		setAlwaysIgnorePolice(source.getAlwaysIgnorePolice());
		setAlwaysIgnoreTradeInOrbit(source.getAlwaysIgnoreTradeInOrbit());
		setAlwaysIgnoreTraders(source.getAlwaysIgnoreTraders());
		setAutoFuel(source.getAutoFuel());
		setAutoRepair(source.getAutoRepair());
		setContinuousAttack(source.getContinuousAttack());
		setContinuousAttackFleeing(source.getContinuousAttackFleeing());
		setDisableOpponents(source.getDisableOpponents());
		setNewsAutoPay(source.getNewsAutoPay());
		setNewsAutoShow(source.getNewsAutoShow());
		setRemindLoans(source.getRemindLoans());
		setReserveMoney(source.getReserveMoney());
		setShowTrackedRange(source.getShowTrackedRange());
		setTrackAutoOff(source.getTrackAutoOff());
		setLeaveEmpty(source.getLeaveEmpty());
	}

	public void LoadFromDefaults(boolean errorIfFileNotFound)
	{

		GameOptions defaults = null;

		Object obj = Functions.LoadFile(Consts.DefaultSettingsFile, !errorIfFileNotFound);
		if (obj == null)
			defaults = new GameOptions(false);
		else
			defaults = new GameOptions((Hashtable)obj);

		CopyValues(defaults);
	}

	public void SaveAsDefaults()
	{
		Functions.SaveFile(Consts.DefaultSettingsFile, Serialize());
	}

	public @Override
	Hashtable Serialize()
	{
		Hashtable hash = super.Serialize();

		hash.add("_alwaysIgnorePirates", _alwaysIgnorePirates);
		hash.add("_alwaysIgnorePolice", _alwaysIgnorePolice);
		hash.add("_alwaysIgnoreTradeInOrbit", _alwaysIgnoreTradeInOrbit);
		hash.add("_alwaysIgnoreTraders", _alwaysIgnoreTraders);
		hash.add("_autoFuel", _autoFuel);
		hash.add("_autoRepair", _autoRepair);
		hash.add("_continuousAttack", _continuousAttack);
		hash.add("_continuousAttackFleeing", _continuousAttackFleeing);
		hash.add("_disableOpponents", _disableOpponents);
		hash.add("_newsAutoPay", _newsAutoPay);
		hash.add("_newsAutoShow", _newsAutoShow);
		hash.add("_remindLoans", _remindLoans);
		hash.add("_reserveMoney", _reserveMoney);
		hash.add("_showTrackedRange", _showTrackedRange);
		hash.add("_trackAutoOff", _trackAutoOff);
		hash.add("_leaveEmpty", _leaveEmpty);

		return hash;
	}

//	 #endregion

//	 #region Properties

	public boolean getAlwaysIgnorePirates()
	{
		return _alwaysIgnorePirates;
	}

	public void setAlwaysIgnorePirates(boolean value)
	{
		_alwaysIgnorePirates = value;
	}

	public boolean getAlwaysIgnorePolice()
	{
		return _alwaysIgnorePolice;
	}

	public void setAlwaysIgnorePolice(boolean value)
	{
		_alwaysIgnorePolice = value;
	}

	public boolean getAlwaysIgnoreTradeInOrbit()
	{
		return _alwaysIgnoreTradeInOrbit;
	}

	public void setAlwaysIgnoreTradeInOrbit(boolean value)
	{
		_alwaysIgnoreTradeInOrbit = value;
	}

	public boolean getAlwaysIgnoreTraders()
	{
		return _alwaysIgnoreTraders;
	}

	public void setAlwaysIgnoreTraders(boolean value)
	{
		_alwaysIgnoreTraders = value;
	}

	public boolean getAutoFuel()
	{
		return _autoFuel;
	}

	public void setAutoFuel(boolean value)
	{
		_autoFuel = value;
	}

	public boolean getAutoRepair()
	{
		return _autoRepair;
	}

	public void setAutoRepair(boolean value)
	{
		_autoRepair = value;
	}

	public boolean getContinuousAttack()
	{
		return _continuousAttack;
	}

	public void setContinuousAttack(boolean value)
	{
		_continuousAttack = value;
	}

	public boolean getContinuousAttackFleeing()
	{
		return _continuousAttackFleeing;
	}

	public void setContinuousAttackFleeing(boolean value)
	{
		_continuousAttackFleeing = value;
	}

	public boolean getDisableOpponents()
	{
		return _disableOpponents;
	}

	public void setDisableOpponents(boolean value)
	{
		_disableOpponents = value;
	}

	public int getLeaveEmpty()
	{
		return _leaveEmpty;
	}

	public void setLeaveEmpty(int value)
	{
		_leaveEmpty = value;
	}

	public boolean getNewsAutoPay()
	{
		return _newsAutoPay;
	}

	public void setNewsAutoPay(boolean value)
	{
		_newsAutoPay = value;
	}

	public boolean getNewsAutoShow()
	{
		return _newsAutoShow;
	}

	public void setNewsAutoShow(boolean value)
	{
		_newsAutoShow = value;
	}

	public boolean getRemindLoans()
	{
		return _remindLoans;
	}

	public void setRemindLoans(boolean value)
	{
		_remindLoans = value;
	}

	public boolean getReserveMoney()
	{
		return _reserveMoney;
	}

	public void setReserveMoney(boolean value)
	{
		_reserveMoney = value;
	}

	public boolean getShowTrackedRange()
	{
		return _showTrackedRange;
	}

	public void setShowTrackedRange(boolean value)
	{
		_showTrackedRange = value;
	}

	public boolean getTrackAutoOff()
	{
		return _trackAutoOff;
	}

	public void setTrackAutoOff(boolean value)
	{
		_trackAutoOff = value;
	}
//	 #endregion
}
