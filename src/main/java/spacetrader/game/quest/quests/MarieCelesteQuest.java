package spacetrader.game.quest.quests;

import com.sun.org.apache.xpath.internal.operations.Bool;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
import spacetrader.game.CrewMember;
import spacetrader.game.Ship;
import spacetrader.game.enums.*;
import spacetrader.game.quest.AlertDialog;
import spacetrader.game.quest.I18n;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.StringContainer;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;

import java.util.*;

import static spacetrader.game.quest.enums.EventName.*;

//TODO -captain
public class MarieCelesteQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511601L;

    // Constants
    private static final int SCORE_KILL_CAPTAIN = 100;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private CrewMember captain; // FAMOUS_CAPTAIN, // = 34,crew of famous captain ships

    //TODO
    MARIE_CELESTE, // = 9,
    MARIE_CELESTE_POLICE, // = 10,

    // Encounters
    private int famousCaptainAttack; // FAMOUS_CAPTAIN_ATTACK
    private int famousCaptainDisabled; // FAMOUS_CAPT_DISABLED

    MARIE_CELESTE, // = 0,
    // Very Rare Encounters
    private Integer captainAhab; // CAPTAIN_AHAB
    private Integer captainConrad; // CAPTAIN_CONRAD
    private Integer captainHuie; // CAPTAIN_HUIE

    private int famousCaptain; // OpponentType

    //TODO
    private boolean justLootedMarie = false; // flag to indicate whether player looted Marie Celeste

    public MarieCelesteQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializeTransitionMap();

        famousCaptainAttack = registerNewEncounter();
        famousCaptainDisabled = registerNewEncounter();

        captainAhab = registerNewVeryRareEncounter();
        captainConrad = registerNewVeryRareEncounter();
        captainHuie = registerNewVeryRareEncounter();

        famousCaptain = registerNewOpponentType();

        registerNews(News.values().length);

        registerListener();

        localize();

        log.fine("started...");
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();
        getTransitionMap().put(ON_DETERMINE_VERY_RARE_ENCOUNTER, this::onDetermineVeryRareEncounter);
        getTransitionMap().put(ON_ENCOUNTER_GENERATE_OPPONENT, this::onGenerateOpponentShip);

        getTransitionMap().put(ENCOUNTER_DETERMINE_NON_RANDOM_ENCOUNTER, this::encounterDetermineNonRandomEncounter);
        getTransitionMap().put(ENCOUNTER_VERIFY_ATTACK, this::encounterVerifyAttack);
        getTransitionMap().put(ENCOUNTER_VERIFY_BOARD, this::encounterVerifyBoard);
        getTransitionMap().put(ENCOUNTER_VERIFY_BRIBE, this::encounterVerifyBribe);
        getTransitionMap().put(ENCOUNTER_VERIFY_FLEE, this::encounterVerifyFlee);
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
        return Collections.emptyList();
    }

    @Override
    public Collection<QuestDialog> getQuestDialogs() {
        return Collections.emptyList();
    }

    @Override
    public String getVeryRareEncounter(Integer id) {
        if (getEncounter().getEncounterType().equals(captainAhab)) {
            return VeryRareEncounters.CaptainAhab.getValue();
        } else if (getEncounter().getEncounterType().equals(captainConrad)) {
            return VeryRareEncounters.CaptainConrad.getValue();
        } else if (getEncounter().getEncounterType().equals(captainHuie)) {
            return VeryRareEncounters.CaptainHuie.getValue();
        } else {
            throw new IndexOutOfBoundsException("No such VeryRareEncounter with ID " + id + " in CaptainQuest");
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

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.VeryRareEncounters, Arrays.stream(VeryRareEncounters.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
    }

    @Override
    public void localize() {
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.VeryRareEncounters, Arrays.stream(VeryRareEncounters.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
    }

    // 2. Captain Ahab will trade your Reflective Shield for skill points in Piloting.
    // 3. Captain Conrad will trade your Military Laser for skill points in Engineering.
    // 4. Captain Huie will trade your Military Laser for points in Trading.
    private void onDetermineVeryRareEncounter(Object object) {
        BooleanContainer happened = (BooleanContainer) object;
        if (happened.getValue()) {
            return;
        }

        if (getEncounter().getVeryRareEncounterId() == captainAhab
                && getShip().hasShield(ShieldType.REFLECTIVE) && getCommander().getPilot() < 10
                && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getEncounter().getVeryRareEncounters().remove(captainAhab);
            getEncounter().setEncounterType(captainAhab);
            game.generateOpponent(famousCaptain);
            happened.setValue(true);
        } else if (getEncounter().getVeryRareEncounterId() == captainConrad
                && getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && getCommander().getEngineer() < 10
                && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getEncounter().getVeryRareEncounters().remove(captainConrad);
            getEncounter().setEncounterType(captainConrad);
            game.generateOpponent(famousCaptain);
            happened.setValue(true);
        } else if (getEncounter().getVeryRareEncounterId() == captainHuie
                && getShip().hasWeapon(WeaponType.MILITARY_LASER, true) && getCommander().getTrader() < 10
                && getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
            getEncounter().getVeryRareEncounters().remove(captainHuie);
            getEncounter().setEncounterType(captainHuie);
            game.generateOpponent(famousCaptain);
            happened.setValue(true);
        }

        //TODO
        // Very Rare Random Events:
        // 1. Encounter the abandoned Marie Celeste, which you may loot.
        // IncreaseRandomSkill one or two times, depending on game difficulty.
        if (veryRareEncounterId.equals(MARIE_CELESTE.castToInt())) {
            // Marie Celeste cannot be at Acamar, Qonos, or Zalkon as it may
            // cause problems with the Space Monster, Scorpion, or Dragonfly
            if (game.getClicks() > 1 && commander.getCurrentSystemId() != StarSystemId.Acamar
                    && commander.getCurrentSystemId() != StarSystemId.Zalkon
                    && commander.getCurrentSystemId() != StarSystemId.Qonos) {
                getVeryRareEncounters().remove(MARIE_CELESTE.castToInt());
                setEncounterType(EncounterType.MARIE_CELESTE);
                game.generateOpponent(OpponentType.TRADER);
                for (int i = 0; i < game.getOpponent().getCargo().length; i++) {
                    game.getOpponent().getCargo()[i] = 0;
                }
                game.getOpponent().getCargo()[TradeItemType.NARCOTICS
                        .castToInt()] = Math.min(game.getOpponent().getCargoBays(), 5);
                return true;
            }
    }

    private void onGenerateOpponentShip(Object object) {
        Ship ship = (Ship) object;
        if (ship.getOpponentType() == famousCaptain) {
            ship.setValues(Consts.ShipSpecs[Consts.MaxShip].getType());

            for (int i = 0; i < ship.getShields().length; i++) {
                ship.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
            }

            for (int i = 0; i < ship.getWeapons().length; i++) {
                ship.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
            }

            ship.addEquipment(Consts.Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()]);
            ship.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

            captain = registerNewSpecialCrewMember(10, 10, 10, 10, false);
            ship.getCrew()[0] = captain;

            ship.setInitialized(true);
        }
    }


        private void encounterDetermineNonRandomEncounter(Object object) {
            if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Zalkon && questStatus == STATUS_DRAGONFLY_FLY_ZALKON) {
                setOpponent(dragonfly);
                getEncounter().setEncounterType(getShip().isCloaked() ? dragonflyIgnoreEncounter : dragonflyAttackEncounter);
                ((BooleanContainer) object).setValue(true);
            }
            //TODO
            // ah, just when you thought you were gonna get away with it...
            /*else if (game.getClicks() == 1 && game.getJustLootedMarie()) {
                game.generateOpponent(OpponentType.POLICE);
                setEncounterType(EncounterType.MARIE_CELESTE_POLICE);
                game.setJustLootedMarie(false);

                showEncounter.setValue(true);
            }*/
        }

    private void encounterVerifyAttack(Object object) {
        if (isVeryRareEncounter()) {
            if (showAlert(Alerts.EncounterAttackCaptain.getValue()) == DialogResult.YES) {
                if (getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreVillain) {
                    getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreVillain);
                }

                getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreAttackTrader);

                if (getEncounter().getEncounterType().equals(captainAhab)) {
                    addNewsByIndex(News.CaptAhabAttacked.ordinal());
                } else if (getEncounter().getEncounterType().equals(captainConrad)) {
                    addNewsByIndex(News.CaptConradAttacked.ordinal());
                } else if (getEncounter().getEncounterType().equals(captainHuie)) {
                    addNewsByIndex(News.CaptHuieAttacked.ordinal());
                }

                getEncounter().setEncounterType(famousCaptainAttack);
            } else {
                ((BooleanContainer) object).setValue(false);
            }
        }
        //TODO
        /*case MARIE_CELESTE_POLICE:
        if (commander.getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                || GuiFacade.alert(AlertType.EncounterAttackPolice) == DialogResult.YES) {
            if (commander.getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                commander.setPoliceRecordScore(Consts.PoliceRecordScoreCriminal);
            }

            commander.setPoliceRecordScore(commander.getPoliceRecordScore() + Consts.ScoreAttackPolice);

            if (encounterType != EncounterType.POLICE_FLEE.castToInt()) {
                setEncounterType(EncounterType.POLICE_ATTACK);
            }
        } else {
            attack.setValue(false);
        }
        break;*/
    }

        private void encounterVerifyBoard(Object object){
            if (GuiFacade.alert(AlertType.EncounterMarieCeleste) == DialogResult.YES) {
                board = true;

                int narcs = commander.getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()];

                GuiFacade.performPlundering();

                if (commander.getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()] > narcs) {
                    game.setJustLootedMarie(true);
                }
            }
        }

        private void encounterVerifyBribe(Object object){
            //TODO check executed first
            if (encounterType == EncounterType.MARIE_CELESTE_POLICE.castToInt()) {
                GuiFacade.alert(AlertType.EncounterMarieCelesteNoBribe);
                BooleanContainer executed = (BooleanContainer) object;
                executed.setValue(true);
            }
        }

        private void encounterVerifyFlee(Object object){
            BooleanContainer executed = (BooleanContainer) object;
        }
            //TODO check executed first
            if (encounterType == EncounterType.MARIE_CELESTE_POLICE.castToInt()
                    && GuiFacade.alert(AlertType.EncounterPostMarieFlee) == DialogResult.NO) {
                setEncounterCmdrFleeing(false);
                executed.setValue(true);
                //TODO carefully test
            } else if (encounterType == EncounterType.MARIE_CELESTE_POLICE.castToInt()) {
                int scoreMod = (encounterType == EncounterType.POLICE_INSPECT.castToInt()) ? Consts.ScoreFleePolice
                        : Consts.ScoreAttackPolice;
                int scoreMin = (encounterType == EncounterType.POLICE_INSPECT.castToInt()) ? Consts.PoliceRecordScoreDubious
                        - (game.getDifficultyId() < Difficulty.NORMAL.castToInt() ? 0 : 1)
                        : Consts.PoliceRecordScoreCriminal;

                setEncounterType(EncounterType.POLICE_ATTACK.castToInt());
                commander.setPoliceRecordScore(Math.min(commander.getPoliceRecordScore() + scoreMod, scoreMin));
                executed.setValue(true);
            }
        }

    private void encounterGetIntroductoryText(Object object) {
        if (getEncounter().getEncounterType().equals(captainAhab)) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainAhab.getValue());
        } else if (getEncounter().getEncounterType().equals(captainConrad)) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainConrad.getValue());
        } else if (getEncounter().getEncounterType().equals(captainHuie)) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainHuie.getValue());
        }

        //TODO
