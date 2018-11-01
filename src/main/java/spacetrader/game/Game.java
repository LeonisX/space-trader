package spacetrader.game;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.cheat.GameCheats;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.stub.ArrayList;
import spacetrader.game.cheat.CheatCode;
import spacetrader.util.Functions;
import spacetrader.util.Hashtable;
import spacetrader.util.Util;

import java.util.Arrays;
import java.util.List;

public class Game extends STSerializableObject implements SpaceTraderGame, SystemTracker, CurrentSystemMgr {

    private static Game game;
    private final Commander commander;
    @CheatCode
    private final GameCheats cheats;

    // Game Data
    private StarSystem[] universe;
    private int[] wormholes = new int[6];
    private CrewMember[] mercenaries = new CrewMember[Strings.CrewMemberNames.length];
    private Ship dragonfly = new Ship(ShipType.DRAGONFLY);
    private Ship scarab = new Ship(ShipType.SCARAB);
    private Ship scorpion = new Ship(ShipType.SCORPION);
    private Ship spaceMonster = new Ship(ShipType.SPACE_MONSTER);
    private Ship opponent = new Ship(ShipType.GNAT);
    private boolean opponentDisabled = false;
    private int chanceOfTradeInOrbit = 100;
    private int clicks = 0; // Distance from target system, 0 = arrived
    private boolean raided = false; // True when the commander has been raided during the trip
    private boolean inspected = false; // True when the commander has been/ inspected during the trip
    private boolean tribbleMessage = false; // Is true if the Ship Yard on the current system informed you about the tribbles
    private boolean arrivedViaWormhole = false; // flag to indicate whether player arrived on current planet via wormhole
    private boolean paidForNewspaper = false; // once you buy a paper on a system, you don't have to pay again.
    private boolean litterWarning = false; // Warning against littering has been issued.
    private ArrayList<NewsEvent> newsEvents = new ArrayList<>(30); // Current Selections
    private Difficulty difficulty; // Difficulty.NORMAL
    private boolean autoSave = false;
    private boolean easyEncounters = false;
    private GameEndType endStatus = GameEndType.NA;
    private EncounterType encounterType = EncounterType.fromInt(0); // Type of current encounter
    private StarSystemId selectedSystemId = StarSystemId.NA; // Current system on chart
    private StarSystemId warpSystemId = StarSystemId.NA; // Target system for warp
    private StarSystemId trackedSystemId = StarSystemId.NA; // The short-range chart will display an arrow towards
    // this system if the value is not null
    private boolean targetWormhole = false; // Wormhole selected?
    private int[] priceCargoBuy = new int[10];
    private int[] priceCargoSell = new int[10]; // Status of Quests
    private int questStatusArtifact = 0; // 0 = not given yet, 1 = Artifact on board, 2 = Artifact no longer on
    // board (either delivered or lost)
    private int questStatusDragonfly = 0; // 0 = not available, 1 = Go to Baratas, 2 = Go to Melina, 3 = Go
    // to Regulas, 4 = Go to Zalkon, 5 = Dragonfly destroyed, 6 = Got Shield
    private int questStatusExperiment = 0; // 0 = not given yet, 1-11 = days from start; 12 = performed, 13 = cancelled
    private int questStatusGemulon = 0; // 0 = not given yet, 1-7 = days from start, 8 = too late, 9 = in time, 10 = done
    private int questStatusJapori = 0; // 0 = no disease, 1 = Go to Japori (always at least 10 medicine
    // canisters), 2 = Assignment finished or canceled
    private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered
    private int questStatusMoon = 0; // 0 = not bought, 1 = bought, 2 = claimed
    private int questStatusPrincess = 0; // 0 = not available, 1 = Go to Centauri, 2 = Go to Inthara, 3 =
    // Go to Qonos, 4 = Princess Rescued, 5-14 = On Board, 15 = Princess Returned, 16 = Got Quantum Disruptor
    private int questStatusReactor = 0; // 0 = not encountered, 1-20 = days of mission (bays of fuel left = 10 -
    // (ReactorStatus / 2), 21 = delivered, 22 = Done
    private int questStatusScarab = 0; // 0 = not given yet, 1 = not destroyed, 2 = destroyed - upgrade not
    // performed, 3 = destroyed - hull upgrade performed
    private int questStatusSculpture = 0; // 0 = not given yet, 1 = on board, 2 = delivered, 3 = done
    private int questStatusSpaceMonster = 0; // 0 = not available, 1 = Space monster is in Acamar system,
    // 2 = Space monster is destroyed, 3 = Claimed reward
    private int questStatusWild = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered
    private int fabricRipProbability = 0; // if Experiment = 12, this is the probability of being warped to a random planet.
    private boolean justLootedMarie = false; // flag to indicate whether player looted Marie Celeste
    private boolean canSuperWarp = false; // Do you have the Portable Singularity on board?
    private int chanceOfVeryRareEncounter = 5; // Rare encounters not done yet.
    private ArrayList<VeryRareEncounter> veryRareEncounters = new ArrayList<>(6); // Array of Very Options
    private GameOptions options = new GameOptions(true); // The rest of the member variables are not saved between games.
    private MainWindow parentWin;
    private boolean encounterContinueFleeing = false;
    private boolean encounterContinueAttacking = false;
    private boolean encounterCmdrFleeing = false;
    private boolean encounterCmdrHit = false;
    private boolean encounterOppFleeingPrev = false;
    private boolean encounterOppFleeing = false;
    private boolean encounterOppHit = false;

    public Game(String name, Difficulty difficulty, int pilot, int fighter, int trader, int engineer,
                MainWindow parentWin) {
        game = this;
        this.parentWin = parentWin;
        this.difficulty = difficulty;

        // Keep Generating a new universe until isPlaceSpecialEvents and isPlaceShipyards return true,
        // indicating all special events and shipyards were placed.
        do {
            generateUniverse();
        } while (!(isPlaceSpecialEvents() && isPlaceShipyards()));

        commander = initializeCommander(name, new CrewMember(CrewMemberId.COMMANDER, pilot, fighter, trader, engineer,
                StarSystemId.NA));
        generateCrewMemberList();

        createShips();

        calculatePrices(commander.getCurrentSystem());

        resetVeryRareEncounters();

        if (getDifficulty().castToInt() < Difficulty.NORMAL.castToInt()) {
            commander.getCurrentSystem().setSpecialEventType(SpecialEventType.Lottery);
        }

        cheats = new GameCheats(this);
        if (name.length() == 0) {
            // TODO: JAF - DEBUG
            commander.setCash(1000000);
            cheats.setCheatMode(true);
            setEasyEncounters(true);
            setCanSuperWarp(true);
        }
    }

    public Game(Hashtable hash, MainWindow parentWin) {
        super();
        game = this;
        this.parentWin = parentWin;

        String version = getValueFromHash(hash, "_version", String.class);
        if (version.compareTo(Consts.CurrentVersion) > 0) {
            throw new FutureVersionException();
        }

        universe = (StarSystem[]) arrayListToArray(getValueFromHash(hash, "_universe", ArrayList.class), "StarSystem");
        wormholes = getValueFromHash(hash, "_wormholes", wormholes, int[].class);
        mercenaries = (CrewMember[]) arrayListToArray(getValueFromHash(hash, "_mercenaries", ArrayList.class),
                "CrewMember");
        commander = new Commander(getValueFromHash(hash, "commander", Hashtable.class));
        dragonfly = new Ship(getValueFromHash(hash, "_dragonfly", dragonfly.serialize(), Hashtable.class));
        scarab = new Ship(getValueFromHash(hash, "_scarab", scarab.serialize(), Hashtable.class));
        scorpion = new Ship(getValueFromHash(hash, "_scorpion", scorpion.serialize(), Hashtable.class));
        spaceMonster = new Ship(getValueFromHash(hash, "_spaceMonster", spaceMonster.serialize(), Hashtable.class));
        opponent = new Ship(getValueFromHash(hash, "_opponent", opponent.serialize(), Hashtable.class));
        chanceOfTradeInOrbit = getValueFromHash(hash, "_chanceOfTradeInOrbit", chanceOfTradeInOrbit);
        clicks = getValueFromHash(hash, "clicks", clicks);
        raided = getValueFromHash(hash, "_raided", raided);
        inspected = getValueFromHash(hash, "_inspected", inspected);
        tribbleMessage = getValueFromHash(hash, "_tribbleMessage", tribbleMessage);
        arrivedViaWormhole = getValueFromHash(hash, "_arrivedViaWormhole", arrivedViaWormhole);
        paidForNewspaper = getValueFromHash(hash, "_paidForNewspaper", paidForNewspaper);
        litterWarning = getValueFromHash(hash, "_litterWarning", litterWarning);
        newsEvents = new ArrayList(Arrays.asList((Integer[]) getValueFromHash(hash, "_newsEvents", newsEvents
                .toArray(new Integer[0]))));
        difficulty = Difficulty.fromInt(getValueFromHash(hash, "_difficulty", difficulty, Integer.class));
        cheats = new GameCheats(this);
        cheats.setCheatMode(getValueFromHash(hash, "_cheatEnabled", cheats.isCheatMode()));
        autoSave = getValueFromHash(hash, "_autoSave", autoSave);
        easyEncounters = getValueFromHash(hash, "_easyEncounters", easyEncounters);
        endStatus = GameEndType.fromInt(getValueFromHash(hash, "_endStatus", endStatus, Integer.class));
        encounterType = EncounterType.fromInt(getValueFromHash(hash, "_encounterType", encounterType, Integer.class));
        selectedSystemId = StarSystemId.fromInt(getValueFromHash(hash, "_selectedSystemId", selectedSystemId,
                Integer.class));
        warpSystemId = StarSystemId.fromInt(getValueFromHash(hash, "_warpSystemId", warpSystemId, Integer.class));
        trackedSystemId = StarSystemId.fromInt(getValueFromHash(hash, "_trackedSystemId", trackedSystemId,
                Integer.class));
        targetWormhole = getValueFromHash(hash, "_targetWormhole", targetWormhole);
        priceCargoBuy = getValueFromHash(hash, "_priceCargoBuy", priceCargoBuy, int[].class);
        priceCargoSell = getValueFromHash(hash, "_priceCargoSell", priceCargoSell, int[].class);
        questStatusArtifact = getValueFromHash(hash, "_questStatusArtifact", questStatusArtifact);
        questStatusDragonfly = getValueFromHash(hash, "_questStatusDragonfly", questStatusDragonfly);
        questStatusExperiment = getValueFromHash(hash, "_questStatusExperiment", questStatusExperiment);
        questStatusGemulon = getValueFromHash(hash, "_questStatusGemulon", questStatusGemulon);
        questStatusJapori = getValueFromHash(hash, "_questStatusJapori", questStatusJapori);
        questStatusJarek = getValueFromHash(hash, "_questStatusJarek", questStatusJarek);
        questStatusMoon = getValueFromHash(hash, "_questStatusMoon", questStatusMoon);
        questStatusPrincess = getValueFromHash(hash, "_questStatusPrincess", questStatusPrincess);
        questStatusReactor = getValueFromHash(hash, "_questStatusReactor", questStatusReactor);
        questStatusScarab = getValueFromHash(hash, "_questStatusScarab", questStatusScarab);
        questStatusSculpture = getValueFromHash(hash, "_questStatusSculpture", questStatusSculpture);
        questStatusSpaceMonster = getValueFromHash(hash, "_questStatusSpaceMonster", questStatusSpaceMonster);
        questStatusWild = getValueFromHash(hash, "_questStatusWild", questStatusWild);
        fabricRipProbability = getValueFromHash(hash, "_fabricRipProbability", fabricRipProbability);
        justLootedMarie = getValueFromHash(hash, "_justLootedMarie", justLootedMarie);
        canSuperWarp = getValueFromHash(hash, "_canSuperWarp", canSuperWarp);
        chanceOfVeryRareEncounter = getValueFromHash(hash, "_chanceOfVeryRareEncounter", chanceOfVeryRareEncounter);
        veryRareEncounters = new ArrayList(Arrays.asList(getValueFromHash(hash, "_veryRareEncounters",
                veryRareEncounters.toArray(new Integer[0]))));
        options = new GameOptions(getValueFromHash(hash, "_options", options.serialize(), Hashtable.class));
    }

    public static Game getCurrentGame() {
        return game;
    }

    public static void setCurrentGame(Game value) {
        game = value;
    }

    private void arrested() {
        int term = Math.max(30, -commander.getPoliceRecordScore());
        int fine = (1 + commander.getWorth() * Math.min(80, -commander.getPoliceRecordScore()) / 50000) * 500;
        if (commander.getShip().isWildOnBoard()) {
            fine = (int) (fine * 1.05);
        }

        GuiFacade.alert(AlertType.EncounterArrested);

        GuiFacade.alert(AlertType.JailConvicted, Functions.multiples(term, Strings.TimeUnit), Functions.multiples(fine,
                Strings.MoneyUnit));

        if (commander.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            while (commander.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
                commander.getShip().removeEquipment(EquipmentType.GADGET, GadgetType.HIDDEN_CARGO_BAYS);
            }

            GuiFacade.alert(AlertType.JailHiddenCargoBaysRemoved);
        }

        if (commander.getShip().isReactorOnBoard()) {
            GuiFacade.alert(AlertType.ReactorConfiscated);
            setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_NOT_STARTED);
        }

