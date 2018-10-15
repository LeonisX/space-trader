package spacetrader.gui;

import spacetrader.controls.*;
import spacetrader.controls.Font;
import spacetrader.controls.Panel;
import spacetrader.game.enums.StarSystemId;
import spacetrader.game.*;

import java.awt.*;

public class ShortRangeChartPanel extends Panel {

    private final SpaceTrader mainWindow;
    private final spacetrader.controls.ImageList ilChartImages;
    private final int OFF_X = ChartsGraphicsConsts.OFF_X;
    private final int OFF_Y = ChartsGraphicsConsts.OFF_Y;
    private final int IMG_G_N = ChartsGraphicsConsts.IMG_G_N;
    private final int IMG_G_V = ChartsGraphicsConsts.IMG_G_V;
    private final int IMG_G_W = ChartsGraphicsConsts.IMG_G_W;
    private final Pen DEFAULT_PEN = new Pen(Color.black);
    private final Brush DEFAULT_BRUSH = new SolidBrush(Color.white);
    private SystemTracker game = null;
    private Commander commander;
    private spacetrader.controls.PictureBox picShortRangeChart;
    private Font font;
    private Font smallFont;

    ShortRangeChartPanel(SpaceTrader mainWindow, ImageList images, String name) {
        this.mainWindow = mainWindow;
        ilChartImages = images;
        setName(name);
    }

    void setGame(SystemTracker game, Commander commander) {
        this.game = game;
        this.commander = commander;
    }

    void initializeComponent() {
        picShortRangeChart = new spacetrader.controls.PictureBox("linePictureBox");

        //
        // picShortRangeChart
        //

        picShortRangeChart.setBackground(Color.white);
        picShortRangeChart.setLocation(new Point(8, 16));
        picShortRangeChart.setName("picShortRangeChart");
        picShortRangeChart.setSize(new spacetrader.controls.Size(160, 145));
        picShortRangeChart.setTabIndex(1);
        picShortRangeChart.setTabStop(false);
        picShortRangeChart.setPaint(new spacetrader.controls.EventHandler<Object, PaintEventArgs>() {
            @Override
            public void handle(Object sender, PaintEventArgs e) {
                picShortRangeChart_Paint(sender, e);
            }
        });
        picShortRangeChart.setMouseDown(new spacetrader.controls.EventHandler<Object, MouseEventArgs>() {
            @Override
            public void handle(Object sender, MouseEventArgs e) {
                picShortRangeChart_MouseDown(sender, e);
            }
        });

        anchor = (((AnchorStyles.Top_Right)));
        controls.add(picShortRangeChart);
        setSize(new spacetrader.controls.Size(176, 168));
        setTabIndex(6);
        setTabStop(false);
        setText("Short-Range Chart");

        fixFonts(super.getFont());
    }

    private void picShortRangeChart_MouseDown(Object sender, spacetrader.controls.MouseEventArgs e) {
        if (e.Button == MouseButtons.Left && game != null) {
            StarSystem[] universe = game.Universe();
            StarSystem curSys = commander.getCurrentSystem();

            boolean clickedSystem = false;
            int centerX = picShortRangeChart.getWidth() / 2;
            int centerY = picShortRangeChart.getHeight() / 2;
            int delta = picShortRangeChart.getHeight() / (Consts.MaxRange * 2);

            for (int i = 0; i < universe.length && !clickedSystem; i++) {
                if ((Math.abs(universe[i].X() - curSys.X()) * delta <= picShortRangeChart.getWidth() / 2 - 10)
                        && (Math.abs(universe[i].Y() - curSys.Y()) * delta <= picShortRangeChart.getHeight() / 2 - 10)) {
                    int x = centerX + (universe[i].X() - curSys.X()) * delta;
                    int y = centerY + (universe[i].Y() - curSys.Y()) * delta;

                    if (e.X >= x - OFF_X && e.X <= x + OFF_X && e.Y >= y - OFF_Y && e.Y <= y + OFF_Y) {
                        clickedSystem = true;
                        game.SelectedSystemId(StarSystemId.fromInt(i));
                    } else if (Functions.WormholeExists(i, -1)) {
                        int xW = x + 9;

                        if (e.X >= xW - OFF_X && e.X <= xW + OFF_X && e.Y >= y - OFF_Y && e.Y <= y + OFF_Y) {
                            clickedSystem = true;
                            game.SelectedSystemId((StarSystemId.fromInt(i)));
                            game.TargetWormhole(true);
                        }
                    }
                }
            }

            if (clickedSystem)
                UpdateAll();
        }
    }

    private void UpdateAll() {
        // todo inline when done
        mainWindow.updateAll();
    }

