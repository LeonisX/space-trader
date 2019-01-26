package spacetrader.game.quest;

import java.io.Serializable;

public class AlertDialog implements Serializable {

    private final String title;
    private final String message;
    private final String accept;
    private final String cancel;

    public AlertDialog(String title, String message) {
        this(title, message, null, null);
    }

    public AlertDialog(String title, String message, String accept) {
        this(title, message, accept, null);
    }

    public AlertDialog(String title, String message, String accept, String cancel) {
        this.title = title;
        this.message = message;
        this.accept = accept;
        this.cancel = cancel;
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

    public String getCancel() {
        return cancel;
    }
}
