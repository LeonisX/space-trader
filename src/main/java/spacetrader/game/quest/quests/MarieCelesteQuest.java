package spacetrader.game.quest.quests;

import spacetrader.controls.Rectangle;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
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

public class MarieCelesteQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511601L;

    // Constants
    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 1;

    // Encounters
    private int mariaCelesteStart; // MARIE_CELESTE
    private int mariaCelestePolice; // MARIE_CELESTE_POLICE

    // Very Rare Encounters
    private Integer mariaCeleste; // MARIE_CELESTE

    private boolean justLootedMarie = false; // flag to indicate whether player looted Marie Celeste

    public MarieCelesteQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializeTransitionMap();

        mariaCelesteStart = registerNewEncounter();
        mariaCelestePolice = registerNewEncounter();

        mariaCeleste = registerNewVeryRareEncounter();

        registerListener();

        localize();

        log.fine("started...");
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();
        getTransitionMap().put(ON_DETERMINE_VERY_RARE_ENCOUNTER, this::onDetermineVeryRareEncounter);

        getTransitionMap().put(ENCOUNTER_DETERMINE_NON_RANDOM_ENCOUNTER, this::encounterDetermineNonRandomEncounter);
        getTransitionMap().put(ENCOUNTER_VERIFY_ATTACK, this::encounterVerifyAttack);
        getTransitionMap().put(ENCOUNTER_VERIFY_BOARD, this::encounterVerifyBoard);
        getTransitionMap().put(ENCOUNTER_VERIFY_BRIBE, this::encounterVerifyBribe);
        getTransitionMap().put(ENCOUNTER_VERIFY_FLEE, this::encounterVerifyFlee);
        getTransitionMap().put(ENCOUNTER_VERIFY_YIELD, this::encounterVerifyYield);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_TEXT, this::encounterGetIntroductoryText);
        getTransitionMap().put(ENCOUNTER_GET_INTRODUCTORY_ACTION, this::encounterGetIntroductoryAction);
        getTransitionMap().put(ENCOUNTER_GET_IMAGE_INDEX, this::encounterGetEncounterImageIndex);
        getTransitionMap().put(ENCOUNTER_SHOW_ACTION_BUTTONS, this::encounterShowActionButtons);
        getTransitionMap().put(ENCOUNTER_GET_EXECUTE_ACTION_FIRE_SHOTS, this::encounterGetExecuteActionFireShots);
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
        if (id.equals(mariaCeleste)) {
            return VeryRareEncounters.MarieCeleste.getValue();
        } else {
            throw new IndexOutOfBoundsException("No such VeryRareEncounter with ID " + id + " in MarieCelesteQuest");
        }
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.VeryRareEncounters, Arrays.stream(VeryRareEncounters.values()));
    }

    @Override
    public void localize() {
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.VeryRareEncounters, Arrays.stream(VeryRareEncounters.values()));
    }

    // Very Rare Random Events:
    // 1. Encounter the abandoned Marie Celeste, which you may loot.
    // IncreaseRandomSkill one or two times, depending on game difficulty.
    private void onDetermineVeryRareEncounter(Object object) {
        BooleanContainer happened = (BooleanContainer) object;
        if (happened.getValue()) {
            return;
        }

        if (getEncounter().getVeryRareEncounterId() == mariaCeleste) {
            // Marie Celeste cannot be at Acamar, Qonos, or Zalkon as it may
            // cause problems with the Space Monster, Scorpion, or Dragonfly
            if (game.getClicks() > 1 && getCurrentSystemId() != StarSystemId.Acamar
                    && getCurrentSystemId() != StarSystemId.Zalkon
                    && getCurrentSystemId() != StarSystemId.Qonos) {
                getEncounter().getVeryRareEncounters().remove(mariaCeleste);
                getEncounter().setEncounterType(mariaCelesteStart);
                game.generateOpponent(OpponentType.TRADER);
                for (int i = 0; i < game.getOpponent().getCargo().length; i++) {
                    game.getOpponent().getCargo()[i] = 0;
                }
                game.getOpponent().getCargo()[TradeItemType.NARCOTICS
                        .castToInt()] = Math.min(game.getOpponent().getCargoBays(), 5);
                happened.setValue(true);
            }
        }
    }

    private void encounterDetermineNonRandomEncounter(Object object) {
        if (game.getClicks() == 1 && justLootedMarie) {
            game.generateOpponent(OpponentType.POLICE);
            getEncounter().setEncounterType(mariaCelestePolice);
            justLootedMarie = false;

            ((BooleanContainer) object).setValue(true);
        }
    }

    private void encounterVerifyAttack(Object object) {
        if (getEncounter().getEncounterType() == mariaCelestePolice) {
            if (getCommander().getPoliceRecordScore() <= Consts.PoliceRecordScoreCriminal
                    || GuiFacade.alert(AlertType.EncounterAttackPolice) == DialogResult.YES) {

                if (getCommander().getPoliceRecordScore() > Consts.PoliceRecordScoreCriminal) {
                    getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreCriminal);
                }

                getCommander().setPoliceRecordScore(getCommander().getPoliceRecordScore() + Consts.ScoreAttackPolice);

                getEncounter().setEncounterType(EncounterType.POLICE_ATTACK);

            } else {
                ((BooleanContainer) object).setValue(false);
            }
        }
    }

    private void encounterVerifyBoard(Object object) {
        if (showYesNoAlert(Alerts.EncounterMarieCeleste.getValue()) == DialogResult.YES) {
            BooleanContainer board = (BooleanContainer) object;
            board.setValue(true);

            int narcs = getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()];

            GuiFacade.performPlundering();

            if (getShip().getCargo()[TradeItemType.NARCOTICS.castToInt()] > narcs) {
                justLootedMarie = true;
            }
        }
    }

    private void encounterVerifyBribe(Object object) {
        BooleanContainer matched = (BooleanContainer) object;
        if (!matched.getValue() && getEncounter().getEncounterType() == mariaCelestePolice) {
            showAlert(Alerts.EncounterMarieCelesteNoBribe.getValue());
            matched.setValue(true);
        }
    }

    private void encounterVerifyFlee(Object object) {
        BooleanContainer matched = (BooleanContainer) object;

        if (matched.getValue() || getEncounter().getEncounterType() != mariaCelestePolice) {
            return;
        }

        if (showYesNoAlert(Alerts.EncounterPostMarieFlee.getValue()) == DialogResult.NO) {
            getEncounter().setEncounterCmdrFleeing(false);
            matched.setValue(true);
            //TODO carefully test
        } else if (getEncounter().getEncounterType() == mariaCelestePolice) {
            int scoreMod = Consts.ScoreAttackPolice;
            int scoreMin = Consts.PoliceRecordScoreCriminal;

            getEncounter().setEncounterType(EncounterType.POLICE_ATTACK.castToInt());
            getCommander().setPoliceRecordScore(Math.min(getCommander().getPoliceRecordScore() + scoreMod, scoreMin));
            matched.setValue(true);
        }
    }

    private void encounterVerifyYield(Object object) {
        if (getEncounter().getEncounterType() == mariaCelestePolice) {
            showAlert(Alerts.EncounterPostMarie.getValue());
        }
    }

    private void encounterGetIntroductoryText(Object object) {
        if (getEncounter().getEncounterType().equals(mariaCelesteStart)) {
            ((StringContainer) object).setValue(Encounters.PretextMarieCeleste.getValue());
        } else if (getEncounter().getEncounterType().equals(mariaCelestePolice)) {
            ((StringContainer) object).setValue(Encounters.PretextMariePolice.getValue());
        }
    }

    private void encounterGetIntroductoryAction(Object object) {
        if (getEncounter().getEncounterType() == mariaCelesteStart) {
            ((StringContainer) object).setValue(Encounters.TextMarieCeleste.getValue());
        }
        if (getEncounter().getEncounterType() == mariaCelestePolice) {
            ((StringContainer) object).setValue(Encounters.TextPolicePostMarie.getValue());
        }
    }

    private void encounterGetEncounterImageIndex(Object object) {
        IntContainer encounterImage = (IntContainer) object;
        if (getEncounter().getEncounterType() == mariaCelesteStart) {
            encounterImage.setValue(Consts.EncounterImgSpecial);
        }
        if (getEncounter().getEncounterType() == mariaCelestePolice) {
            encounterImage.setValue(Consts.EncounterImgPolice);
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterShowActionButtons(Object object) {
        List<Boolean> visible = (ArrayList<Boolean>) object;
        if (getEncounter().getEncounterType() == mariaCelesteStart) {
            visible.set(Buttons.BOARD.ordinal(), true);
            visible.set(Buttons.IGNORE.ordinal(), true);
        }
        if (getEncounter().getEncounterType() == mariaCelestePolice) {
            visible.set(Buttons.ATTACK.ordinal(), true);
            visible.set(Buttons.FLEE.ordinal(), true);
            visible.set(Buttons.YIELD.ordinal(), true);
            visible.set(Buttons.BRIBE.ordinal(), true);
        }
    }

    private void encounterGetExecuteActionFireShots(Object object) {
        if (getEncounter().getEncounterType() == mariaCelestePolice) {
            getEncounter().setEncounterCmdrHit(getEncounter().isEncounterExecuteAttack(game.getOpponent(), getShip(), getEncounter().getEncounterCmdrFleeing()));
            getEncounter().setEncounterOppHit(!getEncounter().getEncounterCmdrFleeing()
                    && getEncounter().isEncounterExecuteAttack(getShip(), game.getOpponent(), false));
        }
    }

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        EncounterMarieCeleste("Engage Marie Celeste", "The ship is empty: there is nothing in the ship's log, but the crew has vanished, leaving food on the tables and cargo in the holds. Do you wish to offload the cargo to your own holds?", "Yes, Take Cargo"),
        EncounterMarieCelesteNoBribe("No Bribe", "We'd love to take your money, but Space Command already knows you've got illegal goods onboard."),
        EncounterPostMarie("Contraband Removed", "The Customs Police confiscated all of your illegal cargo, but since you were cooperative, you avoided stronger fines or penalties."),
        EncounterPostMarieFlee("Criminal Act!", "Are you sure you want to do that? The Customs Police know you have engaged in criminal activity, and will report it!", "Yes, I still want to", "OK, I won't");

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

    enum Encounters implements SimpleValueEnum<String> {
        PretextMarieCeleste("a drifting ^1"),
        PretextMariePolice("the Customs Police in a ^1"),
        TextMarieCeleste("The Marie Celeste appears to be completely abandoned."),
        TextPolicePostMarie("\"We know you removed illegal goods from the Marie Celeste. You must give them up at once!\"");

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
        MarieCeleste("Marie Celeste");

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

    @Override
    public String toString() {
        return "MarieCelesteQuest{" +
                "mariaCelesteStart=" + mariaCelesteStart +
                ", mariaCelestePolice=" + mariaCelestePolice +
                ", mariaCeleste=" + mariaCeleste +
                ", justLootedMarie=" + justLootedMarie +
                "} " + super.toString();
    }
}
