package spacetrader.game.enums;

import java.util.Locale;

public enum Language {

    ENGLISH(new Locale("us")),
    RUSSIAN(new Locale("ru"));

    private Locale locale;

    Language(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
