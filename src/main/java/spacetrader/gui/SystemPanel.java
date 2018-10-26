package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.awt.*;
import java.util.List;

class SystemPanel extends Panel {

    private final SpaceTrader mainWindow;
    private CurrentSystemMgr game = null;
    private GameController controller = null;
    private Commander commander;

    private Button newsButton = new Button();
    private Button specialButton = new Button();
    private Button hireMercenaryButton = new Button();
    private Label systemGovernmentLabel = new Label();
    private Label systemNameLabelValue = new Label();
    private Label systemNameLabel = new Label();
    private Label systemPiratesLabelValue = new Label();
    private Label systemPiratesLabel = new Label();
    private Label systemPoliceLabelValue = new Label();
    private Label systemPoliceLabel = new Label();
    private Label systemGovernmentLabelValue = new Label();
    private Label systemPressureLabelValue = new Label();
    private Label systemPressureLabel = new Label();
    private Label systemResourcesLabelValue = new Label();
    private Label systemResourcesLabel = new Label();
    private Label systemSizeLabelValue = new Label();
    private Label systemSizeLabel = new Label();
    private Label systemTechLevelLabelValue = new Label();
    private Label systemTechLevelLabel = new Label();

    SystemPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(CurrentSystemMgr game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    public void initializeComponent() {
        setSize(240, 206);
        setTabStop(false);
        setText("System Info");

        systemNameLabel.setFont(FontCollection.bold825);
        systemNameLabel.setLocation(8, 16);
        systemNameLabel.setSize(90, 16);
        systemNameLabel.setTabIndex(0);
        systemNameLabel.setText("Name:");

        systemNameLabelValue.setLocation(88, 16);
        systemNameLabelValue.setSize(110, 13);
        systemNameLabelValue.setTabIndex(1);
        //systemNameLabelValue.setText("Tarchannen");

        systemSizeLabel.setFont(FontCollection.bold825);
        systemSizeLabel.setLocation(8, 32);
        systemSizeLabel.setSize(90, 16);
        systemSizeLabel.setTabIndex(2);
        systemSizeLabel.setText("Size:");

        systemSizeLabelValue.setLocation(88, 32);
        systemSizeLabelValue.setSize(110, 13);
        systemSizeLabelValue.setTabIndex(14);
        //systemSizeLabelValue.setText("Medium");

        systemTechLevelLabel.setFont(FontCollection.bold825);
        systemTechLevelLabel.setLocation(8, 48);
        systemTechLevelLabel.setSize(90, 16);
        systemTechLevelLabel.setTabIndex(3);
        systemTechLevelLabel.setText("Tech Level:");

        systemTechLevelLabelValue.setLocation(88, 48);
        systemTechLevelLabelValue.setSize(110, 13);
        systemTechLevelLabelValue.setTabIndex(13);
        //systemTechLevelLabelValue.setText("Pre-Agricultural");

        systemGovernmentLabel.setFont(FontCollection.bold825);
        systemGovernmentLabel.setLocation(8, 64);
        systemGovernmentLabel.setSize(90, 16);
        systemGovernmentLabel.setTabIndex(4);
        systemGovernmentLabel.setText("Government:");

        systemGovernmentLabelValue.setLocation(88, 64);
        systemGovernmentLabelValue.setSize(110, 13);
        systemGovernmentLabelValue.setTabIndex(15);
        //systemGovernmentLabelValue.setText("Cybernetic State");

        systemResourcesLabel.setFont(FontCollection.bold825);
        systemResourcesLabel.setLocation(8, 90);
        systemResourcesLabel.setSize(90, 16);
        systemResourcesLabel.setTabIndex(5);
        systemResourcesLabel.setText("Resource:");

        systemResourcesLabelValue.setLocation(88, 80);
        systemResourcesLabelValue.setSize(110, 13);
        systemResourcesLabelValue.setTabIndex(9);
        //systemResourcesLabelValue.setText("Sweetwater Oceans");

        systemPoliceLabel.setFont(FontCollection.bold825);
        systemPoliceLabel.setLocation(8, 96);
        systemPoliceLabel.setSize(90, 16);
        systemPoliceLabel.setTabIndex(6);
        systemPoliceLabel.setText("Police:");

        systemPoliceLabelValue.setLocation(88, 96);
        systemPoliceLabelValue.setSize(110, 13);
        systemPoliceLabelValue.setTabIndex(10);
        //systemPoliceLabelValue.setText("Moderate");

        systemPiratesLabel.setFont(FontCollection.bold825);
        systemPiratesLabel.setLocation(8, 112);
        systemPiratesLabel.setSize(90, 16);
        systemPiratesLabel.setTabIndex(7);
        systemPiratesLabel.setText("Pirates:");

        systemPiratesLabelValue.setLocation(88, 112);
        systemPiratesLabelValue.setSize(110, 13);
        systemPiratesLabelValue.setTabIndex(11);
        //systemPiratesLabelValue.setText("Abundant");

        systemPressureLabel.setLocation(8, 134);
        systemPressureLabel.setSize(140, 16);
        systemPressureLabel.setTabIndex(17);
        systemPressureLabel.setText("This system is currently");

        systemPressureLabelValue.setLocation(8, 147);
        systemPressureLabelValue.setSize(180, 16);
        systemPressureLabelValue.setTabIndex(18);
        //systemPressureLabelValue.setText("suffering from extreme bordom.");

        newsButton.setFlatStyle(FlatStyle.FLAT);
        newsButton.setLocation(8, 174);
        newsButton.setSize(42, 22);
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
        specialButton.setLocation(58, 174);
        specialButton.setSize(52, 22);
        specialButton.setTabIndex(2);
        specialButton.setText("Special");
        specialButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                specialButtonClick();
            }
        });

        hireMercenaryButton.setFlatStyle(FlatStyle.FLAT);
        hireMercenaryButton.setLocation(118, 174);
        hireMercenaryButton.setSize(112, 22);
        hireMercenaryButton.setTabIndex(3);
        hireMercenaryButton.setText("Mercenary For Hire");
        hireMercenaryButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                hireMercenaryButtonClick();
            }
        });


        getControls().addAll(systemNameLabel, systemNameLabelValue, systemSizeLabel, systemSizeLabelValue,
                systemTechLevelLabel, systemTechLevelLabelValue, systemGovernmentLabel, systemGovernmentLabelValue,
                systemResourcesLabel, systemResourcesLabelValue, systemPoliceLabel, systemPoliceLabelValue,
                systemPiratesLabel, systemPiratesLabelValue, systemPressureLabel, systemPressureLabelValue,
                newsButton, specialButton, hireMercenaryButton);
    }

    void update() {
        if (game == null || commander.getCurrentSystem() == null) {
            systemNameLabelValue.setText("");
            systemSizeLabelValue.setText("");
            systemTechLevelLabelValue.setText("");
            systemGovernmentLabelValue.setText("");
            systemResourcesLabelValue.setText("");
            systemPoliceLabelValue.setText("");
            systemPiratesLabelValue.setText("");
            systemPressureLabelValue.setText("");
            systemPressureLabel.setVisible(false);
            newsButton.setVisible(false);
            hireMercenaryButton.setVisible(false);
            specialButton.setVisible(false);
        } else {
            StarSystem system = commander.getCurrentSystem();
            List<CrewMember> mercenaries = system.getMercenariesForHire();

            systemNameLabelValue.setText(system.getName());
            systemSizeLabelValue.setText(Strings.Sizes[system.getSize().castToInt()]);
            systemTechLevelLabelValue.setText(Strings.TechLevelNames[system.getTechLevel().castToInt()]);
            systemGovernmentLabelValue.setText(system.getPoliticalSystem().getName());
            systemResourcesLabelValue.setText(Strings.SpecialResources[system.getSpecialResource().castToInt()]);
            systemPoliceLabelValue.setText(Strings.ActivityLevels[system.getPoliticalSystem().getActivityPolice().castToInt()]);
            systemPiratesLabelValue.setText(Strings.ActivityLevels[system.getPoliticalSystem().getActivityPirates().castToInt()]);
            systemPressureLabelValue.setText(Strings.SystemPressures[system.getSystemPressure().castToInt()]);
            systemPressureLabel.setVisible(true);
            newsButton.setVisible(true);
            hireMercenaryButton.setVisible(!mercenaries.isEmpty());
            if (hireMercenaryButton.isVisible()) {
                setToolTip(hireMercenaryButton, Functions.stringVars(Strings.MercenariesForHire,
                        mercenaries.size() == 1 ? mercenaries.get(0).getName() : mercenaries.size() + " " + Strings.Mercenaries));
            }
            specialButton.setVisible(system.showSpecialButton());
            if (specialButton.isVisible()) {
                setToolTip(specialButton, system.specialEvent().getTitle());
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

        if (specEvent.isMessageOnly()) {
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

        FormAlert alert = new FormAlert(specEvent.getTitle(), specEvent.getString(), button1Text, button1Result,
                button2Text, button2Result, null);
        if (alert.showDialog() != DialogResult.NO) {
            if (commander.getCashToSpend() < specEvent.getPrice())
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

    private void setToolTip(Button button, String text) {
        button.asJButton().setToolTipText(text);
    }
}
