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

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.EventName.*;
import static spacetrader.game.quest.MessageType.ALERT;
import static spacetrader.game.quest.MessageType.DIALOG;

enum AlertName {
    SpecialPassengerConcernedJarek, SpecialPassengerImpatientJarek, JarekTakenHome
}

class JarekQuest extends AbstractQuest {

    private static final Logger log = Logger.getLogger(LotteryQuest.class.getName());

    private static String CREW_MEMBER_NAME = "Jarek";     // Mercenary

    private static String SPECIAL_CARGO_TITLE = "A haggling computer.";

    private static String CHEATS_TITLE = "Jarek";

    private static String NEWS = "Ambassador Jarek Returns from Crisis.";

    private static String[] QUESTS = {
            "Take ambassador Jarek to Devidia.",
            "Take ambassador Jarek to Devidia." + newline + "Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades."
    };

    private static QuestDialog[] DIALOGS = new QuestDialog[]{
            new QuestDialog(DIALOG, "Ambassador Jarek", "A recent change in the political climate of this solar system has forced Ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?"),
            new QuestDialog(ALERT, "Jarek Gets Out", "Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment.")
    };

    private static AlertDialog[] ALERTS = new AlertDialog[]{
            new AlertDialog("Ship's Comm.", "Commander? Jarek here. Do you require any assistance in charting a course to Devidia?"),
            new AlertDialog("Ship's Comm.", "Captain! This is the Ambassador speaking. We should have been there by now?!"),
            new AlertDialog("Jarek Taken Home", "The Space Corps decides to give ambassador Jarek a lift home to Devidia."),
    };

    // Constants
    private final static int STATUS_JAREK_NOT_STARTED = 0;
    private final static int STATUS_JAREK_STARTED = 1;
    private final static int STATUS_JAREK_IMPATIENT = 11;
    private final static int STATUS_JAREK_DONE = 12;

    private static final boolean REPEATABLE = false;
    private static final int OCCURRENCE = 1;
    private static final int CASH_TO_SPEND = 0;

    private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private CrewMember jarek = new CrewMember(CrewMemberId.SPECIAL, getSpecialCrewId(), 3, 2, 10, 4, StarSystemId.NA);
    private boolean jarekOnBoard;

    private UUID shipBarCode = UUID.randomUUID();

    public JarekQuest(Integer id) {
        setId(id);
        setQuest(this);
        repeatable = REPEATABLE;
        cashToSpend = CASH_TO_SPEND;

        List<Phase> phases = new ArrayList<>();
        phases.add(new JarekQuest.FirstPhase());
        phases.add(new JarekQuest.SecondPhase());
        setPhases(phases);

        setSpecialCrewId(jarek.getId());

        registerListener();
        log.fine("started...");
    }

    private boolean isHagglingComputerOnBoard() {
        return Game.getCurrentGame().getCommander().getShip().getBarCode() == shipBarCode && questStatusJarek == STATUS_JAREK_DONE;
    }

    @Override
    public void affectSkills(int[] skills) {
        if (isHagglingComputerOnBoard()) {
            ++skills[SkillType.TRADER.castToInt()];
        }
    }

