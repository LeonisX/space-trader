package spacetrader.game.quest;

import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.stub.ArrayList;

import java.util.List;
import java.util.logging.Logger;

import static spacetrader.game.quest.EventName.*;
import static spacetrader.game.quest.MessageType.ALERT;

class LotteryQuest extends AbstractQuest {

    private static final Logger log = Logger.getLogger(LotteryQuest.class.getName());

    private static final int CASH_TO_SPEND = -1000;
    private static final boolean MESSAGE_ONLY = true;

    private static QuestDialog DIALOG = new QuestDialog(ALERT, "Lottery Winner",
            "You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account.");

    private static final boolean REPEATABLE = false;
    private static final int OCCURRENCE = 0;

    public LotteryQuest(Integer id) {
        setId(id);
        setQuest(this);
        repeatable = REPEATABLE;
        cashToSpend = CASH_TO_SPEND;
        messageOnly = MESSAGE_ONLY;

        List<Phase> phases = new ArrayList<>();
        phases.add(new FirstPhase());
        setPhases(phases);

        getPhase(0).registerListener();
        log.fine("started...");
    }

    class FirstPhase extends Phase {

        @Override
        public void registerListener() {
            if (Game.getCurrentGame().getDifficulty().castToInt() < Difficulty.NORMAL.castToInt()) {
                registerOperation(AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
                registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
                registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                log.fine("registered");
            } else {
                log.fine("not registered");
            }
        }

        @Override
        public boolean canBeExecuted() {
            return (getQuestState() == QuestState.ACTIVE) && Game.getCurrentGame().getCurrentSystemId().equals(getStarSystemId());
        }

        @Override
        public String getTitle() {
            return DIALOG.getTitle();
        }

        private void onAfterGameInitialize(Object object) {
            log.fine("");
            setStarSystemId(Game.getCurrentGame().getCurrentSystemId());
            setQuestState(QuestState.ACTIVE);
        }

        private void onBeforeSpecialButtonShow(Object object) {
            log.fine(Game.getCurrentGame().getCurrentSystemId() + " ~ " + getStarSystemId());
            if (canBeExecuted()) {
                log.fine("executed");
                showSpecialButton(object, DIALOG.getTitle());
            } else {
                log.fine("skipped");
            }
        }

        private void onSpecialButtonClicked(Object object) {
            log.fine("");
            if (canBeExecuted()) {
                showDialogAndProcessResult(object, DIALOG, LotteryQuest.this::unRegisterAllOperations);
                setQuestState(QuestState.FINISHED);
                QuestsHolder.unSubscribeAll(getQuest());
                log.fine("executed");
            } else {
                log.fine("skipped");
            }
        }
    }

}
