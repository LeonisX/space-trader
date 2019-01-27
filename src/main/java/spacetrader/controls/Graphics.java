package spacetrader.controls;

import spacetrader.controls.enums.ControlBinding;
import spacetrader.controls.enums.GraphicsUnit;
import spacetrader.game.Consts;
import spacetrader.game.Game;
import spacetrader.game.Ship;

import java.awt.*;

public class Graphics {

    private final java.awt.Graphics graphics;

    Graphics(java.awt.Graphics graphics) {
        this.graphics = graphics;
    }

    public void drawEllipse(Pen pen, int x, int y, int width, int height) {
        graphics.setColor(pen.getColor());
        graphics.drawOval(x, y, width, height);
    }

    public void drawLine(Pen pen, int x1, int y1, int x2, int y2) {
        graphics.setColor(pen.getColor());
        graphics.drawLine(x1, y1, x2, y2);
    }

    public void fillRectangle(Brush brush, int x, int y, int width, int height) {
        graphics.setColor(brush.getColor());
        graphics.fillRect(x, y, width, height);
    }

    public void fillPolygon(Brush brush, Point[] points) {
        graphics.setColor(brush.getColor());

        int[] xs = new int[points.length];
        int[] ys = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            xs[i] = points[i].x;
            ys[i] = points[i].y;
        }

        graphics.fillPolygon(xs, ys, points.length);
    }

    public void drawString(String text, Font font, Brush brush, int x, int y) {
        graphics.setColor(brush.getColor());
        graphics.setFont(font);
        graphics.drawString(text, x, y);
    }

    public void clear(Color backgroundColor) {
        Color bk = graphics.getColor();
        graphics.setColor(backgroundColor);

        graphics.fillRect(0, 0, 1000000, 1000000);

        graphics.setColor(bk);
    }

    void drawImage(Image img, int x, int y, Rectangle rect, GraphicsUnit pixel) {
        graphics.drawImage(img.asSwingImage(), x, y, x + rect.getWidth(), y + rect.getHeight(),
                rect.getX(), rect.getY(), rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight(), null);
    }

    public Dimension measureString(String text, java.awt.Font font) {
        if (graphics == null) {
            return new Dimension(text.length() * 5, 30);
        }

        FontMetrics metrics = graphics.getFontMetrics(font);
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        return new Dimension(w, h);
    }

    public static void paintShipImage(Ship ship, Graphics graphics, Color backgroundColor) {
        Rectangle shipImageOffset = (ship.getId() < 1000)
                ? Consts.ShipImageOffsets[ship.getType().castToInt()]
                : Game.getCurrentGame().getQuestSystem().getShipImageOffset(ship.getId());

        int x = shipImageOffset.getX(); //TODO multiple
        int width = shipImageOffset.getWidth();

        int startDamage = x + width - ship.getHull() * width / ship.getHullStrength();
        int startShield = x + width + 2
                - (ship.getShieldStrength() > 0 ? ship.getShieldCharge() * (width + 4) / ship.getShieldStrength() : 0);

        graphics.clear(backgroundColor);

        if (startDamage > x) {
            if (startShield > x) {
                drawPartialImage(graphics, ship.getImageDamaged(), x, Math.min(startDamage, startShield));
            }
            if (startShield < startDamage) {
                drawPartialImage(graphics, ship.getImageDamagedWithShields(), startShield, startDamage);
            }
        }

        if (startShield > startDamage) {
            drawPartialImage(graphics, ship.getImage(), startDamage, startShield);
        }
        if (startShield < x + width + 2) {
            drawPartialImage(graphics, ship.getImageWithShields(), startShield, x + width + 2);
        }
    }

    private static void drawPartialImage(Graphics g, Image img, int start, int stop) {
        g.drawImage(img, 2 + start, 2, new Rectangle(start, 0, stop - start, img.getHeight()), GraphicsUnit.PIXEL);
    }

    public static int getColumnOfFirstNonWhitePixel(Image image, int direction) {
        Bitmap bitmap = new Bitmap(image);
        int step = (direction < 0) ? -1 : 1;
        int col = (step > 0) ? 0 : bitmap.getWidth() - 1;
        int stop = (step > 0) ? bitmap.getWidth() : -1;

        for (; col != stop; col += step) {
            for (int row = 0; row < bitmap.getHeight(); row++) {
                if (bitmap.toArgb(col, row) != 0) {
                    return col;
                }
            }
        }
        return -1;
    }

    public static void resizeIfNeed(Component component, boolean autoSize, boolean autoWidth, boolean autoHeight,
                                    ControlBinding binding) {
        if (!(autoSize || autoWidth || autoHeight)) {
            return;
        }

        Dimension oldSize = component.getSize();
        Dimension preferredSize = component.getPreferredSize();
        double newHeight = oldSize.getHeight();
        double newWidth = oldSize.getWidth();

        if (autoSize || autoHeight) {
            newHeight = preferredSize.getHeight();
        }

        if (autoSize || autoWidth) {
            newWidth = preferredSize.getWidth();

            switch (binding) {
                case LEFT:
                    // Nothing else to do
                    break;
                case RIGHT:
                    component.setLocation(component.getX() + (int) (oldSize.getWidth() - newWidth),
                            component.getY());
                    break;
                case CENTER:
                    component.setLocation(component.getX() + (int) (oldSize.getWidth() - newWidth) / 2,
                            component.getY());
                    break;
            }
        }

        Dimension newSize = new Dimension((int) newWidth, (int) newHeight);

        if (!newSize.equals(oldSize)) {
            component.setSize(newSize);
        }
    }
}
