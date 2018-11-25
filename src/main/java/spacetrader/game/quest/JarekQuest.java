package spacetrader.game.quest;

import spacetrader.game.Consts;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.EventName.*;
import static spacetrader.game.quest.MessageType.ALERT;
import static spacetrader.game.quest.MessageType.DIALOG;
import static spacetrader.game.quest.JarekQuestStatus.Jarek;
import static spacetrader.game.quest.JarekQuestStatus.JarekGetsOut;

class JarekQuest extends AbstractQuest {

    private static final Logger log = Logger.getLogger(LotteryQuest.class.getName());

    private static final String CREW_MEMBER_NAME = "Jarek";     // Mercenary

    private static final String SPECIAL_CARGO_TITLE = "A haggling computer.";

    private static final String CHEATS_TITLE = "Jarek";

    private static final String NEWS = "Ambassador Jarek Returns from Crisis.";

    private static final String[] QUESTS = {
            "Take ambassador Jarek to Devidia.",
            "Take ambassador Jarek to Devidia." + newline + "Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades."
    };

    private static final QuestDialog[] DIALOGS = new QuestDialog[]{
            new QuestDialog(DIALOG, "Ambassador Jarek", "A recent change in the political climate of this solar system has forced Ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?"),
            new QuestDialog(ALERT, "Jarek Gets Out", "Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment.")
    };

    private static final AlertDialog[] ALERTS = new AlertDialog[]{
            new AlertDialog("Ship's Comm.", "Commander? Jarek here. Do you require any assistance in charting a course to Devidia?"),
            new AlertDialog("Ship's Comm.", "Captain! This is the Ambassador speaking. We should have been there by now?!"),
            new AlertDialog("Jarek Taken Home", "The Space Corps decides to give ambassador Jarek a lift home to Devidia."),
    };

    // Constants
    private final static int STATUS_JAREK_NOT_STARTED = 0;
    private final static int STATUS_JAREK_STARTED = 1;
    private final static int STATUS_JAREK_IMPATIENT = 11;
    private final static int STATUS_JAREK_DONE = 12;

    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;
    private static final int CASH_TO_SPEND = 0;

    private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private CrewMember jarek = CrewMember.specialCrewMember(getSpecialCrewId(), 3, 2, 10, 4);
    private boolean jarekOnBoard;

    private UUID shipBarCode = UUID.randomUUID();

