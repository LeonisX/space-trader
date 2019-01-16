package spacetrader.game.quest.containers;

public class IntContainer {

    private int value;

    public IntContainer(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void plus(int number) {
        value += number;
    }

    public void add(int number) {
        value += number;
    }

    public void minus(int number) {
        value -= number;
    }

    public void subtract(int number) {
        value -= number;
    }

    public void multipleBy(int number) {
        value *= number;
    }

    public void divideBy(int number) {
        value /= number;
    }

    public void multipleBy(double number) {
        value = (int) (value * number);
    }

    public void dec() {
        value--;
    }
}
