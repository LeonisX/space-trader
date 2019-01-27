package spacetrader.game.quest.quests;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
import spacetrader.game.CrewMember;
import spacetrader.game.Ship;
import spacetrader.game.Strings;
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
public class BottleQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511602L;

    // Constants
    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    //TODO need???
    private CrewMember captain; // FAMOUS_CAPTAIN, // = 34,crew of famous captain ships

    // Encounters
    private int bottleGoodEncounter; // BOTTLE_GOOD
    private int bottleOldEncounter; // BOTTLE_OLD

    // Very Rare Encounters
    private Integer bottleGood; // BOTTLE_OLD
    private Integer bottleOld; // BOTTLE_GOOD

    //TODO need???
    private int bottle; // OpponentType

    public BottleQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializeTransitionMap();

        bottleGoodEncounter = registerNewEncounter();
        bottleOldEncounter = registerNewEncounter();

        bottleGood = registerNewVeryRareEncounter();
        bottleOld = registerNewVeryRareEncounter();

        bottle = registerNewOpponentType();

        registerListener();

        //localize();
        dumpAllStrings();

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
        getTransitionMap().put(ENCOUNTER_DRINK, this::encounterDrink);
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
        if (getEncounter().getEncounterType().equals(bottleGood)) {
            return VeryRareEncounters.GoodTonic.getValue();
        } else if (getEncounter().getEncounterType().equals(bottleOld)) {
            return VeryRareEncounters.DatedTonic.getValue();
        } else {
            throw new IndexOutOfBoundsException("No such VeryRareEncounter with ID " + id + " in BottleQuest");
        }
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    //TODO need?????
    @Override
    public String getCrewMemberName(int id) {
        return CrewNames.values()[getSpecialCrewIds().indexOf(id)].getValue();
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.VeryRareEncounters, Arrays.stream(VeryRareEncounters.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
    }

    @Override
    public void localize() {
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.VeryRareEncounters, Arrays.stream(VeryRareEncounters.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
    }

    // Very Rare Random Events:
    // 5. Encounter an out-of-date bottle of Captain Marmoset's Skill Tonic.
    // This will affect skills depending on game difficulty level.
    // 6. Encounter a good bottle of Captain Marmoset's Skill Tonic, which will invoke
    // IncreaseRandomSkill one or two times, depending on game difficulty.
    private void onDetermineVeryRareEncounter(Object object) {
        BooleanContainer happened = (BooleanContainer) object;
        if (happened.getValue()) {
            return;
        }

        if (getEncounter().getVeryRareEncounterId() == bottleOld) {
            getEncounter().getVeryRareEncounters().remove(bottleOld);
            getEncounter().setEncounterType(bottleOldEncounter);
            game.generateOpponent(bottle);
            happened.setValue(true);
        } else if (getEncounter().getVeryRareEncounterId() == bottleGood) {
            getEncounter().getVeryRareEncounters().remove(bottleGood);
            getEncounter().setEncounterType(bottleGoodEncounter);
            game.generateOpponent(bottle);
            happened.setValue(true);
        }
    }

    //TODO specs?????
    private void onGenerateOpponentShip(Object object) {
        Ship ship = (Ship) object;
        if (ship.getOpponentType() == bottle) {
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
        /*case BOTTLE_GOOD:
        case BOTTLE_OLD:
        encounterPretext.setValue(Strings.EncounterPretextBottle);
        break;*/
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (isVeryRareEncounter()) {
            ((StringContainer) object).setValue(Encounters.TextFamousCaptain.getValue());
        }
        //TODO
        /*case BOTTLE_GOOD:
        case BOTTLE_OLD:
        text.setValue(Strings.EncounterTextBottle);
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
        /*case BOTTLE_GOOD:
        case BOTTLE_OLD:
        encounterImage.setValue(Consts.EncounterImgSpecial);
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
        /*case BOTTLE_GOOD:
        case BOTTLE_OLD:
        visible.set(Buttons.DRINK.ordinal(), true);
        visible.set(Buttons.IGNORE.ordinal(), true);
        break;*/
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

    private void encounterDrink(Object object) {
        if (GuiFacade.alert(AlertType.EncounterDrinkContents) == DialogResult.YES) {
            if (encounterType == EncounterType.BOTTLE_GOOD.castToInt()) {
                // two points if you're on beginner-normal, one otherwise
                commander.increaseRandomSkill();
                if (game.getDifficultyId() <= Difficulty.NORMAL.castToInt()) {
                    commander.increaseRandomSkill();
                }
                GuiFacade.alert(AlertType.EncounterTonicConsumedGood);
            } else {
                commander.tonicTweakRandomSkill();
                GuiFacade.alert(AlertType.EncounterTonicConsumedStrange);
            }
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

        int oldEvent = newsEvents.get(newsEvents.size() - 1);
        int newEvent = oldEvent + 1;
        newsEvents.remove(oldEvent);
        newsEvents.add(newEvent);
    }

    //TODO need???
    private boolean isVeryRareEncounter() {
        return Objects.equals(getEncounter().getEncounterType(), captainAhab)
                || getEncounter().getEncounterType().equals(captainConrad)
                || getEncounter().getEncounterType().equals(captainHuie);
    }


/*
    EncounterDrinkContents,
            case EncounterDrinkContents:
            return new FormAlert(AlertsEncounterDrinkContentsTitle, AlertsEncounterDrinkContentsMessage,
                                 AlertsEncounterDrinkContentsAccept, DialogResult.YES, AlertsNo, DialogResult.NO, args);

            case EncounterTonicConsumedGood:
            return new FormAlert(AlertsEncounterTonicConsumedGoodTitle, AlertsEncounterTonicConsumedGoodMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case EncounterTonicConsumedStrange:
            return new FormAlert(AlertsEncounterTonicConsumedStrangeTitle, AlertsEncounterTonicConsumedStrangeMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            */

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        EncounterDrinkContents("Drink Contents?", "You have come across an extremely rare bottle of Captain Marmoset's Amazing Skill Tonic! The \"use-by\" date is illegible, but might still be good.  Would you like to drink it?", "Yes, Drink It"),
        EncounterTonicConsumedGood("Tonic Consumed", "Mmmmm. Captain Marmoset's Amazing Skill Tonic not only fills you with energy, but tastes like a fine single-malt."),
        EncounterTonicConsumedStrange("Tonic Consumed", "While you don't know what it was supposed to taste like, you get the feeling that this dose of tonic was a bit off.");

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

    enum Encounters implements SimpleValueEnum<String> {
        PretextBottle("a floating ^1"),
        TextBottle("It appears to be a rare bottle of Captain Marmoset's Skill Tonic!");

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
        DatedTonic("Dated Tonic"),
        GoodTonic("Good Tonic");

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

    //TODO ship name - need????
    //"Bottle",

    enum CrewNames implements SimpleValueEnum<String> {
        Bottle("Bottle");

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
