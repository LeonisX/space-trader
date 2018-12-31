package spacetrader.game.quest.enums;

public interface SimpleValueEnum<T> {

    String name();
    T getValue();
    void setValue(T value);

}
