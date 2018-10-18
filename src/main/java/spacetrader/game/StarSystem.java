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

import spacetrader.game.enums.*;
import spacetrader.util.Hashtable;

import java.util.ArrayList;

public class StarSystem extends STSerializableObject {

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
                      PoliticalSystemType politicalSystemType, SystemPressure systemPressure,
                      SpecialResource specialResource) {
        _id = id;
        _x = x;
        _y = y;
        _size = size;
        _techLevel = techLevel;
        _politicalSystemType = politicalSystemType;
        _systemPressure = systemPressure;
        _specialResource = specialResource;

        initializeTradeItems();
    }

    public StarSystem(Hashtable hash) {
        super();
        _id = StarSystemId.fromInt(GetValueFromHash(hash, "_id", _id, Integer.class));
        _x = GetValueFromHash(hash, "_x", _x);
        _y = GetValueFromHash(hash, "_y", _y);
        _size = Size.fromInt(GetValueFromHash(hash, "_size", _size, Integer.class));
        _techLevel = TechLevel.fromInt(GetValueFromHash(hash, "_techLevel", _techLevel, Integer.class));
        _politicalSystemType = PoliticalSystemType.fromInt(GetValueFromHash(hash, "_politicalSystemType", _politicalSystemType, Integer.class));
        _systemPressure = SystemPressure.fromInt(GetValueFromHash(hash, "_systemPressure", _systemPressure, Integer.class));
        _specialResource = SpecialResource.fromInt(GetValueFromHash(hash, "_specialResource", _specialResource, Integer.class));
        _specialEventType = SpecialEventType.fromInt(GetValueFromHash(hash, "_specialEventType", _specialEventType, Integer.class));
        _tradeItems = GetValueFromHash(hash, "_tradeItems", _tradeItems, int[].class);
        _countDown = GetValueFromHash(hash, "_countDown", _countDown);
        _visited = GetValueFromHash(hash, "_visited", _visited);
        _shipyardId = ShipyardId.fromInt(GetValueFromHash(hash, "_shipyardId", _shipyardId, Integer.class));
    }

    void initializeTradeItems() {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (!itemTraded(Consts.TradeItems[i])) {
                _tradeItems[i] = 0;
            } else {
                _tradeItems[i] = (this.size().castToInt() + 1)
                        * (Functions.GetRandom(9, 14) - Math.abs(Consts.TradeItems[i].TechTopProduction().castToInt()
                        - this.getTechLevel().castToInt()));

                // Because of the enormous profits possible, there shouldn't be
                // too many robots or narcotics available.
                if (i >= TradeItemType.Narcotics.castToInt())
                    _tradeItems[i] = ((_tradeItems[i] *
                            (5 - Game.getCurrentGame()
                                    .Difficulty()
                                    .castToInt())) /
                            (6 - Game.getCurrentGame().Difficulty().castToInt())) + 1;

                if (this.getSpecialResource() == Consts.TradeItems[i].ResourceLowPrice())
                    _tradeItems[i] = _tradeItems[i] * 4 / 3;

                if (this.getSpecialResource() == Consts.TradeItems[i].ResourceHighPrice())
                    _tradeItems[i] = _tradeItems[i] * 3 / 4;

                if (this.getSystemPressure() == Consts.TradeItems[i].PressurePriceHike())
                    _tradeItems[i] = _tradeItems[i] / 5;

                _tradeItems[i] = _tradeItems[i] - Functions.GetRandom(10) + Functions.GetRandom(10);

                if (_tradeItems[i] < 0)
                    _tradeItems[i] = 0;
            }
        }
    }

    public boolean itemTraded() {
        return itemTraded();
    }

    public boolean itemTraded(TradeItem item) {
        return ((item.Type() != TradeItemType.Narcotics || politicalSystem().DrugsOk())
                && (item.Type() != TradeItemType.Firearms || politicalSystem().FirearmsOk()) && getTechLevel().castToInt() >= item
                .TechProduction().castToInt());
    }

    boolean itemUsed(TradeItem item) {
        return ((item.Type() != TradeItemType.Narcotics || politicalSystem().DrugsOk())
                && (item.Type() != TradeItemType.Firearms || politicalSystem().FirearmsOk()) && getTechLevel().castToInt() >= item
                .TechUsage().castToInt());
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_id", _id.castToInt());
        hash.add("_x", _x);
        hash.add("_y", _y);
        hash.add("_size", _size.castToInt());
        hash.add("_techLevel", _techLevel.castToInt());
        hash.add("_politicalSystemType", _politicalSystemType.castToInt());
        hash.add("_systemPressure", _systemPressure.castToInt());
        hash.add("_specialResource", _specialResource.castToInt());
        hash.add("_specialEventType", _specialEventType.castToInt());
        hash.add("_tradeItems", _tradeItems);
        hash.add("_countDown", _countDown);
        hash.add("_visited", _visited);
        hash.add("_shipyardId", _shipyardId.castToInt());

        return hash;
    }

    public boolean showSpecialButton() {
        Game game = Game.getCurrentGame();
        boolean show = false;

        switch (getSpecialEventType()) {
            case Artifact:
            case Dragonfly:
            case Experiment:
            case Jarek:
                show = game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
                break;
            case ArtifactDelivery:
                show = game.getCommander().getShip().ArtifactOnBoard();
                break;
            case CargoForSale:
                show = game.getCommander().getShip().getFreeCargoBays() >= 3;
                break;
            case DragonflyBaratas:
                show = game.getQuestStatusDragonfly() > SpecialEvent.STATUS_DRAGONFLY_NOT_STARTED
                        && game.getQuestStatusDragonfly() < SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
                break;
            case DragonflyDestroyed:
                show = game.getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
                break;
            case DragonflyMelina:
                show = game.getQuestStatusDragonfly() > SpecialEvent.STATUS_DRAGONFLY_FLY_BARATAS
                        && game.getQuestStatusDragonfly() < SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
                break;
            case DragonflyRegulas:
                show = game.getQuestStatusDragonfly() > SpecialEvent.STATUS_DRAGONFLY_FLY_MELINA
                        && game.getQuestStatusDragonfly() < SpecialEvent.STATUS_DRAGONFLY_DESTROYED;
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
                show = game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious;
                break;
            case ExperimentStopped:
                show = game.getQuestStatusExperiment() > SpecialEvent.STATUS_EXPERIMENT_NOT_STARTED
                        && game.getQuestStatusExperiment() < SpecialEvent.STATUS_EXPERIMENT_PERFORMED;
                break;
            case GemulonRescued:
                show = game.getQuestStatusGemulon() > SpecialEvent.STATUS_GEMULON_NOT_STARTED
                        && game.getQuestStatusGemulon() < SpecialEvent.STATUS_GEMULON_TOO_LATE;
                break;
            case Japori:
                show = game.getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_NOT_STARTED
                        && game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
                break;
            case JaporiDelivery:
                show = game.getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT;
                break;
            case JarekGetsOut:
                show = game.getCommander().getShip().JarekOnBoard();
                break;
            case Moon:
                show = game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_NOT_STARTED
                        && game.getCommander().Worth() > SpecialEvent.MOON_COST * .8;
                break;
            case MoonRetirement:
                show = game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_BOUGHT;
                break;
            case Princess:
                show = game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful
                        && game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case PrincessCentauri:
                show = game.getQuestStatusPrincess() >= SpecialEvent.STATUS_PRINCESS_FLY_CENTAURI
                        && game.getQuestStatusPrincess() <= SpecialEvent.STATUS_PRINCESS_FLY_QONOS;
                break;
            case PrincessInthara:
                show = game.getQuestStatusPrincess() >= SpecialEvent.STATUS_PRINCESS_FLY_INTHARA
                        && game.getQuestStatusPrincess() <= SpecialEvent.STATUS_PRINCESS_FLY_QONOS;
                break;
            case PrincessQonos:
                show = game.getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_RESCUED
                        && !game.getCommander().getShip().PrincessOnBoard();
                break;
            case PrincessReturned:
                show = game.getCommander().getShip().PrincessOnBoard();
                break;
            case Reactor:
                show = game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_NOT_STARTED
                        && game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                        && game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case ReactorDelivered:
                show = game.getCommander().getShip().ReactorOnBoard();
                break;
            case Scarab:
                show = game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_NOT_STARTED
                        && game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case ScarabDestroyed:
            case ScarabUpgradeHull:
                show = game.getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DESTROYED;
                break;
            case Sculpture:
                show = game.getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_NOT_STARTED
                        && game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                        && game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case SculptureDelivered:
                show = game.getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_IN_TRANSIT;
                break;
            case SpaceMonsterKilled:
                show = game.getQuestStatusSpaceMonster() == SpecialEvent.STATUS_SPACE_MONSTER_DESTROYED;
                break;
            case TribbleBuyer:
                show = game.getCommander().getShip().getTribbles() > 0;
                break;
            case WildGetsOut:
                show = game.getCommander().getShip().WildOnBoard();
                break;
            default:
                break;
        }

        return show;
    }

    //TODO create normal getters/setters

    public int CountDown() {
        return _countDown;
    }

    public void CountDown(int value) {
        _countDown = value;
    }

    public boolean destOk() {
        Commander comm = Game.getCurrentGame().getCommander();
        return this != comm.getCurrentSystem()
                && (Distance() <= comm.getShip().getFuel() || Functions.wormholeExists(comm.getCurrentSystem(), this));
    }

    public int Distance() {
        return Functions.distance(this, Game.getCurrentGame().getCommander().getCurrentSystem());
    }

    public StarSystemId Id() {
        return _id;
    }

    public CrewMember[] mercenariesForHire() {
        Commander cmdr = Game.getCurrentGame().getCommander();
        CrewMember[] mercs = Game.getCurrentGame().Mercenaries();
        ArrayList forHire = new ArrayList(3);

        for (int i = 1; i < mercs.length; i++) {
            if (mercs[i].getCurrentSystem() == cmdr.getCurrentSystem() && !cmdr.getShip().HasCrew(mercs[i].Id()))
                forHire.add(mercs[i]);
        }

        return (CrewMember[]) forHire.toArray(new CrewMember[0]);
    }

    public String getName() {
        return Strings.SystemNames[_id.castToInt()];
    }

    public PoliticalSystem politicalSystem() {
        return Consts.PoliticalSystems[_politicalSystemType.castToInt()];
    }

    public PoliticalSystemType PoliticalSystemType() {
        return _politicalSystemType;
    }

    public void PoliticalSystemType(PoliticalSystemType value) {
        _politicalSystemType = value;
    }

    public Shipyard getShipyard() {
        //getShipyardId();
        return (_shipyardId == spacetrader.game.enums.ShipyardId.NA ? null : Consts.Shipyards[_shipyardId.castToInt()]);
    }

    public ShipyardId getShipyardId() {
        return _shipyardId;
    }

    public void setShipyardId(ShipyardId value) {
        _shipyardId = value;
    }

    public Size size() {
        return _size;
    }

    public SpecialEvent specialEvent() {
        //SpecialEventType();
        return (_specialEventType == SpecialEventType.NA ? null
                : Consts.SpecialEvents[_specialEventType.castToInt()]);
    }

    public SpecialEventType getSpecialEventType() {
        return _specialEventType;
    }

    public void setSpecialEventType(SpecialEventType value) {
        _specialEventType = value;
    }

    public SpecialResource getSpecialResource() {
        return isVisited() ? _specialResource : SpecialResource.Nothing;
    }

    public SystemPressure getSystemPressure() {
        return _systemPressure;
    }

    public void setSystemPressure(SystemPressure value) {
        _systemPressure = value;
    }

    public TechLevel getTechLevel() {
        return _techLevel;
    }

    public void setTechLevel(TechLevel value) {
        _techLevel = value;
    }

    public int[] getTradeItems() {
        return _tradeItems;
    }

    public boolean isVisited() {
        return _visited;
    }

    public void setVisited(boolean value) {
        _visited = value;
    }

    public int getX() {
        return _x;
    }

    public void setX(int value) {
        _x = value;
    }

    public int getY() {
        return _y;
    }

    public void setY(int value) {
        _y = value;
    }

    // #endregion
}
