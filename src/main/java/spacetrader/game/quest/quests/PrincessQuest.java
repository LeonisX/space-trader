package spacetrader.game.quest.quests;

import spacetrader.controls.Rectangle;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.AlertDialog;
import spacetrader.game.quest.I18n;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.containers.*;
import spacetrader.game.quest.enums.*;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class PrincessQuest extends AbstractQuest implements Serializable {

    static final long serialVersionUID = -4731305242511503L;

    // Constants
    private static final int STATUS_NOT_STARTED = 0;
    private static final int STATUS_FLY_CENTAURI = 1;
    private static final int STATUS_FLY_INTHARA = 2;
    private static final int STATUS_FLY_QONOS = 3;
    private static final int STATUS_PRINCESS_RESCUED = 4;
    private static final int STATUS_PRINCESS_IMPATIENT = 14;
    private static final int STATUS_PRINCESS_RETURNED = 15;
    private static final int STATUS_DONE = 16;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private int questStatus = 0; // 0 = not available, 1 = Go to Centauri, 2 = Go to Inthara, // 3 = Go to Qonos,
    // 4 = Princess Rescued, 5-14 = On Board, 15 = Princess Returned, 16 = Got Quantum Disruptor

    private CrewMember princess;
    private CrewMember scorpionCrew;

    private int shipSpecId;

    private Ship scorpion; //ShipType.SCORPION

    private static final Rectangle SHIP_IMAGE_OFFSET = new Rectangle(2, 0, 60, 0);
    private static final Integer SHIP_IMAGE_INDEX = 16;

    private boolean princessOnBoard;

    private int gameEndTypeId;

    private int imageIndex = 3;

    private int scorpionAttackEncounter; // SCORPION_ATTACK
    private int scorpionIgnoreEncounter; // SCORPION_IGNORE

    public PrincessQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new PrincessPhase(), new PrincessCentauriPhase(), new PrincessIntharaPhase(),
                new PrincessQonosPhase(), new PrincessReturnedPhase(), new PrincessQuantumPhase());
        initializeTransitionMap();

        int d = getDifficultyId();
        princess = registerNewSpecialCrewMember(4, 3, 8, 9, false);
        scorpionCrew = registerNewSpecialCrewMember(8 + d, 8 + d, 1, 6 + d, false);

        scorpionAttackEncounter = registerNewEncounter();
        scorpionIgnoreEncounter = registerNewEncounter();

        registerNews(News.values().length);

        gameEndTypeId = registerNewGameEndType();

        //setSpecialCrewId(princess.getId());

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

        getTransitionMap().put(ON_AFTER_GENERATE_UNIVERSE, this::onAfterGenerateUniverse);
        getTransitionMap().put(ON_ASSIGN_SYSTEM_EVENTS_MANUAL, this::onAssignEventsManual);
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
        getTransitionMap().put(ENCOUNTER_EXECUTE_ATTACK_KEEP_SPECIAL_SHIP, this::encounterExecuteAttackKeepSpecialShip);
        getTransitionMap().put(ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, this::encounterExecuteActionOpponentDisabled);
        getTransitionMap().put(ENCOUNTER_ON_VERIFY_SURRENDER, this::encounterOnVerifySurrender);
        getTransitionMap().put(ENCOUNTER_ON_ROBBERY, this::encounterOnRobbery);
        getTransitionMap().put(ENCOUNTER_GET_STEALABLE_CARGO, this::encounterGetStealableCargo);

        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);

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
        return CrewNames.Scorpion.getValue();
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.GameEndings, Arrays.stream(GameEndings.values()));
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
        I18n.localizeStrings(Res.GameEndings, Arrays.stream(GameEndings.values()));
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

    private void onAfterGenerateUniverse(Object object) {
        // Galvon must be a Monarchy.
        log.fine("");
        StarSystem starSystem = getStarSystem(StarSystemId.Galvon);
        starSystem.setSize(Size.LARGE);
        starSystem.setPoliticalSystemType(PoliticalSystemType.MONARCHY);
        starSystem.setTechLevel(TechLevel.HI_TECH);
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = getStarSystem(StarSystemId.Galvon);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.Princess).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.PrincessReturned).setStarSystemId(starSystem.getId());
        phases.get(QuestPhases.PrincessQuantum).setStarSystemId(starSystem.getId());

        starSystem = getStarSystem(StarSystemId.Centauri);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.PrincessCentauri).setStarSystemId(starSystem.getId());

        starSystem = getStarSystem(StarSystemId.Inthara);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.PrincessInthara).setStarSystemId(starSystem.getId());

        starSystem = getStarSystem(StarSystemId.Qonos);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.PrincessQonos).setStarSystemId(starSystem.getId());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        getMercenaries().put(princess.getId(), princess);
        getMercenaries().put(scorpionCrew.getId(), scorpionCrew);
    }

    private void afterShipSpecInitialized(Object object) {
        ShipSpec shipSpec = new ShipSpec(ShipType.QUEST, Size.HUGE, 30, 2, 2, 2, 2, 1, 1, 300, 1, 500000, 0, Activity.NA,
                Activity.NA, Activity.NA, TechLevel.UNAVAILABLE);
        shipSpecId = registerNewShipSpec(shipSpec);
    }

    private void onCreateShip(Object object) {
        scorpion = game.createShipByShipSpecId(shipSpecId);
        scorpion.getCrew()[0] = scorpionCrew;
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        phases.forEach((key, value) -> showSpecialButtonIfCanBeExecuted(object, value));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class PrincessPhase extends Phase { //new SpecialEvent(SpecialEventType.Princess, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem()
                    && getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful
                    && getCommander().getReputationScore() >= Consts.ReputationScoreAverage
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
            return "PrincessPhase{} " + super.toString();
        }
    }

    class PrincessCentauriPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessCentauri, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() && questStatus == STATUS_FLY_CENTAURI;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.PrincessCentauri);
            questStatus = STATUS_FLY_INTHARA;
            confirmQuestPhase();
        }

        @Override
        public String toString() {
            return "PrincessCentauriPhase{} " + super.toString();
        }
    }

    class PrincessIntharaPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessInthara, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() && questStatus == STATUS_FLY_INTHARA;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.PrincessInthara);
            questStatus = STATUS_FLY_QONOS;
            confirmQuestPhase();
        }

        @Override
        public String toString() {
            return "PrincessIntharaPhase{} " + super.toString();
        }
    }

    class PrincessQonosPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessQonos, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() && questStatus == STATUS_PRINCESS_RESCUED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.PrincessQonos);
            //TODO where does the princess go? need to test this case.
            if (getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, princess.getName());
                getShip().hire(princess);
                princessOnBoard = true;
                questStatus = STATUS_PRINCESS_RESCUED;
                confirmQuestPhase();
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
    }

    class PrincessQuantumPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessQuantum, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem() && questStatus == STATUS_PRINCESS_RETURNED;
        }

        @Override
        public void successFlow() {
            log.fine("phase #" + QuestPhases.PrincessQuantum);
            if (getShip().getFreeWeaponSlots() == 0) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else {
                showAlert(Alerts.EquipmentQuantumDisruptor.getValue());
                getShip().addEquipment(Consts.Weapons[WeaponType.QUANTUM_DISRUPTOR.castToInt()]);
                questStatus = STATUS_DONE;
                setQuestState(QuestState.FINISHED);
                game.getQuestSystem().unSubscribeAll(getQuest());
            }
        }

        @Override
        public String toString() {
            return "PrincessQuantumPhase{} " + super.toString();
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
            case STATUS_FLY_CENTAURI:
                result = QuestClues.PrincessCentauri.getValue();
                break;
            case STATUS_FLY_INTHARA:
                result = QuestClues.PrincessInthara.getValue();
                break;
            case STATUS_FLY_QONOS:
                result = QuestClues.PrincessQonos.getValue();
                break;
            case STATUS_PRINCESS_IMPATIENT:
            case STATUS_PRINCESS_RESCUED:
                if (princessOnBoard) {
                    if (questStatus == STATUS_PRINCESS_IMPATIENT) {
                        result = Functions.stringVars(QuestClues.PrincessReturningImpatient.getValue(), CrewNames.Ziyal.getValue());
                    } else {
                        result = Functions.stringVars(QuestClues.PrincessReturning.getValue(), CrewNames.Ziyal.getValue());
                    }
                } else {
                    result = Functions.stringVars(QuestClues.PrincessReturn.getValue(), CrewNames.Ziyal.getValue());
                }
                break;
            case STATUS_PRINCESS_RETURNED:
                result = QuestClues.PrincessQuantum.getValue();
                break;
            default:
                log.fine("skipped");
        }

        if (result != null) {
            questStrings.add(result);
            log.fine(result);
        }
    }

    private void encounterDetermineNonRandomEncounter(Object object) {
        if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Qonos && questStatus == STATUS_FLY_QONOS) {
            setOpponent(scorpion);
            getEncounter().setEncounterType(getShip().isCloaked() ? scorpionIgnoreEncounter : scorpionAttackEncounter);
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterVerifyAttack(Object object) {
        if (getOpponent().getBarCode() == scorpion.getBarCode()
                && getShip().getWeaponStrength(WeaponType.PHOTON_DISRUPTOR, WeaponType.QUANTUM_DISRUPTOR) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoDisruptors);
            ((BooleanContainer) object).setValue(false);
        }
        if (getEncounter().getEncounterType() == scorpionIgnoreEncounter) {
            getEncounter().setEncounterType(scorpionAttackEncounter);
        }
    }

    private void encounterGetIntroductoryText(Object object) {
        if (scorpion.getBarCode() == getOpponent().getBarCode()) { //TODO opponentIsScorpion
            ((StringContainer) object).setValue(Encounters.PretextScorpion.getValue());
        }
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (scorpion.getBarCode() == getOpponent().getBarCode()) {
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
        if (scorpion.getBarCode() == getOpponent().getBarCode()) {
            ((IntContainer) object).setValue(Consts.EncounterImgPirate);
        }
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == scorpionAttackEncounter) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        List<Boolean> visible = (ArrayList<Boolean>) object;
        if (getEncounter().getEncounterType() == scorpionAttackEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.FLEE.ordinal(), true);
        }
        if (getEncounter().getEncounterType() == scorpionIgnoreEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }
    }

    private void encounterExecuteAttackKeepSpecialShip(Object object) {
        Ship defender = ((Ship) object);
        if (defender.getBarCode() == scorpion.getBarCode() && defender.getHull() == 0) {
            defender.setHull(1); // Make sure the Scorpion doesn't get destroyed.
            game.setOpponentDisabled(true);
        }
    }

    private void encounterExecuteActionOpponentDisabled(Object object) {
        if (getOpponent().getBarCode() == scorpion.getBarCode()) {
            getCommander().setKillsPirate(getCommander().getKillsPirate() + 1);
            getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
            questStatus = STATUS_PRINCESS_RESCUED;

            GuiFacade.alert(AlertType.EncounterDisabledOpponent, getEncounter().getEncounterShipText(), Encounters.PrincessRescued.getValue());

            getCommander().setReputationScore(
                    //TODO getOpponent().getType() == 16
                    getCommander().getReputationScore() + (getOpponent().getType().castToInt() / 2 + 1));
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterOnVerifySurrender(Object object) {
        SurrenderContainer surrenderContainer = (SurrenderContainer) object;
        // If princess on board and no hidden cargo bays - continue fight to die.
        if (!surrenderContainer.isMatch() && princessOnBoard && !getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)
                && getEncounter().getEncounterType() == EncounterType.PIRATE_ATTACK.castToInt()) {
            showAlert(Alerts.EncounterPiratesSurrenderPrincess.getValue());
            surrenderContainer.setMatch(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterOnRobbery(Object object) {
        if (princessOnBoard && getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            ((ArrayList<String>) object).add(Encounters.HidePrincess.getValue());
        }
    }

    private void encounterGetStealableCargo(Object object) {
        if (princessOnBoard) {
            ((IntContainer) object).dec();
        }
    }

    //TODO ability to repeat quest for low difficulty?
    private void onArrested(Object object) {
        if (princessOnBoard) {
            log.fine("Arrested + Princess");
            showAlert(Alerts.PrincessTakenHome.getValue());
            failQuest();
        } else {
            log.fine("Arrested w/o Princess");
        }
    }

    //TODO ability to repeat quest for low difficulty?
    private void onEscapeWithPod(Object object) {
        if (princessOnBoard) {
            log.fine("Escaped + Princess");
            showAlert(Alerts.PrincessTakenHome.getValue());
            failQuest();
        } else {
            log.fine("Escaped w/o Princess");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_NOT_STARTED;
        setQuestState(QuestState.FAILED);
        removePassenger();
    }

    private void removePassenger() {
        getCommander().getShip().fire(princess.getId());
        princessOnBoard = false;
    }

    private void onIncrementDays(Object object) {
        if (princessOnBoard) {
            log.fine(questStatus + "");
            if (questStatus == (STATUS_PRINCESS_IMPATIENT + STATUS_PRINCESS_RESCUED) / 2) {
                showAlert(Alerts.SpecialPassengerConcernedPrincess.getValue());
            } else if (questStatus == STATUS_PRINCESS_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientPrincess.getValue());
                princess.setPilot(0);
                princess.setFighter(0);
                princess.setTrader(0);
                princess.setEngineer(0);
            }

            if (questStatus < STATUS_PRINCESS_IMPATIENT) {
                questStatus++;
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        Integer newsIndex = null;

        switch (questStatus) {
            case STATUS_NOT_STARTED:
                if (isCurrentSystemIs(StarSystemId.Galvon)) {
                    newsIndex = News.Princess.ordinal();
                }
                break;
            //TODO try to catch these news in other systems
            case STATUS_FLY_CENTAURI:
                newsIndex = News.PrincessCentauri.ordinal();
                break;
            case STATUS_FLY_INTHARA:
                newsIndex = News.PrincessInthara.ordinal();
                break;
            case STATUS_FLY_QONOS:
                newsIndex = News.PrincessQonos.ordinal();
                break;
            case STATUS_PRINCESS_RESCUED:
                newsIndex = News.PrincessRescued.ordinal();
                break;
            case STATUS_PRINCESS_RETURNED:
                newsIndex = News.PrincessReturned.ordinal();
                break;
            default:
                log.fine("skipped");
        }

        if (newsIndex != null) {
            log.fine("" + getNewsIdByIndex(newsIndex));
            addNewsByIndex(newsIndex);
        }
    }

    private void onBeforeGameEnd(Object object) {
        MoonQuest moonQuest = (MoonQuest) game.getQuestSystem().getQuest(QuestName.Moon);
        if (game.getEndStatus() == moonQuest.getEndTypeId() && questStatus >= STATUS_PRINCESS_RETURNED) {
            game.setEndStatus(gameEndTypeId);
        }
    }

    private void onGameEndAlert(Object object) {
        if (game.getEndStatus() == gameEndTypeId) {
            new FormAlert(Alerts.GameEndBoughtMoonGirl.getValue().getTitle(), imageIndex).showDialog();
        }
    }

    private void onGetGameScore(Object object) {
        if (game.getEndStatus() == gameEndTypeId) {
            ScoreContainer score = (ScoreContainer) object;
            if (score.getEndStatus() == 1) {
                score.setDaysMoon(Math.max(0, (getDifficultyId() + 1) * 100 - getCommander().getDays()));
                score.setModifier(100); //TODO 110????
            }
        }
    }

    @Override
    public String getGameCompletionText() {
        return GameEndings.ClaimedMoonWithPrincess.getValue();
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Princess.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Princess.getValue(), questStatus);
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Princess(new QuestDialog(ALERT, "Kidnapped", "A member of the Royal Family of Galvon has been kidnapped! Princess Ziyal was abducted by men while travelling across the planet. They escaped in a hi-tech ship called the Scorpion. Please rescue her! (You'll need to equip your ship with disruptors to be able to defeat the Scorpion without destroying it.) A ship bristling with weapons was blasting out of the system. It's trajectory before going to warp indicates that its destination was Centauri.")),
        PrincessCentauri(new QuestDialog(ALERT, "Aggressive Ship", "A ship had its shields upgraded to Lighting Shields just two days ago. A shipyard worker overheard one of the crew saying they were headed to Inthara.")),
        PrincessInthara(new QuestDialog(ALERT, "Dangerous Scorpion", "Just yesterday a ship was seen in docking bay 327. A trader sold goods to a member of the crew, who was a native of Qonos. It's possible that's where they were going next.")),
        PrincessQonos(new QuestDialog(DIALOG, "Royal Rescue", "The Galvonian Ambassador to Qonos approaches you. The Princess needs a ride home. Will you take her? I don't think she'll feel safe with anyone else.")),
        PrincessReturned(new QuestDialog(ALERT, "Royal Return", "The King and Queen are extremely grateful to you for returning their daughter to them. The King says, \"Ziyal is priceless to us, but we feel we must offer you something as a reward. Visit my shipyard captain and he'll install one of our new Quantum Disruptors.\"")),
        PrincessQuantum(new QuestDialog(DIALOG, "Quantum Disruptor", "His Majesty's Shipyard: Do you want us to install a quantum disruptor on your current ship?"));

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
        PrincessCentauri("Follow the Scorpion to Centauri."),
        PrincessInthara("Follow the Scorpion to Inthara."),
        PrincessQonos("Follow the Scorpion to Qonos."),
        PrincessReturn("Transport ^1 from Qonos to Galvon."),
        PrincessReturning("Return ^1 to Galvon."),
        PrincessReturningImpatient("Return ^1 to Galvon." + newline
                + "She is becoming anxious to arrive at home, and is no longer of any help in engineering functions."),
        PrincessQuantum("Get your Quantum Disruptor at Galvon.");

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
        EquipmentQuantumDisruptor("Quantum Disruptor", "You now have one quantum disruptor installed on your ship."),
        SpecialPassengerConcernedPrincess("Ship's Comm.", "[Ziyal] Oh Captain? (giggles) Would it help if I got out and pushed?"),
        SpecialPassengerImpatientPrincess("Ship's Comm.", "Sir! Are you taking me home or merely taking the place of my previous captors?!"),
        EncounterPiratesSurrenderPrincess("You Have the Princess", "Pirates are not nice people, and there's no telling what they might do to the Princess. Better to die fighting than give her up to them!"),
        PrincessTakenHome("Princess Taken Home", "The Space Corps decides to give the Princess a ride home to Galvon since you obviously weren't up to the task."),
        GameEndBoughtMoonGirl("You Have Retired with the Princess", "");

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
        Princess("Member of Royal Family kidnapped!"),
        PrincessCentauri("Aggressive Ship Seen in Orbit Around Centauri"),
        PrincessInthara("Dangerous Scorpion Damages Several Other Ships Near Inthara"),
        PrincessQonos("Kidnappers Holding Out at Qonos"),
        PrincessRescued("Scorpion Defeated! Kidnapped Member of Galvon Royal Family Freed!"),
        PrincessReturned("Beloved Royal Returns Home!");

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
        PretextScorpion("the kidnappers in a ^1"),
        PrincessRescued(newline + newline + "You land your ship near where the Space Corps has landed with the Scorpion in tow. The Princess is revived from hibernation and you get to see her for the first time. Instead of the spoiled child you were expecting, Ziyal is possible the most beautiful woman you've ever seen. \"What took you so long?\" she demands. You notice a twinkle in her eye, and then she smiles. Not only is she beautiful, but she's got a sense of humor. She says, \"Thank you for freeing me. I am in your debt.\" With that she give you a kiss on the cheek, then leaves. You hear her mumble, \"Now about a ride home.\""),
        HidePrincess("the Princess");

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

    enum GameEndings implements SimpleValueEnum<String> {
        ClaimedMoonWithPrincess("Claimed moon with Princess");

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

    enum CrewNames implements SimpleValueEnum<String> {
        Ziyal("Ziyal"),
        Scorpion("Scorpion");

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
        Princess("Princess");

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
        return "PrincessQuest{" +
                "questStatus=" + questStatus +
                ", princess=" + princess +
                ", scorpionCrew=" + scorpionCrew +
                ", shipSpecId=" + shipSpecId +
                ", scorpion=" + scorpion +
                ", princessOnBoard=" + princessOnBoard +
                ", gameEndTypeId=" + gameEndTypeId +
                ", imageIndex=" + imageIndex +
                "} " + super.toString();
    }
}
