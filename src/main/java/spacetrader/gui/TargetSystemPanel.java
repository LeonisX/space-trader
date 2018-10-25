package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.*;

import java.awt.*;

import static spacetrader.game.Strings.*;

class TargetSystemPanel extends Panel {

    private final SpaceTrader mainWindow;
    
    private SystemTracker game = null;
    private GameController controller = null;
    private Commander commander;

    private Button prevSystemButton;
    private Button nextSystemButton;

    private Label targetNameLabel;
    private Label targetNameLabelValue;
    private Label targetSizeLabel;
    private Label targetSizeLabelValue;
    private Label targetTechLevelLabel;
    private Label targetTechLevelLabelValue;
    private Label targetGovernmentLabel;
    private Label targetGovernmentLabelValue;
    private Label targetResourceLabel;
    private Label targetResourceLabelValue;
    private Label targetPoliceLabel;
    private Label targetPoliceLabelValue;
    private Label targetPiratesLabel;
    private Label targetPiratesLabelValue;
    private Label targetDistanceLabelValue;
    private Label targetDistanceLabel;
    private Label targetOutOfRangeLabel;

    private Button trackButton;
    private Button warpButton;

    TargetSystemPanel(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(SystemTracker game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    void initializeComponent() {
        trackButton = new Button();
        nextSystemButton = new Button();
        prevSystemButton = new Button();
        targetOutOfRangeLabel = new Label();
        warpButton = new Button();
        targetGovernmentLabelValue = new Label();
        targetSizeLabelValue = new Label();
        targetTechLevelLabelValue = new Label();
        targetDistanceLabelValue = new Label();
        targetPiratesLabelValue = new Label();
        targetPoliceLabelValue = new Label();
        targetResourceLabelValue = new Label();
        targetDistanceLabel = new Label();
        targetPiratesLabel = new Label();
        targetPoliceLabel = new Label();
        targetResourceLabel = new Label();
        targetGovernmentLabel = new Label();
        targetTechLevelLabel = new Label();
        targetSizeLabel = new Label();
        targetNameLabelValue = new Label();
        targetNameLabel = new Label();

        anchor = AnchorStyles.TOP_RIGHT;

        getControls().add(trackButton);
        getControls().add(nextSystemButton);
        getControls().add(prevSystemButton);
        getControls().add(targetOutOfRangeLabel);
        getControls().add(warpButton);
        getControls().add(targetGovernmentLabelValue);
        getControls().add(targetSizeLabelValue);
        getControls().add(targetTechLevelLabelValue);
        getControls().add(targetDistanceLabelValue);
        getControls().add(targetPiratesLabelValue);
        getControls().add(targetPoliceLabelValue);
        getControls().add(targetResourceLabelValue);
        getControls().add(targetDistanceLabel);
        getControls().add(targetPiratesLabel);
        getControls().add(targetPoliceLabel);
        getControls().add(targetResourceLabel);
        getControls().add(targetGovernmentLabel);
        getControls().add(targetTechLevelLabel);
        getControls().add(targetSizeLabel);
        getControls().add(targetNameLabelValue);
        getControls().add(targetNameLabel);
        
        setSize(new Size(216, 168));
        setTabIndex(7);
        setTabStop(false);
        setText("Target System");

        prevSystemButton.setFlatStyle(FlatStyle.FLAT);
        prevSystemButton.setLocation(new Point(160, 16));
        prevSystemButton.setSize(new Size(18, 18));
        prevSystemButton.setTabIndex(57);
        prevSystemButton.setText("<");
        prevSystemButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                prevSystemButtonClick();
            }
        });

        nextSystemButton.setFlatStyle(FlatStyle.FLAT);
        nextSystemButton.setLocation(new Point(186, 16));
        nextSystemButton.setSize(new Size(18, 18));
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
        targetNameLabel.setLocation(new Point(8, 16));
        targetNameLabel.setSize(new Size(39, 16));
        targetNameLabel.setTabIndex(0);
        targetNameLabel.setText("Name:");

        targetNameLabelValue.setLocation(new Point(88, 16));
        targetNameLabelValue.setSize(new Size(65, 13));
        targetNameLabelValue.setTabIndex(1);
        //targetNameLabelValue.setText("Tarchannen");

        targetSizeLabel.setAutoSize(true);
        targetSizeLabel.setFont(FontCollection.bold825);
        targetSizeLabel.setLocation(new Point(8, 32));
        targetSizeLabel.setSize(new Size(31, 16));
        targetSizeLabel.setTabIndex(2);
        targetSizeLabel.setText("Size:");

        targetSizeLabelValue.setLocation(new Point(88, 32));
        targetSizeLabelValue.setSize(new Size(45, 13));
        targetSizeLabelValue.setTabIndex(14);
        //targetSizeLabelValue.setText("Medium");

