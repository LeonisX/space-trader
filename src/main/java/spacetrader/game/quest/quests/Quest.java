package spacetrader.game.quest.quests;

import spacetrader.controls.Rectangle;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.containers.ShipSpecContainer;
import spacetrader.game.quest.enums.EventName;
import spacetrader.game.quest.enums.QuestState;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface Quest {

    Quest getQuest();

    void setQuest(Quest quest);

    String getId();

    void setId(String id);

    String getCrewMemberName(int id);

    List<Integer> getSpecialCrewIds();

    //StarSystemId getStarSystemId();

    void dumpAllStrings();

    void initializeTransitionMap();

    Consumer<Object> getOperation(EventName eventName);

    Collection<Phase> getPhases();

    Collection<QuestDialog> getQuestDialogs();

    String getNewsTitle(int newsId);

    QuestState getQuestState();

    void affectSkills(int[] skillsCopy);

    void affectShipHullStrength(ShipSpecContainer shipSpecContainer);

    boolean isQuestIsActive();

    boolean isQuestIsInactive();

    String getGameCompletionText();

    void initializeLogger(Quest quest);

    Rectangle getShipImageOffset();

    Integer getShipImageIndex();

    String getShipName();

    void localize();

    String getVeryRareEncounter(Integer id);
}
