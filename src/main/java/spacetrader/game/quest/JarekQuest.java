package spacetrader.game.quest;

import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.SkillType;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.game.quest.enums.SimpleValueEnumWithPhase;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.stub.StringsBundle;
import spacetrader.util.Functions;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

class JarekQuest extends AbstractQuest {

    enum Phases implements SimpleValueEnumWithPhase<QuestDialog> {
        Jarek(new QuestDialog(DIALOG, "Ambassador Jarek", "A recent change in the political climate of this solar system has forced Ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?")),
        JarekGetsOut(new QuestDialog(ALERT, "Jarek Gets Out", "Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment."));

        private QuestDialog value;
        private Phase phase;
        Phases(QuestDialog value) { this.value = value; }
        @Override public QuestDialog getValue() { return value; }
        @Override public void setValue(QuestDialog value) { this.value = value; }
        @Override public Phase getPhase() { return phase; }
        @Override public void setPhase(Phase phase) {this.phase = phase; }
    }

    enum Quests implements SimpleValueEnum<String> {
        QuestJarek("Take ambassador Jarek to Devidia."),
        QuestJarekImpatient("Take ambassador Jarek to Devidia." + newline + "Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades.");

        private String value;
        Quests(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialPassengerConcernedJarek("Ship's Comm.", "Commander? Jarek here. Do you require any assistance in charting a course to Devidia?"),
        SpecialPassengerImpatientJarek("Ship's Comm.", "Captain! This is the Ambassador speaking. We should have been there by now?!"),
        JarekTakenHome("Jarek Taken Home", "The Space Corps decides to give ambassador Jarek a lift home to Devidia.");

        private AlertDialog value;
        Alerts(String title, String body) { this.value = new AlertDialog(title, body); }
        @Override public AlertDialog getValue() { return value; }
        @Override public void setValue(AlertDialog value) { this.value = value; }
    }

    enum News implements SimpleValueEnum<String> {
        AmbassadorJarekReturnsFromCrisis("Ambassador Jarek Returns from Crisis.");

