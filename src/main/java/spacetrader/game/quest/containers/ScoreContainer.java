package spacetrader.game.quest.containers;

public class ScoreContainer {

    private int worth;
    private int daysMoon;
    private int modifier;
    private int endStatus;

    public ScoreContainer(int worth, int daysMoon, int modifier, int endStatus) {
        this.worth = worth;
        this.daysMoon = daysMoon;
        this.modifier = modifier;
        this.endStatus = endStatus;
    }

    public int getWorth() {
        return worth;
    }

    public void setWorth(int worth) {
        this.worth = worth;
    }

    public int getDaysMoon() {
        return daysMoon;
    }

    public void setDaysMoon(int daysMoon) {
        this.daysMoon = daysMoon;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public int getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(int endStatus) {
        this.endStatus = endStatus;
    }
}
