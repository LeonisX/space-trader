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

import java.util.ArrayList;

import spacetrader.util.*;
import spacetrader.enums.*;

@SuppressWarnings("unchecked")
public class StarSystem extends STSerializableObject
{
	// #region Member Declarations

	private StarSystemId _id;
	private int _x;
	private int _y;
	private Size _size;
	private TechLevel _techLevel;
	private PoliticalSystemType _politicalSystemType;
	private SystemPressure _systemPressure;
	private SpecialResource _specialResource;
	private SpecialEventType _specialEventType = SpecialEventType.NA;
	private int[] _tradeItems = new int[10];
	private int _countDown = 0;
	private boolean _visited = false;
	private ShipyardId _shipyardId = ShipyardId.NA;

	// #endregion

	// #region Methods

	public StarSystem(StarSystemId id, int x, int y, Size size, TechLevel techLevel,
			PoliticalSystemType politicalSystemType, SystemPressure systemPressure, SpecialResource specialResource)
	{
		_id = id;
		_x = x;
		_y = y;
		_size = size;
		_techLevel = techLevel;
		_politicalSystemType = politicalSystemType;
		_systemPressure = systemPressure;
		_specialResource = specialResource;

		InitializeTradeItems();
	}

	public StarSystem(Hashtable hash)
	{
		super(hash);
		_id = StarSystemId.FromInt( GetValueFromHash(hash, "_id", _id, Integer.class));
		_x = GetValueFromHash(hash, "_x", _x);
		_y = GetValueFromHash(hash, "_y", _y);
		_size = Size.FromInt( GetValueFromHash(hash, "_size", _size, Integer.class));
		_techLevel = TechLevel.FromInt( GetValueFromHash(hash, "_techLevel", _techLevel, Integer.class));
		_politicalSystemType = PoliticalSystemType.FromInt( GetValueFromHash(hash, "_politicalSystemType", _politicalSystemType, Integer.class));
		_systemPressure = SystemPressure.FromInt( GetValueFromHash(hash, "_systemPressure", _systemPressure, Integer.class));
		_specialResource = SpecialResource.FromInt( GetValueFromHash(hash, "_specialResource", _specialResource, Integer.class));
		_specialEventType = SpecialEventType.FromInt( GetValueFromHash(hash, "_specialEventType", _specialEventType, Integer.class));
		_tradeItems = GetValueFromHash(hash, "_tradeItems", _tradeItems, int[].class);
		_countDown = GetValueFromHash(hash, "_countDown", _countDown);
		_visited = GetValueFromHash(hash, "_visited", _visited);
		_shipyardId = ShipyardId.FromInt( GetValueFromHash(hash, "_shipyardId", _shipyardId, Integer.class));
	}

	public void InitializeTradeItems()
	{
		for (int i = 0; i < Consts.TradeItems.length; i++)
		{
			if (!ItemTraded(Consts.TradeItems[i]))
			{
				_tradeItems[i] = 0;
			} else
			{
				_tradeItems[i] = (this.Size().CastToInt() + 1)
						* (Functions.GetRandom(9, 14) - Math.abs(Consts.TradeItems[i].TechTopProduction().CastToInt()
								- this.TechLevel().CastToInt()));

				// Because of the enormous profits possible, there shouldn't be
				// too many robots or narcotics available.
				if (i >= TradeItemType.Narcotics.CastToInt())
					_tradeItems[i] = ((_tradeItems[i] * 
							(5 - Game.CurrentGame()
									.Difficulty()
									.CastToInt())) /
							(6 - Game.CurrentGame().Difficulty().CastToInt())) + 1;

				if (this.SpecialResource() == Consts.TradeItems[i].ResourceLowPrice())
					_tradeItems[i] = _tradeItems[i] * 4 / 3;

				if (this.SpecialResource() == Consts.TradeItems[i].ResourceHighPrice())
					_tradeItems[i] = _tradeItems[i] * 3 / 4;

				if (this.SystemPressure() == Consts.TradeItems[i].PressurePriceHike())
					_tradeItems[i] = _tradeItems[i] / 5;

				_tradeItems[i] = _tradeItems[i] - Functions.GetRandom(10) + Functions.GetRandom(10);

				if (_tradeItems[i] < 0)
					_tradeItems[i] = 0;
			}
		}
	}

	public boolean ItemTraded(TradeItem item)
	{
		return ((item.Type() != TradeItemType.Narcotics || PoliticalSystem().DrugsOk())
				&& (item.Type() != TradeItemType.Firearms || PoliticalSystem().FirearmsOk()) && TechLevel().CastToInt() >= item
				.TechProduction().CastToInt());
	}

	public boolean ItemUsed(TradeItem item)
	{
		return ((item.Type() != TradeItemType.Narcotics || PoliticalSystem().DrugsOk())
				&& (item.Type() != TradeItemType.Firearms || PoliticalSystem().FirearmsOk()) && TechLevel().CastToInt() >= item
				.TechUsage().CastToInt());
	}

