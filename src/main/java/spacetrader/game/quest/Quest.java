package spacetrader.game.quest;

import spacetrader.controls.Rectangle;
import spacetrader.game.quest.enums.EventName;
import spacetrader.game.quest.enums.QuestName;
import spacetrader.game.quest.enums.QuestState;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public interface Quest {

    Quest getQuest();

    void setQuest(Quest quest);

    QuestName getId();

    void setId(QuestName id);

    String getCrewMemberName(int id);

    List<Integer> getSpecialCrewIds();

    //StarSystemId getStarSystemId();

    void dumpAllStrings();

    void initializeTransitionMap();

    Consumer<Object> getOperation(EventName eventName);

    Collection<Phase> getPhases();

    String getNewsTitle(int newsId);

    QuestState getQuestState();

    void affectSkills(int[] skillsCopy);

    boolean isQuestIsActive();

    boolean isQuestIsInactive();

    String getGameCompletionText();

    void initializeLogger(Quest quest);

    Rectangle getShipImageOffset();

    Integer getShipImageIndex();

    void localize();
}
