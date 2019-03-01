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

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;

public class SpaceMonsterQuest extends AbstractQuest implements Serializable {

    static final long serialVersionUID = -4731305242511508L;

    // Constants
    private static final int STATUS_SPACE_MONSTER_NOT_STARTED = 0;
    private static final int STATUS_SPACE_MONSTER_AT_ACAMAR = 1;
    private static final int STATUS_SPACE_MONSTER_DESTROYED = 2;
    private static final int STATUS_SPACE_MONSTER_DONE = 3;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private int questStatus = 0; // 0 = not available, 1 = Space monster is in Acamar system, 2 = Space monster is destroyed, 3 = Claimed reward

    private CrewMember spaceMonsterCrew;

    private int shipSpecId;

    private Ship spaceMonster; //ShipType.SPACE_MONSTER, //10

    private int spaceMonsterAttackEncounter; // SPACE_MONSTER_ATTACK
    private int spaceMonsterIgnoreEncounter; // SPACE_MONSTER_IGNORE

    private static final Rectangle SHIP_IMAGE_OFFSET = new Rectangle(7, 0, 49, 0);
    private static final Integer SHIP_IMAGE_INDEX = 15;

    public SpaceMonsterQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new SpaceMonsterPhase(), new SpaceMonsterKilledPhase());
        initializeTransitionMap();

        int d = getDifficultyId();
        spaceMonsterCrew = registerNewSpecialCrewMember(8 + d, 8 + d, 1, 1 + d, false);

        spaceMonsterAttackEncounter = registerNewEncounter();
        spaceMonsterIgnoreEncounter = registerNewEncounter();

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
        getTransitionMap().put(ENCOUNTER_GET_IMAGE_INDEX, this::encounterGetEncounterImageIndex);
        getTransitionMap().put(ENCOUNTER_SHOW_ACTION_BUTTONS, this::encounterShowActionButtons);
        getTransitionMap().put(ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS, this::encounterGetExecuteActionFireShots);
        getTransitionMap().put(ENCOUNTER_IS_DISABLEABLE, this::encounterIsDisableable);
        getTransitionMap().put(ENCOUNTER_ON_ENCOUNTER_WON, this::encounterOnEncounterWon);

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
    public Rectangle getShipImageOffset() {
        return SHIP_IMAGE_OFFSET;
    }

    @Override
    public Integer getShipImageIndex() {
        return SHIP_IMAGE_INDEX;
    }

    @Override
    public String getShipName() {
        return CrewNames.SpaceMonster.getValue();
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
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
        StarSystem starSystem = getStarSystem(StarSystemId.Acamar);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.SpaceMonsterKilled).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.SpaceMonster).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        getMercenaries().put(spaceMonsterCrew.getId(), spaceMonsterCrew);
}

    private void afterShipSpecInitialized(Object object) {
        ShipSpec shipSpec = new ShipSpec(ShipType.QUEST, Size.HUGE, 0, 3, 0, 0, 1, 1, 1, 500, 1, 500000, 0, Activity.NA,
                Activity.NA, Activity.NA, TechLevel.UNAVAILABLE);

        shipSpecId = registerNewShipSpec(shipSpec);
    }

    private void onCreateShip(Object object) {
        spaceMonster = game.createShipByShipSpecId(shipSpecId);
        spaceMonster.getCrew()[0] = spaceMonsterCrew;
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        spaceMonster.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        phases.forEach((key, value) -> showSpecialButtonIfCanBeExecuted(object, value));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class SpaceMonsterPhase extends Phase { //new SpecialEvent(SpecialEventType.SpaceMonster, 0, 1, true),

        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_SPACE_MONSTER_NOT_STARTED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.SpaceMonster);
            questStatus = STATUS_SPACE_MONSTER_AT_ACAMAR;
            setQuestState(QuestState.ACTIVE);
            confirmQuestPhase();
        }

        @Override
        public String toString() {
            return "SpaceMonsterPhase{} " + super.toString();
        }
    }

    class SpaceMonsterKilledPhase extends Phase { //new SpecialEvent(SpecialEventType.SpaceMonsterKilled, -15000, 0, true),

        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_SPACE_MONSTER_DESTROYED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.SpaceMonsterKilled);

            questStatus = STATUS_SPACE_MONSTER_DONE;
            setQuestState(QuestState.FINISHED);
            confirmQuestPhase();
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "SpaceMonsterKilledPhase{} " + super.toString();
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
        if (questStatus == STATUS_SPACE_MONSTER_AT_ACAMAR) {
            questStrings.add(QuestClues.SpaceMonsterKill.getValue());
            log.fine(QuestClues.SpaceMonsterKill.getValue());
        } else if (questStatus == STATUS_SPACE_MONSTER_DESTROYED) {
            questStrings.add(QuestClues.SpaceMonsterReward.getValue());
            log.fine(QuestClues.SpaceMonsterReward.getValue());
        } else {
            log.fine("skipped");
        }
    }

    // Encounter with space monster
    private void encounterDetermineNonRandomEncounter(Object object) {
        if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Acamar && questStatus == STATUS_SPACE_MONSTER_AT_ACAMAR) {
            setOpponent(spaceMonster);
            getEncounter().setEncounterType(getShip().isCloaked() ? spaceMonsterIgnoreEncounter : spaceMonsterAttackEncounter);
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterVerifyAttack(Object object) {
        if (getEncounter().getEncounterType() == spaceMonsterIgnoreEncounter) {
            getEncounter().setEncounterType(spaceMonsterAttackEncounter);
        }
    }

    private void encounterGetIntroductoryText(Object object) {
        if (opponentIsSpaceMonster()) {
            ((StringContainer) object).setValue(Encounters.PretextSpaceMonster.getValue());
        }
    }

    private void encounterGetEncounterImageIndex(Object object) {
        if (opponentIsSpaceMonster()) {
            ((IntContainer) object).setValue(Consts.EncounterImgAlien);
        }
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == spaceMonsterAttackEncounter) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        List<Boolean> visible = (ArrayList<Boolean>) object;
        if (getEncounter().getEncounterType() == spaceMonsterAttackEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.FLEE.ordinal(), true);
        }
        if (getEncounter().getEncounterType() == spaceMonsterIgnoreEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }
    }

    private void encounterIsDisableable(Object object) {
        if (opponentIsSpaceMonster()) {
            ((BooleanContainer) object).setValue(false);
        }
    }

    private void encounterOnEncounterWon(Object object) {
        if (getEncounter().getEncounterType() == spaceMonsterAttackEncounter) { //SPACE_MONSTER_ATTACK
            getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
            getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
            questStatus = STATUS_SPACE_MONSTER_DESTROYED;
        }
    }

    private void onIncrementDays(Object object) {
        // The Space Monster's strength increases 5% per day until it is back to full strength.
        if (spaceMonster.getHull() < spaceMonster.getHullStrength()) {
            IntContainer numContainer = (IntContainer) object;
            spaceMonster.setHull(
                    Math.min(spaceMonster.getHullStrength(), (int) (spaceMonster.getHull() * Math.pow(1.05, numContainer.getValue()))));
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        if (isCurrentSystemIs(StarSystemId.Acamar)) {
            if (questStatus == STATUS_SPACE_MONSTER_AT_ACAMAR) {
                newsIndex = News.SpaceMonster.ordinal();
            } else if (questStatus >= STATUS_SPACE_MONSTER_DESTROYED) {
                newsIndex = News.SpaceMonsterKilled.ordinal();
            }
        }

        if (newsIndex != null) {
            log.fine("" + getNewsIdByIndex(newsIndex));
            addNewsByIndex(newsIndex);
        } else {
            log.fine("skipped");
        }
    }

    private boolean opponentIsSpaceMonster() {
        return spaceMonster.getBarCode() == getOpponent().getBarCode();
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.SpaceMonster.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.SpaceMonster.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        SpaceMonster(new QuestDialog(ALERT, "Space Monster", "A space monster has invaded the Acamar system and is disturbing the trade routes. You'll be rewarded handsomely if you manage to destroy it.")),
        SpaceMonsterKilled(new QuestDialog(-15000, ALERT, "Monster Killed", "We thank you for destroying the space monster that circled our system for so long. Please accept 15000 credits as reward for your heroic deed."));

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
        SpaceMonsterKill("Kill the space monster at Acamar."),
        SpaceMonsterReward("Get rewarded for killing a space monster at Acamar.");

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

    enum News implements SimpleValueEnum<String> {
        SpaceMonster("Space Monster Threatens Homeworld!"),
        SpaceMonsterKilled("Hero Slays Space Monster! Parade, Honors Planned for Today.");

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
        PretextSpaceMonster("a horrifying ^1");

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
        SpaceMonster("Space Monster");

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
        SpaceMonster("SpaceMonster");

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
        return "SpaceMonsterQuest{" +
                "questStatus=" + questStatus +
                ", spaceMonsterCrew=" + spaceMonsterCrew +
                ", shipSpecId=" + shipSpecId +
                ", spaceMonster=" + spaceMonster +
                "} " + super.toString();
    }
}
