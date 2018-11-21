package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.enums.CrewMemberId;
import spacetrader.game.enums.SpaceTraderEnum;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.util.List;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.EventName.*;



//TODO - internal method
// public boolean isJarekOnBoard() {
//        return hasCrew(CrewMemberId.JAREK);
//    }

// TODO Special button click
// handleSpecialEvent()
// case Jarek:
//                if (commander.getShip().getFreeCrewQuartersCount() == 0) {
//                    GuiFacade.alert(AlertType.SpecialNoQuarters);
//                    remove = false;
//                } else {
//                    CrewMember jarek = getMercenaries()[CrewMemberId.JAREK.castToInt()];
//                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
//                    commander.getShip().hire(jarek);
//                    setQuestStatusJarek(SpecialEvent.STATUS_JAREK_STARTED);
//                }
//                break;
//            case JarekGetsOut:
//                setQuestStatusJarek(SpecialEvent.STATUS_JAREK_DONE);
//                commander.getShip().fire(CrewMemberId.JAREK);
// TODO end quest
//                break;

// TODO On increment days
// if (commander.getShip().isJarekOnBoard()) {
//            if (getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_IMPATIENT / 2) {
//                GuiFacade.alert(AlertType.SpecialPassengerConcernedJarek);
//            } else if (getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_IMPATIENT - 1) {
//                GuiFacade.alert(AlertType.SpecialPassengerImpatientJarek);
//                getMercenaries()[CrewMemberId.JAREK.castToInt()].setPilot(0);
//                getMercenaries()[CrewMemberId.JAREK.castToInt()].setFighter(0);
//                getMercenaries()[CrewMemberId.JAREK.castToInt()].setTrader(0);
//                getMercenaries()[CrewMemberId.JAREK.castToInt()].setEngineer(0);
//            }
//
//            if (getQuestStatusJarek() < SpecialEvent.STATUS_JAREK_IMPATIENT) {
//                setQuestStatusJarek(getQuestStatusJarek() + 1);
//            }
//        }

// TODO ON_NEWS_ADD_EVENT_ON_ARRIVAL
// case JarekGetsOut:
//                    if (commander.getShip().isJarekOnBoard()) {
//                        newsAddEvent(NewsEvent.JarekGetsOut);
//                    }
//                    break;


//TODO Arrested
// if (commander.getShip().isJarekOnBoard()) {
//            GuiFacade.alert(AlertType.JarekTakenHome);
//            setQuestStatusJarek(SpecialEvent.STATUS_JAREK_NOT_STARTED);
//        }

// TODO Escaped with pod
// if (commander.getShip().isJarekOnBoard()) {
//            GuiFacade.alert(AlertType.JarekTakenHome);
//            setQuestStatusJarek(SpecialEvent.STATUS_JAREK_NOT_STARTED);
//        }

// TODO IS_CONSIDER_CHEAT
// case Status: {
//                    switch (SomeStringsForCheatSwitch.find(second)) {
//                        case Jarek:
//                            game.setQuestStatusJarek(Math.max(0, num2));
//                            break;
// default:
// + Strings.CheatsJarek + ": " + game.getQuestStatusJarek() + Strings.newline

// ON_DISPLAY_SPECIAL_CARGO
// if (game.getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_DONE) {
//            specialCargo.add(Strings.SpecialCargoJarek);
//        }

// TODO - simply increment TRADER skill if STATUS_JAREK_DONE:
// return getSkills()[SkillType.TRADER.castToInt()] + (isHagglingComputerOnBoard() ? 1 : 0);
//
// private boolean isHagglingComputerOnBoard() {
//        return isCommandersShip() && Game.getCurrentGame().getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_DONE;
//    }

// TODO ON_GET_QUESTS_STRINGS
// if (game.getCommander().getShip().isJarekOnBoard()) {
//            if (game.getQuestStatusJarek() == SpecialEvent.STATUS_JAREK_IMPATIENT) {
//                quests.add(Strings.QuestJarekImpatient);
//            } else {
//                quests.add(Strings.QuestJarek);
//            }
//        }

// ALERTS


// case SpecialPassengerConcernedJarek:
//                return new FormAlert(AlertsSpecialPassengerConcernedJarekTitle,
//                        AlertsSpecialPassengerConcernedJarekMessage, AlertsOk,
//                        DialogResult.OK, null, DialogResult.NONE, args);


// case SpecialPassengerImpatientJarek:
//                return new FormAlert(AlertsSpecialPassengerImpatientJarekTitle,
//                        AlertsSpecialPassengerImpatientJarekMessage, AlertsOk,
//                        DialogResult.OK, null, DialogResult.NONE, args);


