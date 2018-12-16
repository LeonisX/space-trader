package spacetrader.game.quest;

import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.Repeatable.DISPOSABLE;

class LotteryQuest extends AbstractQuest {

    private static final Repeatable REPEATABLE = DISPOSABLE;
    private static final int OCCURRENCE = 0;
    private static final int CASH_TO_SPEND = -1000;

    private static final String[] QUESTS = {
            "Get a prize at the ^1 spaceport."
    };

    private static final QuestDialog[] DIALOGS = new QuestDialog[]{
            new QuestDialog(ALERT, "Lottery Winner", "You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account.")
    };

    public LotteryQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);
        initializePhases(DIALOGS, new FirstPhase());
        initializeTransitionMap();

        registerListener();
        log.fine("started...");
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();
        getTransitionMap().put(AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (getPhase(0).canBeExecuted() && (isQuestIsInactive() || isQuestIsActive())) {
            String title = Functions.stringVars(QUESTS[0], Game.getStarSystem(getPhase(0).getStarSystemId()).getName());
            ((ArrayList<String>) object).add(title);
            log.fine(title);
        } else {
            log.fine("skipped");
        }
    }

    private void onAfterGameInitialize(Object object) {
        log.fine("");
        getPhase(0).setStarSystemId(Game.getCurrentSystemId());
    }

    private void onBeforeSpecialButtonShow(Object object) {
        log.fine(Game.getCurrentSystemId() + " ~ " + getPhase(0).getStarSystemId());
        if (getPhase(0).canBeExecuted() && (isQuestIsInactive() || isQuestIsActive())) {
            log.fine("executed");
            setQuestState(QuestState.ACTIVE);
            showSpecialButton(object, DIALOGS[0].getTitle());
        } else {
            log.fine("skipped");
        }
    }

    private void onSpecialButtonClicked(Object object) {
        log.fine("");
        if (getPhase(0).canBeExecuted() && isQuestIsActive()) {
            showDialogAndProcessResult(object, DIALOGS[0], LotteryQuest.this::unRegisterAllOperations);
            setQuestState(QuestState.FINISHED);
            log.fine("executed");
        } else {
            log.fine("skipped");
        }
    }

    class FirstPhase extends Phase {

        @Override
        public boolean canBeExecuted() {
            return Game.isCurrentSystemIs(getStarSystemId())
                    && (Game.getDifficultyId() < Difficulty.NORMAL.castToInt());
        }

        @Override
        public String toString() {
            return "FirstPhase{} " + super.toString();
        }
    }

    @Override
    public String toString() {
        return "LotteryQuest{} " + super.toString();
    }
}