    // Register listener
    private void registerListener() {
        getPhase(0).registerListener();
        //getPhases().get(1).registerListener();
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

    private void registerGlobalListeners() {
        registerOperation(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        registerOperation(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
        registerOperation(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        registerOperation(ON_ARRESTED, this::onArrested);
        registerOperation(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
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

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    //new SpecialEvent(SpecialEventType.Jarek, 0, 1, false),
    class FirstPhase extends Phase {

        //TODO may be in Quest???
        @Override
        public void registerListener() {
            registerGlobalListeners();
            registerOperation(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
            registerOperation(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
            registerOperation(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
            registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
            registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
            registerOperation(ON_INCREMENT_DAYS, this::onIncrementDays);
            registerOperation(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
            registerOperation(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);
        }

        @Override
        public String getTitle() {
            return DIALOGS[0].getTitle();
        }

        private void onAssignEventsManual(Object object) {
            log.fine("");
            StarSystem starSystem = Game.getCurrentGame().getUniverse()[StarSystemId.Devidia.castToInt()];
            starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
            getPhase(1).setStarSystemId(starSystem.getId());
        }

        private void onAssignEventsRandomly(Object object) {
            int system;
            do {
                system = Functions.getRandom(Game.getCurrentGame().getUniverse().length);
            } while (Game.getCurrentGame().getUniverse()[system].getSpecialEventType() != SpecialEventType.NA);

            Game.getCurrentGame().getUniverse()[system].setSpecialEventType(SpecialEventType.ASSIGNED);
            setStarSystemId(Game.getCurrentGame().getUniverse()[system].getId());
            log.fine(getStarSystemId().toString());
        }

        private void onGenerateCrewMemberList(Object object) {
            log.fine("");
            Game.getCurrentGame().getMercenaries().add(jarek);
        }

        private void onBeforeSpecialButtonShow(Object object) {
            log.finest(Game.getCurrentGame().getCurrentSystemId() + " ~ " + getStarSystemId());
            if (getPhase(0).canBeExecuted()) {
                log.fine("phase #1");
                showSpecialButton(object, DIALOGS[0].getTitle());
            } else if (getPhase(1).canBeExecuted()) {
                log.fine("phase #2");
                showSpecialButton(object, DIALOGS[1].getTitle());
            } else {
                log.fine("skipped");
            }
        }

        private void onSpecialButtonClicked(Object object) {
            if (getPhase(0).canBeExecuted()) {
                log.fine("phase #1");
                showDialogAndProcessResult(object, DIALOGS[0], () -> {
                    if (Game.getCurrentGame().getCommander().getShip().getFreeCrewQuartersCount() == 0) {
                        GuiFacade.alert(AlertType.SpecialNoQuarters);
                    } else {
                        GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                        Game.getCurrentGame().getCommander().getShip().hire(jarek);
                        jarekOnBoard = true;
                        questStatusJarek = STATUS_JAREK_STARTED;
                        setQuestState(QuestState.ACTIVE);
                        Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
                    }
                });
            } else if (getPhase(1).canBeExecuted()) {
                log.fine("phase #2");
                showDialogAndProcessResult(object, DIALOGS[1], () -> {
                    questStatusJarek = STATUS_JAREK_DONE;
                    Game.getCurrentGame().getCommander().getShip().fire(getSpecialCrewId());
                    jarekOnBoard = false;
                    shipBarCode = Game.getCurrentGame().getCommander().getShip().getBarCode();
                    setQuestState(QuestState.FINISHED);
                    QuestsHolder.unSubscribeAll(getQuest());
                });
            } else {
                log.fine("skipped");
            }
        }

        @Override
        public boolean canBeExecuted() {
            return (getQuestState() == QuestState.ACTIVE)
                    && Game.getCurrentGame().getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && Game.getCurrentGame().getCurrentSystemId().equals(getStarSystemId()) && !jarekOnBoard;
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

        //TODO
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
            if (jarekOnBoard && Game.getCurrentGame().getCurrentSystemId() == StarSystemId.Devidia) {
                log.fine("" + getNewsId());
                Game.getCurrentGame().newsAddEvent(getNewsId());
            } else {
                log.fine("skipped");
            }
        }
    }

    //new SpecialEvent(SpecialEventType.JarekGetsOut, 0, 0, true),
    class SecondPhase extends Phase {

        @Override
        public void registerListener() {
        }

        @Override
        public String getTitle() {
            return DIALOGS[1].getTitle();
        }
        
        @Override
        public boolean canBeExecuted() {
            return (getQuestState() == QuestState.ACTIVE) && 
                    jarekOnBoard && Game.getCurrentGame().getSelectedSystemId() == StarSystemId.Devidia;
        }
    }
}
