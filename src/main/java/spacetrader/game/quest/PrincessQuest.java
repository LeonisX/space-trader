package spacetrader.game.quest;

import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.ScoreContainer;
import spacetrader.game.quest.containers.StringContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.util.Map;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.PrincessEncounters.*;
import static spacetrader.game.quest.PrincessQuestPhase.*;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

enum PrincessAlertName {
    SpecialPassengerConcernedPrincess, SpecialPassengerImpatientPrincess, EncounterPiratesSurrenderPrincess,
    PrincessTakenHome, GameEndBoughtMoonGirl
}

enum PrincessNewsEvents {
    Princess,           // Galvon
    PrincessCentauri,
    PrincessInthara,
    PrincessQonos,
    PrincessRescued,
    PrincessReturned
}

enum QuestPrincess {
    QuestPrincessCentauri,
    QuestPrincessInthara,
    QuestPrincessQonos,
    QuestPrincessReturn,
    QuestPrincessReturning,
    QuestPrincessReturningImpatient,
    QuestPrincessQuantum
}

enum PrincessQuestPhase {
    Princess,
    PrincessCentauri,
    PrincessInthara,
    PrincessQonos,
    PrincessReturned,
    PrincessQuantum
}

enum PrincessEncounters {
    EncounterPretextScorpion, EncounterPrincessRescued, EncounterHidePrincess
}

class PrincessQuest extends AbstractQuest {

    //"Ziyal", // From ST: Deep Space 9
    //"Scorpion" // dummy crew member used in opponent ship
    //PRINCESS, // = 53,
    //SCORPION,// = 54
    private static final String[] CREW_MEMBER_NAMES = {"Ziyal", "Scorpion"};     // Mercenary

    private static final String CHEATS_TITLE = "Princess";

    private static final String[] NEWS = {
            "Member of Royal Family kidnapped!",
            "Aggressive Ship Seen in Orbit Around Centauri",
            "Dangerous Scorpion Damages Several Other Ships Near Inthara",
            "Kidnappers Holding Out at Qonos",
            "Scorpion Defeated! Kidnapped Member of Galvon Royal Family Freed!",
            "Beloved Royal Returns Home!"
    };

    private static final String[] QUESTS = {
            "Follow the Scorpion to Centauri.",     // QuestPrincessCentauri
            "Follow the Scorpion to Inthara.",      // QuestPrincessInthara
            "Follow the Scorpion to Qonos.",        // QuestPrincessQonos
            "Transport ^1 from Qonos to Galvon.",   // QuestPrincessReturn
            "Return ^1 to Galvon.",                 // QuestPrincessReturning
            "Return ^1 to Galvon." + newline        // QuestPrincessReturningImpatient
                    + "She is becoming anxious to arrive at home, and is no longer of any help in engineering functions.",
            "Get your Quantum Disruptor at Galvon.",// QuestPrincessQuantum
    };

    private static final QuestDialog[] DIALOGS = new QuestDialog[]{
            new QuestDialog(ALERT, "Kidnapped", "A member of the Royal Family of Galvon has been kidnapped! Princess Ziyal was abducted by men while travelling across the planet. They escaped in a hi-tech ship called the Scorpion. Please rescue her! (You'll need to equip your ship with disruptors to be able to defeat the Scorpion without destroying it.) A ship bristling with weapons was blasting out of the system. It's trajectory before going to warp indicates that its destination was Centauri."),
            new QuestDialog(ALERT, "Aggressive Ship", "A ship had its shields upgraded to Lighting Shields just two days ago. A shipyard worker overheard one of the crew saying they were headed to Inthara."),
            new QuestDialog(ALERT, "Dangerous Scorpion", "Just yesterday a ship was seen in docking bay 327. A trader sold goods to a member of the crew, who was a native of Qonos. It's possible that's where they were going next."),
            new QuestDialog(DIALOG, "Royal Rescue", "The Galvonian Ambassador to Qonos approaches you. The Princess needs a ride home. Will you take her? I don't think she'll feel safe with anyone else."),
            new QuestDialog(ALERT, "Royal Return", "The King and Queen are extremely grateful to you for returning their daughter to them. The King says, \"Ziyal is priceless to us, but we feel we must offer you something as a reward. Visit my shipyard captain and he'll install one of our new Quantum Disruptors.\""),
            new QuestDialog(DIALOG, "Quantum Disruptor", "His Majesty's Shipyard: Do you want us to install a quantum disruptor on your current ship?")
    };

