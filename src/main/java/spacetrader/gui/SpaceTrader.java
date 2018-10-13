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
import spacetrader.util.ValuesMap;
import util.PropertiesLoader;

import java.awt.*;
import java.util.Map;

public class SpaceTrader extends jwinforms.WinformWindow implements MainWindow {

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
    private MenuItem mnuGameLine1;
    private MenuItem mnuGameLine2;
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
    private MenuItem mnuViewLine1;
    private MenuItem mnuViewLine2;
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

    private Map<String, String> strings;
    private ValuesMap dimensions;

    public SpaceTrader(String loadFileName) {
        initializeComponent();

        initFileStructure();

        if (loadFileName != null)
            controller.loadGame(loadFileName);

        updateAll();
    }

    // #region Windows Form Designer generated code
    // / <summary>
    // / Required method for Designer support - do not modify
    // / the contents of this method with the code editor.
    // / </summary>
    private void initializeComponent() {
        ResourceManager resources = new ResourceManager(SpaceTrader.class);

        strings = PropertiesLoader.getStringsMap("strings/ru.properties");
        dimensions = PropertiesLoader.getValuesMap("dimensions/0768.properties");

        initializeComponents();
        initializeMenu();
        initializeStatusBar();

        initializeDialogs();

        initializePicLine();

        //this.SuspendLayout();

        initializeImages(resources);

        //
        // SpaceTrader
        //
        this.setClientSize(dimensions.getSize("main"));
        Controls.add(picLine);
        Controls.add(dockBox);
        Controls.add(cargoBox);
        Controls.add(targetSystemBox);
        Controls.add(galacticChart);
        Controls.add(shortRangeChart);

        Controls.add(systemBox);
        Controls.add(shipyardBox);

        setStatusBar(statusBar);
        this.setMenu(mnuMain);

        this.setFormBorderStyle(FormBorderStyle.FixedSingle);
        this.setIcon(((Icon) (resources.getObject("$this.Icon"))));
        this.setMaximizeBox(false);
        this.setStartPosition(FormStartPosition.Manual);
        this.setText(strings.get("main.title"));
        this.setClosing(new jwinforms.EventHandler<Object, CancelEventArgs>() {
            @Override
            public void handle(Object sender, CancelEventArgs e) {
                SpaceTrader_Closing(sender, e);
            }
        });
        this.setClosed(new jwinforms.EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                SpaceTrader_Closed(sender, e);
            }
        });

        this.setLoad(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                SpaceTrader_Load(sender, e);
            }
        });
    }

    private void initializeMenu() {

        mnuMain = new MainMenu();
        mnuGame = new SubMenu();
        mnuGameNew = new MenuItem();
        mnuGameLoad = new MenuItem();
        mnuGameSave = new MenuItem();
        mnuGameSaveAs = new MenuItem();
        mnuGameLine1 = new MenuItem();
        mnuRetire = new MenuItem();
        mnuGameLine2 = new MenuItem();
        mnuGameExit = new MenuItem();
        mnuView = new SubMenu();
        mnuViewCommander = new MenuItem();
        mnuViewShip = new MenuItem();
        mnuViewPersonnel = new MenuItem();
        mnuViewQuests = new MenuItem();
        mnuViewBank = new MenuItem();
        mnuViewLine1 = new MenuItem();
        mnuHighScores = new MenuItem();
        mnuViewLine2 = new MenuItem();
        mnuOptions = new MenuItem();
        mnuHelp = new SubMenu();
        mnuHelpAbout = new MenuItem();

        mnuMain.addAll(mnuGame, mnuView, mnuHelp);

        mnuGame.Index = 0;
        mnuGame.addAll(mnuGameNew, mnuGameLoad, mnuGameSave, mnuGameSaveAs, mnuGameLine1, mnuRetire, mnuGameLine2,
                mnuGameExit);
        mnuGame.setText(strings.get("menu.game"));

        mnuGameNew.Index = 0;
        mnuGameNew.setText(strings.get("menu.game.new"));
        mnuGameNew.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameNew_Click(sender, e);
            }
        });

        mnuGameLoad.Index = 1;
        mnuGameLoad.Shortcut = Shortcut.CtrlL;
        mnuGameLoad.setText(strings.get("menu.game.load"));
        mnuGameLoad.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameLoad_Click(sender, e);
            }
        });

        mnuGameSave.setEnabled(false);
        mnuGameSave.Index = 2;
        mnuGameSave.Shortcut = Shortcut.CtrlS;
        mnuGameSave.setText(strings.get("menu.game.save"));
        mnuGameSave.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameSave_Click(sender, e);
            }
        });

        mnuGameSaveAs.setEnabled(false);
        mnuGameSaveAs.Index = 3;
        mnuGameSaveAs.Shortcut = Shortcut.CtrlA;
        mnuGameSaveAs.setText(strings.get("menu.game.save.as"));
        mnuGameSaveAs.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameSaveAs_Click(sender, e);
            }
        });

        mnuGameLine1.Index = 4;
        mnuGameLine1.setText("-");

        mnuRetire.setEnabled(false);
        mnuRetire.Index = 5;
        mnuRetire.setText(strings.get("menu.game.retire"));
        mnuRetire.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuRetire_Click(sender, e);
            }
        });

        mnuGameLine2.Index = 6;
        mnuGameLine2.setText("-");

        mnuGameExit.Index = 7;
        mnuGameExit.setText(strings.get("menu.game.exit"));
        mnuGameExit.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuGameExit_Click(sender, e);
            }
        });

        mnuView.Index = 1;
        mnuView.addAll(mnuViewCommander, mnuViewShip, mnuViewPersonnel, mnuViewQuests, mnuViewBank, mnuViewLine1,
                mnuHighScores, mnuViewLine2, mnuOptions);
        mnuView.setText(strings.get("menu.view"));

        mnuViewCommander.setEnabled(false);
        mnuViewCommander.Index = 0;
        mnuViewCommander.Shortcut = Shortcut.CtrlC;
        mnuViewCommander.setText(strings.get("menu.view.commander.status"));
        mnuViewCommander.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewCommander_Click(sender, e);
            }
        });

        mnuViewShip.setEnabled(false);
        mnuViewShip.Index = 1;
        mnuViewShip.Shortcut = Shortcut.CtrlH;
        mnuViewShip.setText(strings.get("menu.view.ship"));
        mnuViewShip.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewShip_Click(sender, e);
            }
        });

        mnuViewPersonnel.setEnabled(false);
        mnuViewPersonnel.Index = 2;
        mnuViewPersonnel.Shortcut = Shortcut.CtrlP;
        mnuViewPersonnel.setText(strings.get("menu.view.personnel"));
        mnuViewPersonnel.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewPersonnel_Click(sender, e);
            }
        });

        mnuViewQuests.setEnabled(false);
        mnuViewQuests.Index = 3;
        mnuViewQuests.Shortcut = Shortcut.CtrlQ;
        mnuViewQuests.setText(strings.get("menu.view.quests"));
        mnuViewQuests.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewQuests_Click(sender, e);
            }
        });

        mnuViewBank.setEnabled(false);
        mnuViewBank.Index = 4;
        mnuViewBank.Shortcut = Shortcut.CtrlB;
        mnuViewBank.setText(strings.get("menu.view.bank"));
        mnuViewBank.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuViewBank_Click(sender, e);
            }
        });

        mnuViewLine1.Index = 5;
        mnuViewLine1.setText("-");

        mnuHighScores.Index = 6;
        mnuHighScores.setText(strings.get("menu.view.high.scores"));
        mnuHighScores.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuHighScores_Click(sender, e);
            }
        });

        mnuViewLine2.Index = 7;
        mnuViewLine2.setText("-");

        mnuOptions.Index = 8;
        mnuOptions.setText(strings.get("menu.view.options"));
        mnuOptions.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuOptions_Click(sender, e);
            }
        });

        mnuHelp.Index = 2;
        mnuHelp.add(mnuHelpAbout);
        mnuHelp.setText(strings.get("menu.help"));

        mnuHelpAbout.Index = 0;
        mnuHelpAbout.setText(strings.get("menu.help.about"));
        mnuHelpAbout.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, EventArgs e) {
                mnuHelpAbout_Click(sender, e);
            }
        });
    }

    private void initializeStatusBar() {
        statusBar = new SpaceTraderStatusBar(this);
        statusBar.beginInit();
        statusBar.InitializeComponent();
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

        shortRangeChart.SuspendLayout();
        galacticChart.SuspendLayout();
        targetSystemBox.SuspendLayout();
        cargoBox.SuspendLayout();
        systemBox.SuspendLayout();
        shipyardBox.SuspendLayout();
        dockBox.SuspendLayout();

        galacticChart.InitializeComponent();
        galacticChart.setLocation(new Point(180, 306));

        shortRangeChart.InitializeComponent();
        shortRangeChart.setLocation(new Point(364, 306));

        targetSystemBox.InitializeComponent();
        targetSystemBox.setLocation(new Point(548, 306));

        cargoBox.initializeComponent(strings);
        cargoBox.setLocation(new Point(252, 2));

        systemBox.InitializeComponent();
        systemBox.setLocation(new Point(4, 2));

        shipyardBox.InitializeComponent();
        shipyardBox.setLocation(new Point(4, 306));

        dockBox.InitializeComponent();
        dockBox.setLocation(new Point(4, 212));
    }

    private void initializePicLine() {
        picLine = new PictureBox();
        picLine.setBackColor(Color.darkGray);
        picLine.setLocation(new Point(0, 0));
        picLine.setName("picLine");
        picLine.setSize(new Size(770, 1));
        picLine.setTabIndex(132);
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


    private String GetRegistrySetting(String settingName, String defaultValue) {
        String settingValue = defaultValue;

        try {
            RegistryKey key = Functions.GetRegistryKey();
            Object ObjectValue = key.GetValue(settingName);
            if (ObjectValue != null)
                settingValue = ObjectValue.toString();
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
            if (!Directory.Exists(path))
                Directory.CreateDirectory(path);
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

    private void SpaceTrader_Closing(Object sender, jwinforms.CancelEventArgs e) {
        if (game == null || commander.getDays() == controller.SaveGameDays
                || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes) {
            if (WindowState == FormWindowState.Normal) {
                SetRegistrySetting("X", Left.toString());
                SetRegistrySetting("Y", Top.toString());
            }
        } else
            e.Cancel = true;
    }

    private void SpaceTrader_Closed(Object sender, jwinforms.EventArgs e) {
        FormsOwnerTree.pop(this);
    }

    private void SpaceTrader_Load(Object sender, jwinforms.EventArgs e) {
        Left = Integer.parseInt(GetRegistrySetting("X", "0"));
        Top = Integer.parseInt(GetRegistrySetting("Y", "0"));

        FormsOwnerTree.add(this);

        GuiFacade.alert(AlertType.AppStart);
    }

    private void mnuGameExit_Click(Object sender, jwinforms.EventArgs e) {
        close();
    }

    private void mnuGameNew_Click(Object sender, jwinforms.EventArgs e) {
        FormNewCommander form = new FormNewCommander();
        if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes)
                && form.showDialog(this) == DialogResult.OK) {
            setGame(new Game(form.CommanderName(), form.Difficulty(), form.Pilot(), form.Fighter(), form.Trader(), form
                    .Engineer(), this));
            controller.SaveGameFile = null;
            controller.SaveGameDays = 0;

            SetInGameControlsEnabled(true);
            updateAll();

            if (game.Options().getNewsAutoShow())
                game.ShowNewspaper();
        }
    }

    private void mnuGameLoad_Click(Object sender, jwinforms.EventArgs e) {
        if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes)
                && dlgOpen.ShowDialog(this) == DialogResult.OK)
            controller.loadGame(dlgOpen.getFileName());
    }

    private void mnuGameSave_Click(Object sender, jwinforms.EventArgs e) {
        if (Game.CurrentGame() != null) {
            if (controller.SaveGameFile != null)
                controller.saveGame(controller.SaveGameFile, false);
            else
                mnuGameSaveAs_Click(sender, e);
        }
    }

    private void mnuGameSaveAs_Click(Object sender, jwinforms.EventArgs e) {
        if (Game.CurrentGame() != null && dlgSave.ShowDialog(this) == DialogResult.OK)
            controller.saveGame(dlgSave.getFileName(), true);
    }

    private void mnuHelpAbout_Click(Object sender, jwinforms.EventArgs e) {
        (new FormAbout()).showDialog(this);
    }

    private void mnuHighScores_Click(Object sender, jwinforms.EventArgs e) {
        (new FormViewHighScores()).showDialog(this);
    }

    private void mnuOptions_Click(Object sender, jwinforms.EventArgs e) {
        FormOptions form = new FormOptions();
        if (form.showDialog(this) == DialogResult.OK) {
            game.Options().CopyValues(form.Options());
            updateAll();
        }
    }

    private void mnuRetire_Click(Object sender, jwinforms.EventArgs e) {
        if (GuiFacade.alert(AlertType.GameRetire) == DialogResult.Yes) {
            game.setEndStatus(GameEndType.Retired);
            controller.gameEnd();
            updateAll();
        }
    }

    private void mnuViewBank_Click(Object sender, jwinforms.EventArgs e) {
        showBank();
    }

    void showBank() {
        (new FormViewBank()).showDialog(this);
    }

    private void mnuViewCommander_Click(Object sender, jwinforms.EventArgs e) {
        (new FormViewCommander()).showDialog(this);
    }

    private void mnuViewPersonnel_Click(Object sender, jwinforms.EventArgs e) {
        (new FormViewPersonnel()).showDialog(this);
    }

    private void mnuViewQuests_Click(Object sender, jwinforms.EventArgs e) {
        (new FormViewQuests()).showDialog(this);
    }

    private void mnuViewShip_Click(Object sender, jwinforms.EventArgs e) {
        (new FormViewShip()).showDialog(this);
    }

    // #endregion

    // #region Properties

    Image[] customShipImages() {
        Image[] images = new Image[Consts.ImagesPerShip];
        int baseIndex = ShipType.Custom.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++)
            images[index] = ilShipImages.getImages()[baseIndex + index];

        return images;
    }

    public void customShipImages(Image[] value) {
        int baseIndex = ShipType.Custom.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++)
            ilShipImages.getImages()[baseIndex + index] = value[index];
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
        for (int index = 0; index < Consts.ImagesPerShip; index++)
            images[index] = ilShipImages.getImages()[baseIndex + index];

        return images;
    }

    void setCustomShipImages(Image[] value) {
        int baseIndex = ShipType.Custom.castToInt() * Consts.ImagesPerShip;
        for (int index = 0; index < Consts.ImagesPerShip; index++)
            ilShipImages.getImages()[baseIndex + index] = value[index];
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
