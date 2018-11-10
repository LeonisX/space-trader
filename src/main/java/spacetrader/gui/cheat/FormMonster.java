package spacetrader.gui.cheat;

import spacetrader.controls.*;
import spacetrader.controls.enums.BorderStyle;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatCode;
import spacetrader.game.cheat.SomeStringsForCheatSwitch;
import spacetrader.game.enums.ShipyardId;
import spacetrader.gui.FontCollection;
import spacetrader.gui.SpaceTraderForm;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;
import spacetrader.util.Util;

@CheatCode
public class FormMonster extends SpaceTraderForm {

    private static final int SPLIT_SYSTEMS = 31;

    private final Game game = Game.getCurrentGame();

    private Button closeButton = new Button();
    private SimplePanel mercenariesPanel = new SimplePanel();
    private SimplePanel questsPanel = new SimplePanel();
    private SimplePanel shipyardsPanel = new SimplePanel();
    private HorizontalLine topHorizontalLine = new HorizontalLine();
    private VerticalLine verticalLine = new VerticalLine();
    private HorizontalLine shipyardHorizontalLine = new HorizontalLine();
    private Label mercenariesLabel = new Label();
    private Label questsLabel = new Label();
    private Label shipyardsLabel = new Label();
    private LinkLabel mercenariesIdLabel = new LinkLabel();
    private LinkLabel mercenariesNameLabel = new LinkLabel();
    private LinkLabel mercenariesSkillPilotLabel = new LinkLabel();
    private LinkLabel mercenariesSkillFighterLabel = new LinkLabel();
    private LinkLabel mercenariesSkillTraderLabel = new LinkLabel();
    private LinkLabel mercenariesSkillEngineerLabel = new LinkLabel();
    private LinkLabel mercenariesSystemLabel = new LinkLabel();
    private LinkLabel questsSystemLabel = new LinkLabel();
    private LinkLabel questsDescrLabel = new LinkLabel();
    private LinkLabel shipyardsSystemLabel = new LinkLabel();
    private LinkLabel shipyardsDescrLabel = new LinkLabel();
    private Label mercenariesPanelIds = new Label();
    private Label mercenariesPanelNames = new Label();
    private Label mercenariesPanelSkillsPilotLabel = new Label();
    private Label mercenariesPanelSkillsFighter = new Label();
    private Label mercenariesPanelSkillsTrader = new Label();
    private Label mercenariesPanelSkillsEngineer = new Label();
    private LinkLabel mercenariesPanelSystems = new LinkLabel();
    private LinkLabel mercenariesPanelSystems2 = new LinkLabel();
    private LinkLabel questPanelSystemsLabel = new LinkLabel();
    private Label questsPanelDescrLabel = new Label();
    private LinkLabel shipyardSystemsLabelValue = new LinkLabel();

    private Label shipyardsDescrLabelValue = new Label();

    private Integer[] mercIds;
    private Integer[] questSystemIds;
    private Integer[] shipyardSystemIds;

