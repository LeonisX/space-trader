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

import static java.util.stream.Collectors.toList;

public class QuestSystem implements Serializable {

    static final long serialVersionUID = -1771570019223312592L;

    private static final Logger log = Logger.getLogger(QuestSystem.class.getName());

    private volatile Map<Integer, Quest> quests;
    private volatile Map<EventName, List<Integer>> eventListeners;

    private Map<Integer, Quest> questMercenaries = new HashMap<>();
    private Map<Integer, Quest> questNews = new HashMap<>();
    private Map<Integer, Quest> questShipSpecs = new HashMap<>();
    private Map<Integer, Quest> questGameEndTypes = new HashMap<>();

    private volatile int questCounter = 1;
    private volatile int specialCrewIdCounter = 1000;
    private volatile int newsIdCounter = 1000;
    private volatile int encounterIdCounter = 1000;
    private volatile int shipSpecIdCounter = 1000;
    private volatile int gameEndTypeIdCounter = 1000;

    private transient int transactionStart = -1;

    private static QuestSystem questSystem;

    public static QuestSystem initializeQuestsHolder() {
        questSystem = new QuestSystem();

        questSystem.setQuestsMap(new HashMap<>());
        questSystem.setEventListeners(new HashMap<>());
        Arrays.stream(EventName.values()).forEach(e -> questSystem.eventListeners.put(e, new ArrayList<>()));

        initialize(LotteryQuest.class);
        initialize(JarekQuest.class);
        initialize(PrincessQuest.class);

        log.fine("initialized");
        return questSystem;
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
                questSystem.getQuestsMap().put(id, quest);
            }
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    static CrewMember registerNewSpecialCrewMember(CrewMember crewMember, Quest quest) {
        Game.getCurrentGame().getMercenaries().put(crewMember.getId(), crewMember);
        questSystem.questMercenaries.put(crewMember.getId(), quest);
        return crewMember;
    }

    static void registerNews(int newsId, Quest quest) {
        questSystem.questNews.put(newsId, quest);
    }

    static int registerNewShipSpec(ShipSpec shipSpec, AbstractQuest quest) {
        int shipSpecId = generateShipSpecIdCounter();
        Game.getCurrentGame().getShipSpecs().put(shipSpecId, shipSpec.withId(shipSpecId));
        questSystem.questShipSpecs.put(shipSpecId, quest);
        return shipSpecId;
    }

    static int registerNewGameEndType(AbstractQuest quest) {
        int gameEndTypeId = generateGameEndTypeIdCounter();
        questSystem.questGameEndTypes.put(gameEndTypeId, quest);
        return gameEndTypeId;
    }

    public static void initializeLoggers() {
        questSystem.quests.values().forEach(q -> q.initializeLogger(q));
    }

    private void setQuestsMap(Map<Integer, Quest> map) {
        quests = map;
    }

    private Map<Integer, Quest> getQuestsMap() {
        return quests;
    }

    static int generateSpecialCrewId() {
        return questSystem.specialCrewIdCounter++;
    }

    static int generateNewsId() {
        return questSystem.newsIdCounter++;
    }

    //TODO ??? need to be used???
    static int generateEncounterId() {
        return questSystem.encounterIdCounter++;
    }

    private static int generateShipSpecIdCounter() {
        return questSystem.shipSpecIdCounter++;
    }

    private static int generateGameEndTypeIdCounter() {
        return questSystem.gameEndTypeIdCounter++;
    }

    private static int generateQuestId() {
        return 10000 * questSystem.questCounter++;
    }

    public static int[] affectSkills(int[] skills) {
        //log.fine("affectSkills: " + Arrays.toString(skills));
        int[] skillsCopy = skills.clone();
        questSystem.getQuests().forEach(q -> q.affectSkills(skillsCopy));
        //log.fine("skillsCopy: " + Arrays.toString(skillsCopy));
        return skillsCopy;
    }

    public Collection<Quest> getQuests() {
        return quests.values();
    }

    public static void fireEvent(EventName eventName) {
        if (Game.getCurrentGame() != null) {
            log.fine(eventName.toString());
            questSystem.getEventListeners().get(eventName).forEach(q -> {
                if (questSystem.quests.get(q).getOperation(eventName) != null) {
                    questSystem.quests.get(q).getOperation(eventName).accept(null);
                }
            });
        }
    }

    public static void fireEvent(EventName eventName, Object object) {
        if (Game.getCurrentGame() != null) {
            log.fine(eventName.toString() + "; " + object.toString());
            questSystem.getEventListeners().get(eventName).forEach(q -> {
                if (questSystem.quests.get(q).getOperation(eventName) != null) {
                    questSystem.quests.get(q).getOperation(eventName).accept(object);
                }
            });
        }
    }

    public static void initializeTransitionMaps() {
        questSystem.quests.values().forEach(Quest::initializeTransitionMap);
    }

    static void subscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.getClass().getName());
        questSystem.getEventListeners().get(eventName).add(quest.getId());
    }

    static void unSubscribe(EventName eventName, Quest quest) {
        log.fine(eventName.toString() + "; " + quest.getClass().getName());
        questSystem.getEventListeners().get(eventName).removeIf(q -> q.getClass().equals(quest.getClass()));
    }

    static void unSubscribeAll(Quest quest) {
        log.fine(quest.toString());
        questSystem.getEventListeners().forEach((key, value) -> unSubscribe(key, quest));
    }

    private Map<EventName, List<Integer>> getEventListeners() {
        return eventListeners;
    }

    private void setEventListeners(Map<EventName, List<Integer>> eventListeners) {
        this.eventListeners = eventListeners;
    }

    //TODO optimize need???
    public static Stream<Phase> getPhasesStream() {
        return questSystem.getQuests().stream().flatMap(q -> q.getPhases().stream());
    }

    public static String getNewsTitle(int newsEventId) {
        return questSystem.questNews.get(newsEventId).getNewsTitle(newsEventId);
    }

    public static String getCrewMemberName(int id) {
        return questSystem.questMercenaries.get(id).getCrewMemberName(id);
    }

    public static String getGameCompletionText(int gameEndTypeId) {
        return questSystem.questGameEndTypes.get(gameEndTypeId).getGameCompletionText();
    }

    public static Integer getShipImageIndex(int shipSpecId) {
        return questSystem.questShipSpecs.get(shipSpecId).getShipImageIndex();
    }

    public static Rectangle getShipImageOffset(int shipSpecId) {
        return questSystem.questShipSpecs.get(shipSpecId).getShipImageOffset();
    }

    public static QuestSystem getQuestSystem() {
        return questSystem;
    }

    public static void setQuestSystem(QuestSystem questSystem) {
        QuestSystem.questSystem = questSystem;
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

    @Override
    public String toString() {
        return "QuestSystem{" +
                "quests=" + quests +
                ", eventListeners=" + eventListeners +
                ", questCounter=" + questCounter +
                ", specialCrewIdCounter=" + specialCrewIdCounter +
                ", newsIdCounter=" + newsIdCounter +
                ", transactionStart=" + transactionStart +
                '}';
    }

}
