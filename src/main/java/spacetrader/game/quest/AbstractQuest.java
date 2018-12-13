package spacetrader.game.quest;

import spacetrader.controls.Button;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.ShipSpec;
import spacetrader.game.Strings;
import spacetrader.game.enums.AlertType;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.game.quest.enums.EventName;
import spacetrader.game.quest.enums.MessageType;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.Facaded;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Logger;

public abstract class AbstractQuest implements Quest, Serializable {

    transient public Logger log;

    public int id;
    private Quest quest;
    private Repeatable repeatable;
    private int cashToSpend;
    private int occurrence;

    private List<Phase> phases = new ArrayList<>();

    private QuestState questState;

    private List<Integer> specialCrewIds = new ArrayList<>();

    private List<Integer> newsIds = new ArrayList<>();

    transient private Map<EventName, Consumer<Object>> transitionMap;

    // Common methods

    void initialize(Integer id, Quest quest, Repeatable repeatable, int cashToSpend, int occurrence) {
        this.id = id;
        this.quest = quest;
        this.repeatable = repeatable;
        this.cashToSpend = cashToSpend;
        this.occurrence = occurrence;
        questState = QuestState.INACTIVE;
        initializeLogger(quest);
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

    public void initializeLogger(Quest quest) {
        log = Logger.getLogger(quest.getClass().getName());
    }

    CrewMember registerNewSpecialCrewMember(int pilot, int fighter, int trader, int engineer) {
        CrewMember crewMember =
                CrewMember.specialCrewMember(QuestSystem.generateSpecialCrewId(), pilot, fighter, trader, engineer);
        specialCrewIds.add(crewMember.getId());
        return QuestSystem.registerNewSpecialCrewMember(crewMember, this);
    }

    int registerNewShipSpec(ShipSpec shipSpec) {
        return QuestSystem.registerNewShipSpec(shipSpec, this);
    }

    int registerNewGameEndType() {
        return QuestSystem.registerNewGameEndType(this);
    }

    void registerNews(int count) {
        for (int i = 0; i < count; i++) {
            int newsId = QuestSystem.generateNewsId();
            newsIds.add(newsId);
            QuestSystem.registerNews(newsId, quest);
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
    public Consumer<Object> getOperation(EventName eventName) {
        /*if (transitionMap.isEmpty()) {
            quest.initializeTransitionMap();
        }*/
        return transitionMap.get(eventName);
    }

    void registerOperation(EventName eventName) {
        QuestSystem.subscribe(eventName, this);
    }

    void unRegisterOperation(EventName eventName) {
        QuestSystem.unSubscribe(eventName, this);
    }

    void unRegisterAllOperations() {
        QuestSystem.unSubscribeAll(quest);
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

    public List<Integer> getSpecialCrewIds() {
        return specialCrewIds;
    }

    void setSpecialCrewIds(List<Integer> specialCrewIds) {
        this.specialCrewIds = specialCrewIds;
    }

    public List<Integer> getNewsIds() {
        return newsIds;
    }

    public void setNewsIds(List<Integer> newsIds) {
        this.newsIds = newsIds;
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

    @Override
    public boolean isQuestIsInactive() {
        return questState == QuestState.INACTIVE;
    }

    @Override
    public String getCrewMemberName(int id) {
        return null;
    }

    @Override
    public String getGameCompletionText() {
        return "";
    }

    @Override
    public String getNewsTitle(int newsId) {
        return null;
    }

    void showSpecialButtonIfCanBeExecuted(Object object, Phase phase) {
        if (phase.canBeExecuted()) {
            log.finest("phase `" + phase.getTitle() + "` : " + Game.getCurrentSystemId() + " ~ " + phase.getStarSystemId());
            showSpecialButton(object, phase.getTitle());
        }
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

    @Facaded
    void showAlert(AlertDialog dialog) {
        FormAlert formAlert = new FormAlert(dialog.getTitle(), dialog.getBody(), Strings.AlertsOk, DialogResult.OK, null, DialogResult.NONE, null);
        formAlert.showDialog();
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
                //", specialCrewId=" + specialCrewId +
                ", newsIds=" + newsIds +
                '}';
    }
}