        if (commander.getShip().isSculptureOnBoard()) {
            GuiFacade.alert(AlertType.SculptureConfiscated);
            setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_NOT_STARTED);
        }

        if (commander.getShip().isWildOnBoard()) {
            GuiFacade.alert(AlertType.WildArrested);
            newsAddEvent(NewsEvent.WildArrested);
            setQuestStatusWild(SpecialEvent.STATUS_WILD_NOT_STARTED);
        }

        if (commander.getShip().isAnyIllegalCargo()) {
            GuiFacade.alert(AlertType.JailIllegalGoodsImpounded);
            commander.getShip().removeIllegalGoods();
        }

        if (commander.getInsurance()) {
            GuiFacade.alert(AlertType.JailInsuranceLost);
            commander.setInsurance(false);
            commander.setNoClaim(0);
        }

        if (commander.getShip().getCrewCount() - commander.getShip().getSpecialCrew().length > 1) {
            GuiFacade.alert(AlertType.JailMercenariesLeave);
            for (int i = 1; i < commander.getShip().getCrew().length; i++) {
                commander.getShip().getCrew()[i] = null;
            }
        }

        if (commander.getShip().isJarekOnBoard()) {
            GuiFacade.alert(AlertType.JarekTakenHome);
            setQuestStatusJarek(SpecialEvent.STATUS_JAREK_NOT_STARTED);
        }

        if (commander.getShip().isPrincessOnBoard()) {
            GuiFacade.alert(AlertType.PrincessTakenHome);
            setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_NOT_STARTED);
        }

        if (getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT) {
            GuiFacade.alert(AlertType.AntidoteTaken);
            setQuestStatusJapori(SpecialEvent.STATUS_JAPORI_DONE);
        }

        if (commander.getCash() >= fine) {
            commander.setCash(commander.getCash() - fine);
        } else {
            commander.setCash(Math.max(0, commander.getCash() + commander.getShip().getWorth(true) - fine));

            GuiFacade.alert(AlertType.JailShipSold);

            if (commander.getShip().getTribbles() > 0) {
                GuiFacade.alert(AlertType.TribblesRemoved);
            }

            GuiFacade.alert(AlertType.FleaBuilt);
            createFlea();
        }

        if (commander.getDebt() > 0) {
            int payDown = Math.min(commander.getCash(), commander.getDebt());
            commander.setDebt(commander.getDebt() - payDown);
            commander.setCash(commander.getCash() - payDown);

            if (commander.getDebt() > 0) {
                for (int i = 0; i < term; i++) {
                    commander.payInterest();
                }

            }
        }

        commander.setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
        incDays(term);
    }

    private void arrive() {
        commander.setCurrentSystem(getWarpSystem());
        commander.getCurrentSystem().setVisited(true);
        setPaidForNewspaper(false);

        if (getTrackedSystem() == commander.getCurrentSystem() && getOptions().isTrackAutoOff()) {
            setTrackedSystemId(StarSystemId.NA);
        }

        checkReactorOnArrival();

        checkTribblesOnArrival();

        checkDebtOnArrival();

        performRepairsOnArrival();

        updatePressuresAndQuantitiesOnArrival();

        checkEasterEggOnArrival();

        calculatePrices(commander.getCurrentSystem());

        newsAddEventsOnArrival();

        if (getOptions().isNewsAutoShow()) {
            showNewspaper();
        }
    }

    private void checkDebtOnArrival() {
        // Check for Large Debt - 06/30/01 SRA
        if (commander.getDebt() >= Consts.DebtWarning) {
            GuiFacade.alert(AlertType.DebtWarning);
        } else if (commander.getDebt() > 0 && getOptions().isRemindLoans() && commander.getDays() % 5 == 0) {
            GuiFacade.alert(AlertType.DebtReminder, Functions.multiples(commander.getDebt(), Strings.MoneyUnit));
        }
    }

    private void checkEasterEggOnArrival() {
        /* This Easter Egg gives the commander a Lighting Shield */
        if (commander.getCurrentSystem().getId() == StarSystemId.Og) {
            if (commander.getShip().getFreeShieldSlots() <= 0) {
                return;
            }
            for (int i = 0; i < commander.getShip().getCargo().length; i++) {
                if (commander.getShip().getCargo()[i] != 1) {
                    return;
                }
            }

            GuiFacade.alert(AlertType.Egg);
            commander.getShip().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
            for (int i = 0; i < commander.getShip().getCargo().length; i++) {
                commander.getShip().getCargo()[i] = 0;
                commander.getPriceCargo()[i] = 0;
            }
        }
    }

    private void checkReactorOnArrival() {
        if (getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_DATE) {
            GuiFacade.alert(AlertType.ReactorMeltdown);
            setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_NOT_STARTED);
            if (commander.getShip().getEscapePod()) {
                escapeWithPod();
            } else {
                GuiFacade.alert(AlertType.ReactorDestroyed);
                throw new GameEndException(GameEndType.KILLED);
            }
        } else {
            // Reactor warnings: now they know the quest has a time constraint!
            if (getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_FUEL_OK + 1) {
                GuiFacade.alert(AlertType.ReactorWarningFuel);
            } else if (getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_DATE - 4) {
                GuiFacade.alert(AlertType.ReactorWarningFuelGone);
            } else if (getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_DATE - 2) {
                GuiFacade.alert(AlertType.ReactorWarningTemp);
            }
        }
    }

    private void checkTribblesOnArrival() {
        Ship ship = commander.getShip();

        if (ship.getTribbles() > 0) {
            int previousTribbles = ship.getTribbles();
            int narc = TradeItemType.NARCOTICS.castToInt();
            int food = TradeItemType.FOOD.castToInt();

            if (ship.isReactorOnBoard()) {
                if (ship.getTribbles() < 20) {
                    ship.setTribbles(0);
                    GuiFacade.alert(AlertType.TribblesAllDied);
                } else {
                    ship.setTribbles(ship.getTribbles() / 2);
                    GuiFacade.alert(AlertType.TribblesHalfDied);
                }
            } else if (ship.getCargo()[narc] > 0) {
                int dead = Math.min(1 + Functions.getRandom(3), ship.getCargo()[narc]);
                commander.getPriceCargo()[narc]
                        = commander.getPriceCargo()[narc] * (ship.getCargo()[narc] - dead) / ship.getCargo()[narc];
                ship.getCargo()[narc] -= dead;
                ship.getCargo()[TradeItemType.FURS.castToInt()] += dead;
                ship.setTribbles(ship.getTribbles()
                        - Math.min(dead * (Functions.getRandom(5) + 98), ship.getTribbles() - 1));
                GuiFacade.alert(AlertType.TribblesMostDied);
            } else {
                if (ship.getCargo()[food] > 0 && ship.getTribbles() < Consts.MaxTribbles) {
                    int eaten = ship.getCargo()[food] - Functions.getRandom(ship.getCargo()[food]);
                    commander.getPriceCargo()[food] -= commander.getPriceCargo()[food] * eaten / ship.getCargo()[food];
                    ship.getCargo()[food] -= eaten;
                    ship.setTribbles(ship.getTribbles() + (eaten * 100));
                    GuiFacade.alert(AlertType.TribblesAteFood);
                }

                if (ship.getTribbles() < Consts.MaxTribbles) {
                    ship.setTribbles(ship.getTribbles() + (1 + Functions
                            .getRandom(ship.getCargo()[food] > 0 ? ship.getTribbles() : ship.getTribbles() / 2)));
                }

                if (ship.getTribbles() > Consts.MaxTribbles) {
                    ship.setTribbles(Consts.MaxTribbles);
                }

                if ((previousTribbles < 100 && ship.getTribbles() >= 100)
                        || (previousTribbles < 1000 && ship.getTribbles() >= 1000)
                        || (previousTribbles < 10000 && ship.getTribbles() >= 10000)
                        || (previousTribbles < 50000 && ship.getTribbles() >= 50000)
                        || (previousTribbles < Consts.MaxTribbles && ship.getTribbles() == Consts.MaxTribbles)) {
                    String qty = (ship.getTribbles()) == Consts.MaxTribbles
                            ? Strings.TribbleDangerousNumber : Functions.formatNumber(ship.getTribbles());
                    GuiFacade.alert(AlertType.TribblesInspector, qty);
                }
            }
            setTribbleMessage(false);
        }
    }

    private void performRepairsOnArrival() {
        Ship ship = commander.getShip();

        if (ship.getHull() < ship.getHullStrength()) {
            ship.setHull(ship.getHull() + Math.min(ship.getHullStrength() - ship.getHull(), Functions.getRandom(ship.getEngineer())));
        }

        for (int i = 0; i < ship.getShields().length; ++i) {
            if (ship.getShields()[i] != null) {
                ship.getShields()[i].setCharge(ship.getShields()[i].getPower());
            }
        }

        boolean fuelOk = true;
        int toAdd = ship.getFuelTanks() - ship.getFuel();
        if (getOptions().isAutoFuel() && toAdd > 0) {
            if (commander.getCash() >= toAdd * ship.getFuelCost()) {
                ship.setFuel(ship.getFuel() + toAdd);
                commander.setCash(commander.getCash() - (toAdd * ship.getFuelCost()));
            } else {
                fuelOk = false;
            }
        }

        boolean repairOk = true;
        toAdd = ship.getHullStrength() - ship.getHull();
        if (getOptions().isAutoRepair() && toAdd > 0) {
            if (commander.getCash() >= toAdd * ship.getRepairCost()) {
                ship.setHull(ship.getHull() + toAdd);
                commander.setCash(commander.getCash() - (toAdd * ship.getRepairCost()));
            } else {
                repairOk = false;
            }
        }

        if (!fuelOk && !repairOk) {
            GuiFacade.alert(AlertType.ArrivalIFFuelRepairs);
        } else if (!fuelOk) {
            GuiFacade.alert(AlertType.ArrivalIFFuel);
        } else if (!repairOk) {
            GuiFacade.alert(AlertType.ArrivalIFRepairs);
        }
    }

    private void updatePressuresAndQuantitiesOnArrival() {
        for (int i = 0; i < getUniverse().length; i++) {
            if (Functions.getRandom(100) < 15) {
                getUniverse()[i].setSystemPressure((SystemPressure.fromInt(
                        getUniverse()[i].getSystemPressure() == SystemPressure.NONE ? Functions
                                .getRandom(SystemPressure.WAR.castToInt(), SystemPressure.EMPLOYMENT.castToInt() + 1)
                                : SystemPressure.NONE.castToInt())));
            }

            if (getUniverse()[i].getCountDown() > 0) {
                getUniverse()[i].setCountDown(getUniverse()[i].getCountDown() - 1);

                if (getUniverse()[i].getCountDown() > getCountDownStart()) {
                    getUniverse()[i].setCountDown(getCountDownStart());
                } else if (getUniverse()[i].getCountDown() <= 0) {
                    getUniverse()[i].initializeTradeItems();
                } else {
                    for (int j = 0; j < Consts.TradeItems.length; j++) {
                        if (getWarpSystem().isItemTraded(Consts.TradeItems[j])) {
                            getUniverse()[i].getTradeItems()[j] = Math
                                    .max(0, getUniverse()[i].getTradeItems()[j] + Functions.getRandom(-4, 5));
                        }
                    }
                }
            }
        }
    }

    private void calculatePrices(StarSystem system) {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            int price = Consts.TradeItems[i].standardPrice(system);

            if (price > 0) {
                // In case of a special status, adapt price accordingly
                if (Consts.TradeItems[i].getPressurePriceHike() == system.getSystemPressure()) {
                    price = price * 3 / 2;
                }

                // Randomize price a bit
                int variance = Math.min(Consts.TradeItems[i].getPriceVariance(), price - 1);
                price = price + Functions.getRandom(-variance, variance + 1);

                // Criminals have to pay off an intermediary
                if (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreDubious) {
                    price = price * 90 / 100;
                }
            }

            priceCargoSell[i] = price;
        }

        recalculateBuyPrices(system);
    }

    public boolean getTribbleMessage() {
        return tribbleMessage;
    }

    public void setTribbleMessage(boolean tribbleMessage) {
        this.tribbleMessage = tribbleMessage;
    }

    public StarSystemId getTrackedSystemId() {
        return trackedSystemId;
    }

    public void setTrackedSystemId(StarSystemId trackedSystemId) {
        this.trackedSystemId = trackedSystemId;
    }

    private boolean getRaided() {
        return raided;
    }

    private void setRaided(boolean raided) {
        this.raided = raided;
    }

    public int getQuestStatusWild() {
        return questStatusWild;
    }

    public void setQuestStatusWild(int questStatusWild) {
        this.questStatusWild = questStatusWild;
    }

    public int getQuestStatusSpaceMonster() {
        return questStatusSpaceMonster;
    }

    public void setQuestStatusSpaceMonster(int questStatusSpaceMonster) {
        this.questStatusSpaceMonster = questStatusSpaceMonster;
    }

    public int getQuestStatusSculpture() {
        return questStatusSculpture;
    }

    public void setQuestStatusSculpture(int questStatusSculpture) {
        this.questStatusSculpture = questStatusSculpture;
    }

    public int getQuestStatusScarab() {
        return questStatusScarab;
    }

    public void setQuestStatusScarab(int questStatusScarab) {
        this.questStatusScarab = questStatusScarab;
    }

    public int getQuestStatusReactor() {
        return questStatusReactor;
    }

    public void setQuestStatusReactor(int questStatusReactor) {
        this.questStatusReactor = questStatusReactor;
    }

    public int getQuestStatusPrincess() {
        return questStatusPrincess;
    }

    public void setQuestStatusPrincess(int questStatusPrincess) {
        this.questStatusPrincess = questStatusPrincess;
    }

    public int getQuestStatusMoon() {
        return questStatusMoon;
    }

    public void setQuestStatusMoon(int questStatusMoon) {
        this.questStatusMoon = questStatusMoon;
    }

    public int getQuestStatusJarek() {
        return questStatusJarek;
    }

    public void setQuestStatusJarek(int questStatusJarek) {
        this.questStatusJarek = questStatusJarek;
    }

    public int getQuestStatusJapori() {
        return questStatusJapori;
    }

    public void setQuestStatusJapori(int questStatusJapori) {
        this.questStatusJapori = questStatusJapori;
    }

    public int getQuestStatusGemulon() {
        return questStatusGemulon;
    }

    public void setQuestStatusGemulon(int questStatusGemulon) {
        this.questStatusGemulon = questStatusGemulon;
    }

    public int getQuestStatusExperiment() {
        return questStatusExperiment;
    }

    public void setQuestStatusExperiment(int questStatusExperiment) {
        this.questStatusExperiment = questStatusExperiment;
    }

    public int getQuestStatusDragonfly() {
        return questStatusDragonfly;
    }

    public void setQuestStatusDragonfly(int questStatusDragonfly) {
        this.questStatusDragonfly = questStatusDragonfly;
    }

    public int getQuestStatusArtifact() {
        return questStatusArtifact;
    }

    public void setQuestStatusArtifact(int questStatusArtifact) {
        this.questStatusArtifact = questStatusArtifact;
    }

    /**
     * todo Root of much evil.
     */
    public MainWindow getParentWindow() {
        return parentWin;
    }

    public void setParentWindow(MainWindow parentWindow) {
        parentWin = parentWindow;
    }

    private boolean getPaidForNewspaper() {
        return paidForNewspaper;
    }

    private void setPaidForNewspaper(boolean paidForNewspaper) {
        this.paidForNewspaper = paidForNewspaper;
    }

    private boolean setOpponentDisabled(boolean opponentDisabled) {
        this.opponentDisabled = opponentDisabled;
        return opponentDisabled;
    }

    boolean getOpponentDisabled() {
        return opponentDisabled;
    }

    public Ship getOpponent() {
        return opponent;
    }

    public void setOpponent(Ship opponent) {
        this.opponent = opponent;
    }

    private boolean getLitterWarning() {
        return litterWarning;
    }

    public void setLitterWarning(boolean litterWarning) {
        this.litterWarning = litterWarning;
    }

    private boolean getJustLootedMarie() {
        return justLootedMarie;
    }

    private void setJustLootedMarie(boolean justLootedMarie) {
        this.justLootedMarie = justLootedMarie;
    }

    private boolean getInspected() {
        return inspected;
    }

    private void setInspected(boolean inspected) {
        this.inspected = inspected;
    }

    private int getFabricRipProbability() {
        return fabricRipProbability;
    }

    public void setFabricRipProbability(int fabricRipProbability) {
        this.fabricRipProbability = fabricRipProbability;
    }

    GameEndType getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(GameEndType endStatus) {
        this.endStatus = endStatus;
    }

    public EncounterType getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(EncounterType encounterType) {
        this.encounterType = encounterType;
    }

    private boolean getEncounterOppHit() {
        return encounterOppHit;
    }

    private void setEncounterOppHit(boolean encounterOppHit) {
        this.encounterOppHit = encounterOppHit;
    }

    private boolean getEncounterOppFleeingPrev() {
        return encounterOppFleeingPrev;
    }

    private void setEncounterOppFleeingPrev(boolean encounterOppFleeingPrev) {
        this.encounterOppFleeingPrev = encounterOppFleeingPrev;
    }

    private boolean getEncounterOppFleeing() {
        return encounterOppFleeing;
    }

    private void setEncounterOppFleeing(boolean encounterOppFleeing) {
        this.encounterOppFleeing = encounterOppFleeing;
    }

    public boolean setEncounterContinueAttacking(boolean encounterContinueAttacking) {
        this.encounterContinueAttacking = encounterContinueAttacking;
        return encounterContinueAttacking;
    }

    public boolean getEncounterContinueAttacking() {
        return encounterContinueAttacking;
    }

    private boolean getEncounterCmdrHit() {
        return encounterCmdrHit;
    }

    private void setEncounterCmdrHit(boolean encounterCmdrHit) {
        this.encounterCmdrHit = encounterCmdrHit;
    }

    private boolean getEncounterCmdrFleeing() {
        return encounterCmdrFleeing;
    }

    private void setEncounterCmdrFleeing(boolean encounterCmdrFleeing) {
        this.encounterCmdrFleeing = encounterCmdrFleeing;
    }

    public boolean getEncounterContinueFleeing() {
        return encounterContinueFleeing;
    }

    public void setEncounterContinueFleeing(boolean encounterContinueFleeing) {
        this.encounterContinueFleeing = encounterContinueFleeing;
    }

    public boolean isEasyEncounters() {
        return easyEncounters;
    }

    public void setEasyEncounters(boolean easyEncounters) {
        this.easyEncounters = easyEncounters;
    }

    private int getClicks() {
        return clicks;
    }

    private void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public GameCheats getCheats() {
        return cheats;
    }

    private int getChanceOfVeryRareEncounter() {
        return chanceOfVeryRareEncounter;
    }

    public void setChanceOfVeryRareEncounter(int chanceOfVeryRareEncounter) {
        this.chanceOfVeryRareEncounter = chanceOfVeryRareEncounter;
    }

    private int getChanceOfTradeInOrbit() {
        return chanceOfTradeInOrbit;
    }

    public void setChanceOfTradeInOrbit(int chanceOfTradeInOrbit) {
        this.chanceOfTradeInOrbit = chanceOfTradeInOrbit;
    }

    public boolean getCanSuperWarp() {
        return canSuperWarp;
    }

    public void setCanSuperWarp(boolean canSuperWarp) {
        this.canSuperWarp = canSuperWarp;
    }

    boolean getAutoSave() {
        return autoSave;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    private boolean getArrivedViaWormhole() {
        return arrivedViaWormhole;
    }

    private void setArrivedViaWormhole(boolean arrivedViaWormhole) {
        this.arrivedViaWormhole = arrivedViaWormhole;
    }

    private void cargoBuy(int tradeItem, boolean max, CargoBuyOp op) {
        int freeBays = commander.getShip().getFreeCargoBays();
        int[] items = null;
        int unitPrice = 0;
        int cashToSpend = commander.getCash();

        switch (op) {
            case BUY_SYSTEM:
                freeBays = Math.max(0, commander.getShip().getFreeCargoBays() - getOptions().getLeaveEmpty());
                items = commander.getCurrentSystem().getTradeItems();
                unitPrice = getPriceCargoBuy()[tradeItem];
                cashToSpend = commander.getCashToSpend();
                break;
            case BUY_TRADER:
                items = getOpponent().getCargo();
                TradeItem item = Consts.TradeItems[tradeItem];
                int chance = item.isIllegal() ? 45 : 10;
                double adj = Functions.getRandom(100) < chance ? 1.1 : (item.isIllegal() ? 0.8 : 0.9);
                unitPrice = Math.min(item.getMaxTradePrice(), Math.max(item.getMinTradePrice(), (int) Math
                        .round(getPriceCargoBuy()[tradeItem] * adj / item.getRoundOff()) * item.getRoundOff()));
                break;
            case PLUNDER:
                items = getOpponent().getCargo();
                break;
        }

        if (op == CargoBuyOp.BUY_SYSTEM && commander.getDebt() > Consts.DebtTooLarge) {
            GuiFacade.alert(AlertType.DebtTooLargeTrade);
        } else if (op == CargoBuyOp.BUY_SYSTEM && (items[tradeItem] <= 0 || unitPrice <= 0)) {
            GuiFacade.alert(AlertType.CargoNoneAvailable);
        } else if (freeBays == 0) {
            GuiFacade.alert(AlertType.CargoNoEmptyBays);
        } else if (op != CargoBuyOp.PLUNDER && cashToSpend < unitPrice) {
            GuiFacade.alert(AlertType.CargoIF);
        } else {
            int maxAmount = Math.min(freeBays, items[tradeItem]);
            if (op == CargoBuyOp.BUY_SYSTEM) {
                maxAmount = Math.min(maxAmount, commander.getCashToSpend() / unitPrice);
            }

            int qty = (max) ? maxAmount : GuiFacade.queryAmountAcquire(tradeItem, maxAmount, op);

            if (qty > 0) {
                int totalPrice = qty * unitPrice;

                commander.getShip().getCargo()[tradeItem] += qty;
                items[tradeItem] -= qty;
                commander.setCash(commander.getCash() - totalPrice);
                commander.getPriceCargo()[tradeItem] += totalPrice;
            }
        }
    }

    void cargoBuySystem(int tradeItem, boolean max) {
        cargoBuy(tradeItem, max, CargoBuyOp.BUY_SYSTEM);
    }

    private void cargoBuyTrader(int tradeItem) {
        cargoBuy(tradeItem, false, CargoBuyOp.BUY_TRADER);
    }

    public void cargoPlunder(int tradeItem, boolean max) {
        cargoBuy(tradeItem, max, CargoBuyOp.PLUNDER);
    }

    void cargoDump(int tradeItem) {
        cargoSell(tradeItem, false, CargoSellOp.DUMP);
    }

    public void cargoJettison(int tradeItem, boolean all) {
        cargoSell(tradeItem, all, CargoSellOp.JETTISON);
    }

    void cargoSellSystem(int tradeItem, boolean all) {
        cargoSell(tradeItem, all, CargoSellOp.SELL_SYSTEM);
    }

    private void cargoSell(int tradeItem, boolean all, CargoSellOp op) {
        int qtyInHand = commander.getShip().getCargo()[tradeItem];
        int unitPrice;
        switch (op) {
            case SELL_SYSTEM:
                unitPrice = getPriceCargoSell()[tradeItem];
                break;
            case SELL_TRADER:
                TradeItem item = Consts.TradeItems[tradeItem];
                int chance = item.isIllegal() ? 45 : 10;
                double adj = Functions.getRandom(100) < chance ? (item.isIllegal() ? 0.8 : 0.9) : 1.1;
                unitPrice = Math.min(item.getMaxTradePrice(), Math.max(item.getMinTradePrice(), (int) Math
                        .round(getPriceCargoSell()[tradeItem] * adj / item.getRoundOff())
                        * item.getRoundOff()));
                break;
            default:
                unitPrice = 0;
                break;
        }

        if (qtyInHand == 0) {
            GuiFacade.alert(AlertType.CargoNoneToSell, Strings.CargoSellOps[op.castToInt()]);
        } else if (op == CargoSellOp.SELL_SYSTEM && unitPrice <= 0) {
            GuiFacade.alert(AlertType.CargoNotInterested);
        } else {
            if (op != CargoSellOp.JETTISON || getLitterWarning()
                    || commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreDubious
                    || GuiFacade.alert(AlertType.EncounterDumpWarning) == DialogResult.YES) {
                int unitCost = 0;
                int maxAmount = (op == CargoSellOp.SELL_TRADER) ? Math.min(qtyInHand, getOpponent().getFreeCargoBays())
                        : qtyInHand;
                if (op == CargoSellOp.DUMP) {
                    unitCost = 5 * (getDifficulty().castToInt() + 1);
                    maxAmount = Math.min(maxAmount, commander.getCashToSpend() / unitCost);
                }
                int price = unitPrice > 0 ? unitPrice : -unitCost;

                int qty = (all) ? maxAmount : GuiFacade.queryAmountRelease(tradeItem, op, maxAmount, price);

                if (qty > 0) {
                    int totalPrice = qty * price;

                    commander.getShip().getCargo()[tradeItem] -= qty;
                    commander.getPriceCargo()[tradeItem] = (commander.getPriceCargo()[tradeItem] * (qtyInHand - qty))
                            / qtyInHand;
                    commander.setCash(commander.getCash() + totalPrice);

                    if (op == CargoSellOp.JETTISON) {
                        if (Functions.getRandom(10) < getDifficulty().castToInt() + 1) {
                            if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreDubious) {
                                commander.setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                            } else {
                                commander.setPoliceRecordScore(commander.getPoliceRecordScore() - 1);
                            }

                            newsAddEvent(NewsEvent.CaughtLittering);
                        }
                    }
                }
            }
        }
    }

    private void cargoSellTrader(int tradeItem) {
        cargoSell(tradeItem, false, CargoSellOp.SELL_TRADER);
    }

    private void createFlea() {
        commander.setShip(new Ship(ShipType.FLEA));
        commander.getShip().getCrew()[0] = commander;
        commander.setInsurance(false);
        commander.setNoClaim(0);
    }

    private void createShips() {
        getDragonfly().getCrew()[0] = getMercenaries()[CrewMemberId.DRAGONFLY.castToInt()];
        getDragonfly().addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        getDragonfly().addEquipment(Consts.Weapons[WeaponType.PULSE_LASER.castToInt()]);
        getDragonfly().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        getDragonfly().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        getDragonfly().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        getDragonfly().addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        getDragonfly().addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

        getScarab().getCrew()[0] = getMercenaries()[CrewMemberId.SCARAB.castToInt()];
        getScarab().addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        getScarab().addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);

        scorpion.getCrew()[0] = getMercenaries()[CrewMemberId.SCORPION.castToInt()];
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

        spaceMonster.getCrew()[0] = getMercenaries()[CrewMemberId.SPACE_MONSTER.castToInt()];
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
    }

    public boolean isDetermineEncounter() {
        // If there is a specific encounter that needs to happen, it will,
        // otherwise we'll generate a random encounter.
        return isDetermineNonRandomEncounter() || isDetermineRandomEncounter();
    }

    private boolean isDetermineNonRandomEncounter() {
        boolean showEncounter = false;

        // Encounter with space monster
        if (getClicks() == 1 && getWarpSystem().getId() == StarSystemId.Acamar
                && getQuestStatusSpaceMonster() == SpecialEvent.STATUS_SPACE_MONSTER_AT_ACAMAR) {
            setOpponent(spaceMonster);
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.SPACE_MONSTER_IGNORE
                    : EncounterType.SPACE_MONSTER_ATTACK);
            showEncounter = true;
        }
        // Encounter with the stolen Scarab
        else if (getArrivedViaWormhole() && getClicks() == 20 && getWarpSystem().getSpecialEventType() != SpecialEventType.NA
                && getWarpSystem().specialEvent().getType() == SpecialEventType.ScarabDestroyed
                && getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_HUNTING) {
            setOpponent(getScarab());
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.SCARAB_IGNORE
                    : EncounterType.SCARAB_ATTACK);
            showEncounter = true;
        }
        // Encounter with stolen Dragonfly
        else if (getClicks() == 1 && getWarpSystem().getId() == StarSystemId.Zalkon
                && getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_ZALKON) {
            setOpponent(getDragonfly());
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.DRAGONFLY_IGNORE
                    : EncounterType.DRAGONFLY_ATTACK);
            showEncounter = true;
        }
        // Encounter with kidnappers in the Scorpion
        else if (getClicks() == 1 && getWarpSystem().getId() == StarSystemId.Qonos
                && getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_QONOS) {
            setOpponent(scorpion);
            setEncounterType(commander.getShip().isCloaked() ? EncounterType.SCORPION_IGNORE
                    : EncounterType.SCORPION_ATTACK);
            showEncounter = true;
        }
        // ah, just when you thought you were gonna get away with it...
        else if (getClicks() == 1 && getJustLootedMarie()) {
            generateOpponent(OpponentType.POLICE);
            setEncounterType(EncounterType.MARIE_CELESTE_POLICE);
            setJustLootedMarie(false);

            showEncounter = true;
        }

        return showEncounter;
    }

    private boolean isDeterminePirateEncounter(boolean mantis) {
        if (mantis) {
            generateOpponent(OpponentType.MANTIS);
            setEncounterType(EncounterType.PIRATE_ATTACK);
        } else {
            generateOpponent(OpponentType.PIRATE);

            // If you have a cloak, they don't see you
            if (commander.getShip().isCloaked()) {
                setEncounterType(EncounterType.PIRATE_IGNORE);
                // Pirates will mostly attack, but they are cowardly: if your rep is
                // too high, they tend to flee
                // if Pirates are in a better ship, they won't flee, even if you
                // have a very scary
                // reputation.
            } else if (getOpponent().getType().castToInt() > commander.getShip().getType().castToInt()
                    || getOpponent().getType().castToInt() >= ShipType.GRASSHOPPER.castToInt()
                    || Functions.getRandom(Consts.ReputationScoreElite) > (commander.getReputationScore() * 4)
                    / (1 + getOpponent().getType().castToInt())) {
                setEncounterType(EncounterType.PIRATE_ATTACK);
            } else {
                setEncounterType(EncounterType.PIRATE_FLEE);
            }
        }

        // If they ignore you or flee and you can't see them, the encounter
        // doesn't take place
        // If you automatically don't want to confront someone who ignores you,
        // the
        // encounter may not take place
        return getEncounterType() == EncounterType.PIRATE_ATTACK || !(getOpponent().isCloaked() || getOptions()
                .isAlwaysIgnorePirates());

    }

    private boolean isDeterminePoliceEncounter() {
        generateOpponent(OpponentType.POLICE);

        // If you are cloaked, they don't see you
        setEncounterType(EncounterType.POLICE_IGNORE);
        if (!commander.getShip().isCloaked()) {
            if (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreDubious) {
                // If you're a criminal, the police will tend to attack
                // JAF - fixed this; there was code that didn't do anything.
                // if you're suddenly stuck in a lousy ship, Police won't flee
                // even if you
                // have a fearsome reputation.
                if (getOpponent().getWeaponStrength() > 0
                        && (commander.getReputationScore() < Consts.ReputationScoreAverage || Functions
                        .getRandom(Consts.ReputationScoreElite) > (commander.getReputationScore() / (1 + getOpponent()
                        .getType().castToInt())))
                        || getOpponent().getType().castToInt() > commander.getShip().getType().castToInt()) {
                    if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreCriminal) {
                        setEncounterType(EncounterType.POLICE_SURRENDER);
                    } else {
                        setEncounterType(EncounterType.POLICE_ATTACK);
                    }
                } else if (getOpponent().isCloaked()) {
                    setEncounterType(EncounterType.POLICE_IGNORE);
                } else {
                    setEncounterType(EncounterType.POLICE_FLEE);
                }
            } else if (!getInspected()
                    && (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreClean
                    || (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreLawful && Functions
                    .getRandom(12 - getDifficulty().castToInt()) < 1) || (commander
                    .getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful && Functions.getRandom(40) == 0))) {
                // If you're reputation is dubious, the police will inspect you
                // If your record is clean, the police will inspect you with a
                // chance of 10% on Normal
                // If your record indicates you are a lawful trader, the chance
                // on inspection drops to 2.5%
                setEncounterType(EncounterType.POLICE_INSPECT);
                setInspected(true);
            }
        }

        // If they ignore you or flee and you can't see them, the encounter
        // doesn't take place
        // If you automatically don't want to confront someone who ignores you,
        // the
        // encounter may not take place. Otherwise it will - JAF
        return getEncounterType() == EncounterType.POLICE_ATTACK || getEncounterType() == EncounterType.POLICE_INSPECT
                || !(getOpponent().isCloaked() || getOptions().isAlwaysIgnorePolice());
    }

    private boolean isDetermineRandomEncounter() {
        boolean mantis = false;
        boolean pirate = false;
        boolean police = false;
        boolean trader = false;

        if (getWarpSystem().getId() == StarSystemId.Gemulon && getQuestStatusGemulon() == SpecialEvent.STATUS_GEMULON_TOO_LATE) {
            if (Functions.getRandom(10) > 4) {
                mantis = true;
            }
        } else {
            // Check if it is time for an encounter
            int encounter = Functions.getRandom(44 - (2 * getDifficulty().castToInt()));
            int policeModifier = Math.max(1, 3 - PoliceRecord.getPoliceRecordFromScore(
                    commander.getPoliceRecordScore()).getType().castToInt());

            // encounters are half as likely if you're in a flea.
            if (commander.getShip().getType() == ShipType.FLEA) {
                encounter *= 2;
            }

            if (encounter < getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()) {
                // When you are already raided, other pirates have little to gain
                pirate = !getRaided();
            } else if (encounter < getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()
                    + getWarpSystem().getPoliticalSystem().getActivityPolice().castToInt() * policeModifier) {
                // policeModifier adapts itself to your criminal record: you'll
                // encounter more police if you are a hardened criminal.
                police = true;
            } else if (encounter < getWarpSystem().getPoliticalSystem().getActivityPirates().castToInt()
                    + getWarpSystem().getPoliticalSystem().getActivityPolice().castToInt() * policeModifier
                    + getWarpSystem().getPoliticalSystem().getActivityTraders().castToInt()) {
                trader = true;
            } else if (commander.getShip().isWildOnBoard() && getWarpSystem().getId() == StarSystemId.Kravat) {
                // if you're coming in to Kravat & you have Wild onboard, there'll be swarms o' cops.
                police = Functions.getRandom(100) < 100 / Math.max(2, Math.min(4, 5 - getDifficulty().castToInt()));
            } else if (commander.getShip().isArtifactOnBoard() && Functions.getRandom(20) <= 3) {
                mantis = true;
            }
        }

        if (police) {
            return isDeterminePoliceEncounter();
        } else if (pirate || mantis) {
            return isDeterminePirateEncounter(mantis);
        } else if (trader) {
            return isDetermineTraderEncounter();
        } else if (commander.getDays() > 10 && Functions.getRandom(1000) < getChanceOfVeryRareEncounter()
                && getVeryRareEncounters().size() > 0) {
            return isDetermineVeryRareEncounter();
        } else {
            return false;
        }
    }

    private boolean isDetermineTraderEncounter() {
        generateOpponent(OpponentType.TRADER);

        // If you are cloaked, they don't see you
        setEncounterType(EncounterType.TRADER_IGNORE);
        if (!commander.getShip().isCloaked()) {
            // If you're a criminal, traders tend to flee if you've got at least some reputation
            if (!commander.getShip().isCloaked()
                    && commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                    && Functions.getRandom(Consts.ReputationScoreElite) <= (commander.getReputationScore() * 10)
                    / (1 + getOpponent().getType().castToInt())) {
                setEncounterType(EncounterType.TRADER_FLEE);
                // Will there be trade in orbit?
            } else if (Functions.getRandom(1000) < getChanceOfTradeInOrbit()) {
                if (commander.getShip().getFreeCargoBays() > 0 && getOpponent().hasTradeableItems()) {
                    setEncounterType(EncounterType.TRADER_SELL);
                    // we fudge on whether the trader has capacity to carry the stuff he's buying.
                } else if (commander.getShip().hasTradeableItems()) {
                    setEncounterType(EncounterType.TRADER_BUY);
                }
            }
        }

        // If they ignore you or flee and you can't see them, the encounter doesn't take place
        // If you automatically don't want to confront someone who ignores you, the
        // encounter may not take place; otherwise it will.
        return !getOpponent().isCloaked() && !(getOptions().isAlwaysIgnoreTraders() && (
                getEncounterType() == EncounterType.TRADER_IGNORE || getEncounterType() == EncounterType.TRADER_FLEE))
                && !((getEncounterType() == EncounterType.TRADER_BUY || getEncounterType() == EncounterType.TRADER_SELL)
                && getOptions().isAlwaysIgnoreTradeInOrbit());
    }

    public boolean isDetermineVeryRareEncounter() {
        // Very Rare Random Events:
        // 1. Encounter the abandoned Marie Celeste, which you may loot.
        // 2. Captain Ahab will trade your Reflective Shield for skill points in Piloting.
        // 3. Captain Conrad will trade your Military Laser for skill points in Engineering.
        // 4. Captain Huie will trade your Military Laser for points in Trading.
        // 5. Encounter an out-of-date bottle of Captain Marmoset's Skill Tonic.
        // This will affect skills depending on game difficulty level.
        // 6. Encounter a good bottle of Captain Marmoset's Skill Tonic, which will invoke
        // IncreaseRandomSkill one or two times, depending on game difficulty.
        switch (getVeryRareEncounters().get(Functions.getRandom(getVeryRareEncounters().size()))) {
            case MARIE_CELESTE:
                // Marie Celeste cannot be at Acamar, Qonos, or Zalkon as it may
                // cause problems with the Space Monster, Scorpion, or Dragonfly
                if (getClicks() > 1 && commander.getCurrentSystemId() != StarSystemId.Acamar
                        && commander.getCurrentSystemId() != StarSystemId.Zalkon
                        && commander.getCurrentSystemId() != StarSystemId.Qonos) {
                    getVeryRareEncounters().remove(VeryRareEncounter.MARIE_CELESTE);
                    setEncounterType(EncounterType.MARIE_CELESTE);
                    generateOpponent(OpponentType.TRADER);
                    for (int i = 0; i < getOpponent().getCargo().length; i++) {
                        getOpponent().getCargo()[i] = 0;
                    }
                    getOpponent().getCargo()[TradeItemType.NARCOTICS
                            .castToInt()] = Math.min(getOpponent().getCargoBays(), 5);
                    return true;
                }
                break;

            case CAPTAIN_AHAB:
                if (commander.getShip().hasShield(ShieldType.REFLECTIVE) && commander.getPilot() < 10
                        && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_AHAB);
                    setEncounterType(EncounterType.CAPTAIN_AHAB);
                    generateOpponent(OpponentType.FAMOUS_CAPTAIN);
                    return true;
                }
                break;

            case CAPTAIN_CONRAD:
                if (commander.getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && commander.getEngineer() < 10
                        && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_CONRAD);
                    setEncounterType(EncounterType.CAPTAIN_CONRAD);
                    generateOpponent(OpponentType.FAMOUS_CAPTAIN);
                    return true;
                }
                break;

            case CAPTAIN_HUIE:
                if (commander.getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && commander.getTrader() < 10
                        && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_HUIE);
                    setEncounterType(EncounterType.CAPTAIN_HUIE);
                    generateOpponent(OpponentType.FAMOUS_CAPTAIN);
                    return true;
                }
                break;

            case BOTTLE_OLD:
                getVeryRareEncounters().remove(VeryRareEncounter.BOTTLE_OLD);
                setEncounterType(EncounterType.BOTTLE_OLD);
                generateOpponent(OpponentType.BOTTLE);
                return true;

            case BOTTLE_GOOD:
                getVeryRareEncounters().remove(VeryRareEncounter.BOTTLE_GOOD);
                setEncounterType(EncounterType.BOTTLE_GOOD);
                generateOpponent(OpponentType.BOTTLE);
                return true;
        }

        return false;
    }

    public void encounterBegin() {
        // Set up the encounter variables.
        setEncounterContinueFleeing(setEncounterContinueAttacking(setOpponentDisabled(false)));
    }

    private void encounterDefeatDragonfly() {
        commander.setKillsPirate(commander.getKillsPirate() + 1);
        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        setQuestStatusDragonfly(SpecialEvent.STATUS_DRAGONFLY_DESTROYED);
    }

    private void encounterDefeatScarab() {
        commander.setKillsPirate(commander.getKillsPirate() + 1);
        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        setQuestStatusScarab(SpecialEvent.STATUS_SCARAB_DESTROYED);
    }

    private void encounterDefeatScorpion() {
        commander.setKillsPirate(commander.getKillsPirate() + 1);
        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_RESCUED);
    }

    public void encounterDrink() {
        if (GuiFacade.alert(AlertType.EncounterDrinkContents) == DialogResult.YES) {
            if (getEncounterType() == EncounterType.BOTTLE_GOOD) {
                // two points if you're on beginner-normal, one otherwise
                commander.increaseRandomSkill();
                if (getDifficulty().castToInt() <= Difficulty.NORMAL.castToInt()) {
                    commander.increaseRandomSkill();
                }
                GuiFacade.alert(AlertType.EncounterTonicConsumedGood);
            } else {
                commander.tonicTweakRandomSkill();
                GuiFacade.alert(AlertType.EncounterTonicConsumedStrange);
            }
        }
    }

    public EncounterResult getEncounterExecuteAction() {
        EncounterResult result = EncounterResult.CONTINUE;
        int prevCmdrHull = commander.getShip().getHull();
        int prevOppHull = getOpponent().getHull();

        setEncounterCmdrHit(false);
        setEncounterOppHit(false);
        setEncounterOppFleeingPrev(getEncounterOppFleeing());
        setEncounterOppFleeing(false);

        // Fire shots
        switch (getEncounterType()) {
            case DRAGONFLY_ATTACK:
            case FAMOUS_CAPTAIN_ATTACK:
            case MARIE_CELESTE_POLICE:
            case PIRATE_ATTACK:
            case POLICE_ATTACK:
            case SCARAB_ATTACK:
            case SCORPION_ATTACK:
            case SPACE_MONSTER_ATTACK:
            case TRADER_ATTACK:
                setEncounterCmdrHit(isEncounterExecuteAttack(getOpponent(), commander.getShip(), getEncounterCmdrFleeing()));
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && isEncounterExecuteAttack(commander.getShip(), getOpponent(), false));
                break;
            case PIRATE_FLEE:
            case PIRATE_SURRENDER:
            case POLICE_FLEE:
            case TRADER_FLEE:
            case TRADER_SURRENDER:
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && isEncounterExecuteAttack(commander.getShip(), getOpponent(), true));
                setEncounterOppFleeing(true);
                break;
            default:
                setEncounterOppHit(!getEncounterCmdrFleeing()
                        && isEncounterExecuteAttack(commander.getShip(), getOpponent(), false));
                break;
        }

        // Determine whether someone gets destroyed
        if (commander.getShip().getHull() <= 0) {
            if (commander.getShip().getEscapePod()) {
                result = EncounterResult.ESCAPE_POD;
            } else {
                GuiFacade.alert((getOpponent().getHull() <= 0 ? AlertType.EncounterBothDestroyed
                        : AlertType.EncounterYouLose));

                result = EncounterResult.KILLED;
            }
        } else if (getOpponentDisabled()) {
            if (getOpponent().getType() == ShipType.DRAGONFLY || getOpponent().getType() == ShipType.SCARAB
                    || getOpponent().getType() == ShipType.SCORPION) {
                String str2 = "";

                switch (getOpponent().getType()) {
                    case DRAGONFLY:
                        encounterDefeatDragonfly();
                        break;
                    case SCARAB:
                        encounterDefeatScarab();
                        break;
                    case SCORPION:
                        str2 = Strings.EncounterPrincessRescued;
                        encounterDefeatScorpion();
                        break;
                }

                GuiFacade.alert(AlertType.EncounterDisabledOpponent, EncounterShipText(), str2);

                commander.setReputationScore(
                        commander.getReputationScore() + (getOpponent().getType().castToInt() / 2 + 1));
                result = EncounterResult.NORMAL;
            } else {
                encounterUpdateEncounterType(prevCmdrHull, prevOppHull);
                setEncounterOppFleeing(false);
            }
        } else if (getOpponent().getHull() <= 0) {
            encounterWon();

            result = EncounterResult.NORMAL;
        } else {
            boolean escaped = false;

            // Determine whether someone gets away.
            if (getEncounterCmdrFleeing()
                    && (getDifficulty() == Difficulty.BEGINNER || (Functions.getRandom(7) + commander
                    .getShip().getPilot() / 3) * 2 >= Functions.getRandom(getOpponent().getPilot())
                    * (2 + getDifficulty().castToInt()))) {
                GuiFacade.alert((getEncounterCmdrHit() ? AlertType.EncounterEscapedHit : AlertType.EncounterEscaped));
                escaped = true;
            } else if (getEncounterOppFleeing()
                    && Functions.getRandom(commander.getShip().getPilot()) * 4 <= Functions.getRandom(7 + getOpponent()
                    .getPilot() / 3) * 2) {
                GuiFacade.alert(AlertType.EncounterOpponentEscaped);
                escaped = true;
            }

            if (escaped) {
                result = EncounterResult.NORMAL;
            } else {
                // Determine whether the opponent's actions must be changed
                EncounterType prevEncounter = getEncounterType();

                encounterUpdateEncounterType(prevCmdrHull, prevOppHull);

                // Update the opponent fleeing flag.
                switch (getEncounterType()) {
                    case PIRATE_FLEE:
                    case PIRATE_SURRENDER:
                    case POLICE_FLEE:
                    case TRADER_FLEE:
                    case TRADER_SURRENDER:
                        setEncounterOppFleeing(true);
                        break;
                    default:
                        setEncounterOppFleeing(false);
                }

                if (getOptions().isContinuousAttack()
                        && (getEncounterCmdrFleeing() || !getEncounterOppFleeing() || getOptions()
                        .isContinuousAttackFleeing()
                        && (getEncounterType() == prevEncounter || getEncounterType() != EncounterType.PIRATE_SURRENDER
                        && getEncounterType() != EncounterType.TRADER_SURRENDER))) {
                    if (getEncounterCmdrFleeing()) {
                        setEncounterContinueFleeing(true);
                    } else {
                        setEncounterContinueAttacking(true);
                    }
                }
            }
        }

        return result;
    }

    private boolean isEncounterExecuteAttack(Ship attacker, Ship defender, boolean fleeing) {
        boolean hit = false;

        // On beginner level, if you flee, you will escape unharmed.
        // Otherwise, Fighterskill attacker is pitted against pilotskill
        // defender; if defender
        // is fleeing the attacker has a free shot, but the chance to hit is smaller
        // JAF - if the opponent is disabled and attacker has targeting system, they WILL be hit.
        if (!(getDifficulty() == Difficulty.BEGINNER && defender.isCommandersShip() && fleeing)
                && (attacker.isCommandersShip() && getOpponentDisabled()
                && attacker.hasGadget(GadgetType.TARGETING_SYSTEM) || Functions.getRandom(attacker.getFighter()
                + defender.getSize().castToInt()) >= (fleeing ? 2 : 1)
                * Functions.getRandom(5 + defender.getPilot() / 2))) {
            // If the defender is disabled, it only takes one shot to destroy it
            // completely.
            if (attacker.isCommandersShip() && getOpponentDisabled()) {
                defender.setHull(0);
            } else {
                int attackerLasers = attacker.getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.MORGANS_LASER);
                int attackerDisruptors = attacker.getWeaponStrength(WeaponType.PHOTON_DISRUPTOR,
                        WeaponType.QUANTUM_DISRUPTOR);

                if (defender.getType() == ShipType.SCARAB) {
                    attackerLasers -= attacker.getWeaponStrength(WeaponType.BEAM_LASER, WeaponType.MILITARY_LASER);
                    attackerDisruptors -= attacker.getWeaponStrength(WeaponType.PHOTON_DISRUPTOR,
                            WeaponType.PHOTON_DISRUPTOR);
                }

                int attackerWeapons = attackerLasers + attackerDisruptors;

                int disrupt = 0;

                // Attempt to disable the opponent if they're not already disabled, their shields are down,
                // we have disabling weapons, and the option is checked.
                if (defender.isDisableable() && defender.getShieldCharge() == 0 && !getOpponentDisabled()
                        && getOptions().isDisableOpponents() && attackerDisruptors > 0) {
                    disrupt = Functions.getRandom(attackerDisruptors * (100 + 2 * attacker.getFighter()) / 100);
                } else {
                    int damage = (attackerWeapons == 0)
                            ? 0 : Functions.getRandom(attackerWeapons * (100 + 2 * attacker.getFighter()) / 100);

                    if (damage > 0) {
                        hit = true;

                        // Reactor on board -- damage is boosted!
                        if (defender.isReactorOnBoard()) {
                            damage *= (int) (1 + (getDifficulty().castToInt() + 1)
                                    * (getDifficulty().castToInt() < Difficulty.NORMAL.castToInt() ? 0.25 : 0.33));
                        }

                        // First, shields are depleted
                        for (int i = 0; i < defender.getShields().length && defender.getShields()[i] != null && damage > 0; i++) {
                            int applied = Math.min(defender.getShields()[i].getCharge(), damage);
                            defender.getShields()[i].setCharge(defender.getShields()[i].getCharge() - applied);
                            damage -= applied;
                        }

                        // If there still is damage after the shields have been depleted,
                        // this is subtracted from the hull, modified by the engineering skill
                        // of the defender.
                        // JAF - If the player only has disabling weapons, no damage will be done to the hull.
                        if (damage > 0) {
                            damage = Math.max(1, damage - Functions.getRandom(defender.getEngineer()));

                            disrupt = damage * attackerDisruptors / attackerWeapons;

                            // Only that damage coming from Lasers will deplete the hull.
                            damage -= disrupt;

                            // At least 2 shots on Normal level are needed to destroy the hull
                            // (3 on Easy, 4 on Beginner, 1 on Hard or
                            // Impossible). For opponents,
                            // it is always 2.
                            damage = Math.min(damage, defender.getHullStrength()
                                    / (defender.isCommandersShip() ? Math.max(1, spacetrader.game.enums.Difficulty.IMPOSSIBLE
                                    .castToInt() - getDifficulty().castToInt()) : 2));

                            // If the hull is hardened, damage is halved.
                            if (getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_DONE) {
                                damage /= 2;
                            }

                            defender.setHull(Math.max(0, defender.getHull() - damage));
                        }
                    }
                }

                // Did the opponent get disabled? (Disruptors are 3 times more
                // effective against the ship's systems than they are against the shields).
                if (defender.getHull() > 0 && defender.isDisableable()
                        && Functions.getRandom(100) < disrupt * Consts.DisruptorSystemsMultiplier * 100
                        / defender.getHull()) {
                    setOpponentDisabled(true);
                }

                // Make sure the Scorpion doesn't get destroyed.
                if (defender.getType() == ShipType.SCORPION && defender.getHull() == 0) {
                    defender.setHull(1);
                    setOpponentDisabled(true);
                }
            }
        }

        return hit;
    }

    public void encounterMeet() {
        AlertType initialAlert = AlertType.Alert;
        int skill = 0;
        EquipmentType equipType = EquipmentType.GADGET;
        Object equipSubType = null;

        switch (getEncounterType()) {
            case CAPTAIN_AHAB:
                // Trade a reflective shield for skill points in piloting?
                initialAlert = AlertType.MeetCaptainAhab;
                equipType = EquipmentType.SHIELD;
                equipSubType = ShieldType.REFLECTIVE;
                skill = SkillType.PILOT.castToInt();
                break;

            case CAPTAIN_CONRAD:
                // Trade a military laser for skill points in engineering?
                initialAlert = AlertType.MeetCaptainConrad;
                equipType = EquipmentType.WEAPON;
                equipSubType = WeaponType.MILITARY_LASER;
                skill = SkillType.ENGINEER.castToInt();
                break;

            case CAPTAIN_HUIE:
                // Trade a military laser for skill points in trading?
                initialAlert = AlertType.MeetCaptainHuie;
                equipType = EquipmentType.WEAPON;
                equipSubType = WeaponType.MILITARY_LASER;
                skill = SkillType.TRADER.castToInt();
                break;
        }

        if (GuiFacade.alert(initialAlert) == DialogResult.YES) {
            // Remove the equipment we're trading.
            commander.getShip().removeEquipment(equipType, equipSubType);

            // Add points to the appropriate skill - two points if
            // beginner-normal, one otherwise.
            commander.getSkills()[skill] = Math.min(Consts.MaxSkill, commander.getSkills()[skill]
                    + (getDifficulty().castToInt() <= Difficulty.NORMAL.castToInt() ? 2 : 1));

            GuiFacade.alert(AlertType.SpecialTrainingCompleted);
        }
    }

    public void encounterPlunder() {
        GuiFacade.performPlundering();

        if (getEncounterType().castToInt() >= EncounterType.TRADER_ATTACK.castToInt()) {
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScorePlunderTrader);

            if (getOpponentDisabled()) {
                commander.setKillsTrader(commander.getKillsTrader() + 1);
            }
        } else if (getOpponentDisabled()) {
            if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
                GuiFacade.alert(AlertType.EncounterPiratesBounty, Strings.EncounterPiratesDisabled,
                        " " + Strings.EncounterPiratesLocation, Functions.multiples(getOpponent().getBounty(), Strings.MoneyUnit));

                commander.setCash(commander.getCash() + getOpponent().getBounty());
            }

            commander.setKillsPirate(commander.getKillsPirate() + 1);
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
        } else
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScorePlunderPirate);

        commander.setReputationScore(commander.getReputationScore() + (getOpponent().getType().castToInt() / 2 + 1));
    }

    private void encounterScoop() {
        // Chance 50% to pick something up on Normal level, 33% on Hard level, 25% on
        // Impossible level, and 100% on Easy or Beginner.
        if ((getDifficulty().castToInt() < Difficulty.NORMAL.castToInt()
                || Functions.getRandom(getDifficulty().castToInt()) == 0) && getOpponent().getFilledCargoBays() > 0) {
            // Changed this to actually pick a good that was in the opponent's cargo hold - JAF.
            int index = Functions.getRandom(getOpponent().getFilledCargoBays());
            int tradeItem = -1;
            for (int sum = 0; sum <= index; sum += getOpponent().getCargo()[++tradeItem]) {
            }

            if (GuiFacade.alert(AlertType.EncounterScoop, Consts.TradeItems[tradeItem].getName()) == DialogResult.YES) {
                boolean jettisoned = false;

                if (commander.getShip().getFreeCargoBays() == 0
                        && GuiFacade.alert(AlertType.EncounterScoopNoRoom) == DialogResult.YES) {
                    GuiFacade.performJettison();
                    jettisoned = true;
                }

                if (commander.getShip().getFreeCargoBays() > 0) {
                    commander.getShip().getCargo()[tradeItem]++;
                } else if (jettisoned) {
                    GuiFacade.alert(AlertType.EncounterScoopNoScoop);
                }
            }
        }
    }

    public void encounterTrade() {
        boolean buy = (getEncounterType() == EncounterType.TRADER_BUY);
        int item = (buy ? commander.getShip() : getOpponent()).getRandomTradeableItem();
        String alertStr = (buy ? Strings.CargoSelling : Strings.CargoBuying);

        int cash = commander.getCash();

        if (getEncounterType() == EncounterType.TRADER_BUY) {
            cargoSellTrader(item);
        } else {
            // EncounterType.TRADER_SELL
            cargoBuyTrader(item);
        }

        if (commander.getCash() != cash) {
            GuiFacade.alert(AlertType.EncounterTradeCompleted, alertStr, Consts.TradeItems[item].getName());
        }
    }

    private void encounterUpdateEncounterType(int prevCmdrHull, int prevOppHull) {
        int chance = Functions.getRandom(100);

        if (getOpponent().getHull() < prevOppHull || getOpponentDisabled()) {
            switch (getEncounterType()) {
                case FAMOUS_CAPTAIN_ATTACK:
                    if (getOpponentDisabled()) {
                        setEncounterType(EncounterType.FAMOUS_CAPT_DISABLED);
                    }
                    break;
                case PIRATE_ATTACK:
                case PIRATE_FLEE:
                case PIRATE_SURRENDER:
                    if (getOpponentDisabled()) {
                        setEncounterType(EncounterType.PIRATE_DISABLED);
                    } else if (getOpponent().getHull() < (prevOppHull * 2) / 3) {
                        if (commander.getShip().getHull() < (prevCmdrHull * 2) / 3) {
                            if (chance < 60) {
                                setEncounterType(EncounterType.PIRATE_FLEE);
                            }
                        } else {
                            if (chance < 10 && getOpponent().getType() != ShipType.MANTIS) {
                                setEncounterType(EncounterType.PIRATE_SURRENDER);
                            } else {
                                setEncounterType(EncounterType.PIRATE_FLEE);
                            }
                        }
                    }
                    break;
                case POLICE_ATTACK:
                case POLICE_FLEE:
                    if (getOpponentDisabled()) {
                        setEncounterType(EncounterType.POLICE_DISABLED);
                    } else if (getOpponent().getHull() < prevOppHull / 2
                            && (commander.getShip().getHull() >= prevCmdrHull / 2 || chance < 40)) {
                        setEncounterType(EncounterType.POLICE_FLEE);
                    }
                    break;
                case TRADER_ATTACK:
                case TRADER_FLEE:
                case TRADER_SURRENDER:
                    if (getOpponentDisabled()) {
                        setEncounterType(EncounterType.TRADER_DISABLED);
                    } else if (getOpponent().getHull() < (prevOppHull * 2) / 3) {
                        if (chance < 60) {
                            setEncounterType(EncounterType.TRADER_SURRENDER);
                        } else {
                            setEncounterType(EncounterType.TRADER_FLEE);
                        }
                    }
                    // If you get damaged a lot, the trader tends to keep shooting;
                    // if you get damaged a little, the trader may keep shooting;
                    // if you get damaged very little or not at all, the trader will flee.
                    else if (getOpponent().getHull() < (prevOppHull * 9) / 10
                            && (commander.getShip().getHull() < (prevCmdrHull * 2) / 3 && chance < 20
                            || commander.getShip().getHull() < (prevCmdrHull * 9) / 10 && chance < 60 || commander
                            .getShip().getHull() >= (prevCmdrHull * 9) / 10)) {
                        setEncounterType(EncounterType.TRADER_FLEE);
                    }
            }
        }
    }

    public boolean isEncounterVerifyAttack() {
        boolean attack = true;

        if (commander.getShip().getWeaponStrength() == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoWeapons);
            attack = false;
        } else if (!getOpponent().isDisableable()
                && commander.getShip().getWeaponStrength(WeaponType.PULSE_LASER, WeaponType.MORGANS_LASER) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoLasers);
            attack = false;
        } else if (getOpponent().getType() == ShipType.SCORPION
                && commander.getShip().getWeaponStrength(WeaponType.PHOTON_DISRUPTOR, WeaponType.QUANTUM_DISRUPTOR) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoDisruptors);
            attack = false;
        } else {
            switch (getEncounterType()) {
                case DRAGONFLY_IGNORE:
                case PIRATE_IGNORE:
                case SCARAB_IGNORE:
                case SCORPION_IGNORE:
                case SPACE_MONSTER_IGNORE:
                    setEncounterType(EncounterType.fromInt(getEncounterType().castToInt() - 1));
                    break;

                case POLICE_INSPECT:
                    if (!commander.getShip().isDetectableIllegalCargoOrPassengers()
                            && GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) != DialogResult.YES) {
                        attack = false;
                    }

                    // Fall through...
                    if (attack) {
                    }// goto case POLICE_IGNORE;
                    else {
                        break;
                    }

                case MARIE_CELESTE_POLICE:
                case POLICE_FLEE:
                case POLICE_IGNORE:
                case POLICE_SURRENDER:
                    if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                            || GuiFacade.alert(AlertType.EncounterAttackPolice) == DialogResult.YES) {
                        if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                            commander.setPoliceRecordScore(Consts.PoliceRecordScoreCriminal);
                        }

                        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackPolice);

                        if (getEncounterType() != EncounterType.POLICE_FLEE) {
                            setEncounterType(EncounterType.POLICE_ATTACK);
                        }
                    } else {
                        attack = false;
                    }
                    break;

                case TRADER_BUY:
                case TRADER_IGNORE:
                case TRADER_SELL:
                    if (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreClean) {
                        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackTrader);
                    } else if (GuiFacade.alert(AlertType.EncounterAttackTrader) == DialogResult.YES) {
                        commander.setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                    } else {
                        attack = false;
                    }

                    // Fall through...
                    if (attack) {
                    }// goto case TRADER_ATTACK;
                    else {
                        break;
                    }

                case TRADER_ATTACK:
                case TRADER_SURRENDER:
                    if (Functions.getRandom(Consts.ReputationScoreElite) <= commander.getReputationScore() * 10
                            / (getOpponent().getType().castToInt() + 1)
                            || getOpponent().getWeaponStrength() == 0) {
                        setEncounterType(EncounterType.TRADER_FLEE);
                    } else {
                        setEncounterType(EncounterType.TRADER_ATTACK);
                    }
                    break;

                case CAPTAIN_AHAB:
                case CAPTAIN_CONRAD:
                case CAPTAIN_HUIE:
                    if (GuiFacade.alert(AlertType.EncounterAttackCaptain) == DialogResult.YES) {
                        if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreVillain) {
                            commander.setPoliceRecordScore(Consts.PoliceRecordScoreVillain);
                        }

                        commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackTrader);

                        switch (getEncounterType()) {
                            case CAPTAIN_AHAB:
                                newsAddEvent(NewsEvent.CaptAhabAttacked);
                                break;
                            case CAPTAIN_CONRAD:
                                newsAddEvent(NewsEvent.CaptConradAttacked);
                                break;
                            case CAPTAIN_HUIE:
                                newsAddEvent(NewsEvent.CaptHuieAttacked);
                                break;
                        }

                        setEncounterType(EncounterType.FAMOUS_CAPTAIN_ATTACK);
                    } else {
                        attack = false;
                    }
                    break;
            }

            // Make sure the fleeing flag isn't set if we're attacking.
            if (attack) {
                setEncounterCmdrFleeing(false);
            }
        }

        return attack;
    }

    public boolean isEncounterVerifyBoard() {
        boolean board = false;

        if (GuiFacade.alert(AlertType.EncounterMarieCeleste) == DialogResult.YES) {
            board = true;

            int narcs = commander.getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()];

            GuiFacade.performPlundering();

            if (commander.getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()] > narcs) {
                setJustLootedMarie(true);
            }
        }

        return board;
    }

    public boolean isEncounterVerifyBribe() {
        boolean bribed = false;

        if (getEncounterType() == EncounterType.MARIE_CELESTE_POLICE) {
            GuiFacade.alert(AlertType.EncounterMarieCelesteNoBribe);
        } else if (getWarpSystem().getPoliticalSystem().getBribeLevel() <= 0) {
            GuiFacade.alert(AlertType.EncounterPoliceBribeCant);
        } else if (commander.getShip().isDetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            // Bribe depends on how easy it is to bribe the police and commander's current worth
            int diffMod = 10 + 5 * (Difficulty.IMPOSSIBLE.castToInt() - getDifficulty().castToInt());
            int passMod = commander.getShip().isIllegalSpecialCargo()
                    ? (getDifficulty().castToInt() <= Difficulty.NORMAL.castToInt() ? 2 : 3)
                    : 1;

            int bribe = Math.max(100, Math.min(10000, (int) Math.ceil((double) commander.getWorth()
                    / getWarpSystem().getPoliticalSystem().getBribeLevel() / diffMod / 100) * 100 * passMod));

            if (GuiFacade.alert(AlertType.EncounterPoliceBribe, Functions.multiples(bribe, Strings.MoneyUnit)) == DialogResult.YES) {
                if (commander.getCash() >= bribe) {
                    commander.setCash(commander.getCash() - bribe);
                    bribed = true;
                } else {
                    GuiFacade.alert(AlertType.EncounterPoliceBribeLowCash);
                }
            }
        }

        return bribed;
    }

    public boolean isEncounterVerifyFlee() {
        setEncounterCmdrFleeing(false);

        if (getEncounterType() != EncounterType.POLICE_INSPECT
                || commander.getShip().isDetectableIllegalCargoOrPassengers()
                || GuiFacade.alert(AlertType.EncounterPoliceNothingIllegal) == DialogResult.YES) {
            setEncounterCmdrFleeing(true);

            if (getEncounterType() == EncounterType.MARIE_CELESTE_POLICE
                    && GuiFacade.alert(AlertType.EncounterPostMarieFlee) == DialogResult.NO) {
                setEncounterCmdrFleeing(false);
            } else if (getEncounterType() == EncounterType.POLICE_INSPECT
                    || getEncounterType() == EncounterType.MARIE_CELESTE_POLICE) {
                int scoreMod = (getEncounterType() == EncounterType.POLICE_INSPECT) ? Consts.ScoreFleePolice
                        : Consts.ScoreAttackPolice;
                int scoreMin = (getEncounterType() == EncounterType.POLICE_INSPECT) ? Consts.PoliceRecordScoreDubious
                        - (getDifficulty().castToInt() < Difficulty.NORMAL.castToInt() ? 0 : 1)
                        : Consts.PoliceRecordScoreCriminal;

                setEncounterType(EncounterType.POLICE_ATTACK);
                commander.setPoliceRecordScore(Math.min(commander.getPoliceRecordScore() + scoreMod, scoreMin));
            }
        }

        return getEncounterCmdrFleeing();
    }

    public boolean isEncounterVerifySubmit() {
        boolean submit = false;

        if (commander.getShip().isDetectableIllegalCargoOrPassengers()) {
            String str1 = commander.getShip().getIllegalSpecialCargoDescription("", true, true);
            String str2 = commander.getShip().isIllegalSpecialCargo() ? Strings.EncounterPoliceSubmitArrested : "";

            if (GuiFacade.alert(AlertType.EncounterPoliceSubmit, str1, str2) == DialogResult.YES) {
                submit = true;

                // If you carry illegal goods, they are impounded and you are fined
                if (commander.getShip().isDetectableIllegalCargo()) {
                    commander.getShip().removeIllegalGoods();

                    int fine = (int) Math.max(100, Math.min(10000,
                            Math.ceil((double) commander.getWorth()
                                            / ((Difficulty.IMPOSSIBLE.castToInt()
                                            - getDifficulty().castToInt() + 2) * 10) / 50) * 50));
                    int cashPayment = Math.min(commander.getCash(), fine);
                    commander.setDebt(commander.getDebt() + (fine - cashPayment));
                    commander.setCash(commander.getCash() - cashPayment);

                    GuiFacade.alert(AlertType.EncounterPoliceFine, Functions.multiples(fine, Strings.MoneyUnit));

                    commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreTrafficking);
                }
            }
        } else {
            submit = true;

            // If you aren't carrying illegal cargo or passengers, the police
            // will increase your lawfulness record
            GuiFacade.alert(AlertType.EncounterPoliceNothingFound);
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() - Consts.ScoreTrafficking);
        }

        return submit;
    }

    public EncounterResult getEncounterVerifySurrender() {
        EncounterResult result = EncounterResult.CONTINUE;

        if (getOpponent().getType() == ShipType.MANTIS) {
            if (commander.getShip().isArtifactOnBoard()) {
                if (GuiFacade.alert(AlertType.EncounterAliensSurrender) == DialogResult.YES) {
                    GuiFacade.alert(AlertType.ArtifactRelinquished);
                    setQuestStatusArtifact(SpecialEvent.STATUS_ARTIFACT_NOT_STARTED);

                    result = EncounterResult.NORMAL;
                }
            } else {
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
            }
        } else if (getEncounterType() == EncounterType.POLICE_ATTACK || getEncounterType() == EncounterType.POLICE_SURRENDER) {
            if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScorePsychopath) {
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
            } else if (GuiFacade.alert(AlertType.EncounterPoliceSurrender, (new String[]{
                    commander.getShip().getIllegalSpecialCargoDescription(Strings.EncounterPoliceSurrenderCargo, true,
                            false), commander.getShip().illegalSpecialCargoActions()})) == DialogResult.YES) {
                result = EncounterResult.ARRESTED;
            }
        } else if (commander.getShip().isPrincessOnBoard() && !commander.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            GuiFacade.alert(AlertType.EncounterPiratesSurrenderPrincess);
        } else {
            setRaided(true);

            if (commander.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
                ArrayList<String> precious = new ArrayList<>();
                if (commander.getShip().isPrincessOnBoard()) {
                    precious.add(Strings.EncounterHidePrincess);
                }
                if (commander.getShip().isSculptureOnBoard()) {
                    precious.add(Strings.EncounterHideSculpture);
                }

                GuiFacade.alert(AlertType.PreciousHidden, Functions.stringVars(Strings.ListStrings[precious.size()],
                        precious.toArray(new String[0])));
            } else if (commander.getShip().isSculptureOnBoard()) {
                setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_NOT_STARTED);
                GuiFacade.alert(AlertType.EncounterPiratesTakeSculpture);
            }

            ArrayList<Integer> cargoToSteal = commander.getShip().getStealableCargo();
            if (cargoToSteal.size() == 0) {
                int blackmail = Math.min(25000, Math.max(500, commander.getWorth() / 20));
                int cashPayment = Math.min(commander.getCash(), blackmail);
                commander.setDebt(commander.getDebt() + (blackmail - cashPayment));
                commander.setCash(commander.getCash() - cashPayment);
                GuiFacade.alert(AlertType.EncounterPiratesFindNoCargo, Functions
                        .multiples(blackmail, Strings.MoneyUnit));
            } else {
                GuiFacade.alert(AlertType.EncounterLooting);

                // Pirates steal as much as they have room for, which could be everything - JAF.
                // Take most high-priced items - JAF.
                while (getOpponent().getFreeCargoBays() > 0 && cargoToSteal.size() > 0) {
                    int item = cargoToSteal.get(0);

                    commander.getPriceCargo()[item] -= commander.getPriceCargo()[item]
                            / commander.getShip().getCargo()[item];
                    commander.getShip().getCargo()[item]--;
                    getOpponent().getCargo()[item]++;

                    cargoToSteal.remove(0);
                }
            }

            if (commander.getShip().isWildOnBoard()) {
                if (getOpponent().getCrewQuarters() > 1) {
                    // Wild hops onto Pirate Ship
                    setQuestStatusWild(SpecialEvent.STATUS_WILD_NOT_STARTED);
                    GuiFacade.alert(AlertType.WildGoesPirates);
                } else {
                    GuiFacade.alert(AlertType.WildChatsPirates);
                }
            }

            // pirates puzzled by reactor
            if (commander.getShip().isReactorOnBoard()) {
                GuiFacade.alert(AlertType.EncounterPiratesExamineReactor);
            }

            result = EncounterResult.NORMAL;
        }

        return result;
    }

    public EncounterResult getEncounterVerifyYield() {
        EncounterResult result = EncounterResult.CONTINUE;

        if (commander.getShip().isIllegalSpecialCargo()) {
            if (GuiFacade.alert(AlertType.EncounterPoliceSurrender, (new String[]{
                    commander.getShip().getIllegalSpecialCargoDescription(Strings.EncounterPoliceSurrenderCargo, true,
                            true), commander.getShip().illegalSpecialCargoActions()})) == DialogResult.YES) {
                result = EncounterResult.ARRESTED;
            }
        } else {
            String str1 = commander.getShip().getIllegalSpecialCargoDescription("", false, true);

            if (GuiFacade.alert(AlertType.EncounterPoliceSubmit, str1, "") == DialogResult.YES) {
                // Police Record becomes dubious, if it wasn't already.
                if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreDubious) {
                    commander.setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                }

                commander.getShip().removeIllegalGoods();

                result = EncounterResult.NORMAL;
            }
        }

        return result;
    }

    private void encounterWon() {
        if (getEncounterType().castToInt() >= EncounterType.PIRATE_ATTACK.castToInt()
                && getEncounterType().castToInt() <= EncounterType.PIRATE_DISABLED.castToInt()
                && getOpponent().getType() != ShipType.MANTIS
                && commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
            GuiFacade.alert(AlertType.EncounterPiratesBounty, Strings.EncounterPiratesDestroyed, "", Functions
                    .multiples(getOpponent().getBounty(), Strings.MoneyUnit));
        } else {
            GuiFacade.alert(AlertType.EncounterYouWin);
        }

        switch (getEncounterType()) {
            case FAMOUS_CAPTAIN_ATTACK:
                commander.setKillsTrader(commander.getKillsTrader() + 1);
                if (commander.getReputationScore() < Consts.ReputationScoreDangerous) {
                    commander.setReputationScore(Consts.ReputationScoreDangerous);
                } else {
                    commander.setReputationScore(commander.getReputationScore() + Consts.ScoreKillCaptain);
                }

                // bump news flag from attacked to ship destroyed
                newsReplaceEvent(getNewsLatestEvent(), NewsEvent.fromInt(getNewsLatestEvent().castToInt() + 1));
                break;
            case DRAGONFLY_ATTACK:
                encounterDefeatDragonfly();
                break;
            case PIRATE_ATTACK:
            case PIRATE_FLEE:
            case PIRATE_SURRENDER:
                commander.setKillsPirate(commander.getKillsPirate() + 1);
                if (getOpponent().getType() != ShipType.MANTIS) {
                    if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious) {
                        commander.setCash(commander.getCash() + getOpponent().getBounty());
                    }
                    commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
                    encounterScoop();
                }
                break;
            case POLICE_ATTACK:
            case POLICE_FLEE:
                commander.setKillsPolice(commander.getKillsPolice() + 1);
                commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPolice);
                break;
            case SCARAB_ATTACK:
                encounterDefeatScarab();
                break;
            case SPACE_MONSTER_ATTACK:
                commander.setKillsPirate(commander.getKillsPirate() + 1);
                commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillPirate);
                setQuestStatusSpaceMonster(SpecialEvent.STATUS_SPACE_MONSTER_DESTROYED);
                break;
            case TRADER_ATTACK:
            case TRADER_FLEE:
            case TRADER_SURRENDER:
                commander.setKillsTrader(commander.getKillsTrader() + 1);
                commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreKillTrader);
                encounterScoop();
                break;
        }

        commander.setReputationScore(commander.getReputationScore() + (getOpponent().getType().castToInt() / 2 + 1));
    }

    private void escapeWithPod() {
        GuiFacade.alert(AlertType.EncounterEscapePodActivated);

        if (commander.getShip().isSculptureOnBoard()) {
            GuiFacade.alert(AlertType.SculptureSaved);
        }

        if (commander.getShip().isReactorOnBoard()) {
            GuiFacade.alert(AlertType.ReactorDestroyed);
            setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_DONE);
        }

        if (commander.getShip().getTribbles() > 0) {
            GuiFacade.alert(AlertType.TribblesKilled);
        }

        if (getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT) {
            int system;
            for (system = 0; system < getUniverse().length
                    && getUniverse()[system].getSpecialEventType() != SpecialEventType.Japori; system++) {
            }
            GuiFacade.alert(AlertType.AntidoteDestroyed, getUniverse()[system].getName());
            setQuestStatusJapori(SpecialEvent.STATUS_JAPORI_NOT_STARTED);
        }

        if (commander.getShip().isArtifactOnBoard()) {
            GuiFacade.alert(AlertType.ArtifactLost);
            setQuestStatusArtifact(SpecialEvent.STATUS_ARTIFACT_DONE);
        }

        if (commander.getShip().isJarekOnBoard()) {
            GuiFacade.alert(AlertType.JarekTakenHome);
            setQuestStatusJarek(SpecialEvent.STATUS_JAREK_NOT_STARTED);
        }

        if (commander.getShip().isPrincessOnBoard()) {
            GuiFacade.alert(AlertType.PrincessTakenHome);
            setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_NOT_STARTED);
        }

        if (commander.getShip().isWildOnBoard()) {
            GuiFacade.alert(AlertType.WildArrested);
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreCaughtWithWild);
            newsAddEvent(NewsEvent.WildArrested);
            setQuestStatusWild(SpecialEvent.STATUS_WILD_NOT_STARTED);
        }

        if (commander.getInsurance()) {
            GuiFacade.alert(AlertType.InsurancePayoff);
            commander.setCash(commander.getCash() + commander.getShip().getBaseWorth(true));
        }

        if (commander.getCash() > Consts.FleaConversionCost) {
            commander.setCash(commander.getCash() - Consts.FleaConversionCost);
        } else {
            commander.setDebt(commander.getDebt() + (Consts.FleaConversionCost - commander.getCash()));
            commander.setCash(0);
        }

        GuiFacade.alert(AlertType.FleaBuilt);

        incDays(3);

        createFlea();
    }

    private boolean isFindDistantSystem(StarSystemId baseSystem, SpecialEventType specEvent) {
        int bestDistance = 999;
        int system = -1;
        for (int i = 0; i < getUniverse().length; i++) {
            int distance = Functions.distance(getUniverse()[baseSystem.castToInt()], getUniverse()[i]);
            if (distance >= 70 && distance < bestDistance && getUniverse()[i].getSpecialEventType() == SpecialEventType.NA) {
                system = i;
                bestDistance = distance;
            }
        }
        if (system >= 0) {
            getUniverse()[system].setSpecialEventType(specEvent);
        }

        return (system >= 0);
    }

    private void generateCrewMemberList() {
        int[] used = new int[getUniverse().length];
        int d = getDifficulty().castToInt();

        // Zeethibal may be on Kravat
        used[StarSystemId.Kravat.castToInt()] = 1;

        // special individuals:
        // Zeethibal, Jonathan Wild's Nephew - skills will be set later.
        // Wild, Jonathan Wild earns his keep now - JAF.
        // Jarek, Ambassador Jarek earns his keep now - JAF.
        // Dummy pilots for opponents.
        getMercenaries()[CrewMemberId.ZEETHIBAL.castToInt()] = new CrewMember(CrewMemberId.ZEETHIBAL, 5, 5, 5, 5,
                StarSystemId.NA);
        getMercenaries()[CrewMemberId.OPPONENT.castToInt()] = new CrewMember(CrewMemberId.OPPONENT, 5, 5, 5, 5,
                StarSystemId.NA);
        getMercenaries()[CrewMemberId.WILD.castToInt()] = new CrewMember(CrewMemberId.WILD, 7, 10, 2, 5, StarSystemId.NA);
        getMercenaries()[CrewMemberId.JAREK.castToInt()] = new CrewMember(CrewMemberId.JAREK, 3, 2, 10, 4, StarSystemId.NA);
        getMercenaries()[CrewMemberId.PRINCESS.castToInt()] = new CrewMember(CrewMemberId.PRINCESS, 4, 3, 8, 9,
                StarSystemId.NA);
        getMercenaries()[CrewMemberId.FAMOUS_CAPTAIN.castToInt()] = new CrewMember(CrewMemberId.FAMOUS_CAPTAIN, 10, 10, 10,
                10, StarSystemId.NA);
        getMercenaries()[CrewMemberId.DRAGONFLY.castToInt()] = new CrewMember(CrewMemberId.DRAGONFLY, 4 + d, 6 + d, 1,
                6 + d, StarSystemId.NA);
        getMercenaries()[CrewMemberId.SCARAB.castToInt()] = new CrewMember(CrewMemberId.SCARAB, 5 + d, 6 + d, 1, 6 + d,
                StarSystemId.NA);
        getMercenaries()[CrewMemberId.SCORPION.castToInt()] = new CrewMember(CrewMemberId.SCORPION, 8 + d, 8 + d, 1,
                6 + d, StarSystemId.NA);
        getMercenaries()[CrewMemberId.SPACE_MONSTER.castToInt()] = new CrewMember(CrewMemberId.SPACE_MONSTER, 8 + d, 8 + d,
                1, 1 + d, StarSystemId.NA);

        // JAF - Changing this to allow multiple mercenaries in each system, but no more than three.
        for (int i = 1; i < getMercenaries().length; i++) {
            // Only create a CrewMember Object if one doesn't already exist in this slot in the array.
            if (getMercenaries()[i] == null) {
                StarSystemId id;
                boolean ok = false;

                do {
                    id = StarSystemId.fromInt(Functions.getRandom(getUniverse().length));
                    if (used[id.castToInt()] < 3) {
                        used[id.castToInt()]++;
                        ok = true;
                    }
                } while (!ok);

                getMercenaries()[i] = new CrewMember(CrewMemberId.fromInt(i), Functions.randomSkill(),
                        Functions.randomSkill(), Functions.randomSkill(), Functions.randomSkill(), id);
            }
        }
    }

    private void generateOpponent(OpponentType oppType) {
        setOpponent(new Ship(oppType));
    }

    private void generateUniverse() {
        universe = new StarSystem[Strings.SystemNames.length];
        // _universe = new StarSystem[10];

        for (int i = 0; i < getUniverse().length; i++) {
            StarSystemId id = (StarSystemId.fromInt(i));
            SystemPressure pressure = SystemPressure.NONE;
            SpecialResource specRes = SpecialResource.NOTHING;
            Size size = Size.fromInt(Functions.getRandom(Size.HUGE.castToInt() + 1));
            PoliticalSystem polSys = Consts.PoliticalSystems[Functions.getRandom(Consts.PoliticalSystems.length)];
            TechLevel tech = TechLevel.fromInt(Functions.getRandom(polSys.getMinimumTechLevel().castToInt(), polSys
                    .getMaximumTechLevel().castToInt() + 1));

            // Galvon must be a Monarchy.
            if (id == StarSystemId.Galvon) {
                size = Size.LARGE;
                polSys = Consts.PoliticalSystems[PoliticalSystemType.MONARCHY.castToInt()];
                tech = TechLevel.HI_TECH;
            }

            if (Functions.getRandom(100) < 15)
                pressure = SystemPressure.fromInt(Functions.getRandom(SystemPressure.WAR.castToInt(),
                        SystemPressure.EMPLOYMENT.castToInt() + 1));
            if (Functions.getRandom(5) >= 3)
                specRes = SpecialResource.fromInt(Functions.getRandom(SpecialResource.MINERAL_RICH.castToInt(),
                        SpecialResource.WARLIKE.castToInt() + 1));

            int x = 0;
            int y = 0;

            if (i < getWormholes().length) {
                // Place the first systems somewhere in the center.
                x = ((Consts.GalaxyWidth * (1 + 2 * (i % 3))) / 6)
                        - Functions.getRandom(-Consts.CloseDistance + 1, Consts.CloseDistance);
                y = ((Consts.GalaxyHeight * (i < 3 ? 1 : 3)) / 4)
                        - Functions.getRandom(-Consts.CloseDistance + 1, Consts.CloseDistance);
                getWormholes()[i] = i;
            } else {
                boolean ok = false;
                while (!ok) {
                    x = Functions.getRandom(1, Consts.GalaxyWidth);
                    y = Functions.getRandom(1, Consts.GalaxyHeight);

                    boolean closeFound = false;
                    boolean tooClose = false;
                    for (int j = 0; j < i && !tooClose; j++) {
                        // Minimum distance between any two systems not to be accepted.
                        if (Functions.distance(getUniverse()[j], x, y) < Consts.MinDistance) {
                            tooClose = true;
                        }

                        // There should be at least one system which is close enough.
                        if (Functions.distance(getUniverse()[j], x, y) < Consts.CloseDistance) {
                            closeFound = true;
                        }
                    }
                    ok = (closeFound && !tooClose);
                }
            }

            getUniverse()[i] = new StarSystem(id, x, y, size, tech, polSys.getType(), pressure, specRes);
        }

        // Randomize the system locations a bit more, otherwise the systems with the first
        // names in the alphabet are all in the center.
        for (int i = 0; i < getUniverse().length; i++) {
            int j = Functions.getRandom(getUniverse().length);
            if (!Functions.wormholeExists(j, -1)) {
                int x = getUniverse()[i].getX();
                int y = getUniverse()[i].getY();
                getUniverse()[i].setX(getUniverse()[j].getX());
                getUniverse()[i].setY(getUniverse()[j].getY());
                getUniverse()[j].setX(x);
                getUniverse()[j].setY(y);

                int w = Util.bruteSeek(getWormholes(), i);
                if (w >= 0) {
                    getWormholes()[w] = j;
                }
            }
        }

        // Randomize wormhole order
        for (int i = 0; i < getWormholes().length; i++) {
            int j = Functions.getRandom(getWormholes().length);
            int w = getWormholes()[i];
            getWormholes()[i] = getWormholes()[j];
            getWormholes()[j] = w;
        }
    }

    public void handleSpecialEvent() {
        StarSystem curSys = commander.getCurrentSystem();
        boolean remove = true;

        switch (curSys.getSpecialEventType()) {
            case Artifact:
                setQuestStatusArtifact(SpecialEvent.STATUS_ARTIFACT_ON_BOARD);
                break;
            case ArtifactDelivery:
                setQuestStatusArtifact(SpecialEvent.STATUS_ARTIFACT_DONE);
                break;
            case CargoForSale:
                GuiFacade.alert(AlertType.SpecialSealedCanisters);
                int tradeItem = Functions.getRandom(Consts.TradeItems.length);
                commander.getShip().getCargo()[tradeItem] += 3;
                commander.getPriceCargo()[tradeItem] += commander.getCurrentSystem().specialEvent().getPrice();
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
                if (commander.getShip().getFreeShieldSlots() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentLightningShield);
                    commander.getShip().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
                    setQuestStatusDragonfly(SpecialEvent.STATUS_DRAGONFLY_DONE);
                }
                break;
            case EraseRecord:
                GuiFacade.alert(AlertType.SpecialCleanRecord);
                commander.setPoliceRecordScore(Consts.PoliceRecordScoreClean);
                recalculateSellPrices(curSys);
                break;
            case Experiment:
                setQuestStatusExperiment(SpecialEvent.STATUS_EXPERIMENT_STARTED);
                break;
            case ExperimentFailed:
                break;
            case ExperimentStopped:
                setQuestStatusExperiment(SpecialEvent.STATUS_EXPERIMENT_CANCELLED);
                setCanSuperWarp(true);
                break;
            case Gemulon:
                setQuestStatusGemulon(SpecialEvent.STATUS_GEMULON_STARTED);
                break;
            case GemulonFuel:
                if (commander.getShip().getFreeGadgetSlots() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentFuelCompactor);
                    commander.getShip().addEquipment(Consts.Gadgets[GadgetType.FUEL_COMPACTOR.castToInt()]);
                    setQuestStatusGemulon(SpecialEvent.STATUS_GEMULON_DONE);
                }
                break;
            case GemulonRescued:
                curSys.setSpecialEventType(SpecialEventType.GemulonFuel);
                setQuestStatusGemulon(SpecialEvent.STATUS_GEMULON_FUEL);
                remove = false;
                break;
            case Japori:
                // The japori quest should not be removed since you can fail and start it over again.
                remove = false;

                if (commander.getShip().getFreeCargoBays() < 10) {
                    GuiFacade.alert(AlertType.CargoNoEmptyBays);
                } else {
                    GuiFacade.alert(AlertType.AntidoteOnBoard);
                    setQuestStatusJapori(SpecialEvent.STATUS_JAPORI_IN_TRANSIT);
                }
                break;
            case JaporiDelivery:
                setQuestStatusJapori(SpecialEvent.STATUS_JAPORI_DONE);
                commander.increaseRandomSkill();
                commander.increaseRandomSkill();
                break;
            case Jarek:
                if (commander.getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                    remove = false;
                } else {
                    CrewMember jarek = getMercenaries()[CrewMemberId.JAREK.castToInt()];
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                    commander.getShip().hire(jarek);
                    setQuestStatusJarek(SpecialEvent.STATUS_JAREK_STARTED);
                }
                break;
            case JarekGetsOut:
                setQuestStatusJarek(SpecialEvent.STATUS_JAREK_DONE);
                commander.getShip().fire(CrewMemberId.JAREK);
                break;
            case Lottery:
                break;
            case Moon:
                GuiFacade.alert(AlertType.SpecialMoonBought);
                setQuestStatusMoon(SpecialEvent.STATUS_MOON_BOUGHT);
                break;
            case MoonRetirement:
                setQuestStatusMoon(SpecialEvent.STATUS_MOON_DONE);
                throw new GameEndException(GameEndType.BOUGHT_MOON);
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
                if (commander.getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                    remove = false;
                } else {
                    CrewMember princess = getMercenaries()[CrewMemberId.PRINCESS.castToInt()];
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, princess.getName());
                    commander.getShip().hire(princess);
                }
                break;
            case PrincessQuantum:
                if (commander.getShip().getFreeWeaponSlots() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentQuantumDisruptor);
                    commander.getShip().addEquipment(Consts.Weapons[WeaponType.QUANTUM_DISRUPTOR.castToInt()]);
                    setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_DONE);
                }
                break;
            case PrincessReturned:
                commander.getShip().fire(CrewMemberId.PRINCESS);
                curSys.setSpecialEventType(SpecialEventType.PrincessQuantum);
                setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_RETURNED);
                remove = false;
                break;
            case Reactor:
                if (commander.getShip().getFreeCargoBays() < 15) {
                    GuiFacade.alert(AlertType.CargoNoEmptyBays);
                    remove = false;
                } else {
                    if (commander.getShip().isWildOnBoard()) {
                        if (GuiFacade.alert(AlertType.WildWontStayAboardReactor, curSys.getName()) == DialogResult.OK) {
                            GuiFacade.alert(AlertType.WildLeavesShip, curSys.getName());
                            setQuestStatusWild(SpecialEvent.STATUS_WILD_NOT_STARTED);
                        } else {
                            remove = false;
                        }
                    }

                    if (remove) {
                        GuiFacade.alert(AlertType.ReactorOnBoard);
                        setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_FUEL_OK);
                    }
                }
                break;
            case ReactorDelivered:
                curSys.setSpecialEventType(SpecialEventType.ReactorLaser);
                setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_DELIVERED);
                remove = false;
                break;
            case ReactorLaser:
                if (commander.getShip().getFreeWeaponSlots() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentMorgansLaser);
                    commander.getShip().addEquipment(Consts.Weapons[WeaponType.MORGANS_LASER.castToInt()]);
                    setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_DONE);
                }
                break;
            case Scarab:
                setQuestStatusScarab(SpecialEvent.STATUS_SCARAB_HUNTING);
                break;
            case ScarabDestroyed:
                setQuestStatusScarab(SpecialEvent.STATUS_SCARAB_DESTROYED);
                curSys.setSpecialEventType(SpecialEventType.ScarabUpgradeHull);
                remove = false;
                break;
            case ScarabUpgradeHull:
                GuiFacade.alert(AlertType.ShipHullUpgraded);
                commander.getShip().setHullUpgraded(true);
                commander.getShip().setHull(commander.getShip().getHull() + Consts.HullUpgrade);
                setQuestStatusScarab(SpecialEvent.STATUS_SCARAB_DONE);
                remove = false;
                break;
            case Sculpture:
                setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_IN_TRANSIT);
                break;
            case SculptureDelivered:
                setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_DELIVERED);
                curSys.setSpecialEventType(SpecialEventType.SculptureHiddenBays);
                remove = false;
                break;
            case SculptureHiddenBays:
                setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_DONE);
                if (commander.getShip().getFreeGadgetSlots() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                    remove = false;
                } else {
                    GuiFacade.alert(AlertType.EquipmentHiddenCompartments);
                    commander.getShip().addEquipment(Consts.Gadgets[GadgetType.HIDDEN_CARGO_BAYS.castToInt()]);
                    setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_DONE);
                }
                break;
            case Skill:
                GuiFacade.alert(AlertType.SpecialSkillIncrease);
                commander.increaseRandomSkill();
                break;
            case SpaceMonster:
                setQuestStatusSpaceMonster(SpecialEvent.STATUS_SPACE_MONSTER_AT_ACAMAR);
                break;
            case SpaceMonsterKilled:
                setQuestStatusSpaceMonster(SpecialEvent.STATUS_SPACE_MONSTER_DONE);
                break;
            case Tribble:
                GuiFacade.alert(AlertType.TribblesOwn);
                commander.getShip().setTribbles(1);
                break;
            case TribbleBuyer:
                GuiFacade.alert(AlertType.TribblesGone);
                commander.setCash(commander.getCash() + (commander.getShip().getTribbles() / 2));
                commander.getShip().setTribbles(0);
                break;
            case Wild:
                if (commander.getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                    remove = false;
                } else if (!commander.getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
                    GuiFacade.alert(AlertType.WildWontBoardLaser);
                    remove = false;
                } else if (commander.getShip().isReactorOnBoard()) {
                    GuiFacade.alert(AlertType.WildWontBoardReactor);
                    remove = false;
                } else {
                    CrewMember wild = getMercenaries()[CrewMemberId.WILD.castToInt()];
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, wild.getName());
                    commander.getShip().hire(wild);
                    setQuestStatusWild(SpecialEvent.STATUS_WILD_STARTED);

                    if (commander.getShip().isSculptureOnBoard()) {
                        GuiFacade.alert(AlertType.WildSculpture);
                    }
                }
                break;
            case WildGetsOut:
                // Zeethibal has a 10 in player's lowest score, an 8 in the next lowest score, and 5 elsewhere.
                CrewMember zeethibal = getMercenaries()[CrewMemberId.ZEETHIBAL.castToInt()];
                zeethibal.setCurrentSystem(getUniverse()[StarSystemId.Kravat.castToInt()]);
                int lowest1 = commander.nthLowestSkill(1);
                int lowest2 = commander.nthLowestSkill(2);
                for (int i = 0; i < zeethibal.getSkills().length; i++) {
                    zeethibal.getSkills()[i] = (i == lowest1 ? 10 : (i == lowest2 ? 8 : 5));
                }

                setQuestStatusWild(SpecialEvent.STATUS_WILD_DONE);
                commander.setPoliceRecordScore(Consts.PoliceRecordScoreClean);
                commander.getShip().fire(CrewMemberId.WILD);
                recalculateSellPrices(curSys);
                break;
        }

        if (curSys.specialEvent().getPrice() != 0) {
            commander.setCash(commander.getCash() - curSys.specialEvent().getPrice());
        }

        if (remove) {
            curSys.setSpecialEventType(SpecialEventType.NA);
        }
    }

    private void incDays(int num) {
        commander.setDays(commander.getDays() + num);

        if (commander.getInsurance()) {
            commander.setNoClaim(commander.getNoClaim() + num);
        }

        // Police Record will gravitate towards neutral (0).
        if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreClean) {
            commander.setPoliceRecordScore(
                    Math.max(Consts.PoliceRecordScoreClean, commander.getPoliceRecordScore() - num / 3));
        } else if (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreDubious) {
            commander.setPoliceRecordScore(
                    Math.min(Consts.PoliceRecordScoreDubious, commander.getPoliceRecordScore() + num
                            / (getDifficulty().castToInt() <= spacetrader.game.enums.Difficulty.NORMAL.castToInt() ? 1
                            : getDifficulty().castToInt())));
        }

        // The Space Monster's strength increases 5% per day until it is back to full strength.
        if (spaceMonster.getHull() < spaceMonster.getHullStrength()) {
            spaceMonster.setHull(
                    Math.min(spaceMonster.getHullStrength(), (int) (spaceMonster.getHull() * Math.pow(1.05, num))));
        }

        if (getQuestStatusGemulon() > SpecialEvent.STATUS_GEMULON_NOT_STARTED
                && getQuestStatusGemulon() < SpecialEvent.STATUS_GEMULON_TOO_LATE) {
            setQuestStatusGemulon(Math.min(getQuestStatusGemulon() + num, SpecialEvent.STATUS_GEMULON_TOO_LATE));
            if (getQuestStatusGemulon() == SpecialEvent.STATUS_GEMULON_TOO_LATE) {
                StarSystem gemulon = getUniverse()[StarSystemId.Gemulon.castToInt()];
                gemulon.setSpecialEventType(SpecialEventType.GemulonInvaded);
                gemulon.setTechLevel(TechLevel.PRE_AGRICULTURAL);
                gemulon.setPoliticalSystemType(PoliticalSystemType.ANARCHY);
            }
        }

        if (commander.getShip().isReactorOnBoard()) {
            setQuestStatusReactor(Math.min(getQuestStatusReactor() + num, SpecialEvent.STATUS_REACTOR_DATE));
        }

        if (getQuestStatusExperiment() > SpecialEvent.STATUS_EXPERIMENT_NOT_STARTED
                && getQuestStatusExperiment() < SpecialEvent.STATUS_EXPERIMENT_PERFORMED) {
            setQuestStatusExperiment(Math.min(getQuestStatusExperiment() + num, SpecialEvent.STATUS_EXPERIMENT_PERFORMED));
            if (getQuestStatusExperiment() == SpecialEvent.STATUS_EXPERIMENT_PERFORMED) {
                setFabricRipProbability(Consts.FabricRipInitialProbability);
                getUniverse()[StarSystemId.Daled.castToInt()].setSpecialEventType(SpecialEventType.ExperimentFailed);
                GuiFacade.alert(AlertType.SpecialExperimentPerformed);
                newsAddEvent(NewsEvent.ExperimentPerformed);
            }
        } else if (getQuestStatusExperiment() == SpecialEvent.STATUS_EXPERIMENT_PERFORMED
                && getFabricRipProbability() > 0) {
            setFabricRipProbability(getFabricRipProbability() - num);
        }

        if (commander.getShip().isJarekOnBoard()) {
            if (getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_IMPATIENT / 2) {
                GuiFacade.alert(AlertType.SpecialPassengerConcernedJarek);
            } else if (getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_IMPATIENT - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientJarek);
                getMercenaries()[CrewMemberId.JAREK.castToInt()].setPilot(0);
                getMercenaries()[CrewMemberId.JAREK.castToInt()].setFighter(0);
                getMercenaries()[CrewMemberId.JAREK.castToInt()].setTrader(0);
                getMercenaries()[CrewMemberId.JAREK.castToInt()].setEngineer(0);
            }

            if (getQuestStatusJarek() < SpecialEvent.STATUS_JAREK_IMPATIENT) {
                setQuestStatusJarek(getQuestStatusJarek() + 1);
            }
        }

        if (commander.getShip().isPrincessOnBoard()) {
            if (getQuestStatusPrincess() == (SpecialEvent.STATUS_PRINCESS_IMPATIENT + SpecialEvent.STATUS_PRINCESS_RESCUED) / 2) {
                GuiFacade.alert(AlertType.SpecialPassengerConcernedPrincess);
            } else if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_IMPATIENT - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientPrincess);
                getMercenaries()[CrewMemberId.PRINCESS.castToInt()].setPilot(0);
                getMercenaries()[CrewMemberId.PRINCESS.castToInt()].setFighter(0);
                getMercenaries()[CrewMemberId.PRINCESS.castToInt()].setTrader(0);
                getMercenaries()[CrewMemberId.PRINCESS.castToInt()].setEngineer(0);
            }

            if (getQuestStatusPrincess() < SpecialEvent.STATUS_PRINCESS_IMPATIENT) {
                setQuestStatusPrincess(getQuestStatusPrincess() + 1);
            }
        }

        if (commander.getShip().isWildOnBoard()) {
            if (getQuestStatusWild() == SpecialEvent.STATUS_WILD_IMPATIENT / 2) {
                GuiFacade.alert(AlertType.SpecialPassengerConcernedWild);
            } else if (getQuestStatusWild() == SpecialEvent.STATUS_WILD_IMPATIENT - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientWild);
                getMercenaries()[CrewMemberId.WILD.castToInt()].setPilot(0);
                getMercenaries()[CrewMemberId.WILD.castToInt()].setFighter(0);
                getMercenaries()[CrewMemberId.WILD.castToInt()].setTrader(0);
                getMercenaries()[CrewMemberId.WILD.castToInt()].setEngineer(0);
            }

            if (getQuestStatusWild() < SpecialEvent.STATUS_WILD_IMPATIENT) {
                setQuestStatusWild(getQuestStatusWild() + 1);
            }
        }
    }

    private Commander initializeCommander(String name, CrewMember commanderCrewMember) {
        Commander commander = new Commander(commanderCrewMember);
        getMercenaries()[CrewMemberId.COMMANDER.castToInt()] = commander;
        Strings.CrewMemberNames[CrewMemberId.COMMANDER.castToInt()] = name;

        while (commander.getCurrentSystem() == null) {
            StarSystem system = getUniverse()[Functions.getRandom(getUniverse().length)];
            if (system.getSpecialEventType() == SpecialEventType.NA
                    && system.getTechLevel().castToInt() > TechLevel.PRE_AGRICULTURAL.castToInt()
                    && system.getTechLevel().castToInt() < TechLevel.HI_TECH.castToInt()) {
                // Make sure at least three other systems can be reached
                int close = 0;
                for (int i = 0; i < getUniverse().length && close < 3; i++) {
                    if (i != system.getId().castToInt()
                            && Functions.distance(getUniverse()[i], system) <= commander.getShip().getFuelTanks()) {
                        close++;
                    }
                }

                if (close >= 3) {
                    commander.setCurrentSystem(system);
                }
            }
        }

        commander.getCurrentSystem().setVisited(true);
        return commander;
    }

    private void newsAddEvent(NewsEvent newEvent) {
        getNewsEvents().add(newEvent);
    }

    private void newsAddEventsOnArrival() {
        if (commander.getCurrentSystem().getSpecialEventType() != SpecialEventType.NA) {
            switch (commander.getCurrentSystem().getSpecialEventType()) {
                case ArtifactDelivery:
                    if (commander.getShip().isArtifactOnBoard()) {
                        newsAddEvent(NewsEvent.ArtifactDelivery);
                    }
                    break;
                case Dragonfly:
                    newsAddEvent(NewsEvent.Dragonfly);
                    break;
                case DragonflyBaratas:
                    if (getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_BARATAS) {
                        newsAddEvent(NewsEvent.DragonflyBaratas);
                    }
                    break;
                case DragonflyDestroyed:
                    if (getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_ZALKON) {
                        newsAddEvent(NewsEvent.DragonflyZalkon);
                    } else if (getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_DESTROYED) {
                        newsAddEvent(NewsEvent.DragonflyDestroyed);
                    }
                    break;
                case DragonflyMelina:
                    if (getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_MELINA) {
                        newsAddEvent(NewsEvent.DragonflyMelina);
                    }
                    break;
                case DragonflyRegulas:
                    if (getQuestStatusDragonfly() == SpecialEvent.STATUS_DRAGONFLY_FLY_REGULAS) {
                        newsAddEvent(NewsEvent.DragonflyRegulas);
                    }
                    break;
                case ExperimentFailed:
                    newsAddEvent(NewsEvent.ExperimentFailed);
                    break;
                case ExperimentStopped:
                    if (getQuestStatusExperiment() > SpecialEvent.STATUS_EXPERIMENT_NOT_STARTED
                            && getQuestStatusExperiment() < SpecialEvent.STATUS_EXPERIMENT_PERFORMED) {
                        newsAddEvent(NewsEvent.ExperimentStopped);
                    }
                    break;
                case Gemulon:
                    newsAddEvent(NewsEvent.Gemulon);
                    break;
                case GemulonRescued:
                    if (getQuestStatusGemulon() > SpecialEvent.STATUS_GEMULON_NOT_STARTED) {
                        if (getQuestStatusGemulon() < SpecialEvent.STATUS_GEMULON_TOO_LATE) {
                            newsAddEvent(NewsEvent.GemulonRescued);
                        } else {
                            newsAddEvent(NewsEvent.GemulonInvaded);
                        }
                    }
                    break;
                case Japori:
                    if (getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_NOT_STARTED) {
                        newsAddEvent(NewsEvent.Japori);
                    }
                    break;
                case JaporiDelivery:
                    if (getQuestStatusJapori() == SpecialEvent.STATUS_JAPORI_IN_TRANSIT) {
                        newsAddEvent(NewsEvent.JaporiDelivery);
                    }
                    break;
                case JarekGetsOut:
                    if (commander.getShip().isJarekOnBoard()) {
                        newsAddEvent(NewsEvent.JarekGetsOut);
                    }
                    break;
                case Princess:
                    newsAddEvent(NewsEvent.Princess);
                    break;
                case PrincessCentauri:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_CENTAURI) {
                        newsAddEvent(NewsEvent.PrincessCentauri);
                    }
                    break;
                case PrincessInthara:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_INTHARA) {
                        newsAddEvent(NewsEvent.PrincessInthara);
                    }
                    break;
                case PrincessQonos:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_FLY_QONOS) {
                        newsAddEvent(NewsEvent.PrincessQonos);
                    } else if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_RESCUED) {
                        newsAddEvent(NewsEvent.PrincessRescued);
                    }
                    break;
                case PrincessReturned:
                    if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_RETURNED) {
                        newsAddEvent(NewsEvent.PrincessReturned);
                    }
                    break;
                case Scarab:
                    newsAddEvent(NewsEvent.Scarab);
                    break;
                case ScarabDestroyed:
                    if (getQuestStatusScarab() == SpecialEvent.STATUS_SCARAB_HUNTING) {
                        newsAddEvent(NewsEvent.ScarabHarass);
                    } else if (getQuestStatusScarab() >= SpecialEvent.STATUS_SCARAB_DESTROYED) {
                        newsAddEvent(NewsEvent.ScarabDestroyed);
                    }
                    break;
                case Sculpture:
                    newsAddEvent(NewsEvent.SculptureStolen);
                    break;
                case SculptureDelivered:
                    newsAddEvent(NewsEvent.SculptureTracked);
                    break;
                case SpaceMonsterKilled:
                    if (getQuestStatusSpaceMonster() == SpecialEvent.STATUS_SPACE_MONSTER_AT_ACAMAR) {
                        newsAddEvent(NewsEvent.SpaceMonster);
                    } else if (getQuestStatusSpaceMonster() >= SpecialEvent.STATUS_SPACE_MONSTER_DESTROYED) {
                        newsAddEvent(NewsEvent.SpaceMonsterKilled);
                    }
                    break;
                case WildGetsOut:
                    if (commander.getShip().isWildOnBoard()) {
                        newsAddEvent(NewsEvent.WildGetsOut);
                    }
                    break;
            }
        }
    }

    private NewsEvent getNewsLatestEvent() {
        return getNewsEvents().get(getNewsEvents().size() - 1);
    }

    private void newsReplaceEvent(NewsEvent oldEvent, NewsEvent newEvent) {
        if (getNewsEvents().indexOf(oldEvent) >= 0) {
            getNewsEvents().remove(oldEvent);
        }
        getNewsEvents().add(newEvent);
    }

    private void newsResetEvents() {
        getNewsEvents().clear();
    }

    private void normalDeparture(int fuel) {
        commander.setCash(commander.getCash() - (getMercenaryCosts() + getInsuranceCosts() + getWormholeCosts()));
        commander.getShip().setFuel(commander.getShip().getFuel() - fuel);
        commander.payInterest();
        incDays(1);
    }

    private boolean isPlaceShipyards() {
        boolean goodUniverse = true;

        ArrayList<Integer> systemIdList = new ArrayList<>();
        for (int system = 0; system < getUniverse().length; system++) {
            if (getUniverse()[system].getTechLevel() == TechLevel.HI_TECH) {
                systemIdList.add(system);
            }
        }

        if (systemIdList.size() < Consts.Shipyards.length) {
            goodUniverse = false;
        } else {
            // Assign the shipyards to High-Tech systems.
            for (int shipyard = 0; shipyard < Consts.Shipyards.length; shipyard++) {
                getUniverse()[systemIdList.get(Functions.getRandom(systemIdList.size()))]
                        .setShipyardId(ShipyardId.fromInt(shipyard));
            }
        }

        return goodUniverse;
    }

    private boolean isPlaceSpecialEvents() {
        boolean goodUniverse = true;
        int system;

        getUniverse()[StarSystemId.Baratas.castToInt()].setSpecialEventType(SpecialEventType.DragonflyBaratas);
        getUniverse()[StarSystemId.Melina.castToInt()].setSpecialEventType(SpecialEventType.DragonflyMelina);
        getUniverse()[StarSystemId.Regulas.castToInt()].setSpecialEventType(SpecialEventType.DragonflyRegulas);
        getUniverse()[StarSystemId.Zalkon.castToInt()].setSpecialEventType(SpecialEventType.DragonflyDestroyed);
        getUniverse()[StarSystemId.Daled.castToInt()].setSpecialEventType(SpecialEventType.ExperimentStopped);
        getUniverse()[StarSystemId.Gemulon.castToInt()].setSpecialEventType(SpecialEventType.GemulonRescued);
        getUniverse()[StarSystemId.Japori.castToInt()].setSpecialEventType(SpecialEventType.JaporiDelivery);
        getUniverse()[StarSystemId.Devidia.castToInt()].setSpecialEventType(SpecialEventType.JarekGetsOut);
        getUniverse()[StarSystemId.Utopia.castToInt()].setSpecialEventType(SpecialEventType.MoonRetirement);
        getUniverse()[StarSystemId.Nix.castToInt()].setSpecialEventType(SpecialEventType.ReactorDelivered);
        getUniverse()[StarSystemId.Acamar.castToInt()].setSpecialEventType(SpecialEventType.SpaceMonsterKilled);
        getUniverse()[StarSystemId.Kravat.castToInt()].setSpecialEventType(SpecialEventType.WildGetsOut);
        getUniverse()[StarSystemId.Endor.castToInt()].setSpecialEventType(SpecialEventType.SculptureDelivered);
        getUniverse()[StarSystemId.Galvon.castToInt()].setSpecialEventType(SpecialEventType.Princess);
        getUniverse()[StarSystemId.Centauri.castToInt()].setSpecialEventType(SpecialEventType.PrincessCentauri);
        getUniverse()[StarSystemId.Inthara.castToInt()].setSpecialEventType(SpecialEventType.PrincessInthara);
        getUniverse()[StarSystemId.Qonos.castToInt()].setSpecialEventType(SpecialEventType.PrincessQonos);

        // Assign a wormhole location endpoint for the Scarab.
        for (system = 0; system < getWormholes().length
                && getUniverse()[getWormholes()[system]].getSpecialEventType() != SpecialEventType.NA; system++) {
        }
        if (system < getWormholes().length) {
            getUniverse()[getWormholes()[system]].setSpecialEventType(SpecialEventType.ScarabDestroyed);
        } else {
            goodUniverse = false;
        }

        // Find a Hi-Tech system without a special event.
        if (goodUniverse) {
            for (system = 0; system < getUniverse().length
                    && !(getUniverse()[system].getSpecialEventType() == SpecialEventType.NA && getUniverse()[system].getTechLevel() == TechLevel.HI_TECH); system++) {
            }
            if (system < getUniverse().length) {
                getUniverse()[system].setSpecialEventType(SpecialEventType.ArtifactDelivery);
            } else {
                goodUniverse = false;
            }
        }

        // Find the closest system at least 70 parsecs away from Nix that doesn't already have a special event.
        if (goodUniverse && !isFindDistantSystem(StarSystemId.Nix, SpecialEventType.Reactor)) {
            goodUniverse = false;
        }

        // Find the closest system at least 70 parsecs away from Gemulon that doesn't already have a special event.
        if (goodUniverse && !isFindDistantSystem(StarSystemId.Gemulon, SpecialEventType.Gemulon)) {
            goodUniverse = false;
        }

        // Find the closest system at least 70 parsecs away from Daled that doesn't already have a special event.
        if (goodUniverse && !isFindDistantSystem(StarSystemId.Daled, SpecialEventType.Experiment)) {
            goodUniverse = false;
        }

        // Find the closest system at least 70 parsecs away from Endor that doesn't already have a special event.
        if (goodUniverse && !isFindDistantSystem(StarSystemId.Endor, SpecialEventType.Sculpture)) {
            goodUniverse = false;
        }

        // Assign the rest of the events randomly.
        if (goodUniverse) {
            for (int i = 0; i < Consts.SpecialEvents.length; i++) {
                for (int j = 0; j < Consts.SpecialEvents[i].getOccurrence(); j++) {
                    do {
                        system = Functions.getRandom(getUniverse().length);
                    } while (getUniverse()[system].getSpecialEventType() != SpecialEventType.NA);

                    getUniverse()[system].setSpecialEventType(Consts.SpecialEvents[i].getType());
                }
            }
        }

        return goodUniverse;
    }

    public void recalculateBuyPrices(StarSystem system) {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            if (!system.isItemTraded(Consts.TradeItems[i])) {
                priceCargoBuy[i] = 0;
            } else {
                priceCargoBuy[i] = priceCargoSell[i];

                if (commander.getPoliceRecordScore() < Consts.PoliceRecordScoreDubious) {
                    priceCargoBuy[i] = priceCargoBuy[i] * 100 / 90;
                }

                // BuyPrice = SellPrice + 1 to 12% (depending on trader skill (minimum is 1, max 12))
                priceCargoBuy[i] = priceCargoBuy[i] * (103 + Consts.MaxSkill - commander.getShip().getTrader()) / 100;

                if (priceCargoBuy[i] <= priceCargoSell[i]) {
                    priceCargoBuy[i] = priceCargoSell[i] + 1;
                }
            }
        }
    }

    // *************************************************************************
    // After erasure of police record, selling prices must be recalculated
    // *************************************************************************
    private void recalculateSellPrices(StarSystem system) {
        for (int i = 0; i < Consts.TradeItems.length; i++) {
            priceCargoSell[i] = priceCargoSell[i] * 100 / 90;
        }
    }

    public void resetVeryRareEncounters() {
        getVeryRareEncounters().clear();
        getVeryRareEncounters().add(VeryRareEncounter.MARIE_CELESTE);
        getVeryRareEncounters().add(VeryRareEncounter.CAPTAIN_AHAB);
        getVeryRareEncounters().add(VeryRareEncounter.CAPTAIN_CONRAD);
        getVeryRareEncounters().add(VeryRareEncounter.CAPTAIN_HUIE);
        getVeryRareEncounters().add(VeryRareEncounter.BOTTLE_OLD);
        getVeryRareEncounters().add(VeryRareEncounter.BOTTLE_GOOD);
    }

    public void selectNextSystemWithinRange(boolean forward) {
        int[] dest = Destinations();

        if (dest.length > 0) {
            int index = spacetrader.util.Util.bruteSeek(dest, getWarpSystemId().castToInt());

            if (index < 0) {
                index = forward ? 0 : dest.length - 1;
            } else {
                index = (dest.length + index + (forward ? 1 : -1)) % dest.length;
            }

            if (Functions.wormholeExists(commander.getCurrentSystem(), getUniverse()[dest[index]])) {
                setSelectedSystemId(commander.getCurrentSystemId());
                setTargetWormhole(true);
            } else {
                setSelectedSystemId(StarSystemId.fromInt(dest[index]));
            }
        }
    }

    public void showNewspaper() {
        if (!getPaidForNewspaper()) {
            int cost = getDifficulty().castToInt() + 1;

            if (commander.getCash() < cost) {
                GuiFacade.alert(AlertType.ArrivalIFNewspaper, Functions.multiples(cost, Strings.MoneyUnit));
            } else if (getOptions().isNewsAutoPay()
                    || GuiFacade.alert(AlertType.ArrivalBuyNewspaper, Functions.multiples(cost, Strings.MoneyUnit)) == DialogResult.YES) {
                commander.setCash(commander.getCash() - cost);
                setPaidForNewspaper(true);
                getParentWindow().updateAll();
            }
        }
        if (getPaidForNewspaper()) {
            GuiFacade.alert(AlertType.Alert, getNewspaperHead(), getNewspaperText());
        }
    }

    public @Override
    Hashtable serialize() {
        Hashtable hash = super.serialize();

        hash.add("_version", "2.00");
        hash.add("_universe", arrayToArrayList(universe));
        hash.add("commander", commander.serialize());
        hash.add("_wormholes", wormholes);
        hash.add("_mercenaries", arrayToArrayList(mercenaries));
        hash.add("_dragonfly", dragonfly.serialize());
        hash.add("_scarab", scarab.serialize());
        hash.add("_scorpion", scorpion.serialize());
        hash.add("_spaceMonster", spaceMonster.serialize());
        hash.add("_opponent", opponent.serialize());
        hash.add("_chanceOfTradeInOrbit", chanceOfTradeInOrbit);
        hash.add("clicks", clicks);
        hash.add("_raided", raided);
        hash.add("_inspected", inspected);
        hash.add("_tribbleMessage", tribbleMessage);
        hash.add("_arrivedViaWormhole", arrivedViaWormhole);
        hash.add("_paidForNewspaper", paidForNewspaper);
        hash.add("_litterWarning", litterWarning);
        hash.add("_newsEvents", arrayListToIntArray(newsEvents));
        hash.add("difficulty", difficulty.castToInt());
        hash.add("_cheatEnabled", cheats.isCheatMode());
        hash.add("_autoSave", autoSave);
        hash.add("_easyEncounters", easyEncounters);
        hash.add("_endStatus", endStatus.castToInt());
        hash.add("_encounterType", encounterType.castToInt());
        hash.add("_selectedSystemId", selectedSystemId.castToInt());
        hash.add("_warpSystemId", warpSystemId.castToInt());
        hash.add("_trackedSystemId", trackedSystemId.castToInt());
        hash.add("_targetWormhole", targetWormhole);
        hash.add("_priceCargoBuy", priceCargoBuy);
        hash.add("_priceCargoSell", priceCargoSell);
        hash.add("_questStatusArtifact", questStatusArtifact);
        hash.add("_questStatusDragonfly", questStatusDragonfly);
        hash.add("_questStatusExperiment", questStatusExperiment);
        hash.add("_questStatusGemulon", questStatusGemulon);
        hash.add("_questStatusJapori", questStatusJapori);
        hash.add("_questStatusJarek", questStatusJarek);
        hash.add("_questStatusMoon", questStatusMoon);
        hash.add("_questStatusPrincess", questStatusPrincess);
        hash.add("_questStatusReactor", questStatusReactor);
        hash.add("_questStatusScarab", questStatusScarab);
        hash.add("_questStatusSculpture", questStatusSculpture);
        hash.add("_questStatusSpaceMonster", questStatusSpaceMonster);
        hash.add("_questStatusWild", questStatusWild);
        hash.add("_fabricRipProbability", fabricRipProbability);
        hash.add("_justLootedMarie", justLootedMarie);
        hash.add("_canSuperWarp", canSuperWarp);
        hash.add("_chanceOfVeryRareEncounter", chanceOfVeryRareEncounter);
        hash.add("_veryRareEncounters", arrayListToIntArray(veryRareEncounters));
        hash.add("_options", options.serialize());

        return hash;
    }

    // Returns true if an encounter occurred.
    private boolean isTravel() {
        // if timespace is ripped, we may switch the warp system here.
        if (getQuestStatusExperiment() == SpecialEvent.STATUS_EXPERIMENT_PERFORMED
                && getFabricRipProbability() > 0
                && (getFabricRipProbability() == Consts.FabricRipInitialProbability || Functions.getRandom(100) < getFabricRipProbability())) {
            GuiFacade.alert(AlertType.SpecialTimespaceFabricRip);
            setSelectedSystemId(StarSystemId.fromInt(Functions.getRandom(getUniverse().length)));
        }

        boolean uneventful = true;
        setRaided(false);
        setInspected(false);
        setLitterWarning(false);

        setClicks(Consts.StartClicks);
        while (getClicks() > 0) {
            commander.getShip().performRepairs();

            if (isDetermineEncounter()) {
                uneventful = false;

                EncounterResult result = GuiFacade.performEncounter(getParentWindow());
                getParentWindow().updateStatusBar();
                switch (result) {
                    case ARRESTED:
                        setClicks(0);
                        arrested();
                        break;
                    case ESCAPE_POD:
                        setClicks(0);
                        escapeWithPod();
                        break;
                    case KILLED:
                        throw new GameEndException(GameEndType.KILLED);
                }
            }

            setClicks(getClicks() - 1);
        }

        return !uneventful;
    }

    public void setWarp(boolean viaSingularity) {
        if (commander.getDebt() > Consts.DebtTooLarge) {
            GuiFacade.alert(AlertType.DebtTooLargeGrounded);
        } else if (commander.getCash() < getMercenaryCosts()) {
            GuiFacade.alert(AlertType.LeavingIFMercenaries);
        } else if (commander.getCash() < getMercenaryCosts() + getInsuranceCosts()) {
            GuiFacade.alert(AlertType.LeavingIFInsurance);
        } else if (commander.getCash() < getMercenaryCosts() + getInsuranceCosts() + getWormholeCosts()) {
            GuiFacade.alert(AlertType.LeavingIFWormholeTax);
        }else {
            boolean wildOk = true;

            // if Wild is aboard, make sure ship is armed!
            if (commander.getShip().isWildOnBoard() && !commander.getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
                if (GuiFacade.alert(AlertType.WildWontStayAboardLaser, commander.getCurrentSystem().getName()) == DialogResult.CANCEL) {
                    wildOk = false;
                } else {
                    GuiFacade.alert(AlertType.WildLeavesShip, commander.getCurrentSystem().getName());
                    setQuestStatusWild(SpecialEvent.STATUS_WILD_NOT_STARTED);
                }
            }

            if (wildOk) {
                setArrivedViaWormhole(Functions.wormholeExists(commander.getCurrentSystem(), getWarpSystem()));

                if (viaSingularity) {
                    newsAddEvent(NewsEvent.ExperimentArrival);
                } else {
                    normalDeparture((viaSingularity || getArrivedViaWormhole())
                            ? 0 : Functions.distance(commander.getCurrentSystem(), getWarpSystem()));
                }

                commander.getCurrentSystem().setCountDown(getCountDownStart());

                newsResetEvents();

                calculatePrices(getWarpSystem());

                if (isTravel()) {
                    // Clicks will be -1 if we were arrested or used the escape pod.
                    /*
                     * if (Clicks == 0) FormAlert.alert(AlertType.TravelArrival, ParentWindow);
                     */
                } else {
                    GuiFacade.alert(AlertType.TravelUneventfulTrip);
                }

                arrive();
            }
        }
    }

    @CheatCode
    public void warpDirect() {
        warpSystemId = getSelectedSystemId();

        commander.getCurrentSystem().setCountDown(getCountDownStart());
        newsResetEvents();
        calculatePrices(getWarpSystem());
        arrive();
    }

    public Commander getCommander() {
        return commander;
    }

    private int getCountDownStart() {
        return getDifficulty().castToInt() + 3;
    }

    public int getCurrentCosts() {
        return getInsuranceCosts() + getInterestCosts() + getMercenaryCosts() + getWormholeCosts();
    }

    private int[] Destinations() {
        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < getUniverse().length; i++)
            if (getUniverse()[i].destIsOk()) {
                list.add(i);
            }

        int[] ids = new int[list.size()];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = list.get(i);
        }

        return ids;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Ship getDragonfly() {
        return dragonfly;
    }

    public Ship getScorpion() {
        return scorpion;
    }

    public Ship getSpaceMonster() {
        return spaceMonster;
    }

    public Ship getScarab() {
        return scarab;
    }

    public String getEncounterAction() {
        String action = "";

        if (getOpponentDisabled()) {
            action = Functions.stringVars(Strings.EncounterActionOppDisabled, EncounterShipText());
        } else if (getEncounterOppFleeing()) {
            if (getEncounterType() == EncounterType.PIRATE_SURRENDER
                    || getEncounterType() == EncounterType.TRADER_SURRENDER) {
                action = Functions.stringVars(Strings.EncounterActionOppSurrender, EncounterShipText());
            } else {
                action = Functions.stringVars(Strings.EncounterActionOppFleeing, EncounterShipText());
            }
        } else {
            action = Functions.stringVars(Strings.EncounterActionOppAttacks, EncounterShipText());
        }

        return action;
    }

    public String getEncounterActionInitial() {
        String text = "";

        // Set up the fleeing variable initially.
        setEncounterOppFleeing(false);

        switch (getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
                text = Strings.EncounterTextBottle;
                break;
            case CAPTAIN_AHAB:
            case CAPTAIN_CONRAD:
            case CAPTAIN_HUIE:
                text = Strings.EncounterTextFamousCaptain;
                break;
            case DRAGONFLY_ATTACK:
            case PIRATE_ATTACK:
            case POLICE_ATTACK:
            case SCARAB_ATTACK:
            case SCORPION_ATTACK:
            case SPACE_MONSTER_ATTACK:
                text = Strings.EncounterTextOpponentAttack;
                break;
            case DRAGONFLY_IGNORE:
            case PIRATE_IGNORE:
            case POLICE_IGNORE:
            case SCARAB_IGNORE:
            case SCORPION_IGNORE:
            case SPACE_MONSTER_IGNORE:
            case TRADER_IGNORE:
                text = commander.getShip().isCloaked() ? Strings.EncounterTextOpponentNoNotice
                        : Strings.EncounterTextOpponentIgnore;
                break;
            case MARIE_CELESTE:
                text = Strings.EncounterTextMarieCeleste;
                break;
            case MARIE_CELESTE_POLICE:
                text = Strings.EncounterTextPolicePostMarie;
                break;
            case PIRATE_FLEE:
            case POLICE_FLEE:
            case TRADER_FLEE:
                text = Strings.EncounterTextOpponentFlee;
                setEncounterOppFleeing(true);
                break;
            case POLICE_INSPECT:
                text = Strings.EncounterTextPoliceInspection;
                break;
            case POLICE_SURRENDER:
                text = Strings.EncounterTextPoliceSurrender;
                break;
            case TRADER_BUY:
            case TRADER_SELL:
                text = Strings.EncounterTextTrader;
                break;
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                // These should never be the initial encounter type.
                break;
        }

        return text;
    }

    public int getEncounterImageIndex() {
        int encounterImage = -1;

        switch (getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
            case CAPTAIN_AHAB:
            case CAPTAIN_CONRAD:
            case CAPTAIN_HUIE:
            case MARIE_CELESTE:
                encounterImage = Consts.EncounterImgSpecial;
                break;
            case DRAGONFLY_ATTACK:
            case DRAGONFLY_IGNORE:
            case SCARAB_ATTACK:
            case SCARAB_IGNORE:
            case SCORPION_ATTACK:
            case SCORPION_IGNORE:
                encounterImage = Consts.EncounterImgPirate;
                break;
            case MARIE_CELESTE_POLICE:
            case POLICE_ATTACK:
            case POLICE_FLEE:
            case POLICE_IGNORE:
            case POLICE_INSPECT:
            case POLICE_SURRENDER:
                encounterImage = Consts.EncounterImgPolice;
                break;
            case PIRATE_ATTACK:
            case PIRATE_FLEE:
            case PIRATE_IGNORE:
                if (getOpponent().getType() == ShipType.MANTIS) {
                    encounterImage = Consts.EncounterImgAlien;
                } else {
                    encounterImage = Consts.EncounterImgPirate;
                }
                break;
            case SPACE_MONSTER_ATTACK:
            case SPACE_MONSTER_IGNORE:
                encounterImage = Consts.EncounterImgAlien;
                break;
            case TRADER_BUY:
            case TRADER_FLEE:
            case TRADER_IGNORE:
            case TRADER_SELL:
                encounterImage = Consts.EncounterImgTrader;
                break;
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                // These should never be the initial encounter type.
                break;
        }

        return encounterImage;
    }

    private String EncounterShipText() {
        String shipText = getOpponent().getName();

        switch (getEncounterType()) {
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
                shipText = Strings.EncounterShipCaptain;
                break;
            case PIRATE_ATTACK:
            case PIRATE_DISABLED:
            case PIRATE_FLEE:
            case PIRATE_SURRENDER:
                shipText = (getOpponent().getType() == ShipType.MANTIS)
                        ? Strings.EncounterShipMantis
                        : Strings.EncounterShipPirate;
                break;
            case POLICE_ATTACK:
            case POLICE_DISABLED:
            case POLICE_FLEE:
                shipText = Strings.EncounterShipPolice;
                break;
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_FLEE:
            case TRADER_SURRENDER:
                shipText = Strings.EncounterShipTrader;
                break;
        }

        return shipText;
    }

    public String getEncounterText() {
        String cmdrStatus = "";
        String oppStatus = "";

        if (getEncounterCmdrFleeing()) {
            cmdrStatus = Functions.stringVars(Strings.EncounterActionCmdrChased, EncounterShipText());
        } else if (getEncounterOppHit()) {
            cmdrStatus = Functions.stringVars(Strings.EncounterActionOppHit, EncounterShipText());
        } else {
            cmdrStatus = Functions.stringVars(Strings.EncounterActionOppMissed, EncounterShipText());
        }

        if (getEncounterOppFleeingPrev()) {
            oppStatus = Functions.stringVars(Strings.EncounterActionOppChased, EncounterShipText());
        } else if (getEncounterCmdrHit()) {
            oppStatus = Functions.stringVars(Strings.EncounterActionCmdrHit, EncounterShipText());
        } else {
            oppStatus = Functions.stringVars(Strings.EncounterActionCmdrMissed, EncounterShipText());
        }

        return cmdrStatus + Strings.newline + oppStatus;
    }

    public String getEncounterTextInitial() {
        String encounterPretext = "";

        switch (getEncounterType()) {
            case BOTTLE_GOOD:
            case BOTTLE_OLD:
                encounterPretext = Strings.EncounterPretextBottle;
                break;
            case DRAGONFLY_ATTACK:
            case DRAGONFLY_IGNORE:
            case SCARAB_ATTACK:
            case SCARAB_IGNORE:
                encounterPretext = Strings.EncounterPretextStolen;
                break;
            case CAPTAIN_AHAB:
                encounterPretext = Strings.EncounterPretextCaptainAhab;
                break;
            case CAPTAIN_CONRAD:
                encounterPretext = Strings.EncounterPretextCaptainConrad;
                break;
            case CAPTAIN_HUIE:
                encounterPretext = Strings.EncounterPretextCaptainHuie;
                break;
            case MARIE_CELESTE:
                encounterPretext = Strings.EncounterPretextMarie;
                break;
            case MARIE_CELESTE_POLICE:
            case POLICE_ATTACK:
            case POLICE_FLEE:
            case POLICE_IGNORE:
            case POLICE_INSPECT:
            case POLICE_SURRENDER:
                encounterPretext = Strings.EncounterPretextPolice;
                break;
            case PIRATE_ATTACK:
            case PIRATE_FLEE:
            case PIRATE_IGNORE:
                if (getOpponent().getType() == ShipType.MANTIS) {
                    encounterPretext = Strings.EncounterPretextAlien;
                } else {
                    encounterPretext = Strings.EncounterPretextPirate;
                }
                break;
            case SCORPION_ATTACK:
            case SCORPION_IGNORE:
                encounterPretext = Strings.EncounterPretextScorpion;
                break;
            case SPACE_MONSTER_ATTACK:
            case SPACE_MONSTER_IGNORE:
                encounterPretext = Strings.EncounterPretextSpaceMonster;
                break;
            case TRADER_BUY:
            case TRADER_FLEE:
            case TRADER_IGNORE:
            case TRADER_SELL:
                encounterPretext = Strings.EncounterPretextTrader;
                break;
            case FAMOUS_CAPTAIN_ATTACK:
            case FAMOUS_CAPT_DISABLED:
            case POLICE_DISABLED:
            case PIRATE_DISABLED:
            case PIRATE_SURRENDER:
            case TRADER_ATTACK:
            case TRADER_DISABLED:
            case TRADER_SURRENDER:
                // These should never be the initial encounter type.
                break;
        }

        return Functions.stringVars(Strings.EncounterText, new String[]{
                Functions.multiples(getClicks(), Strings.DistanceSubunit), getWarpSystem().getName(), encounterPretext,
                getOpponent().getName().toLowerCase()});
    }

    public int getInsuranceCosts() {
        return commander.getInsurance() ? (int) Math.max(1, commander.getShip().getBaseWorth(true) * Consts.InsRate
                * (100 - commander.getNoClaim()) / 100) : 0;
    }

    public int getInterestCosts() {
        return commander.getDebt() > 0 ? (int) Math.max(1, commander.getDebt() * Consts.IntRate) : 0;
    }

    public int getMercenaryCosts() {
        int total = 0;

        for (int i = 1; i < commander.getShip().getCrew().length && commander.getShip().getCrew()[i] != null; i++) {
            total += commander.getShip().getCrew()[i].getRate();
        }

        return total;
    }

    public CrewMember[] getMercenaries() {
        return mercenaries;
    }

    private ArrayList<NewsEvent> getNewsEvents() {
        return newsEvents;
    }

    private String getNewspaperHead() {
        String[] heads = Strings.NewsMastheads[commander.getCurrentSystem().getPoliticalSystemType().castToInt()];
        String head = heads[commander.getCurrentSystem().getId().castToInt() % heads.length];

        return Functions.stringVars(head, commander.getCurrentSystem().getName());
    }

    private String getNewspaperText() {
        StarSystem curSys = commander.getCurrentSystem();
        List<String> items = new ArrayList<>();

        // We're using the getRandom2 function so that the same number is
        // generated each time for the same "version" of the newspaper. -JAF
        Functions.randSeed(curSys.getId().castToInt(), commander.getDays());

        for (NewsEvent newsEvent : getNewsEvents()) {
            items.add(Functions.stringVars(Strings.NewsEvent[(newsEvent).castToInt()],
                    new String[]{commander.getName(), commander.getCurrentSystem().getName(),
                            commander.getShip().getName()}));
        }

        if (curSys.getSystemPressure() != SystemPressure.NONE) {
            items.add(Strings.NewsPressureInternal[curSys.getSystemPressure().castToInt()]);
        }

        if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreVillain) {
            String baseStr = Strings.NewsPoliceRecordPsychopath[Functions
                    .getRandom2(Strings.NewsPoliceRecordPsychopath.length)];
            items.add(Functions.stringVars(baseStr, commander.getName(), curSys.getName()));
        } else if (commander.getPoliceRecordScore() >= Consts.PoliceRecordScoreHero) {
            String baseStr = Strings.NewsPoliceRecordHero[Functions.getRandom2(Strings.NewsPoliceRecordHero.length)];
            items.add(Functions.stringVars(baseStr, commander.getName(), curSys.getName()));
        }

        // and now, finally, useful news (if any)
        // base probability of a story showing up is (50 / MAXTECHLEVEL) * Current Tech Level
        // This is then modified by adding 10% for every level of play less than impossible
        boolean realNews = false;
        //TODO ???
        int minProbability = Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10 * (5 - getDifficulty().castToInt());
        for (int i = 0; i < getUniverse().length; i++) {
            if (getUniverse()[i].destIsOk() && getUniverse()[i] != curSys) {
                // Special stories that always get shown: moon, millionaire, shipyard
                if (getUniverse()[i].getSpecialEventType() != SpecialEventType.NA) {
                    if (getUniverse()[i].getSpecialEventType() == SpecialEventType.Moon) {
                        items.add(Functions.stringVars(Strings.NewsMoonForSale, getUniverse()[i].getName()));
                    } else if (getUniverse()[i].getSpecialEventType() == SpecialEventType.TribbleBuyer) {
                        items.add(Functions.stringVars(Strings.NewsTribbleBuyer, getUniverse()[i].getName()));
                    }
                }
                if (getUniverse()[i].getShipyardId() != ShipyardId.NA) {
                    items.add(Functions.stringVars(Strings.NewsShipyard, getUniverse()[i].getName()));
                }

                // And not-always-shown stories
                if (getUniverse()[i].getSystemPressure() != SystemPressure.NONE
                        && Functions.getRandom2(100) <= Consts.StoryProbability * curSys.getTechLevel().castToInt() + 10
                        * (5 - getDifficulty().castToInt())) {
                    int index = Functions.getRandom2(Strings.NewsPressureExternal.length);
                    String baseStr = Strings.NewsPressureExternal[index];
                    String pressure = Strings.NewsPressureExternalPressures[getUniverse()[i].getSystemPressure().castToInt()];
                    items.add(Functions.stringVars(baseStr, pressure, getUniverse()[i].getName()));
                    realNews = true;
                }
            }
        }

        // if there's no useful news, we throw up at least one headline from our canned news list.
        if (!realNews) {
            String[] headlines = Strings.NewsHeadlines[curSys.getPoliticalSystemType().castToInt()];
            boolean[] shown = new boolean[headlines.length];

            int toShow = Functions.getRandom2(headlines.length);
            for (int i = 0; i <= toShow; i++) {
                int index = Functions.getRandom2(headlines.length);
                if (!shown[index]) {
                    items.add(headlines[index]);
                    shown[index] = true;
                }
            }
        }

        return String.join(Strings.newline + Strings.newline, items);
    }

    public GameOptions getOptions() {
        return options;
    }

    public int[] getPriceCargoBuy() {
        return priceCargoBuy;
    }

    public int[] getPriceCargoSell() {
        return priceCargoSell;
    }

    int getScore() {
        int worth = (commander.getWorth() < 1000000)
                ? commander.getWorth() : 1000000 + ((commander.getWorth() - 1000000) / 10);
        int daysMoon = 0;
        int modifier = 0;

        switch (getEndStatus()) {
            case KILLED:
                modifier = 90;
                break;
            case RETIRED:
                modifier = 95;
                break;
            case BOUGHT_MOON:
                daysMoon = Math.max(0, (getDifficulty().castToInt() + 1) * 100 - commander.getDays());
                modifier = 100;
                break;
        }

        return (getDifficulty().castToInt() + 1) * modifier * (daysMoon * 1000 + worth) / 250000;
    }

    public StarSystem getSelectedSystem() {
        return (selectedSystemId == StarSystemId.NA ? null : getUniverse()[selectedSystemId.castToInt()]);
    }

    public StarSystemId getSelectedSystemId() {
        return selectedSystemId;
    }

    public void setSelectedSystemId(StarSystemId value) {
        selectedSystemId = value;
        warpSystemId = value;
        targetWormhole = false;
    }

    public void setSelectedSystemByName(String value) {
        boolean found = false;
        for (int i = 0; i < getUniverse().length && !found; i++) {
            String name = getUniverse()[i].getName();
            if (name.toLowerCase().contains(value.toLowerCase())) {
                setSelectedSystemId(StarSystemId.fromInt(i));
                found = true;
            }
        }
    }

    public boolean isTargetWormhole() {
        return targetWormhole;
    }

    public void setTargetWormhole(boolean targetWormhole) {
        this.targetWormhole = targetWormhole;

        if (targetWormhole) {
            int wormIndex = Util.bruteSeek(getWormholes(), getSelectedSystemId().castToInt());
            warpSystemId = StarSystemId.fromInt(getWormholes()[(wormIndex + 1) % getWormholes().length]);
        }
    }

    public StarSystem getTrackedSystem() {
        return (trackedSystemId == StarSystemId.NA ? null : getUniverse()[trackedSystemId.castToInt()]);
    }

    public StarSystem[] getUniverse() {
        return universe;
    }

    public ArrayList<VeryRareEncounter> getVeryRareEncounters() {
        return veryRareEncounters;
    }

    public StarSystem getWarpSystem() {
        return (warpSystemId == StarSystemId.NA) ? null : getUniverse()[warpSystemId.castToInt()];

    }

    private StarSystemId getWarpSystemId() {
        return warpSystemId;
    }

    public int getWormholeCosts() {
        return Functions.wormholeExists(commander.getCurrentSystem(), getWarpSystem()) ? Consts.WormDist
                * commander.getShip().getFuelCost() : 0;
    }

    public int[] getWormholes() {
        return wormholes;
    }

    public boolean isShowTrackedRange() {
        return getOptions().isShowTrackedRange();
    }
}
