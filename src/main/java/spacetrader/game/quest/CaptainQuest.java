package spacetrader.game.quest;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
    private static final int SCORE_KILL_CAPTAIN = 100;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private CrewMember captain; // FAMOUS_CAPTAIN, // = 34,crew of famous captain ships

    //TODO -Encounter
    private int famousCaptainAttackEncounter; // FAMOUS_CAPTAIN_ATTACK
    private int famousCaptainDisabledEncounter; // FAMOUS_CAPT_DISABLED

    //TODO -VeryRareEncounter
    private int captainAhabVeryRareEncounter; // CAPTAIN_AHAB
    private int captainConradVeryRareEncounter; // CAPTAIN_CONRAD
    private int captainHuieVeryRareEncounter; // CAPTAIN_HUIE

    //TODO -OpponentType
    private int famousCaptainOpponentType;

    public CaptainQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new WildPhase(), new WildGetsOutPhase());
        initializeTransitionMap();

        //TODO
        wild = registerNewSpecialCrewMember(7, 10, 2, 5, false);

        famousCaptainAttackEncounter = registerNewEncounter();
        famousCaptainDisabledEncounter = registerNewEncounter();

        captainAhabVeryRareEncounter = registerNewVeryRareEncounter();
        captainConradVeryRareEncounter = registerNewVeryRareEncounter();
        captainHuieVeryRareEncounter = registerNewVeryRareEncounter();

        famousCaptainOpponentType = registerNewOpponentType();

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

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED_IS_CONFLICT, this::onSpecialButtonClickedResolveIsConflict);

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
        getTransitionMap().put(ENCOUNTER_ON_ENCOUNTER_WON, this::encounterOnEncounterWon);
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
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class WildPhase extends Phase { //new SpecialEvent(SpecialEventType.Wild, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                    && !wildOnBoard && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            if (getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else if (!getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
                showAlert(Alerts.WildWontBoardLaser.getValue());
            } else if (((ReactorQuest) game.getQuestSystem().getQuest(QuestName.Reactor)).isReactorOnBoard()) {
                showAlert(Alerts.WildWontBoardReactor.getValue());
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, wild.getName());
                confirmQuestPhase();
                getShip().hire(wild);
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
            int lowest1 = getCommander().nthLowestSkill(1);
            int lowest2 = getCommander().nthLowestSkill(2);
            for (int i = 0; i < zeethibal.getSkills().length; i++) {
                zeethibal.getSkills()[i] = (i == lowest1 ? 10 : (i == lowest2 ? 8 : 5));
            }

            getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreClean);
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
            if (showCancelAlert(Alerts.WildWontStayAboardReactor.getValue(), getCommander().getCurrentSystem().getName()) == DialogResult.OK) {
                showAlert(Alerts.WildLeavesShip.getValue(), getCommander().getCurrentSystem().getName());
                failQuest();
            } else {
                ((BooleanContainer) object).setValue(true);
            }
        }
    }

    // 2. Captain Ahab will trade your Reflective Shield for skill points in Piloting.
    // 3. Captain Conrad will trade your Military Laser for skill points in Engineering.
    // 4. Captain Huie will trade your Military Laser for points in Trading.
    private void onDetermineVeryRareEncounter(Object object) {
        BooleanContainer happened = (BooleanContainer) object;
        //TODO mercenaries.put(CrewMemberId.FAMOUS_CAPTAIN.castToInt(), new CrewMember(CrewMemberId.FAMOUS_CAPTAIN, 10, 10, 10, 10, false, StarSystemId.NA));
        if (happened.getValue()) {
            return;
        }

        if (getEncounter().getVeryRareEncounterId() == captainAhabVeryRareEncounter
                && getShip().hasShield(ShieldType.REFLECTIVE) && getCommander().getPilot() < 10
                && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getEncounter().getVeryRareEncounters().remove(captainAhabVeryRareEncounter);
            getEncounter().setEncounterType(captainAhabVeryRareEncounter);
            game.generateOpponent(famousCaptainOpponentType);
            happened.setValue(true);
        } else if (getEncounter().getVeryRareEncounterId() == captainConradVeryRareEncounter
                && getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && getCommander().getEngineer() < 10
                && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getEncounter().getVeryRareEncounters().remove(captainConradVeryRareEncounter);
            getEncounter().setEncounterType(captainConradVeryRareEncounter);
            game.generateOpponent(famousCaptainOpponentType);
            happened.setValue(true);
        } else if (getEncounter().getVeryRareEncounterId() == captainHuieVeryRareEncounter
                && getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && getCommander().getTrader() < 10
                && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getEncounter().getVeryRareEncounters().remove(captainHuieVeryRareEncounter);
            getEncounter().setEncounterType(captainHuieVeryRareEncounter);
            game.generateOpponent(famousCaptainOpponentType);
            happened.setValue(true);
        }
    }

    private void onBeforeEncounterGenerateOpponent(Object object) {
        if (Game.getCurrentGame().getWarpSystem().getId() == StarSystemId.Kravat && wildOnBoard
                && Functions.getRandom(10) < Game.getDifficulty().castToInt() + 1) {
            ((CrewMember) object).setEngineer(Consts.MaxSkill);
        }
    }

    private void onGenerateOpponentShip(Object object) {
        Ship ship = (Ship) object;
        if (ship.getOpponentType() == famousCaptainOpponentType) {
            ship.setValues(Consts.ShipSpecs[Consts.MaxShip].getType());

            for (int i = 0; i < ship.getShields().length; i++) {
                ship.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
            }

            for (int i = 0; i < ship.getWeapons().length; i++) {
                ship.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
            }

            ship.addEquipment(Consts.Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()]);
            ship.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

            ship.getCrew()[0] = captain;

            ship.setInitialized(true);
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
        if (getEncounter().getEncounterType() == captainAhabVeryRareEncounter) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainAhab.getValue());
        } else if (getEncounter().getEncounterType() == captainConradVeryRareEncounter) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainConrad.getValue());
        } else if (getEncounter().getEncounterType() == captainHuieVeryRareEncounter) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainHuie.getValue());
        }
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (getEncounter().getEncounterType() == captainAhabVeryRareEncounter
                || getEncounter().getEncounterType() == captainConradVeryRareEncounter
                || getEncounter().getEncounterType() == captainHuieVeryRareEncounter) {
            ((StringContainer) object).setValue(Encounters.TextFamousCaptain.getValue());
        }
    }

    private void encounterGetEncounterShipText(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttackEncounter
                || getEncounter().getEncounterType() == famousCaptainDisabledEncounter) {
            ((StringContainer) object).setValue(Encounters.ShipCaptain.getValue());
        }
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
        if (getEncounter().getEncounterType() == famousCaptainAttackEncounter && game.getOpponentDisabled()) {
            getEncounter().setEncounterType(famousCaptainDisabledEncounter);
        }
    }


    private void encounterMeet(Object object) {
        Alerts alert = null;
        int skill = 0;
        EquipmentType equipType = EquipmentType.GADGET;
        Object equipSubType = null;

        if (getEncounter().getEncounterType() == captainAhabVeryRareEncounter) {
            // Trade a reflective shield for skill points in piloting?
            alert = Alerts.MeetCaptainAhab;
            equipType = EquipmentType.SHIELD;
            equipSubType = ShieldType.REFLECTIVE;
            skill = SkillType.PILOT.castToInt();

        } else if (getEncounter().getEncounterType() == captainConradVeryRareEncounter) {
            // Trade a military laser for skill points in engineering?
            alert = Alerts.MeetCaptainConrad;
            equipType = EquipmentType.WEAPON;
            equipSubType = WeaponType.MILITARY_LASER;
            skill = SkillType.ENGINEER.castToInt();

        } else if (getEncounter().getEncounterType() == captainHuieVeryRareEncounter) {
            // Trade a military laser for skill points in trading?
            alert = Alerts.MeetCaptainHuie;
            equipType = EquipmentType.WEAPON;
            equipSubType = WeaponType.MILITARY_LASER;
            skill = SkillType.TRADER.castToInt();
        }

        if (null == alert || showAlert(alert.getValue()) != DialogResult.YES) {
            return;
        }

        // Remove the equipment we're trading.
        getShip().removeEquipment(equipType, equipSubType);

        // Add points to the appropriate skill - two points if
        // beginner-normal, one otherwise.
        getCommander().getSkills()[skill] = Math.min(Consts.MaxSkill, getCommander().getSkills()[skill]
                + (Game.getDifficultyId() <= Difficulty.NORMAL.castToInt() ? 2 : 1));

        showAlert(Alerts.SpecialTrainingCompleted.getValue());
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

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_WILD_NOT_STARTED;
        setQuestState(QuestState.FAILED);
        removePassenger();
    }

    // Special Events
    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Wild(new QuestDialog(DIALOG, "Jonathan Wild", "Laing to give him a berth?"));

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

    enum Alerts implements SimpleValueEnum<AlertDialog> {

        EncounterAttackCaptain("Really Attack?", "Famous Captains get famous by, among other things, destroying everyone who attacks them. Do you really want to attack?", "Really Attack", "OK, I Won't"),
        MeetCaptainAhab("Meet Captain Ahab", "Captain Ahab is in need of a spare shield for an upcoming mission. He offers to trade you some piloting lessons for your reflective shield. Do you wish to trade?", "Yes, Trade Shield"),
        MeetCaptainConrad("Meet Captain Conrad", "Captain Conrad is in need of a military laser. She offers to trade you some engineering training for your military laser. Do you wish to trade?", "Yes, Trade Laser"),
        MeetCaptainHuie("Meet Captain Huie", "Captain Huie is in need of a military laser. She offers to exchange some bargaining training for your military laser. Do you wish to trade?", "Yes, Trade Laser"),
        SpecialTrainingCompleted("Training Completed", "After a few hours of training with a top expert, you feel your abilities have improved significantly.");

        private AlertDialog value;

        Alerts(String title, String body) {
            this.value = new AlertDialog(title, body);
        }

        Alerts(String title, String body, String accept) {
            this.value = new AlertDialog(title, body, accept);
        }

        Alerts(String title, String body, String accept, String cancel) {
            this.value = new AlertDialog(title, body, accept, cancel);
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
        CaptAhabAttacked("Thug Assaults Captain Ahab!"),
        CaptAhabDestroyed("Destruction of Captain Ahab's Ship Causes Anger!"),
        CaptConradAttacked("Captain Conrad Comes Under Attack By Criminal!"),
        CaptConradDestroyed("Captain Conrad's Ship Destroyed by Villain!"),
        CaptHuieAttacked("Famed Captain Huie Attacked by Brigand!"),
        CaptHuieDestroyed("Citizens Mourn Destruction of Captain Huie's Ship!");

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

    //TODO
    /*VeryRareEncounters

    "Captain Ahab", "Captain Conrad", "Captain Huie"*/


    enum Encounters implements SimpleValueEnum<String> {
        PretextCaptainAhab("the famous Captain Ahab in a ^1"),
        PretextCaptainConrad("the famous Captain Conrad in a ^1"),
        PretextCaptainHuie("the famous Captain Huie in a ^1"),
        ShipCaptain("Captain"),
        TextFamousCaptain("The Captain requests a brief meeting with you.");

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

    //TODO
    @Override
    public String toString() {
        return "WildQuest{" +
                "} " + super.toString();
    }
}
