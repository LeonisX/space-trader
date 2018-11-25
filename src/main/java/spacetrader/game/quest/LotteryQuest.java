package spacetrader.game.quest;

import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;

import java.util.logging.Logger;

import static spacetrader.game.quest.EventName.*;
import static spacetrader.game.quest.MessageType.ALERT;
import static spacetrader.game.quest.Repeatable.DISPOSABLE;

class LotteryQuest extends AbstractQuest {

    private static final Logger log = Logger.getLogger(LotteryQuest.class.getName());

    private static final Repeatable REPEATABLE = DISPOSABLE;
    private static final int OCCURRENCE = 0;
    private static final int CASH_TO_SPEND = -1000;

    private static final QuestDialog[] DIALOGS = new QuestDialog[]{
            new QuestDialog(ALERT, "Lottery Winner", "You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account.")
    };

    public LotteryQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);
        initializePhases(DIALOGS, new FirstPhase());

        registerListener();
        log.fine("started...");
    }

    @Override
    public void registerListener() {
        if (Game.getDifficultyId() < Difficulty.NORMAL.castToInt()) {
            registerOperation(AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
            registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
            registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
            log.fine("registered");
        } else {
            log.fine("not registered");
        }
    }

    private void onAfterGameInitialize(Object object) {
        log.fine("");
        getPhase(0).setStarSystemId(Game.getCurrentSystemId());
        setQuestState(QuestState.ACTIVE);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        log.fine(Game.getCurrentSystemId() + " ~ " + getPhase(0).getStarSystemId());
        if (getPhase(0).canBeExecuted()) {
            log.fine("executed");
            showSpecialButton(object, DIALOGS[0].getTitle());
        } else {
            log.fine("skipped");
        }
    }

    private void onSpecialButtonClicked(Object object) {
        log.fine("");
        if (getPhase(0).canBeExecuted()) {
            showDialogAndProcessResult(object, DIALOGS[0], LotteryQuest.this::unRegisterAllOperations);
            setQuestState(QuestState.FINISHED);
            QuestsHolder.unSubscribeAll(getQuest());
            log.fine("executed");
        } else {
            log.fine("skipped");
        }
    }

    class FirstPhase extends Phase {

        @Override
        public boolean canBeExecuted() {
            return isQuestIsActive() && Game.isCurrentSystemIs(getStarSystemId());
        }
    }
}
