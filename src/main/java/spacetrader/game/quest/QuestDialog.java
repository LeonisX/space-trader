package spacetrader.game.quest;

import spacetrader.game.quest.enums.MessageType;

import java.io.Serializable;

public class QuestDialog extends AlertDialog implements Serializable {

    private final MessageType messageType;
    private final int price;
    private final int occurrence;

    public QuestDialog(MessageType messageType, String title, String message) {
        this(0, 1, messageType, title, message);
    }

    public QuestDialog(int price, MessageType messageType, String title, String message) {
        this(price, 1, messageType, title, message);
    }

    public QuestDialog(int price, int occurrence, MessageType messageType, String title, String message) {
        super(title, message);
        this.messageType = messageType;
        this.price = price;
        this.occurrence = occurrence;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public int getPrice() {
        return price;
    }

    public int getOccurrence() {
        return occurrence;
    }


    @Override
    public String toString() {
        return getTitle();
    }
}