/*        case MARIE_CELESTE:
        encounterPretext.setValue(Strings.EncounterPretextMarie);
        break;
        case MARIE_CELESTE_POLICE:
        encounterPretext.setValue(Strings.EncounterPretextPolice);
        break;*/
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (isVeryRareEncounter()) {
            ((StringContainer) object).setValue(Encounters.TextFamousCaptain.getValue());
        }
        //TODO
        /*case MARIE_CELESTE:
        text.setValue(Strings.EncounterTextMarieCeleste);
        break;
        case MARIE_CELESTE_POLICE:
        text.setValue(Strings.EncounterTextPolicePostMarie);
        break;*/
    }

    private void encounterGetEncounterShipText(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttack
                || getEncounter().getEncounterType() == famousCaptainDisabled) {
            ((StringContainer) object).setValue(Encounters.ShipCaptain.getValue());
        }
    }

    private void encounterGetEncounterImageIndex(Object object) {
        IntContainer encounterImage = (IntContainer) object;
        if (isVeryRareEncounter()) {
            encounterImage.setValue(Consts.EncounterImgSpecial);
        }
        if (getEncounter().getEncounterType() == famousCaptainAttack
                || getEncounter().getEncounterType() == famousCaptainDisabled) {
            // Nothing to do
        }
        //TODO
            /*case MARIE_CELESTE:
                encounterImage.setValue(Consts.EncounterImgSpecial);
                break;
            case MARIE_CELESTE_POLICE:
                encounterImage.setValue(Consts.EncounterImgPolice);
                break;*/
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        List<Boolean> visible = (ArrayList<Boolean>) object;
        if (isVeryRareEncounter()) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
            visible.set(Buttons.MEET.ordinal(), true);
        }
        if (getEncounter().getEncounterType() == famousCaptainAttack
                || getEncounter().getEncounterType() == famousCaptainDisabled) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }

        //TODO
        /*case MARIE_CELESTE:
        visible.set(Buttons.BOARD.ordinal(), true);
        visible.set(Buttons.IGNORE.ordinal(), true);
        break;
        case MARIE_CELESTE_POLICE:
        visible.set(Buttons.ATTACK.ordinal(), true);
        visible.set(Buttons.FLEE.ordinal(), true);
        visible.set(Buttons.YIELD.ordinal(), true);
        visible.set(Buttons.BRIBE.ordinal(), true);
        break;*/
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttack) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
        //TODO
        /*case MARIE_CELESTE_POLICE:
        setEncounterCmdrHit(isEncounterExecuteAttack(game.getOpponent(), commander.getShip(), getEncounterCmdrFleeing()));
        setEncounterOppHit(!getEncounterCmdrFleeing()
                && isEncounterExecuteAttack(commander.getShip(), game.getOpponent(), false));
        break;*/
    }

    private void encounterUpdateEncounterType(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttack && game.getOpponentDisabled()) {
            getEncounter().setEncounterType(famousCaptainDisabled);
        }
    }

    private void encounterMeet(Object object) {
        Alerts alert = null;
        int skill = 0;
        EquipmentType equipType = EquipmentType.GADGET;
        Object equipSubType = null;

        if (getEncounter().getEncounterType().equals(captainAhab)) {
            // Trade a reflective shield for skill points in piloting?
            alert = Alerts.MeetCaptainAhab;
            equipType = EquipmentType.SHIELD;
            equipSubType = ShieldType.REFLECTIVE;
            skill = SkillType.PILOT.castToInt();

        } else if (getEncounter().getEncounterType().equals(captainConrad)) {
            // Trade a military laser for skill points in engineering?
            alert = Alerts.MeetCaptainConrad;
            equipType = EquipmentType.WEAPON;
            equipSubType = WeaponType.MILITARY_LASER;
            skill = SkillType.ENGINEER.castToInt();

        } else if (getEncounter().getEncounterType().equals(captainHuie)) {
            // Trade a military laser for skill points in trading?
            alert = Alerts.MeetCaptainHuie;
            equipType = EquipmentType.WEAPON;
            equipSubType = WeaponType.MILITARY_LASER;
            skill = SkillType.TRADER.castToInt();
        }

        if (null == alert || showYesNoAlert(alert.getValue()) != DialogResult.YES) {
            return;
        }

        // Remove the equipment we're trading.
        getShip().removeEquipment(equipType, equipSubType);

        // Add points to the appropriate skill - two points if
        // beginner-normal, one otherwise.
        getCommander().getSkills()[skill] = Math.min(Consts.MaxSkill, getCommander().getSkills()[skill]
                + (getDifficultyId() <= Difficulty.NORMAL.castToInt() ? 2 : 1));

        showAlert(Alerts.SpecialTrainingCompleted.getValue());
    }

    private void encounterOnEncounterWon(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttack) { //FAMOUS_CAPTAIN_ATTACK
            getCommander().setKillsTrader(getCommander().getKillsTrader() + 1);
            if (getCommander().getReputationScore() < Consts.ReputationScoreDangerous) {
                getCommander().setReputationScore(Consts.ReputationScoreDangerous);
            } else {
                getCommander().setReputationScore(getCommander().getReputationScore() + SCORE_KILL_CAPTAIN);
            }

            // bump news flag from attacked to ship destroyed
            replaceLastAttackedEventWithDestroyedEvent();
        }
    }

    private void replaceLastAttackedEventWithDestroyedEvent() {
        List<Integer> newsEvents = getNews().getNewsEvents();

        int oldEvent = newsEvents.get(newsEvents.size() - 1);
        int newEvent = oldEvent + 1;
        newsEvents.remove(oldEvent);
        newsEvents.add(newEvent);
    }

    private boolean isVeryRareEncounter() {
        return Objects.equals(getEncounter().getEncounterType(), captainAhab)
                || getEncounter().getEncounterType().equals(captainConrad)
                || getEncounter().getEncounterType().equals(captainHuie);
    }



    public static String AlertsEncounterMarieCelesteTitle = "Engage Marie Celeste";
    public static String AlertsEncounterMarieCelesteMessage = "The ship is empty: there is nothing in the ship's log, but the crew has vanished, leaving food on the tables and cargo in the holds. Do you wish to offload the cargo to your own holds? ";
    public static String AlertsEncounterMarieCelesteAccept = "Yes, Take Cargo";
    public static String AlertsEncounterMarieCelesteNoBribeTitle = "No Bribe";
    public static String AlertsEncounterMarieCelesteNoBribeMessage = "We'd love to take your money, but Space Command already knows you've got illegal goods onboard.";

    public static String AlertsEncounterPostMarieTitle = "Contraband Removed";
    public static String AlertsEncounterPostMarieMessage = "The Customs Police confiscated all of your illegal cargo, but since you were cooperative, you avoided stronger fines or penalties.";
    public static String AlertsEncounterPostMarieFleeTitle = "Criminal Act!";
    public static String AlertsEncounterPostMarieFleeMessage = "Are you sure you want to do that? The Customs Police know you have engaged in criminal activity, and will report it!";
    public static String AlertsEncounterPostMarieFleeAccept = "Yes, I still want to";
    public static String AlertsEncounterPostMarieFleeCancel = "OK, I won't";

