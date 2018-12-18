package spacetrader.game.quest;

import spacetrader.controls.Rectangle;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.ScoreContainer;
import spacetrader.game.quest.containers.StringContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.gui.FormAlert;
import spacetrader.guifacade.GuiFacade;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;

import java.io.Serializable;
import java.util.*;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

class PrincessQuest extends AbstractQuest implements Serializable {

    static final long serialVersionUID = -4731305242511503L;

    enum Phases implements SimpleValueEnum<QuestDialog> {
        Princess(new QuestDialog(ALERT, "Kidnapped", "A member of the Royal Family of Galvon has been kidnapped! Princess Ziyal was abducted by men while travelling across the planet. They escaped in a hi-tech ship called the Scorpion. Please rescue her! (You'll need to equip your ship with disruptors to be able to defeat the Scorpion without destroying it.) A ship bristling with weapons was blasting out of the system. It's trajectory before going to warp indicates that its destination was Centauri.")),
        PrincessCentauri(new QuestDialog(ALERT, "Aggressive Ship", "A ship had its shields upgraded to Lighting Shields just two days ago. A shipyard worker overheard one of the crew saying they were headed to Inthara.")),
        PrincessInthara(new QuestDialog(ALERT, "Dangerous Scorpion", "Just yesterday a ship was seen in docking bay 327. A trader sold goods to a member of the crew, who was a native of Qonos. It's possible that's where they were going next.")),
        PrincessQonos(new QuestDialog(DIALOG, "Royal Rescue", "The Galvonian Ambassador to Qonos approaches you. The Princess needs a ride home. Will you take her? I don't think she'll feel safe with anyone else.")),
        PrincessReturned(new QuestDialog(ALERT, "Royal Return", "The King and Queen are extremely grateful to you for returning their daughter to them. The King says, \"Ziyal is priceless to us, but we feel we must offer you something as a reward. Visit my shipyard captain and he'll install one of our new Quantum Disruptors.\"")),
        PrincessQuantum(new QuestDialog(DIALOG, "Quantum Disruptor", "His Majesty's Shipyard: Do you want us to install a quantum disruptor on your current ship?"));

        private QuestDialog value;
        Phases(QuestDialog value) { this.value = value; }
        @Override public QuestDialog getValue() { return value; }
        @Override public void setValue(QuestDialog value) { this.value = value; }
    }

    private EnumMap<Phases, Phase> phases = new EnumMap<>(Phases.class);

    enum Quests implements SimpleValueEnum<String> {
        PrincessCentauri("Follow the Scorpion to Centauri."),
        PrincessInthara("Follow the Scorpion to Inthara."),
        PrincessQonos("Follow the Scorpion to Qonos."),
        PrincessReturn("Transport ^1 from Qonos to Galvon."),
        PrincessReturning("Return ^1 to Galvon."),
        PrincessReturningImpatient("Return ^1 to Galvon." + newline
                + "She is becoming anxious to arrive at home, and is no longer of any help in engineering functions."),
        PrincessQuantum("Get your Quantum Disruptor at Galvon.");

        private String value;
        Quests(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialPassengerConcernedPrincess("Ship's Comm.", "[Ziyal] Oh Captain? (giggles) Would it help if I got out and pushed?"),
        SpecialPassengerImpatientPrincess("Ship's Comm.", "Sir! Are you taking me home or merely taking the place of my previous captors?!"),
        EncounterPiratesSurrenderPrincess("You Have the Princess", "Pirates are not nice people, and there's no telling what they might do to the Princess. Better to die fighting than give her up to them!"),
        PrincessTakenHome("Princess Taken Home", "The Space Corps decides to give the Princess a ride home to Galvon since you obviously weren't up to the task."),
        GameEndBoughtMoonGirl("You Have Retired with the Princess", "");

        private AlertDialog value;
        Alerts(String title, String body) { this.value = new AlertDialog(title, body); }
        @Override public AlertDialog getValue() { return value; }
        @Override public void setValue(AlertDialog value) { this.value = value; }
    }

