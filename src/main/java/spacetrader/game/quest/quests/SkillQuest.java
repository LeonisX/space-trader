package spacetrader.game.quest.quests;

import spacetrader.game.quest.*;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;

import java.util.*;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

public class SkillQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511510L;

    // Constants
    private static final Repeatable REPEATABLE = Repeatable.ONE_TIME;
    private static final int OCCURRENCE = 3;

    public SkillQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new SkillPhase());
        initializeTransitionMap();

        registerListener();

        localize();

        log.fine("started...");
    }

    private void initializePhases(QuestPhases[] values, Phase... phases) {
        for (int i = 0; i < phases.length; i++) {
            this.phases.put(values[i], phases[i]);
            phases[i].setQuest(this);
            phases[i].setPhaseEnum(values[i]);
        }
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();

        getTransitionMap().put(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
    }

    @Override
    public Collection<Phase> getPhases() {
        return phases.values();
    }

    @Override
    public Collection<QuestDialog> getQuestDialogs() {
        return phases.keySet().stream().map(QuestPhases::getValue).collect(Collectors.toList());
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.SpecialSkillIncrease).setStarSystemId(occupyFreeSystemWithEvent());
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class SkillPhase extends Phase { //new SpecialEvent(SpecialEventType.Skill, 3000, 3, false),
        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem() && getQuestState() == QuestState.INACTIVE;
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            showAlert(Alerts.SpecialSkillIncrease.getValue());
            getCommander().increaseRandomSkill();
            confirmQuestPhase();
            setQuestState(QuestState.FINISHED);
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "SkillPhase{} " + super.toString();
        }
    }

    private void onSpecialButtonClicked(Object object) {
        Optional<QuestPhases> activePhase =
                phases.entrySet().stream().filter(p -> p.getValue().canBeExecuted()).map(Map.Entry::getKey).findFirst();
        if (activePhase.isPresent()) {
            showDialogAndProcessResult(object, activePhase.get().getValue(), () -> phases.get(activePhase.get()).successFlow());
        } else {
            log.fine("skipped");
        }
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        SpecialSkillIncrease(new QuestDialog(3000, DIALOG, "Skill Increase", "An alien with a fast-learning machine offers to increase one of your skills for the reasonable sum of 3000 credits. You won't be able to pick that skill, though. Do you accept his offer?"));

        private QuestDialog value;

        QuestPhases(QuestDialog value) {
            this.value = value;
        }

        @Override
        public QuestDialog getValue() {
            return value;
        }

        @Override
        public void setValue(QuestDialog value) {
            this.value = value;
        }
    }

    private EnumMap<QuestPhases, Phase> phases = new EnumMap<>(QuestPhases.class);

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialSkillIncrease("Skill Increase", "The alien increases one of your skills.");

        private AlertDialog value;

        Alerts(String title, String body) {
            this.value = new AlertDialog(title, body);
        }

        @Override
        public AlertDialog getValue() {
            return value;
        }

        @Override
        public void setValue(AlertDialog value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "SkillQuest{} " + super.toString();
    }
}
