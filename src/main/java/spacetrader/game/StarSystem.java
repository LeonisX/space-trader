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
import spacetrader.util.Functions;
import spacetrader.util.Hashtable;

import java.util.ArrayList;
import java.util.List;

public class StarSystem extends STSerializableObject {

    private StarSystemId id;
    private int x;
    private int y;
    private Size size;
    private TechLevel techLevel;
    private PoliticalSystemType politicalSystemType;
    private SystemPressure systemPressure;
    private SpecialResource specialResource;
    private SpecialEventType specialEventType = SpecialEventType.NA;
    private int[] tradeItems = new int[10];
    private int countDown = 0;
    private boolean visited = false;
    private ShipyardId shipyardId = ShipyardId.NA;

    // #endregion

    // #region Methods

    public StarSystem(StarSystemId id, int x, int y, Size size, TechLevel techLevel,
                      PoliticalSystemType politicalSystemType, SystemPressure systemPressure,
                      SpecialResource specialResource) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.size = size;
        this.techLevel = techLevel;
        this.politicalSystemType = politicalSystemType;
        this.systemPressure = systemPressure;
        this.specialResource = specialResource;

        initializeTradeItems();
    }

    public StarSystem(Hashtable hash) {
        super();
        id = StarSystemId.fromInt(getValueFromHash(hash, "_id", id, Integer.class));
        x = getValueFromHash(hash, "_x", x);
        y = getValueFromHash(hash, "_y", y);
        size = Size.fromInt(getValueFromHash(hash, "_size", size, Integer.class));
        techLevel = TechLevel.fromInt(getValueFromHash(hash, "_techLevel", techLevel, Integer.class));
        politicalSystemType = PoliticalSystemType.fromInt(getValueFromHash(hash, "_politicalSystemType", politicalSystemType, Integer.class));
        systemPressure = SystemPressure.fromInt(getValueFromHash(hash, "_systemPressure", systemPressure, Integer.class));
        specialResource = SpecialResource.fromInt(getValueFromHash(hash, "_specialResource", specialResource, Integer.class));
        specialEventType = SpecialEventType.fromInt(getValueFromHash(hash, "_specialEventType", specialEventType, Integer.class));
        tradeItems = getValueFromHash(hash, "_tradeItems", tradeItems, int[].class);
        countDown = getValueFromHash(hash, "_countDown", countDown);
        visited = getValueFromHash(hash, "_visited", visited);
        shipyardId = ShipyardId.fromInt(getValueFromHash(hash, "_shipyardId", shipyardId, Integer.class));
    }

    void initializeTradeItems() {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (!isItemTraded(Consts.TradeItems[i])) {
                tradeItems[i] = 0;
            } else {
                tradeItems[i] = (this.getSize().castToInt() + 1)
                        * (Functions.getRandom(9, 14) - Math.abs(Consts.TradeItems[i].getTechTopProduction().castToInt()
                        - this.getTechLevel().castToInt()));

                // Because of the enormous profits possible, there shouldn't be
                // too many robots or narcotics available.
                if (i >= TradeItemType.NARCOTICS.castToInt()) {
                    tradeItems[i] = ((tradeItems[i] * (5 - Game.getCurrentGame().getDifficulty().castToInt())) /
                            (6 - Game.getCurrentGame().getDifficulty().castToInt())) + 1;
                }

                if (this.getSpecialResource() == Consts.TradeItems[i].getResourceLowPrice()) {
                    tradeItems[i] = tradeItems[i] * 4 / 3;
                }

                if (this.getSpecialResource() == Consts.TradeItems[i].getResourceHighPrice()) {
                    tradeItems[i] = tradeItems[i] * 3 / 4;
                }

                if (this.getSystemPressure() == Consts.TradeItems[i].getPressurePriceHike()) {
                    tradeItems[i] = tradeItems[i] / 5;
                }

                tradeItems[i] = tradeItems[i] - Functions.getRandom(10) + Functions.getRandom(10);

                if (tradeItems[i] < 0) {
                    tradeItems[i] = 0;
                }
            }
        }
    }

    boolean isItemTraded(TradeItem item) {
        return ((item.getType() != TradeItemType.NARCOTICS || getPoliticalSystem().isDrugsOk())
                && (item.getType() != TradeItemType.FIREARMS
                || getPoliticalSystem().isFirearmsOk()) && getTechLevel().castToInt() >= item
                .getTechProduction().castToInt());
    }

    boolean itemUsed(TradeItem item) {
        return ((item.getType() != TradeItemType.NARCOTICS || getPoliticalSystem().isDrugsOk())
                && (item.getType() != TradeItemType.FIREARMS
                || getPoliticalSystem().isFirearmsOk()) && getTechLevel().castToInt() >= item
                .getTechUsage().castToInt());
    }

    @Override
    public Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_id", id.castToInt());
        hash.add("_x", x);
        hash.add("_y", y);
        hash.add("_size", size.castToInt());
        hash.add("_techLevel", techLevel.castToInt());
        hash.add("_politicalSystemType", politicalSystemType.castToInt());
        hash.add("_systemPressure", systemPressure.castToInt());
        hash.add("_specialResource", specialResource.castToInt());
        hash.add("_specialEventType", specialEventType.castToInt());
        hash.add("_tradeItems", tradeItems);
        hash.add("_countDown", countDown);
        hash.add("_visited", visited);
        hash.add("_shipyardId", shipyardId.castToInt());

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
                show = game.getCommander().getShip().isArtifactOnBoard();
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
                show = game.getCommander().getShip().isJarekOnBoard();
                break;
            case Moon:
                show = game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_NOT_STARTED
                        && game.getCommander().getWorth() > SpecialEvent.MOON_COST * .8;
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
                        && !game.getCommander().getShip().isPrincessOnBoard();
                break;
            case PrincessReturned:
                show = game.getCommander().getShip().isPrincessOnBoard();
                break;
            case Reactor:
                show = game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_NOT_STARTED
                        && game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                        && game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
                break;
            case ReactorDelivered:
                show = game.getCommander().getShip().isReactorOnBoard();
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
                show = game.getCommander().getShip().isWildOnBoard();
                break;
        }

        return show;
    }

    int getCountDown() {
        return countDown;
    }

    void setCountDown(int value) {
        countDown = value;
    }

    public boolean destIsOk() {
        Commander comm = Game.getCurrentGame().getCommander();
        return this != comm.getCurrentSystem()
                && (getDistance() <= comm.getShip().getFuel() || Functions.wormholeExists(comm.getCurrentSystem(), this));
    }

    private int getDistance() {
        return Functions.distance(this, Game.getCurrentGame().getCommander().getCurrentSystem());
    }

    public StarSystemId getId() {
        return id;
    }

    public List<CrewMember> getMercenariesForHire() {
        Commander cmdr = Game.getCurrentGame().getCommander();
        CrewMember[] mercs = Game.getCurrentGame().getMercenaries();
        ArrayList<CrewMember> forHire = new ArrayList<>(3);

        for (int i = 1; i < mercs.length; i++) {
            if (mercs[i].getCurrentSystem() == cmdr.getCurrentSystem() && !cmdr.getShip().hasCrew(mercs[i].getId())) {
                forHire.add(mercs[i]);
            }
        }

        return forHire;
    }

    public String getName() {
        return Strings.SystemNames[id.castToInt()];
    }

    public PoliticalSystem getPoliticalSystem() {
        return Consts.PoliticalSystems[politicalSystemType.castToInt()];
    }

    PoliticalSystemType getPoliticalSystemType() {
        return politicalSystemType;
    }

    void setPoliticalSystemType(PoliticalSystemType value) {
        politicalSystemType = value;
    }

    public Shipyard getShipyard() {
        return (shipyardId == ShipyardId.NA) ? null : Consts.Shipyards[shipyardId.castToInt()];
    }

    public ShipyardId getShipyardId() {
        return shipyardId;
    }

    public void setShipyardId(ShipyardId value) {
        shipyardId = value;
    }

    public Size getSize() {
        return size;
    }

    public SpecialEvent specialEvent() {
        return (specialEventType == SpecialEventType.NA ? null : Consts.SpecialEvents[specialEventType.castToInt()]);
    }

    SpecialEventType getSpecialEventType() {
        return specialEventType;
    }

    void setSpecialEventType(SpecialEventType value) {
        specialEventType = value;
    }

    public SpecialResource getSpecialResource() {
        return isVisited() ? specialResource : SpecialResource.NOTHING;
    }

    public SystemPressure getSystemPressure() {
        return systemPressure;
    }

    void setSystemPressure(SystemPressure value) {
        systemPressure = value;
    }

    public TechLevel getTechLevel() {
        return techLevel;
    }

    void setTechLevel(TechLevel value) {
        techLevel = value;
    }

    public int[] getTradeItems() {
        return tradeItems;
    }

    public boolean isVisited() {
        return visited;
    }

    void setVisited(boolean value) {
        visited = value;
    }

    public int getX() {
        return x;
    }

    public void setX(int value) {
        x = value;
    }

    public int getY() {
        return y;
    }

    public void setY(int value) {
        y = value;
    }
}
