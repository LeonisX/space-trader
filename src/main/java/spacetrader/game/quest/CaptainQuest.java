package spacetrader.game.quest;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.RandomEncounterContainer;
import spacetrader.game.quest.containers.StringContainer;
import spacetrader.game.quest.enums.QuestName;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

//TODO - wild, aeethibal
//TODO one quest for one captain
class CaptainQuest extends AbstractQuest {

    //TODO
    static final long serialVersionUID = -4731305242511504L;

    // Constants
    public static final int SCORE_KILL_CAPTAIN = 100;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private volatile int questStatus = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered


    //FAMOUS_CAPTAIN, // = 34,


    private CrewMember captain; // crew of famous captain ships

    private boolean wildOnBoard;

    private int famousCaptainAttackEncounter; // FAMOUS_CAPTAIN_ATTACK
    private int famousCaptainDisabledEncounter; // FAMOUS_CAPT_DISABLED

    private int captainAhabVeryRareEncounter; // CAPTAIN_AHAB
    private int captainConradVeryRareEncounter; // CAPTAIN_CONRAD
    private int captainHuieVeryRareEncounter; // CAPTAIN_HUIE

    public CaptainQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new WildPhase(), new WildGetsOutPhase());
        initializeTransitionMap();

        //TODO
        wild = registerNewSpecialCrewMember(7, 10, 2, 5, false);

        famousCaptainAttackEncounter = registerNewEncounter();
        famousCaptainDisabledEncounter = registerNewEncounter();

        //TODO register veryrareencounters in questsystem
        //veryRareEncounters.add(VeryRareEncounter.CAPTAIN_AHAB);
        //veryRareEncounters.add(VeryRareEncounter.CAPTAIN_CONRAD);
        //veryRareEncounters.add(VeryRareEncounter.CAPTAIN_HUIE);
        captainAhabVeryRareEncounter = registerNewVeryRareEncounter();
        captainConradVeryRareEncounter = registerNewVeryRareEncounter();
        captainHuieVeryRareEncounter = registerNewVeryRareEncounter();

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

        getTransitionMap().put(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
        getTransitionMap().put(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED_IS_CONFLICT, this::onSpecialButtonClickedResolveIsConflict);
        getTransitionMap().put(ON_AFTER_NEW_QUEST_STARTED, this::onAfterNewQuestStarted);

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

        getTransitionMap().put(ON_BEFORE_WARP, this::onBeforeWarp);
        getTransitionMap().put(ON_DETERMINE_RANDOM_ENCOUNTER, this::onDetermineRandomEncounter);
        getTransitionMap().put(ON_DETERMINE_VERY_RARE_ENCOUNTER, this::onDetermineVeryRareEncounter);
        getTransitionMap().put(ON_BEFORE_ENCOUNTER_GENERATE_OPPONENT, this::onBeforeEncounterGenerateOpponent);
        getTransitionMap().put(ON_ENCOUNTER_GENERATE_OPPONENT, this::onGenerateOpponentShip);
        getTransitionMap().put(ON_GENERATE_OPPONENT_SHIP_POLICE_TRIES, this::onGenerateOpponentShipPoliceTries);

        getTransitionMap().put(ENCOUNTER_VERIFY_ATTACK, this::encounterVerifyAttack);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_TEXT, this::encounterGetIntroductoryText);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_ACTION, this::encounterGetIntroductoryAction);
        getTransitionMap().put(ENCOUNTER_GET_ENCOUNTER_SHIP_TEXT, this::encounterGetEncounterShipText);
        getTransitionMap().put(ENCOUNTER_GET_IMAGE_INDEX, this::encounterGetEncounterImageIndex);
        getTransitionMap().put(ENCOUNTER_SHOW_ACTION_BUTTONS, this::encounterShowActionButtons);
        getTransitionMap().put(ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS, this::encounterGetExecuteActionFireShots);

        getTransitionMap().put(ENCOUNTER_UPDATE_ENCOUNTER_TYPE, this::encounterUpdateEncounterType);
        getTransitionMap().put(ENCOUNTER_MEET, this::encounterMeet);
        getTransitionMap().put(ENCOUNTER_ON_SURRENDER_IF_RAIDED, this::onSurrenderIfRaided);
        getTransitionMap().put(ENCOUNTER_ON_ENCOUNTER_WON, this::encounterOnEncounterWon);

        getTransitionMap().put(IS_ILLEGAL_SPECIAL_CARGO, this::onIsIllegalSpecialCargo);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS, this::onGetIllegalSpecialCargoActions);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION, this::onGetIllegalSpecialCargoDescription);
        getTransitionMap().put(ON_BEFORE_ARRESTED_CALCULATE_FINE, this::onBeforeArrestedCalculateFine);
        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
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
    public String getCrewMemberName(int id) {
        return CrewNames.values()[getSpecialCrewIds().indexOf(id)].getValue();
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

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Kravat);
        starSystem.setQuestSystem(true);
        phases.get(QuestPhases.WildGetsOut).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.Wild).setStarSystemId(occupyFreeSystemWithEvent());
    }

    @SuppressWarnings("unchecked")
    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        Game.getCurrentGame().getMercenaries().put(wild.getId(), wild);
        Game.getCurrentGame().getMercenaries().put(zeethibal.getId(), zeethibal);

        List<Integer> usedSystems = (List<Integer>) object;

        // Zeethibal may be on Kravat
        usedSystems.set(StarSystemId.Kravat.castToInt(), 1);


        //TODO

        //mercenaries.put(CrewMemberId.FAMOUS_CAPTAIN.castToInt(), new CrewMember(CrewMemberId.FAMOUS_CAPTAIN, 10, 10, 10, 10, false, StarSystemId.NA));

    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class WildPhase extends Phase { //new SpecialEvent(SpecialEventType.Wild, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                    && !wildOnBoard && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else if (!Game.getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
                showAlert(Alerts.WildWontBoardLaser.getValue());
            } else if (((ReactorQuest) game.getQuestSystem().getQuest(QuestName.Reactor)).isReactorOnBoard()) {
                showAlert(Alerts.WildWontBoardReactor.getValue());
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, wild.getName());
                confirmQuestPhase();
                Game.getShip().hire(wild);
                questStatus = STATUS_WILD_STARTED;
                setQuestState(QuestState.ACTIVE);
                wildOnBoard = true;

                onAfterNewQuestStarted(null);
            }
        }

        @Override
        public String toString() {
            return "WildPhase{} " + super.toString();
        }
    }

    class WildGetsOutPhase extends Phase { //new SpecialEvent(SpecialEventType.WildGetsOut, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return wildOnBoard && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            // Zeethibal has a 10 in player's lowest score, an 8 in the next lowest score, and 5 elsewhere.
            zeethibal.setCurrentSystem(Game.getStarSystem(StarSystemId.Kravat));
            int lowest1 = Game.getCommander().nthLowestSkill(1);
            int lowest2 = Game.getCommander().nthLowestSkill(2);
            for (int i = 0; i < zeethibal.getSkills().length; i++) {
                zeethibal.getSkills()[i] = (i == lowest1 ? 10 : (i == lowest2 ? 8 : 5));
            }

            Game.getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreClean);
            Game.getCurrentGame().recalculateSellPrices();
            game.getQuestSystem().unSubscribeAll(getQuest());
            setQuestState(QuestState.FINISHED);
            questStatus = STATUS_WILD_DONE;
            removePassenger();
        }

        @Override
        public String toString() {
            return "WildGetsOutPhase{} " + super.toString();
        }
    }