    public FormMonster() {
        initializeComponent();

        populateIdArrays();

        setLabelHeights();

        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        this.setName("formMonster");
        this.setText("Monster.com Job Listing");
        this.setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setClientSize(617, 720);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setShowInTaskbar(false);
        this.setCancelButton(closeButton);
        
        this.suspendLayout();

        topHorizontalLine.setLocation(4, 40);
        topHorizontalLine.setWidth(609);
        topHorizontalLine.setTabStop(false);

        questsLabel.setAutoSize(true);
        questsLabel.setFont(FontCollection.bold10);
        questsLabel.setLocation(88, 4);
        questsLabel.setSize(50, 19);
        questsLabel.setTabIndex(134);
        questsLabel.setText("Quests");

        questsSystemLabel.setAutoSize(true);
        questsSystemLabel.setFont(FontCollection.bold825);
        questsSystemLabel.setLocation(13, 24);
        questsSystemLabel.setSize(43, 16);
        questsSystemLabel.setTabIndex(1);
        questsSystemLabel.setTabStop(true);
        questsSystemLabel.setText("System");
        questsSystemLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        questsDescrLabel.setAutoSize(true);
        questsDescrLabel.setFont(FontCollection.bold825);
        questsDescrLabel.setLocation(85, 24);
        questsDescrLabel.setSize(63, 16);
        questsDescrLabel.setTabIndex(2);
        questsDescrLabel.setTabStop(true);
        questsDescrLabel.setText("Description");
        questsDescrLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        questsPanel.autoScroll = true;
        questsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        questsPanel.controls.addAll(questsPanelDescrLabel, questPanelSystemsLabel);
        questsPanel.setLocation(8, 44);
        questsPanel.setSize(222, 242);
        questsPanel.setTabIndex(159);

        questsPanelDescrLabel.setLocation(76, 4);
        questsPanelDescrLabel.setSize(120, 370);
        questsPanelDescrLabel.setTabIndex(48);

        questPanelSystemsLabel.setLinkArea(new LinkArea(0, 0));
        questPanelSystemsLabel.setLocation(4, 4);
        questPanelSystemsLabel.setSize(68, 370);
        questPanelSystemsLabel.setTabIndex(12);
        questPanelSystemsLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        verticalLine.setLocation(234, 8);
        verticalLine.setHeight(357);
        verticalLine.setTabStop(false);

        mercenariesLabel.setAutoSize(true);
        mercenariesLabel.setFont(FontCollection.bold10);
        mercenariesLabel.setLocation(348, 4);
        mercenariesLabel.setSize(84, 19);
        mercenariesLabel.setTabIndex(141);
        mercenariesLabel.setText("Mercenaries");

        mercenariesIdLabel.setAutoSize(true);
        mercenariesIdLabel.setFont(FontCollection.bold825);
        mercenariesIdLabel.setLocation(247, 24);
        mercenariesIdLabel.setSize(16, 16);
        mercenariesIdLabel.setTabIndex(5);
        mercenariesIdLabel.setTabStop(true);
        mercenariesIdLabel.setText("ID");
        mercenariesIdLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        mercenariesNameLabel.setAutoSize(true);
        mercenariesNameLabel.setFont(FontCollection.bold825);
        mercenariesNameLabel.setLocation(268, 24);
        mercenariesNameLabel.setSize(35, 16);
        mercenariesNameLabel.setTabIndex(6);
        mercenariesNameLabel.setTabStop(true);
        mercenariesNameLabel.setText("Name");
        mercenariesNameLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        mercenariesSkillPilotLabel.setAutoSize(true);
        mercenariesSkillPilotLabel.setFont(FontCollection.bold825);
        mercenariesSkillPilotLabel.setLocation(341, 24);
        mercenariesSkillPilotLabel.setSize(12, 16);
        mercenariesSkillPilotLabel.setTabIndex(7);
        mercenariesSkillPilotLabel.setTabStop(true);
        mercenariesSkillPilotLabel.setText("P");
        mercenariesSkillPilotLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        mercenariesSkillFighterLabel.setAutoSize(true);
        mercenariesSkillFighterLabel.setFont(FontCollection.bold825);
        mercenariesSkillFighterLabel.setLocation(362, 24);
        mercenariesSkillFighterLabel.setSize(11, 16);
        mercenariesSkillFighterLabel.setTabIndex(8);
        mercenariesSkillFighterLabel.setTabStop(true);
        mercenariesSkillFighterLabel.setText("F");
        mercenariesSkillFighterLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        mercenariesSkillTraderLabel.setAutoSize(true);
        mercenariesSkillTraderLabel.setFont(FontCollection.bold825);
        mercenariesSkillTraderLabel.setLocation(382, 24);
        mercenariesSkillTraderLabel.setSize(11, 16);
        mercenariesSkillTraderLabel.setTabIndex(9);
        mercenariesSkillTraderLabel.setTabStop(true);
        mercenariesSkillTraderLabel.setText("T");
        mercenariesSkillTraderLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        mercenariesSkillEngineerLabel.setAutoSize(true);
        mercenariesSkillEngineerLabel.setFont(FontCollection.bold825);
        mercenariesSkillEngineerLabel.setLocation(401, 24);
        mercenariesSkillEngineerLabel.setSize(12, 16);
        mercenariesSkillEngineerLabel.setTabIndex(10);
        mercenariesSkillEngineerLabel.setTabStop(true);
        mercenariesSkillEngineerLabel.setText("E");
        mercenariesSkillEngineerLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        mercenariesSystemLabel.setAutoSize(true);
        mercenariesSystemLabel.setFont(FontCollection.bold825);
        mercenariesSystemLabel.setLocation(425, 24);
        mercenariesSystemLabel.setSize(43, 16);
        mercenariesSystemLabel.setTabIndex(11);
        mercenariesSystemLabel.setTabStop(true);
        mercenariesSystemLabel.setText("System");
        mercenariesSystemLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });


        mercenariesPanel.autoScroll = true;
        mercenariesPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        mercenariesPanel.controls.addAll(mercenariesPanelSkillsPilotLabel, mercenariesPanelSkillsFighter,
                mercenariesPanelSkillsTrader, mercenariesPanelSkillsEngineer, mercenariesPanelSystems,
                mercenariesPanelIds, mercenariesPanelNames, mercenariesPanelSystems2);
        mercenariesPanel.setLocation(239, 44);
        mercenariesPanel.setSize(371, 677);
        mercenariesPanel.setTabIndex(158);

        mercenariesPanelSkillsPilotLabel.setLocation(93, 4);
        mercenariesPanelSkillsPilotLabel.setSize(20, 673);
        mercenariesPanelSkillsPilotLabel.setTabIndex(144);

        mercenariesPanelSkillsFighter.setLocation(113, 4);
        mercenariesPanelSkillsFighter.setSize(20, 673);
        mercenariesPanelSkillsFighter.setTabIndex(145);

        mercenariesPanelSkillsTrader.setLocation(133, 4);
        mercenariesPanelSkillsTrader.setSize(20, 673);
        mercenariesPanelSkillsTrader.setTabIndex(146);

        mercenariesPanelSkillsEngineer.setLocation(153, 4);
        mercenariesPanelSkillsEngineer.setSize(20, 673);
        mercenariesPanelSkillsEngineer.setTabIndex(147);

        mercenariesPanelSystems.setLinkArea(new LinkArea(0, 0));
        mercenariesPanelSystems.setLocation(185, 4);
        mercenariesPanelSystems.setSize(160, 405);
        mercenariesPanelSystems.setTabIndex(14);
        mercenariesPanelSystems.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        mercenariesPanelIds.setLocation(0, 4);
        mercenariesPanelIds.setSize(23, 673);
        mercenariesPanelIds.setTabIndex(142);

        mercenariesPanelNames.setLocation(28, 4);
        mercenariesPanelNames.setSize(69, 673);
        mercenariesPanelNames.setTabIndex(141);

        mercenariesPanelSystems2.setLinkArea(new LinkArea(0, 0));
        mercenariesPanelSystems2.setLocation(185, 401);
        mercenariesPanelSystems2.setSize(160, 673);
        mercenariesPanelSystems2.setTabIndex(148);
        mercenariesPanelSystems2.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        shipyardsLabel.setAutoSize(true);
        shipyardsLabel.setFont(FontCollection.bold10);
        shipyardsLabel.setLocation(79, 298);
        shipyardsLabel.setSize(68, 19);
        shipyardsLabel.setTabIndex(155);
        shipyardsLabel.setText("Shipyards");

        shipyardsSystemLabel.setAutoSize(true);
        shipyardsSystemLabel.setFont(FontCollection.bold825);
        shipyardsSystemLabel.setLocation(13, 318);
        shipyardsSystemLabel.setSize(43, 16);
        shipyardsSystemLabel.setTabIndex(3);
        shipyardsSystemLabel.setTabStop(true);
        shipyardsSystemLabel.setText("System");
        shipyardsSystemLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        shipyardsDescrLabel.setAutoSize(true);
        shipyardsDescrLabel.setFont(FontCollection.bold825);
        shipyardsDescrLabel.setLocation(85, 318);
        shipyardsDescrLabel.setSize(63, 16);
        shipyardsDescrLabel.setTabIndex(4);
        shipyardsDescrLabel.setTabStop(true);
        shipyardsDescrLabel.setText("Description");
        shipyardsDescrLabel.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                sortLinkClicked(sender);
            }
        });

        shipyardHorizontalLine.setLocation(4, 334);
        shipyardHorizontalLine.setWidth(222);
        shipyardHorizontalLine.setTabStop(false);

        shipyardsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipyardsPanel.controls.addAll(shipyardsDescrLabelValue, shipyardSystemsLabelValue);
        shipyardsPanel.setLocation(8, 338);
        shipyardsPanel.setSize(222, 93);
        shipyardsPanel.setTabIndex(160);

        shipyardsDescrLabelValue.setLocation(76, 4);
        shipyardsDescrLabelValue.setSize(120, 93);
        shipyardsDescrLabelValue.setTabIndex(158);

        shipyardSystemsLabelValue.setLinkArea(new LinkArea(0, 0));
        shipyardSystemsLabelValue.setLocation(4, 4);
        shipyardSystemsLabelValue.setSize(68, 93);
        shipyardSystemsLabelValue.setTabIndex(13);
        shipyardSystemsLabelValue.setLinkClicked(new EventHandler<Object, LinkLabelLinkClickedEventArgs>() {
            @Override
            public void handle(Object sender, LinkLabelLinkClickedEventArgs e) {
                systemLinkClicked(e);
            }
        });

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        closeButton.setText("X");

        controls.addAll(questsLabel, questsSystemLabel, questsDescrLabel, questsPanel);
        controls.addAll(mercenariesLabel, mercenariesIdLabel, mercenariesNameLabel, mercenariesSkillPilotLabel,
                mercenariesSkillFighterLabel, mercenariesSkillTraderLabel, mercenariesSkillEngineerLabel,
                mercenariesSystemLabel, mercenariesPanel, topHorizontalLine, verticalLine);
        controls.addAll(shipyardsLabel, shipyardsSystemLabel, shipyardsDescrLabel, shipyardHorizontalLine, shipyardsPanel);
        controls.add(closeButton);
    }

    private int compare(int a, int b, String sortWhat, String sortBy) {
        int compareVal = 0;

        if (sortWhat.equals("M")) { // Mercenaries
            CrewMember crewMemberA = game.getMercenaries()[a];
            CrewMember crewMemberB = game.getMercenaries()[b];

            boolean strCompare = false;
            Object valA = null;
            Object valB = null;

            switch (SomeStringsForCheatSwitch.valueOf(sortBy)) {
                case I: // Id
                    valA = crewMemberA.getId().castToInt();
                    valB = crewMemberB.getId().castToInt();
                    break;
                case N: // Name
                    valA = crewMemberA.getName();
                    valB = crewMemberB.getName();
                    strCompare = true;
                    break;
                case P: // Pilot
                    valA = crewMemberA.getPilot();
                    valB = crewMemberB.getPilot();
                    break;
                case F: // Fighter
                    valA = crewMemberA.getFighter();
                    valB = crewMemberB.getFighter();
                    break;
                case T: // Trader
                    valA = crewMemberA.getTrader();
                    valB = crewMemberB.getTrader();
                    break;
                case E: // Engineer
                    valA = crewMemberA.getEngineer();
                    valB = crewMemberB.getEngineer();
                    break;
                case S: // System
                    valA = currentSystemDisplay(crewMemberA);
                    valB = currentSystemDisplay(crewMemberB);
                    strCompare = true;
                    break;
            }

            if (strCompare) {
                compareVal = Util.compareTo((String) valA, (String) valB);
            } else {
                compareVal = Util.compareTo((Integer) valA, (Integer) valB);
            }

            // Secondary sort by Name
            if (compareVal == 0 && !sortBy.equals("N")) {
                compareVal = crewMemberA.getName().compareTo(crewMemberB.getName());
            }
        } else {
            StarSystem starSystemA = game.getUniverse()[a];
            StarSystem starSystemB = game.getUniverse()[b];

            if (sortBy.equals("D")) { // Description
                String nameA = "";
                String nameB = "";

                switch (SomeStringsForCheatSwitch.valueOf(sortWhat)) {
                    case Q: // Quests
                        nameA = starSystemA.specialEvent().getTitle();
                        nameB = starSystemB.specialEvent().getTitle();
                        break;
                    case S: // Shipyards
                        nameA = starSystemA.getShipyard().getName();
                        nameB = starSystemB.getShipyard().getName();
                        break;
                }

                compareVal = nameA.compareTo(nameB);
            }

            if (compareVal == 0) { // Default sort - System Name
                compareVal = starSystemA.getName().compareTo(starSystemB.getName());
            }
        }

        return compareVal;
    }

    private String currentSystemDisplay(CrewMember merc) {
        return (null == merc.getCurrentSystem() ? Strings.Unknown
                : (game.getCommander().getShip().hasCrew(merc.getId()) ? Functions.stringVars(Strings.MercOnBoard, merc
                .getCurrentSystem().getName()) : merc.getCurrentSystem().getName()));
    }

    private void populateIdArrays() {
        // Populate the mercenary ids array.
        ArrayList<Integer> ids = new ArrayList<>();
        for (CrewMember merc : game.getMercenaries()) {
            if (!Util.arrayContains(Consts.SpecialCrewMemberIds, merc.getId())) {
                ids.add(merc.getId().castToInt());
            }
        }
        mercIds = ids.toArray(new Integer[0]);

        // Populate the quest and shipyard system ids arrays.
        ArrayList<Integer> quests = new ArrayList<>();
        ArrayList<Integer> shipyards = new ArrayList<>();
        for (StarSystem system : game.getUniverse()) {
            if (system.showSpecialButton()) {
                quests.add(system.getId().castToInt());
            }
            if (system.getShipyardId() != ShipyardId.NA) {
                shipyards.add(system.getId().castToInt());
            }
        }
        questSystemIds = quests.toArray(new Integer[0]);
        shipyardSystemIds = shipyards.toArray(new Integer[0]);

        // Sort the arrays.
        sort("M", "N"); // Sort mercenaries by name.
        sort("Q", "S"); // Sort quests by system name.
        sort("S", "S"); // Sort shipyards by system name.
    }

    //TODO scale 15
    private void setLabelHeights() {
        int questHeight = (int) Math.ceil(questSystemIds.length * 15) + 1;
        questsPanelDescrLabel.setHeight(questHeight);
        questPanelSystemsLabel.setHeight(questHeight);

        int shipyardHeight = (int) Math.ceil(shipyardSystemIds.length * 15) + 1;
        shipyardsDescrLabelValue.setHeight(shipyardHeight);
        shipyardSystemsLabelValue.setHeight(shipyardHeight);

        int mercHeight = (int) Math.ceil(mercIds.length * 15) + 1;
        mercenariesPanelIds.setHeight(mercHeight);
        mercenariesPanelNames.setHeight(mercHeight);
        mercenariesPanelSkillsPilotLabel.setHeight(mercHeight);
        mercenariesPanelSkillsFighter.setHeight(mercHeight);
        mercenariesPanelSkillsTrader.setHeight(mercHeight);
        mercenariesPanelSkillsEngineer.setHeight(mercHeight);

        // Due to a limitation of the LinkLabel control, no more than 32 links
        // can exist in the LinkLabel.
        mercenariesPanelSystems.setHeight((int) Math.ceil(Math.min(mercIds.length, SPLIT_SYSTEMS) * 15) + 1);
        if (mercIds.length > SPLIT_SYSTEMS) {
            mercenariesPanelSystems2.setVisible(true);
            mercenariesPanelSystems2.setHeight((int) Math.ceil((mercIds.length - SPLIT_SYSTEMS) * 15) + 1);
            mercenariesPanelSystems2.setTop(mercenariesPanelSystems.getTop() + mercenariesPanelSystems.getHeight());
        } else {
            mercenariesPanelSystems2.setVisible(false);
        }
    }

    private void sort(String sortWhat, String sortBy) {
        Integer[] array;
        switch (SomeStringsForCheatSwitch.valueOf(sortWhat)) {
            case M:
                array = mercIds;
                break;
            case Q:
                array = questSystemIds;
                break;
            case S:
                array = shipyardSystemIds;
                break;
            default:
                return;
        }

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (compare(array[j], array[j + 1], sortWhat, sortBy) > 0) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private void updateAll() {
        updateMercs();
        updateQuests();
        updateShipyards();
    }

    private void updateMercs() {
        mercenariesPanelIds.setText("");
        mercenariesPanelNames.setText("");
        mercenariesPanelSkillsPilotLabel.setText("");
        mercenariesPanelSkillsFighter.setText("");
        mercenariesPanelSkillsTrader.setText("");
        mercenariesPanelSkillsEngineer.setText("");
        mercenariesPanelSystems.setText("");
        mercenariesPanelSystems2.setText("");
        mercenariesPanelSystems.getLinks().clear();
        mercenariesPanelSystems2.getLinks().clear();

        for (int i = 0; i < mercIds.length; i++) {
            CrewMember merc = game.getMercenaries()[mercIds[i]];
            boolean link = (merc.getCurrentSystem() != null) && !game.getCommander().getShip().hasCrew(merc.getId());

            mercenariesPanelIds.setText(mercenariesPanelIds.getText() + ((merc.getId().castToInt()) + Strings.newline));
            mercenariesPanelNames.setText(mercenariesPanelNames.getText() + (merc.getName() + Strings.newline));
            mercenariesPanelSkillsPilotLabel.setText(mercenariesPanelSkillsPilotLabel.getText() + (merc.getPilot() + Strings.newline));
            mercenariesPanelSkillsFighter.setText(mercenariesPanelSkillsFighter.getText() + (merc.getFighter() + Strings.newline));
            mercenariesPanelSkillsTrader.setText(mercenariesPanelSkillsTrader.getText() + (merc.getTrader() + Strings.newline));
            mercenariesPanelSkillsEngineer.setText(mercenariesPanelSkillsEngineer.getText() + (merc.getEngineer() + Strings.newline));

            if (i < SPLIT_SYSTEMS) {
                int start = mercenariesPanelSystems.getText().length();
                mercenariesPanelSystems.setText(mercenariesPanelSystems.getText() + (currentSystemDisplay(merc) + Strings.newline));
                if (link) {
                    mercenariesPanelSystems.getLinks().add(start, merc.getCurrentSystem().getName().length(), merc.getCurrentSystem().getName());
                }
            } else {
                int start = mercenariesPanelSystems2.getText().length();
                mercenariesPanelSystems2.setText(mercenariesPanelSystems2.getText() + (currentSystemDisplay(merc) + Strings.newline));
                if (link) {
                    mercenariesPanelSystems2.getLinks().add(start, merc.getCurrentSystem().getName().length(), merc.getCurrentSystem().getName());
                }
            }
        }

        mercenariesPanelIds.setText(mercenariesPanelIds.getText().trim());
        mercenariesPanelNames.setText(mercenariesPanelNames.getText().trim());
        mercenariesPanelSkillsPilotLabel.setText(mercenariesPanelSkillsPilotLabel.getText().trim());
        mercenariesPanelSkillsFighter.setText(mercenariesPanelSkillsFighter.getText().trim());
        mercenariesPanelSkillsTrader.setText(mercenariesPanelSkillsTrader.getText().trim());
        mercenariesPanelSkillsEngineer.setText(mercenariesPanelSkillsEngineer.getText().trim());
        mercenariesPanelSystems.setText(mercenariesPanelSystems.getText().trim());
        mercenariesPanelSystems2.setText(mercenariesPanelSystems2.getText().trim());
    }

    private void updateQuests() {
        questPanelSystemsLabel.setText("");
        questsPanelDescrLabel.setText("");
        questPanelSystemsLabel.getLinks().clear();

        for (Integer questSystemId : questSystemIds) {
            StarSystem system = game.getUniverse()[questSystemId];
            int start = questPanelSystemsLabel.getText().length();

            questPanelSystemsLabel.setText(questPanelSystemsLabel.getText() + (system.getName() + Strings.newline));
            questsPanelDescrLabel.setText(questsPanelDescrLabel.getText() + (system.specialEvent().getTitle() + Strings.newline));

            questPanelSystemsLabel.getLinks().add(start, system.getName().length(), system.getName());
        }

        questPanelSystemsLabel.setText(questPanelSystemsLabel.getText().trim());
        questsPanelDescrLabel.setText(questsPanelDescrLabel.getText().trim());
    }

    private void updateShipyards() {
        shipyardSystemsLabelValue.setText("");
        shipyardsDescrLabelValue.setText("");
        shipyardSystemsLabelValue.getLinks().clear();

        for (Integer shipyardSystemId : shipyardSystemIds) {
            StarSystem system = game.getUniverse()[shipyardSystemId];
            int start = shipyardSystemsLabelValue.getText().length();

            shipyardSystemsLabelValue.setText(shipyardSystemsLabelValue.getText() + (system.getName() + Strings.newline));
            shipyardsDescrLabelValue.setText(shipyardsDescrLabelValue.getText() + (system.getShipyard().getName() + Strings.newline));

            shipyardSystemsLabelValue.getLinks().add(start, system.getName().length(), system.getName());
        }

        shipyardSystemsLabelValue.setText(shipyardSystemsLabelValue.getText().trim());
        shipyardsDescrLabelValue.setText(shipyardsDescrLabelValue.getText().trim());
    }

    private void systemLinkClicked(LinkLabelLinkClickedEventArgs e) {
        Game.getCurrentGame().setSelectedSystemByName(e.getLink().getLinkData().toString());
        Game.getCurrentGame().getParentWindow().updateAll();
        close();
    }

    private void sortLinkClicked(Object sender) {
        sort(((LinkLabel) sender).getName().substring(0, 1).toUpperCase(), ((LinkLabel) sender).getText().substring(0, 1));
        updateAll();
    }
}
