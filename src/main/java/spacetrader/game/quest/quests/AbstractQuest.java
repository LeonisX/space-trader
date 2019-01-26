package spacetrader.game.quest.quests;

import spacetrader.controls.Button;
import spacetrader.controls.Rectangle;
import spacetrader.controls.WinformPane;
import spacetrader.controls.enums.DialogResult;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.Difficulty;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.game.quest.AlertDialog;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.containers.ShipSpecContainer;
import spacetrader.game.quest.enums.EventName;
import spacetrader.game.quest.enums.MessageType;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.Facaded;
import spacetrader.guifacade.GuiFacade;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static spacetrader.game.Strings.AlertsCancel;

public abstract class AbstractQuest implements Quest, Serializable {

    Game game = Game.getCurrentGame();

    transient public Logger log;

    public String id;
    private Quest quest;
    private Repeatable repeatable;
    private int occurrence;

    private QuestState questState;

    private List<Integer> specialCrewIds = new ArrayList<>();

    private List<Integer> newsIds = new ArrayList<>();

    transient private Map<EventName, Consumer<Object>> transitionMap;

    // Common methods

    void initialize(String id, Quest quest, Repeatable repeatable, int occurrence) {
        this.id = id;
        this.quest = quest;
        this.repeatable = repeatable;
        this.occurrence = occurrence;
        questState = QuestState.INACTIVE;
        initializeLogger(quest);
        //TODO return
        //quest.localize();
    }

    public void initializeLogger(Quest quest) {
        log = Logger.getLogger(quest.getClass().getName());
    }

    CrewMember registerNewSpecialCrewMember(int pilot, int fighter, int trader, int engineer, boolean mercenary) {
        CrewMember crewMember =
                CrewMember.specialCrewMember(game.getQuestSystem().generateSpecialCrewId(), pilot, fighter, trader, engineer, mercenary);
        specialCrewIds.add(crewMember.getId());
        return game.getQuestSystem().registerNewSpecialCrewMember(crewMember, this);
    }

    int registerNewShipSpec(ShipSpec shipSpec) {
        return game.getQuestSystem().registerNewShipSpec(shipSpec, this);
    }

    int registerNewGameEndType() {
        return game.getQuestSystem().registerNewGameEndType(this);
    }

    int registerNewEncounter() {
        return game.getQuestSystem().registerNewEncounter(this);
    }

    Integer registerNewVeryRareEncounter() {
        return game.getQuestSystem().registerNewVeryRareEncounter(this);
    }

    int registerNewOpponentType() {
        return game.getQuestSystem().registerNewOpponentType();
    }

    void registerNews(int count) {
        for (int i = 0; i < count; i++) {
            int newsId = game.getQuestSystem().generateNewsId();
            newsIds.add(newsId);
            game.getQuestSystem().registerNews(newsId, quest);
        }
    }

    StarSystemId occupyFreeSystemWithEvent() {
        List<StarSystem> freeSystems = Arrays.stream(getUniverse())
                .filter(s -> !s.isQuestSystem()).collect(Collectors.toList());
        StarSystem system = freeSystems.get(ThreadLocalRandom.current().nextInt(freeSystems.size()));

        system.setQuestSystem(true);
        log.fine(system.getId().toString());
        return system.getId();
    }

    Map<EventName, Consumer<Object>> getTransitionMap() {
        return transitionMap;
    }

