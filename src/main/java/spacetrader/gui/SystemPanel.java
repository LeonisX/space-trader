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
    private Button btnNews;
    private Button btnSpecial;
    private Button btnMerc;
    private Label lblSystemGovtLabel;
    private Label lblSystemName;
    private Label lblSystemNameLabel;
    private Label lblSystemPirates;
    private Label lblSystemPiratesLabel;
    private Label lblSystemPolice;
    private Label lblSystemPoliceLabel;
    private Label lblSystemPolSys;
    private Label lblSystemPressure;
    private Label lblSystemPressurePre;
    private Label lblSystemResource;
    private Label lblSystemResourseLabel;
    private Label lblSystemSize;
    private Label lblSystemSizeLabel;
    private Label lblSystemTech;
    private Label lblSystemTechLabel;
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
        btnMerc = new Button();
        btnSpecial = new Button();
        btnNews = new Button();
        lblSystemPressure = new Label();
        lblSystemPressurePre = new Label();
        lblSystemPolSys = new Label();
        lblSystemSize = new Label();
        lblSystemTech = new Label();
        lblSystemPirates = new Label();
        lblSystemPolice = new Label();
        lblSystemResource = new Label();
        lblSystemPiratesLabel = new Label();
        lblSystemPoliceLabel = new Label();
        lblSystemResourseLabel = new Label();
        lblSystemGovtLabel = new Label();
        lblSystemTechLabel = new Label();
        lblSystemSizeLabel = new Label();
        lblSystemName = new Label();
        lblSystemNameLabel = new Label();

        controls.add(btnMerc);
        controls.add(btnSpecial);
        controls.add(btnNews);
        controls.add(lblSystemPressure);
        controls.add(lblSystemPressurePre);
        controls.add(lblSystemPolSys);
        controls.add(lblSystemSize);
        controls.add(lblSystemTech);
        controls.add(lblSystemPirates);
        controls.add(lblSystemPolice);
        controls.add(lblSystemResource);
        controls.add(lblSystemPiratesLabel);
        controls.add(lblSystemPoliceLabel);
        controls.add(lblSystemResourseLabel);
        controls.add(lblSystemGovtLabel);
        controls.add(lblSystemTechLabel);
        controls.add(lblSystemSizeLabel);
        controls.add(lblSystemName);
        controls.add(lblSystemNameLabel);

        setSize(new spacetrader.controls.Size(240, 206));
        setTabIndex(1);
        setTabStop(false);
        setText("System Info");
        //
        // btnMerc
        //
        btnMerc.setFlatStyle(FlatStyle.FLAT);
        btnMerc.setLocation(new Point(118, 174));
        btnMerc.setName("btnMerc");
        btnMerc.setSize(new spacetrader.controls.Size(112, 22));
        btnMerc.setTabIndex(3);
        btnMerc.setText("Mercenary For Hire");
        btnMerc.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnMerc_Click();
            }
        });
        //
        // btnSpecial
        //
        btnSpecial.setBackground(new Color(255, 255, 128));
        btnSpecial.setFlatStyle(FlatStyle.FLAT);
        btnSpecial.setLocation(new Point(58, 174));
        btnSpecial.setName("btnSpecial");
        btnSpecial.setSize(new Size(52, 22));
        btnSpecial.setTabIndex(2);
        btnSpecial.setText("Special");
        btnSpecial.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnSpecial_Click();
            }
        });
        //
        // btnNews
        //
        btnNews.setFlatStyle(FlatStyle.FLAT);
        btnNews.setLocation(new Point(8, 174));
        btnNews.setName("btnNews");
        btnNews.setSize(new Size(42, 22));
        btnNews.setTabIndex(1);
        btnNews.setText("News");
        btnNews.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnNews_Click();
            }
        });
        //
        // lblSystemPressure
        //
        lblSystemPressure.setLocation(new Point(8, 147));
        lblSystemPressure.setName("lblSystemPressure");
        lblSystemPressure.setSize(new Size(168, 16));
        lblSystemPressure.setTabIndex(18);
        lblSystemPressure.setText("suffering from extreme bordom.");
        //
        // lblSystemPressurePre
        //
        lblSystemPressurePre.setAutoSize(true);
        lblSystemPressurePre.setLocation(new Point(8, 134));
        lblSystemPressurePre.setName("lblSystemPressurePre");
        lblSystemPressurePre.setSize(new Size(122, 16));
        lblSystemPressurePre.setTabIndex(17);
        lblSystemPressurePre.setText("This system is currently");
        //
        // lblSystemPolSys
        //
        lblSystemPolSys.setLocation(new Point(88, 64));
        lblSystemPolSys.setName("lblSystemPolSys");
        lblSystemPolSys.setSize(new Size(91, 13));
        lblSystemPolSys.setTabIndex(15);
        lblSystemPolSys.setText("Cybernetic State");
        //
        // lblSystemSize
        //
        lblSystemSize.setLocation(new Point(88, 32));
        lblSystemSize.setName("lblSystemSize");
        lblSystemSize.setSize(new Size(45, 13));
        lblSystemSize.setTabIndex(14);
        lblSystemSize.setText("Medium");
        //
        // lblSystemTech
        //
        lblSystemTech.setLocation(new Point(88, 48));
        lblSystemTech.setName("lblSystemTech");
        lblSystemTech.setSize(new Size(82, 13));
        lblSystemTech.setTabIndex(13);
        lblSystemTech.setText("Pre-Agricultural");
        //
        // lblSystemPirates
        //
        lblSystemPirates.setLocation(new Point(88, 112));
        lblSystemPirates.setName("lblSystemPirates");
        lblSystemPirates.setSize(new Size(53, 13));
        lblSystemPirates.setTabIndex(11);
        lblSystemPirates.setText("Abundant");

        //
        // lblSystemPolice
        //
        lblSystemPolice.setLocation(new Point(88, 96));
        lblSystemPolice.setName("lblSystemPolice");
        lblSystemPolice.setSize(new Size(53, 13));
        lblSystemPolice.setTabIndex(10);
        lblSystemPolice.setText("Moderate");
        //
        // lblSystemResource
        //
        lblSystemResource.setLocation(new Point(88, 80));
        lblSystemResource.setName("lblSystemResource");
        lblSystemResource.setSize(new Size(105, 13));
        lblSystemResource.setTabIndex(9);
        lblSystemResource.setText("Sweetwater Oceans");
        //
        // lblSystemPiratesLabel
        //
        lblSystemPiratesLabel.setAutoSize(true);
        lblSystemPiratesLabel.setFont(FontCollection.bold825);
        lblSystemPiratesLabel.setLocation(new Point(8, 112));
        lblSystemPiratesLabel.setName("lblSystemPiratesLabel");
        lblSystemPiratesLabel.setSize(new Size(44, 16));
        lblSystemPiratesLabel.setTabIndex(7);
        lblSystemPiratesLabel.setText("Pirates:");
        //
        // lblSystemPoliceLabel
        //
        lblSystemPoliceLabel.setAutoSize(true);
        lblSystemPoliceLabel.setFont(FontCollection.bold825);
        lblSystemPoliceLabel.setLocation(new Point(8, 96));
        lblSystemPoliceLabel.setName("lblSystemPoliceLabel");
        lblSystemPoliceLabel.setSize(new Size(40, 16));
        lblSystemPoliceLabel.setTabIndex(6);
        lblSystemPoliceLabel.setText("Police:");
        //
        // lblSystemResourseLabel
        //
        lblSystemResourseLabel.setAutoSize(true);
        lblSystemResourseLabel.setFont(FontCollection.bold825);
        lblSystemResourseLabel.setLocation(new Point(8, 80));
        lblSystemResourseLabel.setName("lblSystemResourseLabel");
        lblSystemResourseLabel.setSize(new Size(58, 16));
        lblSystemResourseLabel.setTabIndex(5);
        lblSystemResourseLabel.setText("Resource:");
        //
        // lblSystemGovtLabel
        //
        lblSystemGovtLabel.setAutoSize(true);
        lblSystemGovtLabel.setFont(FontCollection.bold825);
        lblSystemGovtLabel.setLocation(new Point(8, 64));
        lblSystemGovtLabel.setName("lblSystemGovtLabel");
        lblSystemGovtLabel.setSize(new Size(72, 16));
        lblSystemGovtLabel.setTabIndex(4);
        lblSystemGovtLabel.setText("Government:");
        //
        // lblSystemTechLabel
        //
        lblSystemTechLabel.setAutoSize(true);
        lblSystemTechLabel.setFont(FontCollection.bold825);
        lblSystemTechLabel.setLocation(new Point(8, 48));
        lblSystemTechLabel.setName("lblSystemTechLabel");
        lblSystemTechLabel.setSize(new Size(65, 16));
        lblSystemTechLabel.setTabIndex(3);
        lblSystemTechLabel.setText("Tech Level:");
        //
        // lblSystemSizeLabel
        //
        lblSystemSizeLabel.setAutoSize(true);
        lblSystemSizeLabel.setFont(FontCollection.bold825);
        lblSystemSizeLabel.setLocation(new Point(8, 32));
        lblSystemSizeLabel.setName("lblSystemSizeLabel");
        lblSystemSizeLabel.setSize(new Size(31, 16));
        lblSystemSizeLabel.setTabIndex(2);
        lblSystemSizeLabel.setText("Size:");
        //
        // lblSystemName
        //
        lblSystemName.setLocation(new Point(88, 16));
        lblSystemName.setName("lblSystemName");
        lblSystemName.setSize(new Size(65, 13));
        lblSystemName.setTabIndex(1);
        lblSystemName.setText("Tarchannen");
        //
        // lblSystemNameLabel
        //
        lblSystemNameLabel.setAutoSize(true);
        lblSystemNameLabel.setFont(FontCollection.bold825);
        lblSystemNameLabel.setLocation(new Point(8, 16));
        lblSystemNameLabel.setName("lblSystemNameLabel");
        lblSystemNameLabel.setSize(new Size(39, 16));
        lblSystemNameLabel.setTabIndex(0);
        lblSystemNameLabel.setText("Name:");

        tipSpecial = new ToolTip();
        tipMerc = new ToolTip();
    }

    void Update() {
        if (game == null || commander.getCurrentSystem() == null) {
            lblSystemName.setText("");
            lblSystemSize.setText("");
            lblSystemTech.setText("");
            lblSystemPolSys.setText("");
            lblSystemResource.setText("");
            lblSystemPolice.setText("");
            lblSystemPirates.setText("");
            lblSystemPressure.setText("");
            lblSystemPressurePre.setVisible(false);
            btnNews.setVisible(false);
            btnMerc.setVisible(false);
            btnSpecial.setVisible(false);
        } else {
            StarSystem system = commander.getCurrentSystem();
            CrewMember[] mercs = system.MercenariesForHire();

            lblSystemName.setText(system.Name());
            lblSystemSize.setText(Strings.Sizes[system.Size().castToInt()]);
            lblSystemTech.setText(Strings.TechLevelNames[system.TechLevel().castToInt()]);
            lblSystemPolSys.setText(system.PoliticalSystem().Name());
            lblSystemResource.setText(Strings.SpecialResources[system.SpecialResource().castToInt()]);
            lblSystemPolice.setText(Strings.ActivityLevels[system.PoliticalSystem().ActivityPolice().castToInt()]);
            lblSystemPirates.setText(Strings.ActivityLevels[system.PoliticalSystem().ActivityPirates().castToInt()]);
            lblSystemPressure.setText(Strings.SystemPressures[system.SystemPressure().castToInt()]);
            lblSystemPressurePre.setVisible(true);
            btnNews.setVisible(true);
            btnMerc.setVisible(mercs.length > 0);
            if (btnMerc.getVisible()) {
                tipMerc.setToolTip(btnMerc, Functions.StringVars(Strings.MercenariesForHire,
                        mercs.length == 1 ? mercs[0].Name() : mercs.length + Strings.Mercenaries));
            }
            btnSpecial.setVisible(system.ShowSpecialButton());
            if (btnSpecial.getVisible()) {
                tipSpecial.setToolTip(btnSpecial, system.SpecialEvent().Title());
            }
        }
    }

    private void btnMerc_Click() {
        (new FormViewPersonnel()).showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void btnNews_Click() {
        game.ShowNewspaper();
    }

    private void btnSpecial_Click() {
        SpecialEvent specEvent = commander.getCurrentSystem().SpecialEvent();
        String btn1, btn2;
        DialogResult res1, res2;

        if (specEvent.MessageOnly()) {
            btn1 = "Ok";
            btn2 = null;
            res1 = DialogResult.OK;
            res2 = DialogResult.NONE;
        } else {
            btn1 = "Yes";
            btn2 = "No";
            res1 = DialogResult.YES;
            res2 = DialogResult.NO;
        }

        FormAlert alert = new FormAlert(specEvent.Title(), specEvent.String(), btn1, res1, btn2, res2, null);
        if (alert.showDialog() != DialogResult.NO) {
            if (commander.CashToSpend() < specEvent.Price())
                GuiFacade.alert(AlertType.SpecialIF);
            else {
                try {
                    game.HandleSpecialEvent();
                } catch (GameEndException ex) {
                    controller.gameEnd();
                }
            }
        }

        mainWindow.updateAll();
    }
}
