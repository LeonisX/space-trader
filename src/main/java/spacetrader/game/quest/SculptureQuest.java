package spacetrader.game.quest;

import spacetrader.game.*;
import spacetrader.game.cheat.CheatWords;
import spacetrader.game.enums.*;
import spacetrader.game.quest.containers.BooleanContainer;
import spacetrader.game.quest.containers.IntContainer;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.game.quest.enums.Repeatable;
import spacetrader.game.quest.enums.SimpleValueEnum;
import spacetrader.guifacade.GuiFacade;

import java.util.*;

import static spacetrader.game.Strings.newline;
import static spacetrader.game.quest.enums.EventName.*;
import static spacetrader.game.quest.enums.MessageType.ALERT;
import static spacetrader.game.quest.enums.MessageType.DIALOG;

//TODO replace Jarek
class SculptureQuest extends AbstractQuest {

    static final long serialVersionUID = -4731305242511505L;

    // Constants
    private static final int STATUS_SCULPTURE_NOT_STARTED = 0;
    private static final int STATUS_SCULPTURE_IN_TRANSIT = 1;
    private static final int STATUS_SCULPTURE_DELIVERED = 2;
    private static final int STATUS_SCULPTURE_DONE = 3;

    private static final Repeatable REPEATABLE = Repeatable.DISPOSABLE;
    private static final int OCCURRENCE = 1;

    private volatile int questStatus = 0; // 0 = not delivered, 1-11 = on board, 12 = delivered

    private boolean sculptureOnBoard;

    public SculptureQuest(QuestName id) {
        initialize(id, this, REPEATABLE, OCCURRENCE);

        initializePhases(QuestPhases.values(), new SculptureInTransitPhase(), new SculptureDeliveredPhase(),
                new SculptureHiddenBaysPhase());
        initializeTransitionMap();

        registerNews(1);

        registerListener();

        //dumpAllStrings();
        //localize();

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
        getTransitionMap().put(ON_ASSIGN_CLOSEST_EVENTS_RANDOMLY, this::onAssignClosestEventsRandomly);

        getTransitionMap().put(ON_BEFORE_SPECIAL_BUTTON_SHOW, this::onBeforeSpecialButtonShow);
        getTransitionMap().put(ON_SPECIAL_BUTTON_CLICKED, this::onSpecialButtonClicked);

        getTransitionMap().put(ON_DISPLAY_SPECIAL_CARGO, this::onDisplaySpecialCargo);
        getTransitionMap().put(ON_GET_QUESTS_STRINGS, this::onGetQuestsStrings);
        getTransitionMap().put(ENCOUNTER_ON_ROBBERY, this::encounterOnRobbery);
        getTransitionMap().put(ENCOUNTER_GET_STEALABLE_CARGO, this::encounterGetStealableCargo);

        getTransitionMap().put(IS_ILLEGAL_SPECIAL_CARGO, this::onIsIllegalSpecialCargo);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_ACTIONS, this::onGetIllegalSpecialCargoActions);
        getTransitionMap().put(ON_GET_ILLEGAL_SPECIAL_CARGO_DESCRIPTION, this::onGetIllegalSpecialCargoDescription);
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
        I18n.dumpStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.dumpStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.dumpStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    @Override
    public void localize() {
        I18n.localizePhases(Arrays.stream(QuestPhases.values()));
        I18n.localizeStrings(Res.Quests, Arrays.stream(QuestClues.values()));
        I18n.localizeAlerts(Arrays.stream(Alerts.values()));
        I18n.localizeStrings(Res.News, Arrays.stream(News.values()));
        I18n.localizeStrings(Res.CrewNames, Arrays.stream(CrewNames.values()));
        I18n.localizeStrings(Res.SpecialCargo, Arrays.stream(SpecialCargo.values()));
        I18n.localizeStrings(Res.CheatTitles, Arrays.stream(CheatTitles.values()));
    }

    private void onAssignEventsManual(Object object) {
        log.fine("");
        StarSystem starSystem = Game.getStarSystem(StarSystemId.Endor);
        starSystem.setSpecialEventType(SpecialEventType.ASSIGNED);
        phases.get(QuestPhases.SculptureDelivered).setStarSystemId(starSystem.getId());
    }

