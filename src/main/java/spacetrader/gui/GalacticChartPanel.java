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

import static spacetrader.gui.ChartsGraphicsConsts.*;

public class GalacticChartPanel extends Panel {

    private final Pen DEFAULT_PEN = new Pen(Color.black);
    private final Brush DEFAULT_BRUSH = new SolidBrush(Color.white);

    private final SpaceTrader mainWindow;

    private final ImageList ilChartImages;

    private SystemTracker game = null;
    private GameController controller = null;
    private Commander commander;
    private GameCheats cheats;

    private Label wormholeLabelValue;
    private Label wormholeLabel;
    private Button jumpButton;
    private Button findButton;
    private PictureBox galacticChartPicture;

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
        galacticChartPicture = new PictureBox();
        wormholeLabelValue = new Label();
        wormholeLabel = new Label();
        jumpButton = new Button();
        findButton = new Button();

        anchor = AnchorStyles.TOP_RIGHT;
        setBackground(SystemColors.CONTROL);

        controls.add(wormholeLabelValue);
        controls.add(wormholeLabel);
        controls.add(jumpButton);
        controls.add(findButton);
        controls.add(galacticChartPicture);

        setSize(new Size(176, 168));
        setTabIndex(5);
        setTabStop(false);
        setText("Galactic Chart");

