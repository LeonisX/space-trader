package spacetrader.game.quest;

public class QuestDialog extends AlertDialog {

    private final MessageType messageType;

    public QuestDialog(String title, String body, MessageType messageType) {
        super(title, body);
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
