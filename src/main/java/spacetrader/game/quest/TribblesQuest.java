package spacetrader.game.quest;

import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.enums.QuestName;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.awt.*;
import java.util.*;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

//TODO -reactor
public class TribblesQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511507L;

    // Constants
    private static final int STATUS_REACTOR_NOT_STARTED = 0;
    private static final int STATUS_REACTOR_FUEL_OK = 1;
    private static final int STATUS_REACTOR_DATE = 20;
    private static final int STATUS_REACTOR_DELIVERED = 21;
    private static final int STATUS_REACTOR_DONE = 22;

    public static final int MaxTribbles = 100000;

    private static final Point[] coordinates = {new Point(16, 16), new Point(56, 8), new Point(96, 16),
            new Point(128, 8), new Point(176, 8), new Point(208, 16), new Point(8, 56),
            new Point(32, 80), new Point(88, 56), new Point(128, 40), new Point(192, 72),
            new Point(216, 48), new Point(8, 96), new Point(56, 96), new Point(96, 80),
            new Point(136, 88), new Point(176, 104), new Point(216, 96), new Point(16, 136),
            new Point(56, 128), new Point(96, 120), new Point(128, 128), new Point(168, 144),
            new Point(208, 128), new Point(8, 184), new Point(48, 176), new Point(88, 168),
            new Point(136, 176), new Point(184, 184), new Point(216, 176), new Point(16, 224),
            new Point(64, 216), new Point(96, 224), new Point(144, 216), new Point(176, 224),
            new Point(208, 216)
    };

    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;

    private volatile int questStatus = 0; // 0 = not encountered, 1-20 = days of mission (bays of fuel left = 10 - (ReactorStatus / 2), 21 = delivered, 22 = Done

    private boolean tribbleMessage = false; // Is true if the Ship Yard on the current system informed you about the tribbles

    private int tribbles = 0;

    public TribblesQuest(QuestName id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new ReactorPhase(), new ReactorDeliveredPhase(), new ReactorLaserPhase());
        initializeTransitionMap();

        registerListener();

        localize();

        log.fine("started...");
    }

    public static Point[] getCoordinates() {
        return coordinates;
    }

    private void initializePhases(QuestPhases[] values, Phase... phases) {
        for (int i = 0; i < phases.length; i++) {
            this.phases.put(values[i], phases[i]);
            phases[i].setQuest(this);
            phases[i].setPhaseEnum(values[i]);
        }
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();

        getTransitionMap().put(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_ASSIGN_CLOSEST_EVENTS_RANDOMLY, this::onAssignClosestEventsRandomly);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ON_FORM_SHIP_LIST_SHOW, this::onFormShipListShow);
        getTransitionMap().put(ON_GET_BASE_WORTH, this::onGetBaseWorth);
        getTransitionMap().put(ON_GET_FILLED_CARGO_BAYS, this::onGetFilledCargoBays);

        getTransitionMap().put(IS_TRADE_SHIP, this::isTradeShip);

        getTransitionMap().put(ENCOUNTER_IS_EXECUTE_ATTACK, this::encounterIsExecuteAttack);
        getTransitionMap().put(ENCOUNTER_ON_ROBBERY, this::encounterOnRobbery);
        getTransitionMap().put(ENCOUNTER_ON_TRIBBLE_PICTURE_CLICK, this::encounterOnTribblePictureClick);

        getTransitionMap().put(IS_ILLEGAL_SPECIAL_CARGO, this::onIsIllegalSpecialCargo);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS, this::onGetIllegalSpecialCargoActions);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION, this::onGetIllegalSpecialCargoDescription);
        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ARRESTED_AND_SHIP_SOLD_FOR_DEBT, this::onArrestedAndShipSoldForDebt);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS, this::onNewsAddEventFromNearestSystems);
        getTransitionMap().put(ON_ARRIVAL, this::onArrival);

        getTransitionMap().put(ON_BEFORE_KILLED, this::onBeforeKilled);

        getTransitionMap().put(IS_CONSIDER_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderStatusCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderStatusDefaultCheat);
    }

    private void onGetBaseWorth(Object object) {
        if (tribbles > 0) {
            ((BooleanContainer) object).setValue(true);
        }
    }

    public int getTribbles() {
        return tribbles;
    }

    @Override
    public Collection<Phase> getPhases() {
        return phases.values();
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    public boolean isReactorOnBoard() {
        return questStatus > STATUS_REACTOR_NOT_STARTED && questStatus < STATUS_REACTOR_DELIVERED;
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Nix);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(QuestPhases.ReactorDelivered).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.ReactorLaser).setStarSystemId(starSystem.getId());
    }

    //TODO common method
    // Find the closest system at least 70 parsecs away from Nix that doesn't already have a special event.
    private void onAssignClosestEventsRandomly(Object object) {
        BooleanContainer goodUniverse = (BooleanContainer) object;
        if (!goodUniverse.getValue()) {
            return;
        }
        int systemId = game.isFindDistantSystem(StarSystemId.Nix, SpecialEventType.ASSIGNED);
        if (systemId < 0) {
            goodUniverse.setValue(false);
        } else {
            phases.get(QuestPhases.Reactor).setStarSystemId(Game.getStarSystem(systemId).getId());
        }
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class ReactorPhase extends Phase { //new SpecialEvent(SpecialEventType.Reactor, 0, 0, false),
        @Override
        public boolean canBeExecuted() {
            return questStatus == STATUS_REACTOR_NOT_STARTED
                    && Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                    && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage
                    && !isReactorOnBoard() && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            if (Game.getShip().getFreeCargoBays() < 15) {
                GuiFacade.alert(AlertType.CargoNoEmptyBays);
            } else {
                BooleanContainer isConflict = new BooleanContainer(false);
                game.getQuestSystem().fireEvent(ON_SPECIAL_BUTTON_CLICKED_IS_CONFLICT, isConflict);

                if (!isConflict.getValue()) {
                    showAlert(Alerts.ReactorOnBoard.getValue());
                    questStatus = STATUS_REACTOR_FUEL_OK;
                    game.confirmQuestPhase();
                    setQuestState(QuestState.ACTIVE);
                    //Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
                }
            }
        }

        @Override
        public String toString() {
            return "ReactorPhase{} " + super.toString();
        }
    }

    class ReactorDeliveredPhase extends Phase { //new SpecialEvent(SpecialEventType.ReactorDelivered, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return isReactorOnBoard() && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            questStatus = STATUS_REACTOR_DELIVERED;
        }

        @Override
        public String toString() {
            return "ReactorDeliveredPhase{} " + super.toString();
        }
    }

    class ReactorLaserPhase extends Phase { //new SpecialEvent(SpecialEventType.ReactorLaser, 0, 0, false),
        @Override
        public boolean canBeExecuted() {
            return !isReactorOnBoard() && isDesiredSystem() && isQuestIsActive();
        }

        @Override
        public void successFlow() {
            log.fine("phase #3");
            if (Game.getShip().getFreeWeaponSlots() == 0) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else {
                showAlert(Alerts.EquipmentMorgansLaser.getValue());
                Game.getShip().addEquipment(Consts.Weapons[WeaponType.MORGANS_LASER.castToInt()]);
                questStatus = STATUS_REACTOR_DONE;
                game.confirmQuestPhase();
                setQuestState(QuestState.FINISHED);
                game.getQuestSystem().unSubscribeAll(getQuest());
            }
        }

        @Override
        public String toString() {
            return "ReactorLaserPhase{} " + super.toString();
        }
    }


    //TODO


    //

