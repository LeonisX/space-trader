package spacetrader.game.quest;

import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.util.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.Repeatable.DISPOSABLE;

class LotteryQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511501L;

    private static final Repeatable REPEATABLE = DISPOSABLE;
    private static final int OCCURRENCE = 0;
    private static final int CASH_TO_SPEND = -1000;

    public LotteryQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);
        initializePhases(Phases.values(), new FirstPhase());
        initializeTransitionMap();

        registerListener();

        localize();

        log.fine("started...");
    }

    private void initializePhases(Phases[] values, Phase... phases) {
        for (int i = 0; i < phases.length; i++) {
            this.phases.put(values[i], phases[i]);
            phases[i].setQuest(this);
            phases[i].setPhaseEnum(values[i]);
        }
    }

    @Override
    public void dumpAllStrings() {
        System.out.println("\n\n## Lottery Quest");
        I18n.dumpPhases(Arrays.stream(Phases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(Quests.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(Phases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(Quests.values()));
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
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    private void onAfterGameInitialize(Object object) {
        log.fine("");
        phases.get(Phases.LotteryWinner).setStarSystemId(Game.getCurrentSystemId());
    }

    private void onBeforeSpecialButtonShow(Object object) {
        log.fine(Game.getCurrentSystemId() + " ~ " + phases.get(Phases.LotteryWinner).getStarSystemId());
        if (phases.get(Phases.LotteryWinner).canBeExecuted() && (isQuestIsInactive() || isQuestIsActive())) {
            log.fine("executed");
            setQuestState(QuestState.ACTIVE);
            showSpecialButton(object, Phases.LotteryWinner.getValue().getTitle());
        } else {
            log.fine("skipped");
        }
    }

    private void onSpecialButtonClicked(Object object) {
        log.fine("");
        if (phases.get(Phases.LotteryWinner).canBeExecuted() && isQuestIsActive()) {
            showDialogAndProcessResult(object, Phases.LotteryWinner.getValue(), () -> phases.get(Phases.LotteryWinner).successFlow());
            setQuestState(QuestState.FINISHED);
            log.fine("executed");
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (phases.get(Phases.LotteryWinner).canBeExecuted() && (isQuestIsInactive() || isQuestIsActive())) {
            String title = Functions.stringVars(Quests.LotteryWinner.getValue(), Game.getStarSystem(phases.get(Phases.LotteryWinner).getStarSystemId()).getName());
            ((ArrayList<String>) object).add(title);
            log.fine(title);
        } else {
            log.fine("skipped");
        }
    }

    class FirstPhase extends Phase {

        @Override
        public boolean canBeExecuted() {
            return Game.isCurrentSystemIs(getStarSystemId()) && (Game.getDifficultyId() < Difficulty.NORMAL.castToInt());
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

    enum Phases implements SimpleValueEnum<QuestDialog> {
        LotteryWinner(new QuestDialog(ALERT, "Lottery Winner", "You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account."));

        private QuestDialog value;
        Phases(QuestDialog value) { this.value = value; }
        @Override public QuestDialog getValue() { return value; }
        @Override public void setValue(QuestDialog value) { this.value = value; }
    }

    private EnumMap<Phases, Phase> phases = new EnumMap<>(Phases.class);

    enum Quests implements SimpleValueEnum<String> {
        LotteryWinner("Get a prize at the ^1 spaceport.");

        private String value;
        Quests(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    @Override
    public String toString() {
        return "LotteryQuest{} " + super.toString();
    }
}
