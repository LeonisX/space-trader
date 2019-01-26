package spacetrader.game.quest;

import spacetrader.controls.Rectangle;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.ShipSpec;
import spacetrader.game.quest.containers.ShipSpecContainer;
import spacetrader.game.quest.enums.EventName;
import spacetrader.game.quest.enums.QuestName;
import spacetrader.game.quest.quests.AbstractQuest;
import spacetrader.game.quest.quests.Quest;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class QuestSystem implements Serializable {

    static final long serialVersionUID = -1771570019223312592L;

    private static final Logger log = Logger.getLogger(QuestSystem.class.getName());

    private static final String QUEST_CLASS_TEMPLATE = QuestSystem.class.getName().replace("QuestSystem", "quests.%sQuest");

    private volatile Map<String, Quest> quests;
    private volatile Map<EventName, List<String>> eventListeners;

    private Map<Integer, Quest> questMercenaries = new HashMap<>();
    private Map<Integer, Quest> questNews = new HashMap<>();
    private Map<Integer, Quest> questShipSpecs = new HashMap<>();
    private Map<Integer, Quest> questGameEndTypes = new HashMap<>();
    private Map<Integer, Quest> questEncounters = new HashMap<>();
    private Map<Integer, Quest> questVeryRareEncounters = new HashMap<>();

    private volatile AtomicInteger specialCrewIdCounter = new AtomicInteger(1000);
    private volatile AtomicInteger newsIdCounter = new AtomicInteger(1000);
    private volatile AtomicInteger encounterIdCounter = new AtomicInteger(1000);
    private volatile AtomicInteger shipSpecIdCounter = new AtomicInteger(1000);
    private volatile AtomicInteger gameEndTypeIdCounter = new AtomicInteger(1000);
    private volatile AtomicInteger veryRareEncounter = new AtomicInteger(10000);
    private volatile AtomicInteger opponentTypeEncounter = new AtomicInteger(1000);

    private transient Set<String> questsBeforeTransaction = new HashSet<>();

    public void initializeQuestsHolder() {
        setQuestsMap(new HashMap<>());
        setEventListeners(new HashMap<>());
        Arrays.stream(EventName.values()).forEach(e -> eventListeners.put(e, new ArrayList<>()));

        Arrays.stream(QuestName.values()).forEach(this::initialize);

        log.fine("initialized");
    }

    private void initialize(QuestName id) {
        try {
            Class<?> clazz = Class.forName(String.format(QUEST_CLASS_TEMPLATE, id.name()));
            Field field = clazz.getDeclaredField("OCCURRENCE");
            field.setAccessible(true);
            int occurrence = Math.max(field.getInt(null), 1);
            for (int i = 0; i < occurrence; i++) {
                Constructor<?> c = clazz.getConstructor(String.class);
                String questId = getQuestId(id, i);
                Quest quest = (Quest) c.newInstance(questId);
                getQuestsMap().put(questId, quest);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getQuestId(QuestName questName, int id) {
        return questName.name() + "[" + id + "]";
    }

    public CrewMember registerNewSpecialCrewMember(CrewMember crewMember, Quest quest) {
        Game.getCurrentGame().getMercenaries().put(crewMember.getId(), crewMember);
        questMercenaries.put(crewMember.getId(), quest);
        return crewMember;
    }

    public void registerNews(int newsId, Quest quest) {
        questNews.put(newsId, quest);
    }

    public int registerNewShipSpec(ShipSpec shipSpec, AbstractQuest quest) {
        int shipSpecId = generateShipSpecIdCounter();
        Game.getCurrentGame().getShipSpecs().put(shipSpecId, shipSpec.withId(shipSpecId));
        questShipSpecs.put(shipSpecId, quest);
        return shipSpecId;
    }

    public int registerNewGameEndType(AbstractQuest quest) {
        int gameEndTypeId = generateGameEndTypeIdCounter();
        questGameEndTypes.put(gameEndTypeId, quest);
        return gameEndTypeId;
    }

    public int registerNewEncounter(AbstractQuest quest) {
        int encounterId = generateEncounterIdCounter();
        questEncounters.put(encounterId, quest);
        return encounterId;
    }

    public Integer registerNewVeryRareEncounter(AbstractQuest quest) {
        Integer veryRareEncounterId = generateVeryRareEncounterIdCounter();
        questVeryRareEncounters.put(veryRareEncounterId, quest);
        return veryRareEncounterId;
    }

    public int registerNewOpponentType() {
        int opponentTypeId = generateOpponentTypeIdCounter();
        Game.getCurrentGame().getOpponentTypes().add(opponentTypeId);
        return opponentTypeId;
    }

    public void initializeLoggers() {
        quests.values().forEach(q -> q.initializeLogger(q));
    }

    private void setQuestsMap(Map<String, Quest> map) {
        quests = map;
    }

    private Map<String, Quest> getQuestsMap() {
        return quests;
    }

    //TODO right now this works only for quests with occurrence 0, 1
    public Quest getQuest(QuestName questName) {
        return quests.get(getQuestId(questName, 0));
    }

    public int generateSpecialCrewId() {
        return specialCrewIdCounter.getAndIncrement();
    }

    public int generateNewsId() {
        return newsIdCounter.getAndIncrement();
    }

    private int generateShipSpecIdCounter() {
        return shipSpecIdCounter.getAndIncrement();
    }

    private int generateGameEndTypeIdCounter() {
        return gameEndTypeIdCounter.getAndIncrement();
    }

    private int generateEncounterIdCounter() {
        return encounterIdCounter.getAndIncrement();
    }

    private Integer generateVeryRareEncounterIdCounter() {
        return veryRareEncounter.getAndIncrement();
    }

    private int generateOpponentTypeIdCounter() {
        return opponentTypeEncounter.getAndIncrement();
    }

    public int[] affectSkills(int[] skills) {
        //log.fine("affectSkills: " + Arrays.toString(skills));
        int[] skillsCopy = skills.clone();
        getQuests().forEach(q -> q.affectSkills(skillsCopy));
        //log.fine("skillsCopy: " + Arrays.toString(skillsCopy));
        return skillsCopy;
    }

    //TODO may be need to do universal, not only for hull
    public int affectShipHullStrength(ShipSpec shipSpec) {
        //log.fine("affectShipHullStrength: " + shipSpec);
        ShipSpecContainer shipSpecContainer = new ShipSpecContainer(shipSpec);
        getQuests().forEach(q -> q.affectShipHullStrength(shipSpecContainer));
        //log.fine("characteristicsCopy: " + shipSpecContainer.getHullStrength());
        return shipSpecContainer.getHullStrength();
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

    public void subscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.getClass().getName());
        getEventListeners().get(eventName).add(quest.getId());
    }

    public void unSubscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.getClass().getName());
        //TODO need fix
        getEventListeners().get(eventName).removeIf(q -> q.getClass().equals(quest.getClass()));
    }

    public void unSubscribeAll(Quest quest) {
        log.fine(quest.toString());
        getEventListeners().forEach((key, value) -> unSubscribe(key, quest));
    }

    private Map<EventName, List<String>> getEventListeners() {
        return eventListeners;
    }

    private void setEventListeners(Map<EventName, List<String>> eventListeners) {
        this.eventListeners = eventListeners;
    }

    //TODO optimize need???
    public Stream<Phase> getPhasesStream() {
        return getQuests().stream().flatMap(q -> q.getPhases().stream());
    }

    public Stream<QuestDialog> getQuestDialogsStream() {
        return getQuests().stream().flatMap(q -> q.getQuestDialogs().stream());
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

    public String getShipName(int shipSpecId) {
        return questShipSpecs.get(shipSpecId).getShipName();
    }

    public Rectangle getShipImageOffset(int shipSpecId) {
        return questShipSpecs.get(shipSpecId).getShipImageOffset();
    }

    public Collection<Integer> getVeryRareEncounters() {
        return questVeryRareEncounters.keySet();
    }

    public String getVeryRareEncounterById(Integer id) {
        return questVeryRareEncounters.get(id).getVeryRareEncounter(id);
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
            Set<String> toRemove = new HashSet<>(quests.keySet());
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