/*    case Tribble:
    show = true;
                break;


            case TribbleBuyer:
    show = Game.getShip().getTribbles() > 0;
                break;*/



    /*case Tribble:
            GuiFacade.alert(AlertType.TribblesOwn);
                commander.getShip().setTribbles(1);
    confirmQuestPhase();
                break;
            case TribbleBuyer:
            GuiFacade.alert(AlertType.TribblesGone);
                commander.setCash(commander.getCash() + (commander.getShip().getTribbles() / 2));
                commander.getShip().setTribbles(0);
    confirmQuestPhase();
                break;*/


    private void onSpecialButtonClicked(Object object) {
        Optional<QuestPhases> activePhase =
                phases.entrySet().stream().filter(p -> p.getValue().canBeExecuted()).map(Map.Entry::getKey).findFirst();
        if (activePhase.isPresent()) {
            showDialogAndProcessResult(object, activePhase.get().getValue(), () -> phases.get(activePhase.get()).successFlow());
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onDisplaySpecialCargo(Object object) {
        /*if (isReactorOnBoard()) {
            log.fine(SpecialCargo.Reactor.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.Reactor.getValue());
            log.fine(Functions.plural(10 - ((questStatus - 1) / 2), Strings.ShipBay)
                    + " " + SpecialCargo.ReactorBays.getValue());
            ((ArrayList<String>) object).add(Functions.plural(10 - ((questStatus - 1) / 2), Strings.ShipBay)
                    + " " + SpecialCargo.ReactorBays.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.Reactor.getValue());
            log.fine("Don't show " + Functions.plural(10 - ((questStatus - 1) / 2), Strings.ShipBay)
                    + " " + SpecialCargo.ReactorBays.getValue());
        }*/

        if (ship.getTribbles() > 0) {
            if (ship.getTribbles() == Consts.MaxTribbles) {
                specialCargo.add(Strings.SpecialCargoTribblesInfest);
            } else if (ship.getTribbles() == 1) {
                specialCargo.add(Strings.SpecialCargoTribbleCute);
            } else {
                specialCargo.add(Strings.SpecialCargoTribblesCute);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        /*if (isReactorOnBoard()) {
            if (questStatus == STATUS_REACTOR_FUEL_OK) {
                ((ArrayList<String>) object).add(QuestClues.Reactor.getValue());
            } else {
                ((ArrayList<String>) object).add(QuestClues.ReactorFuel.getValue());
            }
        } else if (questStatus == STATUS_REACTOR_DELIVERED) {
            ((ArrayList<String>) object).add(QuestClues.ReactorLaser.getValue());
        } else {
            log.fine("skipped");
        }*/

        if (Game.getShip().getTribbles() > 0) {
            quests.add(Strings.QuestTribbles);
        }
    }

    private void onFormShipListShow(Object object) {
        if (Game.getShip().getTribbles() > 0 && !game.getTribbleMessage()) {
            GuiFacade.alert(AlertType.TribblesTradeIn);
            game.setTribbleMessage(true);
        }
    }

    private void onGetFilledCargoBays(Object object) {
        if (isReactorOnBoard()) {
            IntContainer filled = (IntContainer) object;
            filled.setValue(filled.getValue() + 5 + 10 - (questStatus - 1) / 2);
        }
    }

    private void isTradeShip(Object object) {
        if (isReactorOnBoard()) {
            showAlert(Alerts.ShipBuyReactor.getValue());
            ((BooleanContainer) object).setValue(false);
        }
    }

    // Reactor on board -- damage is boosted!
    private void encounterIsExecuteAttack(Object object) {
        if (isReactorOnBoard()) {
            IntContainer damage = (IntContainer) object;
            damage.setValue(damage.getValue() * (int) (1 + (Game.getDifficultyId() + 1)
                    * (Game.getDifficultyId() < Difficulty.NORMAL.castToInt() ? 0.25 : 0.33)));
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterOnRobbery(Object object) {
        // pirates puzzled by reactor
        if (isReactorOnBoard()) {
            showAlert(Alerts.EncounterPiratesExamineReactor.getValue());
        }
    }

    private void encounterOnTribblePictureClick(Object object) {
        GuiFacade.alert(AlertType.TribblesSqueek);
    }

    private void onIsIllegalSpecialCargo(Object object) {
        if (isReactorOnBoard()) {
            ((BooleanContainer) object).setValue(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoActions(Object object) {
        if (isReactorOnBoard()) {
            ((ArrayList<String>) object).add(Encounters.PoliceSurrenderReactor.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoDescription(Object object) {
        if (isReactorOnBoard()) {
            ((ArrayList<String>) object).add(Encounters.PoliceSubmitReactor.getValue());
        }
    }

    // TODO far perspective - if < normal - give second try
    private void onArrested(Object object) {
        if (isReactorOnBoard()) {
            log.fine("Arrested + Reactor");
            showAlert(Alerts.ReactorConfiscated.getValue());
            failQuest();
        } else {
            log.fine("Arrested w/o Reactor");
        }
    }

    private void onArrestedAndShipSoldForDebt(Object object) {
        if (commander.getShip().getTribbles() > 0) {
            GuiFacade.alert(AlertType.TribblesRemoved);
        }
    }

    // TODO far perspective - if < normal - give second try
    private void onEscapeWithPod(Object object) {
        /*if (isReactorOnBoard()) {
            log.fine("Escaped + Reactor");
            showAlert(Alerts.ReactorDestroyed.getValue());
            failQuest();
        } else {
            log.fine("Escaped w/o Reactor");
        }*/

        if (commander.getShip().getTribbles() > 0) {
            GuiFacade.alert(AlertType.TribblesKilled);
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_REACTOR_NOT_STARTED;
        setQuestState(QuestState.FAILED);
    }

    private void onIncrementDays(Object object) {
        if (isReactorOnBoard()) {
            questStatus = (Math.min(questStatus + ((IntContainer) object).getValue(), STATUS_REACTOR_DATE));
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (jarekOnBoard && Game.isCurrentSystemIs(StarSystemId.Devidia)) {
            log.fine("" + getNewsIds().get(0));
            Game.getNews().addEvent(getNewsIds().get(0));
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventFromNearestSystems(Object object) {
        if (starSystem.getSpecialEventType() == SpecialEventType.TribbleBuyer) {
            news.add(Functions.stringVars(Strings.NewsTribbleBuyer, starSystem.getName()));
        }
    }

    // checkTribblesOnArrival
    private void onArrival(Object object) {
        Ship ship = commander.getShip();

        if (ship.getTribbles() > 0) {
            int previousTribbles = ship.getTribbles();
            int narc = TradeItemType.NARCOTICS.castToInt();
            int food = TradeItemType.FOOD.castToInt();

            if (((ReactorQuest) questSystem.getQuest(QuestName.Reactor)).isReactorOnBoard()) {
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


    private void onBeforeKilled(Object object) {
        showAlert(Alerts.ReactorDestroyed.getValue());
    }

    private void onIsConsiderCheat(Object object) {
        /*CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Reactor.name())) {
            questStatus = Math.max(0, cheatWords.getNum2());
            cheatWords.setCheat(true);
            log.fine("consider status cheat");
        } else {
            log.fine("not consider status cheat");
        }*/
        /*case Varmints:
        ship.setTribbles(Math.max(0, words.getNum1()));
        break;*/
        //TODO receive CheatWords, set cheat to true
    }

    private void onIsConsiderStatusCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Reactor.name())) {
            questStatus = Math.max(0, cheatWords.getNum2());
            cheatWords.setCheat(true);
            log.fine("consider status cheat");
        } else {
            log.fine("not consider status cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderStatusDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CheatTitles.Reactor.getValue(), questStatus);
    }


    //"A merchant prince offers you a very special and wondrous item for the sum of 1000 credits. Do you accept?",
    //"Merchant Prince",

    //"An eccentric alien billionaire wants to buy your collection of tribbles and offers half a credit for each of them. Do you accept his offer?",
    //                 "Tribble Buyer",

    //new SpecialEvent(SpecialEventType.Tribble, 1000, 1, false),
    //            new SpecialEvent(SpecialEventType.TribbleBuyer, 0, 3, false),


    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Reactor(new QuestDialog(DIALOG, "Morgan's Reactor", "Galactic criminal Henry Morgan wants this illegal ion reactor delivered to Nix. It's a very dangerous mission! The reactor and its fuel are bulky, taking up 15 bays. Worse, it's not stable -- its resonant energy will weaken your shields and hull strength while it's aboard your ship. Are you willing to deliver it?")),
        ReactorDelivered(new QuestDialog(ALERT, "Reactor Delivered", "Henry Morgan takes delivery of the reactor with great glee. His men immediately set about stabilizing the fuel system. As a reward, Morgan offers you a special, high-powered laser that he designed. Return with an empty weapon slot when you want them to install it.")),
        ReactorLaser(new QuestDialog(DIALOG, "Install Morgan's Laser", "Morgan's technicians are standing by with something that looks a lot like a military laser -- if you ignore the additional cooling vents and anodized ducts. Do you want them to install Morgan's special laser?"));

        private QuestDialog value;

        QuestPhases(QuestDialog value) {
            this.value = value;
        }

        @Override
        public QuestDialog getValue() {
            return value;
        }

        @Override
        public void setValue(QuestDialog value) {
            this.value = value;
        }
    }

    private EnumMap<QuestPhases, Phase> phases = new EnumMap<>(QuestPhases.class);


    //TODO
    public static String QuestTribbles = "Get rid of those pesky tribbles.";

    enum QuestClues implements SimpleValueEnum<String> {
        Reactor("Deliver the unstable reactor to Nix for Henry Morgan."),
        ReactorFuel("Deliver the unstable reactor to Nix before it consumes all its fuel."),
        ReactorLaser("Get your special laser at Nix.");

        private String value;

        QuestClues(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }


    //TODO

    /*case TribblesAllDied:
            return new FormAlert(AlertsTribblesAllDiedTitle, AlertsTribblesAllDiedMessage,
                                 AlertsOk, DialogResult.OK, null,DialogResult.NONE, args);
            case TribblesAteFood:
            return new FormAlert(AlertsTribblesAteFoodTitle, AlertsTribblesAteFoodMessage, AlertsOk,
                                 DialogResult.OK, null, DialogResult.NONE, args);
            case TribblesGone:
            return new FormAlert(AlertsTribblesGoneTitle, AlertsTribblesGoneMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case TribblesHalfDied:
            return new FormAlert(AlertsTribblesHalfDiedTitle, AlertsTribblesHalfDiedMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case TribblesKilled:
            return new FormAlert(AlertsTribblesKilledTitle, AlertsTribblesKilledMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case TribblesMostDied:
            return new FormAlert(AlertsTribblesMostDiedTitle, AlertsTribblesMostDiedMessage, AlertsOk,
                                 DialogResult.OK, null, DialogResult.NONE, args);
            case TribblesOwn:
            return new FormAlert(AlertsTribblesOwnTitle, AlertsTribblesOwnMessage, AlertsOk,
                                 DialogResult.OK, null, DialogResult.NONE, args);
            case TribblesRemoved:
            return new FormAlert(AlertsTribblesRemovedTitle, AlertsTribblesRemovedMessage, AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, args);
            case TribblesInspector:
            return new FormAlert(AlertsTribblesInspectorTitle, AlertsTribblesInspectorMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case TribblesSqueek:
            return new FormAlert(AlertsTribblesSqueekTitle, AlertsTribblesSqueekMessage, AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case TribblesTradeIn:
            return new FormAlert(AlertsTribblesTradeInTitle, AlertsTribblesTradeInMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);*/

    public static String AlertsTribblesAllDiedTitle = "All The Tribbles Died";
    public static String AlertsTribblesAllDiedMessage = "The radiation from the Ion Reactor is deadly to Tribbles. All of the Tribbles on board your ship have died.";
    public static String AlertsTribblesAteFoodTitle = "Tribbles Ate Food";
    public static String AlertsTribblesAteFoodMessage = "You find that, instead of food, some of your cargo bays contain only Tribbles!";
    public static String AlertsTribblesGoneTitle = "No More Tribbles";
    public static String AlertsTribblesGoneMessage = "The alien uses his alien technology to beam over your whole collection of Tribbles to his ship.";
    public static String AlertsTribblesHalfDiedTitle = "Half The Tribbles Died";
    public static String AlertsTribblesHalfDiedMessage = "The radiation from the Ion Reactor seems to be deadly to Tribbles. Half the Tribbles on board died.";
    public static String AlertsTribblesKilledTitle = "Tribbles Killed";
    public static String AlertsTribblesKilledMessage = "Your Tribbles all died in the explosion.";
    public static String AlertsTribblesMostDiedTitle = "Most Tribbles Died";
    public static String AlertsTribblesMostDiedMessage = "You find that, instead of narcotics, some of your cargo bays contain only dead Tribbles!";
    public static String AlertsTribblesOwnTitle = "A Tribble";
    public static String AlertsTribblesOwnMessage = "You are now the proud owner of a little, cute, furry tribble.";
    public static String AlertsTribblesRemovedTitle = "Tribbles Removed";
    public static String AlertsTribblesRemovedMessage = "The Tribbles were sold with your ship.";
    public static String AlertsTribblesInspectorTitle = "Space Port Inspector";
    public static String AlertsTribblesInspectorMessage = "Our scan reports you have ^1 Tribbles on board your ship. Tribbles are pests worse than locusts! You are running the risk of getting a hefty fine!";
    public static String AlertsTribblesSqueekTitle = "A Tribble";
    public static String AlertsTribblesSqueekMessage = "Squeek!";
    public static String AlertsTribblesTradeInTitle = "You've Got Tribbles";
    public static String AlertsTribblesTradeInMessage = "Hm. I see you got a Tribble infestation on your current ship. I'm sorry, but that severely reduces the trade-in price.";


    enum Alerts implements SimpleValueEnum<AlertDialog> {
        EquipmentMorgansLaser("Morgan's Laser", "You now have Henry Morgan's special laser installed on your ship."),
        EncounterPiratesExamineReactor("Pirates Examine Reactor", "The pirates poke around the Ion Reactor while trying to figure out if it's valuable. They finally conclude that the Reactor is worthless, not to mention dangerous, and leave it on your ship."),
        ReactorConfiscated("Police Confiscate Reactor", "The Police confiscate the Ion reactor as evidence of your dealings with unsavory characters."),
        ReactorDestroyed("Reactor Destroyed", "The destruction of your ship was made much more spectacular by the added explosion of the Ion Reactor."),
        ReactorMeltdown("Reactor Meltdown!", "Just as you approach the docking bay, the reactor explodes into a huge radioactive fireball!"),
        ReactorOnBoard("Reactor", "Five of your cargo bays now contain the unstable Ion Reactor, and ten of your bays contain enriched fuel."),
        ReactorWarningFuel("Reactor Warning", "You notice the Ion Reactor has begun to consume fuel rapidly. In a single day, it has burned up nearly half a bay of fuel!"),
        ReactorWarningFuelGone("Reactor Warning", "The Ion Reactor is emitting a shrill whine, and it's shaking. The display indicates that it is suffering from fuel starvation."),
        ReactorWarningTemperature("Reactor Warning", "The Ion Reactor is smoking and making loud noises. The display warns that the core is close to the melting temperature."),
        ShipBuyReactor("Shipyard Engineer", "Sorry! We can't take your ship as a trade-in. That Ion Reactor looks dangerous, and we have no way of removing it. Come back when you've gotten rid of it.");

        private AlertDialog value;

        Alerts(String title, String body) {
            this.value = new AlertDialog(title, body);
        }

        @Override
        public AlertDialog getValue() {
            return value;
        }

        @Override
        public void setValue(AlertDialog value) {
            this.value = value;
        }
    }


    //TODO
    public static String TribbleDangerousNumber = "a dangerous number of";

    enum Encounters implements SimpleValueEnum<String> {
        PoliceSubmitReactor("an illegal Ion Reactor"),
        PoliceSurrenderReactor("destroy the reactor");

        private String value;

        Encounters(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    //TODO
    public static String NewsTribbleBuyer = "Collector in ^1 System seeks to purchase Tribbles.";

    enum News implements SimpleValueEnum<String> {
        AmbassadorJarekReturnsFromCrisis("Ambassador Jarek Returns from Crisis.");

        private String value;

        News(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    ///TODO
    public static String SpecialCargoTribblesInfest = "An infestation of tribbles.";
    public static String SpecialCargoTribbleCute = "Cute, furry tribble.";
    public static String SpecialCargoTribblesCute = "Cute, furry tribbles.";


    enum SpecialCargo implements SimpleValueEnum<String> {
        Reactor("An unstable reactor taking up 5 bays."),
        ReactorBays("of enriched fuel.");

        private String value;

        SpecialCargo(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    enum CheatTitles implements SimpleValueEnum<String> {
        Reactor("Reactor");
        private String value;

        CheatTitles(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "ReactorQuest{" +
                "questStatus=" + questStatus +
                ", reactorOnBoard=" + isReactorOnBoard() +
                "} " + super.toString();
    }
}
