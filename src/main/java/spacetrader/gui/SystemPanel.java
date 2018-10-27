package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
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

    private Label[] labels = new Label[]{systemNameLabel, systemSizeLabel, systemTechLevelLabel,
            systemGovernmentLabel, systemResourcesLabel, systemPoliceLabel, systemPiratesLabel};

    private Label[] labelValues = new Label[]{systemNameLabelValue, systemSizeLabelValue, systemTechLevelLabelValue,
            systemGovernmentLabelValue, systemResourcesLabelValue, systemPoliceLabelValue, systemPiratesLabelValue,
            systemPressureLabel, systemPressureLabelValue};

    SystemPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(CurrentSystemMgr game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    public void initializeComponent() {
        setSize(250, 206);
        setTabStop(false);
        setText("System Info");

        systemNameLabel.setLocation(8, 18);
        systemNameLabel.setText("Name:");

        systemNameLabelValue.setLocation(98, 18);
        //systemNameLabelValue.setText("Tarchannen");

        systemSizeLabel.setLocation(8, 34);
        systemSizeLabel.setText("Size:");

        systemSizeLabelValue.setLocation(98, 34);
        //systemSizeLabelValue.setText("Medium");

        systemTechLevelLabel.setLocation(8, 50);
        systemTechLevelLabel.setText("Tech Level:");

        systemTechLevelLabelValue.setLocation(98, 50);
        //systemTechLevelLabelValue.setText("Pre-Agricultural");

        systemGovernmentLabel.setLocation(8, 68);
        systemGovernmentLabel.setText("Government:");

        systemGovernmentLabelValue.setLocation(98, 68);
        //systemGovernmentLabelValue.setText("Cybernetic State");

        systemResourcesLabel.setLocation(8, 82);
        systemResourcesLabel.setText("Resource:");

        systemResourcesLabelValue.setLocation(98, 82);
        //systemResourcesLabelValue.setText("Sweetwater Oceans");

        systemPoliceLabel.setLocation(8, 98);
        systemPoliceLabel.setText("Police:");

        systemPoliceLabelValue.setLocation(98, 98);
        //systemPoliceLabelValue.setText("Moderate");

        systemPiratesLabel.setLocation(8, 114);
        systemPiratesLabel.setText("Pirates:");

        systemPiratesLabelValue.setLocation(98, 114);
        //systemPiratesLabelValue.setText("Abundant");

        systemPressureLabel.setLocation(8, 136);
        systemPressureLabel.setText("This system is currently");

        systemPressureLabelValue.setLocation(8, 149);
        //systemPressureLabelValue.setText("suffering from extreme bordom.");

        for (Label label : labels) {
            label.setFont(FontCollection.bold825);
            label.setAutoSize(true);
            //labels[i].setTabIndex(i * 2);
            // Fix different fonts sizes
            label.setTop(label.getTop() + 1); // TODO
        }

        for (Label labelValue : labelValues) {
            labelValue.setFont(FontCollection.regular825);
            labelValue.setAutoSize(true);
            //labelValues[i].setTabIndex(i * 2 + 1);
        }

        newsButton.setAutoWidth(true);
        newsButton.setControlBinding(ControlBinding.LEFT);
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
        specialButton.setAutoWidth(true);
        specialButton.setControlBinding(ControlBinding.CENTER);
        specialButton.setLocation(70, 174);
        specialButton.setSize(52, 22);
        specialButton.setTabIndex(2);
        specialButton.setText("Special");
        specialButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                specialButtonClick();
            }
        });

        hireMercenaryButton.setAutoWidth(true);
        hireMercenaryButton.setControlBinding(ControlBinding.RIGHT);
        hireMercenaryButton.setLocation(130, 174);
        hireMercenaryButton.setSize(112, 22);
        hireMercenaryButton.setTabIndex(3);
        hireMercenaryButton.setText("Mercenary For Hire");
        hireMercenaryButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                hireMercenaryButtonClick();
            }
        });

        getControls().addAll(labels);
        getControls().addAll(labelValues);
        getControls().addAll(newsButton, specialButton, hireMercenaryButton);
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
