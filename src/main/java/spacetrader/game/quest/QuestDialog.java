package spacetrader.game.quest;

import spacetrader.game.quest.enums.MessageType;

import java.io.Serializable;

class QuestDialog extends AlertDialog implements Serializable {

    private final MessageType messageType;
    private final int price;
    private final int occurrence;

    QuestDialog(MessageType messageType, String title, String message) {
        this(0, 1, messageType, title, message);
    }

    QuestDialog(int price, MessageType messageType, String title, String message) {
        this(price, 1, messageType, title, message);
    }

    QuestDialog(int price, int occurrence, MessageType messageType, String title, String message) {
        super(title, message);
        this.messageType = messageType;
        this.price = price;
        this.occurrence = occurrence;
    }

    MessageType getMessageType() {
        return messageType;
    }

    public int getPrice() {
        return price;
    }

    public int getOccurrence() {
        return occurrence;
    }
}
