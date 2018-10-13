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

import jwinforms.*;
import jwinforms.Container;
import jwinforms.Icon;
import jwinforms.Image;
import jwinforms.MenuItem;
import spacetrader.*;
import spacetrader.enums.AlertType;
import spacetrader.enums.GameEndType;
import spacetrader.enums.ShipType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.MainWindow;
import spacetrader.stub.Directory;
import spacetrader.stub.RegistryKey;
import spacetrader.util.StringsMap;
import spacetrader.util.ValuesMap;
import util.PropertiesLoader;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.Label;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

import static jwinforms.MenuItem.separator;

public class SpaceTrader extends WinformWindow implements MainWindow {

    // #region Control Declarations
    private DockBox dockBox;
    private CargoBox cargoBox;
    private TargetSystemBox targetSystemBox;
    private GalacticChart galacticChart;
    private ShortRangeChart shortRangeChart;
    private SystemBox systemBox;
    private ShipyardBox shipyardBox;
    private SpaceTraderStatusBar statusBar;

    private ImageList ilChartImages;
    private ImageList ilDirectionImages;
    private ImageList ilEquipmentImages;
    private ImageList ilShipImages;

    private MainMenu mnuMain;
    private SubMenu mnuGame;
    private MenuItem mnuGameExit;
    private MenuItem mnuGameLoad;
    private MenuItem mnuGameNew;
    private MenuItem mnuGameSave;
    private MenuItem mnuGameSaveAs;
    private SubMenu mnuHelp;
    private MenuItem mnuHelpAbout;
    private MenuItem mnuHighScores;
    private MenuItem mnuOptions;
    private MenuItem mnuRetire;
    private SubMenu mnuView;
    private MenuItem mnuViewBank;
    private MenuItem mnuViewCommander;
    private MenuItem mnuViewPersonnel;
    private MenuItem mnuViewQuests;
    private MenuItem mnuViewShip;

    private OpenFileDialog dlgOpen;
    private SaveFileDialog dlgSave;

    private PictureBox picLine;

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

        //ddd(getFrame(), this.getName());

        System.out.println("===================");

        ddd2(getFrame(), this.getName());
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

        initializePicLine();

        //this.suspendLayout();


        //
        // SpaceTrader
        //
        this.setClientSize(dimensions.getSize(this.getName()));
        controls.add(picLine);
        controls.add(dockBox);
        controls.add(cargoBox);
        controls.add(targetSystemBox);
        controls.add(galacticChart);
        controls.add(shortRangeChart);

        controls.add(systemBox);
        controls.add(shipyardBox);

        setStatusBar(statusBar);
        this.setMenu(mnuMain);

