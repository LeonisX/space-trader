package spacetrader.game.quest;

import spacetrader.game.Consts;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.SpecialEventType;
import spacetrader.game.enums.StarSystemId;
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

    static final long serialVersionUID = -4731305242511502L;

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
    private boolean wildOnBoard;

    //TODO need???
    private UUID shipBarCode = UUID.randomUUID();

    public WildQuest(Integer id) {
        initialize(id, this, REPEATABLE, CASH_TO_SPEND, OCCURRENCE);

        initializePhases(Phases.values(), new WildPhase(), new WildGetsOutPhase());
        initializeTransitionMap();

        wild = registerNewSpecialCrewMember(7, 10, 2, 5);

        registerNews(News.values().length);

        registerListener();

        localize();

        log.fine("started...");
    }

    private void initializePhases(Phases[] values, Phase... phases) {
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

        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);

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
        return CrewNames.values()[0].getValue();
    }

    @Override
    public String getNewsTitle(int newsId) {
        return News.values()[newsId].getValue();
    }

    @Override
    public void dumpAllStrings() {
        System.out.println("\n\n## Jarek Quest:");
        I18n.dumpPhases(Arrays.stream(Phases.values()));
        I18n.dumpStrings(Res.Quests, Arrays.stream(Quests.values()));
        I18n.dumpAlerts(Arrays.stream(Alerts.values()));
        I18n.dumpStrings(Res.News, Arrays.stream(News.values()));
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(Phases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(Quests.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Kravat);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(Phases.WildGetsOut).setStarSystemId(starSystem.getId());
    }

    private void onAssignEventsRandomly(Object object) {
        int system;
        //TODO common method
        do {
            system = Functions.getRandom(Game.getCurrentGame().getUniverse().length);
        } while (Game.getStarSystem(system).getSpecialEventType() != SpecialEventType.NA);

        Game.getStarSystem(system).setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(Phases.Jarek).setStarSystemId(Game.getStarSystem(system).getId());
        log.fine(phases.get(Phases.Jarek).getStarSystemId().toString());
    }

    private void onGenerateCrewMemberList(Object object) {
        log.fine("");
        Game.getCurrentGame().getMercenaries().put(wild.getId(), wild);
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class WildPhase extends Phase { //new SpecialEvent(SpecialEventType.Wild, 0, 1, false),
        @Override
        public boolean canBeExecuted() {
            return Game.getCommander().getPoliceRecordScore() >= Consts.PoliceRecordScoreDubious
                    && !wildOnBoard && isDesiredSystem();
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");
            if (Game.getShip().getFreeCrewQuartersCount() == 0) {
                GuiFacade.alert(AlertType.SpecialNoQuarters);
            } else {
                GuiFacade.alert(AlertType.SpecialPassengerOnBoard, jarek.getName());
                Game.getShip().hire(jarek);
                jarekOnBoard = true;
                questStatus = STATUS_JAREK_STARTED;
                setQuestState(QuestState.ACTIVE);
                Game.getCurrentGame().getSelectedSystem().setSpecialEventType(SpecialEventType.NA);
            }


            /*case Wild:
                if (commander.getShip().getFreeCrewQuartersCount() == 0) {
                    GuiFacade.alert(AlertType.SpecialNoQuarters);
                    remove = false;
                } else if (!commander.getShip().hasWeapon(WeaponType.BEAM_LASER, false)) {
                    GuiFacade.alert(AlertType.WildWontBoardLaser);
                    remove = false;
                } else if (commander.getShip().isReactorOnBoard()) {
                    GuiFacade.alert(AlertType.WildWontBoardReactor);
                    remove = false;
                } else {
                    CrewMember wild = mercenaries.get(CrewMemberId.WILD.castToInt());
                    GuiFacade.alert(AlertType.SpecialPassengerOnBoard, wild.getName());
                    commander.getShip().hire(wild);
                    setQuestStatusWild(SpecialEvent.STATUS_WILD_STARTED);

                    if (commander.getShip().isSculptureOnBoard()) {
                        GuiFacade.alert(AlertType.WildSculpture);
                    }
                }
                break;
            case WildGetsOut:
                // Zeethibal has a 10 in player's lowest score, an 8 in the next lowest score, and 5 elsewhere.
                CrewMember zeethibal = mercenaries.get(CrewMemberId.ZEETHIBAL.castToInt());
                zeethibal.setCurrentSystem(getStarSystem(StarSystemId.Kravat));
                int lowest1 = commander.nthLowestSkill(1);
                int lowest2 = commander.nthLowestSkill(2);
                for (int i = 0; i < zeethibal.getSkills().length; i++) {
                    zeethibal.getSkills()[i] = (i == lowest1 ? 10 : (i == lowest2 ? 8 : 5));
                }

                setQuestStatusWild(SpecialEvent.STATUS_WILD_DONE);
                commander.setPoliceRecordScore(Consts.PoliceRecordScoreClean);
                commander.getShip().fire(CrewMemberId.WILD.castToInt());
                recalculateSellPrices(curSys);
                break;*/


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
            questStatus = STATUS_JAREK_DONE;
            Game.getShip().fire(jarek.getId());
            jarekOnBoard = false;
            shipBarCode = Game.getShip().getBarCode();
            setQuestState(QuestState.FINISHED);
            game.getQuestSystem().unSubscribeAll(getQuest());
        }

        @Override
        public String toString() {
            return "WildGetsOutPhase{} " + super.toString();
        }
    }

    private void onSpecialButtonClicked(Object object) {
        Optional<Phases> activePhase =
                phases.entrySet().stream().filter(p -> p.getValue().canBeExecuted()).map(Map.Entry::getKey).findFirst();
        if (activePhase.isPresent()) {
            showDialogAndProcessResult(object, activePhase.get().getValue(), () -> phases.get(activePhase.get()).successFlow());
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (wildOnBoard) {
            if (questStatus == STATUS_WILD_IMPATIENT) {
                ((ArrayList<String>) object).add(Quests.WildImpatient.getValue());
                log.fine(Quests.WildImpatient.getValue());
            } else {
                ((ArrayList<String>) object).add(Quests.Wild.getValue());
                log.fine(Quests.Wild.getValue());
            }
        } else {
            log.fine("skipped");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onArrested(Object object) {
        if (wildOnBoard) {
            log.fine("Arrested + Wild");
            showAlert(Alerts.WildArrested.getValue());
            //TODO
            news.addEvent(NewsEvent.WildArrested);
            questStatus = STATUS_WILD_NOT_STARTED;
            setQuestState(QuestState.FAILED);
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
            news.addEvent(NewsEvent.WildArrested);
            questStatus = STATUS_WILD_NOT_STARTED;
            setQuestState(QuestState.FAILED);
        } else {
            log.fine("Escaped w/o Wild");
        }
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
        //TODO english only??? Jarek, Princess
        if (cheatWords.getSecond().equals(CheatTitles.Wild.getValue())) {
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
    //TODO dialog or alert???
    enum Phases implements SimpleValueEnum<QuestDialog> {
        Wild(new QuestDialog(DIALOG, "Jonathan Wild", "Law Enforcement is closing in on notorious criminal kingpin Jonathan Wild. He would reward you handsomely for smuggling him home to Kravat. You'd have to avoid capture by the Police on the way. Are you willing to give him a berth?")),
        WildGetsOut(new QuestDialog(ALERT, "Wild Gets Out", "Jonathan Wild is most grateful to you for spiriting him to safety. As a reward, he has one of his Cyber Criminals hack into the Police Database, and clean up your record. He also offers you the opportunity to take his talented nephew Zeethibal along as a Mercenary with no pay."));

        private QuestDialog value;

        Phases(QuestDialog value) {
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

    private EnumMap<Phases, Phase> phases = new EnumMap<>(Phases.class);

    enum Quests implements SimpleValueEnum<String> {
        Wild("Smuggle Jonathan Wild to Kravat."),
        WildImpatient("Smuggle Jonathan Wild to Kravat.<br>Wild is getting impatient, and will no longer aid your crew along the way.");

        private String value;

        Quests(String value) {
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

    //TODO
/*    case SpecialPassengerConcernedWild:
            return new FormAlert(AlertsSpecialPassengerConcernedWildTitle,
                                 AlertsSpecialPassengerConcernedWildMessage, AlertsOk,
                                 DialogResult.OK, null, DialogResult.NONE, args);
case SpecialPassengerImpatientWild:
            return new FormAlert(AlertsSpecialPassengerImpatientWildTitle, AlertsSpecialPassengerImpatientWildMessage, AlertsOk,
                                 DialogResult.OK, null, DialogResult.NONE, args);
case WildArrested:
            return new FormAlert(AlertsWildArrestedTitle, AlertsWildArrestedMessage, AlertsOk,
                                 DialogResult.OK, null, DialogResult.NONE, args);
            case WildChatsPirates:
            return new FormAlert(AlertsWildChatsPiratesTitle, AlertsWildChatsPiratesMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case WildGoesPirates:
            return new FormAlert(AlertsWildGoesPiratesTitle, AlertsWildGoesPiratesMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case WildLeavesShip:
            return new FormAlert(AlertsWildLeavesShipTitle, AlertsWildLeavesShipMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case WildSculpture:
            return new FormAlert(AlertsWildSculptureTitle, AlertsWildSculptureMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case WildWontBoardLaser:
            return new FormAlert(AlertsWildWontBoardLaserTitle, AlertsWildWontBoardLaserMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case WildWontBoardReactor:
            return new FormAlert(AlertsWildWontBoardReactorTitle, AlertsWildWontBoardReactorMessage,
                                 AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);
            case WildWontStayAboardLaser:
            return new FormAlert(AlertsWildWontStayAboardLaserTitle, AlertsWildWontStayAboardLaserMessage,
                                 AlertsWildWontStayAboardLaserAccept, DialogResult.OK, AlertsCancel, DialogResult.CANCEL, args);
            case WildWontStayAboardReactor:
            return new FormAlert(AlertsWildWontStayAboardReactorTitle, AlertsWildWontStayAboardReactorMessage,
                                 AlertsWildWontStayAboardReactorAccept, DialogResult.OK, AlertsCancel, DialogResult.CANCEL, args);*/


    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialPassengerConcernedWild("Ship's Comm.", "Bridge? This is Jonathan. Are we there yet? Heh, heh. Sorry, I couldn't resist."),
        SpecialPassengerImpatientWild("Ship's Comm.", "Commander! Wild here. What's taking us so long?!"),
        WildArrested("Wild Arrested", "Jonathan Wild is arrested, and taken away to stand trial."),
        WildChatsPirates("Wild Chats With Pirates", "The Pirate Captain turns out to be an old associate of Jonathan Wild's. They talk about old times, and you get the feeling that Wild would switch ships if the Pirates had any quarters available."),
        WildGoesPirates("Wild Goes With Pirates", "The Pirate Captain turns out to be an old associate of Jonathan Wild's, and invites him to go to Kravat aboard the Pirate ship. Wild accepts the offer and thanks you for the ride."),
        WildLeavesShip("Wild Leaves Ship", "Jonathan Wild leaves your ship, and goes into hiding on ^1."),
        WildSculpture("Wild Eyes Sculpture", "Jonathan Wild sees the stolen sculpture. \"Wow, I only know of one of these left in the whole Universe!\" he exclaims, \"Geurge Locas must be beside himself with it being stolen.\" He seems very impressed with you, which makes you feel much better about the item your delivering."),
        WildWontBoardLaser("Wild Won't Board Ship", "Jonathan Wild isn't willing to go with you if you're not armed with at least a Beam Laser. He'd rather take his chances hiding out here.<br>"),
        WildWontBoardReactor("Wild Won't Board Ship", "Jonathan Wild doesn't like the looks of that Ion Reactor. He thinks it's too dangerous, and won't get on board."),
        //TODO process accept
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
        Wild("Wild");

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
                ", jarek=" + wild +
                ", jarekOnBoard=" + wildOnBoard +
                ", shipBarCode=" + shipBarCode +
                "} " + super.toString();
    }
}
