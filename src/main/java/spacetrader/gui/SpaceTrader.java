package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.MenuItem;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.FormBorderStyle;
import spacetrader.controls.enums.FormStartPosition;
import spacetrader.controls.enums.Shortcut;
import spacetrader.game.*;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.GameEndType;
import spacetrader.game.enums.Language;
import spacetrader.gui.cheat.FormMonster;
import spacetrader.gui.debug.FormAlertTest;
import spacetrader.gui.debug.FormsTest;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.util.IOUtils;
import spacetrader.util.ReflectionUtils;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Logger;

import static spacetrader.controls.MenuItem.separator;
import static spacetrader.game.enums.Language.ENGLISH;
import static spacetrader.game.enums.Language.RUSSIAN;

public class SpaceTrader extends WinformWindow implements MainWindow {

    private static final Logger log = Logger.getLogger(SpaceTrader.class.getName());

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

    private SubMenu cheatsSubMenu = new SubMenu();
    private MenuItem formMonsterMenuItem = new MenuItem();
    private MenuItem formShipyardMenuItem = new MenuItem();
    private MenuItem formGigaGaiaMenuItem = new MenuItem();
    private MenuItem formTestMenuItem = new MenuItem();
    private MenuItem formAlertTestMenuItem = new MenuItem();

    private SubMenu helpSubMenu = new SubMenu();
    private MenuItem aboutMenuItem = new MenuItem();

    private OpenFileDialog openFileDialog;
    private SaveFileDialog saveFileDialog;

    private HorizontalLine horizontalLine = new HorizontalLine();

    private Game game;
    private GameController controller;
    private Commander commander;

