package spacetrader.game.quest;

import spacetrader.controls.Rectangle;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.ShipSpec;
import spacetrader.game.quest.enums.EventName;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

enum QuestName {

    Lottery,
    Jarek,
    Princess,
    Wild,
    Sculpture

}

public class QuestSystem implements Serializable {

    static final long serialVersionUID = -1771570019223312592L;

    private static final String questClassTemplate = QuestSystem.class.getName().replace("QuestSystem", "%sQuest");

    private static final Logger log = Logger.getLogger(QuestSystem.class.getName());

    private volatile EnumMap<QuestName, Quest> quests;
    private volatile Map<EventName, List<QuestName>> eventListeners;

    private Map<Integer, Quest> questMercenaries = new HashMap<>();
    private Map<Integer, Quest> questNews = new HashMap<>();
    private Map<Integer, Quest> questShipSpecs = new HashMap<>();
    private Map<Integer, Quest> questGameEndTypes = new HashMap<>();

    private volatile int specialCrewIdCounter = 1000;
    private volatile int newsIdCounter = 1000;
    private volatile int encounterIdCounter = 1000;
    private volatile int shipSpecIdCounter = 1000;
    private volatile int gameEndTypeIdCounter = 1000;

    private transient Set<QuestName> questsBeforeTransaction = new HashSet<>();

    public void initializeQuestsHolder() {
        setQuestsMap(new EnumMap<>(QuestName.class));
        setEventListeners(new HashMap<>());
        Arrays.stream(EventName.values()).forEach(e -> eventListeners.put(e, new ArrayList<>()));

        Arrays.stream(QuestName.values()).forEach(this::initialize);

        log.fine("initialized");
    }

