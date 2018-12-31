package spacetrader.game.quest;

import java.io.Serializable;

public class AlertDialog implements Serializable {

    private final String title;
    private final String message;
    private final String accept;

    AlertDialog(String title, String message) {
        this(title, message, null);
    }

    AlertDialog(String title, String message, String accept) {
        this.title = title;
        this.message = message;
        this.accept = accept;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getAccept() {
        return accept;
    }
}
