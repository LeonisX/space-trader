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

import java.awt.Color;
import java.awt.Point;

import javax.swing.UIManager;

import jwinforms.*;
import spacetrader.Commander;
import spacetrader.Consts;
import spacetrader.Functions;
import spacetrader.Game;
import spacetrader.GameController;
import spacetrader.enums.AlertType;
import spacetrader.enums.GameEndType;
import spacetrader.enums.ShipType;
import spacetrader.guifacade.GuiFacade;
import spacetrader.guifacade.GuiEngine;
import spacetrader.guifacade.MainWindow;
import spacetrader.stub.Directory;
import spacetrader.stub.RegistryKey;

public class SpaceTrader extends WinformWindow implements MainWindow
{
	// #region Control Declarations

	private DockBox dockBox;
	private CargoBox cargoBox;
	private TargetSystemBox targetSystemBox;
	private GalacticChart galacticChart;
	private ShortRangeChart shortRangeChart;
	private SystemBox system;
	private ShipyardBox shipyard;
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
	private PictureBox picLine;
	private SaveFileDialog dlgSave;

	private IContainer components;

	private Game game;
	private GameController controller;
	private Commander commander;

	public SpaceTrader(String loadFileName)
	{
		InitializeComponent();

		InitFileStructure();

		if (loadFileName != null)
			controller.LoadGame(loadFileName);

		UpdateAll();
	}

