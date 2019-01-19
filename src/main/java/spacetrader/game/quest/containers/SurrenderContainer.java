package spacetrader.game.quest.containers;

import spacetrader.game.enums.EncounterResult;

public class SurrenderContainer {

    private EncounterResult result;
    private boolean match = false;

    public SurrenderContainer(EncounterResult result) {
        this.result = result;
    }

    public EncounterResult getResult() {
        return result;
    }

    public void setResult(EncounterResult result) {
        this.result = result;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }
}
