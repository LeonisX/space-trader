package spacetrader.gui;

import jwinforms.DialogResult;
import jwinforms.EventArgs;
import jwinforms.EventHandler;
import spacetrader.*;
import spacetrader.enums.AlertType;
import spacetrader.guifacade.GuiFacade;

import java.awt.*;

public class SystemBox extends jwinforms.GroupBox {
    private final SpaceTrader mainWindow;
    private CurrentSystemMgr game = null;
    private GameController controller = null;
    private Commander commander;
    private jwinforms.Button btnNews;
    private jwinforms.Button btnSpecial;
    private jwinforms.Button btnMerc;
    private jwinforms.Label lblSystemGovtLabel;
    private jwinforms.Label lblSystemName;
    private jwinforms.Label lblSystemNameLabel;
    private jwinforms.Label lblSystemPirates;
    private jwinforms.Label lblSystemPiratesLabel;
    private jwinforms.Label lblSystemPolice;
    private jwinforms.Label lblSystemPoliceLabel;
    private jwinforms.Label lblSystemPolSys;
    private jwinforms.Label lblSystemPressure;
    private jwinforms.Label lblSystemPressurePre;
    private jwinforms.Label lblSystemResource;
    private jwinforms.Label lblSystemResourseLabel;
    private jwinforms.Label lblSystemSize;
    private jwinforms.Label lblSystemSizeLabel;
    private jwinforms.Label lblSystemTech;
    private jwinforms.Label lblSystemTechLabel;
    private jwinforms.ToolTip tipSpecial;
    private jwinforms.ToolTip tipMerc;
    public SystemBox(SpaceTrader mainWindow) {
        this.mainWindow = mainWindow;
    }

    void setGame(CurrentSystemMgr game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
    }

