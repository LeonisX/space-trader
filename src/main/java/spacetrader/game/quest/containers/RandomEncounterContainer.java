package spacetrader.game.quest.containers;

public class RandomEncounterContainer {

    private boolean mantis = false;
    private boolean pirate = false;
    private boolean police = false;
    private boolean trader = false;

    public boolean isMantis() {
        return mantis;
    }

    public void setMantis(boolean mantis) {
        this.mantis = mantis;
    }

    public boolean isPirate() {
        return pirate;
    }

    public void setPirate(boolean pirate) {
        this.pirate = pirate;
    }

    public boolean isPolice() {
        return police;
    }

    public void setPolice(boolean police) {
        this.police = police;
    }

    public boolean isTrader() {
        return trader;
    }

    public void setTrader(boolean trader) {
        this.trader = trader;
    }
}