	public static void main(String[] args) throws Exception
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("swing.boldMetal", Boolean.FALSE);
		} catch (Exception e)
		{
			// TODO: We get here if there's no windows UI; I think no special treatment is needed.
		}
		SpaceTrader spaceTrader = new SpaceTrader(args.length > 0 ? args[0] : null);
		GuiEngine.installImplementation(new OriginalGuiImplementationProvider(spaceTrader));
		spaceTrader.ShowWindow();
	}

	// #region Windows Form Designer generated code
	// / <summary>
	// / Required method for Designer support - do not modify
	// / the contents of this method with the code editor.
	// / </summary>
	private void InitializeComponent()
	{
		components = new Container();
		ResourceManager resources = new ResourceManager(SpaceTrader.class);
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
		statusBar = new SpaceTraderStatusBar(this);
		cargoBox = new CargoBox();
		system = new SystemBox(this);
		shipyard = new ShipyardBox(this);
		dockBox = new DockBox(this);
		picLine = new PictureBox();
		dlgOpen = new OpenFileDialog();
		dlgSave = new SaveFileDialog();
		ilChartImages = new ImageList(components);
		ilShipImages = new ImageList(components);
		ilDirectionImages = new ImageList(components);
		ilEquipmentImages = new ImageList(components);
		statusBar.BeginInit();
		shortRangeChart = new ShortRangeChart(this, ilChartImages);
		galacticChart = new GalacticChart(this, ilChartImages);
		targetSystemBox = new TargetSystemBox(this);

		shortRangeChart.SuspendLayout();
		galacticChart.SuspendLayout();
		targetSystemBox.SuspendLayout();
		cargoBox.SuspendLayout();
		system.SuspendLayout();
		shipyard.SuspendLayout();
		dockBox.SuspendLayout();
		this.SuspendLayout();
		//
		// mnuMain
		//
		mnuMain.addAll(mnuGame, mnuView, mnuHelp);
		//
		// mnuGame
		//
		mnuGame.Index = 0;
		mnuGame.addAll(mnuGameNew, mnuGameLoad, mnuGameSave, mnuGameSaveAs, mnuGameLine1, mnuRetire, mnuGameLine2,
				mnuGameExit);
		mnuGame.setText("&Game");
		//
		// mnuGameNew
		//
		mnuGameNew.Index = 0;
		mnuGameNew.setText("&New...");
		mnuGameNew.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuGameNew_Click(sender, e);
			}
		});
		//
		// mnuGameLoad
		//
		mnuGameLoad.Index = 1;
		mnuGameLoad.Shortcut = Shortcut.CtrlL;
		mnuGameLoad.setText("&Load...");
		mnuGameLoad.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuGameLoad_Click(sender, e);
			}
		});
		//
		// mnuGameSave
		//
		mnuGameSave.setEnabled(false);
		mnuGameSave.Index = 2;
		mnuGameSave.Shortcut = Shortcut.CtrlS;
		mnuGameSave.setText("&Save");
		mnuGameSave.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuGameSave_Click(sender, e);
			}
		});
		//
		// mnuGameSaveAs
		//
		mnuGameSaveAs.setEnabled(false);
		mnuGameSaveAs.Index = 3;
		mnuGameSaveAs.Shortcut = Shortcut.CtrlA;
		mnuGameSaveAs.setText("Save &As...");
		mnuGameSaveAs.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuGameSaveAs_Click(sender, e);
			}
		});
		//
		// mnuGameLine1
		//
		mnuGameLine1.Index = 4;
		mnuGameLine1.setText("-");
		//
		// mnuRetire
		//
		mnuRetire.setEnabled(false);
		mnuRetire.Index = 5;
		mnuRetire.setText("&Retire");
		mnuRetire.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuRetire_Click(sender, e);
			}
		});
		//
		// mnuGameLine2
		//
		mnuGameLine2.Index = 6;
		mnuGameLine2.setText("-");
		//
		// mnuGameExit
		//
		mnuGameExit.Index = 7;
		mnuGameExit.setText("E&xit");
		mnuGameExit.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuGameExit_Click(sender, e);
			}
		});
		//
		// mnuView
		//
		mnuView.Index = 1;
		mnuView.addAll(mnuViewCommander, mnuViewShip, mnuViewPersonnel, mnuViewQuests, mnuViewBank, mnuViewLine1,
				mnuHighScores, mnuViewLine2, mnuOptions);
		mnuView.setText("&View");
		//
		// mnuViewCommander
		//
		mnuViewCommander.setEnabled(false);
		mnuViewCommander.Index = 0;
		mnuViewCommander.Shortcut = Shortcut.CtrlC;
		mnuViewCommander.setText("&Commander Status");
		mnuViewCommander.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuViewCommander_Click(sender, e);
			}
		});
		//
		// mnuViewShip
		//
		mnuViewShip.setEnabled(false);
		mnuViewShip.Index = 1;
		mnuViewShip.Shortcut = Shortcut.CtrlH;
		mnuViewShip.setText("&Ship");
		mnuViewShip.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuViewShip_Click(sender, e);
			}
		});
		//
		// mnuViewPersonnel
		//
		mnuViewPersonnel.setEnabled(false);
		mnuViewPersonnel.Index = 2;
		mnuViewPersonnel.Shortcut = Shortcut.CtrlP;
		mnuViewPersonnel.setText("&Personnel");
		mnuViewPersonnel.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuViewPersonnel_Click(sender, e);
			}
		});
		//
		// mnuViewQuests
		//
		mnuViewQuests.setEnabled(false);
		mnuViewQuests.Index = 3;
		mnuViewQuests.Shortcut = Shortcut.CtrlQ;
		mnuViewQuests.setText("&Quests");
		mnuViewQuests.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuViewQuests_Click(sender, e);
			}
		});
		//
		// mnuViewBank
		//
		mnuViewBank.setEnabled(false);
		mnuViewBank.Index = 4;
		mnuViewBank.Shortcut = Shortcut.CtrlB;
		mnuViewBank.setText("&Bank");
		mnuViewBank.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuViewBank_Click(sender, e);
			}
		});
		//
		// mnuViewLine1
		//
		mnuViewLine1.Index = 5;
		mnuViewLine1.setText("-");
		//
		// mnuHighScores
		//
		mnuHighScores.Index = 6;
		mnuHighScores.setText("&High Scores");
		mnuHighScores.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuHighScores_Click(sender, e);
			}
		});
		//
		// mnuViewLine2
		//
		mnuViewLine2.Index = 7;
		mnuViewLine2.setText("-");
		//
		// mnuOptions
		//
		mnuOptions.Index = 8;
		mnuOptions.setText("Options");
		mnuOptions.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuOptions_Click(sender, e);
			}
		});
		//
		// mnuHelp
		//
		mnuHelp.Index = 2;
		mnuHelp.add(mnuHelpAbout);
		mnuHelp.setText("&Help");
		//
		// mnuHelpAbout
		//
		mnuHelpAbout.Index = 0;
		mnuHelpAbout.setText("&About Space Trader");
		mnuHelpAbout.setClick(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				mnuHelpAbout_Click(sender, e);
			}
		});
		//
		// picGalacticChart
		//
		galacticChart.InitializeComponent();
		galacticChart.setLocation(new Point(180, 306));

		//
		// statusBar
		//
		statusBar.setLocation(new Point(0, 481));
		statusBar.InitializeComponent();

		//
		// boxShortRangeChart
		//
		shortRangeChart.InitializeComponent();
		shortRangeChart.setLocation(new Point(364, 306));

		//
		// boxTargetSystem
		//
		targetSystemBox.InitializeComponent();
		targetSystemBox.setLocation(new Point(548, 306));

		//
		// boxCargo
		//
		cargoBox.InitializeComponent();
		cargoBox.setLocation(new Point(252, 2));

		//
		// boxSystem
		//
		system.InitializeComponent();
		system.setLocation(new Point(4, 2));

		//
		// boxShipYard
		//
		shipyard.InitializeComponent();
		shipyard.setLocation(new Point(4, 306));

		//
		// boxDock
		//
		dockBox.InitializeComponent();
		dockBox.setLocation(new Point(4, 212));

		//
		// picLine
		//
		picLine.setBackColor(Color.darkGray);
		picLine.setLocation(new Point(0, 0));
		picLine.setName("picLine");
		picLine.setSize(new Size(770, 1));
		picLine.setTabIndex(132);
		picLine.setTabStop(false);
		//
		// dlgOpen
		//
		dlgOpen.setFilter("Saved-Game Files (*.sav)|*.sav|All Files (*.*)|*.*");
		//
		// dlgSave
		//
		dlgSave.setFileName("SpaceTrader.sav");
		dlgSave.setFilter("Saved-Game Files (*.sav)|*.sav|All Files (*.*)|*.*");
		//
		// ilChartImages
		//
		ilChartImages.setImageSize(new Size(7, 7));
		ilChartImages.setImageStream(((ImageListStreamer)(resources.GetObject("ilChartImages.ImageStream"))));
		ilChartImages.setTransparentColor(Color.white);
		//
		// ilShipImages
		//
		ilShipImages.setImageSize(new Size(64, 52));
		ilShipImages.setImageStream(((ImageListStreamer)(resources.GetObject("ilShipImages.ImageStream"))));
		ilShipImages.setTransparentColor(Color.white);
		//
		// ilDirectionImages
		//
		ilDirectionImages.setImageSize(new Size(13, 13));
		ilDirectionImages.setImageStream(((ImageListStreamer)(resources
				.GetObject("ilDirectionImages.ImageStream"))));
		ilDirectionImages.setTransparentColor(Color.white);
		//
		// ilEquipmentImages
		//
		ilEquipmentImages.setImageSize(new Size(64, 52));
		ilEquipmentImages.setImageStream(((ImageListStreamer)(resources
				.GetObject("ilEquipmentImages.ImageStream"))));
		ilEquipmentImages.setTransparentColor(Color.white);

		//
		// SpaceTrader
		//
		this.setAutoScaleBaseSize(new Size(5, 13));
		this.setClientSize(new Size(768, 505));
		Controls.add(picLine);
		Controls.add(dockBox);
		Controls.add(cargoBox);
		Controls.add(targetSystemBox);
		Controls.add(galacticChart);
		Controls.add(shortRangeChart);
		setStatusBar(statusBar);
		Controls.add(system);
		Controls.add(shipyard);
		this.setFormBorderStyle(jwinforms.FormBorderStyle.FixedSingle);
		this.setIcon(((Icon)(resources.GetObject("$this.Icon"))));
		this.setMaximizeBox(false);
		this.setMenu(mnuMain);
		this.setName("SpaceTrader");
		this.setStartPosition(FormStartPosition.Manual);
		this.setText("Space Trader");
		this.setClosing(new EventHandler<Object, CancelEventArgs>()
		{
			@Override
			public void handle(Object sender, CancelEventArgs e)
			{
				SpaceTrader_Closing(sender, e);
			}
		});
		this.setClosed(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				SpaceTrader_Closed(sender, e);
			}
		});

		this.setLoad(new EventHandler<Object, EventArgs>()
		{
			@Override
			public void handle(Object sender, EventArgs e)
			{
				SpaceTrader_Load(sender, e);
			}
		});
	}

	// #endregion

	private String GetRegistrySetting(String settingName, String defaultValue)
	{
		String settingValue = defaultValue;

		try
		{
			RegistryKey key = Functions.GetRegistryKey();
			Object ObjectValue = key.GetValue(settingName);
			if (ObjectValue != null)
				settingValue = ObjectValue.toString();
			key.Close();
		} catch (NullPointerException ex)
		{
			GuiFacade.alert(AlertType.RegistryError, ex.getMessage());
		}

		return settingValue;
	}

	// Make sure all directories exists.
	private void InitFileStructure()
	{
		String[] paths = new String[] { Consts.CustomDirectory, Consts.CustomImagesDirectory,
				Consts.CustomTemplatesDirectory, Consts.DataDirectory, Consts.SaveDirectory };

		for (String path : paths)
		{
			if (!Directory.Exists(path))
				Directory.CreateDirectory(path);
		}

		dlgOpen.setInitialDirectory(Consts.SaveDirectory);
		dlgSave.setInitialDirectory(Consts.SaveDirectory);
	}

	public void SetInGameControlsEnabled(boolean enabled)
	{
		mnuGameSave.setEnabled(enabled);
		mnuGameSaveAs.setEnabled(enabled);
		mnuRetire.setEnabled(enabled);
		mnuViewCommander.setEnabled(enabled);
		mnuViewShip.setEnabled(enabled);
		mnuViewPersonnel.setEnabled(enabled);
		mnuViewQuests.setEnabled(enabled);
		mnuViewBank.setEnabled(enabled);
	}

	private void SetRegistrySetting(String settingName, String settingValue)
	{
		try
		{
			RegistryKey key = Functions.GetRegistryKey();
			key.SetValue(settingName, settingValue);
			key.Close();
		} catch (NullPointerException ex)
		{
			GuiFacade.alert(AlertType.RegistryError, ex.getMessage());
		}
	}

	public void UpdateAll()
	{
		dockBox.Update();
		cargoBox.Update();
		shipyard.Update();
		statusBar.Update();
		system.Update();
		targetSystemBox.Update();
		galacticChart.Refresh();
		shortRangeChart.Refresh();
	}

	private void SpaceTrader_Closing(Object sender, CancelEventArgs e)
	{
		if (game == null || commander.getDays() == controller.SaveGameDays
				|| GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes)
		{
			if (WindowState == FormWindowState.Normal)
			{
				SetRegistrySetting("X", Left.toString());
				SetRegistrySetting("Y", Top.toString());
			}
		} else
			e.Cancel = true;
	}

	private void SpaceTrader_Closed(Object sender, EventArgs e)
	{
		FormsOwnerTree.pop(this);
	}

	private void SpaceTrader_Load(Object sender, EventArgs e)
	{
		Left = Integer.parseInt(GetRegistrySetting("X", "0"));
		Top = Integer.parseInt(GetRegistrySetting("Y", "0"));

		FormsOwnerTree.add(this);

		GuiFacade.alert(AlertType.AppStart);
	}

	private void mnuGameExit_Click(Object sender, EventArgs e)
	{
		Close();
	}

	private void mnuGameNew_Click(Object sender, EventArgs e)
	{
		FormNewCommander form = new FormNewCommander();
		if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes)
				&& form.ShowDialog(this) == DialogResult.OK)
		{
			setGame(new Game(form.CommanderName(), form.Difficulty(), form.Pilot(), form.Fighter(), form.Trader(), form
					.Engineer(), this));
			controller.SaveGameFile = null;
			controller.SaveGameDays = 0;

			SetInGameControlsEnabled(true);
			UpdateAll();

			if (game.Options().getNewsAutoShow())
				game.ShowNewspaper();
		}
	}

	private void mnuGameLoad_Click(Object sender, EventArgs e)
	{
		if ((game == null || commander.getDays() == controller.SaveGameDays || GuiFacade.alert(AlertType.GameAbandonConfirm) == DialogResult.Yes)
				&& dlgOpen.ShowDialog(this) == DialogResult.OK)
			controller.LoadGame(dlgOpen.getFileName());
	}

	private void mnuGameSave_Click(Object sender, EventArgs e)
	{
		if (Game.CurrentGame() != null)
		{
			if (controller.SaveGameFile != null)
				controller.SaveGame(controller.SaveGameFile, false);
			else
				mnuGameSaveAs_Click(sender, e);
		}
	}

	private void mnuGameSaveAs_Click(Object sender, EventArgs e)
	{
		if (Game.CurrentGame() != null && dlgSave.ShowDialog(this) == DialogResult.OK)
			controller.SaveGame(dlgSave.getFileName(), true);
	}

	private void mnuHelpAbout_Click(Object sender, EventArgs e)
	{
		(new FormAbout()).ShowDialog(this);
	}

	private void mnuHighScores_Click(Object sender, EventArgs e)
	{
		(new FormViewHighScores()).ShowDialog(this);
	}

	private void mnuOptions_Click(Object sender, EventArgs e)
	{
		FormOptions form = new FormOptions();
		if (form.ShowDialog(this) == DialogResult.OK)
		{
			game.Options().CopyValues(form.Options());
			UpdateAll();
		}
	}

	private void mnuRetire_Click(Object sender, EventArgs e)
	{
		if (GuiFacade.alert(AlertType.GameRetire) == DialogResult.Yes)
		{
			game.setEndStatus(GameEndType.Retired);
			controller.GameEnd();
			UpdateAll();
		}
	}

	private void mnuViewBank_Click(Object sender, EventArgs e)
	{
		ShowBank();
	}

	public void ShowBank()
	{
		(new FormViewBank()).ShowDialog(this);
	}

	private void mnuViewCommander_Click(Object sender, EventArgs e)
	{
		(new FormViewCommander()).ShowDialog(this);
	}

	private void mnuViewPersonnel_Click(Object sender, EventArgs e)
	{
		(new FormViewPersonnel()).ShowDialog(this);
	}

	private void mnuViewQuests_Click(Object sender, EventArgs e)
	{
		(new FormViewQuests()).ShowDialog(this);
	}

	private void mnuViewShip_Click(Object sender, EventArgs e)
	{
		(new FormViewShip()).ShowDialog(this);
	}

	// #endregion

	// #region Properties

	public Image[] CustomShipImages()
	{
		Image[] images = new Image[Consts.ImagesPerShip];
		int baseIndex = ShipType.Custom.CastToInt() * Consts.ImagesPerShip;
		for (int index = 0; index < Consts.ImagesPerShip; index++)
			images[index] = ilShipImages.getImages()[baseIndex + index];

		return images;
	}

	public void CustomShipImages(Image[] value)
	{
		Image[] images = value;
		int baseIndex = ShipType.Custom.CastToInt() * Consts.ImagesPerShip;
		for (int index = 0; index < Consts.ImagesPerShip; index++)
			ilShipImages.getImages()[baseIndex + index] = images[index];
	}

	public ImageList DirectionImages()
	{
		return ilDirectionImages;
	}

	public ImageList EquipmentImages()
	{
		return ilEquipmentImages;
	}

	public ImageList ShipImages()
	{
		return ilShipImages;
	}

	public Image[] getCustomShipImages()
	{
		Image[] images = new Image[Consts.ImagesPerShip];
		int baseIndex = ShipType.Custom.CastToInt() * Consts.ImagesPerShip;
		for (int index = 0; index < Consts.ImagesPerShip; index++)
			images[index] = ilShipImages.getImages()[baseIndex + index];

		return images;
	}

	public void setCustomShipImages(Image[] value)
	{
		Image[] images = value;
		int baseIndex = ShipType.Custom.CastToInt() * Consts.ImagesPerShip;
		for (int index = 0; index < Consts.ImagesPerShip; index++)
			ilShipImages.getImages()[baseIndex + index] = images[index];
	}

	// #endregion

	public void setGame(Game game)
	{
		this.game = game;
		controller = new GameController(game, this);
		commander = game == null ? null : game.Commander();

		dockBox.setGame(commander);
		cargoBox.setGame(game, controller);
		targetSystemBox.setGame(game, controller, commander);
		galacticChart.setGame(game, controller, commander);
		shortRangeChart.setGame(game, commander);
		system.setGame(game, controller, commander);
		shipyard.setGame(commander);
		statusBar.setGame(commander);
	}

	/**
	 * TODO remove?
	 */
	public void UpdateStatusBar()
	{
		statusBar.Update();
	}
}
