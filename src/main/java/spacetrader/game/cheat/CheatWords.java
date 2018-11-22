package spacetrader.game.cheat;

import spacetrader.util.Functions;

public class CheatWords {

    private String first;
    private String second;
    private String third;
    private int num1;
    private int num2;

    private boolean cheat;

    public static CheatWords parseWords(String[] words) {
        CheatWords cheatWords = new CheatWords();
        cheatWords.setFirst(words.length > 0 ? words[0] : "");
        cheatWords.setSecond(words.length > 1 ? words[1] : "");
        cheatWords.setThird(words.length > 2 ? words[2] : "");
        cheatWords.setNum1(Functions.isInt(cheatWords.getSecond()) ? Integer.parseInt(cheatWords.getSecond()) : 0);
        cheatWords.setNum2(Functions.isInt(cheatWords.getThird()) ? Integer.parseInt(cheatWords.getThird()) : 0);
        return cheatWords;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getThird() {
        return third;
    }

    public void setThird(String third) {
        this.third = third;
    }

    public int getNum1() {
        return num1;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public int getNum2() {
        return num2;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public boolean isCheat() {
        return cheat;
    }

    public void setCheat(boolean cheat) {
        this.cheat = cheat;
    }

}
