package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Button;
import spacetrader.controls.Image;
import spacetrader.controls.Label;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.DialogResult;
import spacetrader.controls.enums.MouseButtons;
import spacetrader.game.Commander;
import spacetrader.game.Game;
import spacetrader.game.GameController;
import spacetrader.game.StarSystem;
import spacetrader.game.cheat.GameCheats;
import spacetrader.game.enums.AlertType;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.exceptions.GameEndException;
import spacetrader.guifacade.GuiFacade;
import spacetrader.util.Functions;

import java.awt.*;

import static spacetrader.gui.ChartsGraphicsConsts.*;

public class GalacticChartPanel extends Panel {

    private final Pen DEFAULT_PEN = new Pen(Color.BLACK);
    private final Brush DEFAULT_BRUSH = new Brush(Color.WHITE);

    private final SpaceTrader mainWindow;

    private final ImageList ilChartImages;

    private Game game = null;
    private GameController controller = null;
    private Commander commander;
    private GameCheats cheats;

    private Label wormholeLabelValue = new Label();
    private Label wormholeLabel = new Label();
    private Button jumpButton = new Button();
    private Button findButton = new Button();
    private PictureBox galacticChartPicture = new PictureBox();

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
        setText("Galactic Chart");
        setBackground(SystemColors.CONTROL);
        setSize(191, 175);
        setTabStop(false);
        
        galacticChartPicture.setBackground(Color.WHITE);
        galacticChartPicture.setLocation(8, 16);
        galacticChartPicture.setSize(175, 121);
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

        wormholeLabel.setAutoSize(true);
        wormholeLabel.setLocation(8, 140);
        //wormholeLabel.setSize(72, 13);
        wormholeLabel.setTabIndex(28);
        wormholeLabel.setText("Wormhole to");

        wormholeLabelValue.setAutoSize(true);
        wormholeLabelValue.setLocation(8, 153);
        //wormholeLabelValue.setSize(72, 13);
        wormholeLabelValue.setTabIndex(29);
        //wormholeLabelValue.setText("Tarchannen");

        jumpButton.setAutoWidth(true);
        jumpButton.setControlBinding(ControlBinding.RIGHT);
        jumpButton.setLocation(96, 143);
        jumpButton.setSize(42, 22);
        jumpButton.setTabIndex(55);
        jumpButton.setText("Jump");
        jumpButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                jumpButtonClick();
            }
        });

        findButton.setAutoWidth(true);
        findButton.setControlBinding(ControlBinding.RIGHT);
        findButton.setLocation(147, 143);
        findButton.setSize(36, 22);
        findButton.setTabIndex(56);
        findButton.setText("Find");
        findButton.setClick(new EventHandler<Object, EventArgs>() {
            @Override
            public void handle(Object sender, spacetrader.controls.EventArgs e) {
                findButtonClick();
            }
        });

        getControls().addAll(wormholeLabelValue, wormholeLabel, jumpButton, findButton, galacticChartPicture);
    }

    private void galacticChartPictureMouseDown(MouseEventArgs e) {
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
            } else if (Functions.wormholeExists(i, -1)) {
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
                e.getGraphics().drawEllipse(DEFAULT_PEN, curSys.getX() + OFF_X - fuel, curSys.getY() + OFF_Y - fuel,
                        fuel * 2, fuel * 2);

            int index = game.getSelectedSystemId().castToInt();
            if (game.isTargetWormhole()) {
                int dest = wormholes[(Functions.bruteSeek(wormholes, index) + 1) % wormholes.length];
                StarSystem destSys = universe[dest];
                e.getGraphics().drawLine(DEFAULT_PEN, targetSys.getX() + OFF_X_WORM + OFF_X, targetSys.getY() + OFF_Y,
                        destSys.getX() + OFF_X, destSys.getY() + OFF_Y);
            }

            for (int i = 0; i < universe.length; i++) {
                int imageIndex = universe[i].isVisited() ? IMG_S_V : IMG_S_N;
                if (universe[i] == game.getWarpSystem()) {
                    imageIndex++;
                }
                Image image = ilChartImages.getImages()[imageIndex];

                if (universe[i] == game.getTrackedSystem()) {
                    e.getGraphics().drawLine(DEFAULT_PEN, universe[i].getX(), universe[i].getY(), universe[i].getX()
                            + image.getWidth() - 1, universe[i].getY() + image.getHeight() - 1);
                    e.getGraphics().drawLine(DEFAULT_PEN, universe[i].getX(), universe[i].getY() + image.getHeight() - 1,
                            universe[i].getX() + image.getWidth() - 1, universe[i].getY());
                }

                ilChartImages.draw(e.getGraphics(), universe[i].getX(), universe[i].getY(), imageIndex);

                if (Functions.wormholeExists(i, -1)) {
                    ilChartImages.draw(e.getGraphics(), universe[i].getX() + OFF_X_WORM, universe[i].getY(), IMG_S_W);
                }
            }
        } else {
            e.getGraphics().fillRectangle(DEFAULT_BRUSH, 0, 0, galacticChartPicture.getWidth(), galacticChartPicture.getHeight());
        }
    }

    private void jumpButtonClick() {
        if (null == game.getWarpSystem()) {
            GuiFacade.alert(AlertType.ChartJumpNoSystemSelected);
        } else if (game.getWarpSystem() == commander.getCurrentSystem()) {
            GuiFacade.alert(AlertType.ChartJumpCurrent);
        } else if (GuiFacade.alert(AlertType.ChartJump, game.getWarpSystem().getName()) == DialogResult.YES) {
            game.setCanSuperWarp(false);
            try {
                controller.autoSaveOnDeparture();
                game.setWarp(true);
                controller.autoSaveOnArrival();
            } catch (GameEndException ex) {
                controller.gameEnd();
            }
            // todo inline when done
            mainWindow.updateAll();
        }
    }

    private void findButtonClick() {
        FormFind form = new FormFind(game.getPreviousSearchPhrase());
        if (form.showDialog(mainWindow) == DialogResult.OK) {
            game.setPreviousSearchPhrase(form.getSearchPhrase());
            String[] words = form.getSystemName().split(" ");

            boolean tryToFind = cheats.isConsiderCheat(words, controller);

            if (tryToFind) {
                game.setSelectedSystemByName(form.getSystemName());
                if (form.isTrackSystem() && game.getSelectedSystem().getName().equalsIgnoreCase(form.getSystemName())) {
                    game.setTrackedSystemId(game.getSelectedSystemId());
                }
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
