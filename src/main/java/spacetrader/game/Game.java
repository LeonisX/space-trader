package spacetrader.game;

import spacetrader.Encounter;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.cheat.CheatCode;
import spacetrader.game.cheat.GameCheats;
import spacetrader.game.enums.*;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.game.quest.QuestSystem;
import spacetrader.game.quest.containers.ScoreContainer;
import spacetrader.game.quest.enums.EventName;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;
import spacetrader.util.Util;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.enums.GameEndType.BOUGHT_MOON;
import static spacetrader.game.enums.GameEndType.KILLED;
import static spacetrader.game.quest.enums.EventName.*;

public class Game implements Serializable {

    static final long serialVersionUID = 110L;

    private static volatile Game game;

    private QuestSystem questSystem;

    private Commander commander;
    @CheatCode
    private GameCheats cheats;

    // Game Data
    private StarSystem[] universe;
    private int[] wormholes = new int[6];
    private Map<Integer, CrewMember> mercenaries = new HashMap<>();
    private Ship dragonfly = new Ship(ShipType.DRAGONFLY);
    private Ship scarab = new Ship(ShipType.SCARAB);
    //private Ship scorpion = new Ship(ShipType.SCORPION);
    public Ship spaceMonster = new Ship(ShipType.SPACE_MONSTER);
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
    
    private News news;
    
    private Difficulty difficulty; // Difficulty.NORMAL
    private boolean autoSave = false;
    private int endStatus = GameEndType.NA.castToInt();

    private Encounter encounter;

    private Map<Integer, ShipSpec> shipSpecs; // ShipSpecs by ID

    private StarSystemId selectedSystemId = StarSystemId.NA; // Current system on chart
    private StarSystemId warpSystemId = StarSystemId.NA; // Target system for warp
    private StarSystemId trackedSystemId = StarSystemId.NA; // The short-range chart will display an arrow towards
    // this system if the value is not null
    private boolean targetWormhole = false; // Wormhole selected?
    private int[] priceCargoBuy = new int[10];
    private int[] priceCargoSell = new int[10]; // Status of Quests
    private int questStatusArtifact = 0; // 0 = not given yet, 1 = Artifact on board, 2 = Artifact no longer on board (either delivered or lost)
    private int questStatusDragonfly = 0; // 0 = not available, 1 = Go to Baratas, 2 = Go to Melina, 3 = Go to Regulas, 4 = Go to Zalkon, 5 = Dragonfly destroyed, 6 = Got Shield
    private int questStatusExperiment = 0; // 0 = not given yet, 1-11 = days from start; 12 = performed, 13 = cancelled
    private int questStatusGemulon = 0; // 0 = not given yet, 1-7 = days from start, 8 = too late, 9 = in time, 10 = done
    private int questStatusJapori = 0; // 0 = no disease, 1 = Go to Japori (always at least 10 medicine canisters), 2 = Assignment finished or canceled
    //private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered
    private int questStatusMoon = 0; // 0 = not bought, 1 = bought, 2 = claimed
    //private int questStatusPrincess = 0; // 0 = not available, 1 = Go to Centauri, 2 = Go to Inthara, 3 = Go to Qonos, 4 = Princess Rescued, 5-14 = On Board, 15 = Princess Returned, 16 = Got Quantum Disruptor
    private int questStatusReactor = 0; // 0 = not encountered, 1-20 = days of mission (bays of fuel left = 10 - (ReactorStatus / 2), 21 = delivered, 22 = Done
    private int questStatusScarab = 0; // 0 = not given yet, 1 = not destroyed, 2 = destroyed - upgrade not performed, 3 = destroyed - hull upgrade performed
    private int questStatusSculpture = 0; // 0 = not given yet, 1 = on board, 2 = delivered, 3 = done
    private int questStatusSpaceMonster = 0; // 0 = not available, 1 = Space monster is in Acamar system, 2 = Space monster is destroyed, 3 = Claimed reward
    private int questStatusWild = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered
    private int fabricRipProbability = 0; // if Experiment = 12, this is the probability of being warped to a random planet.
    private boolean justLootedMarie = false; // flag to indicate whether player looted Marie Celeste
    private boolean canSuperWarp = false; // Do you have the Portable Singularity on board?

    private GameOptions options = new GameOptions(true);

