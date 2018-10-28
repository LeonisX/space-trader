/*******************************************************************************
 *
 * Space Trader for Windows 2.00
 *
 * Copyright (C) 2005 Jay French, All Rights Reserved
 *
 * Additional coding by David Pierron Original coding by Pieter Spronck, Sam Anderson, Samuel Goldstein, Matt Lee
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * If you'd like a copy of the GNU General Public License, go to http://www.gnu.org/copyleft/gpl.html.
 *
 * You can contact the author at spacetrader@frenchfryz.com
 *
 ******************************************************************************/

package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.controls.enums.Shortcut;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.GameEndType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.stub.Directory;
import spacetrader.stub.RegistryKey;
import spacetrader.util.Functions;
import spacetrader.util.ReflectionUtils;

import static spacetrader.controls.MenuItem.separator;

public class SpaceTrader extends WinformWindow implements MainWindow {

    private SystemPanel systemPanel;
    private DockPanel dockPanel;
    private CargoPanel cargoPanel;
    private ShipyardPanel shipyardPanel;
    private GalacticChartPanel galacticChartPanel;
    private ShortRangeChartPanel shortRangeChartPanel;
    private TargetSystemPanel targetSystemPanel;

    private SpaceTraderStatusBar statusBar;

    private MainMenu mainMenu = new MainMenu();

    private SubMenu gameSubMenu = new SubMenu();
    private MenuItem newGameMenuItem = new MenuItem();
    private MenuItem loadGameMenuItem = new MenuItem();
    private MenuItem saveGameMenuItem = new MenuItem();
    private MenuItem saveGameAsMenuItem = new MenuItem();
    private MenuItem retireGameMenuItem = new MenuItem();
    private MenuItem exitGameMenuItem = new MenuItem();

    private SubMenu viewSubMenu = new SubMenu();
    private MenuItem commanderMenuItem = new MenuItem();
    private MenuItem shipMenuItem = new MenuItem();
    private MenuItem personnelMenuItem = new MenuItem();
    private MenuItem questsMenuItem = new MenuItem();
    private MenuItem bankMenuItem = new MenuItem();
    private MenuItem highScoresMenuItem = new MenuItem();
    private SubMenu languagesSubMenu = new SubMenu();
    private MenuItem englishMenuItem = new MenuItem();
    private MenuItem russianMenuItem = new MenuItem();
    private MenuItem optionsMenuItem = new MenuItem();

    private SubMenu helpSubMenu = new SubMenu();
    private MenuItem aboutMenuItem = new MenuItem();

    private OpenFileDialog openFileDialog;
    private SaveFileDialog saveFileDialog;

    private HorizontalLine horizontalLine = new HorizontalLine();

    //TODO need???
    private IContainer components;

    private Game game;
    private GameController controller;
    private Commander commander;

    public SpaceTrader(String loadFileName) {
        initializeComponent();

        initFileStructure();

        if (loadFileName != null) {
            controller.loadGame(loadFileName);
        }

        updateAll();
    }