/*    EncounterMarieCeleste,
    EncounterMarieCelesteNoBribe,
    EncounterPostMarie,
    EncounterPostMarieFlee,


            case EncounterMarieCeleste:
            return new FormAlert(AlertsEncounterMarieCelesteTitle, AlertsEncounterMarieCelesteMessage,
                                 AlertsEncounterMarieCelesteAccept, DialogResult.YES, AlertsNo, DialogResult.NO, args);
            case EncounterMarieCelesteNoBribe:
            return new FormAlert(AlertsEncounterMarieCelesteNoBribeTitle, AlertsEncounterMarieCelesteNoBribeMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterPostMarie:
            return new FormAlert(AlertsEncounterPostMarieTitle, AlertsEncounterPostMarieMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterPostMarieFlee:
            return new FormAlert(AlertsEncounterPostMarieFleeTitle, AlertsEncounterPostMarieFleeMessage,
                                 AlertsEncounterPostMarieFleeAccept, DialogResult.YES, AlertsEncounterPostMarieFleeCancel, DialogResult.NO, args);
            */

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

    public static String EncounterPretextMarie = "a drifting ^1";
    //TODO need to enhance code now this string is unused
    public static String EncounterPretextMariePolice = "the Customs Police in a ^1";
    public static String EncounterTextMarieCeleste = "The Marie Celeste appears to be completely abandoned.";
    public static String EncounterTextPolicePostMarie = "\"We know you removed illegal goods from the Marie Celeste. You must give them up at once!\"";




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

    "Marie Celeste"

    enum VeryRareEncounters implements SimpleValueEnum<String> {
        CaptainAhab("Captain Ahab"),
        CaptainConrad("Captain Conrad"),
        CaptainHuie("Captain Huie");

        private String value;

        VeryRareEncounters(String value) {
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

    @Override
    public String toString() {
        return "CaptainQuest{" +
                "captain=" + captain +
                ", famousCaptainAttack=" + famousCaptainAttack +
                ", famousCaptainDisabled=" + famousCaptainDisabled +
                ", captainAhab=" + captainAhab +
                ", captainConrad=" + captainConrad +
                ", captainHuie=" + captainHuie +
                ", famousCaptainOpponentType=" + famousCaptain +
                "} " + super.toString();
    }
}