    // The rest of the member variables are not saved between games.
    private transient MainWindow parentWin;

    private GameController gameController;

    private Game() {
        // need for tests
    }

    public Game(String name, Difficulty difficulty, int pilot, int fighter, int trader, int engineer,
                MainWindow parentWin) {
        game = this;
        this.parentWin = parentWin;
        this.difficulty = difficulty;

        questSystem = new QuestSystem();
        questSystem.initializeQuestsHolder();

        // Keep Generating a new universe until isSpecialEventsInPlace and isShipyardsInPlace return true,
        // indicating all special events and shipyards were placed.
        do {
            questSystem.rollbackTransaction();
            questSystem.startTransaction();
            generateUniverse();
        } while (!(isSpecialEventsInPlace() && isShipyardsInPlace()));

        initializeCommander(name, new CrewMember(CrewMemberId.COMMANDER, pilot, fighter, trader, engineer, StarSystemId.NA));

        generateCrewMemberList();

        shipSpecs = Arrays.stream(Consts.ShipSpecs).map(e -> e.withId(e.getType().castToInt())).collect(Collectors.toMap(ShipSpec::getId, e -> e));
        shipSpecs.remove(ShipType.QUEST.castToInt());
        questSystem.fireEvent(AFTER_SHIP_SPECS_INITIALIZED);

        createShips();

        calculatePrices(commander.getCurrentSystem());

        encounter = new Encounter(this);

        cheats = new GameCheats();
        if (name.length() == 0) {
            // TODO: JAF - DEBUG
            commander.setCash(1000000);
            cheats.setCheatMode(true);
            encounter.setEasyEncounters(true);
            setCanSuperWarp(true);
        }

        news = new News();

        questSystem.fireEvent(EventName.AFTER_GAME_INITIALIZE);
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

        GuiFacade.alert(AlertType.JailConvicted, Functions.plural(term, Strings.TimeUnit), Functions.plural(fine,
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
            news.addEvent(NewsEvent.WildArrested);
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

        questSystem.fireEvent(EventName.ON_ARRESTED);

        /*if (commander.getShip().isJarekOnBoard()) {
            GuiFacade.alert(AlertType.JarekTakenHome);
            setQuestStatusJarek(SpecialEvent.STATUS_JAREK_NOT_STARTED);
        }*/

        /*if (commander.getShip().isPrincessOnBoard()) {
            GuiFacade.alert(AlertType.PrincessTakenHome);
            setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_NOT_STARTED);
        }*/

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

        news.addEventsOnArrival();

        if (getOptions().isNewsAutoShow()) {
            showNewspaper();
        }
    }

    private void checkDebtOnArrival() {
        // Check for Large Debt - 06/30/01 SRA
        if (commander.getDebt() >= Consts.DebtWarning) {
            GuiFacade.alert(AlertType.DebtWarning);
        } else if (commander.getDebt() > 0 && getOptions().isRemindLoans() && commander.getDays() % 5 == 0) {
            GuiFacade.alert(AlertType.DebtReminder, Functions.plural(commander.getDebt(), Strings.MoneyUnit));
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
                throw new GameEndException(KILLED.castToInt());
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

    public boolean getRaided() {
        return raided;
    }

    public void setRaided(boolean raided) {
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

    /*public int getQuestStatusPrincess() {
        return questStatusPrincess;
    }*/

    /*public void setQuestStatusPrincess(int questStatusPrincess) {
        this.questStatusPrincess = questStatusPrincess;
    }*/

    public int getQuestStatusMoon() {
        return questStatusMoon;
    }

    public void setQuestStatusMoon(int questStatusMoon) {
        this.questStatusMoon = questStatusMoon;
    }

    /*public int getQuestStatusJarek() {
        return questStatusJarek;
    }

    public void setQuestStatusJarek(int questStatusJarek) {
        this.questStatusJarek = questStatusJarek;
    }*/

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

    public boolean setOpponentDisabled(boolean opponentDisabled) {
        this.opponentDisabled = opponentDisabled;
        return opponentDisabled;
    }

    public boolean getOpponentDisabled() {
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

    public boolean getJustLootedMarie() {
        return justLootedMarie;
    }

    public void setJustLootedMarie(boolean justLootedMarie) {
        this.justLootedMarie = justLootedMarie;
    }

    public boolean getInspected() {
        return inspected;
    }

    public void setInspected(boolean inspected) {
        this.inspected = inspected;
    }

    private int getFabricRipProbability() {
        return fabricRipProbability;
    }

    public void setFabricRipProbability(int fabricRipProbability) {
        this.fabricRipProbability = fabricRipProbability;
    }

    public int getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(int endStatus) {
        this.endStatus = endStatus;
    }

    public int getClicks() {
        return clicks;
    }

    private void setClicks(int clicks) {
        this.clicks = clicks;
    }

    public GameCheats getCheats() {
        return cheats;
    }

    public int getChanceOfTradeInOrbit() {
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

    public boolean getArrivedViaWormhole() {
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

    public void cargoBuyTrader(int tradeItem) {
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
                    unitCost = 5 * (getDifficultyId() + 1);
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
                        if (Functions.getRandom(10) < getDifficultyId() + 1) {
                            if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreDubious) {
                                commander.setPoliceRecordScore(Consts.PoliceRecordScoreDubious);
                            } else {
                                commander.setPoliceRecordScore(commander.getPoliceRecordScore() - 1);
                            }

                            news.addEvent(NewsEvent.CaughtLittering);
                        }
                    }
                }
            }
        }
    }

    public void cargoSellTrader(int tradeItem) {
        cargoSell(tradeItem, false, CargoSellOp.SELL_TRADER);
    }

    private void createFlea() {
        commander.setShip(new Ship(ShipType.FLEA));
        commander.getShip().getCrew()[0] = commander;
        commander.setInsurance(false);
        commander.setNoClaim(0);
    }

    private void createShips() {
        getDragonfly().getCrew()[0] = mercenaries.get(CrewMemberId.DRAGONFLY.castToInt());
        getDragonfly().addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        getDragonfly().addEquipment(Consts.Weapons[WeaponType.PULSE_LASER.castToInt()]);
        getDragonfly().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        getDragonfly().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        getDragonfly().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        getDragonfly().addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        getDragonfly().addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

        getScarab().getCrew()[0] = mercenaries.get(CrewMemberId.SCARAB.castToInt());
        getScarab().addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        getScarab().addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);

        /*scorpion.getCrew()[0] = mercenaries.get(CrewMemberId.SCORPION.castToInt());
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);*/

        spaceMonster.getCrew()[0] = mercenaries.get(CrewMemberId.SPACE_MONSTER.castToInt());
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);

        questSystem.fireEvent(ON_CREATE_SHIP);

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

        questSystem.fireEvent(EventName.ON_ESCAPE_WITH_POD);

        /*if (commander.getShip().isJarekOnBoard()) {
            GuiFacade.alert(AlertType.JarekTakenHome);
            setQuestStatusJarek(SpecialEvent.STATUS_JAREK_NOT_STARTED);
        }*/

        /*if (commander.getShip().isPrincessOnBoard()) {
            GuiFacade.alert(AlertType.PrincessTakenHome);
            setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_NOT_STARTED);
        }*/

        if (commander.getShip().isWildOnBoard()) {
            GuiFacade.alert(AlertType.WildArrested);
            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreCaughtWithWild);
            news.addEvent(NewsEvent.WildArrested);
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
        int d = getDifficultyId();

        // Zeethibal may be on Kravat
        used[StarSystemId.Kravat.castToInt()] = 1;

        // special individuals:
        // Zeethibal, Jonathan Wild's Nephew - skills will be set later.
        // Wild, Jonathan Wild earns his keep now - JAF.
        // Jarek, Ambassador Jarek earns his keep now - JAF.
        // Dummy pilots for opponents.
        mercenaries.put(CrewMemberId.ZEETHIBAL.castToInt(), new CrewMember(CrewMemberId.ZEETHIBAL, 5, 5, 5, 5, StarSystemId.NA));
        mercenaries.put(CrewMemberId.OPPONENT.castToInt(), new CrewMember(CrewMemberId.OPPONENT, 5, 5, 5, 5, StarSystemId.NA));
        mercenaries.put(CrewMemberId.WILD.castToInt(), new CrewMember(CrewMemberId.WILD, 7, 10, 2, 5, StarSystemId.NA));

        questSystem.fireEvent(EventName.ON_GENERATE_CREW_MEMBER_LIST);

        // Jarek TODO remove after finish this quest

        // getMercenaries()[CrewMemberId.JAREK.castToInt()] = new CrewMember(CrewMemberId.JAREK, 3, 2, 10, 4, StarSystemId.NA);
        /*mercenaries.put(CrewMemberId.PRINCESS.castToInt(), new CrewMember(CrewMemberId.PRINCESS, 4, 3, 8, 9, StarSystemId.NA));*/
        mercenaries.put(CrewMemberId.FAMOUS_CAPTAIN.castToInt(), new CrewMember(CrewMemberId.FAMOUS_CAPTAIN, 10, 10, 10, 10, StarSystemId.NA));
        mercenaries.put(CrewMemberId.DRAGONFLY.castToInt(), new CrewMember(CrewMemberId.DRAGONFLY, 4 + d, 6 + d, 1, 6 + d, StarSystemId.NA));
        mercenaries.put(CrewMemberId.SCARAB.castToInt(), new CrewMember(CrewMemberId.SCARAB, 5 + d, 6 + d, 1, 6 + d, StarSystemId.NA));
        /*mercenaries.put(CrewMemberId.SCORPION.castToInt(), new CrewMember(CrewMemberId.SCORPION, 8 + d, 8 + d, 1, 6 + d, StarSystemId.NA));*/
        mercenaries.put(CrewMemberId.SPACE_MONSTER.castToInt(), new CrewMember(CrewMemberId.SPACE_MONSTER, 8 + d, 8 + d, 1, 1 + d, StarSystemId.NA));

        // JAF - Changing this to allow multiple mercenaries in each system, but no more than three.
        for (int i = 1; i < CrewMemberId.values().length - 2; i++) { // minus NA, QUEST
            if (!mercenaries.containsKey(i)) { // Create CrewMember if it doesn't exist.
                StarSystemId id;
                boolean ok = false;

                do {
                    id = StarSystemId.fromInt(Functions.getRandom(getUniverse().length));
                    if (used[id.castToInt()] < 3) {
                        used[id.castToInt()]++;
                        ok = true;
                    }
                } while (!ok);

                mercenaries.put(i, new CrewMember(CrewMemberId.fromInt(i), Functions.randomSkill(),
                        Functions.randomSkill(), Functions.randomSkill(), Functions.randomSkill(), id)
                );
            }
        }
    }

    public void generateOpponent(OpponentType oppType) {
        setOpponent(new Ship(oppType));
    }

    private void generateUniverse() {
        universe = new StarSystem[Strings.SystemNames.length];

        for (int i = 0; i < getUniverse().length; i++) {
            StarSystemId id = (StarSystemId.fromInt(i));
            SystemPressure pressure = SystemPressure.NONE;
            SpecialResource specRes = SpecialResource.NOTHING;
            Size size = Size.fromInt(Functions.getRandom(Size.HUGE.castToInt() + 1));
            PoliticalSystem polSys = Consts.PoliticalSystems[Functions.getRandom(Consts.PoliticalSystems.length)];
            TechLevel tech = TechLevel.fromInt(Functions.getRandom(polSys.getMinimumTechLevel().castToInt(), polSys
                    .getMaximumTechLevel().castToInt() + 1));

            //TODO in quest
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
            /*case Jarek:
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
                break;*/
            /*case Lottery:
                break;*/
            case Moon:
                GuiFacade.alert(AlertType.SpecialMoonBought);
                setQuestStatusMoon(SpecialEvent.STATUS_MOON_BOUGHT);
                break;
            case MoonRetirement:
                setQuestStatusMoon(SpecialEvent.STATUS_MOON_DONE);
                throw new GameEndException(BOUGHT_MOON.castToInt());
            /*case Princess:
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
                    CrewMember princess = mercenaries.get(CrewMemberId.PRINCESS.castToInt());
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
                commander.getShip().fire(CrewMemberId.PRINCESS.castToInt());
                curSys.setSpecialEventType(SpecialEventType.PrincessQuantum);
                setQuestStatusPrincess(SpecialEvent.STATUS_PRINCESS_RETURNED);
                remove = false;
                break;*/
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
                    CrewMember wild = mercenaries.get(CrewMemberId.WILD.castToInt());
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
                CrewMember zeethibal = mercenaries.get(CrewMemberId.ZEETHIBAL.castToInt());
                zeethibal.setCurrentSystem(getUniverse()[StarSystemId.Kravat.castToInt()]);
                int lowest1 = commander.nthLowestSkill(1);
                int lowest2 = commander.nthLowestSkill(2);
                for (int i = 0; i < zeethibal.getSkills().length; i++) {
                    zeethibal.getSkills()[i] = (i == lowest1 ? 10 : (i == lowest2 ? 8 : 5));
                }

                setQuestStatusWild(SpecialEvent.STATUS_WILD_DONE);
                commander.setPoliceRecordScore(Consts.PoliceRecordScoreClean);
                commander.getShip().fire(CrewMemberId.WILD.castToInt());
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

    public void incDays(int num) {
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
                            / (getDifficultyId() <= spacetrader.game.enums.Difficulty.NORMAL.castToInt() ? 1
                            : getDifficultyId())));
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
                news.addEvent(NewsEvent.ExperimentPerformed);
            }
        } else if (getQuestStatusExperiment() == SpecialEvent.STATUS_EXPERIMENT_PERFORMED
                && getFabricRipProbability() > 0) {
            setFabricRipProbability(getFabricRipProbability() - num);
        }

