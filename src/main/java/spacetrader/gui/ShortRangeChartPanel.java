package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Font;
import spacetrader.controls.Panel;
import spacetrader.controls.enums.AnchorStyles;
import spacetrader.controls.enums.MouseButtons;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.*;

import java.awt.*;

import static spacetrader.game.Functions.multiples;
import static spacetrader.game.Functions.stringVars;
import static spacetrader.game.Strings.ChartDistance;
import static spacetrader.game.Strings.DistanceUnit;
import static spacetrader.gui.ChartsGraphicsConsts.*;

public class ShortRangeChartPanel extends Panel {

    private final SpaceTrader mainWindow;
    private final ImageList ilChartImages;

    private final Pen DEFAULT_PEN = new Pen(Color.black);
    private final Brush DEFAULT_BRUSH = new SolidBrush(Color.white);

    private SystemTracker game = null;
    private Commander commander;

    private PictureBox shortRangeChartPicture;
    private Font font;
    private Font smallFont;

    ShortRangeChartPanel(SpaceTrader mainWindow, ImageList images) {
        this.mainWindow = mainWindow;
        ilChartImages = images;
    }

    void setGame(SystemTracker game, Commander commander) {
        this.game = game;
        this.commander = commander;
    }

    void initializeComponent() {
        shortRangeChartPicture = new PictureBox();
        shortRangeChartPicture.setBackground(Color.white);
        shortRangeChartPicture.setLocation(new Point(8, 16));
        shortRangeChartPicture.setSize(new Size(160, 145));
        shortRangeChartPicture.setTabIndex(1);
        shortRangeChartPicture.setTabStop(false);
        shortRangeChartPicture.setPaint(new EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                shortRangeChartPicturePaint(e);
            }
        });
        shortRangeChartPicture.setMouseDown(new EventHandler<Object, MouseEventArgs>() {
            @Override
            public void handle(Object sender, MouseEventArgs e) {
                shortRangeChartPictureMouseDown(e);
            }
        });

        anchor = AnchorStyles.TOP_RIGHT;

        getControls().add(shortRangeChartPicture);

        setSize(new spacetrader.controls.Size(176, 168));
        setTabIndex(6);
        setTabStop(false);
        setText("Short-Range Chart");

        fixFonts(super.getFont());
    }

    private void updateAll() {
        // todo inline when done
        mainWindow.updateAll();
    }

    void refresh() {
        shortRangeChartPicture.refresh();
    }

    @Override
    public void setFont(Font font) {
        fixFonts(font);
        super.setFont(font);
    }

    private void fixFonts(Font base) {
        font = base;
        smallFont = (base == null) ? null : new Font(base.getFontFamily(), 7);
    }

    private void shortRangeChartPicturePaint(spacetrader.controls.PaintEventArgs e) {
        if (game != null) {
            StarSystem[] universe = game.getUniverse();
            StarSystem trackSys = game.getTrackedSystem();
            StarSystem curSys = commander.getCurrentSystem();
            int fuel = commander.getShip().getFuel();

            int centerX = shortRangeChartPicture.getWidth() / 2;
            int centerY = shortRangeChartPicture.getHeight() / 2;
            int delta = shortRangeChartPicture.getHeight() / (Consts.MaxRange * 2);

            e.getGraphics().drawLine(DEFAULT_PEN, centerX - 1, centerY - 1, centerX + 1, centerY + 1);
            e.getGraphics().drawLine(DEFAULT_PEN, centerX - 1, centerY + 1, centerX + 1, centerY - 1);
            if (fuel > 0) {
                e.getGraphics().drawEllipse(DEFAULT_PEN, centerX - fuel * delta, centerY - fuel * delta,
                        fuel * delta * 2, fuel * delta * 2);
            }

            if (trackSys != null) {
                int dist = Functions.distance(curSys, trackSys);
                if (dist > 0) {
                    int dX = (int) Math.round(25 * (trackSys.getX() - curSys.getX()) / (double) dist);
                    int dY = (int) Math.round(25 * (trackSys.getY() - curSys.getY()) / (double) dist);

                    int dX2 = (int) Math.round(4 * (trackSys.getY() - curSys.getY()) / (double) dist);
                    int dY2 = (int) Math.round(4 * (curSys.getX() - trackSys.getX()) / (double) dist);

                    e.getGraphics().fillPolygon(new SolidBrush(new Color(220, 20, 60)), new Point[]{
                            new Point(centerX + dX, centerY + dY), new Point(centerX - dX2, centerY - dY2),
                            new Point(centerX + dX2, centerY + dY2)});
                }

                if (game.isShowTrackedRange())
                    e.getGraphics().drawString(stringVars(ChartDistance, multiples(dist, DistanceUnit),
                            trackSys.getName()), font, new SolidBrush(Color.black), 0,
                            shortRangeChartPicture.getHeight() - 13);
            }

            // Two loops: first draw the names and then the systems. The names may
            // overlap and the systems may be drawn on the names, but at least every
            // system is visible.
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < universe.length; i++) {
                    if ((Math.abs(universe[i].getX() - curSys.getX()) * delta <= shortRangeChartPicture.getWidth() / 2 - 10)
                            && (Math.abs(universe[i].getY() - curSys.getY()) * delta <= shortRangeChartPicture.getHeight() / 2 - 10)) {
                        int x = centerX + (universe[i].getX() - curSys.getX()) * delta;
                        int y = centerY + (universe[i].getY() - curSys.getY()) * delta;

                        if (j == 1) {
                            if (universe[i] == game.getWarpSystem()) {
                                e.getGraphics().drawLine(DEFAULT_PEN, x - 6, y, x + 6, y);
                                e.getGraphics().drawLine(DEFAULT_PEN, x, y - 6, x, y + 6);
                            }

                            if (universe[i] == game.getTrackedSystem()) {
                                e.getGraphics().drawLine(DEFAULT_PEN, x - 5, y - 5, x + 5, y + 5);
                                e.getGraphics().drawLine(DEFAULT_PEN, x - 5, y + 5, x + 5, y - 5);
                            }

                            ilChartImages.draw(e.getGraphics(), x - OFF_X, y - OFF_Y, universe[i].isVisited()
                                    ? IMG_G_V : IMG_G_N);

                            if (Functions.wormholeExists(i, -1)) {
                                int xW = x + 9;
                                if (game.isTargetWormhole() && universe[i] == game.getSelectedSystem()) {
                                    e.getGraphics().drawLine(DEFAULT_PEN, xW - 6, y, xW + 6, y);
                                    e.getGraphics().drawLine(DEFAULT_PEN, xW, y - 6, xW, y + 6);
                                }
                                ilChartImages.draw(e.getGraphics(), xW - OFF_X, y - OFF_Y, IMG_G_W);
                            }
                        } else {
                            SizeF size = e.getGraphics().measureString(universe[i].getName(), getFont());
                            e.getGraphics().drawString(universe[i].getName(), smallFont, new SolidBrush(Color.black),
                                    x - size.width / 2 + OFF_X, y /*- size.Height*/ - 5);
                            // implementations differ as to which point we start the string at. --aviv
                        }
                    }
                }
            }
        } else {
            e.getGraphics()
                    .fillRectangle(DEFAULT_BRUSH, 0, 0, shortRangeChartPicture.getWidth(), shortRangeChartPicture.getHeight());
        }
    }

    private void shortRangeChartPictureMouseDown(spacetrader.controls.MouseEventArgs e) {
        if (e.getButton() == MouseButtons.LEFT && game != null) {
            StarSystem[] universe = game.getUniverse();
            StarSystem curSys = commander.getCurrentSystem();

            boolean clickedSystem = false;
            int centerX = shortRangeChartPicture.getWidth() / 2;
            int centerY = shortRangeChartPicture.getHeight() / 2;
            int delta = shortRangeChartPicture.getHeight() / (Consts.MaxRange * 2);

            for (int i = 0; i < universe.length && !clickedSystem; i++) {
                if ((Math.abs(universe[i].getX() - curSys.getX()) * delta <= shortRangeChartPicture.getWidth() / 2 - 10)
                        && (Math.abs(universe[i].getY() - curSys.getY()) * delta <= shortRangeChartPicture.getHeight() / 2 - 10)) {
                    int x = centerX + (universe[i].getX() - curSys.getX()) * delta;
                    int y = centerY + (universe[i].getY() - curSys.getY()) * delta;

                    if (e.getX() >= x - OFF_X && e.getX() <= x + OFF_X && e.getY() >= y - OFF_Y && e.getY() <= y + OFF_Y) {
                        clickedSystem = true;
                        game.setSelectedSystemId(StarSystemId.fromInt(i));
                    } else if (Functions.wormholeExists(i, -1)) {
                        int xW = x + 9;

                        if (e.getX() >= xW - OFF_X && e.getX() <= xW + OFF_X && e.getY() >= y - OFF_Y && e.getY() <= y + OFF_Y) {
                            clickedSystem = true;
                            game.setSelectedSystemId(StarSystemId.fromInt(i));
                            game.setTargetWormhole(true);
                        }
                    }
                }
            }

            if (clickedSystem) {
                updateAll();
            }
        }
    }
}
