package spacetrader.game.quest;

import spacetrader.game.enums.StarSystemId;

import java.io.Serializable;

public abstract class Phase implements Serializable {

    private int cashToSpend;
    private boolean messageOnly;

    //private String messageTitle;
    //private String messageBody;

    private StarSystemId starSystemId;

    Phase(int cashToSpend, boolean messageOnly) {
        this.cashToSpend = cashToSpend;
        this.messageOnly = messageOnly;
    }

    public abstract String getTitle();

    public abstract void registerListener();

    public int getCashToSpend() {
        return cashToSpend;
    }

    public void setCashToSpend(int cashToSpend) {
        this.cashToSpend = cashToSpend;
    }

    public boolean isMessageOnly() {
        return messageOnly;
    }

    public void setMessageOnly(boolean messageOnly) {
        this.messageOnly = messageOnly;
    }

    abstract public String getMessageTitle();

    /*public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }*/

    abstract public String getMessageBody();

    /*public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }*/

    public StarSystemId getStarSystemId() {
        return starSystemId;
    }

    public void setStarSystemId(StarSystemId starSystemId) {
        this.starSystemId = starSystemId;
    }
}