        targetTechLevelLabel.setAutoSize(true);
        targetTechLevelLabel.setFont(FontCollection.bold825);
        targetTechLevelLabel.setLocation(new Point(8, 48));
        targetTechLevelLabel.setSize(new Size(65, 16));
        targetTechLevelLabel.setTabIndex(3);
        targetTechLevelLabel.setText("Tech Level:");

        targetTechLevelLabelValue.setLocation(new Point(88, 48));
        targetTechLevelLabelValue.setSize(new Size(82, 13));
        targetTechLevelLabelValue.setTabIndex(13);
        //targetTechLevelLabelValue.setText("Pre-Agricultural");

        targetGovernmentLabel.setAutoSize(true);
        targetGovernmentLabel.setFont(FontCollection.bold825);
        targetGovernmentLabel.setLocation(new Point(8, 64));
        targetGovernmentLabel.setSize(new Size(72, 16));
        targetGovernmentLabel.setTabIndex(4);
        targetGovernmentLabel.setText("Government:");

        targetGovernmentLabelValue.setLocation(new Point(88, 64));
        targetGovernmentLabelValue.setSize(new Size(91, 13));
        targetGovernmentLabelValue.setTabIndex(15);
        //targetGovernmentLabelValue.setText("Communist State");

        targetResourceLabel.setAutoSize(true);
        targetResourceLabel.setFont(FontCollection.bold825);
        targetResourceLabel.setLocation(new Point(8, 80));
        targetResourceLabel.setSize(new Size(58, 16));
        targetResourceLabel.setTabIndex(5);
        targetResourceLabel.setText("Resource:");

        targetResourceLabelValue.setLocation(new Point(88, 80));
        targetResourceLabelValue.setSize(new Size(105, 13));
        targetResourceLabelValue.setTabIndex(9);
        //targetResourceLabelValue.setText("Sweetwater Oceans");

        targetPoliceLabel.setAutoSize(true);
        targetPoliceLabel.setFont(FontCollection.bold825);
        targetPoliceLabel.setLocation(new Point(8, 96));
        targetPoliceLabel.setSize(new Size(40, 16));
        targetPoliceLabel.setTabIndex(6);
        targetPoliceLabel.setText("Police:");

        targetPoliceLabelValue.setLocation(new Point(88, 96));
        targetPoliceLabelValue.setSize(new Size(53, 13));
        targetPoliceLabelValue.setTabIndex(10);
        //targetPoliceLabelValue.setText("Abundant");

        targetPiratesLabel.setAutoSize(true);
        targetPiratesLabel.setFont(FontCollection.bold825);
        targetPiratesLabel.setLocation(new Point(8, 112));
        targetPiratesLabel.setSize(new Size(44, 16));
        targetPiratesLabel.setTabIndex(7);
        targetPiratesLabel.setText("Pirates:");

        targetPiratesLabelValue.setLocation(new Point(88, 112));
        targetPiratesLabelValue.setSize(new Size(53, 13));
        targetPiratesLabelValue.setTabIndex(11);
        //targetPiratesLabelValue.setText("Abundant");

        targetDistanceLabel.setAutoSize(true);
        targetDistanceLabel.setFont(FontCollection.bold825);
        targetDistanceLabel.setLocation(new Point(8, 128));
        targetDistanceLabel.setSize(new Size(53, 16));
        targetDistanceLabel.setTabIndex(8);
        targetDistanceLabel.setText("Distance:");

        targetDistanceLabelValue.setLocation(new Point(88, 128));
        targetDistanceLabelValue.setSize(new Size(66, 13));
        targetDistanceLabelValue.setTabIndex(12);
        ///targetDistanceLabelValue.setText("888 parsecs");

        trackButton.setFlatStyle(FlatStyle.FLAT);
        trackButton.setLocation(new Point(160, 140));
        trackButton.setSize(new Size(44, 22));
        trackButton.setTabIndex(60);
        trackButton.setText("Track");
        trackButton.setVisible(false);
        trackButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                trackButtonClick();
            }
        });

        targetOutOfRangeLabel.setLocation(new Point(8, 144));
        targetOutOfRangeLabel.setSize(new Size(144, 13));
        targetOutOfRangeLabel.setTabIndex(17);
        targetOutOfRangeLabel.setText("This system is out of range.");

        warpButton.setFlatStyle(FlatStyle.FLAT);
        warpButton.setLocation(new Point(160, 98));
        warpButton.setSize(new Size(44, 44));
        warpButton.setTabIndex(59);
        warpButton.setText("Warp");
        warpButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                warpButtonClick();
            }
        });
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
            targetSizeLabelValue.setText(Sizes[system.size().castToInt()]);
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
