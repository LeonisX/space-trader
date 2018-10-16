package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Image;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.*;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Util;

import java.awt.*;

public class GalacticChartPanel extends Panel {

    private final int OFF_X = ChartsGraphicsConsts.OFF_X;
    private final int OFF_Y = ChartsGraphicsConsts.OFF_Y;
    private final int OFF_X_WORM = ChartsGraphicsConsts.OFF_X_WORM;
    private final int IMG_G_N = ChartsGraphicsConsts.IMG_G_N;
    private final int IMG_G_V = ChartsGraphicsConsts.IMG_G_V;
    private final int IMG_G_W = ChartsGraphicsConsts.IMG_G_W;
    private final int IMG_S_N = ChartsGraphicsConsts.IMG_S_N;
    private final int IMG_S_NS = ChartsGraphicsConsts.IMG_S_NS;
    private final int IMG_S_V = ChartsGraphicsConsts.IMG_S_V;
    private final int IMG_S_VS = ChartsGraphicsConsts.IMG_S_VS;
    private final int IMG_S_W = ChartsGraphicsConsts.IMG_S_W;
    private final Pen DEFAULT_PEN = new Pen(Color.black);
    private final Brush DEFAULT_BRUSH = new SolidBrush(Color.white);

    private final SpaceTrader mainWindow;

    private final ImageList ilChartImages;

    private SystemTracker game = null;
    private GameController controller = null;
    private Commander commander;
    private GameCheats cheats;

    private Label lblWormhole;
    private Label lblWormholeLabel;
    private Button btnJump;
    private Button btnFind;
    private PictureBox picGalacticChart;

    GalacticChartPanel(SpaceTrader mainWindow, ImageList images) {
        this.mainWindow = mainWindow;
        ilChartImages = images;
    }

    void setGame(Game game, GameController controller, Commander commander) {
        this.game = game;
        this.controller = controller;
        this.commander = commander;
        cheats = (game == null) ? null : game.getCheats();
    }

