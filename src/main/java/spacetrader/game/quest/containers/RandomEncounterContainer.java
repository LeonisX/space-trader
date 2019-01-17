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
        this.mantis = mantis && isNotSet();
    }

    public boolean isPirate() {
        return pirate;
    }

    public void setPirate(boolean pirate) {
        this.pirate = pirate && isNotSet();
    }

    public boolean isPolice() {
        return police;
    }

    public void setPolice(boolean police) {
        this.police = police && isNotSet();
    }

    public boolean isTrader() {
        return trader;
    }

    public void setTrader(boolean trader) {
        this.trader = trader && isNotSet();
    }

    private boolean isNotSet() {
        return !(police || pirate || trader || mantis);
    }
}
