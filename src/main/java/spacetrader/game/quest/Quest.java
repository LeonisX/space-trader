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

    String getMessageBody();

    boolean isMessageOnly();

    int getPrice();

    //StarSystemId getStarSystemId();

    Consumer<Object> getCurrentOperation(EventName eventName);

    List<Phase> getPhases();

    void setPhases(List<Phase> phases);

    int getSpecialCrewId();

    int getNewsId();

    String getNewsTitle();

    QuestState getQuestState();

    void affectSkills(int[] skillsCopy);
}
