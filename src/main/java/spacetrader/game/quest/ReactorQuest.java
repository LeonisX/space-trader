package spacetrader.game.quest;

import spacetrader.game.Consts;
import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.Strings;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.enums.QuestName;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.util.*;

import static spacetrader.game.enums.GameEndType.KILLED;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

//TODO -jarek
public class ReactorQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511506L;

    // Constants
    private static final int STATUS_REACTOR_NOT_STARTED = 0;
    private static final int STATUS_REACTOR_FUEL_OK = 1;
    private static final int STATUS_REACTOR_DATE = 20;
    private static final int STATUS_REACTOR_DELIVERED = 21;
    private static final int STATUS_REACTOR_DONE = 22;

    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;

    private volatile int questStatus = 0; // 0 = not encountered, 1-20 = days of mission (bays of fuel left = 10 - (ReactorStatus / 2), 21 = delivered, 22 = Done

    public ReactorQuest(QuestName id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new ReactorPhase(), new ReactorDeliveredPhase(), new ReactorLaserPhase());
        initializeTransitionMap();

        registerListener();

        localize();

        log.fine("started...");
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
        getTransitionMap().put(ON_GET_FILLED_CARGO_BAYS, this::onGetFilledCargoBays);

        getTransitionMap().put(IS_TRADE_SHIP, this::isTradeShip);

        getTransitionMap().put(ENCOUNTER_IS_EXECUTE_ATTACK, this::encounterIsExecuteAttack);
        getTransitionMap().put(ENCOUNTER_ON_ROBBERY, this::encounterOnRobbery);

        getTransitionMap().put(IS_ILLEGAL_SPECIAL_CARGO, this::onIsIllegalSpecialCargo);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS, this::onGetIllegalSpecialCargoActions);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION, this::onGetIllegalSpecialCargoDescription);
        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_ARRIVAL, this::onArrival);

        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
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
        I18n.dumpStrings(Res.Encounters, Arrays.stream(WildQuest.Encounters.values()));
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
            return Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && !jarekOnBoard && isDesiredSystem();

            //case Reactor:
            //    show = game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_NOT_STARTED
            //                        && Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
            //                        && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
            //                break;
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                Game.getShip().hire(jarek);
                jarekOnBoard = true;
                questStatus = STATUS_JAREK_STARTED;
                setQuestState(QuestState.ACTIVE);
                Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            }

            //case Reactor:
            //            if (commander.getShip().getFreeCargoBays() < 15) {
            //        GuiFacade.alert(AlertType.CargoNoEmptyBays);
            //    } else {
            //
            //        BooleanContainer isConflict = new BooleanContainer(false);
            //        questSystem.fireEvent(ON_SPECIAL_BUTTON_CLICKED_IS_CONFLICT, isConflict);
            //
            //        if (!isConflict.getValue()) {
            //            GuiFacade.alert(AlertType.ReactorOnBoard);
            //            setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_FUEL_OK);
            //            confirmQuestPhase();
            //        }
            //    }
            //                break;
        }

        @Override
        public String toString() {
            return "ReactorPhase{} " + super.toString();
        }
    }

    class ReactorDeliveredPhase extends Phase { //new SpecialEvent(SpecialEventType.ReactorDelivered, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && !jarekOnBoard && isDesiredSystem();

            //case ReactorDelivered:
            //    show = Game.getShip().isReactorOnBoard();
            //                break;
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                Game.getShip().hire(jarek);
                jarekOnBoard = true;
                questStatus = STATUS_JAREK_STARTED;
                setQuestState(QuestState.ACTIVE);
                Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            }

            //case ReactorDelivered:
            //    setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_DELIVERED);
            //    switchQuestPhase(SpecialEventType.ReactorLaser);
            //                break;
        }

        @Override
        public String toString() {
            return "ReactorDeliveredPhase{} " + super.toString();
        }
    }

    class ReactorLaserPhase extends Phase { //new SpecialEvent(SpecialEventType.ReactorLaser, 0, 0, false),
        @Override
        public boolean canBeExecuted() {
            return jarekOnBoard && isDesiredSystem();

            //case ReactorLaser:
            //    show = true;
            //    break;
        }

        @Override
        public void successFlow() {
            log.fine("phase #3");
            setQuestState(QuestState.FINISHED);
            questStatus = STATUS_JAREK_DONE;
            removePassenger();
            shipBarCode = Game.getShip().getBarCode();
            game.getQuestSystem().unSubscribeAll(getQuest());

            //case ReactorLaser:
            //            if (commander.getShip().getFreeWeaponSlots() == 0) {
            //        GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            //    } else {
            //        GuiFacade.alert(AlertType.EquipmentMorgansLaser);
            //        commander.getShip().addEquipment(Consts.Weapons[WeaponType.MORGANS_LASER.castToInt()]);
            //        setQuestStatusReactor(SpecialEvent.STATUS_REACTOR_DONE);
            //        confirmQuestPhase();
            //    }
            //                break;
        }

        @Override
        public String toString() {
            return "ReactorLaserPhase{} " + super.toString();
        }
    }

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
        if (isReactorOnBoard()) {
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
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (isReactorOnBoard()) {
            if (questStatus == STATUS_REACTOR_FUEL_OK) {
                ((ArrayList<String>) object).add(QuestClues.Reactor.getValue());
            } else {
                ((ArrayList<String>) object).add(QuestClues.ReactorFuel.getValue());
            }
        } else if (questStatus == STATUS_REACTOR_DELIVERED) {
            ((ArrayList<String>) object).add(QuestClues.ReactorLaser.getValue());
        } else {
            log.fine("skipped");
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
            GuiFacade.alert(AlertType.ShipBuyReactor);
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
            GuiFacade.alert(AlertType.EncounterPiratesExamineReactor);
        }
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

    // TODO repeat if < normal, otherwise fail
    private void onArrested(Object object) {
        /*if (jarekOnBoard) {
            log.fine("Arrested + Jarek");
            showAlert(Alerts.JarekTakenHome.getValue());
            failQuest();
        } else {
            log.fine("Arrested w/o Jarek");
        }*/

        //TODO
        if (isReactorOnBoard()) {
            GuiFacade.alert(AlertType.ReactorConfiscated);
            questStatus = STATUS_REACTOR_NOT_STARTED;
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onEscapeWithPod(Object object) {
        /*if (jarekOnBoard) {
            log.fine("Escaped + Jarek");
            showAlert(Alerts.JarekTakenHome.getValue());
            failQuest();
        } else {
            log.fine("Escaped w/o Jarek");
        }*/


        if (isReactorOnBoard()) {
            GuiFacade.alert(AlertType.ReactorDestroyed);
            //TODO is it correct???
            questStatus = STATUS_REACTOR_DONE;
        }

    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_REACTOR_NOT_STARTED;
        setQuestState(QuestState.FAILED);
    }

    private void onIncrementDays(Object object) {
        /*if (jarekOnBoard) {
            log.fine(questStatus + "");
            if (questStatus == STATUS_JAREK_IMPATIENT / 2) {
                showAlert(Alerts.SpecialPassengerConcernedJarek.getValue());
            } else if (questStatus == STATUS_JAREK_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientJarek.getValue());
                jarek.setPilot(0);
                jarek.setFighter(0);
                jarek.setTrader(0);
                jarek.setEngineer(0);
            }

            if (questStatus < STATUS_JAREK_IMPATIENT) {
                questStatus++;
            }
        } else {
            log.fine("skipped");
        }*/

        if (isReactorOnBoard()) {
            questStatus = (Math.min(questStatus + ((IntContainer) object).getValue(), STATUS_REACTOR_DATE));
        }
    }

    // checkReactorOnArrival
    private void onArrival(Object object) {
        if (questStatus == STATUS_REACTOR_DATE) {
            GuiFacade.alert(AlertType.ReactorMeltdown);
            questStatus = STATUS_REACTOR_NOT_STARTED;
            if (Game.getShip().getEscapePod()) {
                game.escapeWithPod();
            } else {
                GuiFacade.alert(AlertType.ReactorDestroyed);
                throw new GameEndException(KILLED.castToInt());
            }
        } else {
            // Reactor warnings: now they know the quest has a time constraint!
            if (questStatus == STATUS_REACTOR_FUEL_OK + 1) {
                GuiFacade.alert(AlertType.ReactorWarningFuel);
            } else if (questStatus == STATUS_REACTOR_DATE - 4) {
                GuiFacade.alert(AlertType.ReactorWarningFuelGone);
            } else if (questStatus == STATUS_REACTOR_DATE - 2) {
                GuiFacade.alert(AlertType.ReactorWarningTemp);
            }
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Reactor.name())) {
            questStatus = Math.max(0, cheatWords.getNum2());
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CheatTitles.Reactor.getValue(), questStatus);
    }

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
    /*case EncounterPiratesExamineReactor:
            return new FormAlert(AlertsEncounterPiratesExamineReactorTitle, AlertsEncounterPiratesExamineReactorMessage,
                                 AlertsOk, DialogResult.OK, null,DialogResult.NONE, args);
 case ReactorConfiscated:
            return new FormAlert(AlertsReactorConfiscatedTitle, AlertsReactorConfiscatedMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ReactorDestroyed:
            return new FormAlert(AlertsReactorDestroyedTitle, AlertsReactorDestroyedMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ReactorOnBoard:
            return new FormAlert(AlertsReactorOnBoardTitle, AlertsReactorOnBoardMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ReactorMeltdown:
            return new FormAlert(AlertsReactorMeltdownTitle, AlertsReactorMeltdownMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ReactorWarningFuel:
            return new FormAlert(AlertsReactorWarningFuelTitle, AlertsReactorWarningFuelMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ReactorWarningFuelGone:
            return new FormAlert(AlertsReactorWarningFuelGoneTitle, AlertsReactorWarningFuelGoneMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ReactorWarningTemp:
            return new FormAlert(AlertsReactorWarningTempTitle, AlertsReactorWarningTempMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case ShipBuyReactor:
            return new FormAlert(AlertsShipBuyReactorTitle, AlertsShipBuyReactorMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);*/


    //    public static String AlertsEncounterPiratesExamineReactorTitle = "Pirates Examine Reactor";
    //    public static String AlertsEncounterPiratesExamineReactorMessage = "The pirates poke around the Ion Reactor while trying to figure out if it's valuable. They finally conclude that the Reactor is worthless, not to mention dangerous, and leave it on your ship.";
    //    public static String AlertsReactorConfiscatedTitle = "Police Confiscate Reactor";
    //    public static String AlertsReactorConfiscatedMessage = "The Police confiscate the Ion reactor as evidence of your dealings with unsavory characters.";
    //    public static String AlertsReactorDestroyedTitle = "Reactor Destroyed";
    //    public static String AlertsReactorDestroyedMessage = "The destruction of your ship was made much more spectacular by the added explosion of the Ion Reactor.";
    //    public static String AlertsReactorOnBoardTitle = "Reactor";
    //    public static String AlertsReactorOnBoardMessage = "Five of your cargo bays now contain the unstable Ion Reactor, and ten of your bays contain enriched fuel.";
    //    public static String AlertsReactorMeltdownTitle = "Reactor Meltdown!";
    //    public static String AlertsReactorMeltdownMessage = "Just as you approach the docking bay, the reactor explodes into a huge radioactive fireball!";
    //    public static String AlertsReactorWarningFuelTitle = "Reactor Warning";
    //    public static String AlertsReactorWarningFuelMessage = "You notice the Ion Reactor has begun to consume fuel rapidly. In a single day, it has burned up nearly half a bay of fuel!";
    //    public static String AlertsReactorWarningFuelGoneTitle = "Reactor Warning";
    //    public static String AlertsReactorWarningFuelGoneMessage = "The Ion Reactor is emitting a shrill whine, and it's shaking. The display indicates that it is suffering from fuel starvation.";
    //    public static String AlertsReactorWarningTempTitle = "Reactor Warning";
    //    public static String AlertsReactorWarningTempMessage = "The Ion Reactor is smoking and making loud noises. The display warns that the core is close to the melting temperature.";
    //    public static String AlertsShipBuyReactorTitle = "Shipyard Engineer";
    //    public static String AlertsShipBuyReactorMessage = "Sorry! We can't take your ship as a trade-in. That Ion Reactor looks dangerous, and we have no way of removing it. Come back when you've gotten rid of it.";
    //

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialPassengerConcernedJarek("Ship's Comm.", "Commander? Jarek here. Do you require any assistance in charting a course to Devidia?"),
        SpecialPassengerImpatientJarek("Ship's Comm.", "Captain! This is the Ambassador speaking. We should have been there by now?!"),
        JarekTakenHome("Jarek Taken Home", "The Space Corps decides to give ambassador Jarek a lift home to Devidia.");

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