package spacetrader.game.quest.quests;

import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.NewsContainer;
import spacetrader.game.quest.containers.ScoreContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.gui.FormAlert;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class MoonQuest extends AbstractQuest implements Serializable {

    static final long serialVersionUID = -4731305242511511L;

    // Constants
    private static final int STATUS_MOON_NOT_STARTED = 0;
    private static final int STATUS_MOON_BOUGHT = 1;
    private static final int STATUS_MOON_DONE = 2;

    private final static int MOON_COST = 500000;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private int questStatus = 0; // 0 = not bought, 1 = bought, 2 = claimed

    private int gameEndTypeId;

    private int imageIndex = 2;

    public MoonQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new MoonPhase(), new MoonRetirementPhase());
        initializeTransitionMap();

        registerNews(News.values().length);

        gameEndTypeId = registerNewGameEndType();

        registerListener();

        localize();

        log.fine("started...");
    }

    private void initializePhases(QuestPhases[] values, Phase... phases) {
        for (int i = 0; i < phases.length; i++) {
            this.phases.put(values[i], phases[i]);
            phases[i].setQuest(this);
            phases[i].setOccurrence(values[i].getValue().getOccurrence());
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

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ON_GET_WORTH, this::onGetWorth);

        getTransitionMap().put(ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS, this::onNewsAddEventFromNearestSystems);

        getTransitionMap().put(ON_GAME_END_ALERT, this::onGameEndAlert);
        getTransitionMap().put(ON_GET_GAME_SCORE, this::onGetGameScore);

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
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.GameEndings, Arrays.stream(GameEndings.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.GameEndings, Arrays.stream(GameEndings.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    int getEndTypeId() {
        return gameEndTypeId;
    }

    @Override
    public String getNewsTitle(int newsId) {
        return News.values()[getNewsIds().indexOf(newsId)].getValue();
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = getStarSystem(StarSystemId.Utopia);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.MoonRetirement).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        for (int i = 0; i < phases.get(QuestPhases.Moon).getOccurrence(); i++) {
            phases.get(QuestPhases.Moon).setStarSystemId(occupyFreeSystemWithEvent());
        }
    }

    private void onBeforeSpecialButtonShow(Object object) {
        phases.forEach((key, value) -> showSpecialButtonIfCanBeExecuted(object, value));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class MoonPhase extends Phase { //new SpecialEvent(SpecialEventType.Moon, 500000, 4, false),

        @Override
        public boolean canBeExecuted() {
            return questStatus == STATUS_MOON_NOT_STARTED && isDesiredSystem()
                    && getCommander().getWorth() > MOON_COST * .8;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.Moon);
            showAlert(Alerts.SpecialMoonBought.getValue());
            questStatus = STATUS_MOON_BOUGHT;
            confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "MoonPhase{} " + super.toString();
        }
    }

    class MoonRetirementPhase extends Phase { //new SpecialEvent(SpecialEventType.MoonRetirement, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_MOON_BOUGHT;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.MoonRetirement);

            questStatus = STATUS_MOON_DONE;
            setQuestState(QuestState.FINISHED);
            game.getQuestSystem().unSubscribeAll(getQuest());
            throw new GameEndException(gameEndTypeId);
        }

        @Override
        public String toString() {
            return "MoonRetirementPhase{} " + super.toString();
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
        if (questStatus == STATUS_MOON_BOUGHT) {
            ArrayList<String> questStrings = (ArrayList<String>) object;
            questStrings.add(QuestClues.Moon.getValue());
            log.fine(QuestClues.Moon.getValue());
        } else {
            log.fine("skipped");
        }
    }

    private void onGetWorth(Object object) {
        if (questStatus > STATUS_MOON_NOT_STARTED) {
            ((IntContainer) object).plus(MOON_COST);
        }
    }

    @SuppressWarnings("unchecked")
    private void onNewsAddEventFromNearestSystems(Object object) {
        NewsContainer newsContainer = (NewsContainer) object;
        if (!isQuestIsActive() && phases.get(QuestPhases.Moon).isDesiredSystem(newsContainer.getStarSystem())) {
            newsContainer.getNews().add(News.MoonForSale.getValue());
        }
    }

    private void onGameEndAlert(Object object) {
        if (game.getEndStatus() == gameEndTypeId) {
            new FormAlert(Alerts.GameEndBoughtMoon.getValue().getTitle(), imageIndex).showDialog();
        }
    }

    private void onGetGameScore(Object object) {
        if (game.getEndStatus() == gameEndTypeId) {
            ScoreContainer score = (ScoreContainer) object;
            if (score.getEndStatus() == 1) {
                score.setDaysMoon(Math.max(0, (getDifficultyId() + 1) * 100 - getCommander().getDays()));
                score.setModifier(100);
            }
        }
    }

    @Override
    public String getGameCompletionText() {
        return GameEndings.ClaimedMoon.getValue();
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Moon.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Moon.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Moon(new QuestDialog(500000, 4, DIALOG, "Moon For Sale", "There is a small but habitable moon for sale in the Utopia system, for the very reasonable sum of half a million credits. If you accept it, you can retire to it and live a peaceful, happy, and wealthy life. Do you wish to buy it?")),
        MoonRetirement(new QuestDialog(DIALOG, "Retirement", "Welcome to the Utopia system. Your own moon is available for you to retire to it, if you feel inclined to do that. Are you ready to retire and lead a happy, peaceful, and wealthy life?"));

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
        Moon("Claim your moon at Utopia.");

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
        SpecialMoonBought("Moon Bought", "You bought a moon in the Utopia system. Go there to claim it."),
        GameEndBoughtMoon("You Have Retired", "");

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
        MoonForSale("Seller in ^1 System has Utopian Moon available.");

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

    enum GameEndings implements SimpleValueEnum<String> {
        ClaimedMoon("Claimed moon");

        private String value;

        GameEndings(String value) {
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
        Moon("Moon");

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
        return "MoonQuest{" +
                "questStatus=" + questStatus +
                ", gameEndTypeId=" + gameEndTypeId +
                ", imageIndex=" + imageIndex +
                "} " + super.toString();
    }
}