	public @Override
	Hashtable Serialize()
	{
		Hashtable hash = super.Serialize();

		hash.add("_id", _id.CastToInt());
		hash.add("_x", _x);
		hash.add("_y", _y);
		hash.add("_size", _size.CastToInt());
		hash.add("_techLevel", _techLevel.CastToInt());
		hash.add("_politicalSystemType", _politicalSystemType.CastToInt());
		hash.add("_systemPressure", _systemPressure.CastToInt());
		hash.add("_specialResource", _specialResource.CastToInt());
		hash.add("_specialEventType", _specialEventType.CastToInt());
		hash.add("_tradeItems", _tradeItems);
		hash.add("_countDown", _countDown);
		hash.add("_visited", _visited);
		hash.add("_shipyardId", _shipyardId.CastToInt());

		return hash;
	}

	public boolean ShowSpecialButton()
	{
		Game game = Game.CurrentGame();
		boolean show = false;

		switch (SpecialEventType())
		{
		case Artifact:
		case Dragonfly:
		case Experiment:
		case Jarek:
			show = game.Commander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
			break;
		case ArtifactDelivery:
			show = game.Commander().getShip().ArtifactOnBoard();
			break;
		case CargoForSale:
			show = game.Commander().getShip().FreeCargoBays() >= 3;
			break;
		case DragonflyBaratas:
			show = game.getQuestStatusDragonfly() > SpecialEvent.StatusDragonflyNotStarted
					&& game.getQuestStatusDragonfly() < SpecialEvent.StatusDragonflyDestroyed;
			break;
		case DragonflyDestroyed:
			show = game.getQuestStatusDragonfly() == SpecialEvent.StatusDragonflyDestroyed;
			break;
		case DragonflyMelina:
			show = game.getQuestStatusDragonfly() > SpecialEvent.StatusDragonflyFlyBaratas
					&& game.getQuestStatusDragonfly() < SpecialEvent.StatusDragonflyDestroyed;
			break;
		case DragonflyRegulas:
			show = game.getQuestStatusDragonfly() > SpecialEvent.StatusDragonflyFlyMelina
					&& game.getQuestStatusDragonfly() < SpecialEvent.StatusDragonflyDestroyed;
			break;
		case DragonflyShield:
		case ExperimentFailed:
		case Gemulon:
		case GemulonFuel:
		case GemulonInvaded:
		case Lottery:
		case ReactorLaser:
		case PrincessQuantum:
		case SculptureHiddenBays:
		case Skill:
		case SpaceMonster:
		case Tribble:
			show = true;
			break;
		case EraseRecord:
		case Wild:
			show = game.Commander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious;
			break;
		case ExperimentStopped:
			show = game.getQuestStatusExperiment() > SpecialEvent.StatusExperimentNotStarted
					&& game.getQuestStatusExperiment() < SpecialEvent.StatusExperimentPerformed;
			break;
		case GemulonRescued:
			show = game.getQuestStatusGemulon() > SpecialEvent.StatusGemulonNotStarted
					&& game.getQuestStatusGemulon() < SpecialEvent.StatusGemulonTooLate;
			break;
		case Japori:
			show = game.getQuestStatusJapori() == SpecialEvent.StatusJaporiNotStarted
					&& game.Commander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
			break;
		case JaporiDelivery:
			show = game.getQuestStatusJapori() == SpecialEvent.StatusJaporiInTransit;
			break;
		case JarekGetsOut:
			show = game.Commander().getShip().JarekOnBoard();
			break;
		case Moon:
			show = game.getQuestStatusMoon() == SpecialEvent.StatusMoonNotStarted
					&& game.Commander().Worth() > SpecialEvent.MoonCost * .8;
			break;
		case MoonRetirement:
			show = game.getQuestStatusMoon() == SpecialEvent.StatusMoonBought;
			break;
		case Princess:
			show = game.Commander().getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful
					&& game.Commander().getReputationScore() >= Consts.ReputationScoreAverage;
			break;
		case PrincessCentauri:
			show = game.getQuestStatusPrincess() >= SpecialEvent.StatusPrincessFlyCentauri
					&& game.getQuestStatusPrincess() <= SpecialEvent.StatusPrincessFlyQonos;
			break;
		case PrincessInthara:
			show = game.getQuestStatusPrincess() >= SpecialEvent.StatusPrincessFlyInthara
					&& game.getQuestStatusPrincess() <= SpecialEvent.StatusPrincessFlyQonos;
			break;
		case PrincessQonos:
			show = game.getQuestStatusPrincess() == SpecialEvent.StatusPrincessRescued
					&& !game.Commander().getShip().PrincessOnBoard();
			break;
		case PrincessReturned:
			show = game.Commander().getShip().PrincessOnBoard();
			break;
		case Reactor:
			show = game.getQuestStatusReactor() == SpecialEvent.StatusReactorNotStarted
					&& game.Commander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
					&& game.Commander().getReputationScore() >= Consts.ReputationScoreAverage;
			break;
		case ReactorDelivered:
			show = game.Commander().getShip().ReactorOnBoard();
			break;
		case Scarab:
			show = game.getQuestStatusScarab() == SpecialEvent.StatusScarabNotStarted
					&& game.Commander().getReputationScore() >= Consts.ReputationScoreAverage;
			break;
		case ScarabDestroyed:
		case ScarabUpgradeHull:
			show = game.getQuestStatusScarab() == SpecialEvent.StatusScarabDestroyed;
			break;
		case Sculpture:
			show = game.getQuestStatusSculpture() == SpecialEvent.StatusSculptureNotStarted
					&& game.Commander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
					&& game.Commander().getReputationScore() >= Consts.ReputationScoreAverage;
			break;
		case SculptureDelivered:
			show = game.getQuestStatusSculpture() == SpecialEvent.StatusSculptureInTransit;
			break;
		case SpaceMonsterKilled:
			show = game.getQuestStatusSpaceMonster() == SpecialEvent.StatusSpaceMonsterDestroyed;
			break;
		case TribbleBuyer:
			show = game.Commander().getShip().getTribbles() > 0;
			break;
		case WildGetsOut:
			show = game.Commander().getShip().WildOnBoard();
			break;
		default:
			break;
		}

		return show;
	}

