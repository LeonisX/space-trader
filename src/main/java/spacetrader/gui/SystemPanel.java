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
    private Button newsButton;
    private Button specialButton;
    private Button hireMercenaryButton;
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
        hireMercenaryButton = new Button("hireMercenaryButton");
        specialButton = new Button("specialButton");
        newsButton = new Button("newsButton");

        lblSystemNameLabel = new Label();
        lblSystemName = new Label();
        lblSystemSizeLabel = new Label();
        lblSystemSize = new Label();
        lblSystemTechLabel = new Label();
        lblSystemTech = new Label();
        lblSystemGovtLabel = new Label();
        lblSystemPolSys = new Label();
        lblSystemResourseLabel = new Label();
        lblSystemResource = new Label();
        lblSystemPoliceLabel = new Label();
        lblSystemPolice = new Label();
        lblSystemPiratesLabel = new Label();
        lblSystemPirates = new Label();
        lblSystemPressurePre = new Label();
        lblSystemPressure = new Label();

        controls.add(hireMercenaryButton);
        controls.add(specialButton);
        controls.add(newsButton);
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

        setSize(new Size(240, 206));
        setTabIndex(1);
        setTabStop(false);
        setText("System Info");

        lblSystemNameLabel.setAutoSize(true);
        lblSystemNameLabel.setFont(FontCollection.bold825);
        lblSystemNameLabel.setLocation(new Point(8, 16));
        lblSystemNameLabel.setSize(new Size(39, 16));
        lblSystemNameLabel.setTabIndex(0);
        lblSystemNameLabel.setText("Name:");

        lblSystemName.setLocation(new Point(88, 16));
        lblSystemName.setSize(new Size(65, 13));
        lblSystemName.setTabIndex(1);
        //lblSystemName.setText("Tarchannen");

        lblSystemSizeLabel.setAutoSize(true);
        lblSystemSizeLabel.setFont(FontCollection.bold825);
        lblSystemSizeLabel.setLocation(new Point(8, 32));
        lblSystemSizeLabel.setSize(new Size(31, 16));
        lblSystemSizeLabel.setTabIndex(2);
        lblSystemSizeLabel.setText("Size:");

        lblSystemSize.setLocation(new Point(88, 32));
        lblSystemSize.setSize(new Size(45, 13));
        lblSystemSize.setTabIndex(14);
        //lblSystemSize.setText("Medium");

        lblSystemTechLabel.setAutoSize(true);
        lblSystemTechLabel.setFont(FontCollection.bold825);
        lblSystemTechLabel.setLocation(new Point(8, 48));
        lblSystemTechLabel.setSize(new Size(65, 16));
        lblSystemTechLabel.setTabIndex(3);
        lblSystemTechLabel.setText("Tech Level:");

        lblSystemTech.setLocation(new Point(88, 48));
        lblSystemTech.setSize(new Size(82, 13));
        lblSystemTech.setTabIndex(13);
        //lblSystemTech.setText("Pre-Agricultural");

        lblSystemGovtLabel.setAutoSize(true);
        lblSystemGovtLabel.setFont(FontCollection.bold825);
        lblSystemGovtLabel.setLocation(new Point(8, 64));
        lblSystemGovtLabel.setSize(new Size(72, 16));
        lblSystemGovtLabel.setTabIndex(4);
        lblSystemGovtLabel.setText("Government:");

        lblSystemPolSys.setLocation(new Point(88, 64));
        lblSystemPolSys.setSize(new Size(91, 13));
        lblSystemPolSys.setTabIndex(15);
        //lblSystemPolSys.setText("Cybernetic State");

        lblSystemResourseLabel.setAutoSize(true);
        lblSystemResourseLabel.setFont(FontCollection.bold825);
        lblSystemResourseLabel.setLocation(new Point(8, 80));
        lblSystemResourseLabel.setSize(new Size(58, 16));
        lblSystemResourseLabel.setTabIndex(5);
        lblSystemResourseLabel.setText("Resource:");

        lblSystemResource.setLocation(new Point(88, 80));
        lblSystemResource.setSize(new Size(105, 13));
        lblSystemResource.setTabIndex(9);
        //lblSystemResource.setText("Sweetwater Oceans");

        lblSystemPoliceLabel.setAutoSize(true);
        lblSystemPoliceLabel.setFont(FontCollection.bold825);
        lblSystemPoliceLabel.setLocation(new Point(8, 96));
        lblSystemPoliceLabel.setSize(new Size(40, 16));
        lblSystemPoliceLabel.setTabIndex(6);
        lblSystemPoliceLabel.setText("Police:");

        lblSystemPolice.setLocation(new Point(88, 96));
        lblSystemPolice.setSize(new Size(53, 13));
        lblSystemPolice.setTabIndex(10);
        //lblSystemPolice.setText("Moderate");

        lblSystemPiratesLabel.setAutoSize(true);
        lblSystemPiratesLabel.setFont(FontCollection.bold825);
        lblSystemPiratesLabel.setLocation(new Point(8, 112));
        lblSystemPiratesLabel.setSize(new Size(44, 16));
        lblSystemPiratesLabel.setTabIndex(7);
        lblSystemPiratesLabel.setText("Pirates:");

        lblSystemPirates.setLocation(new Point(88, 112));
        lblSystemPirates.setSize(new Size(53, 13));
        lblSystemPirates.setTabIndex(11);
        //lblSystemPirates.setText("Abundant");


        lblSystemPressurePre.setAutoSize(true);
        lblSystemPressurePre.setLocation(new Point(8, 134));
        lblSystemPressurePre.setSize(new Size(122, 16));
        lblSystemPressurePre.setTabIndex(17);
        //lblSystemPressurePre.setText("This system is currently");

        lblSystemPressure.setLocation(new Point(8, 147));
        lblSystemPressure.setSize(new Size(168, 16));
        lblSystemPressure.setTabIndex(18);
        //lblSystemPressure.setText("suffering from extreme bordom.");


        newsButton.setFlatStyle(FlatStyle.FLAT);
        newsButton.setLocation(new Point(8, 174));
        newsButton.setSize(new Size(42, 22));
        newsButton.setTabIndex(1);
        newsButton.setText("News");
        newsButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnNews_Click();
            }
        });

        specialButton.setBackground(new Color(255, 255, 128));
        specialButton.setFlatStyle(FlatStyle.FLAT);
        specialButton.setLocation(new Point(58, 174));
        specialButton.setSize(new Size(52, 22));
        specialButton.setTabIndex(2);
        specialButton.setText("Special");
        specialButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                btnSpecial_Click();
            }
        });

        hireMercenaryButton.setFlatStyle(FlatStyle.FLAT);
        hireMercenaryButton.setLocation(new Point(118, 174));
        hireMercenaryButton.setSize(new Size(112, 22));
        hireMercenaryButton.setTabIndex(3);
        hireMercenaryButton.setText("Mercenary For Hire");
        hireMercenaryButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnMerc_Click();
            }
        });

        //TODO what is it???
        tipSpecial = new ToolTip();
        tipMerc = new ToolTip();
    }

    void update() {
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
            newsButton.setVisible(false);
            hireMercenaryButton.setVisible(false);
            specialButton.setVisible(false);
        } else {
            StarSystem system = commander.getCurrentSystem();
            CrewMember[] mercs = system.mercenariesForHire();

            lblSystemName.setText(system.name());
            lblSystemSize.setText(Strings.Sizes[system.Size().castToInt()]);
            lblSystemTech.setText(Strings.TechLevelNames[system.TechLevel().castToInt()]);
            lblSystemPolSys.setText(system.politicalSystem().name());
            lblSystemResource.setText(Strings.SpecialResources[system.specialResource().castToInt()]);
            lblSystemPolice.setText(Strings.ActivityLevels[system.politicalSystem().activityPolice().castToInt()]);
            lblSystemPirates.setText(Strings.ActivityLevels[system.politicalSystem().activityPirates().castToInt()]);
            lblSystemPressure.setText(Strings.SystemPressures[system.systemPressure().castToInt()]);
            lblSystemPressurePre.setVisible(true);
            newsButton.setVisible(true);
            hireMercenaryButton.setVisible(mercs.length > 0);
            if (hireMercenaryButton.getVisible()) {
                tipMerc.setToolTip(hireMercenaryButton, Functions.stringVars(Strings.MercenariesForHire,
                        mercs.length == 1 ? mercs[0].Name() : mercs.length + Strings.Mercenaries));
            }
            specialButton.setVisible(system.showSpecialButton());
            if (specialButton.getVisible()) {
                tipSpecial.setToolTip(specialButton, system.specialEvent().title());
            }
        }
    }

    private void btnMerc_Click() {
        (new FormViewPersonnel()).showDialog(mainWindow);
        mainWindow.updateAll();
    }

    private void btnNews_Click() {
        game.showNewspaper();
    }

    private void btnSpecial_Click() {
        SpecialEvent specEvent = commander.getCurrentSystem().specialEvent();
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

        FormAlert alert = new FormAlert(specEvent.title(), specEvent.string(), btn1, res1, btn2, res2, null);
        if (alert.showDialog() != DialogResult.NO) {
            if (commander.CashToSpend() < specEvent.Price())
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
}
