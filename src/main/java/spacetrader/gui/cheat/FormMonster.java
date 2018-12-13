package spacetrader.gui.cheat;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.enums.*;
import spacetrader.game.*;
import spacetrader.game.cheat.CheatCode;
import spacetrader.game.cheat.SomeStringsForCheatSwitch;
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.Phase;
import spacetrader.game.quest.QuestSystem;
import spacetrader.game.quest.enums.QuestState;
import spacetrader.gui.FontCollection;
import spacetrader.gui.SpaceTraderForm;
import spacetrader.stub.ArrayList;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;
import spacetrader.util.Util;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    private LinkLabel questsFeatureLabel = new LinkLabel();
    private LinkLabel shipyardsSystemLabel = new LinkLabel();
    private LinkLabel shipyardsDescrLabel = new LinkLabel();
    private LinkLabel shipyardsFeatureLabel = new LinkLabel();

    private Integer[] mercIds; //TODO object
    private List<Row> questSystems = new ArrayList<>();
    private List<Row> shipyardSystems = new ArrayList<>();

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
        this.setClientSize(718, 700);
        this.setMaximizeBox(false);
        this.setMinimizeBox(false);
        this.setShowInTaskbar(false);
        this.setCancelButton(closeButton);

        topHorizontalLine.setLocation(4, 40);
        topHorizontalLine.setWidth(708);

        questsLabel.setAutoSize(true);
        questsLabel.setControlBinding(ControlBinding.CENTER);
        questsLabel.setFont(FontCollection.bold10);
        questsLabel.setLocation(170, 4);
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
        questsDescrLabel.setLocation(60, 24);
        //questsDescrLabel.setSize(63, 16);
        questsDescrLabel.setText("Description");
        questsDescrLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "D");
            }
        });

        questsFeatureLabel.setAutoSize(true);
        questsFeatureLabel.setFont(FontCollection.bold825);
        questsFeatureLabel.setLocation(220, 24);
        questsFeatureLabel.setText("State");
        questsFeatureLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "F");
            }
        });

        //questsPanel.autoScroll = true;
        questsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        //questsPanel.controls.addAll(questPanelSystemsLabel, questsPanelDescrLabel);
        questsPanel.setLocation(8, 44);
        questsPanel.setSize(322, 310);

        metrics = questsPanel.asSwingObject().getFontMetrics(questsPanel.asSwingObject().getFont());

        shipyardsLabel.setAutoSize(true);
        shipyardsLabel.setControlBinding(ControlBinding.CENTER);
        shipyardsLabel.setFont(FontCollection.bold10);
        shipyardsLabel.setLocation(170, 366);
        //shipyardsLabel.setSize(68, 19);
        shipyardsLabel.setText("Shipyards");

        shipyardsSystemLabel.setAutoSize(true);
        shipyardsSystemLabel.setFont(FontCollection.bold825);
        shipyardsSystemLabel.setLocation(23, 386);
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
        shipyardsDescrLabel.setLocation(60, 386);
        //shipyardsDescrLabel.setSize(63, 16);
        shipyardsDescrLabel.setText("Description");
        shipyardsDescrLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "D");
            }
        });

        shipyardsFeatureLabel.setAutoSize(true);
        shipyardsFeatureLabel.setFont(FontCollection.bold825);
        shipyardsFeatureLabel.setLocation(190, 386);
        shipyardsFeatureLabel.setText("Specialization");
        shipyardsFeatureLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "F");
            }
        });

        shipyardHorizontalLine.setLocation(4, 402);
        shipyardHorizontalLine.setWidth(330);

        shipyardsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipyardsPanel.setLocation(8, 406);
        shipyardsPanel.setSize(322, 93);

        verticalLine.setLocation(334, 8);
        verticalLine.setHeight(500);

        mercenariesLabel.setAutoSize(true);
        mercenariesLabel.setControlBinding(ControlBinding.CENTER);
        mercenariesLabel.setFont(FontCollection.bold10);
        mercenariesLabel.setLocation(510, 4);
        //mercenariesLabel.setSize(84, 19);
        mercenariesLabel.setText("Mercenaries");

        mercenariesIdLabel.setAutoSize(true);
        mercenariesIdLabel.setFont(FontCollection.bold825);
        mercenariesIdLabel.setLocation(347, 24);
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
        mercenariesNameLabel.setLocation(390, 24);
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
        mercenariesSkillPilotLabel.setLocation(480, 24);
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
        mercenariesSkillFighterLabel.setLocation(500, 24);
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
        mercenariesSkillTraderLabel.setLocation(520, 24);
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
        mercenariesSkillEngineerLabel.setLocation(540, 24);
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
        mercenariesSystemLabel.setLocation(580, 24);
        //mercenariesSystemLabel.setSize(43, 16);
        mercenariesSystemLabel.setText("System");
        mercenariesSystemLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                sortLinkClicked(sender, "S");
            }
        });


        mercenariesPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        mercenariesPanel.setLocation(339, 44);
        mercenariesPanel.setSize(371, 650);

        closeButton.setDialogResult(DialogResult.CANCEL);
        closeButton.setLocation(-32, -32);
        closeButton.setSize(32, 32);
        closeButton.setTabStop(false);
        closeButton.setText("X");

        controls.addAll(questsLabel, questsSystemLabel, questsDescrLabel, questsFeatureLabel, questsPanel);
        controls.addAll(mercenariesLabel, mercenariesIdLabel, mercenariesNameLabel, mercenariesSkillPilotLabel,
                mercenariesSkillFighterLabel, mercenariesSkillTraderLabel, mercenariesSkillEngineerLabel,
                mercenariesSystemLabel, mercenariesPanel, topHorizontalLine, verticalLine);
        controls.addAll(shipyardsLabel, shipyardsSystemLabel, shipyardsDescrLabel, shipyardsFeatureLabel,
                shipyardHorizontalLine, shipyardsPanel);
        controls.add(closeButton);

        ReflectionUtils.loadControlsData(this);
    }

    private String currentSystemDisplay(CrewMember merc) {
        return (null == merc.getCurrentSystem() ? Strings.Unknown
                : (Game.getShip().hasCrew(merc.getId()) ? Functions.stringVars(Strings.MercOnBoard, merc
                .getCurrentSystem().getName()) : merc.getCurrentSystem().getName()));
    }

    private void populateIdArrays() {
        // Populate the mercenary ids array.
        ArrayList<Integer> ids = new ArrayList<>();
        for (CrewMember merc : game.getMercenaries().values()) {
            if (!Util.arrayContains(Consts.SpecialCrewMemberIds, merc.getCrewMemberId()) && merc.getId() < 1000) {
                ids.add(merc.getId());
            }
        }
        mercIds = ids.toArray(new Integer[0]);

        // Populate the quest and shipyard system ids arrays.
        questSystems.addAll(QuestSystem.getPhasesStream()
                .map(p ->
                        (null == p.getStarSystemId())
                                ? new Row(StarSystemId.NA.castToInt(), Strings.Unknown, p.getTitle(), Strings.QuestStates[p.getQuest().getQuestState().ordinal()])
                                : createQuestRow(p)
                )
                .collect(Collectors.toList()));
        for (StarSystem system : game.getUniverse()) {
            //TODO remove after all quests
            if (system.showSpecialButton()) {
                questSystems.add(createQuestRow(system.getId().castToInt()));
            }
            if (system.getShipyardId() != ShipyardId.NA) {
                shipyardSystems.add(createShipyardRow(system.getId().castToInt()));
            }
        }

        // Sort the arrays.
        sortMercenaries("N"); // Sort mercenaries by name.
        sortRows(questSystems, "S"); // Sort quests by system name.
        sortRows(shipyardSystems, "S"); // Sort shipyards by system name.
    }

    private Row createQuestRow(int systemId) {
        StarSystem system = Game.getStarSystem(systemId);
        String title = system.specialEvent().getTitle();
        String state = Strings.QuestStates[QuestState.UNKNOWN.ordinal()];
        return new Row(systemId, system.getName(), title, state);
    }

    private Row createQuestRow(Phase phase) {
        int systemId = (null == phase.getStarSystemId()) ? StarSystemId.NA.castToInt() : phase.getStarSystemId().castToInt();
        String systemName = (systemId < 0) ? Strings.Unknown : Game.getStarSystem(systemId).getName();
        return new Row(systemId, systemName, phase.getTitle(), Strings.QuestStates[phase.getQuest().getQuestState().ordinal()]);
    }

    private Row createShipyardRow(int systemId) {
        StarSystem system = Game.getStarSystem(systemId);
        return new Row(systemId, system.getName(), system.getShipyard().getName(),
                Strings.ShipyardSkills[system.getShipyard().getSkill().castToInt()]);
    }

    private void sortMercenaries(String sortBy) {
        for (int i = 0; i < mercIds.length - 1; i++) {
            for (int j = 0; j < mercIds.length - i - 1; j++) {
                if (compare(mercIds[j], mercIds[j + 1], sortBy) > 0) {
                    int temp = mercIds[j];
                    mercIds[j] = mercIds[j + 1];
                    mercIds[j + 1] = temp;
                }
            }
        }
    }

    private int compare(int a, int b, String sortBy) {
        int compareVal;

        CrewMember crewMemberA = game.getMercenaries().get(a);
        CrewMember crewMemberB = game.getMercenaries().get(b);

        boolean strCompare = false;
        Object valA = null;
        Object valB = null;

        switch (SomeStringsForCheatSwitch.valueOf(sortBy)) {
            case I: // Id
                valA = crewMemberA.getId();
                valB = crewMemberB.getId();
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

        return compareVal;
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
            CrewMember merc = game.getMercenaries().get(mercId);

            Label label = new Label(THREE_SPACES + merc.getId()).withAutoSize(true);
            idsPanel.asJPanel().add(label.asSwingObject());

            label = new Label(EIGHT_SPACES + merc.getName() + FIVE_SPACES).withAutoSize(true);
            namesPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getPilot()).withAutoSize(true);
            pPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getFighter()).withAutoSize(true);
            fPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getTrader()).withAutoSize(true);
            tPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + merc.getEngineer() + EIGHT_SPACES).withAutoSize(true);
            ePanel.asJPanel().add(label.asSwingObject());

            label = optionalLinkLabel((merc.getCurrentSystem() != null) && !Game.getShip().hasCrew(merc.getId()), currentSystemDisplay(merc));
            systemPanel.asJPanel().add(label.asSwingObject());
        }
    }

    private Label optionalLinkLabel(boolean condition, String systemName) {
        if (condition) {
            LinkLabel linkLabel = new LinkLabel(systemName).withAutoSize(true);
            linkLabel.setLinkClicked(new SimpleEventHandler<Object>() {
                @Override
                public void handle(Object sender) {
                    systemLinkClicked(sender);
                }
            });
            return linkLabel;
        } else {
            return new Label(systemName).withAutoSize(true);
        }
    }

    private void updateQuests() {
        SimpleVPanel dummyPanel = createVPanel(10);
        SimpleVPanel systemPanel = createVPanel(90);
        SimpleVPanel descrPanel = createVPanel(160);
        SimpleVPanel statusPanel = createVPanel(90);
        questsPanel.removeAll();
        questsPanel.addAll(dummyPanel, systemPanel, descrPanel, statusPanel);

        dummyPanel.asJPanel().add(new Label(THREE_SPACES).asSwingObject());

        for (Row questSystem : questSystems) {
            Label label = optionalLinkLabel(questSystem.getSystemId() != StarSystemId.NA.castToInt(), questSystem.getSystem());
            systemPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + questSystem.getDescription()).withAutoSize(true);
            descrPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + questSystem.getFeature()).withAutoSize(true);
            statusPanel.asJPanel().add(label.asSwingObject());
        }
        questsSystemLabel.setLeft(metrics.stringWidth(THREE_SPACES) + 10);
        questsDescrLabel.setLeft(systemPanel.getWidth());
    }

    private void updateShipyards() {
        SimpleVPanel dummyPanel = createVPanel(10);
        SimpleVPanel systemPanel = createVPanel(90);
        SimpleVPanel descrPanel = createVPanel(160);
        SimpleVPanel statusPanel = createVPanel(90);
        shipyardsPanel.removeAll();
        shipyardsPanel.addAll(dummyPanel, systemPanel, descrPanel, statusPanel);

        dummyPanel.asJPanel().add(new Label(THREE_SPACES).asSwingObject());

        for (Row shipyardSystem : shipyardSystems) {
            LinkLabel linkLabel = new LinkLabel(shipyardSystem.getSystem()).withAutoSize(true);
            linkLabel.setLinkClicked(new SimpleEventHandler<Object>() {
                @Override
                public void handle(Object sender) {
                    systemLinkClicked(sender);
                }
            });
            systemPanel.asJPanel().add(linkLabel.asSwingObject());

            Label label = new Label(THREE_SPACES + shipyardSystem.getDescription()).withAutoSize(true);
            descrPanel.asJPanel().add(label.asSwingObject());

            label = new Label(THREE_SPACES + shipyardSystem.getFeature()).withAutoSize(true);
            statusPanel.asJPanel().add(label.asSwingObject());
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

    private void sortLinkClicked(Object sender, String sortBy) {
        switch (((LinkLabel) sender).getName().trim().substring(0, 1).toUpperCase()) {
            case "M":
                sortMercenaries(sortBy);
                break;
            case "Q":
                sortRows(questSystems, sortBy);
                break;
            case "S":
                sortRows(shipyardSystems, sortBy);
        }
        updateAll();
    }

    private void sortRows(List<Row> systems, String sortBy) {
        switch (sortBy) {
            case "S":
                systems.sort(Comparator.comparing(Row::getSystem));
                break;
            case "D":
                systems.sort(Comparator.comparing(Row::getDescription));
                break;
            case "F":
                systems.sort(Comparator.comparing(Row::getFeature));
                break;
        }
    }
}