    @Override
    public void dumpAllStrings() {
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
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Consumer<Object> getOperation(EventName eventName) {
        /*if (transitionMap.isEmpty()) {
            quest.initializeTransitionMap();
        }*/
        return transitionMap.get(eventName);
    }

    @Override
    public String getVeryRareEncounter(Integer id) {
        return null; // It is better to catch null pointer here
    }

    void registerOperation(EventName eventName) {
        game.getQuestSystem().subscribe(eventName, this);
    }

    void unRegisterOperation(EventName eventName) {
        game.getQuestSystem().unSubscribe(eventName, this);
    }

    void unRegisterAllOperations() {
        game.getQuestSystem().unSubscribeAll(quest);
    }

    public List<Integer> getSpecialCrewIds() {
        return specialCrewIds;
    }

    void setSpecialCrewIds(List<Integer> specialCrewIds) {
        this.specialCrewIds = specialCrewIds;
    }

    List<Integer> getNewsIds() {
        return newsIds;
    }

    Integer getNewsIdByIndex(int index) {
        return newsIds.get(index);
    }

    void addNewsByIndex(int index) {
        getNews().addEvent(getNewsIdByIndex(index));
    }

    Commander getCommander() {
        return game.getCommander();
    }

    Ship getShip() {
        return game.getShip();
    }

    Ship getOpponent() {
        return game.getOpponent();
    }

    void setOpponent(Ship opponent) {
        game.setOpponent(opponent);
    }

    Encounter getEncounter() {
        return game.getEncounter();
    }

    StarSystemId getCurrentSystemId() {
        return getCommander().getCurrentSystemId();
    }

    boolean isCurrentSystemIs(StarSystemId starSystemId) {
        return getCurrentSystemId().equals(starSystemId);
    }

    StarSystem getStarSystem(StarSystemId starSystemId) {
        return getStarSystem(starSystemId.castToInt());
    }

    public StarSystem getStarSystem(int starSystemId) {
        return getUniverse()[starSystemId];
    }

    StarSystem[] getUniverse() {
        return game.getUniverse();
    }

    Map<Integer, CrewMember> getMercenaries() {
        return game.getMercenaries();
    }

    Difficulty getDifficulty() {
        return game.getDifficulty();
    }

    int getDifficultyId() {
        return getDifficulty().castToInt();
    }
    News getNews() {
        return game.getNews();
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

    @Override
    public void affectShipHullStrength(ShipSpecContainer shipSpecContainer) {
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

    @Override
    public Rectangle getShipImageOffset() {
        return null;
    }

    @Override
    public Integer getShipImageIndex() {
        return null;
    }

    @Override
    public String getShipName() {
        return null;
    }

    void showSpecialButtonIfCanBeExecuted(Object object, Phase phase) {
        if (phase.canBeExecuted()) {
            log.finest("phase `" + phase.getTitle() + "` : " + getCurrentSystemId() + " ~ " + phase.getStarSystemIds());
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

        FormAlert alert = new FormAlert(dialog.getTitle(), dialog.getMessage(), button1Text, button1Result,
                button2Text, button2Result, null);

        if (alert.showDialog() != DialogResult.NO) {
            if (getCommander().getCashToSpend() < dialog.getPrice())
                GuiFacade.alert(AlertType.SpecialIF);
            else {
                try {
                    ((Button) object).setVisible(false);
                    //getCommander().spendCash(dialog.getPrice());
                    operation.run();
                } catch (GameEndException ex) {
                    game.getController().gameEnd();
                }
            }
        }
    }

    @Facaded
    DialogResult showAlert(AlertDialog dialog) {
        return showAlert(dialog, (String[]) null);
    }

    @Facaded
    DialogResult showAlert(AlertDialog dialog, String... args) {
        String ok = dialog.getAccept() == null ? Strings.AlertsOk : dialog.getAccept();
        FormAlert formAlert = new FormAlert(dialog.getTitle(), dialog.getMessage(), ok, DialogResult.OK, null, DialogResult.NONE, args);
        return formAlert.showDialog((WinformPane) game.getParentWindow());
    }

    @Facaded
    DialogResult showCancelAlert(AlertDialog dialog, String... args) {
        String ok = dialog.getAccept() == null ? Strings.AlertsOk : dialog.getAccept();
        FormAlert formAlert = new FormAlert(dialog.getTitle(), dialog.getMessage(), ok, DialogResult.OK, AlertsCancel, DialogResult.CANCEL, args);
        return formAlert.showDialog((WinformPane) game.getParentWindow());
    }

    @Facaded
    DialogResult showYesNoAlert(AlertDialog dialog, String... args) {
        String yes = dialog.getAccept() == null ? Strings.AlertsYes : dialog.getAccept();
        String no = dialog.getCancel() == null ? Strings.AlertsNo : dialog.getCancel();
        FormAlert formAlert = new FormAlert(dialog.getTitle(), dialog.getMessage(), yes, DialogResult.YES, no, DialogResult.NO, args);
        return formAlert.showDialog((WinformPane) game.getParentWindow());
    }

    @Override
    public String toString() {
        return "AbstractQuest{" +
                "game=" + game +
                ", id='" + id + '\'' +
                ", repeatable=" + repeatable +
                ", occurrence=" + occurrence +
                ", questState=" + questState +
                ", specialCrewIds=" + specialCrewIds +
                ", newsIds=" + newsIds +
                '}';
    }
}
