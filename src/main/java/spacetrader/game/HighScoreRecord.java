package spacetrader.game;

import spacetrader.game.enums.Difficulty;

import java.io.Serializable;

public class HighScoreRecord implements Serializable, Comparable<HighScoreRecord> {

    private static final long serialVersionUID = 8309080721495266420L;

    private String name;
    private int score;
    private int type;
    private int days;
    private int worth;
    private Difficulty difficulty;

    HighScoreRecord(String name, int score, int type, int days, int worth, Difficulty difficulty) {
        this.name = name;
        this.score = score;
        this.type = type;
        this.days = days;
        this.worth = worth;
        this.difficulty = difficulty;
    }

    public int compareTo(HighScoreRecord value) {
        if (value == null) {
            return 1;
        } else if (value.getScore() > getScore()) {
            return 1;
        } else if (value.getScore() < getScore()) {
            return -1;
        } else if (value.getWorth() > getWorth()) {
            return 1;
        } else if (value.getWorth() < getWorth()) {
            return -1;
        } else {
            return Integer.compare(value.getDays(), getDays());
        }
    }

    public int getDays() {
        return days;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public int getType() {
        return type;
    }

    public int getWorth() {
        return worth;
    }
}
