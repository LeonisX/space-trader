package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.enums.*;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.util.List;

import static spacetrader.game.Strings.AlertsOk;
import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.EventName.*;

enum QuestStatus implements SpaceTraderEnum {

    INACTIVE, AMBASSADOR_JAREK, JAREK_JETS_OUT;

    @Override
    public int castToInt() {
        return ordinal() - 1;
    }
}

class JarekQuest extends AbstractQuest {

    public final static int STATUS_JAREK_NOT_STARTED = 0;
    public final static int STATUS_JAREK_STARTED = 1;
    public final static int STATUS_JAREK_IMPATIENT = 11;
    public final static int STATUS_JAREK_DONE = 12;

    private static String CREW_MEMBER_NAME = "Jarek";     // Mercenary

    //TODO link
    private static String SPECIAL_CARGO_TITLE = "A haggling computer.";

    //TODO link
    private static String CHEATS_TITLE = "Jarek";

    //TODO link
    //news:
    private static String NEWS = "Ambassador Jarek Returns from Crisis.";

    private static String[] QUESTS = {
            "Take ambassador Jarek to Devidia.",
            //TODO optimize???
            "Take ambassador Jarek to Devidia." + newline
                    + "Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades."
    };

    private static String[] MESSAGE_TITLES = {
            "Ambassador Jarek",
            "Jarek Gets Out"
    };

    private static String[] MESSAGE_BODIES = {
            "A recent change in the political climate of this solar system has forced Ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?",
            "Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment."
    };

    private static String[] ALERT_TITLES = {
            "Ship's Comm.",     // AlertsSpecialPassengerConcernedJarekTitle
            "Ship's Comm.",     // AlertsSpecialPassengerImpatientJarekTitle
            "Jarek Taken Home"  // AlertsJarekTakenHomeTitle
    };

    private static String[] ALERT_BODIES = {
            "Commander? Jarek here. Do you require any assistance in charting a course to Devidia?",    // AlertsSpecialPassengerConcernedJarekMessage
            "Captain! This is the Ambassador speaking. We should have been there by now?!",             // AlertsSpecialPassengerImpatientJarekMessage
            "The Space Corps decides to give ambassador Jarek a lift home to Devidia."                  // AlertsJarekTakenHomeMessage
    };

    // Constants
    private static final boolean REPEATABLE = false;
    private static final int OCCURRENCE = 1;
    //private static final SpecialEventType TYPE = SpecialEventType.Jarek;
    private static final int CASH_TO_SPEND = 0;
    private static final boolean MESSAGE_ONLY = false;

    private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private QuestStatus questStatus = QuestStatus.INACTIVE;
    private QuestState questState = QuestState.INACTIVE;

    private CrewMember jarek = new CrewMember(CrewMemberId.SPECIAL, getSpecialCrewId(), 3, 2, 10, 4, StarSystemId.NA);
    private boolean jarekOnBoard;

    JarekQuest() {
        setQuest(this);
        repeatable = REPEATABLE;
        occurrence = OCCURRENCE;
        //this.type = TYPE;
        cashToSpend = CASH_TO_SPEND;
        messageOnly = MESSAGE_ONLY;

        List<Phase> phases = new ArrayList<>();
        phases.add(new JarekQuest.FirstPhase(cashToSpend, messageOnly));
        phases.add(new JarekQuest.SecondPhase(cashToSpend, messageOnly));
        setPhases(phases);

        setSpecialCrewId(QuestsHolder.generateSpecialCrewId());

        registerListener();
    }


    // Register listener
    public void registerListener() {
        getPhases().get(0).registerListener();
        getPhases().get(1).registerListener();
    }

    //TODO setters for translation
    @Override
    public String[] getMessageTitles() {
        return MESSAGE_TITLES;
    }

    @Override
    public String[] getMessageBodies() {
        return MESSAGE_BODIES;
    }

    @Override
    public String getCrewMemberName() {
        return CREW_MEMBER_NAME;
    }

    @Override
    public String getSpecialCargoTitle() {
        return SPECIAL_CARGO_TITLE;
    }

