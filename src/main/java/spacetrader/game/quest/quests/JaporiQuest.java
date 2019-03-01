package spacetrader.game.quest.quests;

import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;

import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class JaporiQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511512L;

    // Constants
    private static final int STATUS_JAPORI_NOT_STARTED = 0;
    private static final int STATUS_JAPORI_IN_TRANSIT = 1;
    private static final int STATUS_JAPORI_DONE = 2;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private volatile int questStatus = 0; // 0 = no disease, 1 = Go to Japori (always at least 10 medicine canisters), 2 = Assignment finished or canceled

    public JaporiQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new JaporiPhase(), new JaporiDeliveryPhase());
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
        getTransitionMap().put(ON_ASSIGN_SYSTEM_EVENTS_RANDOMLY, this::onAssignEventsRandomly);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ON_GET_FILLED_CARGO_BAYS, this::onGetFilledCargoBays);

        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);

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

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
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
        StarSystem starSystem = getStarSystem(StarSystemId.Japori);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.JaporiDelivery).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.Japori).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class JaporiPhase extends Phase { //new SpecialEvent(SpecialEventType.Japori, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return questStatus == STATUS_JAPORI_NOT_STARTED && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            // The japori quest should not be removed since you can fail and start it over again.
            if (getShip().getFreeCargoBays() < 10) {
                GuiFacade.alert(AlertType.CargoNoEmptyBays);
            } else {
                showAlert(Alerts.AntidoteOnBoard.getValue());
                questStatus = STATUS_JAPORI_IN_TRANSIT;
                confirmQuestPhase();
                setQuestState(QuestState.ACTIVE);
            }
        }

        @Override
        public String toString() {
            return "JaporiPhase{} " + super.toString();
        }
    }

    class JaporiDeliveryPhase extends Phase { //new SpecialEvent(SpecialEventType.JaporiDelivery, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return questStatus == STATUS_JAPORI_IN_TRANSIT && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            questStatus = STATUS_JAPORI_DONE;
            getCommander().increaseRandomSkill();
            getCommander().increaseRandomSkill();
            confirmQuestPhase();
            setQuestState(QuestState.FINISHED);
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "JaporiDeliveryPhase{} " + super.toString();
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
        if (questStatus == STATUS_JAPORI_IN_TRANSIT) {
            log.fine(SpecialCargo.Antidote.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.Antidote.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.Antidote.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (questStatus == STATUS_JAPORI_IN_TRANSIT) {
            ((ArrayList<String>) object).add(QuestClues.JaporiDeliver.getValue());
        } else {
            log.fine("skipped");
        }
    }

    private void onGetFilledCargoBays(Object object) {
        if (questStatus == STATUS_JAPORI_IN_TRANSIT) {
            ((IntContainer) object).plus(10);
        }
    }

    private void onArrested(Object object) {
        if (questStatus == STATUS_JAPORI_IN_TRANSIT) {
            log.fine("Arrested + Antidote");
            showAlert(Alerts.AntidoteTaken.getValue());
            failQuest();
        } else {
            log.fine("Arrested w/o Antidote");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_JAPORI_DONE;
        setQuestState(QuestState.FAILED);
    }

    private void onEscapeWithPod(Object object) {
        if (questStatus == STATUS_JAPORI_IN_TRANSIT) {
            log.fine("Escaped + Antidote");
            showAlert(Alerts.AntidoteDestroyed.getValue(), getStarSystem(phases.get(QuestPhases.Japori).getStarSystemId()).getName());
            // Second try
            questStatus = STATUS_JAPORI_NOT_STARTED;
            setQuestState(QuestState.INACTIVE);
            getStarSystem(phases.get(QuestPhases.Japori).getStarSystemId()).setQuestSystem(true);
        } else {
            log.fine("Escaped w/o Antidote");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        if (phases.get(QuestPhases.Japori).isDesiredSystem() && questStatus == STATUS_JAPORI_NOT_STARTED) {
            newsIndex = News.Japori.ordinal();
        } else if (isCurrentSystemIs(StarSystemId.Japori)) {
            switch (questStatus) {
                case STATUS_JAPORI_NOT_STARTED:
                    newsIndex = News.JaporiEpidemy.ordinal();
                    break;
                case STATUS_JAPORI_IN_TRANSIT:
                    newsIndex = News.JaporiDelivery.ordinal();
                    break;
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
        if (cheatWords.getSecond().equals(CheatTitles.Japori.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Japori.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Japori(new QuestDialog(DIALOG, "Japori Disease", "A strange disease has invaded the Japori system. We would like you to deliver these ten canisters of special antidote to Japori. Note that, if you accept, ten of your cargo bays will remain in use on your way to Japori. Do you accept this mission?")),
        JaporiDelivery(new QuestDialog(ALERT, "Medicine Delivery", "Thank you for delivering the medicine to us. We don't have any money to reward you, but we do have an alien fast-learning machine with which we will increase your skills."));

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
        JaporiDeliver("Deliver antidote to Japori.");

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
        AntidoteOnBoard("Antidote", "Ten of your cargo bays now contain antidote for the Japori system."),
        AntidoteDestroyed("Antidote Destroyed", "The antidote for the Japori system has been destroyed with your ship. You should return to ^1 and get some more."),
        AntidoteTaken("Antidote Taken", "The Space Corps removed the antidote for Japori from your ship and delivered it, fulfilling your assignment.");

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
        Japori("Editorial: We Must Help Japori!"),
        JaporiEpidemy("Unknown disease becomes epidemic."),
        JaporiDelivery("Disease Antidotes Arrive! Health Officials Optimistic.");

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
        Antidote("10 bays of antidote.");

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
        Japori("Japori");
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
        return "JaporiQuest{" +
                "questStatus=" + questStatus +
                "} " + super.toString();
    }
}