    private void picShortRangeChart_Paint(Object sender, spacetrader.controls.PaintEventArgs e) {
        if (game != null) {
            StarSystem[] universe = game.Universe();
            int[] wormholes = game.Wormholes();
            StarSystem trackSys = game.TrackedSystem();
            StarSystem curSys = commander.getCurrentSystem();
            int fuel = commander.getShip().getFuel();

            int centerX = picShortRangeChart.getWidth() / 2;
            int centerY = picShortRangeChart.getHeight() / 2;
            int delta = picShortRangeChart.getHeight() / (Consts.MaxRange * 2);

            e.Graphics.DrawLine(DEFAULT_PEN, centerX - 1, centerY - 1, centerX + 1, centerY + 1);
            e.Graphics.DrawLine(DEFAULT_PEN, centerX - 1, centerY + 1, centerX + 1, centerY - 1);
            if (fuel > 0)
                e.Graphics.DrawEllipse(DEFAULT_PEN, centerX - fuel * delta, centerY - fuel * delta, fuel * delta * 2,
                        fuel * delta * 2);

            if (trackSys != null) {
                int dist = Functions.Distance(curSys, trackSys);
                if (dist > 0) {
                    int dX = (int) Math.round(25 * (trackSys.X() - curSys.X()) / (double) dist);
                    int dY = (int) Math.round(25 * (trackSys.Y() - curSys.Y()) / (double) dist);

                    int dX2 = (int) Math.round(4 * (trackSys.Y() - curSys.Y()) / (double) dist);
                    int dY2 = (int) Math.round(4 * (curSys.X() - trackSys.X()) / (double) dist);

                    e.Graphics.FillPolygon(new SolidBrush(new Color(220, 20, 60)), new Point[]{
                            new Point(centerX + dX, centerY + dY), new Point(centerX - dX2, centerY - dY2),
                            new Point(centerX + dX2, centerY + dY2)});
                }

                if (game.isShowTrackedRange())
                    e.Graphics.DrawString(Functions.stringVars(Strings.ChartDistance, Functions.Multiples(dist,
                            Strings.DistanceUnit), trackSys.name()), font, new SolidBrush(Color.black), 0,
                            picShortRangeChart.getHeight() - 13);
            }

            // Two loops: first draw the names and then the systems. The names
            // may
            // overlap and the systems may be drawn on the names, but at least
            // every
            // system is visible.
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < universe.length; i++) {
                    if ((Math.abs(universe[i].X() - curSys.X()) * delta <= picShortRangeChart.getWidth() / 2 - 10)
                            && (Math.abs(universe[i].Y() - curSys.Y()) * delta <= picShortRangeChart.getHeight() / 2 - 10)) {
                        int x = centerX + (universe[i].X() - curSys.X()) * delta;
                        int y = centerY + (universe[i].Y() - curSys.Y()) * delta;

                        if (j == 1) {
                            if (universe[i] == game.WarpSystem()) {
                                e.Graphics.DrawLine(DEFAULT_PEN, x - 6, y, x + 6, y);
                                e.Graphics.DrawLine(DEFAULT_PEN, x, y - 6, x, y + 6);
                            }

                            if (universe[i] == game.TrackedSystem()) {
                                e.Graphics.DrawLine(DEFAULT_PEN, x - 5, y - 5, x + 5, y + 5);
                                e.Graphics.DrawLine(DEFAULT_PEN, x - 5, y + 5, x + 5, y - 5);
                            }

                            ilChartImages.draw(e.Graphics, x - OFF_X, y - OFF_Y, universe[i].Visited() ? IMG_G_V
                                    : IMG_G_N);

                            if (Functions.WormholeExists(i, -1)) {
                                int xW = x + 9;
                                if (game.TargetWormhole() && universe[i] == game.SelectedSystem()) {
                                    e.Graphics.DrawLine(DEFAULT_PEN, xW - 6, y, xW + 6, y);
                                    e.Graphics.DrawLine(DEFAULT_PEN, xW, y - 6, xW, y + 6);
                                }
                                ilChartImages.draw(e.Graphics, xW - OFF_X, y - OFF_Y, IMG_G_W);
                            }
                        } else {
                            SizeF size = e.Graphics.MeasureString(universe[i].name(), getFont());
                            e.Graphics.DrawString(universe[i].name(), smallFont, new SolidBrush(Color.black), x
                                    - size.width / 2 + OFF_X, y /*- size.Height*/ - 5);
                            // implementations differ as to which point we start the string at. --aviv
                        }
                    }
                }
            }
        } else
            e.Graphics
                    .FillRectangle(DEFAULT_BRUSH, 0, 0, picShortRangeChart.getWidth(), picShortRangeChart.getHeight());
    }

    public void Refresh() {
        picShortRangeChart.refresh();
    }

    @Override
    public void setFont(Font font) {
        fixFonts(font);
        super.setFont(font);
    }

    private void fixFonts(Font base) {
        font = base;
        smallFont = base == null ? null : new Font(base.FontFamily, 7);
    }
}