    @Override
    public String getNewsTitle() {
        return NEWS;
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    //new SpecialEvent(SpecialEventType.Jarek, 0, 1, false),
    class FirstPhase extends Phase {

        //TODO need???
        FirstPhase(int cashToSpend, boolean messageOnly) {
            super(cashToSpend, messageOnly);
        }

        @Override
        public String getTitle() {
            return MESSAGE_TITLES[0];
        }

        @Override
        public void registerListener() {
            registerOperation(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsRandomly);
        }

        @Override
        public String getMessageTitle() {
            return MESSAGE_TITLES[0];
        }

        @Override
        public String getMessageBody() {
            return MESSAGE_BODIES[0];
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

        private void onIncrementDays(Object object) {
            if (jarekOnBoard) {
                if (questStatusJarek == STATUS_JAREK_IMPATIENT / 2) {
                    specialPassengerConcernedJarek();
                } else if (questStatusJarek == STATUS_JAREK_IMPATIENT - 1) {
                    specialPassengerImpatientJarek();
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
                jarekTakenHome();
                questStatusJarek = STATUS_JAREK_NOT_STARTED;
            }
        }

        private void onEscapeWithPod(Object object) {
            if (jarekOnBoard) {
                jarekTakenHome();
                questStatusJarek = STATUS_JAREK_NOT_STARTED;
            }
        }

        private void onIsConsiderCheat(Object object) {
            if (jarekOnBoard) {
                jarekTakenHome();
                questStatusJarek = STATUS_JAREK_NOT_STARTED;
            }
        }

        private DialogResult specialPassengerConcernedJarek() {
            return new FormAlert(ALERT_TITLES[0],
                        ALERT_BODIES[0], AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, null).showDialog();
        }

        private DialogResult specialPassengerImpatientJarek() {
            return new FormAlert(ALERT_TITLES[1],
                        ALERT_BODIES[1], AlertsOk,
                        DialogResult.OK, null, DialogResult.NONE, null).showDialog();
        }

        private DialogResult jarekTakenHome() {
            return new FormAlert(ALERT_TITLES[2], ALERT_BODIES[2], AlertsOk, DialogResult.OK,
                        null, DialogResult.NONE, null).showDialog();
        }


// TODO IS_CONSIDER_CHEAT
// case Status: {
//                    switch (SomeStringsForCheatSwitch.find(second)) {
//                        case Jarek:
//                            game.setQuestStatusJarek(Math.max(0, num2));
//                            break;
// default:
// + Strings.CheatsJarek + ": " + game.getQuestStatusJarek() + Strings.newline

        private void onDisplaySpecialCargo(Object object) {
            if (questStatusJarek == STATUS_JAREK_DONE) {
                ((ArrayList<String>) object).add(SPECIAL_CARGO_TITLE);
            }
        }


        private void onGetQuestsStrings(Object object) {
            if (jarekOnBoard) {
                if (questStatusJarek == STATUS_JAREK_IMPATIENT) {
                    ((ArrayList<String>) object).add(QUESTS[1]);
                } else {
                    ((ArrayList<String>) object).add(QUESTS[0]);
                }
            }
        }

        private void onBeforeSpecialButtonShow(Object object) {
            if (Game.getCurrentGame().getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious &&
                    Game.getCurrentGame().getCurrentSystemId().equals(getStarSystemId())) {
                if (!((Button) object).isVisible()) {
                    ((Button) object).setVisible(true);
                    ((Button) object).asJButton().setToolTipText(getMessageTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            specialButtonClick(object, this);

            jarekOnBoard = true;

            if (Game.getCurrentGame().getCommander().getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                Game.getCurrentGame().getCommander().getShip().hire(jarek);
                //setQuestStatusJarek(SpecialEvent.STATUS_JAREK_STARTED);
                Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            }
        }
    }

    //new SpecialEvent(SpecialEventType.JarekGetsOut, 0, 0, true),
    class SecondPhase extends Phase {

        //TODO need???
        SecondPhase(int cashToSpend, boolean messageOnly) {
            super(cashToSpend, messageOnly);
        }

        @Override
        public String getTitle() {
            return MESSAGE_TITLES[1];
        }

        @Override
        public void registerListener() {
            registerOperation(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        }

        @Override
        public String getMessageTitle() {
            return MESSAGE_TITLES[1];
        }

        @Override
        public String getMessageBody() {
            return MESSAGE_BODIES[1];
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
                    ((Button) object).asJButton().setToolTipText(getMessageTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            specialButtonClick(object, this);
            questStatusJarek = STATUS_JAREK_DONE;
            Game.getCurrentGame().getCommander().getShip().fire(getSpecialCrewId());
            // TODO end quest
            jarekOnBoard = false;
            int[] skills = Game.getCurrentGame().getCommander().getSkills();
            skills[SkillType.TRADER.castToInt()]++;
            //TODO probably - increase skill if computer on board. After escape - loose it.
            Game.getCurrentGame().getCommander().setSkills(skills);
            //TODO for phase
            unRegisterAllOperations();
        }


        private void onNewsAddEventOnArrival(Object object) {
            if (jarekOnBoard) {
                Game.getCurrentGame().newsAddEvent(getNewsId());
            }
        }
    }
}
