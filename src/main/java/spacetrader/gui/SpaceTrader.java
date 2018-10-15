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
import spacetrader.controls.Container;
import spacetrader.controls.Icon;
import spacetrader.controls.Image;
import spacetrader.controls.MenuItem;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.GameEndType;
import spacetrader.game.enums.ShipType;
import spacetrader.game.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.stub.Directory;
import spacetrader.stub.RegistryKey;
import spacetrader.stub.StringsMap;
import spacetrader.stub.ValuesMap;
import spacetrader.stub.PropertiesLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

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

    private ImageList ilChartImages;
    private ImageList ilDirectionImages;
    private ImageList ilEquipmentImages;
    private ImageList ilShipImages;

    private MainMenu mainMenu;

    private SubMenu gameSubMenu;
    private MenuItem newGameMenuItem;
    private MenuItem loadGameMenuItem;
    private MenuItem saveGameMenuItem;
    private MenuItem saveGameAsMenuItem;
    private MenuItem retireGameMenuItem;
    private MenuItem exitGameMenuItem;

    private SubMenu viewSubMenu;
    private MenuItem commanderMenuItem;
    private MenuItem shipMenuItem;
    private MenuItem personnelMenuItem;
    private MenuItem questsMenuItem;
    private MenuItem bankMenuItem;
    private MenuItem highScoresMenuItem;
    private MenuItem optionsMenuItem;

    private SubMenu helpSubMenu;
    private MenuItem aboutMenuItem;

    private OpenFileDialog openFileDialog;
    private SaveFileDialog saveFileDialog;

    private PictureBox linePictureBox;

    //TODO need???
    private IContainer components;

    private Game game;
    private GameController controller;
    private Commander commander;

    private StringsMap strings;
    private ValuesMap dimensions;

    public SpaceTrader(String loadFileName) {
        initializeComponent();

        initFileStructure();

        if (loadFileName != null) {
            controller.loadGame(loadFileName);
        }

        updateAll();

        dumpDimensions(getFrame(), this.getName());
        System.out.println("===================");
        dumpStrings(getFrame(), this.getName());

        //loadDimensions(getFrame(), this.getName());
        //loadStrings(getFrame(), this.getName());
    }

    private void initializeComponent() {
        ResourceManager resources = new ResourceManager(SpaceTrader.class);

        strings = PropertiesLoader.getStringsMap("strings/en.properties");
        dimensions = PropertiesLoader.getValuesMap("dimensions/0768.properties");

        initializeImages(resources);

        this.setName("mainWindow");

        initializeComponents();
        initializeMenu();
        initializeStatusBar();

        initializeDialogs();

        initializePictureBox();

        //this.suspendLayout();

        this.setClientSize(dimensions.getSize(this.getName()));
        controls.add(linePictureBox);
        controls.add(dockPanel);
        controls.add(cargoPanel);
        controls.add(targetSystemPanel);
        controls.add(galacticChartPanel);
        controls.add(shortRangeChartPanel);

        controls.add(systemPanel);
        controls.add(shipyardPanel);

        setStatusBar(statusBar);
        this.setMenu(mainMenu);

        this.setFormBorderStyle(FormBorderStyle.FixedSingle);
        this.setIcon(((Icon) (resources.getObject("$this.Icon"))));
        this.setMaximizeBox(false);
        this.setStartPosition(FormStartPosition.Manual);
        this.setText(strings.get(this.getName() + ".title"));
        this.setClosing(new spacetrader.controls.EventHandler<Object, CancelEventArgs>() {
            @Override
            public void handle(Object sender, CancelEventArgs e) {
                SpaceTrader_Closing(e);
            }
        });
        this.setClosed(new spacetrader.controls.EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                SpaceTrader_Closed();
            }
        });

        this.setLoad(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                SpaceTrader_Load();
            }
        });

    }

    private void dumpDimensions(Component component, String prefix) {
        component.getSize();
        component.getLocation();

        System.out.println(formatPropertyName(prefix) + ".x=" + component.getX());
        System.out.println(formatPropertyName(prefix) + ".y=" + component.getY());
        System.out.println(formatPropertyName(prefix) + ".width=" + component.getWidth());
        System.out.println(formatPropertyName(prefix) + ".height=" + component.getHeight());

        if (component instanceof java.awt.Container) {
            for (Component child: ((java.awt.Container) component).getComponents()) {
                String name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                dumpDimensions(child, prefix + "." + name);
            }
        }
    }

    private String formatPropertyName(String prefix) {
        return prefix.replace(".JRootPane", "")
                .replace(".null.glassPane", "")
                .replace(".null.layeredPane", "")
                .replace(".null.contentPane", "")
                .replace(".WinformJPanel", "");
    }

    private void dumpStrings(Component component, String prefix) {
        component.getSize();
        component.getLocation();

        if (component instanceof AbstractButton) {
            print(formatPropertyName(prefix) + ".text", ((AbstractButton) component).getText());
        }
        if (component instanceof JLabel) {
            print(formatPropertyName(prefix) + ".text", ((JLabel) component).getText());
        }
        if (component instanceof JPanel && ((JPanel) component).getBorder() instanceof TitledBorder) {
            print(formatPropertyName(prefix) + ".title", ((TitledBorder)((JPanel) component).getBorder()).getTitle());
        }
        if (component instanceof JFrame) {
            print(formatPropertyName(prefix) + ".title", ((JFrame) component).getTitle());
        }

        if (component instanceof JMenu) {
            for (Component child: ((JMenu) component).getMenuComponents()) {
                String name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                dumpStrings(child, prefix + "." + name);
            }
        }

        if (component instanceof java.awt.Container) {
            for (Component child: ((java.awt.Container) component).getComponents()) {
                String name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                dumpStrings(child, prefix + "." + name);
            }
        }
    }

    private void print(String key, String value) {
        //TODO button names
        if (!value.isEmpty() && !key.contains("btnBuyQty") && !key.contains("btnSellQty")) {
            System.out.println(key + "=" + value);
        }
    }

    void loadDimensions(Component component, String prefix) {
        if (component.getName() != null && !component.getName().startsWith("null.")) {
            double scale = 1.5;
            //TODO delete
            if (dimensions.get(formatPropertyName(prefix) + ".width") != null) {
                Dimension dimension = dimensions.getSize(formatPropertyName(prefix));
                dimension.setSize(dimension.getWidth() * scale, dimension.getHeight() * scale);
                component.setSize(dimension);
                if (!(component instanceof JFrame)) {
                    Point point = dimensions.getLocation(formatPropertyName(prefix));
                    point.setLocation(point.getX() * scale, point.getY() * scale);
                    component.setLocation(point);
                }
            } else {
                System.out.println("!!!" + prefix);
            }
        }
        if (component instanceof java.awt.Container) {
            for (Component child: ((java.awt.Container) component).getComponents()) {
                String name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                loadDimensions(child, prefix + "." + name);
            }
        }
    }


    void loadStrings(Component component, String prefix) {
        if (component instanceof JFrame) {
            ((JFrame) component).setTitle(strings.getTitle(formatPropertyName(prefix)));
        }

        if (component instanceof AbstractButton) {
            ((AbstractButton) component).setText(strings.getText(formatPropertyName(prefix)));
        }

        if (component instanceof JLabel) {
            ((JLabel) component).setText(strings.getText(formatPropertyName(prefix)));
        }

        if (component instanceof JPanel && ((JPanel) component).getBorder() instanceof TitledBorder) {
            ((TitledBorder)((JPanel) component).getBorder()).setTitle(strings.getTitle(formatPropertyName(prefix)));
        }

        if (component instanceof JMenu) {
            for (Component child: ((JMenu) component).getMenuComponents()) {
                String name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                loadStrings(child, prefix + "." + name);
            }
        }

        if (component instanceof java.awt.Container) {
            for (Component child: ((java.awt.Container) component).getComponents()) {
                String name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                loadStrings(child, prefix + "." + name);
            }
        }
    }

    private void initializeMenu() {
        mainMenu = new MainMenu("mainMenu");

        gameSubMenu = new SubMenu("gameSubMenu");
        newGameMenuItem = new MenuItem("newGameMenuItem");
        loadGameMenuItem = new MenuItem("loadGameMenuItem");
        saveGameMenuItem = new MenuItem("saveGameMenuItem");
        saveGameAsMenuItem = new MenuItem("saveGameAsMenuItem");
        retireGameMenuItem = new MenuItem("retireGameMenuItem");
        exitGameMenuItem = new MenuItem("exitGameMenuItem");

        viewSubMenu = new SubMenu("viewSubMenu");
        commanderMenuItem = new MenuItem("commanderMenuItem");
        shipMenuItem = new MenuItem("shipMenuItem");
        personnelMenuItem = new MenuItem("personnelMenuItem");
        questsMenuItem = new MenuItem("questsMenuItem");
        bankMenuItem = new MenuItem("bankMenuItem");
        highScoresMenuItem = new MenuItem("highScoresMenuItem");
        optionsMenuItem = new MenuItem("optionsMenuItem");

        helpSubMenu = new SubMenu("helpSubMenu");
        aboutMenuItem = new MenuItem("aboutMenuItem");

        mainMenu.addAll(gameSubMenu, viewSubMenu, helpSubMenu);

        gameSubMenu.addAll(newGameMenuItem, loadGameMenuItem, saveGameMenuItem, saveGameAsMenuItem, separator(), retireGameMenuItem, separator(),
                exitGameMenuItem);

        //TODO remove all setText after dump all strings
        gameSubMenu.setText("&Game");

        newGameMenuItem.setText("&New...");
        newGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameNew_Click();
            }
        });

        loadGameMenuItem.shortcut = Shortcut.CtrlL;
        loadGameMenuItem.setText("&Load...");
        loadGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameLoad_Click();
            }
        });

        saveGameMenuItem.setEnabled(false);
        saveGameMenuItem.shortcut = Shortcut.CtrlS;
        saveGameMenuItem.setText("&Save");
        saveGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameSave_Click();
            }
        });

        saveGameAsMenuItem.setEnabled(false);
        saveGameAsMenuItem.shortcut = Shortcut.CtrlA;
        saveGameAsMenuItem.setText("Save &As...");
        saveGameAsMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameSaveAs_Click();
            }
        });

        retireGameMenuItem.setEnabled(false);
        retireGameMenuItem.setText("&Retire");
        retireGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuRetire_Click();
            }
        });

        exitGameMenuItem.setText("E&xit");
        exitGameMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameExit_Click();
            }
        });

        viewSubMenu.addAll(commanderMenuItem, shipMenuItem, personnelMenuItem, questsMenuItem, bankMenuItem, separator(),
                highScoresMenuItem, separator(), optionsMenuItem);

        viewSubMenu.setText("&View");

        commanderMenuItem.setEnabled(false);
        commanderMenuItem.shortcut = Shortcut.CtrlC;
        commanderMenuItem.setText("&Commander Status");
        commanderMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewCommander_Click();
            }
        });

        shipMenuItem.setEnabled(false);
        shipMenuItem.shortcut = Shortcut.CtrlH;
        shipMenuItem.setText("&Ship");
        shipMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewShip_Click();
            }
        });

        personnelMenuItem.setEnabled(false);
        personnelMenuItem.shortcut = Shortcut.CtrlP;
        personnelMenuItem.setText("&Personnel");
        personnelMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewPersonnel_Click();
            }
        });

        questsMenuItem.setEnabled(false);
        questsMenuItem.shortcut = Shortcut.CtrlQ;
        questsMenuItem.setText("&Quests");
        questsMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewQuests_Click();
            }
        });

        bankMenuItem.setEnabled(false);
        bankMenuItem.shortcut = Shortcut.CtrlB;
        bankMenuItem.setText("&Bank");
        bankMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewBank_Click();
            }
        });

        highScoresMenuItem.setText("&High Scores");
        highScoresMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuHighScores_Click();
            }
        });

        optionsMenuItem.setText("Options");
        optionsMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuOptions_Click();
            }
        });

        helpSubMenu.add(aboutMenuItem);
        helpSubMenu.setText("&Help");

        aboutMenuItem.setText("&About Space Trader");
        aboutMenuItem.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuHelpAbout_Click();
            }
        });
    }

    private void initializeStatusBar() {
        statusBar = new SpaceTraderStatusBar(this, "statusBar");
        statusBar.beginInit();
        statusBar.initializeComponent();
    }

    private void initializeComponents() {
        components = new Container();

        systemPanel = new SystemPanel(this, "systemPanel");
        dockPanel = new DockPanel(this, "dockPanel");
        cargoPanel = new CargoPanel(this, "cargoPanel");
        shipyardPanel = new ShipyardPanel(this, "shipyardPanel");
        galacticChartPanel = new GalacticChartPanel(this, ilChartImages, "galacticChartPanel");
        shortRangeChartPanel = new ShortRangeChartPanel(this, ilChartImages, "shortRangeChartPanel");
        targetSystemPanel = new TargetSystemPanel(this, "targetSystemPanel");

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
        systemPanel.setLocation(new Point(4, 2));

        cargoPanel.initializeComponent(strings);
        cargoPanel.setLocation(new Point(252, 2));

        dockPanel.initializeComponent();
        dockPanel.setLocation(new Point(4, 212));

        shipyardPanel.initializeComponent();
        shipyardPanel.setLocation(new Point(4, 306));

        galacticChartPanel.initializeComponent();
        galacticChartPanel.setLocation(new Point(180, 306));

        shortRangeChartPanel.initializeComponent();
        shortRangeChartPanel.setLocation(new Point(364, 306));

        targetSystemPanel.initializeComponent();
        targetSystemPanel.setLocation(new Point(548, 306));
    }

    private void initializePictureBox() {
        linePictureBox = new PictureBox("linePictureBox");
        linePictureBox.setBackground(Color.DARK_GRAY);
        linePictureBox.setLocation(new Point(0, 0));
        linePictureBox.setSize(new Size(770, 1));
        linePictureBox.setTabStop(false);
    }

    private void initializeDialogs() {
        openFileDialog = new OpenFileDialog();
        saveFileDialog = new SaveFileDialog();

        openFileDialog.setFilter(strings.get("mainWindow.fileDialogs.filter"));
        openFileDialog.setTitle(strings.getTitle("mainWindow.openFileDialog"));
        openFileDialog.setApproveButtonText(strings.getText("mainWindow.openFileDialog.approveButton"));

        saveFileDialog.setFileName(strings.get("mainWindow.saveFileDialog.fileName"));
        saveFileDialog.setFilter(strings.get("mainWindow.fileDialogs.filter"));
        saveFileDialog.setTitle(strings.getTitle("mainWindow.saveFileDialog"));
        saveFileDialog.setApproveButtonText(strings.getText("mainWindow.saveFileDialog.approveButton"));
    }

    private void initializeImages(ResourceManager resources) {
        ilChartImages = new ImageList(components);
        ilShipImages = new ImageList(components);
        ilDirectionImages = new ImageList(components);
        ilEquipmentImages = new ImageList(components);

        ilChartImages.setImageSize(new Size(7, 7));
        ilChartImages.setImageStream(((ImageListStreamer) (resources.getObject("ilChartImages.ImageStream"))));
        ilChartImages.setTransparentColor(Color.white);

        ilShipImages.setImageSize(new Size(64, 52));
        ilShipImages.setImageStream(((ImageListStreamer) (resources.getObject("ilShipImages.ImageStream"))));
        ilShipImages.setTransparentColor(Color.white);

        ilDirectionImages.setImageSize(new Size(13, 13));
        ilDirectionImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilDirectionImages.ImageStream"))));
        ilDirectionImages.setTransparentColor(Color.white);

        ilEquipmentImages.setImageSize(new Size(64, 52));
        ilEquipmentImages.setImageStream(((ImageListStreamer) (resources
                .getObject("ilEquipmentImages.ImageStream"))));
        ilEquipmentImages.setTransparentColor(Color.white);
    }


    private String getRegistrySetting(String settingName, String defaultValue) {
        String settingValue = defaultValue;

        try {
            RegistryKey key = Functions.GetRegistryKey();
            Object ObjectValue = key.GetValue(settingName);
            if (ObjectValue != null) {
                settingValue = ObjectValue.toString();
            }
            key.Close();
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
            if (!Directory.Exists(path)) {
                Directory.CreateDirectory(path);
            }
        }

        openFileDialog.setInitialDirectory(Consts.SaveDirectory);
        saveFileDialog.setInitialDirectory(Consts.SaveDirectory);
    }

    public void SetInGameControlsEnabled(boolean enabled) {
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
            RegistryKey key = Functions.GetRegistryKey();
            key.SetValue(settingName, settingValue);
            key.Close();
        } catch (NullPointerException ex) {
            GuiFacade.alert(AlertType.RegistryError, ex.getMessage());
        }
    }

    public void updateAll() {
        systemPanel.update();
        dockPanel.update();
        cargoPanel.update();
        shipyardPanel.Update();
        galacticChartPanel.Refresh();
        shortRangeChartPanel.Refresh();
        targetSystemPanel.Update();

        statusBar.update();
    }

    private void SpaceTrader_Closing(spacetrader.controls.CancelEventArgs e) {
        if (game == null || commander.getDays() == controller.SaveGameDays
                || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES) {
            if (windowState == FormWindowState.Normal) {
                SetRegistrySetting("X", left.toString());
                SetRegistrySetting("Y", top.toString());
            }
        } else {
            e.Cancel = true;
        }
    }

    private void SpaceTrader_Closed() {
        FormsOwnerTree.pop(this);
    }

    private void SpaceTrader_Load() {
        left = Integer.parseInt(getRegistrySetting("X", "0"));
        top = Integer.parseInt(getRegistrySetting("Y", "0"));

        FormsOwnerTree.add(this);

        GuiFacade.alert(AlertType.AppStart);
    }

    private void mnuGameExit_Click() {
        close();
    }

    private void mnuGameNew_Click() {
        FormNewCommander form = new FormNewCommander();
        if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES)
                && form.showDialog(this) == DialogResult.OK) {
            setGame(new Game(form.CommanderName(), form.Difficulty(), form.Pilot(), form.Fighter(), form.Trader(), form
                    .Engineer(), this));
            controller.SaveGameFile = null;
            controller.SaveGameDays = 0;

            SetInGameControlsEnabled(true);
            updateAll();

            if (game.Options().getNewsAutoShow()) {
                game.showNewspaper();
            }
        }
    }

    private void mnuGameLoad_Click() {
        if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.YES)
                && openFileDialog.showDialog(this) == DialogResult.OK) {
            controller.loadGame(openFileDialog.getFileName());
        }
    }

    private void mnuGameSave_Click() {
        if (Game.currentGame() != null) {
            if (controller.SaveGameFile != null) {
                controller.saveGame(controller.SaveGameFile, false);
            } else {
                mnuGameSaveAs_Click();
            }
        }
    }

    private void mnuGameSaveAs_Click() {
        if (Game.currentGame() != null && saveFileDialog.showDialog(this) == DialogResult.OK) {
            controller.saveGame(saveFileDialog.getFileName(), true);
        }
    }

    private void mnuHelpAbout_Click() {
        (new FormAbout()).showDialog(this);
    }

    private void mnuHighScores_Click() {
        (new FormViewHighScores()).showDialog(this);
    }

    private void mnuOptions_Click() {
        FormOptions form = new FormOptions();
        if (form.showDialog(this) == DialogResult.OK) {
            game.Options().CopyValues(form.Options());
            updateAll();
        }
    }

    private void mnuRetire_Click() {
        if (GuiFacade.alert(AlertType.GameRetire) == DialogResult.YES) {
            game.setEndStatus(GameEndType.Retired);
            controller.gameEnd();
            updateAll();
        }
    }

    private void mnuViewBank_Click() {
        showBank();
    }

    void showBank() {
        (new FormViewBank()).showDialog(this);
    }

    private void mnuViewCommander_Click() {
        (new FormViewCommander()).showDialog(this);
    }

    private void mnuViewPersonnel_Click() {
        (new FormViewPersonnel()).showDialog(this);
    }

    private void mnuViewQuests_Click() {
        (new FormViewQuests()).showDialog(this);
    }

    private void mnuViewShip_Click() {
        (new FormViewShip()).showDialog(this);
    }

    Image[] customShipImages() {
        Image[] images = new Image[Consts.ImagesPerShip];
        int baseIndex = ShipType.Custom.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++) {
            images[index] = ilShipImages.getImages()[baseIndex + index];
        }
        return images;
    }

    public void customShipImages(Image[] value) {
        int baseIndex = ShipType.Custom.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++) {
            ilShipImages.getImages()[baseIndex + index] = value[index];
        }
    }

    ImageList directionImages() {
        return ilDirectionImages;
    }

    ImageList equipmentImages() {
        return ilEquipmentImages;
    }

    ImageList shipImages() {
        return ilShipImages;
    }

    public Image[] getCustomShipImages() {
        Image[] images = new Image[Consts.ImagesPerShip];
        int baseIndex = ShipType.Custom.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++) {
            images[index] = ilShipImages.getImages()[baseIndex + index];
        }
        return images;
    }

    void setCustomShipImages(Image[] value) {
        int baseIndex = ShipType.Custom.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++) {
            ilShipImages.getImages()[baseIndex + index] = value[index];
        }
    }

    public void setGame(Game game) {
        this.game = game;
        controller = new GameController(game, this);
        commander = game == null ? null : game.Commander();

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

    public StringsMap getStrings() {
        return strings;
    }
}
