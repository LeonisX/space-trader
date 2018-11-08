package spacetrader.game.enums;

public enum Language {

    ENGLISH, RUSSIAN;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
