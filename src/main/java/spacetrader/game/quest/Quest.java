package spacetrader.game.quest;

import spacetrader.controls.Rectangle;
import spacetrader.game.quest.enums.EventName;
import spacetrader.game.quest.enums.QuestState;

import java.util.List;
import java.util.function.Consumer;

public interface Quest {

    Quest getQuest();

    void setQuest(Quest quest);

    int getId();

    void setId(int id);

    String getCrewMemberName(int id);

    List<Integer> getSpecialCrewIds();

    int getPrice();

    //StarSystemId getStarSystemId();

    void initializeTransitionMap();

    Consumer<Object> getOperation(EventName eventName);

    List<Phase> getPhases();

    Phase getPhase(int index);

    void setPhases(List<Phase> phases);

    String getNewsTitle(int newsId);

    QuestState getQuestState();

    void affectSkills(int[] skillsCopy);

    boolean isQuestIsActive();

    boolean isQuestIsInactive();

    String getGameCompletionText();

    void initializeLogger(Quest quest);

    Rectangle getShipImageOffset();
}
