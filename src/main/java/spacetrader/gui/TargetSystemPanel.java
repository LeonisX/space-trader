package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.AnchorStyles;
import spacetrader.controls.enums.FlatStyle;
import spacetrader.game.*;
import spacetrader.util.ReflectionUtils;

import static spacetrader.game.Strings.*;

class TargetSystemPanel extends Panel {

    private final SpaceTrader mainWindow;
    
    private SystemTracker game = null;
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
    private Label targetResourceLabel = new Label();
    private Label targetResourceLabelValue = new Label();
    private Label targetPoliceLabel = new Label();
    private Label targetPoliceLabelValue = new Label();
    private Label targetPiratesLabel = new Label();
    private Label targetPiratesLabelValue = new Label();
    private Label targetDistanceLabelValue = new Label();
    private Label targetDistanceLabel = new Label();
    private Label targetOutOfRangeLabel = new Label();

    private Button trackButton = new Button();
    private Button warpButton = new Button();

    TargetSystemPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(SystemTracker game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    void initializeComponent() {
        anchor = AnchorStyles.TOP_RIGHT;
        
        setName("targetSystemPanel");
        setText("Target System");
        setSize(216, 168);
        setTabStop(false);

        prevSystemButton.setFlatStyle(FlatStyle.FLAT);
        prevSystemButton.setLocation(160, 16);
        prevSystemButton.setSize(18, 18);
        prevSystemButton.setTabIndex(57);
        prevSystemButton.setText("<");
        prevSystemButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                prevSystemButtonClick();
            }
        });

        nextSystemButton.setFlatStyle(FlatStyle.FLAT);
        nextSystemButton.setLocation(186, 16);
        nextSystemButton.setSize(18, 18);
        nextSystemButton.setTabIndex(58);
        nextSystemButton.setText(">");
        nextSystemButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                nextSystemButtonClick();
            }
        });

        targetNameLabel.setAutoSize(true);
        targetNameLabel.setFont(FontCollection.bold825);
        targetNameLabel.setLocation(8, 16);
        targetNameLabel.setSize(39, 16);
        targetNameLabel.setTabIndex(0);
        targetNameLabel.setText("Name:");

        targetNameLabelValue.setLocation(88, 16);
        targetNameLabelValue.setSize(65, 13);
        targetNameLabelValue.setTabIndex(1);
        //targetNameLabelValue.setText("Tarchannen");

        targetSizeLabel.setAutoSize(true);
        targetSizeLabel.setFont(FontCollection.bold825);
        targetSizeLabel.setLocation(8, 32);
        targetSizeLabel.setSize(31, 16);
        targetSizeLabel.setTabIndex(2);
        targetSizeLabel.setText("Size:");

        targetSizeLabelValue.setLocation(88, 32);
        targetSizeLabelValue.setSize(45, 13);
        targetSizeLabelValue.setTabIndex(14);
        //targetSizeLabelValue.setText("Medium");

        targetTechLevelLabel.setAutoSize(true);
        targetTechLevelLabel.setFont(FontCollection.bold825);
        targetTechLevelLabel.setLocation(8, 48);
        targetTechLevelLabel.setSize(65, 16);
        targetTechLevelLabel.setTabIndex(3);
        targetTechLevelLabel.setText("Tech Level:");

        targetTechLevelLabelValue.setLocation(88, 48);
        targetTechLevelLabelValue.setSize(82, 13);
        targetTechLevelLabelValue.setTabIndex(13);
        //targetTechLevelLabelValue.setText("Pre-Agricultural");

        targetGovernmentLabel.setAutoSize(true);
        targetGovernmentLabel.setFont(FontCollection.bold825);
        targetGovernmentLabel.setLocation(8, 64);
        targetGovernmentLabel.setSize(72, 16);
        targetGovernmentLabel.setTabIndex(4);
        targetGovernmentLabel.setText("Government:");

        targetGovernmentLabelValue.setLocation(88, 64);
        targetGovernmentLabelValue.setSize(91, 13);
        targetGovernmentLabelValue.setTabIndex(15);
        //targetGovernmentLabelValue.setText("Communist State");

        targetResourceLabel.setAutoSize(true);
        targetResourceLabel.setFont(FontCollection.bold825);
        targetResourceLabel.setLocation(8, 80);
        targetResourceLabel.setSize(58, 16);
        targetResourceLabel.setTabIndex(5);
        targetResourceLabel.setText("Resource:");

        targetResourceLabelValue.setLocation(88, 80);
        targetResourceLabelValue.setSize(105, 13);
        targetResourceLabelValue.setTabIndex(9);
        //targetResourceLabelValue.setText("Sweetwater Oceans");

        targetPoliceLabel.setAutoSize(true);
        targetPoliceLabel.setFont(FontCollection.bold825);
        targetPoliceLabel.setLocation(8, 96);
        targetPoliceLabel.setSize(40, 16);
        targetPoliceLabel.setTabIndex(6);
        targetPoliceLabel.setText("Police:");

        targetPoliceLabelValue.setLocation(88, 96);
        targetPoliceLabelValue.setSize(53, 13);
        targetPoliceLabelValue.setTabIndex(10);
        //targetPoliceLabelValue.setText("Abundant");

        targetPiratesLabel.setAutoSize(true);
        targetPiratesLabel.setFont(FontCollection.bold825);
        targetPiratesLabel.setLocation(8, 112);
        targetPiratesLabel.setSize(44, 16);
        targetPiratesLabel.setTabIndex(7);
        targetPiratesLabel.setText("Pirates:");

        targetPiratesLabelValue.setLocation(88, 112);
        targetPiratesLabelValue.setSize(53, 13);
        targetPiratesLabelValue.setTabIndex(11);
        //targetPiratesLabelValue.setText("Abundant");

        targetDistanceLabel.setAutoSize(true);
        targetDistanceLabel.setFont(FontCollection.bold825);
        targetDistanceLabel.setLocation(8, 128);
        targetDistanceLabel.setSize(53, 16);
        targetDistanceLabel.setTabIndex(8);
        targetDistanceLabel.setText("Distance:");

        targetDistanceLabelValue.setLocation(88, 128);
        targetDistanceLabelValue.setSize(66, 13);
        targetDistanceLabelValue.setTabIndex(12);
        ///targetDistanceLabelValue.setText("888 parsecs");

        trackButton.setFlatStyle(FlatStyle.FLAT);
        trackButton.setLocation(160, 140);
        trackButton.setSize(44, 22);
        trackButton.setTabIndex(60);
        trackButton.setText("Track");
        trackButton.setVisible(false);
        trackButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                trackButtonClick();
            }
        });

        targetOutOfRangeLabel.setLocation(8, 144);
        targetOutOfRangeLabel.setSize(144, 13);
        targetOutOfRangeLabel.setTabIndex(17);
        targetOutOfRangeLabel.setText("This system is out of range.");

        warpButton.setFlatStyle(FlatStyle.FLAT);
        warpButton.setLocation(160, 98);
        warpButton.setSize(44, 44);
        warpButton.setTabIndex(59);
        warpButton.setText("Warp");
        warpButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                warpButtonClick();
            }
        });
        
        getControls().addAll(trackButton, nextSystemButton, prevSystemButton, targetOutOfRangeLabel, warpButton,
                targetGovernmentLabelValue, targetSizeLabelValue, targetTechLevelLabelValue, targetDistanceLabelValue,
                targetPiratesLabelValue, targetPoliceLabelValue, targetResourceLabelValue, targetDistanceLabel,
                targetPiratesLabel, targetPoliceLabel, targetResourceLabel, targetGovernmentLabel, targetTechLevelLabel,
                targetSizeLabel, targetNameLabelValue, targetNameLabel);
        
        ReflectionUtils.loadControlsData(this);
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
            targetResourceLabelValue.setText("");
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
            targetResourceLabelValue.setText(system.isVisited()
                    ? SpecialResources[system.getSpecialResource().castToInt()]
                    : Unknown);
            targetPoliceLabelValue.setText(ActivityLevels[system.getPoliticalSystem().getActivityPolice().castToInt()]);
            targetPiratesLabelValue.setText(ActivityLevels[system.getPoliticalSystem().getActivityPirates().castToInt()]);
            targetDistanceLabelValue.setText(distance);
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
