package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Game;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractQuest implements Quest, Serializable {

    public int id;
    private Quest quest;
    private Repeatable repeatable;
    private int cashToSpend;
    private int occurrence;

    private List<Phase> phases = new ArrayList<>();

    private QuestState questState;

    private int specialCrewId;

    private int newsId;

    transient private Map<EventName, Consumer<Object>> transitionMap;

    // Common methods

    void initialize(Integer id, Quest quest, Repeatable repeatable, int cashToSpend, int occurrence) {
        this.id = id;
        this.quest = quest;
        this.repeatable = repeatable;
        this.cashToSpend = cashToSpend;
        this.occurrence = occurrence;
        questState = QuestState.INACTIVE;
        specialCrewId = QuestsHolder.generateSpecialCrewId();
        newsId = QuestsHolder.generateNewsId();
    }

    void initializePhases(QuestDialog[] dialogs, Phase... phases) {
        for (int i = 0; i < phases.length; i++) {
            phases[i].setId(i);
            phases[i].setDialogs(dialogs);
            phases[i].setQuest(quest);
            //TODO need???
            phases[i].setPhaseId(id + i);
            getPhases().add(phases[i]);
        }
    }

    Map<EventName, Consumer<Object>> getTransitionMap() {
        return transitionMap;
    }

    @Override
    public void initializeTransitionMap() {
        transitionMap = new HashMap<>();
    }

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
    public Consumer<Object> getOperation(EventName eventName) {
        /*if (transitionMap.isEmpty()) {
            quest.initializeTransitionMap();
        }*/
        return transitionMap.get(eventName);
    }

    void registerOperation(EventName eventName) {
        QuestsHolder.subscribe(eventName, this);
    }

    void unRegisterOperation(EventName eventName) {
        QuestsHolder.unSubscribe(eventName, this);
    }

    void unRegisterAllOperations() {
        QuestsHolder.unSubscribeAll(quest);
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

    void setSpecialCrewId(int specialCrewId) {
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

    void setQuestState(QuestState questState) {
        this.questState = questState;
    }

    @Override
    public void affectSkills(int[] skills) {
    }

    public abstract void registerListener();

    @Override
    public boolean isQuestIsActive() {
        return questState == QuestState.ACTIVE;
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
            if (Game.getCommander().getCashToSpend() < getPrice())
                GuiFacade.alert(AlertType.SpecialIF);
            else {
                try {
                    ((Button) object).setVisible(false);
                    Game.getCommander().spendCash(getPrice());
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

    @Override
    public String toString() {
        return "AbstractQuest{" +
                "id=" + id +
                ", repeatable=" + repeatable +
                ", cashToSpend=" + cashToSpend +
                ", occurrence=" + occurrence +
                ", phases=" + phases +
                ", questState=" + questState +
                ", specialCrewId=" + specialCrewId +
                ", newsId=" + newsId +
                '}';
    }
}
