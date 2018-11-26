package spacetrader.game.quest;

import spacetrader.game.enums.StarSystemId;

import java.io.Serializable;

public abstract class Phase implements Serializable {

    private int id;
    private int phaseId;
    private QuestDialog[] dialogs;

    private StarSystemId starSystemId;

    private Quest quest;

    public String getTitle() {
        return dialogs[id].getTitle();
    }

    public QuestDialog getDialog() {
        return dialogs[id];
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

    public void setDialogs(QuestDialog[] dialogs) {
        this.dialogs = dialogs;
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
}