        this.setFormBorderStyle(FormBorderStyle.FixedSingle);
        this.setIcon(((Icon) (resources.getObject("$this.Icon"))));
        this.setMaximizeBox(false);
        this.setStartPosition(FormStartPosition.Manual);
        this.setText(strings.get(this.getName() + ".title"));
        this.setClosing(new jwinforms.EventHandler<Object, CancelEventArgs>() {
            @Override
            public void handle(Object sender, CancelEventArgs e) {
                SpaceTrader_Closing(e);
            }
        });
        this.setClosed(new jwinforms.EventHandler<Object, EventArgs>() {
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

    void ddd(Component component, String prefix) {
        component.getSize();
        component.getLocation();

        String name = component.getName() == null ? component.getClass().getSimpleName() : component.getName();

        System.out.println(prefix + "." + name + ".x=" + component.getX());
        System.out.println(prefix + "." + name + ".y=" + component.getY());
        System.out.println(prefix + "." + name + ".width=" + component.getWidth());
        System.out.println(prefix + "." + name + ".height=" + component.getHeight());

        if (component instanceof java.awt.Container) {
            for (Component child: ((java.awt.Container) component).getComponents()) {
                name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                ddd(child, prefix + "." + name);
            }
        }
    }

    void ddd2(Component component, String prefix) {
        component.getSize();
        component.getLocation();

        String name = component.getName() == null ? component.getClass().getSimpleName() : component.getName();

        if (component instanceof AbstractButton) {
            System.out.println(prefix + "." + name + ".text=" + ((AbstractButton) component).getText());
        }
        /*if (component instanceof JLabel) {
            System.out.println(prefix + "." + name + ".text=" + ((JLabel) component).getText());
        }
        if (component instanceof JPanel && ((JPanel) component).getBorder() instanceof TitledBorder) {
            System.out.println(prefix + "." + name + ".title=" + ((TitledBorder)((JPanel) component).getBorder()).getTitle());
        }
        if (component instanceof JFrame) {
            System.out.println(prefix + "." + name + ".title=" + ((JFrame) component).getTitle());
        }*/

        if (component instanceof JMenu) {
            for (Component child: ((JMenu) component).getMenuComponents()) {
                name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                ddd2(child, prefix + "." + name);
            }
        }

        if (component instanceof java.awt.Container) {
            for (Component child: ((java.awt.Container) component).getComponents()) {
                name = child.getName() == null ? child.getClass().getSimpleName() : child.getName();
                ddd2(child, prefix + "." + name);
            }
        }

        //component.setSize(dimensions.getSize(component.getName()));
        //if (!(component instanceof WinformWindow)) {
        //component.setLocation(dimensions.getLocation(component.getName()));
        //}

        /*if (component instanceof WinformWindow) {
            ((WinformWindow) component).setTitle(strings.getTitle(component.getName()));
        }*/

        /*if (component instanceof AbstractButton) {
            ((AbstractButton) component).setText(strings.getText(component.getName()));
        }*/
    }

    private void initializeMenu() {
        mnuMain = new MainMenu();
        mnuGame = new SubMenu();
        mnuGameNew = new MenuItem();
        mnuGameLoad = new MenuItem();
        mnuGameSave = new MenuItem();
        mnuGameSaveAs = new MenuItem();
        mnuRetire = new MenuItem();
        mnuGameExit = new MenuItem();
        mnuView = new SubMenu();
        mnuViewCommander = new MenuItem();
        mnuViewShip = new MenuItem();
        mnuViewPersonnel = new MenuItem();
        mnuViewQuests = new MenuItem();
        mnuViewBank = new MenuItem();
        mnuHighScores = new MenuItem();
        mnuOptions = new MenuItem();
        mnuHelp = new SubMenu();
        mnuHelpAbout = new MenuItem();

        mnuMain.addAll(mnuGame, mnuView, mnuHelp);
        mnuMain.setName("mainMenu");

        mnuGame.addAll(mnuGameNew, mnuGameLoad, mnuGameSave, mnuGameSaveAs, separator(), mnuRetire, separator(),
                mnuGameExit);
        mnuGame.setName("gameMenu");
        //TODO
        mnuGame.setText(strings.get("menu.game"));

        mnuGameNew.setName("newGameMenuItem");
        mnuGameNew.setText(strings.get("menu.game.new"));
        mnuGameNew.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameNew_Click();
            }
        });

