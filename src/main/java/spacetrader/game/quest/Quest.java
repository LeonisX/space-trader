package spacetrader.game.quest;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public interface Quest {

    Quest getQuest();

    void setQuest(Quest quest);

    int getId();

    void setId(int id);

    //TODO specialized??? QuestWithMercenary?
    String getCrewMemberName();

    int getSpecialCrewId();

    int getPrice();

    //StarSystemId getStarSystemId();

    void initializeTransitionMap();

    Consumer<Object> getOperation(EventName eventName);

    List<Phase> getPhases();

    Phase getPhase(int index);

    void setPhases(List<Phase> phases);

    int getNewsId();

    String getNewsTitle();

    QuestState getQuestState();

    void affectSkills(int[] skillsCopy);

    boolean isQuestIsActive();

    boolean isQuestIsInactive();
}
