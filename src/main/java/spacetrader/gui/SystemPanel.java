package spacetrader.gui;

import spacetrader.controls.Button;
import spacetrader.controls.*;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.game.*;
import spacetrader.game.quest.enums.EventName;
import spacetrader.util.Functions;

import java.awt.*;
import java.util.List;

class SystemPanel extends Panel {

    private final SpaceTrader mainWindow;
    private Game game = null;
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

    void setGame(Game game, Commander commander) {
        this.game = game;
        this.commander = commander;
    }

    public void initializeComponent() {
        setSize(250, 211);
        setTabStop(false);
        setText("System Info");

        systemNameLabel.setLocation(8, 23);
        systemNameLabel.setText("Name:");

        systemNameLabelValue.setLocation(98, 23);
        //systemNameLabelValue.setText("Tarchannen");

        systemSizeLabel.setLocation(8, 39);
        systemSizeLabel.setText("Size:");

        systemSizeLabelValue.setLocation(98, 39);
        //systemSizeLabelValue.setText("Medium");

        systemTechLevelLabel.setLocation(8, 55);
        systemTechLevelLabel.setText("Tech Level:");

        systemTechLevelLabelValue.setLocation(98, 55);
        //systemTechLevelLabelValue.setText("Pre-Agricultural");

        systemGovernmentLabel.setLocation(8, 71);
        systemGovernmentLabel.setText("Government:");

        systemGovernmentLabelValue.setLocation(98, 71);
        //systemGovernmentLabelValue.setText("Cybernetic State");

        systemResourcesLabel.setLocation(8, 87);
        systemResourcesLabel.setText("Resource:");

        systemResourcesLabelValue.setLocation(98, 87);
        //systemResourcesLabelValue.setText("Sweetwater Oceans");

        systemPoliceLabel.setLocation(8, 103);
        systemPoliceLabel.setText("Police:");

        systemPoliceLabelValue.setLocation(98, 103);
        //systemPoliceLabelValue.setText("Moderate");

        systemPiratesLabel.setLocation(8, 119);
        systemPiratesLabel.setText("Pirates:");

        systemPiratesLabelValue.setLocation(98, 119);
        //systemPiratesLabelValue.setText("Abundant");

        systemPressureLabel.setLocation(8, 141);
        systemPressureLabel.setText("This system is currently");

        systemPressureLabelValue.setLocation(8, 154);
        //systemPressureLabelValue.setText("suffering from extreme bordom.");

        for (Label label : labels) {
            label.setFont(FontCollection.bold825);
            label.setAutoSize(true);
            // Fix different fonts sizes
            label.setTop(label.getTop() + 1);
        }

        for (Label labelValue : labelValues) {
            labelValue.setFont(FontCollection.regular825);
            labelValue.setAutoSize(true);
        }

        newsButton.setAutoWidth(true);
        newsButton.setControlBinding(ControlBinding.LEFT);
        newsButton.setLocation(8, 179);
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
        specialButton.setLocation(70, 179);
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
        hireMercenaryButton.setLocation(130, 179);
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

            game.getQuestSystem().fireEvent(EventName.ON_BEFORE_SPECIAL_BUTTON_SHOW, specialButton);
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
        if (game != null) {
            game.getQuestSystem().fireEvent(EventName.ON_SPECIAL_BUTTON_CLICKED, specialButton);
        }
        mainWindow.updateAll();
    }

    private void setToolTip(Button button, String text) {
        button.asJButton().setToolTipText(text);
    }
}