    private static final AlertDialog[] ALERTS = new AlertDialog[]{
            new AlertDialog("Ship's Comm.", "[Ziyal] Oh Captain? (giggles) Would it help if I got out and pushed?"), // AlertsSpecialPassengerConcernedPrincessTitle
            new AlertDialog("Ship's Comm.", "Sir! Are you taking me home or merely taking the place of my previous captors?!"), // AlertsSpecialPassengerImpatientPrincessTitle
            new AlertDialog("You Have the Princess", "Pirates are not nice people, and there's no telling what they might do to the Princess. Better to die fighting than give her up to them!"), // AlertsEncounterPiratesSurrenderPrincessTitle
            new AlertDialog("Princess Taken Home", "The Space Corps decides to give the Princess a ride home to Galvon since you obviously weren't up to the task."), // AlertsPrincessTakenHomeTitle
            new AlertDialog("You Have Retired with the Princess", "") // AlertsGameEndBoughtMoonGirlTitle
    };

    public static final String[] ENCOUNTERS = {
            "the kidnappers in a ^1",
            newline + newline + "You land your ship near where the Space Corps has landed with the Scorpion in tow. The Princess is revived from hibernation and you get to see her for the first time. Instead of the spoiled child you were expecting, Ziyal is possible the most beautiful woman you've ever seen. \"What took you so long?\" she demands. You notice a twinkle in her eye, and then she smiles. Not only is she beautiful, but she's got a sense of humor. She says, \"Thank you for freeing me. I am in your debt.\" With that she give you a kiss on the cheek, then leaves. You hear her mumble, \"Now about a ride home.\"",
            "the Princess"
    };

    private static final String GAME_COMPLETION = "Claimed moon with Princess";

    // Constants
    private final static int STATUS_PRINCESS_NOT_STARTED = 0;
    private final static int STATUS_PRINCESS_FLY_CENTAURI = 1;
    private final static int STATUS_PRINCESS_FLY_INTHARA = 2;
    private final static int STATUS_PRINCESS_FLY_QONOS = 3;
    private final static int STATUS_PRINCESS_RESCUED = 4;
    private final static int STATUS_PRINCESS_IMPATIENT = 14;
    private final static int STATUS_PRINCESS_RETURNED = 15;
    private final static int STATUS_PRINCESS_DONE = 16;

    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;
    private static final int CASH_TO_SPEND = 0;

    private int questStatusPrincess = 0; // 0 = not available, 1 = Go to Centauri, 2 = Go to Inthara, 3 =
    // Go to Qonos, 4 = Princess Rescued, 5-14 = On Board, 15 = Princess Returned, 16 = Got Quantum Disruptor

    private CrewMember princess;
    private CrewMember scorpionCrew;

    private int shipSpecId;

    private boolean princessOnBoard;

    private Ship scorpion; //ShipType.SCORPION

    private int gameEndTypeId;

    private Game game = Game.getCurrentGame();