    private void onAssignClosestEventsRandomly(Object object) {
        // Find the closest system at least 70 parsecs away from Endor that doesn't already have a special event.
        BooleanContainer goodUniverse = (BooleanContainer) object;
        if (!goodUniverse.getValue()) {
            return;
        }
        int systemId = game.isFindDistantSystem(StarSystemId.Endor, SpecialEventType.ASSIGNED);
        if (systemId < 0) {
            goodUniverse.setValue(false);
        } else {
            phases.get(QuestPhases.Sculpture).setStarSystemId(Game.getStarSystem(systemId).getId());
        }
    }

    private void onBeforeSpecialButtonShow(Object object) {
        getPhases().forEach(phase -> showSpecialButtonIfCanBeExecuted(object, phase));
    }

    //SpecialEvent(SpecialEventType type, int price, int occurrence, boolean messageOnly)
    class SculptureInTransitPhase extends Phase { //new SpecialEvent(SpecialEventType.Sculpture, -2000, 0, false),
        @Override
        public boolean canBeExecuted() {
            return isDesiredSystem();

            //case Sculpture:
            //                show = game.getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_NOT_STARTED
            //                        && Game.getCommander().getPoliceRecordScore() < Consts.PoliceRecordScoreDubious
            //                        && Game.getCommander().getReputationScore() >= Consts.ReputationScoreAverage;
            //                break;
        }

        @Override
        public void successFlow() {
            log.fine("phase #1");

            questStatus = STATUS_SCULPTURE_IN_TRANSIT;
            //TODO refactor all phases with this method
            game.confirmQuestPhase();
            setQuestState(QuestState.ACTIVE);
        }

        @Override
        public String toString() {
            return "SculptureInTransitPhase{} " + super.toString();
        }
    }

    class SculptureDeliveredPhase extends Phase { //new SpecialEvent(SpecialEventType.SculptureDelivered, 0, 0, true),
        @Override
        public boolean canBeExecuted() {
            return sculptureOnBoard && isDesiredSystem();

            ////            case SculptureDelivered:
            //            //                show = game.getQuestStatusSculpture() == SpecialEvent.STATUS_SCULPTURE_IN_TRANSIT;
            //            //                break;
        }

        @Override
        public void successFlow() {
            log.fine("phase #2");

            sculptureOnBoard = false;
            questStatus = STATUS_SCULPTURE_DELIVERED;
            //TODO refactor all phases with this method
            //switchQuestPhase(SpecialEventType.SculptureHiddenBays);
        }

        @Override
        public String toString() {
            return "SculptureDeliveredPhase{} " + super.toString();
        }
    }

    class SculptureHiddenBaysPhase extends Phase { //new SpecialEvent(SpecialEventType.SculptureHiddenBays, 0, 0, false),
        @Override
        public boolean canBeExecuted() {
            return (questStatus == STATUS_SCULPTURE_DELIVERED) && !sculptureOnBoard && isDesiredSystem();

            //case SculptureHiddenBays:
            //                show = true;
        }

        @Override
        public void successFlow() {
            log.fine("phase #3");
            if (Game.getShip().getFreeGadgetSlots() == 0) {
                GuiFacade.alert(AlertType.EquipmentNotEnoughSlots);
            } else {
                GuiFacade.alert(AlertType.EquipmentHiddenCompartments);
                //TODO gadget -> in quest
                Game.getShip().addEquipment(Consts.Gadgets[GadgetType.HIDDEN_CARGO_BAYS.castToInt()]);
                game.confirmQuestPhase();
                questStatus = STATUS_SCULPTURE_DONE;
                setQuestState(QuestState.FINISHED);
                game.getQuestSystem().unSubscribeAll(getQuest());
            }
        }