    private void initialize(QuestName id) {
        try {
            Class<?> clazz = Class.forName(String.format(questClassTemplate, id.name()));
            Field field = clazz.getDeclaredField("OCCURRENCE");
            field.setAccessible(true);
            int occurrence = Math.max(field.getInt(null), 1);
            for (int i = 0; i < occurrence; i++) {
                Constructor<?> c = clazz.getConstructor(id.getClass());
                Quest quest = (Quest) c.newInstance(id);
                getQuestsMap().put(id, quest);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    CrewMember registerNewSpecialCrewMember(CrewMember crewMember, Quest quest) {
        Game.getCurrentGame().getMercenaries().put(crewMember.getId(), crewMember);
        questMercenaries.put(crewMember.getId(), quest);
        return crewMember;
    }

    void registerNews(int newsId, Quest quest) {
        questNews.put(newsId, quest);
    }

    int registerNewShipSpec(ShipSpec shipSpec, AbstractQuest quest) {
        int shipSpecId = generateShipSpecIdCounter();
        Game.getCurrentGame().getShipSpecs().put(shipSpecId, shipSpec.withId(shipSpecId));
        questShipSpecs.put(shipSpecId, quest);
        return shipSpecId;
    }

    int registerNewGameEndType(AbstractQuest quest) {
        int gameEndTypeId = generateGameEndTypeIdCounter();
        questGameEndTypes.put(gameEndTypeId, quest);
        return gameEndTypeId;
    }

    public void initializeLoggers() {
        quests.values().forEach(q -> q.initializeLogger(q));
    }

    private void setQuestsMap(EnumMap<QuestName, Quest> map) {
        quests = map;
    }

    EnumMap<QuestName, Quest> getQuestsMap() {
        return quests;
    }

    Quest getQuest(QuestName questName) {
        return quests.get(questName);
    }

    int generateSpecialCrewId() {
        return specialCrewIdCounter++;
    }

    int generateNewsId() {
        return newsIdCounter++;
    }

    //TODO ??? need to be used???
    int generateEncounterId() {
        return encounterIdCounter++;
    }

    private int generateShipSpecIdCounter() {
        return shipSpecIdCounter++;
    }

    private int generateGameEndTypeIdCounter() {
        return gameEndTypeIdCounter++;
    }

    public int[] affectSkills(int[] skills) {
        //log.fine("affectSkills: " + Arrays.toString(skills));
        int[] skillsCopy = skills.clone();
        getQuests().forEach(q -> q.affectSkills(skillsCopy));
        //log.fine("skillsCopy: " + Arrays.toString(skillsCopy));
        return skillsCopy;
    }

    public Collection<Quest> getQuests() {
        return quests.values();
    }

    public void fireEvent(EventName eventName) {
        if (Game.getCurrentGame() != null) {
            log.fine(eventName.toString());
            getEventListeners().get(eventName).forEach(q -> {
                if (quests.get(q).getOperation(eventName) != null) {
                    quests.get(q).getOperation(eventName).accept(null);
                }
            });
        }
    }

    public void fireEvent(EventName eventName, Object object) {
        if (Game.getCurrentGame() != null) {
            log.fine(eventName.toString() + "; " + object.toString());
            getEventListeners().get(eventName).forEach(q -> {
                if (quests.get(q).getOperation(eventName) != null) {
                    quests.get(q).getOperation(eventName).accept(object);
                }
            });
        }
    }

    public void initializeTransitionMaps() {
        quests.values().forEach(Quest::initializeTransitionMap);
    }

    void subscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.getClass().getName());
        getEventListeners().get(eventName).add(quest.getId());
    }

    void unSubscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.getClass().getName());
        getEventListeners().get(eventName).removeIf(q -> q.getClass().equals(quest.getClass()));
    }

    void unSubscribeAll(Quest quest) {
        log.fine(quest.toString());
        getEventListeners().forEach((key, value) -> unSubscribe(key, quest));
    }

    private Map<EventName, List<QuestName>> getEventListeners() {
        return eventListeners;
    }

    private void setEventListeners(Map<EventName, List<QuestName>> eventListeners) {
        this.eventListeners = eventListeners;
    }

    //TODO optimize need???
    public Stream<Phase> getPhasesStream() {
        return getQuests().stream().flatMap(q -> q.getPhases().stream());
    }

    public String getNewsTitle(int newsEventId) {
        return questNews.get(newsEventId).getNewsTitle(newsEventId);
    }

    public String getCrewMemberName(int id) {
        return questMercenaries.get(id).getCrewMemberName(id);
    }

    public String getGameCompletionText(int gameEndTypeId) {
        return questGameEndTypes.get(gameEndTypeId).getGameCompletionText();
    }

    public Integer getShipImageIndex(int shipSpecId) {
        return questShipSpecs.get(shipSpecId).getShipImageIndex();
    }

    public Rectangle getShipImageOffset(int shipSpecId) {
        return questShipSpecs.get(shipSpecId).getShipImageOffset();
    }

    //TODO test
    public void startTransaction() {
        log.fine(quests.keySet().toString());
        questsBeforeTransaction = quests.keySet();
    }

    //TODO test
    public void rollbackTransaction() {
        if (!questsBeforeTransaction.isEmpty()) {
            log.fine("started");
            Set<QuestName> toRemove = new HashSet<>(quests.keySet());
            toRemove.removeAll(questsBeforeTransaction);
            log.fine(toRemove.toString());

            log.fine(quests.toString());
            quests.keySet().removeAll(toRemove);
            log.fine(quests.toString());
            log.fine(eventListeners.toString());
            eventListeners.values().forEach(e -> e.removeAll(toRemove));
            log.fine(eventListeners.toString());
        } else {
            log.fine("skipped");
        }
        questsBeforeTransaction = new HashSet<>();
    }

    @Override
    public String toString() {
        return "QuestSystem{" +
                "quests=" + quests +
                ", eventListeners=" + eventListeners +
                ", specialCrewIdCounter=" + specialCrewIdCounter +
                ", newsIdCounter=" + newsIdCounter +
                '}';
    }

    public void localizeQuests() {
        quests.values().forEach(Quest::localize);
    }
}
