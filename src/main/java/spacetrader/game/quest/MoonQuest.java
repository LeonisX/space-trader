package spacetrader.game.quest;

import spacetrader.controls.Rectangle;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.*;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.*;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

//TODO -princess
class MoonQuest extends AbstractQuest implements Serializable {

    static final long serialVersionUID = -4731305242511511L;

    // Constants
    private static final int STATUS_MOON_NOT_STARTED = 0;
    private static final int STATUS_MOON_BOUGHT = 1;
    private static final int STATUS_MOON_DONE = 2;

    public final static int MOON_COST = 500000;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private int questStatus = 0; // 0 = not bought, 1 = bought, 2 = claimed

    private int gameEndTypeId;

    public MoonQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new MoonPhase(), new MoonRetirementPhase());
        initializeTransitionMap();

        registerNews(News.values().length);

        gameEndTypeId = registerNewGameEndType();

        registerListener();

        //localize();
        dumpAllStrings();

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

        getTransitionMap().put(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

        getTransitionMap().put(ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS, this::onNewsAddEventFromNearestSystems);

        getTransitionMap().put(ON_BEFORE_GAME_END, this::onBeforeGameEnd);
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

    @Override
    public String getNewsTitle(int newsId) {
        return News.values()[getNewsIds().indexOf(newsId)].getValue();
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Utopia);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(QuestPhases.MoonRetirement).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.Moon).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onBeforeSpecialButtonShow(Object object) {
        phases.forEach((key, value) -> showSpecialButtonIfCanBeExecuted(object, value));
    }


    //TODO

    /*case Moon:
    show = game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_NOT_STARTED
                        && Game.getCommander().getWorth() > SpecialEvent.MOON_COST * .8;
                break;
            case MoonRetirement:
    show = game.getQuestStatusMoon() == SpecialEvent.STATUS_MOON_BOUGHT;
                break;*/

    //TODO
            /*new SpecialEvent(SpecialEventType.Moon, 500000, 4, false),
            new SpecialEvent(SpecialEventType.MoonRetirement, 0, 0, false),*/

    /*case Moon:
            GuiFacade.alert(AlertType.SpecialMoonBought);
    setQuestStatusMoon(SpecialEvent.STATUS_MOON_BOUGHT);
    confirmQuestPhase();
                break;
            case MoonRetirement:
    setQuestStatusMoon(SpecialEvent.STATUS_MOON_DONE);
                throw new GameEndException(BOUGHT_MOON.castToInt());*/


    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class MoonPhase extends Phase { //new SpecialEvent(SpecialEventType.Moon, 500000, 4, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem()
                    && Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful
                    && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage
                    && questStatus == STATUS_NOT_STARTED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.Princess);
            questStatus = STATUS_FLY_CENTAURI;
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "MoonPhase{} " + super.toString();
        }
    }

    /*class PrincessQonosPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessQonos, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() && questStatus == STATUS_PRINCESS_RESCUED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.PrincessQonos);
            //TODO where does the princess go? need to test this case.
            if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, princess.getName());
                Game.getShip().hire(princess);
                princessOnBoard = true;
                questStatus = STATUS_PRINCESS_RESCUED;
                game.getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            }
        }

        @Override
        public String toString() {
            return "PrincessQonosPhase{} " + super.toString();
        }
    }

    class PrincessReturnedPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessReturned, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return princessOnBoard && isDesiredSystem() && questStatus >= STATUS_PRINCESS_RESCUED
                    && questStatus <= STATUS_PRINCESS_IMPATIENT;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.PrincessReturned);
            questStatus = STATUS_PRINCESS_RETURNED;
            removePassenger();
        }

        @Override
        public String toString() {
            return "PrincessReturnedPhase{} " + super.toString();
        }
    }*/

    class MoonRetirementPhase extends Phase { //new SpecialEvent(SpecialEventType.MoonRetirement, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() && questStatus == STATUS_PRINCESS_RETURNED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.PrincessQuantum);
            if (Game.getShip().getFreeWeaponSlots() == 0) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else {
                showAlert(Alerts.EquipmentQuantumDisruptor.getValue());
                Game.getShip().addEquipment(Consts.Weapons[WeaponType.QUANTUM_DISRUPTOR.castToInt()]);
                questStatus = STATUS_DONE;
                setQuestState(QuestState.FINISHED);
                game.getQuestSystem().unSubscribeAll(getQuest());
            }
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

    @SuppressWarnings("unchecked")
    private void onNewsAddEventFromNearestSystems(Object object) {
        NewsContainer newsContainer = (NewsContainer) object;
        if (isQuestIsActive() && phases.get(QuestPhases.Moon).isDesiredSystem(newsContainer.getStarSystem())) {
            newsContainer.getNews().add(News.MoonForSale.getValue());
        }
    }

    //TODO need to check this flow
    private void onBeforeGameEnd(Object object) {
        if (game.getEndStatus() == GameEndType.BOUGHT_MOON.castToInt() && questStatus >= STATUS_PRINCESS_RETURNED) {
            game.setEndStatus(gameEndTypeId);
        }
    }

    private void onGameEndAlert(Object object) {
        //TODO need to pass string value as image ID, and get image by this value
        new FormAlert(Alerts.GameEndBoughtMoon.getValue().getTitle(), GameEndType.QUEST.castToInt()).showDialog();
    }

    private void onGetGameScore(Object object) {
        ScoreContainer score = (ScoreContainer) object;
        if (score.getEndStatus() == 1) {
            score.setDaysMoon(Math.max(0, (Game.getDifficultyId() + 1) * 100 - Game.getCommander().getDays()));
            score.setModifier(100); //TODO 110????
        }

        //TODO
        /*switch (GameEndType.fromInt(getEndStatus())) {
        case BOUGHT_MOON:
        score.setDaysMoon(Math.max(0, (getDifficultyId() + 1) * 100 - commander.getDays()));
        score.setModifier(100);
        break;*/
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

    //TODO
    /*case GameEndBoughtMoon:
            return new FormAlert(AlertsGameEndBoughtMoonTitle, GameEndType.BOUGHT_MOON.castToInt());


            case GameEndRetired:
            return new FormAlert(AlertsGameEndRetiredTitle, GameEndType.RETIRED.castToInt());



            case GameRetire:
            return new FormAlert(AlertsGameRetireTitle, AlertsGameRetireMessage, AlertsYes, DialogResult.YES, AlertsNo,
                                 DialogResult.NO, args);



            case SpecialMoonBought:
            return new FormAlert(AlertsSpecialMoonBoughtTitle, AlertsSpecialMoonBoughtMessage, AlertsOk,
                                 DialogResult.OK, null, DialogResult.NONE, args);*/


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
                "} " + super.toString();
    }
}