    public PrincessQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);
        initializePhases(DIALOGS, new PrincessPhase(), new PrincessCentauriPhase(), new PrincessIntharaPhase(),
                new PrincessQonosPhase(), new PrincessReturnedPhase(), new PrincessQuantumPhase());
        initializeTransitionMap();

        int d = Game.getDifficultyId();
        princess = registerNewSpecialCrewMember(4, 3, 8, 9);
        scorpionCrew = registerNewSpecialCrewMember(8 + d, 8 + d, 1, 6 + d);

        registerNews(NEWS.length);

        gameEndTypeId = registerNewGameEndType();

        //setSpecialCrewId(princess.getId());

        registerListener();
        log.fine("started...");
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();

        getTransitionMap().put(AFTER_SHIP_SPECS_INITIALIZED, this::afterShipSpecInitialized);

        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);

        getTransitionMap().put(ON_DETERMINE_NON_RANDOM_ENCOUNTER, this::onDetermineNonRandomEncounter);
        getTransitionMap().put(ON_CREATE_SHIP, this::onCreateShip);
        getTransitionMap().put(ON_ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, this::onEncounterExecuteActionOpponentDisabled);
        getTransitionMap().put(ON_ENCOUNTER_EXECUTE_ATTACK_KEEP_SPECIAL_SHIP, this::onEncounterExecuteAttackKeepSpecialShip);

        getTransitionMap().put(ON_ENCOUNTER_VERIFY_ATTACK, this::onEncounterVerifyAttack);
        getTransitionMap().put(ON_ENCOUNTER_VERIFY_SURRENDER_NO_HIDDEN, this::onEncounterVerifySurrenderNoHidden);
        getTransitionMap().put(ON_ENCOUNTER_VERIFY_SURRENDER_HIDDEN, this::onEncounterVerifySurrenderHidden);

        getTransitionMap().put(ON_GET_STEALABLE_CARGO, this::onGetStealableCargo);

        getTransitionMap().put(ON_GET_ENCOUNTER_TEXT_INITIAL, this::onGetEncounterTextInitial);

        getTransitionMap().put(ON_BEFORE_GAME_END, this::onBeforeGameEnd);
        getTransitionMap().put(ON_GAME_END_ALERT, this::onGameEndAlert);
        getTransitionMap().put(ON_GET_GAME_SCORE, this::onGetGameScore);
    }

    private void onBeforeGameEnd(Object object) {
        if (game.getEndStatus() == GameEndType.BOUGHT_MOON.castToInt()
                && questStatusPrincess >= STATUS_PRINCESS_RETURNED) {
            game.setEndStatus(gameEndTypeId);
        }
    }

    private void afterShipSpecInitialized(Object object) {
        ShipSpec shipSpec = new ShipSpec(ShipType.QUEST, Size.HUGE, 30, 2, 2, 2, 2, 1, 1, 300, 1, 500000, 0, Activity.NA,
                Activity.NA, Activity.NA, TechLevel.UNAVAILABLE);
        shipSpecId = registerNewShipSpec(shipSpec);
    }

    @Override
    public String getGameCompletionText() {
        return GAME_COMPLETION;
    }

    private void onGameEndAlert(Object object) {
        //TODO need to pass string value as image ID, and get image by this value
        new FormAlert(ALERTS[PrincessAlertName.GameEndBoughtMoonGirl.ordinal()].getTitle(), GameEndType.QUEST.castToInt()).showDialog();
    }

    private void onGetGameScore(Object object) {
        ScoreContainer score = (ScoreContainer) object;
        if (score.getEndStatus() == 1) {
            score.setDaysMoon(Math.max(0, (Game.getDifficultyId() + 1) * 100 - Game.getCommander().getDays()));
            score.setModifier(100); //TODO 110????
        }
    }

    private void onGetEncounterTextInitial(Object object) {
        if (scorpion.getBarCode() == Game.getCurrentGame().getOpponent().getBarCode()) {
            ((StringContainer) object).setValue(ENCOUNTERS[EncounterPretextScorpion.ordinal()]);
        }
    }

    private void onCreateShip(Object object) {
        scorpion = game.createShipByShipSpecId(shipSpecId);
        scorpion.getCrew()[0] = scorpionCrew;
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);
    }

    private void onDetermineNonRandomEncounter(Object object) {
        if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Qonos && questStatusPrincess == STATUS_PRINCESS_FLY_QONOS) {
            game.setOpponent(scorpion);
            game.setEncounterType(Game.getShip().isCloaked() ? EncounterType.QUEST_IGNORE : EncounterType.QUEST_ATTACK);
            ((BooleanContainer) object).setValue(true);
        }
    }

    //TODO test
    private void onGetStealableCargo(Object object) {
        if (princessOnBoard) {
            ((IntContainer) object).setValue(((IntContainer) object).getValue() - 1);
        }
    }

    private void onEncounterVerifySurrenderNoHidden(Object object) {
        if (princessOnBoard && !Game.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)
                && game.getEncounterType() == EncounterType.PIRATE_ATTACK) {
            showAlert(ALERTS[PrincessAlertName.EncounterPiratesSurrenderPrincess.ordinal()]);
            ((BooleanContainer) object).setValue(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void onEncounterVerifySurrenderHidden(Object object) {
        if (princessOnBoard && Game.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            ((ArrayList<String>) object).add(ENCOUNTERS[EncounterHidePrincess.ordinal()]);
        }
    }

    private void onEncounterVerifyAttack(Object object) {
        //if (game.getOpponent().getType() == ShipType.SCORPION
        if (game.getOpponent().getBarCode() == scorpion.getBarCode()
                && Game.getShip().getWeaponStrength(WeaponType.PHOTON_DISRUPTOR, WeaponType.QUANTUM_DISRUPTOR) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoDisruptors);
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void onEncounterExecuteAttackKeepSpecialShip(Object object) {
        // Make sure the Scorpion doesn't get destroyed.
        Ship defender = ((Ship) object);
        //if (defender.getType() == ShipType.SCORPION && defender.getHull() == 0) {
        if (defender.getBarCode() == scorpion.getBarCode() && defender.getHull() == 0) {
            defender.setHull(1);
            game.setOpponentDisabled(true);
        }
    }

    private void onEncounterExecuteActionOpponentDisabled(Object object) {
/*        switch (game.getOpponent().getType()) {
            case SCORPION:*/
        if (game.getOpponent().getBarCode() == scorpion.getBarCode()) {
                Game.getCommander().setKillsPirate(Game.getCommander().getKillsPirate() + 1);
                Game.getCommander().setPoliceRecordScore(Game.getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
                questStatusPrincess = STATUS_PRINCESS_RESCUED;

                GuiFacade.alert(AlertType.EncounterDisabledOpponent, game.getEncounterShipText(), ENCOUNTERS[EncounterPrincessRescued.ordinal()]);

                Game.getCommander().setReputationScore(
                        //TODO getOpponent().getType() == 16
                        Game.getCommander().getReputationScore() + (game.getOpponent().getType().castToInt() / 2 + 1));
                ((BooleanContainer) object).setValue(true);
        }
    }

    @Override
    public void affectSkills(int[] skills) {
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public String getCrewMemberName(int id) {
        return CREW_MEMBER_NAMES[getSpecialCrewIds().indexOf(id)];
    }

    @Override
    public String getNewsTitle(int newsId) {
        return NEWS[getNewsIds().indexOf(newsId)];
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Galvon);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        getPhase(Princess).setStarSystemId(starSystem.getId());
        getPhase(PrincessReturned).setStarSystemId(starSystem.getId());
        getPhase(PrincessQuantum).setStarSystemId(starSystem.getId());

        starSystem = Game.getStarSystem(StarSystemId.Centauri);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        getPhase(PrincessQuestPhase.PrincessCentauri).setStarSystemId(starSystem.getId());

        starSystem = Game.getStarSystem(StarSystemId.Inthara);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        getPhase(PrincessQuestPhase.PrincessInthara).setStarSystemId(starSystem.getId());

        starSystem = Game.getStarSystem(StarSystemId.Qonos);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        getPhase(PrincessQuestPhase.PrincessQonos).setStarSystemId(starSystem.getId());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        game.getMercenaries().put(princess.getId(), princess);
        game.getMercenaries().put(scorpionCrew.getId(), scorpionCrew);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class PrincessPhase extends Phase { //new SpecialEvent(SpecialEventType.Princess, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() /*&& !isQuestIsActive()*/
                    && Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful
                    && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage
                    && questStatusPrincess == STATUS_PRINCESS_NOT_STARTED;
        }

        @Override
        public String toString() {
            return "PrincessPhase{} " + super.toString();
        }
    }

    class PrincessCentauriPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessCentauri, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() /*&& isQuestIsActive()*/
                    && questStatusPrincess == STATUS_PRINCESS_FLY_CENTAURI;
        }

        @Override
        public String toString() {
            return "PrincessCentauriPhase{} " + super.toString();
        }
    }

    class PrincessIntharaPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessInthara, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() /*&& isQuestIsActive()*/
                    && questStatusPrincess == STATUS_PRINCESS_FLY_INTHARA;
        }

        @Override
        public String toString() {
            return "PrincessIntharaPhase{} " + super.toString();
        }
    }

    class PrincessQonosPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessQonos, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() /*&& isQuestIsActive()*/
                    && questStatusPrincess == STATUS_PRINCESS_RESCUED;
        }

        @Override
        public String toString() {
            return "PrincessQonosPhase{} " + super.toString();
        }
    }

    class PrincessReturnedPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessReturned, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return princessOnBoard && isDesiredSystem() /*&& isQuestIsActive()*/
                    && questStatusPrincess >= STATUS_PRINCESS_RESCUED
                    && questStatusPrincess <= STATUS_PRINCESS_IMPATIENT;
        }

        @Override
        public String toString() {
            return "PrincessReturnedPhase{} " + super.toString();
        }
    }

    class PrincessQuantumPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessQuantum, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() /*&& isQuestIsActive()*/
                    && questStatusPrincess == STATUS_PRINCESS_RETURNED;
        }

        @Override
        public String toString() {
            return "PrincessQuantumPhase{} " + super.toString();
        }
    }

    private void onSpecialButtonClicked(Object object) {
        if (getPhase(Princess).canBeExecuted()) {
            log.fine("phase #" + Princess);
            showDialogAndProcessResult(object, getPhase(Princess).getDialog(), () -> {
                questStatusPrincess = STATUS_PRINCESS_FLY_CENTAURI;
                setQuestState(QuestState.ACTIVE);
            });
        } else if (getPhase(PrincessCentauri).canBeExecuted()) {
            log.fine("phase #" + PrincessCentauri);
            showDialogAndProcessResult(object, getPhase(PrincessCentauri).getDialog(), () -> {
                questStatusPrincess = STATUS_PRINCESS_FLY_INTHARA;
                game.getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            });
        } else if (getPhase(PrincessInthara).canBeExecuted()) {
            log.fine("phase #" + PrincessInthara);
            showDialogAndProcessResult(object, getPhase(PrincessInthara).getDialog(), () -> {
                questStatusPrincess = STATUS_PRINCESS_FLY_QONOS;
                game.getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            });
        } else if (getPhase(PrincessQonos).canBeExecuted()) {
            log.fine("phase #" + PrincessQonos);
            showDialogAndProcessResult(object, getPhase(PrincessQonos).getDialog(), () -> {
                //TODO where does the princess go? need to test this case.
                if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                } else {
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, princess.getName());
                    Game.getShip().hire(princess);
                    princessOnBoard = true;
                    questStatusPrincess = STATUS_PRINCESS_RESCUED;
                    game.getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
                }
            });
        } else if (getPhase(PrincessReturned).canBeExecuted()) {
            log.fine("phase #" + PrincessReturned);
            showDialogAndProcessResult(object, getPhase(PrincessReturned).getDialog(), () -> {
                questStatusPrincess = STATUS_PRINCESS_RETURNED;
                Game.getShip().fire(princess.getId());
                princessOnBoard = false;
            });
        } else if (getPhase(PrincessQuantum).canBeExecuted() && isQuestIsActive()) {
            log.fine("phase #" + PrincessQuantum);
            showDialogAndProcessResult(object, getPhase(PrincessQuantum).getDialog(), () -> {
                if (Game.getShip().getFreeWeaponSlots() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                } else {
                    GuiFacade.alert(AlertType.EquipmentQuantumDisruptor);
                    Game.getShip().addEquipment(Consts.Weapons[WeaponType.QUANTUM_DISRUPTOR.castToInt()]);
                    questStatusPrincess = STATUS_PRINCESS_DONE;
                    setQuestState(QuestState.FINISHED);
                    QuestSystem.unSubscribeAll(getQuest());
                }
            });
        } else {
            log.fine("skipped");
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CHEATS_TITLE)) {
            questStatusPrincess = Math.max(0, cheatWords.getNum2());
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CHEATS_TITLE, questStatusPrincess);
    }

    private void onArrested(Object object) {
        if (princessOnBoard) {
            log.fine("Arrested + Princess");
            showAlert(ALERTS[PrincessAlertName.PrincessTakenHome.ordinal()]);
            questStatusPrincess = STATUS_PRINCESS_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Arrested w/o Princess");
        }
    }

    private void onEscapeWithPod(Object object) {
        if (princessOnBoard) {
            log.fine("Escaped + Princess");
            showAlert(ALERTS[PrincessAlertName.PrincessTakenHome.ordinal()]);
            questStatusPrincess = STATUS_PRINCESS_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Escaped w/o Princess");
        }
    }

    private void onIncrementDays(Object object) {
        if (princessOnBoard) {
            log.fine(questStatusPrincess + "");
            if (questStatusPrincess == (STATUS_PRINCESS_IMPATIENT + STATUS_PRINCESS_RESCUED) / 2) {
                showAlert(ALERTS[PrincessAlertName.SpecialPassengerConcernedPrincess.ordinal()]);
            } else if (questStatusPrincess == STATUS_PRINCESS_IMPATIENT - 1) {
                showAlert(ALERTS[PrincessAlertName.SpecialPassengerImpatientPrincess.ordinal()]);
                princess.setPilot(0);
                princess.setFighter(0);
                princess.setTrader(0);
                princess.setEngineer(0);
            }

            if (questStatusPrincess < STATUS_PRINCESS_IMPATIENT) {
                questStatusPrincess++;
            }
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        switch (questStatusPrincess) {
            case STATUS_PRINCESS_FLY_CENTAURI:
                ((ArrayList<String>) object).add(QUESTS[QuestPrincess.QuestPrincessCentauri.ordinal()]);
                log.fine(QUESTS[QuestPrincess.QuestPrincessCentauri.ordinal()]);
                break;
            case STATUS_PRINCESS_FLY_INTHARA:
                ((ArrayList<String>) object).add(QUESTS[QuestPrincess.QuestPrincessInthara.ordinal()]);
                log.fine(QUESTS[QuestPrincess.QuestPrincessInthara.ordinal()]);
                break;
            case STATUS_PRINCESS_FLY_QONOS:
                ((ArrayList<String>) object).add(QUESTS[QuestPrincess.QuestPrincessQonos.ordinal()]);
                log.fine(QUESTS[QuestPrincess.QuestPrincessQonos.ordinal()]);
                break;
            case STATUS_PRINCESS_RESCUED:
                if (princessOnBoard) {
                    if (questStatusPrincess == STATUS_PRINCESS_IMPATIENT) {
                        ((ArrayList<String>) object).add(Functions.stringVars(
                                QUESTS[QuestPrincess.QuestPrincessReturningImpatient.ordinal()], CREW_MEMBER_NAMES[0]));
                        log.fine(Functions.stringVars(
                                QUESTS[QuestPrincess.QuestPrincessReturningImpatient.ordinal()], CREW_MEMBER_NAMES[0]));
                    } else {
                        ((ArrayList<String>) object).add(Functions.stringVars(
                                QUESTS[QuestPrincess.QuestPrincessReturning.ordinal()], CREW_MEMBER_NAMES[0]));
                        log.fine(Functions.stringVars(
                                QUESTS[QuestPrincess.QuestPrincessReturning.ordinal()], CREW_MEMBER_NAMES[0]));
                    }
                } else {
                    ((ArrayList<String>) object).add(Functions.stringVars(
                            QUESTS[QuestPrincess.QuestPrincessReturn.ordinal()], CREW_MEMBER_NAMES[0]));
                    log.fine(Functions.stringVars(
                            QUESTS[QuestPrincess.QuestPrincessReturn.ordinal()], CREW_MEMBER_NAMES[0]));
                }
                break;
            case STATUS_PRINCESS_RETURNED:
                ((ArrayList<String>) object).add(QUESTS[QuestPrincess.QuestPrincessQuantum.ordinal()]);
                log.fine(QUESTS[QuestPrincess.QuestPrincessQuantum.ordinal()]);
                break;
            default:
                log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (questStatusPrincess == STATUS_PRINCESS_NOT_STARTED && Game.isCurrentSystemIs(StarSystemId.Galvon)) {
            log.fine("" + getNewsIds().get(PrincessNewsEvents.Princess.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(PrincessNewsEvents.Princess.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_FLY_CENTAURI) {
            log.fine("" + getNewsIds().get(PrincessNewsEvents.PrincessCentauri.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(PrincessNewsEvents.PrincessCentauri.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_FLY_INTHARA) {
            log.fine("" + getNewsIds().get(PrincessNewsEvents.PrincessInthara.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(PrincessNewsEvents.PrincessInthara.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_FLY_QONOS) {
            log.fine("" + getNewsIds().get(PrincessNewsEvents.PrincessQonos.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(PrincessNewsEvents.PrincessQonos.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_RESCUED) {
            log.fine("" + getNewsIds().get(PrincessNewsEvents.PrincessRescued.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(PrincessNewsEvents.PrincessRescued.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_RETURNED) {
            log.fine("" + getNewsIds().get(PrincessNewsEvents.PrincessReturned.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(PrincessNewsEvents.PrincessReturned.ordinal()));
        } else {
            log.fine("skipped");
        }
    }

    private Phase getPhase(PrincessQuestPhase status) {
        return getPhase(status.ordinal());
    }

    @Override
    public String toString() {
        return "PrincessQuest{" +
                "questStatusPrincess=" + questStatusPrincess +
                ", princess=" + princess +
                ", scorpionCrew=" + scorpionCrew +
                ", princessOnBoard=" + princessOnBoard +
                ", scorpion=" + scorpion +
                "} " + super.toString();
    }
}

