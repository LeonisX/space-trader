package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.stub.ArrayList;

import java.util.List;

import static spacetrader.game.quest.EventName.*;
import static spacetrader.game.quest.MessageType.ALERT;

class LotteryQuest extends AbstractQuest {

    private static final int CASH_TO_SPEND = -1000;
    private static final boolean MESSAGE_ONLY = true;

    private static QuestDialog DIALOG = new QuestDialog(ALERT, "Lottery Winner",
            "You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account.");

    private static final boolean REPEATABLE = false;
    public static int OCCURRENCE = 0;

    public LotteryQuest(Integer id) {
        setId(id);
        setQuest(this);
        repeatable = REPEATABLE;
        cashToSpend = CASH_TO_SPEND;
        messageOnly = MESSAGE_ONLY;

        List<Phase> phases = new ArrayList<>();
        phases.add(new FirstPhase());
        setPhases(phases);

        getPhases().get(0).registerListener();
    }

    class FirstPhase extends Phase {

        @Override
        public String getTitle() {
            return DIALOG.getTitle();
        }

        @Override
        public void registerListener() {
            registerOperation(AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
        }

        private void onAfterGameInitialize(Object object) {
            setStarSystemId(Game.getCurrentGame().getCurrentSystemId());
            unRegisterOperation(AFTER_GAME_INITIALIZE);
            if (Game.getCurrentGame().getDifficulty().castToInt() < Difficulty.NORMAL.castToInt()) {
                registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
            }
        }

        private void onBeforeSpecialButtonShow(Object object) {
            if (Game.getCurrentGame().getCurrentSystemId().equals(getStarSystemId())) {
                if (!((Button) object).isVisible()) {
                    ((Button) object).setVisible(true);
                    ((Button) object).asJButton().setToolTipText(getTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            showDialogAndProcessResult(object, DIALOG, LotteryQuest.this::unRegisterAllOperations);
        }
    }
}
