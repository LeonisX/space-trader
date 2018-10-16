package spacetrader.controls;

import java.awt.*;

public class Graphics {

    private final java.awt.Graphics graphics;

    Graphics(java.awt.Graphics graphics) {
        this.graphics = graphics;
    }

    public void drawEllipse(Pen pen, int x, int y, int width, int height) {
        graphics.setColor(pen.color);
        graphics.drawOval(x, y, width, height);
    }

    public void drawLine(Pen pen, int x1, int y1, int x2, int y2) {
        graphics.setColor(pen.color);
        graphics.drawLine(x1, y1, x2, y2);
    }

    public void fillRectangle(Brush brush, int x, int y, int width, int height) {
        graphics.setColor(brush.color);
        graphics.fillRect(x, y, width, height);
    }

    public void fillPolygon(Brush brush, Point[] points) {
        graphics.setColor(brush.color);

        int[] xs = new int[points.length];
        int[] ys = new int[points.length];

        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            xs[i] = point.x;
            ys[i] = point.y;
        }

        graphics.fillPolygon(xs, ys, points.length);
    }

    public void drawString(String text, Font font, Brush Brush, int x, int y) {
        graphics.setColor(Brush.color);
        graphics.setFont(font);
        graphics.drawString(text, x, y);
    }

    public void clear(Color backgroundColor) {
        Color bk = graphics.getColor();
        graphics.setColor(backgroundColor);

        graphics.fillRect(0, 0, 1000000, 1000000);

        graphics.setColor(bk);
    }

    public void drawImage(Image img, int x, int y, Rectangle rect, GraphicsUnit pixel) {
        graphics.drawImage(img.asSwingImage(), x, y, x + rect.Width, y + rect.Height,
                rect.X, rect.Y, rect.X + rect.Width, rect.Y + rect.Height, null);
    }

    public SizeF measureString(String text, java.awt.Font font) {
        if (graphics == null)
            return new SizeF(30, text.length() * 5);

        FontMetrics metrics = graphics.getFontMetrics(font);
        int w = metrics.stringWidth(text);
        int h = metrics.getHeight();
        return new SizeF(h, w);
    }

}
