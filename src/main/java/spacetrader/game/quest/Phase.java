package spacetrader.game.quest;

import spacetrader.game.Game;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.enums.SimpleValueEnum;

import java.io.Serializable;

public abstract class Phase implements Serializable {

    private StarSystemId starSystemId;

    private Quest quest;

    private SimpleValueEnum<QuestDialog> phase;

    public String getTitle() {
        return phase.getValue().getTitle();
    }

    public abstract boolean canBeExecuted();

    public abstract void successFlow();

    // TODO occurrence
    public boolean isDesiredSystem() {
        return Game.isCurrentSystemIs(starSystemId);
    }

    public StarSystemId getStarSystemId() {
        return starSystemId;
    }

    public void setStarSystemId(StarSystemId starSystemId) {
        this.starSystemId = starSystemId;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    void setPhaseEnum(SimpleValueEnum<QuestDialog> phaseEnum) {
        this.phase = phaseEnum;
    }

    @Override
    public String toString() {
        return "Phase{" +
                ", starSystemId=" + starSystemId +
                '}';
    }
}
