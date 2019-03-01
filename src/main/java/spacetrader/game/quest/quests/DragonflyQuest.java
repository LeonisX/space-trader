package spacetrader.game.quest.quests;

import spacetrader.controls.Rectangle;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.StringContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class DragonflyQuest extends AbstractQuest implements Serializable {

    static final long serialVersionUID = -4731305242511516L;

    // Constants
    private static final int STATUS_DRAGONFLY_NOT_STARTED = 0;
    private static final int STATUS_DRAGONFLY_FLY_BARATAS = 1;
    private static final int STATUS_DRAGONFLY_FLY_MELINA = 2;
    private static final int STATUS_DRAGONFLY_FLY_REGULAS = 3;
    private static final int STATUS_DRAGONFLY_FLY_ZALKON = 4;
    private static final int STATUS_DRAGONFLY_DESTROYED = 5;
    private static final int STATUS_DRAGONFLY_DONE = 6;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private int questStatus = 0; // 0 = not available, 1 = Go to Baratas, 2 = Go to Melina, 3 = Go to Regulas,
    // 4 = Go to Zalkon, 5 = Dragonfly destroyed, 6 = Got Shield

    private CrewMember dragonflyCrew;

    private int shipSpecId;

    private Ship dragonfly; //ShipType.DRAGONFLY

    private boolean dragonflyDestroyed = false;

    private int dragonflyAttackEncounter; // DRAGONFLY_ATTACK
    private int dragonflyIgnoreEncounter; // DRAGONFLY_IGNORE

    private static final Rectangle SHIP_IMAGE_OFFSET = new Rectangle(21, 0, 22, 0); // Dragonfly
    private static final Integer SHIP_IMAGE_INDEX = 13;

    public DragonflyQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new DragonflyPhase(), new DragonflyBaratasPhase(), new DragonflyMelinaPhase(),
                new DragonflyRegulasPhase(), new DragonflyDestroyedPhase(), new DragonflyShieldPhase());
        initializeTransitionMap();

        int d = getDifficultyId();
        dragonflyCrew = registerNewSpecialCrewMember(4 + d, 6 + d, 1, 6 + d, false);

        dragonflyAttackEncounter = registerNewEncounter();
        dragonflyIgnoreEncounter = registerNewEncounter();

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
        getTransitionMap().put(ON_AFTER_SHIP_SPECS_INITIALIZED, this::afterShipSpecInitialized);
        getTransitionMap().put(ON_AFTER_CREATE_SHIP, this::onCreateShip);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

        getTransitionMap().put(ENCOUNTER_DETERMINE_NON_RANDOM_ENCOUNTER, this::encounterDetermineNonRandomEncounter);
        getTransitionMap().put(ENCOUNTER_VERIFY_ATTACK, this::encounterVerifyAttack);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_TEXT, this::encounterGetIntroductoryText);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_ACTION, this::encounterGetIntroductoryAction);
        getTransitionMap().put(ENCOUNTER_GET_IMAGE_INDEX, this::encounterGetEncounterImageIndex);
        getTransitionMap().put(ENCOUNTER_SHOW_ACTION_BUTTONS, this::encounterShowActionButtons);
        getTransitionMap().put(ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS, this::encounterGetExecuteActionFireShots);
        getTransitionMap().put(ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, this::encounterExecuteActionOpponentDisabled);
        getTransitionMap().put(ENCOUNTER_ON_ENCOUNTER_WON, this::encounterOnEncounterWon);

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
    public Rectangle getShipImageOffset() {
        return SHIP_IMAGE_OFFSET;
    }

    @Override
    public Integer getShipImageIndex() {
        return SHIP_IMAGE_INDEX;
    }

    @Override
    public String getShipName() {
        return CrewNames.Dragonfly.getValue();
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
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

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = getStarSystem(StarSystemId.Baratas);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.DragonflyBaratas).setStarSystemId(starSystem.getId());

        starSystem = getStarSystem(StarSystemId.Melina);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.DragonflyMelina).setStarSystemId(starSystem.getId());

        starSystem = getStarSystem(StarSystemId.Regulas);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.DragonflyRegulas).setStarSystemId(starSystem.getId());

        starSystem = getStarSystem(StarSystemId.Zalkon);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.DragonflyDestroyed).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.DragonflyShield).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.Dragonfly).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        getMercenaries().put(dragonflyCrew.getId(), dragonflyCrew);
    }

    private void afterShipSpecInitialized(Object object) {
        ShipSpec shipSpec = new ShipSpec(ShipType.QUEST, Size.SMALL, 0, 2, 3, 2, 1, 1, 1, 10, 1, 500000, 0, Activity.NA,
                Activity.NA, Activity.NA, TechLevel.UNAVAILABLE);
        shipSpecId = registerNewShipSpec(shipSpec);
    }

    private void onCreateShip(Object object) {
        dragonfly = game.createShipByShipSpecId(shipSpecId);
        dragonfly.getCrew()[0] = dragonflyCrew;
        dragonfly.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        dragonfly.addEquipment(Consts.Weapons[WeaponType.PULSE_LASER.castToInt()]);
        dragonfly.addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        dragonfly.addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        dragonfly.addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
        dragonfly.addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        dragonfly.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        phases.forEach((key, value) -> showSpecialButtonIfCanBeExecuted(object, value));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class DragonflyPhase extends Phase { //new SpecialEvent(SpecialEventType.Dragonfly, 0, 1, true),

        @Override
        public boolean canBeExecuted() {
            return getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && questStatus == STATUS_DRAGONFLY_NOT_STARTED && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.Dragonfly);
            questStatus++;
            confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "DragonflyPhase{} " + super.toString();
        }
    }

    class DragonflyBaratasPhase extends Phase { //new SpecialEvent(SpecialEventType.DragonflyBaratas, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return questStatus == STATUS_DRAGONFLY_FLY_BARATAS && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.DragonflyBaratas);
            questStatus++;
            confirmQuestPhase();
        }

        @Override
        public String toString() {
            return "DragonflyBaratasPhase{} " + super.toString();
        }
    }

    class DragonflyMelinaPhase extends Phase { //new SpecialEvent(SpecialEventType.DragonflyMelina, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return questStatus == STATUS_DRAGONFLY_FLY_MELINA && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.DragonflyMelina);
            questStatus++;
            confirmQuestPhase();
        }

        @Override
        public String toString() {
            return "DragonflyMelinaPhase{} " + super.toString();
        }
    }

    class DragonflyRegulasPhase extends Phase { //new SpecialEvent(SpecialEventType.DragonflyRegulas, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return questStatus == STATUS_DRAGONFLY_FLY_REGULAS && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.DragonflyRegulas);
            questStatus++;
            confirmQuestPhase();
        }

        @Override
        public String toString() {
            return "DragonflyRegulasPhase{} " + super.toString();
        }
    }

    class DragonflyDestroyedPhase extends Phase { //new SpecialEvent(SpecialEventType.DragonflyDestroyed, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return dragonflyDestroyed && questStatus == STATUS_DRAGONFLY_FLY_ZALKON && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.DragonflyDestroyed);
            questStatus = STATUS_DRAGONFLY_DESTROYED;
        }

        @Override
        public String toString() {
            return "DragonflyDestroyedPhase{} " + super.toString();
        }
    }

    class DragonflyShieldPhase extends Phase { //new SpecialEvent(SpecialEventType.DragonflyShield, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return dragonflyDestroyed && questStatus == STATUS_DRAGONFLY_DESTROYED && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.DragonflyShield);
            if (getShip().getFreeShieldSlots() == 0) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else {
                showAlert(Alerts.EquipmentLightningShield.getValue());
                getShip().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
                questStatus = STATUS_DRAGONFLY_DONE;
                setQuestState(QuestState.FINISHED);
                game.getQuestSystem().unSubscribeAll(getQuest());
            }
        }

        @Override
        public String toString() {
            return "DragonflyShieldPhase{} " + super.toString();
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
        String result = null;

        switch (questStatus) {
            case STATUS_DRAGONFLY_FLY_BARATAS:
                result = QuestClues.DragonflyBaratas.getValue();
                break;
            case STATUS_DRAGONFLY_FLY_MELINA:
                result = QuestClues.DragonflyMelina.getValue();
                break;
            case STATUS_DRAGONFLY_FLY_REGULAS:
                result = QuestClues.DragonflyRegulas.getValue();
                break;
            case STATUS_DRAGONFLY_FLY_ZALKON:
                result = QuestClues.DragonflyZalkon.getValue();
                break;
            case STATUS_DRAGONFLY_DESTROYED:
                result = QuestClues.DragonflyShield.getValue();
                break;
            default:
                log.fine("skipped");
        }

        if (result != null) {
            questStrings.add(result);
            log.fine(result);
        }
    }

    // Encounter with stolen Dragonfly
    private void encounterDetermineNonRandomEncounter(Object object) {
        if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Zalkon && questStatus == STATUS_DRAGONFLY_FLY_ZALKON) {
            setOpponent(dragonfly);
            getEncounter().setEncounterType(getShip().isCloaked() ? dragonflyIgnoreEncounter : dragonflyAttackEncounter);
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterVerifyAttack(Object object) {
        if (getEncounter().getEncounterType() == dragonflyIgnoreEncounter) {
            getEncounter().setEncounterType(dragonflyAttackEncounter);
        }
    }

    private void encounterGetIntroductoryText(Object object) {
        if (dragonfly.getBarCode() == getOpponent().getBarCode()) { // TODO opponentIsDragonfly()
            ((StringContainer) object).setValue(Encounters.PretextStolenDragonfly.getValue());
        }
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (dragonfly.getBarCode() == getOpponent().getBarCode()) {
            //TODO custom encounterType
            /*case QUEST_ATTACK:
                ((StringContainer) object).setValue(Strings.EncounterTextOpponentAttack);
                break;
            case QUEST_IGNORE:
                ((StringContainer) object).setValue(getCommander().getShip().isCloaked() ? Strings.EncounterTextOpponentNoNotice
                        : Strings.EncounterTextOpponentIgnore);
                break;*/
        }
    }

    private void encounterGetEncounterImageIndex(Object object) {
        if (dragonfly.getBarCode() == getOpponent().getBarCode()) {
            ((IntContainer) object).setValue(Consts.EncounterImgPirate);
        }
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == dragonflyAttackEncounter) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        List<Boolean> visible = (ArrayList<Boolean>) object;
        if (getEncounter().getEncounterType() == dragonflyAttackEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.FLEE.ordinal(), true);
        }
        if (getEncounter().getEncounterType() == dragonflyIgnoreEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }
    }

    private void encounterExecuteActionOpponentDisabled(Object object) {
        if (getOpponent().getBarCode() == dragonfly.getBarCode()) {
            encounterDefeatDragonfly();

            GuiFacade.alert(AlertType.EncounterDisabledOpponent, getEncounter().getEncounterShipText(), "");

            getCommander().setReputationScore(
                    getCommander().getReputationScore() + (getOpponent().getType().castToInt() / 2 + 1));
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterDefeatDragonfly() {
        getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
        //questStatus = STATUS_DRAGONFLY_DESTROYED;
        dragonflyDestroyed = true;
    }

    //TODO in future run this check in Encounter.encounterWon()
    private void encounterOnEncounterWon(Object object) {
        if (getEncounter().getEncounterType() == dragonflyAttackEncounter) { //DRAGONFLY_ATTACK
            encounterDefeatDragonfly();
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        switch (questStatus) {
            case STATUS_DRAGONFLY_NOT_STARTED:
                if (phases.get(QuestPhases.Dragonfly).isDesiredSystem()) {
                    newsIndex = News.Dragonfly.ordinal();
                }
                break;
            case STATUS_DRAGONFLY_FLY_BARATAS:
                if (phases.get(QuestPhases.DragonflyBaratas).isDesiredSystem()) {
                    newsIndex = News.DragonflyBaratas.ordinal();
                }
                break;
            case STATUS_DRAGONFLY_FLY_MELINA:
                if (phases.get(QuestPhases.DragonflyMelina).isDesiredSystem()) {
                    newsIndex = News.DragonflyMelina.ordinal();
                }
                break;
            case STATUS_DRAGONFLY_FLY_REGULAS:
                if (phases.get(QuestPhases.DragonflyRegulas).isDesiredSystem()) {
                    newsIndex = News.DragonflyRegulas.ordinal();
                }
                break;
            case STATUS_DRAGONFLY_FLY_ZALKON:
                if (phases.get(QuestPhases.DragonflyDestroyed).isDesiredSystem()) {
                    if (!dragonflyDestroyed) {
                        newsIndex = News.DragonflyZalkon.ordinal();
                    } else {
                        newsIndex = News.DragonflyDestroyed.ordinal();
                    }
                }
            default:
                log.fine("skipped");
        }

        if (newsIndex != null) {
            log.fine("" + getNewsIdByIndex(newsIndex));
            addNewsByIndex(newsIndex);
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Dragonfly.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Dragonfly.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Dragonfly(new QuestDialog(ALERT, "Dragonfly", "This is Colonel Jackson of the Space Corps. An experimental ship, code-named \"Dragonfly\", has been stolen. It is equipped with very special, almost indestructible shields. It shouldn't fall into the wrong hands and we will reward you if you destroy it. It has been last seen in the Baratas system.")),
        DragonflyBaratas(new QuestDialog(ALERT, "Weird Ship", "A small ship of a weird design docked here recently for repairs. The engineer who worked on it said that it had a weak hull, but incredibly strong shields. I heard it took off in the direction of the Melina system.")),
        DragonflyMelina(new QuestDialog(ALERT, "Lightning Ship", "A ship with shields that seemed to be like lightning recently fought many other ships in our system. I have never seen anything like it before. After it left, I heard it went to the Regulas system.")),
        DragonflyRegulas(new QuestDialog(ALERT, "Strange Ship", "A small ship with shields like I have never seen before was here a few days ago. It destroyed at least ten police ships! Last thing I heard was that it went to the Zalkon system.")),
        DragonflyDestroyed(new QuestDialog(ALERT, "Dragonfly Destroyed", "Hello, Commander. This is Colonel Jackson again. On behalf of the Space Corps, I thank you for your valuable assistance in destroying the Dragonfly. As a reward, we will install one of the experimental shields on your ship. Return here for that when you're ready.")),
        DragonflyShield(new QuestDialog(DIALOG, "Lightning Shield", "Colonel Jackson here. Do you want us to install a lightning shield on your current ship?"));

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
        DragonflyBaratas("Follow the Dragonfly to Baratas."),
        DragonflyMelina("Follow the Dragonfly to Melina."),
        DragonflyRegulas("Follow the Dragonfly to Regulas."),
        DragonflyZalkon("Follow the Dragonfly to Zalkon."),
        DragonflyShield("Get your lightning shield at Zalkon.");

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
        EquipmentLightningShield("Lightning Shield", "You now have one lightning shield installed on your ship.");

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
        Dragonfly("Experimental Craft Stolen! Critics Demand Security Review."),
        DragonflyBaratas("Investigators Report Strange Craft."),
        DragonflyMelina("Rumors Continue: Melina Orbitted by Odd Starcraft."),
        DragonflyRegulas("Strange Ship Observed in Regulas Orbit."),
        DragonflyZalkon("Unidentified Ship: A Threat to Zalkon?"),
        DragonflyDestroyed("Spectacular Display as Stolen Ship Destroyed in Fierce Space Battle.");

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
        PretextStolenDragonfly("a stolen ^1");

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

    enum CrewNames implements SimpleValueEnum<String> {
        Dragonfly("Dragonfly");

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

    enum CheatTitles implements SimpleValueEnum<String> {
        Dragonfly("Dragonfly");

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
        return "DragonflyQuest{" +
                "questStatus=" + questStatus +
                ", dragonflyCrew=" + dragonflyCrew +
                ", shipSpecId=" + shipSpecId +
                ", dragonfly=" + dragonfly +
                "} " + super.toString();
    }
}