        @Override
        public String toString() {
            return "SculptureHiddenBaysPhase{} " + super.toString();
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

    @SuppressWarnings("unchecked")
    private void onDisplaySpecialCargo(Object object) {
        if (sculptureOnBoard) {
            log.fine(SpecialCargo.Sculpture.getValue());
            ((ArrayList<String>) object).add(SpecialCargo.Sculpture.getValue());
        } else {
            log.fine("Don't show " + SpecialCargo.Sculpture.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void onGetQuestsStrings(Object object) {
        if (sculptureOnBoard) {
            ((ArrayList<String>) object).add(QuestClues.Sculpture.getValue());
        } else if (game.getQuestStatusReactor() == SpecialEvent.STATUS_REACTOR_DELIVERED) {
            ((ArrayList<String>) object).add(QuestClues.SculptureHiddenBays.getValue());
        } else {
            log.fine("skipped");
        }
    }

    @SuppressWarnings("unchecked")
    private void encounterOnRobbery(Object object) {
        if (princessOnBoard && Game.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
            ((spacetrader.stub.ArrayList<String>) object).add(PrincessQuest.Encounters.HidePrincess.getValue());
        }

        //if (commander.getShip().hasGadget(GadgetType.HIDDEN_CARGO_BAYS)) {
        //
        //                /*if (commander.getShip().isPrincessOnBoard()) {
        //                    precious.add(Strings.EncounterHidePrincess);
        //                }*/
        //                    if (commander.getShip().isSculptureOnBoard()) {
        //                        precious.add(Strings.EncounterHideSculpture);
        //                    }
        //
        //
        //                } else if (commander.getShip().isSculptureOnBoard()) {
        //                    game.setQuestStatusSculpture(SpecialEvent.STATUS_SCULPTURE_NOT_STARTED);
        //                    GuiFacade.alert(AlertType.EncounterPiratesTakeSculpture);
        //                }
    }

    private void encounterGetStealableCargo(Object object) {
        if (princessOnBoard) {
            ((IntContainer) object).setValue(((IntContainer) object).getValue() - 1);
        }

        //if (isSculptureOnBoard()) {
        //            hidden--;
        //        }
    }

    private void onIsIllegalSpecialCargo(Object object) {
        if (wildOnBoard) {
            ((BooleanContainer) object).setValue(true);
        }


    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoActions(Object object) {
        if (wildOnBoard) {
            ((ArrayList<String>) object).add(WildQuest.Encounters.PoliceSurrenderWild.getValue());
        }

        //if (isSculptureOnBoard()) {
        //            actions.add(Strings.EncounterPoliceSurrenderSculpt);
        //        }
    }

    @SuppressWarnings("unchecked")
    private void onGetIllegalSpecialCargoDescription(Object object) {
        if (wildOnBoard) {
            ((ArrayList<String>) object).add(WildQuest.Encounters.PoliceSubmitWild.getValue());
        }

        //if (isSculptureOnBoard()) {
        //            items.add(Strings.EncounterPoliceSubmitSculpture);
        //        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onArrested(Object object) {
        if (sculptureOnBoard) {
            log.fine("Arrested + Sculpture");
            showAlert(Alerts.SculptureConfiscated.getValue());
            failQuest();
        } else {
            log.fine("Arrested w/o Sculpture");
        }
    }

    // TODO repeat if < normal, otherwise fail
    private void onEscapeWithPod(Object object) {
        if (jarekOnBoard) {
            log.fine("Escaped + Jarek");
            showAlert(Alerts.JarekTakenHome.getValue());
            failQuest();
        } else {
            log.fine("Escaped w/o Jarek");
        }
    }

    private void failQuest() {
        game.getQuestSystem().unSubscribeAll(getQuest());
        questStatus = STATUS_NOT_STARTED;
        setQuestState(QuestState.FAILED);
        removePassenger();
    }

    private void removePassenger() {
        Game.getCommander().getShip().fire(jarek.getId());
        jarekOnBoard = false;
    }

    private void onIncrementDays(Object object) {
        if (jarekOnBoard) {
            log.fine(questStatus + "");
            if (questStatus == STATUS_JAREK_IMPATIENT / 2) {
                showAlert(Alerts.SpecialPassengerConcernedJarek.getValue());
            } else if (questStatus == STATUS_JAREK_IMPATIENT - 1) {
                showAlert(Alerts.SpecialPassengerImpatientJarek.getValue());
                jarek.setPilot(0);
                jarek.setFighter(0);
                jarek.setTrader(0);
                jarek.setEngineer(0);
            }

            if (questStatus < STATUS_JAREK_IMPATIENT) {
                questStatus++;
            }
        } else {
            log.fine("skipped");
        }
    }

    private void onNewsAddEventOnArrival(Object object) {
        if (jarekOnBoard && Game.isCurrentSystemIs(StarSystemId.Devidia)) {
            log.fine("" + getNewsIds().get(0));
            Game.getNews().addEvent(getNewsIds().get(0));
        } else {
            log.fine("skipped");
        }
    }


    private void onIsConsiderCheat(Object object) {
        CheatWords cheatWords = (CheatWords) object;
        if (cheatWords.getSecond().equals(CheatTitles.Sculpture.name())) {
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
        ((Map<String, Integer>) object).put(CheatTitles.Sculpture.getValue(), questStatus);
    }

    /*"Stolen Sculpture", "Sculpture Delivered",
            "Install Hidden Compartments",


    "A hooded figure approaches you and asks if you'd be willing to deliver some recently acquired merchandise to Endor. He's holding a small sculpture of a man holding some kind of light sword that you strongly suspect was stolen. It appears to be made of plastic and not very valuable. \"I'll pay you 2,000 credits now, plus 15,000 on delivery,\" the figure says. After seeing the look on your face he adds, \"It's a collector's item. Will you deliver it or not?\"",
            "Yet another dark, hooded figure approaches. \"Do you have the action fig- umm, the sculpture?\" You hand it over and hear what sounds very much like a giggle from under the hood. \"I know you were promised 15,000 credits on delivery, but I'm strapped for cash right now. However, I have something better for you. I have an acquaintance who can install hidden compartments in your ship.\" Return with an empty gadget slot when you're ready to have it installed.",
            "You're taken to a warehouse and whisked through the door. A grubby alien of some humanoid species - you're not sure which one - approaches. \"So you're the being who needs Hidden Compartments. Should I install them in your ship?\" (It requires a free gadget slot.)",
*/

    enum QuestPhases implements SimpleValueEnum<QuestDialog> {
        Sculpture(new QuestDialog(-2000, DIALOG, "Ambassador Jarek", "A recent change in the political climate of this solar system has forced Ambassador Jarek to flee back to his home system, Devidia. Would you be willing to give him a lift?")),
        SculptureDelivered(new QuestDialog(ALERT, "Jarek Gets Out", "Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment.")),
        SculptureHiddenBays(new QuestDialog(DIALOG, "Jarek Gets Out", "Ambassador Jarek is very grateful to you for delivering him back to Devidia. As a reward, he gives you an experimental handheld haggling computer, which allows you to gain larger discounts when purchasing goods and equipment."));

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


    //public static String QuestSculpture = "Deliver the stolen sculpture to Endor.";
    //    public static String QuestSculptureHiddenBays = "Have hidden compartments installed at Endor.";

    enum QuestClues implements SimpleValueEnum<String> {
        Jarek("Take ambassador Jarek to Devidia."),
        JarekImpatient("Take ambassador Jarek to Devidia." + newline + "Jarek is wondering why the journey is taking so long, and is no longer of much help in negotiating trades.");

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

    //public static String AlertsEncounterPiratesTakeSculptureTitle = "Pirates Take Sculpture";
    //public static String AlertsEncounterPiratesTakeSculptureMessage = "As the pirates ransack your ship, they find the stolen sculpture. \"This is worth thousands!\" one pirate exclaims, as he stuffs it into his pack.";
    public static String AlertsEquipmentHiddenCompartmentsTitle = "Hidden Compartments";
    public static String AlertsEquipmentHiddenCompartmentsMessage = "You now have hidden compartments equivalent to 5 extra cargo bays installed in your ship. Police won't find illegal cargo hidden in these compartments.";
    public static String AlertsJailHiddenCargoBaysRemovedTitle = "Hidden Compartments Removed";
    public static String AlertsJailHiddenCargoBaysRemovedMessage = "When your ship is impounded, the police go over it with a fine-toothed comb. You hidden compartments are found and removed.";
    public static String AlertsPreciousHiddenTitle = "Precious Cargo Hidden";
    public static String AlertsPreciousHiddenMessage = "You quickly hide ^1 in your hidden cargo bays before the pirates board your ship. This would never work with the police, but pirates are usually in more of a hurry.";
    public static String AlertsSculptureConfiscatedTitle = "Police Confiscate Sculpture";
    public static String AlertsSculptureConfiscatedMessage = "The Police confiscate the stolen sculpture and return it to its rightful owner.";
    public static String AlertsSculptureSavedTitle = "Sculpture Saved";
    public static String AlertsSculptureSavedMessage = "On your way to the escape pod, you grab the stolen sculpture. Oh well, at least you saved something.";



    //case EquipmentHiddenCompartments:
    //                return new FormAlert(AlertsEquipmentHiddenCompartmentsTitle, AlertsEquipmentHiddenCompartmentsMessage,
    //                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);

    //case JailHiddenCargoBaysRemoved:
    //                return new FormAlert(AlertsJailHiddenCargoBaysRemovedTitle, AlertsJailHiddenCargoBaysRemovedMessage,
    //                        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);





    enum Alerts implements SimpleValueEnum<AlertDialog> {
        SpecialPassengerConcernedJarek("Ship's Comm.", "Commander? Jarek here. Do you require any assistance in charting a course to Devidia?"),
        SpecialPassengerImpatientJarek("Ship's Comm.", "Captain! This is the Ambassador speaking. We should have been there by now?!"),
        JarekTakenHome("Jarek Taken Home", "The Space Corps decides to give ambassador Jarek a lift home to Devidia.");

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


    /// case Sculpture:
    //                    addEvent(NewsEvent.SculptureStolen.castToInt());
    //                    break;
    //                case SculptureDelivered:
    //                    addEvent(NewsEvent.SculptureTracked.castToInt());
    //                    break;
    enum News implements SimpleValueEnum<String> {
        PricelessCollectorsItemWasStolen("Priceless collector's item stolen from home of Geurge Locas!"),
        SpaceCorpsFollowsSculptureCaptors("Space Corps follows ^3 with alleged stolen sculpture to ^2.");

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

    /*public static String EncounterHideSculpture = "the stolen sculpture";
    public static String EncounterPoliceSubmitSculpture = "a stolen sculpture";
    public static String EncounterPoliceSurrenderSculpt = "confiscate the sculpture";*/

    enum Encounters implements SimpleValueEnum<String> {
        PretextScorpion("the kidnappers in a ^1"),
        PrincessRescued(newline + newline + "You land your ship near where the Space Corps has landed with the Scorpion in tow. The Princess is revived from hibernation and you get to see her for the first time. Instead of the spoiled child you were expecting, Ziyal is possible the most beautiful woman you've ever seen. \"What took you so long?\" she demands. You notice a twinkle in her eye, and then she smiles. Not only is she beautiful, but she's got a sense of humor. She says, \"Thank you for freeing me. I am in your debt.\" With that she give you a kiss on the cheek, then leaves. You hear her mumble, \"Now about a ride home.\""),
        HidePrincess("the Princess");

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
        Jarek("Jarek");

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

    //TODO gadgets
    //"5 Hidden Cargo Bays"
    //+ dump/localize
    //Gadgets[GadgetType.EXTRA_CARGO_BAYS.castToInt()],

    enum SpecialCargo implements SimpleValueEnum<String> {
        Sculpture("A stolen plastic sculpture of a man holding some kind of light sword.");
        private String value;

        SpecialCargo(String value) {
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
        Sculpture("Sculpture");
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

    //TODO
    @Override
    public String toString() {
        return "JarekQuest{" +
                "questStatus=" + questStatus +
                //", jarek=" + jarek +
                //", jarekOnBoard=" + jarekOnBoard +
                //", shipBarCode=" + shipBarCode +
                "} " + super.toString();
    }
}
//TODO
/*

case EncounterPiratesTakeSculpture:
        return new FormAlert(AlertsEncounterPiratesTakeSculptureTitle, AlertsEncounterPiratesTakeSculptureMessage,
        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);

case SculptureConfiscated:
        return new FormAlert(AlertsSculptureConfiscatedTitle, AlertsSculptureConfiscatedMessage, AlertsOk,
        DialogResult.OK, null, DialogResult.NONE, args);
case SculptureSaved:
        return new FormAlert(AlertsSculptureSavedTitle, AlertsSculptureSavedMessage,
        AlertsOk, DialogResult.OK, null, DialogResult.NONE, args);*/
