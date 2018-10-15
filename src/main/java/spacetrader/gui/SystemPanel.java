package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.enums.AlertType;
import spacetrader.game.*;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;

class SystemPanel extends Panel {

    private final SpaceTrader mainWindow;
    private CurrentSystemMgr game = null;
    private GameController controller = null;
    private Commander commander;

    private Button newsButton;
    private Button specialButton;
    private Button hireMercenaryButton;
    private Label systemGovermentLabel;
    private Label systemNameLabelValue;
    private Label systemNameLabel;
    private Label systemPiratesLabelValue;
    private Label systemPiratesLabel;
    private Label systemPoliceLabelValue;
    private Label systemPoliceLabel;
    private Label systemGovermentLabelValue;
    private Label systemPressureLabelValue;
    private Label systemPressureLabel;
    private Label systemResoursesLabelValue;
    private Label systemResoursesLabel;
    private Label systemSizeLabelValue;
    private Label systemSizeLabel;
    private Label systemTechLevelLabelValue;
    private Label systemTechLevelLabel;
    private ToolTip tipSpecial;
    private ToolTip tipMerc;

    SystemPanel(SpaceTrader mainWindow, String name) {
        this.mainWindow = mainWindow;
        setName(name);
    }

    void setGame(CurrentSystemMgr game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    public void initializeComponent() {
        hireMercenaryButton = new Button("hireMercenaryButton");
        specialButton = new Button("specialButton");
        newsButton = new Button("newsButton");

        systemNameLabel = new Label("systemNameLabel");
        systemNameLabelValue = new Label("systemNameLabelValue");
        systemSizeLabel = new Label("systemSizeLabel");
        systemSizeLabelValue = new Label("systemSizeLabelValue");
        systemTechLevelLabel = new Label("systemTechLevelLabel");
        systemTechLevelLabelValue = new Label("systemTechLevelLabelValue");
        systemGovermentLabel = new Label("systemGovermentLabel");
        systemGovermentLabelValue = new Label("systemGovermentLabelValue");
        systemResoursesLabel = new Label("systemResoursesLabel");
        systemResoursesLabelValue = new Label("systemResoursesLabelValue");
        systemPoliceLabel = new Label("systemPoliceLabel");
        systemPoliceLabelValue = new Label("systemPoliceLabelValue");
        systemPiratesLabel = new Label("systemPiratesLabel");
        systemPiratesLabelValue = new Label("systemPiratesLabelValue");
        systemPressureLabel = new Label("systemPressureLabel");
        systemPressureLabelValue = new Label("systemPressureLabelValue");

        controls.add(hireMercenaryButton);
        controls.add(specialButton);
        controls.add(newsButton);
        controls.add(systemPressureLabelValue);
        controls.add(systemPressureLabel);
        controls.add(systemGovermentLabelValue);
        controls.add(systemSizeLabelValue);
        controls.add(systemTechLevelLabelValue);
        controls.add(systemPiratesLabelValue);
        controls.add(systemPoliceLabelValue);
        controls.add(systemResoursesLabelValue);
        controls.add(systemPiratesLabel);
        controls.add(systemPoliceLabel);
        controls.add(systemResoursesLabel);
        controls.add(systemGovermentLabel);
        controls.add(systemTechLevelLabel);
        controls.add(systemSizeLabel);
        controls.add(systemNameLabelValue);
        controls.add(systemNameLabel);

        setSize(new Size(240, 206));
        setTabIndex(1);
        setTabStop(false);
        setText("System Info");

        systemNameLabel.setAutoSize(true);
        systemNameLabel.setFont(FontCollection.bold825);
        systemNameLabel.setLocation(new Point(8, 16));
        systemNameLabel.setSize(new Size(39, 16));
        systemNameLabel.setTabIndex(0);
        systemNameLabel.setText("Name:");

        systemNameLabelValue.setLocation(new Point(88, 16));
        systemNameLabelValue.setSize(new Size(65, 13));
        systemNameLabelValue.setTabIndex(1);
        //systemNameLabelValue.setText("Tarchannen");

        systemSizeLabel.setAutoSize(true);
        systemSizeLabel.setFont(FontCollection.bold825);
        systemSizeLabel.setLocation(new Point(8, 32));
        systemSizeLabel.setSize(new Size(31, 16));
        systemSizeLabel.setTabIndex(2);
        systemSizeLabel.setText("Size:");

        systemSizeLabelValue.setLocation(new Point(88, 32));
        systemSizeLabelValue.setSize(new Size(45, 13));
        systemSizeLabelValue.setTabIndex(14);
        //systemSizeLabelValue.setText("Medium");

        systemTechLevelLabel.setAutoSize(true);
        systemTechLevelLabel.setFont(FontCollection.bold825);
        systemTechLevelLabel.setLocation(new Point(8, 48));
        systemTechLevelLabel.setSize(new Size(65, 16));
        systemTechLevelLabel.setTabIndex(3);
        systemTechLevelLabel.setText("Tech Level:");

        systemTechLevelLabelValue.setLocation(new Point(88, 48));
        systemTechLevelLabelValue.setSize(new Size(82, 13));
        systemTechLevelLabelValue.setTabIndex(13);
        //systemTechLevelLabelValue.setText("Pre-Agricultural");

        systemGovermentLabel.setAutoSize(true);
        systemGovermentLabel.setFont(FontCollection.bold825);
        systemGovermentLabel.setLocation(new Point(8, 64));
        systemGovermentLabel.setSize(new Size(72, 16));
        systemGovermentLabel.setTabIndex(4);
        systemGovermentLabel.setText("Government:");

        systemGovermentLabelValue.setLocation(new Point(88, 64));
        systemGovermentLabelValue.setSize(new Size(91, 13));
        systemGovermentLabelValue.setTabIndex(15);
        //systemGovermentLabelValue.setText("Cybernetic State");

        systemResoursesLabel.setAutoSize(true);
        systemResoursesLabel.setFont(FontCollection.bold825);
        systemResoursesLabel.setLocation(new Point(8, 80));
        systemResoursesLabel.setSize(new Size(58, 16));
        systemResoursesLabel.setTabIndex(5);
        systemResoursesLabel.setText("Resource:");

        systemResoursesLabelValue.setLocation(new Point(88, 80));
        systemResoursesLabelValue.setSize(new Size(105, 13));
        systemResoursesLabelValue.setTabIndex(9);
        //systemResoursesLabelValue.setText("Sweetwater Oceans");

        systemPoliceLabel.setAutoSize(true);
        systemPoliceLabel.setFont(FontCollection.bold825);
        systemPoliceLabel.setLocation(new Point(8, 96));
        systemPoliceLabel.setSize(new Size(40, 16));
        systemPoliceLabel.setTabIndex(6);
        systemPoliceLabel.setText("Police:");

        systemPoliceLabelValue.setLocation(new Point(88, 96));
        systemPoliceLabelValue.setSize(new Size(53, 13));
        systemPoliceLabelValue.setTabIndex(10);
        //systemPoliceLabelValue.setText("Moderate");

        systemPiratesLabel.setAutoSize(true);
        systemPiratesLabel.setFont(FontCollection.bold825);
        systemPiratesLabel.setLocation(new Point(8, 112));
        systemPiratesLabel.setSize(new Size(44, 16));
        systemPiratesLabel.setTabIndex(7);
        systemPiratesLabel.setText("Pirates:");

        systemPiratesLabelValue.setLocation(new Point(88, 112));
        systemPiratesLabelValue.setSize(new Size(53, 13));
        systemPiratesLabelValue.setTabIndex(11);
        //systemPiratesLabelValue.setText("Abundant");


        systemPressureLabel.setAutoSize(true);
        systemPressureLabel.setLocation(new Point(8, 134));
        systemPressureLabel.setSize(new Size(122, 16));
        systemPressureLabel.setTabIndex(17);
        systemPressureLabel.setText("This system is currently");

        systemPressureLabelValue.setLocation(new Point(8, 147));
        systemPressureLabelValue.setSize(new Size(168, 16));
        systemPressureLabelValue.setTabIndex(18);
        //systemPressureLabelValue.setText("suffering from extreme bordom.");


        newsButton.setFlatStyle(FlatStyle.FLAT);
        newsButton.setLocation(new Point(8, 174));
        newsButton.setSize(new Size(42, 22));
        newsButton.setTabIndex(1);
        newsButton.setText("News");
        newsButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                newsButtonClick();
            }
        });

        specialButton.setBackground(new Color(255, 255, 128));
        specialButton.setFlatStyle(FlatStyle.FLAT);
        specialButton.setLocation(new Point(58, 174));
        specialButton.setSize(new Size(52, 22));
        specialButton.setTabIndex(2);
        specialButton.setText("Special");
        specialButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                specialButtonClick();
            }
        });

        hireMercenaryButton.setFlatStyle(FlatStyle.FLAT);
        hireMercenaryButton.setLocation(new Point(118, 174));
        hireMercenaryButton.setSize(new Size(112, 22));
        hireMercenaryButton.setTabIndex(3);
        hireMercenaryButton.setText("Mercenary For Hire");
        hireMercenaryButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                hireMercenaryButtonClick();
            }
        });

        //TODO what is it???
        tipSpecial = new ToolTip();
        tipMerc = new ToolTip();
    }

    void update() {
        if (game == null || commander.getCurrentSystem() == null) {
            systemNameLabelValue.setText("");
            systemSizeLabelValue.setText("");
            systemTechLevelLabelValue.setText("");
            systemGovermentLabelValue.setText("");
            systemResoursesLabelValue.setText("");
            systemPoliceLabelValue.setText("");
            systemPiratesLabelValue.setText("");
            systemPressureLabelValue.setText("");
            systemPressureLabel.setVisible(false);
            newsButton.setVisible(false);
            hireMercenaryButton.setVisible(false);
            specialButton.setVisible(false);
        } else {
            StarSystem system = commander.getCurrentSystem();
            CrewMember[] mercenaries = system.mercenariesForHire();

            systemNameLabelValue.setText(system.name());
            systemSizeLabelValue.setText(Strings.Sizes[system.size().castToInt()]);
            systemTechLevelLabelValue.setText(Strings.TechLevelNames[system.techLevel().castToInt()]);
            systemGovermentLabelValue.setText(system.politicalSystem().name());
            systemResoursesLabelValue.setText(Strings.SpecialResources[system.specialResource().castToInt()]);
            systemPoliceLabelValue.setText(Strings.ActivityLevels[system.politicalSystem().activityPolice().castToInt()]);
            systemPiratesLabelValue.setText(Strings.ActivityLevels[system.politicalSystem().activityPirates().castToInt()]);
            systemPressureLabelValue.setText(Strings.SystemPressures[system.systemPressure().castToInt()]);
            systemPressureLabel.setVisible(true);
            newsButton.setVisible(true);
            hireMercenaryButton.setVisible(mercenaries.length > 0);
            if (hireMercenaryButton.getVisible()) {
                tipMerc.setToolTip(hireMercenaryButton, Functions.stringVars(Strings.MercenariesForHire,
                        mercenaries.length == 1 ? mercenaries[0].Name() : mercenaries.length + Strings.Mercenaries));
            }
            specialButton.setVisible(system.showSpecialButton());
            if (specialButton.getVisible()) {
                tipSpecial.setToolTip(specialButton, system.specialEvent().title());
            }
        }
    }

    private void hireMercenaryButtonClick() {
        (new FormViewPersonnel()).showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void newsButtonClick() {
        game.showNewspaper();
    }

    private void specialButtonClick() {
        SpecialEvent specEvent = commander.getCurrentSystem().specialEvent();
        String button1Text, button2Text;
        DialogResult button1Result, button2Result;

        if (specEvent.MessageOnly()) {
            button1Text = "Ok";
            button2Text = null;
            button1Result = DialogResult.OK;
            button2Result = DialogResult.NONE;
        } else {
            button1Text = "Yes";
            button2Text = "No";
            button1Result = DialogResult.YES;
            button2Result = DialogResult.NO;
        }

        FormAlert alert = new FormAlert(specEvent.title(), specEvent.string(), button1Text, button1Result,
                button2Text, button2Result, null);
        if (alert.showDialog() != DialogResult.NO) {
            if (commander.cashToSpend() < specEvent.price())
                GuiFacade.alert(AlertType.SpecialIF);
            else {
                try {
                    game.handleSpecialEvent();
                } catch (GameEndException ex) {
                    controller.gameEnd();
                }
            }
        }

        mainWindow.updateAll();
    }
}
