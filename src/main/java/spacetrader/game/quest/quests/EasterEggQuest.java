package spacetrader.game.quest.quests;

import spacetrader.game.Consts;
import spacetrader.game.enums.ShieldType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.I18n;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestDialog;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.stream.Collectors;

import static spacetrader.game.quest.enums.EventName.ON_AFTER_GAME_INITIALIZE;
import static spacetrader.game.quest.enums.EventName.ON_ARRIVAL;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.Repeatable.ONE_TIME;

/* This Easter Egg gives the commander a Lighting Shield */
public class EasterEggQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511519L;

    private static final Repeatable REPEATABLE = ONE_TIME;
    private static final int OCCURRENCE = 0;

    public EasterEggQuest(String id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);
        initializePhases(QuestPhases.values(), new EasterEggPhase());
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
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();
        getTransitionMap().put(ON_AFTER_GAME_INITIALIZE, this::onAfterGameInitialize);
        getTransitionMap().put(ON_ARRIVAL, this::onArrival);
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

    private void onAfterGameInitialize(Object object) {
        log.fine("");
        phases.get(QuestPhases.EasterEgg).setStarSystemId(StarSystemId.Og);
    }

    private void onArrival(Object object) {
        log.fine("");
        if (phases.get(QuestPhases.EasterEgg).canBeExecuted() && isQuestIsInactive()) {
            phases.get(QuestPhases.EasterEgg).successFlow();
            log.fine("executed");
        } else {
            log.fine("skipped");
        }
    }

    class EasterEggPhase extends Phase {

        @Override
        public boolean canBeExecuted() {
            if (!isDesiredSystem()) {
                return false;
            }
            if (getShip().getFreeShieldSlots() <= 0) {
                return false;
            }
            for (int i = 0; i < getShip().getCargo().length; i++) {
                if (getShip().getCargo()[i] != 1) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public void successFlow() {
            showAlert(QuestPhases.EasterEgg.getValue());
            getShip().addEquipment(Consts.Shields[ShieldType.LIGHTNING.castToInt()]);
            for (int i = 0; i < getShip().getCargo().length; i++) {
                getShip().getCargo()[i] = 0;
                getCommander().getPriceCargo()[i] = 0;
            }

            unRegisterAllOperations();
            phases.get(QuestPhases.EasterEgg).confirmQuestPhase();
            setQuestState(QuestState.FINISHED);
        }

        @Override
        public String toString() {
            return "FirstPhase{} " + super.toString();
        }
    }

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        EasterEgg(new QuestDialog(ALERT, "Egg", "Congratulations! An eccentric Easter Bunny decides to exchange your trade goods for a special present!"));

        private QuestDialog value;
        QuestPhases(QuestDialog value) { this.value = value; }
        @Override public QuestDialog getValue() { return value; }
        @Override public void setValue(QuestDialog value) { this.value = value; }
    }

    private EnumMap<QuestPhases, Phase> phases = new EnumMap<>(QuestPhases.class);

    @Override
    public String toString() {
        return "EasterEggQuest{} " + super.toString();
    }
}
