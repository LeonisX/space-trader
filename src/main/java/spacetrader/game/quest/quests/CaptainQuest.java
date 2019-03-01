package spacetrader.game.quest.quests;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
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

import java.util.*;

import static spacetrader.game.quest.enums.EventName.*;

public class CaptainQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511600L;

    // Constants
    private static final int SCORE_KILL_CAPTAIN = 100;

    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private Ship captain;

    // Encounters
    private int famousCaptainAttack; // FAMOUS_CAPTAIN_ATTACK
    private int famousCaptainDisabled; // FAMOUS_CAPT_DISABLED

    // Very Rare Encounters
    private Integer captainAhab; // CAPTAIN_AHAB
    private Integer captainConrad; // CAPTAIN_CONRAD
    private Integer captainHuie; // CAPTAIN_HUIE

    private int famousCaptain; // OpponentType

    public CaptainQuest(String id) {
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

        getTransitionMap().put(ENCOUNTER_VERIFY_ATTACK, this::encounterVerifyAttack);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_TEXT, this::encounterGetIntroductoryText);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_ACTION, this::encounterGetIntroductoryAction);
        getTransitionMap().put(ENCOUNTER_GET_ENCOUNTER_SHIP_TEXT, this::encounterGetEncounterShipText);
        getTransitionMap().put(ENCOUNTER_GET_IMAGE_INDEX, this::encounterGetEncounterImageIndex);
        getTransitionMap().put(ENCOUNTER_SHOW_ACTION_BUTTONS, this::encounterShowActionButtons);
        getTransitionMap().put(ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS, this::encounterGetExecuteActionFireShots);

        getTransitionMap().put(ENCOUNTER_UPDATE_ENCOUNTER_TYPE, this::encounterUpdateEncounterType);
        getTransitionMap().put(ENCOUNTER_MEET, this::encounterMeet);
        getTransitionMap().put(ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, this::encounterExecuteActionOpponentDisabled);
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
        if (id.equals(captainAhab)) {
            return VeryRareEncounters.CaptainAhab.getValue();
        } else if (id.equals(captainConrad)) {
            return VeryRareEncounters.CaptainConrad.getValue();
        } else if (id.equals(captainHuie)) {
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
    }

    private void onGenerateOpponentShip(Object object) {
        Ship ship = (Ship) object;
        if (ship.getOpponentType() == famousCaptain) {
            captain = ship;
            ship.setValues(Consts.ShipSpecs[Consts.MaxShip].getType());

            for (int i = 0; i < ship.getShields().length; i++) {
                ship.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
            }

            for (int i = 0; i < ship.getWeapons().length; i++) {
                ship.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
            }

            ship.addEquipment(Consts.Gadgets[GadgetType.NAVIGATING_SYSTEM.castToInt()]);
            ship.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);

            ship.getCrew()[0] = registerNewSpecialCrewMember(1, 1, 1, 1, false);

            ship.setInitialized(true);
        }
    }

    private void encounterVerifyAttack(Object object) {
        if (isVeryRareEncounter()) {
            if (showYesNoAlert(Alerts.EncounterAttackCaptain.getValue()) == DialogResult.YES) {
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
    }

    private void encounterGetIntroductoryText(Object object) {
        if (getEncounter().getEncounterType().equals(captainAhab)) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainAhab.getValue());
        } else if (getEncounter().getEncounterType().equals(captainConrad)) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainConrad.getValue());
        } else if (getEncounter().getEncounterType().equals(captainHuie)) {
            ((StringContainer) object).setValue(Encounters.PretextCaptainHuie.getValue());
        }
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (isVeryRareEncounter()) {
            ((StringContainer) object).setValue(Encounters.TextFamousCaptain.getValue());
        }
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
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == famousCaptainAttack) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
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

    private void encounterExecuteActionOpponentDisabled(Object object) {
        if (getOpponent().getBarCode() == captain.getBarCode()) {
            if (getEncounter().getVeryRareEncounterId() == captainAhab) {
                showAlert(Alerts.EncounterCaptainDisabled.getValue(), VeryRareEncounters.CaptainAhab.getValue());
            } else if (getEncounter().getVeryRareEncounterId() == captainConrad) {
                showAlert(Alerts.EncounterCaptainDisabled.getValue(), VeryRareEncounters.CaptainConrad.getValue());
            } else if (getEncounter().getVeryRareEncounterId() == captainHuie) {
                showAlert(Alerts.EncounterCaptainDisabled.getValue(), VeryRareEncounters.CaptainHuie.getValue());
            }
            ((BooleanContainer) object).setValue(true);
        }
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

        Integer oldEvent = newsEvents.get(newsEvents.size() - 1);
        Integer newEvent = oldEvent + 1;
        newsEvents.remove(oldEvent);
        newsEvents.add(newEvent);
    }

    private boolean isVeryRareEncounter() {
        return getEncounter().getEncounterType().equals(captainAhab)
                || getEncounter().getEncounterType().equals(captainConrad)
                || getEncounter().getEncounterType().equals(captainHuie);
    }

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        EncounterAttackCaptain("Really Attack?", "Famous Captains get famous by, among other things, destroying everyone who attacks them. Do you really want to attack?", "Really Attack", "OK, I Won't"),
        EncounterCaptainDisabled("^1 Disabled", "You have disabled your opponent. Without life support he'll have to hibernate. It is not yet known how soon the Space Corps will find the drifting vessel. In any case, your reputation after this act catastrophically suffered."),
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
                ", famousCaptainAttack=" + famousCaptainAttack +
                ", famousCaptainDisabled=" + famousCaptainDisabled +
                ", captainAhab=" + captainAhab +
                ", captainConrad=" + captainConrad +
                ", captainHuie=" + captainHuie +
                ", famousCaptainOpponentType=" + famousCaptain +
                "} " + super.toString();
    }
}
