package spacetrader.game.quest.quests;

import spacetrader.controls.Rectangle;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.*;
import spacetrader.game.quest.containers.*;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class ScarabQuest extends AbstractQuest implements Serializable {

    static final long serialVersionUID = -4731305242511509L;

    // Constants
    private static final int STATUS_SCARAB_NOT_STARTED = 0;
    private static final int STATUS_SCARAB_HUNTING = 1;
    private static final int STATUS_SCARAB_DESTROYED = 2;
    private static final int STATUS_SCARAB_DONE = 3;

    private static final int HULL_UPGRADE = 50;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private int questStatus = 0; // 0 = not given yet, 1 = not destroyed, 2 = destroyed - upgrade not performed, 3 = destroyed - hull upgrade performed

    private CrewMember scarabCrew;

    private int shipSpecId;

    private Ship scarab; //SCARAB,        // 13
    private Boolean hullUpgraded = null;

    private boolean authoritiesNotified;

    private UUID shipBarCode = UUID.randomUUID();

    private int scarabAttackEncounter; // SCARAB_ATTACK
    private int scarabIgnoreEncounter; // SCARAB_IGNORE

    private static final Rectangle SHIP_IMAGE_OFFSET = new Rectangle(7, 0, 49, 0);
    private static final Integer SHIP_IMAGE_INDEX = 14;

    public ScarabQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new ScarabPhase(), new ScarabDestroyedPhase(), new ScarabUpgradeHullPhase());
        initializeTransitionMap();

        int d = getDifficultyId();
        scarabCrew = registerNewSpecialCrewMember(5 + d, 6 + d, 1, 6 + d, false);

        scarabAttackEncounter = registerNewEncounter();
        scarabIgnoreEncounter = registerNewEncounter();

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
        getTransitionMap().put(ON_DISPLAY_SHIP_EQUIPMENT, this::onDisplayShipEquipment);

        getTransitionMap().put(ENCOUNTER_DETERMINE_NON_RANDOM_ENCOUNTER, this::encounterDetermineNonRandomEncounter);
        getTransitionMap().put(ENCOUNTER_VERIFY_ATTACK, this::encounterVerifyAttack);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_TEXT, this::encounterGetIntroductoryText);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_ACTION, this::encounterGetIntroductoryAction);
        getTransitionMap().put(ENCOUNTER_GET_IMAGE_INDEX, this::encounterGetEncounterImageIndex);
        getTransitionMap().put(ENCOUNTER_SHOW_ACTION_BUTTONS, this::encounterShowActionButtons);
        getTransitionMap().put(ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS, this::encounterGetExecuteActionFireShots);
        getTransitionMap().put(ENCOUNTER_IS_EXECUTE_ATTACK_GET_WEAPONS, this::encounterIsExecuteAttackGetWeapons);
        getTransitionMap().put(ENCOUNTER_IS_EXECUTE_ATTACK_SECONDARY_DAMAGE, this::encounterIsExecuteAttackSecondaryDamage);
        getTransitionMap().put(ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, this::encounterExecuteActionOpponentDisabled);
        getTransitionMap().put(ENCOUNTER_ON_ENCOUNTER_WON, this::encounterOnEncounterWon);

        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);

        getTransitionMap().put(IS_CONSIDER_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderStatusCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderStatusDefaultCheat);
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
        return CrewNames.Scarab.getValue();
    }

    @Override
    public void affectShipHullStrength(ShipSpecContainer shipSpecContainer) {
        if (isHardenedHull(shipSpecContainer.getShipSpec())) {
            shipSpecContainer.setHullStrength(shipSpecContainer.getHullStrength() + HULL_UPGRADE);
        }
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

    private boolean isHardenedHull(ShipSpec shipSpec) {
        if (hullUpgraded != null) {
            return hullUpgraded; // for cheats
        } else {
            return shipSpec.getBarCode() == shipBarCode && questStatus == STATUS_SCARAB_DONE;
        }
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
        // Assign a wormhole location endpoint for the Scarab.
        OptionalInt freeWormhole = Arrays.stream(game.getWormholes())
                .filter(wormhole -> !getStarSystem(wormhole).isQuestSystem()).findAny();
        if (freeWormhole.isPresent()) {
            StarSystem starSystem = getStarSystem(freeWormhole.getAsInt());
            starSystem.setQuestSystem(true);
            phases.get(QuestPhases.ScarabDestroyed).setStarSystemId(starSystem.getId());
            phases.get(QuestPhases.ScarabUpgradeHull).setStarSystemId(starSystem.getId());
        } else {
            ((BooleanContainer) object).setValue(false);
        }
    }

    private void onAssignEventsRandomly(Object object) {
        log.fine("");
        phases.get(QuestPhases.Scarab).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        getMercenaries().put(scarabCrew.getId(), scarabCrew);
    }

    private void afterShipSpecInitialized(Object object) {
        ShipSpec shipSpec = new ShipSpec(ShipType.QUEST, Size.LARGE, 20, 2, 0, 0, 2, 1, 1, 400, 1, 500000, 0, Activity.NA, Activity.NA,
                Activity.NA, TechLevel.UNAVAILABLE);
        shipSpecId = registerNewShipSpec(shipSpec);
    }

    private void onCreateShip(Object object) {
        scarab = game.createShipByShipSpecId(shipSpecId);
        scarab.getCrew()[0] = scarabCrew;
        scarab.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scarab.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        phases.forEach((key, value) -> showSpecialButtonIfCanBeExecuted(object, value));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class ScarabPhase extends Phase { //new SpecialEvent(SpecialEventType.Scarab, 0, 1, true),

        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_SCARAB_NOT_STARTED
                    && getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.Scarab);
            questStatus = STATUS_SCARAB_HUNTING;
            setQuestState(QuestState.ACTIVE);
            confirmQuestPhase();
        }

        @Override
        public String toString() {
            return "ScarabPhase{} " + super.toString();
        }
    }

    class ScarabDestroyedPhase extends Phase { //new SpecialEvent(SpecialEventType.ScarabDestroyed, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_SCARAB_DESTROYED && !authoritiesNotified;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.ScarabDestroyed);
            authoritiesNotified = true;
        }

        @Override
        public String toString() {
            return "ScarabDestroyedPhase{} " + super.toString();
        }
    }

    class ScarabUpgradeHullPhase extends Phase { //new SpecialEvent(SpecialEventType.ScarabUpgradeHull, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && questStatus == STATUS_SCARAB_DESTROYED && authoritiesNotified;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.ScarabUpgradeHull);

            showAlert(Alerts.ShipHullUpgraded.getValue());
            shipBarCode = getShip().getBarCode();
            getShip().setHull(getShip().getHull() + HULL_UPGRADE);
            questStatus = STATUS_SCARAB_DONE;
            setQuestState(QuestState.FINISHED);
            confirmQuestPhase();
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "ScarabUpgradeHullPhase{} " + super.toString();
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
        List<String> questStrings = (ArrayList<String>) object;
        if (questStatus == STATUS_SCARAB_HUNTING) {
            questStrings.add(QuestClues.ScarabFind.getValue());
            log.fine(QuestClues.ScarabFind.getValue());
        } else if (questStatus == STATUS_SCARAB_DESTROYED) {
            if (!authoritiesNotified) {
                String text = Functions.stringVars(QuestClues.ScarabNotify.getValue(),
                        getStarSystem(phases.get(QuestPhases.ScarabDestroyed).getStarSystemId()).getName());
                questStrings.add(text);
                log.fine(text);
            } else {
                String text = Functions.stringVars(QuestClues.ScarabHull.getValue(),
                        getStarSystem(phases.get(QuestPhases.ScarabUpgradeHull).getStarSystemId()).getName());
                questStrings.add(text);
                log.fine(text);
            }
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onDisplayShipEquipment(Object object) {
        if (isHardenedHull(getShip())) {
            List<StringBuilder> equipPair = (List<StringBuilder>) object;
            equipPair.get(0).append(Encounters.ShipHull.getValue()).append(Strings.newline).append(Strings.newline);
            equipPair.get(1).append(Encounters.ShipHardened.getValue()).append(Strings.newline).append(Strings.newline);
        } else {
            log.fine("skipped");
        }
    }

    // Encounter with the stolen Scarab
    private void encounterDetermineNonRandomEncounter(Object object) {
        //TODO need constant instead of 20
        if (game.getArrivedViaWormhole() && game.getClicks() == 20 && game.getWarpSystem().isQuestSystem()
                && game.getWarpSystem().getId() == phases.get(QuestPhases.ScarabDestroyed).getStarSystemId()
                && questStatus == STATUS_SCARAB_HUNTING) {
            setOpponent(scarab);
            getEncounter().setEncounterType(getShip().isCloaked() ? scarabIgnoreEncounter : scarabAttackEncounter);
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterVerifyAttack(Object object) {
        if (getEncounter().getEncounterType() == scarabIgnoreEncounter) {
            getEncounter().setEncounterType(scarabAttackEncounter);
        }
    }

    private void encounterGetIntroductoryText(Object object) {
        if (scarab.getBarCode() == getOpponent().getBarCode()) { //TODO opponentIsScarab()
            ((StringContainer) object).setValue(Encounters.PretextStolenScarab.getValue());
        }
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (scarab.getBarCode() == getOpponent().getBarCode()) {
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
        if (scarab.getBarCode() == getOpponent().getBarCode()) {
            ((IntContainer) object).setValue(Consts.EncounterImgPirate);
        }
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == scarabAttackEncounter) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        List<Boolean> visible = (ArrayList<Boolean>) object;
        if (getEncounter().getEncounterType() == scarabAttackEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.FLEE.ordinal(), true);
            visible.set(Buttons.SURRENDER.ordinal(), true);
        }
        if (getEncounter().getEncounterType() == scarabIgnoreEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }
    }

    private void encounterIsExecuteAttackGetWeapons(Object object) {
        OpponentsContainer opponents = (OpponentsContainer) object;
        if (opponents.getDefender().getBarCode() == scarab.getBarCode()) {
            opponents.setAttackerLasers(opponents.getAttackerLasers()
                    - opponents.getAttacker().getWeaponStrength(WeaponType.BEAM_LASER, WeaponType.MILITARY_LASER));
            opponents.setAttackerDisruptors(opponents.getAttackerDisruptors()
                    - opponents.getAttacker().getWeaponStrength(WeaponType.PHOTON_DISRUPTOR, WeaponType.PHOTON_DISRUPTOR));
        }
    }

    // If the hull is hardened, damage is halved.
    private void encounterIsExecuteAttackSecondaryDamage(Object object) {
        if (isHardenedHull(getShip())) {
            ((IntContainer) object).divideBy(2);
        }
    }

    private void encounterExecuteActionOpponentDisabled(Object object) {
        if (getOpponent().getBarCode() == scarab.getBarCode()) {
            encounterDefeatScarab();
            GuiFacade.alert(AlertType.EncounterDisabledOpponent,
                    getEncounter().getEncounterShipText(), PrincessQuest.Encounters.PrincessRescued.getValue());
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterDefeatScarab() {
        getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
        getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
        questStatus = STATUS_SCARAB_DESTROYED;
    }

    private void encounterOnEncounterWon(Object object) {
        if (getEncounter().getEncounterType() == scarabAttackEncounter) { //SCARAB_ATTACK
            encounterDefeatScarab();
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        if (phases.get(QuestPhases.Scarab).isDesiredSystem() && questStatus <= STATUS_SCARAB_HUNTING) {
            newsIndex = News.Scarab.ordinal();
        } else if (phases.get(QuestPhases.ScarabDestroyed).isDesiredSystem()) {
            if (questStatus == STATUS_SCARAB_HUNTING) {
                newsIndex = News.ScarabHarass.ordinal();
            } else if (questStatus >= STATUS_SCARAB_DESTROYED) {
                newsIndex = News.ScarabDestroyed.ordinal();
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
        if (cheatWords.getFirst().equals(CheatTitles.Diamond.name())) {
            hullUpgraded = (hullUpgraded == null) || !hullUpgraded;
            shipBarCode = getShip().getBarCode();
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    private void onIsConsiderStatusCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Scarab.name())) {
            questStatus = Math.max(0, cheatWords.getNum2());
            shipBarCode = getShip().getBarCode();
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderStatusDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CheatTitles.Scarab.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Scarab(new QuestDialog(ALERT, "Scarab Stolen", "Captain Renwick developed a new organic hull material for his ship which cannot be damaged except by Pulse lasers. While he was celebrating this success, pirates boarded and stole the craft, which they have named the Scarab. Rumors suggest it's being hidden at the exit to a wormhole. Destroy the ship for a reward!")),
        ScarabDestroyed(new QuestDialog(ALERT, "Scarab Destroyed", "Space Corps is indebted to you for destroying the Scarab and the pirates who stole it. As a reward, we can have Captain Renwick upgrade the hull of your ship. Note that his upgrades won't be transferable if you buy a new ship! Come back with the ship you wish to upgrade.")),
        ScarabUpgradeHull(new QuestDialog(DIALOG, "Upgrade Hull", "The organic hull used in the Scarab is still not ready for day-to-day use. But Captain Renwick can certainly upgrade your hull with some of his retrofit technology. It's light stuff, and won't reduce your ship's range. Should he upgrade your ship?"));

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
        ScarabFind("Find and destroy the Scarab (which is hiding at the exit to a wormhole)."),
        ScarabNotify("Notify the authorities at ^1 that the Scarab has been destroyed."),
        ScarabHull("Get your hull upgraded at ^1.");

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
        ShipHullUpgraded("Hull Upgraded", "Technicians spend the day retrofitting the hull of your ship.");

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
        Scarab("Security Scandal: Test Craft Confirmed Stolen."),
        ScarabHarass("Wormhole Travelers Harassed by Unusual Ship!"),
        ScarabDestroyed("Wormhole Traffic Delayed as Stolen Craft Destroyed.");

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
        PretextStolenScarab("a stolen ^1"),
        ShipHull("Hull:"),
        ShipHardened("Hardened");

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
        Scarab("Scarab");

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
        Diamond("Diamond"),
        Scarab("Scarab");

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
        return "ScarabQuest{" +
                "questStatus=" + questStatus +
                ", scarabCrew=" + scarabCrew +
                ", shipSpecId=" + shipSpecId +
                ", scarab=" + scarab +
                ", shipBarCode=" + shipBarCode +
                "} " + super.toString();
    }
}