    public SpaceTrader(String loadFileName) {
        initializeComponent();

        initFileStructure();

        if (loadFileName != null) {
            controller = GameController.loadGame(loadFileName, this);
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

        //this.setClientSize(GlobalAssets.getDimensions().getSize(this.getName()));
        this.setClientSize(798, 505);
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

        getFrame().addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                spaceTraderWindowClosing(we);
            }
        });

        //ReflectionUtils.loadControlsData(this);
    }

    private void initializeComponents() {
        systemPanel = new SystemPanel(this);
        dockPanel = new DockPanel(this);
        cargoPanel = new CargoPanel();
        shipyardPanel = new ShipyardPanel(this);
        galacticChartPanel = new GalacticChartPanel(this, GlobalAssets.getChartImages());
        shortRangeChartPanel = new ShortRangeChartPanel(this, GlobalAssets.getChartImages());
        targetSystemPanel = new TargetSystemPanel(this);

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
        shortRangeChartPanel.setLocation(394, 306);

        targetSystemPanel.initializeComponent();
        targetSystemPanel.setLocation(573, 306);
    }

    private void initializeMenu() {
        mainMenu.addAll(gameSubMenu, viewSubMenu, cheatsSubMenu, helpSubMenu);

        gameSubMenu.addAll(newGameMenuItem, loadGameMenuItem, saveGameMenuItem, saveGameAsMenuItem, separator(), 
                retireGameMenuItem, separator(), exitGameMenuItem);

        //TODO remove all setText after dump all strings
        gameSubMenu.setText("&Game");

        newGameMenuItem.setText("&New...");
        newGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                newGameMenuItemClick();
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
                languageMenuItemClick(ENGLISH);
            }
        });

        russianMenuItem.setText("Русский");
        russianMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                languageMenuItemClick(RUSSIAN);
            }
        });

        optionsMenuItem.setText("Options");
        optionsMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                optionsMenuItemClick();
            }
        });


        cheatsSubMenu.addAll(formMonsterMenuItem, formShipyardMenuItem, formGigaGaiaMenuItem, separator(),
                formTestMenuItem, formAlertTestMenuItem);

        cheatsSubMenu.setText("Cheats");

        formMonsterMenuItem.setEnabled(false);
        formMonsterMenuItem.setText("Monster.com");
        formMonsterMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                formMonsterMenuClick();
            }
        });

        formShipyardMenuItem.setEnabled(false);
        formShipyardMenuItem.setText("Shipyard");
        formShipyardMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                new FormShipyard(0).showDialog();
            }
        });

        formGigaGaiaMenuItem.setEnabled(false);
        formGigaGaiaMenuItem.setText("Giga Gaia");
        formGigaGaiaMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                commander.initializeGigaGaia();
                updateAll();
            }
        });


        //formTestMenuItem.setEnabled(false);
        formTestMenuItem.setText("Forms test");
        formTestMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                new FormsTest().showDialog();
            }
        });

        //formAlertTestMenuItem.setEnabled(false);
        formAlertTestMenuItem.setText("Alerts test");
        formAlertTestMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                new FormAlertTest().showDialog();
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
        statusBar.initializeComponent();
    }

    private void initializePictureBox() {
        horizontalLine.setLocation(0, 0);
        horizontalLine.setSize(770, 1);
        horizontalLine.setTabStop(false);
    }

    private void initializeDialogs() {
        openFileDialog = new OpenFileDialog(getName());
        saveFileDialog = new SaveFileDialog(getName());
    }

    // Make sure all directories exists.
    private void initFileStructure() {
        String[] paths = new String[]{Consts.CustomDirectory, Consts.CustomImagesDirectory,
                Consts.CustomTemplatesDirectory, Consts.DataDirectory, Consts.SaveDirectory};

        for (String path : paths) {
            if (!IOUtils.exists(path)) {
                IOUtils.createDirectory(path);
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

        formMonsterMenuItem.setEnabled(enabled);
        formShipyardMenuItem.setEnabled(enabled);
        formGigaGaiaMenuItem.setEnabled(enabled);

        systemPanel.update();
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
        if (game == null || commander.getDays() == controller.getSaveGameDays()
                || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES) {
            e.setCancel(false);
        } else {
            e.setCancel(true);
        }
    }

    private void spaceTraderWindowClosing(WindowEvent we) {
        if (we.getID() == WindowEvent.WINDOW_CLOSING && getFrame().getState() == Frame.NORMAL) {
            IOUtils.setRegistrySetting("x", Integer.toString(getFrame().getX()));
            IOUtils.setRegistrySetting("y", Integer.toString(getFrame().getY()));
        }
    }

    private void spaceTraderClosed() {
        FormsOwnerTree.pop(this);
    }

    private void spaceTraderLoad() {
        left = Integer.parseInt(IOUtils.getRegistrySetting("x", "-1"));
        top = Integer.parseInt(IOUtils.getRegistrySetting("y", "-1"));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        if (left < 0 || left + getWidth() > screenSize.getWidth()) {
            left = (int) (screenSize.getWidth() - getWidth()) / 2;
        }

        if (top < 0 || top + getHeight() > screenSize.getHeight()) {
            top = (int) (screenSize.getHeight() - getHeight()) / 2;
        }

        FormsOwnerTree.add(this);

        setLeft(left);
        setTop(top);

        if (!GlobalAssets.isDebug()) {
            GuiFacade.alert(AlertType.AppStart);
        }
    }

    private void exitGameMenuItemClick() {
        close();
    }

    public void newGameMenuItemClick() {
        FormNewCommander form = new FormNewCommander();
        if ((game == null || commander.getDays() == controller.getSaveGameDays() || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES)
                && form.showDialog(this) == DialogResult.OK) {
            setGame(new Game(form.getCommanderName(), form.getDifficulty(), form.getPilot(), form.getFighter(), form.getTrader(), form
                    .getEngineer(), this));

            controller.setSaveGameFile(null);
            controller.setSaveGameDays(0);

            setInGameControlsEnabled(true);
            updateAll();

            if (game.getOptions().isNewsAutoShow()) {
                game.showNewspaper();
            }
        }
    }

    private void loadGameMenuItemClick() {
        if ((game == null || commander.getDays() == controller.getSaveGameDays() || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES)
                && openFileDialog.showDialog(this) == DialogResult.OK) {

            controller = GameController.loadGame(openFileDialog.getFileName(), this);
            log.finest("Loaded: " + Game.getCurrentGame().getQuestSystem().toString());
        }
    }

    private void saveGameMenuItemClick() {
        if (Game.getCurrentGame() != null) {
            if (controller.getSaveGameFile() != null) {
                log.finest(Game.getCurrentGame().getQuestSystem().toString());
                controller.saveGame(controller.getSaveGameFile(), false);
            } else {
                saveGameAsMenuItemClick();
            }
        }
    }

    private void saveGameAsMenuItemClick() {
        if (Game.getCurrentGame() != null && saveFileDialog.showDialog(this) == DialogResult.OK) {
            log.finest("Saved: " + Game.getCurrentGame().getQuestSystem().toString());
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

    private void formMonsterMenuClick() {
        new FormMonster().showDialog(this);
    }

    private void retireGameMenuItemClick() {
        if (GuiFacade.alert(AlertType.GameRetire) == DialogResult.YES) {
            game.setEndStatus(GameEndType.RETIRED.castToInt());
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

    private void languageMenuItemClick(Language language) {
        GlobalAssets.setLanguage(language);
        GlobalAssets.loadStrings();

        ReflectionUtils.loadControlsStrings(getFrame(), getName(), GlobalAssets.getStrings());
        ReflectionUtils.loadStrings(GlobalAssets.getStrings());

        if (game != null) {
            game.getQuestSystem().localizeQuests();
        }

        updateAll();
    }

    //TODO need??????
    public void setGame(Game game) {
        this.game = game;
        controller = new GameController(game, this);
        commander = (game == null) ? null : game.getCommander();

        dockPanel.setGame(commander);
        cargoPanel.setGame(controller);
        targetSystemPanel.setGame(game, controller, commander);
        galacticChartPanel.setGame(game, controller, commander);
        shortRangeChartPanel.setGame(game, commander);
        systemPanel.setGame(game, commander);
        shipyardPanel.setGame(commander);
        statusBar.setGame(commander);
    }

    // TODO remove?
    public void updateStatusBar() {
        statusBar.update();
    }

}