/*
    CAPTAIN_AHAB, // = 2,
    CAPTAIN_CONRAD, // = 3,
    CAPTAIN_HUIE, // = 4,
    FAMOUS_CAPTAIN_ATTACK, // = 7,
    FAMOUS_CAPT_DISABLED, // = 8,
     */


    private void onSpecialButtonClicked(Object object) {
        Optional<QuestPhases> activePhase =
                phases.entrySet().stream().filter(p -> p.getValue().canBeExecuted()).map(Map.Entry::getKey).findFirst();
        if (activePhase.isPresent()) {
            showDialogAndProcessResult(object, activePhase.get().getValue(), () -> phases.get(activePhase.get()).successFlow());
        } else {
            log.fine("skipped");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onSpecialButtonClickedResolveIsConflict(Object object) {
        if (wildOnBoard) {
            if (showCancelAlert(Alerts.WildWontStayAboardReactor.getValue(), Game.getCommander().getCurrentSystem().getName()) == DialogResult.OK) {
                showAlert(Alerts.WildLeavesShip.getValue(), Game.getCommander().getCurrentSystem().getName());
                failQuest();
            } else {
                ((BooleanContainer) object).setValue(true);
            }
        }
    }

    private void onAfterNewQuestStarted(Object object) {
        if (((SculptureQuest) game.getQuestSystem().getQuest(QuestName.Sculpture)).isSculptureOnBoard() && wildOnBoard) {
            showAlert(Alerts.WildSculpture.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (wildOnBoard) {
            if (questStatus == STATUS_WILD_IMPATIENT) {
                ((ArrayList<String>) object).add(QuestClues.WildImpatient.getValue());
                log.fine(QuestClues.WildImpatient.getValue());
            } else {
                ((ArrayList<String>) object).add(QuestClues.Wild.getValue());
                log.fine(QuestClues.Wild.getValue());
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onBeforeWarp(Object object) {
        // if Wild is aboard, make sure ship is armed!
        if (wildOnBoard && !Game.getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
            if (showCancelAlert(Alerts.WildWontStayAboardLaser.getValue(), Game.getCommander().getCurrentSystem().getName()) == DialogResult.CANCEL) {
                ((BooleanContainer) object).setValue(false);
            } else {
                showAlert(Alerts.WildLeavesShip.getValue(), Game.getCommander().getCurrentSystem().getName());
                failQuest();
            }
        }
    }

    private void onDetermineRandomEncounter(Object object) {
        if (wildOnBoard && game.getWarpSystem().getId() == StarSystemId.Kravat) {
            // if you're coming in to Kravat & you have Wild onboard, there'll be swarms o' cops.
            ((RandomEncounterContainer) object)
                    .setPolice(Functions.getRandom(100) < 100 / Math.max(2, Math.min(4, 5 - Game.getDifficultyId())));
        }
    }

    private void onDetermineVeryRareEncounter(Object object) {
        //TODO
/*

        case CAPTAIN_AHAB:
        if (commander.getShip().hasShield(ShieldType.REFLECTIVE) && commander.getPilot() < 10
                && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_AHAB);
            setEncounterType(EncounterType.CAPTAIN_AHAB);
            game.generateOpponent(OpponentType.FAMOUS_CAPTAIN);
            return true;
        }
        break;

        case CAPTAIN_CONRAD:
        if (commander.getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && commander.getEngineer() < 10
                && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_CONRAD);
            setEncounterType(EncounterType.CAPTAIN_CONRAD);
            game.generateOpponent(OpponentType.FAMOUS_CAPTAIN);
            return true;
        }
        break;

        case CAPTAIN_HUIE:
        if (commander.getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && commander.getTrader() < 10
                && commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getVeryRareEncounters().remove(VeryRareEncounter.CAPTAIN_HUIE);
            setEncounterType(EncounterType.CAPTAIN_HUIE);
            game.generateOpponent(OpponentType.FAMOUS_CAPTAIN);
            return true;
        }
        break;
*/

    }

    private void onBeforeEncounterGenerateOpponent(Object object) {
        if (Game.getCurrentGame().getWarpSystem().getId() == StarSystemId.Kravat && wildOnBoard
                && Functions.getRandom(10) < Game.getDifficulty().castToInt() + 1) {
            ((CrewMember) object).setEngineer(Consts.MaxSkill);
        }
    }

    private void onGenerateOpponentShip(Object object) {
        if (oppType == OpponentType.FAMOUS_CAPTAIN) {
            setValues(Consts.ShipSpecs[Consts.MaxShip].getType());

            for (int i = 0; i < getShields().length; i++) {
                addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
            }

            for (int i = 0; i < getWeapons().length; i++) {
                addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
            }

            addEquipment(Consts.Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()]);
            addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

            getCrew()[0] = Game.getCurrentGame().getMercenaries().get(CrewMemberId.FAMOUS_CAPTAIN.castToInt());
        }
    }

    private void onGenerateOpponentShipPoliceTries(Object object) {
        if (wildOnBoard) {
            ((IntContainer) object).setValue(5);
        }
    }

    private void encounterVerifyAttack(Object object) {
        if (getEncounter().getEncounterType() == scarabIgnoreEncounter) {
            getEncounter().setEncounterType(scarabAttackEncounter);
        }

        //TODO

        /*case CAPTAIN_AHAB:
        case CAPTAIN_CONRAD:
        case CAPTAIN_HUIE:
        if (GuiFacade.alert(AlertType.EncounterAttackCaptain) == DialogResult.YES) {
            if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreVillain) {
                commander.setPoliceRecordScore(Consts.PoliceRecordScoreVillain);
            }

            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackTrader);

            switch (getEncounterType()) {
                case CAPTAIN_AHAB:
                    Game.getNews().addEvent(NewsEvent.CaptAhabAttacked);
                    break;
                case CAPTAIN_CONRAD:
                    Game.getNews().addEvent(NewsEvent.CaptConradAttacked);
                    break;
                case CAPTAIN_HUIE:
                    Game.getNews().addEvent(NewsEvent.CaptHuieAttacked);
                    break;
            }

            setEncounterType(EncounterType.FAMOUS_CAPTAIN_ATTACK);
        } else {
            attack.setValue(false);
        }
        break;*/
    }

    private void encounterGetIntroductoryText(Object object) {
        if (dragonfly.getBarCode() == getOpponent().getBarCode()) {
            ((StringContainer) object).setValue(Encounters.PretextStolenDragonfly.getValue());
        }

        //TODO
        /*case CAPTAIN_AHAB:
        encounterPretext.setValue(Strings.EncounterPretextCaptainAhab);
        break;
        case CAPTAIN_CONRAD:
        encounterPretext.setValue(Strings.EncounterPretextCaptainConrad);
        break;
        case CAPTAIN_HUIE:
        encounterPretext.setValue(Strings.EncounterPretextCaptainHuie);
        break;*/
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (dragonfly.getBarCode() == getOpponent().getBarCode()) { //TODO opponentIsCaptain()
            //TODO custom encounterType
            /*case CAPTAIN_AHAB:
                case CAPTAIN_CONRAD:
                case CAPTAIN_HUIE:
                    text.setValue(Strings.EncounterTextFamousCaptain);
                    break;*/
        }
    }


    private void encounterGetEncounterShipText(Object object) {
        if (dragonfly.getBarCode() == Game.getCurrentGame().getOpponent().getBarCode()) {
            ((StringContainer) object).setValue(Encounters.PretextStolenDragonfly.getValue());
        }

        //TODO
        /*case FAMOUS_CAPTAIN_ATTACK:
        case FAMOUS_CAPT_DISABLED:
        shipText = Strings.EncounterShipCaptain;
        break;*/
    }

    private void encounterGetEncounterImageIndex(Object object) {
        IntContainer encounterImage = (IntContainer) object;
        if (getEncounter().getEncounterType() == captainAhabVeryRareEncounter
                || getEncounter().getEncounterType() == captainConradVeryRareEncounter
                || getEncounter().getEncounterType() == captainHuieVeryRareEncounter) {
            encounterImage.setValue(Consts.EncounterImgSpecial);
        }
        if (getEncounter().getEncounterType() == famousCaptainAttackEncounter
                || getEncounter().getEncounterType() == famousCaptainDisabledEncounter) {
            // Nothing to do
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        List<Boolean> visible = (ArrayList<Boolean>) object;
        if (getEncounter().getEncounterType() == captainAhabVeryRareEncounter
                || getEncounter().getEncounterType() == captainConradVeryRareEncounter
                || getEncounter().getEncounterType() == captainHuieVeryRareEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
            visible.set(Buttons.MEET.ordinal(), true);
        }
        if (getEncounter().getEncounterType() == famousCaptainAttackEncounter
                || getEncounter().getEncounterType() == famousCaptainDisabledEncounter) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttackEncounter) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
    }

    private void encounterUpdateEncounterType(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttackEncounter)
            if (game.getOpponentDisabled()) {
                getEncounter().setEncounterType(famousCaptainDisabledEncounter);
            }
    }


    private void encounterMeet(Object object) {


        AlertType initialAlert = AlertType.Alert;
        int skill = 0;
        EquipmentType equipType = EquipmentType.GADGET;
        Object equipSubType = null;


        switch (getEncounterType()) {
            case CAPTAIN_AHAB:
                // Trade a reflective shield for skill points in piloting?
                initialAlert = AlertType.MeetCaptainAhab;
                equipType = EquipmentType.SHIELD;
                equipSubType = ShieldType.REFLECTIVE;
                skill = SkillType.PILOT.castToInt();
                break;

            case CAPTAIN_CONRAD:
                // Trade a military laser for skill points in engineering?
                initialAlert = AlertType.MeetCaptainConrad;
                equipType = EquipmentType.WEAPON;
                equipSubType = WeaponType.MILITARY_LASER;
                skill = SkillType.ENGINEER.castToInt();
                break;

            case CAPTAIN_HUIE:
                // Trade a military laser for skill points in trading?
                initialAlert = AlertType.MeetCaptainHuie;
                equipType = EquipmentType.WEAPON;
                equipSubType = WeaponType.MILITARY_LASER;
                skill = SkillType.TRADER.castToInt();
                break;
        }

        if (GuiFacade.alert(initialAlert) == DialogResult.YES) {
            // Remove the equipment we're trading.
            getShip().removeEquipment(equipType, equipSubType);

            // Add points to the appropriate skill - two points if
            // beginner-normal, one otherwise.
            commander.getSkills()[skill] = Math.min(Consts.MaxSkill, commander.getSkills()[skill]
                    + (Game.getDifficultyId() <= Difficulty.NORMAL.castToInt() ? 2 : 1));

            GuiFacade.alert(AlertType.SpecialTrainingCompleted);
        }
    }


    // TODO repeat if < normal, otherwise fail
    private void onSurrenderIfRaided(Object object) {
        if (wildOnBoard) {
            BooleanContainer allowRobbery = (BooleanContainer) object;
            allowRobbery.setValue(false);

            if (game.getOpponent().getCrewQuarters() > 1) {
                // Wild hops onto Pirate Ship
                showAlert(Alerts.WildGoesPirates.getValue());
                failQuest();
            } else {
                showAlert(Alerts.WildChatsPirates.getValue());
            }
        }
    }

    private void encounterOnEncounterWon(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttackEncounter) { //FAMOUS_CAPTAIN_ATTACK
            getCommander().setKillsTrader(getCommander().getKillsTrader() + 1);
            if (getCommander().getReputationScore() < Consts.ReputationScoreDangerous) {
                getCommander().setReputationScore(Consts.ReputationScoreDangerous);
            } else {
                getCommander().setReputationScore(getCommander().getReputationScore() + SCORE_KILL_CAPTAIN);
            }

            // bump news flag from attacked to ship destroyed
            Game.getNews().replaceLastAttackedEventWithDestroyedEvent();
        }
    }

    private void onIsIllegalSpecialCargo(Object object) {
        if (wildOnBoard) {
            ((BooleanContainer) object).setValue(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoActions(Object object) {
        if (wildOnBoard) {
            ((ArrayList<String>) object).add(Encounters.PoliceSurrenderWild.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoDescription(Object object) {
        if (wildOnBoard) {
            ((ArrayList<String>) object).add(Encounters.PoliceSubmitWild.getValue());
        }
    }

    private void onBeforeArrestedCalculateFine(Object object) {
        if (wildOnBoard) {
            IntContainer fine = (IntContainer) object;
            fine.multipleBy(1.05);
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onArrested(Object object) {
        if (wildOnBoard) {
            log.fine("Arrested + Wild");
            showAlert(Alerts.WildArrested.getValue());
            Game.getNews().addEvent(getNewsIds().get(News.WildArrested.ordinal()));
            failQuest();
        } else {
            log.fine("Arrested w/o Wild");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onEscapeWithPod(Object object) {
        if (wildOnBoard) {
            log.fine("Escaped + Wild");
            showAlert(Alerts.WildArrested.getValue());
            getCommander().setPoliceRecordScore(Game.getCommander().getPoliceRecordScore() + SCORE_CAUGHT_WITH_WILD);
            Game.getNews().addEvent(getNewsIds().get(News.WildArrested.ordinal()));
            failQuest();
        } else {
            log.fine("Escaped w/o Wild");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_WILD_NOT_STARTED;
        setQuestState(QuestState.FAILED);
        removePassenger();
    }

    private void removePassenger() {
        getShip().fire(wild.getId());
        wildOnBoard = false;
    }

    private void onIncrementDays(Object object) {
        if (wildOnBoard) {
            log.fine(questStatus + "");
            if (questStatus == STATUS_WILD_IMPATIENT / 2) {
                showAlert(Alerts.SpecialPassengerConcernedWild.getValue());
            } else if (questStatus == STATUS_WILD_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientWild.getValue());
                wild.setPilot(0);
                wild.setFighter(0);
                wild.setTrader(0);
                wild.setEngineer(0);
            }

            if (questStatus < STATUS_WILD_IMPATIENT) {
                questStatus++;
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (wildOnBoard && isCurrentSystemIs(StarSystemId.Kravat)) {
            log.fine("" + getNewsIds().get(News.WildGotToKravat.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.WildGotToKravat.ordinal()));
        } else {
            log.fine("skipped");
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Wild.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Wild.getValue(), questStatus);
    }

    // Special Events
    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Wild(new QuestDialog(DIALOG, "Jonathan Wild", "Law Enforcement is closing in on notorious criminal kingpin Jonathan Wild. He would reward you handsomely for smuggling him home to Kravat. You'd have to avoid capture by the Police on the way. Are you willing to give him a berth?")),
        WildGetsOut(new QuestDialog(ALERT, "Wild Gets Out", "Jonathan Wild is most grateful to you for spiriting him to safety. As a reward, he has one of his Cyber Criminals hack into the Police Database, and clean up your record. He also offers you the opportunity to take his talented nephew Zeethibal along as a Mercenary with no pay."));

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
        Wild("Smuggle Jonathan Wild to Kravat."),
        WildImpatient("Smuggle Jonathan Wild to Kravat.<br>Wild is getting impatient, and will no longer aid your crew along the way.");

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

    /*MeetCaptainAhab,
    MeetCaptainConrad,
    MeetCaptainHuie,*/


/*
    case EncounterAttackCaptain:
            return new FormAlert(AlertsEncounterAttackCaptainTitle, AlertsEncounterAttackCaptainMessage,
                                 AlertsEncounterAttackCaptainAccept, DialogResult.YES, AlertsEncounterAttackCaptainCancel, DialogResult.NO, args);

                                 case MeetCaptainAhab:
                return new FormAlert(AlertsMeetCaptainAhabTitle, AlertsMeetCaptainAhabMessage,
                        AlertsMeetCaptainAhabAccept, DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case MeetCaptainConrad:
                return new FormAlert(AlertsMeetCaptainConradTitle, AlertsMeetCaptainConradMessage,
                        AlertsMeetCaptainConradAccept, DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case MeetCaptainHuie:
                return new FormAlert(AlertsMeetCaptainHuieTitle, AlertsMeetCaptainHuieMessage,
                        AlertsMeetCaptainHuieAccept, DialogResult.YES, AlertsNo, DialogResult.NO, args);
*/

    public static String AlertsEncounterAttackCaptainTitle = "Really Attack?";
    public static String AlertsEncounterAttackCaptainMessage = "Famous Captains get famous by, among other things, destroying everyone who attacks them. Do you really want to attack?";
    public static String AlertsEncounterAttackCaptainAccept = "Really Attack";
    public static String AlertsEncounterAttackCaptainCancel = "OK, I Won't";
    public static String AlertsMeetCaptainAhabTitle = "Meet Captain Ahab";
    public static String AlertsMeetCaptainAhabMessage = "Captain Ahab is in need of a spare shield for an upcoming mission. He offers to trade you some piloting lessons for your reflective shield. Do you wish to trade?";
    public static String AlertsMeetCaptainAhabAccept = "Yes, Trade Shield";
    public static String AlertsMeetCaptainConradTitle = "Meet Captain Conrad";
    public static String AlertsMeetCaptainConradMessage = "Captain Conrad is in need of a military laser. She offers to trade you some engineering training for your military laser. Do you wish to trade?";
    public static String AlertsMeetCaptainConradAccept = "Yes, Trade Laser";
    public static String AlertsMeetCaptainHuieTitle = "Meet Captain Huie";
    public static String AlertsMeetCaptainHuieMessage = "Captain Huie is in need of a military laser. She offers to exchange some bargaining training for your military laser. Do you wish to trade?";
    public static String AlertsMeetCaptainHuieAccept = "Yes, Trade Laser";


    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialPassengerConcernedWild("Ship's Comm.", "Bridge? This is Jonathan. Are we there yet? Heh, heh. Sorry, I couldn't resist."),
        SpecialPassengerImpatientWild("Ship's Comm.", "Commander! Wild here. What's taking us so long?!"),
        WildArrested("Wild Arrested", "Jonathan Wild is arrested, and taken away to stand trial."),
        WildChatsPirates("Wild Chats With Pirates", "The Pirate Captain turns out to be an old associate of Jonathan Wild's. They talk about old times, and you get the feeling that Wild would switch ships if the Pirates had any quarters available."),
        WildGoesPirates("Wild Goes With Pirates", "The Pirate Captain turns out to be an old associate of Jonathan Wild's, and invites him to go to Kravat aboard the Pirate ship. Wild accepts the offer and thanks you for the ride."),
        WildLeavesShip("Wild Leaves Ship", "Jonathan Wild leaves your ship, and goes into hiding on ^1."),
        WildSculpture("Wild Eyes Sculpture", "Jonathan Wild sees the stolen sculpture. \"Wow, I only know of one of these left in the whole Universe!\" he exclaims, \"Geurge Locas must be beside himself with it being stolen.\" He seems very impressed with you, which makes you feel much better about the item your delivering."),
        WildWontBoardLaser("Wild Won't Board Ship", "Jonathan Wild isn't willing to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here."),
        WildWontBoardReactor("Wild Won't Board Ship", "Jonathan Wild doesn't like the looks of that Ion Reactor. He thinks it's too dangerous, and won't get on board."),
        WildWontStayAboardLaser("Wild Won't Stay Aboard", "Jonathan Wild isn't about to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here on ^1.", "Say Goodbye to Wild"),
        WildWontStayAboardReactor("Wild Won't Stay Aboard", "Jonathan Wild isn't willing to go with you if you bring that Reactor on board. He'd rather take his chances hiding out here on ^1.", "Say Goodbye to Wild");

        private AlertDialog value;

        Alerts(String title, String body) {
            this.value = new AlertDialog(title, body);
        }

        Alerts(String title, String body, String accept) {
            this.value = new AlertDialog(title, body, accept);
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

    /*
    CaptAhabAttacked, // = 15,
    CaptAhabDestroyed, // = 16,
    CaptConradAttacked, // = 17,
    CaptConradDestroyed, // = 18,
    CaptHuieAttacked, // = 19,
    CaptHuieDestroyed; // = 20,

            "Thug Assaults Captain Ahab!",
            "Destruction of Captain Ahab's Ship Causes Anger!",
            "Captain Conrad Comes Under Attack By Criminal!",
            "Captain Conrad's Ship Destroyed by Villain!",
            "Famed Captain Huie Attacked by Brigand!",
            "Citizens Mourn Destruction of Captain Huie's Ship!"*/


    enum News implements SimpleValueEnum<String> {
        WildArrested("Notorious Criminal Jonathan Wild Arrested!"),
        WildGotToKravat("Rumors Suggest Known Criminal J. Wild May Come to Kravat!");

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

    public static String EncounterPretextCaptainAhab = "the famous Captain Ahab in a ^1";
    public static String EncounterPretextCaptainConrad = "the famous Captain Conrad in a ^1";
    public static String EncounterPretextCaptainHuie = "the famous Captain Huie in a ^1";
    public static String EncounterShipCaptain = "Captain";
    public static String EncounterTextFamousCaptain = "The Captain requests a brief meeting with you.";

    /*VeryRareEncounters

    "Captain Ahab", "Captain Conrad",
            "Captain Huie"*/


    enum Encounters implements SimpleValueEnum<String> {
        PoliceSubmitWild("Jonathan Wild"),
        PoliceSurrenderWild("arrest Wild, too");

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
        Captain("Captain");

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
        Wild("Wild");
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

    //TODO
    @Override
    public String toString() {
        return "WildQuest{" +
                "questStatus=" + questStatus +
                ", wildOnBoard=" + wildOnBoard +
                "} " + super.toString();
    }
}