        private String value;
        News(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum CrewNames implements SimpleValueEnum<String> {
        Jarek("Jarek");

        private String value;
        CrewNames(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum SpecialCargo implements SimpleValueEnum<String> {
        HagglingComputer("A haggling computer.");
        private String value;
        SpecialCargo(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum CheatTitles implements SimpleValueEnum<String> {
        Jarek("Jarek");
        private String value;
        CheatTitles(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    // Constants
    //TODO switch to phases
    private final static int STATUS_JAREK_NOT_STARTED = 0;
    private final static int STATUS_JAREK_STARTED = 1;
    private final static int STATUS_JAREK_IMPATIENT = 11;
    private final static int STATUS_JAREK_DONE = 12;

    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;
    private static final int CASH_TO_SPEND = 0;

    private int questStatusJarek = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private CrewMember jarek;
    private boolean jarekOnBoard;

    private UUID shipBarCode = UUID.randomUUID();

    public JarekQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);

        initializePhases(Phases.values(), new JarekPhase(), new JarekGetsOutPhase());
        initializeTransitionMap();

        jarek = registerNewSpecialCrewMember(3, 2, 10, 4);

        registerNews(1);

        registerListener();

        //TODO remove later
        //localize();
        dumpAllStrings();
        log.fine("started...");
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();
        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
        getTransitionMap().put(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);
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
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public String getCrewMemberName(int id) {
        return CrewNames.values()[0].getValue();
    }

    @Override
    public void dumpAllStrings() {
        System.out.println("\n\n\n## Jarek Quest\n\n# Phases:");
        Arrays.stream(Phases.values()).forEach(q -> {
            System.out.println(q.name() + "Title=" + q.getValue().getTitle());
            System.out.println(q.name() + "Message=" + q.getValue().getMessage());
        });
        System.out.println("\n# Quests:");
        Arrays.stream(Quests.values()).forEach(q -> System.out.println(q.name() + "=" + q.getValue()));
        System.out.println("\n# Alerts:");
        Arrays.stream(Alerts.values()).forEach(q -> {
            System.out.println(q.name() + "Title=" + q.getValue().getTitle());
            System.out.println(q.name() + "Message=" + q.getValue().getMessage());
        });
        System.out.println("\n# News:");
        Arrays.stream(News.values()).forEach(q -> System.out.println(q.name() + "=" + q.getValue()));
        System.out.println("\n# Crew Member Names:");
        Arrays.stream(CrewNames.values()).forEach(q -> System.out.println(q.name() + "=" + q.getValue()));
        System.out.println("\n# Special Cargo Titles:");
        Arrays.stream(SpecialCargo.values()).forEach(q -> System.out.println(q.name() + "=" + q.getValue()));
        System.out.println("\n# Cheats Titles:");
        Arrays.stream(CheatTitles.values()).forEach(q -> System.out.println(q.name() + "=" + q.getValue()));
    }

    @Override
    public void localize() {
        StringsBundle strings = GlobalAssets.getStrings();

        Arrays.stream(Phases.values()).forEach(q ->
                q.setValue(new QuestDialog(q.getValue().getMessageType(), strings.get(q.name() + "Title"), strings.get(q.name() + "Message"))));
        Arrays.stream(CrewNames.values()).forEach(q -> q.setValue(strings.get(q.name())));
        Arrays.stream(SpecialCargo.values()).forEach(q -> q.setValue(strings.get(q.name())));
        Arrays.stream(CheatTitles.values()).forEach(q -> q.setValue(strings.get(q.name())));
        Arrays.stream(Quests.values()).forEach(q -> q.setValue(strings.get(q.name())));
        Arrays.stream(Alerts.values()).forEach(q ->
                q.setValue(new AlertDialog(strings.get(q.name() + "Title"), strings.get(q.name() + "Message"))));
    }

    @Override
    public String getNewsTitle(int newsId) {
        return News.values()[newsId].getValue();
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Devidia);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        Phases.JarekGetsOut.getPhase().setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        int system;
        do {
            system = Functions.getRandom(Game.getCurrentGame().getUniverse().length);
        } while (Game.getStarSystem(system).getSpecialEventType() != SpecialEventType.NA);

        Game.getStarSystem(system).setSpecialEventType(SpecialEventType.ASSIGNED);
        Phases.Jarek.getPhase().setStarSystemId(Game.getStarSystem(system).getId());
        log.fine(Phases.Jarek.getPhase().getStarSystemId().toString());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        Game.getCurrentGame().getMercenaries().put(jarek.getId(), jarek);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class JarekPhase extends Phase { //new SpecialEvent(SpecialEventType.Jarek, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && !jarekOnBoard && isDesiredSystem();
        }

        @Override
        public String toString() {
            return "JarekPhase{} " + super.toString();
        }
    }

    class JarekGetsOutPhase extends Phase { //new SpecialEvent(SpecialEventType.JarekGetsOut, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return jarekOnBoard && isDesiredSystem();
        }

        @Override
        public String toString() {
            return "JarekGetsOutPhase{} " + super.toString();
        }
    }

    private void onSpecialButtonClicked(Object object) {
        if (Phases.Jarek.getPhase().canBeExecuted()) {
            log.fine("phase #1");
            showDialogAndProcessResult(object, Phases.Jarek.getValue(), () -> {
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
        } else if (Phases.JarekGetsOut.getPhase().canBeExecuted() && isQuestIsActive()) {
            log.fine("phase #2");
            showDialogAndProcessResult(object, Phases.JarekGetsOut.getValue(), () -> {
                questStatusJarek = STATUS_JAREK_DONE;
                Game.getShip().fire(jarek.getId());
                jarekOnBoard = false;
                shipBarCode = Game.getShip().getBarCode();
                setQuestState(QuestState.FINISHED);
                game.getQuestSystem().unSubscribeAll(getQuest());
            });
        } else {
            log.fine("skipped");
        }
    }


    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Jarek.getValue())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Jarek.getValue(), questStatusJarek);
    }

    @SuppressWarnings("unchecked")
    private void onDisplaySpecialCargo(Object object) {
        if (isHagglingComputerOnBoard()) {
            log.fine(SpecialCargo.HagglingComputer.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.HagglingComputer.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.HagglingComputer.getValue());
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onArrested(Object object) {
        if (jarekOnBoard) {
            log.fine("Arrested + Jarek");
            showAlert(Alerts.JarekTakenHome.getValue());
            questStatusJarek = STATUS_JAREK_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Arrested w/o Jarek");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onEscapeWithPod(Object object) {
        if (jarekOnBoard) {
            log.fine("Escaped + Jarek");
            showAlert(Alerts.JarekTakenHome.getValue());
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
                showAlert(Alerts.SpecialPassengerConcernedJarek.getValue());
            } else if (questStatusJarek == STATUS_JAREK_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientJarek.getValue());
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
                ((ArrayList<String>) object).add(Quests.QuestJarekImpatient.getValue());
                log.fine(Quests.QuestJarekImpatient.getValue());
            } else {
                ((ArrayList<String>) object).add(Quests.QuestJarek.getValue());
                log.fine(Quests.QuestJarek.getValue());
            }
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

    @Override
    public String toString() {
        return "JarekQuest{" +
                "questStatusJarek=" + questStatusJarek +
                ", jarek=" + jarek +
                ", jarekOnBoard=" + jarekOnBoard +
                ", shipBarCode=" + shipBarCode +
                "} " + super.toString();
    }
}
