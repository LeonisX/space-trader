package spacetrader.game.quest;

import java.io.Serializable;

public class AlertDialog implements Serializable {

    private final String title;
    private final String body;

    public AlertDialog(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}
