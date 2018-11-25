package spacetrader.game.quest;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class QuestsHolder implements Serializable {

    private static final Logger log = Logger.getLogger(LotteryQuest.class.getName());

    private volatile Map<Integer, Quest> quests;
    private volatile Map<EventName, List<Integer>> eventListeners;

    private volatile int questCounter = 1;
    private volatile int specialCrewIdCounter = 1000;
    private volatile int newsIdCounter = 1000;

    private int transactionStart = -1;

    private static QuestsHolder questsHolder;


    public static QuestsHolder initializeQuestsHolder() {
        questsHolder = new QuestsHolder();

        questsHolder.setQuestsMap(new HashMap<>());
        questsHolder.setEventListeners(new HashMap<>());
        Arrays.stream(EventName.values()).forEach(e -> questsHolder.eventListeners.put(e, new ArrayList<>()));

        initialize(LotteryQuest.class);
        initialize(JarekQuest.class);

        log.fine("initialized");
        return questsHolder;
    }


    private static void initialize(Class<? extends Quest> clazz) {
        try {
            Field field = clazz.getDeclaredField("OCCURRENCE");
            field.setAccessible(true);
            int occurrence = Math.max(field.getInt(null), 1);
            for (int i = 0; i < occurrence; i++) {
                Integer id = generateQuestId();
                Constructor<?> c = clazz.getConstructor(id.getClass());
                Quest quest = (Quest) c.newInstance(id);
                questsHolder.getQuestsMap().put(id, quest);
            }

        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void setQuestsMap(Map<Integer, Quest> map) {
        quests = map;
    }

    private Map<Integer, Quest> getQuestsMap() {
        return quests;
    }

    public static int generateSpecialCrewId() {
        return questsHolder.specialCrewIdCounter++;
    }

    public static int generateNewsId() {
        return questsHolder.newsIdCounter++;
    }

    public static int generateQuestId() {
        return questsHolder.questCounter++;
    }

    public static int[] affectSkills(int[] skills) {
        //log.fine("affectSkills: " + Arrays.toString(skills));
        int[] skillsCopy = skills.clone();
        questsHolder.getQuests().forEach(q -> q.affectSkills(skillsCopy));
        //log.fine("skillsCopy: " + Arrays.toString(skillsCopy));
        return skillsCopy;
    }

    public Collection<Quest> getQuests() {
        return quests.values();
    }

    public static void fireEvent(EventName eventName) {
        log.fine(eventName.toString());
        questsHolder.getEventListeners().entrySet().stream().filter(e -> e.getKey().equals(eventName))
                .forEach(listener -> new ArrayList<>(listener.getValue())
                        .forEach(l -> questsHolder.quests.get(l).getCurrentOperation(eventName).accept(null)));
    }

    public static void fireEvent(EventName eventName, Object object) {
        log.fine(eventName.toString() + "; " + object.toString());
        questsHolder.getEventListeners().entrySet().stream().filter(e -> e.getKey().equals(eventName))
                .forEach(entry -> new ArrayList<>(entry.getValue()).forEach(q -> {
                    if (questsHolder.quests.get(q).getCurrentOperation(eventName) != null) {
                        questsHolder.quests.get(q).getCurrentOperation(eventName).accept(object);
                    }
                }));
    }

    public static void subscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.toString());
        questsHolder.getEventListeners().get(eventName).add(quest.getId());
    }

    public static void unSubscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.toString());
        questsHolder.getEventListeners().get(eventName).removeIf(q -> q.getClass().equals(quest.getClass()));
    }

    public static void unSubscribeAll(Quest quest) {
        log.fine(quest.toString());
        questsHolder.getEventListeners().forEach((key, value) -> unSubscribe(key, quest));
    }

    public Map<EventName, List<Integer>> getEventListeners() {
        return eventListeners;
    }

    public void setEventListeners(Map<EventName, List<Integer>> eventListeners) {
        this.eventListeners = eventListeners;
    }

    //TODO optimize
    public Stream<Phase> getPhases() {
        return getQuests().stream().flatMap(q -> q.getPhases().stream());
    }

    public static String getNewsTitle(int newsEventId) {
        return questsHolder.getQuests().stream().filter(q -> q.getNewsId() == newsEventId).findFirst().get().getNewsTitle();
    }

    public static String getCrewMemberName(int id) {
        return questsHolder.getQuests().stream().filter(q -> q.getSpecialCrewId() == id).findFirst().get().getCrewMemberName();
    }

    //TODO test
    public void startTransaction() {
        log.fine("" + questCounter);
        transactionStart = questCounter;
    }

    //TODO test
    public void rollbackTransaction() {
        if (transactionStart >= 0) {
            log.fine("started");
            List<Integer> toRemove = quests.keySet().stream().filter(k -> k > transactionStart).collect(toList());
            log.fine(toRemove.toString());

            log.fine(quests.toString());
            toRemove.forEach(key -> quests.remove(key));
            log.fine(quests.toString());
            log.fine(eventListeners.toString());
            eventListeners.values().forEach(e -> e.removeAll(toRemove));
            log.fine(eventListeners.toString());
        } else {
            log.fine("skipped");
        }
        transactionStart = -1;
    }
}
