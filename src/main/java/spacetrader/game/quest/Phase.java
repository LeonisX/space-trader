package spacetrader.game.quest;

import spacetrader.game.enums.StarSystemId;

import java.io.Serializable;

public abstract class Phase implements Serializable {

    private StarSystemId starSystemId;

    public abstract String getTitle();

    public abstract void registerListener();

    public StarSystemId getStarSystemId() {
        return starSystemId;
    }

    public void setStarSystemId(StarSystemId starSystemId) {
        this.starSystemId = starSystemId;
    }
}
