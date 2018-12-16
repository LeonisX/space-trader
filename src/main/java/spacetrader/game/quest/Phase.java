package spacetrader.game.quest;

import spacetrader.game.Game;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.enums.SimpleValueEnumWithPhase;

import java.io.Serializable;

public abstract class Phase implements Serializable {

    //TODO need???
    private int id;
    //TODO need???
    private int phaseId;

    private StarSystemId starSystemId;

    private Quest quest;
    private SimpleValueEnumWithPhase<QuestDialog> questPhase;

    public String getTitle() {
        return questPhase.getValue().getTitle();
    }

    public boolean isDesiredSystem() {
        return Game.isCurrentSystemIs(starSystemId);
    }

    public StarSystemId getStarSystemId() {
        return starSystemId;
    }

    public void setStarSystemId(StarSystemId starSystemId) {
        this.starSystemId = starSystemId;
    }

    public abstract boolean canBeExecuted();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public int getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(int phaseId) {
        this.phaseId = phaseId;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "id=" + id +
                ", phaseId=" + phaseId +
                ", starSystemId=" + starSystemId +
                '}';
    }

    public SimpleValueEnumWithPhase<QuestDialog> getQuestPhase() {
        return questPhase;
    }

    public void setQuestPhase(SimpleValueEnumWithPhase<QuestDialog> questPhase) {
        this.questPhase = questPhase;
    }
}
