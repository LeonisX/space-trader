package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.game.Game;
import spacetrader.game.enums.Difficulty;
import spacetrader.stub.ArrayList;

import java.util.List;

import static spacetrader.game.quest.EventName.*;

//new SpecialEvent(SpecialEventType.Lottery, -1000, 0, true),
class LotteryQuest extends AbstractQuest {

    private static final int CASH_TO_SPEND = -1000;
    private static final boolean MESSAGE_ONLY = true;

    private static String[] MESSAGE_TITLES = {"Lottery Winner"};
    private static String[] MESSAGE_BODIES = {"You are lucky! While docking on the space port, you receive a message that you won 1000 credits in a lottery. The prize had been added to your account."};

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
        phases.add(new FirstPhase(cashToSpend, messageOnly));
        setPhases(phases);

        registerListener();
    }

    // Register listener
    public void registerListener() {
        if (Game.getCurrentGame().getDifficulty().castToInt() < Difficulty.NORMAL.castToInt()) {
            getPhases().get(0).registerListener();
        }
    }


    @Override
    public String[] getMessageTitles() {
        return MESSAGE_TITLES;
    }

    @Override
    public String[] getMessageBodies() {
        return MESSAGE_BODIES;
    }

    @Override
    public String getCrewMemberName() {
        return null;
    }

    @Override
    public String getSpecialCargoTitle() {
        return null;
    }

    @Override
    public String getNewsTitle() {
        return null;
    }

    class FirstPhase extends Phase {

        //TODO need???
        FirstPhase(int cashToSpend, boolean messageOnly) {
            super(cashToSpend, messageOnly);
        }

        @Override
        public String getTitle() {
            return MESSAGE_TITLES[0];
        }

        @Override
        public void registerListener() {
            registerOperation(AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
        }

        @Override
        public String getMessageTitle() {
            return MESSAGE_TITLES[0];
        }

        @Override
        public String getMessageBody() {
            return MESSAGE_BODIES[0];
        }

        private void onAfterGameInitialize(Object object) {
            setStarSystemId(Game.getCurrentGame().getCurrentSystemId());
            unRegisterOperation(AFTER_GAME_INITIALIZE);
            registerOperation(BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
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
            specialButtonClick(object, this);
        }

    }
}