// case JarekTakenHome:
//                return new FormAlert(AlertsJarekTakenHomeTitle, AlertsJarekTakenHomeMessage, AlertsOk, DialogResult.OK,
//                        null, DialogResult.NONE, args);



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
    //private static final SpecialEventType TYPE = SpecialEventType.Lottery;
    private static final int CASH_TO_SPEND = 0;
    private static final boolean MESSAGE_ONLY = false;

    private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private QuestStatus questStatus = QuestStatus.INACTIVE;
    private QuestState questState = QuestState.INACTIVE;

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
            registerOperation(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        }

        @Override
        public String getMessageTitle() {
            return MESSAGE_TITLES[0];
        }

        @Override
        public String getMessageBody() {
            return MESSAGE_BODIES[0];
        }

        private void onAssignEventsManual(Object object) {
            StarSystem starSystem = Game.getCurrentGame().getUniverse()[StarSystemId.Devidia.castToInt()];
            starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
            getPhases().get(1).setStarSystemId(starSystem.getId());

            unRegisterOperation(ON_ASSIGN_EVENTS_MANUAL);
            registerOperation(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
        }

        private void onAssignEventsRandomly(Object object) {
            int system;
            do {
                system = Functions.getRandom(Game.getCurrentGame().getUniverse().length);
            } while (Game.getCurrentGame().getUniverse()[system].getSpecialEventType() != SpecialEventType.NA);

            //getUniverse()[system].setSpecialEventType(Consts.SpecialEvents[i].getType());
            Game.getCurrentGame().getUniverse()[system].setSpecialEventType(SpecialEventType.ASSIGNED);
            setStarSystemId(Game.getCurrentGame().getUniverse()[system].getId());

            unRegisterOperation(ON_ASSIGN_EVENTS_RANDOMLY);
            registerOperation(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
        }

        private void onGenerateCrewMemberList(Object object) {
            Game.getCurrentGame().getMercenaries().add(new CrewMember(CrewMemberId.SPECIAL, getSpecialCrewId(), 3, 2, 10, 4, StarSystemId.NA));
            unRegisterOperation(ON_GENERATE_CREW_MEMBER_LIST);
            //registerOperation(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
        }

        //TODO remove or rewrite

        private void onBeforeSpecialButtonShow(Object object) {
            // TODO before SpecialButton show
// showSpecialButton() {

/*
//planet id with jarek
case Jarek:
        show = game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
        break;


        case JarekGetsOut:
        show = game.getCommander().getShip().isJarekOnBoard();
        break;  */
            if (Game.getCurrentGame().getCurrentSystemId().equals(getStarSystemId())) {
                if (!((Button) object).isVisible()) {
                    ((Button) object).setVisible(true);
                    ((Button) object).asJButton().setToolTipText(getMessageTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            specialButtonClick(object, this);

            // TODO Special button click
// handleSpecialEvent()
// case Jarek:
//                if (commander.getShip().getFreeCrewQuartersCount() == 0) {
//                    GuiFacade.alert(AlertType.SpecialNoQuarters);
//                    remove = false;
//                } else {
//                    CrewMember jarek = getMercenaries()[CrewMemberId.JAREK.castToInt()];
//                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
//                    commander.getShip().hire(jarek);
//                    setQuestStatusJarek(SpecialEvent.STATUS_JAREK_STARTED);
//                }
//                break;
//            case JarekGetsOut:
//                setQuestStatusJarek(SpecialEvent.STATUS_JAREK_DONE);
//                commander.getShip().fire(CrewMemberId.JAREK);
// TODO end quest
//                break;

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
            //registerOperation(AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
        }

        @Override
        public String getMessageTitle() {
            return MESSAGE_TITLES[1];
        }

        @Override
        public String getMessageBody() {
            return MESSAGE_BODIES[1];
        }

        /*private void onAfterGameInitialize(Object object) {
            starSystemId = Game.getCurrentGame().getCurrentSystemId();
            unRegisterOperation(AFTER_GAME_INITIALIZE);
            registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        }

        private void onBeforeSpecialButtonShow(Object object) {
            if (Game.getCurrentGame().getCurrentSystemId().equals(getStarSystemId())) {
                if (!((Button) object).isVisible()) {
                    ((Button) object).setVisible(true);
                    ((Button) object).asJButton().setToolTipText(getMessageTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            specialButtonClick(object);
        }*/

    }
}
