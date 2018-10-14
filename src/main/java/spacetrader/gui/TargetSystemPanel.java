package spacetrader.gui;

import spacetrader.controls.AnchorStyles;
import spacetrader.controls.EventArgs;
import spacetrader.controls.EventHandler;
import spacetrader.controls.FlatStyle;
import spacetrader.controls.Panel;
import spacetrader.game.*;

import java.awt.*;

public class TargetSystemPanel extends Panel {

    private final SpaceTrader mainWindow;
    private SystemTracker game = null;
    private GameController controller = null;
    private Commander commander;
    private spacetrader.controls.Button btnTrack;
    private spacetrader.controls.Button btnWarp;
    private spacetrader.controls.Button btnPrevSystem;
    private spacetrader.controls.Button btnNextSystem;
    private spacetrader.controls.Label lblTargetDistance;
    private spacetrader.controls.Label lblTargetDistanceLabel;
    private spacetrader.controls.Label lblTargetGovtLabel;
    private spacetrader.controls.Label lblTargetName;
    private spacetrader.controls.Label lblTargetNameLabel;
    private spacetrader.controls.Label lblTargetOutOfRange;
    private spacetrader.controls.Label lblTargetPirates;
    private spacetrader.controls.Label lblTargetPiratesLabel;
    private spacetrader.controls.Label lblTargetPolice;
    private spacetrader.controls.Label lblTargetPoliceLabel;
    private spacetrader.controls.Label lblTargetPolSys;
    private spacetrader.controls.Label lblTargetResource;
    private spacetrader.controls.Label lblTargetResourceLabel;
    private spacetrader.controls.Label lblTargetSize;
    private spacetrader.controls.Label lblTargetTech;
    private spacetrader.controls.Label lblTargetTechLabel;
    private spacetrader.controls.Label lblTargetSizeLabel;

    TargetSystemPanel(SpaceTrader mainWindow, String name) {
        this.mainWindow = mainWindow;
        setName(name);
    }

