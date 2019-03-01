package spacetrader.game.quest.quests;

import spacetrader.game.Consts;
import spacetrader.game.CrewMember;
import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.SkillType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.*;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class JarekQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511502L;

    // Constants
    private static final int STATUS_NOT_STARTED = 0;
    private static final int STATUS_JAREK_STARTED = 1;
    private static final int STATUS_JAREK_IMPATIENT = 11;
    private static final int STATUS_JAREK_DONE = 12;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private volatile AtomicInteger questStatus = new AtomicInteger(0); // 0 = not delivered, 1-11 = on board, 12 = delivered

    private CrewMember jarek; // Jarek, Ambassador Jarek earns his keep now - JAF.
    private boolean jarekOnBoard;

    private UUID shipBarCode = UUID.randomUUID();

    public JarekQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new JarekPhase(), new JarekGetsOutPhase());
        initializeTransitionMap();

        jarek = registerNewSpecialCrewMember(3, 2, 10, 4, false);

        registerNews(News.values().length);

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

        getTransitionMap().put(ON_ASSIGN_SYSTEM_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_ASSIGN_SYSTEM_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
        getTransitionMap().put(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);

        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
    }

    @Override
    public Collection<Phase> getPhases() {
        return phases.values();
    }

    @Override
    public Collection<QuestDialog> getQuestDialogs() {
        return phases.keySet().stream().map(QuestPhases::getValue).collect(Collectors.toList());
    }

    private boolean isHagglingComputerOnBoard() {
        return getShip().getBarCode() == shipBarCode && questStatus.get() == STATUS_JAREK_DONE;
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
        return CrewNames.values()[getSpecialCrewIds().indexOf(id)].getValue();
    }

    @Override
    public String getNewsTitle(int newsId) {
        return News.values()[getNewsIds().indexOf(newsId)].getValue();
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.dumpStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.localizeStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = getStarSystem(StarSystemId.Devidia);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.JarekGetsOut).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.Jarek).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        getMercenaries().put(jarek.getId(), jarek);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class JarekPhase extends Phase { //new SpecialEvent(SpecialEventType.Jarek, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && !jarekOnBoard && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            if (getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                getShip().hire(jarek);
                jarekOnBoard = true;
                questStatus.set(STATUS_JAREK_STARTED);
                setQuestState(QuestState.ACTIVE);
                confirmQuestPhase();
            }
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
        public void successFlow() {
            log.fine("phase #2");
            setQuestState(QuestState.FINISHED);
            questStatus.set(STATUS_JAREK_DONE);
            removePassenger();
            shipBarCode = getShip().getBarCode();
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "JarekGetsOutPhase{} " + super.toString();
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
        if (isHagglingComputerOnBoard()) {
            log.fine(SpecialCargo.HagglingComputer.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.HagglingComputer.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.HagglingComputer.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (jarekOnBoard) {
            if (questStatus.get() == STATUS_JAREK_IMPATIENT) {
                ((ArrayList<String>) object).add(QuestClues.JarekImpatient.getValue());
                log.fine(QuestClues.JarekImpatient.getValue());
            } else {
                ((ArrayList<String>) object).add(QuestClues.Jarek.getValue());
                log.fine(QuestClues.Jarek.getValue());
            }
        } else {
            log.fine("skipped");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onArrested(Object object) {
        if (jarekOnBoard) {
            log.fine("Arrested + Jarek");
            showAlert(Alerts.JarekTakenHome.getValue());
            failQuest();
        } else {
            log.fine("Arrested w/o Jarek");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onEscapeWithPod(Object object) {
        if (jarekOnBoard) {
            log.fine("Escaped + Jarek");
            showAlert(Alerts.JarekTakenHome.getValue());
            failQuest();
        } else {
            log.fine("Escaped w/o Jarek");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus.set(STATUS_NOT_STARTED);
        setQuestState(QuestState.FAILED);
        removePassenger();
    }

    private void removePassenger() {
        getShip().fire(jarek.getId());
        jarekOnBoard = false;
    }

    private void onIncrementDays(Object object) {
        if (jarekOnBoard) {
            log.fine(questStatus + "");
            if (questStatus.get() == STATUS_JAREK_IMPATIENT / 2) {
                showAlert(Alerts.SpecialPassengerConcernedJarek.getValue());
            } else if (questStatus.get() == STATUS_JAREK_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientJarek.getValue());
                jarek.setPilot(0);
                jarek.setFighter(0);
                jarek.setTrader(0);
                jarek.setEngineer(0);
            }

            if (questStatus.get() < STATUS_JAREK_IMPATIENT) {
                questStatus.getAndIncrement();
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (jarekOnBoard && isCurrentSystemIs(StarSystemId.Devidia)) {
            log.fine("" + getNewsIdByIndex(0));
            addNewsByIndex(0);
        } else {
            log.fine("skipped");
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Jarek.name())) {
            questStatus.set(Math.max(0, cheatWords.getNum2()));
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CheatTitles.Jarek.getValue(), questStatus.get());
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Jarek(new QuestDialog(DIALOG, "Ambassador Jarek", "A recent change in the political climate of this solar system has forced Ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?")),
        JarekGetsOut(new QuestDialog(ALERT, "Jarek Gets Out", "Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment."));

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
        Jarek("Take ambassador Jarek to Devidia."),
        JarekImpatient("Take ambassador Jarek to Devidia." + newline + "Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades.");

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

    enum CrewNames implements SimpleValueEnum<String> {
        Jarek("Jarek");

        private String value;

        CrewNames(String value) {
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
        HagglingComputer("A haggling computer.");
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
        Jarek("Jarek");
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
        return "JarekQuest{" +
                "questStatus=" + questStatus +
                ", jarek=" + jarek +
                ", jarekOnBoard=" + jarekOnBoard +
                ", shipBarCode=" + shipBarCode +
                "} " + super.toString();
    }
}