    private void initializeComponent() {
        ResourceManager resources = new ResourceManager(SpaceTrader.class);

        this.setName("mainWindow");

        initializeComponents();
        initializeMenu();
        initializeStatusBar();

        initializeDialogs();

        initializePictureBox();


        ReflectionUtils.setAllComponentNames(this);
        //this.suspendLayout();

        //this.setClientSize(GlobalAssets.getDimensions().getSize(this.getName()));
        //this.setAutoScaleBaseSize(new jwinforms.Size(5, 13));
        this.setClientSize(795, 505);
        controls.addAll(horizontalLine, dockPanel, cargoPanel, targetSystemPanel, galacticChartPanel,
                shortRangeChartPanel, systemPanel, shipyardPanel);

        setStatusBar(statusBar);
        this.setMenu(mainMenu);

        this.setFormBorderStyle(FormBorderStyle.FIXED_SINGLE);
        this.setIcon(((Icon) (resources.getObject("$this.Icon"))));
        this.setMaximizeBox(false);
        this.setStartPosition(FormStartPosition.MANUAL);
        this.setText(GlobalAssets.getStrings().getTitle(this.getName()));
        this.setClosing(new spacetrader.controls.EventHandler<Object, CancelEventArgs>() {
            @Override
            public void handle(Object sender, CancelEventArgs e) {
                spaceTraderClosing(e);
            }
        });
        this.setClosed(new spacetrader.controls.EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                spaceTraderClosed();
            }
        });

        this.setLoad(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                spaceTraderLoad();
            }
        });

        //ReflectionUtils.loadControlsData(this);
    }

    private void initializeComponents() {
        components = new Container();

        systemPanel = new SystemPanel(this);
        dockPanel = new DockPanel(this);
        cargoPanel = new CargoPanel();
        shipyardPanel = new ShipyardPanel(this);
        galacticChartPanel = new GalacticChartPanel(this, GlobalAssets.getChartImages());
        shortRangeChartPanel = new ShortRangeChartPanel(this, GlobalAssets.getChartImages());
        targetSystemPanel = new TargetSystemPanel(this);

        systemPanel.suspendLayout();
        dockPanel.suspendLayout();
        cargoPanel.suspendLayout();
        shipyardPanel.suspendLayout();
        galacticChartPanel.suspendLayout();
        shortRangeChartPanel.suspendLayout();
        targetSystemPanel.suspendLayout();

        // system   cargocargocargocargocargoc
        // system   cargocargocargocargocargoc
        // dock     cargocargocargocargocargoc

        // shipyard galactic shortRange target
        // shipyard galactic shortRange target

        systemPanel.initializeComponent();
        systemPanel.setLocation(4, 2);

        cargoPanel.initializeComponent();
        cargoPanel.setLocation(257, 2);

        dockPanel.initializeComponent();
        dockPanel.setLocation(4, 212);

        shipyardPanel.initializeComponent();
        shipyardPanel.setLocation(4, 306);

        galacticChartPanel.initializeComponent();
        galacticChartPanel.setLocation(200, 306);

        shortRangeChartPanel.initializeComponent();
        shortRangeChartPanel.setLocation(379, 306);

        targetSystemPanel.initializeComponent();
        targetSystemPanel.setLocation(558, 306);
    }

    private void initializeMenu() {
        mainMenu.addAll(gameSubMenu, viewSubMenu, helpSubMenu);

        gameSubMenu.addAll(newGameMenuItem, loadGameMenuItem, saveGameMenuItem, saveGameAsMenuItem, separator(), 
                retireGameMenuItem, separator(), exitGameMenuItem);

        //TODO remove all setText after dump all strings
        gameSubMenu.setText("&Game");

        newGameMenuItem.setText("&New...");
        newGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameNew_Click();
            }
        });

        loadGameMenuItem.setShortcut(Shortcut.CtrlL);
        loadGameMenuItem.setText("&Load...");
        loadGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                loadGameMenuItemClick();
            }
        });

        saveGameMenuItem.setEnabled(false);
        saveGameMenuItem.setShortcut(Shortcut.CtrlS);
        saveGameMenuItem.setText("&Save");
        saveGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                saveGameMenuItemClick();
            }
        });

        saveGameAsMenuItem.setEnabled(false);
        saveGameAsMenuItem.setShortcut(Shortcut.CtrlA);
        saveGameAsMenuItem.setText("Save &As...");
        saveGameAsMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                saveGameAsMenuItemClick();
            }
        });

        retireGameMenuItem.setEnabled(false);
        retireGameMenuItem.setText("&Retire");
        retireGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                retireGameMenuItemClick();
            }
        });

        exitGameMenuItem.setText("E&xit");
        exitGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                exitGameMenuItemClick();
            }
        });

        viewSubMenu.addAll(commanderMenuItem, shipMenuItem, personnelMenuItem, questsMenuItem, bankMenuItem, 
                separator(), highScoresMenuItem, separator(), languagesSubMenu, optionsMenuItem);

        viewSubMenu.setText("&View");

        commanderMenuItem.setEnabled(false);
        commanderMenuItem.setShortcut(Shortcut.CtrlC);
        commanderMenuItem.setText("&Commander Status");
        commanderMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                commanderMenuItemClick();
            }
        });

        shipMenuItem.setEnabled(false);
        shipMenuItem.setShortcut(Shortcut.CtrlH);
        shipMenuItem.setText("&Ship");
        shipMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                shipMenuItemClick();
            }
        });

        personnelMenuItem.setEnabled(false);
        personnelMenuItem.setShortcut(Shortcut.CtrlP);
        personnelMenuItem.setText("&Personnel");
        personnelMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                personnelMenuItemClick();
            }
        });

        questsMenuItem.setEnabled(false);
        questsMenuItem.setShortcut(Shortcut.CtrlQ);
        questsMenuItem.setText("&Quests");
        questsMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                questsMenuItemClick();
            }
        });

        bankMenuItem.setEnabled(false);
        bankMenuItem.setShortcut(Shortcut.CtrlB);
        bankMenuItem.setText("&Bank");
        bankMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                bankMenuItemClick();
            }
        });

        highScoresMenuItem.setText("&High Scores");
        highScoresMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                highScoresMenuItemClick();
            }
        });

        languagesSubMenu.setText("Languages");
        languagesSubMenu.addAll(englishMenuItem, russianMenuItem);

        englishMenuItem.setText("English");
        englishMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                languageMenuItemClick(sender);
            }
        });

        russianMenuItem.setText("Русский");
        russianMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                languageMenuItemClick(sender);
            }
        });

        optionsMenuItem.setText("Options");
        optionsMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                optionsMenuItemClick();
            }
        });

        helpSubMenu.add(aboutMenuItem);
        helpSubMenu.setText("&Help");

        aboutMenuItem.setText("&About Space Trader");
        aboutMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                aboutMenuItemClick();
            }
        });
    }

    private void initializeStatusBar() {
        statusBar = new SpaceTraderStatusBar(this);
        statusBar.beginInit();
        statusBar.initializeComponent();
    }

    private void initializePictureBox() {
        horizontalLine.setLocation(0, 0);
        horizontalLine.setSize(770, 1);
        horizontalLine.setTabStop(false);
    }

    private void initializeDialogs() {
        openFileDialog = new OpenFileDialog();
        saveFileDialog = new SaveFileDialog();

        if (!GlobalAssets.getStrings().isEmpty()) {
            openFileDialog.setFilter(GlobalAssets.getStrings().get("mainWindow.fileDialogs.filter"));
            openFileDialog.setTitle(GlobalAssets.getStrings().getTitle("mainWindow.openFileDialog.title"));
            openFileDialog.setApproveButtonText(GlobalAssets.getStrings().getText("mainWindow.openFileDialog.approveButton"));

            saveFileDialog.setFileName(GlobalAssets.getStrings().get("mainWindow.saveFileDialog.fileName"));
            saveFileDialog.setFilter(GlobalAssets.getStrings().get("mainWindow.fileDialogs.filter"));
            saveFileDialog.setTitle(GlobalAssets.getStrings().getTitle("mainWindow.saveFileDialog.title"));
            saveFileDialog.setApproveButtonText(GlobalAssets.getStrings().getText("mainWindow.saveFileDialog.approveButton"));
        }
    }

    private String getRegistrySetting(String settingName, String defaultValue) {
        String settingValue = defaultValue;

        try {
            RegistryKey key = Functions.getRegistryKey();
            Object objectValue = key.getValue(settingName);
            if (objectValue != null) {
                settingValue = objectValue.toString();
            }
            key.close();
        } catch (NullPointerException ex) {
            GuiFacade.alert(AlertType.RegistryError, ex.getMessage());
        }

        return settingValue;
    }

    // Make sure all directories exists.
    private void initFileStructure() {
        String[] paths = new String[]{Consts.CustomDirectory, Consts.CustomImagesDirectory,
                Consts.CustomTemplatesDirectory, Consts.DataDirectory, Consts.SaveDirectory};

        for (String path : paths) {
            if (!Directory.exists(path)) {
                Directory.createDirectory(path);
            }
        }

        openFileDialog.setInitialDirectory(Consts.SaveDirectory);
        saveFileDialog.setInitialDirectory(Consts.SaveDirectory);
    }

    public void setInGameControlsEnabled(boolean enabled) {
        saveGameMenuItem.setEnabled(enabled);
        saveGameAsMenuItem.setEnabled(enabled);
        retireGameMenuItem.setEnabled(enabled);
        commanderMenuItem.setEnabled(enabled);
        shipMenuItem.setEnabled(enabled);
        personnelMenuItem.setEnabled(enabled);
        questsMenuItem.setEnabled(enabled);
        bankMenuItem.setEnabled(enabled);
    }

    private void SetRegistrySetting(String settingName, String settingValue) {
        try {
            RegistryKey key = Functions.getRegistryKey();
            key.setValue(settingName, settingValue);
            key.close();
        } catch (NullPointerException ex) {
            GuiFacade.alert(AlertType.RegistryError, ex.getMessage());
        }
    }

    public void updateAll() {
        systemPanel.update();
        dockPanel.update();
        cargoPanel.update();
        shipyardPanel.update();
        galacticChartPanel.refresh();
        shortRangeChartPanel.refresh();
        targetSystemPanel.update();

        statusBar.update();
    }

    private void spaceTraderClosing(CancelEventArgs e) {
        if (game == null || commander.getDays() == controller.saveGameDays
                || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES) {
            if (windowState == FormWindowState.NORMAL) {
                SetRegistrySetting("X", left.toString());
                SetRegistrySetting("Y", top.toString());
            }
        } else {
            e.setCancel(true);
        }
    }

    private void spaceTraderClosed() {
        FormsOwnerTree.pop(this);
    }

    private void spaceTraderLoad() {
        left = Integer.parseInt(getRegistrySetting("X", "0"));
        top = Integer.parseInt(getRegistrySetting("Y", "0"));

        FormsOwnerTree.add(this);

        GuiFacade.alert(AlertType.AppStart);
    }

    private void exitGameMenuItemClick() {
        close();
    }

    private void mnuGameNew_Click() {
        FormNewCommander form = new FormNewCommander();
        if ((game == null || commander.getDays() == controller.saveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES)
                && form.showDialog(this) == DialogResult.OK) {
            setGame(new Game(form.getCommanderName(), form.getDifficulty(), form.getPilot(), form.getFighter(), form.getTrader(), form
                    .getEngineer(), this));
            //TODO
            controller.saveGameFile = null;
            controller.saveGameDays = 0;

            setInGameControlsEnabled(true);
            updateAll();

            if (game.getOptions().isNewsAutoShow()) {
                game.showNewspaper();
            }
        }
    }

    private void loadGameMenuItemClick() {
        if ((game == null || commander.getDays() == controller.saveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES)
                && openFileDialog.showDialog(this) == DialogResult.OK) {
            controller.loadGame(openFileDialog.getFileName());
        }
    }

    private void saveGameMenuItemClick() {
        if (Game.getCurrentGame() != null) {
            if (controller.saveGameFile != null) {
                controller.saveGame(controller.saveGameFile, false);
            } else {
                saveGameAsMenuItemClick();
            }
        }
    }

    private void saveGameAsMenuItemClick() {
        if (Game.getCurrentGame() != null && saveFileDialog.showDialog(this) == DialogResult.OK) {
            controller.saveGame(saveFileDialog.getFileName(), true);
        }
    }

    private void aboutMenuItemClick() {
        (new FormAbout()).showDialog(this);
    }

    private void highScoresMenuItemClick() {
        (new FormViewHighScores()).showDialog(this);
    }

    private void optionsMenuItemClick() {
        FormOptions form = new FormOptions();
        if (form.showDialog(this) == DialogResult.OK) {
            game.getOptions().copyValues(form.getOptions());
            updateAll();
        }
    }

    private void retireGameMenuItemClick() {
        if (GuiFacade.alert(AlertType.GameRetire) == DialogResult.YES) {
            game.setEndStatus(GameEndType.RETIRED);
            controller.gameEnd();
            updateAll();
        }
    }

    private void bankMenuItemClick() {
        showBank();
    }

    void showBank() {
        (new FormViewBank()).showDialog(this);
    }

    private void commanderMenuItemClick() {
        (new FormViewCommander()).showDialog(this);
    }

    private void personnelMenuItemClick() {
        (new FormViewPersonnel()).showDialog(this);
    }

    private void questsMenuItemClick() {
        (new FormViewQuests()).showDialog(this);
    }

    private void shipMenuItemClick() {
        (new FormViewShip()).showDialog(this);
    }

    private void languageMenuItemClick(Object sender) {
        String fileName = ((String) sender).replace("MenuItem", "").toLowerCase();

        GlobalAssets.loadStrings(fileName);

        ReflectionUtils.loadControlsStrings(getFrame(), getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());

        updateAll();
    }

    public void setGame(Game game) {
        this.game = game;
        controller = new GameController(game, this);
        commander = (game == null) ? null : game.getCommander();

        dockPanel.setGame(commander);
        cargoPanel.setGame(game, controller);
        targetSystemPanel.setGame(game, controller, commander);
        galacticChartPanel.setGame(game, controller, commander);
        shortRangeChartPanel.setGame(game, commander);
        systemPanel.setGame(game, controller, commander);
        shipyardPanel.setGame(commander);
        statusBar.setGame(commander);
    }

    // TODO remove?
    public void updateStatusBar() {
        statusBar.update();
    }

}
