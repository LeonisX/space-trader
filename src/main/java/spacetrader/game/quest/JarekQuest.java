package spacetrader.game.quest;

import spacetrader.controls.Button;
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

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.EventName.*;
import static spacetrader.game.quest.MessageType.ALERT;
import static spacetrader.game.quest.MessageType.DIALOG;

enum QuestStatus implements SpaceTraderEnum {

    INACTIVE, AMBASSADOR_JAREK, JAREK_JETS_OUT;

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}

enum AlertName {
    SpecialPassengerConcernedJarek, SpecialPassengerImpatientJarek, JarekTakenHome
}

class JarekQuest extends AbstractQuest {

    private final static int STATUS_JAREK_NOT_STARTED = 0;
    private final static int STATUS_JAREK_STARTED = 1;
    private final static int STATUS_JAREK_IMPATIENT = 11;
    private final static int STATUS_JAREK_DONE = 12;

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
    private static final boolean REPEATABLE = false;
    public static int OCCURRENCE = 1;
    private static final int CASH_TO_SPEND = 0;

    private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private QuestStatus questStatus = QuestStatus.INACTIVE;
    private QuestState questState = QuestState.INACTIVE;

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
    public void registerListener() {
        getPhases().get(0).registerListener();
        getPhases().get(1).registerListener();
    }

    @Override
    public String getCrewMemberName() {
        return CREW_MEMBER_NAME;
    }

    @Override
    public String getNewsTitle() {
        return NEWS;
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    //new SpecialEvent(SpecialEventType.Jarek, 0, 1, false),
    class FirstPhase extends Phase {

        @Override
        public String getTitle() {
            return DIALOGS[0].getTitle();
        }

        @Override
        public void registerListener() {
            registerOperation(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
            registerOperation(IS_CONSIDER_CHEAT, this::onIsConsiderCheat);
            registerOperation(IS_CONSIDER_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
            registerOperation(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        }

        private void onAssignEventsRandomly(Object object) {
            int system;
            do {
                system = Functions.getRandom(Game.getCurrentGame().getUniverse().length);
            } while (Game.getCurrentGame().getUniverse()[system].getSpecialEventType() != SpecialEventType.NA);

            Game.getCurrentGame().getUniverse()[system].setSpecialEventType(SpecialEventType.ASSIGNED);
            setStarSystemId(Game.getCurrentGame().getUniverse()[system].getId());

            registerOperation(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
        }

        private void onGenerateCrewMemberList(Object object) {
            Game.getCurrentGame().getMercenaries().add(jarek);
            unRegisterOperation(ON_GENERATE_CREW_MEMBER_LIST);
            registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        }


        private void onBeforeSpecialButtonShow(Object object) {
            if (Game.getCurrentGame().getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious &&
                    Game.getCurrentGame().getCurrentSystemId().equals(getStarSystemId())) {
                if (!((Button) object).isVisible()) {
                    ((Button) object).setVisible(true);
                    ((Button) object).asJButton().setToolTipText(getTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            showDialogAndProcessResult(object, DIALOGS[0], () -> {
                if (Game.getCurrentGame().getCommander().getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                } else {
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                    Game.getCurrentGame().getCommander().getShip().hire(jarek);
                    jarekOnBoard = true;
                    questStatusJarek = STATUS_JAREK_STARTED;
                    Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
                    registerOperation(ON_INCREMENT_DAYS, this::onIncrementDays);
                    registerOperation(ON_ARRESTED, this::onArrested);
                    registerOperation(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
                    registerOperation(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
                    unRegisterOperation(BEFORE_SPECIAL_BUTTON_SHOW);
                    unRegisterOperation(SPECIAL_BUTTON_CLICKED);
                }
            });
        }

        private void onIncrementDays(Object object) {
            if (jarekOnBoard) {
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
            }
        }

        private void onArrested(Object object) {
            if (jarekOnBoard) {
                showAlert(ALERTS[AlertName.JarekTakenHome.ordinal()]);
                questStatusJarek = STATUS_JAREK_NOT_STARTED;
            }
        }

        private void onEscapeWithPod(Object object) {
            if (jarekOnBoard) {
                showAlert(ALERTS[AlertName.JarekTakenHome.ordinal()]);
                questStatusJarek = STATUS_JAREK_NOT_STARTED;
            }
        }

        private void onIsConsiderCheat(Object object) {
            CheatWords cheatWords = (CheatWords) object;
            if (cheatWords.getFirst().equals("Status") && cheatWords.getSecond().equals("Jarek")) {
                questStatusJarek = Math.max(0, cheatWords.getNum2());
                cheatWords.setCheat(true);
            }
        }

        @SuppressWarnings("unchecked")
        private void onIsConsiderDefaultCheat(Object object) {
            Map<String, Integer> map = (Map<String, Integer>) object;
            map.put(CHEATS_TITLE, questStatusJarek);

        }

        @SuppressWarnings("unchecked")
        private void onDisplaySpecialCargo(Object object) {
            if (isHagglingComputerOnBoard()) {
                ((ArrayList<String>) object).add(SPECIAL_CARGO_TITLE);
            }
        }

        //TODO
        @SuppressWarnings("unchecked")
        private void onGetQuestsStrings(Object object) {
            if (jarekOnBoard) {
                if (questStatusJarek == STATUS_JAREK_IMPATIENT) {
                    ((ArrayList<String>) object).add(QUESTS[1]);
                } else {
                    ((ArrayList<String>) object).add(QUESTS[0]);
                }
            }
        }
    }

    //new SpecialEvent(SpecialEventType.JarekGetsOut, 0, 0, true),
    class SecondPhase extends Phase {

        @Override
        public String getTitle() {
            return DIALOGS[1].getTitle();
        }

        @Override
        public void registerListener() {
            registerOperation(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
            registerOperation(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);
        }

        private void onAssignEventsManual(Object object) {
            StarSystem starSystem = Game.getCurrentGame().getUniverse()[StarSystemId.Devidia.castToInt()];
            starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
            setStarSystemId(starSystem.getId());

            unRegisterOperation(ON_ASSIGN_EVENTS_MANUAL);
            registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        }

        private void onBeforeSpecialButtonShow(Object object) {
            if (jarekOnBoard) {
                if (!((Button) object).isVisible()) {
                    ((Button) object).setVisible(true);
                    ((Button) object).asJButton().setToolTipText(getTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            showDialogAndProcessResult(object, DIALOGS[0], () -> {
                questStatusJarek = STATUS_JAREK_DONE;
                Game.getCurrentGame().getCommander().getShip().fire(getSpecialCrewId());
                // TODO end quest
                jarekOnBoard = false;
                int[] skills = Game.getCurrentGame().getCommander().getSkills();
                skills[SkillType.TRADER.castToInt()]++;
                shipBarCode = Game.getCurrentGame().getCommander().getShip().getBarCode();
                Game.getCurrentGame().getCommander().setSkills(skills);
                unRegisterOperation(BEFORE_SPECIAL_BUTTON_SHOW);
                unRegisterOperation(SPECIAL_BUTTON_CLICKED);
                unRegisterOperation(ON_NEWS_ADD_EVENT_ON_ARRIVAL);
            });
        }

        private void onNewsAddEventOnArrival(Object object) {
            if (jarekOnBoard && Game.getCurrentGame().getCurrentSystemId() == StarSystemId.Devidia) {
                Game.getCurrentGame().newsAddEvent(getNewsId());
            }
        }
    }
}
