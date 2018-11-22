package spacetrader.game.quest;

import spacetrader.game.enums.StarSystemId;

import java.io.Serializable;

public abstract class Phase implements Serializable {

    private int cashToSpend;
    private StarSystemId starSystemId;

    Phase(int cashToSpend) {
        this.cashToSpend = cashToSpend;
    }

    public abstract String getTitle();

    public abstract void registerListener();

    public int getCashToSpend() {
        return cashToSpend;
    }

    public void setCashToSpend(int cashToSpend) {
        this.cashToSpend = cashToSpend;
    }

    public StarSystemId getStarSystemId() {
        return starSystemId;
    }

    public void setStarSystemId(StarSystemId starSystemId) {
        this.starSystemId = starSystemId;
    }
}