    void initializeComponent() {
        picGalacticChart = new PictureBox();
        lblWormhole = new Label();
        lblWormholeLabel = new Label();
        btnJump = new Button();
        btnFind = new Button();

        picGalacticChart.setBackground(Color.white);
        picGalacticChart.setLocation(new Point(8, 16));
        picGalacticChart.setSize(new Size(160, 116));
        picGalacticChart.setTabIndex(0);
        picGalacticChart.setTabStop(false);
        picGalacticChart.setPaint(new spacetrader.controls.EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                picGalacticChart_Paint(e);
            }
        });
        picGalacticChart.setMouseDown(new spacetrader.controls.EventHandler<Object, MouseEventArgs>() {
            @Override
            public void handle(Object sender, MouseEventArgs e) {
                picGalacticChart_MouseDown(e);
            }
        });

        anchor = (((AnchorStyles.Top_Right)));
        setBackground(SystemColors.Control);

        controls.add(lblWormhole);
        controls.add(lblWormholeLabel);
        controls.add(btnJump);
        controls.add(btnFind);
        controls.add(picGalacticChart);

        setSize(new Size(176, 168));
        setTabIndex(5);
        setTabStop(false);
        setText("Galactic Chart");

        lblWormhole.setLocation(new Point(8, 148));
        lblWormhole.setSize(new Size(72, 13));
        lblWormhole.setTabIndex(29);
        lblWormhole.setText("Tarchannen");

        lblWormholeLabel.setLocation(new Point(8, 135));
        lblWormholeLabel.setSize(new Size(72, 13));
        lblWormholeLabel.setTabIndex(28);
        lblWormholeLabel.setText("Wormhole to");

        btnJump.setFlatStyle(FlatStyle.FLAT);
        btnJump.setLocation(new Point(81, 138));
        btnJump.setSize(new Size(42, 22));
        btnJump.setTabIndex(55);
        btnJump.setText("Jump");
        btnJump.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnJump_Click();
            }
        });

        btnFind.setFlatStyle(FlatStyle.FLAT);
        btnFind.setLocation(new Point(132, 138));
        btnFind.setSize(new Size(36, 22));
        btnFind.setTabIndex(56);
        btnFind.setText("Find");
        btnFind.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                btnFind_Click();
            }
        });

    }

    private void picGalacticChart_MouseDown(spacetrader.controls.MouseEventArgs e) {
        if (e.Button != MouseButtons.Left || game == null)
            return;

        StarSystem[] universe = game.getUniverse();

        boolean clickedSystem = false;

        for (int i = 0; i < universe.length && !clickedSystem; i++) {
            int x = universe[i].X() + OFF_X;
            int y = universe[i].Y() + OFF_Y;

            if (e.X >= x - 2 && e.X <= x + 2 && e.Y >= y - 2 && e.Y <= y + 2) {
                clickedSystem = true;
                game.SelectedSystemId(StarSystemId.fromInt(i));
            } else if (Functions.WormholeExists(i, -1)) {
                int xW = x + OFF_X_WORM;

                if (e.X >= xW - 2 && e.X <= xW + 2 && e.Y >= y - 2 && e.Y <= y + 2) {
                    clickedSystem = true;
                    game.SelectedSystemId(StarSystemId.fromInt(i));
                    game.TargetWormhole(true);
                }
            }
        }

        if (clickedSystem)
            // todo inline when done
            mainWindow.updateAll();
    }

    private void picGalacticChart_Paint(spacetrader.controls.PaintEventArgs e) {
        if (game != null) {
            StarSystem[] universe = game.getUniverse();
            int[] wormholes = game.Wormholes();
            StarSystem targetSys = game.SelectedSystem();
            StarSystem curSys = commander.getCurrentSystem();
            int fuel = commander.getShip().getFuel();

            if (fuel > 0)
                e.Graphics.DrawEllipse(DEFAULT_PEN, curSys.X() + OFF_X - fuel, curSys.Y() + OFF_Y - fuel, fuel * 2,
                        fuel * 2);

            int index = game.SelectedSystemId().castToInt();
            if (game.TargetWormhole()) {
                int dest = wormholes[(Util.BruteSeek(wormholes, index) + 1) % wormholes.length];
                StarSystem destSys = universe[dest];
                e.Graphics.DrawLine(DEFAULT_PEN, targetSys.X() + OFF_X_WORM + OFF_X, targetSys.Y() + OFF_Y, destSys.X()
                        + OFF_X, destSys.Y() + OFF_Y);
            }

            for (int i = 0; i < universe.length; i++) {
                int imageIndex = universe[i].Visited() ? IMG_S_V : IMG_S_N;
                if (universe[i] == game.getWarpSystem())
                    imageIndex++;
                Image image = ilChartImages.getImages()[imageIndex];

                if (universe[i] == game.TrackedSystem()) {
                    e.Graphics.DrawLine(DEFAULT_PEN, universe[i].X(), universe[i].Y(), universe[i].X()
                            + image.getWidth() - 1, universe[i].Y() + image.getHeight() - 1);
                    e.Graphics.DrawLine(DEFAULT_PEN, universe[i].X(), universe[i].Y() + image.getHeight() - 1,
                            universe[i].X() + image.getWidth() - 1, universe[i].Y());
                }

                ilChartImages.draw(e.Graphics, universe[i].X(), universe[i].Y(), imageIndex);

                if (Functions.WormholeExists(i, -1))
                    ilChartImages.draw(e.Graphics, universe[i].X() + OFF_X_WORM, universe[i].Y(), IMG_S_W);
            }
        } else
            e.Graphics.FillRectangle(DEFAULT_BRUSH, 0, 0, picGalacticChart.getWidth(), picGalacticChart.getHeight());
    }

    private void btnJump_Click() {
        if (game.getWarpSystem() == null)
            GuiFacade.alert(AlertType.ChartJumpNoSystemSelected);
        else if (game.getWarpSystem() == commander.getCurrentSystem())
            GuiFacade.alert(AlertType.ChartJumpCurrent);
        else if (GuiFacade.alert(AlertType.ChartJump, game.getWarpSystem().getName()) == DialogResult.YES) {
            game.setCanSuperWarp(false);
            try {
                controller.autoSave_depart();

                game.Warp(true);

                controller.autoSave_arrive();
            } catch (GameEndException ex) {
                controller.gameEnd();
            }
            // todo inline when done
            mainWindow.updateAll();
        }
    }

    private void btnFind_Click() {
        FormFind form = new FormFind();
        if (form.showDialog(mainWindow) == DialogResult.OK) {
            String[] words = form.SystemName().split(" ");

            boolean tryToFind = cheats.ConsiderCheat(words, controller);

            if (tryToFind) {
                game.setSelectedSystemByName(form.SystemName());
                if (form.TrackSystem()
                        && game.SelectedSystem().getName().toLowerCase().equals(form.SystemName().toLowerCase()))
                    game.setTrackedSystemId(game.SelectedSystemId());
            }

            // todo inline when done
            mainWindow.updateAll();
        }
    }

    void refresh() {
        picGalacticChart.refresh();
        if (game == null) {
            lblWormholeLabel.setVisible(false);
            lblWormhole.setVisible(false);
            btnJump.setVisible(false);
            btnFind.setVisible(false);
        } else {
            if (game.TargetWormhole()) {
                lblWormholeLabel.setVisible(true);
                lblWormhole.setVisible(true);
                lblWormhole.setText(game.getWarpSystem().getName());
            } else {
                lblWormholeLabel.setVisible(false);
                lblWormhole.setVisible(false);
            }
            btnJump.setVisible(game.getCanSuperWarp());
            btnFind.setVisible(true);
        }
    }

    @Override
    public int getWidth() {
        return picGalacticChart.getWidth();
    }

    @Override
    public int getHeight() {
        return picGalacticChart.getHeight();
    }
}