    enum News implements SimpleValueEnum<String> {
        Princess("Member of Royal Family kidnapped!"),
        PrincessCentauri("Aggressive Ship Seen in Orbit Around Centauri"),
        PrincessInthara("Dangerous Scorpion Damages Several Other Ships Near Inthara"),
        PrincessQonos("Kidnappers Holding Out at Qonos"),
        PrincessRescued("Scorpion Defeated! Kidnapped Member of Galvon Royal Family Freed!"),
        PrincessReturned("Beloved Royal Returns Home!");

        private String value;
        News(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum Encounters implements SimpleValueEnum<String> {
        PretextScorpion("the kidnappers in a ^1"),
        PrincessRescued(newline + newline + "You land your ship near where the Space Corps has landed with the Scorpion in tow. The Princess is revived from hibernation and you get to see her for the first time. Instead of the spoiled child you were expecting, Ziyal is possible the most beautiful woman you've ever seen. \"What took you so long?\" she demands. You notice a twinkle in her eye, and then she smiles. Not only is she beautiful, but she's got a sense of humor. She says, \"Thank you for freeing me. I am in your debt.\" With that she give you a kiss on the cheek, then leaves. You hear her mumble, \"Now about a ride home.\""),
        HidePrincess("the Princess");

        private String value;
        Encounters(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum GameEndings implements SimpleValueEnum<String> {
        ClaimedMoonWithPrincess("Claimed moon with Princess");

        private String value;
        GameEndings(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum CrewNames implements SimpleValueEnum<String> {
        Ziyal("Ziyal"),
        Scorpion("Scorpion");

        private String value;
        CrewNames(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    enum CheatTitles implements SimpleValueEnum<String> {
        Princess("Princess");

        private String value;
        CheatTitles(String value) { this.value = value; }
        @Override public String getValue() { return value; }
        @Override public void setValue(String value) { this.value = value; }
    }

    //TODO switch to phases
    // Constants
    private final static int STATUS_PRINCESS_NOT_STARTED = 0;
    private final static int STATUS_PRINCESS_FLY_CENTAURI = 1;
    private final static int STATUS_PRINCESS_FLY_INTHARA = 2;
    private final static int STATUS_PRINCESS_FLY_QONOS = 3;
    private final static int STATUS_PRINCESS_RESCUED = 4;
    private final static int STATUS_PRINCESS_IMPATIENT = 14;
    private final static int STATUS_PRINCESS_RETURNED = 15;
    private final static int STATUS_PRINCESS_DONE = 16;

    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;
    private static final int CASH_TO_SPEND = 0;

    private volatile int questStatusPrincess = 0; // 0 = not available, 1 = Go to Centauri, 2 = Go to Inthara, 3 =
    // Go to Qonos, 4 = Princess Rescued, 5-14 = On Board, 15 = Princess Returned, 16 = Got Quantum Disruptor

    private CrewMember princess;
    private CrewMember scorpionCrew;

    private int shipSpecId;

    private Ship scorpion; //ShipType.SCORPION

    private static final Rectangle SHIP_IMAGE_OFFSET = new Rectangle(2, 0, 60, 0);
    private static final Integer SHIP_IMAGE_INDEX = 16;

    private boolean princessOnBoard;

    private int gameEndTypeId;

    public PrincessQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);
        initializePhases(Phases.values(), new PrincessPhase(), new PrincessCentauriPhase(), new PrincessIntharaPhase(),
                new PrincessQonosPhase(), new PrincessReturnedPhase(), new PrincessQuantumPhase());
        initializeTransitionMap();

        int d = Game.getDifficultyId();
        princess = registerNewSpecialCrewMember(4, 3, 8, 9);
        scorpionCrew = registerNewSpecialCrewMember(8 + d, 8 + d, 1, 6 + d);

        registerNews(News.values().length);

        gameEndTypeId = registerNewGameEndType();

        //setSpecialCrewId(princess.getId());

        registerListener();

        localize();

        log.fine("started...");
    }

    private void initializePhases(PrincessQuest.Phases[] values, Phase... phases) {
        for (int i = 0; i < phases.length; i++) {
            this.phases.put(values[i], phases[i]);
            phases[i].setQuest(this);
            phases[i].setPhaseEnum(values[i]);
        }
    }

    @Override
    public void initializeTransitionMap() {
        super.initializeTransitionMap();

        getTransitionMap().put(AFTER_SHIP_SPECS_INITIALIZED, this::afterShipSpecInitialized);

        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);
        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);

        getTransitionMap().put(ON_DETERMINE_NON_RANDOM_ENCOUNTER, this::onDetermineNonRandomEncounter);
        getTransitionMap().put(ON_CREATE_SHIP, this::onCreateShip);
        getTransitionMap().put(ON_ENCOUNTER_EXECUTE_ACTION_OPPONENT_DISABLED, this::onEncounterExecuteActionOpponentDisabled);
        getTransitionMap().put(ON_ENCOUNTER_EXECUTE_ATTACK_KEEP_SPECIAL_SHIP, this::onEncounterExecuteAttackKeepSpecialShip);

        getTransitionMap().put(ON_ENCOUNTER_VERIFY_ATTACK, this::onEncounterVerifyAttack);
        getTransitionMap().put(ON_ENCOUNTER_VERIFY_SURRENDER_NO_HIDDEN, this::onEncounterVerifySurrenderNoHidden);
        getTransitionMap().put(ON_ENCOUNTER_VERIFY_SURRENDER_HIDDEN, this::onEncounterVerifySurrenderHidden);

        getTransitionMap().put(ON_GET_STEALABLE_CARGO, this::onGetStealableCargo);

        getTransitionMap().put(ON_GET_ENCOUNTER_TEXT_INITIAL, this::onGetEncounterTextInitial);

        getTransitionMap().put(ON_BEFORE_GAME_END, this::onBeforeGameEnd);
        getTransitionMap().put(ON_GAME_END_ALERT, this::onGameEndAlert);
        getTransitionMap().put(ON_GET_GAME_SCORE, this::onGetGameScore);
    }

    @Override
    public Collection<Phase> getPhases() {
        return phases.values();
    }

    @Override
    public Rectangle getShipImageOffset() {
        return SHIP_IMAGE_OFFSET;
    }

    @Override
    public Integer getShipImageIndex() {
        return SHIP_IMAGE_INDEX;
    }

    @Override
    public void dumpAllStrings() {
        System.out.println("\n\n## Princess Quest:");
        I18n.dumpPhases(Arrays.stream(Phases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(Quests.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.GameEndings, Arrays.stream(GameEndings.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(Phases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(Quests.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.GameEndings, Arrays.stream(GameEndings.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onBeforeGameEnd(Object object) {
        if (game.getEndStatus() == GameEndType.BOUGHT_MOON.castToInt()
                && questStatusPrincess >= STATUS_PRINCESS_RETURNED) {
            game.setEndStatus(gameEndTypeId);
        }
    }

    private void afterShipSpecInitialized(Object object) {
        ShipSpec shipSpec = new ShipSpec(ShipType.QUEST, Size.HUGE, 30, 2, 2, 2, 2, 1, 1, 300, 1, 500000, 0, Activity.NA,
                Activity.NA, Activity.NA, TechLevel.UNAVAILABLE);
        shipSpecId = registerNewShipSpec(shipSpec);
    }

    @Override
    public String getGameCompletionText() {
        return GameEndings.ClaimedMoonWithPrincess.getValue();
    }

    private void onGameEndAlert(Object object) {
        //TODO need to pass string value as image ID, and get image by this value
        new FormAlert(Alerts.GameEndBoughtMoonGirl.getValue().getTitle(), GameEndType.QUEST.castToInt()).showDialog();
    }

    private void onGetGameScore(Object object) {
        ScoreContainer score = (ScoreContainer) object;
        if (score.getEndStatus() == 1) {
            score.setDaysMoon(Math.max(0, (Game.getDifficultyId() + 1) * 100 - Game.getCommander().getDays()));
            score.setModifier(100); //TODO 110????
        }
    }

    private void onGetEncounterTextInitial(Object object) {
        if (scorpion.getBarCode() == Game.getCurrentGame().getOpponent().getBarCode()) {
            ((StringContainer) object).setValue(Encounters.PretextScorpion.getValue());
        }
    }

    private void onCreateShip(Object object) {
        scorpion = game.createShipByShipSpecId(shipSpecId);
        scorpion.getCrew()[0] = scorpionCrew;
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Weapons[WeaponType.MILITARY_LASER.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Shields[ShieldType.REFLECTIVE.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.AUTO_REPAIR_SYSTEM.castToInt()]);
        scorpion.addEquipment(Consts.Gadgets[GadgetType.TARGETING_SYSTEM.castToInt()]);
    }

    private void onDetermineNonRandomEncounter(Object object) {
        if (game.getClicks() == 1 && game.getWarpSystem().getId() == StarSystemId.Qonos && questStatusPrincess == STATUS_PRINCESS_FLY_QONOS) {
            game.setOpponent(scorpion);
            game.getEncounter().setEncounterType(Game.getShip().isCloaked() ? EncounterType.QUEST_IGNORE : EncounterType.QUEST_ATTACK);
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void onGetStealableCargo(Object object) {
        if (princessOnBoard) {
            ((IntContainer) object).setValue(((IntContainer) object).getValue() - 1);
        }
    }

    private void onEncounterVerifySurrenderNoHidden(Object object) {
        if (princessOnBoard && !Game.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)
                && game.getEncounter().getEncounterType() == EncounterType.PIRATE_ATTACK) {
            showAlert(Alerts.EncounterPiratesSurrenderPrincess.getValue());
            ((BooleanContainer) object).setValue(true);
        }
    }

    @SuppressWarnings("unchecked")
    private void onEncounterVerifySurrenderHidden(Object object) {
        if (princessOnBoard && Game.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            ((ArrayList<String>) object).add(Encounters.HidePrincess.getValue());
        }
    }

    private void onEncounterVerifyAttack(Object object) {
        //if (game.getOpponent().getType() == ShipType.SCORPION
        if (game.getOpponent().getBarCode() == scorpion.getBarCode()
                && Game.getShip().getWeaponStrength(WeaponType.PHOTON_DISRUPTOR, WeaponType.QUANTUM_DISRUPTOR) == 0) {
            GuiFacade.alert(AlertType.EncounterAttackNoDisruptors);
            ((BooleanContainer) object).setValue(true);
        }
    }

    private void onEncounterExecuteAttackKeepSpecialShip(Object object) {
        // Make sure the Scorpion doesn't get destroyed.
        Ship defender = ((Ship) object);
        //if (defender.getType() == ShipType.SCORPION && defender.getHull() == 0) {
        if (defender.getBarCode() == scorpion.getBarCode() && defender.getHull() == 0) {
            defender.setHull(1);
            game.setOpponentDisabled(true);
        }
    }

    private void onEncounterExecuteActionOpponentDisabled(Object object) {
/*        switch (game.getOpponent().getType()) {
            case SCORPION:*/
        if (game.getOpponent().getBarCode() == scorpion.getBarCode()) {
                Game.getCommander().setKillsPirate(Game.getCommander().getKillsPirate() + 1);
                Game.getCommander().setPoliceRecordScore(Game.getCommander().getPoliceRecordScore() + Consts.ScoreKillPirate);
                questStatusPrincess = STATUS_PRINCESS_RESCUED;

                GuiFacade.alert(AlertType.EncounterDisabledOpponent, game.getEncounter().getEncounterShipText(), Encounters.PrincessRescued.getValue());

                Game.getCommander().setReputationScore(
                        //TODO getOpponent().getType() == 16
                        Game.getCommander().getReputationScore() + (game.getOpponent().getType().castToInt() / 2 + 1));
                ((BooleanContainer) object).setValue(true);
        }
    }

    @Override
    public void affectSkills(int[] skills) {
    }

    @Override
    public void registerListener() {
        getTransitionMap().keySet().forEach(this::registerOperation);
        log.fine("registered");
    }

    @Override
    public String getCrewMemberName(int id) {
        return CrewNames.values()[getSpecialCrewIds().indexOf(id)].getValue();
    }

    @Override
    public String getNewsTitle(int newsId) {
        return News.values()[getNewsIds().indexOf(newsId)].getValue();
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Galvon);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(Phases.Princess).setStarSystemId(starSystem.getId());
        phases.get(Phases.PrincessReturned).setStarSystemId(starSystem.getId());
        phases.get(Phases.PrincessQuantum).setStarSystemId(starSystem.getId());

        starSystem = Game.getStarSystem(StarSystemId.Centauri);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(Phases.PrincessCentauri).setStarSystemId(starSystem.getId());

        starSystem = Game.getStarSystem(StarSystemId.Inthara);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(Phases.PrincessInthara).setStarSystemId(starSystem.getId());

        starSystem = Game.getStarSystem(StarSystemId.Qonos);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(Phases.PrincessQonos).setStarSystemId(starSystem.getId());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        game.getMercenaries().put(princess.getId(), princess);
        game.getMercenaries().put(scorpionCrew.getId(), scorpionCrew);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        phases.forEach((key, value) -> showSpecialButtonIfCanBeExecuted(object, value));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class PrincessPhase extends Phase { //new SpecialEvent(SpecialEventType.Princess, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem()
                    && Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreLawful
                    && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage
                    && questStatusPrincess == STATUS_PRINCESS_NOT_STARTED;
        }

        @Override
        public String toString() {
            return "PrincessPhase{} " + super.toString();
        }
    }

    class PrincessCentauriPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessCentauri, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem()
                    && questStatusPrincess == STATUS_PRINCESS_FLY_CENTAURI;
        }

        @Override
        public String toString() {
            return "PrincessCentauriPhase{} " + super.toString();
        }
    }

    class PrincessIntharaPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessInthara, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem()
                    && questStatusPrincess == STATUS_PRINCESS_FLY_INTHARA;
        }

        @Override
        public String toString() {
            return "PrincessIntharaPhase{} " + super.toString();
        }
    }

    class PrincessQonosPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessQonos, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem()
                    && questStatusPrincess == STATUS_PRINCESS_RESCUED;
        }

        @Override
        public String toString() {
            return "PrincessQonosPhase{} " + super.toString();
        }
    }

    class PrincessReturnedPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessReturned, 0, 0, true),

        @Override
        public boolean canBeExecuted() {
            return princessOnBoard && isDesiredSystem()
                    && questStatusPrincess >= STATUS_PRINCESS_RESCUED
                    && questStatusPrincess <= STATUS_PRINCESS_IMPATIENT;
        }

        @Override
        public String toString() {
            return "PrincessReturnedPhase{} " + super.toString();
        }
    }

    class PrincessQuantumPhase extends Phase { //new SpecialEvent(SpecialEventType.PrincessQuantum, 0, 0, false),

        @Override
        public boolean canBeExecuted() {
            return !princessOnBoard && isDesiredSystem()
                    && questStatusPrincess == STATUS_PRINCESS_RETURNED;
        }

        @Override
        public String toString() {
            return "PrincessQuantumPhase{} " + super.toString();
        }
    }

    private void onSpecialButtonClicked(Object object) {
        if (phases.get(Phases.Princess).canBeExecuted()) {
            log.fine("phase #" + Phases.Princess);
            showDialogAndProcessResult(object, Phases.Princess.getValue(), () -> {
                questStatusPrincess = STATUS_PRINCESS_FLY_CENTAURI;
                setQuestState(QuestState.ACTIVE);
            });
        } else if (phases.get(Phases.PrincessCentauri).canBeExecuted()) {
            log.fine("phase #" + Phases.PrincessCentauri);
            showDialogAndProcessResult(object, Phases.PrincessCentauri.getValue(), () -> {
                questStatusPrincess = STATUS_PRINCESS_FLY_INTHARA;
                game.getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            });
        } else if (phases.get(Phases.PrincessInthara).canBeExecuted()) {
            log.fine("phase #" + Phases.PrincessInthara);
            showDialogAndProcessResult(object, Phases.PrincessInthara.getValue(), () -> {
                questStatusPrincess = STATUS_PRINCESS_FLY_QONOS;
                game.getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            });
        } else if (phases.get(Phases.PrincessQonos).canBeExecuted()) {
            log.fine("phase #" + Phases.PrincessQonos);
            showDialogAndProcessResult(object, Phases.PrincessQonos.getValue(), () -> {
                //TODO where does the princess go? need to test this case.
                if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                } else {
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, princess.getName());
                    Game.getShip().hire(princess);
                    princessOnBoard = true;
                    questStatusPrincess = STATUS_PRINCESS_RESCUED;
                    game.getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
                }
            });
        } else if (phases.get(Phases.PrincessReturned).canBeExecuted()) {
            log.fine("phase #" + Phases.PrincessReturned);
            showDialogAndProcessResult(object, Phases.PrincessReturned.getValue(), () -> {
                questStatusPrincess = STATUS_PRINCESS_RETURNED;
                Game.getShip().fire(princess.getId());
                princessOnBoard = false;
            });
        } else if (phases.get(Phases.PrincessQuantum).canBeExecuted() && isQuestIsActive()) {
            log.fine("phase #" + Phases.PrincessQuantum);
            showDialogAndProcessResult(object, Phases.PrincessQuantum.getValue(), () -> {
                if (Game.getShip().getFreeWeaponSlots() == 0) {
                    GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
                } else {
                    GuiFacade.alert(AlertType.EquipmentQuantumDisruptor);
                    Game.getShip().addEquipment(Consts.Weapons[WeaponType.QUANTUM_DISRUPTOR.castToInt()]);
                    questStatusPrincess = STATUS_PRINCESS_DONE;
                    setQuestState(QuestState.FINISHED);
                    game.getQuestSystem().unSubscribeAll(getQuest());
                }
            });
        } else {
            log.fine("skipped");
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Princess.getValue())) {
            questStatusPrincess = Math.max(0, cheatWords.getNum2());
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CheatTitles.Princess.getValue(), questStatusPrincess);
    }

