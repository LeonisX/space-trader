package spacetrader.gui;

import jwinforms.EventArgs;
import jwinforms.EventHandler;
import spacetrader.*;

import java.awt.*;

public class TargetSystemBox extends jwinforms.GroupBox {
    private final SpaceTrader mainWindow;
    private SystemTracker game = null;
    private GameController controller = null;
    private Commander commander;
    private jwinforms.Button btnTrack;
    private jwinforms.Button btnWarp;
    private jwinforms.Button btnPrevSystem;
    private jwinforms.Button btnNextSystem;
    private jwinforms.Label lblTargetDistance;
    private jwinforms.Label lblTargetDistanceLabel;
    private jwinforms.Label lblTargetGovtLabel;
    private jwinforms.Label lblTargetName;
    private jwinforms.Label lblTargetNameLabel;
    private jwinforms.Label lblTargetOutOfRange;
    private jwinforms.Label lblTargetPirates;
    private jwinforms.Label lblTargetPiratesLabel;
    private jwinforms.Label lblTargetPolice;
    private jwinforms.Label lblTargetPoliceLabel;
    private jwinforms.Label lblTargetPolSys;
    private jwinforms.Label lblTargetResource;
    private jwinforms.Label lblTargetResourceLabel;
    private jwinforms.Label lblTargetSize;
    private jwinforms.Label lblTargetTech;
    private jwinforms.Label lblTargetTechLabel;
    private jwinforms.Label lblTargetSizeLabel;
    public TargetSystemBox(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(SystemTracker game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    private void UpdateAll() {
        // todo inline when done
        mainWindow.updateAll();
    }

    void InitializeComponent() {
        btnTrack = new jwinforms.Button();
        btnNextSystem = new jwinforms.Button();
        btnPrevSystem = new jwinforms.Button();
        lblTargetOutOfRange = new jwinforms.Label();
        btnWarp = new jwinforms.Button();
        lblTargetPolSys = new jwinforms.Label();
        lblTargetSize = new jwinforms.Label();
        lblTargetTech = new jwinforms.Label();
        lblTargetDistance = new jwinforms.Label();
        lblTargetPirates = new jwinforms.Label();
        lblTargetPolice = new jwinforms.Label();
        lblTargetResource = new jwinforms.Label();
        lblTargetDistanceLabel = new jwinforms.Label();
        lblTargetPiratesLabel = new jwinforms.Label();
        lblTargetPoliceLabel = new jwinforms.Label();
        lblTargetResourceLabel = new jwinforms.Label();
        lblTargetGovtLabel = new jwinforms.Label();
        lblTargetTechLabel = new jwinforms.Label();
        lblTargetSizeLabel = new jwinforms.Label();
        lblTargetName = new jwinforms.Label();
        lblTargetNameLabel = new jwinforms.Label();

        Anchor = (((jwinforms.AnchorStyles.Top_Right)));
        Controls.add(btnTrack);
        Controls.add(btnNextSystem);
        Controls.add(btnPrevSystem);
        Controls.add(lblTargetOutOfRange);
        Controls.add(btnWarp);
        Controls.add(lblTargetPolSys);
        Controls.add(lblTargetSize);
        Controls.add(lblTargetTech);
        Controls.add(lblTargetDistance);
        Controls.add(lblTargetPirates);
        Controls.add(lblTargetPolice);
        Controls.add(lblTargetResource);
        Controls.add(lblTargetDistanceLabel);
        Controls.add(lblTargetPiratesLabel);
        Controls.add(lblTargetPoliceLabel);
        Controls.add(lblTargetResourceLabel);
        Controls.add(lblTargetGovtLabel);
        Controls.add(lblTargetTechLabel);
        Controls.add(lblTargetSizeLabel);
        Controls.add(lblTargetName);
        Controls.add(lblTargetNameLabel);
        setName("boxTargetSystem");
        setSize(new jwinforms.Size(216, 168));
        setTabIndex(7);
        setTabStop(false);
        setText("Target System");
        //
        // btnTrack
        //
        btnTrack.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnTrack.setLocation(new Point(160, 140));
        btnTrack.setName("btnTrack");
        btnTrack.setSize(new jwinforms.Size(44, 22));
        btnTrack.setTabIndex(60);
        btnTrack.setText("Track");
        btnTrack.setVisible(false);
        btnTrack.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnTrack_Click(sender, e);
            }
        });
        //
        // btnNextSystem
        //
        btnNextSystem.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnNextSystem.setLocation(new Point(186, 16));
        btnNextSystem.setName("btnNextSystem");
        btnNextSystem.setSize(new jwinforms.Size(18, 18));
        btnNextSystem.setTabIndex(58);
        btnNextSystem.setText(">");
        btnNextSystem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnNextSystem_Click(sender, e);
            }
        });
        //
        // btnPrevSystem
        //
        btnPrevSystem.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnPrevSystem.setLocation(new Point(160, 16));
        btnPrevSystem.setName("btnPrevSystem");
        btnPrevSystem.setSize(new jwinforms.Size(18, 18));
        btnPrevSystem.setTabIndex(57);
        btnPrevSystem.setText("<");
        btnPrevSystem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnPrevSystem_Click(sender, e);
            }
        });
        //
        // lblTargetOutOfRange
        //
        lblTargetOutOfRange.setLocation(new Point(8, 144));
        lblTargetOutOfRange.setName("lblTargetOutOfRange");
        lblTargetOutOfRange.setSize(new jwinforms.Size(144, 13));
        lblTargetOutOfRange.setTabIndex(17);
        lblTargetOutOfRange.setText("This system is out of range.");
        //
        // btnWarp
        //
        btnWarp.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnWarp.setLocation(new Point(160, 98));
        btnWarp.setName("btnWarp");
        btnWarp.setSize(new jwinforms.Size(44, 44));
        btnWarp.setTabIndex(59);
        btnWarp.setText("Warp");
        btnWarp.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnWarp_Click(sender, e);
            }
        });
        //
        // lblTargetPolSys
        //
        lblTargetPolSys.setLocation(new Point(88, 64));
        lblTargetPolSys.setName("lblTargetPolSys");
        lblTargetPolSys.setSize(new jwinforms.Size(91, 13));
        lblTargetPolSys.setTabIndex(15);
        lblTargetPolSys.setText("Communist State");
        //
        // lblTargetSize
        //
        lblTargetSize.setLocation(new Point(88, 32));
        lblTargetSize.setName("lblTargetSize");
        lblTargetSize.setSize(new jwinforms.Size(45, 13));
        lblTargetSize.setTabIndex(14);
        lblTargetSize.setText("Medium");
        //
        // lblTargetTech
        //
        lblTargetTech.setLocation(new Point(88, 48));
        lblTargetTech.setName("lblTargetTech");
        lblTargetTech.setSize(new jwinforms.Size(82, 13));
        lblTargetTech.setTabIndex(13);
        lblTargetTech.setText("Pre-Agricultural");
        //
        // lblTargetDistance
        //
        lblTargetDistance.setLocation(new Point(88, 128));
        lblTargetDistance.setName("lblTargetDistance");
        lblTargetDistance.setSize(new jwinforms.Size(66, 13));
        lblTargetDistance.setTabIndex(12);
        lblTargetDistance.setText("888 parsecs");
        //
        // lblTargetPirates
        //
        lblTargetPirates.setLocation(new Point(88, 112));
        lblTargetPirates.setName("lblTargetPirates");
        lblTargetPirates.setSize(new jwinforms.Size(53, 13));
        lblTargetPirates.setTabIndex(11);
        lblTargetPirates.setText("Abundant");
        //
        // lblTargetPolice
        //
        lblTargetPolice.setLocation(new Point(88, 96));
        lblTargetPolice.setName("lblTargetPolice");
        lblTargetPolice.setSize(new jwinforms.Size(53, 13));
        lblTargetPolice.setTabIndex(10);
        lblTargetPolice.setText("Abundant");
        //
        // lblTargetResource
        //
        lblTargetResource.setLocation(new Point(88, 80));
        lblTargetResource.setName("lblTargetResource");
        lblTargetResource.setSize(new jwinforms.Size(105, 13));
        lblTargetResource.setTabIndex(9);
        lblTargetResource.setText("Sweetwater Oceans");
        //
        // lblTargetDistanceLabel
        //
        lblTargetDistanceLabel.setAutoSize(true);
        lblTargetDistanceLabel.setFont(FontCollection.bold825);
        lblTargetDistanceLabel.setLocation(new Point(8, 128));
        lblTargetDistanceLabel.setName("lblTargetDistanceLabel");
        lblTargetDistanceLabel.setSize(new jwinforms.Size(53, 16));
        lblTargetDistanceLabel.setTabIndex(8);
        lblTargetDistanceLabel.setText("Distance:");
        //
        // lblTargetPiratesLabel
        //
        lblTargetPiratesLabel.setAutoSize(true);
        lblTargetPiratesLabel.setFont(FontCollection.bold825);
        lblTargetPiratesLabel.setLocation(new Point(8, 112));
        lblTargetPiratesLabel.setName("lblTargetPiratesLabel");
        lblTargetPiratesLabel.setSize(new jwinforms.Size(44, 16));
        lblTargetPiratesLabel.setTabIndex(7);
        lblTargetPiratesLabel.setText("Pirates:");
        //
        // lblTargetPoliceLabel
        //
        lblTargetPoliceLabel.setAutoSize(true);
        lblTargetPoliceLabel.setFont(FontCollection.bold825);
        lblTargetPoliceLabel.setLocation(new Point(8, 96));
        lblTargetPoliceLabel.setName("lblTargetPoliceLabel");
        lblTargetPoliceLabel.setSize(new jwinforms.Size(40, 16));
        lblTargetPoliceLabel.setTabIndex(6);
        lblTargetPoliceLabel.setText("Police:");
        //
        // lblTargetResourceLabel
        //
        lblTargetResourceLabel.setAutoSize(true);
        lblTargetResourceLabel.setFont(FontCollection.bold825);
        lblTargetResourceLabel.setLocation(new Point(8, 80));
        lblTargetResourceLabel.setName("lblTargetResourceLabel");
        lblTargetResourceLabel.setSize(new jwinforms.Size(58, 16));
        lblTargetResourceLabel.setTabIndex(5);
        lblTargetResourceLabel.setText("Resource:");
        //
        // lblTargetGovtLabel
        //
        lblTargetGovtLabel.setAutoSize(true);
        lblTargetGovtLabel.setFont(FontCollection.bold825);
        lblTargetGovtLabel.setLocation(new Point(8, 64));
        lblTargetGovtLabel.setName("lblTargetGovtLabel");
        lblTargetGovtLabel.setSize(new jwinforms.Size(72, 16));
        lblTargetGovtLabel.setTabIndex(4);
        lblTargetGovtLabel.setText("Government:");
        //
        // lblTargetTechLabel
        //
        lblTargetTechLabel.setAutoSize(true);
        lblTargetTechLabel.setFont(FontCollection.bold825);
        lblTargetTechLabel.setLocation(new Point(8, 48));
        lblTargetTechLabel.setName("lblTargetTechLabel");
        lblTargetTechLabel.setSize(new jwinforms.Size(65, 16));
        lblTargetTechLabel.setTabIndex(3);
        lblTargetTechLabel.setText("Tech Level:");
        //
        // lblTargetSizeLabel
        //
        lblTargetSizeLabel.setAutoSize(true);
        lblTargetSizeLabel.setFont(FontCollection.bold825);
        lblTargetSizeLabel.setLocation(new Point(8, 32));
        lblTargetSizeLabel.setName("lblTargetSizeLabel");
        lblTargetSizeLabel.setSize(new jwinforms.Size(31, 16));
        lblTargetSizeLabel.setTabIndex(2);
        lblTargetSizeLabel.setText("Size:");
        //
        // lblTargetName
        //
        lblTargetName.setLocation(new Point(88, 16));
        lblTargetName.setName("lblTargetName");
        lblTargetName.setSize(new jwinforms.Size(65, 13));
        lblTargetName.setTabIndex(1);
        lblTargetName.setText("Tarchannen");
        //
        // lblTargetNameLabel
        //
        lblTargetNameLabel.setAutoSize(true);
        lblTargetNameLabel.setFont(FontCollection.bold825);
        lblTargetNameLabel.setLocation(new Point(8, 16));
        lblTargetNameLabel.setName("lblTargetNameLabel");
        lblTargetNameLabel.setSize(new jwinforms.Size(39, 16));
        lblTargetNameLabel.setTabIndex(0);
        lblTargetNameLabel.setText("Name:");

    }

    private void btnTrack_Click(Object sender, jwinforms.EventArgs e) {
        game.setTrackedSystemId(game.SelectedSystemId());
        UpdateAll();
    }

    private void btnWarp_Click(Object sender, jwinforms.EventArgs e) {
        try {
            controller.autoSave_depart();

            game.Warp(false);

            controller.autoSave_arrive();
        } catch (GameEndException ex) {
            controller.gameEnd();
        }
        UpdateAll();
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

    private void btnNextSystem_Click(Object sender, jwinforms.EventArgs e) {
        game.SelectNextSystemWithinRange(true);
        UpdateAll();
    }

    private void btnPrevSystem_Click(Object sender, jwinforms.EventArgs e) {
        game.SelectNextSystemWithinRange(false);
        UpdateAll();
    }
}
