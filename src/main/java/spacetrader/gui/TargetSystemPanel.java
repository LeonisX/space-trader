package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.game.*;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.util.Functions;

import static spacetrader.game.Strings.*;

class TargetSystemPanel extends Panel {

    private final SpaceTrader mainWindow;
    
    private Game game = null;
    private GameController controller = null;
    private Commander commander;

    private Button prevSystemButton = new Button();
    private Button nextSystemButton = new Button();

    private Label targetNameLabel = new Label();
    private Label targetNameLabelValue = new Label();
    private Label targetSizeLabel = new Label();
    private Label targetSizeLabelValue = new Label();
    private Label targetTechLevelLabel = new Label();
    private Label targetTechLevelLabelValue = new Label();
    private Label targetGovernmentLabel = new Label();
    private Label targetGovernmentLabelValue = new Label();
    private Label targetResourcesLabel = new Label();
    private Label targetResourcesLabelValue = new Label();
    private Label targetPoliceLabel = new Label();
    private Label targetPoliceLabelValue = new Label();
    private Label targetPiratesLabel = new Label();
    private Label targetPiratesLabelValue = new Label();
    private Label targetDistanceLabelValue = new Label();
    private Label targetDistanceLabel = new Label();
    private Label targetOutOfRangeLabel = new Label();

    private Button trackButton = new Button();
    private Button warpButton = new Button();
    
    private Label[] labels = new Label[]{targetNameLabel, targetSizeLabel, targetTechLevelLabel,
            targetGovernmentLabel, targetResourcesLabel, targetPoliceLabel, targetPiratesLabel, targetDistanceLabel};

    private Label[] labelValues = new Label[]{targetNameLabelValue, targetSizeLabelValue, targetTechLevelLabelValue,
            targetGovernmentLabelValue, targetResourcesLabelValue, targetPoliceLabelValue, targetPiratesLabelValue,
            targetDistanceLabelValue};

    TargetSystemPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(Game game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    void initializeComponent() {
        setText("Target System");
        setSize(219, 173);
        setTabStop(false);

        prevSystemButton.setAutoWidth(true);
        prevSystemButton.setControlBinding(ControlBinding.LEFT);
        prevSystemButton.setLocation(164, 21);
        prevSystemButton.setSize(18, 18);
        prevSystemButton.setTabIndex(57);
        prevSystemButton.setText("<");
        prevSystemButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                prevSystemButtonClick();
            }
        });

        nextSystemButton.setAutoWidth(true);
        nextSystemButton.setControlBinding(ControlBinding.RIGHT);
        nextSystemButton.setLocation(190, 21);
        nextSystemButton.setSize(18, 18);
        nextSystemButton.setTabIndex(58);
        nextSystemButton.setText(">");
        nextSystemButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                nextSystemButtonClick();
            }
        });

        targetNameLabel.setLocation(8, 21);
        //targetNameLabel.setSize(39, 16);
        targetNameLabel.setText("Name:");

        targetNameLabelValue.setLocation(88, 21);
        //targetNameLabelValue.setSize(65, 13);
        //targetNameLabelValue.setText("Tarchannen");

        targetSizeLabel.setLocation(8, 37);
        //targetSizeLabel.setSize(31, 16);
        targetSizeLabel.setText("Size:");

        targetSizeLabelValue.setLocation(88, 37);
        //targetSizeLabelValue.setSize(45, 13);
        //targetSizeLabelValue.setText("Medium");

        targetTechLevelLabel.setLocation(8, 53);
        //targetTechLevelLabel.setSize(65, 16);
        targetTechLevelLabel.setText("Tech Level:");

        targetTechLevelLabelValue.setLocation(88, 53);
        //targetTechLevelLabelValue.setSize(82, 13);
        //targetTechLevelLabelValue.setText("Pre-Agricultural");

        targetGovernmentLabel.setLocation(8, 69);
        //targetGovernmentLabel.setSize(72, 16);
        targetGovernmentLabel.setText("Government:");

        targetGovernmentLabelValue.setLocation(88, 69);
        //targetGovernmentLabelValue.setSize(91, 13);
        //targetGovernmentLabelValue.setText("Communist State");

        targetResourcesLabel.setLocation(8, 85);
        //targetResourcesLabel.setSize(58, 16);
        targetResourcesLabel.setText("Resource:");

        targetResourcesLabelValue.setLocation(88, 85);
        //targetResourcesLabelValue.setSize(105, 13);
        //targetResourcesLabelValue.setText("Sweetwater Oceans");

        targetPoliceLabel.setLocation(8, 101);
        //targetPoliceLabel.setSize(40, 16);
        targetPoliceLabel.setText("Police:");

        targetPoliceLabelValue.setLocation(88, 101);
        //targetPoliceLabelValue.setSize(53, 13);
        //targetPoliceLabelValue.setText("Abundant");

        targetPiratesLabel.setLocation(8, 117);
        //targetPiratesLabel.setSize(44, 16);
        targetPiratesLabel.setText("Pirates:");

        targetPiratesLabelValue.setLocation(88, 117);
        //targetPiratesLabelValue.setSize(53, 13);
        //targetPiratesLabelValue.setText("Abundant");

        targetDistanceLabel.setLocation(8, 133);
        //targetDistanceLabel.setSize(53, 16);
        targetDistanceLabel.setText("Distance:");

        targetDistanceLabelValue.setLocation(88, 133);
        //targetDistanceLabelValue.setSize(66, 13);
        ///targetDistanceLabelValue.setText("888 parsecs");

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

        warpButton.setAutoWidth(true);
        warpButton.setControlBinding(ControlBinding.RIGHT);
        warpButton.setLocation(164, 103);
        warpButton.setSize(44, 44);
        warpButton.setTabIndex(59);
        warpButton.setText("Warp");
        warpButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                warpButtonClick();
            }
        });

        trackButton.setAutoWidth(true);
        trackButton.setControlBinding(ControlBinding.RIGHT);
        trackButton.setLocation(164, 145);
        trackButton.setSize(44, 22);
        trackButton.setTabIndex(60);
        trackButton.setText("Track");
        trackButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                trackButtonClick();
            }
        });

        targetOutOfRangeLabel.setAutoSize(true);
        targetOutOfRangeLabel.setLocation(8, 149);
        //targetOutOfRangeLabel.setSize(144, 13);
        targetOutOfRangeLabel.setText("This system is out of range.");

        getControls().addAll(prevSystemButton, nextSystemButton, warpButton, targetOutOfRangeLabel, trackButton);
        getControls().addAll(labels);
        getControls().addAll(labelValues);
    }

    private void updateAll() {
        // todo inline when done
        mainWindow.updateAll();
    }
    
    void update() {
        nextSystemButton.setVisible(game != null);
        prevSystemButton.setVisible(game != null);

        if (null == game || null == game.getWarpSystem()) {
            targetNameLabelValue.setText("");
            targetSizeLabelValue.setText("");
            targetTechLevelLabelValue.setText("");
            targetGovernmentLabelValue.setText("");
            targetResourcesLabelValue.setText("");
            targetPoliceLabelValue.setText("");
            targetPiratesLabelValue.setText("");
            targetDistanceLabelValue.setText("");
            targetOutOfRangeLabel.setVisible(false);
            warpButton.setVisible(false);
            trackButton.setVisible(false);
        } else {
            StarSystem system = game.getWarpSystem();
            int distance = Functions.distance(commander.getCurrentSystem(), system);

            targetNameLabelValue.setText(system.getName());
            targetSizeLabelValue.setText(Sizes[system.getSize().castToInt()]);
            targetTechLevelLabelValue.setText(TechLevelNames[system.getTechLevel().castToInt()]);
            targetGovernmentLabelValue.setText(system.getPoliticalSystem().getName());
            targetResourcesLabelValue.setText(system.isVisited()
                    ? SpecialResources[system.getSpecialResource().castToInt()]
                    : Unknown);
            targetPoliceLabelValue.setText(ActivityLevels[system.getPoliticalSystem().getActivityPolice().castToInt()]);
            targetPiratesLabelValue.setText(ActivityLevels[system.getPoliticalSystem().getActivityPirates().castToInt()]);
            targetDistanceLabelValue.setText(Functions.plural(distance, Strings.DistanceUnit));
            targetOutOfRangeLabel.setVisible(!system.destIsOk() && system != commander.getCurrentSystem());
            warpButton.setVisible(system.destIsOk());
            trackButton.setVisible(targetOutOfRangeLabel.isVisible() && system != game.getTrackedSystem());
        }
    }

    private void trackButtonClick() {
        game.setTrackedSystemId(game.getSelectedSystemId());
        updateAll();
    }

    private void warpButtonClick() {
        try {
            controller.autoSaveOnDeparture();
            game.setWarp(false);
            controller.autoSaveOnArrival();
        } catch (GameEndException ex) {
            controller.gameEnd();
        }
        updateAll();
    }

    private void nextSystemButtonClick() {
        game.selectNextSystemWithinRange(true);
        updateAll();
    }

    private void prevSystemButtonClick() {
        game.selectNextSystemWithinRange(false);
        updateAll();
    }
}
