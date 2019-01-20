package spacetrader.game.quest;

import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.enums.SimpleValueEnum;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static spacetrader.game.quest.enums.EventName.ON_AFTER_NEW_QUEST_STARTED;

public abstract class Phase implements Serializable {

    private List<StarSystemId> starSystemIds = new ArrayList<>();

    private Quest quest;

    private SimpleValueEnum<QuestDialog> phase;

    private int occurrence;

    public void confirmQuestPhase() {
        Game.getCommander().setCash(Game.getCommander().getCash() - phase.getValue().getPrice());
        Game.getCommander().getCurrentSystem().setQuestSystem(false);
        Game.getCurrentGame().getQuestSystem().fireEvent(ON_AFTER_NEW_QUEST_STARTED);
    }

    public String getTitle() {
        return phase.getValue().getTitle();
    }

    public abstract boolean canBeExecuted();

    public abstract void successFlow();

    public Phase() {
        this(1);
    }

    public Phase(int occurrence) {
        this.occurrence = occurrence;
    }

    boolean isDesiredSystem() {
        return starSystemIds.stream().anyMatch(Game::isCurrentSystemIs);
    }

    boolean isDesiredSystem(StarSystem starSystem) {
        return starSystemIds.stream().anyMatch(s -> s.equals(starSystem.getId()));
    }

    public List<StarSystemId> getStarSystemIds() {
        return starSystemIds;
    }

    public StarSystemId getStarSystemId() {
        return starSystemIds.get(0);
    }

    void setStarSystemId(StarSystemId starSystemId) {
        starSystemIds.add(starSystemId);
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

    public int getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public String toString() {
        return "Phase{" +
                "starSystemIds=" + starSystemIds +
                ", quest=" + quest +
                ", phase=" + phase +
                ", occurrence=" + occurrence +
                '}';
    }
}