    private void onArrested(Object object) {
        if (princessOnBoard) {
            log.fine("Arrested + Princess");
            showAlert(Alerts.PrincessTakenHome.getValue());
            questStatusPrincess = STATUS_PRINCESS_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Arrested w/o Princess");
        }
    }

    private void onEscapeWithPod(Object object) {
        if (princessOnBoard) {
            log.fine("Escaped + Princess");
            showAlert(Alerts.PrincessTakenHome.getValue());
            questStatusPrincess = STATUS_PRINCESS_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Escaped w/o Princess");
        }
    }

    private void onIncrementDays(Object object) {
        if (princessOnBoard) {
            log.fine(questStatusPrincess + "");
            if (questStatusPrincess == (STATUS_PRINCESS_IMPATIENT + STATUS_PRINCESS_RESCUED) / 2) {
                showAlert(Alerts.SpecialPassengerConcernedPrincess.getValue());
            } else if (questStatusPrincess == STATUS_PRINCESS_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientPrincess.getValue());
                princess.setPilot(0);
                princess.setFighter(0);
                princess.setTrader(0);
                princess.setEngineer(0);
            }

            if (questStatusPrincess < STATUS_PRINCESS_IMPATIENT) {
                questStatusPrincess++;
            }
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        switch (questStatusPrincess) {
            case STATUS_PRINCESS_FLY_CENTAURI:
                ((ArrayList<String>) object).add(Quests.PrincessCentauri.getValue());
                log.fine(Quests.PrincessCentauri.getValue());
                break;
            case STATUS_PRINCESS_FLY_INTHARA:
                ((ArrayList<String>) object).add(Quests.PrincessInthara.getValue());
                log.fine(Quests.PrincessInthara.getValue());
                break;
            case STATUS_PRINCESS_FLY_QONOS:
                ((ArrayList<String>) object).add(Quests.PrincessQonos.getValue());
                log.fine(Quests.PrincessQonos.getValue());
                break;
            case STATUS_PRINCESS_RESCUED:
                if (princessOnBoard) {
                    if (questStatusPrincess == STATUS_PRINCESS_IMPATIENT) {
                        ((ArrayList<String>) object).add(Functions.stringVars(
                                Quests.PrincessReturningImpatient.getValue(), CrewNames.Ziyal.getValue()));
                        log.fine(Functions.stringVars(
                                Quests.PrincessReturningImpatient.getValue(), CrewNames.Ziyal.getValue()));
                    } else {
                        ((ArrayList<String>) object).add(Functions.stringVars(
                                Quests.PrincessReturning.getValue(), CrewNames.Ziyal.getValue()));
                        log.fine(Functions.stringVars(
                                Quests.PrincessReturning.getValue(), CrewNames.Ziyal.getValue()));
                    }
                } else {
                    ((ArrayList<String>) object).add(Functions.stringVars(
                            Quests.PrincessReturn.getValue(), CrewNames.Ziyal.getValue()));
                    log.fine(Functions.stringVars(
                            Quests.PrincessReturn.getValue(), CrewNames.Ziyal.getValue()));
                }
                break;
            case STATUS_PRINCESS_RETURNED:
                ((ArrayList<String>) object).add(Quests.PrincessQuantum.getValue());
                log.fine(Quests.PrincessQuantum.getValue());
                break;
            default:
                log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (questStatusPrincess == STATUS_PRINCESS_NOT_STARTED && Game.isCurrentSystemIs(StarSystemId.Galvon)) {
            log.fine("" + getNewsIds().get(News.Princess.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.Princess.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_FLY_CENTAURI) {
            log.fine("" + getNewsIds().get(News.PrincessCentauri.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.PrincessCentauri.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_FLY_INTHARA) {
            log.fine("" + getNewsIds().get(News.PrincessInthara.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.PrincessInthara.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_FLY_QONOS) {
            log.fine("" + getNewsIds().get(News.PrincessQonos.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.PrincessQonos.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_RESCUED) {
            log.fine("" + getNewsIds().get(News.PrincessRescued.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.PrincessRescued.ordinal()));
        } else if (questStatusPrincess == STATUS_PRINCESS_RETURNED) {
            log.fine("" + getNewsIds().get(News.PrincessReturned.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.PrincessReturned.ordinal()));
        } else {
            log.fine("skipped");
        }
    }

    @Override
    public String toString() {
        return "PrincessQuest{" +
                "questStatusPrincess=" + questStatusPrincess +
                ", princess=" + princess +
                ", scorpionCrew=" + scorpionCrew +
                ", princessOnBoard=" + princessOnBoard +
                ", scorpion=" + scorpion +
                "} " + super.toString();
    }
}
