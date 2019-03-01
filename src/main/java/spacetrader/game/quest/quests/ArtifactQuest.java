package spacetrader.game.quest.quests;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.SurrenderContainer;
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

public class ArtifactQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511518L;

    // Constants
    private static final int STATUS_ARTIFACT_NOT_STARTED = 0;
    private static final int STATUS_ARTIFACT_ON_BOARD = 1;
    private static final int STATUS_ARTIFACT_DONE = 2;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private volatile int questStatus = 0; // 0 = not given yet, 1 = Artifact on board, 2 = Artifact no longer on board (either delivered or lost)

    public ArtifactQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new ArtifactPhase(), new ArtifactDeliveryPhase());
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

        getTransitionMap().put(ENCOUNTER_ON_VERIFY_SURRENDER, this::encounterOnVerifySurrender);
        getTransitionMap().put(ENCOUNTER_ON_ROBBERY, this::encounterOnRobbery);
        getTransitionMap().put(ENCOUNTER_GET_STEALABLE_CARGO, this::encounterGetStealableCargo);

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

    public boolean isArtifactOnBoard() {
        return questStatus == STATUS_ARTIFACT_ON_BOARD;
    }
    
    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        BooleanContainer goodUniverse = (BooleanContainer) object;
        // Find a Hi-Tech system without a special event for ArtifactDelivery.
        if (goodUniverse.getValue()) {
            Optional<StarSystem> freeHiTechSystem = Arrays.stream(getUniverse())
                    .filter(universe -> !universe.isQuestSystem()
                            && universe.getTechLevel() == TechLevel.HI_TECH).findAny();
            if (freeHiTechSystem.isPresent()) {
                freeHiTechSystem.get().setQuestSystem(true);
                phases.get(QuestPhases.ArtifactDelivery).setStarSystemId(freeHiTechSystem.get().getId());
            } else {
                goodUniverse.setValue(false);
            }
        }
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.Artifact).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class ArtifactPhase extends Phase { //new SpecialEvent(SpecialEventType.Artifact, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_ARTIFACT_NOT_STARTED
                    && getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious;
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            questStatus = STATUS_ARTIFACT_ON_BOARD;
            confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "ArtifactPhase{} " + super.toString();
        }
    }

    class ArtifactDeliveryPhase extends Phase { //new SpecialEvent(SpecialEventType.ArtifactDelivery, -20000, 0, true),
        @Override
        public boolean canBeExecuted() {
            return isArtifactOnBoard() && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            questStatus = STATUS_ARTIFACT_DONE;
            confirmQuestPhase();
            setQuestState(QuestState.FINISHED);
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "ArtifactDeliveryPhase{} " + super.toString();
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
        if (isArtifactOnBoard()) {
            log.fine(SpecialCargo.Artifact.getValue());
            ((List<String>) object).add(SpecialCargo.Artifact.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.Artifact.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (isArtifactOnBoard()) {
            ((ArrayList<String>) object).add(QuestClues.Artifact.getValue());
        } else {
            log.fine("skipped");
        }
    }

    private void encounterOnVerifySurrender(Object object) {
        SurrenderContainer surrenderContainer = (SurrenderContainer) object;
        if (!surrenderContainer.isMatch() && getOpponent().getType() == ShipType.MANTIS) {
            if (isArtifactOnBoard()) {
                if (showYesNoAlert(Alerts.EncounterAliensSurrender.getValue()) == DialogResult.YES) {
                    showAlert(Alerts.ArtifactRelinquished.getValue());
                    questStatus = STATUS_ARTIFACT_NOT_STARTED;

                    surrenderContainer.setResult(EncounterResult.NORMAL);
                }
            } else {
                GuiFacade.alert(AlertType.EncounterSurrenderRefused);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterOnRobbery(Object object) {
        if (!isArtifactOnBoard()) {
            return;
        }

        //TODO if too much stealable items - will be hidden cargo bays overflow
        if (getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            ((ArrayList<String>) object).add(Encounters.HideArtifact.getValue());
        } else {
            showAlert(Alerts.EncounterPiratesNotTakeArtifact.getValue());
        }
    }

    private void encounterGetStealableCargo(Object object) {
        if (isArtifactOnBoard()) {
            ((IntContainer) object).dec();
        }
    }

    private void onEscapeWithPod(Object object) {
        if (isArtifactOnBoard()) {
            log.fine("Lost Artifact");
            showAlert(Alerts.ArtifactLost.getValue());
            failQuest();
        } else {
            log.fine("Escaped w/o Artifact");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_ARTIFACT_DONE;
        setQuestState(QuestState.FAILED);
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (phases.get(QuestPhases.ArtifactDelivery).isDesiredSystem()) {
            int newsIndex = News.ArtifactDelivery.ordinal();
            log.fine("" + getNewsIdByIndex(newsIndex));
            addNewsByIndex(newsIndex);
        } else {
            log.fine("skipped");
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Artifact.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Artifact.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Artifact(new QuestDialog(DIALOG, "Alien Artifact", "This alien artifact should be delivered to professor Berger, who is currently traveling. You can probably find him at a hi-tech solar system. The alien race which produced this artifact seems keen on getting it back, however, and may hinder the carrier. Are you, for a price, willing to deliver it?")),
        ArtifactDelivery(new QuestDialog(-20000, ALERT, "Artifact Delivery", "This is professor Berger. I thank you for delivering the alien artifact to me. I hope the aliens weren't too much of a nuisance. I have transferred 20000 credits to your account, which I assume compensates for your troubles."));

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
        Artifact("Deliver the alien artifact to Professor Berger at some hi-tech system.");

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
        ArtifactLost("Artifact Lost", "The alien artifact has been lost in the wreckage of your ship."),
        EncounterAliensSurrender("Surrender", "If you surrender to the aliens, they will take the artifact. Are you sure you wish to do that?"),
        ArtifactRelinquished("Artifact Relinquished", "The aliens take the artifact from you."),
        EncounterPiratesNotTakeArtifact("Pirates left Artifact", "Pirates are very interested in an Alien Artifact. But since no one could say what it is, their leader forbade even to touch him so as not to pick up some unknown infection.");

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
        ArtifactDelivery("Scientist Adds Alien Artifact to Museum Collection.");

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

    enum Encounters implements SimpleValueEnum<String> {
        HideArtifact("the Alien Artifact");

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

    enum SpecialCargo implements SimpleValueEnum<String> {
        Artifact("An alien artifact.");
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
        Artifact("Artifact");
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
        return "ArtifactQuest{" +
                "questStatus=" + questStatus +
                "} " + super.toString();
    }
}
