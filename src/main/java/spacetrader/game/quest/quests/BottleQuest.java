package spacetrader.game.quest.quests;

import spacetrader.controls.Rectangle;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
import spacetrader.game.Ship;
import spacetrader.game.ShipSpec;
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

public class BottleQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511602L;

    // Constants
    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    private static ShipSpec shipSpec = new ShipSpec(ShipType.QUEST, Size.SMALL,
            0, 0, 0, 0, 1, 1, 1, 10, 1, 100, 0,
            Activity.NA, Activity.NA, Activity.NA, TechLevel.UNAVAILABLE);

    // Encounters
    private int bottleGoodEncounter; // BOTTLE_GOOD
    private int bottleOldEncounter; // BOTTLE_OLD

    // Very Rare Encounters
    private Integer bottleGood; // BOTTLE_OLD
    private Integer bottleOld; // BOTTLE_GOOD

    private int bottle; // OpponentType

    private int shipSpecId;

    private static final Rectangle SHIP_IMAGE_OFFSET = new Rectangle(9, 0, 46, 0); // Bottle
    private static final Integer SHIP_IMAGE_INDEX = 12;

    public BottleQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializeTransitionMap();

        bottleGoodEncounter = registerNewEncounter();
        bottleOldEncounter = registerNewEncounter();

        bottleGood = registerNewVeryRareEncounter();
        bottleOld = registerNewVeryRareEncounter();

        bottle = registerNewOpponentType();

        registerListener();

        localize();

        log.fine("started...");
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();
        //getTransitionMap().put(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
        getTransitionMap().put(ON_AFTER_SHIP_SPECS_INITIALIZED, this::afterShipSpecInitialized);

        getTransitionMap().put(ON_DETERMINE_VERY_RARE_ENCOUNTER, this::onDetermineVeryRareEncounter);
        getTransitionMap().put(ON_ENCOUNTER_GENERATE_OPPONENT, this::onGenerateOpponentShip);

        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_TEXT, this::encounterGetIntroductoryText);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_ACTION, this::encounterGetIntroductoryAction);
        getTransitionMap().put(ENCOUNTER_GET_IMAGE_INDEX, this::encounterGetEncounterImageIndex);
        getTransitionMap().put(ENCOUNTER_SHOW_ACTION_BUTTONS, this::encounterShowActionButtons);
        getTransitionMap().put(ENCOUNTER_IS_DISABLEABLE, this::encounterIsDisableable);

        getTransitionMap().put(ENCOUNTER_DRINK, this::encounterDrink);
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
        if (id.equals(bottleGood)) {
            return VeryRareEncounters.GoodTonic.getValue();
        } else if (id.equals(bottleOld)) {
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

    @Override
    public String getCrewMemberName(int id) {
        return CrewNames.values()[getSpecialCrewIds().indexOf(id)].getValue();
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
        return CrewNames.Bottle.getValue();
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

    private void afterShipSpecInitialized(Object object) {
        shipSpecId = registerNewShipSpec(shipSpec);
    }

    private void onGenerateOpponentShip(Object object) {
        Ship ship = (Ship) object;
        if (ship.getOpponentType() == bottle && !ship.isInitialized()) {
            ship.setValues(shipSpec);
            ship.setId(shipSpecId);
            ship.getCrew()[0] = registerNewSpecialCrewMember(10, 10, 10, 10, false);
            ship.setInitialized(true);
        }
    }

    private void encounterGetIntroductoryText(Object object) {
        if (getEncounter().getEncounterType().equals(bottleGoodEncounter)
                || getEncounter().getEncounterType().equals(bottleOldEncounter)) {

            ((StringContainer) object).setValue(Encounters.PretextBottle.getValue());
        }
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (getEncounter().getEncounterType().equals(bottleGoodEncounter)
                || getEncounter().getEncounterType().equals(bottleOldEncounter)) {

            ((StringContainer) object).setValue(Encounters.TextBottle.getValue());
        }
    }

    private void encounterGetEncounterImageIndex(Object object) {
        IntContainer encounterImage = (IntContainer) object;
        if (getEncounter().getEncounterType().equals(bottleGoodEncounter)
                || getEncounter().getEncounterType().equals(bottleOldEncounter)) {

            encounterImage.setValue(Consts.EncounterImgSpecial);
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        if (getEncounter().getEncounterType().equals(bottleGoodEncounter)
                || getEncounter().getEncounterType().equals(bottleOldEncounter)) {

            List<Boolean> visible = (ArrayList<Boolean>) object;
            visible.set(Buttons.DRINK.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }
    }

    private void encounterIsDisableable(Object object) {
        if (shipSpec.getBarCode() == getOpponent().getBarCode()) {
            ((BooleanContainer) object).setValue(false);
        }
    }

    private void encounterDrink(Object object) {
        if (showYesNoAlert(Alerts.EncounterDrinkContents.getValue()) == DialogResult.YES) {
            if (getEncounter().getEncounterType() == bottleGoodEncounter) {
                // two points if you're on beginner-normal, one otherwise
                getCommander().increaseRandomSkill();
                if (game.getDifficultyId() <= Difficulty.NORMAL.castToInt()) {
                    getCommander().increaseRandomSkill();
                }
                showAlert(Alerts.EncounterTonicConsumedGood.getValue());
            } else {
                tonicTweakRandomSkill();
                showAlert(Alerts.EncounterTonicConsumedStrange.getValue());
            }
        }
    }

    // *************************************************************************
    // Randomly tweak the skills.
    // *************************************************************************
    private void tonicTweakRandomSkill() {
        int[] skills = getCommander().getSkills();
        int[] oldSkills = Arrays.copyOf(skills, skills.length);

        if (getDifficultyId() < Difficulty.HARD.castToInt()) {
            // add one to a random skill, subtract one from a random skill
            while (skills[0] == oldSkills[0] && skills[1] == oldSkills[1] && skills[2] == oldSkills[2]
                    && skills[3] == oldSkills[3]) {
                getCommander().changeRandomSkill(1);
                getCommander().changeRandomSkill(-1);
            }
        } else {
            // add one to two random skills, subtract three from one random skill
            getCommander().changeRandomSkill(1);
            getCommander().changeRandomSkill(1);
            getCommander().changeRandomSkill(-3);
        }
    }


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

    @Override
    public String toString() {
        return "BottleQuest{" +
                ", bottleGoodEncounter=" + bottleGoodEncounter +
                ", bottleOldEncounter=" + bottleOldEncounter +
                ", bottleGood=" + bottleGood +
                ", bottleOld=" + bottleOld +
                ", bottle=" + bottle +
                ", shipSpecId=" + shipSpecId +
                "} " + super.toString();
    }
}
