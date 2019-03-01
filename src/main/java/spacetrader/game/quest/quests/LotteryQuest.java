package spacetrader.game.quest.quests;

import spacetrader.game.enums.Difficulty;
import spacetrader.game.quest.I18n;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.Res;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.util.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.Repeatable.ONE_TIME;

public class LotteryQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511501L;

    private static final Repeatable REPEATABLE = ONE_TIME;
    private static final int OCCURRENCE = 0;

    public LotteryQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new FirstPhase());
        initializeTransitionMap();

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
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();
        getTransitionMap().put(ON_AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
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

    private void onAfterGameInitialize(Object object) {
        log.fine("");
        phases.get(QuestPhases.LotteryWinner).setStarSystemId(getCurrentSystemId());
    }

    private void onBeforeSpecialButtonShow(Object object) {
        log.fine(getCurrentSystemId() + " ~ " + phases.get(QuestPhases.LotteryWinner).getStarSystemIds());
        if (phases.get(QuestPhases.LotteryWinner).canBeExecuted() && (isQuestIsInactive() || isQuestIsActive())) {
            log.fine("executed");
            setQuestState(QuestState.ACTIVE);
            showSpecialButton(object, QuestPhases.LotteryWinner.getValue().getTitle());
        } else {
            log.fine("skipped");
        }
    }

    private void onSpecialButtonClicked(Object object) {
        log.fine("");
        if (phases.get(QuestPhases.LotteryWinner).canBeExecuted() && isQuestIsActive()) {
            showDialogAndProcessResult(object, QuestPhases.LotteryWinner.getValue(), () -> phases.get(QuestPhases.LotteryWinner).successFlow());
            game.getQuestSystem().unSubscribeAll(getQuest());
            phases.get(QuestPhases.LotteryWinner).confirmQuestPhase();
            setQuestState(QuestState.FINISHED);
            log.fine("executed");
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (phases.get(QuestPhases.LotteryWinner).canBeExecuted() && (isQuestIsInactive() || isQuestIsActive())) {
            String title = Functions.stringVars(QuestClues.LotteryWinner.getValue(), getStarSystem(phases.get(QuestPhases.LotteryWinner).getStarSystemId()).getName());
            ((ArrayList<String>) object).add(title);
            log.fine(title);
        } else {
            log.fine("skipped");
        }
    }

    class FirstPhase extends Phase {

        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && (getDifficultyId() < Difficulty.NORMAL.castToInt());
        }

        @Override
        public void successFlow() {
            unRegisterAllOperations();
        }

        @Override
        public String toString() {
            return "FirstPhase{} " + super.toString();
        }
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        LotteryWinner(new QuestDialog(-1000, ALERT, "Lottery Winner", "You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account."));

        private QuestDialog value;
        QuestPhases(QuestDialog value) { this.value = value; }
        @Override public QuestDialog getValue() { return value; }
        @Override public void setValue(QuestDialog value) { this.value = value; }
    }

    private EnumMap<QuestPhases, Phase> phases = new EnumMap<>(QuestPhases.class);

    enum QuestClues implements SimpleValueEnum<String> {
        LotteryWinner("Get a prize at the ^1 spaceport.");

        private String value;
        QuestClues(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    @Override
    public String toString() {
        return "LotteryQuest{} " + super.toString();
    }
}
