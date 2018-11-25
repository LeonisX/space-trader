package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Game;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.GuiFacade;

import java.io.Serializable;
import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractQuest implements Quest, Serializable {

    public int id;

    private Quest quest;
    private List<Phase> phases;

    private QuestState questState = QuestState.INACTIVE;

    Repeatable repeatable;

    int cashToSpend;

    private int specialCrewId = QuestsHolder.generateSpecialCrewId();

    private int newsId = QuestsHolder.generateNewsId();

    //StarSystemId starSystemId;

    private Map<EventName, Consumer<Object>> operations = new HashMap<>();

    // Common methods

    @Override
    public Quest getQuest() {
        return quest;
    }

    @Override
    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getPrice() {
        return cashToSpend;
    }

    @Override
    public String getCrewMemberName() {
        return null;
    }

    @Override
    public String getNewsTitle() {
        return null;
    }

    @Override
    public Consumer<Object> getCurrentOperation(EventName eventName) {
        return operations.get(eventName);
    }

    void registerOperation(EventName eventName, Consumer<Object> operation) {
        operations.put(eventName, operation);
        QuestsHolder.subscribe(eventName, this);
    }

    void unRegisterOperation(EventName eventName) {
        QuestsHolder.unSubscribe(eventName, this);
        operations.remove(eventName);
    }

    public void unRegisterAllOperations() {
        QuestsHolder.unSubscribeAll(quest);
        operations.clear();
    }

    @Override
    public List<Phase> getPhases() {
        return phases;
    }

    @Override
    public Phase getPhase(int index) {
        return phases.get(index);
    }

    @Override
    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }

    public int getSpecialCrewId() {
        return specialCrewId;
    }

    public void setSpecialCrewId(int specialCrewId) {
        this.specialCrewId = specialCrewId;
    }

    @Override
    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    @Override
    public QuestState getQuestState() {
        return questState;
    }

    public void setQuestState(QuestState questState) {
        this.questState = questState;
    }

    @Override
    public void affectSkills(int[] skills) {
    }

    void showSpecialButton(Object object, String title) {
        if (!((Button) object).isVisible()) {
            ((Button) object).setVisible(true);
            ((Button) object).asJButton().setToolTipText(title);
        }
    }

    void showDialogAndProcessResult(Object object, QuestDialog dialog, Runnable operation) {
        String button1Text, button2Text;
        DialogResult button1Result, button2Result;

        if (dialog.getMessageType() == MessageType.ALERT) {
            button1Text = Strings.AlertsOk;
            button2Text = null;
            button1Result = DialogResult.OK;
            button2Result = DialogResult.NONE;
        } else {
            button1Text = Strings.AlertsYes;
            button2Text = Strings.AlertsNo;
            button1Result = DialogResult.YES;
            button2Result = DialogResult.NO;
        }

        FormAlert alert = new FormAlert(dialog.getTitle(), dialog.getBody(), button1Text, button1Result,
                button2Text, button2Result, null);

        if (alert.showDialog() != DialogResult.NO) {
            if (Game.getCurrentGame().getCommander().getCashToSpend() < getPrice())
                GuiFacade.alert(AlertType.SpecialIF);
            else {
                try {
                    ((Button) object).setVisible(false);
                    Game.getCurrentGame().getCommander().spendCash(getPrice());
                    operation.run();
                } catch (GameEndException ex) {
                    Game.getCurrentGame().getController().gameEnd();
                }
            }
        }
    }

    void showAlert(AlertDialog dialog) {
        new FormAlert(dialog.getTitle(), dialog.getBody(), Strings.AlertsOk, DialogResult.OK, null, DialogResult.NONE, null);
    }
}
