package spacetrader.game.quest.quests;

import spacetrader.game.Commander;
import spacetrader.game.Ship;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.TradeItemType;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.NewsContainer;
import spacetrader.game.quest.enums.*;
import spacetrader.util.Functions;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.*;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class TribblesQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511507L;

    // Constants
    private static final int MaxTribbles = 100000;

    private static final Point[] coordinates = {new Point(16, 16), new Point(56, 8), new Point(96, 16),
            new Point(128, 8), new Point(176, 8), new Point(208, 16), new Point(8, 56),
            new Point(32, 80), new Point(88, 56), new Point(128, 40), new Point(192, 72),
            new Point(216, 48), new Point(8, 96), new Point(56, 96), new Point(96, 80),
            new Point(136, 88), new Point(176, 104), new Point(216, 96), new Point(16, 136),
            new Point(56, 128), new Point(96, 120), new Point(128, 128), new Point(168, 144),
            new Point(208, 128), new Point(8, 184), new Point(48, 176), new Point(88, 168),
            new Point(136, 176), new Point(184, 184), new Point(216, 176), new Point(16, 224),
            new Point(64, 216), new Point(96, 224), new Point(144, 216), new Point(176, 224),
            new Point(208, 216)
    };

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    //TODO remove in future, if don't need
    private static final int OCCURRENCE = 1;

    private boolean tribbleMessage = false; // Is true if the Ship Yard on the current system informed you about the tribbles

    private int tribbles = 0;

    public TribblesQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new TribblePhase(), new TribbleBuyerPhase());
        initializeTransitionMap();

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

        getTransitionMap().put(ON_ASSIGN_SYSTEM_EVENTS_RANDOMLY, this::onAssignEventsRandomly);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ON_FORM_SHIP_LIST_SHOW, this::onFormShipListShow);
        getTransitionMap().put(ON_GET_BASE_WORTH, this::onGetBaseWorth);

        getTransitionMap().put(ENCOUNTER_ON_TRIBBLE_PICTURE_CLICK, this::encounterOnTribblePictureClick);

        getTransitionMap().put(ON_ARRESTED_AND_SHIP_SOLD_FOR_DEBT, this::onArrestedAndShipSoldForDebt);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_FROM_NEAREST_SYSTEMS, this::onNewsAddEventFromNearestSystems);
        getTransitionMap().put(ON_ARRIVAL, this::onArrival);

        getTransitionMap().put(IS_CONSIDER_CHEAT, this::onIsConsiderCheat);
    }

    @Override
    public Collection<Phase> getPhases() {
        return phases.values();
    }

    @Override
    public Collection<QuestDialog> getQuestDialogs() {
        return phases.keySet().stream().map(QuestPhases::getValue).collect(Collectors.toList());
    }

    public List<Point> getCoordinates() {
        int toShow = min(coordinates.length,
                (int) sqrt(tribbles / ceil(MaxTribbles / pow(coordinates.length + 1, 2))));

        if (toShow == 0) {
            return Collections.emptyList();
        }

        List<Point> list = new ArrayList<>(Arrays.asList(coordinates));
        Collections.shuffle(list);

        return list.subList(0, toShow - 1);
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsRandomly(Object object) {
        for (int i = 0; i < phases.get(QuestPhases.Tribble).getOccurrence(); i++) {
            phases.get(QuestPhases.Tribble).setStarSystemId(occupyFreeSystemWithEvent());
        }
        for (int i = 0; i < phases.get(QuestPhases.TribbleBuyer).getOccurrence(); i++) {
            phases.get(QuestPhases.TribbleBuyer).setStarSystemId(occupyFreeSystemWithEvent());
        }
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class TribblePhase extends Phase { //new SpecialEvent(SpecialEventType.Tribble, 1000, 1, false),
        @Override
        public boolean canBeExecuted() {
            return tribbles == 0 && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            showAlert(Alerts.TribblesOwn.getValue());
            setTribbles(1);
            confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "TribblePhase{} " + super.toString();
        }
    }

    class TribbleBuyerPhase extends Phase { //            new SpecialEvent(SpecialEventType.TribbleBuyer, 0, 3, false),
        @Override
        public boolean canBeExecuted() {
            return tribbles > 0 && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");

            showAlert(Alerts.TribblesGone.getValue());
            getCommander().setCash(getCommander().getCash() + (tribbles / 2));
            setTribbles(0);
            confirmQuestPhase();
            setQuestState(QuestState.FINISHED);
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "TribbleBuyer{} " + super.toString();
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
        if (tribbles == MaxTribbles) {
            log.fine(SpecialCargo.TribblesInfestation.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.TribblesInfestation.getValue());
        } else if (tribbles == 1) {
            log.fine(SpecialCargo.TribbleCute.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.TribbleCute.getValue());
        } else if (tribbles > 0) {
            log.fine(SpecialCargo.TribblesCute.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.TribblesCute.getValue());
        } else {
            log.fine("Don't show tribbles");
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (tribbles > 0) {
            ((ArrayList<String>) object).add(QuestClues.Tribbles.getValue());
        } else {
            log.fine("skipped");
        }
    }

    private void onFormShipListShow(Object object) {
        if (tribbles > 0 && !tribbleMessage) {
            showAlert(Alerts.TribblesTradeIn.getValue());
            tribbleMessage = true;
        }
    }

    private void onGetBaseWorth(Object object) {
        if (tribbles > 0) {
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterOnTribblePictureClick(Object object) {
        showAlert(Alerts.TribblesSqueek.getValue());
    }

    private void onArrestedAndShipSoldForDebt(Object object) {
        if (tribbles > 0) {
            showAlert(Alerts.TribblesRemoved.getValue());
        }
    }

    private void onEscapeWithPod(Object object) {
        if (tribbles > 0) {
            log.fine("Escaped + Tribbles");
            showAlert(Alerts.TribblesKilled.getValue());
            failQuest();
        } else {
            log.fine("Escaped w/o Tribbles");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        setTribbles(0);
        setQuestState(QuestState.FAILED);
    }

    @SuppressWarnings("unchecked")
    private void onNewsAddEventFromNearestSystems(Object object) {
        NewsContainer newsContainer = (NewsContainer) object;
        if (isQuestIsActive() && phases.get(QuestPhases.TribbleBuyer).isDesiredSystem(newsContainer.getStarSystem())) {
            newsContainer.getNews().add(News.TribbleBuyer.getValue());
        }
    }

    // checkTribblesOnArrival
    private void onArrival(Object object) {
        Ship ship = getShip();
        Commander commander = getCommander();

        if (tribbles > 0) {
            int previousTribbles = tribbles;
            int narc = TradeItemType.NARCOTICS.castToInt();
            int food = TradeItemType.FOOD.castToInt();

            if (((ReactorQuest) game.getQuestSystem().getQuest(QuestName.Reactor)).isReactorOnBoard()) {
                if (tribbles < 20) {
                    showAlert(Alerts.TribblesAllDied.getValue());
                    failQuest();
                } else {
                    setTribbles(tribbles / 2);
                    showAlert(Alerts.TribblesHalfDied.getValue());
                }
            } else if (ship.getCargo()[narc] > 0) {
                int dead = Math.min(1 + Functions.getRandom(3), ship.getCargo()[narc]);
                commander.getPriceCargo()[narc]
                        = commander.getPriceCargo()[narc] * (ship.getCargo()[narc] - dead) / ship.getCargo()[narc];
                ship.getCargo()[narc] -= dead;
                ship.getCargo()[TradeItemType.FURS.castToInt()] += dead;
                setTribbles(tribbles - Math.min(dead * (Functions.getRandom(5) + 98), tribbles - 1));
                showAlert(Alerts.TribblesMostDied.getValue());
            } else {
                if (ship.getCargo()[food] > 0 && tribbles <= MaxTribbles) {
                    int eaten = ship.getCargo()[food] - Functions.getRandom(ship.getCargo()[food]);
                    commander.getPriceCargo()[food] -= commander.getPriceCargo()[food] * eaten / ship.getCargo()[food];
                    ship.getCargo()[food] -= eaten;
                    setTribbles(tribbles + (eaten * 100));
                    showAlert(Alerts.TribblesAteFood.getValue());
                }

                if (tribbles < MaxTribbles) {
                    int max = ship.getCargo()[food] > 0 ? tribbles : tribbles / 2;
                    int tribblessToAdd = (max > 0) ? Functions.getRandom(max) : 0;
                    setTribbles(tribbles + tribblessToAdd + 1);
                }

                if (tribbles > MaxTribbles) {
                    setTribbles(MaxTribbles);
                }

                if ((previousTribbles < 100 && tribbles >= 100)
                        || (previousTribbles < 1000 && tribbles >= 1000)
                        || (previousTribbles < 10000 && tribbles >= 10000)
                        || (previousTribbles < 50000 && tribbles >= 50000)
                        || (previousTribbles < MaxTribbles && tribbles == MaxTribbles)) {
                    String qty = (tribbles == MaxTribbles)
                            ? Encounters.TribbleDangerousNumber.getValue() : Functions.formatNumber(tribbles);
                    showAlert(Alerts.TribblesInspector.getValue(), qty);
                }
            }
            tribbleMessage = false;
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getFirst().equals(CheatTitles.Varmints.name())) {
            setTribbles(Math.max(0, cheatWords.getNum1()));
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    private void setTribbles(int tribbles) {
        log.fine("Tribbles: " + this.tribbles + " -> " + tribbles);
        this.tribbles = tribbles;
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Tribble(new QuestDialog(1000, 1, DIALOG, "Merchant Prince", "A merchant prince offers you a very special and wondrous item for the sum of 1000 credits. Do you accept?")),
        TribbleBuyer(new QuestDialog(0, 3, DIALOG, "Tribble Buyer", "An eccentric alien billionaire wants to buy your collection of tribbles and offers half a credit for each of them. Do you accept his offer?"));

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
        Tribbles("Get rid of those pesky tribbles.");

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
        TribblesOwn("A Tribble", "You are now the proud owner of a little, cute, furry tribble."),
        TribblesGone("No More Tribbles", "The alien uses his alien technology to beam over your whole collection of Tribbles to his ship."),
        TribblesMostDied("Most Tribbles Died", "You find that, instead of narcotics, some of your cargo bays contain only dead Tribbles!"),
        TribblesHalfDied("Half The Tribbles Died", "The radiation from the Ion Reactor seems to be deadly to Tribbles. Half the Tribbles on board died."),
        TribblesAllDied("All The Tribbles Died", "The radiation from the Ion Reactor is deadly to Tribbles. All of the Tribbles on board your ship have died."),
        TribblesSqueek("A Tribble", "Squeek!"),
        TribblesAteFood("Tribbles Ate Food", "You find that, instead of food, some of your cargo bays contain only Tribbles!"),
        TribblesKilled("Tribbles Killed", "Your Tribbles all died in the explosion."),
        TribblesRemoved("Tribbles Removed", "The Tribbles were sold with your ship."),
        TribblesInspector("Space Port Inspector", "Our scan reports you have ^1 Tribbles on board your ship. Tribbles are pests worse than locusts! You are running the risk of getting a hefty fine!"),
        TribblesTradeIn("You've Got Tribbles", "Hm. I see you got a Tribble infestation on your current ship. I'm sorry, but that severely reduces the trade-in price.");

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

    enum Encounters implements SimpleValueEnum<String> {
        TribbleDangerousNumber("a dangerous number of");

        private String value;

        Encounters(String value) {
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

    enum News implements SimpleValueEnum<String> {
        TribbleBuyer("Collector in ^1 System seeks to purchase Tribbles.");

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
        TribbleCute("Cute, furry tribble."),
        TribblesCute("Cute, furry tribbles."),
        TribblesInfestation("An infestation of tribbles.");

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
        Varmints(null);
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
        return "TribblesQuest{" +
                "tribbleMessage=" + tribbleMessage +
                ", tribbles=" + tribbles +
                "} " + super.toString();
    }
}
