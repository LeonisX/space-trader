package spacetrader.game.quest;

import spacetrader.game.quest.enums.MessageType;

import java.io.Serializable;

public class QuestDialog extends AlertDialog implements Serializable {

    private final MessageType messageType;

    public QuestDialog(MessageType messageType, String title, String body) {
        super(title, body);
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