    public JarekQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);
        initializePhases(DIALOGS, new JarekPhase(), new JarekGetsOutPhase());

        setSpecialCrewId(jarek.getId());

        registerListener();
        log.fine("started...");
    }

    private boolean isHagglingComputerOnBoard() {
        return Game.getShip().getBarCode() == shipBarCode && questStatusJarek == STATUS_JAREK_DONE;
    }

    @Override
    public void affectSkills(int[] skills) {
        if (isHagglingComputerOnBoard()) {
            ++skills[SkillType.TRADER.castToInt()];
        }
    }

    @Override
    public void registerListener() {
        registerOperation(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        registerOperation(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
        registerOperation(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        registerOperation(ON_ARRESTED, this::onArrested);
        registerOperation(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        registerOperation(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        registerOperation(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
        registerOperation(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
        registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        registerOperation(ON_INCREMENT_DAYS, this::onIncrementDays);
        registerOperation(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        registerOperation(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);
        log.fine("registered");
    }

    @Override
    public String getCrewMemberName() {
        return CREW_MEMBER_NAME;
    }

    @Override
    public String getNewsTitle() {
        return NEWS;
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Devidia);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        getPhase(JarekGetsOut).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        int system;
        do {
            system = Functions.getRandom(Game.getCurrentGame().getUniverse().length);
        } while (Game.getStarSystem(system).getSpecialEventType() != SpecialEventType.NA);

        Game.getStarSystem(system).setSpecialEventType(SpecialEventType.ASSIGNED);
        getPhase(Jarek).setStarSystemId(Game.getStarSystem(system).getId());
        log.fine(getPhase(Jarek).getStarSystemId().toString());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        Game.getCurrentGame().getMercenaries().add(jarek);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        if (getPhase(Jarek).canBeExecuted()) {
            log.finest("phase #1 : " + Game.getCurrentSystemId() + " ~ " + getPhase(Jarek).getStarSystemId());
            showSpecialButton(object, getPhase(Jarek).getTitle());
        } else if (getPhase(JarekGetsOut).canBeExecuted()) {
            log.finest("phase #2 : " + Game.getCurrentSystemId() + " ~ " + getPhase(JarekGetsOut).getStarSystemId());
            showSpecialButton(object, getPhase(JarekGetsOut).getTitle());
        } else {
            log.fine("skipped : " + Game.getCurrentSystemId());
        }
    }

    private void onSpecialButtonClicked(Object object) {
        if (getPhase(Jarek).canBeExecuted()) {
            log.fine("phase #1");
            showDialogAndProcessResult(object, getPhase(Jarek).getDialog(), () -> {
                if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                } else {
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                    Game.getShip().hire(jarek);
                    jarekOnBoard = true;
                    questStatusJarek = STATUS_JAREK_STARTED;
                    setQuestState(QuestState.ACTIVE);
                    Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
                }
            });
        } else if (getPhase(JarekGetsOut).canBeExecuted()) {
            log.fine("phase #2");
            showDialogAndProcessResult(object, getPhase(JarekGetsOut).getDialog(), () -> {
                questStatusJarek = STATUS_JAREK_DONE;
                Game.getShip().fire(getSpecialCrewId());
                jarekOnBoard = false;
                shipBarCode = Game.getShip().getBarCode();
                setQuestState(QuestState.FINISHED);
                QuestsHolder.unSubscribeAll(getQuest());
            });
        } else {
            log.fine("skipped");
        }
    }


    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CHEATS_TITLE)) {
            questStatusJarek = Math.max(0, cheatWords.getNum2());
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CHEATS_TITLE, questStatusJarek);
    }

    @SuppressWarnings("unchecked")
    private void onDisplaySpecialCargo(Object object) {
        if (isHagglingComputerOnBoard()) {
            log.fine(SPECIAL_CARGO_TITLE);
            ((ArrayList<String>) object).add(SPECIAL_CARGO_TITLE);
        } else {
            log.fine("Don't show " + SPECIAL_CARGO_TITLE);
        }
    }

    // TODO repeat quest, or fail???
    private void onArrested(Object object) {
        if (jarekOnBoard) {
            log.fine("Arrested + Jarek");
            showAlert(ALERTS[AlertName.JarekTakenHome.ordinal()]);
            questStatusJarek = STATUS_JAREK_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Arrested w/o Jarek");
        }
    }

    // TODO repeat quest, or fail???
    private void onEscapeWithPod(Object object) {
        if (jarekOnBoard) {
            log.fine("Escaped + Jarek");
            showAlert(ALERTS[AlertName.JarekTakenHome.ordinal()]);
            questStatusJarek = STATUS_JAREK_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Escaped w/o Jarek");
        }
    }

    private void onIncrementDays(Object object) {
        if (jarekOnBoard) {
            log.fine(questStatusJarek + "");
            if (questStatusJarek == STATUS_JAREK_IMPATIENT / 2) {
                showAlert(ALERTS[AlertName.SpecialPassengerConcernedJarek.ordinal()]);
            } else if (questStatusJarek == STATUS_JAREK_IMPATIENT - 1) {
                showAlert(ALERTS[AlertName.SpecialPassengerImpatientJarek.ordinal()]);
                jarek.setPilot(0);
                jarek.setFighter(0);
                jarek.setTrader(0);
                jarek.setEngineer(0);
            }

            if (questStatusJarek < STATUS_JAREK_IMPATIENT) {
                questStatusJarek++;
            }
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (jarekOnBoard) {
            if (questStatusJarek == STATUS_JAREK_IMPATIENT) {
                ((ArrayList<String>) object).add(QUESTS[1]);
                log.fine(QUESTS[1]);
            } else {
                ((ArrayList<String>) object).add(QUESTS[0]);
                log.fine(QUESTS[0]);
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (jarekOnBoard && Game.isCurrentSystemIs(StarSystemId.Devidia)) {
            log.fine("" + getNewsId());
            Game.getCurrentGame().newsAddEvent(getNewsId());
        } else {
            log.fine("skipped");
        }
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    //new SpecialEvent(SpecialEventType.Jarek, 0, 1, false),
    class JarekPhase extends Phase {

        @Override
        public boolean canBeExecuted() {
            return isQuestIsActive()
                    && Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && Game.isCurrentSystemIs(getStarSystemId()) && !jarekOnBoard;
        }
    }

    //new SpecialEvent(SpecialEventType.JarekGetsOut, 0, 0, true),
    class JarekGetsOutPhase extends Phase {

        @Override
        public boolean canBeExecuted() {
            return isQuestIsActive() && jarekOnBoard && Game.isCurrentSystemIs(StarSystemId.Devidia);
        }
    }
    
    private Phase getPhase(JarekQuestStatus status) {
        return getPhase(status.ordinal());
    }
}

enum AlertName {
    SpecialPassengerConcernedJarek, SpecialPassengerImpatientJarek, JarekTakenHome
}

enum JarekQuestStatus {
    Jarek, JarekGetsOut
}