        galacticChartPicture.setBackground(Color.white);
        galacticChartPicture.setLocation(new Point(8, 16));
        galacticChartPicture.setSize(new Size(160, 116));
        galacticChartPicture.setTabIndex(0);
        galacticChartPicture.setTabStop(false);
        galacticChartPicture.setPaint(new spacetrader.controls.EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                galacticChartPicturePaint(e);
            }
        });
        galacticChartPicture.setMouseDown(new spacetrader.controls.EventHandler<Object, MouseEventArgs>() {
            @Override
            public void handle(Object sender, MouseEventArgs e) {
                galacticChartPictureMouseDown(e);
            }
        });

        wormholeLabel.setLocation(new Point(8, 135));
        wormholeLabel.setSize(new Size(72, 13));
        wormholeLabel.setTabIndex(28);
        wormholeLabel.setText("Wormhole to");

        wormholeLabelValue.setLocation(new Point(8, 148));
        wormholeLabelValue.setSize(new Size(72, 13));
        wormholeLabelValue.setTabIndex(29);
        //wormholeLabelValue.setText("Tarchannen");

        jumpButton.setFlatStyle(FlatStyle.FLAT);
        jumpButton.setLocation(new Point(81, 138));
        jumpButton.setSize(new Size(42, 22));
        jumpButton.setTabIndex(55);
        jumpButton.setText("Jump");
        jumpButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                jumpButtonClick();
            }
        });

        findButton.setFlatStyle(FlatStyle.FLAT);
        findButton.setLocation(new Point(132, 138));
        findButton.setSize(new Size(36, 22));
        findButton.setTabIndex(56);
        findButton.setText("Find");
        findButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                findButtonClick();
            }
        });

    }

    private void galacticChartPictureMouseDown(spacetrader.controls.MouseEventArgs e) {
        if (e.getButton() != MouseButtons.LEFT || game == null) {
            return;
        }

        StarSystem[] universe = game.getUniverse();

        boolean clickedSystem = false;

        for (int i = 0; i < universe.length && !clickedSystem; i++) {
            int x = universe[i].getX() + OFF_X;
            int y = universe[i].getY() + OFF_Y;

            if (e.getX() >= x - 2 && e.getX() <= x + 2 && e.getY() >= y - 2 && e.getY() <= y + 2) {
                clickedSystem = true;
                game.setSelectedSystemId(StarSystemId.fromInt(i));
            } else if (Functions.WormholeExists(i, -1)) {
                int xW = x + OFF_X_WORM;

                if (e.getX() >= xW - 2 && e.getX() <= xW + 2 && e.getY() >= y - 2 && e.getY() <= y + 2) {
                    clickedSystem = true;
                    game.setSelectedSystemId(StarSystemId.fromInt(i));
                    game.setTargetWormhole(true);
                }
            }
        }

        if (clickedSystem)
            // todo inline when done
            mainWindow.updateAll();
    }

    private void galacticChartPicturePaint(spacetrader.controls.PaintEventArgs e) {
        if (game != null) {
            StarSystem[] universe = game.getUniverse();
            int[] wormholes = game.getWormholes();
            StarSystem targetSys = game.getSelectedSystem();
            StarSystem curSys = commander.getCurrentSystem();
            int fuel = commander.getShip().getFuel();

            if (fuel > 0)
                e.Graphics.DrawEllipse(DEFAULT_PEN, curSys.getX() + OFF_X - fuel, curSys.getY() + OFF_Y - fuel, fuel * 2,
                        fuel * 2);

            int index = game.getSelectedSystemId().castToInt();
            if (game.isTargetWormhole()) {
                int dest = wormholes[(Util.BruteSeek(wormholes, index) + 1) % wormholes.length];
                StarSystem destSys = universe[dest];
                e.Graphics.DrawLine(DEFAULT_PEN, targetSys.getX() + OFF_X_WORM + OFF_X, targetSys.getY() + OFF_Y, destSys.getX()
                        + OFF_X, destSys.getY() + OFF_Y);
            }

            for (int i = 0; i < universe.length; i++) {
                int imageIndex = universe[i].Visited() ? IMG_S_V : IMG_S_N;
                if (universe[i] == game.getWarpSystem())
                    imageIndex++;
                Image image = ilChartImages.getImages()[imageIndex];

                if (universe[i] == game.getTrackedSystem()) {
                    e.Graphics.DrawLine(DEFAULT_PEN, universe[i].getX(), universe[i].getY(), universe[i].getX()
                            + image.getWidth() - 1, universe[i].getY() + image.getHeight() - 1);
                    e.Graphics.DrawLine(DEFAULT_PEN, universe[i].getX(), universe[i].getY() + image.getHeight() - 1,
                            universe[i].getX() + image.getWidth() - 1, universe[i].getY());
                }

                ilChartImages.draw(e.Graphics, universe[i].getX(), universe[i].getY(), imageIndex);

                if (Functions.WormholeExists(i, -1))
                    ilChartImages.draw(e.Graphics, universe[i].getX() + OFF_X_WORM, universe[i].getY(), IMG_S_W);
            }
        } else
            e.Graphics.FillRectangle(DEFAULT_BRUSH, 0, 0, galacticChartPicture.getWidth(), galacticChartPicture.getHeight());
    }

    private void jumpButtonClick() {
        if (game.getWarpSystem() == null)
            GuiFacade.alert(AlertType.ChartJumpNoSystemSelected);
        else if (game.getWarpSystem() == commander.getCurrentSystem())
            GuiFacade.alert(AlertType.ChartJumpCurrent);
        else if (GuiFacade.alert(AlertType.ChartJump, game.getWarpSystem().getName()) == DialogResult.YES) {
            game.setCanSuperWarp(false);
            try {
                controller.autoSave_depart();

                game.setWarp(true);

                controller.autoSave_arrive();
            } catch (GameEndException ex) {
                controller.gameEnd();
            }
            // todo inline when done
            mainWindow.updateAll();
        }
    }

    private void findButtonClick() {
        FormFind form = new FormFind();
        if (form.showDialog(mainWindow) == DialogResult.OK) {
            String[] words = form.SystemName().split(" ");

            boolean tryToFind = cheats.ConsiderCheat(words, controller);

            if (tryToFind) {
                game.setSelectedSystemByName(form.SystemName());
                if (form.TrackSystem()
                        && game.getSelectedSystem().getName().toLowerCase().equals(form.SystemName().toLowerCase()))
                    game.setTrackedSystemId(game.getSelectedSystemId());
            }

            // todo inline when done
            mainWindow.updateAll();
        }
    }

    void refresh() {
        galacticChartPicture.refresh();
        if (game == null) {
            wormholeLabel.setVisible(false);
            wormholeLabelValue.setVisible(false);
            jumpButton.setVisible(false);
            findButton.setVisible(false);
        } else {
            if (game.isTargetWormhole()) {
                wormholeLabel.setVisible(true);
                wormholeLabelValue.setVisible(true);
                wormholeLabelValue.setText(game.getWarpSystem().getName());
            } else {
                wormholeLabel.setVisible(false);
                wormholeLabelValue.setVisible(false);
            }
            jumpButton.setVisible(game.getCanSuperWarp());
            findButton.setVisible(true);
        }
    }

    @Override
    public int getWidth() {
        return galacticChartPicture.getWidth();
    }

    @Override
    public int getHeight() {
        return galacticChartPicture.getHeight();
    }
}