        mnuGameLoad.shortcut = Shortcut.CtrlL;
        mnuGameLoad.setText(strings.get("menu.game.load"));
        mnuGameLoad.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameLoad_Click();
            }
        });

        mnuGameSave.setEnabled(false);
        mnuGameSave.shortcut = Shortcut.CtrlS;
        mnuGameSave.setText(strings.get("menu.game.save"));
        mnuGameSave.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameSave_Click();
            }
        });

        mnuGameSaveAs.setEnabled(false);
        mnuGameSaveAs.shortcut = Shortcut.CtrlA;
        mnuGameSaveAs.setText(strings.get("menu.game.save.as"));
        mnuGameSaveAs.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameSaveAs_Click();
            }
        });

        mnuRetire.setEnabled(false);
        mnuRetire.setText(strings.get("menu.game.retire"));
        mnuRetire.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuRetire_Click();
            }
        });

        mnuGameExit.setText(strings.get("menu.game.exit"));
        mnuGameExit.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameExit_Click();
            }
        });

        mnuView.addAll(mnuViewCommander, mnuViewShip, mnuViewPersonnel, mnuViewQuests, mnuViewBank, separator(),
                mnuHighScores, separator(), mnuOptions);
        mnuView.setText(strings.get("menu.view"));

        mnuViewCommander.setEnabled(false);
        mnuViewCommander.shortcut = Shortcut.CtrlC;
        mnuViewCommander.setText(strings.get("menu.view.commander.status"));
        mnuViewCommander.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewCommander_Click();
            }
        });

        mnuViewShip.setEnabled(false);
        mnuViewShip.shortcut = Shortcut.CtrlH;
        mnuViewShip.setText(strings.get("menu.view.ship"));
        mnuViewShip.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewShip_Click();
            }
        });

        mnuViewPersonnel.setEnabled(false);
        mnuViewPersonnel.shortcut = Shortcut.CtrlP;
        mnuViewPersonnel.setText(strings.get("menu.view.personnel"));
        mnuViewPersonnel.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewPersonnel_Click();
            }
        });

        mnuViewQuests.setEnabled(false);
        mnuViewQuests.shortcut = Shortcut.CtrlQ;
        mnuViewQuests.setText(strings.get("menu.view.quests"));
        mnuViewQuests.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewQuests_Click();
            }
        });

        mnuViewBank.setEnabled(false);
        mnuViewBank.shortcut = Shortcut.CtrlB;
        mnuViewBank.setText(strings.get("menu.view.bank"));
        mnuViewBank.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewBank_Click();
            }
        });

        mnuHighScores.setText(strings.get("menu.view.high.scores"));
        mnuHighScores.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuHighScores_Click();
            }
        });

        mnuOptions.setText(strings.get("menu.view.options"));
        mnuOptions.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuOptions_Click();
            }
        });

        mnuHelp.add(mnuHelpAbout);
        mnuHelp.setText(strings.get("menu.help"));

        mnuHelpAbout.setText(strings.get("menu.help.about"));
        mnuHelpAbout.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuHelpAbout_Click();
            }
        });
    }

    private void initializeStatusBar() {
        statusBar = new SpaceTraderStatusBar(this);
        statusBar.beginInit();
        statusBar.initializeComponent();
    }

    private void initializeComponents() {
        components = new Container();

        cargoBox = new CargoBox();
        systemBox = new SystemBox(this);
        shipyardBox = new ShipyardBox(this);
        dockBox = new DockBox(this);

        shortRangeChart = new ShortRangeChart(this, ilChartImages);
        galacticChart = new GalacticChart(this, ilChartImages);
        targetSystemBox = new TargetSystemBox(this);

        shortRangeChart.suspendLayout();
        galacticChart.suspendLayout();
        targetSystemBox.suspendLayout();
        cargoBox.suspendLayout();
        systemBox.suspendLayout();
        shipyardBox.suspendLayout();
        dockBox.suspendLayout();

        // system   cargocargocargocargocargoc
        // system   cargocargocargocargocargoc
        // dock     cargocargocargocargocargoc

        // shipyard galactic shortRange target
        // shipyard galactic shortRange target

        systemBox.initializeComponent();
        systemBox.setLocation(new Point(4, 2));

        cargoBox.initializeComponent(strings);
        cargoBox.setLocation(new Point(252, 2));

        dockBox.initializeComponent();
        dockBox.setLocation(new Point(4, 212));

        shipyardBox.initializeComponent();
        shipyardBox.setLocation(new Point(4, 306));

        galacticChart.initializeComponent();
        galacticChart.setLocation(new Point(180, 306));

        shortRangeChart.initializeComponent();
        shortRangeChart.setLocation(new Point(364, 306));

        targetSystemBox.initializeComponent();
        targetSystemBox.setLocation(new Point(548, 306));
    }

    private void initializePicLine() {
        picLine = new PictureBox();
        picLine.setBackColor(Color.darkGray);
        picLine.setLocation(new Point(0, 0));
        picLine.setName("picLine");
        picLine.setSize(new Size(770, 1));
        picLine.setTabStop(false);
    }

    private void initializeDialogs() {
        dlgOpen = new OpenFileDialog();
        dlgSave = new SaveFileDialog();

        dlgOpen.setFilter(strings.get("dialog.open.save.filter"));

        dlgSave.setFileName("SpaceTrader.sav");
        dlgSave.setFilter(strings.get("dialog.open.save.filter"));
        dlgSave.setTitle(strings.get("dialog.save.title"));
        dlgSave.setButtonText(strings.get("dialog.save.button.text"));
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

        dlgOpen.setInitialDirectory(Consts.SaveDirectory);
        dlgSave.setInitialDirectory(Consts.SaveDirectory);
    }

    public void SetInGameControlsEnabled(boolean enabled) {
        mnuGameSave.setEnabled(enabled);
        mnuGameSaveAs.setEnabled(enabled);
        mnuRetire.setEnabled(enabled);
        mnuViewCommander.setEnabled(enabled);
        mnuViewShip.setEnabled(enabled);
        mnuViewPersonnel.setEnabled(enabled);
        mnuViewQuests.setEnabled(enabled);
        mnuViewBank.setEnabled(enabled);
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
        dockBox.Update();
        cargoBox.update(strings);
        shipyardBox.Update();
        statusBar.Update();
        systemBox.Update();
        targetSystemBox.Update();
        galacticChart.Refresh();
        shortRangeChart.Refresh();
    }

    private void SpaceTrader_Closing(jwinforms.CancelEventArgs e) {
        if (game == null || commander.getDays() == controller.SaveGameDays
                || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes) {
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
        if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes)
                && form.showDialog(this) == DialogResult.OK) {
            setGame(new Game(form.CommanderName(), form.Difficulty(), form.Pilot(), form.Fighter(), form.Trader(), form
                    .Engineer(), this));
            controller.SaveGameFile = null;
            controller.SaveGameDays = 0;

            SetInGameControlsEnabled(true);
            updateAll();

            if (game.Options().getNewsAutoShow()) {
                game.ShowNewspaper();
            }
        }
    }

    private void mnuGameLoad_Click() {
        if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes)
                && dlgOpen.ShowDialog(this) == DialogResult.OK) {
            controller.loadGame(dlgOpen.getFileName());
        }
    }

    private void mnuGameSave_Click() {
        if (Game.CurrentGame() != null) {
            if (controller.SaveGameFile != null) {
                controller.saveGame(controller.SaveGameFile, false);
            } else {
                mnuGameSaveAs_Click();
            }
        }
    }

    private void mnuGameSaveAs_Click() {
        if (Game.CurrentGame() != null && dlgSave.ShowDialog(this) == DialogResult.OK) {
            controller.saveGame(dlgSave.getFileName(), true);
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
        if (GuiFacade.alert(AlertType.GameRetire) == DialogResult.Yes) {
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

    // #endregion

    // #region Properties

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

    // #endregion

    public void setGame(Game game) {
        this.game = game;
        controller = new GameController(game, this);
        commander = game == null ? null : game.Commander();

        dockBox.setGame(commander);
        cargoBox.setGame(game, controller);
        targetSystemBox.setGame(game, controller, commander);
        galacticChart.setGame(game, controller, commander);
        shortRangeChart.setGame(game, commander);
        systemBox.setGame(game, controller, commander);
        shipyardBox.setGame(commander);
        statusBar.setGame(commander);
    }

    /**
     * TODO remove?
     */
    public void updateStatusBar() {
        statusBar.Update();
    }
}
