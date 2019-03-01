package spacetrader.game.quest.quests;

import spacetrader.game.Consts;
import spacetrader.game.StarSystem;
import spacetrader.game.Strings;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.RandomEncounterContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class GemulonQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511513L;

    // Constants
    private static final int STATUS_GEMULON_NOT_STARTED = 0;
    private static final int STATUS_GEMULON_STARTED = 1;
    private static final int STATUS_GEMULON_DATE = 7;
    private static final int STATUS_GEMULON_TOO_LATE = 8;
    private static final int STATUS_GEMULON_FUEL = 9;
    private static final int STATUS_GEMULON_DONE = 10;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private volatile AtomicInteger questStatus = new AtomicInteger(0); // 0 = not given yet, 1-7 = days from start, 8 = too late, 9 = in time, 10 = done

    public GemulonQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new GemulonPhase(), new GemulonRescuedPhase(),
                new GemulonFuelPhase(), new GemulonInvadedPhase());
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

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

        getTransitionMap().put(ON_DETERMINE_RANDOM_ENCOUNTER, this::onDetermineRandomEncounter);

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
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = getStarSystem(StarSystemId.Gemulon);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.GemulonRescued).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.GemulonFuel).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.GemulonInvaded).setStarSystemId(starSystem.getId());
    }

    //TODO common method
    // Find the closest system at least 70 parsecs away from Gemulon that doesn't already have a special event.
    private void onAssignClosestEventsRandomly(Object object) {
        BooleanContainer goodUniverse = (BooleanContainer) object;
        if (!goodUniverse.getValue()) {
            return;
        }
        int systemId = game.isFindDistantSystem(StarSystemId.Gemulon);
        if (systemId < 0) {
            goodUniverse.setValue(false);
        } else {
            phases.get(QuestPhases.Gemulon).setStarSystemId(getStarSystem(systemId).getId());
        }
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class GemulonPhase extends Phase { //new SpecialEvent(SpecialEventType.Gemulon, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return questStatus.get() == STATUS_GEMULON_NOT_STARTED && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            questStatus.set(STATUS_GEMULON_STARTED);
            confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "GemulonPhase{} " + super.toString();
        }
    }

    class GemulonRescuedPhase extends Phase { //new SpecialEvent(SpecialEventType.GemulonRescued, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return questStatus.get() > STATUS_GEMULON_NOT_STARTED && questStatus.get() < STATUS_GEMULON_TOO_LATE && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            questStatus.set(STATUS_GEMULON_FUEL);
        }

        @Override
        public String toString() {
            return "GemulonRescuedPhase{} " + super.toString();
        }
    }

    class GemulonFuelPhase extends Phase { //new SpecialEvent(SpecialEventType.GemulonFuel, 0, 0, false),
        @Override
        public boolean canBeExecuted() {
            return questStatus.get() == STATUS_GEMULON_FUEL && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #3");
            if (getShip().getFreeGadgetSlots() == 0) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else {
                showAlert(Alerts.EquipmentFuelCompactor.getValue());
                getShip().addEquipment(Consts.Gadgets[GadgetType.FUEL_COMPACTOR.castToInt()]);
                questStatus.set(STATUS_GEMULON_DONE);
                setQuestState(QuestState.FINISHED);
                confirmQuestPhase();
                game.getQuestSystem().unSubscribeAll(getQuest());
            }
        }

        @Override
        public String toString() {
            return "GemulonFuelPhase{} " + super.toString();
        }
    }

    class GemulonInvadedPhase extends Phase { //new SpecialEvent(SpecialEventType.GemulonInvaded, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return questStatus.get() == STATUS_GEMULON_TOO_LATE && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #4");
            setQuestState(QuestState.FAILED);
            questStatus.set(STATUS_GEMULON_DONE);
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "GemulonInvadedPhase{} " + super.toString();
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
    private void onGetQuestsStrings(Object object) {
        ArrayList<String> questStrings = (ArrayList<String>) object;

        if (questStatus.get() > STATUS_GEMULON_NOT_STARTED && questStatus.get() < STATUS_GEMULON_DATE) {
            if (questStatus.get() == STATUS_GEMULON_DATE - 1) {
                questStrings.add(QuestClues.GemulonInformTomorrow.getValue());
            } else {
                questStrings.add(Functions.stringVars(QuestClues.GemulonInformDays.getValue(),
                        Functions.plural(STATUS_GEMULON_DATE - questStatus.get(), Strings.TimeUnit)));
            }
        } else if (questStatus.get() == STATUS_GEMULON_FUEL) {
            questStrings.add(QuestClues.GemulonFuel.getValue());
        } else {
            log.fine("skipped");
        }
    }

    private void onDetermineRandomEncounter(Object object) {
        if (game.getWarpSystem().getId() == StarSystemId.Gemulon && questStatus.get() == STATUS_GEMULON_TOO_LATE) {
            if (Functions.getRandom(10) > 4) {
                ((RandomEncounterContainer) object).setMantis(true);
            }
        }
    }

    private void onIncrementDays(Object object) {
        if (questStatus.get() > STATUS_GEMULON_NOT_STARTED && questStatus.get() < STATUS_GEMULON_TOO_LATE) {
            questStatus.set(Math.min(questStatus.get() + ((IntContainer) object).getValue(), STATUS_GEMULON_TOO_LATE));
            if (questStatus.get() == STATUS_GEMULON_TOO_LATE) {
                StarSystem gemulon = getStarSystem(StarSystemId.Gemulon);
                gemulon.setQuestSystem(true);
                gemulon.setTechLevel(TechLevel.PRE_AGRICULTURAL);
                gemulon.setPoliticalSystemType(PoliticalSystemType.ANARCHY);
            }
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        if (phases.get(QuestPhases.Gemulon).isDesiredSystem() && questStatus.get() < STATUS_GEMULON_TOO_LATE) {
            newsIndex = News.Gemulon.ordinal();
        } else if (phases.get(QuestPhases.GemulonRescued).isDesiredSystem()) {
            if (questStatus.get() > STATUS_GEMULON_NOT_STARTED && questStatus.get() < STATUS_GEMULON_TOO_LATE) {
                newsIndex = News.GemulonRescued.ordinal();
            } else if (questStatus.get() == STATUS_GEMULON_TOO_LATE || getQuestState() == QuestState.FAILED) {
                newsIndex = News.GemulonInvaded.ordinal();
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
        if (cheatWords.getSecond().equals(CheatTitles.Gemulon.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Gemulon.getValue(), questStatus.get());
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Gemulon(new QuestDialog(ALERT, "Alien Invasion", "We received word that aliens will invade Gemulon seven days from now. We know exactly at which coordinates they will arrive, but we can't warn Gemulon because an ion storm disturbs all forms of communication. We need someone, anyone, to deliver this info to Gemulon within six days.")),
        GemulonRescued(new QuestDialog(ALERT, "Gemulon Rescued", "This information of the arrival of the alien invasion force allows us to prepare a defense. You have saved our way of life. As a reward, we have a fuel compactor gadget for you, which will increase the travel distance by 3 parsecs for any ship. Return here to get it installed.")),
        GemulonFuel(new QuestDialog(DIALOG, "Fuel Compactor", "Do you wish us to install the fuel compactor on your current ship? (You need a free gadget slot)")),
        GemulonInvaded(new QuestDialog(ALERT, "Gemulon Invaded", "Alas, Gemulon has been invaded by aliens, which has thrown us back to pre-agricultural times. If only we had known the exact coordinates where they first arrived at our system, we might have prevented this tragedy from happening."));

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
        GemulonInformDays("Inform Gemulon about alien invasion within ^1."),
        GemulonInformTomorrow("Inform Gemulon about alien invasion by tomorrow."),
        GemulonFuel("Get your fuel compactor at Gemulon.");

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
        EquipmentFuelCompactor("Fuel Compactor", "You now have a fuel compactor installed on your ship.");

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
        Gemulon("Editorial: Who Will Warn Gemulon?"),
        GemulonRescued("Invasion Imminent! Plans in Place to Repel Hostile Invaders."),
        GemulonInvaded("Alien Invasion Devastates Planet!");

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

    enum CheatTitles implements SimpleValueEnum<String> {
        Gemulon("Gemulon");
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
        return "GemulonQuest{" +
                "questStatus=" + questStatus +
                "} " + super.toString();
    }
}
