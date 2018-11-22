package spacetrader.game.quest;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

public class QuestsHolder implements Serializable {


    //TODO interpret news from quests by id

    private volatile List<Quest> quests = new ArrayList<>();
    private volatile Map<EventName, List<Quest>> eventListeners;

    private volatile int specialCrewIdCounter = 1000;
    private volatile int newsIdCounter = 1000;

    private int transactionStart = -1;

    private static QuestsHolder questsHolder;


    public static QuestsHolder initializeQuestsHolder() {
        questsHolder = new QuestsHolder();

        questsHolder.setEventListeners(new HashMap<>());
        Arrays.stream(EventName.values()).forEach(e -> questsHolder.eventListeners.put(e, new ArrayList<>()));

        questsHolder.getQuests().add(new LotteryQuest());
        questsHolder.getQuests().add(new JarekQuest());

        return questsHolder;
    }

    public static int generateSpecialCrewId() {
        return questsHolder.specialCrewIdCounter++;
    }

    public static int generateNewsId() {
        return questsHolder.newsIdCounter++;
    }

    public static int[] affectSkills(int[] skills) {
        int[] skillsCopy = skills.clone();
        questsHolder.getQuests().forEach(q -> q.affectSkills(skillsCopy));
        return skillsCopy;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public static void fireEvent(EventName eventName) {
        questsHolder.getEventListeners().entrySet().stream().filter(e -> e.getKey().equals(eventName))
                .forEach(listener -> new ArrayList<>(listener.getValue()).forEach(l -> l.getCurrentOperation(eventName).accept(null)));
    }

    public static void fireEvent(EventName eventName, Object object) {
        questsHolder.getEventListeners().entrySet().stream().filter(e -> e.getKey().equals(eventName))
                .forEach(entry -> new ArrayList<>(entry.getValue()).forEach(q -> {
                    if (q.getCurrentOperation(eventName) != null) {
                        q.getCurrentOperation(eventName).accept(object);
                    }
                }));
    }

    public static void subscribe(EventName eventName, Quest quest) {
        questsHolder.getEventListeners().get(eventName).add(quest);
    }

    public static void unSubscribe(EventName eventName, Quest quest) {
        questsHolder.getEventListeners().get(eventName).removeIf(q -> q.getClass().equals(quest.getClass()));
    }

    public static void unSubscribeAll(Quest quest) {
        questsHolder.getEventListeners().forEach((key, value) -> unSubscribe(key, quest));
    }

    public Map<EventName, List<Quest>> getEventListeners() {
        return eventListeners;
    }

    public void setEventListeners(Map<EventName, List<Quest>> eventListeners) {
        this.eventListeners = eventListeners;
    }

    //TODO optimize
    public Stream<Phase> getPhases() {
        return getQuests().stream().flatMap(q -> q.getPhases().stream());
    }

    public void startTransaction() {
        transactionStart = quests.size();
    }

    public void rollbackTransaction() {
        if (transactionStart >= 0) {
            List<Quest> toRemove = quests.subList(transactionStart, quests.size());
            quests.removeAll(toRemove);
            eventListeners.values().forEach(e -> e.removeAll(toRemove));
        }
        transactionStart = -1;
    }
}
