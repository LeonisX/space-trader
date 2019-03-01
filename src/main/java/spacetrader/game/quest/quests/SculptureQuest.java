package spacetrader.game.quest.quests;

import spacetrader.game.Consts;
import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.EquipmentType;
import spacetrader.game.enums.GadgetType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.AlertDialog;
import spacetrader.game.quest.I18n;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.containers.BooleanContainer;
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

public class SculptureQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511505L;

    // Constants
    private static final int STATUS_SCULPTURE_NOT_STARTED = 0;
    private static final int STATUS_SCULPTURE_IN_TRANSIT = 1;
    private static final int STATUS_SCULPTURE_DELIVERED = 2;
    private static final int STATUS_SCULPTURE_DONE = 3;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private volatile int questStatus = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private boolean sculptureOnBoard;

    public SculptureQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new SculpturePhase(), new SculptureDeliveredPhase(), new SculptureHiddenBaysPhase());
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

        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

        getTransitionMap().put(ENCOUNTER_ON_ROBBERY, this::encounterOnRobbery);
        getTransitionMap().put(ENCOUNTER_GET_STEALABLE_CARGO, this::encounterGetStealableCargo);

        getTransitionMap().put(IS_ILLEGAL_SPECIAL_CARGO, this::onIsIllegalSpecialCargo);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS, this::onGetIllegalSpecialCargoActions);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION, this::onGetIllegalSpecialCargoDescription);
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
        StarSystem starSystem = getStarSystem(StarSystemId.Endor);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.SculptureDelivered).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.SculptureHiddenBays).setStarSystemId(starSystem.getId());
    }

    //TODO common method
    private void onAssignClosestEventsRandomly(Object object) {
        // Find the closest system at least 70 parsecs away from Endor that doesn't already have a special event.
        BooleanContainer goodUniverse = (BooleanContainer) object;
        if (!goodUniverse.getValue()) {
            return;
        }
        int systemId = game.isFindDistantSystem(StarSystemId.Endor);
        if (systemId < 0) {
            goodUniverse.setValue(false);
        } else {
            phases.get(QuestPhases.Sculpture).setStarSystemId(getStarSystem(systemId).getId());
        }
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class SculpturePhase extends Phase { //new SpecialEvent(SpecialEventType.Sculpture, -2000, 0, false),
        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_SCULPTURE_NOT_STARTED
                    && getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                    && getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");

            sculptureOnBoard = true;
            questStatus = STATUS_SCULPTURE_IN_TRANSIT;
            confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "SculpturePhase{} " + super.toString();
        }
    }

    class SculptureDeliveredPhase extends Phase { //new SpecialEvent(SpecialEventType.SculptureDelivered, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return sculptureOnBoard && isDesiredSystem() && questStatus == STATUS_SCULPTURE_IN_TRANSIT;
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");

            sculptureOnBoard = false;
            questStatus = STATUS_SCULPTURE_DELIVERED;
        }

        @Override
        public String toString() {
            return "SculptureDeliveredPhase{} " + super.toString();
        }
    }

    class SculptureHiddenBaysPhase extends Phase { //new SpecialEvent(SpecialEventType.SculptureHiddenBays, 0, 0, false),
        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_SCULPTURE_DELIVERED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #3");
            if (getShip().getFreeGadgetSlots() == 0) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else {
                showAlert(Alerts.EquipmentHiddenCompartments.getValue());
                getShip().addEquipment(Consts.Gadgets[GadgetType.HIDDEN_CARGO_BAYS.castToInt()]);
                confirmQuestPhase();
                questStatus = STATUS_SCULPTURE_DONE;
                setQuestState(QuestState.FINISHED);
                game.getQuestSystem().unSubscribeAll(getQuest());
            }
        }

        @Override
        public String toString() {
            return "SculptureHiddenBaysPhase{} " + super.toString();
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
        if (sculptureOnBoard) {
            log.fine(SpecialCargo.Sculpture.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.Sculpture.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.Sculpture.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (sculptureOnBoard) {
            ((ArrayList<String>) object).add(QuestClues.Sculpture.getValue());
        } else if (questStatus == STATUS_SCULPTURE_DELIVERED) {
            ((ArrayList<String>) object).add(QuestClues.SculptureHiddenBays.getValue());
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterOnRobbery(Object object) {
        if (!sculptureOnBoard) {
            return;
        }

        //TODO HOW I HAS GOT HIDDEN_CARGO_BAYS WO Sculpture???
        if (getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            ((ArrayList<String>) object).add(Encounters.HideSculpture.getValue());
        } else {
            showAlert(Alerts.EncounterPiratesTakeSculpture.getValue());
            failQuest();
        }
    }

    private void encounterGetStealableCargo(Object object) {
        if (sculptureOnBoard) {
            ((IntContainer) object).dec();
        }
    }

    private void onIsIllegalSpecialCargo(Object object) {
        if (sculptureOnBoard) {
            ((BooleanContainer) object).setValue(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoActions(Object object) {
        if (sculptureOnBoard) {
            ((ArrayList<String>) object).add(Encounters.PoliceSurrenderSculpture.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoDescription(Object object) {
        if (sculptureOnBoard) {
            ((ArrayList<String>) object).add(Encounters.PoliceSubmitSculpture.getValue());
        }
    }

    private void onArrested(Object object) {

        if (getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            log.fine("Arrested + Hidden Cargo Bays");
            while (getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
                getShip().removeEquipment(EquipmentType.GADGET, GadgetType.HIDDEN_CARGO_BAYS);
            }
            showAlert(Alerts.JailHiddenCargoBaysRemoved.getValue());
        } else {
            log.fine("Arrested w/o Hidden Cargo Bays");
        }


        if (sculptureOnBoard) {
            log.fine("Arrested + Sculpture");
            showAlert(Alerts.SculptureConfiscated.getValue());
            failQuest();
        } else {
            log.fine("Arrested w/o Sculpture");
        }
    }

    private void onEscapeWithPod(Object object) {
        if (sculptureOnBoard) {
            log.fine("Escaped + Sculpture");
            showAlert(Alerts.SculptureSaved.getValue());
        } else {
            log.fine("Escaped w/o Sculpture");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_SCULPTURE_NOT_STARTED;
        setQuestState(QuestState.FAILED);
        removePassenger();
    }

    private void removePassenger() {
        sculptureOnBoard = false;
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        if (phases.get(QuestPhases.Sculpture).isDesiredSystem()) {
            newsIndex = News.PricelessCollectorsItemWasStolen.ordinal();
        } else if (phases.get(QuestPhases.SculptureDelivered).isDesiredSystem()) {
            newsIndex = News.SpaceCorpsFollowsSculptureCaptors.ordinal();
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
        if (cheatWords.getSecond().equals(CheatTitles.Sculpture.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Sculpture.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Sculpture(new QuestDialog(-2000, DIALOG, "Stolen Sculpture", "A hooded figure approaches you and asks if you'd be willing to deliver some recently acquired merchandise to Endor. He's holding a small sculpture of a man holding some kind of light sword that you strongly suspect was stolen. It appears to be made of plastic and not very valuable. \"I'll pay you 2,000 credits now, plus 15,000 on delivery,\" the figure says. After seeing the look on your face he adds, \"It's a collector's item. Will you deliver it or not?\"")),
        SculptureDelivered(new QuestDialog(ALERT, "Sculpture Delivered", "Yet another dark, hooded figure approaches. \"Do you have the action fig- umm, the sculpture?\" You hand it over and hear what sounds very much like a giggle from under the hood. \"I know you were promised 15,000 credits on delivery, but I'm strapped for cash right now. However, I have something better for you. I have an acquaintance who can install hidden compartments in your ship.\" Return with an empty gadget slot when you're ready to have it installed.")),
        SculptureHiddenBays(new QuestDialog(DIALOG, "Install Hidden Compartments", "You're taken to a warehouse and whisked through the door. A grubby alien of some humanoid species - you're not sure which one - approaches. \"So you're the being who needs Hidden Compartments. Should I install them in your ship?\" (It requires a free gadget slot.)"));

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
        Sculpture("Deliver the stolen sculpture to Endor."),
        SculptureHiddenBays("Have hidden compartments installed at Endor.");

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
        EncounterPiratesTakeSculpture("Pirates Take Sculpture", "As the pirates ransack your ship, they find the stolen sculpture. \"This is worth thousands!\" one pirate exclaims, as he stuffs it into his pack."),
        EquipmentHiddenCompartments("Hidden Compartments", "You now have hidden compartments equivalent to 5 extra cargo bays installed in your ship. Police won't find illegal cargo hidden in these compartments."),
        JailHiddenCargoBaysRemoved("Hidden Compartments Removed", "When your ship is impounded, the police go over it with a fine-toothed comb. You hidden compartments are found and removed."),
        SculptureConfiscated("Police Confiscate Sculpture", "The Police confiscate the stolen sculpture and return it to its rightful owner."),
        SculptureSaved("Sculpture Saved", "On your way to the escape pod, you grab the stolen sculpture. Oh well, at least you saved something.");

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
        PricelessCollectorsItemWasStolen("Priceless collector's item stolen from home of Geurge Locas!"),
        SpaceCorpsFollowsSculptureCaptors("Space Corps follows ^3 with alleged stolen sculpture to ^2.");

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
        HideSculpture("the stolen sculpture"),
        PoliceSubmitSculpture("a stolen sculpture"),
        PoliceSurrenderSculpture("confiscate the sculpture");

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
        Sculpture("A stolen plastic sculpture of a man holding some kind of light sword.");
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
        Sculpture("Sculpture");
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

    boolean isSculptureOnBoard() {
        return sculptureOnBoard;
    }

    @Override
    public String toString() {
        return "SculptureQuest{" +
                "questStatus=" + questStatus +
                ", sculptureOnBoard=" + sculptureOnBoard +
                "} " + super.toString();
    }
}
