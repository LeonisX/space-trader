package jwinforms;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Point;

public class Graphics
{
	private final java.awt.Graphics impl;

	public Graphics(java.awt.Graphics graphics)
	{
		impl = graphics;
	}

	public void DrawEllipse(Pen pen, int x, int y, int width, int height)
	{
		impl.setColor(pen.color);
		impl.drawOval(x, y, width, height);
	}

	public void DrawLine(Pen pen, int x1, int y1, int x2, int y2)
	{
		impl.setColor(pen.color);
		impl.drawLine(x1, y1, x2, y2);
	}

	public void FillRectangle(Brush brush, int x, int y, int width, int height)
	{
		impl.setColor(brush.color);
		impl.fillRect(x, y, width, height);
	}

	public void FillPolygon(Brush brush, Point[] points)
	{
		impl.setColor(brush.color);

		int[] xs = new int[points.length];
		int[] ys = new int[points.length];

		for (int i = 0; i < points.length; i++)
		{
			Point point = points[i];
			xs[i] = point.x;
			ys[i] = point.y;
		}

		impl.fillPolygon(xs, ys, points.length);
	}

	public void DrawString(String text, Font font, Brush Brush, int x, int y)
	{
		impl.setColor(Brush.color);
		impl.setFont(font);
		impl.drawString(text, x, y);
	}

	public void clear(Color backgroundColor)
	{
		Color bk = impl.getColor();
		impl.setColor(backgroundColor);

		impl.fillRect(0, 0, 1000000, 1000000);

		impl.setColor(bk);
	}

	public void DrawImage(Image img, int x, int y, Rectangle rect, GraphicsUnit pixel)
	{
		impl.drawImage(img.asSwingImage(), x, y, x+rect.Width, y+rect.Hight,
				rect.X, rect.Y, rect.X+rect.Width, rect.Y+rect.Hight, null);
	}

	public SizeF MeasureString(String text, java.awt.Font font)
	{
		if (impl==null)
			return new SizeF(30, text.length() * 5);

		FontMetrics metrics = impl.getFontMetrics(font);
		int w = metrics.stringWidth(text);
		int h = metrics.getHeight();
		return new SizeF(h, w);
	}

}
