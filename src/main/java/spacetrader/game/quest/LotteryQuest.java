package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.stub.ArrayList;

import java.util.List;

import static spacetrader.game.quest.EventName.*;
import static spacetrader.game.quest.MessageType.ALERT;

//new SpecialEvent(SpecialEventType.Lottery, -1000, 0, true),
class LotteryQuest extends AbstractQuest {

    private static final int CASH_TO_SPEND = -1000;
    private static final boolean MESSAGE_ONLY = true;

    private static QuestDialog[] DIALOGS = new QuestDialog[]{
            new QuestDialog("Lottery Winner",
                    "You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account.",
                    ALERT)
    };

    // Constants
    private static final boolean REPEATABLE = false;
    private static final int OCCURRENCE = 0;
    //private static final SpecialEventType TYPE = SpecialEventType.Lottery;

    LotteryQuest() {
        setQuest(this);
        repeatable = REPEATABLE;
        //this.type = TYPE;
        occurrence = OCCURRENCE;
        cashToSpend = CASH_TO_SPEND;
        messageOnly = MESSAGE_ONLY;

        List<Phase> phases = new ArrayList<>();
        phases.add(new FirstPhase(cashToSpend));
        setPhases(phases);

        registerListener();
    }

    // Register listener
    public void registerListener() {
        getPhases().get(0).registerListener();
    }

    @Override
    public String getCrewMemberName() {
        return null;
    }

    @Override
    public String getNewsTitle() {
        return null;
    }

    class FirstPhase extends Phase {

        //TODO need???
        FirstPhase(int cashToSpend) {
            super(cashToSpend);
        }

        @Override
        public String getTitle() {
            return DIALOGS[0].getTitle();
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
                    ((Button) object).asJButton().setToolTipText(getMessageTitle());
                    registerOperation(SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
                }
            }
        }

        private void onSpecialButtonClicked(Object object) {
            specialButtonClick(object, DIALOGS[0], () -> {
                unRegisterAllOperations();
            });
        }
    }
}
