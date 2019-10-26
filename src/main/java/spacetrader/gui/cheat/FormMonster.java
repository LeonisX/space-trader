package spacetrader.gui.cheat;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.enums.*;
import spacetrader.game.CrewMember;
import spacetrader.game.Game;
import spacetrader.game.StarSystem;
import spacetrader.game.Strings;
import spacetrader.game.cheat.CheatCode;
import spacetrader.game.enums.ShipyardId;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.quest.Phase;
import spacetrader.gui.FontCollection;
import spacetrader.gui.SpaceTraderForm;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.util.*;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static spacetrader.gui.cheat.FormMonster.Letters.*;

@CheatCode
public class FormMonster extends SpaceTraderForm {

    enum Letters {
        S, D, F, I, N, P, T, E
    }

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

    private MercenariesRows mercenaries;
    private Rows questSystems;
    private Rows shipyardSystems;

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
                questSystems.sort(S);
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
                questSystems.sort(D);
            }
        });

        questsFeatureLabel.setAutoSize(true);
        questsFeatureLabel.setFont(FontCollection.bold825);
        questsFeatureLabel.setLocation(220, 24);
        questsFeatureLabel.setText("State");
        questsFeatureLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                questSystems.sort(F);
            }
        });

        //questsPanel.autoScroll = true;
        questsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        //questsPanel.controls.addAll(questPanelSystemsLabel, questsPanelDescrLabel);
        questsPanel.setLocation(8, 44);
        questsPanel.setSize(322, 505);

        metrics = questsPanel.asSwingObject().getFontMetrics(questsPanel.asSwingObject().getFont());

        shipyardsLabel.setAutoSize(true);
        shipyardsLabel.setControlBinding(ControlBinding.CENTER);
        shipyardsLabel.setFont(FontCollection.bold10);
        shipyardsLabel.setLocation(170, 561);
        //shipyardsLabel.setSize(68, 19);
        shipyardsLabel.setText("Shipyards");

        shipyardsSystemLabel.setAutoSize(true);
        shipyardsSystemLabel.setFont(FontCollection.bold825);
        shipyardsSystemLabel.setLocation(23, 581);
        //shipyardsSystemLabel.setSize(43, 16);
        shipyardsSystemLabel.setText("System");
        shipyardsSystemLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                shipyardSystems.sort(S);
            }
        });

        shipyardsDescrLabel.setAutoSize(true);
        shipyardsDescrLabel.setFont(FontCollection.bold825);
        shipyardsDescrLabel.setLocation(60, 581);
        //shipyardsDescrLabel.setSize(63, 16);
        shipyardsDescrLabel.setText("Description");
        shipyardsDescrLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                shipyardSystems.sort(D);
            }
        });

        shipyardsFeatureLabel.setAutoSize(true);
        shipyardsFeatureLabel.setFont(FontCollection.bold825);
        shipyardsFeatureLabel.setLocation(190, 581);
        shipyardsFeatureLabel.setText("Specialization");
        shipyardsFeatureLabel.setLinkClicked(new SimpleEventHandler<Object>() {
            @Override
            public void handle(Object sender) {
                shipyardSystems.sort(F);
            }
        });

        shipyardHorizontalLine.setLocation(4, 597);
        shipyardHorizontalLine.setWidth(330);

        shipyardsPanel.setBorderStyle(BorderStyle.FIXED_SINGLE);
        shipyardsPanel.setLocation(8, 601);
        shipyardsPanel.setSize(322, 93);

        verticalLine.setLocation(334, 8);
        verticalLine.setHeight(680);

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
                mercenaries.sort(I);
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
                mercenaries.sort(N);
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
                mercenaries.sort(P);
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
                mercenaries.sort(F);
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
                mercenaries.sort(T);
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
                mercenaries.sort(E);
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
                mercenaries.sort(S);
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

    private void populateIdArrays() {
        // Populate the mercenary ids array.
        mercenaries = new MercenariesRows(game.getMercenaries().values().stream()
                .filter(CrewMember::isMercenary).collect(toList())
        );

        // Populate the quest and shipyard system ids arrays.
        questSystems = new Rows(game.getQuestSystem().getPhasesStream()
                .flatMap(p -> p.getStarSystemIds().stream().map(s -> createQuestRow(p, s))).collect(toList())
        );

        shipyardSystems = new Rows(Arrays.stream(game.getUniverse())
                .filter(s -> s.getShipyardId() != ShipyardId.NA)
                .map(s -> createShipyardRow(s.getId().castToInt())).collect(toList())
        );


        // Sort the arrays.
        mercenaries.sort(N); // Sort mercenaries by name.
        questSystems.sort(S); // Sort quests by system name.
        shipyardSystems.sort(S); // Sort shipyards by system name.
    }

    private Row createQuestRow(Phase phase, StarSystemId starSystemId) {
        int systemId = (null == starSystemId) ? StarSystemId.NA.castToInt() : starSystemId.castToInt();
        String systemName = (systemId < 0) ? Strings.Unknown : game.getStarSystem(systemId).getName();
        return new Row(systemId, systemName, phase.getTitle(), Strings.QuestStates[phase.getQuest().getQuestState().ordinal()]);
    }

    private Row createShipyardRow(int systemId) {
        StarSystem system = game.getStarSystem(systemId);
        return new Row(systemId, system.getName(), system.getShipyard().getName(),
                Strings.ShipyardSkills[system.getShipyard().getSkill().castToInt()]);
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

        for (CrewMember merc : mercenaries.getCrew()) {
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

            label = optionalLinkLabel((merc.getCurrentSystem() != null) && !game.getShip().hasCrew(merc.getId()), currentSystemDisplay(merc));
            systemPanel.asJPanel().add(label.asSwingObject());
        }
    }

    private static String currentSystemDisplay(CrewMember merc) {
        return (null == merc.getCurrentSystem() ? Strings.Unknown
                : (Game.getCurrentGame().getShip().hasCrew(merc.getId()) ? Functions.stringVars(Strings.MercOnBoard, merc
                .getCurrentSystem().getName()) : merc.getCurrentSystem().getName()));
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

        for (Row questSystem : questSystems.getRows()) {
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

        for (Row shipyardSystem : shipyardSystems.getRows()) {
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

    private class Rows extends HashMap<Letters, Boolean> {

        private List<Row> rows;

        Rows(List<Row> rows) {
            this.rows = rows;
        }

        void sort(Letters sortBy) {
            rows.sort(getComparator(sortBy));
            updateAll();
        }

        Comparator<? super Row> getComparator(Letters sortBy) {
            Comparator<? super Row> comparator = null;
            switch (sortBy) {
                case S:
                    comparator = Comparator.comparing(Row::getSystem);
                    break;
                case D:
                    comparator = Comparator.comparing(Row::getDescription);
                    break;
                case F:
                    comparator = Comparator.comparing(Row::getFeature);
                    break;
            }
            if (getDirection(sortBy)) {
                comparator = Collections.reverseOrder(comparator);
            }
            return comparator;
        }

        private Boolean getDirection(Letters sortBy) {
            Boolean result = (null == get(sortBy)) ? false : get(sortBy);
            put(sortBy, !result);
            return result;
        }

        List<Row> getRows() {
            return rows;
        }
    }

    private class MercenariesRows extends HashMap<Letters, Boolean> {

        private List<CrewMember> crew;

        MercenariesRows(List<CrewMember> crew) {
            this.crew = crew;
        }

        void sort(Letters sortBy) {
            crew.sort(getComparator(sortBy));
            updateAll();
        }

        Comparator<? super CrewMember> getComparator(Letters sortBy) {
            Comparator<? super CrewMember> comparator = null;
            switch (sortBy) {
                case I:
                    comparator = Comparator.comparing(CrewMember::getId);
                    break;
                case N:
                    comparator = Comparator.comparing(CrewMember::getName);
                    break;
                case P:
                    comparator = Comparator.comparing(CrewMember::getPilot);
                    break;
                case F:
                    comparator = Comparator.comparing(CrewMember::getFighter);
                    break;
                case T:
                    comparator = Comparator.comparing(CrewMember::getTrader);
                    break;
                case E:
                    comparator = Comparator.comparing(CrewMember::getEngineer);
                    break;
                case S:
                    comparator = Comparator.comparing(FormMonster::currentSystemDisplay);
                    break;
            }
            if (getDirection(sortBy)) {
                comparator = Collections.reverseOrder(comparator);
            }
            return comparator;
        }

        private Boolean getDirection(Letters sortBy) {
            Boolean result = (null == get(sortBy)) ? false : get(sortBy);
            put(sortBy, !result);
            return result;
        }

        List<CrewMember> getCrew() {
            return crew;
        }
    }
}