    void setGame(SystemTracker game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    private void updateAll() {
        // todo inline when done
        mainWindow.updateAll();
    }

    void initializeComponent() {
        btnTrack = new spacetrader.controls.Button();
        btnNextSystem = new spacetrader.controls.Button();
        btnPrevSystem = new spacetrader.controls.Button();
        lblTargetOutOfRange = new spacetrader.controls.Label();
        btnWarp = new spacetrader.controls.Button();
        lblTargetPolSys = new spacetrader.controls.Label();
        lblTargetSize = new spacetrader.controls.Label();
        lblTargetTech = new spacetrader.controls.Label();
        lblTargetDistance = new spacetrader.controls.Label();
        lblTargetPirates = new spacetrader.controls.Label();
        lblTargetPolice = new spacetrader.controls.Label();
        lblTargetResource = new spacetrader.controls.Label();
        lblTargetDistanceLabel = new spacetrader.controls.Label();
        lblTargetPiratesLabel = new spacetrader.controls.Label();
        lblTargetPoliceLabel = new spacetrader.controls.Label();
        lblTargetResourceLabel = new spacetrader.controls.Label();
        lblTargetGovtLabel = new spacetrader.controls.Label();
        lblTargetTechLabel = new spacetrader.controls.Label();
        lblTargetSizeLabel = new spacetrader.controls.Label();
        lblTargetName = new spacetrader.controls.Label();
        lblTargetNameLabel = new spacetrader.controls.Label();

        anchor = (((AnchorStyles.Top_Right)));
        controls.add(btnTrack);
        controls.add(btnNextSystem);
        controls.add(btnPrevSystem);
        controls.add(lblTargetOutOfRange);
        controls.add(btnWarp);
        controls.add(lblTargetPolSys);
        controls.add(lblTargetSize);
        controls.add(lblTargetTech);
        controls.add(lblTargetDistance);
        controls.add(lblTargetPirates);
        controls.add(lblTargetPolice);
        controls.add(lblTargetResource);
        controls.add(lblTargetDistanceLabel);
        controls.add(lblTargetPiratesLabel);
        controls.add(lblTargetPoliceLabel);
        controls.add(lblTargetResourceLabel);
        controls.add(lblTargetGovtLabel);
        controls.add(lblTargetTechLabel);
        controls.add(lblTargetSizeLabel);
        controls.add(lblTargetName);
        controls.add(lblTargetNameLabel);
        setSize(new spacetrader.controls.Size(216, 168));
        setTabIndex(7);
        setTabStop(false);
        setText("Target System");
        //
        // btnTrack
        //
        btnTrack.setFlatStyle(FlatStyle.FLAT);
        btnTrack.setLocation(new Point(160, 140));
        btnTrack.setName("btnTrack");
        btnTrack.setSize(new spacetrader.controls.Size(44, 22));
        btnTrack.setTabIndex(60);
        btnTrack.setText("Track");
        btnTrack.setVisible(false);
        btnTrack.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnTrack_Click(sender, e);
            }
        });
        //
        // btnNextSystem
        //
        btnNextSystem.setFlatStyle(FlatStyle.FLAT);
        btnNextSystem.setLocation(new Point(186, 16));
        btnNextSystem.setName("btnNextSystem");
        btnNextSystem.setSize(new spacetrader.controls.Size(18, 18));
        btnNextSystem.setTabIndex(58);
        btnNextSystem.setText(">");
        btnNextSystem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnNextSystem_Click(sender, e);
            }
        });
        //
        // btnPrevSystem
        //
        btnPrevSystem.setFlatStyle(FlatStyle.FLAT);
        btnPrevSystem.setLocation(new Point(160, 16));
        btnPrevSystem.setName("btnPrevSystem");
        btnPrevSystem.setSize(new spacetrader.controls.Size(18, 18));
        btnPrevSystem.setTabIndex(57);
        btnPrevSystem.setText("<");
        btnPrevSystem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnPrevSystem_Click(sender, e);
            }
        });
        //
        // lblTargetOutOfRange
        //
        lblTargetOutOfRange.setLocation(new Point(8, 144));
        lblTargetOutOfRange.setName("lblTargetOutOfRange");
        lblTargetOutOfRange.setSize(new spacetrader.controls.Size(144, 13));
        lblTargetOutOfRange.setTabIndex(17);
        lblTargetOutOfRange.setText("This system is out of range.");
        //
        // btnWarp
        //
        btnWarp.setFlatStyle(FlatStyle.FLAT);
        btnWarp.setLocation(new Point(160, 98));
        btnWarp.setName("btnWarp");
        btnWarp.setSize(new spacetrader.controls.Size(44, 44));
        btnWarp.setTabIndex(59);
        btnWarp.setText("Warp");
        btnWarp.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnWarp_Click(sender, e);
            }
        });
        //
        // lblTargetPolSys
        //
        lblTargetPolSys.setLocation(new Point(88, 64));
        lblTargetPolSys.setName("lblTargetPolSys");
        lblTargetPolSys.setSize(new spacetrader.controls.Size(91, 13));
        lblTargetPolSys.setTabIndex(15);
        lblTargetPolSys.setText("Communist State");
        //
        // lblTargetSize
        //
        lblTargetSize.setLocation(new Point(88, 32));
        lblTargetSize.setName("lblTargetSize");
        lblTargetSize.setSize(new spacetrader.controls.Size(45, 13));
        lblTargetSize.setTabIndex(14);
        lblTargetSize.setText("Medium");
        //
        // lblTargetTech
        //
        lblTargetTech.setLocation(new Point(88, 48));
        lblTargetTech.setName("lblTargetTech");
        lblTargetTech.setSize(new spacetrader.controls.Size(82, 13));
        lblTargetTech.setTabIndex(13);
        lblTargetTech.setText("Pre-Agricultural");
        //
        // lblTargetDistance
        //
        lblTargetDistance.setLocation(new Point(88, 128));
        lblTargetDistance.setName("lblTargetDistance");
        lblTargetDistance.setSize(new spacetrader.controls.Size(66, 13));
        lblTargetDistance.setTabIndex(12);
        lblTargetDistance.setText("888 parsecs");
        //
        // lblTargetPirates
        //
        lblTargetPirates.setLocation(new Point(88, 112));
        lblTargetPirates.setName("lblTargetPirates");
        lblTargetPirates.setSize(new spacetrader.controls.Size(53, 13));
        lblTargetPirates.setTabIndex(11);
        lblTargetPirates.setText("Abundant");
        //
        // lblTargetPolice
        //
        lblTargetPolice.setLocation(new Point(88, 96));
        lblTargetPolice.setName("lblTargetPolice");
        lblTargetPolice.setSize(new spacetrader.controls.Size(53, 13));
        lblTargetPolice.setTabIndex(10);
        lblTargetPolice.setText("Abundant");
        //
        // lblTargetResource
        //
        lblTargetResource.setLocation(new Point(88, 80));
        lblTargetResource.setName("lblTargetResource");
        lblTargetResource.setSize(new spacetrader.controls.Size(105, 13));
        lblTargetResource.setTabIndex(9);
        lblTargetResource.setText("Sweetwater Oceans");
        //
        // lblTargetDistanceLabel
        //
        lblTargetDistanceLabel.setAutoSize(true);
        lblTargetDistanceLabel.setFont(FontCollection.bold825);
        lblTargetDistanceLabel.setLocation(new Point(8, 128));
        lblTargetDistanceLabel.setName("lblTargetDistanceLabel");
        lblTargetDistanceLabel.setSize(new spacetrader.controls.Size(53, 16));
        lblTargetDistanceLabel.setTabIndex(8);
        lblTargetDistanceLabel.setText("Distance:");
        //
        // lblTargetPiratesLabel
        //
        lblTargetPiratesLabel.setAutoSize(true);
        lblTargetPiratesLabel.setFont(FontCollection.bold825);
        lblTargetPiratesLabel.setLocation(new Point(8, 112));
        lblTargetPiratesLabel.setName("lblTargetPiratesLabel");
        lblTargetPiratesLabel.setSize(new spacetrader.controls.Size(44, 16));
        lblTargetPiratesLabel.setTabIndex(7);
        lblTargetPiratesLabel.setText("Pirates:");
        //
        // lblTargetPoliceLabel
        //
        lblTargetPoliceLabel.setAutoSize(true);
        lblTargetPoliceLabel.setFont(FontCollection.bold825);
        lblTargetPoliceLabel.setLocation(new Point(8, 96));
        lblTargetPoliceLabel.setName("lblTargetPoliceLabel");
        lblTargetPoliceLabel.setSize(new spacetrader.controls.Size(40, 16));
        lblTargetPoliceLabel.setTabIndex(6);
        lblTargetPoliceLabel.setText("Police:");
        //
        // lblTargetResourceLabel
        //
        lblTargetResourceLabel.setAutoSize(true);
        lblTargetResourceLabel.setFont(FontCollection.bold825);
        lblTargetResourceLabel.setLocation(new Point(8, 80));
        lblTargetResourceLabel.setName("lblTargetResourceLabel");
        lblTargetResourceLabel.setSize(new spacetrader.controls.Size(58, 16));
        lblTargetResourceLabel.setTabIndex(5);
        lblTargetResourceLabel.setText("Resource:");
        //
        // lblTargetGovtLabel
        //
        lblTargetGovtLabel.setAutoSize(true);
        lblTargetGovtLabel.setFont(FontCollection.bold825);
        lblTargetGovtLabel.setLocation(new Point(8, 64));
        lblTargetGovtLabel.setName("lblTargetGovtLabel");
        lblTargetGovtLabel.setSize(new spacetrader.controls.Size(72, 16));
        lblTargetGovtLabel.setTabIndex(4);
        lblTargetGovtLabel.setText("Government:");
        //
        // lblTargetTechLabel
        //
        lblTargetTechLabel.setAutoSize(true);
        lblTargetTechLabel.setFont(FontCollection.bold825);
        lblTargetTechLabel.setLocation(new Point(8, 48));
        lblTargetTechLabel.setName("lblTargetTechLabel");
        lblTargetTechLabel.setSize(new spacetrader.controls.Size(65, 16));
        lblTargetTechLabel.setTabIndex(3);
        lblTargetTechLabel.setText("Tech Level:");
        //
        // lblTargetSizeLabel
        //
        lblTargetSizeLabel.setAutoSize(true);
        lblTargetSizeLabel.setFont(FontCollection.bold825);
        lblTargetSizeLabel.setLocation(new Point(8, 32));
        lblTargetSizeLabel.setName("lblTargetSizeLabel");
        lblTargetSizeLabel.setSize(new spacetrader.controls.Size(31, 16));
        lblTargetSizeLabel.setTabIndex(2);
        lblTargetSizeLabel.setText("Size:");
        //
        // lblTargetName
        //
        lblTargetName.setLocation(new Point(88, 16));
        lblTargetName.setName("lblTargetName");
        lblTargetName.setSize(new spacetrader.controls.Size(65, 13));
        lblTargetName.setTabIndex(1);
        lblTargetName.setText("Tarchannen");
        //
        // lblTargetNameLabel
        //
        lblTargetNameLabel.setAutoSize(true);
        lblTargetNameLabel.setFont(FontCollection.bold825);
        lblTargetNameLabel.setLocation(new Point(8, 16));
        lblTargetNameLabel.setName("lblTargetNameLabel");
        lblTargetNameLabel.setSize(new spacetrader.controls.Size(39, 16));
        lblTargetNameLabel.setTabIndex(0);
        lblTargetNameLabel.setText("Name:");

    }

    private void btnTrack_Click(Object sender, spacetrader.controls.EventArgs e) {
        game.setTrackedSystemId(game.SelectedSystemId());
        updateAll();
    }

    private void btnWarp_Click(Object sender, spacetrader.controls.EventArgs e) {
        try {
            controller.autoSave_depart();

            game.Warp(false);

            controller.autoSave_arrive();
        } catch (GameEndException ex) {
            controller.gameEnd();
        }
        updateAll();
    }

    void Update() {
        btnNextSystem.setVisible(game != null);
        btnPrevSystem.setVisible(game != null);

        if (game == null || game.WarpSystem() == null) {
            lblTargetName.setText("");
            lblTargetSize.setText("");
            lblTargetTech.setText("");
            lblTargetPolSys.setText("");
            lblTargetResource.setText("");
            lblTargetPolice.setText("");
            lblTargetPirates.setText("");
            lblTargetDistance.setText("");
            lblTargetOutOfRange.setVisible(false);
            btnWarp.setVisible(false);
            btnTrack.setVisible(false);
        } else {
            StarSystem system = game.WarpSystem();
            int distance = Functions.Distance(commander.getCurrentSystem(), system);

            lblTargetName.setText(system.Name());
            lblTargetSize.setText(Strings.Sizes[system.Size().castToInt()]);
            lblTargetTech.setText(Strings.TechLevelNames[system.TechLevel().castToInt()]);
            lblTargetPolSys.setText(system.PoliticalSystem().Name());
            lblTargetResource.setText(system.Visited() ? Strings.SpecialResources[system.SpecialResource().castToInt()]
                    : Strings.Unknown);
            lblTargetPolice.setText(Strings.ActivityLevels[system.PoliticalSystem().ActivityPolice().castToInt()]);
            lblTargetPirates.setText(Strings.ActivityLevels[system.PoliticalSystem().ActivityPirates().castToInt()]);
            lblTargetDistance.setText("" + distance);
            lblTargetOutOfRange.setVisible(!system.DestOk() && system != commander.getCurrentSystem());
            btnWarp.setVisible(system.DestOk());
            btnTrack.setVisible(lblTargetOutOfRange.getVisible() && system != game.TrackedSystem());
        }
    }

    private void btnNextSystem_Click(Object sender, spacetrader.controls.EventArgs e) {
        game.SelectNextSystemWithinRange(true);
        updateAll();
    }

    private void btnPrevSystem_Click(Object sender, spacetrader.controls.EventArgs e) {
        game.SelectNextSystemWithinRange(false);
        updateAll();
    }
}
