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
// using System;
// using System.Collections;
// using System.Windows.Forms;
package spacetrader.game;

import spacetrader.controls.DialogResult;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.stub.ArrayList;
import spacetrader.util.CheatCode;
import spacetrader.util.Hashtable;
import spacetrader.util.Util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Game extends STSerializableObject implements SpaceTraderGame, SystemTracker, CurrentSystemMgr {

    private static Game _game;
    private final Commander _commander;
    @CheatCode
    private final GameCheats _cheats;
    // Game Data
    private StarSystem[] _universe;
    private int[] _wormholes = new int[6];
    private CrewMember[] _mercenaries = new CrewMember[Strings.CrewMemberNames.length];
    private Ship _dragonfly = new Ship(ShipType.Dragonfly);
    private Ship _scarab = new Ship(ShipType.Scarab);
    private Ship _scorpion = new Ship(ShipType.Scorpion);
    private Ship _spaceMonster = new Ship(ShipType.SpaceMonster);
    private Ship _opponent = new Ship(ShipType.Gnat);
    private boolean _opponentDisabled = false;
    private int _chanceOfTradeInOrbit = 100;
    private int _clicks = 0; // Distance from target system, 0 = arrived
    private boolean _raided = false; // True when the commander has been raided
    // during the trip
    private boolean _inspected = false; // True when the commander has been
    // inspected during the trip
    private boolean _tribbleMessage = false; // Is true if the Ship Yard on the
    // current system informed you
    // about the tribbles
    private boolean _arrivedViaWormhole = false; // flag to indicate whether
    // player arrived on current
    // planet via wormhole
    private boolean _paidForNewspaper = false; // once you buy a paper on a
    // system, you don't have to pay
    // again.
    private boolean _litterWarning = false; // Warning against littering has
    // been issued.
    private ArrayList _newsEvents = new ArrayList(30);
    // Current Selections
    private Difficulty _difficulty = spacetrader.game.enums.Difficulty.Normal; // Difficulty
    // level
    private boolean _autoSave = false;
    private boolean _easyEncounters = false;
    private GameEndType _endStatus = GameEndType.NA;
    private EncounterType _encounterType = spacetrader.game.enums.EncounterType.fromInt(0); // Type
    // of
    // current
    // encounter
    private StarSystemId _selectedSystemId = StarSystemId.NA; // Current system
    // on chart
    private StarSystemId _warpSystemId = StarSystemId.NA; // Target system for
    // warp
    private StarSystemId _trackedSystemId = StarSystemId.NA; // The short-range
    // chart will
    // display an
    // arrow towards
    // this system
    // if the value
    // is not null
    private boolean _targetWormhole = false; // Wormhole selected?
    private int[] _priceCargoBuy = new int[10];
    private int[] _priceCargoSell = new int[10];
    // Status of Quests
    private int _questStatusArtifact = 0; // 0 = not given yet, 1 = Artifact on
    // board, 2 = Artifact no longer on
    // board (either delivered or lost)
    private int _questStatusDragonfly = 0; // 0 = not available, 1 = Go to
    // Baratas, 2 = Go to Melina, 3 = Go
    // to Regulas, 4 = Go to Zalkon, 5 =
    // Dragonfly destroyed, 6 = Got
    // Shield
    private int _questStatusExperiment = 0; // 0 = not given yet, 1-11 = days
    // from start; 12 = performed, 13 =
    // cancelled
    private int _questStatusGemulon = 0; // 0 = not given yet, 1-7 = days from
    // start, 8 = too late, 9 = in time,
    // 10 = done
    private int _questStatusJapori = 0; // 0 = no disease, 1 = Go to Japori
    // (always at least 10 medicine
    // cannisters), 2 = Assignment finished
    // or canceled
    private int _questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12
    // = delivered
    private int _questStatusMoon = 0; // 0 = not bought, 1 = bought, 2 = claimed
    private int _questStatusPrincess = 0; // 0 = not available, 1 = Go to
    // Centauri, 2 = Go to Inthara, 3 =
    // Go to Qonos, 4 = Princess
    // Rescued, 5-14 = On Board, 15 =
    // Princess Returned, 16 = Got
    // Quantum Disruptor
    private int _questStatusReactor = 0; // 0 = not encountered, 1-20 = days of
    // mission (bays of fuel left = 10 -
    // (ReactorStatus / 2), 21 =
    // delivered, 22 = Done
    private int _questStatusScarab = 0; // 0 = not given yet, 1 = not destroyed,
    // 2 = destroyed - upgrade not
    // performed, 3 = destroyed - hull
    // upgrade performed
    private int _questStatusSculpture = 0; // 0 = not given yet, 1 = on board, 2
    // = delivered, 3 = done
    private int _questStatusSpaceMonster = 0; // 0 = not available, 1 = Space
    // monster is in Acamar system,
    // 2 = Space monster is
    // destroyed, 3 = Claimed reward
    private int _questStatusWild = 0; // 0 = not delivered, 1-11 = on board, 12
    // = delivered
    private int _fabricRipProbability = 0; // if Experiment = 12, this is the
    // probability of being warped to a
    // random planet.
    private boolean _justLootedMarie = false; // flag to indicate whether player
    // looted Marie Celeste
    private boolean _canSuperWarp = false; // Do you have the Portable
    // Singularity on board?
    private int _chanceOfVeryRareEncounter = 5;
    // Rare
    // encounters
    // not done yet.
    private ArrayList<VeryRareEncounter> _veryRareEncounters = new ArrayList(6); // Array of Very
    // Options
    private GameOptions _options = new GameOptions(true);
    // The rest of the member variables are not saved between games.
    private MainWindow _parentWin = null;
    private boolean _encounterContinueFleeing = false;
    private boolean _encounterContinueAttacking = false;
    private boolean _encounterCmdrFleeing = false;
    private boolean _encounterCmdrHit = false;
    private boolean _encounterOppFleeingPrev = false;
    private boolean _encounterOppFleeing = false;
    private boolean _encounterOppHit = false;

    // #endregion

    // #region Methods

    public Game(String name, Difficulty difficulty, int pilot, int fighter, int trader, int engineer,
                MainWindow parentWin) {
        _game = this;
        _parentWin = parentWin;
        _difficulty = difficulty;

        // Keep Generating a new universe until PlaceSpecialEvents and
        // PlaceShipyards return true,
        // indicating all special events and shipyards were placed.
        do
            GenerateUniverse();
        while (!(PlaceSpecialEvents() && PlaceShipyards()));

        _commander = InitializeCommander(name, new CrewMember(CrewMemberId.Commander, pilot, fighter, trader, engineer,
                StarSystemId.NA));
        GenerateCrewMemberList();

        CreateShips();

        CalculatePrices(getCommander().getCurrentSystem());

        ResetVeryRareEncounters();

        Difficulty();
        if (Difficulty().castToInt() < Difficulty.Normal.castToInt())
            getCommander().getCurrentSystem().setSpecialEventType(SpecialEventType.Lottery);

        _cheats = new GameCheats(this);
        if (name.length() == 0) {
            // TODO: JAF - DEBUG
            getCommander().setCash(1000000);
            _cheats.cheatMode = true;
            setEasyEncounters(true);
            setCanSuperWarp(true);
        }
    }

    public Game(Hashtable hash, MainWindow parentWin) {
        super();
        _game = this;
        _parentWin = parentWin;

        String version = GetValueFromHash(hash, "_version", String.class);
        if (version.compareTo(Consts.CurrentVersion) > 0)
            throw new FutureVersionException();

        _universe = (StarSystem[]) ArrayListToArray(GetValueFromHash(hash, "_universe", ArrayList.class), "StarSystem");
        _wormholes = GetValueFromHash(hash, "_wormholes", _wormholes, int[].class);
        _mercenaries = (CrewMember[]) ArrayListToArray(GetValueFromHash(hash, "_mercenaries", ArrayList.class),
                "CrewMember");
        _commander = new Commander(GetValueFromHash(hash, "_commander", Hashtable.class));
        _dragonfly = new Ship(GetValueFromHash(hash, "_dragonfly", _dragonfly.serialize(), Hashtable.class));
        _scarab = new Ship(GetValueFromHash(hash, "_scarab", _scarab.serialize(), Hashtable.class));
        _scorpion = new Ship(GetValueFromHash(hash, "_scorpion", _scorpion.serialize(), Hashtable.class));
        _spaceMonster = new Ship(GetValueFromHash(hash, "_spaceMonster", _spaceMonster.serialize(), Hashtable.class));
        _opponent = new Ship(GetValueFromHash(hash, "_opponent", _opponent.serialize(), Hashtable.class));
        _chanceOfTradeInOrbit = GetValueFromHash(hash, "_chanceOfTradeInOrbit", _chanceOfTradeInOrbit);
        _clicks = GetValueFromHash(hash, "_clicks", _clicks);
        _raided = GetValueFromHash(hash, "_raided", _raided);
        _inspected = GetValueFromHash(hash, "_inspected", _inspected);
        _tribbleMessage = GetValueFromHash(hash, "_tribbleMessage", _tribbleMessage);
        _arrivedViaWormhole = GetValueFromHash(hash, "_arrivedViaWormhole", _arrivedViaWormhole);
        _paidForNewspaper = GetValueFromHash(hash, "_paidForNewspaper", _paidForNewspaper);
        _litterWarning = GetValueFromHash(hash, "_litterWarning", _litterWarning);
        _newsEvents = new ArrayList(Arrays.asList((Integer[]) GetValueFromHash(hash, "_newsEvents", _newsEvents
                .toArray(new Integer[0]))));
        _difficulty = Difficulty.fromInt(GetValueFromHash(hash, "_difficulty", _difficulty, Integer.class));
        _cheats = new GameCheats(this);
        _cheats.cheatMode = GetValueFromHash(hash, "_cheatEnabled", _cheats.cheatMode);
        _autoSave = GetValueFromHash(hash, "_autoSave", _autoSave);
        _easyEncounters = GetValueFromHash(hash, "_easyEncounters", _easyEncounters);
        _endStatus = GameEndType.fromInt(GetValueFromHash(hash, "_endStatus", _endStatus, Integer.class));
        _encounterType = EncounterType.fromInt(GetValueFromHash(hash, "_encounterType", _encounterType, Integer.class));
        _selectedSystemId = StarSystemId.fromInt(GetValueFromHash(hash, "_selectedSystemId", _selectedSystemId,
                Integer.class));
        _warpSystemId = StarSystemId.fromInt(GetValueFromHash(hash, "_warpSystemId", _warpSystemId, Integer.class));
        _trackedSystemId = StarSystemId.fromInt(GetValueFromHash(hash, "_trackedSystemId", _trackedSystemId,
                Integer.class));
        _targetWormhole = GetValueFromHash(hash, "_targetWormhole", _targetWormhole);
        _priceCargoBuy = GetValueFromHash(hash, "_priceCargoBuy", _priceCargoBuy, int[].class);
        _priceCargoSell = GetValueFromHash(hash, "_priceCargoSell", _priceCargoSell, int[].class);
        _questStatusArtifact = GetValueFromHash(hash, "_questStatusArtifact", _questStatusArtifact);
        _questStatusDragonfly = GetValueFromHash(hash, "_questStatusDragonfly", _questStatusDragonfly);
        _questStatusExperiment = GetValueFromHash(hash, "_questStatusExperiment", _questStatusExperiment);
        _questStatusGemulon = GetValueFromHash(hash, "_questStatusGemulon", _questStatusGemulon);
        _questStatusJapori = GetValueFromHash(hash, "_questStatusJapori", _questStatusJapori);
        _questStatusJarek = GetValueFromHash(hash, "_questStatusJarek", _questStatusJarek);
        _questStatusMoon = GetValueFromHash(hash, "_questStatusMoon", _questStatusMoon);
        _questStatusPrincess = GetValueFromHash(hash, "_questStatusPrincess", _questStatusPrincess);
        _questStatusReactor = GetValueFromHash(hash, "_questStatusReactor", _questStatusReactor);
        _questStatusScarab = GetValueFromHash(hash, "_questStatusScarab", _questStatusScarab);
        _questStatusSculpture = GetValueFromHash(hash, "_questStatusSculpture", _questStatusSculpture);
        _questStatusSpaceMonster = GetValueFromHash(hash, "_questStatusSpaceMonster", _questStatusSpaceMonster);
        _questStatusWild = GetValueFromHash(hash, "_questStatusWild", _questStatusWild);
        _fabricRipProbability = GetValueFromHash(hash, "_fabricRipProbability", _fabricRipProbability);
        _justLootedMarie = GetValueFromHash(hash, "_justLootedMarie", _justLootedMarie);
        _canSuperWarp = GetValueFromHash(hash, "_canSuperWarp", _canSuperWarp);
        _chanceOfVeryRareEncounter = GetValueFromHash(hash, "_chanceOfVeryRareEncounter", _chanceOfVeryRareEncounter);
        _veryRareEncounters = new ArrayList(Arrays.asList(GetValueFromHash(hash, "_veryRareEncounters",
                _veryRareEncounters.toArray(new Integer[0]))));
        _options = new GameOptions(GetValueFromHash(hash, "_options", _options.serialize(), Hashtable.class));
    }

    public static Game currentGame() {
        return _game;
    }

    public static void currentGame(Game value) {
        _game = value;
    }

    private void Arrested() {
        int term = Math.max(30, -getCommander().getPoliceRecordScore());
        int fine = (1 + getCommander().Worth() * Math.min(80, -getCommander().getPoliceRecordScore()) / 50000) * 500;
        if (getCommander().getShip().WildOnBoard())
            fine = (int) (fine * 1.05);

        GuiFacade.alert(AlertType.EncounterArrested);

        GuiFacade.alert(AlertType.JailConvicted, Functions.multiples(term, Strings.TimeUnit), Functions.multiples(fine,
                Strings.MoneyUnit));

        if (getCommander().getShip().HasGadget(GadgetType.HiddenCargoBays)) {
            while (getCommander().getShip().HasGadget(GadgetType.HiddenCargoBays))
                getCommander().getShip().RemoveEquipment(EquipmentType.Gadget, GadgetType.HiddenCargoBays);

            GuiFacade.alert(AlertType.JailHiddenCargoBaysRemoved);
        }

        if (getCommander().getShip().ReactorOnBoard()) {
            GuiFacade.alert(AlertType.ReactorConfiscated);
            setQuestStatusReactor(SpecialEvent.StatusReactorNotStarted);
        }

        if (getCommander().getShip().SculptureOnBoard()) {
            GuiFacade.alert(AlertType.SculptureConfiscated);
            setQuestStatusSculpture(SpecialEvent.StatusSculptureNotStarted);
        }

        if (getCommander().getShip().WildOnBoard()) {
            GuiFacade.alert(AlertType.WildArrested);
            NewsAddEvent(NewsEvent.WildArrested);
            setQuestStatusWild(SpecialEvent.StatusWildNotStarted);
        }

        if (getCommander().getShip().AnyIllegalCargo()) {
            GuiFacade.alert(AlertType.JailIllegalGoodsImpounded);
            getCommander().getShip().RemoveIllegalGoods();
        }

        if (getCommander().getInsurance()) {
            GuiFacade.alert(AlertType.JailInsuranceLost);
            getCommander().setInsurance(false);
            getCommander().NoClaim(0);
        }

        if (getCommander().getShip().CrewCount() - getCommander().getShip().SpecialCrew().length > 1) {
            GuiFacade.alert(AlertType.JailMercenariesLeave);
            for (int i = 1; i < getCommander().getShip().Crew().length; i++)
                getCommander().getShip().Crew()[i] = null;
        }

        if (getCommander().getShip().JarekOnBoard()) {
            GuiFacade.alert(AlertType.JarekTakenHome);
            setQuestStatusJarek(SpecialEvent.StatusJarekNotStarted);
        }

        if (getCommander().getShip().PrincessOnBoard()) {
            GuiFacade.alert(AlertType.PrincessTakenHome);
            setQuestStatusPrincess(SpecialEvent.StatusPrincessNotStarted);
        }

        if (getQuestStatusJapori() == SpecialEvent.StatusJaporiInTransit) {
            GuiFacade.alert(AlertType.AntidoteTaken);
            setQuestStatusJapori(SpecialEvent.StatusJaporiDone);
        }

        if (getCommander().getCash() >= fine)
            getCommander().setCash(getCommander().getCash() - fine);
        else {
            getCommander().setCash(Math.max(0, getCommander().getCash() + getCommander().getShip().Worth(true) - fine));

            GuiFacade.alert(AlertType.JailShipSold);

            if (getCommander().getShip().getTribbles() > 0)
                GuiFacade.alert(AlertType.TribblesRemoved);

            GuiFacade.alert(AlertType.FleaBuilt);
            CreateFlea();
        }

        if (getCommander().getDebt() > 0) {
            int paydown = Math.min(getCommander().getCash(), getCommander().getDebt());
            getCommander().setDebt(getCommander().getDebt() - paydown);
            getCommander().setCash(getCommander().getCash() - paydown);

            if (getCommander().getDebt() > 0)
                for (int i = 0; i < term; i++)
                    getCommander().PayInterest();
        }

        getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
        IncDays(term);
    }

    private void Arrival() {
        getCommander().setCurrentSystem(getWarpSystem());
        getCommander().getCurrentSystem().Visited(true);
        setPaidForNewspaper(false);

        if (TrackedSystem() == getCommander().getCurrentSystem() && Options().getTrackAutoOff())
            setTrackedSystemId(StarSystemId.NA);

        ArrivalCheckReactor();
        ArrivalCheckTribbles();
        ArrivalCheckDebt();
        ArrivalPerformRepairs();
        ArrivalUpdatePressuresAndQuantities();
        ArrivalCheckEasterEgg();

        CalculatePrices(getCommander().getCurrentSystem());
        NewsAddEventsOnArrival();

        if (Options().getNewsAutoShow())
            showNewspaper();
    }

    private void ArrivalCheckDebt() {
        // Check for Large Debt - 06/30/01 SRA
        if (getCommander().getDebt() >= Consts.DebtWarning)
            GuiFacade.alert(AlertType.DebtWarning);
        else if (getCommander().getDebt() > 0 && Options().getRemindLoans() && getCommander().getDays() % 5 == 0)
            GuiFacade.alert(AlertType.DebtReminder, Functions.multiples(getCommander().getDebt(), Strings.MoneyUnit));
    }

    private void ArrivalCheckEasterEgg() {
        /* This Easter Egg gives the commander a Lighting Shield */
        if (getCommander().getCurrentSystem().Id() == StarSystemId.Og) {
            if (getCommander().getShip().FreeSlotsShield() <= 0)
                return;
            for (int i = 0; i < getCommander().getShip().getCargo().length; i++)
                if (getCommander().getShip().getCargo()[i] != 1)
                    return;

            GuiFacade.alert(AlertType.Egg);
            getCommander().getShip().AddEquipment(Consts.Shields[ShieldType.Lightning.castToInt()]);
            for (int i = 0; i < getCommander().getShip().getCargo().length; i++) {
                getCommander().getShip().getCargo()[i] = 0;
                getCommander().getPriceCargo()[i] = 0;
            }
        }
    }

    private void ArrivalCheckReactor() {
        if (getQuestStatusReactor() == SpecialEvent.StatusReactorDate) {
            GuiFacade.alert(AlertType.ReactorMeltdown);
            setQuestStatusReactor(SpecialEvent.StatusReactorNotStarted);
            if (getCommander().getShip().getEscapePod())
                EscapeWithPod();
            else {
                GuiFacade.alert(AlertType.ReactorDestroyed);

                throw new GameEndException(GameEndType.Killed);
            }
        } else {
            // Reactor warnings:
            // now they know the quest has a time finalraint!
            if (getQuestStatusReactor() == SpecialEvent.StatusReactorFuelOk + 1)
                GuiFacade.alert(AlertType.ReactorWarningFuel);
            else if (getQuestStatusReactor() == SpecialEvent.StatusReactorDate - 4)
                GuiFacade.alert(AlertType.ReactorWarningFuelGone);
            else if (getQuestStatusReactor() == SpecialEvent.StatusReactorDate - 2)
                GuiFacade.alert(AlertType.ReactorWarningTemp);
        }
    }

    private void ArrivalCheckTribbles() {
        Ship ship = getCommander().getShip();

        if (ship.getTribbles() > 0) {
            int previousTribbles = ship.getTribbles();
            int narc = TradeItemType.Narcotics.castToInt();
            int food = TradeItemType.Food.castToInt();

            if (ship.ReactorOnBoard()) {
                if (ship.getTribbles() < 20) {
                    ship.setTribbles(0);
                    GuiFacade.alert(AlertType.TribblesAllDied);
                } else {
                    ship.setTribbles(ship.getTribbles() / 2);
                    GuiFacade.alert(AlertType.TribblesHalfDied);
                }
            } else if (ship.getCargo()[narc] > 0) {
                int dead = Math.min(1 + Functions.GetRandom(3), ship.getCargo()[narc]);
                getCommander().getPriceCargo()[narc] = getCommander().getPriceCargo()[narc] * (ship.getCargo()[narc] - dead)
                        / ship.getCargo()[narc];
                ship.getCargo()[narc] -= dead;
                ship.getCargo()[TradeItemType.Furs.castToInt()] += dead;
                ship.setTribbles(ship.getTribbles()
                        - Math.min(dead * (Functions.GetRandom(5) + 98), ship.getTribbles() - 1));
                GuiFacade.alert(AlertType.TribblesMostDied);
            } else {
                if (ship.getCargo()[food] > 0 && ship.getTribbles() < Consts.MaxTribbles) {
                    int eaten = ship.getCargo()[food] - Functions.GetRandom(ship.getCargo()[food]);
                    getCommander().getPriceCargo()[food] -= getCommander().getPriceCargo()[food] * eaten / ship.getCargo()[food];
                    ship.getCargo()[food] -= eaten;
                    ship.setTribbles(ship.getTribbles() + (eaten * 100));
                    GuiFacade.alert(AlertType.TribblesAteFood);
                }

                if (ship.getTribbles() < Consts.MaxTribbles)
                    ship.setTribbles(ship.getTribbles()
                            + (1 + Functions.GetRandom(ship.getCargo()[food] > 0 ? ship.getTribbles()
                            : ship.getTribbles() / 2)));

                if (ship.getTribbles() > Consts.MaxTribbles)
                    ship.setTribbles(Consts.MaxTribbles);

                if ((previousTribbles < 100 && ship.getTribbles() >= 100)
                        || (previousTribbles < 1000 && ship.getTribbles() >= 1000)
                        || (previousTribbles < 10000 && ship.getTribbles() >= 10000)
                        || (previousTribbles < 50000 && ship.getTribbles() >= 50000)
                        || (previousTribbles < Consts.MaxTribbles && ship.getTribbles() == Consts.MaxTribbles)) {
                    String qty = ship.getTribbles() == Consts.MaxTribbles ? Strings.TribbleDangerousNumber : Functions
                            .formatNumber(ship.getTribbles());
                    GuiFacade.alert(AlertType.TribblesInspector, qty);
                }
            }

            setTribbleMessage(false);
        }
    }

    private void ArrivalPerformRepairs() {
        Ship ship = getCommander().getShip();

        if (ship.getHull() < ship.getHullStrength())
            ship.setHull(ship.getHull()
                    + Math.min(ship.getHullStrength() - ship.getHull(), Functions.GetRandom(ship.Engineer())));

        for (int i = 0; i < ship.Shields().length; ++i)
            if (ship.Shields()[i] != null)
                ship.Shields()[i].setCharge(ship.Shields()[i].Power());

        boolean fuelOk = true;
        int toAdd = ship.getFuelTanks() - ship.getFuel();
        if (Options().getAutoFuel() && toAdd > 0) {
            if (getCommander().getCash() >= toAdd * ship.getFuelCost()) {
                ship.setFuel(ship.getFuel() + toAdd);
                getCommander().setCash(getCommander().getCash() - (toAdd * ship.getFuelCost()));
            } else
                fuelOk = false;
        }

        boolean repairOk = true;
        toAdd = ship.getHullStrength() - ship.getHull();
        if (Options().getAutoRepair() && toAdd > 0) {
            if (getCommander().getCash() >= toAdd * ship.getRepairCost()) {
                ship.setHull(ship.getHull() + toAdd);
                getCommander().setCash(getCommander().getCash() - (toAdd * ship.getRepairCost()));
            } else
                repairOk = false;
        }

        if (!fuelOk && !repairOk)
            GuiFacade.alert(AlertType.ArrivalIFFuelRepairs);
        else if (!fuelOk)
            GuiFacade.alert(AlertType.ArrivalIFFuel);
        else if (!repairOk)
            GuiFacade.alert(AlertType.ArrivalIFRepairs);
    }

    private void ArrivalUpdatePressuresAndQuantities() {
        for (int i = 0; i < Universe().length; i++) {
            if (Functions.GetRandom(100) < 15)
                Universe()[i].setSystemPressure((SystemPressure
                        .fromInt(Universe()[i].getSystemPressure() == SystemPressure.None ? Functions.GetRandom(
                                SystemPressure.War.castToInt(), SystemPressure.Employment.castToInt() + 1)
                                : SystemPressure.None.castToInt())));

            if (Universe()[i].CountDown() > 0) {
                Universe()[i].CountDown(Universe()[i].CountDown() - 1);

                if (Universe()[i].CountDown() > CountDownStart())
                    Universe()[i].CountDown(CountDownStart());
                else if (Universe()[i].CountDown() <= 0)
                    Universe()[i].initializeTradeItems();
                else {
                    for (int j = 0; j < Consts.TradeItems.length; j++) {
                        if (getWarpSystem().itemTraded(Consts.TradeItems[j]))
                            Universe()[i].getTradeItems()[j] = Math.max(0, Universe()[i].getTradeItems()[j]
                                    + Functions.GetRandom(-4, 5));
                    }
                }
            }
        }
    }

    private void CalculatePrices(StarSystem system) {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            int price = Consts.TradeItems[i].standardPrice(system);

            if (price > 0) {
                // In case of a special status, adapt price accordingly
                if (Consts.TradeItems[i].PressurePriceHike() == system.getSystemPressure())
                    price = price * 3 / 2;

                // Randomize price a bit
                int variance = Math.min(Consts.TradeItems[i].PriceVariance(), price - 1);
                price = price + Functions.GetRandom(-variance, variance + 1);

                // Criminals have to pay off an intermediary
                if (getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious)
                    price = price * 90 / 100;
            }

            _priceCargoSell[i] = price;
        }

        RecalculateBuyPrices(system);
    }

    public boolean getTribbleMessage() {
        return _tribbleMessage;
    }

    public void setTribbleMessage(boolean tribbleMessage) {
        _tribbleMessage = tribbleMessage;
    }

    public StarSystemId getTrackedSystemId() {
        return _trackedSystemId;
    }

    public void setTrackedSystemId(StarSystemId trackedSystemId) {
        _trackedSystemId = trackedSystemId;
    }

    public boolean getRaided() {
        return _raided;
    }

    public void setRaided(boolean raided) {
        _raided = raided;
    }

    public int getQuestStatusWild() {
        return _questStatusWild;
    }

    public void setQuestStatusWild(int questStatusWild) {
        _questStatusWild = questStatusWild;
    }

    public int getQuestStatusSpaceMonster() {
        return _questStatusSpaceMonster;
    }

    public void setQuestStatusSpaceMonster(int questStatusSpaceMonster) {
        _questStatusSpaceMonster = questStatusSpaceMonster;
    }

    public int getQuestStatusSculpture() {
        return _questStatusSculpture;
    }

    public void setQuestStatusSculpture(int questStatusSculpture) {
        _questStatusSculpture = questStatusSculpture;
    }

    public int getQuestStatusScarab() {
        return _questStatusScarab;
    }

    public void setQuestStatusScarab(int questStatusScarab) {
        _questStatusScarab = questStatusScarab;
    }

    public int getQuestStatusReactor() {
        return _questStatusReactor;
    }

    public void setQuestStatusReactor(int questStatusReactor) {
        _questStatusReactor = questStatusReactor;
    }

    public int getQuestStatusPrincess() {
        return _questStatusPrincess;
    }

    public void setQuestStatusPrincess(int questStatusPrincess) {
        _questStatusPrincess = questStatusPrincess;
    }

    public int getQuestStatusMoon() {
        return _questStatusMoon;
    }

    public void setQuestStatusMoon(int questStatusMoon) {
        _questStatusMoon = questStatusMoon;
    }

    public int getQuestStatusJarek() {
        return _questStatusJarek;
    }

    public void setQuestStatusJarek(int questStatusJarek) {
        _questStatusJarek = questStatusJarek;
    }

    public int getQuestStatusJapori() {
        return _questStatusJapori;
    }

    public void setQuestStatusJapori(int questStatusJapori) {
        _questStatusJapori = questStatusJapori;
    }

    public int getQuestStatusGemulon() {
        return _questStatusGemulon;
    }

    public void setQuestStatusGemulon(int questStatusGemulon) {
        _questStatusGemulon = questStatusGemulon;
    }

    public int getQuestStatusExperiment() {
        return _questStatusExperiment;
    }

    public void setQuestStatusExperiment(int questStatusExperiment) {
        _questStatusExperiment = questStatusExperiment;
    }

    public int getQuestStatusDragonfly() {
        return _questStatusDragonfly;
    }

    public void setQuestStatusDragonfly(int questStatusDragonfly) {
        _questStatusDragonfly = questStatusDragonfly;
    }

    public int getQuestStatusArtifact() {
        return _questStatusArtifact;
    }

    public void setQuestStatusArtifact(int questStatusArtifact) {
        _questStatusArtifact = questStatusArtifact;
    }

    /**
     * todo Root of much evil.
     *
     * @return
     */
    public MainWindow getParentWindow() {
        return _parentWin;
    }

    public void setParentWindow(MainWindow parentWindow) {
        _parentWin = parentWindow;
    }

    private boolean getPaidForNewspaper() {
        return _paidForNewspaper;
    }

    private void setPaidForNewspaper(boolean paidForNewspaper) {
        _paidForNewspaper = paidForNewspaper;
    }

    public boolean setOpponentDisabled(boolean opponentDisabled) {
        _opponentDisabled = opponentDisabled;
        return opponentDisabled;
    }

    public boolean getOpponentDisabled() {
        return _opponentDisabled;
    }

    public Ship getOpponent() {
        return _opponent;
    }

    public void setOpponent(Ship opponent) {
        _opponent = opponent;
    }

    public boolean getLitterWarning() {
        return _litterWarning;
    }

    public void setLitterWarning(boolean litterWarning) {
        _litterWarning = litterWarning;
    }

    public boolean getJustLootedMarie() {
        return _justLootedMarie;
    }

    public void setJustLootedMarie(boolean justLootedMarie) {
        _justLootedMarie = justLootedMarie;
    }

    public boolean getInspected() {
        return _inspected;
    }

    public void setInspected(boolean inspected) {
        _inspected = inspected;
    }

    public int getFabricRipProbability() {
        return _fabricRipProbability;
    }

    public void setFabricRipProbability(int fabricRipProbability) {
        _fabricRipProbability = fabricRipProbability;
    }

    public GameEndType getEndStatus() {
        return _endStatus;
    }

    public void setEndStatus(GameEndType endStatus) {
        _endStatus = endStatus;
    }

    public EncounterType getEncounterType() {
        return _encounterType;
    }

    public void setEncounterType(EncounterType encounterType) {
        _encounterType = encounterType;
    }

    public boolean getEncounterOppHit() {
        return _encounterOppHit;
    }

    public void setEncounterOppHit(boolean encounterOppHit) {
        _encounterOppHit = encounterOppHit;
    }

    public boolean getEncounterOppFleeingPrev() {
        return _encounterOppFleeingPrev;
    }

    public void setEncounterOppFleeingPrev(boolean encounterOppFleeingPrev) {
        _encounterOppFleeingPrev = encounterOppFleeingPrev;
    }

    public boolean getEncounterOppFleeing() {
        return _encounterOppFleeing;
    }

    public void setEncounterOppFleeing(boolean encounterOppFleeing) {
        _encounterOppFleeing = encounterOppFleeing;
    }

    public boolean setEncounterContinueAttacking(boolean encounterContinueAttacking) {
        _encounterContinueAttacking = encounterContinueAttacking;
        return encounterContinueAttacking;
    }

    public boolean getEncounterContinueAttacking() {
        return _encounterContinueAttacking;
    }

    public boolean getEncounterCmdrHit() {
        return _encounterCmdrHit;
    }

    public void setEncounterCmdrHit(boolean encounterCmdrHit) {
        _encounterCmdrHit = encounterCmdrHit;
    }

    public boolean getEncounterCmdrFleeing() {
        return _encounterCmdrFleeing;
    }

    public void setEncounterCmdrFleeing(boolean encounterCmdrFleeing) {
        _encounterCmdrFleeing = encounterCmdrFleeing;
    }

    public boolean getEncounterContinueFleeing() {
        return _encounterContinueFleeing;
    }

    public void setEncounterContinueFleeing(boolean encounterContinueFleeing) {
        _encounterContinueFleeing = encounterContinueFleeing;
    }

    public boolean getEasyEncounters() {
        return _easyEncounters;
    }

    public void setEasyEncounters(boolean easyEncounters) {
        _easyEncounters = easyEncounters;
    }

    public int getClicks() {
        return _clicks;
    }

    public void setClicks(int clicks) {
        _clicks = clicks;
    }

    public GameCheats Cheats() {
        return _cheats;
    }

    public int getChanceOfVeryRareEncounter() {
        return _chanceOfVeryRareEncounter;
    }

    public void setChanceOfVeryRareEncounter(int chanceOfVeryRareEncounter) {
        _chanceOfVeryRareEncounter = chanceOfVeryRareEncounter;
    }

    public int getChanceOfTradeInOrbit() {
        return _chanceOfTradeInOrbit;
    }

    public void setChanceOfTradeInOrbit(int chanceOfTradeInOrbit) {
        _chanceOfTradeInOrbit = chanceOfTradeInOrbit;
    }

    public boolean getCanSuperWarp() {
        return _canSuperWarp;
    }

    public void setCanSuperWarp(boolean canSuperWarp) {
        _canSuperWarp = canSuperWarp;
    }

    public boolean getAutoSave() {
        return _autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        _autoSave = autoSave;
    }

    public boolean getArrivedViaWormhole() {
        return _arrivedViaWormhole;
    }

    public void setArrivedViaWormhole(boolean arrivedViaWormhole) {
        _arrivedViaWormhole = arrivedViaWormhole;
    }

    private void cargoBuy(int tradeItem, boolean max, CargoBuyOp op) {
        int freeBays = getCommander().getShip().FreeCargoBays();
        int[] items = null;
        int unitPrice = 0;
        int cashToSpend = getCommander().getCash();

        switch (op) {
            case BuySystem:
                freeBays = Math.max(0, getCommander().getShip().FreeCargoBays() - Options().getLeaveEmpty());
                items = getCommander().getCurrentSystem().getTradeItems();
                unitPrice = getPriceCargoBuy()[tradeItem];
                cashToSpend = getCommander().cashToSpend();
                break;
            case BuyTrader:
                items = getOpponent().getCargo();
                TradeItem item = Consts.TradeItems[tradeItem];
                int chance = item.Illegal() ? 45 : 10;
                double adj = Functions.GetRandom(100) < chance ? 1.1 : (item.Illegal() ? 0.8 : 0.9);
                unitPrice = Math.min(item.MaxTradePrice(), Math.max(item.MinTradePrice(), (int) Math
                        .round(getPriceCargoBuy()[tradeItem] * adj / item.RoundOff())
                        * item.RoundOff()));
                break;
            case Plunder:
                items = getOpponent().getCargo();
                break;
        }

        if (op == CargoBuyOp.BuySystem && getCommander().getDebt() > Consts.DebtTooLarge)
            GuiFacade.alert(AlertType.DebtTooLargeTrade);
        else if (op == CargoBuyOp.BuySystem && (items[tradeItem] <= 0 || unitPrice <= 0))
            GuiFacade.alert(AlertType.CargoNoneAvailable);
        else if (freeBays == 0)
            GuiFacade.alert(AlertType.CargoNoEmptyBays);
        else if (op != CargoBuyOp.Plunder && cashToSpend < unitPrice)
            GuiFacade.alert(AlertType.CargoIF);
        else {
            int qty = 0;
            int maxAmount = Math.min(freeBays, items[tradeItem]);
            if (op == CargoBuyOp.BuySystem)
                maxAmount = Math.min(maxAmount, getCommander().cashToSpend() / unitPrice);

            if (max)
                qty = maxAmount;
            else
                qty = GuiFacade.queryAmountAcquire(tradeItem, maxAmount, op);

            if (qty > 0) {
                int totalPrice = qty * unitPrice;

                getCommander().getShip().getCargo()[tradeItem] += qty;
                items[tradeItem] -= qty;
                getCommander().setCash(getCommander().getCash() - totalPrice);
                getCommander().getPriceCargo()[tradeItem] += totalPrice;
            }
        }
    }

    public void cargoBuySystem(int tradeItem, boolean max) {
        cargoBuy(tradeItem, max, CargoBuyOp.BuySystem);
    }

    public void CargoBuyTrader(int tradeItem) {
        cargoBuy(tradeItem, false, CargoBuyOp.BuyTrader);
    }

    public void CargoPlunder(int tradeItem, boolean max) {
        cargoBuy(tradeItem, max, CargoBuyOp.Plunder);
    }

    public void CargoDump(int tradeItem) {
        CargoSell(tradeItem, false, CargoSellOp.Dump);
    }

    public void CargoJettison(int tradeItem, boolean all) {
        CargoSell(tradeItem, all, CargoSellOp.Jettison);
    }

    public void CargoSellSystem(int tradeItem, boolean all) {
        CargoSell(tradeItem, all, CargoSellOp.SellSystem);
    }

    private void CargoSell(int tradeItem, boolean all, CargoSellOp op) {
        int qtyInHand = getCommander().getShip().getCargo()[tradeItem];
        int unitPrice;
        switch (op) {
            case SellSystem:
                unitPrice = getPriceCargoSell()[tradeItem];
                break;
            case SellTrader:
                TradeItem item = Consts.TradeItems[tradeItem];
                int chance = item.Illegal() ? 45 : 10;
                double adj = Functions.GetRandom(100) < chance ? (item.Illegal() ? 0.8 : 0.9) : 1.1;
                unitPrice = Math.min(item.MaxTradePrice(), Math.max(item.MinTradePrice(), (int) Math
                        .round(getPriceCargoSell()[tradeItem] * adj / item.RoundOff())
                        * item.RoundOff()));
                break;
            default:
                unitPrice = 0;
                break;
        }

        if (qtyInHand == 0)
            GuiFacade.alert(AlertType.CargoNoneToSell, Strings.CargoSellOps[op.castToInt()]);
        else if (op == CargoSellOp.SellSystem && unitPrice <= 0)
            GuiFacade.alert(AlertType.CargoNotInterested);
        else {
            if (op != CargoSellOp.Jettison || getLitterWarning()
                    || getCommander().getPoliceRecordScore() <= Consts.PoliceRecordScoreDubious
                    || GuiFacade.alert(AlertType.EncounterDumpWarning) == DialogResult.YES) {
                int unitCost = 0;
                int maxAmount = (op == CargoSellOp.SellTrader) ? Math.min(qtyInHand, getOpponent().FreeCargoBays())
                        : qtyInHand;
                if (op == CargoSellOp.Dump) {
                    unitCost = 5 * (Difficulty().castToInt() + 1);
                    maxAmount = Math.min(maxAmount, getCommander().cashToSpend() / unitCost);
                }
                int price = unitPrice > 0 ? unitPrice : -unitCost;

                int qty = 0;
                if (all)
                    qty = maxAmount;
                else {
                    qty = GuiFacade.queryAmountRelease(tradeItem, op, maxAmount, price);
                }

                if (qty > 0) {
                    int totalPrice = qty * price;

                    getCommander().getShip().getCargo()[tradeItem] -= qty;
                    getCommander().getPriceCargo()[tradeItem] = (getCommander().getPriceCargo()[tradeItem] * (qtyInHand - qty))
                            / qtyInHand;
                    getCommander().setCash(getCommander().getCash() + totalPrice);

                    if (op == CargoSellOp.Jettison) {
                        if (Functions.GetRandom(10) < Difficulty().castToInt() + 1) {
                            if (getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreDubious)
                                getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                            else
                                getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() - 1);

                            NewsAddEvent(NewsEvent.CaughtLittering);
                        }
                    }
                }
            }
        }
    }

    public void CargoSellTrader(int tradeItem) {
        CargoSell(tradeItem, false, CargoSellOp.SellTrader);
    }

    public void CreateFlea() {
        getCommander().setShip(new Ship(ShipType.Flea));
        getCommander().getShip().Crew()[0] = getCommander();
        getCommander().setInsurance(false);
        getCommander().NoClaim(0);
    }

    private void CreateShips() {
        Dragonfly().Crew()[0] = Mercenaries()[CrewMemberId.Dragonfly.castToInt()];
        Dragonfly().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);
        Dragonfly().AddEquipment(Consts.Weapons[WeaponType.PulseLaser.castToInt()]);
        Dragonfly().AddEquipment(Consts.Shields[ShieldType.Lightning.castToInt()]);
        Dragonfly().AddEquipment(Consts.Shields[ShieldType.Lightning.castToInt()]);
        Dragonfly().AddEquipment(Consts.Shields[ShieldType.Lightning.castToInt()]);
        Dragonfly().AddEquipment(Consts.Gadgets[GadgetType.AutoRepairSystem.castToInt()]);
        Dragonfly().AddEquipment(Consts.Gadgets[GadgetType.TargetingSystem.castToInt()]);

        Scarab().Crew()[0] = Mercenaries()[CrewMemberId.Scarab.castToInt()];
        Scarab().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);
        Scarab().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);

        Scorpion().Crew()[0] = Mercenaries()[CrewMemberId.Scorpion.castToInt()];
        Scorpion().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);
        Scorpion().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);
        Scorpion().AddEquipment(Consts.Shields[ShieldType.Reflective.castToInt()]);
        Scorpion().AddEquipment(Consts.Shields[ShieldType.Reflective.castToInt()]);
        Scorpion().AddEquipment(Consts.Gadgets[GadgetType.AutoRepairSystem.castToInt()]);
        Scorpion().AddEquipment(Consts.Gadgets[GadgetType.TargetingSystem.castToInt()]);

        SpaceMonster().Crew()[0] = Mercenaries()[CrewMemberId.SpaceMonster.castToInt()];
        SpaceMonster().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);
        SpaceMonster().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);
        SpaceMonster().AddEquipment(Consts.Weapons[WeaponType.MilitaryLaser.castToInt()]);
    }

    private boolean DetermineEncounter() {
        // If there is a specific encounter that needs to happen, it will,
        // otherwise we'll generate a random encounter.
        return DetermineNonRandomEncounter() || DetermineRandomEncounter();
    }

    private boolean DetermineNonRandomEncounter() {
        boolean showEncounter = false;

        // Encounter with space monster
        if (getClicks() == 1 && getWarpSystem().Id() == StarSystemId.Acamar
                && getQuestStatusSpaceMonster() == SpecialEvent.StatusSpaceMonsterAtAcamar) {
            setOpponent(SpaceMonster());
            setEncounterType(getCommander().getShip().Cloaked() ? spacetrader.game.enums.EncounterType.SpaceMonsterIgnore
                    : spacetrader.game.enums.EncounterType.SpaceMonsterAttack);
            showEncounter = true;
        }
        // Encounter with the stolen Scarab
        else if (getArrivedViaWormhole() && getClicks() == 20 && getWarpSystem().getSpecialEventType() != SpecialEventType.NA
                && getWarpSystem().specialEvent().Type() == SpecialEventType.ScarabDestroyed
                && getQuestStatusScarab() == SpecialEvent.StatusScarabHunting) {
            setOpponent(Scarab());
            setEncounterType(getCommander().getShip().Cloaked() ? spacetrader.game.enums.EncounterType.ScarabIgnore
                    : spacetrader.game.enums.EncounterType.ScarabAttack);
            showEncounter = true;
        }
        // Encounter with stolen Dragonfly
        else if (getClicks() == 1 && getWarpSystem().Id() == StarSystemId.Zalkon
                && getQuestStatusDragonfly() == SpecialEvent.StatusDragonflyFlyZalkon) {
            setOpponent(Dragonfly());
            setEncounterType(getCommander().getShip().Cloaked() ? spacetrader.game.enums.EncounterType.DragonflyIgnore
                    : spacetrader.game.enums.EncounterType.DragonflyAttack);
            showEncounter = true;
        }
        // Encounter with kidnappers in the Scorpion
        else if (getClicks() == 1 && getWarpSystem().Id() == StarSystemId.Qonos
                && getQuestStatusPrincess() == SpecialEvent.StatusPrincessFlyQonos) {
            setOpponent(Scorpion());
            setEncounterType(getCommander().getShip().Cloaked() ? spacetrader.game.enums.EncounterType.ScorpionIgnore
                    : spacetrader.game.enums.EncounterType.ScorpionAttack);
            showEncounter = true;
        }
        // ah, just when you thought you were gonna get away with it...
        else if (getClicks() == 1 && getJustLootedMarie()) {
            GenerateOpponent(OpponentType.Police);
            setEncounterType(spacetrader.game.enums.EncounterType.MarieCelestePolice);
            setJustLootedMarie(false);

            showEncounter = true;
        }

        return showEncounter;
    }

    private boolean DeterminePirateEncounter(boolean mantis) {
        boolean showEncounter = false;

        if (mantis) {
            GenerateOpponent(OpponentType.Mantis);
            setEncounterType(spacetrader.game.enums.EncounterType.PirateAttack);
        } else {
            GenerateOpponent(OpponentType.Pirate);

            // If you have a cloak, they don't see you
            if (getCommander().getShip().Cloaked())
                setEncounterType(spacetrader.game.enums.EncounterType.PirateIgnore);
                // Pirates will mostly attack, but they are cowardly: if your rep is
                // too high, they tend to flee
                // if Pirates are in a better ship, they won't flee, even if you
                // have a very scary
                // reputation.
            else if (getOpponent().Type().castToInt() > getCommander().getShip().Type().castToInt()
                    || getOpponent().Type().castToInt() >= ShipType.Grasshopper.castToInt()
                    || Functions.GetRandom(Consts.ReputationScoreElite) > (getCommander().getReputationScore() * 4)
                    / (1 + getOpponent().Type().castToInt()))
                setEncounterType(spacetrader.game.enums.EncounterType.PirateAttack);
            else
                setEncounterType(spacetrader.game.enums.EncounterType.PirateFlee);
        }

        // If they ignore you or flee and you can't see them, the encounter
        // doesn't take place
        // If you automatically don't want to confront someone who ignores you,
        // the
        // encounter may not take place
        if (getEncounterType() == spacetrader.game.enums.EncounterType.PirateAttack
                || !(getOpponent().Cloaked() || Options().getAlwaysIgnorePirates()))
            showEncounter = true;

        return showEncounter;
    }

    private boolean DeterminePoliceEncounter() {
        boolean showEncounter = false;

        GenerateOpponent(OpponentType.Police);

        // If you are cloaked, they don't see you
        setEncounterType(spacetrader.game.enums.EncounterType.PoliceIgnore);
        if (!getCommander().getShip().Cloaked()) {
            if (getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious) {
                // If you're a criminal, the police will tend to attack
                // JAF - fixed this; there was code that didn't do anything.
                // if you're suddenly stuck in a lousy ship, Police won't flee
                // even if you
                // have a fearsome reputation.
                if (getOpponent().WeaponStrength() > 0
                        && (getCommander().getReputationScore() < Consts.ReputationScoreAverage || Functions
                        .GetRandom(Consts.ReputationScoreElite) > (getCommander().getReputationScore() / (1 + getOpponent()
                        .Type().castToInt())))
                        || getOpponent().Type().castToInt() > getCommander().getShip().Type().castToInt()) {
                    if (getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreCriminal) {
                        getEncounterType();
                        setEncounterType(spacetrader.game.enums.EncounterType.PoliceSurrender);
                    } else
                        setEncounterType(spacetrader.game.enums.EncounterType.PoliceAttack);
                } else if (getOpponent().Cloaked())
                    setEncounterType(spacetrader.game.enums.EncounterType.PoliceIgnore);
                else
                    setEncounterType(spacetrader.game.enums.EncounterType.PoliceFlee);
            } else if (!getInspected()
                    && (getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreClean
                    || (getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreLawful && Functions
                    .GetRandom(12 - Difficulty().castToInt()) < 1) || (getCommander()
                    .getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful && Functions.GetRandom(40) == 0))) {
                // If you're reputation is dubious, the police will inspect you
                // If your record is clean, the police will inspect you with a
                // chance of 10% on Normal
                // If your record indicates you are a lawful trader, the chance
                // on inspection drops to 2.5%
                setEncounterType(spacetrader.game.enums.EncounterType.PoliceInspect);
                setInspected(true);
            }
        }

        // If they ignore you or flee and you can't see them, the encounter
        // doesn't take place
        // If you automatically don't want to confront someone who ignores you,
        // the
        // encounter may not take place. Otherwise it will - JAF
        if (getEncounterType() == spacetrader.game.enums.EncounterType.PoliceAttack
                || getEncounterType() == spacetrader.game.enums.EncounterType.PoliceInspect
                || !(getOpponent().Cloaked() || Options().getAlwaysIgnorePolice()))
            showEncounter = true;

        return showEncounter;
    }

    private boolean DetermineRandomEncounter() {
        boolean showEncounter = false;
        boolean mantis = false;
        boolean pirate = false;
        boolean police = false;
        boolean trader = false;

        if (getWarpSystem().Id() == StarSystemId.Gemulon && getQuestStatusGemulon() == SpecialEvent.StatusGemulonTooLate) {
            if (Functions.GetRandom(10) > 4)
                mantis = true;
        } else {
            // Check if it is time for an encounter
            int encounter = Functions.GetRandom(44 - (2 * Difficulty().castToInt()));
            int policeModifier = Math.max(1, 3 - PoliceRecord.GetPoliceRecordFromScore(
                    getCommander().getPoliceRecordScore()).Type().castToInt());

            // encounters are half as likely if you're in a flea.
            if (getCommander().getShip().Type() == ShipType.Flea)
                encounter *= 2;

            if (encounter < getWarpSystem().politicalSystem().activityPirates().castToInt())
                // When you are already raided, other pirates have little to
                // gain
                pirate = !getRaided();
            else if (encounter < getWarpSystem().politicalSystem().activityPirates().castToInt()
                    + getWarpSystem().politicalSystem().activityPolice().castToInt() * policeModifier)
                // policeModifier adapts itself to your criminal record: you'll
                // encounter more police if you are a hardened criminal.
                police = true;
            else if (encounter < getWarpSystem().politicalSystem().activityPirates().castToInt()
                    + getWarpSystem().politicalSystem().activityPolice().castToInt() * policeModifier
                    + getWarpSystem().politicalSystem().ActivityTraders().castToInt())
                trader = true;
            else if (getCommander().getShip().WildOnBoard() && getWarpSystem().Id() == StarSystemId.Kravat)
                // if you're coming in to Kravat & you have Wild onboard,
                // there'll be swarms o' cops.
                police = Functions.GetRandom(100) < 100 / Math.max(2, Math.min(4, 5 - Difficulty().castToInt()));
            else if (getCommander().getShip().ArtifactOnBoard() && Functions.GetRandom(20) <= 3)
                mantis = true;
        }

        if (police)
            showEncounter = DeterminePoliceEncounter();
        else if (pirate || mantis)
            showEncounter = DeterminePirateEncounter(mantis);
        else if (trader)
            showEncounter = DetermineTraderEncounter();
        else if (getCommander().getDays() > 10 && Functions.GetRandom(1000) < getChanceOfVeryRareEncounter()
                && VeryRareEncounters().size() > 0)
            showEncounter = DetermineVeryRareEncounter();

        return showEncounter;
    }

    private boolean DetermineTraderEncounter() {
        boolean showEncounter = false;

        GenerateOpponent(OpponentType.Trader);

        // If you are cloaked, they don't see you
        setEncounterType(spacetrader.game.enums.EncounterType.TraderIgnore);
        if (!getCommander().getShip().Cloaked()) {
            // If you're a criminal, traders tend to flee if you've got at least
            // some reputation
            if (!getCommander().getShip().Cloaked()
                    && getCommander().getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                    && Functions.GetRandom(Consts.ReputationScoreElite) <= (getCommander().getReputationScore() * 10)
                    / (1 + getOpponent().Type().castToInt()))
                setEncounterType(spacetrader.game.enums.EncounterType.TraderFlee);
                // Will there be trade in orbit?
            else if (Functions.GetRandom(1000) < getChanceOfTradeInOrbit()) {
                if (getCommander().getShip().FreeCargoBays() > 0 && getOpponent().HasTradeableItems())
                    setEncounterType(spacetrader.game.enums.EncounterType.TraderSell);
                    // we fudge on whether the trader has capacity to carry the
                    // stuff he's buying.
                else if (getCommander().getShip().HasTradeableItems())
                    setEncounterType(spacetrader.game.enums.EncounterType.TraderBuy);
            }
        }

        // If they ignore you or flee and you can't see them, the encounter
        // doesn't take place
        // If you automatically don't want to confront someone who ignores you,
        // the
        // encounter may not take place; otherwise it will.
        if (!getOpponent().Cloaked()
                && !(Options().getAlwaysIgnoreTraders() && (getEncounterType() == spacetrader.game.enums.EncounterType.TraderIgnore || getEncounterType() == spacetrader.game.enums.EncounterType.TraderFlee))
                && !((getEncounterType() == spacetrader.game.enums.EncounterType.TraderBuy || getEncounterType() == spacetrader.game.enums.EncounterType.TraderSell) && Options()
                .getAlwaysIgnoreTradeInOrbit()))
            showEncounter = true;

        return showEncounter;
    }

    private boolean DetermineVeryRareEncounter() {
        boolean showEncounter = false;

        // Very Rare Random Events:
        // 1. Encounter the abandoned Marie Celeste, which you may loot.
        // 2. Captain Ahab will trade your Reflective Shield for skill points in
        // Piloting.
        // 3. Captain Conrad will trade your Military Laser for skill points in
        // Engineering.
        // 4. Captain Huie will trade your Military Laser for points in Trading.
        // 5. Encounter an out-of-date bottle of Captain Marmoset's Skill Tonic.
        // This
        // will affect skills depending on game difficulty level.
        // 6. Encounter a good bottle of Captain Marmoset's Skill Tonic, which
        // will invoke
        // IncreaseRandomSkill one or two times, depending on game difficulty.
        switch (VeryRareEncounters().get(Functions.GetRandom(VeryRareEncounters().size()))) {
            case MarieCeleste:
                // Marie Celeste cannot be at Acamar, Qonos, or Zalkon as it may
                // cause problems with the
                // Space Monster, Scorpion, or Dragonfly
                if (getClicks() > 1 && getCommander().getCurrentSystemId() != StarSystemId.Acamar
                        && getCommander().getCurrentSystemId() != StarSystemId.Zalkon
                        && getCommander().getCurrentSystemId() != StarSystemId.Qonos) {
                    VeryRareEncounters().remove(VeryRareEncounter.MarieCeleste);
                    setEncounterType(spacetrader.game.enums.EncounterType.MarieCeleste);
                    GenerateOpponent(OpponentType.Trader);
                    for (int i = 0; i < getOpponent().getCargo().length; i++)
                        getOpponent().getCargo()[i] = 0;
                    getOpponent().getCargo()[TradeItemType.Narcotics.castToInt()] = Math.min(getOpponent().CargoBays(), 5);

                    showEncounter = true;
                }
                break;
            case CaptainAhab:
                if (getCommander().getShip().HasShield(ShieldType.Reflective) && getCommander().Pilot() < 10
                        && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    VeryRareEncounters().remove(VeryRareEncounter.CaptainAhab);
                    getEncounterType();
                    setEncounterType(spacetrader.game.enums.EncounterType.CaptainAhab);
                    GenerateOpponent(OpponentType.FamousCaptain);

                    showEncounter = true;
                }
                break;
            case CaptainConrad:
                if (getCommander().getShip().HasWeapon(WeaponType.MilitaryLaser, true) && getCommander().Engineer() < 10
                        && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    VeryRareEncounters().remove(VeryRareEncounter.CaptainConrad);
                    getEncounterType();
                    setEncounterType(spacetrader.game.enums.EncounterType.CaptainConrad);
                    GenerateOpponent(OpponentType.FamousCaptain);

                    showEncounter = true;
                }
                break;
            case CaptainHuie:
                if (getCommander().getShip().HasWeapon(WeaponType.MilitaryLaser, true) && getCommander().Trader() < 10
                        && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    VeryRareEncounters().remove(VeryRareEncounter.CaptainHuie);
                    getEncounterType();
                    setEncounterType(spacetrader.game.enums.EncounterType.CaptainHuie);
                    GenerateOpponent(OpponentType.FamousCaptain);

                    showEncounter = true;
                }
                break;
            case BottleOld:
                VeryRareEncounters().remove(VeryRareEncounter.BottleOld);
                setEncounterType(spacetrader.game.enums.EncounterType.BottleOld);
                GenerateOpponent(OpponentType.Bottle);

                showEncounter = true;
                break;
            case BottleGood:
                VeryRareEncounters().remove(VeryRareEncounter.BottleGood);
                setEncounterType(spacetrader.game.enums.EncounterType.BottleGood);
                GenerateOpponent(OpponentType.Bottle);

                showEncounter = true;
                break;
        }

        return showEncounter;
    }

    public void EncounterBegin() {
        // Set up the encounter variables.
        setEncounterContinueFleeing(setEncounterContinueAttacking(setOpponentDisabled(false)));
    }

    private void EncounterDefeatDragonfly() {
        getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
        setQuestStatusDragonfly(SpecialEvent.StatusDragonflyDestroyed);
    }

    private void EncounterDefeatScarab() {
        getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
        setQuestStatusScarab(SpecialEvent.StatusScarabDestroyed);
    }

    private void EncounterDefeatScorpion() {
        getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
        setQuestStatusPrincess(SpecialEvent.StatusPrincessRescued);
    }

    public void EncounterDrink() {
        if (GuiFacade.alert(AlertType.EncounterDrinkContents) == DialogResult.YES) {
            if (getEncounterType() == spacetrader.game.enums.EncounterType.BottleGood) {
                // two points if you're on beginner-normal, one otherwise
                getCommander().IncreaseRandomSkill();
                if (Difficulty().castToInt() <= spacetrader.game.enums.Difficulty.Normal.castToInt())
                    getCommander().IncreaseRandomSkill();
                GuiFacade.alert(AlertType.EncounterTonicConsumedGood);
            } else {
                getCommander().TonicTweakRandomSkill();
                GuiFacade.alert(AlertType.EncounterTonicConsumedStrange);
            }
        }
    }

    public EncounterResult EncounterExecuteAction() {
        EncounterResult result = EncounterResult.Continue;
        int prevCmdrHull = getCommander().getShip().getHull();
        int prevOppHull = getOpponent().getHull();

        setEncounterCmdrHit(false);
        setEncounterOppHit(false);
        setEncounterOppFleeingPrev(getEncounterOppFleeing());
        setEncounterOppFleeing(false);

        // Fire shots
        switch (getEncounterType()) {
            case DragonflyAttack:
            case FamousCaptainAttack:
            case MarieCelestePolice:
            case PirateAttack:
            case PoliceAttack:
            case ScarabAttack:
            case ScorpionAttack:
            case SpaceMonsterAttack:
            case TraderAttack:
                setEncounterCmdrHit(EncounterExecuteAttack(getOpponent(), getCommander().getShip(), getEncounterCmdrFleeing()));
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && EncounterExecuteAttack(getCommander().getShip(), getOpponent(), false));
                break;
            case PirateFlee:
            case PirateSurrender:
            case PoliceFlee:
            case TraderFlee:
            case TraderSurrender:
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && EncounterExecuteAttack(getCommander().getShip(), getOpponent(), true));
                setEncounterOppFleeing(true);
                break;
            default:
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && EncounterExecuteAttack(getCommander().getShip(), getOpponent(), false));
                break;
        }

        // Determine whether someone gets destroyed
        if (getCommander().getShip().getHull() <= 0) {
            if (getCommander().getShip().getEscapePod())
                result = EncounterResult.EscapePod;
            else {
                GuiFacade.alert((getOpponent().getHull() <= 0 ? AlertType.EncounterBothDestroyed
                        : AlertType.EncounterYouLose));

                result = EncounterResult.Killed;
            }
        } else if (getOpponentDisabled()) {
            if (getOpponent().Type() == ShipType.Dragonfly || getOpponent().Type() == ShipType.Scarab
                    || getOpponent().Type() == ShipType.Scorpion) {
                String str2 = "";

                switch (getOpponent().Type()) {
                    case Dragonfly:
                        EncounterDefeatDragonfly();
                        break;
                    case Scarab:
                        EncounterDefeatScarab();
                        break;
                    case Scorpion:
                        str2 = Strings.EncounterPrincessRescued;
                        EncounterDefeatScorpion();
                        break;
                }

                GuiFacade.alert(AlertType.EncounterDisabledOpponent, EncounterShipText(), str2);

                getCommander().setReputationScore(
                        getCommander().getReputationScore() + (getOpponent().Type().castToInt() / 2 + 1));
                result = EncounterResult.Normal;
            } else {
                EncounterUpdateEncounterType(prevCmdrHull, prevOppHull);
                setEncounterOppFleeing(false);
            }
        } else if (getOpponent().getHull() <= 0) {
            EncounterWon();

            result = EncounterResult.Normal;
        } else {
            boolean escaped = false;

            // Determine whether someone gets away.
            if (getEncounterCmdrFleeing()
                    && (Difficulty() == spacetrader.game.enums.Difficulty.Beginner || (Functions.GetRandom(7) + getCommander()
                    .getShip().Pilot() / 3) * 2 >= Functions.GetRandom(getOpponent().Pilot())
                    * (2 + Difficulty().castToInt()))) {
                GuiFacade.alert((getEncounterCmdrHit() ? AlertType.EncounterEscapedHit : AlertType.EncounterEscaped));
                escaped = true;
            } else if (getEncounterOppFleeing()
                    && Functions.GetRandom(getCommander().getShip().Pilot()) * 4 <= Functions.GetRandom(7 + getOpponent()
                    .Pilot() / 3) * 2) {
                GuiFacade.alert(AlertType.EncounterOpponentEscaped);
                escaped = true;
            }

            if (escaped)
                result = EncounterResult.Normal;
            else {
                // Determine whether the opponent's actions must be changed
                EncounterType prevEncounter = getEncounterType();

                EncounterUpdateEncounterType(prevCmdrHull, prevOppHull);

                // Update the opponent fleeing flag.
                switch (getEncounterType()) {
                    case PirateFlee:
                    case PirateSurrender:
                    case PoliceFlee:
                    case TraderFlee:
                    case TraderSurrender:
                        setEncounterOppFleeing(true);
                        break;
                    default:
                        setEncounterOppFleeing(false);
                        break;
                }

                if (Options().getContinuousAttack()
                        && (getEncounterCmdrFleeing() || !getEncounterOppFleeing() || Options()
                        .getContinuousAttackFleeing()
                        && (getEncounterType() == prevEncounter || getEncounterType() != spacetrader.game.enums.EncounterType.PirateSurrender
                        && getEncounterType() != spacetrader.game.enums.EncounterType.TraderSurrender))) {
                    if (getEncounterCmdrFleeing())
                        setEncounterContinueFleeing(true);
                    else
                        setEncounterContinueAttacking(true);
                }
            }
        }

        return result;
    }

    private boolean EncounterExecuteAttack(Ship attacker, Ship defender, boolean fleeing) {
        boolean hit = false;

        // On beginner level, if you flee, you will escape unharmed.
        // Otherwise, Fighterskill attacker is pitted against pilotskill
        // defender; if defender
        // is fleeing the attacker has a free shot, but the chance to hit is
        // smaller
        // JAF - if the opponent is disabled and attacker has targeting system,
        // they WILL be hit.
        if (!(Difficulty() == spacetrader.game.enums.Difficulty.Beginner && defender.CommandersShip() && fleeing)
                && (attacker.CommandersShip() && getOpponentDisabled()
                && attacker.HasGadget(GadgetType.TargetingSystem) || Functions.GetRandom(attacker.Fighter()
                + defender.getSize().castToInt()) >= (fleeing ? 2 : 1)
                * Functions.GetRandom(5 + defender.Pilot() / 2))) {
            // If the defender is disabled, it only takes one shot to destroy it
            // completely.
            if (attacker.CommandersShip() && getOpponentDisabled())
                defender.setHull(0);
            else {
                int attackerLasers = attacker.WeaponStrength(WeaponType.PulseLaser, WeaponType.MorgansLaser);
                int attackerDisruptors = attacker.WeaponStrength(WeaponType.PhotonDisruptor,
                        WeaponType.QuantumDisruptor);

                if (defender.Type() == ShipType.Scarab) {
                    attackerLasers -= attacker.WeaponStrength(WeaponType.BeamLaser, WeaponType.MilitaryLaser);
                    attackerDisruptors -= attacker.WeaponStrength(WeaponType.PhotonDisruptor,
                            WeaponType.PhotonDisruptor);
                }

                int attackerWeapons = attackerLasers + attackerDisruptors;

                int disrupt = 0;

                // Attempt to disable the opponent if they're not already
                // disabled, their shields are down,
                // we have disabling weapons, and the option is checked.
                if (defender.Disableable() && defender.ShieldCharge() == 0 && !getOpponentDisabled()
                        && Options().getDisableOpponents() && attackerDisruptors > 0) {
                    disrupt = Functions.GetRandom(attackerDisruptors * (100 + 2 * attacker.Fighter()) / 100);
                } else {
                    int damage = attackerWeapons == 0 ? 0 : Functions.GetRandom(attackerWeapons
                            * (100 + 2 * attacker.Fighter()) / 100);

                    if (damage > 0) {
                        hit = true;

                        // Reactor on board -- damage is boosted!
                        if (defender.ReactorOnBoard())
                            damage *= (int) (1 + (Difficulty().castToInt() + 1)
                                    * (Difficulty().castToInt() < spacetrader.game.enums.Difficulty.Normal.castToInt() ? 0.25
                                    : 0.33));

                        // First, shields are depleted
                        for (int i = 0; i < defender.Shields().length && defender.Shields()[i] != null && damage > 0; i++) {
                            int applied = Math.min(defender.Shields()[i].getCharge(), damage);
                            defender.Shields()[i].setCharge(defender.Shields()[i].getCharge() - applied);
                            damage -= applied;
                        }

                        // If there still is damage after the shields have been
                        // depleted,
                        // this is subtracted from the hull, modified by the
                        // engineering skill
                        // of the defender.
                        // JAF - If the player only has disabling weapons, no
                        // damage will be done to the hull.
                        if (damage > 0) {
                            damage = Math.max(1, damage - Functions.GetRandom(defender.Engineer()));

                            disrupt = damage * attackerDisruptors / attackerWeapons;

                            // Only that damage coming from Lasers will deplete
                            // the hull.
                            damage -= disrupt;

                            // At least 2 shots on Normal level are needed to
                            // destroy the hull
                            // (3 on Easy, 4 on Beginner, 1 on Hard or
                            // Impossible). For opponents,
                            // it is always 2.
                            damage = Math.min(damage, defender.getHullStrength()
                                    / (defender.CommandersShip() ? Math.max(1, spacetrader.game.enums.Difficulty.Impossible
                                    .castToInt()
                                    - Difficulty().castToInt()) : 2));

                            // If the hull is hardened, damage is halved.
                            if (getQuestStatusScarab() == SpecialEvent.StatusScarabDone)
                                damage /= 2;

                            defender.setHull(Math.max(0, defender.getHull() - damage));
                        }
                    }
                }

                // Did the opponent get disabled? (Disruptors are 3 times more
                // effective against the ship's
                // systems than they are against the shields).
                if (defender.getHull() > 0
                        && defender.Disableable()
                        && Functions.GetRandom(100) < disrupt * Consts.DisruptorSystemsMultiplier * 100
                        / defender.getHull())
                    setOpponentDisabled(true);

                // Make sure the Scorpion doesn't get destroyed.
                if (defender.Type() == ShipType.Scorpion && defender.getHull() == 0) {
                    defender.setHull(1);
                    setOpponentDisabled(true);
                }
            }
        }

        return hit;
    }

    public void EncounterMeet() {
        AlertType initialAlert = AlertType.Alert;
        int skill = 0;
        EquipmentType equipType = EquipmentType.Gadget;
        Object equipSubType = null;

        switch (getEncounterType()) {
            case CaptainAhab:
                // Trade a reflective shield for skill points in piloting?
                initialAlert = AlertType.MeetCaptainAhab;
                equipType = EquipmentType.Shield;
                equipSubType = ShieldType.Reflective;
                skill = SkillType.Pilot.castToInt();

                break;
            case CaptainConrad:
                // Trade a military laser for skill points in engineering?
                initialAlert = AlertType.MeetCaptainConrad;
                equipType = EquipmentType.Weapon;
                equipSubType = WeaponType.MilitaryLaser;
                skill = SkillType.Engineer.castToInt();

                break;
            case CaptainHuie:
                // Trade a military laser for skill points in trading?
                initialAlert = AlertType.MeetCaptainHuie;
                equipType = EquipmentType.Weapon;
                equipSubType = WeaponType.MilitaryLaser;
                skill = SkillType.Trader.castToInt();

                break;
        }

        if (GuiFacade.alert(initialAlert) == DialogResult.YES) {
            // Remove the equipment we're trading.
            getCommander().getShip().RemoveEquipment(equipType, equipSubType);

            // Add points to the appropriate skill - two points if
            // beginner-normal, one otherwise.
            getCommander().Skills()[skill] = Math.min(Consts.MaxSkill, getCommander().Skills()[skill]
                    + (Difficulty().castToInt() <= spacetrader.game.enums.Difficulty.Normal.castToInt() ? 2 : 1));

            GuiFacade.alert(AlertType.SpecialTrainingCompleted);
        }
    }

    public void EncounterPlunder() {
        GuiFacade.performPlundering();

        if (getEncounterType().castToInt() >= spacetrader.game.enums.EncounterType.TraderAttack.castToInt()) {
            getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScorePlunderTrader);

            if (getOpponentDisabled())
                getCommander().setKillsTrader(getCommander().getKillsTrader() + 1);
        } else if (getOpponentDisabled()) {
            if (getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
                GuiFacade.alert(AlertType.EncounterPiratesBounty, Strings.EncounterPiratesDisabled, Strings.EncounterPiratesLocation, Functions
                        .multiples(getOpponent().Bounty(), Strings.MoneyUnit));

                getCommander().setCash(getCommander().getCash() + getOpponent().Bounty());
            }

            getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
            getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
        } else
            getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScorePlunderPirate);

        getCommander().setReputationScore(getCommander().getReputationScore() + (getOpponent().Type().castToInt() / 2 + 1));
    }

    private void EncounterScoop() {
        // Chance 50% to pick something up on Normal level, 33% on Hard level,
        // 25% on
        // Impossible level, and 100% on Easy or Beginner.
        if ((Difficulty().castToInt() < spacetrader.game.enums.Difficulty.Normal.castToInt() || Functions
                .GetRandom(Difficulty().castToInt()) == 0)
                && getOpponent().FilledCargoBays() > 0) {
            // Changed this to actually pick a good that was in the opponent's
            // cargo hold - JAF.
            int index = Functions.GetRandom(getOpponent().FilledCargoBays());
            int tradeItem = -1;
            for (int sum = 0; sum <= index; sum += getOpponent().getCargo()[++tradeItem])
                ;

            if (GuiFacade.alert(AlertType.EncounterScoop, Consts.TradeItems[tradeItem].Name()) == DialogResult.YES) {
                boolean jettisoned = false;

                if (getCommander().getShip().FreeCargoBays() == 0
                        && GuiFacade.alert(AlertType.EncounterScoopNoRoom) == DialogResult.YES) {
                    GuiFacade.performJettison();
                    jettisoned = true;
                }

                if (getCommander().getShip().FreeCargoBays() > 0)
                    getCommander().getShip().getCargo()[tradeItem]++;
                else if (jettisoned)
                    GuiFacade.alert(AlertType.EncounterScoopNoScoop);
            }
        }
    }

    public void EncounterTrade() {
        boolean buy = (getEncounterType() == spacetrader.game.enums.EncounterType.TraderBuy);
        int item = (buy ? getCommander().getShip() : getOpponent()).GetRandomTradeableItem();
        String alertStr = (buy ? Strings.CargoSelling : Strings.CargoBuying);

        int cash = getCommander().getCash();

        if (getEncounterType() == spacetrader.game.enums.EncounterType.TraderBuy)
            CargoSellTrader(item);
        else
            // EncounterType.TraderSell
            CargoBuyTrader(item);

        if (getCommander().getCash() != cash)
            GuiFacade.alert(AlertType.EncounterTradeCompleted, alertStr, Consts.TradeItems[item].Name());
    }

    private void EncounterUpdateEncounterType(int prevCmdrHull, int prevOppHull) {
        int chance = Functions.GetRandom(100);

        if (getOpponent().getHull() < prevOppHull || getOpponentDisabled()) {
            switch (getEncounterType()) {
                case FamousCaptainAttack:
                    if (getOpponentDisabled())
                        setEncounterType(spacetrader.game.enums.EncounterType.FamousCaptDisabled);
                    break;
                case PirateAttack:
                case PirateFlee:
                case PirateSurrender:
                    if (getOpponentDisabled())
                        setEncounterType(spacetrader.game.enums.EncounterType.PirateDisabled);
                    else if (getOpponent().getHull() < (prevOppHull * 2) / 3) {
                        if (getCommander().getShip().getHull() < (prevCmdrHull * 2) / 3) {
                            if (chance < 60) {
                                getEncounterType();
                                setEncounterType(spacetrader.game.enums.EncounterType.PirateFlee);
                            }
                        } else {
                            if (chance < 10 && getOpponent().Type() != ShipType.Mantis)
                                setEncounterType(spacetrader.game.enums.EncounterType.PirateSurrender);
                            else
                                setEncounterType(spacetrader.game.enums.EncounterType.PirateFlee);
                        }
                    }
                    break;
                case PoliceAttack:
                case PoliceFlee:
                    if (getOpponentDisabled())
                        setEncounterType(spacetrader.game.enums.EncounterType.PoliceDisabled);
                    else if (getOpponent().getHull() < prevOppHull / 2
                            && (getCommander().getShip().getHull() >= prevCmdrHull / 2 || chance < 40))
                        setEncounterType(spacetrader.game.enums.EncounterType.PoliceFlee);
                    break;
                case TraderAttack:
                case TraderFlee:
                case TraderSurrender:
                    if (getOpponentDisabled())
                        setEncounterType(spacetrader.game.enums.EncounterType.TraderDisabled);
                    else if (getOpponent().getHull() < (prevOppHull * 2) / 3) {
                        if (chance < 60)
                            setEncounterType(spacetrader.game.enums.EncounterType.TraderSurrender);
                        else
                            setEncounterType(spacetrader.game.enums.EncounterType.TraderFlee);
                    }
                    // If you get damaged a lot, the trader tends to keep shooting;
                    // if
                    // you get damaged a little, the trader may keep shooting; if
                    // you
                    // get damaged very little or not at all, the trader will flee.
                    else if (getOpponent().getHull() < (prevOppHull * 9) / 10
                            && (getCommander().getShip().getHull() < (prevCmdrHull * 2) / 3 && chance < 20
                            || getCommander().getShip().getHull() < (prevCmdrHull * 9) / 10 && chance < 60 || getCommander()
                            .getShip().getHull() >= (prevCmdrHull * 9) / 10))
                        setEncounterType(spacetrader.game.enums.EncounterType.TraderFlee);
                    break;
                default:
                    break;
            }
        }
    }

    public boolean EncounterVerifyAttack() {
        boolean attack = true;

        if (getCommander().getShip().WeaponStrength() == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoWeapons);
            attack = false;
        } else if (!getOpponent().Disableable()
                && getCommander().getShip().WeaponStrength(WeaponType.PulseLaser, WeaponType.MorgansLaser) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoLasers);
            attack = false;
        } else if (getOpponent().Type() == ShipType.Scorpion
                && getCommander().getShip().WeaponStrength(WeaponType.PhotonDisruptor, WeaponType.QuantumDisruptor) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoDisruptors);
            attack = false;
        } else {
            switch (getEncounterType()) {
                case DragonflyIgnore:
                case PirateIgnore:
                case ScarabIgnore:
                case ScorpionIgnore:
                case SpaceMonsterIgnore:
                    setEncounterType(spacetrader.game.enums.EncounterType.fromInt(getEncounterType().castToInt() - 1));

                    break;
                case PoliceInspect:
                    if (!getCommander().getShip().DetectableIllegalCargoOrPassengers()
                            && GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) != DialogResult.YES)
                        attack = false;

                    // Fall through...
                    if (attack) {
                    }// goto case PoliceIgnore;
                    else
                        break;
                case MarieCelestePolice:
                case PoliceFlee:
                case PoliceIgnore:
                case PoliceSurrender:
                    if (getCommander().getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                            || GuiFacade.alert(AlertType.EncounterAttackPolice) == DialogResult.YES) {
                        if (getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal)
                            getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreCriminal);

                        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreAttackPolice);

                        if (getEncounterType() != spacetrader.game.enums.EncounterType.PoliceFlee)
                            setEncounterType(spacetrader.game.enums.EncounterType.PoliceAttack);
                    } else
                        attack = false;

                    break;
                case TraderBuy:
                case TraderIgnore:
                case TraderSell:
                    if (getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreClean)
                        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreAttackTrader);
                    else if (GuiFacade.alert(AlertType.EncounterAttackTrader) == DialogResult.YES)
                        getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                    else
                        attack = false;

                    // Fall through...
                    if (attack) {
                    }// goto case TraderAttack;
                    else
                        break;
                case TraderAttack:
                case TraderSurrender:
                    if (Functions.GetRandom(Consts.ReputationScoreElite) <= getCommander().getReputationScore() * 10
                            / (getOpponent().Type().castToInt() + 1)
                            || getOpponent().WeaponStrength() == 0)
                        setEncounterType(spacetrader.game.enums.EncounterType.TraderFlee);
                    else
                        setEncounterType(spacetrader.game.enums.EncounterType.TraderAttack);

                    break;
                case CaptainAhab:
                case CaptainConrad:
                case CaptainHuie:
                    if (GuiFacade.alert(AlertType.EncounterAttackCaptain) == DialogResult.YES) {
                        if (getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreVillain)
                            getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreVillain);

                        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreAttackTrader);

                        switch (getEncounterType()) {
                            case CaptainAhab:
                                NewsAddEvent(NewsEvent.CaptAhabAttacked);
                                break;
                            case CaptainConrad:
                                NewsAddEvent(NewsEvent.CaptConradAttacked);
                                break;
                            case CaptainHuie:
                                NewsAddEvent(NewsEvent.CaptHuieAttacked);
                                break;
                        }

                        setEncounterType(spacetrader.game.enums.EncounterType.FamousCaptainAttack);
                    } else
                        attack = false;

                    break;
            }

            // Make sure the fleeing flag isn't set if we're attacking.
            if (attack)
                setEncounterCmdrFleeing(false);
        }

        return attack;
    }

    public boolean EncounterVerifyBoard() {
        boolean board = false;

        if (GuiFacade.alert(AlertType.EncounterMarieCeleste) == DialogResult.YES) {
            board = true;

            int narcs = getCommander().getShip().getCargo()[TradeItemType.Narcotics.castToInt()];

            GuiFacade.performPlundering();

            if (getCommander().getShip().getCargo()[TradeItemType.Narcotics.castToInt()] > narcs)
                setJustLootedMarie(true);
        }

        return board;
    }

    public boolean EncounterVerifyBribe() {
        boolean bribed = false;

        if (getEncounterType() == spacetrader.game.enums.EncounterType.MarieCelestePolice)
            GuiFacade.alert(AlertType.EncounterMarieCelesteNoBribe);
        else if (getWarpSystem().politicalSystem().BribeLevel() <= 0)
            GuiFacade.alert(AlertType.EncounterPoliceBribeCant);
        else if (getCommander().getShip().DetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            // Bribe depends on how easy it is to bribe the police and
            // commander's current worth
            int diffMod = 10 + 5 * (spacetrader.game.enums.Difficulty.Impossible.castToInt() - Difficulty().castToInt());
            int passMod = getCommander().getShip().IllegalSpecialCargo() ? (Difficulty().castToInt() <= spacetrader.game.enums.Difficulty.Normal
                    .castToInt() ? 2 : 3)
                    : 1;

            int bribe = Math.max(100, Math.min(10000, (int) Math.ceil((double) getCommander().Worth()
                    / getWarpSystem().politicalSystem().BribeLevel() / diffMod / 100)
                    * 100 * passMod));

            if (GuiFacade.alert(AlertType.EncounterPoliceBribe, Functions.multiples(bribe, Strings.MoneyUnit)) == DialogResult.YES) {
                if (getCommander().getCash() >= bribe) {
                    getCommander().setCash(getCommander().getCash() - bribe);
                    bribed = true;
                } else
                    GuiFacade.alert(AlertType.EncounterPoliceBribeLowCash);
            }
        }

        return bribed;
    }

    public boolean EncounterVerifyFlee() {
        setEncounterCmdrFleeing(false);

        if (getEncounterType() != spacetrader.game.enums.EncounterType.PoliceInspect
                || getCommander().getShip().DetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            setEncounterCmdrFleeing(true);

            if (getEncounterType() == spacetrader.game.enums.EncounterType.MarieCelestePolice
                    && GuiFacade.alert(AlertType.EncounterPostMarieFlee) == DialogResult.NO) {
                setEncounterCmdrFleeing(false);
            } else if (getEncounterType() == spacetrader.game.enums.EncounterType.PoliceInspect
                    || getEncounterType() == spacetrader.game.enums.EncounterType.MarieCelestePolice) {
                int scoreMod = getEncounterType() == spacetrader.game.enums.EncounterType.PoliceInspect ? Consts.ScoreFleePolice
                        : Consts.ScoreAttackPolice;
                int scoreMin = getEncounterType() == spacetrader.game.enums.EncounterType.PoliceInspect ? Consts.PoliceRecordScoreDubious
                        - (Difficulty().castToInt() < spacetrader.game.enums.Difficulty.Normal.castToInt() ? 0 : 1)
                        : Consts.PoliceRecordScoreCriminal;

                setEncounterType(spacetrader.game.enums.EncounterType.PoliceAttack);
                getCommander().setPoliceRecordScore(Math.min(getCommander().getPoliceRecordScore() + scoreMod, scoreMin));
            }
        }

        return getEncounterCmdrFleeing();
    }

    public boolean EncounterVerifySubmit() {
        boolean submit = false;

        if (getCommander().getShip().DetectableIllegalCargoOrPassengers()) {
            String str1 = getCommander().getShip().IllegalSpecialCargoDescription("", true, true);
            String str2 = getCommander().getShip().IllegalSpecialCargo() ? Strings.EncounterPoliceSubmitArrested : "";

            if (GuiFacade.alert(AlertType.EncounterPoliceSubmit, str1, str2) == DialogResult.YES) {
                submit = true;

                // If you carry illegal goods, they are impounded and you are
                // fined
                if (getCommander().getShip().DetectableIllegalCargo()) {
                    getCommander().getShip().RemoveIllegalGoods();

                    int fine = (int) Math.max(100, Math.min(10000,
                            Math
                                    .ceil((double) getCommander().Worth()
                                            / ((spacetrader.game.enums.Difficulty.Impossible.castToInt()
                                            - Difficulty().castToInt() + 2) * 10) / 50) * 50));
                    int cashPayment = Math.min(getCommander().getCash(), fine);
                    getCommander().setDebt(getCommander().getDebt() + (fine - cashPayment));
                    getCommander().setCash(getCommander().getCash() - cashPayment);

                    GuiFacade.alert(AlertType.EncounterPoliceFine, Functions.multiples(fine, Strings.MoneyUnit));

                    getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreTrafficking);
                }
            }
        } else {
            submit = true;

            // If you aren't carrying illegal cargo or passengers, the police
            // will increase your lawfulness record
            GuiFacade.alert(AlertType.EncounterPoliceNothingFound);
            getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() - Consts.ScoreTrafficking);
        }

        return submit;
    }

    public EncounterResult EncounterVerifySurrender() {
        EncounterResult result = EncounterResult.Continue;

        if (getOpponent().Type() == ShipType.Mantis) {
            if (getCommander().getShip().ArtifactOnBoard()) {
                if (GuiFacade.alert(AlertType.EncounterAliensSurrender) == DialogResult.YES) {
                    GuiFacade.alert(AlertType.ArtifactRelinquished);
                    setQuestStatusArtifact(SpecialEvent.StatusArtifactNotStarted);

                    result = EncounterResult.Normal;
                }
            } else
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
        } else if (getEncounterType() == spacetrader.game.enums.EncounterType.PoliceAttack
                || getEncounterType() == spacetrader.game.enums.EncounterType.PoliceSurrender) {
            if (getCommander().getPoliceRecordScore() <= Consts.PoliceRecordScorePsychopath)
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
            else if (GuiFacade.alert(AlertType.EncounterPoliceSurrender, (new String[]{
                    getCommander().getShip().IllegalSpecialCargoDescription(Strings.EncounterPoliceSurrenderCargo, true,
                            false), getCommander().getShip().IllegalSpecialCargoActions()})) == DialogResult.YES)
                result = EncounterResult.Arrested;
        } else if (getCommander().getShip().PrincessOnBoard()
                && !getCommander().getShip().HasGadget(GadgetType.HiddenCargoBays)) {
            GuiFacade.alert(AlertType.EncounterPiratesSurrenderPrincess);
        } else {
            setRaided(true);

            if (getCommander().getShip().HasGadget(GadgetType.HiddenCargoBays)) {
                ArrayList precious = new ArrayList();
                if (getCommander().getShip().PrincessOnBoard())
                    precious.add(Strings.EncounterHidePrincess);
                if (getCommander().getShip().SculptureOnBoard())
                    precious.add(Strings.EncounterHideSculpture);

                GuiFacade.alert(AlertType.PreciousHidden, Functions.stringVars(Strings.ListStrings[precious.size()],
                        (String[]) precious.toArray(new String[0])));
            } else if (getCommander().getShip().SculptureOnBoard()) {
                setQuestStatusSculpture(SpecialEvent.StatusSculptureNotStarted);
                GuiFacade.alert(AlertType.EncounterPiratesTakeSculpture);
            }

            ArrayList cargoToSteal = getCommander().getShip().StealableCargo();
            if (cargoToSteal.size() == 0) {
                int blackmail = Math.min(25000, Math.max(500, getCommander().Worth() / 20));
                int cashPayment = Math.min(getCommander().getCash(), blackmail);
                getCommander().setDebt(getCommander().getDebt() + (blackmail - cashPayment));
                getCommander().setCash(getCommander().getCash() - cashPayment);
                GuiFacade.alert(AlertType.EncounterPiratesFindNoCargo, Functions
                        .multiples(blackmail, Strings.MoneyUnit));
            } else {
                GuiFacade.alert(AlertType.EncounterLooting);

                // Pirates steal as much as they have room for, which could be
                // everything - JAF.
                // Take most high-priced items - JAF.
                while (getOpponent().FreeCargoBays() > 0 && cargoToSteal.size() > 0) {
                    int item = (Integer) cargoToSteal.get(0);

                    getCommander().getPriceCargo()[item] -= getCommander().getPriceCargo()[item]
                            / getCommander().getShip().getCargo()[item];
                    getCommander().getShip().getCargo()[item]--;
                    getOpponent().getCargo()[item]++;

                    cargoToSteal.remove(0);
                }
            }

            if (getCommander().getShip().WildOnBoard()) {
                if (getOpponent().getCrewQuarters() > 1) {
                    // Wild hops onto Pirate Ship
                    setQuestStatusWild(SpecialEvent.StatusWildNotStarted);
                    GuiFacade.alert(AlertType.WildGoesPirates);
                } else
                    GuiFacade.alert(AlertType.WildChatsPirates);
            }

            // pirates puzzled by reactor
            if (getCommander().getShip().ReactorOnBoard())
                GuiFacade.alert(AlertType.EncounterPiratesExamineReactor);

            result = EncounterResult.Normal;
        }

        return result;
    }

    public EncounterResult EncounterVerifyYield() {
        EncounterResult result = EncounterResult.Continue;

        if (getCommander().getShip().IllegalSpecialCargo()) {
            if (GuiFacade.alert(AlertType.EncounterPoliceSurrender, (new String[]{
                    getCommander().getShip().IllegalSpecialCargoDescription(Strings.EncounterPoliceSurrenderCargo, true,
                            true), getCommander().getShip().IllegalSpecialCargoActions()})) == DialogResult.YES)
                result = EncounterResult.Arrested;
        } else {
            String str1 = getCommander().getShip().IllegalSpecialCargoDescription("", false, true);

            if (GuiFacade.alert(AlertType.EncounterPoliceSubmit, str1, "") == DialogResult.YES) {
                // Police Record becomes dubious, if it wasn't already.
                if (getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreDubious)
                    getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreDubious);

                getCommander().getShip().RemoveIllegalGoods();

                result = EncounterResult.Normal;
            }
        }

        return result;
    }

    private void EncounterWon() {
        if (getEncounterType().castToInt() >= spacetrader.game.enums.EncounterType.PirateAttack.castToInt()
                && getEncounterType().castToInt() <= spacetrader.game.enums.EncounterType.PirateDisabled.castToInt()
                && getOpponent().Type() != ShipType.Mantis
                && getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious)
            GuiFacade.alert(AlertType.EncounterPiratesBounty, Strings.EncounterPiratesDestroyed, "", Functions
                    .multiples(getOpponent().Bounty(), Strings.MoneyUnit));
        else
            GuiFacade.alert(AlertType.EncounterYouWin);

        switch (getEncounterType()) {
            case FamousCaptainAttack:
                getCommander().setKillsTrader(getCommander().getKillsTrader() + 1);
                if (getCommander().getReputationScore() < Consts.ReputationScoreDangerous)
                    getCommander().setReputationScore(Consts.ReputationScoreDangerous);
                else
                    getCommander().setReputationScore(getCommander().getReputationScore() + Consts.ScoreKillCaptain);

                // bump news flag from attacked to ship destroyed
                NewsReplaceEvent(NewsLatestEvent(), NewsEvent.fromInt(NewsLatestEvent().castToInt() + 1));
                break;
            case DragonflyAttack:
                EncounterDefeatDragonfly();
                break;
            case PirateAttack:
            case PirateFlee:
            case PirateSurrender:
                getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
                if (getOpponent().Type() != ShipType.Mantis) {
                    if (getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious)
                        getCommander().setCash(getCommander().getCash() + getOpponent().Bounty());
                    getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
                    EncounterScoop();
                }
                break;
            case PoliceAttack:
            case PoliceFlee:
                getCommander().setKillsPolice(getCommander().getKillsPolice() + 1);
                getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPolice);
                break;
            case ScarabAttack:
                EncounterDefeatScarab();
                break;
            case SpaceMonsterAttack:
                getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
                getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
                setQuestStatusSpaceMonster(SpecialEvent.StatusSpaceMonsterDestroyed);
                break;
            case TraderAttack:
            case TraderFlee:
            case TraderSurrender:
                getCommander().setKillsTrader(getCommander().getKillsTrader() + 1);
                getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillTrader);
                EncounterScoop();
                break;
            default:
                break;
        }

        getCommander().setReputationScore(getCommander().getReputationScore() + (getOpponent().Type().castToInt() / 2 + 1));
    }

    public void EscapeWithPod() {
        GuiFacade.alert(AlertType.EncounterEscapePodActivated);

        if (getCommander().getShip().SculptureOnBoard())
            GuiFacade.alert(AlertType.SculptureSaved);

        if (getCommander().getShip().ReactorOnBoard()) {
            GuiFacade.alert(AlertType.ReactorDestroyed);
            setQuestStatusReactor(SpecialEvent.StatusReactorDone);
        }

        if (getCommander().getShip().getTribbles() > 0)
            GuiFacade.alert(AlertType.TribblesKilled);

        if (getQuestStatusJapori() == SpecialEvent.StatusJaporiInTransit) {
            int system;
            for (system = 0; system < Universe().length
                    && Universe()[system].getSpecialEventType() != SpecialEventType.Japori; system++)
                ;
            GuiFacade.alert(AlertType.AntidoteDestroyed, Universe()[system].name());
            setQuestStatusJapori(SpecialEvent.StatusJaporiNotStarted);
        }

        if (getCommander().getShip().ArtifactOnBoard()) {
            GuiFacade.alert(AlertType.ArtifactLost);
            setQuestStatusArtifact(SpecialEvent.StatusArtifactDone);
        }

        if (getCommander().getShip().JarekOnBoard()) {
            GuiFacade.alert(AlertType.JarekTakenHome);
            setQuestStatusJarek(SpecialEvent.StatusJarekNotStarted);
        }

        if (getCommander().getShip().PrincessOnBoard()) {
            GuiFacade.alert(AlertType.PrincessTakenHome);
            setQuestStatusPrincess(SpecialEvent.StatusPrincessNotStarted);
        }

        if (getCommander().getShip().WildOnBoard()) {
            GuiFacade.alert(AlertType.WildArrested);
            getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreCaughtWithWild);
            NewsAddEvent(NewsEvent.WildArrested);
            setQuestStatusWild(SpecialEvent.StatusWildNotStarted);
        }

        if (getCommander().getInsurance()) {
            GuiFacade.alert(AlertType.InsurancePayoff);
            getCommander().setCash(getCommander().getCash() + getCommander().getShip().BaseWorth(true));
        }

        if (getCommander().getCash() > Consts.FleaConversionCost)
            getCommander().setCash(getCommander().getCash() - Consts.FleaConversionCost);
        else {
            getCommander().setDebt(getCommander().getDebt() + (Consts.FleaConversionCost - getCommander().getCash()));
            getCommander().setCash(0);
        }

        GuiFacade.alert(AlertType.FleaBuilt);

        IncDays(3);

        CreateFlea();
    }

    private boolean FindDistantSystem(StarSystemId baseSystem, SpecialEventType specEvent) {
        int bestDistance = 999;
        int system = -1;
        for (int i = 0; i < Universe().length; i++) {
            int distance = Functions.Distance(Universe()[baseSystem.castToInt()], Universe()[i]);
            if (distance >= 70 && distance < bestDistance && Universe()[i].getSpecialEventType() == SpecialEventType.NA) {
                system = i;
                bestDistance = distance;
            }
        }
        if (system >= 0)
            Universe()[system].setSpecialEventType(specEvent);

        return (system >= 0);
    }

    private void GenerateCrewMemberList() {
        int[] used = new int[Universe().length];
        int d = Difficulty().castToInt();

        // Zeethibal may be on Kravat
        used[StarSystemId.Kravat.castToInt()] = 1;

        // special individuals:
        // Zeethibal, Jonathan Wild's Nephew - skills will be set later.
        // Wild, Jonathan Wild earns his keep now - JAF.
        // Jarek, Ambassador Jarek earns his keep now - JAF.
        // Dummy pilots for opponents.
        Mercenaries()[CrewMemberId.Zeethibal.castToInt()] = new CrewMember(CrewMemberId.Zeethibal, 5, 5, 5, 5,
                StarSystemId.NA);
        Mercenaries()[CrewMemberId.Opponent.castToInt()] = new CrewMember(CrewMemberId.Opponent, 5, 5, 5, 5,
                StarSystemId.NA);
        Mercenaries()[CrewMemberId.Wild.castToInt()] = new CrewMember(CrewMemberId.Wild, 7, 10, 2, 5, StarSystemId.NA);
        Mercenaries()[CrewMemberId.Jarek.castToInt()] = new CrewMember(CrewMemberId.Jarek, 3, 2, 10, 4, StarSystemId.NA);
        Mercenaries()[CrewMemberId.Princess.castToInt()] = new CrewMember(CrewMemberId.Princess, 4, 3, 8, 9,
                StarSystemId.NA);
        Mercenaries()[CrewMemberId.FamousCaptain.castToInt()] = new CrewMember(CrewMemberId.FamousCaptain, 10, 10, 10,
                10, StarSystemId.NA);
        Mercenaries()[CrewMemberId.Dragonfly.castToInt()] = new CrewMember(CrewMemberId.Dragonfly, 4 + d, 6 + d, 1,
                6 + d, StarSystemId.NA);
        Mercenaries()[CrewMemberId.Scarab.castToInt()] = new CrewMember(CrewMemberId.Scarab, 5 + d, 6 + d, 1, 6 + d,
                StarSystemId.NA);
        Mercenaries()[CrewMemberId.Scorpion.castToInt()] = new CrewMember(CrewMemberId.Scorpion, 8 + d, 8 + d, 1,
                6 + d, StarSystemId.NA);
        Mercenaries()[CrewMemberId.SpaceMonster.castToInt()] = new CrewMember(CrewMemberId.SpaceMonster, 8 + d, 8 + d,
                1, 1 + d, StarSystemId.NA);

        // JAF - Changing this to allow multiple mercenaries in each system, but
        // no more
        // than three.
        for (int i = 1; i < Mercenaries().length; i++) {
            // Only create a CrewMember Object if one doesn't already exist in
            // this slot in the array.
            if (Mercenaries()[i] == null) {
                StarSystemId id;
                boolean ok = false;

                do {
                    id = StarSystemId.fromInt(Functions.GetRandom(Universe().length));
                    if (used[id.castToInt()] < 3) {
                        used[id.castToInt()]++;
                        ok = true;
                    }
                } while (!ok);

                Mercenaries()[i] = new CrewMember(CrewMemberId.fromInt(i), Functions.RandomSkill(), Functions
                        .RandomSkill(), Functions.RandomSkill(), Functions.RandomSkill(), id);
            }
        }
    }

    private void GenerateOpponent(OpponentType oppType) {
        setOpponent(new Ship(oppType));
    }

    private void GenerateUniverse() {
        _universe = new StarSystem[Strings.SystemNames.length];/////////////////
        // //////
        // _universe = new StarSystem[10];

        int i, j;

        for (i = 0; i < Universe().length; i++) {
            StarSystemId id = (StarSystemId.fromInt(i));
            SystemPressure pressure = SystemPressure.None;
            SpecialResource specRes = SpecialResource.Nothing;
            Size size = Size.fromInt(Functions.GetRandom(Size.Huge.castToInt() + 1));
            PoliticalSystem polSys = Consts.PoliticalSystems[Functions.GetRandom(Consts.PoliticalSystems.length)];
            TechLevel tech = TechLevel.fromInt(Functions.GetRandom(polSys.MinimumTechLevel().castToInt(), polSys
                    .MaximumTechLevel().castToInt() + 1));

            // Galvon must be a Monarchy.
            if (id == StarSystemId.Galvon) {
                size = Size.Large;
                polSys = Consts.PoliticalSystems[PoliticalSystemType.Monarchy.castToInt()];
                tech = TechLevel.HiTech;
            }

            if (Functions.GetRandom(100) < 15)
                pressure = SystemPressure.fromInt(Functions.GetRandom(SystemPressure.War.castToInt(),
                        SystemPressure.Employment.castToInt() + 1));
            if (Functions.GetRandom(5) >= 3)
                specRes = SpecialResource.fromInt(Functions.GetRandom(SpecialResource.MineralRich.castToInt(),
                        SpecialResource.Warlike.castToInt() + 1));

            int x = 0;
            int y = 0;

            if (i < Wormholes().length) {
                // Place the first systems somewhere in the center.
                x = ((Consts.GalaxyWidth * (1 + 2 * (i % 3))) / 6)
                        - Functions.GetRandom(-Consts.CloseDistance + 1, Consts.CloseDistance);
                y = ((Consts.GalaxyHeight * (i < 3 ? 1 : 3)) / 4)
                        - Functions.GetRandom(-Consts.CloseDistance + 1, Consts.CloseDistance);
                Wormholes()[i] = i;
            } else {
                boolean ok = false;
                while (!ok) {
                    x = Functions.GetRandom(1, Consts.GalaxyWidth);
                    y = Functions.GetRandom(1, Consts.GalaxyHeight);

                    boolean closeFound = false;
                    boolean tooClose = false;
                    for (j = 0; j < i && !tooClose; j++) {
                        // Minimum distance between any two systems not to be
                        // accepted.
                        if (Functions.Distance(Universe()[j], x, y) < Consts.MinDistance)
                            tooClose = true;

                        // There should be at least one system which is close
                        // enough.
                        if (Functions.Distance(Universe()[j], x, y) < Consts.CloseDistance)
                            closeFound = true;
                    }
                    ok = (closeFound && !tooClose);
                }
            }

            Universe()[i] = new StarSystem(id, x, y, size, tech, polSys.Type(), pressure, specRes);
        }

        // Randomize the system locations a bit more, otherwise the systems with
        // the first
        // names in the alphabet are all in the center.
        for (i = 0; i < Universe().length; i++) {
            j = Functions.GetRandom(Universe().length);
            if (!Functions.WormholeExists(j, -1)) {
                int x = Universe()[i].X();
                int y = Universe()[i].Y();
                Universe()[i].X(Universe()[j].X());
                Universe()[i].Y(Universe()[j].Y());
                Universe()[j].X(x);
                Universe()[j].Y(y);

                int w = Util.BruteSeek(Wormholes(), i);
                if (w >= 0)
                    Wormholes()[w] = j;
            }
        }

        // Randomize wormhole order
        for (i = 0; i < Wormholes().length; i++) {
            j = Functions.GetRandom(Wormholes().length);
            int w = Wormholes()[i];
            Wormholes()[i] = Wormholes()[j];
            Wormholes()[j] = w;
        }
    }

    public void handleSpecialEvent() {
        StarSystem curSys = getCommander().getCurrentSystem();
        boolean remove = true;

        switch (curSys.getSpecialEventType()) {
            case Artifact:
                setQuestStatusArtifact(SpecialEvent.StatusArtifactOnBoard);
                break;
            case ArtifactDelivery:
                setQuestStatusArtifact(SpecialEvent.StatusArtifactDone);
                break;
            case CargoForSale:
                GuiFacade.alert(AlertType.SpecialSealedCanisters);
                int tradeItem = Functions.GetRandom(Consts.TradeItems.length);
                getCommander().getShip().getCargo()[tradeItem] += 3;
                getCommander().getPriceCargo()[tradeItem] += getCommander().getCurrentSystem().specialEvent().price();
                break;
            case Dragonfly:
            case DragonflyBaratas:
            case DragonflyMelina:
            case DragonflyRegulas:
                setQuestStatusDragonfly(getQuestStatusDragonfly() + 1);
                break;
            case DragonflyDestroyed:
                curSys.setSpecialEventType(SpecialEventType.DragonflyShield);
                remove = false;
                break;
            case DragonflyShield:
                if (getCommander().getShip().FreeSlotsShield() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentLightningShield);
                    getCommander().getShip().AddEquipment(Consts.Shields[ShieldType.Lightning.castToInt()]);
                    setQuestStatusDragonfly(SpecialEvent.StatusDragonflyDone);
                }
                break;
            case EraseRecord:
                GuiFacade.alert(AlertType.SpecialCleanRecord);
                getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreClean);
                RecalculateSellPrices(curSys);
                break;
            case Experiment:
                setQuestStatusExperiment(SpecialEvent.StatusExperimentStarted);
                break;
            case ExperimentFailed:
                break;
            case ExperimentStopped:
                setQuestStatusExperiment(SpecialEvent.StatusExperimentCancelled);
                setCanSuperWarp(true);
                break;
            case Gemulon:
                setQuestStatusGemulon(SpecialEvent.StatusGemulonStarted);
                break;
            case GemulonFuel:
                if (getCommander().getShip().FreeSlotsGadget() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentFuelCompactor);
                    getCommander().getShip().AddEquipment(Consts.Gadgets[GadgetType.FuelCompactor.castToInt()]);
                    setQuestStatusGemulon(SpecialEvent.StatusGemulonDone);
                }
                break;
            case GemulonRescued:
                curSys.setSpecialEventType(SpecialEventType.GemulonFuel);
                setQuestStatusGemulon(SpecialEvent.StatusGemulonFuel);
                remove = false;
                break;
            case Japori:
                // The japori quest should not be removed since you can fail and
                // start it over again.
                remove = false;

                if (getCommander().getShip().FreeCargoBays() < 10)
                    GuiFacade.alert(AlertType.CargoNoEmptyBays);
                else {
                    GuiFacade.alert(AlertType.AntidoteOnBoard);
                    setQuestStatusJapori(SpecialEvent.StatusJaporiInTransit);
                }
                break;
            case JaporiDelivery:
                setQuestStatusJapori(SpecialEvent.StatusJaporiDone);
                getCommander().IncreaseRandomSkill();
                getCommander().IncreaseRandomSkill();
                break;
            case Jarek:
                if (getCommander().getShip().FreeCrewQuarters() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                    remove = false;
                } else {
                    CrewMember jarek = Mercenaries()[CrewMemberId.Jarek.castToInt()];
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.Name());
                    getCommander().getShip().Hire(jarek);
                    setQuestStatusJarek(SpecialEvent.StatusJarekStarted);
                }
                break;
            case JarekGetsOut:
                setQuestStatusJarek(SpecialEvent.StatusJarekDone);
                getCommander().getShip().Fire(CrewMemberId.Jarek);
                break;
            case Lottery:
                break;
            case Moon:
                GuiFacade.alert(AlertType.SpecialMoonBought);
                setQuestStatusMoon(SpecialEvent.StatusMoonBought);
                break;
            case MoonRetirement:
                setQuestStatusMoon(SpecialEvent.StatusMoonDone);
                throw new GameEndException(GameEndType.BoughtMoon);
            case Princess:
                curSys.setSpecialEventType(SpecialEventType.PrincessReturned);
                remove = false;
                setQuestStatusPrincess(getQuestStatusPrincess() + 1);
                break;
            case PrincessCentauri:
            case PrincessInthara:
                setQuestStatusPrincess(getQuestStatusPrincess() + 1);
                break;
            case PrincessQonos:
                if (getCommander().getShip().FreeCrewQuarters() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                    remove = false;
                } else {
                    CrewMember princess = Mercenaries()[CrewMemberId.Princess.castToInt()];
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, princess.Name());
                    getCommander().getShip().Hire(princess);
                }
                break;
            case PrincessQuantum:
                if (getCommander().getShip().FreeSlotsWeapon() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentQuantumDisruptor);
                    getCommander().getShip().AddEquipment(Consts.Weapons[WeaponType.QuantumDisruptor.castToInt()]);
                    setQuestStatusPrincess(SpecialEvent.StatusPrincessDone);
                }
                break;
            case PrincessReturned:
                getCommander().getShip().Fire(CrewMemberId.Princess);
                curSys.setSpecialEventType(SpecialEventType.PrincessQuantum);
                setQuestStatusPrincess(SpecialEvent.StatusPrincessReturned);
                remove = false;
                break;
            case Reactor:
                if (getCommander().getShip().FreeCargoBays() < 15) {
                    GuiFacade.alert(AlertType.CargoNoEmptyBays);
                    remove = false;
                } else {
                    if (getCommander().getShip().WildOnBoard()) {
                        if (GuiFacade.alert(AlertType.WildWontStayAboardReactor, curSys.name()) == DialogResult.OK) {
                            GuiFacade.alert(AlertType.WildLeavesShip, curSys.name());
                            setQuestStatusWild(SpecialEvent.StatusWildNotStarted);
                        } else
                            remove = false;
                    }

                    if (remove) {
                        GuiFacade.alert(AlertType.ReactorOnBoard);
                        setQuestStatusReactor(SpecialEvent.StatusReactorFuelOk);
                    }
                }
                break;
            case ReactorDelivered:
                curSys.setSpecialEventType(SpecialEventType.ReactorLaser);
                setQuestStatusReactor(SpecialEvent.StatusReactorDelivered);
                remove = false;
                break;
            case ReactorLaser:
                if (getCommander().getShip().FreeSlotsWeapon() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentMorgansLaser);
                    getCommander().getShip().AddEquipment(Consts.Weapons[WeaponType.MorgansLaser.castToInt()]);
                    setQuestStatusReactor(SpecialEvent.StatusReactorDone);
                }
                break;
            case Scarab:
                setQuestStatusScarab(SpecialEvent.StatusScarabHunting);
                break;
            case ScarabDestroyed:
                setQuestStatusScarab(SpecialEvent.StatusScarabDestroyed);
                curSys.setSpecialEventType(SpecialEventType.ScarabUpgradeHull);
                remove = false;
                break;
            case ScarabUpgradeHull:
                GuiFacade.alert(AlertType.ShipHullUpgraded);
                getCommander().getShip().setHullUpgraded(true);
                getCommander().getShip().setHull(getCommander().getShip().getHull() + Consts.HullUpgrade);
                setQuestStatusScarab(SpecialEvent.StatusScarabDone);
                remove = false;
                break;
            case Sculpture:
                setQuestStatusSculpture(SpecialEvent.StatusSculptureInTransit);
                break;
            case SculptureDelivered:
                setQuestStatusSculpture(SpecialEvent.StatusSculptureDelivered);
                curSys.setSpecialEventType(SpecialEventType.SculptureHiddenBays);
                remove = false;
                break;
            case SculptureHiddenBays:
                setQuestStatusSculpture(SpecialEvent.StatusSculptureDone);
                if (getCommander().getShip().FreeSlotsGadget() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentHiddenCompartments);
                    getCommander().getShip().AddEquipment(Consts.Gadgets[GadgetType.HiddenCargoBays.castToInt()]);
                    setQuestStatusSculpture(SpecialEvent.StatusSculptureDone);
                }
                break;
            case Skill:
                GuiFacade.alert(AlertType.SpecialSkillIncrease);
                getCommander().IncreaseRandomSkill();
                break;
            case SpaceMonster:
                setQuestStatusSpaceMonster(SpecialEvent.StatusSpaceMonsterAtAcamar);
                break;
            case SpaceMonsterKilled:
                setQuestStatusSpaceMonster(SpecialEvent.StatusSpaceMonsterDone);
                break;
            case Tribble:
                GuiFacade.alert(AlertType.TribblesOwn);
                getCommander().getShip().setTribbles(1);
                break;
            case TribbleBuyer:
                GuiFacade.alert(AlertType.TribblesGone);
                getCommander().setCash(getCommander().getCash() + (getCommander().getShip().getTribbles() / 2));
                getCommander().getShip().setTribbles(0);
                break;
            case Wild:
                if (getCommander().getShip().FreeCrewQuarters() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                    remove = false;
                } else if (!getCommander().getShip().HasWeapon(WeaponType.BeamLaser, false)) {
                    GuiFacade.alert(AlertType.WildWontBoardLaser);
                    remove = false;
                } else if (getCommander().getShip().ReactorOnBoard()) {
                    GuiFacade.alert(AlertType.WildWontBoardReactor);
                    remove = false;
                } else {
                    CrewMember wild = Mercenaries()[CrewMemberId.Wild.castToInt()];
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, wild.Name());
                    getCommander().getShip().Hire(wild);
                    setQuestStatusWild(SpecialEvent.StatusWildStarted);

                    if (getCommander().getShip().SculptureOnBoard())
                        GuiFacade.alert(AlertType.WildSculpture);
                }
                break;
            case WildGetsOut:
                // Zeethibal has a 10 in player's lowest score, an 8
                // in the next lowest score, and 5 elsewhere.
                CrewMember zeethibal = Mercenaries()[CrewMemberId.Zeethibal.castToInt()];
                zeethibal.setCurrentSystem(Universe()[StarSystemId.Kravat.castToInt()]);
                int lowest1 = getCommander().NthLowestSkill(1);
                int lowest2 = getCommander().NthLowestSkill(2);
                for (int i = 0; i < zeethibal.Skills().length; i++)
                    zeethibal.Skills()[i] = (i == lowest1 ? 10 : (i == lowest2 ? 8 : 5));

                setQuestStatusWild(SpecialEvent.StatusWildDone);
                getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreClean);
                getCommander().getShip().Fire(CrewMemberId.Wild);
                RecalculateSellPrices(curSys);
                break;
        }

        if (curSys.specialEvent().price() != 0)
            getCommander().setCash(getCommander().getCash() - curSys.specialEvent().price());

        if (remove)
            curSys.setSpecialEventType(SpecialEventType.NA);
    }

    public void IncDays(int num) {
        getCommander().setDays(getCommander().getDays() + num);

        if (getCommander().getInsurance())
            getCommander().NoClaim(getCommander().NoClaim() + num);

        // Police Record will gravitate towards neutral (0).
        if (getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreClean)
            getCommander().setPoliceRecordScore(
                    Math.max(Consts.PoliceRecordScoreClean, getCommander().getPoliceRecordScore() - num / 3));
        else if (getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious)
            getCommander().setPoliceRecordScore(
                    Math.min(Consts.PoliceRecordScoreDubious, getCommander().getPoliceRecordScore()
                            + num
                            / (Difficulty().castToInt() <= spacetrader.game.enums.Difficulty.Normal.castToInt() ? 1
                            : (int) Difficulty().castToInt())));

        // The Space Monster's strength increases 5% per day until it is back to
        // full strength.
        if (SpaceMonster().getHull() < SpaceMonster().getHullStrength())
            SpaceMonster().setHull(
                    Math.min(SpaceMonster().getHullStrength(), (int) (SpaceMonster().getHull() * Math.pow(1.05, num))));

        if (getQuestStatusGemulon() > SpecialEvent.StatusGemulonNotStarted
                && getQuestStatusGemulon() < SpecialEvent.StatusGemulonTooLate) {
            setQuestStatusGemulon(Math.min(getQuestStatusGemulon() + num, SpecialEvent.StatusGemulonTooLate));
            if (getQuestStatusGemulon() == SpecialEvent.StatusGemulonTooLate) {
                StarSystem gemulon = Universe()[StarSystemId.Gemulon.castToInt()];
                gemulon.setSpecialEventType(SpecialEventType.GemulonInvaded);
                gemulon.setTechLevel(TechLevel.PreAgricultural);
                gemulon.PoliticalSystemType(PoliticalSystemType.Anarchy);
            }
        }

        if (getCommander().getShip().ReactorOnBoard())
            setQuestStatusReactor(Math.min(getQuestStatusReactor() + num, SpecialEvent.StatusReactorDate));

        if (getQuestStatusExperiment() > SpecialEvent.StatusExperimentNotStarted
                && getQuestStatusExperiment() < SpecialEvent.StatusExperimentPerformed) {
            setQuestStatusExperiment(Math.min(getQuestStatusExperiment() + num, SpecialEvent.StatusExperimentPerformed));
            if (getQuestStatusExperiment() == SpecialEvent.StatusExperimentPerformed) {
                setFabricRipProbability(Consts.FabricRipInitialProbability);
                Universe()[StarSystemId.Daled.castToInt()].setSpecialEventType(SpecialEventType.ExperimentFailed);
                GuiFacade.alert(AlertType.SpecialExperimentPerformed);
                NewsAddEvent(NewsEvent.ExperimentPerformed);
            }
        } else if (getQuestStatusExperiment() == SpecialEvent.StatusExperimentPerformed
                && getFabricRipProbability() > 0)
            setFabricRipProbability(getFabricRipProbability() - num);

        if (getCommander().getShip().JarekOnBoard()) {
            if (getQuestStatusJarek() == SpecialEvent.StatusJarekImpatient / 2)
                GuiFacade.alert(AlertType.SpecialPassengerConcernedJarek);
            else if (getQuestStatusJarek() == SpecialEvent.StatusJarekImpatient - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientJarek);
                Mercenaries()[CrewMemberId.Jarek.castToInt()].Pilot(0);
                Mercenaries()[CrewMemberId.Jarek.castToInt()].Fighter(0);
                Mercenaries()[CrewMemberId.Jarek.castToInt()].Trader(0);
                Mercenaries()[CrewMemberId.Jarek.castToInt()].Engineer(0);
            }

            if (getQuestStatusJarek() < SpecialEvent.StatusJarekImpatient)
                setQuestStatusJarek(getQuestStatusJarek() + 1);
        }

        if (getCommander().getShip().PrincessOnBoard()) {
            if (getQuestStatusPrincess() == (SpecialEvent.StatusPrincessImpatient + SpecialEvent.StatusPrincessRescued) / 2)
                GuiFacade.alert(AlertType.SpecialPassengerConcernedPrincess);
            else if (getQuestStatusPrincess() == SpecialEvent.StatusPrincessImpatient - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientPrincess);
                Mercenaries()[CrewMemberId.Princess.castToInt()].Pilot(0);
                Mercenaries()[CrewMemberId.Princess.castToInt()].Fighter(0);
                Mercenaries()[CrewMemberId.Princess.castToInt()].Trader(0);
                Mercenaries()[CrewMemberId.Princess.castToInt()].Engineer(0);
            }

            if (getQuestStatusPrincess() < SpecialEvent.StatusPrincessImpatient)
                setQuestStatusPrincess(getQuestStatusPrincess() + 1);
        }

        if (getCommander().getShip().WildOnBoard()) {
            if (getQuestStatusWild() == SpecialEvent.StatusWildImpatient / 2)
                GuiFacade.alert(AlertType.SpecialPassengerConcernedWild);
            else if (getQuestStatusWild() == SpecialEvent.StatusWildImpatient - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientWild);
                Mercenaries()[CrewMemberId.Wild.castToInt()].Pilot(0);
                Mercenaries()[CrewMemberId.Wild.castToInt()].Fighter(0);
                Mercenaries()[CrewMemberId.Wild.castToInt()].Trader(0);
                Mercenaries()[CrewMemberId.Wild.castToInt()].Engineer(0);
            }

            if (getQuestStatusWild() < SpecialEvent.StatusWildImpatient)
                setQuestStatusWild(getQuestStatusWild() + 1);
        }
    }

    private Commander InitializeCommander(String name, CrewMember commanderCrewMember) {
        Commander commander = new Commander(commanderCrewMember);
        Mercenaries()[CrewMemberId.Commander.castToInt()] = commander;
        Strings.CrewMemberNames[CrewMemberId.Commander.castToInt()] = name;

        while (commander.getCurrentSystem() == null) {
            StarSystem system = Universe()[Functions.GetRandom(Universe().length)];
            if (system.getSpecialEventType() == SpecialEventType.NA
                    && system.getTechLevel().castToInt() > TechLevel.PreAgricultural.castToInt()
                    && system.getTechLevel().castToInt() < TechLevel.HiTech.castToInt()) {
                // Make sure at least three other systems can be reached
                int close = 0;
                for (int i = 0; i < Universe().length && close < 3; i++) {
                    if (i != system.Id().castToInt()
                            && Functions.Distance(Universe()[i], system) <= commander.getShip().getFuelTanks())
                        close++;
                }

                if (close >= 3)
                    commander.setCurrentSystem(system);
            }
        }

        commander.getCurrentSystem().Visited(true);
        return commander;
    }

    public void NewsAddEvent(NewsEvent newEvent) {
        NewsEvents().add(newEvent);
    }

    public void NewsAddEventsOnArrival() {
        if (getCommander().getCurrentSystem().getSpecialEventType() != SpecialEventType.NA) {
            switch (getCommander().getCurrentSystem().getSpecialEventType()) {
                case ArtifactDelivery:
                    if (getCommander().getShip().ArtifactOnBoard())
                        NewsAddEvent(NewsEvent.ArtifactDelivery);
                    break;
                case Dragonfly:
                    NewsAddEvent(NewsEvent.Dragonfly);
                    break;
                case DragonflyBaratas:
                    if (getQuestStatusDragonfly() == SpecialEvent.StatusDragonflyFlyBaratas)
                        NewsAddEvent(NewsEvent.DragonflyBaratas);
                    break;
                case DragonflyDestroyed:
                    if (getQuestStatusDragonfly() == SpecialEvent.StatusDragonflyFlyZalkon)
                        NewsAddEvent(NewsEvent.DragonflyZalkon);
                    else if (getQuestStatusDragonfly() == SpecialEvent.StatusDragonflyDestroyed)
                        NewsAddEvent(NewsEvent.DragonflyDestroyed);
                    break;
                case DragonflyMelina:
                    if (getQuestStatusDragonfly() == SpecialEvent.StatusDragonflyFlyMelina)
                        NewsAddEvent(NewsEvent.DragonflyMelina);
                    break;
                case DragonflyRegulas:
                    if (getQuestStatusDragonfly() == SpecialEvent.StatusDragonflyFlyRegulas)
                        NewsAddEvent(NewsEvent.DragonflyRegulas);
                    break;
                case ExperimentFailed:
                    NewsAddEvent(NewsEvent.ExperimentFailed);
                    break;
                case ExperimentStopped:
                    if (getQuestStatusExperiment() > SpecialEvent.StatusExperimentNotStarted
                            && getQuestStatusExperiment() < SpecialEvent.StatusExperimentPerformed)
                        NewsAddEvent(NewsEvent.ExperimentStopped);
                    break;
                case Gemulon:
                    NewsAddEvent(NewsEvent.Gemulon);
                    break;
                case GemulonRescued:
                    if (getQuestStatusGemulon() > SpecialEvent.StatusGemulonNotStarted) {
                        if (getQuestStatusGemulon() < SpecialEvent.StatusGemulonTooLate)
                            NewsAddEvent(NewsEvent.GemulonRescued);
                        else
                            NewsAddEvent(NewsEvent.GemulonInvaded);
                    }
                    break;
                case Japori:
                    if (getQuestStatusJapori() == SpecialEvent.StatusJaporiNotStarted)
                        NewsAddEvent(NewsEvent.Japori);
                    break;
                case JaporiDelivery:
                    if (getQuestStatusJapori() == SpecialEvent.StatusJaporiInTransit)
                        NewsAddEvent(NewsEvent.JaporiDelivery);
                    break;
                case JarekGetsOut:
                    if (getCommander().getShip().JarekOnBoard())
                        NewsAddEvent(NewsEvent.JarekGetsOut);
                    break;
                case Princess:
                    NewsAddEvent(NewsEvent.Princess);
                    break;
                case PrincessCentauri:
                    if (getQuestStatusPrincess() == SpecialEvent.StatusPrincessFlyCentauri)
                        NewsAddEvent(NewsEvent.PrincessCentauri);
                    break;
                case PrincessInthara:
                    if (getQuestStatusPrincess() == SpecialEvent.StatusPrincessFlyInthara)
                        NewsAddEvent(NewsEvent.PrincessInthara);
                    break;
                case PrincessQonos:
                    if (getQuestStatusPrincess() == SpecialEvent.StatusPrincessFlyQonos)
                        NewsAddEvent(NewsEvent.PrincessQonos);
                    else if (getQuestStatusPrincess() == SpecialEvent.StatusPrincessRescued)
                        NewsAddEvent(NewsEvent.PrincessRescued);
                    break;
                case PrincessReturned:
                    if (getQuestStatusPrincess() == SpecialEvent.StatusPrincessReturned)
                        NewsAddEvent(NewsEvent.PrincessReturned);
                    break;
                case Scarab:
                    NewsAddEvent(NewsEvent.Scarab);
                    break;
                case ScarabDestroyed:
                    if (getQuestStatusScarab() == SpecialEvent.StatusScarabHunting)
                        NewsAddEvent(NewsEvent.ScarabHarass);
                    else if (getQuestStatusScarab() >= SpecialEvent.StatusScarabDestroyed)
                        NewsAddEvent(NewsEvent.ScarabDestroyed);
                    break;
                case Sculpture:
                    NewsAddEvent(NewsEvent.SculptureStolen);
                    break;
                case SculptureDelivered:
                    NewsAddEvent(NewsEvent.SculptureTracked);
                    break;
                case SpaceMonsterKilled:
                    if (getQuestStatusSpaceMonster() == SpecialEvent.StatusSpaceMonsterAtAcamar)
                        NewsAddEvent(NewsEvent.SpaceMonster);
                    else if (getQuestStatusSpaceMonster() >= SpecialEvent.StatusSpaceMonsterDestroyed)
                        NewsAddEvent(NewsEvent.SpaceMonsterKilled);
                    break;
                case WildGetsOut:
                    if (getCommander().getShip().WildOnBoard())
                        NewsAddEvent(NewsEvent.WildGetsOut);
                    break;
            }
        }
    }

    public NewsEvent NewsLatestEvent() {
        return (NewsEvent) NewsEvents().get(NewsEvents().size() - 1);
    }

    public void NewsReplaceEvent(NewsEvent oldEvent, NewsEvent newEvent) {
        if (NewsEvents().indexOf(oldEvent) >= 0)
            NewsEvents().remove(oldEvent);
        NewsEvents().add(newEvent);
    }

    public void NewsResetEvents() {
        NewsEvents().clear();
    }

    private void NormalDeparture(int fuel) {
        getCommander().setCash(getCommander().getCash() - (MercenaryCosts() + InsuranceCosts() + WormholeCosts()));
        getCommander().getShip().setFuel(getCommander().getShip().getFuel() - fuel);
        getCommander().PayInterest();
        IncDays(1);
    }

    private boolean PlaceShipyards() {
        boolean goodUniverse = true;

        ArrayList systemIdList = new ArrayList();
        for (int system = 0; system < Universe().length; system++) {
            if (Universe()[system].getTechLevel() == TechLevel.HiTech)
                systemIdList.add(system);
        }

        if (systemIdList.size() < Consts.Shipyards.length)
            goodUniverse = false;
        else {
            // Assign the shipyards to High-Tech systems.
            for (int shipyard = 0; shipyard < Consts.Shipyards.length; shipyard++)
                Universe()[(Integer) systemIdList.get(Functions.GetRandom(systemIdList.size()))].shipyardId(ShipyardId
                        .fromInt(shipyard));
        }

        return goodUniverse;
    }

    private boolean PlaceSpecialEvents() {
        boolean goodUniverse = true;
        int system;

        Universe()[StarSystemId.Baratas.castToInt()].setSpecialEventType(SpecialEventType.DragonflyBaratas);
        Universe()[StarSystemId.Melina.castToInt()].setSpecialEventType(SpecialEventType.DragonflyMelina);
        Universe()[StarSystemId.Regulas.castToInt()].setSpecialEventType(SpecialEventType.DragonflyRegulas);
        Universe()[StarSystemId.Zalkon.castToInt()].setSpecialEventType(SpecialEventType.DragonflyDestroyed);
        Universe()[StarSystemId.Daled.castToInt()].setSpecialEventType(SpecialEventType.ExperimentStopped);
        Universe()[StarSystemId.Gemulon.castToInt()].setSpecialEventType(SpecialEventType.GemulonRescued);
        Universe()[StarSystemId.Japori.castToInt()].setSpecialEventType(SpecialEventType.JaporiDelivery);
        Universe()[StarSystemId.Devidia.castToInt()].setSpecialEventType(SpecialEventType.JarekGetsOut);
        Universe()[StarSystemId.Utopia.castToInt()].setSpecialEventType(SpecialEventType.MoonRetirement);
        Universe()[StarSystemId.Nix.castToInt()].setSpecialEventType(SpecialEventType.ReactorDelivered);
        Universe()[StarSystemId.Acamar.castToInt()].setSpecialEventType(SpecialEventType.SpaceMonsterKilled);
        Universe()[StarSystemId.Kravat.castToInt()].setSpecialEventType(SpecialEventType.WildGetsOut);
        Universe()[StarSystemId.Endor.castToInt()].setSpecialEventType(SpecialEventType.SculptureDelivered);
        Universe()[StarSystemId.Galvon.castToInt()].setSpecialEventType(SpecialEventType.Princess);
        Universe()[StarSystemId.Centauri.castToInt()].setSpecialEventType(SpecialEventType.PrincessCentauri);
        Universe()[StarSystemId.Inthara.castToInt()].setSpecialEventType(SpecialEventType.PrincessInthara);
        Universe()[StarSystemId.Qonos.castToInt()].setSpecialEventType(SpecialEventType.PrincessQonos);

        // Assign a wormhole location endpoint for the Scarab.
        for (system = 0; system < Wormholes().length
                && Universe()[Wormholes()[system]].getSpecialEventType() != SpecialEventType.NA; system++)
            ;
        if (system < Wormholes().length)
            Universe()[Wormholes()[system]].setSpecialEventType(SpecialEventType.ScarabDestroyed);
        else
            goodUniverse = false;

        // Find a Hi-Tech system without a special event.
        if (goodUniverse) {
            for (system = 0; system < Universe().length
                    && !(Universe()[system].getSpecialEventType() == SpecialEventType.NA && Universe()[system].getTechLevel() == TechLevel.HiTech); system++)
                ;
            if (system < Universe().length)
                Universe()[system].setSpecialEventType(SpecialEventType.ArtifactDelivery);
            else
                goodUniverse = false;
        }

        // Find the closest system at least 70 parsecs away from Nix that
        // doesn't already have a special event.
        if (goodUniverse && !FindDistantSystem(StarSystemId.Nix, SpecialEventType.Reactor))
            goodUniverse = false;

        // Find the closest system at least 70 parsecs away from Gemulon that
        // doesn't already have a special event.
        if (goodUniverse && !FindDistantSystem(StarSystemId.Gemulon, SpecialEventType.Gemulon))
            goodUniverse = false;

        // Find the closest system at least 70 parsecs away from Daled that
        // doesn't already have a special event.
        if (goodUniverse && !FindDistantSystem(StarSystemId.Daled, SpecialEventType.Experiment))
            goodUniverse = false;

        // Find the closest system at least 70 parsecs away from Endor that
        // doesn't already have a special event.
        if (goodUniverse && !FindDistantSystem(StarSystemId.Endor, SpecialEventType.Sculpture))
            goodUniverse = false;

        // Assign the rest of the events randomly.
        if (goodUniverse) {
            for (int i = 0; i < Consts.SpecialEvents.length; i++) {
                for (int j = 0; j < Consts.SpecialEvents[i].Occurrence(); j++) {
                    do {
                        system = Functions.GetRandom(Universe().length);
                    } while (Universe()[system].getSpecialEventType() != SpecialEventType.NA);

                    Universe()[system].setSpecialEventType(Consts.SpecialEvents[i].Type());
                }
            }
        }

        return goodUniverse;
    }

    public void RecalculateBuyPrices(StarSystem system) {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (!system.itemTraded(Consts.TradeItems[i]))
                _priceCargoBuy[i] = 0;
            else {
                _priceCargoBuy[i] = _priceCargoSell[i];

                if (getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious)
                    _priceCargoBuy[i] = _priceCargoBuy[i] * 100 / 90;

                // BuyPrice = SellPrice + 1 to 12% (depending on trader skill
                // (minimum is 1, max 12))
                _priceCargoBuy[i] = _priceCargoBuy[i] * (103 + Consts.MaxSkill - getCommander().getShip().Trader()) / 100;

                if (_priceCargoBuy[i] <= _priceCargoSell[i])
                    _priceCargoBuy[i] = _priceCargoSell[i] + 1;
            }
        }
    }

    // *************************************************************************
    // After erasure of police record, selling prices must be recalculated
    // *************************************************************************
    public void RecalculateSellPrices(StarSystem system) {
        for (int i = 0; i < Consts.TradeItems.length; i++)
            _priceCargoSell[i] = _priceCargoSell[i] * 100 / 90;
    }

    public void ResetVeryRareEncounters() {
        VeryRareEncounters().clear();
        VeryRareEncounters().add(VeryRareEncounter.MarieCeleste);
        VeryRareEncounters().add(VeryRareEncounter.CaptainAhab);
        VeryRareEncounters().add(VeryRareEncounter.CaptainConrad);
        VeryRareEncounters().add(VeryRareEncounter.CaptainHuie);
        VeryRareEncounters().add(VeryRareEncounter.BottleOld);
        VeryRareEncounters().add(VeryRareEncounter.BottleGood);
    }

    public void SelectNextSystemWithinRange(boolean forward) {
        int[] dest = Destinations();

        if (dest.length > 0) {
            int index = spacetrader.util.Util.BruteSeek(dest, getWarpSystemId().castToInt());

            if (index < 0)
                index = forward ? 0 : dest.length - 1;
            else
                index = (dest.length + index + (forward ? 1 : -1)) % dest.length;

            if (Functions.WormholeExists(getCommander().getCurrentSystem(), Universe()[dest[index]])) {
                SelectedSystemId(getCommander().getCurrentSystemId());
                TargetWormhole(true);
            } else
                SelectedSystemId(StarSystemId.fromInt(dest[index]));
        }
    }

    public void showNewspaper() {
        if (!getPaidForNewspaper()) {
            int cost = Difficulty().castToInt() + 1;

            if (getCommander().getCash() < cost)
                GuiFacade.alert(AlertType.ArrivalIFNewspaper, Functions.multiples(cost, "credit"));
            else if (Options().getNewsAutoPay()
                    || GuiFacade.alert(AlertType.ArrivalBuyNewspaper, Functions.multiples(cost, "credit")) == DialogResult.YES) {
                getCommander().setCash(getCommander().getCash() - cost);
                setPaidForNewspaper(true);
                getParentWindow().updateAll();
            }
        }

        if (getPaidForNewspaper())
            GuiFacade.alert(AlertType.Alert, NewspaperHead(), NewspaperText());
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_version", "2.00");
        hash.add("_universe", ArrayToArrayList(_universe));
        hash.add("_commander", _commander.serialize());
        hash.add("_wormholes", _wormholes);
        hash.add("_mercenaries", ArrayToArrayList(_mercenaries));
        hash.add("_dragonfly", _dragonfly.serialize());
        hash.add("_scarab", _scarab.serialize());
        hash.add("_scorpion", _scorpion.serialize());
        hash.add("_spaceMonster", _spaceMonster.serialize());
        hash.add("_opponent", _opponent.serialize());
        hash.add("_chanceOfTradeInOrbit", _chanceOfTradeInOrbit);
        hash.add("_clicks", _clicks);
        hash.add("_raided", _raided);
        hash.add("_inspected", _inspected);
        hash.add("_tribbleMessage", _tribbleMessage);
        hash.add("_arrivedViaWormhole", _arrivedViaWormhole);
        hash.add("_paidForNewspaper", _paidForNewspaper);
        hash.add("_litterWarning", _litterWarning);
        hash.add("_newsEvents", ArrayListToIntArray(_newsEvents));
        hash.add("_difficulty", _difficulty.castToInt());
        hash.add("_cheatEnabled", _cheats.cheatMode);
        hash.add("_autoSave", _autoSave);
        hash.add("_easyEncounters", _easyEncounters);
        hash.add("_endStatus", _endStatus.castToInt());
        hash.add("_encounterType", _encounterType.castToInt());
        hash.add("_selectedSystemId", _selectedSystemId.castToInt());
        hash.add("_warpSystemId", _warpSystemId.castToInt());
        hash.add("_trackedSystemId", _trackedSystemId.castToInt());
        hash.add("_targetWormhole", _targetWormhole);
        hash.add("_priceCargoBuy", _priceCargoBuy);
        hash.add("_priceCargoSell", _priceCargoSell);
        hash.add("_questStatusArtifact", _questStatusArtifact);
        hash.add("_questStatusDragonfly", _questStatusDragonfly);
        hash.add("_questStatusExperiment", _questStatusExperiment);
        hash.add("_questStatusGemulon", _questStatusGemulon);
        hash.add("_questStatusJapori", _questStatusJapori);
        hash.add("_questStatusJarek", _questStatusJarek);
        hash.add("_questStatusMoon", _questStatusMoon);
        hash.add("_questStatusPrincess", _questStatusPrincess);
        hash.add("_questStatusReactor", _questStatusReactor);
        hash.add("_questStatusScarab", _questStatusScarab);
        hash.add("_questStatusSculpture", _questStatusSculpture);
        hash.add("_questStatusSpaceMonster", _questStatusSpaceMonster);
        hash.add("_questStatusWild", _questStatusWild);
        hash.add("_fabricRipProbability", _fabricRipProbability);
        hash.add("_justLootedMarie", _justLootedMarie);
        hash.add("_canSuperWarp", _canSuperWarp);
        hash.add("_chanceOfVeryRareEncounter", _chanceOfVeryRareEncounter);
        hash.add("_veryRareEncounters", ArrayListToIntArray(_veryRareEncounters));
        hash.add("_options", _options.serialize());

        return hash;
    }

    // Returns true if an encounter occurred.
    public boolean Travel() {
        // if timespace is ripped, we may switch the warp system here.
        if (getQuestStatusExperiment() == SpecialEvent.StatusExperimentPerformed
                && getFabricRipProbability() > 0
                && (getFabricRipProbability() == Consts.FabricRipInitialProbability || Functions.GetRandom(100) < getFabricRipProbability())) {
            GuiFacade.alert(AlertType.SpecialTimespaceFabricRip);
            SelectedSystemId(StarSystemId.fromInt(Functions.GetRandom(Universe().length)));
        }

        boolean uneventful = true;
        setRaided(false);
        setInspected(false);
        setLitterWarning(false);

        setClicks(Consts.StartClicks);
        while (getClicks() > 0) {
            getCommander().getShip().PerformRepairs();

            if (DetermineEncounter()) {
                uneventful = false;

                EncounterResult result = GuiFacade.performEncounter(getParentWindow());
                getParentWindow().updateStatusBar();
                switch (result) {
                    case Arrested:
                        setClicks(0);
                        Arrested();
                        break;
                    case EscapePod:
                        setClicks(0);
                        EscapeWithPod();
                        break;
                    case Killed:
                        throw new GameEndException(GameEndType.Killed);
                }
            }

            setClicks(getClicks() - 1);
        }

        return !uneventful;
    }

    // #endregion

    // #region Properties

    public void Warp(boolean viaSingularity) {
        if (getCommander().getDebt() > Consts.DebtTooLarge)
            GuiFacade.alert(AlertType.DebtTooLargeGrounded);
        else if (getCommander().getCash() < MercenaryCosts())
            GuiFacade.alert(AlertType.LeavingIFMercenaries);
        else if (getCommander().getCash() < MercenaryCosts() + InsuranceCosts())
            GuiFacade.alert(AlertType.LeavingIFInsurance);
        else if (getCommander().getCash() < MercenaryCosts() + InsuranceCosts() + WormholeCosts())
            GuiFacade.alert(AlertType.LeavingIFWormholeTax);
        else {
            boolean wildOk = true;

            // if Wild is aboard, make sure ship is armed!
            if (getCommander().getShip().WildOnBoard() && !getCommander().getShip().HasWeapon(WeaponType.BeamLaser, false)) {
                if (GuiFacade.alert(AlertType.WildWontStayAboardLaser, getCommander().getCurrentSystem().name()) == DialogResult.CANCEL)
                    wildOk = false;
                else {
                    GuiFacade.alert(AlertType.WildLeavesShip, getCommander().getCurrentSystem().name());
                    setQuestStatusWild(SpecialEvent.StatusWildNotStarted);
                }
            }

            if (wildOk) {
                setArrivedViaWormhole(Functions.WormholeExists(getCommander().getCurrentSystem(), getWarpSystem()));

                if (viaSingularity)
                    NewsAddEvent(NewsEvent.ExperimentArrival);
                else
                    NormalDeparture(viaSingularity || getArrivedViaWormhole() ? 0 : Functions.Distance(getCommander()
                            .getCurrentSystem(), getWarpSystem()));

                getCommander().getCurrentSystem().CountDown(CountDownStart());

                NewsResetEvents();

                CalculatePrices(getWarpSystem());

                if (Travel()) {
                    // Clicks will be -1 if we were arrested or used the escape
                    // pod.
                    /*
                     * if (Clicks == 0) FormAlert.Alert(AlertType.TravelArrival, ParentWindow);
                     */
                } else
                    GuiFacade.alert(AlertType.TravelUneventfulTrip);

                Arrival();
            }
        }
    }

    @CheatCode
    public void WarpDirect() {
        _warpSystemId = SelectedSystemId();

        getCommander().getCurrentSystem().CountDown(CountDownStart());
        NewsResetEvents();
        CalculatePrices(getWarpSystem());
        Arrival();
    }

    public Commander getCommander() {
        return _commander;
    }

    public int CountDownStart() {
        return Difficulty().castToInt() + 3;
    }

    public int CurrentCosts() {
        return InsuranceCosts() + InterestCosts() + MercenaryCosts() + WormholeCosts();
    }

    public int[] Destinations() {
        ArrayList list = new ArrayList();

        for (int i = 0; i < Universe().length; i++)
            if (Universe()[i].destOk())
                list.add(i);

        int[] ids = new int[list.size()];
        for (int i = 0; i < ids.length; i++)
            ids[i] = (Integer) list.get(i);

        return ids;
    }

    public Difficulty Difficulty() {
        return _difficulty;
    }

    public Ship Dragonfly() {
        return _dragonfly;
    }

    public String EncounterAction() {
        String action = "";

        if (getOpponentDisabled())
            action = Functions.stringVars(Strings.EncounterActionOppDisabled, EncounterShipText());
        else if (getEncounterOppFleeing()) {
            if (getEncounterType() == spacetrader.game.enums.EncounterType.PirateSurrender
                    || getEncounterType() == spacetrader.game.enums.EncounterType.TraderSurrender)
                action = Functions.stringVars(Strings.EncounterActionOppSurrender, EncounterShipText());
            else
                action = Functions.stringVars(Strings.EncounterActionOppFleeing, EncounterShipText());
        } else
            action = Functions.stringVars(Strings.EncounterActionOppAttacks, EncounterShipText());

        return action;
    }

    public String EncounterActionInitial() {
        String text = "";

        // Set up the fleeing variable initially.
        setEncounterOppFleeing(false);

        switch (getEncounterType()) {
            case BottleGood:
            case BottleOld:
                text = Strings.EncounterTextBottle;
                break;
            case CaptainAhab:
            case CaptainConrad:
            case CaptainHuie:
                text = Strings.EncounterTextFamousCaptain;
                break;
            case DragonflyAttack:
            case PirateAttack:
            case PoliceAttack:
            case ScarabAttack:
            case ScorpionAttack:
            case SpaceMonsterAttack:
                text = Strings.EncounterTextOpponentAttack;
                break;
            case DragonflyIgnore:
            case PirateIgnore:
            case PoliceIgnore:
            case ScarabIgnore:
            case ScorpionIgnore:
            case SpaceMonsterIgnore:
            case TraderIgnore:
                text = getCommander().getShip().Cloaked() ? Strings.EncounterTextOpponentNoNotice
                        : Strings.EncounterTextOpponentIgnore;
                break;
            case MarieCeleste:
                text = Strings.EncounterTextMarieCeleste;
                break;
            case MarieCelestePolice:
                text = Strings.EncounterTextPolicePostMarie;
                break;
            case PirateFlee:
            case PoliceFlee:
            case TraderFlee:
                text = Strings.EncounterTextOpponentFlee;
                setEncounterOppFleeing(true);
                break;
            case PoliceInspect:
                text = Strings.EncounterTextPoliceInspection;
                break;
            case PoliceSurrender:
                text = Strings.EncounterTextPoliceSurrender;
                break;
            case TraderBuy:
            case TraderSell:
                text = Strings.EncounterTextTrader;
                break;
            case FamousCaptainAttack:
            case FamousCaptDisabled:
            case PoliceDisabled:
            case PirateDisabled:
            case PirateSurrender:
            case TraderAttack:
            case TraderDisabled:
            case TraderSurrender:
                // These should never be the initial encounter type.
                break;
        }

        return text;
    }

    public int EncounterImageIndex() {
        int encounterImage = -1;

        switch (getEncounterType()) {
            case BottleGood:
            case BottleOld:
            case CaptainAhab:
            case CaptainConrad:
            case CaptainHuie:
            case MarieCeleste:
                encounterImage = Consts.EncounterImgSpecial;
                break;
            case DragonflyAttack:
            case DragonflyIgnore:
            case ScarabAttack:
            case ScarabIgnore:
            case ScorpionAttack:
            case ScorpionIgnore:
                encounterImage = Consts.EncounterImgPirate;
                break;
            case MarieCelestePolice:
            case PoliceAttack:
            case PoliceFlee:
            case PoliceIgnore:
            case PoliceInspect:
            case PoliceSurrender:
                encounterImage = Consts.EncounterImgPolice;
                break;
            case PirateAttack:
            case PirateFlee:
            case PirateIgnore:
                if (getOpponent().Type() == ShipType.Mantis)
                    encounterImage = Consts.EncounterImgAlien;
                else
                    encounterImage = Consts.EncounterImgPirate;
                break;
            case SpaceMonsterAttack:
            case SpaceMonsterIgnore:
                encounterImage = Consts.EncounterImgAlien;
                break;
            case TraderBuy:
            case TraderFlee:
            case TraderIgnore:
            case TraderSell:
                encounterImage = Consts.EncounterImgTrader;
                break;
            case FamousCaptainAttack:
            case FamousCaptDisabled:
            case PoliceDisabled:
            case PirateDisabled:
            case PirateSurrender:
            case TraderAttack:
            case TraderDisabled:
            case TraderSurrender:
                // These should never be the initial encounter type.
                break;
        }

        return encounterImage;
    }

    public String EncounterShipText() {
        String shipText = getOpponent().Name();

        switch (getEncounterType()) {
            case FamousCaptainAttack:
            case FamousCaptDisabled:
                shipText = Strings.EncounterShipCaptain;
                break;
            case PirateAttack:
            case PirateDisabled:
            case PirateFlee:
            case PirateSurrender:
                shipText = getOpponent().Type() == ShipType.Mantis ? Strings.EncounterShipMantis
                        : Strings.EncounterShipPirate;
                break;
            case PoliceAttack:
            case PoliceDisabled:
            case PoliceFlee:
                shipText = Strings.EncounterShipPolice;
                break;
            case TraderAttack:
            case TraderDisabled:
            case TraderFlee:
            case TraderSurrender:
                shipText = Strings.EncounterShipTrader;
                break;
            default:
                break;
        }

        return shipText;
    }

    public String EncounterText() {
        String cmdrStatus = "";
        String oppStatus = "";

        if (getEncounterCmdrFleeing())
            cmdrStatus = Functions.stringVars(Strings.EncounterActionCmdrChased, EncounterShipText());
        else if (getEncounterOppHit())
            cmdrStatus = Functions.stringVars(Strings.EncounterActionOppHit, EncounterShipText());
        else
            cmdrStatus = Functions.stringVars(Strings.EncounterActionOppMissed, EncounterShipText());

        if (getEncounterOppFleeingPrev())
            oppStatus = Functions.stringVars(Strings.EncounterActionOppChased, EncounterShipText());
        else if (getEncounterCmdrHit())
            oppStatus = Functions.stringVars(Strings.EncounterActionCmdrHit, EncounterShipText());
        else
            oppStatus = Functions.stringVars(Strings.EncounterActionCmdrMissed, EncounterShipText());

        return cmdrStatus + Strings.newline + oppStatus;
    }

    public String EncounterTextInitial() {
        String encounterPretext = "";

        switch (getEncounterType()) {
            case BottleGood:
            case BottleOld:
                encounterPretext = Strings.EncounterPretextBottle;
                break;
            case DragonflyAttack:
            case DragonflyIgnore:
            case ScarabAttack:
            case ScarabIgnore:
                encounterPretext = Strings.EncounterPretextStolen;
                break;
            case CaptainAhab:
                encounterPretext = Strings.EncounterPretextCaptainAhab;
                break;
            case CaptainConrad:
                encounterPretext = Strings.EncounterPretextCaptainConrad;
                break;
            case CaptainHuie:
                encounterPretext = Strings.EncounterPretextCaptainHuie;
                break;
            case MarieCeleste:
                encounterPretext = Strings.EncounterPretextMarie;
                break;
            case MarieCelestePolice:
            case PoliceAttack:
            case PoliceFlee:
            case PoliceIgnore:
            case PoliceInspect:
            case PoliceSurrender:
                encounterPretext = Strings.EncounterPretextPolice;
                break;
            case PirateAttack:
            case PirateFlee:
            case PirateIgnore:
                if (getOpponent().Type() == ShipType.Mantis)
                    encounterPretext = Strings.EncounterPretextAlien;
                else
                    encounterPretext = Strings.EncounterPretextPirate;
                break;
            case ScorpionAttack:
            case ScorpionIgnore:
                encounterPretext = Strings.EncounterPretextScorpion;
                break;
            case SpaceMonsterAttack:
            case SpaceMonsterIgnore:
                encounterPretext = Strings.EncounterPretextSpaceMonster;
                break;
            case TraderBuy:
            case TraderFlee:
            case TraderIgnore:
            case TraderSell:
                encounterPretext = Strings.EncounterPretextTrader;
                break;
            case FamousCaptainAttack:
            case FamousCaptDisabled:
            case PoliceDisabled:
            case PirateDisabled:
            case PirateSurrender:
            case TraderAttack:
            case TraderDisabled:
            case TraderSurrender:
                // These should never be the initial encounter type.
                break;
        }

        return Functions.stringVars(Strings.EncounterText, new String[]{
                Functions.multiples(getClicks(), Strings.DistanceSubunit), getWarpSystem().name(), encounterPretext,
                getOpponent().Name().toLowerCase()});
    }

    public int InsuranceCosts() {
        return getCommander().getInsurance() ? (int) Math.max(1, getCommander().getShip().BaseWorth(true) * Consts.InsRate
                * (100 - getCommander().NoClaim()) / 100) : 0;
    }

    public int InterestCosts() {
        return getCommander().getDebt() > 0 ? (int) Math.max(1, getCommander().getDebt() * Consts.IntRate) : 0;
    }

    public int MercenaryCosts() {
        int total = 0;

        for (int i = 1; i < getCommander().getShip().Crew().length && getCommander().getShip().Crew()[i] != null; i++)
            total += getCommander().getShip().Crew()[i].Rate();

        return total;
    }

    public CrewMember[] Mercenaries() {
        return _mercenaries;
    }

    public ArrayList NewsEvents() {
        return _newsEvents;
    }

    public String NewspaperHead() {
        String[] heads = Strings.NewsMastheads[getCommander().getCurrentSystem().PoliticalSystemType().castToInt()];
        String head = heads[getCommander().getCurrentSystem().Id().castToInt() % heads.length];

        return Functions.stringVars(head, getCommander().getCurrentSystem().name());
    }

    public String NewspaperText() {
        StarSystem curSys = getCommander().getCurrentSystem();
        List items = new ArrayList();

        // We're //using the GetRandom2 function so that the same number is
        // generated each time for the same
        // "version" of the newspaper. -JAF
        Functions.RandSeed(curSys.Id().castToInt(), getCommander().getDays());

        for (Iterator en = NewsEvents().iterator(); en.hasNext(); )
            items.add(Functions.stringVars(Strings.NewsEvent[((spacetrader.game.enums.NewsEvent) en.next()).castToInt()],
                    new String[]{getCommander().Name(), getCommander().getCurrentSystem().name(),
                            getCommander().getShip().Name()}));

        if (curSys.getSystemPressure() != SystemPressure.None)
            items.add(Strings.NewsPressureInternal[curSys.getSystemPressure().castToInt()]);

        if (getCommander().getPoliceRecordScore() <= Consts.PoliceRecordScoreVillain) {
            String baseStr = Strings.NewsPoliceRecordPsychopath[Functions
                    .GetRandom2(Strings.NewsPoliceRecordPsychopath.length)];
            items.add(Functions.stringVars(baseStr, getCommander().Name(), curSys.name()));
        } else if (getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreHero) {
            String baseStr = Strings.NewsPoliceRecordHero[Functions.GetRandom2(Strings.NewsPoliceRecordHero.length)];
            items.add(Functions.stringVars(baseStr, getCommander().Name(), curSys.name()));
        }

        // and now, finally, useful news (if any)
        // base probability of a story showing up is (50 / MAXTECHLEVEL) *
        // Current Tech Level
        // This is then modified by adding 10% for every level of play less than
        // Impossible
        boolean realNews = false;
        int minProbability = Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10
                * (5 - Difficulty().castToInt());
        for (int i = 0; i < Universe().length; i++) {
            if (Universe()[i].destOk() && Universe()[i] != curSys) {
                // Special stories that always get shown: moon, millionaire,
                // shipyard
                if (Universe()[i].getSpecialEventType() != SpecialEventType.NA) {
                    if (Universe()[i].getSpecialEventType() == SpecialEventType.Moon)
                        items.add(Functions.stringVars(Strings.NewsMoonForSale, Universe()[i].name()));
                    else if (Universe()[i].getSpecialEventType() == SpecialEventType.TribbleBuyer)
                        items.add(Functions.stringVars(Strings.NewsTribbleBuyer, Universe()[i].name()));
                }
                if (Universe()[i].shipyardId() != ShipyardId.NA)
                    items.add(Functions.stringVars(Strings.NewsShipyard, Universe()[i].name()));

                // And not-always-shown stories
                if (Universe()[i].getSystemPressure() != SystemPressure.None
                        && Functions.GetRandom2(100) <= Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10
                        * (5 - Difficulty().castToInt())) {
                    int index = Functions.GetRandom2(Strings.NewsPressureExternal.length);
                    String baseStr = Strings.NewsPressureExternal[index];
                    String pressure = Strings.NewsPressureExternalPressures[Universe()[i].getSystemPressure().castToInt()];
                    items.add(Functions.stringVars(baseStr, pressure, Universe()[i].name()));
                    realNews = true;
                }
            }
        }

        // if there's no useful news, we throw up at least one
        // headline from our canned news list.
        if (!realNews) {
            String[] headlines = Strings.NewsHeadlines[curSys.PoliticalSystemType().castToInt()];
            boolean[] shown = new boolean[headlines.length];

            int toShow = Functions.GetRandom2(headlines.length);
            for (int i = 0; i <= toShow; i++) {
                int index = Functions.GetRandom2(headlines.length);
                if (!shown[index]) {
                    items.add(headlines[index]);
                    shown[index] = true;
                }
            }
        }

        return Util.StringsJoin(Strings.newline + Strings.newline, Functions.ArrayListtoStringArray(items));
    }

    public GameOptions Options() {
        return _options;
    }

    public int[] getPriceCargoBuy() {
        return _priceCargoBuy;
    }

    public int[] getPriceCargoSell() {
        return _priceCargoSell;
    }

    public Ship Scarab() {
        return _scarab;
    }

    public int Score() {
        int worth = getCommander().Worth() < 1000000 ? getCommander().Worth()
                : 1000000 + ((getCommander().Worth() - 1000000) / 10);
        int daysMoon = 0;
        int modifier = 0;

        switch (getEndStatus()) {
            case Killed:
                modifier = 90;
                break;
            case Retired:
                modifier = 95;
                break;
            case BoughtMoon:
                daysMoon = Math.max(0, (Difficulty().castToInt() + 1) * 100 - getCommander().getDays());
                modifier = 100;
                break;
        }

        return (Difficulty().castToInt() + 1) * modifier * (daysMoon * 1000 + worth) / 250000;
    }

    public Ship Scorpion() {
        return _scorpion;
    }

    public StarSystem SelectedSystem() {
        return (_selectedSystemId == StarSystemId.NA ? null : Universe()[_selectedSystemId.castToInt()]);
    }

    public StarSystemId SelectedSystemId() {
        return _selectedSystemId;
    }

    public void SelectedSystemId(StarSystemId value) {
        _selectedSystemId = value;
        _warpSystemId = value;
        _targetWormhole = false;
    }

    public void setSelectedSystemByName(String value) {
        boolean found = false;
        for (int i = 0; i < Universe().length && !found; i++) {
            String name = Universe()[i].name();
            if (name.toLowerCase().contains(value.toLowerCase())) {
                SelectedSystemId(StarSystemId.fromInt(i));
                found = true;
            }
        }
    }

    public Ship SpaceMonster() {
        return _spaceMonster;
    }

    public boolean TargetWormhole() {
        return _targetWormhole;
    }

    public void TargetWormhole(boolean value) {
        _targetWormhole = value;

        if (_targetWormhole) {
            int wormIndex = Util.BruteSeek(Wormholes(), SelectedSystemId().castToInt());
            _warpSystemId = StarSystemId.fromInt(Wormholes()[(wormIndex + 1) % Wormholes().length]);
        }
    }

    public StarSystem TrackedSystem() {
        return (_trackedSystemId == StarSystemId.NA ? null : Universe()[_trackedSystemId.castToInt()]);
    }

    public StarSystem[] Universe() {
        return _universe;
    }

    public ArrayList<VeryRareEncounter> VeryRareEncounters() {
        return _veryRareEncounters;
    }

    public StarSystem getWarpSystem() {
        return (_warpSystemId == StarSystemId.NA ? null : Universe()[_warpSystemId.castToInt()]);

    }

    public StarSystemId getWarpSystemId() {
        return _warpSystemId;
    }

    public int WormholeCosts() {
        return Functions.WormholeExists(getCommander().getCurrentSystem(), getWarpSystem()) ? Consts.WormDist
                * getCommander().getShip().getFuelCost() : 0;
    }

    public int[] Wormholes() {
        return _wormholes;
    }

    public boolean isShowTrackedRange() {
        return Options().getShowTrackedRange();
    }

    // #endregion
}
