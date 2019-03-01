package spacetrader.game.quest.quests;

import spacetrader.game.Consts;
import spacetrader.game.StarSystem;
import spacetrader.game.Strings;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.util.Functions;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;

public class ExperimentQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511514L;

    // Constants
    private static final int STATUS_EXPERIMENT_NOT_STARTED = 0;
    private static final int STATUS_EXPERIMENT_STARTED = 1;
    private static final int STATUS_EXPERIMENT_DATE = 11;
    private static final int STATUS_EXPERIMENT_PERFORMED = 12;
    private static final int STATUS_EXPERIMENT_CANCELLED = 13;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private volatile AtomicInteger questStatus = new AtomicInteger(0); // 0 = not given yet, 1-11 = days from start; 12 = performed, 13 = cancelled

    public ExperimentQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new ExperimentPhase(), new ExperimentFailedPhase(),
                new ExperimentStoppedPhase());
        initializeTransitionMap();

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
        getTransitionMap().put(ON_ASSIGN_SYSTEM_CLOSEST_EVENTS_RANDOMLY, this::onAssignClosestEventsRandomly);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(IS_TRAVEL, this::isTravel);

        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

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
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public Collection<QuestDialog> getQuestDialogs() {
        return phases.keySet().stream().map(QuestPhases::getValue).collect(Collectors.toList());
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
        I18n.dumpStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = getStarSystem(StarSystemId.Daled);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.ExperimentStopped).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.ExperimentFailed).setStarSystemId(starSystem.getId());
    }

    //TODO common method
    // Find the closest system at least 70 parsecs away from Daled that doesn't already have a special event.
    private void onAssignClosestEventsRandomly(Object object) {
        BooleanContainer goodUniverse = (BooleanContainer) object;
        if (!goodUniverse.getValue()) {
            return;
        }
        int systemId = game.isFindDistantSystem(StarSystemId.Daled);
        if (systemId < 0) {
            goodUniverse.setValue(false);
        } else {
            phases.get(QuestPhases.Experiment).setStarSystemId(getStarSystem(systemId).getId());
        }
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class ExperimentPhase extends Phase { //new SpecialEvent(SpecialEventType.Experiment, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
            && questStatus.get() == STATUS_EXPERIMENT_NOT_STARTED && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            questStatus.set(STATUS_EXPERIMENT_STARTED);
            confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "ExperimentPhase{} " + super.toString();
        }
    }

    class ExperimentFailedPhase extends Phase { //new SpecialEvent(SpecialEventType.ExperimentFailed, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return questStatus.get() == STATUS_EXPERIMENT_PERFORMED && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            setQuestState(QuestState.FAILED);
            confirmQuestPhase();
            questStatus.set(STATUS_EXPERIMENT_NOT_STARTED);
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "ExperimentFailedPhase{} " + super.toString();
        }
    }

    class ExperimentStoppedPhase extends Phase { //new SpecialEvent(SpecialEventType.ExperimentStopped, 0, 0, true),*/
        @Override
        public boolean canBeExecuted() {
            return questStatus.get() > STATUS_EXPERIMENT_NOT_STARTED
                    && questStatus.get() < STATUS_EXPERIMENT_PERFORMED && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #3");

            questStatus.set(STATUS_EXPERIMENT_CANCELLED);
            game.setCanSuperWarp(true);
            setQuestState(QuestState.FINISHED);
            confirmQuestPhase();
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "ExperimentStoppedPhase{} " + super.toString();
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

    private void isTravel(Object object) {
        // if timespace is ripped, we may switch the warp system here.
        if (questStatus.get() == STATUS_EXPERIMENT_PERFORMED && game.getFabricRipProbability() > 0
                && (game.getFabricRipProbability() == Consts.FabricRipInitialProbability || Functions.getRandom(100) < game.getFabricRipProbability())) {
            showAlert(Alerts.TimespaceFabricRip.getValue());
            game.setSelectedSystemId(StarSystemId.fromInt(Functions.getRandom(getUniverse().length)));
        }
    }

    @SuppressWarnings("unchecked")
    private void onDisplaySpecialCargo(Object object) {
        if (game.getCanSuperWarp()) {
            ((ArrayList<String>) object).add(SpecialCargo.PortableSingularity.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.PortableSingularity.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        ArrayList<String> questStrings = (ArrayList<String>) object;

        if (questStatus.get() > STATUS_EXPERIMENT_NOT_STARTED && questStatus.get() < STATUS_EXPERIMENT_DATE) {
            if (questStatus.get() == STATUS_EXPERIMENT_DATE - 1) {
                questStrings.add(QuestClues.ExperimentInformTomorrow.getValue());
            } else {
                questStrings.add(Functions.stringVars(
                        QuestClues.ExperimentInformDays.getValue(),
                        Functions.plural(STATUS_EXPERIMENT_DATE - questStatus.get(), Strings.TimeUnitGen)));
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onIncrementDays(Object object) {
        if (questStatus.get() > STATUS_EXPERIMENT_NOT_STARTED && questStatus.get() < STATUS_EXPERIMENT_PERFORMED) {
            questStatus.set(Math.min(questStatus.get() + ((IntContainer) object).getValue(), STATUS_EXPERIMENT_PERFORMED));
            if (questStatus.get() == STATUS_EXPERIMENT_PERFORMED) {
                game.setFabricRipProbability(Consts.FabricRipInitialProbability);
                getStarSystem(StarSystemId.Daled).setQuestSystem(true);
                showAlert(Alerts.ExperimentPerformed.getValue());
                addNewsByIndex(News.ExperimentPerformed.ordinal());
            }
        } else if (questStatus.get() == STATUS_EXPERIMENT_PERFORMED  && game.getFabricRipProbability() > 0) {
            game.setFabricRipProbability(game.getFabricRipProbability() - ((IntContainer) object).getValue());
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        if (phases.get(QuestPhases.ExperimentFailed).isDesiredSystem()) {
            if (questStatus.get() == STATUS_EXPERIMENT_PERFORMED) {
                newsIndex = News.ExperimentFailed.ordinal();
            } else if (questStatus.get() > STATUS_EXPERIMENT_NOT_STARTED && questStatus.get() < STATUS_EXPERIMENT_PERFORMED) {
                newsIndex = News.ExperimentStopped.ordinal();
            }
        }

        if (newsIndex != null) {
            log.fine("" + getNewsIdByIndex(newsIndex));
            addNewsByIndex(newsIndex);
        } else {
            log.fine("skipped");
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Experiment.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Experiment.getValue(), questStatus.get());
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Experiment(new QuestDialog(ALERT, "Dangerous Experiment", "While reviewing the plans for Dr. Fehler's new space-warping drive, Dr. Lowenstam discovered a critical error. If you don't go to Daled and stop the experiment within ten days, the time-space continuum itself could be damaged!")),
        ExperimentFailed(new QuestDialog(ALERT, "Experiment Failed", "Dr. Fehler can't understand why the experiment failed. But the failure has had a dramatic and disastrous effect on the fabric of space-time itself. It seems that Dr. Fehler won't be getting tenure any time soon... and you may have trouble when you warp!")),
        ExperimentStopped(new QuestDialog(ALERT, "Disaster Averted", "Upon your warning, Dr. Fehler calls off the experiment. As your  reward, you are given a Portable Singularity. This device will, for one time only, instantaneously transport you to any system in the galaxy. The Singularity can be accessed  by clicking the \"J\" (Jump) button on the Galactic Chart."));

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
        ExperimentInformDays("Stop Dr. Fehler's experiment at Daled within ^1."),
        ExperimentInformTomorrow("Stop Dr. Fehler's experiment at Daled by tomorrow.");

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
        ExperimentPerformed("Experiment Performed", "The galaxy is abuzz with news of a terrible malfunction in Dr. Fehler's laboratory. Evidently, he was not warned in time and he performed his experiment... with disastrous results!"),
        TimespaceFabricRip("Timespace Fabric Rip", "You have flown through a tear in the timespace continuum caused by Dr. Fehler's failed experiment. You may not have reached your planned destination!");

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
        ExperimentFailed("Huge Explosion Reported at Research Facility."),
        ExperimentPerformed("Travelers Report Timespace Damage, Warp Problems!"),
        ExperimentStopped("Scientists cancel High-profile Test! Committee to Investigate Design.");

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

    enum SpecialCargo implements SimpleValueEnum<String> {
        PortableSingularity("A portable singularity.");
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
        Experiment("Experiment");
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
        return "ExperimentQuest{" +
                "questStatus=" + questStatus +
                "} " + super.toString();
    }
}