        questSystem.fireEvent(EventName.ON_INCREMENT_DAYS);

        /*if (commander.getShip().isJarekOnBoard()) {
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
        }*/

        /*if (commander.getShip().isPrincessOnBoard()) {
            if (getQuestStatusPrincess() == (SpecialEvent.STATUS_PRINCESS_IMPATIENT + SpecialEvent.STATUS_PRINCESS_RESCUED) / 2) {
                GuiFacade.alert(AlertType.SpecialPassengerConcernedPrincess);
            } else if (getQuestStatusPrincess() == SpecialEvent.STATUS_PRINCESS_IMPATIENT - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientPrincess);
                mercenaries.get(CrewMemberId.PRINCESS.castToInt()).setPilot(0);
                mercenaries.get(CrewMemberId.PRINCESS.castToInt()).setFighter(0);
                mercenaries.get(CrewMemberId.PRINCESS.castToInt()).setTrader(0);
                mercenaries.get(CrewMemberId.PRINCESS.castToInt()).setEngineer(0);
            }

            if (getQuestStatusPrincess() < SpecialEvent.STATUS_PRINCESS_IMPATIENT) {
                setQuestStatusPrincess(getQuestStatusPrincess() + 1);
            }
        }*/

        if (commander.getShip().isWildOnBoard()) {
            if (getQuestStatusWild() == SpecialEvent.STATUS_WILD_IMPATIENT / 2) {
                GuiFacade.alert(AlertType.SpecialPassengerConcernedWild);
            } else if (getQuestStatusWild() == SpecialEvent.STATUS_WILD_IMPATIENT - 1) {
                GuiFacade.alert(AlertType.SpecialPassengerImpatientWild);
                mercenaries.get(CrewMemberId.WILD.castToInt()).setPilot(0);
                mercenaries.get(CrewMemberId.WILD.castToInt()).setFighter(0);
                mercenaries.get(CrewMemberId.WILD.castToInt()).setTrader(0);
                mercenaries.get(CrewMemberId.WILD.castToInt()).setEngineer(0);
            }

            if (getQuestStatusWild() < SpecialEvent.STATUS_WILD_IMPATIENT) {
                setQuestStatusWild(getQuestStatusWild() + 1);
            }
        }
    }

    private void initializeCommander(String name, CrewMember commanderCrewMember) {
        commander = new Commander(commanderCrewMember);
        mercenaries.put(commander.getId(), commander);
        Strings.CrewMemberNames[commander.getId()] = name;

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
    }

    private void normalDeparture(int fuel) {
        commander.setCash(commander.getCash() - (getMercenaryCosts() + getInsuranceCosts() + getWormholeCosts()));
        commander.getShip().setFuel(commander.getShip().getFuel() - fuel);
        commander.payInterest();
        incDays(1);
    }

    private boolean isShipyardsInPlace() {
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

    private boolean isSpecialEventsInPlace() {
        boolean goodUniverse = true;

        getUniverse()[StarSystemId.Baratas.castToInt()].setSpecialEventType(SpecialEventType.DragonflyBaratas);
        getUniverse()[StarSystemId.Melina.castToInt()].setSpecialEventType(SpecialEventType.DragonflyMelina);
        getUniverse()[StarSystemId.Regulas.castToInt()].setSpecialEventType(SpecialEventType.DragonflyRegulas);
        getUniverse()[StarSystemId.Zalkon.castToInt()].setSpecialEventType(SpecialEventType.DragonflyDestroyed);
        getUniverse()[StarSystemId.Daled.castToInt()].setSpecialEventType(SpecialEventType.ExperimentStopped);
        getUniverse()[StarSystemId.Gemulon.castToInt()].setSpecialEventType(SpecialEventType.GemulonRescued);
        getUniverse()[StarSystemId.Japori.castToInt()].setSpecialEventType(SpecialEventType.JaporiDelivery);

        questSystem.fireEvent(EventName.ON_ASSIGN_EVENTS_MANUAL);
        //getUniverse()[StarSystemId.Devidia.castToInt()].setSpecialEventType(SpecialEventType.JarekGetsOut);
        getUniverse()[StarSystemId.Utopia.castToInt()].setSpecialEventType(SpecialEventType.MoonRetirement);
        getUniverse()[StarSystemId.Nix.castToInt()].setSpecialEventType(SpecialEventType.ReactorDelivered);
        getUniverse()[StarSystemId.Acamar.castToInt()].setSpecialEventType(SpecialEventType.SpaceMonsterKilled);
        getUniverse()[StarSystemId.Kravat.castToInt()].setSpecialEventType(SpecialEventType.WildGetsOut);
        getUniverse()[StarSystemId.Endor.castToInt()].setSpecialEventType(SpecialEventType.SculptureDelivered);
        /*getUniverse()[StarSystemId.Galvon.castToInt()].setSpecialEventType(SpecialEventType.Princess);
        getUniverse()[StarSystemId.Centauri.castToInt()].setSpecialEventType(SpecialEventType.PrincessCentauri);
        getUniverse()[StarSystemId.Inthara.castToInt()].setSpecialEventType(SpecialEventType.PrincessInthara);
        getUniverse()[StarSystemId.Qonos.castToInt()].setSpecialEventType(SpecialEventType.PrincessQonos);*/

        // Assign a wormhole location endpoint for the Scarab.
        OptionalInt freeWormhole = Arrays.stream(getWormholes())
                .filter(wormhole -> getUniverse()[wormhole].getSpecialEventType() == SpecialEventType.NA).findAny();
        if (freeWormhole.isPresent()) {
            getUniverse()[freeWormhole.getAsInt()].setSpecialEventType(SpecialEventType.ScarabDestroyed);
        } else {
            goodUniverse = false;
        }

        // Find a Hi-Tech system without a special event for ArtifactDelivery.
        if (goodUniverse) {
            Optional<StarSystem> freeHiTechSystem = Arrays.stream(getUniverse())
                    .filter(universe -> universe.getSpecialEventType() == SpecialEventType.NA
                            && universe.getTechLevel() == TechLevel.HI_TECH).findAny();
            if (freeHiTechSystem.isPresent()) {
                freeHiTechSystem.get().setSpecialEventType(SpecialEventType.ArtifactDelivery);
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
        //TODO remove after all quests
        int system;
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

        if (goodUniverse) {
            questSystem.fireEvent(EventName.ON_ASSIGN_EVENTS_RANDOMLY);
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

    public void selectNextSystemWithinRange(boolean forward) {
        int[] dest = getDestinations();

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
            int cost = getDifficultyId() + 1;

            if (commander.getCash() < cost) {
                GuiFacade.alert(AlertType.ArrivalIFNewspaper, Functions.plural(cost, Strings.MoneyUnit));
            } else if (getOptions().isNewsAutoPay()
                    || GuiFacade.alert(AlertType.ArrivalBuyNewspaper, Functions.plural(cost, Strings.MoneyUnit)) == DialogResult.YES) {
                commander.setCash(commander.getCash() - cost);
                setPaidForNewspaper(true);
                getParentWindow().updateAll();
            }
        }
        if (getPaidForNewspaper()) {
            GuiFacade.alert(AlertType.Alert, news.getNewspaperHead(), news.getNewspapersText());
        }
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

            if (encounter.isDetermineEncounter()) {
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
                        throw new GameEndException(KILLED.castToInt());
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
        } else {
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
                    news.addEvent(NewsEvent.ExperimentArrival);
                } else {
                    normalDeparture((viaSingularity || getArrivedViaWormhole())
                            ? 0 : Functions.distance(commander.getCurrentSystem(), getWarpSystem()));
                }

                commander.getCurrentSystem().setCountDown(getCountDownStart());

                news.resetEvents();

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
        news.resetEvents();
        calculatePrices(getWarpSystem());
        arrive();
    }

    private int getCountDownStart() {
        return getDifficultyId() + 3;
    }

    public int getCurrentCosts() {
        return getInsuranceCosts() + getInterestCosts() + getMercenaryCosts() + getWormholeCosts();
    }

    private int[] getDestinations() {
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

    public Ship getDragonfly() {
        return dragonfly;
    }

    /*public Ship getScorpion() {
        return scorpion;
    }*/

    public Ship getSpaceMonster() {
        return spaceMonster;
    }

    public Ship getScarab() {
        return scarab;
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

    public Map<Integer, CrewMember> getMercenaries() {
        return mercenaries;
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
        int worth = commander.getWorth();
        ScoreContainer score = new ScoreContainer(
                (worth < 1000000) ? worth : 1000000 + ((worth - 1000000) / 10), 0, 0, getEndStatus());

        if (getEndStatus() <= GameEndType.BOUGHT_MOON.castToInt()) {
            switch (GameEndType.fromInt(getEndStatus())) {
                case KILLED:
                    score.setModifier(90);
                    break;
                case RETIRED:
                    score.setModifier(95);
                    break;
                case BOUGHT_MOON:
                    score.setDaysMoon(Math.max(0, (getDifficultyId() + 1) * 100 - commander.getDays()));
                    score.setModifier(100);
                    break;
            }
        } else {
            questSystem.fireEvent(ON_GET_GAME_SCORE, score);
        }

        return (getDifficultyId() + 1) * score.getModifier() * (score.getDaysMoon() * 1000 + score.getWorth()) / 250000;
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

    //TODO add all quests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return opponentDisabled == game.opponentDisabled &&
                chanceOfTradeInOrbit == game.chanceOfTradeInOrbit &&
                clicks == game.clicks &&
                raided == game.raided &&
                inspected == game.inspected &&
                tribbleMessage == game.tribbleMessage &&
                arrivedViaWormhole == game.arrivedViaWormhole &&
                paidForNewspaper == game.paidForNewspaper &&
                litterWarning == game.litterWarning &&
                autoSave == game.autoSave &&
                //easyEncounters == game.easyEncounters &&
                targetWormhole == game.targetWormhole &&
                questStatusArtifact == game.questStatusArtifact &&
                questStatusDragonfly == game.questStatusDragonfly &&
                questStatusExperiment == game.questStatusExperiment &&
                questStatusGemulon == game.questStatusGemulon &&
                questStatusJapori == game.questStatusJapori &&
                //questStatusJarek == game.questStatusJarek &&
                questStatusMoon == game.questStatusMoon &&
                /*questStatusPrincess == game.questStatusPrincess &&*/
                questStatusReactor == game.questStatusReactor &&
                questStatusScarab == game.questStatusScarab &&
                questStatusSculpture == game.questStatusSculpture &&
                questStatusSpaceMonster == game.questStatusSpaceMonster &&
                questStatusWild == game.questStatusWild &&
                fabricRipProbability == game.fabricRipProbability &&
                justLootedMarie == game.justLootedMarie &&
                canSuperWarp == game.canSuperWarp &&
                /*chanceOfVeryRareEncounter == game.chanceOfVeryRareEncounter &&
                encounterContinueFleeing == game.encounterContinueFleeing &&
                encounterContinueAttacking == game.encounterContinueAttacking &&
                encounterCmdrFleeing == game.encounterCmdrFleeing &&
                encounterCmdrHit == game.encounterCmdrHit &&
                encounterOppFleeingPrev == game.encounterOppFleeingPrev &&
                encounterOppFleeing == game.encounterOppFleeing &&
                encounterOppHit == game.encounterOppHit &&*/
                Objects.equals(commander, game.commander) &&
                Objects.equals(cheats, game.cheats) &&
                Arrays.equals(universe, game.universe) &&
                Arrays.equals(wormholes, game.wormholes) &&
                mercenaries.equals(game.mercenaries) &&
                Objects.equals(dragonfly, game.dragonfly) &&
                Objects.equals(scarab, game.scarab) &&
                //Objects.equals(scorpion, game.scorpion) &&
                Objects.equals(spaceMonster, game.spaceMonster) &&
                Objects.equals(opponent, game.opponent) &&
                Objects.equals(news, game.news) &&
                difficulty == game.difficulty &&
                endStatus == game.endStatus &&
                //encounterType == game.encounterType &&
                selectedSystemId == game.selectedSystemId &&
                warpSystemId == game.warpSystemId &&
                trackedSystemId == game.trackedSystemId &&
                Arrays.equals(priceCargoBuy, game.priceCargoBuy) &&
                Arrays.equals(priceCargoSell, game.priceCargoSell) &&
                //Objects.equals(veryRareEncounters, game.veryRareEncounters) &&
                Objects.equals(options, game.options) &&
                Objects.equals(parentWin, game.parentWin);
    }

    //TODO add all quests
    @Override
    public int hashCode() {
        int result = Objects.hash(commander, cheats, dragonfly, scarab, /*scorpion,*/ spaceMonster, opponent,
                opponentDisabled, chanceOfTradeInOrbit, clicks, raided, inspected, tribbleMessage, arrivedViaWormhole,
                paidForNewspaper, litterWarning, news, difficulty, autoSave, /*easyEncounters,*/ endStatus,
                /*encounterType,*/ selectedSystemId, warpSystemId, trackedSystemId, targetWormhole, questStatusArtifact,
                questStatusDragonfly, questStatusExperiment, questStatusGemulon, questStatusJapori, /*questStatusJarek,*/
                questStatusMoon, /*questStatusPrincess,*/ questStatusReactor, questStatusScarab, questStatusSculpture,
                questStatusSpaceMonster, questStatusWild, fabricRipProbability, justLootedMarie, canSuperWarp,
                /*chanceOfVeryRareEncounter, veryRareEncounters,*/ options, parentWin/*, encounterContinueFleeing,
                encounterContinueAttacking, encounterCmdrFleeing, encounterCmdrHit, encounterOppFleeingPrev,
                encounterOppFleeing, encounterOppHit*/);
        result = 31 * result + Arrays.hashCode(universe);
        result = 31 * result + Arrays.hashCode(wormholes);
        result = 31 * result + mercenaries.hashCode();
        result = 31 * result + Arrays.hashCode(priceCargoBuy);
        result = 31 * result + Arrays.hashCode(priceCargoSell);
        return result;
    }

    public void setController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController getController() {
        return gameController;
    }

    public QuestSystem getQuestSystem() {
        return questSystem;
    }

    public void setQuestSystem(QuestSystem questSystem) {
        this.questSystem = questSystem;
    }


    public static Commander getCommander() {
        return getCurrentGame().commander;
    }

    public static Ship getShip() {
        return getCommander().getShip();
    }

    public static StarSystemId getCurrentSystemId() {
        return getCommander().getCurrentSystemId();
    }

    public static boolean isCurrentSystemIs(StarSystemId starSystemId) {
        return getCurrentSystemId().equals(starSystemId);
    }

    public static StarSystem getStarSystem(StarSystemId starSystemId) {
        return Game.getCurrentGame().getUniverse()[starSystemId.castToInt()];
    }

    public static StarSystem getStarSystem(int starSystemId) {
        return Game.getCurrentGame().getUniverse()[starSystemId];
    }

    public static Difficulty getDifficulty() {
        return getCurrentGame().difficulty;
    }

    public static int getDifficultyId() {
        return getDifficulty().castToInt();
    }

    public static void setDifficulty(Difficulty difficulty) {
        getCurrentGame().difficulty = difficulty;
    }

    public static News getNews() {
        return getCurrentGame().news;
    }

    public static void setNews(News news) {
        getCurrentGame().news = news;
    }

    public Map<Integer, ShipSpec> getShipSpecs() {
        return shipSpecs;
    }

    public void setShipSpecs(Map<Integer, ShipSpec> shipSpecs) {
        this.shipSpecs = shipSpecs;
    }

    public Ship createShipByShipSpecId(int shipSpecId) {
        return new Ship(shipSpecs.get(shipSpecId));
    }

    public Encounter getEncounter() {
        return encounter;
    }

    public void setEncounter(Encounter encounter) {
        this.encounter = encounter;
    }
}
