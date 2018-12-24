package spacetrader.game.quest;

import spacetrader.controls.enums.DialogResult;
import spacetrader.game.Consts;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.enums.WeaponType;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.containers.RandomEncounterContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.util.*;

import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

class WildQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511504L;

    // Constants
    private static final int STATUS_WILD_NOT_STARTED = 0;
    private static final int STATUS_WILD_STARTED = 1;
    private static final int STATUS_WILD_IMPATIENT = 11;
    private static final int STATUS_WILD_DONE = 12;

    private static final int SCORE_CAUGHT_WITH_WILD = -4;


    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;
    private static final int CASH_TO_SPEND = 0;

    private volatile int questStatus = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private CrewMember wild;
    private CrewMember zeethibal;
    private boolean wildOnBoard;

    public WildQuest(QuestName id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);

        initializePhases(QuestPhases.values(), new WildPhase(), new WildGetsOutPhase());
        initializeTransitionMap();

        wild = registerNewSpecialCrewMember(7, 10, 2, 5, false);
        zeethibal = registerNewSpecialCrewMember(5, 5, 5, 5, true);
        zeethibal.setVolunteer(true);

        registerNews(News.values().length);

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

        getTransitionMap().put(ON_ASSIGN_EVENTS_MANUAL, this::onAssignEventsManual);
        getTransitionMap().put(ON_ASSIGN_EVENTS_RANDOMLY, this::onAssignEventsRandomly);
        getTransitionMap().put(ON_GENERATE_CREW_MEMBER_LIST, this::onGenerateCrewMemberList);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED_IS_CONFLICT, this::onSpecialButtonClickedResolveIsConflict);
        getTransitionMap().put(ON_AFTER_NEW_QUEST_STARTED, this::onAfterNewQuestStarted);

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

        getTransitionMap().put(ON_BEFORE_WARP, this::onBeforeWarp);
        getTransitionMap().put(ON_DETERMINE_RANDOM_ENCOUNTER, this::onDetermineRandomEncounter);
        getTransitionMap().put(ON_BEFORE_ENCOUNTER_GENERATE_OPPONENT, this::onBeforeEncounterGenerateOpponent);
        getTransitionMap().put(ON_GENERATE_OPPONENT_SHIP_POLICE_TRIES, this::onGenerateOpponentShipPoliceTries);
        getTransitionMap().put(ENCOUNTER_ON_SURRENDER_IF_RAIDED, this::onSurrenderIfRaided);

        getTransitionMap().put(IS_ILLEGAL_SPECIAL_CARGO, this::onIsIllegalSpecialCargo);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS, this::onGetIllegalSpecialCargoActions);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION, this::onGetIllegalSpecialCargoDescription);
        getTransitionMap().put(ON_BEFORE_ARRESTED_CALCULATE_FINE, this::onBeforeArrestedCalculateFine);
        getTransitionMap().put(ON_ARRESTED, this::onArrested);
        getTransitionMap().put(ON_ESCAPE_WITH_POD, this::onEscapeWithPod);
        getTransitionMap().put(ON_INCREMENT_DAYS, this::onIncrementDays);
        getTransitionMap().put(ON_NEWS_ADD_EVENT_ON_ARRIVAL, this::onNewsAddEventOnArrival);

        getTransitionMap().put(IS_CONSIDER_STATUS_CHEAT, this::onIsConsiderCheat);
        getTransitionMap().put(IS_CONSIDER_STATUS_DEFAULT_CHEAT, this::onIsConsiderDefaultCheat);
    }

    @Override
    public Collection<Phase> getPhases() {
        return phases.values();
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

    @Override
    public void dumpAllStrings() {
        I18n.echoQuestName(this.getClass());
        I18n.dumpPhases(Arrays.stream(QuestPhases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.Encounters, Arrays.stream(Encounters.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Kravat);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(QuestPhases.WildGetsOut).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        phases.get(QuestPhases.Wild).setStarSystemId(occupyFreeSystemWithEvent());
    }

    @SuppressWarnings("unchecked")
    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        Game.getCurrentGame().getMercenaries().put(wild.getId(), wild);
        Game.getCurrentGame().getMercenaries().put(zeethibal.getId(), zeethibal);

        List<Integer> usedSystems = (List<Integer>) object;

        // Zeethibal may be on Kravat
        usedSystems.set(StarSystemId.Kravat.castToInt(), 1);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class WildPhase extends Phase { //new SpecialEvent(SpecialEventType.Wild, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
                    && !wildOnBoard && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else if (!Game.getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
                showAlert(Alerts.WildWontBoardLaser.getValue());
            } else if (Game.getShip().isReactorOnBoard()) {
                showAlert(Alerts.WildWontBoardReactor.getValue());
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, wild.getName());
                Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
                Game.getShip().hire(wild);
                questStatus = STATUS_WILD_STARTED;
                setQuestState(QuestState.ACTIVE);
                wildOnBoard = true;

                onAfterNewQuestStarted(null);
            }
        }

        @Override
        public String toString() {
            return "WildPhase{} " + super.toString();
        }
    }

    class WildGetsOutPhase extends Phase { //new SpecialEvent(SpecialEventType.WildGetsOut, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return wildOnBoard && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");
            // Zeethibal has a 10 in player's lowest score, an 8 in the next lowest score, and 5 elsewhere.
            zeethibal.setCurrentSystem(Game.getStarSystem(StarSystemId.Kravat));
            int lowest1 = Game.getCommander().nthLowestSkill(1);
            int lowest2 = Game.getCommander().nthLowestSkill(2);
            for (int i = 0; i < zeethibal.getSkills().length; i++) {
                zeethibal.getSkills()[i] = (i == lowest1 ? 10 : (i == lowest2 ? 8 : 5));
            }

            Game.getCommander().setPoliceRecordScore(Consts.PoliceRecordScoreClean);
            Game.getCurrentGame().recalculateSellPrices();
            game.getQuestSystem().unSubscribeAll(getQuest());
            setQuestState(QuestState.FINISHED);
            questStatus = STATUS_WILD_DONE;
            removePassenger();
        }

        @Override
        public String toString() {
            return "WildGetsOutPhase{} " + super.toString();
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

    // TODO repeat if < normal, otherwise fail
    private void onSpecialButtonClickedResolveIsConflict(Object object) {
        if (wildOnBoard) {
            if (showCancelAlert(Alerts.WildWontStayAboardReactor.getValue(), Game.getCommander().getCurrentSystem().getName()) == DialogResult.OK) {
                showAlert(Alerts.WildLeavesShip.getValue(), Game.getCommander().getCurrentSystem().getName());
                failQuest();
            } else {
                ((BooleanContainer) object).setValue(true);
            }
        }
    }

    private void onAfterNewQuestStarted(Object object) {
        if (Game.getShip().isSculptureOnBoard() && wildOnBoard) {
            showAlert(Alerts.WildSculpture.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (wildOnBoard) {
            if (questStatus == STATUS_WILD_IMPATIENT) {
                ((ArrayList<String>) object).add(QuestClues.WildImpatient.getValue());
                log.fine(QuestClues.WildImpatient.getValue());
            } else {
                ((ArrayList<String>) object).add(QuestClues.Wild.getValue());
                log.fine(QuestClues.Wild.getValue());
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onBeforeWarp(Object object) {
        // if Wild is aboard, make sure ship is armed!
        if (wildOnBoard && !Game.getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
            if (showCancelAlert(Alerts.WildWontStayAboardLaser.getValue(), Game.getCommander().getCurrentSystem().getName()) == DialogResult.CANCEL) {
                ((BooleanContainer) object).setValue(false);
            } else {
                showAlert(Alerts.WildLeavesShip.getValue(), Game.getCommander().getCurrentSystem().getName());
                failQuest();
            }
        }
    }

    private void onDetermineRandomEncounter(Object object) {
        if (wildOnBoard && game.getWarpSystem().getId() == StarSystemId.Kravat) {
            // if you're coming in to Kravat & you have Wild onboard, there'll be swarms o' cops.
            ((RandomEncounterContainer) object)
                    .setPolice(Functions.getRandom(100) < 100 / Math.max(2, Math.min(4, 5 - Game.getDifficultyId())));
        }
    }

    private void onBeforeEncounterGenerateOpponent(Object object) {
        if (Game.getCurrentGame().getWarpSystem().getId() == StarSystemId.Kravat && wildOnBoard
                && Functions.getRandom(10) < Game.getDifficulty().castToInt() + 1) {
            ((CrewMember) object).setEngineer(Consts.MaxSkill);
        }
    }

    private void onGenerateOpponentShipPoliceTries(Object object) {
        if (wildOnBoard) {
            ((IntContainer) object).setValue(5);
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onSurrenderIfRaided(Object object) {
        if (wildOnBoard) {
            BooleanContainer allowRobbery = (BooleanContainer) object;
            allowRobbery.setValue(false);

            if (game.getOpponent().getCrewQuarters() > 1) {
                // Wild hops onto Pirate Ship
                showAlert(Alerts.WildGoesPirates.getValue());
                failQuest();
            } else {
                showAlert(Alerts.WildChatsPirates.getValue());
            }
        }
    }

    private void onIsIllegalSpecialCargo(Object object) {
        if (wildOnBoard) {
            ((BooleanContainer) object).setValue(true);
        }
    }


    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoActions(Object object) {
        if (wildOnBoard) {
            ((ArrayList<String>) object).add(Encounters.PoliceSurrenderWild.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoDescription(Object object) {
        if (wildOnBoard) {
            ((ArrayList<String>) object).add(Encounters.PoliceSubmitWild.getValue());
        }
    }

    private void onBeforeArrestedCalculateFine(Object object) {
        if (wildOnBoard) {
            IntContainer fine = (IntContainer) object;
            fine.setValue((int) (fine.getValue() * 1.05));
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onArrested(Object object) {
        if (wildOnBoard) {
            log.fine("Arrested + Wild");
            showAlert(Alerts.WildArrested.getValue());
            Game.getNews().addEvent(getNewsIds().get(News.WildArrested.ordinal()));
            failQuest();
        } else {
            log.fine("Arrested w/o Wild");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onEscapeWithPod(Object object) {
        if (wildOnBoard) {
            log.fine("Escaped + Wild");
            showAlert(Alerts.WildArrested.getValue());
            Game.getCommander().setPoliceRecordScore(Game.getCommander().getPoliceRecordScore() + SCORE_CAUGHT_WITH_WILD);
            Game.getNews().addEvent(getNewsIds().get(News.WildArrested.ordinal()));
            failQuest();
        } else {
            log.fine("Escaped w/o Wild");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_WILD_NOT_STARTED;
        setQuestState(QuestState.FAILED);
        removePassenger();
    }

    private void removePassenger() {
        Game.getCommander().getShip().fire(wild.getId());
        wildOnBoard = false;
    }

    private void onIncrementDays(Object object) {
        if (wildOnBoard) {
            log.fine(questStatus + "");
            if (questStatus == STATUS_WILD_IMPATIENT / 2) {
                showAlert(Alerts.SpecialPassengerConcernedWild.getValue());
            } else if (questStatus == STATUS_WILD_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientWild.getValue());
                wild.setPilot(0);
                wild.setFighter(0);
                wild.setTrader(0);
                wild.setEngineer(0);
            }

            if (questStatus < STATUS_WILD_IMPATIENT) {
                questStatus++;
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (wildOnBoard && Game.isCurrentSystemIs(StarSystemId.Kravat)) {
            log.fine("" + getNewsIds().get(News.WildGotToKravat.ordinal()));
            Game.getNews().addEvent(getNewsIds().get(News.WildGotToKravat.ordinal()));
        } else {
            log.fine("skipped");
        }
    }

    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Wild.name())) {
            questStatus = Math.max(0, cheatWords.getNum2());
            cheatWords.setCheat(true);
            log.fine("consider cheat");
        } else {
            log.fine("not consider cheat");
        }
    }

    @SuppressWarnings("unchecked")
    private void onIsConsiderDefaultCheat(Object object) {
        log.fine("");
        ((Map<String, Integer>) object).put(CheatTitles.Wild.getValue(), questStatus);
    }

    // Special Events
    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Wild(new QuestDialog(DIALOG, "Jonathan Wild", "Law Enforcement is closing in on notorious criminal kingpin Jonathan Wild. He would reward you handsomely for smuggling him home to Kravat. You'd have to avoid capture by the Police on the way. Are you willing to give him a berth?")),
        WildGetsOut(new QuestDialog(ALERT, "Wild Gets Out", "Jonathan Wild is most grateful to you for spiriting him to safety. As a reward, he has one of his Cyber Criminals hack into the Police Database, and clean up your record. He also offers you the opportunity to take his talented nephew Zeethibal along as a Mercenary with no pay."));

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

    enum QuestClues implements SimpleValueEnum<String> {
        Wild("Smuggle Jonathan Wild to Kravat."),
        WildImpatient("Smuggle Jonathan Wild to Kravat.<br>Wild is getting impatient, and will no longer aid your crew along the way.");

        private String value;

        QuestClues(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialPassengerConcernedWild("Ship's Comm.", "Bridge? This is Jonathan. Are we there yet? Heh, heh. Sorry, I couldn't resist."),
        SpecialPassengerImpatientWild("Ship's Comm.", "Commander! Wild here. What's taking us so long?!"),
        WildArrested("Wild Arrested", "Jonathan Wild is arrested, and taken away to stand trial."),
        WildChatsPirates("Wild Chats With Pirates", "The Pirate Captain turns out to be an old associate of Jonathan Wild's. They talk about old times, and you get the feeling that Wild would switch ships if the Pirates had any quarters available."),
        WildGoesPirates("Wild Goes With Pirates", "The Pirate Captain turns out to be an old associate of Jonathan Wild's, and invites him to go to Kravat aboard the Pirate ship. Wild accepts the offer and thanks you for the ride."),
        WildLeavesShip("Wild Leaves Ship", "Jonathan Wild leaves your ship, and goes into hiding on ^1."),
        WildSculpture("Wild Eyes Sculpture", "Jonathan Wild sees the stolen sculpture. \"Wow, I only know of one of these left in the whole Universe!\" he exclaims, \"Geurge Locas must be beside himself with it being stolen.\" He seems very impressed with you, which makes you feel much better about the item your delivering."),
        WildWontBoardLaser("Wild Won't Board Ship", "Jonathan Wild isn't willing to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here."),
        WildWontBoardReactor("Wild Won't Board Ship", "Jonathan Wild doesn't like the looks of that Ion Reactor. He thinks it's too dangerous, and won't get on board."),
        WildWontStayAboardLaser("Wild Won't Stay Aboard", "Jonathan Wild isn't about to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here on ^1.", "Say Goodbye to Wild"),
        WildWontStayAboardReactor("Wild Won't Stay Aboard", "Jonathan Wild isn't willing to go with you if you bring that Reactor on board. He'd rather take his chances hiding out here on ^1.", "Say Goodbye to Wild");

        private AlertDialog value;

        Alerts(String title, String body) {
            this.value = new AlertDialog(title, body);
        }

        Alerts(String title, String body, String accept) {
            this.value = new AlertDialog(title, body, accept);
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

    enum News implements SimpleValueEnum<String> {
        WildArrested("Notorious Criminal Jonathan Wild Arrested!"),
        WildGotToKravat("Rumors Suggest Known Criminal J. Wild May Come to Kravat!");

        private String value;

        News(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    enum Encounters implements SimpleValueEnum<String> {
        PoliceSubmitWild("Jonathan Wild"),
        PoliceSurrenderWild("arrest Wild, too");

        private String value;

        Encounters(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    enum CrewNames implements SimpleValueEnum<String> {
        Wild("Wild"),
        Zeethibal("Zeethibal");

        private String value;

        CrewNames(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    enum CheatTitles implements SimpleValueEnum<String> {
        Wild("Wild");
        private String value;

        CheatTitles(String value) {
            this.value = value;
        }

        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(String value) {
            this.value = value;
        }
    }

    @Override
    public String toString() {
        return "WildQuest{" +
                "questStatus=" + questStatus +
                ", wild=" + wild +
                ", zeethibal=" + zeethibal +
                ", wildOnBoard=" + wildOnBoard +
                "} " + super.toString();
    }
}
