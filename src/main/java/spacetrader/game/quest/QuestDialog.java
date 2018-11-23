package spacetrader.game.quest;

public class QuestDialog extends AlertDialog {

    private final MessageType messageType;

    public QuestDialog(MessageType messageType, String title, String body) {
        super(title, body);
        this.messageType = messageType;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
