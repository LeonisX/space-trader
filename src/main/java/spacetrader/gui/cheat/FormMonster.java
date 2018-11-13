package spacetrader.gui.cheat;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.enums.*;
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

import java.awt.*;

@CheatCode
public class FormMonster extends SpaceTraderForm {

    private static final String THREE_SPACES = "   ";
    private static final String FIVE_SPACES = "     ";
    private static final String EIGHT_SPACES = "        ";

    private final Game game = Game.getCurrentGame();

    private Button closeButton = new Button();
    private SimpleHPanel mercenariesPanel = new SimpleHPanel();
    private SimpleHPanel questsPanel = new SimpleHPanel();
    private SimpleHPanel shipyardsPanel = new SimpleHPanel();
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
    private LinkLabel questPanelSystemsLabel = new LinkLabel();
    private Label questsPanelDescrLabel = new Label();
    private LinkLabel shipyardSystemsLabelValue = new LinkLabel();

    private Label shipyardsDescrLabelValue = new Label();

    private Integer[] mercIds;
    private Integer[] questSystemIds;
    private Integer[] shipyardSystemIds;

    private FontMetrics metrics;


    public FormMonster() {
        initializeComponent();

        populateIdArrays();

        updateAll();
    }

    private void initializeComponent() {
        ReflectionUtils.setAllComponentNames(this);

        this.setName("formMonster");
        this.setText("Monster.com Job Listing");
        this.setFormBorderStyle(FormBorderStyle.FIXED_DIALOG);
        this.setStartPosition(FormStartPosition.CENTER_PARENT);
        this.setClientSize(638, 700);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setShowInTaskbar(false);
        this.setCancelButton(closeButton);

        topHorizontalLine.setLocation(4, 40);
        topHorizontalLine.setWidth(628);

        questsLabel.setAutoSize(true);
        questsLabel.setControlBinding(ControlBinding.CENTER);
        questsLabel.setFont(FontCollection.bold10);
        questsLabel.setLocation(130, 4);
        //questsLabel.setSize(50, 19);
        questsLabel.setText("Quests");

        questsSystemLabel.setAutoSize(true);
        questsSystemLabel.setFont(FontCollection.bold825);
        questsSystemLabel.setLocation(23, 24);
        //questsSystemLabel.setSize(43, 16);
        questsSystemLabel.setText("System");
        questsSystemLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "S");
            }
        });

        questsDescrLabel.setAutoSize(true);
        questsDescrLabel.setFont(FontCollection.bold825);
        questsDescrLabel.setLocation(90, 24);
        //questsDescrLabel.setSize(63, 16);
        questsDescrLabel.setText("Description");
        questsDescrLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "D");
            }
        });

        //questsPanel.autoScroll = true;
        questsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        //questsPanel.controls.addAll(questPanelSystemsLabel, questsPanelDescrLabel);
        questsPanel.setLocation(8, 44);
        questsPanel.setSize(242, 270);

        metrics = questsPanelDescrLabel.asSwingObject().getFontMetrics(questsPanelDescrLabel.asSwingObject().getFont());

        questPanelSystemsLabel.setAutoSize(false);
        questPanelSystemsLabel.setLocation(4, 4);
        questPanelSystemsLabel.setSize(80, 370);
        questPanelSystemsLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                systemLinkClicked(sender);
            }
        });

        questPanelSystemsLabel.setAutoSize(false);
        questsPanelDescrLabel.setLocation(90, 4);
        questsPanelDescrLabel.setSize(140, 370);

        shipyardsLabel.setAutoSize(true);
        shipyardsLabel.setControlBinding(ControlBinding.CENTER);
        shipyardsLabel.setFont(FontCollection.bold10);
        shipyardsLabel.setLocation(130, 336);
        //shipyardsLabel.setSize(68, 19);
        shipyardsLabel.setText("Shipyards");

        shipyardsSystemLabel.setAutoSize(true);
        shipyardsSystemLabel.setFont(FontCollection.bold825);
        shipyardsSystemLabel.setLocation(23, 356);
        //shipyardsSystemLabel.setSize(43, 16);
        shipyardsSystemLabel.setText("System");
        shipyardsSystemLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "S");
            }
        });

        shipyardsDescrLabel.setAutoSize(true);
        shipyardsDescrLabel.setFont(FontCollection.bold825);
        shipyardsDescrLabel.setLocation(90, 356);
        //shipyardsDescrLabel.setSize(63, 16);
        shipyardsDescrLabel.setText("Description");
        shipyardsDescrLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "D");
            }
        });

        shipyardHorizontalLine.setLocation(4, 372);
        shipyardHorizontalLine.setWidth(250);

        shipyardsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipyardsPanel.controls.addAll(shipyardSystemsLabelValue, shipyardsDescrLabelValue);
        shipyardsPanel.setLocation(8, 376);
        shipyardsPanel.setSize(242, 93);

        shipyardsDescrLabelValue.setLocation(76, 4);
        shipyardsDescrLabelValue.setSize(120, 93);

        shipyardSystemsLabelValue.setLocation(4, 4);
        shipyardSystemsLabelValue.setSize(68, 93);
        shipyardSystemsLabelValue.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                systemLinkClicked(sender);
            }
        });

        verticalLine.setLocation(254, 8);
        verticalLine.setHeight(460);

        mercenariesLabel.setAutoSize(true);
        mercenariesLabel.setControlBinding(ControlBinding.CENTER);
        mercenariesLabel.setFont(FontCollection.bold10);
        mercenariesLabel.setLocation(430, 4);
        //mercenariesLabel.setSize(84, 19);
        mercenariesLabel.setText("Mercenaries");

        mercenariesIdLabel.setAutoSize(true);
        mercenariesIdLabel.setFont(FontCollection.bold825);
        mercenariesIdLabel.setLocation(267, 24);
        //mercenariesIdLabel.setSize(16, 16);
        mercenariesIdLabel.setText("ID");
        mercenariesIdLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "I");
            }
        });

        mercenariesNameLabel.setAutoSize(true);
        mercenariesNameLabel.setFont(FontCollection.bold825);
        mercenariesNameLabel.setLocation(280, 24);
        //mercenariesNameLabel.setSize(35, 16);
        mercenariesNameLabel.setText("Name");
        mercenariesNameLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "N");
            }
        });

        mercenariesSkillPilotLabel.setAutoSize(true);
        mercenariesSkillPilotLabel.setFont(FontCollection.bold825);
        mercenariesSkillPilotLabel.setLocation(400, 24);
        //mercenariesSkillPilotLabel.setSize(12, 16);
        mercenariesSkillPilotLabel.setText("P");
        mercenariesSkillPilotLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "P");
            }
        });

        mercenariesSkillFighterLabel.setAutoSize(true);
        mercenariesSkillFighterLabel.setFont(FontCollection.bold825);
        mercenariesSkillFighterLabel.setLocation(420, 24);
        //mercenariesSkillFighterLabel.setSize(11, 16);
        mercenariesSkillFighterLabel.setText("F");
        mercenariesSkillFighterLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "F");
            }
        });

        mercenariesSkillTraderLabel.setAutoSize(true);
        mercenariesSkillTraderLabel.setFont(FontCollection.bold825);
        mercenariesSkillTraderLabel.setLocation(440, 24);
        //mercenariesSkillTraderLabel.setSize(11, 16);
        mercenariesSkillTraderLabel.setText("T");
        mercenariesSkillTraderLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "T");
            }
        });

        mercenariesSkillEngineerLabel.setAutoSize(true);
        mercenariesSkillEngineerLabel.setFont(FontCollection.bold825);
        mercenariesSkillEngineerLabel.setLocation(460, 24);
        //mercenariesSkillEngineerLabel.setSize(12, 16);
        mercenariesSkillEngineerLabel.setText("E");
        mercenariesSkillEngineerLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "E");
            }
        });

        mercenariesSystemLabel.setAutoSize(true);
        mercenariesSystemLabel.setFont(FontCollection.bold825);
        mercenariesSystemLabel.setLocation(500, 24);
        //mercenariesSystemLabel.setSize(43, 16);
        mercenariesSystemLabel.setText("System");
        mercenariesSystemLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "S");
            }
        });


        mercenariesPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        mercenariesPanel.setLocation(259, 44);
        mercenariesPanel.setSize(371, 650);

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

        ReflectionUtils.loadControlsData(this);
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
        SimpleVPanel idsPanel = createVPanel(20);
        SimpleVPanel namesPanel = createVPanel(160);
        SimpleVPanel pPanel = createVPanel(20);
        SimpleVPanel fPanel = createVPanel(20);
        SimpleVPanel tPanel = createVPanel(20);
        SimpleVPanel ePanel = createVPanel(20);
        SimpleVPanel systemPanel = createVPanel(90);

        mercenariesPanel.removeAll();
        mercenariesPanel.addAll(idsPanel, namesPanel, pPanel, fPanel, tPanel, ePanel, systemPanel);

        for (Integer mercId : mercIds) {
            CrewMember merc = game.getMercenaries()[mercId];

            Label label = new Label(THREE_SPACES + merc.getId().castToInt());
            label.setAutoSize(true);
            idsPanel.asJPanel().add(label.asSwingObject());

            label = new Label(EIGHT_SPACES + merc.getName() + FIVE_SPACES);
            label.setAutoSize(true);
            namesPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getPilot());
            label.setAutoSize(true);
            pPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getFighter());
            label.setAutoSize(true);
            fPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getTrader());
            label.setAutoSize(true);
            tPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getEngineer() + EIGHT_SPACES);
            label.setAutoSize(true);
            ePanel.asJPanel().add(label.asSwingObject());

            if ((merc.getCurrentSystem() != null) && !game.getCommander().getShip().hasCrew(merc.getId())) {
                LinkLabel linkLabel = new LinkLabel(currentSystemDisplay(merc));
                linkLabel.setAutoSize(true);
                linkLabel.setLinkClicked(new SimpleEventHandler<Object>() {
                    @Override
                    public void handle(Object sender) {
                        systemLinkClicked(sender);
                    }
                });
                systemPanel.asJPanel().add(linkLabel.asSwingObject());
            } else {
                label = new Label(currentSystemDisplay(merc));
                label.setAutoSize(true);
                systemPanel.asJPanel().add(label.asSwingObject());
            }
        }
    }

    private void updateQuests() {
        SimpleVPanel dummyPanel = createVPanel(10);
        SimpleVPanel systemPanel = createVPanel(90);
        SimpleVPanel descrPanel = createVPanel(160);
        questsPanel.removeAll();
        questsPanel.addAll(dummyPanel, systemPanel, descrPanel);

        dummyPanel.asJPanel().add(new Label(THREE_SPACES).asSwingObject());

        for (Integer questSystemId : questSystemIds) {
            StarSystem system = game.getUniverse()[questSystemId];

            LinkLabel linkLabel = new LinkLabel(system.getName());
            linkLabel.setAutoSize(true);
            linkLabel.setLinkClicked(new SimpleEventHandler<Object>() {
                @Override
                public void handle(Object sender) {
                    systemLinkClicked(sender);
                }
            });
            systemPanel.asJPanel().add(linkLabel.asSwingObject());

            Label label = new Label(EIGHT_SPACES + system.specialEvent().getTitle());
            label.setAutoSize(true);
            descrPanel.asJPanel().add(label.asSwingObject());
        }
        questsSystemLabel.setLeft(metrics.stringWidth(THREE_SPACES) + 10);
        questsDescrLabel.setLeft(systemPanel.getWidth());
    }

    private void updateShipyards() {
        SimpleVPanel dummyPanel = createVPanel(10);
        SimpleVPanel systemPanel = createVPanel(90);
        SimpleVPanel descrPanel = createVPanel(160);
        shipyardsPanel.removeAll();
        shipyardsPanel.addAll(dummyPanel, systemPanel, descrPanel);

        dummyPanel.asJPanel().add(new Label(THREE_SPACES).asSwingObject());

        for (Integer shipyardSystemId : shipyardSystemIds) {
            StarSystem system = game.getUniverse()[shipyardSystemId];

            LinkLabel linkLabel = new LinkLabel(system.getName());
            linkLabel.setAutoSize(true);
            linkLabel.setLinkClicked(new SimpleEventHandler<Object>() {
                @Override
                public void handle(Object sender) {
                    systemLinkClicked(sender);
                }
            });
            systemPanel.asJPanel().add(linkLabel.asSwingObject());

            Label label = new Label(EIGHT_SPACES + system.getShipyard().getName());
            label.setAutoSize(true);
            descrPanel.asJPanel().add(label.asSwingObject());
        }
        shipyardsSystemLabel.setLeft(metrics.stringWidth(THREE_SPACES) + 10);
        shipyardsDescrLabel.setLeft(systemPanel.getWidth());
    }

    private SimpleVPanel createVPanel(int width) {
        SimpleVPanel panel = new SimpleVPanel();
        panel.setSize(width, questsPanel.getHeight());
        return panel;
    }

    private void systemLinkClicked(Object sender) {
        Game.getCurrentGame().setSelectedSystemByName(((LinkLabel) sender).getText().trim());
        Game.getCurrentGame().getParentWindow().updateAll();
        close();
    }

    private void sortLinkClicked(Object sender, String key) {
        sort(((LinkLabel) sender).getName().trim().substring(0, 1).toUpperCase(), key);
        updateAll();
    }
}