	// #endregion

	// #region Properties

	public int CountDown()
	{
		return _countDown;
	}

	public void CountDown(int value)
	{
		_countDown = value;
	}

	public boolean DestOk()
	{
		Commander comm = Game.CurrentGame().Commander();
		return this != comm.getCurrentSystem()
				&& (Distance() <= comm.getShip().getFuel() || Functions.WormholeExists(comm.getCurrentSystem(), this));
	}

	public int Distance()
	{
		return Functions.Distance(this, Game.CurrentGame().Commander().getCurrentSystem());
	}

	public StarSystemId Id()
	{
		return _id;
	}

	public CrewMember[] MercenariesForHire()
	{
		Commander cmdr = Game.CurrentGame().Commander();
		CrewMember[] mercs = Game.CurrentGame().Mercenaries();
		ArrayList forHire = new ArrayList(3);

		for (int i = 1; i < mercs.length; i++)
		{
			if (mercs[i].getCurrentSystem() == cmdr.getCurrentSystem() && !cmdr.getShip().HasCrew(mercs[i].Id()))
				forHire.add(mercs[i]);
		}

		return (CrewMember[])forHire.toArray(new CrewMember[0]);
	}

	public String Name()
	{
		return Strings.SystemNames[_id.CastToInt()];
	}

	public PoliticalSystem PoliticalSystem()
	{
		return Consts.PoliticalSystems[_politicalSystemType.CastToInt()];
	}

	public PoliticalSystemType PoliticalSystemType()
	{
		return _politicalSystemType;
	}

	public void PoliticalSystemType(PoliticalSystemType value)
	{
		_politicalSystemType = value;
	}

	public Shipyard Shipyard()
	{
		ShipyardId();
		return (_shipyardId == ShipyardId.NA ? null : Consts.Shipyards[_shipyardId.CastToInt()]);
	}

	public ShipyardId ShipyardId()
	{
		return _shipyardId;
	}

	public void ShipyardId(ShipyardId value)
	{
		_shipyardId = value;
	}

	public Size Size()
	{
		return _size;
	}

	public SpecialEvent SpecialEvent()
	{
		SpecialEventType();
		return (_specialEventType == SpecialEventType.NA ? null
				: Consts.SpecialEvents[_specialEventType.CastToInt()]);
	}

	public SpecialEventType SpecialEventType()
	{
		return _specialEventType;
	}

	public void SpecialEventType(SpecialEventType value)
	{
		_specialEventType = value;
	}

	public SpecialResource SpecialResource()
	{
		return Visited() ? _specialResource : SpecialResource.Nothing;
	}

	public SystemPressure SystemPressure()
	{
		return _systemPressure;
	}

	public void SystemPressure(SystemPressure value)
	{
		_systemPressure = value;
	}

	public TechLevel TechLevel()
	{
		return _techLevel;
	}

	public void TechLevel(TechLevel value)
	{
		_techLevel = value;
	}

	public int[] TradeItems()
	{
		return _tradeItems;
	}

	public boolean Visited()
	{
		return _visited;
	}

	public void Visited(boolean value)
	{
		_visited = value;
	}

	public int X()
	{
		return _x;
	}

	public void X(int value)
	{
		_x = value;
	}

	public int Y()
	{
		return _y;
	}

	public void Y(int value)
	{
		_y = value;
	}

	// #endregion
}
