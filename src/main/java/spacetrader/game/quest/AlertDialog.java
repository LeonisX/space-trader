package spacetrader.game.quest;

import java.io.Serializable;

public class AlertDialog implements Serializable {

    private String title;
    private String message;

    public AlertDialog(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