    void InitializeComponent() {
        btnMerc = new jwinforms.Button();
        btnSpecial = new jwinforms.Button();
        btnNews = new jwinforms.Button();
        lblSystemPressure = new jwinforms.Label();
        lblSystemPressurePre = new jwinforms.Label();
        lblSystemPolSys = new jwinforms.Label();
        lblSystemSize = new jwinforms.Label();
        lblSystemTech = new jwinforms.Label();
        lblSystemPirates = new jwinforms.Label();
        lblSystemPolice = new jwinforms.Label();
        lblSystemResource = new jwinforms.Label();
        lblSystemPiratesLabel = new jwinforms.Label();
        lblSystemPoliceLabel = new jwinforms.Label();
        lblSystemResourseLabel = new jwinforms.Label();
        lblSystemGovtLabel = new jwinforms.Label();
        lblSystemTechLabel = new jwinforms.Label();
        lblSystemSizeLabel = new jwinforms.Label();
        lblSystemName = new jwinforms.Label();
        lblSystemNameLabel = new jwinforms.Label();

        Controls.add(btnMerc);
        Controls.add(btnSpecial);
        Controls.add(btnNews);
        Controls.add(lblSystemPressure);
        Controls.add(lblSystemPressurePre);
        Controls.add(lblSystemPolSys);
        Controls.add(lblSystemSize);
        Controls.add(lblSystemTech);
        Controls.add(lblSystemPirates);
        Controls.add(lblSystemPolice);
        Controls.add(lblSystemResource);
        Controls.add(lblSystemPiratesLabel);
        Controls.add(lblSystemPoliceLabel);
        Controls.add(lblSystemResourseLabel);
        Controls.add(lblSystemGovtLabel);
        Controls.add(lblSystemTechLabel);
        Controls.add(lblSystemSizeLabel);
        Controls.add(lblSystemName);
        Controls.add(lblSystemNameLabel);
        setName("boxSystem");
        setSize(new jwinforms.Size(240, 206));
        setTabIndex(1);
        setTabStop(false);
        setText("System Info");
        //
        // btnMerc
        //
        btnMerc.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnMerc.setLocation(new Point(118, 174));
        btnMerc.setName("btnMerc");
        btnMerc.setSize(new jwinforms.Size(112, 22));
        btnMerc.setTabIndex(3);
        btnMerc.setText("Mercenary For Hire");
        btnMerc.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnMerc_Click(sender, e);
            }
        });
        //
        // btnSpecial
        //
        btnSpecial.setBackColor(new Color(255, 255, 128));
        btnSpecial.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnSpecial.setLocation(new Point(58, 174));
        btnSpecial.setName("btnSpecial");
        btnSpecial.setSize(new jwinforms.Size(52, 22));
        btnSpecial.setTabIndex(2);
        btnSpecial.setText("Special");
        btnSpecial.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnSpecial_Click(sender, e);
            }
        });
        //
        // btnNews
        //
        btnNews.setFlatStyle(jwinforms.FlatStyle.Flat);
        btnNews.setLocation(new Point(8, 174));
        btnNews.setName("btnNews");
        btnNews.setSize(new jwinforms.Size(42, 22));
        btnNews.setTabIndex(1);
        btnNews.setText("News");
        btnNews.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, jwinforms.EventArgs e) {
                btnNews_Click(sender, e);
            }
        });
        //
        // lblSystemPressure
        //
        lblSystemPressure.setLocation(new Point(8, 147));
        lblSystemPressure.setName("lblSystemPressure");
        lblSystemPressure.setSize(new jwinforms.Size(168, 16));
        lblSystemPressure.setTabIndex(18);
        lblSystemPressure.setText("suffering from extreme bordom.");
        //
        // lblSystemPressurePre
        //
        lblSystemPressurePre.setAutoSize(true);
        lblSystemPressurePre.setLocation(new Point(8, 134));
        lblSystemPressurePre.setName("lblSystemPressurePre");
        lblSystemPressurePre.setSize(new jwinforms.Size(122, 16));
        lblSystemPressurePre.setTabIndex(17);
        lblSystemPressurePre.setText("This system is currently");
        //
        // lblSystemPolSys
        //
        lblSystemPolSys.setLocation(new Point(88, 64));
        lblSystemPolSys.setName("lblSystemPolSys");
        lblSystemPolSys.setSize(new jwinforms.Size(91, 13));
        lblSystemPolSys.setTabIndex(15);
        lblSystemPolSys.setText("Cybernetic State");
        //
        // lblSystemSize
        //
        lblSystemSize.setLocation(new Point(88, 32));
        lblSystemSize.setName("lblSystemSize");
        lblSystemSize.setSize(new jwinforms.Size(45, 13));
        lblSystemSize.setTabIndex(14);
        lblSystemSize.setText("Medium");
        //
        // lblSystemTech
        //
        lblSystemTech.setLocation(new Point(88, 48));
        lblSystemTech.setName("lblSystemTech");
        lblSystemTech.setSize(new jwinforms.Size(82, 13));
        lblSystemTech.setTabIndex(13);
        lblSystemTech.setText("Pre-Agricultural");
        //
        // lblSystemPirates
        //
        lblSystemPirates.setLocation(new Point(88, 112));
        lblSystemPirates.setName("lblSystemPirates");
        lblSystemPirates.setSize(new jwinforms.Size(53, 13));
        lblSystemPirates.setTabIndex(11);
        lblSystemPirates.setText("Abundant");

        //
        // lblSystemPolice
        //
        lblSystemPolice.setLocation(new Point(88, 96));
        lblSystemPolice.setName("lblSystemPolice");
        lblSystemPolice.setSize(new jwinforms.Size(53, 13));
        lblSystemPolice.setTabIndex(10);
        lblSystemPolice.setText("Moderate");
        //
        // lblSystemResource
        //
        lblSystemResource.setLocation(new Point(88, 80));
        lblSystemResource.setName("lblSystemResource");
        lblSystemResource.setSize(new jwinforms.Size(105, 13));
        lblSystemResource.setTabIndex(9);
        lblSystemResource.setText("Sweetwater Oceans");
        //
        // lblSystemPiratesLabel
        //
        lblSystemPiratesLabel.setAutoSize(true);
        lblSystemPiratesLabel.setFont(FontCollection.bold825);
        lblSystemPiratesLabel.setLocation(new Point(8, 112));
        lblSystemPiratesLabel.setName("lblSystemPiratesLabel");
        lblSystemPiratesLabel.setSize(new jwinforms.Size(44, 16));
        lblSystemPiratesLabel.setTabIndex(7);
        lblSystemPiratesLabel.setText("Pirates:");
        //
        // lblSystemPoliceLabel
        //
        lblSystemPoliceLabel.setAutoSize(true);
        lblSystemPoliceLabel.setFont(FontCollection.bold825);
        lblSystemPoliceLabel.setLocation(new Point(8, 96));
        lblSystemPoliceLabel.setName("lblSystemPoliceLabel");
        lblSystemPoliceLabel.setSize(new jwinforms.Size(40, 16));
        lblSystemPoliceLabel.setTabIndex(6);
        lblSystemPoliceLabel.setText("Police:");
        //
        // lblSystemResourseLabel
        //
        lblSystemResourseLabel.setAutoSize(true);
        lblSystemResourseLabel.setFont(FontCollection.bold825);
        lblSystemResourseLabel.setLocation(new Point(8, 80));
        lblSystemResourseLabel.setName("lblSystemResourseLabel");
        lblSystemResourseLabel.setSize(new jwinforms.Size(58, 16));
        lblSystemResourseLabel.setTabIndex(5);
        lblSystemResourseLabel.setText("Resource:");
        //
        // lblSystemGovtLabel
        //
        lblSystemGovtLabel.setAutoSize(true);
        lblSystemGovtLabel.setFont(FontCollection.bold825);
        lblSystemGovtLabel.setLocation(new Point(8, 64));
        lblSystemGovtLabel.setName("lblSystemGovtLabel");
        lblSystemGovtLabel.setSize(new jwinforms.Size(72, 16));
        lblSystemGovtLabel.setTabIndex(4);
        lblSystemGovtLabel.setText("Government:");
        //
        // lblSystemTechLabel
        //
        lblSystemTechLabel.setAutoSize(true);
        lblSystemTechLabel.setFont(FontCollection.bold825);
        lblSystemTechLabel.setLocation(new Point(8, 48));
        lblSystemTechLabel.setName("lblSystemTechLabel");
        lblSystemTechLabel.setSize(new jwinforms.Size(65, 16));
        lblSystemTechLabel.setTabIndex(3);
        lblSystemTechLabel.setText("Tech Level:");
        //
        // lblSystemSizeLabel
        //
        lblSystemSizeLabel.setAutoSize(true);
        lblSystemSizeLabel.setFont(FontCollection.bold825);
        lblSystemSizeLabel.setLocation(new Point(8, 32));
        lblSystemSizeLabel.setName("lblSystemSizeLabel");
        lblSystemSizeLabel.setSize(new jwinforms.Size(31, 16));
        lblSystemSizeLabel.setTabIndex(2);
        lblSystemSizeLabel.setText("Size:");
        //
        // lblSystemName
        //
        lblSystemName.setLocation(new Point(88, 16));
        lblSystemName.setName("lblSystemName");
        lblSystemName.setSize(new jwinforms.Size(65, 13));
        lblSystemName.setTabIndex(1);
        lblSystemName.setText("Tarchannen");
        //
        // lblSystemNameLabel
        //
        lblSystemNameLabel.setAutoSize(true);
        lblSystemNameLabel.setFont(FontCollection.bold825);
        lblSystemNameLabel.setLocation(new Point(8, 16));
        lblSystemNameLabel.setName("lblSystemNameLabel");
        lblSystemNameLabel.setSize(new jwinforms.Size(39, 16));
        lblSystemNameLabel.setTabIndex(0);
        lblSystemNameLabel.setText("Name:");

        tipSpecial = new jwinforms.ToolTip();
        tipMerc = new jwinforms.ToolTip();
    }

    public void Update() {
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
                tipMerc.SetToolTip(btnMerc, Functions.StringVars(Strings.MercenariesForHire,
                        mercs.length == 1 ? mercs[0].Name() : mercs.length + Strings.Mercenaries));
            }
            btnSpecial.setVisible(system.ShowSpecialButton());
            if (btnSpecial.getVisible())
                tipSpecial.SetToolTip(btnSpecial, system.SpecialEvent().Title());
        }
    }

    private void btnMerc_Click(Object sender, jwinforms.EventArgs e) {
        (new FormViewPersonnel()).showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void btnNews_Click(Object sender, jwinforms.EventArgs e) {
        game.ShowNewspaper();
    }

    private void btnSpecial_Click(Object sender, jwinforms.EventArgs e) {
        SpecialEvent specEvent = commander.getCurrentSystem().SpecialEvent();
        String btn1, btn2;
        DialogResult res1, res2;

        if (specEvent.MessageOnly()) {
            btn1 = "Ok";
            btn2 = null;
            res1 = DialogResult.OK;
            res2 = DialogResult.None;
        } else {
            btn1 = "Yes";
            btn2 = "No";
            res1 = DialogResult.Yes;
            res2 = DialogResult.No;
        }

        FormAlert alert = new FormAlert(specEvent.Title(), specEvent.String(), btn1, res1, btn2, res2, null);
        if (alert.showDialog() != DialogResult.No) {
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
